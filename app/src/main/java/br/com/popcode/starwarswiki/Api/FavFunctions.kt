package br.com.popcode.starwarswiki.api

import android.content.Context
import android.util.Log
import android.widget.Toast
import br.com.popcode.starwarswiki.Constants
import br.com.popcode.starwarswiki.helpers.DB
import br.com.popcode.starwarswiki.models.FavFail
import br.com.popcode.starwarswiki.models.FavSuccess
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Response
import java.security.SecureRandom

class FavFunctions(val context: Context) {

    val gson = GsonBuilder().create()

    val service = Swapi().fav_service

    val characterDao = DB(context).characterDao

    fun favoriteChar(b: Boolean, name: String) {

        val random = SecureRandom()

        val dice = random.nextInt(100)

        val request = if (dice % 2 == 0) service.favCharacter(42, 201)
        else service.favCharacter(42, 400)

        request.enqueue(object : retrofit2.Callback<FavSuccess> {
            override fun onFailure(call: Call<FavSuccess>, t: Throwable) {
                Toast.makeText(context, t.message, Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<FavSuccess>, response: Response<FavSuccess>) {

                Log.e(Constants.TAG, response.message())
                Log.e(Constants.TAG, response.code().toString())

                val text = if (response.isSuccessful) {
                    response.body()!!.message!!
                } else {
                    val favFail = gson.fromJson(response.errorBody()!!.string(), FavFail::class.java)

                    favFail.errorMessage!!
                }

                Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
                Log.e(Constants.TAG, text)
            }
        })
        characterDao.favChar(b, name)
    }

}
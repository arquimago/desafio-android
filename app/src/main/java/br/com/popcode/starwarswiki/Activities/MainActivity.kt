package br.com.popcode.starwarswiki.Activities

import android.app.SearchManager
import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Menu
import android.widget.SearchView
import br.com.popcode.starwarswiki.Api.Swapi
import br.com.popcode.starwarswiki.Models.Character
import br.com.popcode.starwarswiki.Adapters.ListAdapter
import br.com.popcode.starwarswiki.Constants
import br.com.popcode.starwarswiki.R
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    val service = Swapi().service

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Hawk.init(this).build()

        my_toolbar.title = ""
        setSupportActionBar(my_toolbar)

        rv_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        var request = service.getAllPeople(1)
        request.enqueue(object : retrofit2.Callback<MutableList<Character>> {
            override fun onFailure(call: Call<MutableList<Character>>, t: Throwable) {
                Log.e(Constants.TAG,t.message)
            }

            override fun onResponse(call: Call<MutableList<Character>>, response: Response<MutableList<Character>>) {
                if(response.isSuccessful){
                    val list: MutableList<Character> = response.body()!!
                    rv_list.adapter = ListAdapter(list)
                } else {
                    Log.e(Constants.TAG,"resposta " + response.message())
                }

            }

        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_search, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu!!.findItem(R.id.search).actionView as SearchView
        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = getString(R.string.search_hint)

        searchView.isSubmitButtonEnabled = true
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.

                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }
}

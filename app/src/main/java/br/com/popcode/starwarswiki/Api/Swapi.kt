package br.com.popcode.starwarswiki.api

import br.com.popcode.starwarswiki.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Swapi {

    val service = Retrofit.Builder()
            .baseUrl(Constants.BASE_SW_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SwQueries::class.java)

    val fav_service = Retrofit.Builder()
            .baseUrl(Constants.BASE_FAV_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FavQueries::class.java)

}
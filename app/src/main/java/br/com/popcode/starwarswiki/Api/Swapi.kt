package br.com.popcode.starwarswiki.Api

import br.com.popcode.starwarswiki.Constants
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

public class Swapi {

    val retrofit = Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

}
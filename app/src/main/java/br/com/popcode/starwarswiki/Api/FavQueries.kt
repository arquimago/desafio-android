package br.com.popcode.starwarswiki.api

import br.com.popcode.starwarswiki.models.FavFail
import br.com.popcode.starwarswiki.models.FavSuccess
import retrofit2.Call
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

interface FavQueries {

    @POST("favorite/{id}")
    fun favCharacter(@Path("id") peopleId: Int, @Header("Prefer") prefer: Int): Call<FavSuccess>

}
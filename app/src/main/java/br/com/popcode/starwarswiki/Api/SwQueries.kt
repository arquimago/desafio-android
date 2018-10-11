package br.com.popcode.starwarswiki.api

import br.com.popcode.starwarswiki.models.AllPeople
import br.com.popcode.starwarswiki.models.Character
import br.com.popcode.starwarswiki.models.Planet
import br.com.popcode.starwarswiki.models.Species
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface SwQueries {

    @GET("people/")
    fun getAllPeople(@Query("page") page: Int) : Call<AllPeople>

    @GET("people/{id}/")
    fun getPeople(@Path("id") peopleId: Int): Call<Character>

    @GET("species/{id}/")
    fun getSpecies(@Path("id") speciesId: Int): Call<Species>

    @GET("planets/{id}/")
    fun getPlanet(@Path("id") planetId: Int): Call<Planet>
}
package br.com.popcode.starwarswiki.Api

import retrofit2.Callback
import retrofit2.http.GET
import br.com.popcode.starwarswiki.Models.*
import retrofit2.Call
import retrofit2.http.Query
import retrofit2.http.Path


public interface SwQueries{

    @GET("people/")
    fun getAllPeople(@Query("page") page: Int): Call<MutableList<Character>>

    @GET("people/{id}/")
    fun getPeople(@Path("id") peopleId: Int): Call<Character>

    @GET("species/{id}/")
    fun getSpecies(@Path("id") speciesId: Int) : Call<Species>

    @GET("planets/{id}/")
    fun getPlanet(@Path("id") planetId: Int) : Call<Planet>
}
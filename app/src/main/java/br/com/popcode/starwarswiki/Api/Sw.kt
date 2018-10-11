package br.com.popcode.starwarswiki.api

import android.util.Log
import br.com.popcode.starwarswiki.Constants
import br.com.popcode.starwarswiki.models.AllPeople
import br.com.popcode.starwarswiki.models.Character
import br.com.popcode.starwarswiki.models.Planet
import br.com.popcode.starwarswiki.models.Species
import retrofit2.Call
import retrofit2.Response

class Sw {

    val service = Swapi().service

    fun getPeople(page: Int, listener: PeopleListener) {
        val request = service.getAllPeople(page)
        request.enqueue(object : retrofit2.Callback<AllPeople> {
            override fun onFailure(call: Call<AllPeople>, t: Throwable) {
                t.stackTrace.forEach {
                    Log.e(Constants.TAG, it.toString())
                }
                listener.onFailure(t)
            }

            override fun onResponse(call: Call<AllPeople>, response: Response<AllPeople>) {
                if (response.isSuccessful) {
                    listener.onResponse(response.body()?.next, response.body()!!.results)
                }
            }

        })
    }

    interface PeopleListener {
        fun onResponse(next: String?, chars: MutableList<Character>)
        fun onFailure(t: Throwable)
    }

    fun getPlanet(url: String, listener: PlanetListener){

        val id = url.replace("https://swapi.co/api/planets/","").replace("/","").toInt()
        val request = service.getPlanet(id)
        request.enqueue(object : retrofit2.Callback<Planet> {
            override fun onFailure(call: Call<Planet>, t: Throwable) {
                listener.onFailure(t)
            }

            override fun onResponse(call: Call<Planet>, response: Response<Planet>) {
                if(response.isSuccessful) listener.onResponse(response.body()!!)
                else listener.onFailure(Throwable("Falha"))
            }

        })

    }

    interface PlanetListener{
        fun onResponse(planet: Planet)
        fun onFailure(t: Throwable)
    }

    fun getSpecies(url: String, listener: SpeciesListener){
        val id = url.replace("https://swapi.co/api/species/","").replace("/","").toInt()
        val request = service.getSpecies(id)
        request.enqueue(object : retrofit2.Callback<Species> {

            override fun onFailure(call: Call<Species>, t: Throwable) {
                listener.onFailure(t)
            }

            override fun onResponse(call: Call<Species>, response: Response<Species>) {
                if(response.isSuccessful) listener.onResponse(response.body()!!)
                else listener.onFailure(Throwable("Falha"))
            }
        })
    }

    interface SpeciesListener{
        fun onResponse(species: Species)
        fun onFailure(t: Throwable)
    }

}
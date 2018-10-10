package br.com.popcode.starwarswiki.Api

import br.com.popcode.starwarswiki.Models.Character
import br.com.popcode.starwarswiki.Models.Planet
import br.com.popcode.starwarswiki.Models.Species
import retrofit2.Call
import retrofit2.Response

class Sw {

    val service = Swapi().service

    fun getPeople(listener: PeopleListener) {
        for (i in 1..87) {
            val request = service.getPeople(i)
            request.enqueue(object : retrofit2.Callback<Character> {
                override fun onFailure(call: Call<Character>, t: Throwable) {
                    listener.onFailure(t)
                }

                override fun onResponse(call: Call<Character>, response: Response<Character>) {
                    if (response.isSuccessful) {
                        listener.onResponse(response.body()!!)
                    }
                }

            })
        }
    }

    interface PeopleListener {
        fun onResponse(char: Character)
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
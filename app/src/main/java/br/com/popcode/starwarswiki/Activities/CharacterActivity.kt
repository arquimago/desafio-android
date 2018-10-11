package br.com.popcode.starwarswiki.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.popcode.starwarswiki.api.Sw
import br.com.popcode.starwarswiki.models.Character
import br.com.popcode.starwarswiki.models.Planet
import br.com.popcode.starwarswiki.models.Species
import br.com.popcode.starwarswiki.R
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_character.*

class CharacterActivity : AppCompatActivity() {

    var character: Character = Hawk.get("character")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        if (character.favorite) fav_button.setImageResource(R.drawable.ic_star_gold_24dp)

        val height = if (character.height != "unknown") "${getString(R.string.height)} ${character.height}cm" else "${getString(R.string.height)} unknown"
        val mass = if (character.mass != "unknown") "${getString(R.string.mass)} ${character.mass}kg" else "${getString(R.string.mass)} unknown"
        val hairColor = "${getString(R.string.hair_color)} ${character.hairColor}"
        val skinColor = "${getString(R.string.skin_color)} ${character.skinColor}"
        val eyeColor = "${getString(R.string.eye_color)} ${character.eyeColor}"
        val birthYear = "${getString(R.string.birth_year)} ${character.birthYear}"
        val gender = "${getString(R.string.gender)} ${character.gender}"

        tv_name.text = character.name
        tv_height.text = height
        tv_mass.text = mass
        tv_hair_color.text = hairColor
        tv_skin_color.text = skinColor
        tv_eye_color.text = eyeColor
        tv_birth_year.text = birthYear
        tv_gender.text = gender
        Sw().getPlanet(character.homeworld!!, PlanetListener())
        Sw().getSpecies(character.species!![0], SpeciesListener())

        back_arrow.setOnClickListener {
            Hawk.delete("character")
            onBackPressed()
        }

        fav_button.setOnClickListener {
            character.favorite = !character.favorite
            if (character.favorite) {
                fav_button.setImageResource(R.drawable.ic_star_border_gold_24dp)
            } else {
                fav_button.setImageResource(R.drawable.ic_star_gold_24dp)
                //TODO alterar favorito no BD e API...
            }
        }

    }

    inner class PlanetListener : Sw.PlanetListener {
        override fun onFailure(t: Throwable) {
            tv_planet.text = "Falha ao carregar nome do Planeta"
        }

        override fun onResponse(planet: Planet) {
            val planet_name = "${getString(R.string.planet)} ${planet.name}"
            tv_planet.text = planet_name
        }
    }

    inner class SpeciesListener : Sw.SpeciesListener {
        override fun onFailure(t: Throwable) {
            tv_species.text = "Falha ao carregar nome da Espécie"
        }

        override fun onResponse(species: Species) {
            val species_name = "${getString(R.string.species)} ${species.name}"
            tv_species.text = species_name
        }
    }
}

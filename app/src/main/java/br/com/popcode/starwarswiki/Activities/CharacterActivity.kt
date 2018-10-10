package br.com.popcode.starwarswiki.Activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import br.com.popcode.starwarswiki.Models.Character
import br.com.popcode.starwarswiki.R
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.activity_character.*

class CharacterActivity : AppCompatActivity() {

    var character: Character = Hawk.get("character")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_character)

        tv_name.text = character.name
        tv_height.text = character.height
        tv_mass.text = character.mass
        tv_hair_color.text = character.hairColor
        tv_skin_color.text = character.skinColor
        tv_eye_color.text = character.eyeColor
        tv_birth_year.text = character.birthYear
        tv_gender.text = character.gender
        //TODO buscar planeta
        //tv_planet.text = character.homeworld
        //TODO buscar espécie
        //tv_species.text = character.species

    }
}

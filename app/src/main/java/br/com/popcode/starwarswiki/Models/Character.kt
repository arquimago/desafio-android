package br.com.popcode.starwarswiki.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverter
import android.arch.persistence.room.TypeConverters
import br.com.popcode.starwarswiki.helpers.Converters
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

@Entity
class Character(@PrimaryKey (autoGenerate = true)
                var id: Int) {

    @SerializedName("name")
    @Expose
    var name: String? = null
    @SerializedName("height")
    @Expose
    var height: String? = null
    @SerializedName("mass")
    @Expose
    var mass: String? = null
    @SerializedName("hair_color")
    @Expose
    var hairColor: String? = null
    @SerializedName("skin_color")
    @Expose
    var skinColor: String? = null
    @SerializedName("eye_color")
    @Expose
    var eyeColor: String? = null
    @SerializedName("birth_year")
    @Expose
    var birthYear: String? = null
    @SerializedName("gender")
    @Expose
    var gender: String? = null
    @SerializedName("homeworld")
    @Expose
    var homeworld: String? = null
    @SerializedName("films")
    @Expose
    var films: ArrayList<String>? = null
    @SerializedName("species")
    @Expose
    var species: ArrayList<String>? = null
    @SerializedName("vehicles")
    @Expose
    var vehicles: ArrayList<String>? = null
    @SerializedName("starships")
    @Expose
    var starships: ArrayList<String>? = null
    @SerializedName("created")
    @Expose
    var created: String? = null
    @SerializedName("edited")
    @Expose
    var edited: String? = null
    @SerializedName("url")
    @Expose
    var url: String? = null

    var favorite: Boolean = false

}
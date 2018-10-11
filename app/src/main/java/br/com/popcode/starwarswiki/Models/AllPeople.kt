package br.com.popcode.starwarswiki.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName


class AllPeople {

    @SerializedName("count")
    @Expose
    var count: Int = 0
    @SerializedName("next")
    @Expose
    var next: String = ""
    @SerializedName("previous")
    @Expose
    var previous =  Object()
    @SerializedName("results")
    @Expose
    var results: MutableList<Character> = arrayListOf()

}
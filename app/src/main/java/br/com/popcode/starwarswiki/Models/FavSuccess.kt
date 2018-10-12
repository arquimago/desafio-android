package br.com.popcode.starwarswiki.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FavSuccess {

    @SerializedName("status")
    @Expose
    var status: String? = null
    @SerializedName("message")
    @Expose
    var message: String? = null

}
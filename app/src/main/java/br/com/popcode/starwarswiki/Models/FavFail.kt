package br.com.popcode.starwarswiki.models

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class FavFail {

    @SerializedName("error")
    @Expose
    var error: String? = null
    @SerializedName("error_message")
    @Expose
    var errorMessage: String? = null

}
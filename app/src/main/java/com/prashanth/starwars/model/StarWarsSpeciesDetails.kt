package com.prashanth.starwars.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StarWarsSpeciesDetails(
    @SerializedName("name") val name: String,
    @SerializedName("language") val language: String

) : Serializable {
    override fun toString(): String {
        return "StarWarsSpeciesDetails(name='$name', description='$language')"
    }
}
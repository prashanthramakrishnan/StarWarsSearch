package com.prashanth.starwars.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StarWarsCharacterDetails(
    @SerializedName("name") val name: String,
    @SerializedName("birth_year") val birthYear: String,
    @SerializedName("height") val height: String,
    @SerializedName("species") val speciesUrl: List<String>,
    @SerializedName("homeworld") val homeWorldUrl: String,
    @SerializedName("films") val filmsUrl: List<String>
) : Serializable {
    override fun toString(): String {
        return "StarWarsCharacterDetails(name='$name', description='$birthYear', height='$height', speciesUrl=$speciesUrl," +
                " homeworldurl='$homeWorldUrl', filmsUrl=$filmsUrl)"
    }
}

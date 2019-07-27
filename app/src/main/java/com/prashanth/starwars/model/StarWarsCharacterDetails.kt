package com.prashanth.starwars.model

import com.google.gson.annotations.SerializedName
import lombok.Getter
import lombok.Setter
import java.io.Serializable

@Getter
@Setter
data class StarWarsCharacterDetails(
    @SerializedName("name") val name: String,
    @SerializedName("birth_year") val birthYear: String,
    @SerializedName("height") val height: String,
    @SerializedName("species") val speciesUrl: List<String>, //species name, species language
    @SerializedName("homeworld") val homeWorldUrl: String, //population count
    @SerializedName("films") val filmsUrl: List<String>
): Serializable {
    override fun toString(): String {
        return "StarWarsCharacterDetails(name='$name', description='$birthYear', height='$height', speciesUrl=$speciesUrl," +
                " homeworldurl='$homeWorldUrl', filmsUrl=$filmsUrl)"
    }
}

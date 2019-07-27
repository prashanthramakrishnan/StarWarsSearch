package com.prashanth.starwars.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class StarWarsHomeWorldDetails(
    @SerializedName("population") val population: String,
    @SerializedName("name") val name: String
) : Serializable {
    override fun toString(): String {
        return "StarWarsHomeWorldDetails(population='$population', name='$name')"
    }
}
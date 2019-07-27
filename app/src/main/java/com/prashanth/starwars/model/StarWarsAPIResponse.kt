package com.prashanth.starwars.model

import com.google.gson.annotations.SerializedName
import lombok.Getter
import lombok.Setter
import java.io.Serializable

@Getter
@Setter
class StarWarsAPIResponse(
    @SerializedName("count") val count: Int,
    @SerializedName("next") val nextUrl: String,
    @SerializedName("previous") val previousUrl: String,
    @SerializedName("results") val results: List<StarWarsCharacterDetails>
) : Serializable {
    override fun toString(): String {
        return "StarWarsAPIResponse(count=$count, nextUrl='$nextUrl', previousUrl='$previousUrl', results=$results)"
    }
}
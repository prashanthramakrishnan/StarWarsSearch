package com.prashanth.starwars.model

import com.google.gson.annotations.SerializedName
import lombok.Getter
import lombok.Setter
import java.io.Serializable

@Getter
@Setter
data class StarWarsFilmsDetails(
    @SerializedName("title") val title: String,
    @SerializedName("opening_crawl") val openingCrawl: String,
    @SerializedName("release_date") val releaseDate: String

) : Serializable {
    override fun toString(): String {
        return "StarWarsFilmsDetails(title='$title', openingCrawl='$openingCrawl', releaseDate='$releaseDate')"
    }
}
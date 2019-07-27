package com.prashanth.starwars.network

import com.prashanth.starwars.model.StarWarsAPIResponse
import com.prashanth.starwars.model.StarWarsFilmsDetails
import com.prashanth.starwars.model.StarWarsHomeWorldDetails
import com.prashanth.starwars.model.StarWarsSpeciesDetails
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url


interface StarWarsAPI {

    @GET
    fun getSpecies(@Url url: String): Observable<StarWarsSpeciesDetails>

    @GET
    fun getHomeWorld(@Url url: String): Observable<StarWarsHomeWorldDetails>

    @GET
    fun getFilmDetails(@Url url: String): Observable<StarWarsFilmsDetails>

    @GET("/api/people/")
    fun searchPeople(@Query("search") page: String): Observable<StarWarsAPIResponse>
}
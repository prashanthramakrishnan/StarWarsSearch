package com.prashanth.starwars.contracts

import com.prashanth.starwars.model.StarWarsCharacterDetails
import com.prashanth.starwars.model.StarWarsFilmsDetails
import com.prashanth.starwars.model.StarWarsHomeWorldDetails
import com.prashanth.starwars.model.StarWarsSpeciesDetails

interface APIContract {

    interface View {

        fun callStarted()

        fun callComplete()

        fun callFailed(throwable: Throwable)
    }

    interface CharacterSearchView : View {

        fun onResponseCharacterSearch(starWarsCharacterDetails: List<StarWarsCharacterDetails>)
    }

    interface SpeciesView : View {

        fun onResponseSpecies(starWarsSpeciesDetails: StarWarsSpeciesDetails)
    }

    interface HomeWorldView : View {

        fun onReponseHomeWorldDetails(starWarsHomeWorldDetails: StarWarsHomeWorldDetails)
    }

    interface FilmDetailsView : View {

        fun onReponseFilmDetails(starWarsFilmsDetails: List<StarWarsFilmsDetails>)
    }

    interface Presenter {

        fun subscribe()

        fun unsubscribe()

        fun onDestroy()
    }

    interface StarWarsCharactersSearchPresenter : Presenter {

        fun fetchStarWarsCharacters(query: String, view: CharacterSearchView)
    }

    interface StarWarsSpeciesPresenter : Presenter {

        fun fetchStarWarsSpeciesDetails(url: String, view: SpeciesView)
    }

    interface StarWarsHomeWorldPresenter : Presenter {

        fun fetchStarWarsHomeWorldDetails(url: String, view: HomeWorldView)
    }

    interface StarWarsFilmDetailsPresenter : Presenter {

        fun fetchStarWarsFilmsDetails(urls: List<String>, view: FilmDetailsView)
    }

}
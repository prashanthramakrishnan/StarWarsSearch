package com.prashanth.starwars.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashanth.starwars.R
import com.prashanth.starwars.StarWarsApplication
import com.prashanth.starwars.adapter.FilmsDetailsAdapter
import com.prashanth.starwars.contracts.APIContract
import com.prashanth.starwars.model.StarWarsCharacterDetails
import com.prashanth.starwars.model.StarWarsFilmsDetails
import com.prashanth.starwars.model.StarWarsHomeWorldDetails
import com.prashanth.starwars.model.StarWarsSpeciesDetails
import com.prashanth.starwars.presenter.StarWarsFilmDetailsPresenter
import com.prashanth.starwars.presenter.StarWarsHomeWorldPresenter
import com.prashanth.starwars.presenter.StarWarsSpeciesPresenter
import kotlinx.android.synthetic.main.activity_characters_details.*
import javax.inject.Inject

class CharacterDetailsActivity : AppCompatActivity(), APIContract.SpeciesView, APIContract.HomeWorldView,
    APIContract.FilmDetailsView {

    private var response: StarWarsCharacterDetails? = null

    @Inject
    lateinit var starWarsSpeciesPresenter: StarWarsSpeciesPresenter

    @Inject
    lateinit var starWarsHomeWorldPresenter: StarWarsHomeWorldPresenter

    @Inject
    lateinit var starWarsFilmDetailsPresenter: StarWarsFilmDetailsPresenter

    @Inject
    lateinit var adapter: FilmsDetailsAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    companion object {
        fun startCharacterDetailsActivity(response: StarWarsCharacterDetails, context: Context) {
            val intent = Intent(context, CharacterDetailsActivity::class.java)
            intent.putExtra("RESPONSE", response)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_details)
        StarWarsApplication.component.inject(this)

        films_recycler_view.layoutManager = linearLayoutManager
        films_recycler_view.adapter = adapter

        response = intent?.extras?.get("RESPONSE") as StarWarsCharacterDetails

        character_name_id.text = response!!.name
        birth_year_id.text = response!!.birthYear
        height_id.text = response!!.height

        starWarsSpeciesPresenter.fetchStarWarsSpeciesDetails(response!!.speciesUrl[0], this)

    }

    override fun callStarted() {
    }

    override fun callComplete() {
    }

    override fun callFailed(throwable: Throwable) {
    }

    override fun onResponseSpecies(starWarsSpeciesDetails: StarWarsSpeciesDetails) {
        species_name_id.text = starWarsSpeciesDetails.name
        language_id.text = starWarsSpeciesDetails.language

        starWarsHomeWorldPresenter.fetchStarWarsHomeWorldDetails(response!!.homeWorldUrl, this)
    }

    override fun onReponseHomeWorldDetails(starWarsHomeWorldDetails: StarWarsHomeWorldDetails) {
        homeworld_id.text = starWarsHomeWorldDetails.name
        population_id.text = starWarsHomeWorldDetails.population

        starWarsFilmDetailsPresenter.fetchStarWarsFilmsDetails(response!!.filmsUrl, this)
    }

    override fun onReponseFilmDetails(starWarsFilmsDetails: List<StarWarsFilmsDetails>) {
        adapter.update(starWarsFilmsDetails)
    }
}
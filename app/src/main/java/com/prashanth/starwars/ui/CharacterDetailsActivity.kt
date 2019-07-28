package com.prashanth.starwars.ui

import android.app.ProgressDialog
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
import timber.log.Timber
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

    private var progressDialog: ProgressDialog? = null

    companion object {
        fun startCharacterDetailsActivity(response: StarWarsCharacterDetails, context: Context) {
            val intent = Intent(context, CharacterDetailsActivity::class.java)
            intent.putExtra(RESPONSE, response)
            context.startActivity(intent)
        }

        const val RESPONSE = "RESPONSE"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_characters_details)
        StarWarsApplication.component.inject(this)
        response = intent?.extras?.get(RESPONSE) as StarWarsCharacterDetails
        progressDialog = ProgressDialog(this)
        films_recycler_view.layoutManager = linearLayoutManager
        films_recycler_view.adapter = adapter
        setNameYearAndHeight()
        starWarsSpeciesPresenter.fetchStarWarsSpeciesDetails(response!!.speciesUrl[0], this)
        showProgressDialog()

    }

    private fun setNameYearAndHeight() {
        character_name_id.text = response!!.name
        birth_year_id.text = response!!.birthYear
        height_id.text = response!!.height
    }

    private fun setSpeciesDetails(starWarsSpeciesDetails: StarWarsSpeciesDetails) {
        species_name_id.text = starWarsSpeciesDetails.name
        language_id.text = starWarsSpeciesDetails.language
    }

    private fun setHomeWorldDetails(starWarsHomeWorldDetails: StarWarsHomeWorldDetails) {
        homeworld_id.text = starWarsHomeWorldDetails.name
        population_id.text = starWarsHomeWorldDetails.population
    }

    override fun callStarted() {
        Timber.d("callStarted")
    }

    override fun callComplete() {
        Timber.d("callComplete")
    }

    override fun callFailed(throwable: Throwable) {
        dismissProgressDialog()
    }

    override fun onResponseSpecies(starWarsSpeciesDetails: StarWarsSpeciesDetails) {
        setSpeciesDetails(starWarsSpeciesDetails)
        starWarsHomeWorldPresenter.fetchStarWarsHomeWorldDetails(response!!.homeWorldUrl, this)
    }

    override fun onResponseHomeWorldDetails(starWarsHomeWorldDetails: StarWarsHomeWorldDetails) {
        setHomeWorldDetails(starWarsHomeWorldDetails)
        starWarsFilmDetailsPresenter.fetchStarWarsFilmsDetails(response!!.filmsUrl, this)
    }

    override fun onResponseFilmDetails(starWarsFilmsDetails: List<StarWarsFilmsDetails>) {
        dismissProgressDialog()
        adapter.update(starWarsFilmsDetails)
    }

    private fun showProgressDialog() {
        if (progressDialog != null && !progressDialog!!.isShowing) {
            progressDialog!!.setMessage(getString(R.string.loading))
            progressDialog!!.show()
            progressDialog!!.setCancelable(false)
        }
    }

    private fun dismissProgressDialog() {
        progressDialog?.dismiss()
    }

    override fun onDestroy() {
        super.onDestroy()
        starWarsHomeWorldPresenter.onDestroy()
        starWarsSpeciesPresenter.onDestroy()
        starWarsFilmDetailsPresenter.onDestroy()
    }
}
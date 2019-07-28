package com.prashanth.starwars.presenter

import androidx.annotation.VisibleForTesting
import com.prashanth.starwars.StarWarsApplication
import com.prashanth.starwars.contracts.APIContract
import com.prashanth.starwars.model.StarWarsFilmsDetails
import com.prashanth.starwars.network.StarWarsAPI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarWarsFilmDetailsPresenter : APIContract.StarWarsFilmDetailsPresenter {

    @Inject
    lateinit var starWarsAPI: StarWarsAPI

    private lateinit var urls: List<String>

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var view: APIContract.FilmDetailsView

    constructor() {
        StarWarsApplication.component.inject(this)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    constructor(starWarsAPI: StarWarsAPI, view: APIContract.FilmDetailsView) {
        this.starWarsAPI = starWarsAPI
        this.view = view
    }


    override fun subscribe() {
        fetchStarWarsFilmsDetails(urls, view)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    override fun fetchStarWarsFilmsDetails(urls: List<String>, view: APIContract.FilmDetailsView) {
        this.urls = urls
        this.view = view
        val requests = ArrayList<Observable<*>>()
        for (url in urls) {
            requests.add(starWarsAPI.getFilmDetails(url))
        }
        val disposable = Observable
            .zip(requests) { objects ->
                val filmDetailsResponse = java.util.ArrayList<StarWarsFilmsDetails>()
                for (o in objects) {
                    filmDetailsResponse.add(o as StarWarsFilmsDetails)
                }
                return@zip filmDetailsResponse
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { filmDetailsResponses ->
                    view.onResponseFilmDetails(filmDetailsResponses)
                },
                { e -> view.callFailed(e) }
            )
        compositeDisposable.add(disposable)
    }
}
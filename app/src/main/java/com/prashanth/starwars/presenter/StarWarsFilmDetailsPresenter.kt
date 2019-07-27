package com.prashanth.starwars.presenter

import com.prashanth.starwars.StarWarsApplication
import com.prashanth.starwars.contracts.APIContract
import com.prashanth.starwars.model.StarWarsFilmsDetails
import com.prashanth.starwars.network.StarWarsAPI
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarWarsFilmDetailsPresenter : APIContract.StarWarsFilmDetailsPresenter {

    @Inject
    lateinit var starWarsAPI: StarWarsAPI

    private lateinit var urls: List<String>

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var view: APIContract.FilmDetailsView

    init {
        StarWarsApplication.component.inject(this)
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
                filmDetailsResponse
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .subscribe(
                { filmDetailsResponses ->
                    Timber.d("size %s", filmDetailsResponses.size)
                    view.onReponseFilmDetails(filmDetailsResponses)
                },
                { e -> Timber.e(e) }
            )
        compositeDisposable.add(disposable)
    }
}
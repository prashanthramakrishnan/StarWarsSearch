package com.prashanth.starwars.presenter

import androidx.annotation.VisibleForTesting
import com.prashanth.starwars.StarWarsApplication
import com.prashanth.starwars.contracts.APIContract
import com.prashanth.starwars.model.StarWarsAPIResponse
import com.prashanth.starwars.network.StarWarsAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarWarsCharacterSearchPresenter : APIContract.StarWarsCharactersSearchPresenter {

    @Inject
    lateinit var starWarsAPI: StarWarsAPI

    private lateinit var searchQuery: String

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var view: APIContract.CharacterSearchView

    constructor() {
        StarWarsApplication.component.inject(this)
    }

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    constructor(starWarsAPI: StarWarsAPI, view: APIContract.CharacterSearchView) {
        this.starWarsAPI = starWarsAPI
        this.view = view
    }


    override fun subscribe() {
        fetchStarWarsCharacters(searchQuery, view)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    override fun fetchStarWarsCharacters(query: String, view: APIContract.CharacterSearchView) {
        this.searchQuery = query
        this.view = view
        val disposable = starWarsAPI.searchPeople(query)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<StarWarsAPIResponse>() {
                override fun onNext(response: StarWarsAPIResponse) {
                    view.onResponseCharacterSearch(response.results)
                }

                override fun onError(e: Throwable) {
                    view.callFailed(e)
                }

                override fun onComplete() {
                    view.callComplete()
                }
            })
        compositeDisposable.add(disposable)
    }
}
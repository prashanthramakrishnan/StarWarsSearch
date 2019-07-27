package com.prashanth.starwars.presenter

import com.prashanth.starwars.StarWarsApplication
import com.prashanth.starwars.contracts.APIContract
import com.prashanth.starwars.model.StarWarsHomeWorldDetails
import com.prashanth.starwars.network.StarWarsAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StarWarsHomeWorldPresenter : APIContract.StarWarsHomeWorldPresenter {

    @Inject
    lateinit var starWarsAPI: StarWarsAPI

    private lateinit var url: String

    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    private lateinit var view: APIContract.HomeWorldView

    init {
        StarWarsApplication.component.inject(this)
    }


    override fun subscribe() {
        fetchStarWarsHomeWorldDetails(url, view)
    }

    override fun unsubscribe() {
        compositeDisposable.clear()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
    }

    override fun fetchStarWarsHomeWorldDetails(url: String, view: APIContract.HomeWorldView) {
        this.url = url
        this.view = view
        val disposable = starWarsAPI.getHomeWorld(url)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableObserver<StarWarsHomeWorldDetails>() {
                override fun onNext(response: StarWarsHomeWorldDetails) {
                    view.onReponseHomeWorldDetails(response)
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
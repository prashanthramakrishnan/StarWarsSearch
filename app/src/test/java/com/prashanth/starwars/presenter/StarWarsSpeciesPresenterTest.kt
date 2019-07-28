package com.prashanth.starwars.presenter

import com.prashanth.starwars.contracts.APIContract
import com.prashanth.starwars.model.StarWarsAPIResponse
import com.prashanth.starwars.model.StarWarsFilmsDetails
import com.prashanth.starwars.model.StarWarsHomeWorldDetails
import com.prashanth.starwars.model.StarWarsSpeciesDetails
import com.prashanth.starwars.network.StarWarsAPI
import io.reactivex.Observable
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.BeforeClass
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class StarWarsSpeciesPresenterTest {

    @Mock
    private lateinit var view: APIContract.SpeciesView

    companion object {
        @Before
        @Throws(Exception::class)
        fun setUp() {
            MockitoAnnotations.initMocks(this)
        }

        @BeforeClass
        @JvmStatic
        fun setupClass() {
            RxAndroidPlugins.setInitMainThreadSchedulerHandler { schedulerCallable -> Schedulers.trampoline() }
            RxJavaPlugins.setIoSchedulerHandler { scheduler -> Schedulers.trampoline() }
        }
    }

    private fun <T> any(): T {
        Mockito.any<T>()
        return uninitialized()
    }

    private fun <T> uninitialized(): T = null as T

    @Test
    fun getDataAndLoadViewTest() {
        val presenter = StarWarsSpeciesPresenter(provideStarWarsAPI(true), view)
        presenter.fetchStarWarsSpeciesDetails("https://localhost:8080", view)
        Mockito.verify(view, times(1)).callComplete()
        Mockito.verify(view, times(1)).onResponseSpecies(response())
    }

    @Test
    fun getDataAndLoadViewFailTest() {
        val presenter = StarWarsSpeciesPresenter(provideStarWarsAPI(false), view)
        presenter.fetchStarWarsSpeciesDetails("https://localhost:8080", view)
        Mockito.verify(view, times(1)).callFailed(any())

    }

    private fun provideStarWarsAPI(success: Boolean): StarWarsAPI {
        if (success) {
            return MockStarWarsAPISuccess()
        }
        return MockStarWarsAPIFailure()
    }

    private inner class MockStarWarsAPISuccess : StarWarsAPI {
        override fun getHomeWorld(url: String): Observable<StarWarsHomeWorldDetails> {
            return Observable.just(null)

        }

        override fun getFilmDetails(url: String): Observable<StarWarsFilmsDetails> {
            return Observable.just(null)
        }

        override fun searchPeople(page: String): Observable<StarWarsAPIResponse> {
            return Observable.just(null)
        }

        override fun getSpecies(url: String): Observable<StarWarsSpeciesDetails> {
            return Observable.just(response())
        }

    }

    private inner class MockStarWarsAPIFailure : StarWarsAPI {
        override fun getHomeWorld(url: String): Observable<StarWarsHomeWorldDetails> {
            return Observable.just(null)
        }

        override fun getFilmDetails(url: String): Observable<StarWarsFilmsDetails> {
            return Observable.just(null)
        }

        override fun searchPeople(page: String): Observable<StarWarsAPIResponse> {
            return Observable.just(null)
        }

        override fun getSpecies(url: String): Observable<StarWarsSpeciesDetails> {
            return Observable.error(Throwable("Error"))

        }

    }

    private fun response(): StarWarsSpeciesDetails {
        return StarWarsSpeciesDetails("HUMAN", "KANNADA")
    }
}
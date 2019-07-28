package com.prashanth.starwars.presenter

import com.prashanth.starwars.contracts.APIContract
import com.prashanth.starwars.model.*
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
class StarWarsCharacterSearchPresenterTest {

    @Mock
    private lateinit var view: APIContract.CharacterSearchView

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
        val presenter = StarWarsCharacterSearchPresenter(provideStarWarsAPI(true), view)
        presenter.fetchStarWarsCharacters("LUKE", view)
        Mockito.verify(view, times(1)).callComplete()
        Mockito.verify(view, times(1)).onResponseCharacterSearch(response().results)
    }

    @Test
    fun getDataAndLoadViewFailTest() {
        val presenter = StarWarsCharacterSearchPresenter(provideStarWarsAPI(false), view)
        presenter.fetchStarWarsCharacters("LUKE", view)
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

            return Observable.just(response())
        }

        override fun getSpecies(url: String): Observable<StarWarsSpeciesDetails> {
            return Observable.just(null)
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
            return Observable.error(Throwable("Error"))
        }

        override fun getSpecies(url: String): Observable<StarWarsSpeciesDetails> {
            return Observable.just(null)
        }

    }

    private fun response(): StarWarsAPIResponse {
        val listDetails: List<StarWarsCharacterDetails> = starWarsCharacterDetailsList()
        return StarWarsAPIResponse(1, "null", "null", listDetails)
    }

    private fun starWarsCharacterDetailsList(): List<StarWarsCharacterDetails> {
        val list: List<String>
        list = ArrayList()
        list.add("https://localhost:8080")
        val details = StarWarsCharacterDetails("Luke", "1212", "122", list, "https://localhost:8080", list)
        val listDetails: List<StarWarsCharacterDetails>
        listDetails = ArrayList()
        listDetails.add(details)
        return listDetails
    }
}
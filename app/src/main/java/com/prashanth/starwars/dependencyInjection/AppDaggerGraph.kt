package com.prashanth.starwars.dependencyInjection


import com.prashanth.starwars.presenter.StarWarsCharacterSearchPresenter
import com.prashanth.starwars.presenter.StarWarsFilmDetailsPresenter
import com.prashanth.starwars.presenter.StarWarsHomeWorldPresenter
import com.prashanth.starwars.presenter.StarWarsSpeciesPresenter
import com.prashanth.starwars.ui.CharacterDetailsActivity
import com.prashanth.starwars.ui.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkDaggerModule::class, ApplicationModule::class, PresenterModule::class])
interface AppDaggerGraph {
    fun inject(target: MainActivity)
    fun inject(target: CharacterDetailsActivity)
    fun inject(starWarsCharacterSearchPresenter: StarWarsCharacterSearchPresenter)
    fun inject(starWarsSpeciesPresenter: StarWarsSpeciesPresenter)
    fun inject(starWarsHomeWorldPresenter: StarWarsHomeWorldPresenter)
    fun inject(starWarsFilmDetailsPresenter: StarWarsFilmDetailsPresenter)
}
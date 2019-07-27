package com.prashanth.starwars.dependencyInjection

import android.content.Context
import androidx.recyclerview.widget.LinearLayoutManager
import com.prashanth.starwars.adapter.CharacterSearchAdapter
import com.prashanth.starwars.adapter.FilmsDetailsAdapter
import com.prashanth.starwars.presenter.StarWarsCharacterSearchPresenter
import com.prashanth.starwars.presenter.StarWarsFilmDetailsPresenter
import com.prashanth.starwars.presenter.StarWarsHomeWorldPresenter
import com.prashanth.starwars.presenter.StarWarsSpeciesPresenter
import dagger.Module
import dagger.Provides

@Module
class PresenterModule(private val context: Context) {

    @Provides
    fun provideStarWarsCharacterSearchPresenter(): StarWarsCharacterSearchPresenter {
        return StarWarsCharacterSearchPresenter()
    }

    @Provides
    fun provideStarWarsSpeciesPresenter(): StarWarsSpeciesPresenter {
        return StarWarsSpeciesPresenter()
    }

    @Provides
    fun provideStarWarsHomeWorldPresenter(): StarWarsHomeWorldPresenter {
        return StarWarsHomeWorldPresenter()
    }

    @Provides
    fun provideStarWarsFilmDetailsPresenter(): StarWarsFilmDetailsPresenter {
        return StarWarsFilmDetailsPresenter()
    }

    @Provides
    fun provideCharacterSearchAdapter(): CharacterSearchAdapter {
        return CharacterSearchAdapter(context)
    }

    @Provides
    fun provideFilmsDetailsAdapter(): FilmsDetailsAdapter {
        return FilmsDetailsAdapter()
    }

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager {
        return LinearLayoutManager(context)
    }

}
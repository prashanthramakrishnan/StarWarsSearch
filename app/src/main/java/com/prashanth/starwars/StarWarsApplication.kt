package com.prashanth.starwars

import android.app.Application
import com.prashanth.starwars.dependencyInjection.*
import timber.log.Timber

class StarWarsApplication : Application() {

    companion object {
        lateinit var component: AppDaggerGraph
    }

    protected fun daggerComponent(application: StarWarsApplication): DaggerAppDaggerGraph.Builder {
        return DaggerAppDaggerGraph.builder()
            .networkDaggerModule(NetworkDaggerModule(BuildConfig.STAR_WARS_URL))
            .applicationModule(ApplicationModule(this))
            .presenterModule(PresenterModule(this))
    }

    override fun onCreate() {
        super.onCreate()
        component = daggerComponent(this).build()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
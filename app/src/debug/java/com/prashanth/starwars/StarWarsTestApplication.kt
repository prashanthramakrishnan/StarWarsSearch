package com.prashanth.starwars

import com.prashanth.starwars.dependencyInjection.DaggerAppDaggerGraph
import com.prashanth.starwars.dependencyInjection.NetworkDaggerModule

class StarWarsTestApplication : StarWarsApplication() {

    override fun daggerComponent(application: StarWarsApplication): DaggerAppDaggerGraph.Builder {
        return super.daggerComponent(this)
            .networkDaggerModule(NetworkDaggerModule("http://localhost:8080/"))
    }
}
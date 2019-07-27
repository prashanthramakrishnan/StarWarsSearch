package com.prashanth.starwars

import android.content.Intent
import android.widget.EditText
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry.getInstrumentation
import androidx.test.rule.ActivityTestRule
import com.prashanth.starwars.ui.CharacterDetailsActivity
import com.prashanth.starwars.ui.MainActivity
import com.robotium.solo.Solo
import okhttp3.mockwebserver.Dispatcher
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okhttp3.mockwebserver.RecordedRequest
import org.apache.commons.io.IOUtils
import org.junit.After
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import timber.log.Timber
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ApplicationFlowTest {
    private var server: MockWebServer? = null

    private var solo: Solo? = null

    @Rule
    @JvmField
    var rule = ActivityTestRule(MainActivity::class.java, false, false)

    @Before
    @Throws(Exception::class)
    fun setUp() {
        setupServer()
        solo = Solo(getInstrumentation(), rule.activity)
    }

    @After
    @Throws(Exception::class)
    fun tearDown() {
        solo!!.finishOpenedActivities()
        server!!.shutdown()
    }

    @Test
    fun startLoginFlowTest() {

        introduceDelay(2000L)

        //Launch app with the MockServer running
        rule.launchActivity(Intent())

        assertTrue(solo!!.waitForActivity(MainActivity::class.java.simpleName))

        introduceDelay(1000L)

        val editTextUsername = solo!!.getView(R.id.search_view) as EditText

        //search for doctors
        solo!!.enterText(editTextUsername, "Luke")

        introduceDelay(2000L)

        solo!!.clickOnText("Skywalker")

        introduceDelay(2000L)

        assertTrue(solo!!.waitForActivity(CharacterDetailsActivity::class.java.simpleName))

        introduceDelay(2000L)

    }

    @Throws(Exception::class)
    private fun setupServer() {

        server = MockWebServer()
        val dispatcher = object : Dispatcher() {
            @Throws(InterruptedException::class)
            override fun dispatch(request: RecordedRequest): MockResponse {
                if (request.method == "GET" && "/api/people/?search=Luke" == request.path!!) {

                    val response: String
                    try {
                        response =
                            IOUtils.toString(getInstrumentation().context.resources.assets.open("json/search_response.json"))
                        return MockResponse().setResponseCode(200)
                            .setBody(response)
                    } catch (e: IOException) {
                        Timber.e(e)
                    }

                }

                if (("http://localhost:8080/api/planets/1/").equals(request.requestUrl.toString())) {
                    val response: String
                    try {
                        response =
                            IOUtils.toString(getInstrumentation().context.resources.assets.open("json/home_world_response.json"))
                        return MockResponse().setResponseCode(200)
                            .setBody(response)
                    } catch (e: IOException) {
                        Timber.e(e)
                    }
                }

                if (("http://localhost:8080/api/species/1/").equals(request.requestUrl.toString())) {
                    val response: String
                    try {
                        response =
                            IOUtils.toString(getInstrumentation().context.resources.assets.open("json/species_response.json"))
                        return MockResponse().setResponseCode(200)
                            .setBody(response)
                    } catch (e: IOException) {
                        Timber.e(e)
                    }

                }


                if (("http://localhost:8080/api/films/2/").equals(request.requestUrl.toString())) {
                    val response: String
                    try {
                        response =
                            IOUtils.toString(getInstrumentation().context.resources.assets.open("json/films_response_2.json"))
                        return MockResponse().setResponseCode(200).setBody(response)
                    } catch (e: IOException) {
                        Timber.e(e)
                    }

                }

                if (("http://localhost:8080/api/films/1/").equals(request.requestUrl.toString())) {
                    val response: String
                    try {
                        response =
                            IOUtils.toString(getInstrumentation().context.resources.assets.open("json/films_response_1.json"))
                        return MockResponse().setResponseCode(200)
                            .setBody(response)
                    } catch (e: IOException) {
                        Timber.e(e)
                    }

                }

                if (("http://localhost:8080/api/films/3/").equals(request.requestUrl.toString())) {
                    val response: String
                    try {
                        response =
                            IOUtils.toString(getInstrumentation().context.resources.assets.open("json/films_response_3.json"))
                        return MockResponse().setResponseCode(200)
                            .setBody(response)
                    } catch (e: IOException) {
                        Timber.e(e)
                    }

                }

                if (("http://localhost:8080/api/films/6/").equals(request.requestUrl.toString())) {
                    val response: String
                    try {
                        response =
                            IOUtils.toString(getInstrumentation().context.resources.assets.open("json/films_response_6.json"))
                        return MockResponse().setResponseCode(200)
                            .setBody(response)
                    } catch (e: IOException) {
                        Timber.e(e)
                    }

                }

                if (("http://localhost:8080/api/films/7/").equals(request.requestUrl.toString())) {
                    val response: String
                    try {
                        response =
                            IOUtils.toString(getInstrumentation().context.resources.assets.open("json/films_response_7.json"))
                        return MockResponse().setResponseCode(200)
                            .setBody(response)
                    } catch (e: IOException) {
                        Timber.e(e)
                    }

                }

                return MockResponse().setResponseCode(200).setBody("")
            }
        }
        server!!.dispatcher = dispatcher
        server!!.start(8080)
    }

    private fun introduceDelay(timeout: Long) {
        Thread.sleep(timeout)
    }
}
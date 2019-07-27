package com.prashanth.starwars.ui

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import butterknife.BindView
import butterknife.ButterKnife
import com.jakewharton.rxbinding3.widget.textChanges
import com.prashanth.starwars.BuildConfig
import com.prashanth.starwars.R
import com.prashanth.starwars.StarWarsApplication
import com.prashanth.starwars.adapter.CharacterSearchAdapter
import com.prashanth.starwars.contracts.APIContract
import com.prashanth.starwars.model.StarWarsCharacterDetails
import com.prashanth.starwars.network.StarWarsAPI
import com.prashanth.starwars.presenter.StarWarsCharacterSearchPresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MainActivity : AppCompatActivity(), APIContract.CharacterSearchView {

    private var disposable: Disposable? = null

    @BindView(R.id.search_view)
    lateinit var searchEditText: EditText

    @Inject
    lateinit var starWarsAPI: StarWarsAPI

    @Inject
    lateinit var adapter: CharacterSearchAdapter

    @Inject
    lateinit var linearLayoutManager: LinearLayoutManager

    @Inject
    lateinit var starWarsCharacterSearchPresenter: StarWarsCharacterSearchPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        ButterKnife.bind(this)
        StarWarsApplication.component.inject(this)
        recycler_view.layoutManager = linearLayoutManager
        recycler_view.adapter = adapter

    }

    override fun callStarted() {
        Timber.d("callStarted")
    }

    override fun callComplete() {
        Timber.d("callComplete")
    }

    override fun callFailed(throwable: Throwable) {
        Timber.e(throwable, "callFailed")
        Toast.makeText(this@MainActivity, R.string.error, Toast.LENGTH_SHORT).show()
    }

    override fun onResponseCharacterSearch(starWarsCharacterDetails: List<StarWarsCharacterDetails>) {
        if (starWarsCharacterDetails.isEmpty()) {
            Toast.makeText(this@MainActivity, R.string.no_results_found, Toast.LENGTH_SHORT).show()
        } else {
            adapter.update(starWarsCharacterDetails)
        }
    }

    private fun attachEditTextBindings() {
        if (disposable != null && !disposable!!.isDisposed) {
            disposable!!.dispose()
        }
        disposable = searchEditText.textChanges()
            .debounce(700, TimeUnit.MILLISECONDS)
            .map { charSequence -> charSequence }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { queryString ->
                val length = queryString.count()
                if (length > 0 && length >= Integer.valueOf(BuildConfig.MIN_CHARACTER_SEARCH_COUNT)) {
                    starWarsCharacterSearchPresenter.fetchStarWarsCharacters(queryString.toString(), this)
                    hideKeyboard()
                }
            }
    }

    private fun hideKeyboard() {
        if (currentFocus != null) {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
        hideKeyboard()
    }

    override fun onResume() {
        super.onResume()
        attachEditTextBindings()
    }

}
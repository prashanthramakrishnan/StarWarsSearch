package com.prashanth.starwars.adapter


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.Nullable
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.prashanth.starwars.R
import com.prashanth.starwars.model.StarWarsFilmsDetails

class FilmsDetailsAdapter : RecyclerView.Adapter<FilmsDetailsAdapter.ViewHolder>() {


    private var filmDetailsList: List<StarWarsFilmsDetails>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_film_details, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = filmDetailsList!![position].title
        holder.releaseDate.text = filmDetailsList!![position].releaseDate
        holder.openingCrawl.text = filmDetailsList!![position].openingCrawl
    }

    override fun getItemCount(): Int {
        return if (filmDetailsList == null) 0 else filmDetailsList!!.size
    }


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.title_id)
        lateinit var title: TextView

        @Nullable
        @BindView(R.id.release_date_id)
        lateinit var releaseDate: TextView

        @BindView(R.id.opening_crawl_id)
        lateinit var openingCrawl: TextView

        init {
            ButterKnife.bind(this, view)
        }
    }

    fun update(filmDetailsList: List<StarWarsFilmsDetails>) {
        this.filmDetailsList = filmDetailsList
        notifyDataSetChanged()
    }

}
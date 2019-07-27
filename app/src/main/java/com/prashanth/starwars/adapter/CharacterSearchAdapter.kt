package com.prashanth.starwars.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import butterknife.BindView
import butterknife.ButterKnife
import com.prashanth.starwars.R
import com.prashanth.starwars.model.StarWarsCharacterDetails
import com.prashanth.starwars.ui.CharacterDetailsActivity

class CharacterSearchAdapter(context: Context) : RecyclerView.Adapter<CharacterSearchAdapter.ViewHolder>() {

    private var activity: Context = context

    private var characterNameList: List<StarWarsCharacterDetails>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_character_search, parent, false)
        return ViewHolder(view, activity)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.characterName.text = characterNameList!![position].name
        holder.birthYear.text = characterNameList!![position].birthYear

        holder.itemView.setOnClickListener {
            CharacterDetailsActivity.startCharacterDetailsActivity(characterNameList!![position], activity)
        }
    }

    override fun getItemCount(): Int {
        return if (characterNameList == null) 0 else characterNameList!!.size
    }


    inner class ViewHolder(view: View, context: Context) : RecyclerView.ViewHolder(view) {

        @BindView(R.id.character_name_id)
        lateinit var characterName: TextView

        @BindView(R.id.birth_year_id)
        lateinit var birthYear: TextView

        init {
            ButterKnife.bind(this, view)
        }
    }

    fun update(characterNameList: List<StarWarsCharacterDetails>) {
        this.characterNameList = characterNameList
        notifyDataSetChanged()
    }

}
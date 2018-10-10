package br.com.popcode.starwarswiki.Adapters

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.popcode.starwarswiki.Activities.CharacterActivity
import br.com.popcode.starwarswiki.Models.Character
import br.com.popcode.starwarswiki.R
import com.orhanobut.hawk.Hawk
import kotlinx.android.synthetic.main.item_character.view.*

class ListAdapter(private val list: MutableList<Character>) : RecyclerView.Adapter<ListAdapter.CharHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_character, parent, false)
        return CharHolder(view)
    }

    override fun onBindViewHolder(holder: CharHolder, position: Int) {
        val character = list[position]
        holder.name.text = character.name
        holder.height.text = character.height
        holder.gender.text = character.gender
        holder.mass.text = character.mass

        if (character.favorite) holder.fav.setImageResource(R.drawable.ic_star_gold_24dp)

        holder.view.setOnClickListener {
            Hawk.put("character", character)
            val characterActivity = Intent(it.context, CharacterActivity::class.java)
            it.context.startActivity(characterActivity)
        }

        holder.fav.setOnClickListener {
            if (character.favorite) holder.fav.setImageResource(R.drawable.ic_star_gold_24dp) else holder.fav.setImageResource(R.drawable.ic_star_border_gold_24dp)
            //TODO alterar favorito no BD e API...
        }
    }

    override fun getItemCount(): Int = list.size

    class CharHolder(val view: View) : RecyclerView.ViewHolder(view) {
        val name = view.item_name
        val height = view.item_height
        val gender = view.item_gender
        val mass = view.item_mass
        val fav = view.item_fav_star
    }
}
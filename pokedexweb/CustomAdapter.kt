package com.example.pokedexweb

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.squareup.picasso.Picasso

class CustomAdapter(private var mList: ArrayList<ItemsViewModel>, private val pokemonSelect: ImageView) : RecyclerView.Adapter<CustomAdapter.ViewHolder>() {
    @SuppressLint("NotifyDataSetChanged")
    fun setFilteredList(mList: ArrayList<ItemsViewModel>){
        this.mList = mList
        notifyDataSetChanged()
    }
    // create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val mainXML = LayoutInflater.from(parent.context)
            .inflate(R.layout.activity_main, parent, false)
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.card_view_design, parent, false)

        return ViewHolder(view)
    }
    // binds the list items to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val ItemsViewModel = mList[position]

        // sets the image to the imageview from our itemHolder class
        Picasso.get().load("https://img.pokemondb.net/sprites/x-y/normal/" + ItemsViewModel.text + ".png").into(holder.imageView)

        // sets the text to the textview from our itemHolder class
        holder.textView.text = ItemsViewModel.text
        holder.buttonView.setOnClickListener {
                Glide.with(pokemonSelect).load("https://projectpokemon.org/images/normal-sprite/" + ItemsViewModel.text + ".gif").into(pokemonSelect)
            }
        }

    // return the number of the items in the list
    override fun getItemCount(): Int {
        return mList.size
    }

    // Holds the views for adding it to image and text
    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val imageView: ImageView = itemView.findViewById(R.id.pokemonImg)
        val textView: TextView = itemView.findViewById(R.id.pokemon)
        val buttonView: Button = itemView.findViewById(R.id.changeGIF)
    }
}

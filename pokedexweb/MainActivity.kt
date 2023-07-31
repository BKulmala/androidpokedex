package com.example.pokedexweb

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.util.Locale

class MainActivity : AppCompatActivity() {
    val pokemonList: HashMap<String, String> = hashMapOf()
    lateinit var adapter: CustomAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        val mDatabase: DatabaseReference = Firebase.database.reference
        mDatabase.get().addOnSuccessListener {
            var iterator = 0
            var pokeExample = ""
            adapter = CustomAdapter(ArrayList<ItemsViewModel>(), findViewById(R.id.pokemonSelect))
            val searchPokemon = findViewById<SearchView>(R.id.searchView)
            it.child("sinnoh").children.forEach { _ ->
                if(it.child("kanto").child(iterator.toString()).child("name").value.toString().take(pokeExample.length) == pokeExample) {
                    pokemonList[it.child("kanto").child(iterator.toString()).child("name").value.toString()] = it.child("kanto").child(iterator.toString()).child("type").value.toString()
                }
                if(it.child("johto").child(iterator.toString()).child("name").value.toString().take(pokeExample.length) == pokeExample) {
                    pokemonList[it.child("johto").child(iterator.toString()).child("name").value.toString()] = it.child("johto").child(iterator.toString()).child("type").value.toString()
                }
                if(it.child("hoenn").child(iterator.toString()).child("name").value.toString().take(pokeExample.length) == pokeExample) {
                    pokemonList[it.child("hoenn").child(iterator.toString()).child("name").value.toString()] = it.child("hoenn").child(iterator.toString()).child("type").value.toString()
                }
                if(it.child("sinnoh").child(iterator.toString()).child("name").value.toString().take(pokeExample.length) == pokeExample) {
                    pokemonList[it.child("sinnoh").child(iterator.toString()).child("name").value.toString()] = it.child("sinnoh").child(iterator.toString()).child("type").value.toString()
                }
                if(it.child("unova").child(iterator.toString()).child("name").value.toString().take(pokeExample.length) == pokeExample) {
                    pokemonList[it.child("unova").child(iterator.toString()).child("name").value.toString()] = it.child("unova").child(iterator.toString()).child("type").value.toString()
                }
                if(it.child("kalos").child(iterator.toString()).child("name").value.toString().take(pokeExample.length) == pokeExample) {
                    pokemonList[it.child("kalos").child(iterator.toString()).child("name").value.toString()] = it.child("kalos").child(iterator.toString()).child("type").value.toString()
                }
                if(it.child("alola").child(iterator.toString()).child("name").value.toString().take(pokeExample.length) == pokeExample) {
                    pokemonList[it.child("alola").child(iterator.toString()).child("name").value.toString()] = it.child("alola").child(iterator.toString()).child("type").value.toString()
                }
                if(it.child("galar").child(iterator.toString()).child("name").value.toString().take(pokeExample.length) == pokeExample) {
                    pokemonList[it.child("galar").child(iterator.toString()).child("name").value.toString()] = it.child("galar").child(iterator.toString()).child("type").value.toString()
                }
                iterator = iterator + 1
            }
            searchPokemon.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    filterList(newText)
                    return true
                }

            })
            var pokemonDisplay = pokemonList
            var data = ArrayList<ItemsViewModel>()
            val recyclerview = findViewById<RecyclerView>(R.id.recycleView)
            recyclerview.layoutManager = LinearLayoutManager(this)
            recyclerview.adapter = adapter
            pokemonDisplay.forEach{(key, _) -> data.add(ItemsViewModel(R.drawable.ic_baseline_folder_24, key))
            }

        }.addOnFailureListener{
            Log.e("firebase", "Error getting data")
        }
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    private fun filterList(query: String?) {
    Log.e("query", query.toString())
        if (query != null) {
            val filteredList = ArrayList<ItemsViewModel>()
            for (i in pokemonList) {
                if (i.key.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(ItemsViewModel(R.drawable.ic_baseline_folder_24, i.key))
                }
            }

            if (filteredList.isEmpty()) {
                Toast.makeText(this, "No Data found", Toast.LENGTH_SHORT).show()
            } else {
                adapter.setFilteredList(filteredList)
            }
        }
    }
}

package org.example.recipesapp.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.example.recipesapp.R
import org.example.recipesapp.databinding.ActivityMainBinding
import org.example.recipesapp.ui.categories.CategoriesListFragment
import org.example.recipesapp.ui.recipes.favorites.FavoritesFragment

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for ActivityMainBinding must not be null"
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val btnCategories = binding.btnCategory
        val btnFavorites = binding.btnFavourites

        btnFavorites.setOnClickListener {
            supportFragmentManager.commit {
                replace<FavoritesFragment>(R.id.mainContainer)
                setReorderingAllowed(true)
                addToBackStack("Fragment Favorites")
            }
        }

        btnCategories.setOnClickListener {
            supportFragmentManager.commit {
                replace<CategoriesListFragment>(R.id.mainContainer)
                setReorderingAllowed(true)
                addToBackStack("Fragment Categories")
            }
        }
    }
}
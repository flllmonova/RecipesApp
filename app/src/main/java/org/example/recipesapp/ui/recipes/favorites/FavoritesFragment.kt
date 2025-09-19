package org.example.recipesapp.ui.recipes.favorites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.example.recipesapp.R
import org.example.recipesapp.data.STUB
import org.example.recipesapp.databinding.FragmentFavoritesBinding
import org.example.recipesapp.ui.ARG_RECIPE
import org.example.recipesapp.ui.recipes.recipe.RecipeFragment
import org.example.recipesapp.ui.recipes.recipeslist.RecipesListAdapter
import org.example.recipesapp.ui.SET_FAVORITE_RECIPES_IDS
import org.example.recipesapp.ui.SHARED_PREFS_FAVORITES

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for FragmentFavoritesBinding must not be null"
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        initUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val recipesIds = getFavorites().mapNotNull { it.toIntOrNull() }.toSet()
        val adapter = RecipesListAdapter(STUB.getRecipesByIds(recipesIds))
        binding.rvFavorites.adapter = adapter
        adapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
    }

    private fun initUI() {
        if (getFavorites().isEmpty()) {
            binding.rvFavorites.visibility = View.GONE
            binding.tvFavoritesStub.visibility = View.VISIBLE
        } else {
            binding.rvFavorites.visibility = View.VISIBLE
            binding.tvFavoritesStub.visibility = View.GONE
        }
    }

    private fun getFavorites(): HashSet<String> {
        return HashSet(
            activity
                ?.getSharedPreferences(SHARED_PREFS_FAVORITES, Context.MODE_PRIVATE)
                ?.getStringSet(SET_FAVORITE_RECIPES_IDS, mutableSetOf<String>())
                ?: mutableSetOf()
        )
    }

    private fun openRecipeByRecipeId(recipeId: Int) {
        val recipe = STUB.getRecipeById(recipeId)
        val bundle = bundleOf(ARG_RECIPE to recipe)
        activity?.supportFragmentManager?.commit {
            replace<RecipeFragment>(R.id.mainContainer, args = bundle)
            setReorderingAllowed(true)
            addToBackStack("Recipe Fragment")
        }
    }
}
package org.example.recipesapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.example.recipesapp.databinding.FragmentFavoritesBinding

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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler() {
        val recipesIds = (getFavorites().mapNotNull { it.toIntOrNull() }).toSet()
        val adapter = RecipesListAdapter(STUB.getRecipesByIds(recipesIds))
        binding.rvFavorites.adapter = adapter
        adapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })

        if (recipesIds.isEmpty()) {
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
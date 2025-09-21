package org.example.recipesapp.ui.recipes.recipe

import androidx.lifecycle.ViewModel
import org.example.recipesapp.model.Ingredient

class RecipeViewModel : ViewModel() {

    data class RecipeState(
        val id: Int? = null,
        val title: String? = null,
        val imageUrl: String? = null,
        val isAddedToFavorites: Boolean = false,
        val portionsAmount: Int = 1,
        val ingredients: List<Ingredient> = listOf(),
        val method: List<String> = listOf(),
    )
}
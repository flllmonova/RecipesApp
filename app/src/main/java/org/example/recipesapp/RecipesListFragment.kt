package org.example.recipesapp

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import org.example.recipesapp.databinding.FragmentRecipesListBinding
import java.io.InputStream

class RecipesListFragment : Fragment() {

    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for FragmentRecipesListBinding must not be null"
        )

    var categoryId: Int? = null
    var categoryName: String? = null
    var categoryImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipesListBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        categoryId = arguments?.getInt(ARG_CATEGORY_ID) ?: 0
        categoryName = arguments?.getString(ARG_CATEGORY_NAME) ?: ""
        categoryImageUrl = arguments?.getString(ARG_CATEGORY_IMAGE_URL) ?: ""
        binding.tvCategoryTitle.text = categoryName
        try {
            val inputStream: InputStream? =
                binding.root.context?.assets?.open(categoryImageUrl.toString())
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.ivCategory.setImageDrawable(drawable)
        } catch (e: Exception) {
            Log.e("Image not found", Log.getStackTraceString(e))
        }
        initRecycler(categoryId ?: 0)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initRecycler(categoryId: Int) {
        val adapter = RecipesListAdapter(STUB.getRecipesByCategoryId(categoryId))
        binding.rvRecipes.adapter = adapter
        adapter.setOnItemClickListener(object : RecipesListAdapter.OnItemClickListener {
            override fun onItemClick(recipeId: Int) {
                openRecipeByRecipeId(recipeId)
            }
        })
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
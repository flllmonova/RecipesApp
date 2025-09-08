package org.example.recipesapp

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import com.google.android.material.divider.MaterialDividerItemDecoration
import org.example.recipesapp.databinding.FragmentRecipeBinding
import java.io.InputStream

class RecipeFragment : Fragment() {

    private var _binding: FragmentRecipeBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException(
            "Binding for FragmentRecipeBinding must not be null"
        )
    private var recipe: Recipe? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRecipeBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recipe = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable(ARG_RECIPE, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE)
        }
        initUI()
        initRecycler()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun initUI() {
        binding.tvRecipeTitle.text = recipe?.title ?: ""
        try {
            val inputStream: InputStream? =
                binding.root.context?.assets?.open(recipe?.imageUrl ?: "")
            val drawable = Drawable.createFromStream(inputStream, null)
            binding.ivRecipe.setImageDrawable(drawable)
        } catch (e: Exception) {
            Log.e("Image not found", Log.getStackTraceString(e))
        }
    }

    private fun initRecycler() {
        initIngredientsRecycler()
        initMethodRecycler()
    }

    private fun initIngredientsRecycler() {
        val adapter = IngredientsAdapter(recipe?.ingredients ?: listOf())
        binding.rvIngredients.adapter = adapter
        binding.rvIngredients.addItemDecoration(initDivider())
    }

    private fun initMethodRecycler() {
        val adapter = MethodAdapter(recipe?.method ?: listOf())
        binding.rvMethod.adapter = adapter
        binding.rvMethod.addItemDecoration(initDivider())

    }

    private fun initDivider(): MaterialDividerItemDecoration {
        val context = binding.root.context
        val divider = MaterialDividerItemDecoration(context, LinearLayout.VERTICAL)
        divider.apply {
            isLastItemDecorated = false
            dividerColor = resources.getColor(
                R.color.navigationBarColor, context.theme
            )
            dividerThickness = resources.getInteger(R.integer.dividerThickness)
        }
        return divider
    }
}
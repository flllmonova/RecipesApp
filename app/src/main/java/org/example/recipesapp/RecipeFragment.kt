package org.example.recipesapp

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SeekBar
import androidx.core.content.edit
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

        binding.ibFavorites.apply {
            var favoriteRecipesIdSet = getFavorites()
            if (recipe?.id.toString() in favoriteRecipesIdSet) setImageResource(R.drawable.ic_heart)
            else setImageResource(R.drawable.ic_heart_empty)

            setOnClickListener {
                favoriteRecipesIdSet = HashSet(getFavorites())
                if (recipe?.id.toString() in favoriteRecipesIdSet) {
                    setImageResource(R.drawable.ic_heart_empty)
                    favoriteRecipesIdSet.remove(recipe?.id.toString())
                } else {
                    favoriteRecipesIdSet.add(recipe?.id.toString())
                    setImageResource(R.drawable.ic_heart)
                }
                saveFavorites(favoriteRecipesIdSet)
            }
        }
    }

    private fun initRecycler() {
        val adapterIngredients = IngredientsAdapter(recipe?.ingredients ?: listOf())
        binding.rvIngredients.apply {
            adapter = adapterIngredients
            addItemDecoration(initDivider())
        }

        val adapterMethod = MethodAdapter(recipe?.method ?: listOf())
        binding.rvMethod.apply {
            adapter = adapterMethod
            addItemDecoration(initDivider())
        }

        binding.sbPortions.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(
                seekBar: SeekBar?,
                progress: Int,
                fromUser: Boolean
            ) {
                binding.tvPortionsAmount.text = progress.toString()
                adapterIngredients.updateIngredients(progress)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    private fun initDivider(): MaterialDividerItemDecoration {
        val context = binding.root.context
        val divider = MaterialDividerItemDecoration(context, LinearLayout.VERTICAL)
        divider.apply {
            isLastItemDecorated = false
            dividerColor = resources.getColor(
                R.color.navigationBarColor, context.theme
            )
            dividerThickness = resources.getInteger(R.integer.divider_thickness)
        }
        return divider
    }

    private fun saveFavorites(recipesIdSet: Set<String>) {
        val sharedPrefs = activity?.getSharedPreferences(
            SHARED_PREFS_FAVORITES,
            Context.MODE_PRIVATE
        ) ?: return
        sharedPrefs.edit {
            putStringSet(SET_FAVORITE_RECIPES_ID, recipesIdSet)
            apply()
        }
    }

    private fun getFavorites(): MutableSet<String> {
        return activity
            ?.getSharedPreferences(SHARED_PREFS_FAVORITES, Context.MODE_PRIVATE)
            ?.getStringSet(SET_FAVORITE_RECIPES_ID, mutableSetOf<String>())
            ?: mutableSetOf()
    }
}
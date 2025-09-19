package org.example.recipesapp.ui.recipes.recipeslist

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.example.recipesapp.databinding.ItemRecipeBinding
import org.example.recipesapp.model.Recipe
import java.io.InputStream

class RecipesListAdapter(private val dataSet: List<Recipe>) :
    RecyclerView.Adapter<RecipesListAdapter.ViewHolder>() {

    var itemClickListener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onItemClick(recipeId: Int)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        itemClickListener = listener
    }

    class ViewHolder(val itemBinding: ItemRecipeBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val imageRecipeItem = itemBinding.ivRecipeItem
        val titleRecipeItem = itemBinding.tvRecipeItemTitle
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemRecipeBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val recipe = dataSet[position]
        try {
            val inputStream: InputStream? =
                viewHolder.itemBinding.root.context?.assets?.open(recipe.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.imageRecipeItem.setImageDrawable(drawable)
        } catch (e: Exception) {
            Log.e("Image not found", Log.getStackTraceString(e))
        }
        viewHolder.titleRecipeItem.text = recipe.title
        viewHolder.itemBinding.root.setOnClickListener {
            itemClickListener?.onItemClick(recipe.id)
        }
    }

    override fun getItemCount() = dataSet.size
}
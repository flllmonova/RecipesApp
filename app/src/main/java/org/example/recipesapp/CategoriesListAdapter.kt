package org.example.recipesapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.example.recipesapp.databinding.ItemCategoryBinding
import java.io.InputStream

class CategoriesListAdapter(private val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(val itemBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val imageCategoryItem = itemBinding.imageCategoryItem
        val titleCategoryItem = itemBinding.titleCategoryItem
        val descriptionCategoryItem = itemBinding.descriptionCategoryItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding = ItemCategoryBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category = dataSet[position]
        try {
            val inputStream: InputStream? =
                viewHolder.itemBinding.root.context?.assets?.open(category.imageUrl)
            val drawable = Drawable.createFromStream(inputStream, null)
            viewHolder.imageCategoryItem.setImageDrawable(drawable)
        } catch (e: Exception) {
            Log.e("Image not found", Log.getStackTraceString(e))
        }
        viewHolder.titleCategoryItem.text = category.title
        viewHolder.descriptionCategoryItem.text = category.description
    }

    override fun getItemCount() = dataSet.size
}
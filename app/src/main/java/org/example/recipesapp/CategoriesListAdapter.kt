package org.example.recipesapp

import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.example.recipesapp.databinding.ItemCategoryBinding.bind
import java.io.InputStream

class CategoriesListAdapter(private val dataSet: List<Category>) :
    RecyclerView.Adapter<CategoriesListAdapter.ViewHolder>() {

    class ViewHolder(val itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = bind(itemView)
        val imageCategoryItem = binding.imageCategoryItem
        val titleCategoryItem = binding.titleCategoryItem
        val descriptionCategoryItem = binding.descriptionCategoryItem
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val view = inflater.inflate(
            R.layout.item_category, viewGroup, false
        )
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val category = dataSet[position]
        try {
            val inputStream: InputStream? =
                viewHolder.itemView.context?.assets?.open(category.imageUrl)
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
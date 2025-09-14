package org.example.recipesapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.example.recipesapp.databinding.ItemIngredientBinding
import java.math.BigDecimal
import java.math.BigInteger
import java.math.RoundingMode.HALF_UP

class IngredientsAdapter(private val dataSet: List<Ingredient>) :
    RecyclerView.Adapter<IngredientsAdapter.ViewHolder>() {

    private var quantity = PORTIONS_AMOUNT_MIN

    fun updateIngredients(progress: Int) {
        quantity = progress
        notifyDataSetChanged()
    }

    private val pluralsUnitsOfMeasure = mapOf(
        UNIT_OF_MEASURE_LEAF to R.plurals.unit_of_measure_leaf,
        UNIT_OF_MEASURE_SLICE to R.plurals.unit_of_measure_slice,
        UNIT_OF_MEASURE_TEA_SPOON to R.plurals.unit_of_measure_tea_spoon,
        UNIT_OF_MEASURE_TABLE_SPOON to R.plurals.unit_of_measure_table_spoon,
    )

    class ViewHolder(itemBinding: ItemIngredientBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        val descriptionIngredientItem = itemBinding.tvIngredientItemDescription
        val quantityIngredientItem = itemBinding.tvIngredientItemQuantity
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(viewGroup.context)
        val binding =
            ItemIngredientBinding.inflate(inflater, viewGroup, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        val ingredient = dataSet[position]
        val countedQuantity = BigDecimal(ingredient.quantity)
            .multiply(BigDecimal(quantity))
            .setScale(1, HALF_UP)
            .run {
                if (this.remainder(BigDecimal(1)) == BigDecimal("0.0")) {
                    toBigInteger()
                } else {
                    stripTrailingZeros()
                }
            }
        viewHolder.descriptionIngredientItem.text = ingredient.description
        viewHolder.quantityIngredientItem.text = (
            if (countedQuantity is BigInteger
                && ingredient.unitOfMeasure in pluralsUnitsOfMeasure
            ) {
                viewHolder.itemView.resources.getQuantityString(
                    pluralsUnitsOfMeasure[ingredient.unitOfMeasure]
                        ?: R.plurals.unit_of_measure_default,
                    countedQuantity.toInt(), countedQuantity.toInt()
                )
            } else viewHolder.itemView.resources.getString(
                R.string.quantity_ingredient,
                countedQuantity.toString(),
                ingredient.unitOfMeasure
            ))
    }

    override fun getItemCount(): Int = dataSet.size
}
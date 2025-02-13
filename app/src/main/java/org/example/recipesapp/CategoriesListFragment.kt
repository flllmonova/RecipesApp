package org.example.recipesapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import org.example.recipesapp.databinding.FragmentListCategoriesBinding
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class CategoriesListFragment : Fragment() {

    private var _binding: FragmentListCategoriesBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding FragmentListCategoriesBinding must not be null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListCategoriesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
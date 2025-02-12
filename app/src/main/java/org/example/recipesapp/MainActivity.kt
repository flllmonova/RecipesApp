package org.example.recipesapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.example.recipesapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding
            ?: throw IllegalStateException("Binding for ActivityMainBinding must not be null")

    private val fragmentManager = supportFragmentManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.mainContainer, CategoriesListFragment()).commit()
    }
}
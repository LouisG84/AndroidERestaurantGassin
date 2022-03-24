package fr.isen.gassin.androiderestaurant

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.gassin.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.gassin.androiderestaurant.databinding.ActivityHomeBinding

class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root

        binding.detailTitle.text = intent.getStringExtra(ActivityMeal.ITEM_KEY)

        setContentView(view)
    }
}
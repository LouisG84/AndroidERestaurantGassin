package fr.isen.gassin.androiderestaurant

import android.os.Bundle
import android.view.ContextMenu
import android.view.Menu
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import fr.isen.gassin.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.gassin.androiderestaurant.model.Item


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val actionBar = supportActionBar

        button = findViewById(R.id.buttonTotal);
        button.setOnClickListener{
            val snackbar = Snackbar.make(it, "Produit ajouté au panier", Snackbar.LENGTH_LONG)
            snackbar.show()}

        val item = intent.getSerializableExtra(ActivityMeal.ITEM_KEY) as Item // on caste l'item et après on fait les opérations

        binding.detailTitle.text = item.name_fr
        binding.detailIngredient.text = item.ingredients.joinToString(", "){it.name_fr}
        binding.buttonTotal.text = item.prices.joinToString(", "){"Total: "+ it.price.toString()}

        val carousselAdapter = CarouselAdapter(this, item.images)

        binding.detailSlider.adapter = carousselAdapter


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }




}
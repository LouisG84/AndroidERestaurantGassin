package fr.isen.gassin.androiderestaurant

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.Toast
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

        var somme = 1
        val actionBar = supportActionBar

        button = findViewById(R.id.buttonTotal)
       // button = findViewById(R.id.buttonPlus)
        button.setOnClickListener{
            val snackbar = Snackbar.make(it, "Produit ajouté au panier", Snackbar.LENGTH_LONG)
            snackbar.show()}

        val item = intent.getSerializableExtra(ActivityMeal.ITEM_KEY) as Item // on caste l'item et après on fait les opérations

        binding.detailTitle.text = item.name_fr
        binding.detailIngredient.text = item.ingredients.joinToString(", "){it.name_fr}
        binding.buttonTotal.text = item.prices.joinToString(", "){"Ajouter au panier: "+ it.price.toString()}

        val carousselAdapter = CarouselAdapter(this, item.images)

        binding.detailSlider.adapter = carousselAdapter

        binding.buttonPlus.setOnClickListener()
        {
            somme++
            Log.i("somme", somme.toString())
            display(somme, somme * item.prices[0].price.toFloat())
        }

        binding.buttonMoins.setOnClickListener()
        {

            if (somme > 1) {
                somme--
                Log.i("somme", somme.toString())
                display(somme, somme * item.prices[0].price.toFloat())
            }
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu to use in the action bar
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_toolbar, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle presses on the action bar menu items
        when (item.itemId) {
            R.id.panier -> {
                Toast.makeText(this@DetailActivity, "Panier", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.bluetooth -> {
                val intent = Intent(this, BLEActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@DetailActivity, "Bluetooth", Toast.LENGTH_SHORT).show()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }

    @SuppressLint("setTextI18n")
    private fun display(somme: Int, price: Float) {
        binding.quantityText.text = somme.toString()
        binding.buttonTotal.text = "Ajouter au panier: " + price.toString() + "€"
    }




}
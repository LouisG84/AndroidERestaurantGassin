package fr.isen.gassin.androiderestaurant

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import fr.isen.gassin.androiderestaurant.ActivityMeal.Companion.ITEM_KEY
import fr.isen.gassin.androiderestaurant.databinding.ActivityDetailBinding
import fr.isen.gassin.androiderestaurant.model.Basket
import fr.isen.gassin.androiderestaurant.model.BasketItem
import fr.isen.gassin.androiderestaurant.model.Item
import java.io.File


class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var button: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val somme = 1
        val actionBar = supportActionBar
        actionBar!!.title = "Detail"

        button = findViewById(R.id.buttonTotal)
        button.setOnClickListener{
            val snackbar = Snackbar.make(it, "Produit ajouté au panier", Snackbar.LENGTH_LONG)
            val intent = Intent(this@DetailActivity, BasketActivity::class.java)
            startActivity(intent)
            snackbar.show()
        }

        val item = intent.getSerializableExtra(ITEM_KEY) as Item // on caste l'item et après on fait les opérations

        binding.detailTitle.text = item.name_fr
        binding.detailIngredient.text = item.ingredients.joinToString(", "){it.name_fr}
        binding.buttonTotal.text = item.prices.joinToString(", "){"Ajouter au panier: "+ it.price.toString()}

        val carousselAdapter = CarouselAdapter(this, item.images)

        binding.detailSlider.adapter = carousselAdapter

        addArticle(item)


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
                val intent = Intent(this, BasketActivity::class.java)
                startActivity(intent)
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



    private fun updateSharedPreferences(quantity: Int, price: Float) {
        val sharedPreferences = this.getSharedPreferences(getString(R.string.spFileName), Context.MODE_PRIVATE)

        val oldQuantity = sharedPreferences.getInt(getString(R.string.spTotalQuantity), 0)
        val newQuantity = oldQuantity + quantity
        sharedPreferences.edit().putInt(getString(R.string.spTotalQuantity), newQuantity).apply()

        val oldPrice = sharedPreferences.getFloat(getString(R.string.spTotalPrice), 0.0f)
        val newPrice = oldPrice + price
        sharedPreferences.edit().putFloat(getString(R.string.spTotalPrice), newPrice).apply()
    }

    private fun changeActivity() {
        val intent = Intent(this@DetailActivity, BasketActivity::class.java)
        startActivity(intent)
    }

    private fun addArticle(item: Item) {
        var somme = 0
        binding.buttonPlus.setOnClickListener()
        {
            somme++
            Log.i("somme", somme.toString())
            display(somme, somme * item.prices[0].price)
        }

        binding.buttonMoins.setOnClickListener()
        {

            if (somme > 0) {
                somme--
                Log.i("somme", somme.toString())
                display(somme, somme * item.prices[0].price)
            }
        }

        binding.detailTitle.text = item.name_fr

        val txt = getString(R.string.totalPrice) + item.prices[0].price + " €"
        binding.buttonTotal.text = txt

        binding.buttonTotal.setOnClickListener {
            if (somme >= 1 ) {
                Snackbar.make(
                    it,
                    "Article(s) ajouté(s) au panier : " + somme + " " + binding.detailTitle.text,
                    Snackbar.LENGTH_LONG
                ).setAction("> Panier") {
                    startActivity(Intent(this, BasketActivity::class.java))
                }
                    .show()
                updateFile(BasketItem(item, somme))
                updateSharedPreferences(somme, (item.prices[0].price * somme))
            }
            else{
                Snackbar.make(it, "Veuillez ajouter au moins un article", Snackbar.LENGTH_LONG ).show()
            }
        }
    }

    private fun updateFile(itemBasket: BasketItem) {
        val file = File(cacheDir.absolutePath + "/basket.json")
        var itemsBasket: List<BasketItem> = ArrayList()

        if (file.exists()) {
            itemsBasket = Gson().fromJson(file.readText(), Basket::class.java).data
        }

        var dupli = false
        for (i in itemsBasket.indices) {
            if (itemsBasket[i].article == itemBasket.article) {
                itemsBasket[i].quantity += itemBasket.quantity
                dupli = true
            }
        }

        if (!dupli) {
            itemsBasket = itemsBasket + itemBasket
        }

        file.writeText(Gson().toJson(Basket(itemsBasket)))
    }



}
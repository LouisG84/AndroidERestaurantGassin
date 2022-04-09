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

        var somme = 1
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

        addArticle(somme, item)
        initDetail(item)

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

    private fun initDetail(item: Item) {

        var nbInBucket = 1
        binding.buttonPlus.setOnClickListener {
            addArticle(1, item)
            nbInBucket += 1
        }
        binding.buttonMoins.setOnClickListener {
            addArticle(1, item)
            if (nbInBucket==1){}
            else{nbInBucket -= 1}
        }
        binding.detailTitle.text = item.name_fr

        val txt = getString(R.string.totalPrice) + item.prices[0].price + " €"
        binding.buttonTotal.text = txt

        binding.buttonTotal.setOnClickListener {
            Snackbar.make(
                it, "Article(s) ajouté(s) au panier : " + nbInBucket + " " + binding.detailTitle.text,
                Snackbar.LENGTH_LONG
            ).setAction("Voir le panier"){
                startActivity(Intent(this, BasketActivity::class.java))
            }
                .show()

            //Toast.makeText(this, getString(R.string.add_to_basket), Toast.LENGTH_SHORT).show()
            updateFile(BasketItem(item, nbInBucket))
            updateSharedPreferences(nbInBucket, (item.prices[0].price.toFloat() * nbInBucket))
            //finish()
            //changeActivityToBasket()
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

    private fun addArticle(somme: Int, item: Item) {
        var somme1 = somme
        binding.buttonPlus.setOnClickListener()
        {
            somme1++
            Log.i("somme", somme1.toString())
            display(somme1, somme1 * item.prices[0].price.toFloat())
        }

        binding.buttonMoins.setOnClickListener()
        {

            if (somme1 > 1) {
                somme1--
                Log.i("somme", somme1.toString())
                display(somme1, somme1 * item.prices[0].price.toFloat())
            }
        }
    }


}
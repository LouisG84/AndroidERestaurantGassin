package fr.isen.gassin.androiderestaurant

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.gson.Gson
import fr.isen.gassin.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.gassin.androiderestaurant.model.Basket
import fr.isen.gassin.androiderestaurant.model.BasketItem
import java.io.File

class BasketActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBasketBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBasketBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Panier" // affiche nom categorie en haut

        val file = File(cacheDir.absolutePath + "/basket.json")

        if (file.exists()) {
            val basketItems: List<BasketItem> =
                Gson().fromJson(file.readText(), Basket::class.java).data
            display(basketItems)
        }
/*
        val quantity = getString(R.string.basketTotalQuantity) + " " + this.getSharedPreferences(
            getString(R.string.spFileName),
            Context.MODE_PRIVATE
        ).getInt(getString(R.string.spTotalQuantity), 0).toString() + " article(s)"
        binding.basketTotalQuantity.text = quantity*/

        val price = getString(R.string.totalPrice) + " " + this.getSharedPreferences(
            getString(R.string.spFileName),
            Context.MODE_PRIVATE
        ).getFloat(getString(R.string.spTotalPrice), 0.0f).toString() + " € "
        binding.basketPrixTotal.text = price


        binding.deleteAll.setOnClickListener {
            deleteBasketData()
            finish()
            changeActivity()
        }

        binding.commande.setOnClickListener {
            startActivity(Intent(this, ActivityCommande::class.java))
        }


    }


    private fun display(dishesList: List<BasketItem>) {
        binding.basketRecycler.layoutManager = LinearLayoutManager(this)
        binding.basketRecycler.adapter = BasketAdapter(dishesList) {
            deleteArticle(it)
        }
    }

    private fun deleteArticle(article: BasketItem) {
        val file = File(cacheDir.absolutePath + "/basket.json")
        var platPanier: List<BasketItem> = ArrayList()

        if (file.exists()) {
            platPanier = Gson().fromJson(file.readText(), Basket::class.java).data
            platPanier = platPanier - article
            updateSharedPreferences(article.quantity, article.article.prices[0].price)
        }

        file.writeText(Gson().toJson(Basket(platPanier)))

        finish()
        this.recreate()
    }


    private fun deleteBasketData() {
        File(cacheDir.absolutePath + "/basket.json").delete()
        this.getSharedPreferences(getString(R.string.spFileName), Context.MODE_PRIVATE).edit()
            .remove(getString(R.string.spTotalPrice)).apply()
        this.getSharedPreferences(getString(R.string.spFileName), Context.MODE_PRIVATE).edit()
            .remove(getString(R.string.spTotalQuantity)).apply()
        Toast.makeText(this, "deleted", Toast.LENGTH_SHORT).show()
    }

    private fun updateSharedPreferences(quantity: Int, price: Float) {
        val sharedPreferences =
            this.getSharedPreferences(getString(R.string.spFileName), Context.MODE_PRIVATE)

        val oldQuantity = sharedPreferences.getInt(getString(R.string.spTotalQuantity), 0)
        val newQuantity = oldQuantity + quantity
        sharedPreferences.edit().putInt(getString(R.string.spTotalQuantity), newQuantity).apply()

        val oldPrice = sharedPreferences.getFloat(getString(R.string.spTotalPrice), 0.0f)
        val newPrice = oldPrice - price
        sharedPreferences.edit().putFloat(getString(R.string.spTotalPrice), newPrice).apply()
    }

    private fun changeActivity() {
        val intent = Intent(this, ActivityHome::class.java)
        startActivity(intent)
    }


}
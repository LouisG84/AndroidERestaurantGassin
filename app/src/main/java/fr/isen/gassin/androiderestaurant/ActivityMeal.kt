package fr.isen.gassin.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.gson.Gson
import fr.isen.gassin.androiderestaurant.databinding.ActivityMealBinding
import fr.isen.gassin.androiderestaurant.model.DataResult
import fr.isen.gassin.androiderestaurant.model.Item
import java.util.*
import kotlin.collections.ArrayList
import org.json.JSONObject
import java.nio.charset.Charset

class ActivityMeal : AppCompatActivity() {
    private lateinit var binding: ActivityMealBinding
    private var itemsList = ArrayList<Item>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMealBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)


        val actionBar = supportActionBar
        val categoryName =
            intent.getStringExtra("category") // recupere valeur stocké dans "category"
        actionBar!!.title = categoryName // affiche nom categorie en haut
        binding.category.text = categoryName // affiche nom categorie en titre

        val items = resources.getStringArray(R.array.items_list).toList() as ArrayList

        binding.itemsList.layoutManager = LinearLayoutManager(applicationContext)
        binding.itemsList.adapter = CategoryAdapter(itemsList) {
            val intent = Intent(this, DetailActivity::class.java)
            intent.putExtra(ITEM_KEY, it)
            startActivity(intent)
        }

        getDataFromApi(intent.getStringExtra("category") ?: "")
        //loadDataFromServer()
    }

    private fun getDataFromApi(category: String) {
        val queue = Volley.newRequestQueue(this)
        val url = "http://test.api.catering.bluecodegames.com/menu"
        val json = JSONObject()
        json.put("id_shop", "1")
        json.toString()
        val requestBody = json.toString()

        val stringReq: StringRequest =
            object : StringRequest(Method.POST, url,
                { response ->
                    // response
                    val strResp = response.toString()
                    Log.d("API", strResp)
                    val dataResult = Gson().fromJson(strResp, DataResult::class.java)

                    val items = dataResult.data.firstOrNull { it.name_fr == category }?.items
                        ?: arrayListOf()
                    binding.itemsList.adapter = CategoryAdapter(items) {
                        val intent = Intent(this, DetailActivity::class.java)
                        intent.putExtra(ITEM_KEY, it)// Recupere et stocke dans ITEM_KEY
                        startActivity(intent)
                    }

                },
                Response.ErrorListener { error ->
                    Log.d("API", "error => $error")
                }
            ) {
                override fun getBody(): ByteArray {
                    return requestBody.toByteArray(Charset.defaultCharset())
                }
            }
        queue.add(stringReq)
    }

    /* Correction prof
    private fun loadDataFromServer(){
        val queue = Volley.newRequestQueue(this)
        val url: String = "http://test.api.catering.bluecodegames.com/menu"

        val jsonObject = JSONObject()

        jsonObject.put("id_shop", "1")
        //Request response from
        val stringReq = JsonObjectRequest(
            Request.Method.POST,
            url,
            { response ->
                var strResp = response, toString()
                Log.d("API", strResp)
            },{
                Log.d("API", "that didn't work")
            })
        queue.add(stringReq)

    }*/

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
                Toast.makeText(this@ActivityMeal, "Panier", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.bluetooth -> {
                val intent = Intent(this, BLEActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@ActivityMeal, "Bluetooth", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        val ITEM_KEY = "item"
    }

}
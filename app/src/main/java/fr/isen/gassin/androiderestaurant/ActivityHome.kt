package fr.isen.gassin.androiderestaurant

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import fr.isen.gassin.androiderestaurant.databinding.ActivityHomeBinding


class ActivityHome : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Accueil" // titre accueil

        binding.buttonEntree.setOnClickListener{
            goToCategory(getString(R.string.button_entree))
        }        
        
        binding.buttonPlats.setOnClickListener{
            goToCategory(getString(R.string.button_plat))
        }

        binding.buttonDesserts.setOnClickListener{
            goToCategory(getString(R.string.button_dessert))
        }
    }

    private fun goToCategory(category: String) {
        val intent = Intent(this, ActivityMeal::class.java)

        Toast.makeText(
            this@ActivityHome,
            category,
            Toast.LENGTH_SHORT
        ).show()
        intent.putExtra("category", category)
        startActivity(intent)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("home","ondestroy called")
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
                Toast.makeText(this@ActivityHome, "Panier", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.bluetooth -> {
                val intent = Intent(this, BLEActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@ActivityHome, "Bluetooth", Toast.LENGTH_SHORT).show()
                return true
            }

        }
        return super.onOptionsItemSelected(item)
    }
}

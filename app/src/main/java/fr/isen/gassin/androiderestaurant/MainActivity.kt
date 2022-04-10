package fr.isen.gassin.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val secondActivityBtn = findViewById<View>(R.id.button)//récupère id bouton

        secondActivityBtn.setOnClickListener() {
            val intent = Intent(this, ActivityHome::class.java)
            startActivity(intent)
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
                val intent = Intent(this, BasketActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@MainActivity, "Panier", Toast.LENGTH_SHORT).show()
                return true
            }
            R.id.bluetooth -> {
                val intent = Intent(this, BLEActivity::class.java)
                startActivity(intent)
                Toast.makeText(this@MainActivity, "Bluetooth", Toast.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
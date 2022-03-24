package fr.isen.gassin.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import fr.isen.gassin.androiderestaurant.databinding.ActivityHomeBinding

class ActivityHome : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        binding = ActivityHomeBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Accueil" // titre accueil

        binding.BoutonEntree.setOnClickListener{
            goToCategory(getString(R.string.bouton_entree))
        }        
        
        binding.BoutonPlats.setOnClickListener{
            goToCategory(getString(R.string.bouton_plat))
        }

        binding.BoutonDesserts.setOnClickListener{
            goToCategory(getString(R.string.bouton_dessert))
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
}
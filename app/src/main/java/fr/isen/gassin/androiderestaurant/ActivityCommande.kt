package fr.isen.gassin.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import fr.isen.gassin.androiderestaurant.databinding.ActivityBasketBinding
import fr.isen.gassin.androiderestaurant.databinding.ActivityCommandeBinding

class ActivityCommande : AppCompatActivity() {
    private lateinit var binding: ActivityCommandeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCommandeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.homeCommande.setOnClickListener {
            startActivity(Intent(this, ActivityHome::class.java))
        }
        binding.homeTextCommande.setOnClickListener {
            startActivity(Intent(this, ActivityHome::class.java))
        }


    }
}
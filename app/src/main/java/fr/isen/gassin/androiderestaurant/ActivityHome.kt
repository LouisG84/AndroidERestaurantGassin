package fr.isen.gassin.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast

class ActivityHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val actionBar = supportActionBar
        actionBar!!.title = "Accueil" // titre accueil
        val secondActivityBtn = findViewById<View>(R.id.BoutonEntree)

        secondActivityBtn.setOnClickListener() {
            val intent = Intent(this, ActivityMeal::class.java)

            Toast.makeText(
                this@ActivityHome,
                "Entrées",
                Toast.LENGTH_SHORT
            ).show()
            startActivity(intent)
        }


    }
}
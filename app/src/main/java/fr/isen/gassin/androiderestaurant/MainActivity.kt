package fr.isen.gassin.androiderestaurant

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val secondActivityBtn = findViewById<View>(R.id.button)//récupère id bouton
        val bleBtn = findViewById<View>(R.id.bleBtn)

        secondActivityBtn.setOnClickListener() {
            val intent = Intent(this, ActivityHome::class.java)
            startActivity(intent)
        }

        bleBtn.setOnClickListener(){
            val intent = Intent(this, BLEActivity::class.java)
            startActivity(intent)
        }
    }

}
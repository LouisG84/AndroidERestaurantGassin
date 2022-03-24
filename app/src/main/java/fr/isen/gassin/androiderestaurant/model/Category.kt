package fr.isen.gassin.androiderestaurant.model

import java.io.Serializable

data class Category(val name_fr: String, val item :ArrayList<Item>): Serializable

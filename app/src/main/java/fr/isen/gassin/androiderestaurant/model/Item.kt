package fr.isen.gassin.androiderestaurant.model

import java.io.Serializable

data class Item(val name_fr: String, val images: ArrayList<String>): Serializable

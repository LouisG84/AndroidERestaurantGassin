package fr.isen.gassin.androiderestaurant.model

import java.io.Serializable

data class Item(
    val name_fr: String,
    val ingredients: ArrayList<Ingredient>,
    val prices: ArrayList<Price>,
    val images: ArrayList<String>
    ): Serializable

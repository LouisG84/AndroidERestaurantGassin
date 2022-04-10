package fr.isen.gassin.androiderestaurant.model

import java.io.Serializable

data class BasketItem(
    val article: Item,
    var quantity: Int
): Serializable

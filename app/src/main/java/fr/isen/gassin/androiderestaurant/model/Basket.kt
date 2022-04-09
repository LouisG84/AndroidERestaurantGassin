package fr.isen.gassin.androiderestaurant.model

import java.io.Serializable

data class Basket(
    val data : List<BasketItem>
): Serializable

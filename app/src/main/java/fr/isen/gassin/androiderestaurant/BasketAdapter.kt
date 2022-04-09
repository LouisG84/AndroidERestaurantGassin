package fr.isen.gassin.androiderestaurant

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.gassin.androiderestaurant.model.BasketItem

internal class BasketAdapter(val panier: List<BasketItem>,
                             val clickListener: (BasketItem) -> Unit) : RecyclerView.Adapter<BasketAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemNom: TextView = view.findViewById(R.id.nomBasketPlat)
        var quantity: TextView = view.findViewById(R.id.quantiteBasketPlat)
        val imagePlat: ImageView = view.findViewById(R.id.imageBasketPlat)
        val price: TextView = view.findViewById(R.id.prixBasket)
        val plus: ImageView = view.findViewById(R.id.plusBasket)
        val moins: ImageView = view.findViewById(R.id.moinsBasket)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.cell_basket, parent, false)
        return MyViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: BasketAdapter.MyViewHolder, position: Int) {

        val item = panier[position]

        holder.itemNom.text = item.article.name_fr


        val url = item.article.images[0]
        Picasso.get()
            .load(url.ifEmpty{null})
            .placeholder(R.drawable.mister_v)
            .fit().centerCrop()
            .into(holder.imagePlat)


        val price = " Total : ${item.article.prices[0].price.toFloat() * item.quantity} €"
        holder.price.text = price


        val quantity = "Quantité : ${item.quantity}"
        holder.quantity.text = quantity

        holder.moins.setOnClickListener {
            clickListener(item)
        }
    }


    override fun getItemCount(): Int {
        return panier.size
    }


}
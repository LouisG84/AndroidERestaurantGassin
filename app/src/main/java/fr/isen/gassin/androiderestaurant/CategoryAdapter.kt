package fr.isen.gassin.androiderestaurant

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ExpandableListView
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import fr.isen.gassin.androiderestaurant.model.Data
import fr.isen.gassin.androiderestaurant.model.Item
import java.util.ArrayList

internal class CategoryAdapter(val data: ArrayList<Item>, val clickListener: (Item) -> Unit) : RecyclerView.Adapter<CategoryAdapter.MyViewHolder>() {

    internal inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemTextView: TextView = view.findViewById(R.id.categoryTitle)
        val image: ImageView = view.findViewById(R.id.imageViewSelect)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.cell_category, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i("XXX","onBindViewHolder")
        val item = data[position]
        holder.itemTextView.text = item.name_fr

        val url = item.images[0]
        Picasso.get()
            .load(url.ifEmpty{null})
            .placeholder(R.drawable.mister_v)
            .fit().centerCrop()
            .into(holder.image)


        holder.itemView.setOnClickListener{
            clickListener(item)

        }
    }
    override fun getItemCount(): Int {
        return data.size
    }}

package fr.isen.gassin.androiderestaurant

import android.annotation.SuppressLint
import android.bluetooth.BluetoothDevice
import android.bluetooth.le.ScanResult
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class BLEAdapter(val data: ArrayList<ScanResult>, val result: (BluetoothDevice) -> Unit) :
    RecyclerView.Adapter<BLEAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var adresse: TextView = view.findViewById(R.id.adressDevice)
        var name: TextView = view.findViewById(R.id.nameDevice)
        var rssi: TextView = view.findViewById(R.id.rssiText)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val bleView =
            LayoutInflater.from(parent.context).inflate(R.layout.cell_ble, parent, false)
        return MyViewHolder(bleView)
    }

    @SuppressLint("MissingPermission", "SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        Log.i("XXX", "onBindViewHolder")
        val result = data[position]
        holder.adresse.text = result.device.address
        holder.name.text = result.device.name
        holder.name.text = "Unknown"
        holder.rssi.text = result.rssi.toString()

        holder.itemView.setOnClickListener {
            result(result.device)
        }
    }

    fun addElement(result: ScanResult) {
        val indexOfResult = data.indexOfFirst{
            it.device.address == result.device.address
        }
        if(indexOfResult != -1){
            data[indexOfResult] = result
           // notifyItemChanged(indexOfResult)
        } else {
            data.add(result)
           // notifyItemInserted()
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }


}

package fr.isen.gassin.androiderestaurant

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattDescriptor
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup
import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder
import java.nio.charset.StandardCharsets
import java.util.*
import kotlin.collections.ArrayList

class BLEServiceAdapter(
    bleServices: List<BLEService>
) :
    ExpandableRecyclerViewAdapter<BLEServiceAdapter.ServiceViewHolder, BLEServiceAdapter.CharacteristicViewHolder>(
        bleServices
    ) {

    class ServiceViewHolder(itemView: View) : GroupViewHolder(itemView) {
        val serviceName: TextView = itemView.findViewById(R.id.serviceName)
        val serviceUuid: TextView = itemView.findViewById(R.id.serviceUuid)
        private val serviceArrow: View = itemView.findViewById(R.id.serviceFleche)
        override fun expand() {
            serviceArrow.animate().rotation(-180f).setDuration(400L).start()
        }

        override fun collapse() {
            serviceArrow.animate().rotation(0f).setDuration(400L).start()
        }
    }

    class CharacteristicViewHolder(itemView: View) : ChildViewHolder(itemView) {
        val characteristicName: TextView = itemView.findViewById(R.id.characteristicName)
        val characteristicUuid: TextView = itemView.findViewById(R.id.characteristicUuid)
        //val characteristicProperties: TextView = itemView.findViewById(R.id.characteristicProperties)
        //val characteristicValue: TextView = itemView.findViewById(R.id.characteristicValue)

        val characteristicReadAction: Button = itemView.findViewById(R.id.buttonLire)
        val characteristicWriteAction: Button = itemView.findViewById(R.id.buttonEcrire)
        val characteristicNotifyAction: Button = itemView.findViewById(R.id.buttonNotif)
    }

    override fun onCreateGroupViewHolder(parent: ViewGroup?, viewType: Int): ServiceViewHolder =
        ServiceViewHolder(
            LayoutInflater.from(parent?.context)
                .inflate(R.layout.cell_ble_service, parent, false)
        )

    override fun onCreateChildViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CharacteristicViewHolder =
        CharacteristicViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.cell_ble_characteristic, parent, false)
        )

    override fun onBindGroupViewHolder(
        holder: ServiceViewHolder,
        flatPosition: Int,
        group: ExpandableGroup<*>
    ) {
        val title =
            BLEUUIDAttributes.getBLEAttributeFromUUID(group.title).title
        holder.serviceName.text = title
        holder.serviceUuid.text = group.title

    }

    override fun onBindChildViewHolder(
        holder: CharacteristicViewHolder?,
        flatPosition: Int,
        group: ExpandableGroup<*>?,
        childIndex: Int
    ) {
        val characteristic = (group?.items?.get(childIndex) as BluetoothGattCharacteristic)
        val title =
            BLEUUIDAttributes.getBLEAttributeFromUUID(characteristic.uuid.toString()).title

        val uuidMessage = "UUID : ${characteristic.uuid}"
        if (holder != null) {
            holder.characteristicUuid.text = uuidMessage
        }

        if (holder != null) {
            holder.characteristicName.text = title
        }
        val properties = arrayListOf<String>()



    }

    private fun addPropertyFromCharacteristic(
        characteristic: BluetoothGattCharacteristic,
        properties: ArrayList<String>,
        propertyName: String,
        propertyType: Int,
        propertyAction: Button,
        propertyCallBack: (BluetoothGattCharacteristic) -> Unit
    ) {
        if ((characteristic.properties and propertyType) != 0) {
            properties.add(propertyName)
            propertyAction.isEnabled = true
            propertyAction.alpha = 1f
            propertyAction.setOnClickListener {
                propertyCallBack.invoke(characteristic)
            }
        }
    }


}






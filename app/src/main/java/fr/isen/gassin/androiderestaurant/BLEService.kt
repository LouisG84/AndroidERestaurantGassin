package fr.isen.gassin.androiderestaurant

import android.bluetooth.BluetoothGattCharacteristic
import android.bluetooth.BluetoothGattService
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup


class BLEService(title: String, characteristics: MutableList<BluetoothGattCharacteristic>) :
    ExpandableGroup<BluetoothGattCharacteristic>(title, characteristics)
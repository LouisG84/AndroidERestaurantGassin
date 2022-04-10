package fr.isen.gassin.androiderestaurant

import android.annotation.SuppressLint
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.bluetooth.le.ScanCallback
import android.bluetooth.le.ScanResult
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.isen.gassin.androiderestaurant.databinding.ActivityBleactivityBinding

class BLEActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBleactivityBinding
    private val ENABLE_BLUETOOTH_REQUEST_CODE = 1
    private val ALL_PERMISSION_REQUEST_CODE = 100
    private var isScanning = false
    private var adapter: BLEAdapter? = null
    private lateinit var myRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBleactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        when {
            bluetoothAdapter?.isEnabled == true ->
                startLeScanWithPermission(true)
            bluetoothAdapter != null ->
                askBluetoothPermission()
            else -> {
                displayBLEUnavailable()
            }
        }

        binding.btnPlay.setOnClickListener {
            startLeScanWithPermission(!isScanning)
        }

        myRecycler = findViewById(R.id.deviceList)
        binding.deviceList.layoutManager = LinearLayoutManager(this)
        adapter = BLEAdapter(arrayListOf()){
            val intent = Intent(this, BLEDeviceActivity::class.java)
            intent.putExtra(DEVICE_KEY, it)
            startActivity(intent)
        }
        binding.deviceList.adapter = adapter

        //binding.deviceList.layoutManager = LinearLayoutManager(applicationContext)
        //adapter = BLEAdapter(arrayListOf(), {})


    }



    private val scanCallBack = object : ScanCallback() {
        @SuppressLint("MissingPermission", "NotifyDataSetChanged")
        override fun onScanResult(callbackType: Int, result: ScanResult?) {
            Log.d("BLEScanActivity", "result: ${result?.device?.address},name: ${result?.device?.name} , rssi : ${result?.rssi}")
            if (result != null) {
                adapter?.addElement(result)
                adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun startLeScanWithPermission(enable: Boolean) {
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startLeScanBLE(enable)
        } else {
            ActivityCompat.requestPermissions(
                this, arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                    /*Manifest.permission.BLUETOOTH_CONNECT,
                    Manifest.permission.BLUETOOTH_SCAN*/
                ), ALL_PERMISSION_REQUEST_CODE)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLeScanBLE(enable: Boolean) {
        bluetoothAdapter?.bluetoothLeScanner?.apply {
            if (enable) {
                isScanning = true
                startScan(scanCallBack)
            } else {
                isScanning = false
                stopScan(scanCallBack)
            }
            handlePlayPause()
        }
    }

    private fun askBluetoothPermission() {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.BLUETOOTH_CONNECT
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            startActivityForResult(enableBtIntent, ENABLE_BLUETOOTH_REQUEST_CODE)
        }
    }

    private fun displayBLEUnavailable() {
        binding.btnPlay.isVisible = true
    }

    private fun handlePlayPause() {
        if (isScanning) {
            binding.titleBle.text = "Scan en cours..."
            binding.progressBar.isIndeterminate = true
            binding.btnPlay.setImageResource(R.drawable.ic_stop)
        } else {
            binding.titleBle.text = "Lancer scan BLE"
            binding.progressBar.isIndeterminate = false
            binding.btnPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24)
        }

    }

    private val bluetoothAdapter: BluetoothAdapter? by lazy {
        val bluetoothManager = getSystemService(Context.BLUETOOTH_SERVICE) as BluetoothManager
        bluetoothManager.adapter
    }

    override fun onStop() {
        super.onStop()
        startLeScanWithPermission(false)
    }

    companion object{
        const val DEVICE_KEY = "device"
    }


    }


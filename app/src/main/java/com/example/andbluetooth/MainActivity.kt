package com.example.andbluetooth

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ToggleButton
import androidx.core.app.ActivityCompat

class MainActivity : AppCompatActivity() {

    private val REQUEST_ENABLE_BT=1
    private var bluetoothAdapter: BluetoothAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val bleOnOffBtn:ToggleButton = findViewById(R.id.ble_on_off_btn)
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()

        if(bluetoothAdapter != null) {
            //Device doesn't support Bluetooth
            if(bluetoothAdapter?.isEnabled==false) {
                bleOnOffBtn.isChecked = true
            } else {
                bleOnOffBtn.isChecked = false
            }
        }
        bleOnOffBtn.setOnCheckedChangeListener{ _, isChecked -> bluetoothOnOff()}
    }

    private fun bluetoothOnOff() {
        if(bluetoothAdapter == null) {
            Log.d("bluetoothAdapter", "Device doesn't support Bluetooth")
        } else {
            if (bluetoothAdapter?.isEnabled == false) {
                val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
                if (ActivityCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return
                }
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT)
            } else {  //블루투스 켜져있으면 비활성화
                bluetoothAdapter?.disable()
            }
        }
    }
}
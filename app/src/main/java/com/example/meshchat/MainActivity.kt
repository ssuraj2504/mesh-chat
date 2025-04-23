package com.example.meshchat

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meshchat.databinding.ActivityMainBinding
import com.example.meshchat.model.Message
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*



class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val bluetoothAdapter: BluetoothAdapter? = BluetoothAdapter.getDefaultAdapter()
    private var connectedSocket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null
    private val uuid: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB")
    private val messageAdapter = MessageAdapter(mutableListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter = messageAdapter

        checkPermissions()

        binding.btnSend.setOnClickListener {
            val message = binding.etMessage.text.toString()
            if (message.isNotEmpty()) {
                sendMessage(message)
                messageAdapter.addMessage(Message("Me", message))
                binding.etMessage.text.clear()
            }
        }
    }

    private fun Message(id: String, text: String): Message {
        TODO("Not yet implemented")
    }

    private fun checkPermissions() {
        val permissions = arrayOf(
            Manifest.permission.BLUETOOTH_CONNECT,
            Manifest.permission.BLUETOOTH_SCAN,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        ActivityCompat.requestPermissions(this, permissions, 1)
    }

    private fun hasBluetoothPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1 && grantResults.all { it == PackageManager.PERMISSION_GRANTED }) {
            connectToFirstDevice()
        } else {
            Toast.makeText(this, "Permissions not granted", Toast.LENGTH_SHORT).show()
        }
    }

    private fun connectToFirstDevice() {
        if (!hasBluetoothPermissions()) {
            Toast.makeText(this, "Bluetooth permissions not granted", Toast.LENGTH_SHORT).show()
            return
        }

        try {
            val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter?.bondedDevices
            pairedDevices?.firstOrNull()?.let { device ->
                Thread {
                    try {
                        val socket = device.createRfcommSocketToServiceRecord(uuid)
                        socket.connect()
                        connectedSocket = socket
                        outputStream = socket.outputStream
                        runOnUiThread {
                            Toast.makeText(this, "Connected to ${device.name}", Toast.LENGTH_SHORT).show()
                            binding.tvStatus.text = "Connected to ${device.name}"
                        }
                        listenForMessages(socket.inputStream)
                    } catch (e: IOException) {
                        e.printStackTrace()
                        runOnUiThread {
                            Toast.makeText(this, "Connection failed: ${e.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
                }.start()
            } ?: run {
                Toast.makeText(this, "No paired Bluetooth devices found", Toast.LENGTH_SHORT).show()
            }
        } catch (e: SecurityException) {
            e.printStackTrace()
            Toast.makeText(this, "Security exception: Missing permissions", Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendMessage(message: String) {
        try {
            outputStream?.write(message.toByteArray())
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Failed to send message", Toast.LENGTH_SHORT).show()
        }
    }

    private fun listenForMessages(inputStream: InputStream) {
        val buffer = ByteArray(1024)
        var bytes: Int
        while (true) {
            try {
                bytes = inputStream.read(buffer)
                val message = String(buffer, 0, bytes)
                runOnUiThread {
                    messageAdapter.addMessage(Message("Peer", message))
                }
            } catch (e: IOException) {
                e.printStackTrace()
                break
            }
        }
    }
}

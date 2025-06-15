package com.example.meshchat

import android.Manifest
import android.bluetooth.*
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.meshchat.databinding.ActivityMainBinding
import com.example.meshchat.model.Message
import com.example.meshchat.adapter.MessageAdapter
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*
import android.annotation.SuppressLint

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
            val text = binding.etMessage.text.toString()
            if (text.isNotEmpty()) {
                sendMessage(text)
                messageAdapter.addMessage(Message(sender = "Me", text = text, timestamp = System.currentTimeMillis()))
                binding.etMessage.text.clear()
            }
        }

        binding.btnHost.setOnClickListener {
            startBluetoothServer()
        }

        binding.btnClient.setOnClickListener {
            connectToFirstDevice()
        }

        makeDeviceDiscoverable()
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
                ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_SCAN) == PackageManager.PERMISSION_GRANTED
    }

    @SuppressLint("MissingPermission")
    private fun makeDeviceDiscoverable() {
        val intent = Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE)
        intent.putExtra(BluetoothAdapter.EXTRA_DISCOVERABLE_DURATION, 300)
        startActivity(intent)
    }

    @SuppressLint("MissingPermission")
    private fun startBluetoothServer() {
        val serverSocket: BluetoothServerSocket? = try {
            bluetoothAdapter?.listenUsingRfcommWithServiceRecord("MeshChat", uuid)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }

        Thread {
            try {
                val socket = serverSocket?.accept()
                connectedSocket = socket
                outputStream = socket?.outputStream

                runOnUiThread {
                    binding.tvStatus.text = "Client connected"
                    Toast.makeText(this, "Client connected!", Toast.LENGTH_SHORT).show()
                }

                socket?.inputStream?.let { listenForMessages(it) }

            } catch (e: IOException) {
                e.printStackTrace()
                runOnUiThread {
                    Toast.makeText(this, "Server accept failed", Toast.LENGTH_SHORT).show()
                }
            }
        }.start()
    }

    private fun connectToFirstDevice() {
        if (!hasBluetoothPermissions()) {
            Toast.makeText(this, "Bluetooth permissions not granted", Toast.LENGTH_SHORT).show()
            return
        }

        val pairedDevices = bluetoothAdapter?.bondedDevices
        pairedDevices?.firstOrNull()?.let { device ->
            Thread {
                try {
                    val socket = device.createRfcommSocketToServiceRecord(uuid)
                    bluetoothAdapter?.cancelDiscovery()

                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.BLUETOOTH_CONNECT) != PackageManager.PERMISSION_GRANTED) {
                        runOnUiThread {
                            Toast.makeText(this, "Missing BLUETOOTH_CONNECT permission", Toast.LENGTH_SHORT).show()
                        }
                        return@Thread
                    }

                    socket.connect()
                    connectedSocket = socket
                    outputStream = socket.outputStream

                    runOnUiThread {
                        binding.tvStatus.text = "Connected to ${device.name}"
                        Toast.makeText(this, "Connected to ${device.name}", Toast.LENGTH_SHORT).show()
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
                    messageAdapter.addMessage(
                        Message(sender = "Peer", text = message, timestamp = System.currentTimeMillis())
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
                break
            }
        }
    }
}

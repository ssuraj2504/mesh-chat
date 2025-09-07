package com.example.meshchat;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000f\u001a\u00020\u0010H\u0002J\b\u0010\u0011\u001a\u00020\u0010H\u0002J\b\u0010\u0012\u001a\u00020\u0013H\u0002J\u0010\u0010\u0014\u001a\u00020\u00102\u0006\u0010\u0015\u001a\u00020\u0016H\u0002J\b\u0010\u0017\u001a\u00020\u0010H\u0003J\u0012\u0010\u0018\u001a\u00020\u00102\b\u0010\u0019\u001a\u0004\u0018\u00010\u001aH\u0014J\u0010\u0010\u001b\u001a\u00020\u00102\u0006\u0010\u001c\u001a\u00020\u001dH\u0002J\b\u0010\u001e\u001a\u00020\u0010H\u0003R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u000b\u001a\u0004\u0018\u00010\fX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\r\u001a\u00020\u000eX\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001f"}, d2 = {"Lcom/example/meshchat/MainActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/example/meshchat/databinding/ActivityMainBinding;", "bluetoothAdapter", "Landroid/bluetooth/BluetoothAdapter;", "connectedSocket", "Landroid/bluetooth/BluetoothSocket;", "messageAdapter", "Lcom/example/meshchat/adapter/MessageAdapter;", "outputStream", "Ljava/io/OutputStream;", "uuid", "Ljava/util/UUID;", "checkPermissions", "", "connectToFirstDevice", "hasBluetoothPermissions", "", "listenForMessages", "inputStream", "Ljava/io/InputStream;", "makeDeviceDiscoverable", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "sendMessage", "message", "", "startBluetoothServer", "app_debug"})
public final class MainActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.example.meshchat.databinding.ActivityMainBinding binding;
    @org.jetbrains.annotations.Nullable
    private final android.bluetooth.BluetoothAdapter bluetoothAdapter = null;
    @org.jetbrains.annotations.Nullable
    private android.bluetooth.BluetoothSocket connectedSocket;
    @org.jetbrains.annotations.Nullable
    private java.io.OutputStream outputStream;
    @org.jetbrains.annotations.NotNull
    private final java.util.UUID uuid = null;
    @org.jetbrains.annotations.NotNull
    private final com.example.meshchat.adapter.MessageAdapter messageAdapter = null;
    
    public MainActivity() {
        super();
    }
    
    @java.lang.Override
    protected void onCreate(@org.jetbrains.annotations.Nullable
    android.os.Bundle savedInstanceState) {
    }
    
    private final void checkPermissions() {
    }
    
    private final boolean hasBluetoothPermissions() {
        return false;
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final void makeDeviceDiscoverable() {
    }
    
    @android.annotation.SuppressLint(value = {"MissingPermission"})
    private final void startBluetoothServer() {
    }
    
    private final void connectToFirstDevice() {
    }
    
    private final void sendMessage(java.lang.String message) {
    }
    
    private final void listenForMessages(java.io.InputStream inputStream) {
    }
}
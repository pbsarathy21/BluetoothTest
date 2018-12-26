package com.ninositsolution.bluetoothtest;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Set;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_ENABLE_BT = 0;
    private static final int REQUEST_DISCOVERABLE_BT = 0;

    TextView textView, pairedDevices;
    Button turn_on, discoverable, turn_off;

    BluetoothAdapter bluetoothAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textView = findViewById(R.id.out);
        pairedDevices = findViewById(R.id.paired_devices);

        turn_on = findViewById(R.id.turn_on);
        turn_off = findViewById(R.id.turn_off);
        discoverable = findViewById(R.id.discoverable);

        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        if (bluetoothAdapter == null)
        {
            textView.append("device not supported");
        }

        checkBluetoothState();
    }

    private void checkBluetoothState()
    {
        if (bluetoothAdapter == null)
        {
            textView.append("Bluetooth not supported");
        }
        else  if (bluetoothAdapter.isEnabled())
        {
            textView.append("bluetooth enabled");

            //Listing paired devices

            pairedDevices.setText("\nPaired devices are ");
            Set<BluetoothDevice> devices = bluetoothAdapter.getBondedDevices();

            for (BluetoothDevice device : devices)
            {
                pairedDevices.append("\n Device "+device.getName()+" , "+device);
            }
        }
        else
        {
                Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enable, REQUEST_ENABLE_BT);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_ENABLE_BT)
        {
            checkBluetoothState();
        }
    }

    public void isOnClicked(View view) {

        if (!bluetoothAdapter.isEnabled())
        {
            Intent enable = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enable, REQUEST_ENABLE_BT);
        }
    }

    public void isDiscoverableClicked(View view) {

        if (!bluetoothAdapter.isDiscovering())
        {
            textView.append("Making your device discoverable");

            Intent discover = new Intent(BluetoothAdapter.ACTION_REQUEST_DISCOVERABLE);
            startActivityForResult(discover, REQUEST_DISCOVERABLE_BT);
        }
    }

    public void isOffClicked(View view) {

        bluetoothAdapter.disable();
        textView.append("Turned off bluetooth");
    }
}

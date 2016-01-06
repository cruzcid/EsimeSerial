package com.example.cruz.esimeserial;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.Set;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class MainActivity extends AppCompatActivity {
    int REQUEST_ENABLE_BT; // esta en onActivityResult
    private BroadcastReceiver mReceiver;
        ImageView onOfImage;
    Button onOffBtn;
    BluetoothAdapter mBluetoothAdapter;
    BluetoothSPP bt ; // Bluetooth SSP Serial Port Profile Akexorcist
    /*
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == BluetoothState.REQUEST_CONNECT_DEVICE) {
            if(resultCode == Activity.RESULT_OK)
                bt.connect(data);
        } else if(requestCode == BluetoothState.REQUEST_ENABLE_BT) {
            if(resultCode == Activity.RESULT_OK) {
                bt.setupService();
                bt.startService(BluetoothState.DEVICE_ANDROID);
                setup();
            } else {
                // Do something if user doesn't choose any device (Pressed back)
            }
        }
    }*/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //bt = new BluetoothSPP(this);
        onOffBtn = (Button) findViewById(R.id.botonOnOffId);
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();

        onOffBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                queryingPairedDevices();
            }
        });
        disponibilidadOfBluetooth();
        bluethoothOn();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mBluetoothAdapter.startDiscovery();//Start asyncronous background discovery of other devices
        //mBluetoothAdapter.cancelDiscovery();//Remember to cancel discoveery afetr conecting to a device
        // Create a BroadcastReceiver for ACTION_FOUND
            mReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                // When discovery finds a device
                if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                    // Get the BluetoothDevice object from the Intent
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    // Add the name and address to an array adapter to show in a ListView
                    //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                }
            }
        };
        // Register the BroadcastReceiver
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        registerReceiver(mReceiver, filter); // Don't forget to unregister during onDestroy

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //Delete register option
        unregisterReceiver(mReceiver);
    }

    public void disponibilidadOfBluetooth(){

        //Check if bluetooth is available
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetooth
            Toast.makeText(getApplicationContext(), "Bluethoot no disponible",
                    Toast.LENGTH_LONG).show();

            //Cierra todas las actividades y vuelve a la actividad principal
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.putExtra("EXIT", true);
            startActivity(intent);
            //Cierra la actividad principal
            if (getIntent().getExtras() != null && getIntent().getExtras().getBoolean("EXIT", false)) {
                finish();
            }
        }

    }
    private void bluethoothOn() {
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.enable();
            /*Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            */
            Toast.makeText(MainActivity.this, "Tu Bluetooth se ha Activado", Toast.LENGTH_SHORT).show();
        }
    }

    private void queryingPairedDevices() {
        Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
        // If there are paired devices
        String output;
        output="";
        if (pairedDevices.size() > 0) {
            // Loop through paired devices

            for (BluetoothDevice device : pairedDevices) {
                // Add the name and address to an array adapter to show in a ListView
                //mArrayAdapter.add(device.getName() + "\n" + device.getAddress());
                output += device.getName() + "\n" + device.getAddress()+"\n";
            }
            onOffBtn.setText(output);
        }
    }


}
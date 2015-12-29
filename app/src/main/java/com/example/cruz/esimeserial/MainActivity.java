package com.example.cruz.esimeserial;

import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.Toast;

import app.akexorcist.bluetotohspp.library.BluetoothSPP;

public class MainActivity extends AppCompatActivity {
    int REQUEST_ENABLE_BT; // esta en onActivityResult
    ImageView onOfImage;

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
        bt = new BluetoothSPP(this);
        disponibilidadOfBluetooth();
    }

    public void disponibilidadOfBluetooth(){
        //Check if bluetooth is available
        if(!bt.isBluetoothAvailable()) {
            // any command for bluetooth is not available
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
        Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
        startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
    }

    private void doesSupportBluethooth() {
        if (mBluetoothAdapter == null) {
            // Device does not support Bluetoot
            Toast.makeText(getApplicationContext(), "Dispositivo sin soporte de Bluethoot",
                    Toast.LENGTH_LONG).show();
        }
    }


}
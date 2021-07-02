package com.example.dc_motor;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ToneGenerator;
import android.os.Build;
import android.os.Bundle;
import android.os.ParcelUuid;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_spinner_dropdown_item;

public class MainActivity extends AppCompatActivity {
    ImageView imgSetup, imgMenuListdivice, imgBluetoothConnection;
    View layoutSetup, layoutListdevice, layoutSetup2;
    TextView txtBackSetting, txtBackListdevice, txtNameBluetoothConnection, txtBackSetting2, txtNextSetting;

    ProgressBar pgbRefeshListdevice;
    ListView lvListdevice;
    //------------------ main menu---------------------
    EditText edtNameMotor1,edtNameMotor2,edtNameMotor3,edtNameMotor4,edtNameMotor5,edtNameMotor6;
    Button btnOpenMotor1,btnOpenMotor2,btnOpenMotor3,btnOpenMotor4,btnOpenMotor5,btnOpenMotor6;
    Button btnStopMotor1,btnStopMotor2,btnStopMotor3,btnStopMotor4,btnStopMotor5,btnStopMotor6;
    Button btnCloseMotor1,btnCloseMotor2,btnCloseMotor3,btnCloseMotor4,btnCloseMotor5,btnCloseMotor6;
    Button btnSaveNameMotor;
    SwitchCompat swStepCheckDistant, swTestDistantMotor;
    EditText edtStepCheckDistant;

    //------------------setup-------------------------
    TextView txtMotor1,txtMotor2,txtMotor3,txtMotor4,txtMotor5,txtMotor6;
    TextView txtVoltage1,txtVoltage2,txtVoltage3,txtVoltage4,txtVoltage5,txtVoltage6;
    TextView txtCurrent1,txtCurrent2,txtCurrent3,txtCurrent4,txtCurrent5,txtCurrent6;
    EditText edtSetDistantMotor1,edtSetDistantMotor2,edtSetDistantMotor3,edtSetDistantMotor4,edtSetDistantMotor5,edtSetDistantMotor6;
    Button btnRunDistantMotor1,btnRunDistantMotor2,btnRunDistantMotor3,btnRunDistantMotor4,btnRunDistantMotor5,btnRunDistantMotor6;
    Button btnSetDistantMotor1,btnSetDistantMotor2,btnSetDistantMotor3,btnSetDistantMotor4,btnSetDistantMotor5,btnSetDistantMotor6;

    //----------------setup 2-------------------------
    Spinner ST2spinerName;
    TextView ST2txtMotor1,ST2txtMotor2,ST2txtMotor3,ST2txtMotor4,ST2txtMotor5,ST2txtMotor6;
    TextView ST2txtSlowPulse1,ST2txtSlowPulse2,ST2txtSlowPulse3,ST2txtSlowPulse4,ST2txtSlowPulse5,ST2txtSlowPulse6;
    TextView ST2txtMaxCurrent1,ST2txtMaxCurrent2,ST2txtMaxCurrent3,ST2txtMaxCurrent4,ST2txtMaxCurrent5,ST2txtMaxCurrent6;
    TextView ST2txtReturn1,ST2txtReturn2,ST2txtReturn3,ST2txtReturn4,ST2txtReturn5,ST2txtReturn6;
    EditText ST2edtSlowDistant,ST2edtMaxCurrent,ST2timeReturn;
    Button ST2btnSave;

    ArrayAdapter<String> arrayAdapterListdevice;

    public static int REQUEST_BLUETOOTH = 1;
    public static int REQUEST_DISCOVERABLE_BT = 1;
    private static final UUID MY_UUID_INSECURE =
            UUID.fromString("00001101-0000-1000-8000-00805f9b34fb");

    boolean isSwitchStep = false;

    String TAG = "MainActivity";
    String deviceName;
    private BluetoothDevice mmDevice;
    //private UUID deviceUUID;

    ParcelUuid[] mDeviceUUIDs;
    ConnectedThread mConnectedThread;
    //    private Handler handler;
    Object[] OjectBluetooth;

    //varialble for save name motor
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String nameMotor1,nameMotor2,nameMotor3,nameMotor4,nameMotor5,nameMotor6;
    String name1 = "motor1";
    String name2 = "motor2";
    String name3 = "motor3";
    String name4 = "motor4";
    String name5 = "motor5";
    String name6 = "motor6";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        Anhxa();
        //---------------------------------------------------save name motor--------------------------------------
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        edtNameMotor1.setText(sharedPreferences.getString(name1, ""));
        edtNameMotor2.setText(sharedPreferences.getString(name2, ""));
        edtNameMotor3.setText(sharedPreferences.getString(name3, ""));
        edtNameMotor4.setText(sharedPreferences.getString(name4, ""));
        edtNameMotor5.setText(sharedPreferences.getString(name5, ""));
        edtNameMotor6.setText(sharedPreferences.getString(name6, ""));
        List<String> list = new ArrayList<>();
        list.add(sharedPreferences.getString(name1, ""));
        list.add(sharedPreferences.getString(name2, ""));
        list.add(sharedPreferences.getString(name3, ""));
        list.add(sharedPreferences.getString(name4, ""));
        list.add(sharedPreferences.getString(name5, ""));
        list.add(sharedPreferences.getString(name6, ""));
        //ArrayAdapter spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, list);
        ArrayAdapter spinnerAdapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, list);
        spinnerAdapter.setDropDownViewResource(simple_spinner_dropdown_item);
        ST2spinerName.setAdapter(spinnerAdapter);
        //------------------------------------------------------------------------------------------------------

        //--------------------------------------for bluetooth--------------------------------------------------------
        BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();
        // Phone does not support Bluetooth so let the user know and exit.
        if (BTAdapter == null) {
            new AlertDialog.Builder(this)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
        if (!BTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
        //------------------------------------------------------------------------------------------------------

        swTestDistantMotor.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    swStepCheckDistant.setChecked(false);
                    swStepCheckDistant.setVisibility(View.INVISIBLE);
                }
                else{
                    swStepCheckDistant.setVisibility(View.VISIBLE);
                }
            }
        });

        swStepCheckDistant.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    edtStepCheckDistant.setVisibility(View.VISIBLE);
                }
                else{
                    edtStepCheckDistant.setVisibility(View.INVISIBLE);
                }
            }
        });
        //-------------------Main button OPEN------------------------------
        btnOpenMotor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor1\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor1\",\"command\":\"open\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor1\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });
        btnOpenMotor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor2\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor2\",\"command\":\"open\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor2\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });
        btnOpenMotor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor3\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor3\",\"command\":\"open\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor3\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });
        btnOpenMotor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor4\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor4\",\"command\":\"open\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor4\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });
        btnOpenMotor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor5\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor5\",\"command\":\"open\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor5\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });
        btnOpenMotor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor6\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor6\",\"command\":\"open\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor6\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });
        //-------------------Main button STOP------------------------------
        btnStopMotor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"run_no_step\",\"name\":\"motor1\",\"command\":\"stop\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG,data);
                }
            }
        });
        btnStopMotor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"run_no_step\",\"name\":\"motor2\",\"command\":\"stop\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG,data);
                }
            }
        });
        btnStopMotor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"run_no_step\",\"name\":\"motor3\",\"command\":\"stop\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG,data);
                }
            }
        });
        btnStopMotor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"run_no_step\",\"name\":\"motor4\",\"command\":\"stop\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG,data);
                }
            }
        });
        btnStopMotor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"run_no_step\",\"name\":\"motor5\",\"command\":\"stop\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG,data);
                }
            }
        });
        btnStopMotor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"run_no_step\",\"name\":\"motor6\",\"command\":\"stop\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    //Log.d(TAG,data);
                }
            }
        });
        //-------------------Main button CLOSE------------------------------
        btnCloseMotor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor1\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor1\",\"command\":\"close\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor1\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });
        btnCloseMotor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor2\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor2\",\"command\":\"close\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor2\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });
        btnCloseMotor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor3\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor3\",\"command\":\"close\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor3\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });
        btnCloseMotor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor4\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor4\",\"command\":\"close\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor4\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });
        btnCloseMotor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor5\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor5\",\"command\":\"close\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor5\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });
        btnCloseMotor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(swTestDistantMotor.isChecked()){
                        String data = "{\"type\":\"run_test_distant\",\"name\":\"motor6\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else if(swStepCheckDistant.isChecked()){
                        String data = "{\"type\":\"run_with_step\",\"name\":\"motor6\",\"command\":\"close\",\"data\":\"";
                        data += edtStepCheckDistant.getText().toString();
                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }else {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"motor6\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            }
        });

        btnSaveNameMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editor.putString(name1, edtNameMotor1.getText().toString());
                editor.putString(name2, edtNameMotor2.getText().toString());
                editor.putString(name3, edtNameMotor3.getText().toString());
                editor.putString(name4, edtNameMotor4.getText().toString());
                editor.putString(name5, edtNameMotor5.getText().toString());
                editor.putString(name6, edtNameMotor6.getText().toString());
                editor.commit();
            }
        });

        //-------------------------------------------------------------------
        imgSetup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtMotor1.setText(sharedPreferences.getString(name1, ""));
                txtMotor2.setText(sharedPreferences.getString(name2, ""));
                txtMotor3.setText(sharedPreferences.getString(name3, ""));
                txtMotor4.setText(sharedPreferences.getString(name4, ""));
                txtMotor5.setText(sharedPreferences.getString(name5, ""));
                txtMotor6.setText(sharedPreferences.getString(name6, ""));

                btnRunDistantMotor1.setText("RUN");
                btnRunDistantMotor2.setText("RUN");
                btnRunDistantMotor3.setText("RUN");
                btnRunDistantMotor4.setText("RUN");
                btnRunDistantMotor5.setText("RUN");
                btnRunDistantMotor6.setText("RUN");

//                layoutSetup.setVisibility(View.VISIBLE);
                if (mmDevice !=null && isConnected(mmDevice)) {
//                    String data = "{\"type\":\"get_status\",\"name\":\"\"}";
//                    byte[] bytes = data.getBytes(Charset.defaultCharset());
//                    mConnectedThread.write(bytes);
                    layoutSetup.setVisibility(View.VISIBLE);
                }
            }
        });

        txtBackSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //btnControl.setVisibility(View.VISIBLE);
                layoutSetup.setVisibility(View.GONE);
                layoutSetup2.setVisibility(View.GONE);

            }
        });
        txtBackSetting2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSetup.setVisibility(View.VISIBLE);
                layoutSetup2.setVisibility(View.GONE);
            }
        });
        txtNextSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ST2txtMotor1.setText(sharedPreferences.getString(name1, ""));
                ST2txtMotor2.setText(sharedPreferences.getString(name2, ""));
                ST2txtMotor3.setText(sharedPreferences.getString(name3, ""));
                ST2txtMotor4.setText(sharedPreferences.getString(name4, ""));
                ST2txtMotor5.setText(sharedPreferences.getString(name5, ""));
                ST2txtMotor6.setText(sharedPreferences.getString(name6, ""));
                layoutSetup.setVisibility(View.GONE);
                layoutSetup2.setVisibility(View.VISIBLE);

            }
        });
        //------------------------------------------------------------------

        //---------------------------Run with save distant-------------------------------------------------
        btnRunDistantMotor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(btnRunDistantMotor1.getText().toString().equals("RUN")){
                        btnRunDistantMotor1.setText("STOP");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor1\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        btnRunDistantMotor1.setText("RUN");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor1\",\"command\":\"stop\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnRunDistantMotor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(btnRunDistantMotor2.getText().toString().equals("RUN")){
                        btnRunDistantMotor2.setText("STOP");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor2\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        btnRunDistantMotor2.setText("RUN");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor2\",\"command\":\"stop\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnRunDistantMotor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(btnRunDistantMotor3.getText().toString().equals("RUN")){
                        btnRunDistantMotor3.setText("STOP");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor3\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        btnRunDistantMotor3.setText("RUN");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor3\",\"command\":\"stop\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnRunDistantMotor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(btnRunDistantMotor4.getText().toString().equals("RUN")){
                        btnRunDistantMotor4.setText("STOP");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor4\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        btnRunDistantMotor4.setText("RUN");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor4\",\"command\":\"stop\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnRunDistantMotor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(btnRunDistantMotor5.getText().toString().equals("RUN")){
                        btnRunDistantMotor5.setText("STOP");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor5\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        btnRunDistantMotor5.setText("RUN");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor5\",\"command\":\"stop\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        btnRunDistantMotor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    if(btnRunDistantMotor6.getText().toString().equals("RUN")){
                        btnRunDistantMotor6.setText("STOP");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor6\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        btnRunDistantMotor6.setText("RUN");
                        String data = "{\"type\":\"run_save_distant\",\"name\":\"motor6\",\"command\":\"stop\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        //------------------------Button save distant with edit text--------------------------
        btnSetDistantMotor1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice) && !btnSetDistantMotor1.getText().toString().equals("")) {
                    String data = "{\"type\":\"save_distant\",\"name\":\"motor1\",\"data\":\"";
                    data += edtSetDistantMotor1.getText().toString();
                    data += "\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSetDistantMotor2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice) && !btnSetDistantMotor2.getText().toString().equals("")) {
                    String data = "{\"type\":\"save_distant\",\"name\":\"motor2\",\"data\":\"";
                    data += edtSetDistantMotor2.getText().toString();
                    data += "\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSetDistantMotor3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice) && !btnSetDistantMotor3.getText().toString().equals("")) {
                    String data = "{\"type\":\"save_distant\",\"name\":\"motor3\",\"data\":\"";
                    data += edtSetDistantMotor3.getText().toString();
                    data += "\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSetDistantMotor4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice) && !btnSetDistantMotor4.getText().toString().equals("")) {
                    String data = "{\"type\":\"save_distant\",\"name\":\"motor4\",\"data\":\"";
                    data += edtSetDistantMotor4.getText().toString();
                    data += "\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSetDistantMotor5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice) && !btnSetDistantMotor5.getText().toString().equals("")) {
                    String data = "{\"type\":\"save_distant\",\"name\":\"motor5\",\"data\":\"";
                    data += edtSetDistantMotor5.getText().toString();
                    data += "\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnSetDistantMotor6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice) && !btnSetDistantMotor6.getText().toString().equals("")) {
                    String data = "{\"type\":\"save_distant\",\"name\":\"motor6\",\"data\":\"";
                    data += edtSetDistantMotor6.getText().toString();
                    data += "\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //-----------------------SETUP UP 2 SEND BUTTON--------------------------------
        ST2btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mmDevice !=null && isConnected(mmDevice)) {

                    String data = "{\"type\":\"save_data\",\"name\":\"";
                    data += ST2spinerName.getSelectedItemPosition();
                    data += "\",\"max_current\":\"";
                    data += ST2edtMaxCurrent.getText().toString();
                    data += "\",\"time_return\":\"";
                    data += ST2timeReturn.getText().toString();
                    data += "\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
//                    Log.i(TAG, data);
                }
            }
        });

        imgMenuListdivice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutListdevice.setVisibility(View.VISIBLE);
//                imgRefeshListdevice.setVisibility(View.VISIBLE);
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                List<String> s = new ArrayList<String>();
//                s.add("---Thiết bị đã ghép đôi---");
                for(BluetoothDevice bt : pairedDevices){
                    s.add(bt.getName() + "\n" + bt.getAddress());
                }
                OjectBluetooth = pairedDevices.toArray();
//                s.add("---Thiết bị hiện có---");
                arrayAdapterListdevice = new ArrayAdapter<String>(
                        MainActivity.this,
                        simple_list_item_1,
                        s );
                lvListdevice.setAdapter(arrayAdapterListdevice);
            }
        });
        txtBackListdevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutListdevice.setVisibility(View.INVISIBLE);
                pgbRefeshListdevice.setVisibility(View.INVISIBLE);
            }
        });
        lvListdevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.i(TAG, arrayAdapterListdevice.getItem(i));

                pgbRefeshListdevice.setVisibility(View.VISIBLE);
                BluetoothDevice bluetoothDeviceconnect = (BluetoothDevice)OjectBluetooth[i];

                deviceName = bluetoothDeviceconnect.getName();
                String deviceAdress = bluetoothDeviceconnect.getAddress();

                Log.d(TAG, "onItemClick: deviceName = " + deviceName);
                Log.d(TAG, "onItemClick: deviceAdress = " + deviceAdress);
                Log.d(TAG, "Trying to Pair with " + deviceName);
                bluetoothDeviceconnect.createBond();

                mDeviceUUIDs = bluetoothDeviceconnect.getUuids();


                Log.d(TAG, "Trying to create UUID: " + deviceName);

                for (ParcelUuid uuid: mDeviceUUIDs) {
                    Log.d(TAG, "UUID: " + uuid.getUuid().toString());
                }


//                ParcelUuid uuidExtra Intent intent = null;
//                intent.getParcelableExtra("android.bluetooth.device.extra.UUID");
//                UUID uuid = mDeviceUUIDs.getUuid();


                ConnectThread connect = new ConnectThread(bluetoothDeviceconnect,MY_UUID_INSECURE);
                connect.start();
            }
        });

    }

    public void Anhxa(){

        imgSetup = findViewById(R.id.imgSetup);
        txtBackSetting = findViewById(R.id.txtBackSetting);
        txtBackSetting2 = findViewById(R.id.txtBackSetting2);
        txtNextSetting = findViewById(R.id.txtNextSetting);

        layoutSetup = findViewById(R.id.layoutSetup);
        layoutSetup2 = findViewById(R.id.layoutSetup2);
        layoutListdevice = findViewById(R.id.layoutListdevice);
        txtBackListdevice = findViewById(R.id.txtBackListDevice);
        pgbRefeshListdevice = findViewById(R.id.pgbListdevice);
        imgMenuListdivice = findViewById(R.id.imgMenuListDevice);
        txtBackListdevice = findViewById(R.id.txtBackListDevice);
        lvListdevice = findViewById(R.id.lvListdevice);
        imgBluetoothConnection = findViewById(R.id.imgBluetoothConnection);
        txtNameBluetoothConnection = findViewById(R.id.txtNameBluetoothConnection);

        edtNameMotor1 = findViewById(R.id.edtNameMotor1);
        edtNameMotor2 = findViewById(R.id.edtNameMotor2);
        edtNameMotor3 = findViewById(R.id.edtNameMotor3);
        edtNameMotor4 = findViewById(R.id.edtNameMotor4);
        edtNameMotor5 = findViewById(R.id.edtNameMotor5);
        edtNameMotor6 = findViewById(R.id.edtNameMotor6);

        btnOpenMotor1 = findViewById(R.id.btnOpenMotor1);
        btnOpenMotor2 = findViewById(R.id.btnOpenMotor2);
        btnOpenMotor3 = findViewById(R.id.btnOpenMotor3);
        btnOpenMotor4 = findViewById(R.id.btnOpenMotor4);
        btnOpenMotor5 = findViewById(R.id.btnOpenMotor5);
        btnOpenMotor6 = findViewById(R.id.btnOpenMotor6);

        btnCloseMotor1 = findViewById(R.id.btnCloseMotor1);
        btnCloseMotor2 = findViewById(R.id.btnCloseMotor2);
        btnCloseMotor3 = findViewById(R.id.btnCloseMotor3);
        btnCloseMotor4 = findViewById(R.id.btnCloseMotor4);
        btnCloseMotor5 = findViewById(R.id.btnCloseMotor5);
        btnCloseMotor6 = findViewById(R.id.btnCloseMotor6);

        btnStopMotor1 = findViewById(R.id.btnStopMotor1);
        btnStopMotor2 = findViewById(R.id.btnStopMotor2);
        btnStopMotor3 = findViewById(R.id.btnStopMotor3);
        btnStopMotor4 = findViewById(R.id.btnStopMotor4);
        btnStopMotor5 = findViewById(R.id.btnStopMotor5);
        btnStopMotor6 = findViewById(R.id.btnStopMotor6);

        btnSaveNameMotor = findViewById(R.id.btnSaveNameMotor);
        swStepCheckDistant = findViewById(R.id.swStepCheckDistant);
        swTestDistantMotor = findViewById(R.id.swTestDistantMotor);
        edtStepCheckDistant = findViewById(R.id.edtStepCheckDistant);


        //-----------------setup------------------------
        txtMotor1 = findViewById(R.id.txtMotor1);
        txtMotor2 = findViewById(R.id.txtMotor2);
        txtMotor3 = findViewById(R.id.txtMotor3);
        txtMotor4 = findViewById(R.id.txtMotor4);
        txtMotor5 = findViewById(R.id.txtMotor5);
        txtMotor6 = findViewById(R.id.txtMotor6);

        txtVoltage1 = findViewById(R.id.txtVoltage1);
        txtVoltage2 = findViewById(R.id.txtVoltage2);
        txtVoltage3 = findViewById(R.id.txtVoltage3);
        txtVoltage4 = findViewById(R.id.txtVoltage4);
        txtVoltage5 = findViewById(R.id.txtVoltage5);
        txtVoltage6 = findViewById(R.id.txtVoltage6);

        txtCurrent1 = findViewById(R.id.txtCurrent1);
        txtCurrent2 = findViewById(R.id.txtCurrent2);
        txtCurrent3 = findViewById(R.id.txtCurrent3);
        txtCurrent4 = findViewById(R.id.txtCurrent4);
        txtCurrent5 = findViewById(R.id.txtCurrent5);
        txtCurrent6 = findViewById(R.id.txtCurrent6);

        edtSetDistantMotor1 = findViewById(R.id.edtSetDistantMotor1);
        edtSetDistantMotor2 = findViewById(R.id.edtSetDistantMotor2);
        edtSetDistantMotor3 = findViewById(R.id.edtSetDistantMotor3);
        edtSetDistantMotor4 = findViewById(R.id.edtSetDistantMotor4);
        edtSetDistantMotor5 = findViewById(R.id.edtSetDistantMotor5);
        edtSetDistantMotor6 = findViewById(R.id.edtSetDistantMotor6);

        btnRunDistantMotor1 = findViewById(R.id.btnRunDistantMotor1);
        btnRunDistantMotor2 = findViewById(R.id.btnRunDistantMotor2);
        btnRunDistantMotor3 = findViewById(R.id.btnRunDistantMotor3);
        btnRunDistantMotor4 = findViewById(R.id.btnRunDistantMotor4);
        btnRunDistantMotor5 = findViewById(R.id.btnRunDistantMotor5);
        btnRunDistantMotor6 = findViewById(R.id.btnRunDistantMotor6);

        btnSetDistantMotor1 = findViewById(R.id.btnSetDistantMotor1);
        btnSetDistantMotor2 = findViewById(R.id.btnSetDistantMotor2);
        btnSetDistantMotor3 = findViewById(R.id.btnSetDistantMotor3);
        btnSetDistantMotor4 = findViewById(R.id.btnSetDistantMotor4);
        btnSetDistantMotor5 = findViewById(R.id.btnSetDistantMotor5);
        btnSetDistantMotor6 = findViewById(R.id.btnSetDistantMotor6);

        //----------------------Setup 2-------------------------------
        ST2spinerName = findViewById(R.id.ST2spinerName);

        ST2txtMotor1 = findViewById(R.id.ST2txtMotor1);
        ST2txtMotor2 = findViewById(R.id.ST2txtMotor2);
        ST2txtMotor3 = findViewById(R.id.ST2txtMotor3);
        ST2txtMotor4 = findViewById(R.id.ST2txtMotor4);
        ST2txtMotor5 = findViewById(R.id.ST2txtMotor5);
        ST2txtMotor6 = findViewById(R.id.ST2txtMotor6);

        ST2txtSlowPulse1 = findViewById(R.id.ST2txtSlowPulse1);
        ST2txtSlowPulse2 = findViewById(R.id.ST2txtSlowPulse2);
        ST2txtSlowPulse3 = findViewById(R.id.ST2txtSlowPulse3);
        ST2txtSlowPulse4 = findViewById(R.id.ST2txtSlowPulse4);
        ST2txtSlowPulse5 = findViewById(R.id.ST2txtSlowPulse5);
        ST2txtSlowPulse6 = findViewById(R.id.ST2txtSlowPulse6);

        ST2txtMaxCurrent1 = findViewById(R.id.ST2txtMaxCurrent1);
        ST2txtMaxCurrent2 = findViewById(R.id.ST2txtMaxCurrent2);
        ST2txtMaxCurrent3 = findViewById(R.id.ST2txtMaxCurrent3);
        ST2txtMaxCurrent4 = findViewById(R.id.ST2txtMaxCurrent4);
        ST2txtMaxCurrent5 = findViewById(R.id.ST2txtMaxCurrent5);
        ST2txtMaxCurrent6 = findViewById(R.id.ST2txtMaxCurrent6);

        ST2txtReturn1 = findViewById(R.id.ST2txtReturn1);
        ST2txtReturn2 = findViewById(R.id.ST2txtReturn2);
        ST2txtReturn3 = findViewById(R.id.ST2txtReturn3);
        ST2txtReturn4 = findViewById(R.id.ST2txtReturn4);
        ST2txtReturn5 = findViewById(R.id.ST2txtReturn5);
        ST2txtReturn6 = findViewById(R.id.ST2txtReturn6);

        ST2edtSlowDistant = findViewById(R.id.ST2edtSlowDistant);
        ST2edtMaxCurrent = findViewById(R.id.ST2edtMaxCurrent);
        ST2timeReturn = findViewById(R.id.ST2timeReturn);
        ST2btnSave = findViewById(R.id.ST2btnSave);

    }






    public static boolean isBluetoothHeadsetConnected() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        return (mBluetoothAdapter != null && mBluetoothAdapter.isEnabled()
                && mBluetoothAdapter.getProfileConnectionState(BluetoothHeadset.HEADSET) == BluetoothHeadset.STATE_CONNECTED);
    }
    public static boolean isConnected(BluetoothDevice device) {
        try {
            Method m = device.getClass().getMethod("isConnected", (Class[]) null);
            boolean connected = (boolean) m.invoke(device, (Object[]) null);
            return connected;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
    private class ConnectThread extends Thread {
        private BluetoothSocket mmSocket;

        public ConnectThread(BluetoothDevice device, UUID uuid) {
            Log.d(TAG, "ConnectThread: started.");
            mmDevice = device;
            //deviceUUID = uuid;
        }

        public void run(){
            BluetoothSocket tmp = null;
            Log.d(TAG, "RUN mConnectThread ");

            // Get a BluetoothSocket for a connection with the
            // given BluetoothDevice
            try {
                Log.d(TAG, "ConnectThread: Trying to create InsecureRfcommSocket using UUID: "
                        + MY_UUID_INSECURE );
                tmp = mmDevice.createRfcommSocketToServiceRecord(MY_UUID_INSECURE);
            } catch (IOException e) {
                Log.e(TAG, "ConnectThread: Could not create InsecureRfcommSocket " + e.getMessage());
            }

            mmSocket = tmp;

            // Make a connection to the BluetoothSocket

            try {
                // This is a blocking call and will only return on a
                // successful connection or an exception
                mmSocket.connect();

            } catch (IOException e) {
                // Close the socket
                try {
                    mmSocket.close();
                    Log.d(TAG, "run: Closed Socket.");
                } catch (IOException e1) {
                    Log.e(TAG, "mConnectThread: run: Unable to close connection in socket " + e1.getMessage());
                }
                Log.d(TAG, "run: ConnectThread: Could not connect to UUID: " + MY_UUID_INSECURE );
            }

            //will talk about this in the 3rd video
            connected(mmSocket);
        }
        public void cancel() {
            try {
                Log.d(TAG, "cancel: Closing Client Socket.");
                mmSocket.close();
            } catch (IOException e) {
                Log.e(TAG, "cancel: close() of mmSocket in Connectthread failed. " + e.getMessage());
            }
        }
    }
    private void connected(BluetoothSocket mmSocket) {
        Log.d(TAG, "connected: Starting.");
        pgbRefeshListdevice.setVisibility(View.INVISIBLE);

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();

//        byte[] bytes = "abcd".getBytes(Charset.defaultCharset());
//        mConnectedThread.write(bytes);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Stuff that updates the UI

                layoutListdevice.setVisibility(View.INVISIBLE);
                pgbRefeshListdevice.setVisibility(View.INVISIBLE);

                imgBluetoothConnection.setBackgroundResource(R.mipmap.ic_bluetooth_connected);
                txtNameBluetoothConnection.setText(deviceName);

            }
        });





    }
    private class ConnectedThread extends Thread {
        private final BluetoothSocket mmSocket;
        private final InputStream mmInStream;
        private final OutputStream mmOutStream;

        public ConnectedThread(BluetoothSocket socket) {
            Log.d(TAG, "ConnectedThread: Starting.");

            mmSocket = socket;
            InputStream tmpIn = null;
            OutputStream tmpOut = null;


            try {
                tmpIn = mmSocket.getInputStream();
                tmpOut = mmSocket.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }

            mmInStream = tmpIn;
            mmOutStream = tmpOut;
        }

        public void run() {
            byte[] buffer = new byte[1024];  // buffer store for the stream

            int bytes; // bytes returned from read()
            final byte delimiter = 10; //This is the ASCII code for a newline character

            byte[] readBuffer = new byte[1024];;
            int readBufferPosition = 0;
            String incomingMessage = "";
            String data[] = new String[24];
            // Keep listening to the InputStream until an exception occurs
            while (true) {

                // Read from the InputStream
                try {

                    bytes = mmInStream.read(buffer);

                    incomingMessage += new String(buffer, 0, bytes);
                    if(incomingMessage.contains("}")){
                        Log.d(TAG, "InputStream: " + incomingMessage);
                        JSONObject reader = new JSONObject(incomingMessage);
                        //voltage
                        data[0] = reader.getString("11");
                        data[1] = reader.getString("21");
                        data[2] = reader.getString("31");
                        data[3] = reader.getString("41");
                        data[4] = reader.getString("51");
                        data[5] = reader.getString("61");
                        //current
                        data[6] = reader.getString("12");
                        data[7] = reader.getString("22");
                        data[8] = reader.getString("32");
                        data[9] = reader.getString("42");
                        data[10] = reader.getString("52");
                        data[11] = reader.getString("62");
                        //max current
                        data[12] = reader.getString("13");
                        data[13] = reader.getString("23");
                        data[14] = reader.getString("33");
                        data[15] = reader.getString("43");
                        data[16] = reader.getString("53");
                        data[17] = reader.getString("63");
                        //time return
                        data[18] = reader.getString("14");
                        data[19] = reader.getString("24");
                        data[20] = reader.getString("34");
                        data[21] = reader.getString("44");
                        data[22] = reader.getString("54");
                        data[23] = reader.getString("64");
                        incomingMessage = "";
                    }
                    runOnUiThread(new Runnable() {

                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {
                            //-------
                            txtVoltage1.setText(data[0] + " V");
                            txtVoltage2.setText(data[1] + " V");
                            txtVoltage3.setText(data[2] + " V");
                            txtVoltage4.setText(data[3] + " V");
                            txtVoltage5.setText(data[4] + " V");
                            txtVoltage6.setText(data[5] + " V");
                            //-------
                            txtCurrent1.setText(data[6] + " mA");
                            txtCurrent2.setText(data[7] + " mA");
                            txtCurrent3.setText(data[8] + " mA");
                            txtCurrent4.setText(data[9] + " mA");
                            txtCurrent5.setText(data[10] + " mA");
                            txtCurrent6.setText(data[11] + " mA");
                            //---------
                            ST2txtMaxCurrent1.setText(data[12]);
                            ST2txtMaxCurrent2.setText(data[13]);
                            ST2txtMaxCurrent3.setText(data[14]);
                            ST2txtMaxCurrent4.setText(data[15]);
                            ST2txtMaxCurrent5.setText(data[16]);
                            ST2txtMaxCurrent6.setText(data[17]);
                            //---------
                            ST2txtReturn1.setText(data[18]);
                            ST2txtReturn2.setText(data[19]);
                            ST2txtReturn3.setText(data[20]);
                            ST2txtReturn4.setText(data[21]);
                            ST2txtReturn5.setText(data[22]);
                            ST2txtReturn6.setText(data[23]);
                        }
                    });


                } catch (IOException | JSONException e) {
                    Log.e(TAG, "write: Error reading Input Stream. " + e.getMessage());
//                    Toast.makeText(MainActivity.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();
                    break;
                }
            }
        }

        public void write(byte[] bytes) {
            String text = new String(bytes, Charset.defaultCharset());
            Log.d(TAG, "write: Writing to outputstream: " + text);
            try {
                mmOutStream.write(bytes);
            } catch (IOException e) {
                Log.e(TAG, "write: Error writing to output stream. " + e.getMessage());
            }
        }

        /* Call this from the main activity to shutdown the connection */
        public void cancel() {
            try {
                mmSocket.close();
            } catch (IOException e) {
            }
        }
    }
}
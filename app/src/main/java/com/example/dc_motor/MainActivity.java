package com.example.dc_motor;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothHeadset;
import android.bluetooth.BluetoothSocket;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.ToneGenerator;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.ParcelUuid;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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

//import org.apache.http.HttpResponse;
//import org.apache.http.client.ClientProtocolException;
//import org.apache.http.client.HttpClient;
//import org.apache.http.client.methods.HttpGet;
//import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_spinner_dropdown_item;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

//import okhttp3.Authenticator;
//import okhttp3.Call;
//import okhttp3.Callback;
//import okhttp3.Credentials;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import okhttp3.ResponseBody;
//import okhttp3.Route;

public class MainActivity extends AppCompatActivity {
    int MAX_MOTOR = 9;
    ImageView imgBackground, imgSelectBackground, imgMenuListdivice, imgBluetoothConnection, imgSaveDataSetting, imgGetDataSetting;
    View layoutSetup, layoutListdevice, layoutSetup2, layoutSetupData, layoutSetupStep;
    TextView txtBackSetting, txtBackListdevice, txtNameBluetoothConnection, txtBackSetting2, txtNextSetting;

    ProgressBar pgbRefeshListdevice;
    ListView lvListdevice;
    //------------------ main menu---------------------
    EditText[] edtNameMotor = new EditText[MAX_MOTOR];
    Button[] btnOpenMotor = new Button[MAX_MOTOR];
    Button[] btnStopMotor = new Button[MAX_MOTOR];
    Button[] btnCloseMotor = new Button[MAX_MOTOR];
//    EditText edtNameMotor1,edtNameMotor2,edtNameMotor3,edtNameMotor4,edtNameMotor5,edtNameMotor6,edtNameMotor7,edtNameMotor8,edtNameMotor9;
//    Button btnOpenMotor1,btnOpenMotor2,btnOpenMotor3,btnOpenMotor4,btnOpenMotor5,btnOpenMotor6,btnOpenMotor7,btnOpenMotor8,btnOpenMotor9;
//    Button btnStopMotor1,btnStopMotor2,btnStopMotor3,btnStopMotor4,btnStopMotor5,btnStopMotor6,btnStopMotor7,btnStopMotor8,btnStopMotor9;
//    Button btnCloseMotor1,btnCloseMotor2,btnCloseMotor3,btnCloseMotor4,btnCloseMotor5,btnCloseMotor6,btnCloseMotor7,btnCloseMotor8,btnCloseMotor9;
    Button btnSaveNameMotor;
    SwitchCompat swStepCheckDistant, swTestDistantMotor;
    EditText edtStepCheckDistant;

    //---------------------------------------------------
    TextView[] txtNameMotor = new TextView[MAX_MOTOR];
    TextView[] txtMinMotor = new TextView[MAX_MOTOR];
    TextView[] txtMaxMotor = new TextView[MAX_MOTOR];
    TextView[] edtMinMotor = new TextView[MAX_MOTOR];
    TextView[] edtMaxMotor = new TextView[MAX_MOTOR];
    CheckBox[] checkReverseMotor = new CheckBox[MAX_MOTOR];
    Button[] btnSaveDataMotor = new Button[MAX_MOTOR];
    Button btnSaveDataAllMotor;

    //---------------------------------------------
    TextView[] txtNameOpenMotor = new TextView[MAX_MOTOR];
    Spinner[] spnOpenStep1Motor = new Spinner[MAX_MOTOR];
    Spinner[] spnVoltageMotor = new Spinner[MAX_MOTOR];
    Spinner[] spnOpenStep2Motor = new Spinner[MAX_MOTOR];
    Spinner[] spnOpenStep3Motor = new Spinner[MAX_MOTOR];
    TextView[] txtNameCloseMotor = new TextView[MAX_MOTOR];
    Spinner[] spnCloseStep1Motor = new Spinner[MAX_MOTOR];
    Spinner[] spnCloseStep2Motor = new Spinner[MAX_MOTOR];
    Spinner[] spnCloseStep3Motor = new Spinner[MAX_MOTOR];
    Button btnSaveStep;




    //------------------setup-------------------------
    TextView txtMotor1,txtMotor2,txtMotor3,txtMotor4,txtMotor5,txtMotor6;
    TextView txtVoltage1,txtVoltage2,txtVoltage3,txtVoltage4,txtVoltage5,txtVoltage6;
    TextView[] txtCurrent = new TextView[MAX_MOTOR];
//    TextView txtCurrent1,txtCurrent2,txtCurrent3,txtCurrent4,txtCurrent5,txtCurrent6;
    EditText edtSetDistantMotor1,edtSetDistantMotor2,edtSetDistantMotor3,edtSetDistantMotor4,edtSetDistantMotor5,edtSetDistantMotor6;
    Button btnRunDistantMotor1,btnRunDistantMotor2,btnRunDistantMotor3,btnRunDistantMotor4,btnRunDistantMotor5,btnRunDistantMotor6;
    Button btnSetDistantMotor1,btnSetDistantMotor2,btnSetDistantMotor3,btnSetDistantMotor4,btnSetDistantMotor5,btnSetDistantMotor6;
    ImageView imgRefreshData;
    //----------------setup 2-------------------------
    Spinner ST2spinerName;
    TextView ST2txtMotor1,ST2txtMotor2,ST2txtMotor3,ST2txtMotor4,ST2txtMotor5,ST2txtMotor6;
    TextView ST2txtMaxCurrent1,ST2txtMaxCurrent2,ST2txtMaxCurrent3,ST2txtMaxCurrent4,ST2txtMaxCurrent5,ST2txtMaxCurrent6;
    TextView ST2txtMinCurrent1,ST2txtMinCurrent2,ST2txtMinCurrent3,ST2txtMinCurrent4,ST2txtMinCurrent5,ST2txtMinCurrent6;
    TextView ST2txtReturn1,ST2txtReturn2,ST2txtReturn3,ST2txtReturn4,ST2txtReturn5,ST2txtReturn6;
    EditText ST2edtMinCurrent,ST2edtMaxCurrent,ST2timeReturn;
    Button ST2btnSave;
    ImageView imgRefreshStep;

    ArrayAdapter<String> arrayAdapterListdevice;

    public static int REQUEST_BLUETOOTH = 1;
    public static int REQUEST_DISCOVERABLE_BT = 1;
    public static int REQUEST_CODE_STORAGE_PERMISSION = 1;
    public static int REQUEST_CODE_SELECT_IMAGE = 2;
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
    String[] name = {"Motor1","Motor2","Motor3","Motor4","Motor5","Motor6","Motor7","Motor8","Motor9"};
    String nameUri = "Uri";
    String IMAGES_FOLDER_NAME = "Landing";
    String nameImageBackGround = "background";
    String strIndexName = "index";
    int indexNameImageBackGround = 0;

    String OPEN = "1", CLOSE = "2", STOP = "0";
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        InitLayout();
        LoadDataBegin();
        try {
            getDataFromUrl("http://api.thingspeak.com/channels/1723005/feeds.json?results=2");
//            getDataFromUrl("https://raw.github.com/square/okhttp/master/README.md");
        } catch (IOException e) {
            e.printStackTrace();
        }

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

        //--------------------------------------change background--------------------------------------------------------
        imgSelectBackground.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ContextCompat.checkSelfPermission(
                        getApplicationContext(), Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED){
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                            REQUEST_CODE_STORAGE_PERMISSION);
                } else {
                    selectImage();
                }
            }
        });

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
        for(int i = 0; i < MAX_MOTOR; i++){
            int finalI = i;
            btnOpenMotor[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mmDevice !=null && isConnected(mmDevice)) {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"" +
                                Integer.toString(finalI) +
                                "\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            });
        }
        //-------------------Main button STOP------------------------------
        for(int i = 0; i< MAX_MOTOR; i++){
            int finalI = i;
            btnStopMotor[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mmDevice !=null && isConnected(mmDevice)) {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"" +
                                Integer.toString(finalI) +
                                "\",\"command\":\"stop\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            });
        }
        //-------------------Main button CLOSE------------------------------
        for(int i = 0; i < MAX_MOTOR; i++){
            int finalI = i;
            btnCloseMotor[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mmDevice !=null && isConnected(mmDevice)) {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"" +
                                Integer.toString(finalI) +
                                "\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            });
        }
        //--------------------------------------------------------------------

        //-------------------Setup button Save data------------------------------
        for(int i = 0; i < MAX_MOTOR; i++){
            int finalI = i;
            btnSaveDataMotor[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mmDevice !=null && isConnected(mmDevice)) {
                        String data = "{\"type\":\"save_data\",\"name\":\"" +
                                Integer.toString(finalI) +
                                "\",\"min_current\":\"" +
                                edtMinMotor[finalI].getText().toString() +
                                "\",\"max_current\":\"" +
                                edtMaxMotor[finalI].getText().toString() +
                                "\",\"reverse\":\"";
                        if(checkReverseMotor[finalI].isChecked()){
                            data += "true";
                        }
                        else{
                            data += "false";
                        }

                        data += "\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                }
            });
        }

        btnSaveDataAllMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mmDevice !=null && isConnected(mmDevice)) {
//                    for(int i = 0; i < MAX_MOTOR; i++){
//                        final Handler handler = new Handler();
//                        int finalI = i;
//                        handler.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                // Do something after 50ms
//                                String data = "{\"type\":\"save_data\",\"name\":\"" +
//                                        Integer.toString(finalI) +
//                                        "\",\"min_current\":\"" +
//                                        edtMinMotor[finalI].getText().toString() +
//                                        "\",\"max_current\":\"" +
//                                        edtMaxMotor[finalI].getText().toString() +
//                                        "\",\"reverse\":\"";
//                                if(checkReverseMotor[finalI].isChecked()){
//                                    data += "true";
//                                }
//                                else{
//                                    data += "false";
//                                }
//                                data += "\"}";
//                                byte[] bytes = data.getBytes(Charset.defaultCharset());
//                                mConnectedThread.write(bytes);
//                            }
//                        }, 100);
//                    }

                    String data = "{\"type\":\"data\",\"1\":[";
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(!edtMinMotor[i].getText().toString().equals("")){
                            data += edtMinMotor[i].getText().toString();
                        }
                        else{
                            data += "0";
                        }
                        data += ",";
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(!edtMaxMotor[i].getText().toString().equals("")){
                            data += edtMaxMotor[i].getText().toString();
                        }
                        else{
                            data += "0";
                        }
                        data += ",";
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(checkReverseMotor[i].isChecked()){
                            data += "1";
                        }
                        else{
                            data += "0";
                        }
                        if(i == (MAX_MOTOR - 1)){
                            break;
                        }
                        data += ",";
                    }
                    data += "]}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);

                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //--------------------------------------------------------------------
        imgRefreshData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"request_data\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //--------------------------------------------------------------------
        btnSaveStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"step\",\"1\":[";

                    for(int i = 0; i < MAX_MOTOR; i++){
                        data += String.valueOf(spnOpenStep1Motor[i].getSelectedItemPosition());
                        data += ",";
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        data += String.valueOf(spnOpenStep2Motor[i].getSelectedItemPosition());
                        data += ",";
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        data += String.valueOf(spnOpenStep3Motor[i].getSelectedItemPosition());
                        data += ",";
                    }
                    //---------Close------------------
                    for(int i = 0; i < MAX_MOTOR; i++){
                        data += String.valueOf(spnCloseStep1Motor[i].getSelectedItemPosition());
                        data += ",";
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        data += String.valueOf(spnCloseStep2Motor[i].getSelectedItemPosition());
                        data += ",";
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        data += String.valueOf(spnCloseStep3Motor[i].getSelectedItemPosition());
                        if(i == (MAX_MOTOR - 1)){
                            break;
                        }
                        data += ",";
                    }
                    data += "]}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
            }
        });

        //--------------------------------------------------------------------
        imgRefreshStep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"request_step\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnSaveNameMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for(int i = 0; i < MAX_MOTOR; i++){
                    editor.putString(name[i], edtNameMotor[i].getText().toString());
                    edtNameMotor[i].clearFocus();
//                    edtNameMotor[i].setCursorVisible(false);
                }
                editor.commit();

                for(int i = 0; i < MAX_MOTOR; i++){
                    edtNameMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
                    txtNameMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
                    txtNameOpenMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
                    txtNameCloseMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
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
                    data += "\",\"min_current\":\"";
                    data += ST2edtMinCurrent.getText().toString();
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

    public void InitLayout(){
        imgBackground = findViewById(R.id.imgBackground);
        imgSelectBackground = findViewById(R.id.imgSelectBackground);
        imgSaveDataSetting = findViewById(R.id.imgSaveDataSetting);
        imgGetDataSetting = findViewById(R.id.imgGetDataSetting);

        txtBackSetting = findViewById(R.id.txtBackSetting);
        txtBackSetting2 = findViewById(R.id.txtBackSetting2);
        txtNextSetting = findViewById(R.id.txtNextSetting);

        layoutSetup = findViewById(R.id.layoutSetup);
        layoutSetup2 = findViewById(R.id.layoutSetup2);
        layoutListdevice = findViewById(R.id.layoutListdevice);
        layoutSetupStep = findViewById(R.id.layoutSetupStep);

        txtBackListdevice = findViewById(R.id.txtBackListDevice);
        pgbRefeshListdevice = findViewById(R.id.pgbListdevice);
        imgMenuListdivice = findViewById(R.id.imgMenuListDevice);
        txtBackListdevice = findViewById(R.id.txtBackListDevice);
        lvListdevice = findViewById(R.id.lvListdevice);
        imgBluetoothConnection = findViewById(R.id.imgBluetoothConnection);
        txtNameBluetoothConnection = findViewById(R.id.txtNameBluetoothConnection);

        edtNameMotor[0] = findViewById(R.id.edtNameMotor1);
        edtNameMotor[1] = findViewById(R.id.edtNameMotor2);
        edtNameMotor[2] = findViewById(R.id.edtNameMotor3);
        edtNameMotor[3] = findViewById(R.id.edtNameMotor4);
        edtNameMotor[4] = findViewById(R.id.edtNameMotor5);
        edtNameMotor[5] = findViewById(R.id.edtNameMotor6);
        edtNameMotor[6] = findViewById(R.id.edtNameMotor7);
        edtNameMotor[7] = findViewById(R.id.edtNameMotor8);
        edtNameMotor[8] = findViewById(R.id.edtNameMotor9);

        btnOpenMotor[0] = findViewById(R.id.btnOpenMotor1);
        btnOpenMotor[1] = findViewById(R.id.btnOpenMotor2);
        btnOpenMotor[2] = findViewById(R.id.btnOpenMotor3);
        btnOpenMotor[3] = findViewById(R.id.btnOpenMotor4);
        btnOpenMotor[4] = findViewById(R.id.btnOpenMotor5);
        btnOpenMotor[5] = findViewById(R.id.btnOpenMotor6);
        btnOpenMotor[6] = findViewById(R.id.btnOpenMotor7);
        btnOpenMotor[7] = findViewById(R.id.btnOpenMotor8);
        btnOpenMotor[8] = findViewById(R.id.btnOpenMotor9);

        btnCloseMotor[0] = findViewById(R.id.btnCloseMotor1);
        btnCloseMotor[1] = findViewById(R.id.btnCloseMotor2);
        btnCloseMotor[2] = findViewById(R.id.btnCloseMotor3);
        btnCloseMotor[3] = findViewById(R.id.btnCloseMotor4);
        btnCloseMotor[4] = findViewById(R.id.btnCloseMotor5);
        btnCloseMotor[5] = findViewById(R.id.btnCloseMotor6);
        btnCloseMotor[6] = findViewById(R.id.btnCloseMotor7);
        btnCloseMotor[7] = findViewById(R.id.btnCloseMotor8);
        btnCloseMotor[8] = findViewById(R.id.btnCloseMotor9);

        btnStopMotor[0] = findViewById(R.id.btnStopMotor1);
        btnStopMotor[1] = findViewById(R.id.btnStopMotor2);
        btnStopMotor[2] = findViewById(R.id.btnStopMotor3);
        btnStopMotor[3] = findViewById(R.id.btnStopMotor4);
        btnStopMotor[4] = findViewById(R.id.btnStopMotor5);
        btnStopMotor[5] = findViewById(R.id.btnStopMotor6);
        btnStopMotor[6] = findViewById(R.id.btnStopMotor7);
        btnStopMotor[7] = findViewById(R.id.btnStopMotor8);
        btnStopMotor[8] = findViewById(R.id.btnStopMotor9);

        btnSaveNameMotor = findViewById(R.id.btnSaveNameMotor);
        swStepCheckDistant = findViewById(R.id.swStepCheckDistant);
        swTestDistantMotor = findViewById(R.id.swTestDistantMotor);
        edtStepCheckDistant = findViewById(R.id.edtStepCheckDistant);

        txtNameMotor[0] = findViewById(R.id.txtNameMotor1);
        txtNameMotor[1] = findViewById(R.id.txtNameMotor2);
        txtNameMotor[2] = findViewById(R.id.txtNameMotor3);
        txtNameMotor[3] = findViewById(R.id.txtNameMotor4);
        txtNameMotor[4] = findViewById(R.id.txtNameMotor5);
        txtNameMotor[5] = findViewById(R.id.txtNameMotor6);
        txtNameMotor[6] = findViewById(R.id.txtNameMotor7);
        txtNameMotor[7] = findViewById(R.id.txtNameMotor8);
        txtNameMotor[8] = findViewById(R.id.txtNameMotor9);

        txtMinMotor[0] = findViewById(R.id.txtMinMotor1);
        txtMinMotor[1] = findViewById(R.id.txtMinMotor2);
        txtMinMotor[2] = findViewById(R.id.txtMinMotor3);
        txtMinMotor[3] = findViewById(R.id.txtMinMotor4);
        txtMinMotor[4] = findViewById(R.id.txtMinMotor5);
        txtMinMotor[5] = findViewById(R.id.txtMinMotor6);
        txtMinMotor[6] = findViewById(R.id.txtMinMotor7);
        txtMinMotor[7] = findViewById(R.id.txtMinMotor8);
        txtMinMotor[8] = findViewById(R.id.txtMinMotor9);

        txtMaxMotor[0] = findViewById(R.id.txtMaxMotor1);
        txtMaxMotor[1] = findViewById(R.id.txtMaxMotor2);
        txtMaxMotor[2] = findViewById(R.id.txtMaxMotor3);
        txtMaxMotor[3] = findViewById(R.id.txtMaxMotor4);
        txtMaxMotor[4] = findViewById(R.id.txtMaxMotor5);
        txtMaxMotor[5] = findViewById(R.id.txtMaxMotor6);
        txtMaxMotor[6] = findViewById(R.id.txtMaxMotor7);
        txtMaxMotor[7] = findViewById(R.id.txtMaxMotor8);
        txtMaxMotor[8] = findViewById(R.id.txtMaxMotor9);

        edtMinMotor[0] = findViewById(R.id.edtMinMotor1);
        edtMinMotor[1] = findViewById(R.id.edtMinMotor2);
        edtMinMotor[2] = findViewById(R.id.edtMinMotor3);
        edtMinMotor[3] = findViewById(R.id.edtMinMotor4);
        edtMinMotor[4] = findViewById(R.id.edtMinMotor5);
        edtMinMotor[5] = findViewById(R.id.edtMinMotor6);
        edtMinMotor[6] = findViewById(R.id.edtMinMotor7);
        edtMinMotor[7] = findViewById(R.id.edtMinMotor8);
        edtMinMotor[8] = findViewById(R.id.edtMinMotor9);

        spnVoltageMotor[0] = findViewById(R.id.spnVoltageMotor1);
        spnVoltageMotor[1] = findViewById(R.id.spnVoltageMotor2);
        spnVoltageMotor[2] = findViewById(R.id.spnVoltageMotor3);
        spnVoltageMotor[3] = findViewById(R.id.spnVoltageMotor4);
        spnVoltageMotor[4] = findViewById(R.id.spnVoltageMotor5);
        spnVoltageMotor[5] = findViewById(R.id.spnVoltageMotor6);
        spnVoltageMotor[6] = findViewById(R.id.spnVoltageMotor7);
        spnVoltageMotor[7] = findViewById(R.id.spnVoltageMotor8);
        spnVoltageMotor[8] = findViewById(R.id.spnVoltageMotor9);

        edtMaxMotor[0] = findViewById(R.id.edtMaxMotor1);
        edtMaxMotor[1] = findViewById(R.id.edtMaxMotor2);
        edtMaxMotor[2] = findViewById(R.id.edtMaxMotor3);
        edtMaxMotor[3] = findViewById(R.id.edtMaxMotor4);
        edtMaxMotor[4] = findViewById(R.id.edtMaxMotor5);
        edtMaxMotor[5] = findViewById(R.id.edtMaxMotor6);
        edtMaxMotor[6] = findViewById(R.id.edtMaxMotor7);
        edtMaxMotor[7] = findViewById(R.id.edtMaxMotor8);
        edtMaxMotor[8] = findViewById(R.id.edtMaxMotor9);

        checkReverseMotor[0] = findViewById(R.id.checkReverseMotor1);
        checkReverseMotor[1] = findViewById(R.id.checkReverseMotor2);
        checkReverseMotor[2] = findViewById(R.id.checkReverseMotor3);
        checkReverseMotor[3] = findViewById(R.id.checkReverseMotor4);
        checkReverseMotor[4] = findViewById(R.id.checkReverseMotor5);
        checkReverseMotor[5] = findViewById(R.id.checkReverseMotor6);
        checkReverseMotor[6] = findViewById(R.id.checkReverseMotor7);
        checkReverseMotor[7] = findViewById(R.id.checkReverseMotor8);
        checkReverseMotor[8] = findViewById(R.id.checkReverseMotor9);

        btnSaveDataMotor[0] = findViewById(R.id.btnSaveDataMotor1);
        btnSaveDataMotor[1] = findViewById(R.id.btnSaveDataMotor2);
        btnSaveDataMotor[2] = findViewById(R.id.btnSaveDataMotor3);
        btnSaveDataMotor[3] = findViewById(R.id.btnSaveDataMotor4);
        btnSaveDataMotor[4] = findViewById(R.id.btnSaveDataMotor5);
        btnSaveDataMotor[5] = findViewById(R.id.btnSaveDataMotor6);
        btnSaveDataMotor[6] = findViewById(R.id.btnSaveDataMotor7);
        btnSaveDataMotor[7] = findViewById(R.id.btnSaveDataMotor8);
        btnSaveDataMotor[8] = findViewById(R.id.btnSaveDataMotor9);
        btnSaveDataAllMotor = findViewById(R.id.btnSaveDataAllMotor);


        txtNameOpenMotor[0] = findViewById(R.id.txtNameOpenMotor1);
        txtNameOpenMotor[1] = findViewById(R.id.txtNameOpenMotor2);
        txtNameOpenMotor[2] = findViewById(R.id.txtNameOpenMotor3);
        txtNameOpenMotor[3] = findViewById(R.id.txtNameOpenMotor4);
        txtNameOpenMotor[4] = findViewById(R.id.txtNameOpenMotor5);
        txtNameOpenMotor[5] = findViewById(R.id.txtNameOpenMotor6);
        txtNameOpenMotor[6] = findViewById(R.id.txtNameOpenMotor7);
        txtNameOpenMotor[7] = findViewById(R.id.txtNameOpenMotor8);
        txtNameOpenMotor[8] = findViewById(R.id.txtNameOpenMotor9);

        spnOpenStep1Motor[0] = findViewById(R.id.spnOpenStep1Motor1);
        spnOpenStep1Motor[1] = findViewById(R.id.spnOpenStep1Motor2);
        spnOpenStep1Motor[2] = findViewById(R.id.spnOpenStep1Motor3);
        spnOpenStep1Motor[3] = findViewById(R.id.spnOpenStep1Motor4);
        spnOpenStep1Motor[4] = findViewById(R.id.spnOpenStep1Motor5);
        spnOpenStep1Motor[5] = findViewById(R.id.spnOpenStep1Motor6);
        spnOpenStep1Motor[6] = findViewById(R.id.spnOpenStep1Motor7);
        spnOpenStep1Motor[7] = findViewById(R.id.spnOpenStep1Motor8);
        spnOpenStep1Motor[8] = findViewById(R.id.spnOpenStep1Motor9);

        spnOpenStep2Motor[0] = findViewById(R.id.spnOpenStep2Motor1);
        spnOpenStep2Motor[1] = findViewById(R.id.spnOpenStep2Motor2);
        spnOpenStep2Motor[2] = findViewById(R.id.spnOpenStep2Motor3);
        spnOpenStep2Motor[3] = findViewById(R.id.spnOpenStep2Motor4);
        spnOpenStep2Motor[4] = findViewById(R.id.spnOpenStep2Motor5);
        spnOpenStep2Motor[5] = findViewById(R.id.spnOpenStep2Motor6);
        spnOpenStep2Motor[6] = findViewById(R.id.spnOpenStep2Motor7);
        spnOpenStep2Motor[7] = findViewById(R.id.spnOpenStep2Motor8);
        spnOpenStep2Motor[8] = findViewById(R.id.spnOpenStep2Motor9);

        spnOpenStep3Motor[0] = findViewById(R.id.spnOpenStep3Motor1);
        spnOpenStep3Motor[1] = findViewById(R.id.spnOpenStep3Motor2);
        spnOpenStep3Motor[2] = findViewById(R.id.spnOpenStep3Motor3);
        spnOpenStep3Motor[3] = findViewById(R.id.spnOpenStep3Motor4);
        spnOpenStep3Motor[4] = findViewById(R.id.spnOpenStep3Motor5);
        spnOpenStep3Motor[5] = findViewById(R.id.spnOpenStep3Motor6);
        spnOpenStep3Motor[6] = findViewById(R.id.spnOpenStep3Motor7);
        spnOpenStep3Motor[7] = findViewById(R.id.spnOpenStep3Motor8);
        spnOpenStep3Motor[8] = findViewById(R.id.spnOpenStep3Motor9);

        txtNameCloseMotor[0] = findViewById(R.id.txtNameCloseMotor1);
        txtNameCloseMotor[1] = findViewById(R.id.txtNameCloseMotor2);
        txtNameCloseMotor[2] = findViewById(R.id.txtNameCloseMotor3);
        txtNameCloseMotor[3] = findViewById(R.id.txtNameCloseMotor4);
        txtNameCloseMotor[4] = findViewById(R.id.txtNameCloseMotor5);
        txtNameCloseMotor[5] = findViewById(R.id.txtNameCloseMotor6);
        txtNameCloseMotor[6] = findViewById(R.id.txtNameCloseMotor7);
        txtNameCloseMotor[7] = findViewById(R.id.txtNameCloseMotor8);
        txtNameCloseMotor[8] = findViewById(R.id.txtNameCloseMotor9);

        spnCloseStep1Motor[0] = findViewById(R.id.spnCloseStep1Motor1);
        spnCloseStep1Motor[1] = findViewById(R.id.spnCloseStep1Motor2);
        spnCloseStep1Motor[2] = findViewById(R.id.spnCloseStep1Motor3);
        spnCloseStep1Motor[3] = findViewById(R.id.spnCloseStep1Motor4);
        spnCloseStep1Motor[4] = findViewById(R.id.spnCloseStep1Motor5);
        spnCloseStep1Motor[5] = findViewById(R.id.spnCloseStep1Motor6);
        spnCloseStep1Motor[6] = findViewById(R.id.spnCloseStep1Motor7);
        spnCloseStep1Motor[7] = findViewById(R.id.spnCloseStep1Motor8);
        spnCloseStep1Motor[8] = findViewById(R.id.spnCloseStep1Motor9);

        spnCloseStep2Motor[0] = findViewById(R.id.spnCloseStep2Motor1);
        spnCloseStep2Motor[1] = findViewById(R.id.spnCloseStep2Motor2);
        spnCloseStep2Motor[2] = findViewById(R.id.spnCloseStep2Motor3);
        spnCloseStep2Motor[3] = findViewById(R.id.spnCloseStep2Motor4);
        spnCloseStep2Motor[4] = findViewById(R.id.spnCloseStep2Motor5);
        spnCloseStep2Motor[5] = findViewById(R.id.spnCloseStep2Motor6);
        spnCloseStep2Motor[6] = findViewById(R.id.spnCloseStep2Motor7);
        spnCloseStep2Motor[7] = findViewById(R.id.spnCloseStep2Motor8);
        spnCloseStep2Motor[8] = findViewById(R.id.spnCloseStep2Motor9);

        spnCloseStep3Motor[0] = findViewById(R.id.spnCloseStep3Motor1);
        spnCloseStep3Motor[1] = findViewById(R.id.spnCloseStep3Motor2);
        spnCloseStep3Motor[2] = findViewById(R.id.spnCloseStep3Motor3);
        spnCloseStep3Motor[3] = findViewById(R.id.spnCloseStep3Motor4);
        spnCloseStep3Motor[4] = findViewById(R.id.spnCloseStep3Motor5);
        spnCloseStep3Motor[5] = findViewById(R.id.spnCloseStep3Motor6);
        spnCloseStep3Motor[6] = findViewById(R.id.spnCloseStep3Motor7);
        spnCloseStep3Motor[7] = findViewById(R.id.spnCloseStep3Motor8);
        spnCloseStep3Motor[8] = findViewById(R.id.spnCloseStep3Motor9);
        btnSaveStep = findViewById(R.id.btnSaveStep);




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

        txtCurrent[0] = findViewById(R.id.txtCurrent1);
        txtCurrent[1] = findViewById(R.id.txtCurrent2);
        txtCurrent[2] = findViewById(R.id.txtCurrent3);
        txtCurrent[3] = findViewById(R.id.txtCurrent4);
        txtCurrent[4] = findViewById(R.id.txtCurrent5);
        txtCurrent[5] = findViewById(R.id.txtCurrent6);
        txtCurrent[6] = findViewById(R.id.txtCurrent7);
        txtCurrent[7] = findViewById(R.id.txtCurrent8);
        txtCurrent[8] = findViewById(R.id.txtCurrent9);

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
        imgRefreshData = findViewById(R.id.imgRefreshData);

        //----------------------Setup 2-------------------------------
        ST2spinerName = findViewById(R.id.ST2spinerName);

        ST2txtMotor1 = findViewById(R.id.ST2txtMotor1);
        ST2txtMotor2 = findViewById(R.id.ST2txtMotor2);
        ST2txtMotor3 = findViewById(R.id.ST2txtMotor3);
        ST2txtMotor4 = findViewById(R.id.ST2txtMotor4);
        ST2txtMotor5 = findViewById(R.id.ST2txtMotor5);
        ST2txtMotor6 = findViewById(R.id.ST2txtMotor6);

        ST2txtMaxCurrent1 = findViewById(R.id.ST2txtMaxCurrent1);
        ST2txtMaxCurrent2 = findViewById(R.id.ST2txtMaxCurrent2);
        ST2txtMaxCurrent3 = findViewById(R.id.ST2txtMaxCurrent3);
        ST2txtMaxCurrent4 = findViewById(R.id.ST2txtMaxCurrent4);
        ST2txtMaxCurrent5 = findViewById(R.id.ST2txtMaxCurrent5);
        ST2txtMaxCurrent6 = findViewById(R.id.ST2txtMaxCurrent6);

        ST2txtMinCurrent1 = findViewById(R.id.ST2txtMinCurrent1);
        ST2txtMinCurrent2 = findViewById(R.id.ST2txtMinCurrent2);
        ST2txtMinCurrent3 = findViewById(R.id.ST2txtMinCurrent3);
        ST2txtMinCurrent4 = findViewById(R.id.ST2txtMinCurrent4);
        ST2txtMinCurrent5 = findViewById(R.id.ST2txtMinCurrent5);
        ST2txtMinCurrent6 = findViewById(R.id.ST2txtMinCurrent6);

        ST2txtReturn1 = findViewById(R.id.ST2txtReturn1);
        ST2txtReturn2 = findViewById(R.id.ST2txtReturn2);
        ST2txtReturn3 = findViewById(R.id.ST2txtReturn3);
        ST2txtReturn4 = findViewById(R.id.ST2txtReturn4);
        ST2txtReturn5 = findViewById(R.id.ST2txtReturn5);
        ST2txtReturn6 = findViewById(R.id.ST2txtReturn6);

        ST2edtMinCurrent = findViewById(R.id.ST2edtMinCurrent);
        ST2edtMaxCurrent = findViewById(R.id.ST2edtMaxCurrent);
        ST2timeReturn = findViewById(R.id.ST2timeReturn);
        ST2btnSave = findViewById(R.id.ST2btnSave);
        imgRefreshStep = findViewById(R.id.imgRefreshStep);


    }

    public void LoadDataBegin() {
        //---------------------------------------------------save name motor--------------------------------------
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = sharedPreferences.edit();
        for(int i = 0; i < MAX_MOTOR; i++){
            edtNameMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
            txtNameMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
            txtNameOpenMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
            txtNameCloseMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
        }
        //------get background---------
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            if(!sharedPreferences.getString(nameUri, nameUri).equals(nameUri)){
                try {
//                Toast.makeText(MainActivity.this, sharedPreferences.getString(nameUri, nameUri), Toast.LENGTH_SHORT).show();
                    indexNameImageBackGround = sharedPreferences.getInt(strIndexName, indexNameImageBackGround);
                    String path = sharedPreferences.getString(nameUri, nameUri);
                    Log.d("filename_path", path);
//                    path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath()
//                            + "/" + IMAGES_FOLDER_NAME + "/" + nameImageBackGround + "_"
//                            + String.valueOf(sharedPreferences.getInt(strIndexName, indexNameImageBackGround)) + ".png";
//                    Log.d("filename_path", path);
                    InputStream inputStream = getContentResolver().openInputStream(Uri.parse(path));
//                    Log.d("filename_path", path);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
//                    Log.d("filename_path", path);
//                    Bitmap bitmap = BitmapFactory.decodeFile(path);
                    imgBackground.setImageBitmap(bitmap);
                } catch (Exception exception){
                    Log.d("filename_path", "cant convert");
                }
            }
        }

//        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/background.png";
//        Log.d("filename_path", fileName);
//        File imageFile = new File(fileName);
//        FileInputStream fis = null;
//        try {
//            fis = new FileInputStream(imageFile);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        Bitmap bitmap = BitmapFactory.decodeStream(fis);
//        imgBackground.setImageBitmap(bitmap);


        List<String> listStep = new ArrayList<>();
        listStep.add("S");
        listStep.add("O");
        listStep.add("C");
        //ArrayAdapter spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listStep);
        ArrayAdapter spinnerAdapterStep = new ArrayAdapter<>(this, R.layout.spinner_list, listStep);
        spinnerAdapterStep.setDropDownViewResource(R.layout.spinner_list);
        for(int i = 0; i < MAX_MOTOR; i++){
            spnOpenStep1Motor[i].setAdapter(spinnerAdapterStep);
            spnOpenStep2Motor[i].setAdapter(spinnerAdapterStep);
            spnOpenStep3Motor[i].setAdapter(spinnerAdapterStep);
            spnCloseStep1Motor[i].setAdapter(spinnerAdapterStep);
            spnCloseStep2Motor[i].setAdapter(spinnerAdapterStep);
            spnCloseStep3Motor[i].setAdapter(spinnerAdapterStep);
        }

        List<String> listVoltage = new ArrayList<>();
        listVoltage.add("12V");
        listVoltage.add("11V");
        listVoltage.add("10V");
        listVoltage.add("9V");
        listVoltage.add("8V");
        listVoltage.add("7V");
        listVoltage.add("6V");
        //ArrayAdapter spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listVoltage);
        ArrayAdapter spinnerAdapterVoltage = new ArrayAdapter<>(this, R.layout.spinner_list, listVoltage);
        spinnerAdapterVoltage.setDropDownViewResource(R.layout.spinner_list);
//        spnVoltageMotor[0].setAdapter(spinnerAdapterVoltage);
        for(int i = 0; i < MAX_MOTOR; i++){
            spnVoltageMotor[i].setAdapter(spinnerAdapterVoltage);
        }


        //------------------------------------------------------------------------------------------------------
    }


//    String error = ""; // string field
//    private String getDataFromUrl(String demoIdUrl) {
//
//        String result = null;
//        int resCode;
//        InputStream in;
//        try {
//            URL url = new URL(demoIdUrl);
//            URLConnection urlConn = url.openConnection();
//
//            HttpsURLConnection httpsConn = (HttpsURLConnection) urlConn;
//            httpsConn.setAllowUserInteraction(false);
//            httpsConn.setInstanceFollowRedirects(true);
//            httpsConn.setRequestMethod("GET");
//            httpsConn.connect();
//            resCode = httpsConn.getResponseCode();
//
//            if (resCode == HttpURLConnection.HTTP_OK) {
//                in = httpsConn.getInputStream();
//
//                BufferedReader reader = new BufferedReader(new InputStreamReader(
//                        in, "iso-8859-1"), 8);
//                StringBuilder sb = new StringBuilder();
//                String line;
//                while ((line = reader.readLine()) != null) {
//                    sb.append(line).append("\n");
//                }
//                in.close();
//                result = sb.toString();
//            } else {
//                error += resCode;
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        Log.d("http_request", result);
//        return result;
//    }

    void getDataFromUrl(String url) throws IOException {
//        OkHttpClient client;
//        client = new OkHttpClient.Builder()
//                .connectTimeout(10, TimeUnit.SECONDS)
//                .writeTimeout(10, TimeUnit.SECONDS)
//                .readTimeout(30, TimeUnit.SECONDS)
//                .build();
//
//        Request get = new Request.Builder()
//                .url("https://reqres.in/api/users?page=2")
//                .build();
//
//        client.newCall(get).enqueue(new Callback() {
//            @Override
//            public void onFailure(Call call, IOException e) {
//                Log.d("http_request", "Call Cancel");
//                e.printStackTrace();
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) {
//                try {
//                    ResponseBody responseBody = response.body();
//                    Log.d("http_request", responseBody.toString());
//                    if (!response.isSuccessful()) {
//                        throw new IOException("Unexpected code " + response);
//                    }
//
//                    Log.i("data", responseBody.string());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        });

        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);
//        url = "https://www.google.com";

// Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
//                        textView.setText("Response is: " + response.substring(0,500));
                        Log.d("http_request", response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                textView.setText("That didn't work!");
                Log.d("http_request", "That didn't work!");
            }
        });

// Add the request to the RequestQueue.
        queue.add(stringRequest);

    }

    public void selectImage() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.putExtra("crop", "true");
        intent.putExtra("scale", true);
        intent.putExtra("aspectX", 16);
        intent.putExtra("aspectY", 9);
        if(intent.resolveActivity(getPackageManager()) != null){
            startActivityForResult(intent, REQUEST_CODE_SELECT_IMAGE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(requestCode == REQUEST_CODE_STORAGE_PERMISSION && grantResults.length >0){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                selectImage();
            }
            else{
                Toast.makeText(MainActivity.this, "Permission denied!", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE_SELECT_IMAGE && resultCode == RESULT_OK){
            if(data != null){
                Uri selectImageUri = data.getData();
//                Toast.makeText(MainActivity.this, selectImageUri.toString(), Toast.LENGTH_SHORT).show();
                if(selectImageUri != null){
                    try {
                        //content://com.google.android.apps.photos.contentprovider/-1/1/content%3A%2F%2Fmedia%2Fexternal%2Fimages%2Fmedia%2F5286/ORIGINAL/NONE/image%2Fjpeg/600183246
                        indexNameImageBackGround++;
                        editor.putString(nameUri, selectImageUri.toString());
                        editor.commit();
                        editor.putInt(strIndexName, indexNameImageBackGround);
                        editor.commit();
                        InputStream inputStream = getContentResolver().openInputStream(selectImageUri);
                        Log.d("filename_path", selectImageUri.toString());


                        Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                        imgBackground.setImageBitmap(bitmap);
//                        saveBitmap(bitmap);
//                        saveImage(bitmap, nameImageBackGround );
//                        String fileName = Environment.getExternalStorageDirectory().getAbsolutePath() + "/background.png";
//                        Log.d("filename_path", fileName);
//                        File sd = Environment.getExternalStorageDirectory();
//                        File dest = new File(sd, fileName);
//
////                        bitmap = (Bitmap)data.getExtras().get("data");
//                        try {
//                            FileOutputStream out = new FileOutputStream(dest);
//                            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
//                            out.flush();
//                            out.close();
//                        } catch (Exception e) {
//                            Log.d("filename_path", "Exception Write");
//                            e.printStackTrace();
//                        }

                    } catch (Exception exception){

                    }
                }
            }
        }
    }

    private void saveImage(Bitmap bitmap, @NonNull String name) throws IOException {
        boolean saved;
        OutputStream fos;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Log.d("filename_path", "Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q");
            ContentResolver resolver = this.getContentResolver();
            ContentValues contentValues = new ContentValues();
            contentValues.put(MediaStore.MediaColumns.DISPLAY_NAME, name);
            contentValues.put(MediaStore.MediaColumns.MIME_TYPE, "image/png");
            contentValues.put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/" + IMAGES_FOLDER_NAME);
            Uri imageUri = resolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            fos = resolver.openOutputStream(imageUri);
        } else {
            String imagesDir = Environment.getExternalStoragePublicDirectory(
                    Environment.DIRECTORY_DCIM).toString() + File.separator + IMAGES_FOLDER_NAME;

            File file = new File(imagesDir);

            if (!file.exists()) {
                file.mkdir();
            }

            File image = new File(imagesDir, name + "_" + String.valueOf(sharedPreferences.getInt(strIndexName, indexNameImageBackGround)) + ".png");
            fos = new FileOutputStream(image);

        }

        saved = bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
        fos.flush();
        fos.close();
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

            // Keep listening to the InputStream until an exception occurs
            while (true) {
                // Read from the InputStream
                try {
                    bytes = mmInStream.read(buffer);
                    incomingMessage += new String(buffer, 0, bytes);
//                    Log.d(TAG, "InputStream---: " + incomingMessage);
                    if(incomingMessage.contains("}")){
                        Log.d(TAG, "InputStream: " + incomingMessage);
                        JSONObject reader = new JSONObject(incomingMessage);
                        incomingMessage = "";
                        //min max data current
                        if(reader.has("2")){
                            JSONArray array= reader.getJSONArray("2");
                            runOnUiThread(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    //-------
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        try {
                                            txtMinMotor[i].setText(String.valueOf(array.getInt(i)));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        try {
                                            txtMaxMotor[i].setText(String.valueOf(array.getInt(MAX_MOTOR+i)));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        try {
//                                            txtMaxMotor[i].setText(String.valueOf(array.getInt(2*MAX_MOTOR+i)));
                                            if(array.getInt(2*MAX_MOTOR+i) == 1){
                                                checkReverseMotor[i].setChecked(true);
                                            }
                                            else{
                                                checkReverseMotor[i].setChecked(false);
                                            }

                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }

                                    }
                                }
                            });

                        }
                        //Step
                        else if(reader.has("3")){
                            JSONArray array= reader.getJSONArray("3");
                            runOnUiThread(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    //-------------------OPEN STEP 1---------------------------
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        try {
                                            spnOpenStep1Motor[i].setSelection(array.getInt(i));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    //-------------------OPEN STEP 2---------------------------
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        try {
                                            spnOpenStep2Motor[i].setSelection(array.getInt(MAX_MOTOR+i));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    //-------------------OPEN STEP 3---------------------------
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        try {
                                            spnOpenStep3Motor[i].setSelection(array.getInt(2*MAX_MOTOR+i));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    //-------------------CLOSE STEP 1---------------------------
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        try {
                                            spnCloseStep1Motor[i].setSelection(array.getInt(3*MAX_MOTOR+i));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    //-------------------CLOSE STEP 2---------------------------
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        try {
                                            spnCloseStep2Motor[i].setSelection(array.getInt(4*MAX_MOTOR+i));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    //-------------------CLOSE STEP 3---------------------------
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        try {
                                            spnCloseStep3Motor[i].setSelection(array.getInt(5*MAX_MOTOR+i));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                        //current
                        else if(reader.has("1")){
                            double data[] = new double[9];

                            JSONArray array= reader.getJSONArray("1");
                            for(int i = 0; i < array.length(); i++){
                                try{
                                    data[i] = array.getDouble(i);
                                }
                                catch (JSONException e) {
                                    Log.e("InputStream", "write: Error reading Input Stream. " + e.getMessage());
                                }

                            }
//                            Log.d(TAG, "InputStream: " + data[0]);
                            runOnUiThread(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    //-------
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        txtCurrent[i].setText(String.valueOf(data[i]) + " mA");
                                    }
                                }
                            });
                        }
                    }

                } catch (IOException | JSONException e) {
                    incomingMessage = "";
                    Log.e("InputStream", "write: Error reading Input Stream. " + e.getMessage());
//                    Toast.makeText(MainActivity.this, "Kết nối thất bại", Toast.LENGTH_SHORT).show();
//                    break;
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
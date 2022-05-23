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
import android.widget.RelativeLayout;
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
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import static android.R.layout.simple_list_item_1;
import static android.R.layout.simple_spinner_dropdown_item;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

//import com.android.volley.Request;
//import com.android.volley.RequestQueue;
//import com.android.volley.Response;
//import com.android.volley.VolleyError;
//import com.android.volley.toolbox.StringRequest;
//import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    int MAX_MOTOR = 9;
    ImageView imgBackground, imgSelectBackground, imgMenuListDevice, imgBluetoothConnection, imgSaveDataSetting, imgGetDataSetting, imgBackGetSetting;
    View layoutSetup, layoutListDevice, layoutSetup2, layoutSetupData, layoutSetupStep, layoutSaveDataSetting, layoutGetDataSetting;
    TextView txtBackSetting, txtBackListDevice, txtNameBluetoothConnection, txtBackSetting2, txtNextSetting;
    TextView[] txtNameGetSetting = new TextView[3];
    Button btnCancelSaveSetting, btnOkSaveSetting;
    EditText edtSaveNameSetting;
    Spinner spnSelectNumberSetting;

    RelativeLayout[] rlGetDataSetting = new RelativeLayout[3];

    ProgressBar pgbRefreshListDevice, proBarLoadingSaveDataSetting, proBarLoadingGetDataSetting;
    ListView lvListDevice;
    //------------------ main menu---------------------
    EditText[] edtNameMotor = new EditText[MAX_MOTOR];
    Button[] btnOpenMotor = new Button[MAX_MOTOR];
    Button[] btnStopMotor = new Button[MAX_MOTOR];
    Button[] btnCloseMotor = new Button[MAX_MOTOR];
    Button btnSaveNameMotor, btnResetTotalPower;
    ImageView imgRefreshVoltage;
    TextView txtPinVoltage, txtTotalPower, txtModeRunBoard;

    //---------------------------------------------------
    TextView[] txtNameMotor = new TextView[MAX_MOTOR];
    TextView[] txtMinMotor = new TextView[MAX_MOTOR];
    TextView[] txtMaxMotor = new TextView[MAX_MOTOR];
    TextView[] edtMinMotor = new TextView[MAX_MOTOR];
    TextView[] edtMaxMotor = new TextView[MAX_MOTOR];
    CheckBox[] checkReverseMotor = new CheckBox[MAX_MOTOR];
    CheckBox[] checkDisableMotor = new CheckBox[MAX_MOTOR];
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
    Spinner ST2SpinnerName;
    TextView ST2txtMotor1,ST2txtMotor2,ST2txtMotor3,ST2txtMotor4,ST2txtMotor5,ST2txtMotor6;
    TextView ST2txtMaxCurrent1,ST2txtMaxCurrent2,ST2txtMaxCurrent3,ST2txtMaxCurrent4,ST2txtMaxCurrent5,ST2txtMaxCurrent6;
    TextView ST2txtMinCurrent1,ST2txtMinCurrent2,ST2txtMinCurrent3,ST2txtMinCurrent4,ST2txtMinCurrent5,ST2txtMinCurrent6;
    TextView ST2txtReturn1,ST2txtReturn2,ST2txtReturn3,ST2txtReturn4,ST2txtReturn5,ST2txtReturn6;
    EditText ST2edtMinCurrent,ST2edtMaxCurrent,ST2timeReturn;
    Button ST2btnSave;
    ImageView imgRefreshStep;

    ArrayAdapter<String> arrayAdapterListDevice;

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
    Object[] ObjectBluetooth;

//    CountDownLatch cdStartHttpRequest;

    //variable for save name motor
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String[] name = {"Motor1","Motor2","Motor3","Motor4","Motor5","Motor6","Motor7","Motor8","Motor9"};
    String[] nameSetting = {"Setting 1", "Setting 2", "Setting 3"};
    String nameUri = "Uri";
    String IMAGES_FOLDER_NAME = "Landing";
    String strIndexName = "index";
    int indexNameImageBackGround = 0;

    String OPEN = "1", CLOSE = "2", STOP = "0";

    Boolean flagSelectGetSettingLayout = false;
    Boolean flagSelectSaveOkSettingLayout = false;
    Boolean flagSelectGetNumberDataSetting = false;
    String urlGetNameSetting = "https://api.thingspeak.com/channels/1725474/fields/1.json?api_key=L3JZMZPK5FYFA9PP&results=1";
    String urlGetSetting1 = "https://api.thingspeak.com/channels/1723005/feeds.json?api_key=2B92OPPGGLLJ9FQW&results=1";
    String urlGetSetting2 = "https://api.thingspeak.com/channels/1725472/feeds.json?api_key=7T86FTOU1J8YPUOV&results=1";
    String urlGetSetting3 = "https://api.thingspeak.com/channels/1725473/feeds.json?api_key=6JY2NDB81JL8XSZT&results=1";
    String urlPreSendNameSetting = "https://api.thingspeak.com/update?api_key=N16UL08THD3ZCIUF&";
    String urlPreSendSetting1 = "https://api.thingspeak.com/update?api_key=Z40R2BCIBSSAOJCT&";
    String urlPreSendSetting2 = "https://api.thingspeak.com/update?api_key=FCFICTT66HDEYY29&";
    String urlPreSendSetting3 = "https://api.thingspeak.com/update?api_key=UXI5VZ9KEM8S8OPF&";
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

        //--------------------------------------save data setting--------------------------------------------------------
        imgSaveDataSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSaveDataSetting.setVisibility(View.VISIBLE);
                layoutGetDataSetting.setVisibility(View.INVISIBLE);
            }
        });
        btnOkSaveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isInvalidData = true;
                for(int i = 0; i < MAX_MOTOR; i++){
                    if(edtNameMotor[i].getText().toString().equals("")
                            || edtMinMotor[i].getText().toString().equals("")
                            || edtMaxMotor[i].getText().toString().equals("")){
                        isInvalidData = false;
                    }
                }
                if(!isInvalidData){
                    edtSaveNameSetting.getText().clear();
                    layoutSaveDataSetting.setVisibility(View.INVISIBLE);
                    Toast.makeText(MainActivity.this, "Chưa nhập đủ thông số!", Toast.LENGTH_SHORT).show();
                    return;
                }
                proBarLoadingSaveDataSetting.setVisibility(View.VISIBLE);
                flagSelectSaveOkSettingLayout = true;
                String paramField = "field1=[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    paramField += "\"";
                    paramField += edtNameMotor[i].getText().toString();
                    paramField += "\"";
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "]&field2=[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    paramField += edtMinMotor[i].getText().toString();
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "]&field3=[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    paramField += edtMaxMotor[i].getText().toString();
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "]&field4=[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    if(checkReverseMotor[i].isChecked()){
                        paramField += "1";
                    }else
                        paramField += "0";
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "]&field5=[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    if(checkDisableMotor[i].isChecked()){
                        paramField += "1";
                    }else
                        paramField += "0";
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "]&field6={\"1\":[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    paramField += String.valueOf(spnOpenStep1Motor[i].getSelectedItemPosition());
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "],\"2\":[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    paramField += String.valueOf(spnOpenStep2Motor[i].getSelectedItemPosition());
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "],\"3\":[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    paramField += String.valueOf(spnOpenStep3Motor[i].getSelectedItemPosition());
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "]}&field7={\"1\":[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    paramField += String.valueOf(spnCloseStep1Motor[i].getSelectedItemPosition());
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "],\"2\":[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    paramField += String.valueOf(spnCloseStep2Motor[i].getSelectedItemPosition());
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "],\"3\":[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    paramField += String.valueOf(spnCloseStep3Motor[i].getSelectedItemPosition());
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "]}&field8=[";
                for(int i = 0; i < MAX_MOTOR; i++){
                    paramField += String.valueOf(spnVoltageMotor[i].getSelectedItemPosition());
                    if(i == MAX_MOTOR - 1)break;
                    paramField += ",";
                }
                paramField += "]";
//                Log.d("http_request", param);
                String dataSendValue = "";
                if(spnSelectNumberSetting.getSelectedItemPosition() == 0){
                    nameSetting[0] = edtSaveNameSetting.getText().toString();
                    dataSendValue = urlPreSendSetting1 + paramField;
                }
                else if(spnSelectNumberSetting.getSelectedItemPosition() == 1){
                    nameSetting[1] = edtSaveNameSetting.getText().toString();
                    dataSendValue = urlPreSendSetting2 + paramField;
                }
                else if(spnSelectNumberSetting.getSelectedItemPosition() == 2){
                    nameSetting[2] = edtSaveNameSetting.getText().toString();
                    dataSendValue = urlPreSendSetting3 + paramField;
                }
                String paramName = "field1=[\"";
                paramName += nameSetting[0];
                paramName += "\",\"";
                paramName += nameSetting[1];
                paramName += "\",\"";
                paramName += nameSetting[2];
                paramName += "\"]";
                String dataSendName = urlPreSendNameSetting + paramName;
                Log.d("http_request", "dataSendName: " + dataSendName);
                Log.d("http_request", "dataSendValue: " + dataSendValue);
                try {
                    getDataFromUrl(dataSendName);
                    getDataFromUrl(dataSendValue);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Không kết nối được Server!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btnCancelSaveSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtSaveNameSetting.getText().clear();
                layoutSaveDataSetting.setVisibility(View.INVISIBLE);
                layoutGetDataSetting.setVisibility(View.INVISIBLE);
            }
        });

        //--------------------------------------get data setting--------------------------------------------------------
        imgGetDataSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSaveDataSetting.setVisibility(View.INVISIBLE);
                layoutGetDataSetting.setVisibility(View.VISIBLE);
                proBarLoadingGetDataSetting.setVisibility(View.VISIBLE);
                flagSelectGetSettingLayout = true;
                try {
                    getDataFromUrl(urlGetNameSetting);

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("http_request", e.toString());
                }
//                proBarLoadingGetDataSetting.setVisibility(View.INVISIBLE);
            }
        });
        imgBackGetSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSaveDataSetting.setVisibility(View.INVISIBLE);
                layoutGetDataSetting.setVisibility(View.INVISIBLE);
            }
        });

        for(int i = 0; i < 3; i++){
            int finalI = i;
            rlGetDataSetting[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flagSelectGetNumberDataSetting = true;
                    proBarLoadingGetDataSetting.setVisibility(View.VISIBLE);
                    switch (finalI){
                    case 0:
                        try {
                            getDataFromUrl(urlGetSetting1);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("http_request", e.toString());
                        }
                        break;
                    case 1:
                        try {
                            getDataFromUrl(urlGetSetting2);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("http_request", e.toString());
                        }
                        break;
                    case 2:
                        try {
                            getDataFromUrl(urlGetSetting3);
                        } catch (IOException e) {
                            e.printStackTrace();
                            Log.d("http_request", e.toString());
                        }
                        break;
                    }
                }
            });
        }

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
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(checkDisableMotor[i].isChecked()){
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

        btnResetTotalPower.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"reset_power\"}";
                    byte[] bytes = data.getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgRefreshVoltage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"request_voltage\"}";
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
                if (mmDevice !=null && isConnected(mmDevice)) {
                    String data = "{\"type\":\"voltage\",\"1\":[";
                    for(int i = 0; i < MAX_MOTOR; i++){
                        data += String.valueOf(spnVoltageMotor[i].getSelectedItemPosition());
                        data += ",";
                    }
                    data += "]}";
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
                    data += ST2SpinnerName.getSelectedItemPosition();
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

        imgMenuListDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutListDevice.setVisibility(View.VISIBLE);
//                imgRefreshListDevice.setVisibility(View.VISIBLE);
                BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                Set<BluetoothDevice> pairedDevices = mBluetoothAdapter.getBondedDevices();
                List<String> s = new ArrayList<String>();
//                s.add("---Thiết bị đã ghép đôi---");
                for(BluetoothDevice bt : pairedDevices){
                    s.add(bt.getName() + "\n" + bt.getAddress());
                }
                ObjectBluetooth = pairedDevices.toArray();
//                s.add("---Thiết bị hiện có---");
                arrayAdapterListDevice = new ArrayAdapter<String>(
                        MainActivity.this,
                        simple_list_item_1,
                        s );
                lvListDevice.setAdapter(arrayAdapterListDevice);
            }
        });
        txtBackListDevice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                layoutListDevice.setVisibility(View.INVISIBLE);
                pgbRefreshListDevice.setVisibility(View.INVISIBLE);
            }
        });
        lvListDevice.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Log.i(TAG, arrayAdapterListDevice.getItem(i));

                pgbRefreshListDevice.setVisibility(View.VISIBLE);
                BluetoothDevice bluetoothDeviceConnect = (BluetoothDevice)ObjectBluetooth[i];

                deviceName = bluetoothDeviceConnect.getName();
                String deviceAddress = bluetoothDeviceConnect.getAddress();

                Log.d(TAG, "onItemClick: deviceName = " + deviceName);
                Log.d(TAG, "onItemClick: deviceAddress = " + deviceAddress);
                Log.d(TAG, "Trying to Pair with " + deviceName);
                bluetoothDeviceConnect.createBond();

                mDeviceUUIDs = bluetoothDeviceConnect.getUuids();


                Log.d(TAG, "Trying to create UUID: " + deviceName);

                for (ParcelUuid uuid: mDeviceUUIDs) {
                    Log.d(TAG, "UUID: " + uuid.getUuid().toString());
                }

//                ParcelUuid uuidExtra Intent intent = null;
//                intent.getParcelableExtra("android.bluetooth.device.extra.UUID");
//                UUID uuid = mDeviceUUIDs.getUuid();

                ConnectThread connect = new ConnectThread(bluetoothDeviceConnect,MY_UUID_INSECURE);
                connect.start();
            }
        });

    }

    public void InitLayout(){
        imgBackground = findViewById(R.id.imgBackground);
        imgSelectBackground = findViewById(R.id.imgSelectBackground);
        imgSaveDataSetting = findViewById(R.id.imgSaveDataSetting);
        imgGetDataSetting = findViewById(R.id.imgGetDataSetting);
        imgBackGetSetting = findViewById(R.id.imgBackGetSetting);
        edtSaveNameSetting = findViewById(R.id.edtSaveNameSetting);
        spnSelectNumberSetting = findViewById(R.id.spnSelectNumberSetting);
        proBarLoadingSaveDataSetting = findViewById(R.id.proBarLoadingSaveDataSetting);
        proBarLoadingGetDataSetting = findViewById(R.id.proBarLoadingGetDataSetting);

        rlGetDataSetting[0] = findViewById(R.id.rlGetDataSetting1);
        rlGetDataSetting[1] = findViewById(R.id.rlGetDataSetting2);
        rlGetDataSetting[2] = findViewById(R.id.rlGetDataSetting3);

        txtBackSetting = findViewById(R.id.txtBackSetting);
        txtBackSetting2 = findViewById(R.id.txtBackSetting2);
        txtNextSetting = findViewById(R.id.txtNextSetting);
        txtNameGetSetting[0] = findViewById(R.id.txtNameGetSetting1);
        txtNameGetSetting[1] = findViewById(R.id.txtNameGetSetting2);
        txtNameGetSetting[2] = findViewById(R.id.txtNameGetSetting3);

        layoutSetup = findViewById(R.id.layoutSetup);
        layoutSetup2 = findViewById(R.id.layoutSetup2);
        layoutListDevice = findViewById(R.id.layoutListDevice);
        layoutSetupStep = findViewById(R.id.layoutSetupStep);
        layoutGetDataSetting = findViewById(R.id.layoutGetDataSetting);
        layoutSaveDataSetting = findViewById(R.id.layoutSaveDataSetting);

        btnCancelSaveSetting = findViewById(R.id.btnCancelSaveSetting);
        btnOkSaveSetting = findViewById(R.id.btnOkSaveSetting);

        txtBackListDevice = findViewById(R.id.txtBackListDevice);
        pgbRefreshListDevice = findViewById(R.id.pgbListDevice);
        imgMenuListDevice = findViewById(R.id.imgMenuListDevice);
        txtBackListDevice = findViewById(R.id.txtBackListDevice);
        lvListDevice = findViewById(R.id.lvListDevice);
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

        btnResetTotalPower = findViewById(R.id.btnResetTotalPower);
        txtPinVoltage = findViewById(R.id.txtPinVoltage);
        txtTotalPower = findViewById(R.id.txtTotalPower);
        txtModeRunBoard = findViewById(R.id.txtModeRunBoard);
        imgRefreshVoltage = findViewById(R.id.imgRefreshVoltage);
        btnSaveNameMotor = findViewById(R.id.btnSaveNameMotor);

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

        checkDisableMotor[0] = findViewById(R.id.checkDisableMotor1);
        checkDisableMotor[1] = findViewById(R.id.checkDisableMotor2);
        checkDisableMotor[2] = findViewById(R.id.checkDisableMotor3);
        checkDisableMotor[3] = findViewById(R.id.checkDisableMotor4);
        checkDisableMotor[4] = findViewById(R.id.checkDisableMotor5);
        checkDisableMotor[5] = findViewById(R.id.checkDisableMotor6);
        checkDisableMotor[6] = findViewById(R.id.checkDisableMotor7);
        checkDisableMotor[7] = findViewById(R.id.checkDisableMotor8);
        checkDisableMotor[8] = findViewById(R.id.checkDisableMotor9);

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
        ST2SpinnerName = findViewById(R.id.ST2SpinnerName);

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

        List<String> listSetting = new ArrayList<>();
        listSetting.add("Setting 1");
        listSetting.add("Setting 2");
        listSetting.add("Setting 3");
        //ArrayAdapter spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listSetting);
        ArrayAdapter spinnerAdapterSetting = new ArrayAdapter<>(this, R.layout.spinner_list, listSetting);
        spinnerAdapterSetting.setDropDownViewResource(R.layout.spinner_list);
//        spnSettingMotor[0].setAdapter(spinnerAdapterSetting);
        spnSelectNumberSetting.setAdapter(spinnerAdapterSetting);

        //------------------------------------------------------------------------------------------------------
        flagSelectGetSettingLayout = true;
        try {
            getDataFromUrl(urlGetNameSetting);

        } catch (IOException e) {
            e.printStackTrace();
            Log.d("http_request", e.toString());
        }
    }

    void getDataFromUrl(String url) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                call.cancel();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "Can't Connect To Network!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String myResponse = response.body().string();
                Log.d("http_request", myResponse);
//                countDownLatch.countDown();
                proBarLoadingGetDataSetting.setVisibility(View.INVISIBLE);
                if(flagSelectGetSettingLayout){
                    flagSelectGetSettingLayout = false;
                    try {
                        JSONObject reader = new JSONObject(myResponse);
                        String field4 = reader.getJSONArray("feeds").getJSONObject(0).getString("field1");
                        JSONArray arrayField4 = new JSONArray(field4);
                        nameSetting[0] = arrayField4.getString(0);
                        nameSetting[1] = arrayField4.getString(1);
                        nameSetting[2] = arrayField4.getString(2);
//                        Log.d("http_request", name1);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                txtNameGetSetting[0].setText(nameSetting[0]);
                                txtNameGetSetting[1].setText(nameSetting[1]);
                                txtNameGetSetting[2].setText(nameSetting[2]);
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("http_request", e.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "Không thể lấy dữ liệu!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else if(flagSelectGetNumberDataSetting){
                    flagSelectGetNumberDataSetting = false;

                    try {
                        JSONObject reader = new JSONObject(myResponse);
                        String field1 = reader.getJSONArray("feeds").getJSONObject(0).getString("field1");
                        JSONArray arrayField1 = new JSONArray(field1);
                        String[] NameMotor = new String[MAX_MOTOR];
                        for(int i = 0; i < MAX_MOTOR; i++){
                            NameMotor[i] = arrayField1.getString(i);
                        }
                        String field2 = reader.getJSONArray("feeds").getJSONObject(0).getString("field2");
                        JSONArray arrayField2 = new JSONArray(field2);
                        int[] MinCurrent = new int[9];
                        for(int i = 0; i < MAX_MOTOR; i++){
                            MinCurrent[i] = arrayField2.getInt(i);
                        }
                        String field3 = reader.getJSONArray("feeds").getJSONObject(0).getString("field3");
                        JSONArray arrayField3 = new JSONArray(field3);
                        int[] MaxCurrent = new int[9];
                        for(int i = 0; i < MAX_MOTOR; i++){
                            MaxCurrent[i] = arrayField3.getInt(i);
                        }
                        String field4 = reader.getJSONArray("feeds").getJSONObject(0).getString("field4");
                        JSONArray arrayField4 = new JSONArray(field4);
                        int[] Reverse = new int[9];
                        for(int i = 0; i < MAX_MOTOR; i++){
                            Reverse[i] = arrayField4.getInt(i);
                        }
                        String field5 = reader.getJSONArray("feeds").getJSONObject(0).getString("field5");
                        JSONArray arrayField5 = new JSONArray(field5);
                        int[] Disable = new int[9];
                        for(int i = 0; i < MAX_MOTOR; i++){
                            Disable[i] = arrayField5.getInt(i);
                        }
                        String strFieldOpenStep1 = reader.getJSONArray("feeds").getJSONObject(0).getString("field6");
                        JSONObject objectOpenStep1 = new JSONObject(strFieldOpenStep1);
                        String strOpenStep1 = objectOpenStep1.getString("1");
                        JSONArray arrayOpenStep1= new JSONArray(strOpenStep1);
                        int[] intOpenStep1 = new int[9];
                        String strFieldOpenStep2 = reader.getJSONArray("feeds").getJSONObject(0).getString("field6");
                        JSONObject objectOpenStep2 = new JSONObject(strFieldOpenStep2);
                        String strOpenStep2 = objectOpenStep2.getString("2");
                        JSONArray arrayOpenStep2= new JSONArray(strOpenStep2);
                        int[] intOpenStep2 = new int[9];
                        String strFieldOpenStep3 = reader.getJSONArray("feeds").getJSONObject(0).getString("field6");
                        JSONObject objectOpenStep3 = new JSONObject(strFieldOpenStep3);
                        String strOpenStep3 = objectOpenStep3.getString("3");
                        JSONArray arrayOpenStep3= new JSONArray(strOpenStep3);
                        int[] intOpenStep3 = new int[9];
                        String strFieldCloseStep1 = reader.getJSONArray("feeds").getJSONObject(0).getString("field7");
                        JSONObject objectCloseStep1 = new JSONObject(strFieldCloseStep1);
                        String strCloseStep1 = objectCloseStep1.getString("1");
                        JSONArray arrayCloseStep1= new JSONArray(strCloseStep1);
                        int[] intCloseStep1 = new int[9];
                        String strFieldCloseStep2 = reader.getJSONArray("feeds").getJSONObject(0).getString("field7");
                        JSONObject objectCloseStep2 = new JSONObject(strFieldCloseStep2);
                        String strCloseStep2 = objectCloseStep2.getString("2");
                        JSONArray arrayCloseStep2= new JSONArray(strCloseStep2);
                        int[] intCloseStep2 = new int[9];
                        String strFieldCloseStep3 = reader.getJSONArray("feeds").getJSONObject(0).getString("field7");
                        JSONObject objectCloseStep3 = new JSONObject(strFieldCloseStep3);
                        String strCloseStep3 = objectCloseStep3.getString("3");
                        JSONArray arrayCloseStep3= new JSONArray(strCloseStep3);
                        int[] intCloseStep3 = new int[9];

                        for(int i = 0; i < MAX_MOTOR; i++){
                            intOpenStep1[i] = arrayOpenStep1.getInt(i);
                            intOpenStep2[i] = arrayOpenStep2.getInt(i);
                            intOpenStep3[i] = arrayOpenStep3.getInt(i);
                            intCloseStep1[i] = arrayCloseStep1.getInt(i);
                            intCloseStep2[i] = arrayCloseStep2.getInt(i);
                            intCloseStep3[i] = arrayCloseStep3.getInt(i);
                        }

                        String field8 = reader.getJSONArray("feeds").getJSONObject(0).getString("field8");
                        JSONArray arrayField8 = new JSONArray(field8);
                        int[] Voltage = new int[9];
                        for(int i = 0; i < MAX_MOTOR; i++){
                            Voltage[i] = arrayField8.getInt(i);
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                layoutGetDataSetting.setVisibility(View.INVISIBLE);
                                //set Name
                                for(int i = 0; i < MAX_MOTOR; i++){
                                    editor.putString(name[i], NameMotor[i]);
                                    edtNameMotor[i].clearFocus();
//                                    edtNameMotor[i].setCursorVisible(false);
                                }
                                editor.commit();
                                for(int i = 0; i < MAX_MOTOR; i++){
                                    edtNameMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
                                    txtNameMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
                                    txtNameOpenMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
                                    txtNameCloseMotor[i].setText(sharedPreferences.getString(name[i], name[i]));
                                }
                                //Set Min Current
                                for(int i = 0; i < MAX_MOTOR; i++){
                                    edtMinMotor[i].setText(String.valueOf(MinCurrent[i]));
                                }
                                //Set Max Current
                                for(int i = 0; i < MAX_MOTOR; i++){
                                    edtMaxMotor[i].setText(String.valueOf(MaxCurrent[i]));
                                }
                                //Set Reverse
                                for(int i = 0; i < MAX_MOTOR; i++){
                                    if(Reverse[i] == 1){
                                        checkReverseMotor[i].setChecked(true);
                                    }
                                    else{
                                        checkReverseMotor[i].setChecked(false);
                                    }
                                }
                                //Set Disable
                                for(int i = 0; i < MAX_MOTOR; i++){
                                    if(Disable[i] == 1){
                                        checkDisableMotor[i].setChecked(true);
                                    }
                                    else{
                                        checkDisableMotor[i].setChecked(false);
                                    }
                                }
                                //set Step Open Close
                                for(int i = 0; i < MAX_MOTOR; i++){
                                    spnOpenStep1Motor[i].setSelection(intOpenStep1[i]);
                                    spnOpenStep2Motor[i].setSelection(intOpenStep2[i]);
                                    spnOpenStep3Motor[i].setSelection(intOpenStep3[i]);
                                    spnCloseStep1Motor[i].setSelection(intCloseStep1[i]);
                                    spnCloseStep2Motor[i].setSelection(intCloseStep2[i]);
                                    spnCloseStep3Motor[i].setSelection(intCloseStep3[i]);
                                }
                                //Voltage
                                for(int i = 0; i < MAX_MOTOR; i++){
                                    spnVoltageMotor[i].setSelection(Voltage[i]);
                                }
                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("http_request", e.toString());
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                layoutGetDataSetting.setVisibility(View.INVISIBLE);
                                Toast.makeText(MainActivity.this, "Không thể lấy dữ liệu!", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
                else if(flagSelectSaveOkSettingLayout){
                    flagSelectSaveOkSettingLayout = false;
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            layoutSaveDataSetting.setVisibility(View.INVISIBLE);
                            layoutGetDataSetting.setVisibility(View.INVISIBLE);
                            proBarLoadingSaveDataSetting.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }
        });
    }

//    String getDataFromUrl(String url) throws IOException {
//        cdStartHttpRequest = new CountDownLatch(1);
//        final String[] data = {""};
//        // Instantiate the RequestQueue.
//        RequestQueue queue = Volley.newRequestQueue(this);
////        url = "https://www.google.com";
//
////        Request a string response from the provided URL.
//        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        // Display the first 500 characters of the response string.
//                        data[0] = response;
//                        Log.d("http_request", response);
//                        cdStartHttpRequest.countDown();
//                    }
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                Log.d("http_request", error.toString());
//            }
//        });
//
////        Add the request to the RequestQueue.
//        queue.add(stringRequest);
////        cdStartHttpRequest.countDown();
//        return data[0];
//    }

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
                Log.e(TAG, "cancel: close() of mmSocket in ConnectThread failed. " + e.getMessage());
            }
        }
    }
    private void connected(BluetoothSocket mmSocket) {
        Log.d(TAG, "connected: Starting.");
        pgbRefreshListDevice.setVisibility(View.INVISIBLE);

        // Start the thread to manage the connection and perform transmissions
        mConnectedThread = new ConnectedThread(mmSocket);
        mConnectedThread.start();

//        byte[] bytes = "abcd".getBytes(Charset.defaultCharset());
//        mConnectedThread.write(bytes);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                // Stuff that updates the UI

                layoutListDevice.setVisibility(View.INVISIBLE);
                pgbRefreshListDevice.setVisibility(View.INVISIBLE);

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
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        try {
//                                            txtMaxMotor[i].setText(String.valueOf(array.getInt(2*MAX_MOTOR+i)));
                                            if(array.getInt(3*MAX_MOTOR+i) == 1){
                                                checkDisableMotor[i].setChecked(true);
                                            }
                                            else{
                                                checkDisableMotor[i].setChecked(false);
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
                        //current very 0.5s
                        else if(reader.has("1")){
                            //1 - 9: current
                            //10 : battery voltage
                            //11 : total power
                            //12 : mode run board
                            double data[] = new double[15];

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
                                    if(array.length() >= MAX_MOTOR){
                                        txtPinVoltage.setText(String.valueOf(data[9]) + "V");
                                        txtTotalPower.setText("Total: " + String.valueOf(data[10]) + "mAh");
                                        if(data[11] == 1){
                                            txtModeRunBoard.setText("Mode: Auto");
                                        }else{
                                            txtModeRunBoard.setText("Mode: Manual");
                                        }
                                    }
                                }
                            });
                        }
                        //set voltage
                        else if(reader.has("4")){
                            JSONArray array= reader.getJSONArray("4");
//                            Log.d(TAG, "InputStream: " + data[0]);
                            runOnUiThread(new Runnable() {
                                @SuppressLint("SetTextI18n")
                                @Override
                                public void run() {
                                    //-------
                                    for(int i =0; i < MAX_MOTOR; i++){
                                        try {
                                            spnVoltageMotor[i].setSelection(array.getInt(i));
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
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
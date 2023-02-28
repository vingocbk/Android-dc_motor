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
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
//import android.os.FileUtils;
import android.os.ParcelUuid;

import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
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
import java.util.Timer;
import java.util.TimerTask;
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
    ImageView imgBackground, imgSelectBackground, imgMenuListDevice, imgBluetoothConnection,
            imgSaveDataSetting, imgGetDataSetting, imgBackGetSetting, imgLoadDataFromLocalFile;
    View layoutSetup, layoutListDevice, layoutSetup2, layoutSetupData, layoutSetupStep, layoutSaveDataSetting, layoutGetDataSetting;
    TextView txtBackSetting, txtBackListDevice, txtNameBluetoothConnection, txtBackSetting2, txtNextSetting;
    TextView[] txtNameGetSetting = new TextView[3];
    Button btnCancelSaveSetting, btnOkSaveSetting;
    EditText edtSaveNameSetting;
    Spinner spnSelectNumberSetting;

    RelativeLayout[] rlGetDataSetting = new RelativeLayout[3];

    ProgressBar pgbRefreshListDevice;
    ListView lvListDevice;
    //------------------ main menu---------------------
    EditText[] edtNameMotor = new EditText[MAX_MOTOR];
    Button[] btnOpenMotor = new Button[MAX_MOTOR];
    Button[] btnStopMotor = new Button[MAX_MOTOR];
    Button[] btnCloseMotor = new Button[MAX_MOTOR];
    Button btnSaveNameMotor, btnResetTotalPower;
    ImageView imgRefreshVoltage;
    TextView txtPinVoltage, txtTotalPower, txtModeRunBoard, txtModalLoading;

    //---------------------------------------------------
    TextView[] txtNameMotor = new TextView[MAX_MOTOR];
    EditText[] edtMinCurrentMotor = new EditText[MAX_MOTOR];
    EditText[] edtMaxCurrentMotor = new EditText[MAX_MOTOR];
    CheckBox[] cbSelectMotor = new CheckBox[MAX_MOTOR];
    CheckBox[] cbSelectServo = new CheckBox[MAX_MOTOR];
    CheckBox[] cbReverseMotor = new CheckBox[MAX_MOTOR];
    EditText[] edtStartAngleServo = new EditText[MAX_MOTOR];
    EditText[] edtEndAngleServo = new EditText[MAX_MOTOR];
    EditText[] edtRunTimeServo = new EditText[MAX_MOTOR];
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
    public static int REQUEST_CODE_WRITE_PERMISSION = 10;
    public static int REQUEST_CODE_READ_PERMISSION = 11;
    public static int REQUEST_CODE_SELECT_IMAGE = 2;
    public static int REQUEST_CODE_SELECT_FILE_SETTING = 3;
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

    String nameUri = "Uri";
    String IMAGES_FOLDER_NAME = "Landing";
    File fileSaveText;
    String file_folder_name_save_setting = "LandingGear";
    String file_name_local_save_setting = "LandingGear1.txt";
    String file_name_download_save_setting = "fileNameDC";
    String strIndexName = "index";
    int indexNameImageBackGround = 0;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide(); // hide the title bar
//        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); //enable full screen
        setContentView(R.layout.activity_main);

        CheckPermission();
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
                if(edtSaveNameSetting.getText().toString().equals("")){
                    Toast.makeText(MainActivity.this, "Fill Name Setting!", Toast.LENGTH_SHORT).show();
                    return;
                }
                layoutSaveDataSetting.setVisibility(View.INVISIBLE);
                layoutGetDataSetting.setVisibility(View.INVISIBLE);
                int index = spnSelectNumberSetting.getSelectedItemPosition();
                txtNameGetSetting[index].setText(edtSaveNameSetting.getText().toString());
                String dataLocal = getDataSettingLocal();
                try {
                    JSONArray jsonArraySetting = new JSONArray(dataLocal);
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("name setting", edtSaveNameSetting.getText().toString());
                    JSONObject jsonObjectData = new JSONObject();
                    JSONArray jsonArrayNameMotor = new JSONArray();
                    JSONArray jsonArrayMinCurrent = new JSONArray();
                    JSONArray jsonArrayMaxCurrent = new JSONArray();
                    JSONArray jsonArraySelectMotor = new JSONArray();
                    JSONArray jsonArraySelectServo = new JSONArray();
                    JSONArray jsonArrayReverse = new JSONArray();
                    JSONArray jsonArrayStartAngle = new JSONArray();
                    JSONArray jsonArrayEndAngle = new JSONArray();
                    JSONArray jsonArrayTimeServo = new JSONArray();
                    JSONArray jsonArrayOpenStep1 = new JSONArray();
                    JSONArray jsonArrayOpenStep2 = new JSONArray();
                    JSONArray jsonArrayOpenStep3 = new JSONArray();
                    JSONArray jsonArrayCloseStep1 = new JSONArray();
                    JSONArray jsonArrayCloseStep2 = new JSONArray();
                    JSONArray jsonArrayCloseStep3 = new JSONArray();
                    for(int i = 0; i < MAX_MOTOR; i++){
                        //save Name
                        jsonArrayNameMotor.put(edtNameMotor[i].getText());
                        jsonObjectData.put("name", jsonArrayNameMotor);
                        //Min Current
                        if(edtMinCurrentMotor[i].getText().toString().equals("")){
                            jsonArrayMinCurrent.put(-1);
                        }else{
                            jsonArrayMinCurrent.put(Integer.valueOf(edtMinCurrentMotor[i].getText().toString()));
                        }
                        jsonObjectData.put("minCurrent", jsonArrayMinCurrent);
                        //Max Current
                        if(edtMaxCurrentMotor[i].getText().toString().equals("")){
                            jsonArrayMaxCurrent.put(-1);
                        }else{
                            jsonArrayMaxCurrent.put(Integer.valueOf(edtMaxCurrentMotor[i].getText().toString()));
                        }
                        jsonObjectData.put("maxCurrent", jsonArrayMaxCurrent);
                        //Select Motor
                        jsonArraySelectMotor.put(cbSelectMotor[i].isChecked());
                        jsonObjectData.put("selectMotor", jsonArraySelectMotor);
                        //Select Servo
                        jsonArraySelectServo.put(cbSelectServo[i].isChecked());
                        jsonObjectData.put("selectServo", jsonArraySelectServo);
                        //Reverse
                        jsonArrayReverse.put(cbReverseMotor[i].isChecked());
                        jsonObjectData.put("reverse", jsonArrayReverse);
                        //Start Angle
                        if(edtStartAngleServo[i].getText().toString().equals("")){
                            jsonArrayStartAngle.put(-1);
                        }else{
                            jsonArrayStartAngle.put(Integer.valueOf(edtStartAngleServo[i].getText().toString()));
                        }
                        jsonObjectData.put("startAngle", jsonArrayStartAngle);
                        //End Angle
                        if(edtEndAngleServo[i].getText().toString().equals("")){
                            jsonArrayEndAngle.put(-1);
                        }else{
                            jsonArrayEndAngle.put(Integer.valueOf(edtEndAngleServo[i].getText().toString()));
                        }
                        jsonObjectData.put("endAngle", jsonArrayEndAngle);
                        //Time Servo
                        if(edtRunTimeServo[i].getText().toString().equals("")){
                            jsonArrayTimeServo.put(-1);
                        }else{
                            jsonArrayTimeServo.put(Integer.valueOf(edtRunTimeServo[i].getText().toString()));
                        }
                        jsonObjectData.put("timeServo", jsonArrayTimeServo);
                        //Step
                        jsonArrayOpenStep1.put(spnOpenStep1Motor[i].getSelectedItemPosition());
                        jsonObjectData.put("openStep1", jsonArrayOpenStep1);
                        jsonArrayOpenStep2.put(spnOpenStep2Motor[i].getSelectedItemPosition());
                        jsonObjectData.put("openStep2", jsonArrayOpenStep2);
                        jsonArrayOpenStep3.put(spnOpenStep3Motor[i].getSelectedItemPosition());
                        jsonObjectData.put("openStep3", jsonArrayOpenStep3);
                        jsonArrayCloseStep1.put(spnCloseStep1Motor[i].getSelectedItemPosition());
                        jsonObjectData.put("closeStep1", jsonArrayCloseStep1);
                        jsonArrayCloseStep2.put(spnCloseStep2Motor[i].getSelectedItemPosition());
                        jsonObjectData.put("closeStep2", jsonArrayCloseStep2);
                        jsonArrayCloseStep3.put(spnCloseStep3Motor[i].getSelectedItemPosition());
                        jsonObjectData.put("closeStep3", jsonArrayCloseStep3);
                    }

                    jsonObject.put("data", jsonObjectData);
                    jsonArraySetting.put(index, jsonObject);
                    dataLocal = jsonArraySetting.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Log.i("jsonObject put", dataLocal);
                saveTextFileToLocal(dataLocal);
                saveTextFileToDownloadFolder(dataLocal);
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
            }
        });
        imgBackGetSetting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutSaveDataSetting.setVisibility(View.INVISIBLE);
                layoutGetDataSetting.setVisibility(View.INVISIBLE);
            }
        });
        imgLoadDataFromLocalFile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                data.setType("*/*");
                data = Intent.createChooser(data, "Choose a file");
                if(data.resolveActivity(getPackageManager()) != null){
                    startActivityForResult(data, REQUEST_CODE_SELECT_FILE_SETTING);
                }
            }
        });

        for(int i = 0; i < 3; i++){
            int finalI = i;
            rlGetDataSetting[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    layoutGetDataSetting.setVisibility(View.INVISIBLE);
                    String data = getDataSettingLocal();
                    try {
                        JSONArray jsonArraySetting = new JSONArray(data);
                        JSONObject jsonObjectSetting = jsonArraySetting.getJSONObject(finalI);
                        txtModalLoading.setText("Model " + String.valueOf(finalI + 1) + ": "
                                + jsonObjectSetting.getString("name setting"));
                        JSONObject jsonObjectData = jsonObjectSetting.getJSONObject("data");
                        JSONArray jsonArrayName = jsonObjectData.getJSONArray("name");
                        JSONArray jsonArrayMinCurrent = jsonObjectData.getJSONArray("minCurrent");
                        JSONArray jsonArrayMaxCurrent = jsonObjectData.getJSONArray("maxCurrent");
                        JSONArray jsonArraySelectMotor = jsonObjectData.getJSONArray("selectMotor");
                        JSONArray jsonArraySelectServo = jsonObjectData.getJSONArray("selectServo");
                        JSONArray jsonArrayReverse = jsonObjectData.getJSONArray("reverse");
                        JSONArray jsonArrayStartAngle = jsonObjectData.getJSONArray("startAngle");
                        JSONArray jsonArrayEndAngle = jsonObjectData.getJSONArray("endAngle");
                        JSONArray jsonArrayTimeServo = jsonObjectData.getJSONArray("timeServo");
                        JSONArray jsonArrayOpenStep1 = jsonObjectData.getJSONArray("openStep1");
                        JSONArray jsonArrayOpenStep2 = jsonObjectData.getJSONArray("openStep2");
                        JSONArray jsonArrayOpenStep3 = jsonObjectData.getJSONArray("openStep3");
                        JSONArray jsonArrayCloseStep1 = jsonObjectData.getJSONArray("closeStep1");
                        JSONArray jsonArrayCloseStep2 = jsonObjectData.getJSONArray("closeStep2");
                        JSONArray jsonArrayCloseStep3 = jsonObjectData.getJSONArray("closeStep3");
                        for(int i = 0; i < MAX_MOTOR; i++){
                            //name
                            edtNameMotor[i].setText(jsonArrayName.getString(i));
                            //Min Current
                            if(jsonArrayMinCurrent.getInt(i) != -1){
                                edtMinCurrentMotor[i].setText(String.valueOf(jsonArrayMinCurrent.getInt(i)));
                            }
                            else{
                                edtMinCurrentMotor[i].getText().clear();
                            }
                            //Max Current
                            if(jsonArrayMaxCurrent.getInt(i) != -1){
                                edtMaxCurrentMotor[i].setText(String.valueOf(jsonArrayMaxCurrent.getInt(i)));
                            }
                            else{
                                edtMaxCurrentMotor[i].getText().clear();
                            }
                            //Select Motor
                            cbSelectMotor[i].setChecked(jsonArraySelectMotor.getBoolean(i));
                            //Select Servo
                            cbSelectServo[i].setChecked(jsonArraySelectServo.getBoolean(i));
                            //Reverse
                            cbReverseMotor[i].setChecked(jsonArrayReverse.getBoolean(i));
                            //Start Angle
                            if(jsonArrayStartAngle.getInt(i) != -1){
                                edtStartAngleServo[i].setText(String.valueOf(jsonArrayStartAngle.getInt(i)));
                            }
                            else{
                                edtStartAngleServo[i].getText().clear();
                            }
                            //End Angle
                            if(jsonArrayEndAngle.getInt(i) != -1){
                                edtEndAngleServo[i].setText(String.valueOf(jsonArrayEndAngle.getInt(i)));
                            }
                            else{
                                edtEndAngleServo[i].getText().clear();
                            }
                            //Time Servo
                            if(jsonArrayTimeServo.getInt(i) != -1){
                                edtRunTimeServo[i].setText(String.valueOf(jsonArrayTimeServo.getInt(i)));
                            }
                            else{
                                edtRunTimeServo[i].getText().clear();
                            }
                            //Step
                            spnOpenStep1Motor[i].setSelection(jsonArrayOpenStep1.getInt(i));
                            spnOpenStep2Motor[i].setSelection(jsonArrayOpenStep2.getInt(i));
                            spnOpenStep3Motor[i].setSelection(jsonArrayOpenStep3.getInt(i));
                            spnCloseStep1Motor[i].setSelection(jsonArrayCloseStep1.getInt(i));
                            spnCloseStep2Motor[i].setSelection(jsonArrayCloseStep2.getInt(i));
                            spnCloseStep3Motor[i].setSelection(jsonArrayCloseStep3.getInt(i));
                        }
                    } catch (JSONException e) {
                        Toast.makeText(MainActivity.this, "No Data!", Toast.LENGTH_SHORT).show();
                        e.printStackTrace();
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
                    if (mmDevice != null && isConnected(mmDevice) && txtModeRunBoard.getText().toString().contains("Manual")) {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"" +
                                Integer.toString(finalI) +
                                "\",\"command\":\"open\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Kết nối thiết bị, chuyển Manual!", Toast.LENGTH_SHORT).show();
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
                    if (mmDevice !=null && isConnected(mmDevice) && txtModeRunBoard.getText().toString().contains("Manual")) {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"" +
                                Integer.toString(finalI) +
                                "\",\"command\":\"stop\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Kết nối thiết bị, chuyển Manual!", Toast.LENGTH_SHORT).show();
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
                    if (mmDevice !=null && isConnected(mmDevice) && txtModeRunBoard.getText().toString().contains("Manual")) {
                        String data = "{\"type\":\"run_no_step\",\"name\":\"" +
                                Integer.toString(finalI) +
                                "\",\"command\":\"close\"}";
                        byte[] bytes = data.getBytes(Charset.defaultCharset());
                        mConnectedThread.write(bytes);
                        Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                        //Log.d(TAG,data);
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Kết nối thiết bị, chuyển Manual!", Toast.LENGTH_SHORT).show();
                    }
                }

            });
        }
        //--------------------------------------------------------------------

        btnSaveDataAllMotor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mmDevice !=null && isConnected(mmDevice) && txtModeRunBoard.getText().toString().contains("Manual")) {
                    StringBuilder data = new StringBuilder("{\"type\":\"data\",\"1\":[");
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(!edtMinCurrentMotor[i].getText().toString().equals("")){
                            data.append(edtMinCurrentMotor[i].getText().toString());
                        }
                        else{
                            data.append("-1");
                        }
                        data.append(",");
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(!edtMaxCurrentMotor[i].getText().toString().equals("")){
                            data.append(edtMaxCurrentMotor[i].getText().toString());
                        }
                        else{
                            data.append("-1");
                        }
                        data.append(",");
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(cbSelectMotor[i].isChecked()){
                            data.append("1");
                        }
                        else{
                            data.append("0");
                        }
                        data.append(",");
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(cbSelectServo[i].isChecked()){
                            data.append("1");
                        }
                        else{
                            data.append("0");
                        }
                        data.append(",");
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(cbReverseMotor[i].isChecked()){
                            data.append("1");
                        }
                        else{
                            data.append("0");
                        }
                        data.append(",");
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(!edtStartAngleServo[i].getText().toString().equals("")){
                            data.append(edtStartAngleServo[i].getText().toString());
                        }
                        else{
                            data.append("-1");
                        }
                        data.append(",");
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(!edtEndAngleServo[i].getText().toString().equals("")){
                            data.append(edtEndAngleServo[i].getText().toString());
                        }
                        else{
                            data.append("-1");
                        }
                        data.append(",");
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        if(!edtRunTimeServo[i].getText().toString().equals("")){
                            data.append(edtRunTimeServo[i].getText().toString());
                        }
                        else{
                            data.append("-1");
                        }
                        if(i == (MAX_MOTOR - 1)){
                            break;
                        }
                        data.append(",");
                    }
                    data.append("]}");
                    byte[] bytes = data.toString().getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);

                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Kết nối thiết bị, chuyển Manual!", Toast.LENGTH_SHORT).show();
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
                if (mmDevice !=null && isConnected(mmDevice) && txtModeRunBoard.getText().toString().contains("Manual")) {
                    StringBuilder data = new StringBuilder("{\"type\":\"step\",\"1\":[");

                    for(int i = 0; i < MAX_MOTOR; i++){
                        data.append(String.valueOf(spnOpenStep1Motor[i].getSelectedItemPosition()));
                        data.append(",");
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        data.append(String.valueOf(spnOpenStep2Motor[i].getSelectedItemPosition()));
                        data.append(",");
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        data.append(String.valueOf(spnOpenStep3Motor[i].getSelectedItemPosition()));
                        data.append(",");
                    }
                    //---------Close------------------
                    for(int i = 0; i < MAX_MOTOR; i++){
                        data.append(String.valueOf(spnCloseStep1Motor[i].getSelectedItemPosition()));
                        data.append(",");
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        data.append(String.valueOf(spnCloseStep2Motor[i].getSelectedItemPosition()));
                        data.append(",");
                    }
                    for(int i = 0; i < MAX_MOTOR; i++){
                        data.append(String.valueOf(spnCloseStep3Motor[i].getSelectedItemPosition()));
                        if(i == (MAX_MOTOR - 1)){
                            break;
                        }
                        data.append(",");
                    }
                    data.append("]}");
                    byte[] bytes = data.toString().getBytes(Charset.defaultCharset());
                    mConnectedThread.write(bytes);
                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(MainActivity.this, "Kết nối thiết bị, chuyển Manual!", Toast.LENGTH_SHORT).show();
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
//                if (mmDevice !=null && isConnected(mmDevice) && txtModeRunBoard.getText().toString().contains("Manual")) {
//                    StringBuilder data = new StringBuilder("{\"type\":\"voltage\",\"1\":[");
//                    for(int i = 0; i < MAX_MOTOR; i++){
//                        data.append(String.valueOf(spnVoltageMotor[i].getSelectedItemPosition()));
//                        if(i == (MAX_MOTOR - 1)){
//                            break;
//                        }
//                        data.append(",");
//                    }
//                    data.append("]}");
//                    byte[] bytes = data.toString().getBytes(Charset.defaultCharset());
//                    mConnectedThread.write(bytes);
//                    Toast.makeText(MainActivity.this, "DONE", Toast.LENGTH_SHORT).show();
//                }
//                else{
//                    Toast.makeText(MainActivity.this, "Kết nối thiết bị, chuyển Manual!", Toast.LENGTH_SHORT).show();
//                }
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

        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true){
                    CountDownLatch latch = new CountDownLatch(1);
                    new Timer().schedule(
                        new TimerTask(){
                            @Override
                            public void run(){
                                //if you need some code to run when the delay expires
                                if (mmDevice == null || !isConnected(mmDevice)) {
                                    runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            Log.d(TAG, "checkBluetoothStatus");
                                            imgBluetoothConnection.setBackgroundResource(R.mipmap.ic_bluetooth_disconnected);
                                            txtNameBluetoothConnection.setText("No Connected");
                                            latch.countDown();
                                        }
                                    });
                                }
                            }
                        }, 500);
                    try {
                        latch.await();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        thread.start();
//        checkBluetoothStatus();
    }

    void CheckPermission(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CODE_WRITE_PERMISSION);
        }
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_PERMISSION);
        }
    }

    public void InitLayout(){
        imgBackground = findViewById(R.id.imgBackground);
        imgSelectBackground = findViewById(R.id.imgSelectBackground);
        imgSaveDataSetting = findViewById(R.id.imgSaveDataSetting);
        imgGetDataSetting = findViewById(R.id.imgGetDataSetting);
        imgBackGetSetting = findViewById(R.id.imgBackGetSetting);
        imgLoadDataFromLocalFile = findViewById(R.id.imgLoadDataFromLocalFile);

        edtSaveNameSetting = findViewById(R.id.edtSaveNameSetting);
        spnSelectNumberSetting = findViewById(R.id.spnSelectNumberSetting);

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
        txtModalLoading = findViewById(R.id.txtModalLoading);
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

        edtMinCurrentMotor[0] = findViewById(R.id.edtMinCurrentMotor1);
        edtMinCurrentMotor[1] = findViewById(R.id.edtMinCurrentMotor2);
        edtMinCurrentMotor[2] = findViewById(R.id.edtMinCurrentMotor3);
        edtMinCurrentMotor[3] = findViewById(R.id.edtMinCurrentMotor4);
        edtMinCurrentMotor[4] = findViewById(R.id.edtMinCurrentMotor5);
        edtMinCurrentMotor[5] = findViewById(R.id.edtMinCurrentMotor6);
        edtMinCurrentMotor[6] = findViewById(R.id.edtMinCurrentMotor7);
        edtMinCurrentMotor[7] = findViewById(R.id.edtMinCurrentMotor8);
        edtMinCurrentMotor[8] = findViewById(R.id.edtMinCurrentMotor9);

        spnVoltageMotor[0] = findViewById(R.id.spnVoltageMotor1);
        spnVoltageMotor[1] = findViewById(R.id.spnVoltageMotor2);
        spnVoltageMotor[2] = findViewById(R.id.spnVoltageMotor3);
        spnVoltageMotor[3] = findViewById(R.id.spnVoltageMotor4);
        spnVoltageMotor[4] = findViewById(R.id.spnVoltageMotor5);
        spnVoltageMotor[5] = findViewById(R.id.spnVoltageMotor6);
        spnVoltageMotor[6] = findViewById(R.id.spnVoltageMotor7);
        spnVoltageMotor[7] = findViewById(R.id.spnVoltageMotor8);
        spnVoltageMotor[8] = findViewById(R.id.spnVoltageMotor9);

        edtMaxCurrentMotor[0] = findViewById(R.id.edtMaxCurrentMotor1);
        edtMaxCurrentMotor[1] = findViewById(R.id.edtMaxCurrentMotor2);
        edtMaxCurrentMotor[2] = findViewById(R.id.edtMaxCurrentMotor3);
        edtMaxCurrentMotor[3] = findViewById(R.id.edtMaxCurrentMotor4);
        edtMaxCurrentMotor[4] = findViewById(R.id.edtMaxCurrentMotor5);
        edtMaxCurrentMotor[5] = findViewById(R.id.edtMaxCurrentMotor6);
        edtMaxCurrentMotor[6] = findViewById(R.id.edtMaxCurrentMotor7);
        edtMaxCurrentMotor[7] = findViewById(R.id.edtMaxCurrentMotor8);
        edtMaxCurrentMotor[8] = findViewById(R.id.edtMaxCurrentMotor9);

        cbSelectMotor[0] = findViewById(R.id.cbSelectMotor1);
        cbSelectMotor[1] = findViewById(R.id.cbSelectMotor2);
        cbSelectMotor[2] = findViewById(R.id.cbSelectMotor3);
        cbSelectMotor[3] = findViewById(R.id.cbSelectMotor4);
        cbSelectMotor[4] = findViewById(R.id.cbSelectMotor5);
        cbSelectMotor[5] = findViewById(R.id.cbSelectMotor6);
        cbSelectMotor[6] = findViewById(R.id.cbSelectMotor7);
        cbSelectMotor[7] = findViewById(R.id.cbSelectMotor8);
        cbSelectMotor[8] = findViewById(R.id.cbSelectMotor9);

        cbSelectServo[0] = findViewById(R.id.cbSelectServo1);
        cbSelectServo[1] = findViewById(R.id.cbSelectServo2);
        cbSelectServo[2] = findViewById(R.id.cbSelectServo3);
        cbSelectServo[3] = findViewById(R.id.cbSelectServo4);
        cbSelectServo[4] = findViewById(R.id.cbSelectServo5);
        cbSelectServo[5] = findViewById(R.id.cbSelectServo6);
        cbSelectServo[6] = findViewById(R.id.cbSelectServo7);
        cbSelectServo[7] = findViewById(R.id.cbSelectServo8);
        cbSelectServo[8] = findViewById(R.id.cbSelectServo9);

        cbReverseMotor[0] = findViewById(R.id.cbReverseMotor1);
        cbReverseMotor[1] = findViewById(R.id.cbReverseMotor2);
        cbReverseMotor[2] = findViewById(R.id.cbReverseMotor3);
        cbReverseMotor[3] = findViewById(R.id.cbReverseMotor4);
        cbReverseMotor[4] = findViewById(R.id.cbReverseMotor5);
        cbReverseMotor[5] = findViewById(R.id.cbReverseMotor6);
        cbReverseMotor[6] = findViewById(R.id.cbReverseMotor7);
        cbReverseMotor[7] = findViewById(R.id.cbReverseMotor8);
        cbReverseMotor[8] = findViewById(R.id.cbReverseMotor9);

        edtStartAngleServo[0] = findViewById(R.id.edtStartAngleServo1);
        edtStartAngleServo[1] = findViewById(R.id.edtStartAngleServo2);
        edtStartAngleServo[2] = findViewById(R.id.edtStartAngleServo3);
        edtStartAngleServo[3] = findViewById(R.id.edtStartAngleServo4);
        edtStartAngleServo[4] = findViewById(R.id.edtStartAngleServo5);
        edtStartAngleServo[5] = findViewById(R.id.edtStartAngleServo6);
        edtStartAngleServo[6] = findViewById(R.id.edtStartAngleServo7);
        edtStartAngleServo[7] = findViewById(R.id.edtStartAngleServo8);
        edtStartAngleServo[8] = findViewById(R.id.edtStartAngleServo9);

        edtEndAngleServo[0] = findViewById(R.id.edtEndAngleServo1);
        edtEndAngleServo[1] = findViewById(R.id.edtEndAngleServo2);
        edtEndAngleServo[2] = findViewById(R.id.edtEndAngleServo3);
        edtEndAngleServo[3] = findViewById(R.id.edtEndAngleServo4);
        edtEndAngleServo[4] = findViewById(R.id.edtEndAngleServo5);
        edtEndAngleServo[5] = findViewById(R.id.edtEndAngleServo6);
        edtEndAngleServo[6] = findViewById(R.id.edtEndAngleServo7);
        edtEndAngleServo[7] = findViewById(R.id.edtEndAngleServo8);
        edtEndAngleServo[8] = findViewById(R.id.edtEndAngleServo9);

        edtRunTimeServo[0] = findViewById(R.id.edtRunTimeServo1);
        edtRunTimeServo[1] = findViewById(R.id.edtRunTimeServo2);
        edtRunTimeServo[2] = findViewById(R.id.edtRunTimeServo3);
        edtRunTimeServo[3] = findViewById(R.id.edtRunTimeServo4);
        edtRunTimeServo[4] = findViewById(R.id.edtRunTimeServo5);
        edtRunTimeServo[5] = findViewById(R.id.edtRunTimeServo6);
        edtRunTimeServo[6] = findViewById(R.id.edtRunTimeServo7);
        edtRunTimeServo[7] = findViewById(R.id.edtRunTimeServo8);
        edtRunTimeServo[8] = findViewById(R.id.edtRunTimeServo9);

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
        listSetting.add("Modal 1");
        listSetting.add("Modal 2");
        listSetting.add("Modal 3");
        //ArrayAdapter spinAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, listSetting);
        ArrayAdapter spinnerAdapterSetting = new ArrayAdapter<>(this, R.layout.spinner_list, listSetting);
        spinnerAdapterSetting.setDropDownViewResource(R.layout.spinner_list);
//        spnSettingMotor[0].setAdapter(spinnerAdapterSetting);
        spnSelectNumberSetting.setAdapter(spinnerAdapterSetting);

        //------------------------------------------------------------------------------------------------------
//        flagSelectGetSettingLayout = true;
//        try {
//            getDataFromUrl(urlGetNameSetting);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.d("http_request", e.toString());
//        }
        readSettingFromLocalBegin();

    }

    void readSettingFromLocalBegin(){
        fileSaveText = new File(MainActivity.this.getFilesDir(),file_name_local_save_setting);
        if (!fileSaveText.exists()){
            try{
                fileSaveText.createNewFile();
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
//        //read data from file
//        int length = (int) fileSaveText.length();
//        byte[] bytes = new byte[length];
//        FileInputStream in = null;
//        try {
//            in = new FileInputStream(fileSaveText);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }
//        try {
//            if(in != null){
//                in.read(bytes);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        } finally {
//            try {
//                if(in != null){
//                    in.close();
//                }
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        String contentsLoadSettingLocalFile = getDataSettingLocal();
        Log.i("jsonObject put", "test: " + contentsLoadSettingLocalFile);
        if(contentsLoadSettingLocalFile.equals("")){
            JSONArray jsonArraySetting = new JSONArray();
            for(int i = 0; i < 3; i++){
                JSONObject jsonObjectSetting1 = new JSONObject();
                try {
                    jsonObjectSetting1.put("name setting", "No Data");
                    jsonObjectSetting1.put("data", null);
                    jsonArraySetting.put(jsonObjectSetting1);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            contentsLoadSettingLocalFile = jsonArraySetting.toString();
            Log.i("jsonObject put", contentsLoadSettingLocalFile);
            //write data begin to local file
            saveTextFileToLocal(contentsLoadSettingLocalFile);
        }
        try {
            JSONArray jsonArraySetting = new JSONArray(contentsLoadSettingLocalFile);
            for(int i = 0; i < jsonArraySetting.length(); i++){
                JSONObject jsonObjectSetting = jsonArraySetting.getJSONObject(i);
                txtNameGetSetting[i].setText(jsonObjectSetting.getString("name setting"));
            }
        } catch (JSONException e) {
            Toast.makeText(MainActivity.this, "Can,t load data setting", Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }


    }

    String getDataSettingLocal(){
        //read data from file
        int length = (int) fileSaveText.length();
        byte[] bytes = new byte[length];
        FileInputStream in = null;
        try {
            in = new FileInputStream(fileSaveText);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return new String(bytes);
    }

    public void saveTextFileToLocal(String content){
        try {
            FileOutputStream fos = new FileOutputStream(fileSaveText);
            try {
                fos.write(content.getBytes());
                fos.close();
//                Toast.makeText(MainActivity.this, "save!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
//                Toast.makeText(MainActivity.this, "error save!", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
//            Toast.makeText(MainActivity.this, "file not found!", Toast.LENGTH_SHORT).show();
        }
    }
    public void saveTextFileToDownloadFolder(String content){
        File fileDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),file_folder_name_save_setting);
        if (!fileDir.exists())
        {
            fileDir.mkdirs();
        }
        File fileSaveTextDownload;
        String file_name = sharedPreferences.getString(file_name_download_save_setting, "");
        if(file_name.equals("")){
            int count = 0;
            while(true){
                fileSaveTextDownload = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                        + "/" + file_folder_name_save_setting,file_folder_name_save_setting + String.valueOf(count)+".txt");
                if(!fileSaveTextDownload.exists()){
                    editor.putString(file_name_download_save_setting, file_folder_name_save_setting + String.valueOf(count)+".txt");
                    break;
                }
                if(fileSaveTextDownload.exists()){
                    if(fileSaveTextDownload.delete()){
                        editor.putString(file_name_download_save_setting, file_folder_name_save_setting + String.valueOf(count)+".txt");
                        break;
                    }
                    Log.i("jsonObject put", fileSaveTextDownload.getAbsolutePath());
                }
                count++;
            }
        }
        else{
            fileSaveTextDownload = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
                    + "/" + file_folder_name_save_setting,file_name);
        }

        if(!fileSaveTextDownload.exists()){
            try {
                fileSaveTextDownload.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            FileOutputStream fos = new FileOutputStream(fileSaveTextDownload);
            try {
                fos.write(content.getBytes());
                fos.close();
                Toast.makeText(MainActivity.this, "save!", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this, "error save!", Toast.LENGTH_SHORT).show();
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            Toast.makeText(MainActivity.this, "file not found!", Toast.LENGTH_SHORT).show();
        }
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
        if(requestCode == REQUEST_CODE_WRITE_PERMISSION){
            if(grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(MainActivity.this, "Permission write granted!", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(MainActivity.this, "Permission write not granted!", Toast.LENGTH_SHORT).show();
                finish();
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
        if(requestCode == REQUEST_CODE_SELECT_FILE_SETTING && resultCode == RESULT_OK){
            if(data != null){
                Uri uri = data.getData();
                byte[] bytes = getBytesFromUri(getApplicationContext(), uri);
                String dataFile = new String(bytes);
                try {
                    JSONArray jsonArray = new JSONArray(dataFile);
                    for(int i = 0; i < jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        txtNameGetSetting[i].setText(jsonObject.getString("name setting"));
//                        JSONObject jsonObjectData = jsonObject.getJSONObject("data");
                    }
                    saveTextFileToLocal(dataFile);
                } catch (JSONException e) {
                    Toast.makeText(MainActivity.this, "Can't Load Data", Toast.LENGTH_SHORT).show();
                    e.printStackTrace();
                }
            }
        }
    }
    byte[] getBytesFromUri(Context context, Uri uri){
        InputStream iStream = null;
        try {
            iStream = context.getContentResolver().openInputStream(uri);
            ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int len = 0;
            while ( (len = iStream.read(buffer)) != -1){
                byteBuffer.write(buffer, 0, len);
            }
            return byteBuffer.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

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
        if(mmSocket.isConnected()){
            Log.d(TAG, "connected: Starting.");
            pgbRefreshListDevice.setVisibility(View.INVISIBLE);
            // Start the thread to manage the connection and perform transmissions
            mConnectedThread = new ConnectedThread(mmSocket);
            mConnectedThread.start();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Stuff that updates the UI
                    layoutListDevice.setVisibility(View.INVISIBLE);
                    pgbRefreshListDevice.setVisibility(View.INVISIBLE);
                    imgBluetoothConnection.setBackgroundResource(R.mipmap.ic_bluetooth_connected);
                    txtNameBluetoothConnection.setText(deviceName);
                    Toast.makeText(MainActivity.this, "Connected!", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else{
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // Stuff that updates the UI
                    layoutListDevice.setVisibility(View.INVISIBLE);
                    pgbRefreshListDevice.setVisibility(View.INVISIBLE);
                    imgBluetoothConnection.setBackgroundResource(R.mipmap.ic_bluetooth_disconnected);
                    txtNameBluetoothConnection.setText("No Connected");
                    Toast.makeText(MainActivity.this, "Can't connect to device!", Toast.LENGTH_SHORT).show();
                }
            });
        }

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
                        Log.d("InputStream", "InputStream: " + incomingMessage);
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
                                            edtMinCurrentMotor[i].setText(String.valueOf(array.getInt(i)));
                                            edtMaxCurrentMotor[i].setText(String.valueOf(array.getInt(MAX_MOTOR+i)));
                                            if(array.getInt(2*MAX_MOTOR+i) == 1){
                                                cbSelectMotor[i].setChecked(true);
                                            }
                                            else{
                                                cbSelectMotor[i].setChecked(false);
                                            }
                                            if(array.getInt(3*MAX_MOTOR+i) == 1){
                                                cbSelectServo[i].setChecked(true);
                                            }
                                            else{
                                                cbSelectServo[i].setChecked(false);
                                            }
                                            if(array.getInt(4*MAX_MOTOR+i) == 1){
                                                cbReverseMotor[i].setChecked(true);
                                            }
                                            else{
                                                cbReverseMotor[i].setChecked(false);
                                            }
                                            edtStartAngleServo[i].setText(String.valueOf(array.getInt(5*MAX_MOTOR+i)));
                                            edtEndAngleServo[i].setText(String.valueOf(array.getInt(6*MAX_MOTOR+i)));
                                            edtRunTimeServo[i].setText(String.valueOf(array.getInt(7*MAX_MOTOR+i)));

                                        } catch (JSONException e) {
//                                            Log.d(TAG, "InputStream: " + e.toString());
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
//                                    Log.e("InputStream", "write: Error reading Input Stream. " + e.getMessage());
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
                                        txtPinVoltage.setText("Pin: " + String.valueOf(data[9]) + "V");
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
//                    Log.e("InputStream", "write: Error reading Input Stream. " + e.getMessage());
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
package com.example.smart_box_app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.io.InputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class MainActivity extends AppCompatActivity {

    public static TextView textResult;
    Button btnScan;
    MediaPlayer mediaPlayer = new MediaPlayer();
    public String URL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textResult = (TextView) findViewById(R.id.textResult);
        textResult.setAlpha(0.0f);
        btnScan = (Button) findViewById(R.id.btnScan);
        storagePermissions();

        /**
         * Function for scanning QR code
         */
        btnScan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PermissionListener permissionListener = new PermissionListener() {
                    @Override
                    public void onPermissionGranted() {
                        Toast.makeText(MainActivity.this, "Permission given", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), ScanCodeActivity.class));
                    }
                    @Override
                    public void onPermissionDenied(List<String> deniedPermissions) {
                        Toast.makeText(MainActivity.this, "Permission not given", Toast.LENGTH_SHORT).show();
                    }
                };
                TedPermission.with(MainActivity.this)
                    .setPermissionListener(permissionListener)
                    .setPermissions(Manifest.permission.CAMERA)
                    .check();
            }
        });

    }

    public void storagePermissions(){
        PermissionListener permissionListener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                Toast.makeText(MainActivity.this, "Permission given", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(MainActivity.this, "Permission not given", Toast.LENGTH_SHORT).show();
            }
        };
        TedPermission.with(MainActivity.this)
                .setPermissionListener(permissionListener)
                .setPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .check();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(textResult.getText() != "" && textResult.getText().length() > 6){
            String subString = textResult.getText().toString().substring(3,9);
            URL = "http://api-test.direct4.me/Sandbox/PublicAccess/V1/api/access/OpenBox?boxID="+subString+"&tokenFormat=2";
            getApi();
        }else if(textResult.getText() != ""){
            URL = "http://api-test.direct4.me/Sandbox/PublicAccess/V1/api/access/OpenBox?boxID="+textResult.getText()+"&tokenFormat=2";
            getApi();
        }

    }

    /**
     * Getting Data from api
     */
    public void getApi(){
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            String data = jsonObject.getString("Data");
                            byte[] arrayByte = Base64.decode(data,Base64.DEFAULT);
                            File tempZip = File.createTempFile("kurchina", ".zip");
                            FileOutputStream fos = new FileOutputStream(tempZip);
                            fos.write(arrayByte);
                            fos.close();
                            if(unpackZip(tempZip.getPath())){
                                    showDialog("01232131");
                            }
                            tempZip.delete();
                        } catch (JSONException | UnsupportedEncodingException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }){

        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
    private boolean unpackZip(String path) throws IOException {
        File tempWav = File.createTempFile("token", ".wav");
        InputStream is;
        ZipInputStream zis;
        try
        {
            String filename;
            is = new FileInputStream(path);
            zis = new ZipInputStream(new BufferedInputStream(is));
            ZipEntry ze;
            byte[] buffer = new byte[1024];
            int count;

            while ((ze = zis.getNextEntry()) != null)
            {
                filename = ze.getName();
                if (ze.isDirectory()) {
                    File fmd = new File(path + filename);
                    fmd.mkdirs();
                    continue;
                }

                FileOutputStream fout = new FileOutputStream(tempWav);
                while ((count = zis.read(buffer)) != -1)
                {
                    fout.write(buffer, 0, count);
                }
                fout.close();
                zis.closeEntry();
                mediaPlayer.reset();
                FileInputStream fis = new FileInputStream(tempWav);
                mediaPlayer.setDataSource(fis.getFD());
                mediaPlayer.prepare();
                mediaPlayer.start();
            }

            tempWav.delete();
            textResult.setText("");
            zis.close();
        }
        catch(IOException e)
        {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    //Tukaj se bo shranjevala statistika odvisno ali bo uporabnik kliknil na Yes ali No
    public void showDialog(final String phone) throws Exception {
        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

        builder.setMessage("Did you successfully opened Direct4.me post box?");

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                //TODO
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which){
                //TODO
                dialog.dismiss();
            }
        });
        builder.show();
    }

}


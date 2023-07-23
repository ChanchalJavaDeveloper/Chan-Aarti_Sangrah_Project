package com.AartiSangrah;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.AartiSangrah.Data.clsSharedVariables;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
public class A4_AddAarti extends AppCompatActivity {
    TextView lblAartiTextName;
    EditText txt_titleHindi, txt_titleEnglish, txt_artiHindi, txt_artiEnglish;
    Button btnPhoto, btnUpload;
    ImageView image;
    RequestQueue requestQueue;
    int RC_TEXT = 1;
    String UrlUpload = clsSharedVariables.BaseUrl + "AartiAdd.php";
    private final int Gallery_code_Req = 1000;
    String status = "0";
    Uri TextFileuri;
    String AartiTextName;
    private ArrayList<HashMap<String, String>> arraylist;
    ProgressDialog progressDialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a4_add_aarti);

        txt_artiHindi = findViewById(R.id.A4_txt_aartiHindi);
        txt_titleHindi = findViewById(R.id.A4_txt_TitleHindi);
        txt_titleEnglish = findViewById(R.id.A4_txt_TitleEnglish);
        txt_artiEnglish = findViewById(R.id.A4_txt_aartiEnglish);
        image = findViewById(R.id.A4_Image);
        btnPhoto = findViewById(R.id.A4_btnPhoto);
        btnUpload = findViewById(R.id.A4_Arti_Upload);
        lblAartiTextName = findViewById(R.id.A4_lbl_AartiName);

        requestQueue = Volley.newRequestQueue(A4_AddAarti.this);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strAartiText = lblAartiTextName.getText().toString();
                String titleEnglish = txt_titleEnglish.getText().toString();
                String titleHindi = txt_titleHindi.getText().toString();
                String artiEnglish = txt_artiEnglish.getText().toString();
                String artiHindi = txt_artiHindi.getText().toString();

                if (titleEnglish.length() == 0) {

                    AlertDialog.Builder ad = new AlertDialog.Builder(A4_AddAarti.this);

                    ad.setTitle(Html.fromHtml("<font color='#800000'> ***Title Error***</font>"));
                    ad.setMessage(Html.fromHtml("<font color='#800000'> Enter Title </font>"));

                    AlertDialog add = ad.create();
                    add.show();

                } else if (strAartiText.length() == 0) {

                    btnPhoto.setBackgroundColor(0xFFFF0000);
                    AlertDialog.Builder ad = new AlertDialog.Builder(A4_AddAarti.this);

                    ad.setTitle(Html.fromHtml("<font color='#800000'> ***Aarti FileName Error***</font>"));
                    ad.setMessage(Html.fromHtml("<font color='#800000'> Select Text File </font>"));

                    AlertDialog add = ad.create();
                    add.show();
                } else {
                    CustomAsyncTask c1 = new CustomAsyncTask();
                    c1.execute();
                    status = "1";
                }
            }
        });

        btnPhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent, RC_TEXT);
            }
        });
    }

    @Override
    public void onBackPressed() {

        if (status.equals("0")) {
            super.onBackPressed();
        } else {
            Intent intent = new Intent(A4_AddAarti.this, A4_AddAarti.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == RC_TEXT) {
                TextFileuri = data.getData();
                String uriString = TextFileuri.toString();
                //lblAartiTextShow.setText(uriString);
                AartiTextName = GetFileName(TextFileuri, uriString);
                lblAartiTextName.setText(AartiTextName);
                btnPhoto.setBackgroundColor(0xFF37A207);

            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }


    public String GetFileName(Uri tUri, String tUriString) {

        File myFile = new File(tUriString);
        //String path = myFile.getAbsolutePath();
        String displayName = null;

        if (tUriString.startsWith("content://")) {
            Cursor cursor = null;
            try {
                cursor = this.getContentResolver().query(tUri, null, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {
                    @SuppressLint("Range") String CurrentFileName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    String extension = CurrentFileName.substring(CurrentFileName.lastIndexOf("."));
                    displayName = String.valueOf(System.currentTimeMillis()) + extension;

                }
            } finally {
                cursor.close();
            }
        } else if (tUriString.startsWith("file://")) {
            String CurrentFileName = myFile.getName();
            String extension = CurrentFileName.substring(CurrentFileName.lastIndexOf("."));
            displayName = String.valueOf(System.currentTimeMillis()) + extension;

        }
        return displayName;
    }

    private void uploadPDF(Uri tTextFileUri) {
        InputStream iStreamTextFile = null;

        try {
            iStreamTextFile = getContentResolver().openInputStream(tTextFileUri);
            final byte[] inputDataTextFile = getBytes(iStreamTextFile);

            VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UrlUpload,
                    new Response.Listener<NetworkResponse>() {
                        @Override
                        public void onResponse(NetworkResponse response) {
                            Log.d("ressssssoo", new String(response.data));
                            requestQueue.getCache().clear();
                            try {
                                JSONObject jsonObject = new JSONObject(new String(response.data));
                                Toast.makeText(getApplicationContext(), jsonObject.getString("Status"), Toast.LENGTH_SHORT).show();

                                jsonObject.toString().replace("\\\\", "");

                                if (jsonObject.getString("Status").equals("Success")) {
                                    Log.d("come::: >>>  ", "yessssss");
                                    arraylist = new ArrayList<HashMap<String, String>>();
                                    //JSONArray dataArray = jsonObject.getJSONArray("data");

//                                    for (int i = 0; i < dataArray.length(); i++) {
//                                        JSONObject dataobj = dataArray.getJSONObject(i);
//                                        String txt = dataobj.optString("FileName");
//                                        tv.setText(txt);
//                                    }
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }) {

                /*
                 * If you want to add more parameters with the image
                 * you can do it here
                 * here we have only one parameter with the image
                 * which is tags
                 * */
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();

                    params.put("TitleHindi", txt_titleHindi.getText().toString());  //add string parameters
                    params.put("TitleEnglish", txt_titleEnglish.getText().toString());  //add string parameters
                    params.put("DescriptionHindi", txt_artiHindi.getText().toString());  //add string parameters
                    params.put("DescriptionEnglish", txt_artiEnglish.getText().toString());  //add string parameters

                    return params;
                }

                /*
                 *pass files using below method
                 * */
                @Override
                protected Map<String, DataPart> getByteData() {
                    Map<String, DataPart> params = new HashMap<>();
                    params.put("FU_Photo", new DataPart(lblAartiTextName.getText().toString(), inputDataTextFile));

                    return params;
                }
            };
            volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(
                    0,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            requestQueue = Volley.newRequestQueue(A4_AddAarti.this);
            requestQueue.add(volleyMultipartRequest);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];

        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }
    private class CustomAsyncTask extends AsyncTask<Void, Void, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(A4_AddAarti.this);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(true);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(Void... params) {
            //do whatever you want
            try {
                uploadPDF(TextFileuri);

                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "";
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            btnUpload.setBackgroundColor(0xFF37A207);
            Toast.makeText(A4_AddAarti.this, "Aarti Successfully Uploaded", Toast.LENGTH_SHORT).show();
            //txt_titleHindi.setText("");
            //lblAartiTextName.setText("");
        }
    }
}

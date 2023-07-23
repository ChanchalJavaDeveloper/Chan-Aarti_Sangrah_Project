package com.AartiSangrah;

import androidx.appcompat.app.AppCompatActivity;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import com.android.volley.AuthFailureError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class A0_DownloadData extends AppCompatActivity {
    SQLiteDatabase dbSqlite;
    ProgressDialog pDialog;
    RequestQueue requestQueue;
    String UrgGetAarti = "https://api.sunshinesoftwares.com/Aarti/getAarti.php";
    //String UrgGetLastAartiId = "https://api.sunshinesoftwares.com/Aarti/getAartiLastId.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a0_download_data);

        dbSqlite = openOrCreateDatabase("db_Aarti", Context.MODE_PRIVATE, null);
        requestQueue = Volley.newRequestQueue(A0_DownloadData.this);

        StartDownload startDownload = new StartDownload();
        startDownload.execute();
    }

    private class StartDownload extends AsyncTask<Object, Object, Object> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(A0_DownloadData.this);
            pDialog.setMessage("Downloading  Data ....");
            pDialog.show();
        }

        @Override
        protected Object doInBackground(Object... objects) {
            try {
                CreateDB();
                //SaveContacts();
            } catch (Exception ex) {
                Log.d("error", ex.getMessage().toString());
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            if (pDialog.isShowing())
                pDialog.dismiss();
        }
    }

    @SuppressLint("Range")
    public void CreateDB() {
        int n;
        String AartiLastId = "0";

        //dbSqlite.execSQL("DROP TABLE IF EXISTS tbl_Settings  ");
        //dbSqlite.execSQL("DROP TABLE tbl_Settings");
        String qry = "CREATE TABLE IF NOT EXISTS tbl_Settings (AartiLastId  Integer NOT NULL)";
        dbSqlite.execSQL(qry);

        qry = "SELECT * FROM tbl_Settings";
        Cursor C = dbSqlite.rawQuery(qry, null);

        C.moveToLast();
        n = C.getCount();
        C.moveToFirst();

        if (n == 0) {
            dbSqlite.execSQL("INSERT INTO tbl_Settings (AartiLastId) VALUES (0);");
        } else {
            AartiLastId = C.getString(C.getColumnIndex("AartiLastId"));
        }

        //dbSqlite.execSQL("DROP TABLE tbl_aarti");

        dbSqlite.execSQL("CREATE TABLE IF NOT EXISTS tbl_aarti(Id1  Integer NOT NULL,  TitleHindi  varchar(500), TitleEnglish  varchar(4000), PhotoUrl  varchar(4000), DescriptionHindi  varchar(4000),  DescriptionEnglish  varchar(4000), HindiFilePath  varchar(4000), EnglishFilePath  varchar(4000));");
        UpdateAarti(AartiLastId);
    }

    private void UpdateAarti(String LastId) {
        AartiServerRequest aartiServerRequest = new AartiServerRequest(LastId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                String strId = "0", strTitleHindi, strTitleEnglish, strPhotoUrl,strDescriptionHindi,strDescriptionEnglish,strHindiFilePath,strEnglishFilePath, qry;
                try {

                    JSONArray jsonArray = new JSONArray(response);

                    int n = jsonArray.length();
                    for (int i = 0; i < n; i++) {
                        JSONObject obj = jsonArray.getJSONObject(i);
                        strId = obj.getString("Id1");
                        strTitleHindi = obj.getString("TitleHindi");
                        strTitleEnglish = obj.getString("TitleEnglish");
                        strPhotoUrl = obj.getString("PhotoUrl");
                        strDescriptionHindi = obj.getString("DescriptionHindi");
                        strDescriptionEnglish = obj.getString("DescriptionEnglish");
                        strHindiFilePath = obj.getString("HindiFilePath");
                        strEnglishFilePath = obj.getString("EnglishFilePath");

                        qry = "INSERT INTO tbl_Aarti (Id1, TitleHindi, TitleEnglish, PhotoUrl, DescriptionHindi, ";
                        qry = qry + " DescriptionEnglish, HindiFilePath, EnglishFilePath) VALUES ";
                        qry = qry + " (" + strId + ",'" + strTitleHindi + "','" + strTitleEnglish + "','" + strPhotoUrl + "',";
                        qry = qry + " '" + strDescriptionHindi + "','" + strDescriptionEnglish + "','" + strHindiFilePath + "',";
                        qry = qry + " '" + strEnglishFilePath + "')";
                        dbSqlite.execSQL(qry);
                    }

                    if (n > 0) {
                        qry = "UPDATE tbl_Settings SET AartiLastId=" + strId;
                        dbSqlite.execSQL(qry);
                    }

                    Intent intent = new Intent(A0_DownloadData.this, A3_AartiListSqLite.class);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        aartiServerRequest.setShouldCache(false);
        requestQueue.add(aartiServerRequest);
    }

    public class AartiServerRequest extends StringRequest {
        private Map<String, String> parameters;

        public AartiServerRequest(String LastId, Response.Listener<String> listener) {
            super(Method.POST, UrgGetAarti, listener, null);
            parameters = new HashMap<String, String>();
            parameters.put("LastId", LastId);
        }

        @Override
        protected Map<String, String> getParams() throws AuthFailureError {
            return parameters;
        }
    }
}


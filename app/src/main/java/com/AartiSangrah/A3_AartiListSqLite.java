package com.AartiSangrah;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.AartiSangrah.Data.clsAarti;
import com.AartiSangrah.Data.clsSharedVariables;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.ArrayList;
import java.util.List;

public class A3_AartiListSqLite extends AppCompatActivity {
    ListView lst;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;
    SQLiteDatabase dbSqlite;
    List<clsAarti> ColAarti;
    clsAarti objAarti;

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void TB_fun_Exit(View view) {
        finishAffinity();
        System.exit(0);
    }
    public void TB_fun_Home(View view) {
        startActivity(new Intent(this, A2_AartiList.class));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2_aarti_list);

        lst = findViewById(R.id.A2_Lst);
        requestQueue = Volley.newRequestQueue(A3_AartiListSqLite.this);
        dbSqlite = openOrCreateDatabase("db_Aarti", Context.MODE_PRIVATE, null);
        dbSqlite.execSQL("CREATE TABLE IF NOT EXISTS tbl_aarti(Id1  Integer NOT NULL,  TitleHindi  varchar(500), TitleEnglish  varchar(4000), PhotoUrl  varchar(4000), DescriptionHindi  varchar(4000),  DescriptionEnglish  varchar(4000), HindiFilePath  varchar(4000), EnglishFilePath  varchar(4000));");

        ColAarti = new ArrayList<clsAarti>();
        FillList();
    }

    @SuppressLint("Range")
    private void FillList() {
        String sql_query = "Select * From tbl_aarti";
        Cursor cur = dbSqlite.rawQuery(sql_query, null);
        cur.moveToLast();
        int n = cur.getCount();
        cur.moveToFirst();

        if (n != 0) {
            do {
                objAarti = new clsAarti(cur.getString(cur.getColumnIndex("Id1")), cur.getString(cur.getColumnIndex("TitleHindi")), cur.getString(cur.getColumnIndex("TitleEnglish")), cur.getString(cur.getColumnIndex("PhotoUrl")), cur.getString(cur.getColumnIndex("DescriptionHindi")), cur.getString(cur.getColumnIndex("DescriptionEnglish")), cur.getString(cur.getColumnIndex("HindiFilePath")), cur.getString(cur.getColumnIndex("EnglishFilePath")));
                ColAarti.add(objAarti);
            } while (cur.moveToNext());

            custAdpt adpt = new custAdpt(A3_AartiListSqLite.this);
            lst.setAdapter(adpt);
        }
    }

    public class custAdpt extends ArrayAdapter<clsAarti> {
        Context scontext;

        public custAdpt(Context context) {
            super(context, R.layout.activity_a2_aarti_list_row, ColAarti);
            scontext = context;
        }

        @Override
        public View getView(int position, View contextView, ViewGroup parent) {
            View row = contextView;
            ViewHolder vholder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(scontext.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.activity_a2_aarti_list_row, parent, false);
                vholder = new ViewHolder(row);
                row.setTag(vholder);
            } else {
                vholder = (ViewHolder) row.getTag();
            }
            clsAarti tData = ColAarti.get(position);

            Glide.with(A3_AartiListSqLite.this)
                    .load(clsSharedVariables.BaseUrlPhoto + tData.getPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(vholder.img);

            vholder.txtTitleHindi.setText(tData.getTitleHindi());
            vholder.txtTitleHindi.setTag(tData);
            vholder.txtTitleHindi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Id1, TitleHindi, TitleEnglish, DescriptionHindi, DescriptionEnglish;

                    clsAarti ttData = (clsAarti) ((TextView) view).getTag();
                    Id1 = ttData.getId1();
                    TitleHindi = ttData.getTitleHindi();
                    TitleEnglish = ttData.getTitleEnglish();
                    DescriptionHindi = ttData.getDescriptionHindi();
                    DescriptionEnglish = ttData.getDescriptionEnglish();

                    Intent ii = new Intent(A3_AartiListSqLite.this, A3_ShowAarti.class);
                    ii.putExtra("Id", Id1);
                    ii.putExtra("TitleHindi", TitleHindi);
                    ii.putExtra("TitleEnglish", TitleEnglish);
                    ii.putExtra("DescriptionHindi", DescriptionHindi);
                    ii.putExtra("DescriptionEnglish", DescriptionEnglish);
                    startActivity(ii);

                }
            });
            return row;
        }

        public class ViewHolder {
            TextView txtTitleHindi;
            ImageView img;

            public ViewHolder(View v) {
                txtTitleHindi = v.findViewById(R.id.A2_lbl_Title);
                img = v.findViewById(R.id.A2_img_Photo);

            }
        }
    }
}


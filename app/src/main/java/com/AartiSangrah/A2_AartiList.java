package com.AartiSangrah;
import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.AartiSangrah.Data.clsAarti;
import com.AartiSangrah.Data.clsParamPass;
import com.AartiSangrah.Data.clsSharedVariables;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
public class A2_AartiList extends AppCompatActivity implements SearchView.OnQueryTextListener{
    ListView lst;
    SearchView searchView;
    RequestQueue requestQueue;
    ProgressDialog progressDialog;

    int AddEditFlag = 0;
    List<clsAarti> ColAarti;
    clsAarti objAarti;
    custAdpt adpt;

    ImageView btnShowSearch;
    LinearLayout llHeading;
    String urlGet = clsSharedVariables.BaseUrl + "getAarti.php";

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void TB_fun_Exit(View view) {
        finishAffinity();
        System.exit(0);
    }


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a2_aarti_list);

        lst = findViewById(R.id.A2_Lst);
        searchView =findViewById(R.id.A2_searchView);
        requestQueue = Volley.newRequestQueue(A2_AartiList.this);

        btnShowSearch =findViewById(R.id.A2_btn_ShowSearch);
        llHeading =findViewById(R.id.A2_ll_Heading);

        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {

                llHeading.setVisibility(View.VISIBLE);
                searchView.setVisibility(View.GONE);

                return false;
            }
        });
        btnShowSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llHeading.setVisibility(View.GONE);
                searchView.setVisibility(View.VISIBLE);
                searchView.setIconified(false);}
});

        ColAarti = new ArrayList<clsAarti>();
        FillList();

    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        //adapter.getFilter().filter(s);
        return false;

    }

    @Override
    public boolean onQueryTextChange(String s) {
        adpt.getFilter().filter(s);
        return false;
    }
    private void FillList() {
        String FieldList, ValueList;

        FieldList = "LastId";
        ValueList = "0";
        progressDialog = new ProgressDialog(A2_AartiList.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        clsParamPass getTrainingData = new clsParamPass(urlGet, FieldList, ValueList, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                ColAarti.clear();

                JSONArray jsonArray = null;
                try {
                    jsonArray = new JSONArray(response);
                    int x = jsonArray.length();

                    for (int i = 0; i < x; i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        objAarti = new clsAarti(jsonObject.getString("Id1"), jsonObject.getString("TitleHindi"), jsonObject.getString("TitleEnglish"), jsonObject.getString("PhotoUrl"), jsonObject.getString("DescriptionHindi"), jsonObject.getString("DescriptionEnglish"), jsonObject.getString("HindiFilePath"), jsonObject.getString("EnglishFilePath"));
                        ColAarti.add(objAarti);
                    }

                    adpt = new custAdpt(A2_AartiList.this,ColAarti);
                    lst.setAdapter(adpt);

                    searchView.setOnQueryTextListener(A2_AartiList.this);

                    if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        getTrainingData.setShouldCache(false);
        requestQueue.add(getTrainingData);
    }

    public class custAdpt extends BaseAdapter implements Filterable {
        Context scontext;
        ValueFilter valueFilter;
        List<clsAarti> mStringFilterList;
        List<clsAarti> mColFeesList;

        public custAdpt(Context context, List<clsAarti> tColFeesList) {
            scontext = context;
            this.mColFeesList=tColFeesList;
            this.mStringFilterList=tColFeesList;
        }
        @Override
        public Filter getFilter() {
            if (valueFilter == null) {
                valueFilter = new ValueFilter();
            }
            return valueFilter;
        }

        private class ValueFilter extends Filter {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();

                if (constraint != null && constraint.length() > 0) {
                    ArrayList<clsAarti> filterList = new ArrayList<clsAarti>();
                    for (int i = 0; i < mStringFilterList.size(); i++) {
                        if ((mStringFilterList.get(i).getTitleEnglish().toUpperCase())
                                .contains(constraint.toString().toUpperCase())) {

                            clsAarti bean = new clsAarti(
                                    mStringFilterList.get(i).getId1(),
                                    mStringFilterList.get(i).getTitleHindi(),
                                    mStringFilterList.get(i).getTitleEnglish(),
                                    mStringFilterList.get(i).getPhotoUrl(),
                                    mStringFilterList.get(i).getDescriptionHindi(),
                                    mStringFilterList.get(i).getDescriptionEnglish(),
                                    mStringFilterList.get(i).getHindiFilePath(),
                                    mStringFilterList.get(i).getEnglishFilePath());
                            filterList.add(bean);
                        }
                    }
                    results.count = filterList.size();
                    results.values = filterList;
                } else {
                    results.count = mStringFilterList.size();
                    results.values = mStringFilterList;
                }
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                mColFeesList = (ArrayList<clsAarti>) results.values;
                notifyDataSetChanged();
            }
        }

        @Override
        public int getCount() {
            return mColFeesList.size();
        }

        @Override
        public Object getItem(int i) {
            return mColFeesList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int position, View contextView, ViewGroup parent) {
            View row = contextView;
            ViewHolder vholder;
            if (row == null) {
                LayoutInflater inflater = (LayoutInflater) scontext.getSystemService(scontext.LAYOUT_INFLATER_SERVICE);
                row = inflater.inflate(R.layout.activity_a2_aarti_list_row, parent, false);
                vholder = new ViewHolder(row);
                row.setTag(vholder);
            } else {
                vholder = (ViewHolder) row.getTag();
            }
            clsAarti tData = mColFeesList.get(position);

            Glide.with(A2_AartiList.this)
                    .load(clsSharedVariables.BaseUrlPhoto + tData.getPhotoUrl())
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(vholder.img);

            vholder.txtTitleHindi.setText(tData.getTitleHindi().trim());
            vholder.txtTitleHindi.setTag(tData);
            vholder.txtTitleHindi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Id1, TitleHindi, TitleEnglish, DescriptionHindi, DescriptionEnglish;

                    clsAarti ttData = (clsAarti) ((TextView) view).getTag();
                    Id1 = ttData.getId1();
                    TitleHindi = ttData.getTitleHindi().trim();
                    TitleEnglish = ttData.getTitleEnglish().trim();
                    DescriptionHindi = ttData.getDescriptionHindi().trim();
                    DescriptionEnglish = ttData.getDescriptionEnglish().trim();

                    Intent ii = new Intent(A2_AartiList.this, A3_ShowAarti.class);
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


//    public class custAdpt extends ArrayAdapter<clsAarti> {
//        Context scontext;
//
//        public custAdpt(Context context) {
//            super(context, R.layout.activity_a2_aarti_list_row, ColAarti);
//            scontext = context;
//        }
//
//        @Override
//        public View getView(int position, View contextView, ViewGroup parent) {
//            View row = contextView;
//            ViewHolder vholder;
//            if (row == null) {
//                LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(scontext.LAYOUT_INFLATER_SERVICE);
//                row = inflater.inflate(R.layout.activity_a2_aarti_list_row, parent, false);
//                vholder = new ViewHolder(row);
//                row.setTag(vholder);
//            } else {
//                vholder = (ViewHolder) row.getTag();
//            }
//            clsAarti tData = ColAarti.get(position);
//
//            Glide.with(A2_AartiList.this)
//                    .load(clsSharedVariables.BaseUrlPhoto + tData.getPhotoUrl())
//                    .diskCacheStrategy(DiskCacheStrategy.ALL)
//                    .into(vholder.img);
//
//            vholder.txtTitleHindi.setText(tData.getTitleHindi().trim());
//            vholder.txtTitleHindi.setTag(tData);
//            vholder.txtTitleHindi.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    String Id1, TitleHindi, TitleEnglish, DescriptionHindi, DescriptionEnglish;
//
//                    clsAarti ttData = (clsAarti) ((TextView) view).getTag();
//                    Id1 = ttData.getId1();
//                    TitleHindi = ttData.getTitleHindi();
//                    TitleEnglish = ttData.getTitleEnglish();
//                    DescriptionHindi = ttData.getDescriptionHindi();
//                    DescriptionEnglish = ttData.getDescriptionEnglish();
//
//                    Intent ii = new Intent(A2_AartiList.this, A3_ShowAarti.class);
//                    ii.putExtra("Id", Id1);
//                    ii.putExtra("TitleHindi", TitleHindi);
//                    ii.putExtra("TitleEnglish", TitleEnglish);
//                    ii.putExtra("DescriptionHindi", DescriptionHindi);
//                    ii.putExtra("DescriptionEnglish", DescriptionEnglish);
//                    startActivity(ii);
//                }
//            });
//            return row;
//        }
//
//
//    }
}

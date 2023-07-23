
package com.AartiSangrah;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
public class A3_ShowAarti extends AppCompatActivity {
    TextView txtTitle, txtDescription, btnHindi,btnEnglish;
    String Id1 = "", TitleHindi = "", TitleEnglish = "", DescriptionHindi = "", DescriptionEnglish = "";
    Integer TextSize = 20;
    ImageView btnZoomIn, btnZoomOut;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_a3_show_aarti);
        txtTitle = findViewById(R.id.A3_txt_Title);
        txtDescription = findViewById(R.id.A3_txt_Description);
        btnHindi = findViewById(R.id.A3_btn_Hindi);
        btnEnglish = findViewById(R.id.A3_btn_English);

//        //enable action bar in style.xml or androidmanifest.xml
//        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
//        getSupportActionBar().setCustomView(R.layout.custom_action_bar);
//
        btnZoomIn = findViewById(R.id.A3_btn_ZoomIn);
        btnZoomOut = findViewById(R.id.A3_btn_ZoomOut);
//        btnSaerch = findViewById(R.id.CAB1_btn_Search);
//        txtSearch = findViewById(R.id.CAB1_txt_Search);

        btnZoomIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextSize = TextSize + 4;
                txtDescription.setTextSize(TextSize);
            }
        });

        btnZoomOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextSize = TextSize - 4;
                txtDescription.setTextSize(TextSize);
            }
        });
        btnHindi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText(TitleHindi);
                txtDescription.setText(DescriptionHindi);
                btnHindi.setBackgroundColor(getColor(R.color.TabActive));
                btnEnglish.setBackgroundColor(getColor(R.color.TabInactive));
                btnEnglish.setTextColor(getColor(R.color.TabActive));
                btnHindi.setTextColor(getColor(R.color.TabInactive));
            }
        });
        btnEnglish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtTitle.setText(TitleEnglish);
                txtDescription.setText(DescriptionEnglish);
                btnHindi.setBackgroundColor(getColor(R.color.TabInactive));
                btnEnglish.setBackgroundColor(getColor(R.color.TabActive));
                btnEnglish.setTextColor(getColor(R.color.TabInactive));
                btnHindi.setTextColor(getColor(R.color.TabActive));
            }
        });

//        btnSaerch.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String s = txtSearch.getText().toString();
//                Toast.makeText(A3_ShowAarti.this, s, Toast.LENGTH_LONG).show();
//
//            }
//        });
//        txtDescription.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//
//
//                int action = MotionEventCompat.getActionMasked(event);
//
//
//                if (action == MotionEvent.ACTION_DOWN) {
//                    TextSize=TextSize-4;
//                    txtDescription.setTextSize(TextSize);
//
//                    Toast.makeText(A3_ShowAarti.this, "Action was DOWN", Toast.LENGTH_SHORT).show();
//                } else if (action == MotionEvent.ACTION_MOVE) {
//                    Toast.makeText(A3_ShowAarti.this, "Action was MOVE", Toast.LENGTH_SHORT).show();
//                } else if (action == MotionEvent.ACTION_UP) {
//                    TextSize=TextSize+4;
//                    txtDescription.setTextSize(TextSize);
//
//                    Toast.makeText(A3_ShowAarti.this, "Action was UP", Toast.LENGTH_SHORT).show();
//                } else if (action == MotionEvent.ACTION_CANCEL) {
//                    Toast.makeText(A3_ShowAarti.this, "Action was CANCEL", Toast.LENGTH_SHORT).show();
//                } else if (action == MotionEvent.ACTION_OUTSIDE) {
//                    Toast.makeText(A3_ShowAarti.this, "Movement occurred outside bounds of current screen element", Toast.LENGTH_SHORT).show();
//                }
//                return true;
//            }
//        });

        Intent ii = getIntent();
        Id1 = ii.getStringExtra("Id");
        TitleHindi = ii.getStringExtra("TitleHindi");
        TitleEnglish = ii.getStringExtra("TitleEnglish");
        DescriptionHindi = ii.getStringExtra("DescriptionHindi");
        DescriptionEnglish = ii.getStringExtra("DescriptionEnglish");

        if (!Id1.equals("")) {
            txtTitle.setText(TitleHindi);
            txtDescription.setText(DescriptionHindi);
        }
    }
}



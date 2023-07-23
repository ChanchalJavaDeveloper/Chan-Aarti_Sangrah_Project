package com.AartiSangrah;

import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


public class NDBaseActivity extends AppCompatActivity {
    ImageView ProfileImage;
    LayoutInflater layoutInflater;
    String ProfileName = "ISKCON";

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    public void setBaseContentView(int ChaildLayoutId) {
        layoutInflater = getLayoutInflater();

        View ChildcontentView = layoutInflater.inflate(ChaildLayoutId, null);
        View baseView = layoutInflater.inflate(R.layout.activity_ndbase, null);

        FrameLayout layout = baseView.findViewById(R.id.cantainer);
        layout.addView(ChildcontentView);

        setContentView(baseView);

        //Toolbar is a view in your hierarchy just like any other, making it easier to interleave with the rest of your views, animate it, and react to scroll events. ... The app bar, formerly known as the action bar in Android, is a special kind of toolbar that's used for branding, navigation, search, and actions.
        Toolbar toolbar = findViewById(R.id.toolbar);
        //In Android, DrawerLayout acts as top level container for window content that allows for interactive “drawer” views to be pulled out from one or both vertical edges of the window.
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        TextView txt = drawerLayout.findViewById(R.id.profile_name);
        txt.setText(ProfileName);

        ProfileImage = drawerLayout.findViewById(R.id.profile_image);
        ProfileImage.setImageResource(R.drawable.ic_launcher_background);

        //ActionBarDrawerToggle enables integration between drawer functionality and app bar framework. It displays navigation drawer icon. When navigation drawer icon is touched, onOptionsItemSelected callback method is called. In this method, you need to call ActionBarDrawerToggle's onOptionsItemSelected method.
        ActionBarDrawerToggle mDrawerToggle;

        // setSupportActionBar is used to set up toolbar as an actionbar
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            //actionBar.setDisplayHomeAsUpEnabled(true);
            //openDrawerContentDescRes : A String resource to describe the "open drawer" action for accessibility
            //closeDrawerContentDescRes : A String resource to describe the "close drawer" action for accessibility
            mDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open, R.string.close) {

                public void onDrawerClosed(View view) {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = false;
                }

                public void onDrawerOpened(View drawerView) {
                    supportInvalidateOptionsMenu();
                    //drawerOpened = true;
                }
            };

            //mDrawerToggle.setDrawerIndicatorEnabled(true);
            mDrawerToggle.syncState();
        }
    }

    public void ND_btn_aarti(View view) {
        startActivity(new Intent(this, A2_AartiList.class));
    }

//    public void ND_btn_aarti(View view) {startActivity(new Intent(this, A3_Aarti.class));
//    }
//
//    public void ND_btn_mantras(View view) {startActivity(new Intent(this, A4_Mantra.class));
//    }
//
//    public void ND_btn_bhajan(View view) {startActivity(new Intent(this, A9_VaishnavBhajan.class));
//    }
//
//    public void ND_btn_videos(View view) {startActivity(new Intent(this, A5_Videos.class));
//    }
//
//    public void ND_btn_calender(View view) {startActivity(new Intent(this, A8_VaishnavCalendar.class));
//    }
//
//    public void ND_btn_logout(View view) {startActivity(new Intent(this, D0_Home.class));
//    }
//
//    public void ND_btn_contactus(View view) {startActivity(new Intent(this, A10_ContactUs.class));
//    }
//
//    public void ND_btn_notifications(View view) {startActivity(new Intent(this, D7_Notification.class));
//    }
}
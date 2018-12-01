package com.example.toastapp;

import android.app.Activity;
import android.graphics.LinearGradient;
import android.support.v4.*;
import android.os.Bundle;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void showToast(){
        Toast toast = Toast.makeText(getApplicationContext(),"This is seccond lesson",Toast.LENGTH_SHORT);
        toast.show();
        toast.setGravity(Gravity.CENTER,0,0);
        LinearLayout toastContainer = (LinearLayout) toast.getView();
        ImageView image = new ImageView(getApplicationContext());
    }
}

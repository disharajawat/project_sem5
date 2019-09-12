package com.disha.testfunda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.Button;



public class MainActivity extends AppCompatActivity {

    private static int time_out=2000;
    SharedPreferences sharedpreferences;
    public static  String mypreference = "mypref";
    public String isLogin="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sharedpreferences = getSharedPreferences(mypreference,
                Context.MODE_PRIVATE);
        String con;
        con=sharedpreferences.getString("Contact","");
        isLogin=sharedpreferences.getString("isLogin","");
        Log.e("log2",isLogin);
        Log.e("con2",con);
//                isLogin="true";

        new Handler().postDelayed(new Runnable(){
            @Override
            public void run()
            {
                if (isLogin.equals("true")) {
                    Intent i = new Intent(MainActivity.this, HomePageActivity.class);
                    startActivity(i);
                }else{
                    Intent i=new Intent(MainActivity.this,login.class);
                    startActivity(i);
                    finish();
                }
            }
        },time_out);
    }
}

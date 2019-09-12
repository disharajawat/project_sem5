package com.disha.testfunda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;


public class login extends AppCompatActivity {

    EditText contact,password;
    Button login;
    String status;
    String cont, pass;
    ProgressBar progessbar;
    SharedPreferences sharedpreferences;
    public static  String mypreference = "mypref";
    public static  String Contact = "nameKey";
    public static  String isLogin = "isLogin";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        contact = (EditText) findViewById(R.id.contact);
        password = (EditText) findViewById(R.id.password);

        login = (Button) findViewById(R.id.login);
        progessbar=findViewById(R.id.progressbar);
        sharedpreferences =getSharedPreferences(mypreference,0);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                cont = contact.getText().toString();
                pass = password.getText().toString();
                if(cont.equals("") || pass.equals(""))
                {
                    Toast.makeText(login.this,"enter the details",Toast.LENGTH_LONG).show();
                }
                else
                {
                    getLogin();
                }

            }
        });

    }

    public void Signin(View view) {

        Intent i= new Intent(login.this,signup.class);
        startActivity(i);
    }
    public void getLogin(){
        progessbar.setVisibility(View.VISIBLE);
        AndroidNetworking.initialize(login.this);
        AndroidNetworking.get("https://testfunda.000webhostapp.com/login.php")
                .addQueryParameter("contact",cont)
                .addQueryParameter("pass",pass)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            status=response.getString("status");
                            if(status=="success");
                            {
                                progessbar.setVisibility(View.GONE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Contact, cont);
                                editor.putString(isLogin,"true");
                                editor.commit();
                                Toast.makeText(login.this,"Successfully Logged in",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(login.this, HomePageActivity.class);
                                startActivity(i);

                            }
                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.d("errqq",anError.toString());
                        Log.e("erroe",anError.toString());
                        Toast.makeText(login.this, "oh no" + anError, Toast.LENGTH_LONG).show();

                    }
                });
    }


}

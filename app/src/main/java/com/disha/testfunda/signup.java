package com.disha.testfunda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;

import org.json.JSONObject;

public class signup extends AppCompatActivity {

    EditText name,email,contact,password;
    Button signin;
    String n,c,e,status,error="signup",p;
    ProgressBar progessbar;
    TextView pas;
    public static  String Contact = "nameKey";
    public static  String isLogin = "isLogin";

    SharedPreferences sharedpreferences;
    public static  String mypreference = "mypref";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        name=(EditText)findViewById(R.id.name);
        email=(EditText)findViewById(R.id.email);
        contact=(EditText)findViewById(R.id.contact);
        password=(EditText)findViewById(R.id.password);
        signin=(Button)findViewById(R.id.signin);
        pas=(TextView)findViewById(R.id.note);
        progessbar=findViewById(R.id.progressbar);
        error="abcd";
        sharedpreferences =getSharedPreferences(mypreference,0);



        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                n=name.getText().toString();
                e=email.getText().toString();
                c=contact.getText().toString();
                p=password.getText().toString();

                if(c.equals("") || p.equals("") || n.equals("") || e.equals(""))
                {
                    Toast.makeText(signup.this,"enter the details",Toast.LENGTH_LONG).show();
                }
                else if(c.length()<10)
                {
                    Toast.makeText(signup.this,"Invalid contact number",Toast.LENGTH_LONG).show();
                }
                else if(p.length()<9)
                {
                    Toast.makeText(signup.this,"password should have atleast 8 char",Toast.LENGTH_LONG).show();
                }

                else
                {
                    getSignIn();
                }

            }
        });

    }
    private void getSignIn() {
        progessbar.setVisibility(View.VISIBLE);

        AndroidNetworking.initialize(signup.this);
        AndroidNetworking.get("https://testfunda.000webhostapp.com/registration.php")
                .addQueryParameter("name",n)
                .addQueryParameter("contact",c)
                .addQueryParameter("email",e)
                .addQueryParameter("password",p)
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            status=response.getString("status");
                            String msg;
                            msg=response.getString("message");
                            if(status.equals("success"))
                            {
                                progessbar.setVisibility(View.GONE);
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putString(Contact, c);
                                editor.putString(isLogin,"true");
                                editor.commit();

                                Toast.makeText(signup.this,"Successfully Logged in",Toast.LENGTH_LONG).show();
                                Intent i = new Intent(signup.this, HomePageActivity.class);
                                startActivity(i);
                            }
                            else if(status.equals("error") && msg.equals("already"))
                            {
                                Toast.makeText(signup.this,"User already exists",Toast.LENGTH_LONG).show();
                            }
                            else
                            {
                                Toast.makeText(signup.this,"Error hitting API",Toast.LENGTH_LONG).show();
                            }

                        }
                        catch(Exception ex)
                        {
                            ex.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                        Log.d(error,anError.toString());
                        Toast.makeText(signup.this, "oh no" + anError, Toast.LENGTH_LONG).show();

                    }
                });
    }


}

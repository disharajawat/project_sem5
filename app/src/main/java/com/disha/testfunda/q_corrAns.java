package com.disha.testfunda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class q_corrAns extends AppCompatActivity {

    TextView title,question;
    RadioButton choice1,choice2,choice3,choice4;
    Button next,end;
    ExpandableListView expandable;
    ExpandableListViewAdapter adapter;
    List<String> listDataheader;
    HashMap<String,List<String>> listHashMap;
    ArrayList correctQuestion=new ArrayList<String>();
    ArrayList explanation1=new ArrayList<String>();
    List<String> explanation=new ArrayList<>();
    List<String> correct=new ArrayList<>();
    List<String> time=new ArrayList<>();
    List<String> others=new ArrayList<>();
    List<String> percentage=new ArrayList<>();
    String a,section;
    int j=0,h=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_q_corr_ans);

        title=(TextView)findViewById(R.id.title);
        question=(TextView)findViewById(R.id.question);
        choice1=(RadioButton)findViewById(R.id.choice1);
        choice2=(RadioButton)findViewById(R.id.choice2);
        choice3=(RadioButton)findViewById(R.id.choice3);
        choice4=(RadioButton)findViewById(R.id.choice4);
        next=(Button)findViewById(R.id.next);
        end=(Button)findViewById(R.id.end);
        expandable=(ExpandableListView)findViewById(R.id.expandable);
        expandable.setAdapter(adapter);
        Intent i=getIntent();
        section=i.getStringExtra("section");
        correctQuestion=i.getStringArrayListExtra("correctQ");

        listDataheader=new ArrayList<>();
        listHashMap=new HashMap<>();

        listDataheader.add("Explanation1");
        listDataheader.add("Correct Ans");
        listDataheader.add("Time taken by you");
        listDataheader.add("Time taken by others");
        listDataheader.add("percentage of students got it correct");

        Log.e("as", String.valueOf(j));
        getCorrect();

        adapter=new ExpandableListViewAdapter(q_corrAns.this,listDataheader,listHashMap);
        expandable.setAdapter(adapter);


        Log.e("size",String.valueOf(correctQuestion.size()));

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(j<=(correctQuestion.size()-1)) {
                    Log.e("as", String.valueOf(j));
                    Log.e("qa",correctQuestion.get(j).toString());
                    getCorrect();
                }
                else
                {
                    end.setVisibility(View.VISIBLE);
                }

            }
        });
        end.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(q_corrAns.this,v_score1.class);
                startActivity(i);
            }
        });
    }

    public void getCorrect()
    {
        Log.e("inside correct",String.valueOf(j));

        AndroidNetworking.get("http://testfunda.000webhostapp.com/q1_ans.php")
                .addQueryParameter("question",correctQuestion.get(j).toString())
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            Log.e("added",correctQuestion.get(j).toString());
                            if(j<correctQuestion.size())
                            {
                                JSONObject obj = response.getJSONObject(h);
//
                                Log.e("qw", obj.getString("title"));
                                title.setText(obj.getString("title"));
                                question.setText(obj.getString("question"));
                                choice1.setText(obj.getString("choice1"));
                                choice2.setText(obj.getString("choice2"));
                                choice3.setText(obj.getString("choice3"));
                                choice4.setText(obj.getString("choice4"));

                                correct.clear();
                                explanation.clear();
                                correct.add(obj.getString("ans"));
                                explanation.add(obj.getString("explanation"));

                                listHashMap.put(listDataheader.get(0), explanation);
                                listHashMap.put(listDataheader.get(1), correct);
                                listHashMap.put(listDataheader.get(2), time);
                                listHashMap.put(listDataheader.get(3), others);
                                listHashMap.put(listDataheader.get(4), percentage);
                                j++;
                            }

                        }
                        catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onError(ANError anError) {
                    }
                });

    }
}



package com.disha.testfunda;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class v_score1 extends AppCompatActivity {

    String sd="",a="",in="",sk="";
    ArrayList skippedQuestion=new ArrayList<String>();
    ArrayList correctQuestion=new ArrayList<String>();
    ArrayList incorrectQuestion=new ArrayList<String>();
    ArrayList explanation=new ArrayList<String>();
    String section;
    TextView score,skipped,attempted,incorrect;
    Button correct,wrong,skiped,analysis;
    int acc,crct,skp,incrct,att;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_score1);

        score=(TextView)findViewById(R.id.score);
        skipped=(TextView)findViewById(R.id.skipped);
        attempted=(TextView)findViewById(R.id.attempted);
        incorrect=(TextView)findViewById(R.id.incorrect);

        correct=(Button)findViewById(R.id.correct);
        wrong=(Button)findViewById(R.id.wrong);
        skiped=(Button)findViewById(R.id.skiped);
        analysis=(Button)findViewById(R.id.analysis);

        Intent i=getIntent();



        sd=i.getStringExtra("Score");
        sk=i.getStringExtra("Skipped");
        in=i.getStringExtra("Incorrect");
        a=i.getStringExtra("Attempted");

        section=i.getStringExtra("section");

        Log.e("Score12",sd);
        Log.e("Skipped12",sk);
        Log.e("Incorrect12",in);
        Log.e("Attempted12",a);
//        crct=Integer.parseInt(sd);
//        skp=Integer.parseInt(sk);
//        incrct=Integer.parseInt(in);
//        att=Integer.parseInt(a);

        if(sd.equals("null"))
        {
            crct=0;
        }
        else
        {
            crct=Integer.parseInt(sd);
        }
        if(sk.equals("null"))
        {
            skp=0;
        }
        else
        {
            skp=Integer.parseInt(sk);
        }
        if(in.equals("null"))
        {
            incrct=0;
        }
        else
        {
            incrct=Integer.parseInt(in);
        }
        if(a.equals("null"))
        {
            att=0;
        }
        else
        {
            att=Integer.parseInt(a);
        }



        double attp;

        acc=(crct/att)*100;
        attp=(att/10)*100;


        for (Object correctQ : correctQuestion = i.getStringArrayListExtra("correctQ")) {
            Log.e("1",correctQ.toString());
        }
        ;
        for (Object incorrectQ : incorrectQuestion = i.getStringArrayListExtra("incorrectQ")) {
            Log.e("1",incorrectQ.toString());
        }
        ;
        for (Object skippedQ : skippedQuestion = i.getIntegerArrayListExtra("skippedQ")) {
            Log.e("1",skippedQ.toString());
        }
        ;

        score.setText("Score is"+sd);
        skipped.setText("Skipped"+sk);
        attempted.setText("Attempted"+a);
        incorrect.setText("Incorrect"+in);

        correct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(section.equals("verbal"))
                {
                    Intent i=new Intent(v_score1.this,V1_ans.class);
                    i.putStringArrayListExtra("correctQ",correctQuestion);
                    startActivity(i);
                }
                else if(section.equals("quant"))
                {
                    Intent i=new Intent(v_score1.this,q_corrAns.class);
                    i.putStringArrayListExtra("correctQ",correctQuestion);
                    startActivity(i);
                }
                else if(section.equals("logic"))
                {
                    Intent i=new Intent(v_score1.this,d_corrAns.class);
                    i.putStringArrayListExtra("correctQ",correctQuestion);
                    startActivity(i);
                }



            }
        });
        skiped.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(section.equals("verbal"))
                {
                    Intent i=new Intent(v_score1.this,V1_skippedAns.class);
                    i.putStringArrayListExtra("skipQ",skippedQuestion);
                    startActivity(i);
                }
                else if(section.equals("quant"))
                {
                    Intent i=new Intent(v_score1.this,q_skAns.class);
                    i.putStringArrayListExtra("skipQ",skippedQuestion);
                    startActivity(i);
                }
                else if(section.equals("logic"))
                {
                    Intent i=new Intent(v_score1.this,d_skAns.class);
                    i.putStringArrayListExtra("skipQ",skippedQuestion);
                    startActivity(i);
                }

            }
        });
        wrong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(section.equals("verbal"))
                {
                    Intent i=new Intent(v_score1.this,V1_wrngAns.class);
                    i.putStringArrayListExtra("wrngQ",incorrectQuestion);
                    startActivity(i);
                }
                else if(section.equals("quant"))
                {
                    Intent i=new Intent(v_score1.this,q_wrngAns.class);
                    i.putStringArrayListExtra("wrngQ",incorrectQuestion);
                    startActivity(i);
                }
                else if(section.equals("logic"))
                {
                    Intent i=new Intent(v_score1.this,d_wrngAns.class);
                    i.putStringArrayListExtra("wrngQ",incorrectQuestion);
                    startActivity(i);
                }

            }
        });
        analysis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it=new Intent(v_score1.this,graph.class);
                it.putExtra("section",section);
                it.putExtra("x",att);
                startActivity(it);

            }
        });

    }
}

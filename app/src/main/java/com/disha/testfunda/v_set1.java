package com.disha.testfunda;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class v_set1 extends AppCompatActivity {

    TextView title,question,timer_text;
    Chronometer chr;
    boolean running;
    RadioButton choice1,choice2,choice3,choice4;
    Button next,skip;
    String q1,c1,c2,c3,c4,t1,ans,my_ans="",qw="";
    int score=0;
    AlertDialog.Builder builder,time_finish,skp;
    int skipped=0,attempted=0,incorrect=0;
    int correct=0;
    long startTime=0L,updateTime=0L,timeInMilli=0L,timeSwap=0L;
    Handler customHandler=new Handler();
    final JSONObject response=new JSONObject();
    int j,az=1;
    String[] ans1=new String[7];
    String[] c_ans=new String[6];
    String[] qquestion=new String[6];
    List<Question> list= new ArrayList<>();
    List<Ans> ansList=new ArrayList<>();
    String sd="null",sk="null",a="null",in="null";
    ArrayList explanation1=new ArrayList<String>();
    ArrayList skippedQuestion=new ArrayList<String>();
    ArrayList correctQuestion=new ArrayList<String>();
    ArrayList incorrectQuestion=new ArrayList<String>();

    Runnable updateTimeThread=new Runnable() {
        @Override
        public void run() {
            timeInMilli=SystemClock.uptimeMillis()-startTime;
            int secs=(int)(updateTime%1000);
            int mins=secs/60;
            secs%=60;
            int millisecs=(int)(updateTime%1000);
            Log.e("runnable",""+mins+":"+String.format("%2d",secs)+":"
                    +String.format("%3d",secs));
            timer_text.setText(""+mins+":"+String.format("%2d",secs)+":"
                    +String.format("%3d",secs));
            customHandler.postDelayed(this,0);

        }
    };

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_v_set1);

        title=(TextView)findViewById(R.id.title);
        timer_text=(TextView)findViewById(R.id.timer_text);
        question=(TextView)findViewById(R.id.question);
        choice1=(RadioButton)findViewById(R.id.choice1);
        choice2=(RadioButton)findViewById(R.id.choice2);
        choice3=(RadioButton)findViewById(R.id.choice3);
        choice4=(RadioButton)findViewById(R.id.choice4);
        next=(Button)findViewById(R.id.next);
        skip=(Button)findViewById(R.id.skip);
        chr=(Chronometer)findViewById(R.id.chr);
        Question quest;

        Log.e("maindisha","run");
        ctimer.start();

        j=0;
        ans="";
        getQuestion();

        time_finish=new AlertDialog.Builder(v_set1.this)
                .setTitle("Click OK to continue or cancel to go to next question")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        timer_text.setVisibility(View.INVISIBLE);
                        chr.setVisibility(View.VISIBLE);
                        Start();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ctimer.cancel();
                        ans1[j]="skip";
                        Log.e("myans",j+"-"+ans1[j]);
                        getQuestion();
                    }
                });
        skp=new AlertDialog.Builder(this)
                .setTitle("Choose Skip to go to next question or Cancel to stay")
                .setPositiveButton("Skip", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        ctimer.cancel();
                        Stop();
                        ans1[j]="skip";
                        Log.e("myans",j+"-"+ans1[j]);
                        getQuestion();
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(j<7)
                {
                    my_ans="";
                    if(choice1.isChecked())
                    {
                        my_ans=choice1.getText().toString();
                        choice1.setChecked(false);
                    }
                    else if(choice2.isChecked())
                    {
                        my_ans=choice2.getText().toString();
                        choice2.setChecked(false);
                    }
                    else if(choice3.isChecked())
                    {
                        my_ans=choice3.getText().toString();
                        choice3.setChecked(false);
                    }
                    else if(choice4.isChecked())
                    {
                        my_ans=choice4.getText().toString();
                        choice4.setChecked(false);
                    }


                    if(my_ans!="")
                    {
                        Ans answer=new Ans(my_ans);
                        ansList.add(answer);
                        ans1[j]=my_ans;
                        Log.e("myans",j+"-"+ans1[j]);
                        getQuestion();

                        attempted=attempted+1;
                    }
                    if(my_ans.equals(""))
                    {
                        ctimer.cancel();
                        skp.create().show();
                    }
                    Toast.makeText(v_set1.this,"Score is"+score,Toast.LENGTH_LONG).show();
                    ctimer.start();
                }
                else
                {
                    getQuestion();
                }

            }
        });

        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(j<7)
                {
                    ans1[j]="skip";
                    Log.e("my",j+"-"+ans1[j]);
                    if(choice1.isChecked())
                    {
//                    my_ans=choice1.getText().toString();
                        choice1.setChecked(false);
                    }
                    else if(choice2.isChecked())
                    {
//                    my_ans=choice2.getText().toString();
                        choice2.setChecked(false);
                    }
                    else if(choice3.isChecked())
                    {
//                    my_ans=choice3.getText().toString();
                        choice3.setChecked(false);
                    }
                    else if(choice4.isChecked())
                    {
//                    my_ans=choice4.getText().toString();
                        choice4.setChecked(false);
                    }
                    getQuestion();
                    ctimer.cancel();
                }
                else
                {
                    getQuestion();

                }
            }
        });
    }

    CountDownTimer ctimer = new CountDownTimer(60000, 1000) {

        public void onTick(long millisUntilFinished) {
            timer_text.setText("" + millisUntilFinished / 1000);
        }
        public void onFinish() {
            time_finish.create().show();

        }
    };

    public void getQuestion()
    {

        AndroidNetworking.get("http://testfunda.000webhostapp.com/verbal.php")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONArray(new JSONArrayRequestListener() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Log.e("onresponse","ok");
                        try {
                            if(j<6){
                                for(int i=0;i<6;i++){
                                    JSONObject obj = response.getJSONObject(i);
                                    Question question = new Question(obj.getString("question"),obj.getString("ans"),obj.getString("title"),obj.getString("choice1"),obj.getString("choice2"),obj.getString("choice3"),obj.getString("choice4"),obj.getString("explanation"));
                                    list.add(question);
                                }
                                Question quest = list.get(j);
                                title.setText(quest.getTitle());
                                question.setText(quest.getQuestion());
                                choice1.setText(quest.getChoice1());
                                choice2.setText(quest.getChoice2());
                                choice3.setText(quest.getChoice3());
                                choice4.setText(quest.getChoice4());
                                ans=quest.getAnswer();
                                qw=quest.getQuestion();
                                explanation1.add(quest.getExplanation());
                                c_ans[j]=ans;
                                qquestion[j]=qw;
                                Log.e("correct ans"+"-"+j,ans);
                                j=j+1;
                            }
                            else
                            {
                                for(int k=0;k<6;k++)
                                {
                                    Log.e("skipped",ans1[az]);
                                    if(ans1[az].equals("skip"))
                                    {
                                        skipped+=1;
                                        Log.e("skipped_questions",qquestion[k]);
                                        skippedQuestion.add(qquestion[k]);
                                    }
                                    else if(ans1[az].equals(c_ans[k]))
                                    {
                                        correct+=1;
                                        incorrect=attempted-correct;
                                        sd=Integer.toString(correct);
                                        Log.e("sc",sd);
                                        in=Integer.toString(incorrect);
                                        Log.e("correct_questions",qquestion[k]);
                                        correctQuestion.add(qquestion[k]);
                                    }
                                    else
                                    {
                                        Log.e("incorrect-questions",qquestion[k]);
                                        incorrectQuestion.add(qquestion[k]);
                                    }
                                    if (az<7)
                                    {
                                        az=az+1;
                                    }

                                }

                                builder=new AlertDialog.Builder(v_set1.this)
                                        .setTitle("Test Over")
                                        .setMessage("Click OK to view your score")
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                Intent i=new Intent(v_set1.this,v_score1.class);
                                                sk=Integer.toString(skipped);
                                                a=Integer.toString(attempted);
                                                i.putExtra("Score",sd);
                                                i.putExtra("Skipped",sk);
                                                i.putExtra("Attempted",a);
                                                i.putExtra("Incorrect",in);
                                                i.putExtra("section","verbal");
                                                Log.e("you scored",sd);
                                                Log.e("you skipped",sk);
                                                Log.e("you attempted",a);
                                                Log.e("you got incorrect",in);
                                                i.putStringArrayListExtra("explanation",explanation1);
                                                i.putStringArrayListExtra("correctQ",correctQuestion);
                                                i.putStringArrayListExtra("incorrectQ",incorrectQuestion);
                                                i.putStringArrayListExtra("skippedQ",skippedQuestion);
                                                startActivity(i);

                                            }
                                        });
                                builder.create().show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        anError.printStackTrace();
                    }
                });


    }

    class Question {
        String question;
        String answer;
        String title;
        String choice1;
        String choice2;
        String choice3;
        String choice4,explanation;

        public Question(String question, String answer,String title,String choice1,String choice2,String choice3,String choice4, String explanation) {
            this.title = title;
            this.question = question;
            this.answer = answer;
            this.choice1 = choice1;
            this.choice2 = choice2;
            this.choice3 = choice3;
            this.choice4 = choice4;
            this.explanation=explanation;

        }

        public String getQuestion() {
            return question;
        }

        public String getAnswer() {
            return answer;
        }
        public String getTitle() {
            return title;
        }
        public String getChoice1() {
            return choice1;
        }
        public String getChoice2() {
            return choice2;
        }
        public String getChoice3() {
            return choice3;
        }
        public String getChoice4() {
            return choice4;
        }
        public String getExplanation() {
            return explanation;
        }

    }

    class Ans
    {
        String ans1;

        public Ans(String ans1)
        {
            this.ans1=ans1;
        }
        public String getAns1()
        {
            return ans1;
        }
    }

    public void Start() {

        if (!running) {
            chr.setBase((SystemClock.elapsedRealtime() - (60 * 1000)));
            chr.start();
            running = true;
        }
    }

    public void Stop()
    {
        if(running)
        {
            chr.stop();
            chr.setBase((SystemClock.elapsedRealtime() - (60 * 1000)));
            running=false;
            chr.setVisibility(View.INVISIBLE);
        }
    }

}



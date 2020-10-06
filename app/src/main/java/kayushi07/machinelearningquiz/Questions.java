package kayushi07.machinelearningquiz;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.DonutProgress;

import java.util.ArrayList;
import java.util.Collections;

public class Questions extends AppCompatActivity {
    DonutProgress donutProgress;
    int variable =0;
    TextView ques, desc;
    Button OptA, OptB, OptC, OptD;
    Button play_button;
    String get;
    computer Computer;
    public int visibility = 0, c1 = 0, i, j = 0, k = 0, l = 0;
    String global = null, Ques, Opta, Optb, Optc, Optd;
    ArrayList<Integer> list = new ArrayList<Integer>();
    Toast toast;
    private AdView mAdView;
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mAdView = (AdView) findViewById(R.id.adView2);

        try {
            AdRequest adRequest = new AdRequest.Builder()
                    .build();
            mAdView.loadAd(adRequest);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();//recieving the intent send by the Navigation activity
        get = intent.getStringExtra(Navigation_Activity.Message);//converting that intent message to string using the getStringExtra() method
        toast = new Toast(this);
        //attribute of the circular progress bar
        donutProgress = (DonutProgress) findViewById(R.id.donut_progress);
        donutProgress.setMax(100);
        donutProgress.setFinishedStrokeColor(Color.parseColor("#FFFB385F"));
        donutProgress.setTextColor(Color.parseColor("#FFFB385F"));
        donutProgress.setKeepScreenOn(true);

        //Now the linking of all the datbase files with the Question activity

        Computer = new computer(this);
        Computer.createDatabase();
        Computer.openDatabase();
        Computer.getWritableDatabase();

        //Till here we are linking the database file
        OptA = (Button) findViewById(R.id.OptionA);
        OptB = (Button) findViewById(R.id.OptionB);
        OptC = (Button) findViewById(R.id.OptionC);
        OptD = (Button) findViewById(R.id.OptionD);
        ques = (TextView) findViewById(R.id.Questions);
        play_button = (Button) findViewById(R.id.play_button);//Play button to start the game
        desc = (TextView) findViewById(R.id.desc);

    }


    public void onClick(View v) {//When this method is executed then there will be new question came and also same method for play button
        final SharedPreferences shared = getSharedPreferences("Score", Context.MODE_PRIVATE);
        k++;
        if (visibility == 0) {
            //showing the buttons which were previously invisible
            OptA.setVisibility(View.VISIBLE);
            OptB.setVisibility(View.VISIBLE);
            OptC.setVisibility(View.VISIBLE);
            OptD.setVisibility(View.VISIBLE);
            play_button.setVisibility(View.GONE);
            desc.setVisibility(View.GONE);
            donutProgress.setVisibility(View.VISIBLE);
            visibility = 1;
            new CountDownTimer(100000, 1000) {//countdowntimer
                int i = 100;

                @Override
                public void onTick(long millisUntilFinished) {
                    i = i - 1;
                    donutProgress.setProgress(i);

                }

                @Override
                public void onFinish() {
                    toast.cancel();
                    SharedPreferences.Editor editor = shared.edit();//here we are saving the data when the countdown timer will finish and it is saved in shared prefrence file that is defined in onCreate method as score
                    editor.putInt("Questions", k).commit();
                    int sco = l*10;
                    if (get.equals("c1") && shared.getInt("Computer", 0) < sco)
                        editor.putInt("Computer", l * 10).apply();
                    donutProgress.setProgress(0);
                    if(variable==0) {
                        Intent intent = new Intent(Questions.this, Result.class);
                        intent.putExtra("correct", l);
                        intent.putExtra("attemp", k);
                        startActivity(intent);
                        finish();
                    }
                }
            }.start();
        }

        if (global != null) {
            if (global.equals("A")) {
                if (v.getId() == R.id.OptionA) {
                    //Here we use the snackbar because if we use the toast then they will be stacked an user cannot idetify which questions answer is it showing
//                    Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.corr)
                            .setTitle("Correct!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();

                    l++;
                } else {
//                    Snackbar.make(v, "Incorrect\t      Answer :" + Opta + "", Snackbar.LENGTH_SHORT).show();

                    String an = "Answer is \n"+ Opta;
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(an)
                            .setIcon(R.drawable.cross)
                            .setTitle("Wrong!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();

                }

            } else if (global.equals("B")) {
                if (v.getId() == R.id.OptionB) {
//                    Snackbar.make(v, "          Correct ☺", Snackbar.LENGTH_SHORT).show();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.corr)
                            .setTitle("Correct!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();


                    l++;
                } else {
//                    Snackbar.make(v, "Incorrect\t      Answer :" + Optb + "", Snackbar.LENGTH_SHORT).show();

                    String an = "Answer is \n"+ Optb;
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(an)
                            .setIcon(R.drawable.cross)
                            .setTitle("Wrong!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                }

            } else if (global.equals("C")) {
                if (v.getId() == R.id.OptionC) {

//                    Snackbar.make(v, "         Correct ☺", Snackbar.LENGTH_SHORT).show();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.corr)
                            .setTitle("Correct!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();


                    l++;
                } else {
//                    Snackbar.make(v, "Incorrect\tAnswer :" + Optc + "", Snackbar.LENGTH_SHORT).show();
                    String an = "Answer is \n"+ Optc;
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(an)
                            .setIcon(R.drawable.cross)
                            .setTitle("Wrong!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                }
            } else if (global.equals("D")) {
                if (v.getId() == R.id.OptionD) {
//                    Snackbar.make(v, "        Correct ☺", Snackbar.LENGTH_SHORT).show();

                    final AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setIcon(R.drawable.corr)
                            .setTitle("Correct!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!

                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();

                    l++;
                } else {

//                    Snackbar.make(v, "Incorrect\tAnswer :" + Optd + "", Snackbar.LENGTH_SHORT).show();
                    String an = "Answer is \n"+ Optd;
                    AlertDialog.Builder builder = new AlertDialog.Builder(this);
                    builder.setMessage(an)
                            .setIcon(R.drawable.cross)
                            .setTitle("Wrong!")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // FIRE ZE MISSILES!
                                }
                            });
                    // Create the AlertDialog object and return it
                    builder.create();
                    builder.show();
                }
            }
        }
        if (get.equals("c1")) {

            if (c1 == 0) {
                for (i = 1; i < 58; i++) {
                    list.add(new Integer(i));
                }
                Collections.shuffle(list);
                c1=1;
            }
            Ques = Computer.readQuestion(list.get(j));
            Opta = Computer.readOptionA(list.get(j));
            Optb = Computer.readOptionB(list.get(j));
            Optc = Computer.readOptionC(list.get(j));
            Optd = Computer.readOptionD(list.get(j));
            global = Computer.readAnswer(list.get(j++));

        }
        ques.setText("" + Ques);
        OptA.setText(Opta);
        OptB.setText(Optb);
        OptC.setText(Optc);
        OptD.setText(Optd);
    }

    @Override
    protected void onPause() {
        if (mAdView != null) {
            mAdView.pause();
        }
        super.onPause();
        variable =1;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        variable =1;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        variable = 1;
        finish();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdView != null) {
            mAdView.resume();
        }
    }

    @Override
    public void onDestroy() {
        if (mAdView != null) {
            mAdView.destroy();
        }

        super.onDestroy();
    }
}

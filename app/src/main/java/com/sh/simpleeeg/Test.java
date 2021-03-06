package com.sh.simpleeeg;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.Guideline;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import static com.sh.simpleeeg.CLS_BrainWave.iAttention;
import static com.sh.simpleeeg.CLS_BrainWave.iDelta;
import static com.sh.simpleeeg.CLS_BrainWave.iFatigue;
import static com.sh.simpleeeg.CLS_BrainWave.iGoodSignal;
import static com.sh.simpleeeg.CLS_BrainWave.iHighAlpha;
import static com.sh.simpleeeg.CLS_BrainWave.iHighBeta;
import static com.sh.simpleeeg.CLS_BrainWave.iHighGamma;
import static com.sh.simpleeeg.CLS_BrainWave.iLowAlpha;
import static com.sh.simpleeeg.CLS_BrainWave.iLowBeta;
import static com.sh.simpleeeg.CLS_BrainWave.iLowGamma;
import static com.sh.simpleeeg.CLS_BrainWave.iMeditation;
import static com.sh.simpleeeg.CLS_BrainWave.iTheta;

//import com.bumptech.glide.request.Request;

public class Test extends Activity {


    public static boolean mbIsInitialised;
    // ??????????????????MediaPlayer??????????????????prepareAsync()

    TextView txtList,txtData,tvAMvalue,tvAM,tvTime,tvNextLevel,tvLifeValue,
            tvIntroduction,tvLevelDifficulty,tvBeforeGain,tvBeforeCombo,tvCombo
            ,tvLifeCountX
            ,tvChallengeWhat,tvChallengeSong;
    private static final String DB_FILE = "sample.db", DB_FILE2 = "sample2.db",
            DB_TABLE = "sample", DB_TABLE2 = "sample2" , DB_TABLE_ID = "sample3";
    private LinearLayout mLinLay;
    private ImageView imgGhost,imgDog,imgAudience,imgFlower01,imgFlower02,imgFlower03,imgFlower04,imgFlower05
            ,imgNote01,imgNote02,imgNote03,imgNote04,imgNote05,imglevelatt,imglevelma
            ,imgLifeValue
            ,imgCombo;
    private Dialog mDlgNext;
    RadioGroup rgSaveMethod;
    RadioButton rbSQLite, rbExcel, rbCloud;
    ObjectAnimator animTxtAlpha;
    ObjectAnimator animTxtAlpha2;
    ObjectAnimator animFlower01,animFlower02,animFlower03,animFlower04,animFlower05;
    ObjectAnimator animNote01,animNote02,animNote03,animNote04,animNote05;
    ObjectAnimator animDog;
    ObjectAnimator animCombo,animLifeCount;



    public static int TestTime = 0;
    CLS_PARAM S = new CLS_PARAM();
    CLS_BrainWave clsBrainwave = new CLS_BrainWave();
    CLS_DATA clsData = new CLS_DATA();
    Music music = new Music();
    CLS_LineChart clsLineChart;

    Context mContext;
    LinearLayout layoutChart,LCFatigue;
    View viewLineChart,viewLineChart2;
    ImageView ivBG,ivStopTest;
    ImageView ivStartTest;
    Guideline guidelineTop,guidelineBottom;


    String sheetboo = "true";



    private ClockThread1000 m_clockThread1000;
    private Handler m_clockHandler = new Handler();
    boolean bThreadRun = false;
    int iCountDownTimer = 100;
    static int progress = 31;//??????????????????
    private ProgressBar pb;
    private Timer timer;
    private TimerTask timerTask;
    static int level = 1;
    RequestOptions myGdiOptions = new RequestOptions().fitCenter();

    float set1=1600;
    float set2=1600;
    int LifeValue = 5;  //??????????????????
    static int AttentionAvg1=0,AttentionAvg2=0,AttentionAvg3=0,AttentionAvg4=0,AttentionAvg5=0;  //?????????????????????
    static int MeditationAvg1=0,MeditationAvg2=0,MeditationAvg3=0,MeditationAvg4=0,MeditationAvg5=0;  //?????????????????????
    static int Checkpoint1=0,Checkpoint2=0,Checkpoint3=0,Checkpoint4=0,Checkpoint5=0;  //????????????????????????
    static int ComboCount1=0, ComboCount2=0, ComboCount3=0, ComboCount4=0, ComboCount5=0;
    static int AttentionTotal=0, MeditationTotal=0;
    int tpA1=0, tpA2=0, tpM1=0, tpM2=0;
    int triple=0;
    int width,height,densityDpi;
    double AudienceClose,GhostClose,BaseLine;
    float density;

    static double dX = 0;




    @RequiresApi(api = Build.VERSION_CODES.P)
    @SuppressLint({"SourceLockedOrientationActivity", "ResourceType"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.test);


        ivStartTest = findViewById(R.id.ivStartTest);
        rbSQLite = findViewById(R.id.rbSQLite);
        rbExcel = findViewById(R.id.rbExcel);
        rbCloud = findViewById(R.id.rbCloud);

        mContext = this;


        ivBG = (ImageView) findViewById(R.id.ivBG);
        ivStopTest = (ImageView) findViewById(R.id.ivStopTest);
        layoutChart = (LinearLayout) findViewById(R.id.layoutChart);
        LCFatigue = (LinearLayout) findViewById(R.id.LCFatigue);
        tvAMvalue = (TextView) findViewById(R.id.tvAMvalue);
        tvAM = findViewById(R.id.tvAM);
        tvLifeCountX = findViewById(R.id.tvLifeCountX);
        //tvMeditation = (TextView) findViewById(R.id.tvMeditation);
        guidelineTop = findViewById(R.id.guideline23);
        guidelineBottom = findViewById(R.id.guideline24);
        imgGhost = (ImageView) findViewById(R.id.imgGhost);
        imgDog = findViewById(R.id.imgDog);
        imgAudience = (ImageView) findViewById(R.id.imgAudience);
        imgFlower01 = findViewById(R.id.gifFlower01);
        imgFlower02 = findViewById(R.id.gifFlower02);
        imgFlower03 = findViewById(R.id.gifFlower03);
        imgFlower04 = findViewById(R.id.gifFlower04);
        imgFlower05 = findViewById(R.id.gifFlower05);
        imgNote01 = findViewById(R.id.imgNote01);
        imgNote02 = findViewById(R.id.imgNote02);
        imgNote03 = findViewById(R.id.imgNote03);
        imgNote04 = findViewById(R.id.imgNote04);
        imgNote05 = findViewById(R.id.imgNote05);
        tvLifeValue = findViewById(R.id.tvLifeValue);
        imgLifeValue = findViewById(R.id.imgLifeValue);
        imgCombo = findViewById(R.id.imgCombo);

//???????????????-----------------------------------------------------------------------------------------


        mContext = this;


        txtList = (TextView) findViewById(R.id.txtList);
        txtData = (TextView) findViewById(R.id.txtData);
        tvTime = (TextView) findViewById(R.id.tvTime);
        //chartPreMed = (PieChart) findViewById(R.id.pieChartMed);
        pb = (ProgressBar) findViewById(R.id.progressBar);


        float fSize = 10f;

        //??????????????????????????????
        pb.setMax(progress - 1);
        pb.setProgress(0);



        clsData.DoRawCalculation();


            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {


                }

            }, 1000);



        //-----------------------------------------------------------------------------------------


        m_clockThread1000 = new ClockThread1000();
        m_clockHandler.post(m_clockThread1000);
        bThreadRun = true;

        clsData.ClearRawData();

        SetCallback();

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        width = metric.widthPixels;     // ????????????????????????
        height = metric.heightPixels;   // ????????????????????????
        density = metric.density;      // ???????????????0.75 / 1.0 / 1.5???
        densityDpi = metric.densityDpi;  // ????????????DPI???120 / 160 / 240???

        GhostClose = height * 0.25;
        AudienceClose = height * 0.35;
        BaseLine = height * 0.85;

        animCombo = ObjectAnimator.ofFloat(imgCombo, "alpha",0, 0);
        animCombo.start();

    }



    void setMusic(){
        int min = 1;
        int max = 5;
        Random r = new Random();
        int i1 = r.nextInt(max - min + 1) + min;


        switch (level) {
            case 1:
                switch (i1){
                    case 1 :music.play(this, R.raw.classic01);break;
                    case 2 :music.play(this, R.raw.classic02);break;
                    case 3 :music.play(this, R.raw.classic03);break;
                    case 4 :music.play(this, R.raw.classic04);break;
                    case 5 :music.play(this, R.raw.classic05);break;
                }
                break;
            case 2: //??????2=====
                switch (i1){
                    case 1 :music.play(this, R.raw.calm01);break;
                    case 2 :music.play(this, R.raw.calm02);break;
                    case 3 :music.play(this, R.raw.calm03);break;
                    case 4 :music.play(this, R.raw.calm04);break;
                    case 5 :music.play(this, R.raw.calm05);break;
                }
                break;
            case 3: //??????3=====
                switch (i1){
                    case 1 :music.play(this, R.raw.passionate01);break;
                    case 2 :music.play(this, R.raw.passionate02);break;
                    case 3 :music.play(this, R.raw.passionate03);break;
                    case 4 :music.play(this, R.raw.passionate04);break;
                    case 5 :music.play(this, R.raw.passionate05);break;
                }
                break;
            case 4: //??????4=====
                switch (i1){
                    case 1 :music.play(this, R.raw.pop01);break;
                    case 2 :music.play(this, R.raw.pop02);break;
                    case 3 :music.play(this, R.raw.pop03);break;
                    case 4 :music.play(this, R.raw.pop04);break;
                    case 5 :music.play(this, R.raw.pop05);break;
                }
                break;
            case 5:
                switch (i1){
                    case 1 :music.play(this, R.raw.binaural01);break;
                    case 2 :music.play(this, R.raw.binaural02);break;
                    case 3 :music.play(this, R.raw.binaural03);break;
                    case 4 :music.play(this, R.raw.binaural04);break;
                    case 5 :music.play(this, R.raw.binaural05);break;
                }
                break;
        }
    }


    @Override
    protected void onStart()
    {
        super.onStart();
        Glide.with(mContext).load(R.drawable.bg_test_classical).apply(myGdiOptions).into(ivBG);
        imgLifeValue.setImageResource(R.drawable.note);

        clsData.SetTestReady(true);
        dX = 0;
        clsLineChart = new CLS_LineChart(3);
        viewLineChart = clsLineChart.viewDrawAppearance(this, true, 30, 100, Color.RED, Color.GREEN);
        viewLineChart2 = clsLineChart.viewDrawAppearance2(this, true, 30, 10,Color.YELLOW,Color.BLUE);
        layoutChart.addView(viewLineChart,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT));
        LCFatigue.addView(viewLineChart2,
                new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.FILL_PARENT,
                        LinearLayout.LayoutParams.FILL_PARENT));
        clsLineChart.AddPoint(1, 0, 0);//????????????????????????????????????
        clsLineChart.AddPoint(2, 0, 0);
        clsLineChart.AddPoint(3, 0, 0);

    }
    //??????==================================================================

    @Override
    protected void onResume() {
        super.onResume();
        StartTimer();
        setMusic();

    }
    @Override
    protected void onPause() {
        super.onPause();
        level=1;
        EndTimer();
        music.stop(this);

    }

    @Override
    public void finish() {
        music.stop(this);
        level=1;
        super.finish();
    }

    //===========================================================================

    @Override
    protected void onStop()
    {
        super.onStop();

        Glide.with(this).clear(ivBG);
        Glide.with(this).clear(ivStopTest);

        bThreadRun = false;
        clsLineChart = null;
        clsData.SetTestReady(false);
    }
    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        m_clockHandler.removeCallbacks(m_clockThread1000);
    }
    @Override
    protected void onRestart()
    {
        super.onRestart();
    }
    @Override
    public void onBackPressed() //??????user????????????????????? "???????????????"
    {
        progress = 31;
        Intent intent = new Intent();
        intent.setClass(this, Main.class);//input info
        startActivity(intent);
    }
    //==============================================================================================
    Handler handler = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0)
            {
                tvTime.setText(Integer.toString(progress));
            }
            if(msg.what==1)
            {
                tvTime.setText("?????????");
            }
        };
    };
    Handler handler2 = new Handler()
    {
        public void handleMessage(android.os.Message msg) {
            if(msg.what==0)
            {
                level+=1;
                Animation_Original_LifeCount();

                mDlgNext = new Dialog(Test.this);
                mDlgNext.setCancelable(false);//???????????????
                mDlgNext.requestWindowFeature(Window.FEATURE_NO_TITLE);//?????????
                mDlgNext.setContentView(R.layout.dlg_next);
                Window window=mDlgNext.getWindow();//??????????????????
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//??????????????????
                tvNextLevel = (TextView) mDlgNext.findViewById(R.id.tvNextLevel);//?????????
                tvIntroduction = mDlgNext.findViewById(R.id.tvIntroduction);
                tvLevelDifficulty = mDlgNext.findViewById(R.id.tvLevelDifficulty);
                tvCombo = mDlgNext.findViewById(R.id.tvCombo);
                tvBeforeGain = mDlgNext.findViewById(R.id.tvBeforeGain);
                tvBeforeCombo = mDlgNext.findViewById(R.id.tvBeforeCombo);
                tvChallengeWhat = mDlgNext.findViewById(R.id.tvChallengeWhat);
                tvChallengeSong = mDlgNext.findViewById(R.id.tvChallengeSong);
                imglevelatt = (ImageView) mDlgNext.findViewById(R.id.imglevelatt);
                imglevelma = (ImageView) mDlgNext.findViewById(R.id.imglevelma);
                Button loginBtnNext = mDlgNext.findViewById(R.id.btnNext);

                switch (level){ //?????????????????????
                    case 1 :
                        //???Main????????????~~~~~~~~~
                        break;

                    case 2 :

                        tvNextLevel.setText("?????????");
                        tvBeforeGain.setText("??????????????? " + LifeValue + " ????????????");
                        tvBeforeCombo.setText("??????????????? " + ComboCount1 + " ???Combo");
                        //=
                        tvChallengeWhat.setText("??????40+");
                        tvChallengeWhat.setBackgroundResource(R.drawable.challenge_meditation);
                        tvChallengeSong.setText("??????");
                        tvChallengeSong.setBackgroundResource(R.drawable.challenge_song);
                        imglevelatt.setAlpha(0f);//??????????????????
                        imglevelma.setAlpha(1f);//?????????????????????
                        //=
                        tvIntroduction.setText("?????????????????????????????????????????????" + "\n"
                                                + "???????????????????????????????????????" + "\n"
                                                + "?????? 5 ??????????????????");
                        //=
                        tvLevelDifficulty.setText("????????? : ??????????????? +4???");
                        tvCombo.setText("Combo : ??????Combo +6???");
                        //=
                        //loginBtnNext.setText("???????????????");
                        //=
                        imgLifeValue.setImageResource(R.drawable.flowerscore);
                        Glide.with(mContext).load(R.drawable.bg_test_nature).apply(myGdiOptions).into(ivBG);
                        //=
                        Checkpoint1 = LifeValue;  //?????????????????????
                        AttentionAvg1 = AttentionTotal / 30; //30 = (progress - 1)
                        MeditationAvg1 = MeditationTotal / 30;  //30 = (progress - 1)

                        //AttentionAvg1 = AttentionTimes;
                        //RecommendCount_M1 = MeditationTimes;
                        LifeValue = 0;  //???????????????(???????????????????????????????????????0)
                        AttentionTotal = 0;
                        MeditationTotal = 0;

                        break;

                    case 3 :
                        tvNextLevel.setText("?????????");
                        tvBeforeGain.setText("??????????????? " + LifeValue + " ????????????");
                        tvBeforeCombo.setText("??????????????? " + ComboCount2 + " ???Combo");
                        //=
                        tvChallengeWhat.setText("??????50+");
                        tvChallengeWhat.setBackgroundResource(R.drawable.challenge_attention);
                        tvChallengeSong.setText("??????");
                        tvChallengeSong.setBackgroundResource(R.drawable.challenge_song);
                        imglevelatt.setAlpha(1f);//?????????????????????
                        imglevelma.setAlpha(0f);//??????????????????
                        //=
                        tvIntroduction.setText("??????????????????????????????????????????" + "\n"
                                + "????????????????????????????????????????????????" + "\n"
                                + "?????? 5 ?????????????????????");
                        //=
                        tvLevelDifficulty.setText("????????? : ??????????????? +4???");
                        tvCombo.setText("Combo : ??????Combo +7???");
                        //=
                        //loginBtnNext.setText("???????????????");
                        //=
                        imgLifeValue.setImageResource(R.drawable.note);
                        Glide.with(mContext).load(R.drawable.bg_test_hot).apply(myGdiOptions).into(ivBG);
                        //=
                        Checkpoint2 = LifeValue;
                        AttentionAvg2 = AttentionTotal / 30;
                        MeditationAvg2 = MeditationTotal / 30;
                        LifeValue = 5;  //???????????????(???????????????????????????????????????5)
                        AttentionTotal = 0;
                        MeditationTotal = 0;
                        break;

                    case 4 :
                        tvNextLevel.setText("?????????");
                        tvBeforeGain.setText("??????????????? " + LifeValue + " ????????????");
                        tvBeforeCombo.setText("??????????????? " + ComboCount3 + " ???Combo");
                        //=
                        tvChallengeWhat.setText("??????60+");
                        tvChallengeWhat.setBackgroundResource(R.drawable.challenge_meditation);
                        tvChallengeSong.setText("??????");
                        tvChallengeSong.setBackgroundResource(R.drawable.challenge_song);
                        imglevelatt.setAlpha(0f);
                        imglevelma.setAlpha(1f);
                        //=
                        tvIntroduction.setText("?????????????????????????????????????????????" + "\n"
                                + "???????????????????????????????????????" + "\n"
                                + "?????? 5 ??????????????????");
                        //=
                        tvLevelDifficulty.setText("????????? : ??????????????? +4???");
                        tvCombo.setText("Combo : ??????Combo +8???");
                        //=
                        //loginBtnNext.setText("???????????????");
                        //=
                        imgLifeValue.setImageResource(R.drawable.flowerscore);
                        Glide.with(mContext).load(R.drawable.bg_test_popular).apply(myGdiOptions).into(ivBG);
                        Checkpoint3 = LifeValue;
                        AttentionAvg3 = AttentionTotal / 30;
                        MeditationAvg3 = MeditationTotal / 30;
                        LifeValue = 0;
                        AttentionTotal = 0;
                        MeditationTotal = 0;
                        break;

                    case 5: //??????5=====
                        tvNextLevel.setText("?????????");
                        tvBeforeGain.setText("??????????????? " + LifeValue + " ????????????");
                        tvBeforeCombo.setText("??????????????? " + ComboCount4 + " ???Combo");
                        //=
                        tvChallengeWhat.setText("??????70+");
                        tvChallengeWhat.setBackgroundResource(R.drawable.challenge_attention);
                        tvChallengeSong.setText("????????????");
                        tvChallengeSong.setBackgroundResource(R.drawable.challenge_song);
                        imglevelatt.setAlpha(1f);
                        imglevelma.setAlpha(0f);
                        //=
                        tvIntroduction.setText("??????????????????????????????????????????" + "\n"
                                + "????????????????????????????????????????????????" + "\n"
                                + "?????? 5 ?????????????????????");
                        //=
                        tvLevelDifficulty.setText("????????? : ??????????????? +4???");
                        tvCombo.setText("Combo : ??????Combo +9???");
                        //=
                        //loginBtnNext.setText("???????????????");
                        //=
                        imgLifeValue.setImageResource(R.drawable.note);
                        Glide.with(mContext).load(R.drawable.bg_test_mysterious).apply(myGdiOptions).into(ivBG);
                        Checkpoint4 = LifeValue;
                        AttentionAvg4 = AttentionTotal / 30;
                        MeditationAvg4 = MeditationTotal / 30;
                        LifeValue = 5;
                        AttentionTotal = 0;
                        MeditationTotal = 0;
                        break;

                    case 6: //??????=====
                        tvNextLevel.setText("????????????");
                        tvBeforeGain.setText("??????????????? " + LifeValue + " ????????????");
                        tvBeforeCombo.setText("??????????????? " + ComboCount5 + " ???Combo");
                        imglevelatt.setAlpha(0f);
                        imglevelma.setAlpha(0f);
                        //=
                        tvChallengeWhat.setText("");
                        tvChallengeSong.setText("");
                        //=
                        tvIntroduction.setText("???????????????" + "\n" + "?????????????????????!");
                        //=
                        tvLevelDifficulty.setText("");
                        tvCombo.setText("");
                        //=
                        loginBtnNext.setText("????????????");
                        //=
                        Checkpoint5 = LifeValue;
                        AttentionAvg5 = AttentionTotal / 30;
                        MeditationAvg5 = MeditationTotal / 30;
                        AttentionTotal = 0;
                        MeditationTotal = 0;
                        break;
                }

                loginBtnNext.setOnClickListener(loginDlgBtnNextOnClick);
                mDlgNext.show();
                // ?????????????????????????????????????????????????????????
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = mDlgNext.getWindow().getAttributes();
                lp.width = (int)(display.getWidth() * 0.95);//????????????
                mDlgNext.getWindow().setAttributes(lp);
            }
        };
    };




//???????????????============================================================================================================================================
    public void Animation_Ghost(){  //?????????
        animTxtAlpha = ObjectAnimator.ofFloat(imgGhost, "alpha", 1, (float)0.3);
        animTxtAlpha2 = ObjectAnimator.ofFloat(imgAudience, "alpha", 0, 0);
        animTxtAlpha.setDuration(500);
        animTxtAlpha.setRepeatCount(-1);
        animTxtAlpha.setRepeatMode(ObjectAnimator.REVERSE);
        animTxtAlpha.setInterpolator(new LinearInterpolator());
        animTxtAlpha2.setRepeatCount(-1);
        animTxtAlpha2.setRepeatMode(ObjectAnimator.REVERSE);
        animTxtAlpha2.setInterpolator(new LinearInterpolator());
        animTxtAlpha.start();
        animTxtAlpha2.start();
    }


    public void Animation_Audience(){   //????????????
        animTxtAlpha = ObjectAnimator.ofFloat(imgGhost, "alpha",0,0);
        animTxtAlpha2 = ObjectAnimator.ofFloat(imgAudience, "alpha", 1, 1);
        animTxtAlpha.setRepeatCount(-1);
        animTxtAlpha.setRepeatMode(ObjectAnimator.REVERSE);
        animTxtAlpha.setInterpolator(new LinearInterpolator());
        animTxtAlpha2.setRepeatCount(-1);
        animTxtAlpha2.setRepeatMode(ObjectAnimator.REVERSE);
        animTxtAlpha2.setInterpolator(new LinearInterpolator());
        animTxtAlpha.start();
        animTxtAlpha2.start();
    }

    public void Animation_Ghost_Loading(){  //???????????????????????????
        animTxtAlpha = ObjectAnimator.ofFloat(imgGhost, "alpha",1,(float)0.3);
        animTxtAlpha2 = ObjectAnimator.ofFloat(imgAudience, "alpha", 0, 0);
        animTxtAlpha.setDuration(250);
        animTxtAlpha.setRepeatCount(-1);
        animTxtAlpha.setRepeatMode(ObjectAnimator.REVERSE);
        animTxtAlpha.setInterpolator(new LinearInterpolator());
        animTxtAlpha2.setDuration(250);
        animTxtAlpha2.setRepeatCount(-1);
        animTxtAlpha2.setRepeatMode(ObjectAnimator.REVERSE);
        animTxtAlpha2.setInterpolator(new LinearInterpolator());
        animTxtAlpha.start();
        animTxtAlpha2.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void Animation_Audience_Loading(){   //??????????????????????????????
        animTxtAlpha = ObjectAnimator.ofFloat(imgGhost, "alpha",0, 0);
        animTxtAlpha2 = ObjectAnimator.ofFloat(imgAudience, "alpha", 1, (float)0.3);
        animTxtAlpha.setDuration(250);
        animTxtAlpha.setRepeatCount(-1);
        animTxtAlpha.setRepeatMode(ObjectAnimator.REVERSE);
        animTxtAlpha.setInterpolator(new LinearInterpolator());
        animTxtAlpha2.setDuration(250);
        animTxtAlpha2.setRepeatCount(-1);
        animTxtAlpha2.setRepeatMode(ObjectAnimator.REVERSE);
        animTxtAlpha2.setInterpolator(new LinearInterpolator());
        animTxtAlpha.start();
        animTxtAlpha2.start();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    public void Animation_Flower_visible(){ //?????????
        if(LifeValue == 0){
            animFlower01 = ObjectAnimator.ofFloat(imgFlower01, "alpha",0, 0);animFlower01.start();
            animFlower02 = ObjectAnimator.ofFloat(imgFlower02, "alpha",0, 0);animFlower02.start();
            animFlower03 = ObjectAnimator.ofFloat(imgFlower03, "alpha",0, 0);animFlower03.start();
            animFlower04 = ObjectAnimator.ofFloat(imgFlower04, "alpha",0, 0);animFlower04.start();
            animFlower05 = ObjectAnimator.ofFloat(imgFlower05, "alpha",0, 0);animFlower05.start();
            animNote01 = ObjectAnimator.ofFloat(imgNote01, "alpha",0, 0);animNote01.start();
            animNote02 = ObjectAnimator.ofFloat(imgNote02, "alpha",0, 0);animNote02.start();
            animNote03 = ObjectAnimator.ofFloat(imgNote03, "alpha",0, 0);animNote03.start();
            animNote04 = ObjectAnimator.ofFloat(imgNote04, "alpha",0, 0);animNote04.start();
            animNote05 = ObjectAnimator.ofFloat(imgNote05, "alpha",0, 0);animNote05.start();
        }else {

            switch (LifeValue) {
                case 1:
                    animFlower01 = ObjectAnimator.ofFloat(imgFlower01, "alpha", 1, 1);
                    animFlower01.setStartDelay(400);
                    animFlower01.start();
                    break;
                case 2:
                    animFlower02 = ObjectAnimator.ofFloat(imgFlower02, "alpha", 1, 1);
                    animFlower02.setStartDelay(400);
                    animFlower02.start();
                    break;
                case 3:
                    animFlower03 = ObjectAnimator.ofFloat(imgFlower03, "alpha", 1, 1);
                    animFlower03.setStartDelay(400);
                    animFlower03.start();
                    break;
                case 4:
                    animFlower04 = ObjectAnimator.ofFloat(imgFlower04, "alpha", 1, 1);
                    animFlower04.setStartDelay(400);
                    animFlower04.start();
                    break;
                case 5:
                    animFlower05 = ObjectAnimator.ofFloat(imgFlower05, "alpha", 1, 1);
                    animFlower05.setStartDelay(400);
                    animFlower05.start();
                    break;
            }
        }
    }

    public void Animation_Note_visible(){   //????????????
        if(LifeValue ==5){
            animFlower01 = ObjectAnimator.ofFloat(imgFlower01, "alpha",0, 0);animFlower01.start();
            animFlower02 = ObjectAnimator.ofFloat(imgFlower02, "alpha",0, 0);animFlower02.start();
            animFlower03 = ObjectAnimator.ofFloat(imgFlower03, "alpha",0, 0);animFlower03.start();
            animFlower04 = ObjectAnimator.ofFloat(imgFlower04, "alpha",0, 0);animFlower04.start();
            animFlower05 = ObjectAnimator.ofFloat(imgFlower05, "alpha",0, 0);animFlower05.start();
            animNote01 = ObjectAnimator.ofFloat(imgNote01, "alpha",1, 1);animNote01.start();
            animNote02 = ObjectAnimator.ofFloat(imgNote02, "alpha",1, 1);animNote02.start();
            animNote03 = ObjectAnimator.ofFloat(imgNote03, "alpha",1, 1);animNote03.start();
            animNote04 = ObjectAnimator.ofFloat(imgNote04, "alpha",1, 1);animNote04.start();
            animNote05 = ObjectAnimator.ofFloat(imgNote05, "alpha",1, 1);animNote05.start();
        }else {
            switch (LifeValue) {
                case 0:
                    animNote01 = ObjectAnimator.ofFloat(imgNote01, "alpha", 0, 0);
                    animNote01.setStartDelay(400);
                    animNote01.start();
                    break;
                case 1:
                    animNote02 = ObjectAnimator.ofFloat(imgNote02, "alpha", 0, 0);
                    animNote02.setStartDelay(400);
                    animNote02.start();
                    break;
                case 2:
                    animNote03 = ObjectAnimator.ofFloat(imgNote03, "alpha", 0, 0);
                    animNote03.setStartDelay(400);
                    animNote03.start();
                    break;
                case 3:
                    animNote04 = ObjectAnimator.ofFloat(imgNote04, "alpha", 0, 0);
                    animNote04.setStartDelay(400);
                    animNote04.start();
                    break;
                case 4:
                    animNote05 = ObjectAnimator.ofFloat(imgNote05, "alpha", 0, 0);
                    animNote05.setStartDelay(400);
                    animNote05.start();
                    break;
            }
        }
    }

    public void Animation_Reset(){  //??????????????????????????????
        set1 = (float) BaseLine;
        set2 = (float) BaseLine;
    }

    public void Animation_Hit(){    //??????????????????
        animDog = ObjectAnimator.ofFloat(imgDog, "alpha", 1, (float)0.3);
        animDog.setDuration(200);
        animDog.setRepeatCount(3);
        animDog.setRepeatMode(ObjectAnimator.REVERSE);
        animDog.setInterpolator(new LinearInterpolator());
        animDog.start();
    }

    public void Animation_GhostMove(){  //???????????????
        float point1=set1, point2=set2;
        set1 = set2;  //???????????????
        AnimatorSet animTxtMove = new AnimatorSet();
        ObjectAnimator animTxtMove1 =
                ObjectAnimator.ofFloat(imgGhost, "y", point1, point2);
        animTxtMove1.setDuration(500);
        animTxtMove1.setInterpolator(new AccelerateDecelerateInterpolator());
        animTxtMove.playSequentially(animTxtMove1);
        animTxtMove.start();
    }

    public void Animation_AudienceMove(){   //??????????????????
        float point1=set1, point2=set2;
        set1 = set2;  //???????????????
        AnimatorSet animTxtMove = new AnimatorSet();
        ObjectAnimator animTxtMove1 =
                ObjectAnimator.ofFloat(imgAudience, "y", point1, point2);
        animTxtMove1.setDuration(500);
        animTxtMove1.setInterpolator(new AccelerateDecelerateInterpolator());
        animTxtMove.playSequentially(animTxtMove1);
        animTxtMove.start();
    }

    public void Animation_Combo(){  //Combo????????????
        animCombo = ObjectAnimator.ofFloat(imgCombo, "alpha",0, 1);
        animCombo.setDuration(300);
        animCombo.start();
        animCombo = ObjectAnimator.ofFloat(imgCombo, "alpha",1, 0);
        animCombo.setDuration(1700);
        animCombo.start();
    }

    public void SetValueColor_A(){  //?????????Combo??????
        tvAM.setTextColor(Color.parseColor("#EF5350"));
        tvAMvalue.setTextColor(Color.parseColor("#EF5350"));
    }

    public void SetValueColor_M(){  //?????????Combo??????
        tvAM.setTextColor(Color.parseColor("#66BB6A"));
        tvAMvalue.setTextColor(Color.parseColor("#66BB6A"));
    }

    public void SetValueColor_O(){  //?????????????????????
        tvAM.setTextColor(Color.parseColor("#ECECEC"));
        tvAMvalue.setTextColor(Color.parseColor("#ECECEC"));
    }

    public void Animation_Note_LifeCount(){ //?????????????????????
        tvLifeCountX.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        tvLifeCountX.setTextColor(Color.parseColor("#EF5350"));
        tvLifeValue.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        tvLifeValue.setTextColor(Color.parseColor("#EF5350"));
    }

    public void Animation_Flower_LifeCount(){ //?????????????????????
        tvLifeCountX.setTextSize(TypedValue.COMPLEX_UNIT_SP, 30);
        tvLifeCountX.setTextColor(Color.parseColor("#66BB6A"));
        tvLifeValue.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        tvLifeValue.setTextColor(Color.parseColor("#66BB6A"));
    }

    public void Animation_Original_LifeCount(){
        tvLifeCountX.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        tvLifeCountX.setTextColor(Color.parseColor("#ECECEC"));
        tvLifeValue.setTextSize(TypedValue.COMPLEX_UNIT_SP,22);
        tvLifeValue.setTextColor(Color.parseColor("#ECECEC"));
    }
//=======================================================================================================================================================
//????????????================================================================================================================================================
    public void SetNoteValue(){
        if(0 < LifeValue && LifeValue <= 6){
            LifeValue--;
            Animation_Note_LifeCount();
        }else{
            LifeValue = 0;
        }
    }

    public void SetFlowerValue(){
        if(0 <= LifeValue && LifeValue < 5){
            LifeValue++;
            Animation_Flower_LifeCount();
        }else{
            LifeValue = 5;
        }
    }



    public void ComboCount(){
        tpA1 = tpA2;
        tpM1 = tpM2;
        tpA2 = clsData.iGetAttention();
        tpM2 = clsData.iGetMeditation();

        switch (level){
            case 1 :
                if(tpA1 >= 80 && tpA2 >= 80){
                    triple++;
                    SetValueColor_A();

                    if(triple == 3){
                        ComboCount1++;
                        Animation_Combo();
                        triple = 0;
                    }
                }else{
                    triple = 0;
                    animCombo = ObjectAnimator.ofFloat(imgCombo, "alpha",0, 0);
                    animCombo.start();
                    SetValueColor_O();
                }
                break;
            case 2:
                if(tpM1 >= 80 && tpM2 >= 80){
                    triple++;
                    SetValueColor_M();
                    if(triple == 3){
                        ComboCount2++;
                        Animation_Combo();
                        triple = 0;
                    }
                }else{
                    triple = 0;
                    animCombo = ObjectAnimator.ofFloat(imgCombo, "alpha",0, 0);
                    animCombo.start();
                    SetValueColor_O();
                }
                break;
            case 3:
                if(tpA1 >= 80 && tpA2 >= 80){
                    triple++;
                    SetValueColor_A();
                    if(triple == 3){
                        ComboCount3++;
                        Animation_Combo();
                        triple = 0;
                    }
                }else{
                    triple = 0;
                    animCombo = ObjectAnimator.ofFloat(imgCombo, "alpha",0, 0);
                    animCombo.start();
                    SetValueColor_O();
                }
                break;
            case 4:
                if(tpM1 >= 80 && tpM2 >= 80){
                    triple++;
                    SetValueColor_M();
                    if(triple == 3){
                        ComboCount4++;
                        Animation_Combo();
                        triple = 0;
                    }
                }else{
                    triple = 0;
                    animCombo = ObjectAnimator.ofFloat(imgCombo, "alpha",0, 0);
                    animCombo.start();
                    SetValueColor_O();
                }
                break;
            case 5:
                if(tpA1 >=80 && tpA2 >=80){
                    triple++;
                    SetValueColor_A();
                    if(triple == 3){
                        ComboCount5++;
                        Animation_Combo();
                        triple = 0;
                    }
                }else{
                    triple = 0;
                    animCombo = ObjectAnimator.ofFloat(imgCombo, "alpha",0, 0);
                    animCombo.start();
                    SetValueColor_O();
                }
                break;
        }

    }

    /*public void RecommendCount(){  //????????????
        if(clsData.iGetAttention() >= 80){
            AttentionTimes++;
        }
        if(clsData.iGetMeditation() >= 80){
            MeditationTimes++;
        }
    }*/
//=======================================================================================================================================================
    public void StartTimer() {
        //??????timer???timerTask????????????null???

        if (timer == null&&timerTask==null) {
            //??????timer???timerTask
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Bundle bundle = getIntent().getExtras();
                    String CheckRB = bundle.getString("CheckRB");
                    float fCheckRB = Float.parseFloat(CheckRB);
                    //??????progress??????
                    progress--;
                    handler.sendEmptyMessage(0);
                    if (progress <= 0) {
                        handler.sendEmptyMessage(1);
                        Animation_Reset();
                        //???????????????
                        clsData.SetLD = "True";
                        clsData.SetBrainData(iGoodSignal, iAttention=-1, iMeditation=-1,iFatigue=-1, iDelta=-1, iTheta=-1,
                                iLowAlpha=-1, iHighAlpha=-1, iLowBeta=-1, iHighBeta=-1, iLowGamma=-1, iHighGamma=-1);
                        switch((int) fCheckRB){
                            case 1 : Main.addSql2();break;
                            case 2 : break;//CLS_DATA(Ctrl+F => LoadingData);
                            case 3 : AddDataToSheets();break;
                        }
                        if(progress==0) {
                            if(level<6)
                            handler2.sendEmptyMessage(0);
                        }
                    }
                    else {
                        clsData.SetLD = "False";
                        clsData.SetBrainData(iGoodSignal, iAttention, iMeditation,iFatigue, iDelta, iTheta,
                                iLowAlpha, iHighAlpha, iLowBeta, iHighBeta, iLowGamma, iHighGamma);
                        switch ((int) fCheckRB){
                            case 1 : Main.addSql2();break;
                            case 2 : break;//CLS_DATA(Ctrl+F => LoadingData);
                            case 3 : AddDataToSheets();break;
                        }


                    }
                    //?????????????????????
                    pb.setProgress(progress);
                }
            };
            /*????????????timer,???????????????????????????????????????
            ??????????????????????????????????????????????????????????????????Date??????????????????????????????????????????????????????
            ????????????????????????????????????????????????????????????????????????*/
            timer.schedule(timerTask, 0, 1000);
        }
    }

    private void AddDataToSheets() {
        new addDataToSheet().execute("https://script.google.com/macros/s/AKfycbyQ0RLFNohoVVE4O4T_eROfEp0evCgdO3hy4ojKeHrkOQ4tDjyK76SE5b9g9O6tXhC1/exec");
    }


    public void EndTimer()
    {
        timer.cancel();
        timerTask.cancel();
        timer=null;
        timerTask=null;

    }

    public void GoToResult(){
        Intent intent = new Intent();
        intent.setClass(this, Result.class);//input info
        startActivity(intent);
    }

    private final View.OnClickListener loginDlgBtnNextOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            //Toast.makeText(Test.this, "?????????", Toast.LENGTH_LONG).show();
            if(level == 6){
                Bundle bundle = getIntent().getExtras();
                String CheckRB = bundle.getString("CheckRB");
                float fCheckRB = Float.parseFloat(CheckRB);

                if (fCheckRB == 1){
                    Main.addSql();
                    Toast.makeText(Test.this, "???????????????SQLite???", Toast.LENGTH_LONG).show();
                }
                if (fCheckRB == 2){
                    clsData.WriteXLS(mContext);
                    Toast.makeText(Test.this, "???????????????Excel???", Toast.LENGTH_LONG).show();
                }
                if (fCheckRB == 3){
                    Toast.makeText(Test.this, "???????????????Google Sheets???", Toast.LENGTH_LONG).show();
                }
                GoToResult();
            }else{
                progress = 31;
                setMusic();
                mDlgNext.dismiss();
            }

        }

    };
    //==============================================================================================
    //==============================================================================================
    public void SetCallback()
    {
        clsBrainwave.SetCallback_BWTest(new CLS_BrainWave.Brainwave_Callback_Test()
        {
            //@Override
            public void Do(int iCmd, int iVal)
            {
                switch(iCmd)
                {
                    case 0:
                        //System.out.println("work");
                        //ShowToast("!! data callback ok !!");
                        break;
                }
                //
                if(iCmd == S.BrainwaveValue)
                {
                    SendProcessMessage(S.BrainwaveValue);
                }
            }
        });
    }
    //==============================================================================================
    void StopTest()
    {
        Bundle bundle = getIntent().getExtras();
        String CheckRB = bundle.getString("CheckRB");
        float fCheckRB = Float.parseFloat(CheckRB);

        if (fCheckRB == 1){
            Main.addSql();
            Toast.makeText(Test.this, "???????????????SQLite???", Toast.LENGTH_LONG).show();
        }
        if (fCheckRB == 2){
            clsData.WriteXLS(mContext);
            Toast.makeText(Test.this, "???????????????Excel???", Toast.LENGTH_LONG).show();
        }
        if (fCheckRB == 3){
            Toast.makeText(Test.this, "???????????????Google Sheets???", Toast.LENGTH_LONG).show();
        }

        try
        {
            bThreadRun = false;

            Intent intent = new Intent();
            intent.setClass(this, Report.class);
            startActivity(intent);
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage().toString());
        }
    }
    //==============================================================================================
    public void ivStopTest_OnClick(View view) {

        StopTest();
    }

    //==============================================================================================
    private class ClockThread1000 extends Thread
    {
        @Override
        public void run()
        {
            try
            {
                if (!bThreadRun)
                    return;
                m_clockHandler.postDelayed(m_clockThread1000, 1000);
                SendProcessMessage(1000);
            }
            catch(Exception ex)
            {
                System.out.println(ex.getMessage().toString());
            }
        }
    }
    //==============================================================================================
    void SendProcessMessage(int iMsg)
    {
        Message _message = new Message();
        _message.what = iMsg;
        m_processMessageHandler.sendMessage(_message);
    }

    Handler m_processMessageHandler = new Handler()
    {




        @SuppressLint({"HandlerLeak", "ResourceType"})
        @Override
        public void handleMessage(Message msg)
        {
            //imgGhost.setImageResource(R.drawable.);   //------??????????????????[1]------
            super.handleMessage(msg);

            switch (msg.what)
            {
                case 1:
                    try
                    {

                    }
                    catch (Exception ex)
                    {
                        System.out.println(ex.getMessage().toString());
                    }
                    break;
                case 1000:
                    //textViewCountDown.setText(String.valueOf(iCountDownTimer));
                    /*
                    iCountDownTimer--;
                    if(iCountDownTimer < 0)
                        StopTest();
                    */
                    break;
            }
            //
            if(msg.what == S.BrainwaveValue)
            {
                Bundle bundle = getIntent().getExtras();
                String CheckRB = bundle.getString("CheckRB");
                float fCheckRB = Float.parseFloat(CheckRB);
//??????=======================================================================================================
                Animation_Original_LifeCount();

                switch (level) {
                    case 1 : //??????1=====
                        Animation_Ghost();
                        Animation_Note_visible();  //???????????????(???)

                        tvLifeValue.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvLifeValue.setText(String.valueOf(LifeValue));
                            }
                        }, 500);

                        if(clsData.iGetAttention() == -1) {
                            Animation_Reset();
                            Animation_Ghost_Loading();
                        }else{
                            if (clsData.iGetAttention() <= 30) {
                                set2 = (float) GhostClose;  //==??????
                                Animation_GhostMove();
                                Animation_Hit();  //????????????????????????
                                SetNoteValue();   //????????????
                            } else {
                                set2 = (float) GhostClose + clsData.iGetAttention() * 9;  //==??????
                                Animation_GhostMove();
                            }
                        }
                        ComboCount();
                        AttentionTotal += clsData.iGetAttention();
                        MeditationTotal += clsData.iGetMeditation();
                        //RecommendCount();
                        clsLineChart.AddPoint(1, dX, clsData.iGetAttention());
                        clsLineChart.AddPoint(2, dX, clsData.iGetMeditation());
                        tvAM.setText("?????????");
                        tvAMvalue.setText(String.valueOf(clsData.iGetAttention()));
                        break;

                    case 2 :  //??????2=====
                        Animation_Audience();
                        Animation_Flower_visible();

                        tvLifeValue.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvLifeValue.setText(String.valueOf(LifeValue));
                            }
                        }, 500);  //???????????????

                        if(clsData.iGetMeditation() == -1) {
                            Animation_Reset();
                            Animation_Audience_Loading();
                        }else{
                            if (clsData.iGetMeditation() <= 40) {
                                set2 = (float) (BaseLine - clsData.iGetMeditation() * 9);  //==??????
                                Animation_AudienceMove();
                            } else {
                                set2 = (float) AudienceClose;  //==??????
                                Animation_AudienceMove();
                                SetFlowerValue();
                            }
                        }
                        ComboCount();
                        AttentionTotal += clsData.iGetAttention();
                        MeditationTotal += clsData.iGetMeditation();
                        //RecommendCount();
                        clsLineChart.AddPoint(1, dX, clsData.iGetAttention());
                        clsLineChart.AddPoint(2, dX, clsData.iGetMeditation());
                        tvAM.setText("?????????");
                        tvAMvalue.setText(String.valueOf(clsData.iGetMeditation()));
                        break;

                    case 3 :  //??????3=====
                        Animation_Ghost();
                        Animation_Note_visible();  //???????????????(???)

                        tvLifeValue.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvLifeValue.setText(String.valueOf(LifeValue));
                            }
                        }, 500);

                        if(clsData.iGetAttention() == -1) {
                            Animation_Reset();
                            Animation_Ghost_Loading();
                        }else{
                            if (clsData.iGetAttention() <= 50) {
                                set2 = (float) GhostClose;  //==??????
                                Animation_GhostMove();
                                Animation_Hit();  //????????????????????????
                                SetNoteValue();   //????????????
                            } else {
                                set2 = (float) GhostClose + clsData.iGetAttention() * 9;  //==??????
                                Animation_GhostMove();
                            }
                        }
                        ComboCount();
                        AttentionTotal += clsData.iGetAttention();
                        MeditationTotal += clsData.iGetMeditation();
                        //RecommendCount();
                        clsLineChart.AddPoint(1, dX, clsData.iGetAttention());
                        clsLineChart.AddPoint(2, dX, clsData.iGetMeditation());
                        tvAM.setText("?????????");
                        tvAMvalue.setText(String.valueOf(clsData.iGetAttention()));
                        break;

                    case 4 :  //??????4=====
                        Animation_Audience();
                        Animation_Flower_visible();

                        tvLifeValue.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvLifeValue.setText(String.valueOf(LifeValue));
                            }
                        }, 500);  //???????????????

                        if(clsData.iGetMeditation() == -1) {
                            Animation_Reset();
                            Animation_Audience_Loading();
                        }else{
                            if (clsData.iGetMeditation() <= 60) {
                                set2 = (float) (BaseLine - clsData.iGetMeditation() * 9);  //==??????
                                Animation_AudienceMove();
                            } else {
                                set2 = (float) AudienceClose;  //==??????
                                Animation_AudienceMove();
                                SetFlowerValue();
                            }
                        }
                        ComboCount();
                        AttentionTotal += clsData.iGetAttention();
                        MeditationTotal += clsData.iGetMeditation();
                        //RecommendCount();
                        clsLineChart.AddPoint(1, dX, clsData.iGetAttention());
                        clsLineChart.AddPoint(2, dX, clsData.iGetMeditation());
                        tvAM.setText("?????????");
                        tvAMvalue.setText(String.valueOf(clsData.iGetMeditation()));
                        break;

                    case 5 :  //??????5=====
                        Animation_Ghost();
                        Animation_Note_visible();  //???????????????(???)

                        tvLifeValue.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvLifeValue.setText(String.valueOf(LifeValue));
                            }
                        }, 500);

                        if(clsData.iGetAttention() == -1) {
                            Animation_Reset();
                            Animation_Ghost_Loading();
                        }else{
                            if (clsData.iGetAttention() <= 70) {
                                set2 = (float) GhostClose;  //==??????
                                Animation_GhostMove();
                                Animation_Hit();  //????????????????????????
                                SetNoteValue();   //????????????
                            } else {
                                set2 = (float) GhostClose + clsData.iGetAttention() * 9;  //==??????
                                Animation_GhostMove();
                            }
                        }
                        ComboCount();
                        AttentionTotal += clsData.iGetAttention();
                        MeditationTotal += clsData.iGetMeditation();
                        //RecommendCount();
                        clsLineChart.AddPoint(1, dX, clsData.iGetAttention());
                        clsLineChart.AddPoint(2, dX, clsData.iGetMeditation());
                        tvAM.setText("?????????");
                        tvAMvalue.setText(String.valueOf(clsData.iGetAttention()));
                        break;
                }
                //=============================================================================

                if (clsLineChart == null)
                    return;


                //?????????===============

                //?????????==============

                clsLineChart.AddPoint(3, dX, clsData.iGetFatigue());

                viewLineChart.invalidate();
                viewLineChart2.invalidate();
                dX += 1;

            }
        }
    };


    //==============================================================================================
//????????????????????????????????????????????????????????????
    private class addDataToSheet extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... params) {
            //????????? ??????????????????
            for (String urlStr : params) {
                try {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlStr,
                            new Response.Listener<String>() {   //???????????????Http????????????
                                @Override
                                public void onResponse(String response) {
                                    //??????????????????????????????????????????????????????????????????????????????
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {    //????????????
                                    Log.d("Error", String.valueOf(error));
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {         //????????????
                            Map<String, String> parmas = new HashMap<>();
                            //??????????????????????????????SCRIPT???
                            parmas.put("action", "addRec");
                            //??????SHEET
                            parmas.put("newSheet",sheetboo);
                            //???????????????????????????
                            parmas.put("attention", String.valueOf(clsData.iGetAttention()));
                            parmas.put("meditation", String.valueOf(clsData.iGetMeditation()));
                            parmas.put("fatigue", String.valueOf(clsData.iGetFatigue()));
                            parmas.put("delta", String.valueOf(clsData.iGetDelta()));
                            parmas.put("theta", String.valueOf(clsData.iGetTheta()));
                            parmas.put("lowAlpha", String.valueOf(clsData.iGetLowAlpha()));
                            parmas.put("highAlpha", String.valueOf(clsData.iGetHighAlpha()));
                            parmas.put("lowBeta", String.valueOf(clsData.iGetLowBeta()));
                            parmas.put("highBeta", String.valueOf(clsData.iGetHighBeta()));
                            parmas.put("lowGamma", String.valueOf(clsData.iGetLowGamma()));
                            parmas.put("highGamma", String.valueOf(clsData.iGetHighGamma()));
                            return parmas;
                        }

                    };
                    //???????????????????????????????????????????????????Rec????????????
                    int socketTimeOut = 50000;// u can change this .. here it is 50 seconds //?????????????????????50???????????????HTTP????????????
                    RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut, 0, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
                    stringRequest.setRetryPolicy(retryPolicy);
                    RequestQueue queue = Volley.newRequestQueue(Test.this);
                    queue.add(stringRequest);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
    }
}


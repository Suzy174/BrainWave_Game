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
    // 用來記錄是否MediaPlayer物件需要執行prepareAsync()

    TextView txtList,txtData,tvAMvalue,tvAM,tvTime,tvNextLevel,tvLifeValue,
            tvIntroduction,tvLevelDifficulty,tvBeforeGain,tvBeforeCombo,tvCombo
            ,tvLifeCountX
            ,tvChallengeWhat,tvChallengeSong;
    private static final String DB_FILE = "sample.db", DB_FILE2 = "sample2.db",
            DB_TABLE = "sample", DB_TABLE2 = "sample2" , DB_TABLE_ID = "sample3";
    private DBHelper mySampleDbOpenHelper,mySampleDbOpenHelper2,searchID;
    private LinearLayout mLinLay;
    private ImageView imgGhost,imgDog,imgAudience,imgFlower01,imgFlower02,imgFlower03,imgFlower04,imgFlower05
            ,imgNote01,imgNote02,imgNote03,imgNote04,imgNote05
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
    static int progress = 31;//倒數計時及間
    private ProgressBar pb;
    private Timer timer;
    private TimerTask timerTask;
    static int level = 1;
    RequestOptions myGdiOptions = new RequestOptions().fitCenter();

    float set1=1600;
    float set2=1600;
    int LifeValue = 5;  //第一關起始值
    static int RecommendCount_A1=0,RecommendCount_A2=0,RecommendCount_A3=0,RecommendCount_A4=0,RecommendCount_A5=0;
    static int RecommendCount_M1=0,RecommendCount_M2=0,RecommendCount_M3=0,RecommendCount_M4=0,RecommendCount_M5=0;
    static int Checkpoint1=0,Checkpoint2=0,Checkpoint3=0,Checkpoint4=0,Checkpoint5=0;  //紀錄每關的生命值
    static int ComboCount1=0, ComboCount2=0, ComboCount3=0, ComboCount4=0, ComboCount5=0;
    static int AttentionTimes=0,MeditationTimes=0;
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

//建立資料表-----------------------------------------------------------------------------------------


        mContext = this;


        txtList = (TextView) findViewById(R.id.txtList);
        txtData = (TextView) findViewById(R.id.txtData);
        tvTime = (TextView) findViewById(R.id.tvTime);
        //chartPreMed = (PieChart) findViewById(R.id.pieChartMed);
        pb = (ProgressBar) findViewById(R.id.progressBar);


        float fSize = 10f;

        //設置進度條的最大數值
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
        width = metric.widthPixels;     // 螢幕寬度（畫素）
        height = metric.heightPixels;   // 螢幕高度（畫素）
        density = metric.density;      // 螢幕密度（0.75 / 1.0 / 1.5）
        densityDpi = metric.densityDpi;  // 螢幕密度DPI（120 / 160 / 240）

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
            case 2: //等級2=====
                switch (i1){
                    case 1 :music.play(this, R.raw.calm01);break;
                    case 2 :music.play(this, R.raw.calm02);break;
                    case 3 :music.play(this, R.raw.calm03);break;
                    case 4 :music.play(this, R.raw.calm04);break;
                    case 5 :music.play(this, R.raw.calm05);break;
                }
                break;
            case 3: //等級3=====
                switch (i1){
                    case 1 :music.play(this, R.raw.passionate01);break;
                    case 2 :music.play(this, R.raw.passionate02);break;
                    case 3 :music.play(this, R.raw.passionate03);break;
                    case 4 :music.play(this, R.raw.passionate04);break;
                    case 5 :music.play(this, R.raw.passionate05);break;
                }
                break;
            case 4: //等級4=====
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
        clsLineChart.AddPoint(1, 0, 0);//要有資料才有辦法調整大小
        clsLineChart.AddPoint(2, 0, 0);
        clsLineChart.AddPoint(3, 0, 0);

    }
    //動畫==================================================================

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
    public void onBackPressed() //不讓user按螢幕左下方的 "退回上一層"
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
                tvTime.setText("時間到");
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
                mDlgNext.setCancelable(false);//不能按返回
                mDlgNext.requestWindowFeature(Window.FEATURE_NO_TITLE);//去標題
                mDlgNext.setContentView(R.layout.dlg_next);
                Window window=mDlgNext.getWindow();//解決直角問題
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));//解決直角問題
                tvNextLevel = (TextView) mDlgNext.findViewById(R.id.tvNextLevel);//換內容
                tvIntroduction = mDlgNext.findViewById(R.id.tvIntroduction);
                tvLevelDifficulty = mDlgNext.findViewById(R.id.tvLevelDifficulty);
                tvCombo = mDlgNext.findViewById(R.id.tvCombo);
                tvBeforeGain = mDlgNext.findViewById(R.id.tvBeforeGain);
                tvBeforeCombo = mDlgNext.findViewById(R.id.tvBeforeCombo);
                tvChallengeWhat = mDlgNext.findViewById(R.id.tvChallengeWhat);
                tvChallengeSong = mDlgNext.findViewById(R.id.tvChallengeSong);
                Button loginBtnNext = mDlgNext.findViewById(R.id.btnNext);

                switch (level){ //訊息及切換背景
                    case 1 :
                        //在Main中更改喔~~~~~~~~~
                        break;

                    case 2 :

                        tvNextLevel.setText("第二關");
                        tvBeforeGain.setText("前一關獲得 " + LifeValue + " 點生命點");
                        tvBeforeCombo.setText("前一關獲得 " + ComboCount1 + " 點Combo");
                        //=
                        tvChallengeWhat.setText("放鬆40+");
                        tvChallengeWhat.setBackgroundResource(R.drawable.challenge_meditation);
                        tvChallengeSong.setText("平靜");
                        tvChallengeSong.setBackgroundResource(R.drawable.challenge_song);
                        //=
                        tvIntroduction.setText("音樂家要利用放鬆值來使觀眾靠近" + "\n"
                                                + "觀眾只要靠近就會獻上一朵花" + "\n"
                                                + "蒐集 5 朵花得到滿分");
                        //=
                        tvLevelDifficulty.setText("生命值 : 一點生命值 +4分");
                        tvCombo.setText("Combo : 一次Combo +6分");
                        //=
                        loginBtnNext.setText("開始第二關");
                        //=
                        imgLifeValue.setImageResource(R.drawable.flowerscore);
                        Glide.with(mContext).load(R.drawable.bg_test_nature).apply(myGdiOptions).into(ivBG);
                        //=
                        Checkpoint1 = LifeValue;  //第一關的生命值
                        RecommendCount_A1 = AttentionTimes;
                        RecommendCount_M1 = MeditationTimes;
                        LifeValue = 0;  //生命值重製(因為下一關是放鬆，起始值為0)
                        AttentionTimes = 0;
                        MeditationTimes = 0;

                        break;

                    case 3 :
                        tvNextLevel.setText("第三關");
                        tvBeforeGain.setText("前一關獲得 " + LifeValue + " 點生命點");
                        tvBeforeCombo.setText("前一關獲得 " + ComboCount2 + " 點Combo");
                        //=
                        tvChallengeWhat.setText("專注50+");
                        tvChallengeWhat.setBackgroundResource(R.drawable.challenge_attention);
                        tvChallengeSong.setText("熱血");
                        tvChallengeSong.setBackgroundResource(R.drawable.challenge_song);
                        //=
                        tvIntroduction.setText("音樂家要利用專注值來驅趕鬼怪" + "\n"
                                + "鬼怪只要靠近就會失去一點音樂天分" + "\n"
                                + "維持 5 點音符得到滿分");
                        //=
                        tvLevelDifficulty.setText("生命值 : 一點生命值 +4分");
                        tvCombo.setText("Combo : 一次Combo +7分");
                        //=
                        loginBtnNext.setText("開始第三關");
                        //=
                        imgLifeValue.setImageResource(R.drawable.note);
                        Glide.with(mContext).load(R.drawable.bg_test_hot).apply(myGdiOptions).into(ivBG);
                        //=
                        Checkpoint2 = LifeValue;
                        RecommendCount_A2 = AttentionTimes;
                        RecommendCount_M2 = MeditationTimes;
                        LifeValue = 5;  //生命值重製(因為下一關是專注，起始值為5)
                        AttentionTimes = 0;
                        MeditationTimes = 0;
                        break;

                    case 4 :
                        tvNextLevel.setText("第四關");
                        tvBeforeGain.setText("前一關獲得 " + LifeValue + " 點生命點");
                        tvBeforeCombo.setText("前一關獲得 " + ComboCount3 + " 點Combo");
                        //=
                        tvChallengeWhat.setText("放鬆60+");
                        tvChallengeWhat.setBackgroundResource(R.drawable.challenge_meditation);
                        tvChallengeSong.setText("流行");
                        tvChallengeSong.setBackgroundResource(R.drawable.challenge_song);
                        //=
                        tvIntroduction.setText("音樂家要利用放鬆值來使觀眾靠近" + "\n"
                                + "觀眾只要靠近就會獻上一朵花" + "\n"
                                + "蒐集 5 朵花得到滿分");
                        //=
                        tvLevelDifficulty.setText("生命值 : 一點生命值 +4分");
                        tvCombo.setText("Combo : 一次Combo +8分");
                        //=
                        loginBtnNext.setText("開始第四關");
                        //=
                        imgLifeValue.setImageResource(R.drawable.flowerscore);
                        Glide.with(mContext).load(R.drawable.bg_test_popular).apply(myGdiOptions).into(ivBG);
                        Checkpoint3 = LifeValue;
                        RecommendCount_A3 = AttentionTimes;
                        RecommendCount_M3 = MeditationTimes;
                        LifeValue = 0;
                        AttentionTimes = 0;
                        MeditationTimes = 0;
                        break;

                    case 5: //等級5=====
                        tvNextLevel.setText("第五關");
                        tvBeforeGain.setText("前一關獲得 " + LifeValue + " 點生命點");
                        tvBeforeCombo.setText("前一關獲得 " + ComboCount4 + " 點Combo");
                        //=
                        tvChallengeWhat.setText("專注70+");
                        tvChallengeWhat.setBackgroundResource(R.drawable.challenge_attention);
                        tvChallengeSong.setText("雙耳波差");
                        tvChallengeSong.setBackgroundResource(R.drawable.challenge_song);
                        //=
                        tvIntroduction.setText("音樂家要利用專注值來驅趕鬼怪" + "\n"
                                + "鬼怪只要靠近就會失去一點音樂天分" + "\n"
                                + "維持 5 點音符得到滿分");
                        //=
                        tvLevelDifficulty.setText("生命值 : 一點生命值 +4分");
                        tvCombo.setText("Combo : 一次Combo +9分");
                        //=
                        loginBtnNext.setText("開始第五關");
                        //=
                        imgLifeValue.setImageResource(R.drawable.note);
                        Glide.with(mContext).load(R.drawable.bg_test_mysterious).apply(myGdiOptions).into(ivBG);
                        Checkpoint4 = LifeValue;
                        RecommendCount_A4 = AttentionTimes;
                        RecommendCount_M4 = MeditationTimes;
                        LifeValue = 5;
                        AttentionTimes = 0;
                        MeditationTimes = 0;
                        break;

                    case 6: //結束=====
                        tvNextLevel.setText("遊戲結束");
                        tvBeforeGain.setText("前一關獲得 " + LifeValue + " 點生命點");
                        tvBeforeCombo.setText("前一關獲得 " + ComboCount5 + " 點Combo");
                        //=
                        tvChallengeWhat.setText("");
                        tvChallengeSong.setText("");
                        //=
                        tvIntroduction.setText("遊戲已結束" + "\n" + "看看分數多少吧!");
                        //=
                        tvLevelDifficulty.setText("");
                        tvCombo.setText("");
                        //=
                        loginBtnNext.setText("觀看分數");
                        //=
                        Checkpoint5 = LifeValue;
                        RecommendCount_A5 = AttentionTimes;
                        RecommendCount_M5 = MeditationTimes;
                        AttentionTimes = 0;
                        MeditationTimes = 0;
                        break;
                }

                loginBtnNext.setOnClickListener(loginDlgBtnNextOnClick);
                mDlgNext.show();
                // 將對話方塊的大小按螢幕大小的百分比設定
                WindowManager windowManager = getWindowManager();
                Display display = windowManager.getDefaultDisplay();
                WindowManager.LayoutParams lp = mDlgNext.getWindow().getAttributes();
                lp.width = (int)(display.getWidth() * 0.95);//設定寬度
                mDlgNext.getWindow().setAttributes(lp);
            }
        };
    };




//動畫專案集============================================================================================================================================
    public void Animation_Ghost(){  //顯示鬼
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


    public void Animation_Audience(){   //顯示觀眾
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

    public void Animation_Ghost_Loading(){  //下一關開始前鬼閃爍
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
    public void Animation_Audience_Loading(){   //下一關開始前觀眾閃爍
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
    public void Animation_Flower_visible(){ //顯示花
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

    public void Animation_Note_visible(){   //顯示音符
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

    public void Animation_Reset(){  //下一關開始前圖片歸位
        set1 = (float) BaseLine;
        set2 = (float) BaseLine;
    }

    public void Animation_Hit(){    //狗被攻擊閃爍
        animDog = ObjectAnimator.ofFloat(imgDog, "alpha", 1, (float)0.3);
        animDog.setDuration(200);
        animDog.setRepeatCount(3);
        animDog.setRepeatMode(ObjectAnimator.REVERSE);
        animDog.setInterpolator(new LinearInterpolator());
        animDog.start();
    }

    public void Animation_GhostMove(){  //鬼移動動畫
        float point1=set1, point2=set2;
        set1 = set2;  //使動畫較順
        AnimatorSet animTxtMove = new AnimatorSet();
        ObjectAnimator animTxtMove1 =
                ObjectAnimator.ofFloat(imgGhost, "y", point1, point2);
        animTxtMove1.setDuration(500);
        animTxtMove1.setInterpolator(new AccelerateDecelerateInterpolator());
        animTxtMove.playSequentially(animTxtMove1);
        animTxtMove.start();
    }

    public void Animation_AudienceMove(){   //觀眾移動動畫
        float point1=set1, point2=set2;
        set1 = set2;  //使動畫較順
        AnimatorSet animTxtMove = new AnimatorSet();
        ObjectAnimator animTxtMove1 =
                ObjectAnimator.ofFloat(imgAudience, "y", point1, point2);
        animTxtMove1.setDuration(500);
        animTxtMove1.setInterpolator(new AccelerateDecelerateInterpolator());
        animTxtMove.playSequentially(animTxtMove1);
        animTxtMove.start();
    }

    public void Animation_Combo(){  //Combo時的圖案
        animCombo = ObjectAnimator.ofFloat(imgCombo, "alpha",0, 1);
        animCombo.setDuration(300);
        animCombo.start();
        animCombo = ObjectAnimator.ofFloat(imgCombo, "alpha",1, 0);
        animCombo.setDuration(1700);
        animCombo.start();
    }

    public void SetValueColor_A(){  //專注值Combo變色
        tvAM.setTextColor(Color.parseColor("#EF5350"));
        tvAMvalue.setTextColor(Color.parseColor("#EF5350"));
    }

    public void SetValueColor_M(){  //放鬆值Combo變色
        tvAM.setTextColor(Color.parseColor("#66BB6A"));
        tvAMvalue.setTextColor(Color.parseColor("#66BB6A"));
    }

    public void SetValueColor_O(){  //腦波值原本顏色
        tvAM.setTextColor(Color.parseColor("#ECECEC"));
        tvAMvalue.setTextColor(Color.parseColor("#ECECEC"));
    }

    public void Animation_Note_LifeCount(){ //生命值變動動畫
        tvLifeCountX.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        tvLifeCountX.setTextColor(Color.parseColor("#EF5350"));
        tvLifeValue.setTextSize(TypedValue.COMPLEX_UNIT_SP,30);
        tvLifeValue.setTextColor(Color.parseColor("#EF5350"));
    }

    public void Animation_Flower_LifeCount(){ //生命值原本顏色
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
//數值計算================================================================================================================================================
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

    public void RecommendCount(){  //推薦算分
        if(clsData.iGetAttention() >= 80){
            AttentionTimes++;
        }
        if(clsData.iGetMeditation() >= 80){
            MeditationTimes++;
        }
    }
//=======================================================================================================================================================
    public void StartTimer() {
        //如果timer和timerTask已經被置null了
        progress = 31;
        if (timer == null&&timerTask==null) {
            //新建timer和timerTask
            timer = new Timer();
            timerTask = new TimerTask() {
                @Override
                public void run() {
                    Bundle bundle = getIntent().getExtras();
                    String CheckRB = bundle.getString("CheckRB");
                    float fCheckRB = Float.parseFloat(CheckRB);
                    //每次progress減一
                    progress--;
                    handler.sendEmptyMessage(0);
                    if (progress <= 0) {
                        handler.sendEmptyMessage(1);
                        Animation_Reset();
                        //設定腦波值
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
                    //設置進度條進度
                    pb.setProgress(progress);
                }
            };
            /*开始执行timer,第一个参数是要执行的任务，
            第二个参数是开始的延迟时间（单位毫秒）或者是Date类型的日期，代表开始执行时的系统时间
            第三个参数是计时器两次计时之间的间隔（单位毫秒）*/
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
            //Toast.makeText(Test.this, "下一關", Toast.LENGTH_LONG).show();
            if(level == 6){
                Bundle bundle = getIntent().getExtras();
                String CheckRB = bundle.getString("CheckRB");
                float fCheckRB = Float.parseFloat(CheckRB);

                if (fCheckRB == 1){
                    Main.addSql();
                    Toast.makeText(Test.this, "資料已存至SQLite中", Toast.LENGTH_LONG).show();
                }
                if (fCheckRB == 2){
                    clsData.WriteXLS(mContext);
                    Toast.makeText(Test.this, "資料已存至Excel中", Toast.LENGTH_LONG).show();
                }
                if (fCheckRB == 3){
                    Toast.makeText(Test.this, "資料已存至Google Sheets中", Toast.LENGTH_LONG).show();
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
            Toast.makeText(Test.this, "資料已存至SQLite中", Toast.LENGTH_LONG).show();
        }
        if (fCheckRB == 2){
            clsData.WriteXLS(mContext);
            Toast.makeText(Test.this, "資料已存至Excel中", Toast.LENGTH_LONG).show();
        }
        if (fCheckRB == 3){
            Toast.makeText(Test.this, "資料已存至Google Sheets中", Toast.LENGTH_LONG).show();
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
            //imgGhost.setImageResource(R.drawable.);   //------圖片交替位置[1]------
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
//動畫=======================================================================================================
                Animation_Original_LifeCount();

                switch (level) {
                    case 1 : //等級1=====
                        Animation_Ghost();
                        Animation_Note_visible();  //設定生命值(圖)

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
                                set2 = (float) GhostClose;  //==靠近
                                Animation_GhostMove();
                                Animation_Hit();  //狗狗被攻擊的特效
                                SetNoteValue();   //扣生命值
                            } else {
                                set2 = (float) GhostClose + clsData.iGetAttention() * 9;  //==遠離
                                Animation_GhostMove();
                            }
                        }
                        ComboCount();
                        RecommendCount();
                        clsLineChart.AddPoint(1, dX, clsData.iGetAttention());
                        clsLineChart.AddPoint(2, dX, clsData.iGetMeditation());
                        tvAM.setText("專注值");
                        tvAMvalue.setText(String.valueOf(clsData.iGetAttention()));
                        break;

                    case 2 :  //等級2=====
                        Animation_Audience();
                        Animation_Flower_visible();

                        tvLifeValue.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvLifeValue.setText(String.valueOf(LifeValue));
                            }
                        }, 500);  //顯示生命值

                        if(clsData.iGetMeditation() == -1) {
                            Animation_Reset();
                            Animation_Audience_Loading();
                        }else{
                            if (clsData.iGetMeditation() <= 40) {
                                set2 = (float) (BaseLine - clsData.iGetMeditation() * 9);  //==遠離
                                Animation_AudienceMove();
                            } else {
                                set2 = (float) AudienceClose;  //==靠近
                                Animation_AudienceMove();
                                SetFlowerValue();
                            }
                        }
                        ComboCount();
                        RecommendCount();
                        clsLineChart.AddPoint(1, dX, clsData.iGetAttention());
                        clsLineChart.AddPoint(2, dX, clsData.iGetMeditation());
                        tvAM.setText("放鬆值");
                        tvAMvalue.setText(String.valueOf(clsData.iGetMeditation()));
                        break;

                    case 3 :  //等級3=====
                        Animation_Ghost();
                        Animation_Note_visible();  //設定生命值(圖)

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
                                set2 = (float) GhostClose;  //==靠近
                                Animation_GhostMove();
                                Animation_Hit();  //狗狗被攻擊的特效
                                SetNoteValue();   //扣生命值
                            } else {
                                set2 = (float) GhostClose + clsData.iGetAttention() * 9;  //==遠離
                                Animation_GhostMove();
                            }
                        }
                        ComboCount();
                        RecommendCount();
                        clsLineChart.AddPoint(1, dX, clsData.iGetAttention());
                        clsLineChart.AddPoint(2, dX, clsData.iGetMeditation());
                        tvAM.setText("專注值");
                        tvAMvalue.setText(String.valueOf(clsData.iGetAttention()));
                        break;

                    case 4 :  //等級4=====
                        Animation_Audience();
                        Animation_Flower_visible();

                        tvLifeValue.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                tvLifeValue.setText(String.valueOf(LifeValue));
                            }
                        }, 500);  //顯示生命值

                        if(clsData.iGetMeditation() == -1) {
                            Animation_Reset();
                            Animation_Audience_Loading();
                        }else{
                            if (clsData.iGetMeditation() <= 60) {
                                set2 = (float) (BaseLine - clsData.iGetMeditation() * 9);  //==遠離
                                Animation_AudienceMove();
                            } else {
                                set2 = (float) AudienceClose;  //==靠近
                                Animation_AudienceMove();
                                SetFlowerValue();
                            }
                        }
                        ComboCount();
                        RecommendCount();
                        clsLineChart.AddPoint(1, dX, clsData.iGetAttention());
                        clsLineChart.AddPoint(2, dX, clsData.iGetMeditation());
                        tvAM.setText("放鬆值");
                        tvAMvalue.setText(String.valueOf(clsData.iGetMeditation()));
                        break;

                    case 5 :  //等級5=====
                        Animation_Ghost();
                        Animation_Note_visible();  //設定生命值(圖)

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
                                set2 = (float) GhostClose;  //==靠近
                                Animation_GhostMove();
                                Animation_Hit();  //狗狗被攻擊的特效
                                SetNoteValue();   //扣生命值
                            } else {
                                set2 = (float) GhostClose + clsData.iGetAttention() * 9;  //==遠離
                                Animation_GhostMove();
                            }
                        }
                        ComboCount();
                        RecommendCount();
                        clsLineChart.AddPoint(1, dX, clsData.iGetAttention());
                        clsLineChart.AddPoint(2, dX, clsData.iGetMeditation());
                        tvAM.setText("專注值");
                        tvAMvalue.setText(String.valueOf(clsData.iGetAttention()));
                        break;
                }
                //=============================================================================

                if (clsLineChart == null)
                    return;


                //專注值===============

                //放鬆值==============

                clsLineChart.AddPoint(3, dX, clsData.iGetFatigue());

                viewLineChart.invalidate();
                viewLineChart2.invalidate();
                dX += 1;

            }
        }
    };


    //==============================================================================================
//使用非同步方式上傳雲端保持主畫面持續進行
    private class addDataToSheet extends AsyncTask<String, Integer, Void> {
        @Override
        protected Void doInBackground(String... params) {
            //執行中 在背景做事情
            for (String urlStr : params) {
                try {
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, urlStr,
                            new Response.Listener<String>() {   //跟網址上的Http提出請求
                                @Override
                                public void onResponse(String response) {
                                    //因多次上傳，如每次都回應可能會吃太多資源，所以是空的
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {    //錯誤時做
                                    Log.d("Error", String.valueOf(error));
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {         //取得參數
                            Map<String, String> parmas = new HashMap<>();
                            //傳遞參數至試算表上的SCRIPT檔
                            parmas.put("action", "addRec");
                            //建新SHEET
                            parmas.put("newSheet",sheetboo);
                            //以下為各種參數上傳
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
                    //下面能保證資料不會遺失但會使資料表Rec編號亂掉
                    int socketTimeOut = 50000;// u can change this .. here it is 50 seconds //當回傳時間超過50秒會重新跟HTTP提出請求
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


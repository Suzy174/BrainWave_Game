package com.sh.simpleeeg;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Result extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.result);

        TextView tvGrade01,tvGrade02,tvGrade03,tvGrade04,tvGrade05;
        TextView tvCombo1,tvCombo2,tvCombo3,tvCombo4,tvCombo5;
        TextView tvTotalGrade, tvGradeLevelTitle, tvGradeLevel, tvResultTitle, tvResult;
        TextView tvRecommend01,tvRecommend02;
        TextView tvNotes01,tvNotes02,tvNotes03,tvNotes04,tvNotes05;
        int grade01,grade02,grade03,grade04,grade05;
        int Total;


        tvGrade01 = findViewById(R.id.tvGrade01);
        tvGrade02 = findViewById(R.id.tvGrade02);
        tvGrade03 = findViewById(R.id.tvGrade03);
        tvGrade04 = findViewById(R.id.tvGrade04);
        tvGrade05 = findViewById(R.id.tvGrade05);

        tvGradeLevelTitle = findViewById(R.id.tvGradeLevelTitle);
        tvGradeLevel = findViewById(R.id.tvGradeLevel);

        tvCombo1 = findViewById(R.id.tvCombo1);
        tvCombo2 = findViewById(R.id.tvCombo2);
        tvCombo3 = findViewById(R.id.tvCombo3);
        tvCombo4 = findViewById(R.id.tvCombo4);
        tvCombo5 = findViewById(R.id.tvCombo5);

        tvNotes01 = findViewById(R.id.tvNotes01);
        tvNotes02 = findViewById(R.id.tvNotes02);
        tvNotes03 = findViewById(R.id.tvNotes03);
        tvNotes04= findViewById(R.id.tvNotes04);
        tvNotes05 = findViewById(R.id.tvNotes05);

        tvTotalGrade = findViewById(R.id.tvTotalGrade);

        tvResultTitle = findViewById(R.id.tvResultTitle);
        tvResult = findViewById(R.id.tvResult);

        tvRecommend01 = findViewById(R.id.tvRecommend01);
        tvRecommend02 = findViewById(R.id.tvRecommend02);

        tvGrade01.setText(String.valueOf(Test.Checkpoint1));
        tvGrade02.setText(String.valueOf(Test.Checkpoint2));
        tvGrade03.setText(String.valueOf(Test.Checkpoint3));
        tvGrade04.setText(String.valueOf(Test.Checkpoint4));
        tvGrade05.setText(String.valueOf(Test.Checkpoint5));

        tvCombo1.setText(String.valueOf(Test.ComboCount1));
        tvCombo2.setText(String.valueOf(Test.ComboCount2));
        tvCombo3.setText(String.valueOf(Test.ComboCount3));
        tvCombo4.setText(String.valueOf(Test.ComboCount4));
        tvCombo5.setText(String.valueOf(Test.ComboCount5));

        grade01 = Test.Checkpoint1 * 2 + Test.ComboCount1 * 5;
        grade02 = Test.Checkpoint2 * 2 + Test.ComboCount2 * 5;
        grade03 = Test.Checkpoint3 * 3 + Test.ComboCount3 * 5;
        grade04 = Test.Checkpoint4 * 4 + Test.ComboCount4 * 5;
        grade05 = Test.Checkpoint5 * 4 + Test.ComboCount5 * 5;

        tvNotes01.setText("+" + grade01);
        tvNotes02.setText("+" + grade02);
        tvNotes03.setText("+" + grade03);
        tvNotes04.setText("+" + grade04);
        tvNotes05.setText("+" + grade05);

        Total = grade01 + grade02 +grade03 +grade04 +grade05;

        List grades = new ArrayList();
        grades.add(grade01);
        grades.add(grade02);
        grades.add(grade03);
        grades.add(grade04);
        grades.add(grade05);

        int Types = grades.indexOf(Collections.max(grades));    //取最大值的索引值
        if(Total < 60){
            tvResult.setText("不合格的音樂家...");
            tvResultTitle.setTextColor(Color.parseColor("#CACACA"));
            tvResult.setTextColor(Color.parseColor("#CACACA"));

        }else {
            switch (Types) {
                case 0:
                    tvResult.setText("古典型音樂家");
                    break;
                case 1:
                    tvResult.setText("放鬆型音樂家");
                    break;
                case 2:
                    tvResult.setText("熱血型音樂家");
                    break;
                case 3:
                    tvResult.setText("流型音樂家");
                    break;
                case 4:
                    tvResult.setText("輕音樂型音樂家");
                    break;
            }
        }
            tvTotalGrade.setText(String.valueOf(Total));

        if(0 <= Total && Total < 30){
            tvGradeLevel.setText("F");
            tvGradeLevelTitle.setTextColor(Color.parseColor("#CACACA"));
            tvGradeLevel.setTextColor(Color.parseColor("#CACACA"));
        }else if(30 <= Total && Total < 45){
            tvGradeLevel.setText("D");
            tvGradeLevelTitle.setTextColor(Color.parseColor("#87683C"));
            tvGradeLevel.setTextColor(Color.parseColor("#87683C"));
        }else if(45 <= Total && Total < 60){
            tvGradeLevel.setText("C");
            tvGradeLevelTitle.setTextColor(Color.parseColor("#56A35A"));
            tvGradeLevel.setTextColor(Color.parseColor("#56A35A"));
        }else if(60 <= Total && Total < 80){
            tvGradeLevel.setText("B");
            tvGradeLevelTitle.setTextColor(Color.parseColor("#5C6BC0"));
            tvGradeLevel.setTextColor(Color.parseColor("#5C6BC0"));
        }else if(80 <= Total && Total < 90){
            tvGradeLevel.setText("A");
            tvGradeLevelTitle.setTextColor(Color.parseColor("#EC407A"));
            tvGradeLevel.setTextColor(Color.parseColor("#EC407A"));
        }else if(90 <= Total && Total < 100){
            tvGradeLevel.setText("S");
            tvGradeLevelTitle.setTextColor(Color.parseColor("#FFCA28"));
            tvGradeLevel.setTextColor(Color.parseColor("#FFCA28"));
        }else{
            tvGradeLevel.setText("S++");
            tvGradeLevelTitle.setTextColor(Color.parseColor("#FFCA28"));
            tvGradeLevel.setTextColor(Color.parseColor("#FFCA28"));
        }

        List recommendArray = new ArrayList();
        recommendArray.add(Test.RecommendCount_A1);  //古典 + 專注
        recommendArray.add(Test.RecommendCount_A2);  //平靜 + 專注
        recommendArray.add(Test.RecommendCount_A3);  //熱血 + 專注
        recommendArray.add(Test.RecommendCount_A4);  //流行 + 專注
        recommendArray.add(Test.RecommendCount_A5);  //雙耳 + 專注
        recommendArray.add(Test.RecommendCount_M1);  //古典 + 放鬆
        recommendArray.add(Test.RecommendCount_M2);  //平靜 + 放鬆
        recommendArray.add(Test.RecommendCount_M3);  //熱血 + 放鬆
        recommendArray.add(Test.RecommendCount_M4);  //流行 + 放鬆
        recommendArray.add(Test.RecommendCount_M5);  //古典 + 放鬆
        int RecommendMAX = recommendArray.indexOf(Collections.max(recommendArray));
        System.out.println("RecommendMAX : " + RecommendMAX);
        switch (RecommendMAX){
            case 0 :
                tvRecommend01.setText("古典");tvRecommend01.setBackgroundResource(R.drawable.challenge_song);
                tvRecommend02.setText("專注");tvRecommend02.setBackgroundResource(R.drawable.challenge_attention);
                break;
            case 1 :
                tvRecommend01.setText("平靜");tvRecommend01.setBackgroundResource(R.drawable.challenge_song);
                tvRecommend02.setText("專注");tvRecommend02.setBackgroundResource(R.drawable.challenge_attention);
                break;
            case 2 :
                tvRecommend01.setText("熱血");tvRecommend01.setBackgroundResource(R.drawable.challenge_song);
                tvRecommend02.setText("專注");tvRecommend02.setBackgroundResource(R.drawable.challenge_attention);
                break;
            case 3 :
                tvRecommend01.setText("流行");tvRecommend01.setBackgroundResource(R.drawable.challenge_song);
                tvRecommend02.setText("專注");tvRecommend02.setBackgroundResource(R.drawable.challenge_attention);
                break;
            case 4 :
                tvRecommend01.setText("雙耳");tvRecommend01.setBackgroundResource(R.drawable.challenge_song);
                tvRecommend02.setText("專注");tvRecommend02.setBackgroundResource(R.drawable.challenge_attention);
                break;
            case 5 :
                tvRecommend01.setText("古典");tvRecommend01.setBackgroundResource(R.drawable.challenge_song);
                tvRecommend02.setText("放鬆");tvRecommend02.setBackgroundResource(R.drawable.challenge_meditation);
                break;
            case 6 :
                tvRecommend01.setText("平靜");tvRecommend01.setBackgroundResource(R.drawable.challenge_song);
                tvRecommend02.setText("放鬆");tvRecommend02.setBackgroundResource(R.drawable.challenge_meditation);
                break;
            case 7 :
                tvRecommend01.setText("熱血");tvRecommend01.setBackgroundResource(R.drawable.challenge_song);
                tvRecommend02.setText("放鬆");tvRecommend02.setBackgroundResource(R.drawable.challenge_meditation);
                break;
            case 8 :
                tvRecommend01.setText("流行");tvRecommend01.setBackgroundResource(R.drawable.challenge_song);
                tvRecommend02.setText("放鬆");tvRecommend02.setBackgroundResource(R.drawable.challenge_meditation);
                break;
            case 9 :
                tvRecommend01.setText("雙耳");tvRecommend01.setBackgroundResource(R.drawable.challenge_song);
                tvRecommend02.setText("放鬆");tvRecommend02.setBackgroundResource(R.drawable.challenge_meditation);
                break;
        }

    }


    public void BackToMain(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Main.class);//input info
        startActivity(intent);
    }

    public void GoToReport(View view) {
        Intent intent = new Intent();
        intent.setClass(this, Report.class);//input info
        startActivity(intent);
    }

    public void LevelSet(){

    }

}

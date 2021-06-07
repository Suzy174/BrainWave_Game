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
        TextView tvTotalGrade, tvResult;
        int grade01,grade02,grade03,grade04,grade05;
        int Total;


        tvGrade01 = findViewById(R.id.tvGrade01);
        tvGrade02 = findViewById(R.id.tvGrade02);
        tvGrade03 = findViewById(R.id.tvGrade03);
        tvGrade04 = findViewById(R.id.tvGrade04);
        tvGrade05 = findViewById(R.id.tvGrade05);

        tvCombo1 = findViewById(R.id.tvCombo1);
        tvCombo2 = findViewById(R.id.tvCombo2);
        tvCombo3 = findViewById(R.id.tvCombo3);
        tvCombo4 = findViewById(R.id.tvCombo4);
        tvCombo5 = findViewById(R.id.tvCombo5);

        tvTotalGrade = findViewById(R.id.tvTotalGrade);
        tvResult = findViewById(R.id.tvResult);

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

        System.out.println(grade01);
        System.out.println(grade02);
        System.out.println(grade03);
        System.out.println(grade04);
        System.out.println(grade05);

        Total = grade01 + grade02 +grade03 +grade04 +grade05;

        List grades = new ArrayList();
        grades.add(grade01);
        grades.add(grade02);
        grades.add(grade03);
        grades.add(grade04);
        grades.add(grade05);

        int indexOfMaxElement = grades.indexOf(Collections.max(grades));
        if(Total < 60){
            tvResult.setText("不合格的音樂家...");
            tvResult.setTextColor(Color.parseColor("#CACACA"));
        }else {
            switch (indexOfMaxElement) {
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

}

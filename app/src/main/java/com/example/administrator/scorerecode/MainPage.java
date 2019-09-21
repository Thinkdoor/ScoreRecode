package com.example.administrator.scorerecode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.administrator.scorerecode.database.DBController;
import com.example.administrator.scorerecode.util.ToastUtil;

/**
 * 主页面
 *
 * 1.定义属性
 * 2.初始化视图组建
 * 3.下拉框组建设置
 * 4.设置录入按钮的监听
 *      1.获取输入框数据
 *      2.由DBController插入数据
 *
 */
public class MainPage extends AppCompatActivity {
    //1.定义属性
    Intent intent;

    EditText editText,editText1;
    Button button;

    String cn;
    String en;
    String score;

    DBController dbcontroller;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_math_page);

        //2.初始化视图组建
        editText = (EditText) findViewById(R.id.et_cn);
        editText1 = (EditText) findViewById(R.id.et_sc);

        //3.下拉框组建设置
        Spinner spinner = (Spinner) findViewById(R.id.spinner1);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                String[] languages = getResources().getStringArray(R.array.languages);

                ToastUtil.showMsg(getApplicationContext(),languages[pos]);
                cn = languages[pos];
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

        //4.设置录入按钮的监听
        button = (Button) findViewById(R.id.bt_insert);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dbcontroller = new DBController(getApplicationContext());

                //1.获取输入框数据
                en = editText.getText().toString();
                score = editText1.getText().toString();

                //2.由DBController插入数据插入数据到数据库
                if(en.isEmpty() || score.isEmpty()) {
                    ToastUtil.showMsg(getApplication(),"请输入数据！");

                }else{
                    dbcontroller.testInsertDate(cn, en, score);
                    ToastUtil.showMsg(getApplicationContext(), "录入成功！");
                    editText.setText("");
                    editText1.setText("");
                }
            }
        });


    }

    /**
     * 进入成绩单页面
     * @param view
     */
    public void intoScores(View view){
        //通过bundle传输数课程名称给Scores
        intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString("cn",cn);
        intent.putExtras(bundle);
        intent.setClass(getApplicationContext(),Scores.class);
        startActivity(intent);
    }
}

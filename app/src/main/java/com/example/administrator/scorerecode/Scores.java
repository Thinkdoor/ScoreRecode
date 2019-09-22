package com.example.administrator.scorerecode;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.scorerecode.adapter.ScoreAdapter;
import com.example.administrator.scorerecode.database.DBController;
import com.example.administrator.scorerecode.database.ScoreDB;

import java.util.ArrayList;

public class Scores extends AppCompatActivity {

    TextView textView;
    DBController dbController;
    ListView listView;
    String cn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);
        dbController = new DBController(getApplicationContext());

        //获取对应的课程名称
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        cn = bundle.getString("cn");

        textView = (TextView) findViewById(R.id.tv);
        textView.setText(cn+"成绩单");
        ArrayList<Score> list = dbController.testQueryDate(cn);
        listView = (ListView) findViewById(R.id.list);
        listView.setAdapter(new ScoreAdapter(list,getApplication()));

        //长按删除成绩
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                //通过 课程名 获取到要删除的是哪门课
                Score score = dbController.testQueryDate(cn).get(i);
                //根据 考试名称 删除对应的数据
                dbController.testDeleteDate(score.EN);
                return false;
            }
        });
    }
}

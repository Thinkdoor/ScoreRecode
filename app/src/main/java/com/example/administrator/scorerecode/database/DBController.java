package com.example.administrator.scorerecode.database;

import android.app.Application;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.administrator.scorerecode.Score;
import com.example.administrator.scorerecode.Scores;
import com.example.administrator.scorerecode.util.ToastUtil;

import java.util.ArrayList;

/**
 * Created by One Lion on 2019/9/21.
 *
 * 数据库控制器类
 *
 * 1.编写插入数据方法
 *      1.创建数据库
 *      2.得到连接
 *      3.执行insert
 *      4.关闭连接
 * 2.编写获取数据方法，返回一个Score数据集合
 *      1.创建数据库
 *      2.得到连接
 *      3.执行sql查询获取cursor
 *      4.循环获取数据，并加入到集合中
 *      5.返回一个集合
 */

public class DBController{

    Context context;
    Score score;

    //构造方法
    public DBController(Context context){
        this.context = context;
    }

    /**
     * 插入数据
     *
     * @param classname 课程名
     * @param examinationname   考试名称
     * @param score 考试成绩
     */
    public void testInsertDate(String classname,String examinationname,String score){
        //创建数据库
        ScoreDB scoreDB = new ScoreDB(context,2);
        //1.得到连接
        SQLiteDatabase sqLiteDatabase = scoreDB.getReadableDatabase();

        //2.执行insert
        ContentValues values = new ContentValues();
        values.put("CN",classname);
        values.put("EN",examinationname);
        values.put("SCORE",score);

        //返回插入的id
        /**
         * 第一个参数：表名
         * 第二个参数：null
         * 第三个参数：传入的HashMap值
         */
        long tb_sores = sqLiteDatabase.insert("tb_score", null, values);

        //3.关闭连接
        sqLiteDatabase.close();

        //4.提示
        ToastUtil.showMsg(context,"id= "+tb_sores);
    }

    /**
     * 查询数据
     *
     * @param
     */
    public ArrayList<Score> testQueryDate(String classn) {
        ArrayList<Score> scores = new ArrayList<>();

        ScoreDB scoreDB = new ScoreDB(context,2);
        //1.得到连接
        SQLiteDatabase sqLiteDatabase = scoreDB.getReadableDatabase();

        //2.执行query select * from person
        Cursor cursor =  sqLiteDatabase.query("tb_score",null,"CN=?",new String[]{classn},null,null,null);

        //得到cursor查询到的总记录数
        int count = cursor.getCount();

        while(cursor.moveToNext()){
            score = new Score();
            // CN
            String cn = cursor.getString(cursor.getColumnIndex("CN"));
            score.CN=cn;
            // EN
            String en = cursor.getString(cursor.getColumnIndex("EN"));
            score.EN=en;
            // score
            String s = cursor.getString(cursor.getColumnIndex("SCORE"));
            score.SCORE=s;
            scores.add(score);
        }

        //3.关闭连接
        cursor.close();
        sqLiteDatabase.close();

        //4.提示
        ToastUtil.showMsg(context,"一共有"+count+"条数据");
        return scores;
    }

    /**
     * 删除数据
     *
     * @param EN 考试名称
     */
    public void testDeleteDate(String EN) {
        ScoreDB scoreDB = new ScoreDB(context,2);
        //1.得到连接
        SQLiteDatabase sqLiteDatabase = scoreDB.getReadableDatabase();

        //2.执行delete
        String where = "EN = ?";
        String[] value = new String[]{EN};
        //返回删除的数量
        /**
         * 第一个参数：表名
         * 第二个参数：删除的where后的语句
         * 第三个参数：? 所代表的值
         */
        int deleteCount = sqLiteDatabase.delete("tb_score",where,value);

        //3.关闭连接
        sqLiteDatabase.close();

        //4.提示
        ToastUtil.showMsg(context,"删除了"+deleteCount+"条记录");
    }
}

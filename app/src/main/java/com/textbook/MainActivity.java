package com.textbook;

import androidx.appcompat.app.AppCompatActivity;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //响应按钮的单击事件
    public void onClick(View view)
    {
        RadioButton radio1=(RadioButton)findViewById(R.id.radio1);
        RadioButton radio2=(RadioButton)findViewById(R.id.radio2);
        String song="";
//获取用户选择的歌曲
        if(radio1.isChecked())
        {
            song="everybody";
        }
        if(radio2.isChecked())
        {
            song="we_are_young";
        }
//定义显式意图
        Intent intent=new Intent(MainActivity.this,Music.class);
        intent.putExtra("music",song);
//当用户单击"play"按钮时启动音乐播放服务；当用户单击"stop"按钮时停止音乐播放服务
        if (view.getId() == R.id.play) {
            startService(intent); //启动服务
        } else if (view.getId() == R.id.stop) {
            stopService(intent); //停止服务
        }
    }
}
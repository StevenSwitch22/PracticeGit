package com.textbook;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
public class NewActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new);
        //接收传递的意图及携带的消息
        Bundle bundle=this.getIntent().getExtras();
        //通过 key 得到 Bundle 存储的 value 值
        String s="infor is:"+bundle.getString("name")+","
                +bundle.getString("message");
        //获取 TextView 视图对象
        TextView infor=(TextView)findViewById(R.id.infor);
        infor.setText(s); //将接收到的信息显示在 TextView 中
        /**用户单击按钮时，结束当前活动，返回到 MainActivity 活动中
         * 为什么要返回到 MainActivity 中呢？建议结合活动的生命周期进行理解
         * 当活动堆栈中的当前活动结束后，堆栈里的下一个活动（被压入活动堆栈中的
         MainActivity）返回栈顶
         * */
        Button btn=(Button)findViewById(R.id.button);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //结束当前活动
                NewActivity.this.finish();
            }
        });
    }
}

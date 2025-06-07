package com.textbook;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.util.Log;

public class Music extends Service {
    //创建一个 MediaPlayer 对象，播放音乐
    MediaPlayer mp=null;
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(mp!=null)
            mp.stop();
        mp=null;
        Log.i(String.valueOf(flags),String.valueOf(startId));
        //获取 startService(intent)传递过来的信息
        String song=intent.getExtras().getString("music");
        if(song.equals("everybody"))
        {
            //实例化 MediaPlayer 对象 mp
            mp=MediaPlayer.create(this, R.raw.everybody);
        }else if(song.equals("we_are_young"))
        {
            mp=MediaPlayer.create(this, R.raw.we_are_young);
        }
        //播放音乐
        mp.start();
        return super.onStartCommand(intent, flags, startId);
    }
    // onDestroy()方法在结束服务时调用
    @Override
    public void onDestroy() {
        //停止音乐播放
        mp.stop();
        mp=null;
        super.onDestroy();
    }
}
package com.mail.service;

import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import com.shidian.mail.SendMailUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Copyright © CapRobin
 *
 * Name：MyService
 * Describe：自定义Service服务类
 * Date：2021-05-06 19:54:26
 * Author: YuFarong CapRobin@yeah.net
 *
 */
public class MyService extends Service {
    public MyService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.v("onBind", "MyService onBind----------------------------->>");
        return null;
    }

    @Override
    public void onCreate() {
        Log.v("onCreate", "MyService onCreate----------------------------->>");
        super.onCreate();
    }

    @Override
    public void onStart(Intent intent, int startId) {
        Log.v("onStart", "MyService onStart----------------------------->>");
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.v("onStartCommand", "MyService onStartCommand----------------------------->>");
        ExecutorService threadPool = Executors.newCachedThreadPool();
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                int time = 0;
                while (true) {
                    Log.d("Jason", "time:" + time);
                    try {
                        Thread.sleep(20000);
                        System.out.print("开始发送短信----------------------------->>");
                        Log.d("RUN", "开始发送短信----------------------------->>");
                        //开始发送普通邮件
                        SendMailUtil.send("756657266@qq.com");

                        //开始发送文件邮件
                        sendFileEmail();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    time++;
                }
            }
        });

        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * Describe：发送带附件邮件
     * Params: []
     * Return: void
     * Date：2021-05-06 19:55:59
     */
    public void sendFileEmail() {
        File file = new File(Environment.getExternalStorageDirectory() + File.separator + "EmailFile.txt");
//        Date date = new Date();
//        String dateStr = date.toString();
//        //本地文件存储
//        boolean isSave = MethodsUtil.writeFile("测试数据测试数据......" + dateStr, file);
//        System.out.print("isSave----------------------------->>" + isSave);
        OutputStream os = null;
        try {
            os = new FileOutputStream(file);
            String str = "发送带附件邮件测试";
            byte[] data = str.getBytes();
            os.write(data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) os.close();
            } catch (IOException e) {
            }
        }
        SendMailUtil.send(file, "756657266@qq.com");
    }
}

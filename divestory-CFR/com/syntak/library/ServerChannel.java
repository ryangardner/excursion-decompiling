/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Service
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 */
package com.syntak.library;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import com.syntak.library.StringOp;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;

public class ServerChannel
extends Service {
    private static final long DELAY_INTERVAL = 0L;
    public static final String HOST_NAME = "host_name";
    public static final String HOST_PORT = "host_port";
    public static final String MESSAGE = "message";
    private static final String TAG = ServerChannel.class.getSimpleName();
    private static final long UPDATE_INTERVAL = 1000L;
    private Handler handler_UI = new Handler();
    String host_name;
    int host_port;
    BufferedReader in;
    int local_port;
    String message;
    BufferedWriter out;
    String response;
    Socket s = null;
    private Timer timer;

    public String OnNotificationUI(String string2) {
        return null;
    }

    public void OnResponseUI(String string2) {
    }

    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public void onDestroy() {
        this.timer.cancel();
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int n, int n2) {
        Object object = intent.getExtras();
        this.host_name = object.getString(HOST_NAME);
        this.host_port = object.getInt(HOST_PORT);
        this.message = object.getString(MESSAGE);
        try {
            object = new Socket(this.host_name, this.host_port);
            this.s = object;
            ((Socket)object).setSoTimeout(0);
            Runnable runnable2 = new OutputStreamWriter(this.s.getOutputStream());
            this.out = object = new BufferedWriter((Writer)((Object)runnable2));
            object = new InputStreamReader(this.s.getInputStream());
            runnable2 = new BufferedReader((Reader)object);
            this.in = runnable2;
            object = this.out;
            runnable2 = new StringBuilder();
            ((StringBuilder)((Object)runnable2)).append(this.message);
            ((StringBuilder)((Object)runnable2)).append(System.getProperty("line.separator"));
            ((Writer)object).write(((StringBuilder)((Object)runnable2)).toString());
            this.out.flush();
            this.response = object = this.in.readLine();
            if (StringOp.strlen((String)object) > 0) {
                object = this.handler_UI;
                runnable2 = new Runnable(){

                    @Override
                    public void run() {
                        ServerChannel serverChannel = ServerChannel.this;
                        serverChannel.OnResponseUI(serverChannel.response);
                    }
                };
                object.post(runnable2);
            }
        }
        catch (IOException | UnknownHostException iOException) {}
        super.onStartCommand(intent, n, n2);
        return 0;
    }

}


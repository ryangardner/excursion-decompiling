package com.syntak.library;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Timer;

public class ServerChannel extends Service {
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

   public String OnNotificationUI(String var1) {
      return null;
   }

   public void OnResponseUI(String var1) {
   }

   public IBinder onBind(Intent var1) {
      throw new UnsupportedOperationException("Not yet implemented");
   }

   public void onDestroy() {
      this.timer.cancel();
      super.onDestroy();
   }

   public int onStartCommand(Intent var1, int var2, int var3) {
      Bundle var4 = var1.getExtras();
      this.host_name = var4.getString("host_name");
      this.host_port = var4.getInt("host_port");
      this.message = var4.getString("message");

      try {
         Socket var7 = new Socket(this.host_name, this.host_port);
         this.s = var7;
         var7.setSoTimeout(0);
         OutputStreamWriter var5 = new OutputStreamWriter(this.s.getOutputStream());
         BufferedWriter var8 = new BufferedWriter(var5);
         this.out = var8;
         InputStreamReader var9 = new InputStreamReader(this.s.getInputStream());
         BufferedReader var10 = new BufferedReader(var9);
         this.in = var10;
         var8 = this.out;
         StringBuilder var11 = new StringBuilder();
         var11.append(this.message);
         var11.append(System.getProperty("line.separator"));
         var8.write(var11.toString());
         this.out.flush();
         String var12 = this.in.readLine();
         this.response = var12;
         if (StringOp.strlen(var12) > 0) {
            Handler var13 = this.handler_UI;
            Runnable var14 = new Runnable() {
               public void run() {
                  ServerChannel var1 = ServerChannel.this;
                  var1.OnResponseUI(var1.response);
               }
            };
            var13.post(var14);
         }
      } catch (IOException | UnknownHostException var6) {
      }

      super.onStartCommand(var1, var2, var3);
      return 0;
   }
}

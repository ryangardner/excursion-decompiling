package com.syntak.library.time;

import android.app.Activity;
import android.content.Intent;
import java.util.Timer;
import java.util.TimerTask;

public class TimerResetable {
   public static final String ACTION_COUNT_DOWN = "ACTION_COUNT_DOWN";
   public static final String ACTION_TIMEOUT = "ACTION_TIMEOUT";
   public static final String TAG_TIME_ELAPSED = "time_elapsed";
   public static final String TAG_TIME_LEFT = "time_left";
   public static final String TAG_USER_ID = "user_id";
   Activity activity = null;
   private boolean isPaused = true;
   private int resolution = 100;
   private TimerTask task_count_down;
   private int time_elapsed = 0;
   private int time_left = 0;
   private int timeout_setting;
   private Timer timer;
   String user_tag = null;

   public TimerResetable(int var1) {
      this.init(var1);
   }

   public TimerResetable(Activity var1, int var2) {
      this.activity = var1;
      this.init(var2);
   }

   private void broadcastCountDown() {
      if (this.activity != null) {
         Intent var1 = new Intent("ACTION_COUNT_DOWN");
         var1.putExtra("time_left", this.time_left);
         var1.putExtra("time_elapsed", this.time_elapsed);
         String var2 = this.user_tag;
         if (var2 != null) {
            var1.putExtra("user_id", var2);
         }

         this.activity.sendBroadcast(var1);
      }
   }

   private void broadcastTimeout() {
      if (this.activity != null) {
         Intent var1 = new Intent("ACTION_TIMEOUT");
         var1.putExtra("time_left", this.time_left);
         var1.putExtra("time_elapsed", this.time_elapsed);
         String var2 = this.user_tag;
         if (var2 != null) {
            var1.putExtra("user_id", var2);
         }

         this.activity.sendBroadcast(var1);
      }
   }

   private void init(int var1) {
      this.timeout_setting = var1;
      this.time_left = var1;
      this.task_count_down = new TimerTask() {
         public void run() {
            if (!TimerResetable.this.isPaused) {
               TimerResetable var1 = TimerResetable.this;
               var1.time_left = var1.time_left - TimerResetable.this.resolution;
               var1 = TimerResetable.this;
               var1.time_elapsed = var1.time_elapsed + TimerResetable.this.resolution;
               if (TimerResetable.this.user_tag != null) {
                  var1 = TimerResetable.this;
                  var1.CountDown(var1.user_tag, TimerResetable.this.time_left);
               } else {
                  var1 = TimerResetable.this;
                  var1.CountDown(var1.time_left);
               }

               TimerResetable.this.broadcastCountDown();
               if (TimerResetable.this.time_left <= 0) {
                  TimerResetable.this.timeout();
               }
            }

         }
      };
      this.start_timer();
   }

   private void start_timer() {
      Timer var1 = new Timer(true);
      this.timer = var1;
      var1.scheduleAtFixedRate(this.task_count_down, 0L, (long)this.resolution);
   }

   private void timeout() {
      this.isPaused = true;
      String var1 = this.user_tag;
      if (var1 != null) {
         this.Timeout(var1, this.time_elapsed);
      } else {
         this.Timeout(this.time_elapsed);
      }

      this.broadcastTimeout();
   }

   public void CountDown(int var1) {
   }

   public void CountDown(String var1, int var2) {
   }

   public void Timeout(int var1) {
   }

   public void Timeout(String var1, int var2) {
   }

   public void forceTimeout() {
      this.timeout();
   }

   public int getTimeElapsed() {
      return this.time_elapsed;
   }

   public int getTimeLeft() {
      return this.time_left;
   }

   public int getTimeoutSetting() {
      return this.timeout_setting;
   }

   public boolean isPaused() {
      return this.isPaused;
   }

   public TimerResetable pause() {
      this.isPaused = true;
      return this;
   }

   public TimerResetable resetTimer() {
      this.isPaused = true;
      this.time_left = this.timeout_setting;
      this.time_elapsed = 0;
      return this;
   }

   public TimerResetable resetTimer(int var1) {
      this.timeout_setting = var1;
      return this.resetTimer();
   }

   public TimerResetable setResolution(int var1) {
      this.isPaused = true;
      this.resolution = var1;
      return this;
   }

   public void start() {
      this.user_tag = null;
      this.isPaused = false;
   }

   public void start(String var1) {
      this.user_tag = var1;
      this.isPaused = false;
   }

   public void stop() {
      this.isPaused = true;
      this.timer.cancel();
      this.timer.purge();
      this.task_count_down = null;
      this.timer = null;
   }
}

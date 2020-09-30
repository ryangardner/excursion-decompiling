/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Intent
 */
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

    public TimerResetable(int n) {
        this.init(n);
    }

    public TimerResetable(Activity activity, int n) {
        this.activity = activity;
        this.init(n);
    }

    private void broadcastCountDown() {
        if (this.activity == null) {
            return;
        }
        Intent intent = new Intent(ACTION_COUNT_DOWN);
        intent.putExtra(TAG_TIME_LEFT, this.time_left);
        intent.putExtra(TAG_TIME_ELAPSED, this.time_elapsed);
        String string2 = this.user_tag;
        if (string2 != null) {
            intent.putExtra(TAG_USER_ID, string2);
        }
        this.activity.sendBroadcast(intent);
    }

    private void broadcastTimeout() {
        if (this.activity == null) {
            return;
        }
        Intent intent = new Intent(ACTION_TIMEOUT);
        intent.putExtra(TAG_TIME_LEFT, this.time_left);
        intent.putExtra(TAG_TIME_ELAPSED, this.time_elapsed);
        String string2 = this.user_tag;
        if (string2 != null) {
            intent.putExtra(TAG_USER_ID, string2);
        }
        this.activity.sendBroadcast(intent);
    }

    private void init(int n) {
        this.timeout_setting = n;
        this.time_left = n;
        this.task_count_down = new TimerTask(){

            @Override
            public void run() {
                if (TimerResetable.this.isPaused) return;
                TimerResetable timerResetable = TimerResetable.this;
                timerResetable.time_left = timerResetable.time_left - TimerResetable.this.resolution;
                timerResetable = TimerResetable.this;
                timerResetable.time_elapsed = timerResetable.time_elapsed + TimerResetable.this.resolution;
                if (TimerResetable.this.user_tag != null) {
                    timerResetable = TimerResetable.this;
                    timerResetable.CountDown(timerResetable.user_tag, TimerResetable.this.time_left);
                } else {
                    timerResetable = TimerResetable.this;
                    timerResetable.CountDown(timerResetable.time_left);
                }
                TimerResetable.this.broadcastCountDown();
                if (TimerResetable.this.time_left > 0) return;
                TimerResetable.this.timeout();
            }
        };
        this.start_timer();
    }

    private void start_timer() {
        Timer timer;
        this.timer = timer = new Timer(true);
        timer.scheduleAtFixedRate(this.task_count_down, 0L, (long)this.resolution);
    }

    private void timeout() {
        this.isPaused = true;
        String string2 = this.user_tag;
        if (string2 != null) {
            this.Timeout(string2, this.time_elapsed);
        } else {
            this.Timeout(this.time_elapsed);
        }
        this.broadcastTimeout();
    }

    public void CountDown(int n) {
    }

    public void CountDown(String string2, int n) {
    }

    public void Timeout(int n) {
    }

    public void Timeout(String string2, int n) {
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

    public TimerResetable resetTimer(int n) {
        this.timeout_setting = n;
        return this.resetTimer();
    }

    public TimerResetable setResolution(int n) {
        this.isPaused = true;
        this.resolution = n;
        return this;
    }

    public void start() {
        this.user_tag = null;
        this.isPaused = false;
    }

    public void start(String string2) {
        this.user_tag = string2;
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


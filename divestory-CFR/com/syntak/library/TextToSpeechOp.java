/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.speech.tts.TextToSpeech
 *  android.speech.tts.TextToSpeech$OnInitListener
 */
package com.syntak.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import java.util.HashMap;
import java.util.Locale;

public class TextToSpeechOp
implements TextToSpeech.OnInitListener {
    private boolean allowed = false;
    private Context context;
    private boolean ready = false;
    private TextToSpeech tts;

    public TextToSpeechOp(Context context) {
        this.context = context;
        this.tts = new TextToSpeech(context, (TextToSpeech.OnInitListener)this);
    }

    public static void check_TTS(Context context, int n) {
        Intent intent = new Intent();
        intent.setAction("android.speech.tts.engine.CHECK_TTS_DATA");
        ((Activity)context).startActivityForResult(intent, n);
    }

    public void allow(boolean bl) {
        this.allowed = bl;
    }

    public void destroy() {
        this.tts.shutdown();
    }

    public boolean isAllowed() {
        return this.allowed;
    }

    public void onInit(int n) {
        if (n == 0) {
            this.tts.setLanguage(Locale.US);
            this.ready = true;
            return;
        }
        this.ready = false;
    }

    public void speak(String string2) {
        if (!this.ready) return;
        if (!this.allowed) return;
        HashMap<String, String> hashMap = new HashMap<String, String>();
        hashMap.put("streamType", String.valueOf(5));
        this.tts.speak(string2, 1, hashMap);
    }
}


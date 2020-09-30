package com.syntak.library;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeech.OnInitListener;
import java.util.HashMap;
import java.util.Locale;

public class TextToSpeechOp implements OnInitListener {
   private boolean allowed = false;
   private Context context;
   private boolean ready = false;
   private TextToSpeech tts;

   public TextToSpeechOp(Context var1) {
      this.context = var1;
      this.tts = new TextToSpeech(var1, this);
   }

   public static void check_TTS(Context var0, int var1) {
      Intent var2 = new Intent();
      var2.setAction("android.speech.tts.engine.CHECK_TTS_DATA");
      ((Activity)var0).startActivityForResult(var2, var1);
   }

   public void allow(boolean var1) {
      this.allowed = var1;
   }

   public void destroy() {
      this.tts.shutdown();
   }

   public boolean isAllowed() {
      return this.allowed;
   }

   public void onInit(int var1) {
      if (var1 == 0) {
         this.tts.setLanguage(Locale.US);
         this.ready = true;
      } else {
         this.ready = false;
      }

   }

   public void speak(String var1) {
      if (this.ready && this.allowed) {
         HashMap var2 = new HashMap();
         var2.put("streamType", String.valueOf(5));
         this.tts.speak(var1, 1, var2);
      }

   }
}

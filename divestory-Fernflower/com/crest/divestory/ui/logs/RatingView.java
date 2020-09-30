package com.crest.divestory.ui.logs;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RatingView {
   Activity activity;
   private int selected = 0;

   RatingView(Activity var1, int var2) {
      this.activity = var1;
      this.init(var2);
   }

   RatingView(Activity var1, int var2, int var3) {
      this.activity = var1;
      this.selected = Math.min(var2, var3);
      this.init(var2);
   }

   private void init(int var1) {
      LinearLayout var2 = (LinearLayout)this.activity.findViewById(2131231239);
      final ImageView[] var3 = new ImageView[var1];

      int var6;
      for(int var4 = 0; var4 < var1; var4 = var6) {
         ImageView var5 = (ImageView)this.activity.getLayoutInflater().inflate(2131427451, (ViewGroup)null);
         var6 = var4 + 1;
         var5.setTag(var6);
         var5.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               RatingView.this.selected = (Integer)var1.getTag();
               int var2 = 0;

               while(true) {
                  ImageView[] var4 = var3;
                  if (var2 >= var4.length) {
                     return;
                  }

                  ImageView var5 = var4[var2];
                  boolean var3x;
                  if (var2 < RatingView.this.selected) {
                     var3x = true;
                  } else {
                     var3x = false;
                  }

                  var5.setSelected(var3x);
                  ++var2;
               }
            }
         });
         var3[var4] = var5;
         ImageView var7 = var3[var4];
         boolean var8;
         if (var4 < this.selected) {
            var8 = true;
         } else {
            var8 = false;
         }

         var7.setSelected(var8);
         var2.addView(var5);
      }

   }
}

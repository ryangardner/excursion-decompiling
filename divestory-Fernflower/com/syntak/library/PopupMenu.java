package com.syntak.library;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class PopupMenu {
   public PopupWindow NewInstance(Context var1, String[] var2, LayoutParams var3, View var4) {
      View var5 = ((LayoutInflater)var1.getSystemService("layout_inflater")).inflate(R.layout.popup_menu, (ViewGroup)null);
      LinearLayout var6 = (LinearLayout)var5.findViewById(R.id.linear_layout);
      PopupWindow var7 = new PopupWindow(var5, -2, -2);
      ImageView var8 = (ImageView)var5.findViewById(R.id.image);
      TextView var10 = (TextView)var5.findViewById(R.id.text_view);
      var8.setVisibility(8);
      LayoutParams var11 = var10.getLayoutParams();

      for(int var9 = 0; var9 < var2.length; ++var9) {
         var10 = new TextView(var1);
         if (var3 != null) {
            var10.setLayoutParams(var3);
         } else {
            var10.setLayoutParams(var11);
         }

         var6.addView(var10);
         var10.setTag(var9);
         var10.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               int var2 = (Integer)var1.getTag();
               PopupMenu.this.onItemClicked(var2);
            }
         });
      }

      var7.setBackgroundDrawable(new BitmapDrawable());
      var7.setOutsideTouchable(false);
      var7.showAtLocation(var4, 17, 0, 0);
      return var7;
   }

   public void PopupMenu() {
   }

   public void onItemClicked(int var1) {
   }
}

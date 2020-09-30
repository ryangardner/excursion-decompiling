package com.syntak.library.ui;

import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;

public class ShowHiddenVIewByClick {
   ShowHiddenVIewByClick.CLICK_TYPE click_type_for_hide;
   ShowHiddenVIewByClick.CLICK_TYPE click_type_for_show;
   View hidden;
   View title;

   public ShowHiddenVIewByClick(View var1, View var2) {
      this.click_type_for_show = ShowHiddenVIewByClick.CLICK_TYPE.SHORT;
      this.click_type_for_hide = ShowHiddenVIewByClick.CLICK_TYPE.SHORT;
      this.title = var1;
      this.hidden = var2;
      this.start();
   }

   public ShowHiddenVIewByClick(View var1, View var2, ShowHiddenVIewByClick.CLICK_TYPE var3, ShowHiddenVIewByClick.CLICK_TYPE var4) {
      this.click_type_for_show = ShowHiddenVIewByClick.CLICK_TYPE.SHORT;
      this.click_type_for_hide = ShowHiddenVIewByClick.CLICK_TYPE.SHORT;
      this.title = var1;
      this.hidden = var2;
      this.click_type_for_show = var3;
      this.click_type_for_hide = var4;
      this.start();
   }

   private void start() {
      if (this.click_type_for_show == ShowHiddenVIewByClick.CLICK_TYPE.SHORT) {
         this.title.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               ShowHiddenVIewByClick.this.toggle_view(var1);
            }
         });
      } else {
         this.title.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View var1) {
               ShowHiddenVIewByClick.this.toggle_view(var1);
               return true;
            }
         });
      }

      if (this.click_type_for_hide == ShowHiddenVIewByClick.CLICK_TYPE.SHORT) {
         this.title.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               ShowHiddenVIewByClick.this.toggle_view(var1);
            }
         });
      } else {
         this.title.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View var1) {
               ShowHiddenVIewByClick.this.toggle_view(var1);
               return true;
            }
         });
      }

      this.title.setTag(0);
      this.hidden.setVisibility(8);
   }

   private void toggle_view(View var1) {
      if ((Integer)var1.getTag() == 0) {
         var1.setTag(1);
         this.hidden.setVisibility(0);
      } else {
         var1.setTag(0);
         this.hidden.setVisibility(8);
      }

   }

   public static enum CLICK_TYPE {
      LONG,
      SHORT;

      static {
         ShowHiddenVIewByClick.CLICK_TYPE var0 = new ShowHiddenVIewByClick.CLICK_TYPE("LONG", 1);
         LONG = var0;
      }
   }
}

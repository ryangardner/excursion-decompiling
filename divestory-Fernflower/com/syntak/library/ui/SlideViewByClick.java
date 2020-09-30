package com.syntak.library.ui;

import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import com.syntak.library.R;
import com.syntak.library.UiOp;

public class SlideViewByClick {
   boolean auto_sizing = false;
   View back;
   SlideViewByClick.CLICK_TYPE click_type_for_off;
   SlideViewByClick.CLICK_TYPE click_type_for_on;
   SlideViewByClick.SLIDE_DIRECTION direction;
   View front;
   final int id_tag;
   int size_back = 0;
   int size_front = 0;
   int thickness_front = 0;
   SlideViewByClick.TOWARD toward;

   public SlideViewByClick(View var1, View var2, SlideViewByClick.SLIDE_DIRECTION var3, SlideViewByClick.TOWARD var4) {
      this.id_tag = R.id.tag_9;
      this.click_type_for_on = SlideViewByClick.CLICK_TYPE.LONG;
      this.click_type_for_off = SlideViewByClick.CLICK_TYPE.SHORT;
      this.front = var1;
      this.back = var2;
      this.direction = var3;
      this.toward = var4;
   }

   public SlideViewByClick(View var1, View var2, SlideViewByClick.SLIDE_DIRECTION var3, SlideViewByClick.TOWARD var4, SlideViewByClick.CLICK_TYPE var5, SlideViewByClick.CLICK_TYPE var6) {
      this.id_tag = R.id.tag_9;
      this.click_type_for_on = SlideViewByClick.CLICK_TYPE.LONG;
      this.click_type_for_off = SlideViewByClick.CLICK_TYPE.SHORT;
      this.front = var1;
      this.back = var2;
      this.direction = var3;
      this.toward = var4;
      this.click_type_for_on = var5;
      this.click_type_for_off = var6;
   }

   private void back_resizing() {
      this.back.post(new Runnable() {
         public void run() {
            LayoutParams var1 = SlideViewByClick.this.back.getLayoutParams();
            if (SlideViewByClick.this.direction == SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL) {
               var1.height = SlideViewByClick.this.thickness_front;
            } else {
               var1.width = SlideViewByClick.this.thickness_front;
            }

            SlideViewByClick.this.back.setLayoutParams(var1);
         }
      });
   }

   private void click_off(View var1) {
      if ((Integer)var1.getTag(this.id_tag) == 0) {
         this.OnFrontClick(var1);
      } else {
         var1.setTag(this.id_tag, 0);
         UiOp.TRANSLATION_AXIS var2;
         if (this.direction == SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL) {
            var2 = UiOp.TRANSLATION_AXIS.X;
         } else {
            var2 = UiOp.TRANSLATION_AXIS.Y;
         }

         UiOp.viewAnimation(var1, var2, 0.0F, 300, UiOp.INTERPOLATION_TYPE.ACCEL_DECEL, true);
      }

   }

   private void click_on(View var1) {
      if ((Integer)var1.getTag(this.id_tag) == 0) {
         var1.setTag(this.id_tag, 1);
         UiOp.TRANSLATION_AXIS var2;
         if (this.direction == SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL) {
            var2 = UiOp.TRANSLATION_AXIS.X;
         } else {
            var2 = UiOp.TRANSLATION_AXIS.Y;
         }

         int var3;
         if (this.toward == SlideViewByClick.TOWARD.LEAD) {
            var3 = -this.size_back;
         } else {
            var3 = this.size_back;
         }

         UiOp.viewAnimation(var1, var2, (float)var3, 300, UiOp.INTERPOLATION_TYPE.ACCEL_DECEL, true);
      }

   }

   public void OnFrontClick(View var1) {
   }

   public void reset() {
      if ((Integer)this.front.getTag(this.id_tag) == 1) {
         this.front.setTag(this.id_tag, 0);
         View var1 = this.front;
         UiOp.TRANSLATION_AXIS var2;
         if (this.direction == SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL) {
            var2 = UiOp.TRANSLATION_AXIS.X;
         } else {
            var2 = UiOp.TRANSLATION_AXIS.Y;
         }

         UiOp.viewAnimation(var1, var2, 0.0F, 1, UiOp.INTERPOLATION_TYPE.ACCEL_DECEL, true);
      }

   }

   public SlideViewByClick setAutoSizing(boolean var1) {
      this.auto_sizing = var1;
      return this;
   }

   public void start() {
      if (this.click_type_for_on == SlideViewByClick.CLICK_TYPE.SHORT) {
         this.front.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               SlideViewByClick.this.click_on(var1);
            }
         });
      } else {
         this.front.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View var1) {
               SlideViewByClick.this.click_on(var1);
               return true;
            }
         });
      }

      if (this.click_type_for_off == SlideViewByClick.CLICK_TYPE.SHORT) {
         this.front.setOnClickListener(new OnClickListener() {
            public void onClick(View var1) {
               SlideViewByClick.this.click_off(var1);
            }
         });
      } else {
         this.front.setOnLongClickListener(new OnLongClickListener() {
            public boolean onLongClick(View var1) {
               SlideViewByClick.this.click_off(var1);
               return true;
            }
         });
      }

      this.front.setTag(this.id_tag, 0);
      View var1 = this.back;
      ViewTreeObserver var2;
      if (var1 != null) {
         var2 = var1.getViewTreeObserver();
         if (var2.isAlive()) {
            var2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
               public void onGlobalLayout() {
                  SlideViewByClick.this.back.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                  SlideViewByClick var1;
                  if (SlideViewByClick.this.direction == SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL) {
                     var1 = SlideViewByClick.this;
                     var1.size_back = var1.back.getWidth();
                  } else {
                     var1 = SlideViewByClick.this;
                     var1.size_back = var1.back.getHeight();
                  }

                  if (SlideViewByClick.this.auto_sizing && SlideViewByClick.this.thickness_front > 0) {
                     SlideViewByClick.this.back_resizing();
                  }

               }
            });
         }
      }

      var1 = this.front;
      if (var1 != null) {
         var2 = var1.getViewTreeObserver();
         if (var2.isAlive()) {
            var2.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
               public void onGlobalLayout() {
                  SlideViewByClick.this.front.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                  SlideViewByClick var1;
                  if (SlideViewByClick.this.direction == SlideViewByClick.SLIDE_DIRECTION.HORIZONTAL) {
                     var1 = SlideViewByClick.this;
                     var1.size_front = var1.front.getWidth();
                     var1 = SlideViewByClick.this;
                     var1.thickness_front = var1.front.getHeight();
                  } else {
                     var1 = SlideViewByClick.this;
                     var1.size_front = var1.front.getHeight();
                     var1 = SlideViewByClick.this;
                     var1.thickness_front = var1.front.getWidth();
                  }

                  if (SlideViewByClick.this.auto_sizing && SlideViewByClick.this.size_back > 0) {
                     SlideViewByClick.this.back_resizing();
                  }

               }
            });
         }
      }

   }

   public void stop() {
      this.click_off(this.front);
   }

   public static enum CLICK_TYPE {
      LONG,
      SHORT;

      static {
         SlideViewByClick.CLICK_TYPE var0 = new SlideViewByClick.CLICK_TYPE("LONG", 1);
         LONG = var0;
      }
   }

   public static enum SLIDE_DIRECTION {
      HORIZONTAL,
      VERTICAL;

      static {
         SlideViewByClick.SLIDE_DIRECTION var0 = new SlideViewByClick.SLIDE_DIRECTION("VERTICAL", 1);
         VERTICAL = var0;
      }
   }

   public static enum TOWARD {
      LEAD,
      TRAIL;

      static {
         SlideViewByClick.TOWARD var0 = new SlideViewByClick.TOWARD("TRAIL", 1);
         TRAIL = var0;
      }
   }
}

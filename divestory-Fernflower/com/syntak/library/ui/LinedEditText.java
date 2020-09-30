package com.syntak.library.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import androidx.appcompat.widget.AppCompatEditText;

public class LinedEditText extends AppCompatEditText {
   private Paint mPaint = new Paint();

   public LinedEditText(Context var1) {
      super(var1);
      this.initPaint();
   }

   public LinedEditText(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.initPaint();
   }

   public LinedEditText(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.initPaint();
   }

   private void initPaint() {
      this.mPaint.setStyle(Style.STROKE);
      this.mPaint.setColor(Integer.MIN_VALUE);
   }

   protected void onDraw(Canvas var1) {
      int var2 = this.getLeft();
      int var3 = this.getRight();
      int var4 = this.getPaddingTop();
      int var5 = this.getPaddingBottom();
      int var6 = this.getPaddingLeft();
      int var7 = this.getPaddingRight();
      int var8 = this.getHeight();
      int var9 = this.getLineHeight();
      var8 = (var8 - var4 - var5) / var9;
      var5 = 0;

      while(var5 < var8) {
         ++var5;
         float var10 = (float)(var2 + var6);
         float var11 = (float)(var9 * var5 + var4);
         var1.drawLine(var10, var11, (float)(var3 - var7), var11, this.mPaint);
      }

      super.onDraw(var1);
   }
}

package com.github.mikephil.charting.renderer.scatter;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Paint.Style;
import com.github.mikephil.charting.interfaces.datasets.IScatterDataSet;
import com.github.mikephil.charting.utils.Utils;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class TriangleShapeRenderer implements IShapeRenderer {
   protected Path mTrianglePathBuffer = new Path();

   public void renderShape(Canvas var1, IScatterDataSet var2, ViewPortHandler var3, float var4, float var5, Paint var6) {
      float var7 = var2.getScatterShapeSize();
      float var8 = var7 / 2.0F;
      float var9 = (var7 - Utils.convertDpToPixel(var2.getScatterShapeHoleRadius()) * 2.0F) / 2.0F;
      int var10 = var2.getScatterShapeHoleColor();
      var6.setStyle(Style.FILL);
      Path var15 = this.mTrianglePathBuffer;
      var15.reset();
      float var11 = var5 - var8;
      var15.moveTo(var4, var11);
      float var12 = var4 + var8;
      float var13 = var5 + var8;
      var15.lineTo(var12, var13);
      var5 = var4 - var8;
      var15.lineTo(var5, var13);
      double var16;
      int var14 = (var16 = (double)var7 - 0.0D) == 0.0D ? 0 : (var16 < 0.0D ? -1 : 1);
      if (var14 > 0) {
         var15.lineTo(var4, var11);
         var8 = var5 + var9;
         var7 = var13 - var9;
         var15.moveTo(var8, var7);
         var15.lineTo(var12 - var9, var7);
         var15.lineTo(var4, var11 + var9);
         var15.lineTo(var8, var7);
      }

      var15.close();
      var1.drawPath(var15, var6);
      var15.reset();
      if (var14 > 0 && var10 != 1122867) {
         var6.setColor(var10);
         var15.moveTo(var4, var11 + var9);
         var4 = var13 - var9;
         var15.lineTo(var12 - var9, var4);
         var15.lineTo(var5 + var9, var4);
         var15.close();
         var1.drawPath(var15, var6);
         var15.reset();
      }

   }
}

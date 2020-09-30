package com.google.android.material.shape;

import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.RectF;
import com.google.android.material.shadow.ShadowRenderer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ShapePath {
   protected static final float ANGLE_LEFT = 180.0F;
   private static final float ANGLE_UP = 270.0F;
   private boolean containsIncompatibleShadowOp;
   @Deprecated
   public float currentShadowAngle;
   @Deprecated
   public float endShadowAngle;
   @Deprecated
   public float endX;
   @Deprecated
   public float endY;
   private final List<ShapePath.PathOperation> operations = new ArrayList();
   private final List<ShapePath.ShadowCompatOperation> shadowCompatOperations = new ArrayList();
   @Deprecated
   public float startX;
   @Deprecated
   public float startY;

   public ShapePath() {
      this.reset(0.0F, 0.0F);
   }

   public ShapePath(float var1, float var2) {
      this.reset(var1, var2);
   }

   private void addConnectingShadowIfNecessary(float var1) {
      if (this.getCurrentShadowAngle() != var1) {
         float var2 = (var1 - this.getCurrentShadowAngle() + 360.0F) % 360.0F;
         if (var2 <= 180.0F) {
            ShapePath.PathArcOperation var3 = new ShapePath.PathArcOperation(this.getEndX(), this.getEndY(), this.getEndX(), this.getEndY());
            var3.setStartAngle(this.getCurrentShadowAngle());
            var3.setSweepAngle(var2);
            this.shadowCompatOperations.add(new ShapePath.ArcShadowOperation(var3));
            this.setCurrentShadowAngle(var1);
         }
      }
   }

   private void addShadowCompatOperation(ShapePath.ShadowCompatOperation var1, float var2, float var3) {
      this.addConnectingShadowIfNecessary(var2);
      this.shadowCompatOperations.add(var1);
      this.setCurrentShadowAngle(var3);
   }

   private float getCurrentShadowAngle() {
      return this.currentShadowAngle;
   }

   private float getEndShadowAngle() {
      return this.endShadowAngle;
   }

   private void setCurrentShadowAngle(float var1) {
      this.currentShadowAngle = var1;
   }

   private void setEndShadowAngle(float var1) {
      this.endShadowAngle = var1;
   }

   private void setEndX(float var1) {
      this.endX = var1;
   }

   private void setEndY(float var1) {
      this.endY = var1;
   }

   private void setStartX(float var1) {
      this.startX = var1;
   }

   private void setStartY(float var1) {
      this.startY = var1;
   }

   public void addArc(float var1, float var2, float var3, float var4, float var5, float var6) {
      ShapePath.PathArcOperation var7 = new ShapePath.PathArcOperation(var1, var2, var3, var4);
      var7.setStartAngle(var5);
      var7.setSweepAngle(var6);
      this.operations.add(var7);
      ShapePath.ArcShadowOperation var12 = new ShapePath.ArcShadowOperation(var7);
      float var8 = var5 + var6;
      boolean var9;
      if (var6 < 0.0F) {
         var9 = true;
      } else {
         var9 = false;
      }

      var6 = var5;
      if (var9) {
         var6 = (var5 + 180.0F) % 360.0F;
      }

      if (var9) {
         var5 = (180.0F + var8) % 360.0F;
      } else {
         var5 = var8;
      }

      this.addShadowCompatOperation(var12, var6, var5);
      var5 = (var3 - var1) / 2.0F;
      double var10 = (double)var8;
      this.setEndX((var1 + var3) * 0.5F + var5 * (float)Math.cos(Math.toRadians(var10)));
      this.setEndY((var2 + var4) * 0.5F + (var4 - var2) / 2.0F * (float)Math.sin(Math.toRadians(var10)));
   }

   public void applyToPath(Matrix var1, Path var2) {
      int var3 = this.operations.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         ((ShapePath.PathOperation)this.operations.get(var4)).applyToPath(var1, var2);
      }

   }

   boolean containsIncompatibleShadowOp() {
      return this.containsIncompatibleShadowOp;
   }

   ShapePath.ShadowCompatOperation createShadowCompatOperation(final Matrix var1) {
      this.addConnectingShadowIfNecessary(this.getEndShadowAngle());
      return new ShapePath.ShadowCompatOperation(new ArrayList(this.shadowCompatOperations)) {
         // $FF: synthetic field
         final List val$operations;

         {
            this.val$operations = var2;
         }

         public void draw(Matrix var1x, ShadowRenderer var2, int var3, Canvas var4) {
            Iterator var5 = this.val$operations.iterator();

            while(var5.hasNext()) {
               ((ShapePath.ShadowCompatOperation)var5.next()).draw(var1, var2, var3, var4);
            }

         }
      };
   }

   public void cubicToPoint(float var1, float var2, float var3, float var4, float var5, float var6) {
      ShapePath.PathCubicOperation var7 = new ShapePath.PathCubicOperation(var1, var2, var3, var4, var5, var6);
      this.operations.add(var7);
      this.containsIncompatibleShadowOp = true;
      this.setEndX(var5);
      this.setEndY(var6);
   }

   float getEndX() {
      return this.endX;
   }

   float getEndY() {
      return this.endY;
   }

   float getStartX() {
      return this.startX;
   }

   float getStartY() {
      return this.startY;
   }

   public void lineTo(float var1, float var2) {
      ShapePath.PathLineOperation var3 = new ShapePath.PathLineOperation();
      var3.x = var1;
      var3.y = var2;
      this.operations.add(var3);
      ShapePath.LineShadowOperation var4 = new ShapePath.LineShadowOperation(var3, this.getEndX(), this.getEndY());
      this.addShadowCompatOperation(var4, var4.getAngle() + 270.0F, var4.getAngle() + 270.0F);
      this.setEndX(var1);
      this.setEndY(var2);
   }

   public void quadToPoint(float var1, float var2, float var3, float var4) {
      ShapePath.PathQuadOperation var5 = new ShapePath.PathQuadOperation();
      var5.setControlX(var1);
      var5.setControlY(var2);
      var5.setEndX(var3);
      var5.setEndY(var4);
      this.operations.add(var5);
      this.containsIncompatibleShadowOp = true;
      this.setEndX(var3);
      this.setEndY(var4);
   }

   public void reset(float var1, float var2) {
      this.reset(var1, var2, 270.0F, 0.0F);
   }

   public void reset(float var1, float var2, float var3, float var4) {
      this.setStartX(var1);
      this.setStartY(var2);
      this.setEndX(var1);
      this.setEndY(var2);
      this.setCurrentShadowAngle(var3);
      this.setEndShadowAngle((var3 + var4) % 360.0F);
      this.operations.clear();
      this.shadowCompatOperations.clear();
      this.containsIncompatibleShadowOp = false;
   }

   static class ArcShadowOperation extends ShapePath.ShadowCompatOperation {
      private final ShapePath.PathArcOperation operation;

      public ArcShadowOperation(ShapePath.PathArcOperation var1) {
         this.operation = var1;
      }

      public void draw(Matrix var1, ShadowRenderer var2, int var3, Canvas var4) {
         float var5 = this.operation.getStartAngle();
         float var6 = this.operation.getSweepAngle();
         var2.drawCornerShadow(var4, var1, new RectF(this.operation.getLeft(), this.operation.getTop(), this.operation.getRight(), this.operation.getBottom()), var3, var5, var6);
      }
   }

   static class LineShadowOperation extends ShapePath.ShadowCompatOperation {
      private final ShapePath.PathLineOperation operation;
      private final float startX;
      private final float startY;

      public LineShadowOperation(ShapePath.PathLineOperation var1, float var2, float var3) {
         this.operation = var1;
         this.startX = var2;
         this.startY = var3;
      }

      public void draw(Matrix var1, ShadowRenderer var2, int var3, Canvas var4) {
         float var5 = this.operation.y;
         float var6 = this.startY;
         float var7 = this.operation.x;
         float var8 = this.startX;
         RectF var9 = new RectF(0.0F, 0.0F, (float)Math.hypot((double)(var5 - var6), (double)(var7 - var8)), 0.0F);
         var1 = new Matrix(var1);
         var1.preTranslate(this.startX, this.startY);
         var1.preRotate(this.getAngle());
         var2.drawEdgeShadow(var4, var1, var9, var3);
      }

      float getAngle() {
         return (float)Math.toDegrees(Math.atan((double)((this.operation.y - this.startY) / (this.operation.x - this.startX))));
      }
   }

   public static class PathArcOperation extends ShapePath.PathOperation {
      private static final RectF rectF = new RectF();
      @Deprecated
      public float bottom;
      @Deprecated
      public float left;
      @Deprecated
      public float right;
      @Deprecated
      public float startAngle;
      @Deprecated
      public float sweepAngle;
      @Deprecated
      public float top;

      public PathArcOperation(float var1, float var2, float var3, float var4) {
         this.setLeft(var1);
         this.setTop(var2);
         this.setRight(var3);
         this.setBottom(var4);
      }

      private float getBottom() {
         return this.bottom;
      }

      private float getLeft() {
         return this.left;
      }

      private float getRight() {
         return this.right;
      }

      private float getStartAngle() {
         return this.startAngle;
      }

      private float getSweepAngle() {
         return this.sweepAngle;
      }

      private float getTop() {
         return this.top;
      }

      private void setBottom(float var1) {
         this.bottom = var1;
      }

      private void setLeft(float var1) {
         this.left = var1;
      }

      private void setRight(float var1) {
         this.right = var1;
      }

      private void setStartAngle(float var1) {
         this.startAngle = var1;
      }

      private void setSweepAngle(float var1) {
         this.sweepAngle = var1;
      }

      private void setTop(float var1) {
         this.top = var1;
      }

      public void applyToPath(Matrix var1, Path var2) {
         Matrix var3 = this.matrix;
         var1.invert(var3);
         var2.transform(var3);
         rectF.set(this.getLeft(), this.getTop(), this.getRight(), this.getBottom());
         var2.arcTo(rectF, this.getStartAngle(), this.getSweepAngle(), false);
         var2.transform(var1);
      }
   }

   public static class PathCubicOperation extends ShapePath.PathOperation {
      private float controlX1;
      private float controlX2;
      private float controlY1;
      private float controlY2;
      private float endX;
      private float endY;

      public PathCubicOperation(float var1, float var2, float var3, float var4, float var5, float var6) {
         this.setControlX1(var1);
         this.setControlY1(var2);
         this.setControlX2(var3);
         this.setControlY2(var4);
         this.setEndX(var5);
         this.setEndY(var6);
      }

      private float getControlX1() {
         return this.controlX1;
      }

      private float getControlX2() {
         return this.controlX2;
      }

      private float getControlY1() {
         return this.controlY1;
      }

      private float getControlY2() {
         return this.controlY1;
      }

      private float getEndX() {
         return this.endX;
      }

      private float getEndY() {
         return this.endY;
      }

      private void setControlX1(float var1) {
         this.controlX1 = var1;
      }

      private void setControlX2(float var1) {
         this.controlX2 = var1;
      }

      private void setControlY1(float var1) {
         this.controlY1 = var1;
      }

      private void setControlY2(float var1) {
         this.controlY2 = var1;
      }

      private void setEndX(float var1) {
         this.endX = var1;
      }

      private void setEndY(float var1) {
         this.endY = var1;
      }

      public void applyToPath(Matrix var1, Path var2) {
         Matrix var3 = this.matrix;
         var1.invert(var3);
         var2.transform(var3);
         var2.cubicTo(this.controlX1, this.controlY1, this.controlX2, this.controlY2, this.endX, this.endY);
         var2.transform(var1);
      }
   }

   public static class PathLineOperation extends ShapePath.PathOperation {
      private float x;
      private float y;

      public void applyToPath(Matrix var1, Path var2) {
         Matrix var3 = this.matrix;
         var1.invert(var3);
         var2.transform(var3);
         var2.lineTo(this.x, this.y);
         var2.transform(var1);
      }
   }

   public abstract static class PathOperation {
      protected final Matrix matrix = new Matrix();

      public abstract void applyToPath(Matrix var1, Path var2);
   }

   public static class PathQuadOperation extends ShapePath.PathOperation {
      @Deprecated
      public float controlX;
      @Deprecated
      public float controlY;
      @Deprecated
      public float endX;
      @Deprecated
      public float endY;

      private float getControlX() {
         return this.controlX;
      }

      private float getControlY() {
         return this.controlY;
      }

      private float getEndX() {
         return this.endX;
      }

      private float getEndY() {
         return this.endY;
      }

      private void setControlX(float var1) {
         this.controlX = var1;
      }

      private void setControlY(float var1) {
         this.controlY = var1;
      }

      private void setEndX(float var1) {
         this.endX = var1;
      }

      private void setEndY(float var1) {
         this.endY = var1;
      }

      public void applyToPath(Matrix var1, Path var2) {
         Matrix var3 = this.matrix;
         var1.invert(var3);
         var2.transform(var3);
         var2.quadTo(this.getControlX(), this.getControlY(), this.getEndX(), this.getEndY());
         var2.transform(var1);
      }
   }

   abstract static class ShadowCompatOperation {
      static final Matrix IDENTITY_MATRIX = new Matrix();

      public abstract void draw(Matrix var1, ShadowRenderer var2, int var3, Canvas var4);

      public final void draw(ShadowRenderer var1, int var2, Canvas var3) {
         this.draw(IDENTITY_MATRIX, var1, var2, var3);
      }
   }
}

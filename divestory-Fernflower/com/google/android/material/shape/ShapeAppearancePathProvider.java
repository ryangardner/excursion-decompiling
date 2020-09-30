package com.google.android.material.shape;

import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PointF;
import android.graphics.RectF;
import android.graphics.Path.Direction;
import android.graphics.Path.Op;
import android.os.Build.VERSION;

public class ShapeAppearancePathProvider {
   private final Path boundsPath = new Path();
   private final ShapePath[] cornerPaths = new ShapePath[4];
   private final Matrix[] cornerTransforms = new Matrix[4];
   private boolean edgeIntersectionCheckEnabled = true;
   private final Matrix[] edgeTransforms = new Matrix[4];
   private final Path overlappedEdgePath = new Path();
   private final PointF pointF = new PointF();
   private final float[] scratch = new float[2];
   private final float[] scratch2 = new float[2];
   private final ShapePath shapePath = new ShapePath();

   public ShapeAppearancePathProvider() {
      for(int var1 = 0; var1 < 4; ++var1) {
         this.cornerPaths[var1] = new ShapePath();
         this.cornerTransforms[var1] = new Matrix();
         this.edgeTransforms[var1] = new Matrix();
      }

   }

   private float angleOfEdge(int var1) {
      return (float)((var1 + 1) * 90);
   }

   private void appendCornerPath(ShapeAppearancePathProvider.ShapeAppearancePathSpec var1, int var2) {
      this.scratch[0] = this.cornerPaths[var2].getStartX();
      this.scratch[1] = this.cornerPaths[var2].getStartY();
      this.cornerTransforms[var2].mapPoints(this.scratch);
      Path var3;
      float[] var4;
      if (var2 == 0) {
         var3 = var1.path;
         var4 = this.scratch;
         var3.moveTo(var4[0], var4[1]);
      } else {
         var3 = var1.path;
         var4 = this.scratch;
         var3.lineTo(var4[0], var4[1]);
      }

      this.cornerPaths[var2].applyToPath(this.cornerTransforms[var2], var1.path);
      if (var1.pathListener != null) {
         var1.pathListener.onCornerPathCreated(this.cornerPaths[var2], this.cornerTransforms[var2], var2);
      }

   }

   private void appendEdgePath(ShapeAppearancePathProvider.ShapeAppearancePathSpec var1, int var2) {
      int var3 = (var2 + 1) % 4;
      this.scratch[0] = this.cornerPaths[var2].getEndX();
      this.scratch[1] = this.cornerPaths[var2].getEndY();
      this.cornerTransforms[var2].mapPoints(this.scratch);
      this.scratch2[0] = this.cornerPaths[var3].getStartX();
      this.scratch2[1] = this.cornerPaths[var3].getStartY();
      this.cornerTransforms[var3].mapPoints(this.scratch2);
      float[] var4 = this.scratch;
      float var5 = var4[0];
      float[] var6 = this.scratch2;
      float var7 = Math.max((float)Math.hypot((double)(var5 - var6[0]), (double)(var4[1] - var6[1])) - 0.001F, 0.0F);
      var5 = this.getEdgeCenterForIndex(var1.bounds, var2);
      this.shapePath.reset(0.0F, 0.0F);
      EdgeTreatment var8 = this.getEdgeTreatmentForIndex(var2, var1.shapeAppearanceModel);
      var8.getEdgePath(var7, var5, var1.interpolation, this.shapePath);
      Path var9 = new Path();
      this.shapePath.applyToPath(this.edgeTransforms[var2], var9);
      if (!this.edgeIntersectionCheckEnabled || VERSION.SDK_INT < 19 || !var8.forceIntersection() && !this.pathOverlapsCorner(var9, var2) && !this.pathOverlapsCorner(var9, var3)) {
         this.shapePath.applyToPath(this.edgeTransforms[var2], var1.path);
      } else {
         var9.op(var9, this.boundsPath, Op.DIFFERENCE);
         this.scratch[0] = this.shapePath.getStartX();
         this.scratch[1] = this.shapePath.getStartY();
         this.edgeTransforms[var2].mapPoints(this.scratch);
         var9 = this.overlappedEdgePath;
         var4 = this.scratch;
         var9.moveTo(var4[0], var4[1]);
         this.shapePath.applyToPath(this.edgeTransforms[var2], this.overlappedEdgePath);
      }

      if (var1.pathListener != null) {
         var1.pathListener.onEdgePathCreated(this.shapePath, this.edgeTransforms[var2], var2);
      }

   }

   private void getCoordinatesOfCorner(int var1, RectF var2, PointF var3) {
      if (var1 != 1) {
         if (var1 != 2) {
            if (var1 != 3) {
               var3.set(var2.right, var2.top);
            } else {
               var3.set(var2.left, var2.top);
            }
         } else {
            var3.set(var2.left, var2.bottom);
         }
      } else {
         var3.set(var2.right, var2.bottom);
      }

   }

   private CornerSize getCornerSizeForIndex(int var1, ShapeAppearanceModel var2) {
      if (var1 != 1) {
         if (var1 != 2) {
            return var1 != 3 ? var2.getTopRightCornerSize() : var2.getTopLeftCornerSize();
         } else {
            return var2.getBottomLeftCornerSize();
         }
      } else {
         return var2.getBottomRightCornerSize();
      }
   }

   private CornerTreatment getCornerTreatmentForIndex(int var1, ShapeAppearanceModel var2) {
      if (var1 != 1) {
         if (var1 != 2) {
            return var1 != 3 ? var2.getTopRightCorner() : var2.getTopLeftCorner();
         } else {
            return var2.getBottomLeftCorner();
         }
      } else {
         return var2.getBottomRightCorner();
      }
   }

   private float getEdgeCenterForIndex(RectF var1, int var2) {
      this.scratch[0] = this.cornerPaths[var2].endX;
      this.scratch[1] = this.cornerPaths[var2].endY;
      this.cornerTransforms[var2].mapPoints(this.scratch);
      return var2 != 1 && var2 != 3 ? Math.abs(var1.centerY() - this.scratch[1]) : Math.abs(var1.centerX() - this.scratch[0]);
   }

   private EdgeTreatment getEdgeTreatmentForIndex(int var1, ShapeAppearanceModel var2) {
      if (var1 != 1) {
         if (var1 != 2) {
            return var1 != 3 ? var2.getRightEdge() : var2.getTopEdge();
         } else {
            return var2.getLeftEdge();
         }
      } else {
         return var2.getBottomEdge();
      }
   }

   private boolean pathOverlapsCorner(Path var1, int var2) {
      Path var3 = new Path();
      this.cornerPaths[var2].applyToPath(this.cornerTransforms[var2], var3);
      RectF var4 = new RectF();
      boolean var5 = true;
      var1.computeBounds(var4, true);
      var3.computeBounds(var4, true);
      var1.op(var3, Op.INTERSECT);
      var1.computeBounds(var4, true);
      boolean var6 = var5;
      if (var4.isEmpty()) {
         if (var4.width() > 1.0F && var4.height() > 1.0F) {
            var6 = var5;
         } else {
            var6 = false;
         }
      }

      return var6;
   }

   private void setCornerPathAndTransform(ShapeAppearancePathProvider.ShapeAppearancePathSpec var1, int var2) {
      CornerSize var3 = this.getCornerSizeForIndex(var2, var1.shapeAppearanceModel);
      this.getCornerTreatmentForIndex(var2, var1.shapeAppearanceModel).getCornerPath(this.cornerPaths[var2], 90.0F, var1.interpolation, var1.bounds, var3);
      float var4 = this.angleOfEdge(var2);
      this.cornerTransforms[var2].reset();
      this.getCoordinatesOfCorner(var2, var1.bounds, this.pointF);
      this.cornerTransforms[var2].setTranslate(this.pointF.x, this.pointF.y);
      this.cornerTransforms[var2].preRotate(var4);
   }

   private void setEdgePathAndTransform(int var1) {
      this.scratch[0] = this.cornerPaths[var1].getEndX();
      this.scratch[1] = this.cornerPaths[var1].getEndY();
      this.cornerTransforms[var1].mapPoints(this.scratch);
      float var2 = this.angleOfEdge(var1);
      this.edgeTransforms[var1].reset();
      Matrix var3 = this.edgeTransforms[var1];
      float[] var4 = this.scratch;
      var3.setTranslate(var4[0], var4[1]);
      this.edgeTransforms[var1].preRotate(var2);
   }

   public void calculatePath(ShapeAppearanceModel var1, float var2, RectF var3, Path var4) {
      this.calculatePath(var1, var2, var3, (ShapeAppearancePathProvider.PathListener)null, var4);
   }

   public void calculatePath(ShapeAppearanceModel var1, float var2, RectF var3, ShapeAppearancePathProvider.PathListener var4, Path var5) {
      var5.rewind();
      this.overlappedEdgePath.rewind();
      this.boundsPath.rewind();
      this.boundsPath.addRect(var3, Direction.CW);
      ShapeAppearancePathProvider.ShapeAppearancePathSpec var9 = new ShapeAppearancePathProvider.ShapeAppearancePathSpec(var1, var2, var3, var4, var5);
      byte var6 = 0;
      int var7 = 0;

      while(true) {
         int var8 = var6;
         if (var7 >= 4) {
            while(var8 < 4) {
               this.appendCornerPath(var9, var8);
               this.appendEdgePath(var9, var8);
               ++var8;
            }

            var5.close();
            this.overlappedEdgePath.close();
            if (VERSION.SDK_INT >= 19 && !this.overlappedEdgePath.isEmpty()) {
               var5.op(this.overlappedEdgePath, Op.UNION);
            }

            return;
         }

         this.setCornerPathAndTransform(var9, var7);
         this.setEdgePathAndTransform(var7);
         ++var7;
      }
   }

   void setEdgeIntersectionCheckEnable(boolean var1) {
      this.edgeIntersectionCheckEnabled = var1;
   }

   public interface PathListener {
      void onCornerPathCreated(ShapePath var1, Matrix var2, int var3);

      void onEdgePathCreated(ShapePath var1, Matrix var2, int var3);
   }

   static final class ShapeAppearancePathSpec {
      public final RectF bounds;
      public final float interpolation;
      public final Path path;
      public final ShapeAppearancePathProvider.PathListener pathListener;
      public final ShapeAppearanceModel shapeAppearanceModel;

      ShapeAppearancePathSpec(ShapeAppearanceModel var1, float var2, RectF var3, ShapeAppearancePathProvider.PathListener var4, Path var5) {
         this.pathListener = var4;
         this.shapeAppearanceModel = var1;
         this.interpolation = var2;
         this.bounds = var3;
         this.path = var5;
      }
   }
}

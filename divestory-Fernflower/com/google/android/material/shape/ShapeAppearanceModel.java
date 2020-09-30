package com.google.android.material.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import com.google.android.material.R;

public class ShapeAppearanceModel {
   public static final CornerSize PILL = new RelativeCornerSize(0.5F);
   EdgeTreatment bottomEdge;
   CornerTreatment bottomLeftCorner;
   CornerSize bottomLeftCornerSize;
   CornerTreatment bottomRightCorner;
   CornerSize bottomRightCornerSize;
   EdgeTreatment leftEdge;
   EdgeTreatment rightEdge;
   EdgeTreatment topEdge;
   CornerTreatment topLeftCorner;
   CornerSize topLeftCornerSize;
   CornerTreatment topRightCorner;
   CornerSize topRightCornerSize;

   public ShapeAppearanceModel() {
      this.topLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
      this.topRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
      this.bottomRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
      this.bottomLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
      this.topLeftCornerSize = new AbsoluteCornerSize(0.0F);
      this.topRightCornerSize = new AbsoluteCornerSize(0.0F);
      this.bottomRightCornerSize = new AbsoluteCornerSize(0.0F);
      this.bottomLeftCornerSize = new AbsoluteCornerSize(0.0F);
      this.topEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
      this.rightEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
      this.bottomEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
      this.leftEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
   }

   private ShapeAppearanceModel(ShapeAppearanceModel.Builder var1) {
      this.topLeftCorner = var1.topLeftCorner;
      this.topRightCorner = var1.topRightCorner;
      this.bottomRightCorner = var1.bottomRightCorner;
      this.bottomLeftCorner = var1.bottomLeftCorner;
      this.topLeftCornerSize = var1.topLeftCornerSize;
      this.topRightCornerSize = var1.topRightCornerSize;
      this.bottomRightCornerSize = var1.bottomRightCornerSize;
      this.bottomLeftCornerSize = var1.bottomLeftCornerSize;
      this.topEdge = var1.topEdge;
      this.rightEdge = var1.rightEdge;
      this.bottomEdge = var1.bottomEdge;
      this.leftEdge = var1.leftEdge;
   }

   // $FF: synthetic method
   ShapeAppearanceModel(ShapeAppearanceModel.Builder var1, Object var2) {
      this(var1);
   }

   public static ShapeAppearanceModel.Builder builder() {
      return new ShapeAppearanceModel.Builder();
   }

   public static ShapeAppearanceModel.Builder builder(Context var0, int var1, int var2) {
      return builder(var0, var1, var2, 0);
   }

   private static ShapeAppearanceModel.Builder builder(Context var0, int var1, int var2, int var3) {
      return builder(var0, var1, var2, new AbsoluteCornerSize((float)var3));
   }

   private static ShapeAppearanceModel.Builder builder(Context var0, int var1, int var2, CornerSize var3) {
      Object var4 = var0;
      int var5 = var1;
      if (var2 != 0) {
         var4 = new ContextThemeWrapper(var0, var1);
         var5 = var2;
      }

      TypedArray var12 = ((Context)var4).obtainStyledAttributes(var5, R.styleable.ShapeAppearance);

      ShapeAppearanceModel.Builder var13;
      try {
         int var6 = var12.getInt(R.styleable.ShapeAppearance_cornerFamily, 0);
         var1 = var12.getInt(R.styleable.ShapeAppearance_cornerFamilyTopLeft, var6);
         var2 = var12.getInt(R.styleable.ShapeAppearance_cornerFamilyTopRight, var6);
         var5 = var12.getInt(R.styleable.ShapeAppearance_cornerFamilyBottomRight, var6);
         var6 = var12.getInt(R.styleable.ShapeAppearance_cornerFamilyBottomLeft, var6);
         CornerSize var7 = getCornerSize(var12, R.styleable.ShapeAppearance_cornerSize, var3);
         CornerSize var14 = getCornerSize(var12, R.styleable.ShapeAppearance_cornerSizeTopLeft, var7);
         CornerSize var8 = getCornerSize(var12, R.styleable.ShapeAppearance_cornerSizeTopRight, var7);
         var3 = getCornerSize(var12, R.styleable.ShapeAppearance_cornerSizeBottomRight, var7);
         CornerSize var9 = getCornerSize(var12, R.styleable.ShapeAppearance_cornerSizeBottomLeft, var7);
         ShapeAppearanceModel.Builder var15 = new ShapeAppearanceModel.Builder();
         var13 = var15.setTopLeftCorner(var1, var14).setTopRightCorner(var2, var8).setBottomRightCorner(var5, var3).setBottomLeftCorner(var6, var9);
      } finally {
         var12.recycle();
      }

      return var13;
   }

   public static ShapeAppearanceModel.Builder builder(Context var0, AttributeSet var1, int var2, int var3) {
      return builder(var0, var1, var2, var3, 0);
   }

   public static ShapeAppearanceModel.Builder builder(Context var0, AttributeSet var1, int var2, int var3, int var4) {
      return builder(var0, var1, var2, var3, new AbsoluteCornerSize((float)var4));
   }

   public static ShapeAppearanceModel.Builder builder(Context var0, AttributeSet var1, int var2, int var3, CornerSize var4) {
      TypedArray var5 = var0.obtainStyledAttributes(var1, R.styleable.MaterialShape, var2, var3);
      var3 = var5.getResourceId(R.styleable.MaterialShape_shapeAppearance, 0);
      var2 = var5.getResourceId(R.styleable.MaterialShape_shapeAppearanceOverlay, 0);
      var5.recycle();
      return builder(var0, var3, var2, var4);
   }

   private static CornerSize getCornerSize(TypedArray var0, int var1, CornerSize var2) {
      TypedValue var3 = var0.peekValue(var1);
      if (var3 == null) {
         return var2;
      } else if (var3.type == 5) {
         return new AbsoluteCornerSize((float)TypedValue.complexToDimensionPixelSize(var3.data, var0.getResources().getDisplayMetrics()));
      } else {
         return (CornerSize)(var3.type == 6 ? new RelativeCornerSize(var3.getFraction(1.0F, 1.0F)) : var2);
      }
   }

   public EdgeTreatment getBottomEdge() {
      return this.bottomEdge;
   }

   public CornerTreatment getBottomLeftCorner() {
      return this.bottomLeftCorner;
   }

   public CornerSize getBottomLeftCornerSize() {
      return this.bottomLeftCornerSize;
   }

   public CornerTreatment getBottomRightCorner() {
      return this.bottomRightCorner;
   }

   public CornerSize getBottomRightCornerSize() {
      return this.bottomRightCornerSize;
   }

   public EdgeTreatment getLeftEdge() {
      return this.leftEdge;
   }

   public EdgeTreatment getRightEdge() {
      return this.rightEdge;
   }

   public EdgeTreatment getTopEdge() {
      return this.topEdge;
   }

   public CornerTreatment getTopLeftCorner() {
      return this.topLeftCorner;
   }

   public CornerSize getTopLeftCornerSize() {
      return this.topLeftCornerSize;
   }

   public CornerTreatment getTopRightCorner() {
      return this.topRightCorner;
   }

   public CornerSize getTopRightCornerSize() {
      return this.topRightCornerSize;
   }

   public boolean isRoundRect(RectF var1) {
      boolean var2 = this.leftEdge.getClass().equals(EdgeTreatment.class);
      boolean var3 = true;
      boolean var4;
      if (var2 && this.rightEdge.getClass().equals(EdgeTreatment.class) && this.topEdge.getClass().equals(EdgeTreatment.class) && this.bottomEdge.getClass().equals(EdgeTreatment.class)) {
         var4 = true;
      } else {
         var4 = false;
      }

      float var5 = this.topLeftCornerSize.getCornerSize(var1);
      boolean var6;
      if (this.topRightCornerSize.getCornerSize(var1) == var5 && this.bottomLeftCornerSize.getCornerSize(var1) == var5 && this.bottomRightCornerSize.getCornerSize(var1) == var5) {
         var6 = true;
      } else {
         var6 = false;
      }

      boolean var7;
      if (this.topRightCorner instanceof RoundedCornerTreatment && this.topLeftCorner instanceof RoundedCornerTreatment && this.bottomRightCorner instanceof RoundedCornerTreatment && this.bottomLeftCorner instanceof RoundedCornerTreatment) {
         var7 = true;
      } else {
         var7 = false;
      }

      if (!var4 || !var6 || !var7) {
         var3 = false;
      }

      return var3;
   }

   public ShapeAppearanceModel.Builder toBuilder() {
      return new ShapeAppearanceModel.Builder(this);
   }

   public ShapeAppearanceModel withCornerSize(float var1) {
      return this.toBuilder().setAllCornerSizes(var1).build();
   }

   public ShapeAppearanceModel withCornerSize(CornerSize var1) {
      return this.toBuilder().setAllCornerSizes(var1).build();
   }

   public ShapeAppearanceModel withTransformedCornerSizes(ShapeAppearanceModel.CornerSizeUnaryOperator var1) {
      return this.toBuilder().setTopLeftCornerSize(var1.apply(this.getTopLeftCornerSize())).setTopRightCornerSize(var1.apply(this.getTopRightCornerSize())).setBottomLeftCornerSize(var1.apply(this.getBottomLeftCornerSize())).setBottomRightCornerSize(var1.apply(this.getBottomRightCornerSize())).build();
   }

   public static final class Builder {
      private EdgeTreatment bottomEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
      private CornerTreatment bottomLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
      private CornerSize bottomLeftCornerSize = new AbsoluteCornerSize(0.0F);
      private CornerTreatment bottomRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
      private CornerSize bottomRightCornerSize = new AbsoluteCornerSize(0.0F);
      private EdgeTreatment leftEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
      private EdgeTreatment rightEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
      private EdgeTreatment topEdge = MaterialShapeUtils.createDefaultEdgeTreatment();
      private CornerTreatment topLeftCorner = MaterialShapeUtils.createDefaultCornerTreatment();
      private CornerSize topLeftCornerSize = new AbsoluteCornerSize(0.0F);
      private CornerTreatment topRightCorner = MaterialShapeUtils.createDefaultCornerTreatment();
      private CornerSize topRightCornerSize = new AbsoluteCornerSize(0.0F);

      public Builder() {
      }

      public Builder(ShapeAppearanceModel var1) {
         this.topLeftCorner = var1.topLeftCorner;
         this.topRightCorner = var1.topRightCorner;
         this.bottomRightCorner = var1.bottomRightCorner;
         this.bottomLeftCorner = var1.bottomLeftCorner;
         this.topLeftCornerSize = var1.topLeftCornerSize;
         this.topRightCornerSize = var1.topRightCornerSize;
         this.bottomRightCornerSize = var1.bottomRightCornerSize;
         this.bottomLeftCornerSize = var1.bottomLeftCornerSize;
         this.topEdge = var1.topEdge;
         this.rightEdge = var1.rightEdge;
         this.bottomEdge = var1.bottomEdge;
         this.leftEdge = var1.leftEdge;
      }

      private static float compatCornerTreatmentSize(CornerTreatment var0) {
         if (var0 instanceof RoundedCornerTreatment) {
            return ((RoundedCornerTreatment)var0).radius;
         } else {
            return var0 instanceof CutCornerTreatment ? ((CutCornerTreatment)var0).size : -1.0F;
         }
      }

      public ShapeAppearanceModel build() {
         return new ShapeAppearanceModel(this);
      }

      public ShapeAppearanceModel.Builder setAllCornerSizes(float var1) {
         return this.setTopLeftCornerSize(var1).setTopRightCornerSize(var1).setBottomRightCornerSize(var1).setBottomLeftCornerSize(var1);
      }

      public ShapeAppearanceModel.Builder setAllCornerSizes(CornerSize var1) {
         return this.setTopLeftCornerSize(var1).setTopRightCornerSize(var1).setBottomRightCornerSize(var1).setBottomLeftCornerSize(var1);
      }

      public ShapeAppearanceModel.Builder setAllCorners(int var1, float var2) {
         return this.setAllCorners(MaterialShapeUtils.createCornerTreatment(var1)).setAllCornerSizes(var2);
      }

      public ShapeAppearanceModel.Builder setAllCorners(CornerTreatment var1) {
         return this.setTopLeftCorner(var1).setTopRightCorner(var1).setBottomRightCorner(var1).setBottomLeftCorner(var1);
      }

      public ShapeAppearanceModel.Builder setAllEdges(EdgeTreatment var1) {
         return this.setLeftEdge(var1).setTopEdge(var1).setRightEdge(var1).setBottomEdge(var1);
      }

      public ShapeAppearanceModel.Builder setBottomEdge(EdgeTreatment var1) {
         this.bottomEdge = var1;
         return this;
      }

      public ShapeAppearanceModel.Builder setBottomLeftCorner(int var1, float var2) {
         return this.setBottomLeftCorner(MaterialShapeUtils.createCornerTreatment(var1)).setBottomLeftCornerSize(var2);
      }

      public ShapeAppearanceModel.Builder setBottomLeftCorner(int var1, CornerSize var2) {
         return this.setBottomLeftCorner(MaterialShapeUtils.createCornerTreatment(var1)).setBottomLeftCornerSize(var2);
      }

      public ShapeAppearanceModel.Builder setBottomLeftCorner(CornerTreatment var1) {
         this.bottomLeftCorner = var1;
         float var2 = compatCornerTreatmentSize(var1);
         if (var2 != -1.0F) {
            this.setBottomLeftCornerSize(var2);
         }

         return this;
      }

      public ShapeAppearanceModel.Builder setBottomLeftCornerSize(float var1) {
         this.bottomLeftCornerSize = new AbsoluteCornerSize(var1);
         return this;
      }

      public ShapeAppearanceModel.Builder setBottomLeftCornerSize(CornerSize var1) {
         this.bottomLeftCornerSize = var1;
         return this;
      }

      public ShapeAppearanceModel.Builder setBottomRightCorner(int var1, float var2) {
         return this.setBottomRightCorner(MaterialShapeUtils.createCornerTreatment(var1)).setBottomRightCornerSize(var2);
      }

      public ShapeAppearanceModel.Builder setBottomRightCorner(int var1, CornerSize var2) {
         return this.setBottomRightCorner(MaterialShapeUtils.createCornerTreatment(var1)).setBottomRightCornerSize(var2);
      }

      public ShapeAppearanceModel.Builder setBottomRightCorner(CornerTreatment var1) {
         this.bottomRightCorner = var1;
         float var2 = compatCornerTreatmentSize(var1);
         if (var2 != -1.0F) {
            this.setBottomRightCornerSize(var2);
         }

         return this;
      }

      public ShapeAppearanceModel.Builder setBottomRightCornerSize(float var1) {
         this.bottomRightCornerSize = new AbsoluteCornerSize(var1);
         return this;
      }

      public ShapeAppearanceModel.Builder setBottomRightCornerSize(CornerSize var1) {
         this.bottomRightCornerSize = var1;
         return this;
      }

      public ShapeAppearanceModel.Builder setLeftEdge(EdgeTreatment var1) {
         this.leftEdge = var1;
         return this;
      }

      public ShapeAppearanceModel.Builder setRightEdge(EdgeTreatment var1) {
         this.rightEdge = var1;
         return this;
      }

      public ShapeAppearanceModel.Builder setTopEdge(EdgeTreatment var1) {
         this.topEdge = var1;
         return this;
      }

      public ShapeAppearanceModel.Builder setTopLeftCorner(int var1, float var2) {
         return this.setTopLeftCorner(MaterialShapeUtils.createCornerTreatment(var1)).setTopLeftCornerSize(var2);
      }

      public ShapeAppearanceModel.Builder setTopLeftCorner(int var1, CornerSize var2) {
         return this.setTopLeftCorner(MaterialShapeUtils.createCornerTreatment(var1)).setTopLeftCornerSize(var2);
      }

      public ShapeAppearanceModel.Builder setTopLeftCorner(CornerTreatment var1) {
         this.topLeftCorner = var1;
         float var2 = compatCornerTreatmentSize(var1);
         if (var2 != -1.0F) {
            this.setTopLeftCornerSize(var2);
         }

         return this;
      }

      public ShapeAppearanceModel.Builder setTopLeftCornerSize(float var1) {
         this.topLeftCornerSize = new AbsoluteCornerSize(var1);
         return this;
      }

      public ShapeAppearanceModel.Builder setTopLeftCornerSize(CornerSize var1) {
         this.topLeftCornerSize = var1;
         return this;
      }

      public ShapeAppearanceModel.Builder setTopRightCorner(int var1, float var2) {
         return this.setTopRightCorner(MaterialShapeUtils.createCornerTreatment(var1)).setTopRightCornerSize(var2);
      }

      public ShapeAppearanceModel.Builder setTopRightCorner(int var1, CornerSize var2) {
         return this.setTopRightCorner(MaterialShapeUtils.createCornerTreatment(var1)).setTopRightCornerSize(var2);
      }

      public ShapeAppearanceModel.Builder setTopRightCorner(CornerTreatment var1) {
         this.topRightCorner = var1;
         float var2 = compatCornerTreatmentSize(var1);
         if (var2 != -1.0F) {
            this.setTopRightCornerSize(var2);
         }

         return this;
      }

      public ShapeAppearanceModel.Builder setTopRightCornerSize(float var1) {
         this.topRightCornerSize = new AbsoluteCornerSize(var1);
         return this;
      }

      public ShapeAppearanceModel.Builder setTopRightCornerSize(CornerSize var1) {
         this.topRightCornerSize = var1;
         return this;
      }
   }

   public interface CornerSizeUnaryOperator {
      CornerSize apply(CornerSize var1);
   }
}

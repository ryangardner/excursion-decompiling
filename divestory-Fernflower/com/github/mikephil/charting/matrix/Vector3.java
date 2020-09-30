package com.github.mikephil.charting.matrix;

public final class Vector3 {
   public static final Vector3 UNIT_X = new Vector3(1.0F, 0.0F, 0.0F);
   public static final Vector3 UNIT_Y = new Vector3(0.0F, 1.0F, 0.0F);
   public static final Vector3 UNIT_Z = new Vector3(0.0F, 0.0F, 1.0F);
   public static final Vector3 ZERO = new Vector3(0.0F, 0.0F, 0.0F);
   public float x;
   public float y;
   public float z;

   public Vector3() {
   }

   public Vector3(float var1, float var2, float var3) {
      this.set(var1, var2, var3);
   }

   public Vector3(Vector3 var1) {
      this.set(var1);
   }

   public Vector3(float[] var1) {
      this.set(var1[0], var1[1], var1[2]);
   }

   public final void add(float var1, float var2, float var3) {
      this.x += var1;
      this.y += var2;
      this.z += var3;
   }

   public final void add(Vector3 var1) {
      this.x += var1.x;
      this.y += var1.y;
      this.z += var1.z;
   }

   public final Vector3 cross(Vector3 var1) {
      float var2 = this.y;
      float var3 = var1.z;
      float var4 = this.z;
      float var5 = var1.y;
      float var6 = var1.x;
      float var7 = this.x;
      return new Vector3(var2 * var3 - var4 * var5, var4 * var6 - var3 * var7, var7 * var5 - var2 * var6);
   }

   public final float distance2(Vector3 var1) {
      float var2 = this.x - var1.x;
      float var3 = this.y - var1.y;
      float var4 = this.z - var1.z;
      return var2 * var2 + var3 * var3 + var4 * var4;
   }

   public final void divide(float var1) {
      if (var1 != 0.0F) {
         this.x /= var1;
         this.y /= var1;
         this.z /= var1;
      }

   }

   public final float dot(Vector3 var1) {
      return this.x * var1.x + this.y * var1.y + this.z * var1.z;
   }

   public final float length() {
      return (float)Math.sqrt((double)this.length2());
   }

   public final float length2() {
      float var1 = this.x;
      float var2 = this.y;
      float var3 = this.z;
      return var1 * var1 + var2 * var2 + var3 * var3;
   }

   public final void multiply(float var1) {
      this.x *= var1;
      this.y *= var1;
      this.z *= var1;
   }

   public final void multiply(Vector3 var1) {
      this.x *= var1.x;
      this.y *= var1.y;
      this.z *= var1.z;
   }

   public final float normalize() {
      float var1 = this.length();
      if (var1 != 0.0F) {
         this.x /= var1;
         this.y /= var1;
         this.z /= var1;
      }

      return var1;
   }

   public final boolean pointsInSameDirection(Vector3 var1) {
      boolean var2;
      if (this.dot(var1) > 0.0F) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public final void set(float var1, float var2, float var3) {
      this.x = var1;
      this.y = var2;
      this.z = var3;
   }

   public final void set(Vector3 var1) {
      this.x = var1.x;
      this.y = var1.y;
      this.z = var1.z;
   }

   public final void subtract(Vector3 var1) {
      this.x -= var1.x;
      this.y -= var1.y;
      this.z -= var1.z;
   }

   public final void subtractMultiple(Vector3 var1, float var2) {
      this.x -= var1.x * var2;
      this.y -= var1.y * var2;
      this.z -= var1.z * var2;
   }

   public final void zero() {
      this.set(0.0F, 0.0F, 0.0F);
   }
}

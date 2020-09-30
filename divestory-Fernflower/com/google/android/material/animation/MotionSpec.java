package com.google.android.material.animation;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.Property;
import androidx.collection.SimpleArrayMap;
import java.util.List;

public class MotionSpec {
   private static final String TAG = "MotionSpec";
   private final SimpleArrayMap<String, PropertyValuesHolder[]> propertyValues = new SimpleArrayMap();
   private final SimpleArrayMap<String, MotionTiming> timings = new SimpleArrayMap();

   private static void addInfoFromAnimator(MotionSpec var0, Animator var1) {
      if (var1 instanceof ObjectAnimator) {
         ObjectAnimator var3 = (ObjectAnimator)var1;
         var0.setPropertyValues(var3.getPropertyName(), var3.getValues());
         var0.setTiming(var3.getPropertyName(), MotionTiming.createFromAnimator(var3));
      } else {
         StringBuilder var2 = new StringBuilder();
         var2.append("Animator must be an ObjectAnimator: ");
         var2.append(var1);
         throw new IllegalArgumentException(var2.toString());
      }
   }

   private PropertyValuesHolder[] clonePropertyValuesHolder(PropertyValuesHolder[] var1) {
      PropertyValuesHolder[] var2 = new PropertyValuesHolder[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = var1[var3].clone();
      }

      return var2;
   }

   public static MotionSpec createFromAttribute(Context var0, TypedArray var1, int var2) {
      if (var1.hasValue(var2)) {
         var2 = var1.getResourceId(var2, 0);
         if (var2 != 0) {
            return createFromResource(var0, var2);
         }
      }

      return null;
   }

   public static MotionSpec createFromResource(Context param0, int param1) {
      // $FF: Couldn't be decompiled
   }

   private static MotionSpec createSpecFromAnimators(List<Animator> var0) {
      MotionSpec var1 = new MotionSpec();
      int var2 = var0.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         addInfoFromAnimator(var1, (Animator)var0.get(var3));
      }

      return var1;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof MotionSpec)) {
         return false;
      } else {
         MotionSpec var2 = (MotionSpec)var1;
         return this.timings.equals(var2.timings);
      }
   }

   public <T> ObjectAnimator getAnimator(String var1, T var2, Property<T, ?> var3) {
      ObjectAnimator var4 = ObjectAnimator.ofPropertyValuesHolder(var2, this.getPropertyValues(var1));
      var4.setProperty(var3);
      this.getTiming(var1).apply(var4);
      return var4;
   }

   public PropertyValuesHolder[] getPropertyValues(String var1) {
      if (this.hasPropertyValues(var1)) {
         return this.clonePropertyValuesHolder((PropertyValuesHolder[])this.propertyValues.get(var1));
      } else {
         throw new IllegalArgumentException();
      }
   }

   public MotionTiming getTiming(String var1) {
      if (this.hasTiming(var1)) {
         return (MotionTiming)this.timings.get(var1);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public long getTotalDuration() {
      int var1 = this.timings.size();
      long var2 = 0L;

      for(int var4 = 0; var4 < var1; ++var4) {
         MotionTiming var5 = (MotionTiming)this.timings.valueAt(var4);
         var2 = Math.max(var2, var5.getDelay() + var5.getDuration());
      }

      return var2;
   }

   public boolean hasPropertyValues(String var1) {
      boolean var2;
      if (this.propertyValues.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean hasTiming(String var1) {
      boolean var2;
      if (this.timings.get(var1) != null) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public int hashCode() {
      return this.timings.hashCode();
   }

   public void setPropertyValues(String var1, PropertyValuesHolder[] var2) {
      this.propertyValues.put(var1, var2);
   }

   public void setTiming(String var1, MotionTiming var2) {
      this.timings.put(var1, var2);
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append('\n');
      var1.append(this.getClass().getName());
      var1.append('{');
      var1.append(Integer.toHexString(System.identityHashCode(this)));
      var1.append(" timings: ");
      var1.append(this.timings);
      var1.append("}\n");
      return var1.toString();
   }
}

package com.google.common.util.concurrent;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicDouble extends Number implements Serializable {
   private static final long serialVersionUID = 0L;
   private transient AtomicLong value;

   public AtomicDouble() {
      this(0.0D);
   }

   public AtomicDouble(double var1) {
      this.value = new AtomicLong(Double.doubleToRawLongBits(var1));
   }

   private void readObject(ObjectInputStream var1) throws IOException, ClassNotFoundException {
      var1.defaultReadObject();
      this.value = new AtomicLong();
      this.set(var1.readDouble());
   }

   private void writeObject(ObjectOutputStream var1) throws IOException {
      var1.defaultWriteObject();
      var1.writeDouble(this.get());
   }

   public final double addAndGet(double var1) {
      long var3;
      double var5;
      long var7;
      do {
         var3 = this.value.get();
         var5 = Double.longBitsToDouble(var3) + var1;
         var7 = Double.doubleToRawLongBits(var5);
      } while(!this.value.compareAndSet(var3, var7));

      return var5;
   }

   public final boolean compareAndSet(double var1, double var3) {
      return this.value.compareAndSet(Double.doubleToRawLongBits(var1), Double.doubleToRawLongBits(var3));
   }

   public double doubleValue() {
      return this.get();
   }

   public float floatValue() {
      return (float)this.get();
   }

   public final double get() {
      return Double.longBitsToDouble(this.value.get());
   }

   public final double getAndAdd(double var1) {
      long var3;
      double var5;
      long var7;
      do {
         var3 = this.value.get();
         var5 = Double.longBitsToDouble(var3);
         var7 = Double.doubleToRawLongBits(var5 + var1);
      } while(!this.value.compareAndSet(var3, var7));

      return var5;
   }

   public final double getAndSet(double var1) {
      long var3 = Double.doubleToRawLongBits(var1);
      return Double.longBitsToDouble(this.value.getAndSet(var3));
   }

   public int intValue() {
      return (int)this.get();
   }

   public final void lazySet(double var1) {
      long var3 = Double.doubleToRawLongBits(var1);
      this.value.lazySet(var3);
   }

   public long longValue() {
      return (long)this.get();
   }

   public final void set(double var1) {
      long var3 = Double.doubleToRawLongBits(var1);
      this.value.set(var3);
   }

   public String toString() {
      return Double.toString(this.get());
   }

   public final boolean weakCompareAndSet(double var1, double var3) {
      return this.value.weakCompareAndSet(Double.doubleToRawLongBits(var1), Double.doubleToRawLongBits(var3));
   }
}

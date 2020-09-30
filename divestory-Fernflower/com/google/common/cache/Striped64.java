package com.google.common.cache;

import java.lang.reflect.Field;
import java.security.AccessController;
import java.security.PrivilegedActionException;
import java.security.PrivilegedExceptionAction;
import java.util.Random;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import sun.misc.Unsafe;

abstract class Striped64 extends Number {
   static final int NCPU = Runtime.getRuntime().availableProcessors();
   private static final Unsafe UNSAFE;
   private static final long baseOffset;
   private static final long busyOffset;
   static final Random rng = new Random();
   static final ThreadLocal<int[]> threadHashCode = new ThreadLocal();
   transient volatile long base;
   transient volatile int busy;
   @NullableDecl
   transient volatile Striped64.Cell[] cells;

   static {
      try {
         Unsafe var0 = getUnsafe();
         UNSAFE = var0;
         baseOffset = var0.objectFieldOffset(Striped64.class.getDeclaredField("base"));
         busyOffset = UNSAFE.objectFieldOffset(Striped64.class.getDeclaredField("busy"));
      } catch (Exception var1) {
         throw new Error(var1);
      }
   }

   private static Unsafe getUnsafe() {
      Unsafe var3;
      try {
         var3 = Unsafe.getUnsafe();
         return var3;
      } catch (SecurityException var2) {
         try {
            PrivilegedExceptionAction var0 = new PrivilegedExceptionAction<Unsafe>() {
               public Unsafe run() throws Exception {
                  Field[] var1 = Unsafe.class.getDeclaredFields();
                  int var2 = var1.length;

                  for(int var3 = 0; var3 < var2; ++var3) {
                     Field var4 = var1[var3];
                     var4.setAccessible(true);
                     Object var5 = var4.get((Object)null);
                     if (Unsafe.class.isInstance(var5)) {
                        return (Unsafe)Unsafe.class.cast(var5);
                     }
                  }

                  throw new NoSuchFieldError("the Unsafe");
               }
            };
            var3 = (Unsafe)AccessController.doPrivileged(var0);
            return var3;
         } catch (PrivilegedActionException var1) {
            throw new RuntimeException("Could not initialize intrinsics", var1.getCause());
         }
      }
   }

   final boolean casBase(long var1, long var3) {
      return UNSAFE.compareAndSwapLong(this, baseOffset, var1, var3);
   }

   final boolean casBusy() {
      return UNSAFE.compareAndSwapInt(this, busyOffset, 0, 1);
   }

   abstract long fn(long var1, long var3);

   final void internalReset(long var1) {
      Striped64.Cell[] var3 = this.cells;
      this.base = var1;
      if (var3 != null) {
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Striped64.Cell var6 = var3[var5];
            if (var6 != null) {
               var6.value = var1;
            }
         }
      }

   }

   final void retryUpdate(long var1, int[] var3, boolean var4) {
      int var6;
      int var7;
      if (var3 == null) {
         ThreadLocal var5 = threadHashCode;
         var3 = new int[1];
         var5.set(var3);
         var6 = rng.nextInt();
         var7 = var6;
         if (var6 == 0) {
            var7 = 1;
         }

         var3[0] = var7;
      } else {
         var7 = var3[0];
      }

      int var8 = var7;
      boolean var60 = false;
      boolean var9 = var4;

      while(true) {
         Striped64.Cell[] var57 = this.cells;
         Striped64.Cell var11;
         long var12;
         Throwable var10000;
         boolean var10001;
         Throwable var56;
         boolean var59;
         if (var57 != null) {
            int var10 = var57.length;
            if (var10 > 0) {
               label1010: {
                  var11 = var57[var10 - 1 & var8];
                  Striped64.Cell[] var61;
                  if (var11 == null) {
                     if (this.busy == 0) {
                        Striped64.Cell var58 = new Striped64.Cell(var1);
                        if (this.busy == 0 && this.casBusy()) {
                           label979: {
                              label978: {
                                 label1038: {
                                    try {
                                       var61 = this.cells;
                                    } catch (Throwable var51) {
                                       var10000 = var51;
                                       var10001 = false;
                                       break label1038;
                                    }

                                    if (var61 == null) {
                                       break label978;
                                    }

                                    try {
                                       var6 = var61.length;
                                    } catch (Throwable var50) {
                                       var10000 = var50;
                                       var10001 = false;
                                       break label1038;
                                    }

                                    if (var6 > 0) {
                                       var6 = var6 - 1 & var8;
                                       if (var61[var6] == null) {
                                          var61[var6] = var58;
                                          var59 = true;
                                          break label979;
                                       }
                                    }
                                    break label978;
                                 }

                                 var56 = var10000;
                                 this.busy = 0;
                                 throw var56;
                              }

                              var59 = false;
                           }

                           this.busy = 0;
                           if (var59) {
                              break;
                           }
                           continue;
                        }
                     }
                  } else {
                     if (!var9) {
                        var4 = true;
                        var59 = var60;
                        break label1010;
                     }

                     var12 = var11.value;
                     if (var11.cas(var12, this.fn(var12, var1))) {
                        break;
                     }

                     if (var10 < NCPU && this.cells == var57) {
                        if (!var60) {
                           var59 = true;
                           var4 = var9;
                        } else {
                           var4 = var9;
                           var59 = var60;
                           if (this.busy == 0) {
                              var4 = var9;
                              var59 = var60;
                              if (this.casBusy()) {
                                 label998: {
                                    label1040: {
                                       try {
                                          if (this.cells != var57) {
                                             break label998;
                                          }

                                          var61 = new Striped64.Cell[var10 << 1];
                                       } catch (Throwable var53) {
                                          var10000 = var53;
                                          var10001 = false;
                                          break label1040;
                                       }

                                       for(var7 = 0; var7 < var10; ++var7) {
                                          var61[var7] = var57[var7];
                                       }

                                       label993:
                                       try {
                                          this.cells = var61;
                                          break label998;
                                       } catch (Throwable var52) {
                                          var10000 = var52;
                                          var10001 = false;
                                          break label993;
                                       }
                                    }

                                    var56 = var10000;
                                    this.busy = 0;
                                    throw var56;
                                 }

                                 this.busy = 0;
                                 var60 = false;
                                 continue;
                              }
                           }
                        }
                        break label1010;
                     }
                  }

                  var59 = false;
                  var4 = var9;
               }

               var7 = var8 ^ var8 << 13;
               var7 ^= var7 >>> 17;
               var8 = var7 ^ var7 << 5;
               var3[0] = var8;
               var9 = var4;
               var60 = var59;
               continue;
            }
         }

         if (this.busy == 0 && this.cells == var57 && this.casBusy()) {
            label1026: {
               label1025: {
                  label1041: {
                     try {
                        if (this.cells != var57) {
                           break label1041;
                        }

                        var57 = new Striped64.Cell[2];
                        var11 = new Striped64.Cell(var1);
                     } catch (Throwable var55) {
                        var10000 = var55;
                        var10001 = false;
                        break label1025;
                     }

                     var57[var8 & 1] = var11;

                     try {
                        this.cells = var57;
                     } catch (Throwable var54) {
                        var10000 = var54;
                        var10001 = false;
                        break label1025;
                     }

                     var59 = true;
                     break label1026;
                  }

                  var59 = false;
                  break label1026;
               }

               var56 = var10000;
               this.busy = 0;
               throw var56;
            }

            this.busy = 0;
            if (var59) {
               break;
            }
         } else {
            var12 = this.base;
            if (this.casBase(var12, this.fn(var12, var1))) {
               break;
            }
         }
      }

   }

   static final class Cell {
      private static final Unsafe UNSAFE;
      private static final long valueOffset;
      volatile long p0;
      volatile long p1;
      volatile long p2;
      volatile long p3;
      volatile long p4;
      volatile long p5;
      volatile long p6;
      volatile long q0;
      volatile long q1;
      volatile long q2;
      volatile long q3;
      volatile long q4;
      volatile long q5;
      volatile long q6;
      volatile long value;

      static {
         try {
            Unsafe var0 = Striped64.getUnsafe();
            UNSAFE = var0;
            valueOffset = var0.objectFieldOffset(Striped64.Cell.class.getDeclaredField("value"));
         } catch (Exception var1) {
            throw new Error(var1);
         }
      }

      Cell(long var1) {
         this.value = var1;
      }

      final boolean cas(long var1, long var3) {
         return UNSAFE.compareAndSwapLong(this, valueOffset, var1, var3);
      }
   }
}

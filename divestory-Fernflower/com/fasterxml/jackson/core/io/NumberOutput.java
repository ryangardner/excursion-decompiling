package com.fasterxml.jackson.core.io;

public final class NumberOutput {
   private static int BILLION;
   private static long BILLION_L;
   private static long MAX_INT_AS_LONG;
   private static int MILLION;
   private static long MIN_INT_AS_LONG;
   static final String SMALLEST_INT = String.valueOf(Integer.MIN_VALUE);
   static final String SMALLEST_LONG = String.valueOf(Long.MIN_VALUE);
   private static final int[] TRIPLET_TO_CHARS = new int[1000];
   private static final String[] sSmallIntStrs;
   private static final String[] sSmallIntStrs2;

   static {
      int var0 = 0;

      for(int var1 = 0; var0 < 10; ++var0) {
         for(int var2 = 0; var2 < 10; ++var2) {
            for(int var3 = 0; var3 < 10; ++var1) {
               TRIPLET_TO_CHARS[var1] = var0 + 48 << 16 | var2 + 48 << 8 | var3 + 48;
               ++var3;
            }
         }
      }

      sSmallIntStrs = new String[]{"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
      sSmallIntStrs2 = new String[]{"-1", "-2", "-3", "-4", "-5", "-6", "-7", "-8", "-9", "-10"};
   }

   private static int _full3(int var0, byte[] var1, int var2) {
      var0 = TRIPLET_TO_CHARS[var0];
      int var3 = var2 + 1;
      var1[var2] = (byte)((byte)(var0 >> 16));
      var2 = var3 + 1;
      var1[var3] = (byte)((byte)(var0 >> 8));
      var1[var2] = (byte)((byte)var0);
      return var2 + 1;
   }

   private static int _full3(int var0, char[] var1, int var2) {
      int var3 = TRIPLET_TO_CHARS[var0];
      var0 = var2 + 1;
      var1[var2] = (char)((char)(var3 >> 16));
      var2 = var0 + 1;
      var1[var0] = (char)((char)(var3 >> 8 & 127));
      var1[var2] = (char)((char)(var3 & 127));
      return var2 + 1;
   }

   private static int _leading3(int var0, byte[] var1, int var2) {
      int var3 = TRIPLET_TO_CHARS[var0];
      int var4 = var2;
      if (var0 > 9) {
         var4 = var2;
         if (var0 > 99) {
            var1[var2] = (byte)((byte)(var3 >> 16));
            var4 = var2 + 1;
         }

         var1[var4] = (byte)((byte)(var3 >> 8));
         ++var4;
      }

      var1[var4] = (byte)((byte)var3);
      return var4 + 1;
   }

   private static int _leading3(int var0, char[] var1, int var2) {
      int var3 = TRIPLET_TO_CHARS[var0];
      int var4 = var2;
      if (var0 > 9) {
         var4 = var2;
         if (var0 > 99) {
            var1[var2] = (char)((char)(var3 >> 16));
            var4 = var2 + 1;
         }

         var1[var4] = (char)((char)(var3 >> 8 & 127));
         ++var4;
      }

      var1[var4] = (char)((char)(var3 & 127));
      return var4 + 1;
   }

   private static int _outputFullBillion(int var0, byte[] var1, int var2) {
      int var3 = var0 / 1000;
      int var4 = var3 / 1000;
      int[] var5 = TRIPLET_TO_CHARS;
      int var6 = var5[var4];
      int var7 = var2 + 1;
      var1[var2] = (byte)((byte)(var6 >> 16));
      int var8 = var7 + 1;
      var1[var7] = (byte)((byte)(var6 >> 8));
      var2 = var8 + 1;
      var1[var8] = (byte)((byte)var6);
      var6 = var5[var3 - var4 * 1000];
      var8 = var2 + 1;
      var1[var2] = (byte)((byte)(var6 >> 16));
      var4 = var8 + 1;
      var1[var8] = (byte)((byte)(var6 >> 8));
      var2 = var4 + 1;
      var1[var4] = (byte)((byte)var6);
      var3 = var5[var0 - var3 * 1000];
      var0 = var2 + 1;
      var1[var2] = (byte)((byte)(var3 >> 16));
      var2 = var0 + 1;
      var1[var0] = (byte)((byte)(var3 >> 8));
      var1[var2] = (byte)((byte)var3);
      return var2 + 1;
   }

   private static int _outputFullBillion(int var0, char[] var1, int var2) {
      int var3 = var0 / 1000;
      int var4 = var3 / 1000;
      int[] var5 = TRIPLET_TO_CHARS;
      int var6 = var5[var4];
      int var7 = var2 + 1;
      var1[var2] = (char)((char)(var6 >> 16));
      int var8 = var7 + 1;
      var1[var7] = (char)((char)(var6 >> 8 & 127));
      var2 = var8 + 1;
      var1[var8] = (char)((char)(var6 & 127));
      var6 = var5[var3 - var4 * 1000];
      var7 = var2 + 1;
      var1[var2] = (char)((char)(var6 >> 16));
      var4 = var7 + 1;
      var1[var7] = (char)((char)(var6 >> 8 & 127));
      var2 = var4 + 1;
      var1[var4] = (char)((char)(var6 & 127));
      var3 = var5[var0 - var3 * 1000];
      var0 = var2 + 1;
      var1[var2] = (char)((char)(var3 >> 16));
      var2 = var0 + 1;
      var1[var0] = (char)((char)(var3 >> 8 & 127));
      var1[var2] = (char)((char)(var3 & 127));
      return var2 + 1;
   }

   private static int _outputSmallestI(byte[] var0, int var1) {
      int var2 = SMALLEST_INT.length();

      for(int var3 = 0; var3 < var2; ++var1) {
         var0[var1] = (byte)((byte)SMALLEST_INT.charAt(var3));
         ++var3;
      }

      return var1;
   }

   private static int _outputSmallestI(char[] var0, int var1) {
      int var2 = SMALLEST_INT.length();
      SMALLEST_INT.getChars(0, var2, var0, var1);
      return var1 + var2;
   }

   private static int _outputSmallestL(byte[] var0, int var1) {
      int var2 = SMALLEST_LONG.length();

      for(int var3 = 0; var3 < var2; ++var1) {
         var0[var1] = (byte)((byte)SMALLEST_LONG.charAt(var3));
         ++var3;
      }

      return var1;
   }

   private static int _outputSmallestL(char[] var0, int var1) {
      int var2 = SMALLEST_LONG.length();
      SMALLEST_LONG.getChars(0, var2, var0, var1);
      return var1 + var2;
   }

   private static int _outputUptoBillion(int var0, byte[] var1, int var2) {
      int var3;
      if (var0 < MILLION) {
         if (var0 < 1000) {
            return _leading3(var0, var1, var2);
         } else {
            var3 = var0 / 1000;
            return _outputUptoMillion(var1, var2, var3, var0 - var3 * 1000);
         }
      } else {
         var3 = var0 / 1000;
         int var4 = var3 / 1000;
         int var5 = _leading3(var4, var1, var2);
         int[] var6 = TRIPLET_TO_CHARS;
         var4 = var6[var3 - var4 * 1000];
         var2 = var5 + 1;
         var1[var5] = (byte)((byte)(var4 >> 16));
         var5 = var2 + 1;
         var1[var2] = (byte)((byte)(var4 >> 8));
         var2 = var5 + 1;
         var1[var5] = (byte)((byte)var4);
         var0 = var6[var0 - var3 * 1000];
         var3 = var2 + 1;
         var1[var2] = (byte)((byte)(var0 >> 16));
         var2 = var3 + 1;
         var1[var3] = (byte)((byte)(var0 >> 8));
         var1[var2] = (byte)((byte)var0);
         return var2 + 1;
      }
   }

   private static int _outputUptoBillion(int var0, char[] var1, int var2) {
      int var3;
      if (var0 < MILLION) {
         if (var0 < 1000) {
            return _leading3(var0, var1, var2);
         } else {
            var3 = var0 / 1000;
            return _outputUptoMillion(var1, var2, var3, var0 - var3 * 1000);
         }
      } else {
         var3 = var0 / 1000;
         int var4 = var3 / 1000;
         int var5 = _leading3(var4, var1, var2);
         int[] var6 = TRIPLET_TO_CHARS;
         var2 = var6[var3 - var4 * 1000];
         var4 = var5 + 1;
         var1[var5] = (char)((char)(var2 >> 16));
         int var7 = var4 + 1;
         var1[var4] = (char)((char)(var2 >> 8 & 127));
         var5 = var7 + 1;
         var1[var7] = (char)((char)(var2 & 127));
         var2 = var6[var0 - var3 * 1000];
         var0 = var5 + 1;
         var1[var5] = (char)((char)(var2 >> 16));
         var3 = var0 + 1;
         var1[var0] = (char)((char)(var2 >> 8 & 127));
         var1[var3] = (char)((char)(var2 & 127));
         return var3 + 1;
      }
   }

   private static int _outputUptoMillion(byte[] var0, int var1, int var2, int var3) {
      int var4 = TRIPLET_TO_CHARS[var2];
      int var5 = var1;
      if (var2 > 9) {
         var5 = var1;
         if (var2 > 99) {
            var0[var1] = (byte)((byte)(var4 >> 16));
            var5 = var1 + 1;
         }

         var0[var5] = (byte)((byte)(var4 >> 8));
         ++var5;
      }

      var1 = var5 + 1;
      var0[var5] = (byte)((byte)var4);
      var3 = TRIPLET_TO_CHARS[var3];
      var2 = var1 + 1;
      var0[var1] = (byte)((byte)(var3 >> 16));
      var1 = var2 + 1;
      var0[var2] = (byte)((byte)(var3 >> 8));
      var0[var1] = (byte)((byte)var3);
      return var1 + 1;
   }

   private static int _outputUptoMillion(char[] var0, int var1, int var2, int var3) {
      int var4 = TRIPLET_TO_CHARS[var2];
      int var5 = var1;
      if (var2 > 9) {
         var5 = var1;
         if (var2 > 99) {
            var0[var1] = (char)((char)(var4 >> 16));
            var5 = var1 + 1;
         }

         var0[var5] = (char)((char)(var4 >> 8 & 127));
         ++var5;
      }

      var1 = var5 + 1;
      var0[var5] = (char)((char)(var4 & 127));
      var3 = TRIPLET_TO_CHARS[var3];
      var2 = var1 + 1;
      var0[var1] = (char)((char)(var3 >> 16));
      var1 = var2 + 1;
      var0[var2] = (char)((char)(var3 >> 8 & 127));
      var0[var1] = (char)((char)(var3 & 127));
      return var1 + 1;
   }

   public static boolean notFinite(double var0) {
      boolean var2;
      if (!Double.isNaN(var0) && !Double.isInfinite(var0)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }

   public static boolean notFinite(float var0) {
      boolean var1;
      if (!Float.isNaN(var0) && !Float.isInfinite(var0)) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   public static int outputInt(int var0, byte[] var1, int var2) {
      int var3 = var0;
      int var4 = var2;
      if (var0 < 0) {
         if (var0 == Integer.MIN_VALUE) {
            return _outputSmallestI(var1, var2);
         }

         var1[var2] = (byte)45;
         var3 = -var0;
         var4 = var2 + 1;
      }

      if (var3 < MILLION) {
         if (var3 < 1000) {
            if (var3 < 10) {
               var0 = var4 + 1;
               var1[var4] = (byte)((byte)(var3 + 48));
            } else {
               var0 = _leading3(var3, var1, var4);
            }
         } else {
            var0 = var3 / 1000;
            var0 = _full3(var3 - var0 * 1000, var1, _leading3(var0, var1, var4));
         }

         return var0;
      } else {
         var2 = BILLION;
         if (var3 >= var2) {
            var0 = var3 - var2;
            if (var0 >= var2) {
               var0 -= var2;
               var2 = var4 + 1;
               var1[var4] = (byte)50;
            } else {
               var2 = var4 + 1;
               var1[var4] = (byte)49;
            }

            return _outputFullBillion(var0, var1, var2);
         } else {
            var2 = var3 / 1000;
            var0 = var2 / 1000;
            return _full3(var3 - var2 * 1000, var1, _full3(var2 - var0 * 1000, var1, _leading3(var0, var1, var4)));
         }
      }
   }

   public static int outputInt(int var0, char[] var1, int var2) {
      int var3 = var0;
      int var4 = var2;
      if (var0 < 0) {
         if (var0 == Integer.MIN_VALUE) {
            return _outputSmallestI(var1, var2);
         }

         var1[var2] = (char)45;
         var3 = -var0;
         var4 = var2 + 1;
      }

      if (var3 < MILLION) {
         if (var3 < 1000) {
            if (var3 < 10) {
               var1[var4] = (char)((char)(var3 + 48));
               return var4 + 1;
            } else {
               return _leading3(var3, var1, var4);
            }
         } else {
            var0 = var3 / 1000;
            return _full3(var3 - var0 * 1000, var1, _leading3(var0, var1, var4));
         }
      } else {
         var2 = BILLION;
         if (var3 >= var2) {
            var0 = var3 - var2;
            if (var0 >= var2) {
               var0 -= var2;
               var2 = var4 + 1;
               var1[var4] = (char)50;
            } else {
               var2 = var4 + 1;
               var1[var4] = (char)49;
            }

            return _outputFullBillion(var0, var1, var2);
         } else {
            var0 = var3 / 1000;
            var2 = var0 / 1000;
            return _full3(var3 - var0 * 1000, var1, _full3(var0 - var2 * 1000, var1, _leading3(var2, var1, var4)));
         }
      }
   }

   public static int outputLong(long var0, byte[] var2, int var3) {
      long var4;
      int var6;
      if (var0 < 0L) {
         if (var0 > MIN_INT_AS_LONG) {
            return outputInt((int)var0, var2, var3);
         }

         if (var0 == Long.MIN_VALUE) {
            return _outputSmallestL(var2, var3);
         }

         var2[var3] = (byte)45;
         var4 = -var0;
         var6 = var3 + 1;
      } else {
         var4 = var0;
         var6 = var3;
         if (var0 <= MAX_INT_AS_LONG) {
            return outputInt((int)var0, var2, var3);
         }
      }

      var0 = BILLION_L;
      long var7 = var4 / var0;
      if (var7 < var0) {
         var3 = _outputUptoBillion((int)var7, var2, var6);
      } else {
         long var9 = var7 / var0;
         var3 = _leading3((int)var9, var2, var6);
         var3 = _outputFullBillion((int)(var7 - var0 * var9), var2, var3);
      }

      return _outputFullBillion((int)(var4 - var7 * var0), var2, var3);
   }

   public static int outputLong(long var0, char[] var2, int var3) {
      long var4;
      int var6;
      if (var0 < 0L) {
         if (var0 > MIN_INT_AS_LONG) {
            return outputInt((int)var0, var2, var3);
         }

         if (var0 == Long.MIN_VALUE) {
            return _outputSmallestL(var2, var3);
         }

         var2[var3] = (char)45;
         var4 = -var0;
         var6 = var3 + 1;
      } else {
         var4 = var0;
         var6 = var3;
         if (var0 <= MAX_INT_AS_LONG) {
            return outputInt((int)var0, var2, var3);
         }
      }

      long var7 = BILLION_L;
      var0 = var4 / var7;
      if (var0 < var7) {
         var3 = _outputUptoBillion((int)var0, var2, var6);
      } else {
         long var9 = var0 / var7;
         var3 = _leading3((int)var9, var2, var6);
         var3 = _outputFullBillion((int)(var0 - var7 * var9), var2, var3);
      }

      return _outputFullBillion((int)(var4 - var0 * var7), var2, var3);
   }

   public static String toString(double var0) {
      return Double.toString(var0);
   }

   public static String toString(float var0) {
      return Float.toString(var0);
   }

   public static String toString(int var0) {
      String[] var1 = sSmallIntStrs;
      if (var0 < var1.length) {
         if (var0 >= 0) {
            return var1[var0];
         }

         int var2 = -var0 - 1;
         var1 = sSmallIntStrs2;
         if (var2 < var1.length) {
            return var1[var2];
         }
      }

      return Integer.toString(var0);
   }

   public static String toString(long var0) {
      return var0 <= 2147483647L && var0 >= -2147483648L ? toString((int)var0) : Long.toString(var0);
   }
}

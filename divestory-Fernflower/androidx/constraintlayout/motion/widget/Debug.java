package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Debug {
   public static void dumpLayoutParams(LayoutParams var0, String var1) {
      StackTraceElement var2 = (new Throwable()).getStackTrace()[1];
      StringBuilder var3 = new StringBuilder();
      var3.append(".(");
      var3.append(var2.getFileName());
      var3.append(":");
      var3.append(var2.getLineNumber());
      var3.append(") ");
      var3.append(var1);
      var3.append("  ");
      var1 = var3.toString();
      PrintStream var10 = System.out;
      var3 = new StringBuilder();
      var3.append(" >>>>>>>>>>>>>>>>>>. dump ");
      var3.append(var1);
      var3.append("  ");
      var3.append(var0.getClass().getName());
      var10.println(var3.toString());
      Field[] var11 = var0.getClass().getFields();

      for(int var4 = 0; var4 < var11.length; ++var4) {
         Field var5 = var11[var4];

         try {
            Object var12 = var5.get(var0);
            String var13 = var5.getName();
            if (var13.contains("To") && !var12.toString().equals("-1")) {
               PrintStream var6 = System.out;
               StringBuilder var7 = new StringBuilder();
               var7.append(var1);
               var7.append("       ");
               var7.append(var13);
               var7.append(" ");
               var7.append(var12);
               var6.println(var7.toString());
            }
         } catch (IllegalAccessException var8) {
         }
      }

      var10 = System.out;
      StringBuilder var9 = new StringBuilder();
      var9.append(" <<<<<<<<<<<<<<<<< dump ");
      var9.append(var1);
      var10.println(var9.toString());
   }

   public static void dumpLayoutParams(ViewGroup var0, String var1) {
      StackTraceElement var2 = (new Throwable()).getStackTrace()[1];
      StringBuilder var3 = new StringBuilder();
      var3.append(".(");
      var3.append(var2.getFileName());
      var3.append(":");
      var3.append(var2.getLineNumber());
      var3.append(") ");
      var3.append(var1);
      var3.append("  ");
      String var14 = var3.toString();
      int var4 = var0.getChildCount();
      PrintStream var15 = System.out;
      StringBuilder var5 = new StringBuilder();
      var5.append(var1);
      var5.append(" children ");
      var5.append(var4);
      var15.println(var5.toString());

      for(int var6 = 0; var6 < var4; ++var6) {
         View var17 = var0.getChildAt(var6);
         var15 = System.out;
         StringBuilder var12 = new StringBuilder();
         var12.append(var14);
         var12.append("     ");
         var12.append(getName(var17));
         var15.println(var12.toString());
         LayoutParams var16 = var17.getLayoutParams();
         Field[] var13 = var16.getClass().getFields();

         for(int var7 = 0; var7 < var13.length; ++var7) {
            Field var8 = var13[var7];

            try {
               Object var9 = var8.get(var16);
               if (var8.getName().contains("To") && !var9.toString().equals("-1")) {
                  PrintStream var10 = System.out;
                  var5 = new StringBuilder();
                  var5.append(var14);
                  var5.append("       ");
                  var5.append(var8.getName());
                  var5.append(" ");
                  var5.append(var9);
                  var10.println(var5.toString());
               }
            } catch (IllegalAccessException var11) {
            }
         }
      }

   }

   public static void dumpPoc(Object var0) {
      StackTraceElement var1 = (new Throwable()).getStackTrace()[1];
      StringBuilder var2 = new StringBuilder();
      var2.append(".(");
      var2.append(var1.getFileName());
      var2.append(":");
      var2.append(var1.getLineNumber());
      var2.append(")");
      String var12 = var2.toString();
      Class var11 = var0.getClass();
      PrintStream var3 = System.out;
      StringBuilder var4 = new StringBuilder();
      var4.append(var12);
      var4.append("------------- ");
      var4.append(var11.getName());
      var4.append(" --------------------");
      var3.println(var4.toString());
      Field[] var13 = var11.getFields();

      for(int var5 = 0; var5 < var13.length; ++var5) {
         Field var14 = var13[var5];

         try {
            Object var6 = var14.get(var0);
            if (var14.getName().startsWith("layout_constraint") && (!(var6 instanceof Integer) || !var6.toString().equals("-1")) && (!(var6 instanceof Integer) || !var6.toString().equals("0")) && (!(var6 instanceof Float) || !var6.toString().equals("1.0")) && (!(var6 instanceof Float) || !var6.toString().equals("0.5"))) {
               PrintStream var7 = System.out;
               StringBuilder var8 = new StringBuilder();
               var8.append(var12);
               var8.append("    ");
               var8.append(var14.getName());
               var8.append(" ");
               var8.append(var6);
               var7.println(var8.toString());
            }
         } catch (IllegalAccessException var9) {
         }
      }

      var3 = System.out;
      StringBuilder var10 = new StringBuilder();
      var10.append(var12);
      var10.append("------------- ");
      var10.append(var11.getSimpleName());
      var10.append(" --------------------");
      var3.println(var10.toString());
   }

   public static String getActionType(MotionEvent var0) {
      int var1 = var0.getAction();
      Field[] var5 = MotionEvent.class.getFields();

      for(int var2 = 0; var2 < var5.length; ++var2) {
         Field var3 = var5[var2];

         try {
            if (Modifier.isStatic(var3.getModifiers()) && var3.getType().equals(Integer.TYPE) && var3.getInt((Object)null) == var1) {
               String var6 = var3.getName();
               return var6;
            }
         } catch (IllegalAccessException var4) {
         }
      }

      return "---";
   }

   public static String getCallFrom(int var0) {
      StackTraceElement var1 = (new Throwable()).getStackTrace()[var0 + 2];
      StringBuilder var2 = new StringBuilder();
      var2.append(".(");
      var2.append(var1.getFileName());
      var2.append(":");
      var2.append(var1.getLineNumber());
      var2.append(")");
      return var2.toString();
   }

   public static String getLoc() {
      StackTraceElement var0 = (new Throwable()).getStackTrace()[1];
      StringBuilder var1 = new StringBuilder();
      var1.append(".(");
      var1.append(var0.getFileName());
      var1.append(":");
      var1.append(var0.getLineNumber());
      var1.append(") ");
      var1.append(var0.getMethodName());
      var1.append("()");
      return var1.toString();
   }

   public static String getLocation() {
      StackTraceElement var0 = (new Throwable()).getStackTrace()[1];
      StringBuilder var1 = new StringBuilder();
      var1.append(".(");
      var1.append(var0.getFileName());
      var1.append(":");
      var1.append(var0.getLineNumber());
      var1.append(")");
      return var1.toString();
   }

   public static String getLocation2() {
      StackTraceElement var0 = (new Throwable()).getStackTrace()[2];
      StringBuilder var1 = new StringBuilder();
      var1.append(".(");
      var1.append(var0.getFileName());
      var1.append(":");
      var1.append(var0.getLineNumber());
      var1.append(")");
      return var1.toString();
   }

   public static String getName(Context var0, int var1) {
      if (var1 != -1) {
         try {
            return var0.getResources().getResourceEntryName(var1);
         } catch (Exception var2) {
            StringBuilder var3 = new StringBuilder();
            var3.append("?");
            var3.append(var1);
            return var3.toString();
         }
      } else {
         return "UNKNOWN";
      }
   }

   public static String getName(Context param0, int[] param1) {
      // $FF: Couldn't be decompiled
   }

   public static String getName(View var0) {
      try {
         String var2 = var0.getContext().getResources().getResourceEntryName(var0.getId());
         return var2;
      } catch (Exception var1) {
         return "UNKNOWN";
      }
   }

   public static String getState(MotionLayout var0, int var1) {
      return var1 == -1 ? "UNDEFINED" : var0.getContext().getResources().getResourceEntryName(var1);
   }

   public static void logStack(String var0, String var1, int var2) {
      StackTraceElement[] var3 = (new Throwable()).getStackTrace();
      int var4 = var3.length;
      byte var5 = 1;
      var4 = Math.min(var2, var4 - 1);
      String var6 = " ";

      for(var2 = var5; var2 <= var4; ++var2) {
         StackTraceElement var10000 = var3[var2];
         StringBuilder var7 = new StringBuilder();
         var7.append(".(");
         var7.append(var3[var2].getFileName());
         var7.append(":");
         var7.append(var3[var2].getLineNumber());
         var7.append(") ");
         var7.append(var3[var2].getMethodName());
         String var9 = var7.toString();
         StringBuilder var8 = new StringBuilder();
         var8.append(var6);
         var8.append(" ");
         var6 = var8.toString();
         var8 = new StringBuilder();
         var8.append(var1);
         var8.append(var6);
         var8.append(var9);
         var8.append(var6);
         Log.v(var0, var8.toString());
      }

   }

   public static void printStack(String var0, int var1) {
      StackTraceElement[] var2 = (new Throwable()).getStackTrace();
      int var3 = var2.length;
      byte var4 = 1;
      var3 = Math.min(var1, var3 - 1);
      String var5 = " ";

      for(var1 = var4; var1 <= var3; ++var1) {
         StackTraceElement var10000 = var2[var1];
         StringBuilder var6 = new StringBuilder();
         var6.append(".(");
         var6.append(var2[var1].getFileName());
         var6.append(":");
         var6.append(var2[var1].getLineNumber());
         var6.append(") ");
         String var9 = var6.toString();
         StringBuilder var7 = new StringBuilder();
         var7.append(var5);
         var7.append(" ");
         var5 = var7.toString();
         PrintStream var8 = System.out;
         var7 = new StringBuilder();
         var7.append(var0);
         var7.append(var5);
         var7.append(var9);
         var7.append(var5);
         var8.println(var7.toString());
      }

   }
}

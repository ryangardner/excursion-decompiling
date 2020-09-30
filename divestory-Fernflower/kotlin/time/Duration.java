package kotlin.time;

import java.util.concurrent.TimeUnit;
import kotlin.Metadata;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.functions.Function3;
import kotlin.jvm.functions.Function4;
import kotlin.jvm.functions.Function5;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.DoubleCompanionObject;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000^\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0005\n\u0002\u0010\b\n\u0002\b&\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0015\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\t\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u000e\n\u0002\b\u0012\b\u0087@\u0018\u0000 s2\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001sB\u0014\b\u0000\u0012\u0006\u0010\u0002\u001a\u00020\u0003ø\u0001\u0000¢\u0006\u0004\b\u0004\u0010\u0005J\u001b\u0010%\u001a\u00020\t2\u0006\u0010&\u001a\u00020\u0000H\u0096\u0002ø\u0001\u0000¢\u0006\u0004\b'\u0010(J\u001b\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010,J\u001b\u0010)\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\tH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b+\u0010-J\u001b\u0010)\u001a\u00020\u00032\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b.\u0010,J\u0013\u0010/\u001a\u0002002\b\u0010&\u001a\u0004\u0018\u000101HÖ\u0003J\t\u00102\u001a\u00020\tHÖ\u0001J\r\u00103\u001a\u000200¢\u0006\u0004\b4\u00105J\r\u00106\u001a\u000200¢\u0006\u0004\b7\u00105J\r\u00108\u001a\u000200¢\u0006\u0004\b9\u00105J\r\u0010:\u001a\u000200¢\u0006\u0004\b;\u00105J\u001b\u0010<\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b=\u0010,J\u001b\u0010>\u001a\u00020\u00002\u0006\u0010&\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\b?\u0010,J\u0017\u0010@\u001a\u00020\t2\u0006\u0010\u0002\u001a\u00020\u0003H\u0002¢\u0006\u0004\bA\u0010(J\u001b\u0010B\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\u0003H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bC\u0010,J\u001b\u0010B\u001a\u00020\u00002\u0006\u0010*\u001a\u00020\tH\u0086\u0002ø\u0001\u0000¢\u0006\u0004\bC\u0010-J\u008d\u0001\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2u\u0010F\u001aq\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(J\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0GH\u0086\b¢\u0006\u0004\bO\u0010PJx\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2`\u0010F\u001a\\\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(K\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0QH\u0086\b¢\u0006\u0004\bO\u0010RJc\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E2K\u0010F\u001aG\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(L\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0SH\u0086\b¢\u0006\u0004\bO\u0010TJN\u0010D\u001a\u0002HE\"\u0004\b\u0000\u0010E26\u0010F\u001a2\u0012\u0013\u0012\u00110V¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(M\u0012\u0013\u0012\u00110\t¢\u0006\f\bH\u0012\b\bI\u0012\u0004\b\b(N\u0012\u0004\u0012\u0002HE0UH\u0086\b¢\u0006\u0004\bO\u0010WJ\u0019\u0010X\u001a\u00020\u00032\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\b\\\u0010]J\u0019\u0010^\u001a\u00020\t2\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\b_\u0010`J\r\u0010a\u001a\u00020b¢\u0006\u0004\bc\u0010dJ\u0019\u0010e\u001a\u00020V2\n\u0010Y\u001a\u00060Zj\u0002`[¢\u0006\u0004\bf\u0010gJ\r\u0010h\u001a\u00020V¢\u0006\u0004\bi\u0010jJ\r\u0010k\u001a\u00020V¢\u0006\u0004\bl\u0010jJ\u000f\u0010m\u001a\u00020bH\u0016¢\u0006\u0004\bn\u0010dJ#\u0010m\u001a\u00020b2\n\u0010Y\u001a\u00060Zj\u0002`[2\b\b\u0002\u0010o\u001a\u00020\t¢\u0006\u0004\bn\u0010pJ\u0013\u0010q\u001a\u00020\u0000H\u0086\u0002ø\u0001\u0000¢\u0006\u0004\br\u0010\u0005R\u0014\u0010\u0006\u001a\u00020\u00008Fø\u0001\u0000¢\u0006\u0006\u001a\u0004\b\u0007\u0010\u0005R\u001a\u0010\b\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\n\u0010\u000b\u001a\u0004\b\f\u0010\rR\u0011\u0010\u000e\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u000f\u0010\u0005R\u0011\u0010\u0010\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0011\u0010\u0005R\u0011\u0010\u0012\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0013\u0010\u0005R\u0011\u0010\u0014\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0015\u0010\u0005R\u0011\u0010\u0016\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0017\u0010\u0005R\u0011\u0010\u0018\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u0019\u0010\u0005R\u0011\u0010\u001a\u001a\u00020\u00038F¢\u0006\u0006\u001a\u0004\b\u001b\u0010\u0005R\u001a\u0010\u001c\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b\u001d\u0010\u000b\u001a\u0004\b\u001e\u0010\rR\u001a\u0010\u001f\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b \u0010\u000b\u001a\u0004\b!\u0010\rR\u001a\u0010\"\u001a\u00020\t8@X\u0081\u0004¢\u0006\f\u0012\u0004\b#\u0010\u000b\u001a\u0004\b$\u0010\rR\u000e\u0010\u0002\u001a\u00020\u0003X\u0080\u0004¢\u0006\u0002\n\u0000ø\u0001\u0000\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006t"},
   d2 = {"Lkotlin/time/Duration;", "", "value", "", "constructor-impl", "(D)D", "absoluteValue", "getAbsoluteValue-impl", "hoursComponent", "", "hoursComponent$annotations", "()V", "getHoursComponent-impl", "(D)I", "inDays", "getInDays-impl", "inHours", "getInHours-impl", "inMicroseconds", "getInMicroseconds-impl", "inMilliseconds", "getInMilliseconds-impl", "inMinutes", "getInMinutes-impl", "inNanoseconds", "getInNanoseconds-impl", "inSeconds", "getInSeconds-impl", "minutesComponent", "minutesComponent$annotations", "getMinutesComponent-impl", "nanosecondsComponent", "nanosecondsComponent$annotations", "getNanosecondsComponent-impl", "secondsComponent", "secondsComponent$annotations", "getSecondsComponent-impl", "compareTo", "other", "compareTo-LRDsOJo", "(DD)I", "div", "scale", "div-impl", "(DD)D", "(DI)D", "div-LRDsOJo", "equals", "", "", "hashCode", "isFinite", "isFinite-impl", "(D)Z", "isInfinite", "isInfinite-impl", "isNegative", "isNegative-impl", "isPositive", "isPositive-impl", "minus", "minus-LRDsOJo", "plus", "plus-LRDsOJo", "precision", "precision-impl", "times", "times-impl", "toComponents", "T", "action", "Lkotlin/Function5;", "Lkotlin/ParameterName;", "name", "days", "hours", "minutes", "seconds", "nanoseconds", "toComponents-impl", "(DLkotlin/jvm/functions/Function5;)Ljava/lang/Object;", "Lkotlin/Function4;", "(DLkotlin/jvm/functions/Function4;)Ljava/lang/Object;", "Lkotlin/Function3;", "(DLkotlin/jvm/functions/Function3;)Ljava/lang/Object;", "Lkotlin/Function2;", "", "(DLkotlin/jvm/functions/Function2;)Ljava/lang/Object;", "toDouble", "unit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "toDouble-impl", "(DLjava/util/concurrent/TimeUnit;)D", "toInt", "toInt-impl", "(DLjava/util/concurrent/TimeUnit;)I", "toIsoString", "", "toIsoString-impl", "(D)Ljava/lang/String;", "toLong", "toLong-impl", "(DLjava/util/concurrent/TimeUnit;)J", "toLongMilliseconds", "toLongMilliseconds-impl", "(D)J", "toLongNanoseconds", "toLongNanoseconds-impl", "toString", "toString-impl", "decimals", "(DLjava/util/concurrent/TimeUnit;I)Ljava/lang/String;", "unaryMinus", "unaryMinus-impl", "Companion", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public final class Duration implements Comparable<Duration> {
   public static final Duration.Companion Companion = new Duration.Companion((DefaultConstructorMarker)null);
   private static final double INFINITE;
   private static final double ZERO = constructor-impl(0.0D);
   private final double value;

   static {
      INFINITE = constructor-impl(DoubleCompanionObject.INSTANCE.getPOSITIVE_INFINITY());
   }

   // $FF: synthetic method
   private Duration(double var1) {
      this.value = var1;
   }

   // $FF: synthetic method
   public static final Duration box_impl/* $FF was: box-impl*/(double var0) {
      return new Duration(var0);
   }

   public static int compareTo_LRDsOJo/* $FF was: compareTo-LRDsOJo*/(double var0, double var2) {
      return Double.compare(var0, var2);
   }

   public static double constructor_impl/* $FF was: constructor-impl*/(double var0) {
      return var0;
   }

   public static final double div_LRDsOJo/* $FF was: div-LRDsOJo*/(double var0, double var2) {
      return var0 / var2;
   }

   public static final double div_impl/* $FF was: div-impl*/(double var0, double var2) {
      return constructor-impl(var0 / var2);
   }

   public static final double div_impl/* $FF was: div-impl*/(double var0, int var2) {
      return constructor-impl(var0 / (double)var2);
   }

   public static boolean equals_impl/* $FF was: equals-impl*/(double var0, Object var2) {
      return var2 instanceof Duration && Double.compare(var0, ((Duration)var2).unbox-impl()) == 0;
   }

   public static final boolean equals_impl0/* $FF was: equals-impl0*/(double var0, double var2) {
      boolean var4;
      if (Double.compare(var0, var2) == 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      return var4;
   }

   public static final double getAbsoluteValue_impl/* $FF was: getAbsoluteValue-impl*/(double var0) {
      double var2 = var0;
      if (isNegative-impl(var0)) {
         var2 = unaryMinus-impl(var0);
      }

      return var2;
   }

   public static final int getHoursComponent_impl/* $FF was: getHoursComponent-impl*/(double var0) {
      return (int)(getInHours-impl(var0) % (double)24);
   }

   public static final double getInDays_impl/* $FF was: getInDays-impl*/(double var0) {
      return toDouble-impl(var0, TimeUnit.DAYS);
   }

   public static final double getInHours_impl/* $FF was: getInHours-impl*/(double var0) {
      return toDouble-impl(var0, TimeUnit.HOURS);
   }

   public static final double getInMicroseconds_impl/* $FF was: getInMicroseconds-impl*/(double var0) {
      return toDouble-impl(var0, TimeUnit.MICROSECONDS);
   }

   public static final double getInMilliseconds_impl/* $FF was: getInMilliseconds-impl*/(double var0) {
      return toDouble-impl(var0, TimeUnit.MILLISECONDS);
   }

   public static final double getInMinutes_impl/* $FF was: getInMinutes-impl*/(double var0) {
      return toDouble-impl(var0, TimeUnit.MINUTES);
   }

   public static final double getInNanoseconds_impl/* $FF was: getInNanoseconds-impl*/(double var0) {
      return toDouble-impl(var0, TimeUnit.NANOSECONDS);
   }

   public static final double getInSeconds_impl/* $FF was: getInSeconds-impl*/(double var0) {
      return toDouble-impl(var0, TimeUnit.SECONDS);
   }

   public static final int getMinutesComponent_impl/* $FF was: getMinutesComponent-impl*/(double var0) {
      return (int)(getInMinutes-impl(var0) % (double)60);
   }

   public static final int getNanosecondsComponent_impl/* $FF was: getNanosecondsComponent-impl*/(double var0) {
      return (int)(getInNanoseconds-impl(var0) % 1.0E9D);
   }

   public static final int getSecondsComponent_impl/* $FF was: getSecondsComponent-impl*/(double var0) {
      return (int)(getInSeconds-impl(var0) % (double)60);
   }

   public static int hashCode_impl/* $FF was: hashCode-impl*/(double var0) {
      long var2 = Double.doubleToLongBits(var0);
      return (int)(var2 ^ var2 >>> 32);
   }

   // $FF: synthetic method
   public static void hoursComponent$annotations() {
   }

   public static final boolean isFinite_impl/* $FF was: isFinite-impl*/(double var0) {
      boolean var2;
      if (!Double.isInfinite(var0) && !Double.isNaN(var0)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public static final boolean isInfinite_impl/* $FF was: isInfinite-impl*/(double var0) {
      return Double.isInfinite(var0);
   }

   public static final boolean isNegative_impl/* $FF was: isNegative-impl*/(double var0) {
      boolean var2 = false;
      if (var0 < (double)0) {
         var2 = true;
      }

      return var2;
   }

   public static final boolean isPositive_impl/* $FF was: isPositive-impl*/(double var0) {
      boolean var2 = false;
      if (var0 > (double)0) {
         var2 = true;
      }

      return var2;
   }

   public static final double minus_LRDsOJo/* $FF was: minus-LRDsOJo*/(double var0, double var2) {
      return constructor-impl(var0 - var2);
   }

   // $FF: synthetic method
   public static void minutesComponent$annotations() {
   }

   // $FF: synthetic method
   public static void nanosecondsComponent$annotations() {
   }

   public static final double plus_LRDsOJo/* $FF was: plus-LRDsOJo*/(double var0, double var2) {
      return constructor-impl(var0 + var2);
   }

   private static final int precision_impl/* $FF was: precision-impl*/(double var0, double var2) {
      byte var4 = 1;
      if (var2 < (double)1) {
         var4 = 3;
      } else if (var2 < (double)10) {
         var4 = 2;
      } else if (var2 >= (double)100) {
         var4 = 0;
      }

      return var4;
   }

   // $FF: synthetic method
   public static void secondsComponent$annotations() {
   }

   public static final double times_impl/* $FF was: times-impl*/(double var0, double var2) {
      return constructor-impl(var0 * var2);
   }

   public static final double times_impl/* $FF was: times-impl*/(double var0, int var2) {
      return constructor-impl(var0 * (double)var2);
   }

   public static final <T> T toComponents_impl/* $FF was: toComponents-impl*/(double var0, Function2<? super Long, ? super Integer, ? extends T> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "action");
      return var2.invoke((long)getInSeconds-impl(var0), getNanosecondsComponent-impl(var0));
   }

   public static final <T> T toComponents_impl/* $FF was: toComponents-impl*/(double var0, Function3<? super Integer, ? super Integer, ? super Integer, ? extends T> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "action");
      return var2.invoke((int)getInMinutes-impl(var0), getSecondsComponent-impl(var0), getNanosecondsComponent-impl(var0));
   }

   public static final <T> T toComponents_impl/* $FF was: toComponents-impl*/(double var0, Function4<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "action");
      return var2.invoke((int)getInHours-impl(var0), getMinutesComponent-impl(var0), getSecondsComponent-impl(var0), getNanosecondsComponent-impl(var0));
   }

   public static final <T> T toComponents_impl/* $FF was: toComponents-impl*/(double var0, Function5<? super Integer, ? super Integer, ? super Integer, ? super Integer, ? super Integer, ? extends T> var2) {
      Intrinsics.checkParameterIsNotNull(var2, "action");
      return var2.invoke((int)getInDays-impl(var0), getHoursComponent-impl(var0), getMinutesComponent-impl(var0), getSecondsComponent-impl(var0), getNanosecondsComponent-impl(var0));
   }

   public static final double toDouble_impl/* $FF was: toDouble-impl*/(double var0, TimeUnit var2) {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      return DurationUnitKt.convertDurationUnit(var0, DurationKt.access$getStorageUnit$p(), var2);
   }

   public static final int toInt_impl/* $FF was: toInt-impl*/(double var0, TimeUnit var2) {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      return (int)toDouble-impl(var0, var2);
   }

   public static final String toIsoString_impl/* $FF was: toIsoString-impl*/(double var0) {
      StringBuilder var2 = new StringBuilder();
      if (isNegative-impl(var0)) {
         var2.append('-');
      }

      var2.append("PT");
      var0 = getAbsoluteValue-impl(var0);
      int var3 = (int)getInHours-impl(var0);
      int var4 = getMinutesComponent-impl(var0);
      int var5 = getSecondsComponent-impl(var0);
      int var6 = getNanosecondsComponent-impl(var0);
      boolean var7 = true;
      boolean var8;
      if (var3 != 0) {
         var8 = true;
      } else {
         var8 = false;
      }

      boolean var9;
      if (var5 == 0 && var6 == 0) {
         var9 = false;
      } else {
         var9 = true;
      }

      boolean var10 = var7;
      if (var4 == 0) {
         if (var9 && var8) {
            var10 = var7;
         } else {
            var10 = false;
         }
      }

      if (var8) {
         var2.append(var3);
         var2.append('H');
      }

      if (var10) {
         var2.append(var4);
         var2.append('M');
      }

      if (var9 || !var8 && !var10) {
         var2.append(var5);
         if (var6 != 0) {
            var2.append('.');
            String var11 = StringsKt.padStart(String.valueOf(var6), 9, '0');
            if (var6 % 1000000 == 0) {
               var2.append((CharSequence)var11, 0, 3);
               Intrinsics.checkExpressionValueIsNotNull(var2, "this.append(value, startIndex, endIndex)");
            } else if (var6 % 1000 == 0) {
               var2.append((CharSequence)var11, 0, 6);
               Intrinsics.checkExpressionValueIsNotNull(var2, "this.append(value, startIndex, endIndex)");
            } else {
               var2.append(var11);
            }
         }

         var2.append('S');
      }

      String var12 = var2.toString();
      Intrinsics.checkExpressionValueIsNotNull(var12, "StringBuilder().apply(builderAction).toString()");
      return var12;
   }

   public static final long toLong_impl/* $FF was: toLong-impl*/(double var0, TimeUnit var2) {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      return (long)toDouble-impl(var0, var2);
   }

   public static final long toLongMilliseconds_impl/* $FF was: toLongMilliseconds-impl*/(double var0) {
      return toLong-impl(var0, TimeUnit.MILLISECONDS);
   }

   public static final long toLongNanoseconds_impl/* $FF was: toLongNanoseconds-impl*/(double var0) {
      return toLong-impl(var0, TimeUnit.NANOSECONDS);
   }

   public static String toString_impl/* $FF was: toString-impl*/(double var0) {
      String var2;
      if (isInfinite-impl(var0)) {
         var2 = String.valueOf(var0);
      } else if (var0 == 0.0D) {
         var2 = "0s";
      } else {
         double var3;
         boolean var5;
         byte var6;
         TimeUnit var9;
         label62: {
            var3 = getInNanoseconds-impl(getAbsoluteValue-impl(var0));
            var5 = false;
            if (var3 < 1.0E-6D) {
               var9 = TimeUnit.SECONDS;
            } else {
               label61: {
                  if (var3 < (double)1) {
                     var9 = TimeUnit.NANOSECONDS;
                     var6 = 7;
                     break label62;
                  }

                  if (var3 < 1000.0D) {
                     var9 = TimeUnit.NANOSECONDS;
                  } else if (var3 < 1000000.0D) {
                     var9 = TimeUnit.MICROSECONDS;
                  } else if (var3 < 1.0E9D) {
                     var9 = TimeUnit.MILLISECONDS;
                  } else if (var3 < 1.0E12D) {
                     var9 = TimeUnit.SECONDS;
                  } else if (var3 < 6.0E13D) {
                     var9 = TimeUnit.MINUTES;
                  } else if (var3 < 3.6E15D) {
                     var9 = TimeUnit.HOURS;
                  } else {
                     if (var3 >= 8.64E20D) {
                        var9 = TimeUnit.DAYS;
                        break label61;
                     }

                     var9 = TimeUnit.DAYS;
                  }

                  var6 = 0;
                  break label62;
               }
            }

            var6 = 0;
            var5 = true;
         }

         var3 = toDouble-impl(var0, var9);
         StringBuilder var7 = new StringBuilder();
         String var8;
         if (var5) {
            var8 = FormatToDecimalsKt.formatScientific(var3);
         } else if (var6 > 0) {
            var8 = FormatToDecimalsKt.formatUpToDecimals(var3, var6);
         } else {
            var8 = FormatToDecimalsKt.formatToExactDecimals(var3, precision-impl(var0, Math.abs(var3)));
         }

         var7.append(var8);
         var7.append(DurationUnitKt.shortName(var9));
         var2 = var7.toString();
      }

      return var2;
   }

   public static final String toString_impl/* $FF was: toString-impl*/(double var0, TimeUnit var2, int var3) {
      Intrinsics.checkParameterIsNotNull(var2, "unit");
      boolean var4;
      if (var3 >= 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      if (var4) {
         if (isInfinite-impl(var0)) {
            return String.valueOf(var0);
         } else {
            var0 = toDouble-impl(var0, var2);
            StringBuilder var5 = new StringBuilder();
            String var6;
            if (Math.abs(var0) < 1.0E14D) {
               var6 = FormatToDecimalsKt.formatToExactDecimals(var0, RangesKt.coerceAtMost(var3, 12));
            } else {
               var6 = FormatToDecimalsKt.formatScientific(var0);
            }

            var5.append(var6);
            var5.append(DurationUnitKt.shortName(var2));
            return var5.toString();
         }
      } else {
         StringBuilder var7 = new StringBuilder();
         var7.append("decimals must be not negative, but was ");
         var7.append(var3);
         throw (Throwable)(new IllegalArgumentException(var7.toString().toString()));
      }
   }

   // $FF: synthetic method
   public static String toString_impl$default/* $FF was: toString-impl$default*/(double var0, TimeUnit var2, int var3, int var4, Object var5) {
      if ((var4 & 2) != 0) {
         var3 = 0;
      }

      return toString-impl(var0, var2, var3);
   }

   public static final double unaryMinus_impl/* $FF was: unaryMinus-impl*/(double var0) {
      return constructor-impl(-var0);
   }

   public int compareTo_LRDsOJo/* $FF was: compareTo-LRDsOJo*/(double var1) {
      return compareTo-LRDsOJo(this.value, var1);
   }

   public boolean equals(Object var1) {
      return equals-impl(this.value, var1);
   }

   public int hashCode() {
      return hashCode-impl(this.value);
   }

   public String toString() {
      return toString-impl(this.value);
   }

   // $FF: synthetic method
   public final double unbox_impl/* $FF was: unbox-impl*/() {
      return this.value;
   }

   @Metadata(
      bv = {1, 0, 3},
      d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0010\u0006\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002¢\u0006\u0002\u0010\u0002J&\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\n\u0010\r\u001a\u00060\u000ej\u0002`\u000f2\n\u0010\u0010\u001a\u00060\u000ej\u0002`\u000fR\u0016\u0010\u0003\u001a\u00020\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\u0005\u0010\u0006R\u0016\u0010\b\u001a\u00020\u0004ø\u0001\u0000¢\u0006\n\n\u0002\u0010\u0007\u001a\u0004\b\t\u0010\u0006\u0082\u0002\u0004\n\u0002\b\u0019¨\u0006\u0011"},
      d2 = {"Lkotlin/time/Duration$Companion;", "", "()V", "INFINITE", "Lkotlin/time/Duration;", "getINFINITE", "()D", "D", "ZERO", "getZERO", "convert", "", "value", "sourceUnit", "Ljava/util/concurrent/TimeUnit;", "Lkotlin/time/DurationUnit;", "targetUnit", "kotlin-stdlib"},
      k = 1,
      mv = {1, 1, 16}
   )
   public static final class Companion {
      private Companion() {
      }

      // $FF: synthetic method
      public Companion(DefaultConstructorMarker var1) {
         this();
      }

      public final double convert(double var1, TimeUnit var3, TimeUnit var4) {
         Intrinsics.checkParameterIsNotNull(var3, "sourceUnit");
         Intrinsics.checkParameterIsNotNull(var4, "targetUnit");
         return DurationUnitKt.convertDurationUnit(var1, var3, var4);
      }

      public final double getINFINITE() {
         return Duration.INFINITE;
      }

      public final double getZERO() {
         return Duration.ZERO;
      }
   }
}

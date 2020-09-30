package okhttp3.internal.http;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000+\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\n\u001a\u000e\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u0005H\u0000\u001a\f\u0010\u000e\u001a\u00020\u0005*\u00020\rH\u0000\"\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0003\"\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001X\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u0006\"\u000e\u0010\u0007\u001a\u00020\bX\u0080T¢\u0006\u0002\n\u0000\"\u0010\u0010\t\u001a\u00020\nX\u0082\u0004¢\u0006\u0004\n\u0002\u0010\u000b¨\u0006\u000f"},
   d2 = {"BROWSER_COMPATIBLE_DATE_FORMATS", "", "Ljava/text/DateFormat;", "[Ljava/text/DateFormat;", "BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS", "", "[Ljava/lang/String;", "MAX_DATE", "", "STANDARD_DATE_FORMAT", "okhttp3/internal/http/DatesKt$STANDARD_DATE_FORMAT$1", "Lokhttp3/internal/http/DatesKt$STANDARD_DATE_FORMAT$1;", "toHttpDateOrNull", "Ljava/util/Date;", "toHttpDateString", "okhttp"},
   k = 2,
   mv = {1, 1, 16}
)
public final class DatesKt {
   private static final DateFormat[] BROWSER_COMPATIBLE_DATE_FORMATS;
   private static final String[] BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
   public static final long MAX_DATE = 253402300799999L;
   private static final <undefinedtype> STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>() {
      protected DateFormat initialValue() {
         SimpleDateFormat var1 = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
         var1.setLenient(false);
         var1.setTimeZone(Util.UTC);
         return (DateFormat)var1;
      }
   };

   static {
      String[] var0 = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
      BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS = var0;
      BROWSER_COMPATIBLE_DATE_FORMATS = new DateFormat[var0.length];
   }

   public static final Date toHttpDateOrNull(String var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toHttpDateOrNull");
      boolean var1;
      if (((CharSequence)var0).length() == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      if (var1) {
         return null;
      } else {
         ParsePosition var2 = new ParsePosition(0);
         Date var3 = ((DateFormat)STANDARD_DATE_FORMAT.get()).parse(var0, var2);
         if (var2.getIndex() == var0.length()) {
            return var3;
         } else {
            String[] var4 = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
            synchronized(var4){}

            Throwable var10000;
            label383: {
               int var5;
               boolean var10001;
               try {
                  var5 = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length;
               } catch (Throwable var37) {
                  var10000 = var37;
                  var10001 = false;
                  break label383;
               }

               for(int var40 = 0; var40 < var5; ++var40) {
                  DateFormat var6;
                  try {
                     var6 = BROWSER_COMPATIBLE_DATE_FORMATS[var40];
                  } catch (Throwable var36) {
                     var10000 = var36;
                     var10001 = false;
                     break label383;
                  }

                  DateFormat var41 = var6;
                  if (var6 == null) {
                     try {
                        SimpleDateFormat var42 = new SimpleDateFormat(BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS[var40], Locale.US);
                        var42.setTimeZone(Util.UTC);
                        var41 = (DateFormat)var42;
                        BROWSER_COMPATIBLE_DATE_FORMATS[var40] = var41;
                     } catch (Throwable var35) {
                        var10000 = var35;
                        var10001 = false;
                        break label383;
                     }
                  }

                  int var7;
                  try {
                     var2.setIndex(0);
                     var3 = var41.parse(var0, var2);
                     var7 = var2.getIndex();
                  } catch (Throwable var34) {
                     var10000 = var34;
                     var10001 = false;
                     break label383;
                  }

                  if (var7 != 0) {
                     return var3;
                  }
               }

               label358:
               try {
                  Unit var39 = Unit.INSTANCE;
                  return null;
               } catch (Throwable var33) {
                  var10000 = var33;
                  var10001 = false;
                  break label358;
               }
            }

            Throwable var38 = var10000;
            throw var38;
         }
      }
   }

   public static final String toHttpDateString(Date var0) {
      Intrinsics.checkParameterIsNotNull(var0, "$this$toHttpDateString");
      String var1 = ((DateFormat)STANDARD_DATE_FORMAT.get()).format(var0);
      Intrinsics.checkExpressionValueIsNotNull(var1, "STANDARD_DATE_FORMAT.get().format(this)");
      return var1;
   }
}

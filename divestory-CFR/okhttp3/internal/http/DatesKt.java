/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  okhttp3.internal.http.DatesKt$STANDARD_DATE_FORMAT
 */
package okhttp3.internal.http;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import kotlin.Metadata;
import kotlin.Unit;
import kotlin.jvm.internal.Intrinsics;
import okhttp3.internal.Util;
import okhttp3.internal.http.DatesKt;

@Metadata(bv={1, 0, 3}, d1={"\u0000+\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002*\u0001\n\u001a\u000e\u0010\f\u001a\u0004\u0018\u00010\r*\u00020\u0005H\u0000\u001a\f\u0010\u000e\u001a\u00020\u0005*\u00020\rH\u0000\"\u0018\u0010\u0000\u001a\n\u0012\u0006\u0012\u0004\u0018\u00010\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0003\"\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00050\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0006\"\u000e\u0010\u0007\u001a\u00020\bXT\u00a2\u0006\u0002\n\u0000\"\u0010\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000b\u00a8\u0006\u000f"}, d2={"BROWSER_COMPATIBLE_DATE_FORMATS", "", "Ljava/text/DateFormat;", "[Ljava/text/DateFormat;", "BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS", "", "[Ljava/lang/String;", "MAX_DATE", "", "STANDARD_DATE_FORMAT", "okhttp3/internal/http/DatesKt$STANDARD_DATE_FORMAT$1", "Lokhttp3/internal/http/DatesKt$STANDARD_DATE_FORMAT$1;", "toHttpDateOrNull", "Ljava/util/Date;", "toHttpDateString", "okhttp"}, k=2, mv={1, 1, 16})
public final class DatesKt {
    private static final DateFormat[] BROWSER_COMPATIBLE_DATE_FORMATS;
    private static final String[] BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
    public static final long MAX_DATE = 253402300799999L;
    private static final STANDARD_DATE_FORMAT.1 STANDARD_DATE_FORMAT;

    static {
        STANDARD_DATE_FORMAT = new ThreadLocal<DateFormat>(){

            protected DateFormat initialValue() {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);
                simpleDateFormat.setLenient(false);
                simpleDateFormat.setTimeZone(Util.UTC);
                return simpleDateFormat;
            }
        };
        String[] arrstring = new String[]{"EEE, dd MMM yyyy HH:mm:ss zzz", "EEEE, dd-MMM-yy HH:mm:ss zzz", "EEE MMM d HH:mm:ss yyyy", "EEE, dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MMM-yyyy HH-mm-ss z", "EEE, dd MMM yy HH:mm:ss z", "EEE dd-MMM-yyyy HH:mm:ss z", "EEE dd MMM yyyy HH:mm:ss z", "EEE dd-MMM-yyyy HH-mm-ss z", "EEE dd-MMM-yy HH:mm:ss z", "EEE dd MMM yy HH:mm:ss z", "EEE,dd-MMM-yy HH:mm:ss z", "EEE,dd-MMM-yyyy HH:mm:ss z", "EEE, dd-MM-yyyy HH:mm:ss z", "EEE MMM d yyyy HH:mm:ss z"};
        BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS = arrstring;
        BROWSER_COMPATIBLE_DATE_FORMATS = new DateFormat[arrstring.length];
    }

    public static final Date toHttpDateOrNull(String object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toHttpDateOrNull");
        if (((CharSequence)object).length() == 0) {
            return null;
        }
        int n = 0;
        if (n != 0) {
            return null;
        }
        ParsePosition parsePosition = new ParsePosition(0);
        Cloneable cloneable = ((DateFormat)STANDARD_DATE_FORMAT.get()).parse((String)object, parsePosition);
        if (parsePosition.getIndex() == ((String)object).length()) {
            return cloneable;
        }
        String[] arrstring = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS;
        synchronized (arrstring) {
            int n2 = BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS.length;
            for (n = 0; n < n2; cloneable = cloneable.parse((String)object, (ParsePosition)parsePosition), ++n) {
                DateFormat dateFormat = BROWSER_COMPATIBLE_DATE_FORMATS[n];
                cloneable = dateFormat;
                if (dateFormat == null) {
                    cloneable = new SimpleDateFormat(BROWSER_COMPATIBLE_DATE_FORMAT_STRINGS[n], Locale.US);
                    ((DateFormat)cloneable).setTimeZone(Util.UTC);
                    cloneable = (DateFormat)cloneable;
                    DatesKt.BROWSER_COMPATIBLE_DATE_FORMATS[n] = cloneable;
                }
                parsePosition.setIndex(0);
                int n3 = parsePosition.getIndex();
                if (n3 != 0) return cloneable;
            }
            object = Unit.INSTANCE;
            return null;
        }
    }

    public static final String toHttpDateString(Date object) {
        Intrinsics.checkParameterIsNotNull(object, "$this$toHttpDateString");
        object = ((DateFormat)STANDARD_DATE_FORMAT.get()).format((Date)object);
        Intrinsics.checkExpressionValueIsNotNull(object, "STANDARD_DATE_FORMAT.get().format(this)");
        return object;
    }
}


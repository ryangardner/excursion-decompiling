/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.time;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000.\n\u0000\n\u0002\u0010\u0011\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0006\n\u0002\b\u0003\u001a\u0010\u0010\t\u001a\u00020\u00032\u0006\u0010\n\u001a\u00020\u000bH\u0002\u001a\u0010\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0000\u001a\u0018\u0010\u0010\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\u001a\u0018\u0010\u0011\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\n\u001a\u00020\u000bH\u0000\"\u001c\u0010\u0000\u001a\u000e\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\u00020\u0001X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0004\"\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000\"\u0014\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00030\u0002X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"precisionFormats", "", "Ljava/lang/ThreadLocal;", "Ljava/text/DecimalFormat;", "[Ljava/lang/ThreadLocal;", "rootNegativeExpFormatSymbols", "Ljava/text/DecimalFormatSymbols;", "rootPositiveExpFormatSymbols", "scientificFormat", "createFormatForDecimals", "decimals", "", "formatScientific", "", "value", "", "formatToExactDecimals", "formatUpToDecimals", "kotlin-stdlib"}, k=2, mv={1, 1, 16})
public final class FormatToDecimalsKt {
    private static final ThreadLocal<DecimalFormat>[] precisionFormats;
    private static final DecimalFormatSymbols rootNegativeExpFormatSymbols;
    private static final DecimalFormatSymbols rootPositiveExpFormatSymbols;
    private static final ThreadLocal<DecimalFormat> scientificFormat;

    static {
        ThreadLocal[] arrthreadLocal = new DecimalFormatSymbols(Locale.ROOT);
        arrthreadLocal.setExponentSeparator("e");
        rootNegativeExpFormatSymbols = arrthreadLocal;
        arrthreadLocal = new DecimalFormatSymbols(Locale.ROOT);
        arrthreadLocal.setExponentSeparator("e+");
        rootPositiveExpFormatSymbols = arrthreadLocal;
        arrthreadLocal = new ThreadLocal[4];
        int n = 0;
        do {
            if (n >= 4) {
                precisionFormats = arrthreadLocal;
                scientificFormat = new ThreadLocal();
                return;
            }
            arrthreadLocal[n] = new ThreadLocal();
            ++n;
        } while (true);
    }

    private static final DecimalFormat createFormatForDecimals(int n) {
        DecimalFormat decimalFormat = new DecimalFormat("0", rootNegativeExpFormatSymbols);
        if (n > 0) {
            decimalFormat.setMinimumFractionDigits(n);
        }
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
        return decimalFormat;
    }

    public static final String formatScientific(double d) {
        ThreadLocal<DecimalFormat> threadLocal = scientificFormat;
        Object object = threadLocal.get();
        if (object == null) {
            object = new DecimalFormat("0E0", rootNegativeExpFormatSymbols);
            ((DecimalFormat)object).setMinimumFractionDigits(2);
            threadLocal.set((DecimalFormat)object);
        }
        threadLocal = object;
        object = !(d >= (double)true) && !(d <= (double)-1) ? rootNegativeExpFormatSymbols : rootPositiveExpFormatSymbols;
        ((DecimalFormat)((Object)threadLocal)).setDecimalFormatSymbols((DecimalFormatSymbols)object);
        object = ((NumberFormat)((Object)threadLocal)).format(d);
        Intrinsics.checkExpressionValueIsNotNull(object, "scientificFormat.getOrSe\u2026 }\n        .format(value)");
        return object;
    }

    public static final String formatToExactDecimals(double d, int n) {
        Object object = precisionFormats;
        if (n < ((ThreadLocal<DecimalFormat>[])object).length) {
            ThreadLocal<DecimalFormat> threadLocal = object[n];
            object = threadLocal.get();
            if (object == null) {
                object = FormatToDecimalsKt.createFormatForDecimals(n);
                threadLocal.set((DecimalFormat)object);
            }
            object = (DecimalFormat)object;
        } else {
            object = FormatToDecimalsKt.createFormatForDecimals(n);
        }
        object = ((NumberFormat)object).format(d);
        Intrinsics.checkExpressionValueIsNotNull(object, "format.format(value)");
        return object;
    }

    public static final String formatUpToDecimals(double d, int n) {
        Object object = FormatToDecimalsKt.createFormatForDecimals(0);
        ((DecimalFormat)object).setMaximumFractionDigits(n);
        object = ((NumberFormat)object).format(d);
        Intrinsics.checkExpressionValueIsNotNull(object, "createFormatForDecimals(\u2026 }\n        .format(value)");
        return object;
    }
}


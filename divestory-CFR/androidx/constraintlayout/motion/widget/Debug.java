/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.widget.MotionLayout;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class Debug {
    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static void dumpLayoutParams(ViewGroup.LayoutParams var0, String var1_1) {
        var2_2 = new Throwable().getStackTrace()[1];
        var3_3 = new StringBuilder();
        var3_3.append(".(");
        var3_3.append(var2_2.getFileName());
        var3_3.append(":");
        var3_3.append(var2_2.getLineNumber());
        var3_3.append(") ");
        var3_3.append(var1_1);
        var3_3.append("  ");
        var1_1 = var3_3.toString();
        var2_2 = System.out;
        var3_3 = new StringBuilder();
        var3_3.append(" >>>>>>>>>>>>>>>>>>. dump ");
        var3_3.append(var1_1);
        var3_3.append("  ");
        var3_3.append(var0.getClass().getName());
        var2_2.println(var3_3.toString());
        var2_2 = var0.getClass().getFields();
        var4_5 = 0;
        block2 : do {
            if (var4_5 >= ((Field[])var2_2).length) {
                var2_2 = System.out;
                var0 = new StringBuilder();
                var0.append(" <<<<<<<<<<<<<<<<< dump ");
                var0.append(var1_1);
                var2_2.println(var0.toString());
                return;
            }
            var5_6 = var2_2[var4_5];
            try {
                var3_3 = var5_6.get(var0);
                var5_6 = var5_6.getName();
                if (var5_6.contains("To") && !var3_3.toString().equals("-1")) {
                    var6_7 = System.out;
                    var7_8 = new StringBuilder();
                    var7_8.append(var1_1);
                    var7_8.append("       ");
                    var7_8.append((String)var5_6);
                    var7_8.append(" ");
                    var7_8.append(var3_3);
                    var6_7.println(var7_8.toString());
                }
lbl59: // 4 sources:
                do {
                    ++var4_5;
                    continue block2;
                    break;
                } while (true);
            }
            catch (IllegalAccessException var3_4) {
                ** continue;
            }
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static void dumpLayoutParams(ViewGroup var0, String var1_1) {
        var2_2 = new Throwable().getStackTrace()[1];
        var3_3 = new StringBuilder();
        var3_3.append(".(");
        var3_3.append(var2_2.getFileName());
        var3_3.append(":");
        var3_3.append(var2_2.getLineNumber());
        var3_3.append(") ");
        var3_3.append((String)var1_1);
        var3_3.append("  ");
        var2_2 = var3_3.toString();
        var4_4 = var0.getChildCount();
        var3_3 = System.out;
        var5_5 = new StringBuilder();
        var5_5.append((String)var1_1);
        var5_5.append(" children ");
        var5_5.append(var4_4);
        var3_3.println(var5_5.toString());
        var6_7 = 0;
        block2 : do {
            if (var6_7 >= var4_4) return;
            var5_5 = var0.getChildAt(var6_7);
            var3_3 = System.out;
            var1_1 = new StringBuilder();
            var1_1.append((String)var2_2);
            var1_1.append("     ");
            var1_1.append(Debug.getName((View)var5_5));
            var3_3.println(var1_1.toString());
            var3_3 = var5_5.getLayoutParams();
            var1_1 = var3_3.getClass().getFields();
            var7_8 = 0;
            block3 : do {
                if (var7_8 >= ((CharSequence)var1_1).length) ** GOTO lbl64
                var8_9 = var1_1[var7_8];
                try {
                    block6 : {
                        var9_10 = var8_9.get(var3_3);
                        if (var8_9.getName().contains("To") && !var9_10.toString().equals("-1")) {
                            var10_11 = System.out;
                            var5_5 = new StringBuilder();
                            var5_5.append((String)var2_2);
                            var5_5.append("       ");
                            var5_5.append(var8_9.getName());
                            var5_5.append(" ");
                            var5_5.append(var9_10);
                            var10_11.println(var5_5.toString());
                        }
                        break block6;
lbl64: // 1 sources:
                        ++var6_7;
                        continue block2;
                    }
lbl67: // 2 sources:
                    do {
                        ++var7_8;
                        continue block3;
                        break;
                    } while (true);
                }
                catch (IllegalAccessException var5_6) {
                    ** continue;
                }
            } while (true);
            break;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static void dumpPoc(Object var0) {
        var1_1 = new Throwable().getStackTrace()[1];
        var2_2 = new StringBuilder();
        var2_2.append(".(");
        var2_2.append(var1_1.getFileName());
        var2_2.append(":");
        var2_2.append(var1_1.getLineNumber());
        var2_2.append(")");
        var2_3 = var2_2.toString();
        var1_1 = var0.getClass();
        var3_4 = System.out;
        var4_5 = new StringBuilder();
        var4_5.append(var2_3);
        var4_5.append("------------- ");
        var4_5.append(var1_1.getName());
        var4_5.append(" --------------------");
        var3_4.println(var4_5.toString());
        var3_4 = var1_1.getFields();
        var5_7 = 0;
        block2 : do {
            if (var5_7 >= ((Field[])var3_4).length) {
                var3_4 = System.out;
                var0 = new StringBuilder();
                var0.append(var2_3);
                var0.append("------------- ");
                var0.append(var1_1.getSimpleName());
                var0.append(" --------------------");
                var3_4.println(var0.toString());
                return;
            }
            var4_5 = var3_4[var5_7];
            try {
                var6_8 = var4_5.get(var0);
                if (!(!var4_5.getName().startsWith("layout_constraint") || var6_8 instanceof Integer && var6_8.toString().equals("-1") || var6_8 instanceof Integer && var6_8.toString().equals("0") || var6_8 instanceof Float && var6_8.toString().equals("1.0") || var6_8 instanceof Float && var6_8.toString().equals("0.5"))) {
                    var7_9 = System.out;
                    var8_10 = new StringBuilder();
                    var8_10.append(var2_3);
                    var8_10.append("    ");
                    var8_10.append(var4_5.getName());
                    var8_10.append(" ");
                    var8_10.append(var6_8);
                    var7_9.println(var8_10.toString());
                }
lbl59: // 4 sources:
                do {
                    ++var5_7;
                    continue block2;
                    break;
                } while (true);
            }
            catch (IllegalAccessException var4_6) {
                ** continue;
            }
        } while (true);
    }

    public static String getActionType(MotionEvent arrfield) {
        int n = arrfield.getAction();
        arrfield = MotionEvent.class.getFields();
        int n2 = 0;
        while (n2 < arrfield.length) {
            Field field = arrfield[n2];
            try {
                if (Modifier.isStatic(field.getModifiers()) && field.getType().equals(Integer.TYPE) && field.getInt(null) == n) {
                    return field.getName();
                }
            }
            catch (IllegalAccessException illegalAccessException) {}
            ++n2;
        }
        return "---";
    }

    public static String getCallFrom(int n) {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[n + 2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(".(");
        stringBuilder.append(stackTraceElement.getFileName());
        stringBuilder.append(":");
        stringBuilder.append(stackTraceElement.getLineNumber());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static String getLoc() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(".(");
        stringBuilder.append(stackTraceElement.getFileName());
        stringBuilder.append(":");
        stringBuilder.append(stackTraceElement.getLineNumber());
        stringBuilder.append(") ");
        stringBuilder.append(stackTraceElement.getMethodName());
        stringBuilder.append("()");
        return stringBuilder.toString();
    }

    public static String getLocation() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(".(");
        stringBuilder.append(stackTraceElement.getFileName());
        stringBuilder.append(":");
        stringBuilder.append(stackTraceElement.getLineNumber());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static String getLocation2() {
        StackTraceElement stackTraceElement = new Throwable().getStackTrace()[2];
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(".(");
        stringBuilder.append(stackTraceElement.getFileName());
        stringBuilder.append(":");
        stringBuilder.append(stackTraceElement.getLineNumber());
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    public static String getName(Context context, int n) {
        if (n == -1) return "UNKNOWN";
        try {
            return context.getResources().getResourceEntryName(n);
        }
        catch (Exception exception) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("?");
            stringBuilder.append(n);
            return stringBuilder.toString();
        }
    }

    public static String getName(Context object, int[] arrn) {
        try {
            CharSequence charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(arrn.length);
            ((StringBuilder)charSequence).append("[");
            charSequence = ((StringBuilder)charSequence).toString();
            for (int i = 0; i < arrn.length; ++i) {
                CharSequence charSequence2 = new StringBuilder();
                charSequence2.append((String)charSequence);
                charSequence = i == 0 ? "" : " ";
                charSequence2.append((String)charSequence);
                charSequence2 = charSequence2.toString();
                try {
                    charSequence = object.getResources().getResourceEntryName(arrn[i]);
                }
                catch (Resources.NotFoundException notFoundException) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append("? ");
                    ((StringBuilder)charSequence).append(arrn[i]);
                    ((StringBuilder)charSequence).append(" ");
                    charSequence = ((StringBuilder)charSequence).toString();
                }
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append((String)charSequence2);
                stringBuilder.append((String)charSequence);
                charSequence = stringBuilder.toString();
            }
            object = new StringBuilder();
            ((StringBuilder)object).append((String)charSequence);
            ((StringBuilder)object).append("]");
            return ((StringBuilder)object).toString();
        }
        catch (Exception exception) {
            Log.v((String)"DEBUG", (String)exception.toString());
            return "UNKNOWN";
        }
    }

    public static String getName(View object) {
        try {
            return object.getContext().getResources().getResourceEntryName(object.getId());
        }
        catch (Exception exception) {
            return "UNKNOWN";
        }
    }

    public static String getState(MotionLayout motionLayout, int n) {
        if (n != -1) return motionLayout.getContext().getResources().getResourceEntryName(n);
        return "UNDEFINED";
    }

    public static void logStack(String string2, String string3, int n) {
        StackTraceElement[] arrstackTraceElement = new Throwable().getStackTrace();
        int n2 = arrstackTraceElement.length;
        int n3 = 1;
        n2 = Math.min(n, n2 - 1);
        String string4 = " ";
        n = n3;
        while (n <= n2) {
            Object object = arrstackTraceElement[n];
            object = new StringBuilder();
            ((StringBuilder)object).append(".(");
            ((StringBuilder)object).append(arrstackTraceElement[n].getFileName());
            ((StringBuilder)object).append(":");
            ((StringBuilder)object).append(arrstackTraceElement[n].getLineNumber());
            ((StringBuilder)object).append(") ");
            ((StringBuilder)object).append(arrstackTraceElement[n].getMethodName());
            object = ((StringBuilder)object).toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string4);
            stringBuilder.append(" ");
            string4 = stringBuilder.toString();
            stringBuilder = new StringBuilder();
            stringBuilder.append(string3);
            stringBuilder.append(string4);
            stringBuilder.append((String)object);
            stringBuilder.append(string4);
            Log.v((String)string2, (String)stringBuilder.toString());
            ++n;
        }
    }

    public static void printStack(String string2, int n) {
        StackTraceElement[] arrstackTraceElement = new Throwable().getStackTrace();
        int n2 = arrstackTraceElement.length;
        int n3 = 1;
        n2 = Math.min(n, n2 - 1);
        String string3 = " ";
        n = n3;
        while (n <= n2) {
            Object object = arrstackTraceElement[n];
            object = new StringBuilder();
            ((StringBuilder)object).append(".(");
            ((StringBuilder)object).append(arrstackTraceElement[n].getFileName());
            ((StringBuilder)object).append(":");
            ((StringBuilder)object).append(arrstackTraceElement[n].getLineNumber());
            ((StringBuilder)object).append(") ");
            object = ((StringBuilder)object).toString();
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string3);
            stringBuilder.append(" ");
            string3 = stringBuilder.toString();
            PrintStream printStream = System.out;
            stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(string3);
            stringBuilder.append((String)object);
            stringBuilder.append(string3);
            printStream.println(stringBuilder.toString());
            ++n;
        }
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ClipData
 *  android.content.ClipData$Item
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.location.Address
 *  android.location.Geocoder
 *  android.net.Uri
 *  android.text.Html
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.syntak.library;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.syntak.library.MathOp;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.ShortBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringOp {
    public static final byte bESCAPE = 37;
    public static final String sESCAPE = "%";
    public static final String sESCAPE2 = "%%";

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static String ASCII_to_UTF8(String var0) {
        var1_2 = null;
        if (var0 == null) {
            return null;
        }
        var2_3 = var0.toCharArray();
        var3_5 = new char[var2_3.length];
        var4_6 = 0;
        var6_8 = 0;
        for (var5_7 = 0; var5_7 < var2_3.length; ++var5_7) {
            if (var2_3[var5_7] != '%') ** GOTO lbl17
            if (var2_3[++var5_7] == '%') {
                var3_5[var6_8] = (char)37;
            } else {
                var3_5[var6_8] = (char)Integer.parseInt(var0.substring(var5_7, var5_7 + 2), 16);
                ++var6_8;
                ++var5_7;
                continue;
lbl17: // 1 sources:
                var3_5[var6_8] = var2_3[var5_7];
            }
            ++var6_8;
        }
        var2_4 = new byte[var6_8];
        for (var5_7 = var4_6; var5_7 < var6_8; ++var5_7) {
            var2_4[var5_7] = (byte)var3_5[var5_7];
        }
        try {
            return new String(var2_4, "UTF-8");
        }
        catch (UnsupportedEncodingException var0_1) {
            return var1_2;
        }
    }

    public static byte[] ASCII_to_binary(String string2) {
        if (string2 == null) {
            return null;
        }
        int n = StringOp.strlen(string2);
        byte[] arrby = new byte[n / 2];
        int n2 = 0;
        int n3 = 0;
        while (n2 < n) {
            int n4 = n2 + 2;
            arrby[n3] = (byte)Integer.parseInt(string2.substring(n2, n4), 16);
            ++n3;
            n2 = n4;
        }
        return arrby;
    }

    public static byte[] ASCII_to_binary(byte[] object, int n) {
        byte[] arrby = new byte[n];
        System.arraycopy(object, 0, arrby, 0, n);
        try {
            object = new String(arrby, "UTF-8");
            return StringOp.ASCII_to_binary((String)object);
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            object = null;
        }
        return StringOp.ASCII_to_binary((String)object);
    }

    public static float[] ArrayBytesToFloats(byte[] arrby, int n, ByteOrder byteOrder) {
        float[] arrf = new float[n / 4];
        ByteBuffer.wrap(arrby, 0, n).order(byteOrder).asFloatBuffer().get(arrf);
        return arrf;
    }

    public static short[] ArrayBytesToShorts(byte[] arrby, int n, ByteOrder byteOrder) {
        if (arrby == null) {
            return null;
        }
        short[] arrs = new short[n / 2];
        ByteBuffer.wrap(arrby, 0, n).order(byteOrder).asShortBuffer().get(arrs);
        return arrs;
    }

    public static byte[] ArrayFloatsToBytes(float[] arrf, int n, ByteOrder byteOrder) {
        byte[] arrby = new byte[n * 4];
        ByteBuffer.wrap(arrby).order(byteOrder).asFloatBuffer().put(arrf, 0, n);
        return arrby;
    }

    public static byte[] ArrayShortsToBytes(short[] arrs, int n, ByteOrder byteOrder) {
        if (arrs == null) {
            return null;
        }
        byte[] arrby = new byte[n * 2];
        ByteBuffer.wrap(arrby).order(byteOrder).asShortBuffer().put(arrs, 0, n);
        return arrby;
    }

    public static String ByteArrayToString(byte[] arrby) {
        return new String(arrby, 0, arrby.length);
    }

    public static String ByteArrayToString(byte[] object, Charset charset) {
        try {
            String string2 = new String((byte[])object, String.valueOf(charset));
            return string2;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            return null;
        }
    }

    public static void FloatsGain(float[] arrf, float f) {
        int n = 0;
        while (n < arrf.length) {
            arrf[n] = arrf[n] * f;
            ++n;
        }
    }

    public static String HEX_to_DECIMAL(String string2) {
        return String.valueOf(Integer.parseInt(string2, 16));
    }

    public static String HourMinuteSecondToString(int n, int n2, int n3, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%02d", n));
        stringBuilder.append(string2);
        stringBuilder.append(String.format("%02d", n2));
        stringBuilder.append(string2);
        stringBuilder.append(String.format("%02d", n3));
        return stringBuilder.toString();
    }

    public static String HourMinuteToString(int n, int n2, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        String string3 = String.format("%02d", n);
        String string4 = String.format("%02d", n2);
        stringBuilder.append(string3);
        stringBuilder.append(string2);
        stringBuilder.append(string4);
        return stringBuilder.toString();
    }

    public static String SecToStringMinSec(int n, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(String.format("%02d", n / 60));
        stringBuilder.append(string2);
        stringBuilder.append(String.format("%02d", n % 60));
        return stringBuilder.toString();
    }

    public static void ShortsDeduct(short[] arrs, short[] arrs2) {
        int n = Math.min(arrs.length, arrs2.length);
        int n2 = 0;
        while (n2 < n) {
            arrs[n2] = (short)(arrs[n2] - arrs2[n2]);
            ++n2;
        }
    }

    public static void ShortsGain(short[] arrs, float f) {
        int n = 0;
        while (n < arrs.length) {
            arrs[n] = (short)((float)arrs[n] * f);
            ++n;
        }
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static String UTF8_to_ASCII(String var0) {
        var1_2 = null;
        if (var0 == null) {
            return null;
        }
        try {
            var1_2 = var0 = var0.getBytes("UTF8");
lbl6: // 2 sources:
            do {
                var2_3 = 0;
                var0 = "";
                break;
            } while (true);
        }
        catch (UnsupportedEncodingException var0_1) {
            ** continue;
        }
        while (var2_3 < ((Object)var1_2).length) {
            var3_4 = var1_2[var2_3];
            var4_5 = var3_4 < 0 ? var3_4 + 256 : var3_4;
            if (var3_4 == 37) {
                var5_6 = new StringBuilder();
                var5_6.append((String)var0);
                var5_6.append("%%");
                var0 = var5_6.toString();
            } else if (var4_5 <= 127 && var4_5 >= 47) {
                var5_6 = new StringBuilder();
                var5_6.append((String)var0);
                var5_6.append(Character.toString((char)var3_4));
                var0 = var5_6.toString();
            } else {
                var5_6 = new StringBuilder();
                var5_6.append((String)var0);
                var5_6.append("%");
                var5_6 = var5_6.toString();
                var6_7 = Integer.toHexString((int)var4_5);
                var0 = var5_6;
                if (var6_7.length() < 2) {
                    var0 = new StringBuilder();
                    var0.append((String)var5_6);
                    var0.append("0");
                    var0 = var0.toString();
                }
                var5_6 = new StringBuilder();
                var5_6.append((String)var0);
                var5_6.append(var6_7);
                var0 = var5_6.toString();
            }
            ++var2_3;
        }
        return var0;
    }

    public static void changeSearchViewTextColor(View view, int n) {
        if (view == null) return;
        if (view instanceof TextView) {
            ((TextView)view).setTextColor(n);
            return;
        }
        if (!(view instanceof ViewGroup)) return;
        view = (ViewGroup)view;
        int n2 = 0;
        while (n2 < view.getChildCount()) {
            StringOp.changeSearchViewTextColor(view.getChildAt(n2), n);
            ++n2;
        }
    }

    public static byte[] combineByteArrays(byte[] arrby, byte[] arrby2) {
        byte[] arrby3 = new byte[arrby.length + arrby2.length];
        System.arraycopy(arrby, 0, arrby3, 0, arrby.length);
        System.arraycopy(arrby2, 0, arrby3, arrby.length, arrby2.length);
        return arrby3;
    }

    public static Bitmap encodeQR(String string2) {
        Object object = new QRCodeWriter();
        try {
            object = ((QRCodeWriter)object).encode(string2, BarcodeFormat.QR_CODE, 512, 512);
            int n = ((BitMatrix)object).getWidth();
            int n2 = ((BitMatrix)object).getHeight();
            string2 = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.RGB_565);
            int n3 = 0;
            while (n3 < n) {
                for (int i = 0; i < n2; ++i) {
                    int n4 = ((BitMatrix)object).get(n3, i) ? -16777216 : -1;
                    string2.setPixel(n3, i, n4);
                }
                ++n3;
            }
            return string2;
        }
        catch (WriterException writerException) {
            writerException.printStackTrace();
            return null;
        }
    }

    public static byte[] getAbsMaxAverageOfBytes(byte[] arrby, int n) {
        byte[] arrby2;
        byte[] arrby3 = arrby2 = new byte[2];
        arrby3[0] = 0;
        arrby3[1] = 0;
        float f = 0.0f;
        int n2 = 0;
        int n3 = 0;
        do {
            int n4;
            if (n2 >= n) {
                arrby2[0] = (byte)n3;
                arrby2[1] = (byte)f;
                return arrby2;
            }
            int n5 = n4 = arrby[n2];
            if (n4 < 0) {
                n5 = n4 * -1;
            }
            n4 = n3;
            if (n5 > n3) {
                n4 = n5;
            }
            f += (float)(n5 / n);
            ++n2;
            n3 = n4;
        } while (true);
    }

    public static short[] getAbsMaxAverageOfShorts(short[] arrs, int n) {
        short[] arrs2;
        short[] arrs3 = arrs2 = new short[2];
        arrs3[0] = 0;
        arrs3[1] = 0;
        float f = 0.0f;
        int n2 = 0;
        short s = 0;
        do {
            short s2;
            if (n2 >= n) {
                arrs2[0] = s;
                arrs2[1] = (short)f;
                return arrs2;
            }
            short s3 = s2 = arrs[n2];
            if (s2 < 0) {
                s3 = (short)(s2 * -1);
            }
            s2 = s;
            if (s3 > s) {
                s2 = s3;
            }
            f += (float)(s3 / n);
            ++n2;
            s = s2;
        } while (true);
    }

    public static String getCheckFromBytes(byte[] arrby) {
        int n = 0;
        int n2 = 0;
        while (n < arrby.length) {
            n2 += arrby[n] & 255;
            ++n;
        }
        return String.valueOf(MathOp.mod(n2, arrby.length));
    }

    public static HashMap<String, String> getHashMapFromString(String arrstring) {
        int n = StringOp.strlen((String)arrstring);
        HashMap<String, String> hashMap = null;
        if (n <= 0) {
            return null;
        }
        String[] arrstring2 = arrstring.split("&");
        arrstring = hashMap;
        if (arrstring2.length <= 0) return arrstring;
        hashMap = new HashMap<String, String>();
        int n2 = arrstring2.length;
        n = 0;
        do {
            arrstring = hashMap;
            if (n >= n2) return arrstring;
            arrstring = arrstring2[n].split("=");
            hashMap.put(arrstring[0], arrstring[1]);
            ++n;
        } while (true);
    }

    public static String getHexFromBytes(byte[] arrby, boolean bl, String string2) {
        if (arrby == null) return null;
        if (arrby.length == 0) {
            return null;
        }
        CharSequence charSequence = new String();
        int n = 0;
        while (n < arrby.length) {
            CharSequence charSequence2;
            CharSequence charSequence3;
            block6 : {
                block7 : {
                    charSequence2 = charSequence;
                    if (StringOp.strlen(string2) <= 0) break block6;
                    if (n > 0) break block7;
                    charSequence2 = charSequence;
                    if (!bl) break block6;
                }
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append(string2);
                charSequence2 = ((StringBuilder)charSequence2).toString();
            }
            charSequence = charSequence3 = Integer.toHexString(arrby[n] & 255);
            if (((String)charSequence3).length() == 1) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append('0');
                ((StringBuilder)charSequence).append((String)charSequence3);
                charSequence = ((StringBuilder)charSequence).toString();
            }
            charSequence3 = new StringBuilder();
            ((StringBuilder)charSequence3).append((String)charSequence2);
            ((StringBuilder)charSequence3).append((String)charSequence);
            charSequence = ((StringBuilder)charSequence3).toString();
            ++n;
        }
        return charSequence;
    }

    public static String getLocationName(Context object, double d, double d2) {
        String string2 = "Not Found";
        object = new Geocoder(object, Locale.getDefault());
        String string3 = string2;
        try {
            Iterator iterator2 = object.getFromLocation(d, d2, 10).iterator();
            object = string2;
            do {
                string3 = object;
                string2 = object;
                if (!iterator2.hasNext()) return string2;
                string3 = object;
                string2 = (Address)iterator2.next();
                if (string2 == null) continue;
                string3 = object;
                if ((string2 = string2.getLocality()) == null) continue;
                string3 = object;
                boolean bl = string2.equals("");
                if (bl) continue;
                object = string2;
            } while (true);
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return string3;
        }
    }

    public static String[] getStringArrayFromArrayList(ArrayList<String> arrayList) {
        String[] arrstring = new String[arrayList.size()];
        int n = 0;
        while (n < arrayList.size()) {
            arrstring[n] = arrayList.get(n);
            ++n;
        }
        return arrstring;
    }

    public static String[] getStringArrayFromIntent(Intent object) {
        object = StringOp.getUriFromIntent((Intent)object);
        String[] arrstring = new String[((ArrayList)object).size()];
        int n = 0;
        while (n < ((ArrayList)object).size()) {
            arrstring[n] = ((Uri)((ArrayList)object).get(n)).getPath();
            ++n;
        }
        return arrstring;
    }

    public static ArrayList<Uri> getUriFromIntent(Intent intent) {
        ArrayList<Uri> arrayList = new ArrayList<Uri>();
        Uri uri = intent.getData();
        if (uri != null) {
            arrayList.add(uri);
            return arrayList;
        }
        if (intent.getClipData() == null) return arrayList;
        intent = intent.getClipData();
        int n = 0;
        while (n < intent.getItemCount()) {
            arrayList.add(intent.getItemAt(n).getUri());
            ++n;
        }
        return arrayList;
    }

    public static Uri getUriFromPath(String string2) {
        return Uri.fromFile((File)new File("/sdcard/sample.jpg"));
    }

    public static String getVersionName(Context object) {
        try {
            return object.getPackageManager().getPackageInfo((String)object.getPackageName(), (int)0).versionName;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return null;
        }
    }

    public static String get_HexString_normalized(String string2) {
        String[] arrstring;
        boolean bl = string2.contains(" ");
        Object var2_2 = null;
        if (bl) {
            arrstring = string2.split(" ");
        } else if (string2.contains(":")) {
            arrstring = string2.split(":");
        } else {
            if (StringOp.strlen(string2) % 2 != 0) {
                return null;
            }
            arrstring = null;
        }
        if (arrstring != null) {
            string2 = "";
            for (int i = 0; i < arrstring.length; ++i) {
                CharSequence charSequence = arrstring[i];
                if (StringOp.strlen((String)charSequence) > 2) {
                    return null;
                }
                CharSequence charSequence2 = charSequence;
                if (StringOp.strlen((String)charSequence) < 2) {
                    charSequence2 = new StringBuilder();
                    ((StringBuilder)charSequence2).append("0");
                    ((StringBuilder)charSequence2).append((String)charSequence);
                    charSequence2 = ((StringBuilder)charSequence2).toString();
                }
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append((String)charSequence2);
                string2 = ((StringBuilder)charSequence).toString();
            }
        }
        if (StringOp.isHexString(string2)) return string2;
        return var2_2;
    }

    public static boolean highlightSearched(TextView textView, String string2, String charSequence) {
        String string3;
        CharSequence charSequence2;
        block6 : {
            block5 : {
                charSequence2 = textView.getText().toString();
                if (string2 == null) return false;
                if (string2.length() <= 0) return false;
                if (charSequence == null) break block5;
                string3 = charSequence;
                if (((String)charSequence).length() != 0) break block6;
            }
            string3 = "yellow";
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("<font color='");
        ((StringBuilder)charSequence).append(string3);
        ((StringBuilder)charSequence).append("'>");
        string3 = ((StringBuilder)charSequence).toString();
        int n = ((String)charSequence2).toLowerCase().indexOf(string2.toLowerCase(), 0);
        charSequence = charSequence2;
        do {
            if (n < 0) {
                textView.setText((CharSequence)Html.fromHtml((String)charSequence));
                return true;
            }
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append(((String)charSequence).substring(0, n));
            ((StringBuilder)charSequence2).append(string3);
            ((StringBuilder)charSequence2).append(((String)charSequence).substring(n, string2.length() + n));
            ((StringBuilder)charSequence2).append("</font>");
            ((StringBuilder)charSequence2).append(((String)charSequence).substring(string2.length() + n));
            charSequence = ((StringBuilder)charSequence2).toString();
            int n2 = string3.length();
            int n3 = string2.length();
            n = ((String)charSequence).toLowerCase().indexOf(string2.toLowerCase(), n + (n2 + n3 + 7));
        } while (true);
    }

    public static String intToStr(int n, int n2) {
        if (n < 0) return null;
        if (n2 < 2) return null;
        if (n2 > 36) {
            return null;
        }
        String string2 = "";
        while (n > 0) {
            int n3 = n % n2;
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".substring(n3, n3 + 1));
            stringBuilder.append(string2);
            string2 = stringBuilder.toString();
            n /= n2;
        }
        return string2;
    }

    public static boolean isBytesEqual(byte[] arrby, byte[] arrby2) {
        if (arrby == null) return false;
        if (arrby2 == null) {
            return false;
        }
        int n = 0;
        while (n < arrby.length) {
            if (arrby[n] != arrby2[n]) {
                return false;
            }
            ++n;
        }
        return true;
    }

    public static boolean isHexString(String string2) {
        return Pattern.compile("[A-Fa-f0-9]+").matcher(string2).matches();
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static String keepOnlyAlphabetNumber(String var0) {
        var1_2 = null;
        if (var0 == null) {
            return null;
        }
        try {
            var0 = var0.getBytes("UTF8");
lbl6: // 2 sources:
            do {
                var2_6 = 0;
                var1_3 = "";
                break;
            } while (true);
        }
        catch (UnsupportedEncodingException var0_1) {
            var0 = var1_2;
            ** continue;
        }
        while (var2_6 < var0.length) {
            block9 : {
                block8 : {
                    var3_7 = var0[var2_6];
                    if (var3_7 >= 48 && var3_7 <= 57 || var3_7 >= 65 && var3_7 <= 90) break block8;
                    var4_8 /* !! */  = var1_4;
                    if (var3_7 < 97) break block9;
                    var4_8 /* !! */  = var1_4;
                    if (var3_7 > 122) break block9;
                }
                var4_8 /* !! */  = new StringBuilder();
                var4_8 /* !! */ .append((String)var1_4);
                var4_8 /* !! */ .append(Character.toString((char)var3_7));
                var4_8 /* !! */  = var4_8 /* !! */ .toString();
            }
            ++var2_6;
            var1_5 = var4_8 /* !! */ ;
        }
        return var1_4;
    }

    public static String limitLengthString(String string2, int n) {
        if (string2.length() < n) {
            return string2;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2.substring(0, n));
        stringBuilder.append(" ...");
        return stringBuilder.toString();
    }

    public static void setTextViewMaxWidth(TextView textView, int n) {
        try {
            Field field = textView.getClass().getDeclaredField("mMaxWidth");
            field.setAccessible(true);
            field.set((Object)textView, n);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            illegalAccessException.printStackTrace();
            return;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            noSuchFieldException.printStackTrace();
            return;
        }
        catch (SecurityException securityException) {
            securityException.printStackTrace();
        }
    }

    public static int strlen(String string2) {
        if (string2 == null) return 0;
        return string2.length();
    }

    public static byte[] uint16ToByteArray(int n) {
        return new byte[]{(byte)(n & 255), (byte)(n >> 8 & 255)};
    }

    public String getBig5FromUTF8(String string2) {
        try {
            String string3 = new String(string2.getBytes("UTF-8"), "big5");
            return string3;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }

    public String getUTF8FromBig5(String string2) {
        try {
            String string3 = new String(string2.getBytes("big5"), "UTF-8");
            return string3;
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return "";
        }
    }
}


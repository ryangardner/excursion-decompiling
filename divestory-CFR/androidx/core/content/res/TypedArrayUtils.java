/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 */
package androidx.core.content.res;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import androidx.core.content.res.ColorStateListInflaterCompat;
import androidx.core.content.res.ComplexColorCompat;
import org.xmlpull.v1.XmlPullParser;

public class TypedArrayUtils {
    private static final String NAMESPACE = "http://schemas.android.com/apk/res/android";

    private TypedArrayUtils() {
    }

    public static int getAttr(Context context, int n, int n2) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(n, typedValue, true);
        if (typedValue.resourceId == 0) return n2;
        return n;
    }

    public static boolean getBoolean(TypedArray typedArray, int n, int n2, boolean bl) {
        return typedArray.getBoolean(n, typedArray.getBoolean(n2, bl));
    }

    public static Drawable getDrawable(TypedArray typedArray, int n, int n2) {
        Drawable drawable2;
        Drawable drawable3 = drawable2 = typedArray.getDrawable(n);
        if (drawable2 != null) return drawable3;
        return typedArray.getDrawable(n2);
    }

    public static int getInt(TypedArray typedArray, int n, int n2, int n3) {
        return typedArray.getInt(n, typedArray.getInt(n2, n3));
    }

    public static boolean getNamedBoolean(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n, boolean bl) {
        if (TypedArrayUtils.hasAttribute(xmlPullParser, string2)) return typedArray.getBoolean(n, bl);
        return bl;
    }

    public static int getNamedColor(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n, int n2) {
        if (TypedArrayUtils.hasAttribute(xmlPullParser, string2)) return typedArray.getColor(n, n2);
        return n2;
    }

    public static ColorStateList getNamedColorStateList(TypedArray object, XmlPullParser object2, Resources.Theme theme, String string2, int n) {
        if (!TypedArrayUtils.hasAttribute((XmlPullParser)object2, string2)) return null;
        object2 = new TypedValue();
        object.getValue(n, (TypedValue)object2);
        if (((TypedValue)object2).type != 2) {
            if (((TypedValue)object2).type < 28) return ColorStateListInflaterCompat.inflate(object.getResources(), object.getResourceId(n, 0), theme);
            if (((TypedValue)object2).type > 31) return ColorStateListInflaterCompat.inflate(object.getResources(), object.getResourceId(n, 0), theme);
            return TypedArrayUtils.getNamedColorStateListFromInt((TypedValue)object2);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Failed to resolve attribute at index ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(": ");
        ((StringBuilder)object).append(object2);
        throw new UnsupportedOperationException(((StringBuilder)object).toString());
    }

    private static ColorStateList getNamedColorStateListFromInt(TypedValue typedValue) {
        return ColorStateList.valueOf((int)typedValue.data);
    }

    public static ComplexColorCompat getNamedComplexColor(TypedArray object, XmlPullParser object2, Resources.Theme theme, String string2, int n, int n2) {
        if (!TypedArrayUtils.hasAttribute((XmlPullParser)object2, string2)) return ComplexColorCompat.from(n2);
        object2 = new TypedValue();
        object.getValue(n, (TypedValue)object2);
        if (((TypedValue)object2).type >= 28 && ((TypedValue)object2).type <= 31) {
            return ComplexColorCompat.from(((TypedValue)object2).data);
        }
        if ((object = ComplexColorCompat.inflate(object.getResources(), object.getResourceId(n, 0), theme)) == null) return ComplexColorCompat.from(n2);
        return object;
    }

    public static float getNamedFloat(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n, float f) {
        if (TypedArrayUtils.hasAttribute(xmlPullParser, string2)) return typedArray.getFloat(n, f);
        return f;
    }

    public static int getNamedInt(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n, int n2) {
        if (TypedArrayUtils.hasAttribute(xmlPullParser, string2)) return typedArray.getInt(n, n2);
        return n2;
    }

    public static int getNamedResourceId(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n, int n2) {
        if (TypedArrayUtils.hasAttribute(xmlPullParser, string2)) return typedArray.getResourceId(n, n2);
        return n2;
    }

    public static String getNamedString(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n) {
        if (TypedArrayUtils.hasAttribute(xmlPullParser, string2)) return typedArray.getString(n);
        return null;
    }

    public static int getResourceId(TypedArray typedArray, int n, int n2, int n3) {
        return typedArray.getResourceId(n, typedArray.getResourceId(n2, n3));
    }

    public static String getString(TypedArray typedArray, int n, int n2) {
        String string2;
        String string3 = string2 = typedArray.getString(n);
        if (string2 != null) return string3;
        return typedArray.getString(n2);
    }

    public static CharSequence getText(TypedArray typedArray, int n, int n2) {
        CharSequence charSequence;
        CharSequence charSequence2 = charSequence = typedArray.getText(n);
        if (charSequence != null) return charSequence2;
        return typedArray.getText(n2);
    }

    public static CharSequence[] getTextArray(TypedArray typedArray, int n, int n2) {
        CharSequence[] arrcharSequence;
        CharSequence[] arrcharSequence2 = arrcharSequence = typedArray.getTextArray(n);
        if (arrcharSequence != null) return arrcharSequence2;
        return typedArray.getTextArray(n2);
    }

    public static boolean hasAttribute(XmlPullParser xmlPullParser, String string2) {
        if (xmlPullParser.getAttributeValue(NAMESPACE, string2) == null) return false;
        return true;
    }

    public static TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] arrn) {
        if (theme != null) return theme.obtainStyledAttributes(attributeSet, arrn, 0, 0);
        return resources.obtainAttributes(attributeSet, arrn);
    }

    public static TypedValue peekNamedValue(TypedArray typedArray, XmlPullParser xmlPullParser, String string2, int n) {
        if (TypedArrayUtils.hasAttribute(xmlPullParser, string2)) return typedArray.peekValue(n);
        return null;
    }
}


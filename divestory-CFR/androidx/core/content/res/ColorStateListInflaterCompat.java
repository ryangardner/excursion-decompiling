/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Color
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.StateSet
 *  android.util.Xml
 */
package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.util.StateSet;
import android.util.Xml;
import androidx.core.R;
import androidx.core.content.res.GrowingArrayUtils;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class ColorStateListInflaterCompat {
    private ColorStateListInflaterCompat() {
    }

    public static ColorStateList createFromXml(Resources resources, XmlPullParser xmlPullParser, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)xmlPullParser);
        while ((n = xmlPullParser.next()) != 2 && n != 1) {
        }
        if (n != 2) throw new XmlPullParserException("No start tag found");
        return ColorStateListInflaterCompat.createFromXmlInner(resources, xmlPullParser, attributeSet, theme);
    }

    public static ColorStateList createFromXmlInner(Resources object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        String string2 = xmlPullParser.getName();
        if (string2.equals("selector")) {
            return ColorStateListInflaterCompat.inflate((Resources)object, xmlPullParser, attributeSet, theme);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(xmlPullParser.getPositionDescription());
        ((StringBuilder)object).append(": invalid color state list tag ");
        ((StringBuilder)object).append(string2);
        throw new XmlPullParserException(((StringBuilder)object).toString());
    }

    public static ColorStateList inflate(Resources resources, int n, Resources.Theme theme) {
        try {
            return ColorStateListInflaterCompat.createFromXml(resources, resources.getXml(n), theme);
        }
        catch (Exception exception) {
            Log.e((String)"CSLCompat", (String)"Failed to inflate ColorStateList.", (Throwable)exception);
            return null;
        }
    }

    private static ColorStateList inflate(Resources arrarrn, XmlPullParser arrn, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int n;
        int n2;
        int n3 = arrn.getDepth() + 1;
        int[][] arrarrn2 = new int[20][];
        int[] arrn2 = new int[20];
        int n4 = 0;
        while ((n2 = arrn.next()) != 1 && ((n = arrn.getDepth()) >= n3 || n2 != 3)) {
            if (n2 != 2 || n > n3 || !arrn.getName().equals("item")) continue;
            int[] arrn3 = ColorStateListInflaterCompat.obtainAttributes((Resources)arrarrn, theme, attributeSet, R.styleable.ColorStateListItem);
            int n5 = arrn3.getColor(R.styleable.ColorStateListItem_android_color, -65281);
            float f = 1.0f;
            if (arrn3.hasValue(R.styleable.ColorStateListItem_android_alpha)) {
                f = arrn3.getFloat(R.styleable.ColorStateListItem_android_alpha, 1.0f);
            } else if (arrn3.hasValue(R.styleable.ColorStateListItem_alpha)) {
                f = arrn3.getFloat(R.styleable.ColorStateListItem_alpha, 1.0f);
            }
            arrn3.recycle();
            int n6 = attributeSet.getAttributeCount();
            arrn3 = new int[n6];
            n = 0;
            for (n2 = 0; n2 < n6; ++n2) {
                int n7 = attributeSet.getAttributeNameResource(n2);
                int n8 = n;
                if (n7 != 16843173) {
                    n8 = n;
                    if (n7 != 16843551) {
                        n8 = n;
                        if (n7 != R.attr.alpha) {
                            n8 = attributeSet.getAttributeBooleanValue(n2, false) ? n7 : -n7;
                            arrn3[n] = n8;
                            n8 = n + 1;
                        }
                    }
                }
                n = n8;
            }
            arrn3 = StateSet.trimStateSet((int[])arrn3, (int)n);
            arrn2 = GrowingArrayUtils.append(arrn2, n4, ColorStateListInflaterCompat.modulateColorAlpha(n5, f));
            arrarrn2 = GrowingArrayUtils.append(arrarrn2, n4, arrn3);
            ++n4;
        }
        arrn = new int[n4];
        arrarrn = new int[n4][];
        System.arraycopy(arrn2, 0, arrn, 0, n4);
        System.arraycopy(arrarrn2, 0, arrarrn, 0, n4);
        return new ColorStateList((int[][])arrarrn, arrn);
    }

    private static int modulateColorAlpha(int n, float f) {
        return n & 16777215 | Math.round((float)Color.alpha((int)n) * f) << 24;
    }

    private static TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] arrn) {
        if (theme != null) return theme.obtainStyledAttributes(attributeSet, arrn, 0, 0);
        return resources.obtainAttributes(attributeSet, arrn);
    }
}


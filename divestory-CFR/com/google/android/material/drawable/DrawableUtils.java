/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.RippleDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.util.Xml
 */
package com.google.android.material.drawable;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.RippleDrawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Xml;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class DrawableUtils {
    private DrawableUtils() {
    }

    /*
     * WARNING - void declaration
     */
    public static AttributeSet parseDrawableXml(Context object, int n, CharSequence object2) {
        void var0_3;
        try {
            int n2;
            object = object.getResources().getXml(n);
            while ((n2 = object.next()) != 2 && n2 != 1) {
            }
            if (n2 != 2) {
                object = new XmlPullParserException("No start tag found");
                throw object;
            }
            if (TextUtils.equals((CharSequence)object.getName(), (CharSequence)object2)) {
                return Xml.asAttributeSet((XmlPullParser)object);
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Must have a <");
            stringBuilder.append(object2);
            stringBuilder.append("> start tag");
            object = new XmlPullParserException(stringBuilder.toString());
            throw object;
        }
        catch (IOException iOException) {
        }
        catch (XmlPullParserException xmlPullParserException) {
            // empty catch block
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append("Can't load badge resource ID #0x");
        ((StringBuilder)object2).append(Integer.toHexString(n));
        object2 = new Resources.NotFoundException(((StringBuilder)object2).toString());
        object2.initCause((Throwable)var0_3);
        throw object2;
    }

    /*
     * WARNING - void declaration
     */
    public static void setRippleDrawableRadius(RippleDrawable rippleDrawable, int n) {
        void var0_4;
        if (Build.VERSION.SDK_INT >= 23) {
            rippleDrawable.setRadius(n);
            return;
        }
        try {
            RippleDrawable.class.getDeclaredMethod("setMaxRadius", Integer.TYPE).invoke((Object)rippleDrawable, n);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            throw new IllegalStateException("Couldn't set RippleDrawable radius", (Throwable)var0_4);
        }
        catch (InvocationTargetException invocationTargetException) {
            throw new IllegalStateException("Couldn't set RippleDrawable radius", (Throwable)var0_4);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            // empty catch block
        }
        throw new IllegalStateException("Couldn't set RippleDrawable radius", (Throwable)var0_4);
    }

    public static PorterDuffColorFilter updateTintFilter(Drawable drawable2, ColorStateList colorStateList, PorterDuff.Mode mode) {
        if (colorStateList == null) return null;
        if (mode != null) return new PorterDuffColorFilter(colorStateList.getColorForState(drawable2.getState(), 0), mode);
        return null;
    }
}


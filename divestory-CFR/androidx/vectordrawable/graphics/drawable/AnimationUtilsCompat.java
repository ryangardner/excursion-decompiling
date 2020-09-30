/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Xml
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AnimationUtils
 *  android.view.animation.AnticipateInterpolator
 *  android.view.animation.AnticipateOvershootInterpolator
 *  android.view.animation.BounceInterpolator
 *  android.view.animation.CycleInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.LinearInterpolator
 *  android.view.animation.OvershootInterpolator
 */
package androidx.vectordrawable.graphics.drawable;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.AnticipateOvershootInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.CycleInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.OvershootInterpolator;
import androidx.interpolator.view.animation.FastOutLinearInInterpolator;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;
import androidx.interpolator.view.animation.LinearOutSlowInInterpolator;
import androidx.vectordrawable.graphics.drawable.PathInterpolatorCompat;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimationUtilsCompat {
    private AnimationUtilsCompat() {
    }

    private static Interpolator createInterpolatorFromXml(Context object, Resources object2, Resources.Theme theme, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int n = xmlPullParser.getDepth();
        object2 = null;
        do {
            int n2;
            if ((n2 = xmlPullParser.next()) == 3) {
                if (xmlPullParser.getDepth() <= n) return object2;
            }
            if (n2 == 1) return object2;
            if (n2 != 2) continue;
            theme = Xml.asAttributeSet((XmlPullParser)xmlPullParser);
            object2 = xmlPullParser.getName();
            if (((String)object2).equals("linearInterpolator")) {
                object2 = new LinearInterpolator();
                continue;
            }
            if (((String)object2).equals("accelerateInterpolator")) {
                object2 = new AccelerateInterpolator((Context)object, (AttributeSet)theme);
                continue;
            }
            if (((String)object2).equals("decelerateInterpolator")) {
                object2 = new DecelerateInterpolator((Context)object, (AttributeSet)theme);
                continue;
            }
            if (((String)object2).equals("accelerateDecelerateInterpolator")) {
                object2 = new AccelerateDecelerateInterpolator();
                continue;
            }
            if (((String)object2).equals("cycleInterpolator")) {
                object2 = new CycleInterpolator((Context)object, (AttributeSet)theme);
                continue;
            }
            if (((String)object2).equals("anticipateInterpolator")) {
                object2 = new AnticipateInterpolator((Context)object, (AttributeSet)theme);
                continue;
            }
            if (((String)object2).equals("overshootInterpolator")) {
                object2 = new OvershootInterpolator((Context)object, (AttributeSet)theme);
                continue;
            }
            if (((String)object2).equals("anticipateOvershootInterpolator")) {
                object2 = new AnticipateOvershootInterpolator((Context)object, (AttributeSet)theme);
                continue;
            }
            if (((String)object2).equals("bounceInterpolator")) {
                object2 = new BounceInterpolator();
                continue;
            }
            if (!((String)object2).equals("pathInterpolator")) {
                object = new StringBuilder();
                ((StringBuilder)object).append("Unknown interpolator name: ");
                ((StringBuilder)object).append(xmlPullParser.getName());
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            object2 = new PathInterpolatorCompat((Context)object, (AttributeSet)theme, xmlPullParser);
        } while (true);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    public static Interpolator loadInterpolator(Context var0, int var1_2) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT >= 21) {
            return AnimationUtils.loadInterpolator((Context)var0 /* !! */ , (int)var1_2);
        }
        var2_3 = null;
        var3_5 = null;
        var4_7 = null;
        if (var1_2 != 17563663) ** GOTO lbl9
        try {
            return new FastOutLinearInInterpolator();
lbl9: // 1 sources:
            if (var1_2 == 17563661) {
                return new FastOutSlowInInterpolator();
            }
            if (var1_2 == 17563662) {
                return new LinearOutSlowInInterpolator();
            }
            var4_7 = var5_8 = var0 /* !! */ .getResources().getAnimation(var1_2);
            var2_3 = var5_8;
            var3_5 = var5_8;
            var0 /* !! */  = AnimationUtilsCompat.createInterpolatorFromXml(var0 /* !! */ , var0 /* !! */ .getResources(), var0 /* !! */ .getTheme(), var5_8);
            if (var5_8 == null) return var0 /* !! */ ;
        }
        catch (Throwable var0_1) {}
        var5_8.close();
        return var0 /* !! */ ;
        if (var4_7 == null) throw var0_1;
        var4_7.close();
        throw var0_1;
        catch (IOException var3_6) {}
        var4_7 = var2_3;
        {
            var4_7 = var2_3;
            var4_7 = var2_3;
            var5_9 = new StringBuilder();
            var4_7 = var2_3;
            var5_9.append("Can't load animation resource ID #0x");
            var4_7 = var2_3;
            var5_9.append(Integer.toHexString(var1_2));
            var4_7 = var2_3;
            var0 /* !! */  = new Resources.NotFoundException(var5_9.toString());
            var4_7 = var2_3;
            var0 /* !! */ .initCause((Throwable)var3_6);
            var4_7 = var2_3;
            throw var0 /* !! */ ;
            catch (XmlPullParserException var2_4) {}
            var4_7 = var3_5;
            var4_7 = var3_5;
            var4_7 = var3_5;
            var5_10 = new StringBuilder();
            var4_7 = var3_5;
            var5_10.append("Can't load animation resource ID #0x");
            var4_7 = var3_5;
            var5_10.append(Integer.toHexString(var1_2));
            var4_7 = var3_5;
            var0 /* !! */  = new Resources.NotFoundException(var5_10.toString());
            var4_7 = var3_5;
            var0 /* !! */ .initCause((Throwable)var2_4);
            var4_7 = var3_5;
            throw var0 /* !! */ ;
        }
    }
}


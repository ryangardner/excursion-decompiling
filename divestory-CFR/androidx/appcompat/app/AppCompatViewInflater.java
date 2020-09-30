/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.InflateException
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package androidx.appcompat.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import androidx.appcompat.R;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.appcompat.widget.AppCompatCheckedTextView;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.AppCompatImageButton;
import androidx.appcompat.widget.AppCompatImageView;
import androidx.appcompat.widget.AppCompatMultiAutoCompleteTextView;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.appcompat.widget.AppCompatRatingBar;
import androidx.appcompat.widget.AppCompatSeekBar;
import androidx.appcompat.widget.AppCompatSpinner;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.AppCompatToggleButton;
import androidx.appcompat.widget.TintContextWrapper;
import androidx.collection.SimpleArrayMap;
import androidx.core.view.ViewCompat;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class AppCompatViewInflater {
    private static final String LOG_TAG = "AppCompatViewInflater";
    private static final String[] sClassPrefixList;
    private static final SimpleArrayMap<String, Constructor<? extends View>> sConstructorMap;
    private static final Class<?>[] sConstructorSignature;
    private static final int[] sOnClickAttrs;
    private final Object[] mConstructorArgs = new Object[2];

    static {
        sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
        sOnClickAttrs = new int[]{16843375};
        sClassPrefixList = new String[]{"android.widget.", "android.view.", "android.webkit."};
        sConstructorMap = new SimpleArrayMap();
    }

    private void checkOnClickListener(View view, AttributeSet attributeSet) {
        Object object = view.getContext();
        if (!(object instanceof ContextWrapper)) return;
        if (Build.VERSION.SDK_INT >= 15 && !ViewCompat.hasOnClickListeners(view)) {
            return;
        }
        attributeSet = object.obtainStyledAttributes(attributeSet, sOnClickAttrs);
        object = attributeSet.getString(0);
        if (object != null) {
            view.setOnClickListener((View.OnClickListener)new DeclaredOnClickListener(view, (String)object));
        }
        attributeSet.recycle();
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private View createViewByPrefix(Context var1_1, String var2_3, String var3_4) throws ClassNotFoundException, InflateException {
        var4_5 = AppCompatViewInflater.sConstructorMap.get(var2_3);
        var5_6 = var4_5;
        if (var4_5 != null) ** GOTO lbl18
        if (var3_4 == null) ** GOTO lbl13
        try {
            block2 : {
                var5_6 = new Constructor<View>();
                var5_6.append(var3_4);
                var5_6.append(var2_3);
                var3_4 = var5_6.toString();
                break block2;
lbl13: // 1 sources:
                var3_4 = var2_3;
            }
            var5_6 = Class.forName(var3_4, false, var1_1.getClassLoader()).asSubclass(View.class).getConstructor(AppCompatViewInflater.sConstructorSignature);
            AppCompatViewInflater.sConstructorMap.put(var2_3, var5_6);
lbl18: // 2 sources:
            var5_6.setAccessible(true);
            return var5_6.newInstance(this.mConstructorArgs);
        }
        catch (Exception var1_2) {
            return null;
        }
    }

    private View createViewFromTag(Context arrobject, String view, AttributeSet attributeSet) {
        View view2 = view;
        if (view.equals("view")) {
            view2 = attributeSet.getAttributeValue(null, "class");
        }
        try {
            this.mConstructorArgs[0] = arrobject;
            this.mConstructorArgs[1] = attributeSet;
            if (-1 == view2.indexOf(46)) {
            } else {
                arrobject = this.createViewByPrefix((Context)arrobject, (String)view2, null);
                view = this.mConstructorArgs;
                view[0] = null;
                view[1] = null;
                return arrobject;
            }
            for (int i = 0; i < sClassPrefixList.length; ++i) {
                view = this.createViewByPrefix((Context)arrobject, (String)view2, sClassPrefixList[i]);
                if (view == null) continue;
                arrobject = this.mConstructorArgs;
                arrobject[0] = null;
                arrobject[1] = null;
                return view;
            }
            arrobject = this.mConstructorArgs;
            arrobject[0] = null;
            arrobject[1] = null;
            return null;
        }
        catch (Throwable throwable) {
            arrobject = this.mConstructorArgs;
            arrobject[0] = null;
            arrobject[1] = null;
            throw throwable;
        }
        catch (Exception exception) {
            Object[] arrobject2 = this.mConstructorArgs;
            arrobject2[0] = null;
            arrobject2[1] = null;
            return null;
        }
    }

    private static Context themifyContext(Context context, AttributeSet object, boolean bl, boolean bl2) {
        object = context.obtainStyledAttributes(object, R.styleable.View, 0, 0);
        int n = bl ? object.getResourceId(R.styleable.View_android_theme, 0) : 0;
        int n2 = n;
        if (bl2) {
            n2 = n;
            if (n == 0) {
                n2 = n = object.getResourceId(R.styleable.View_theme, 0);
                if (n != 0) {
                    Log.i((String)LOG_TAG, (String)"app:theme is now deprecated. Please move to using android:theme instead.");
                    n2 = n;
                }
            }
        }
        object.recycle();
        object = context;
        if (n2 == 0) return object;
        if (!(context instanceof ContextThemeWrapper)) return new ContextThemeWrapper(context, n2);
        object = context;
        if (((ContextThemeWrapper)context).getThemeResId() == n2) return object;
        return new ContextThemeWrapper(context, n2);
    }

    private void verifyNotNull(View object, String string2) {
        if (object != null) {
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append(this.getClass().getName());
        ((StringBuilder)object).append(" asked to inflate view for <");
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append(">, but returned null");
        throw new IllegalStateException(((StringBuilder)object).toString());
    }

    protected AppCompatAutoCompleteTextView createAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatAutoCompleteTextView(context, attributeSet);
    }

    protected AppCompatButton createButton(Context context, AttributeSet attributeSet) {
        return new AppCompatButton(context, attributeSet);
    }

    protected AppCompatCheckBox createCheckBox(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckBox(context, attributeSet);
    }

    protected AppCompatCheckedTextView createCheckedTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatCheckedTextView(context, attributeSet);
    }

    protected AppCompatEditText createEditText(Context context, AttributeSet attributeSet) {
        return new AppCompatEditText(context, attributeSet);
    }

    protected AppCompatImageButton createImageButton(Context context, AttributeSet attributeSet) {
        return new AppCompatImageButton(context, attributeSet);
    }

    protected AppCompatImageView createImageView(Context context, AttributeSet attributeSet) {
        return new AppCompatImageView(context, attributeSet);
    }

    protected AppCompatMultiAutoCompleteTextView createMultiAutoCompleteTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatMultiAutoCompleteTextView(context, attributeSet);
    }

    protected AppCompatRadioButton createRadioButton(Context context, AttributeSet attributeSet) {
        return new AppCompatRadioButton(context, attributeSet);
    }

    protected AppCompatRatingBar createRatingBar(Context context, AttributeSet attributeSet) {
        return new AppCompatRatingBar(context, attributeSet);
    }

    protected AppCompatSeekBar createSeekBar(Context context, AttributeSet attributeSet) {
        return new AppCompatSeekBar(context, attributeSet);
    }

    protected AppCompatSpinner createSpinner(Context context, AttributeSet attributeSet) {
        return new AppCompatSpinner(context, attributeSet);
    }

    protected AppCompatTextView createTextView(Context context, AttributeSet attributeSet) {
        return new AppCompatTextView(context, attributeSet);
    }

    protected AppCompatToggleButton createToggleButton(Context context, AttributeSet attributeSet) {
        return new AppCompatToggleButton(context, attributeSet);
    }

    protected View createView(Context context, String string2, AttributeSet attributeSet) {
        return null;
    }

    /*
     * Unable to fully structure code
     */
    final View createView(View var1_1, String var2_2, Context var3_3, AttributeSet var4_4, boolean var5_5, boolean var6_6, boolean var7_7, boolean var8_8) {
        block39 : {
            block38 : {
                var9_29 /* !! */  = var5_25 != false && var1_1 /* !! */  != null ? var1_1 /* !! */ .getContext() : var3_23;
                if (var6_26 != false) break block38;
                var1_2 = var9_29 /* !! */ ;
                if (var7_27 == false) break block39;
            }
            var1_4 = AppCompatViewInflater.themifyContext(var9_29 /* !! */ , (AttributeSet)var4_24, (boolean)var6_26, (boolean)var7_27);
        }
        var9_29 /* !! */  = var1_5;
        if (var8_28 != false) {
            var9_29 /* !! */  = TintContextWrapper.wrap((Context)var1_5);
        }
        var10_30 = -1;
        switch (var2_22.hashCode()) {
            default: {
                break;
            }
            case 2001146706: {
                if (!var2_22.equals("Button")) break;
                var10_30 = 2;
                break;
            }
            case 1666676343: {
                if (!var2_22.equals("EditText")) break;
                var10_30 = 3;
                break;
            }
            case 1601505219: {
                if (!var2_22.equals("CheckBox")) break;
                var10_30 = 6;
                break;
            }
            case 1413872058: {
                if (!var2_22.equals("AutoCompleteTextView")) break;
                var10_30 = 9;
                break;
            }
            case 1125864064: {
                if (!var2_22.equals("ImageView")) break;
                var10_30 = 1;
                break;
            }
            case 799298502: {
                if (!var2_22.equals("ToggleButton")) break;
                var10_30 = 13;
                break;
            }
            case 776382189: {
                if (!var2_22.equals("RadioButton")) break;
                var10_30 = 7;
                break;
            }
            case -339785223: {
                if (!var2_22.equals("Spinner")) break;
                var10_30 = 4;
                break;
            }
            case -658531749: {
                if (!var2_22.equals("SeekBar")) break;
                var10_30 = 12;
                break;
            }
            case -937446323: {
                if (!var2_22.equals("ImageButton")) break;
                var10_30 = 5;
                break;
            }
            case -938935918: {
                if (!var2_22.equals("TextView")) break;
                var10_30 = 0;
                break;
            }
            case -1346021293: {
                if (!var2_22.equals("MultiAutoCompleteTextView")) break;
                var10_30 = 10;
                break;
            }
            case -1455429095: {
                if (!var2_22.equals("CheckedTextView")) break;
                var10_30 = 8;
                break;
            }
            case -1946472170: {
                if (!var2_22.equals("RatingBar")) break;
                var10_30 = 11;
            }
        }
        switch (var10_30) {
            default: {
                var1_6 = this.createView(var9_29 /* !! */ , (String)var2_22, (AttributeSet)var4_24);
                ** break;
            }
            case 13: {
                var1_7 = this.createToggleButton(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_7, (String)var2_22);
                ** break;
            }
            case 12: {
                var1_8 = this.createSeekBar(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_8, (String)var2_22);
                ** break;
            }
            case 11: {
                var1_9 = this.createRatingBar(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_9, (String)var2_22);
                ** break;
            }
            case 10: {
                var1_10 = this.createMultiAutoCompleteTextView(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_10, (String)var2_22);
                ** break;
            }
            case 9: {
                var1_11 = this.createAutoCompleteTextView(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_11, (String)var2_22);
                ** break;
            }
            case 8: {
                var1_12 = this.createCheckedTextView(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_12, (String)var2_22);
                ** break;
            }
            case 7: {
                var1_13 = this.createRadioButton(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_13, (String)var2_22);
                ** break;
            }
            case 6: {
                var1_14 = this.createCheckBox(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_14, (String)var2_22);
                ** break;
            }
            case 5: {
                var1_15 = this.createImageButton(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_15, (String)var2_22);
                ** break;
            }
            case 4: {
                var1_16 = this.createSpinner(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_16, (String)var2_22);
                ** break;
            }
            case 3: {
                var1_17 = this.createEditText(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_17, (String)var2_22);
                ** break;
            }
            case 2: {
                var1_18 = this.createButton(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_18, (String)var2_22);
                ** break;
            }
            case 1: {
                var1_19 = this.createImageView(var9_29 /* !! */ , (AttributeSet)var4_24);
                this.verifyNotNull((View)var1_19, (String)var2_22);
                ** break;
            }
            case 0: 
        }
        var1_20 = this.createTextView(var9_29 /* !! */ , (AttributeSet)var4_24);
        this.verifyNotNull((View)var1_20, (String)var2_22);
lbl129: // 15 sources:
        var11_31 = var1_21;
        if (var1_21 == null) {
            var11_32 = var1_21;
            if (var3_23 != var9_29 /* !! */ ) {
                var11_33 = this.createViewFromTag(var9_29 /* !! */ , (String)var2_22, (AttributeSet)var4_24);
            }
        }
        if (var11_34 == null) return var11_34;
        this.checkOnClickListener((View)var11_34, (AttributeSet)var4_24);
        return var11_34;
    }

    private static class DeclaredOnClickListener
    implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(View view, String string2) {
            this.mHostView = view;
            this.mMethodName = string2;
        }

        private void resolveMethod(Context object) {
            Object object2;
            while (object != null) {
                try {
                    if (!object.isRestricted()) {
                        object2 = object.getClass().getMethod(this.mMethodName, View.class);
                        if (object2 != null) {
                            this.mResolvedMethod = object2;
                            this.mResolvedContext = object;
                            return;
                        }
                    }
                }
                catch (NoSuchMethodException noSuchMethodException) {
                    // empty catch block
                }
                if (object instanceof ContextWrapper) {
                    object = ((ContextWrapper)object).getBaseContext();
                    continue;
                }
                object = null;
            }
            int n = this.mHostView.getId();
            if (n == -1) {
                object = "";
            } else {
                object = new StringBuilder();
                ((StringBuilder)object).append(" with id '");
                ((StringBuilder)object).append(this.mHostView.getContext().getResources().getResourceEntryName(n));
                ((StringBuilder)object).append("'");
                object = ((StringBuilder)object).toString();
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("Could not find method ");
            ((StringBuilder)object2).append(this.mMethodName);
            ((StringBuilder)object2).append("(View) in a parent or ancestor Context for android:onClick attribute defined on view ");
            ((StringBuilder)object2).append(this.mHostView.getClass());
            ((StringBuilder)object2).append((String)object);
            throw new IllegalStateException(((StringBuilder)object2).toString());
        }

        public void onClick(View view) {
            if (this.mResolvedMethod == null) {
                this.resolveMethod(this.mHostView.getContext());
            }
            try {
                this.mResolvedMethod.invoke((Object)this.mResolvedContext, new Object[]{view});
                return;
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new IllegalStateException("Could not execute method for android:onClick", invocationTargetException);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", illegalAccessException);
            }
        }
    }

}


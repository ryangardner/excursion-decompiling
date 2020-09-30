/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Color
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.Xml
 *  android.view.View
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.View;
import androidx.constraintlayout.widget.R;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;

public class ConstraintAttribute {
    private static final String TAG = "TransitionLayout";
    boolean mBooleanValue;
    private int mColorValue;
    private float mFloatValue;
    private int mIntegerValue;
    String mName;
    private String mStringValue;
    private AttributeType mType;

    public ConstraintAttribute(ConstraintAttribute constraintAttribute, Object object) {
        this.mName = constraintAttribute.mName;
        this.mType = constraintAttribute.mType;
        this.setValue(object);
    }

    public ConstraintAttribute(String string2, AttributeType attributeType) {
        this.mName = string2;
        this.mType = attributeType;
    }

    public ConstraintAttribute(String string2, AttributeType attributeType, Object object) {
        this.mName = string2;
        this.mType = attributeType;
        this.setValue(object);
    }

    private static int clamp(int n) {
        n = (n & n >> 31) - 255;
        return (n & n >> 31) + 255;
    }

    public static HashMap<String, ConstraintAttribute> extractAttributes(HashMap<String, ConstraintAttribute> hashMap, View view) {
        HashMap<String, ConstraintAttribute> hashMap2 = new HashMap<String, ConstraintAttribute>();
        Class<?> class_ = view.getClass();
        Iterator<String> iterator2 = hashMap.keySet().iterator();
        while (iterator2.hasNext()) {
            String string2 = iterator2.next();
            ConstraintAttribute constraintAttribute = hashMap.get(string2);
            try {
                Object object;
                if (string2.equals("BackgroundColor")) {
                    int n = ((ColorDrawable)view.getBackground()).getColor();
                    object = new ConstraintAttribute(constraintAttribute, n);
                    hashMap2.put(string2, (ConstraintAttribute)object);
                    continue;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("getMap");
                ((StringBuilder)object).append(string2);
                object = class_.getMethod(((StringBuilder)object).toString(), new Class[0]).invoke((Object)view, new Object[0]);
                ConstraintAttribute constraintAttribute2 = new ConstraintAttribute(constraintAttribute, object);
                hashMap2.put(string2, constraintAttribute2);
            }
            catch (InvocationTargetException invocationTargetException) {
                invocationTargetException.printStackTrace();
            }
            catch (IllegalAccessException illegalAccessException) {
                illegalAccessException.printStackTrace();
            }
            catch (NoSuchMethodException noSuchMethodException) {
                noSuchMethodException.printStackTrace();
            }
        }
        return hashMap2;
    }

    public static void parse(Context context, XmlPullParser object, HashMap<String, ConstraintAttribute> hashMap) {
        Boolean bl;
        TypedArray typedArray = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)object), R.styleable.CustomAttribute);
        int n = typedArray.getIndexCount();
        Object object2 = null;
        Boolean bl2 = bl = null;
        for (int i = 0; i < n; ++i) {
            Object object3;
            Object object4;
            block9 : {
                block12 : {
                    int n2;
                    block17 : {
                        block16 : {
                            block15 : {
                                block14 : {
                                    block13 : {
                                        block11 : {
                                            block10 : {
                                                block8 : {
                                                    n2 = typedArray.getIndex(i);
                                                    if (n2 != R.styleable.CustomAttribute_attributeName) break block8;
                                                    object = object2 = typedArray.getString(n2);
                                                    object3 = bl;
                                                    object4 = bl2;
                                                    if (object2 != null) {
                                                        object = object2;
                                                        object3 = bl;
                                                        object4 = bl2;
                                                        if (((String)object2).length() > 0) {
                                                            object = new StringBuilder();
                                                            ((StringBuilder)object).append(Character.toUpperCase(((String)object2).charAt(0)));
                                                            ((StringBuilder)object).append(((String)object2).substring(1));
                                                            object = ((StringBuilder)object).toString();
                                                            object3 = bl;
                                                            object4 = bl2;
                                                        }
                                                    }
                                                    break block9;
                                                }
                                                if (n2 != R.styleable.CustomAttribute_customBoolean) break block10;
                                                object3 = typedArray.getBoolean(n2, false);
                                                object4 = AttributeType.BOOLEAN_TYPE;
                                                object = object2;
                                                break block9;
                                            }
                                            if (n2 != R.styleable.CustomAttribute_customColorValue) break block11;
                                            object = AttributeType.COLOR_TYPE;
                                            object3 = typedArray.getColor(n2, 0);
                                            break block12;
                                        }
                                        if (n2 != R.styleable.CustomAttribute_customColorDrawableValue) break block13;
                                        object = AttributeType.COLOR_DRAWABLE_TYPE;
                                        object3 = typedArray.getColor(n2, 0);
                                        break block12;
                                    }
                                    if (n2 != R.styleable.CustomAttribute_customPixelDimension) break block14;
                                    object = AttributeType.DIMENSION_TYPE;
                                    object3 = Float.valueOf(TypedValue.applyDimension((int)1, (float)typedArray.getDimension(n2, 0.0f), (DisplayMetrics)context.getResources().getDisplayMetrics()));
                                    break block12;
                                }
                                if (n2 != R.styleable.CustomAttribute_customDimension) break block15;
                                object = AttributeType.DIMENSION_TYPE;
                                object3 = Float.valueOf(typedArray.getDimension(n2, 0.0f));
                                break block12;
                            }
                            if (n2 != R.styleable.CustomAttribute_customFloatValue) break block16;
                            object = AttributeType.FLOAT_TYPE;
                            object3 = Float.valueOf(typedArray.getFloat(n2, Float.NaN));
                            break block12;
                        }
                        if (n2 != R.styleable.CustomAttribute_customIntegerValue) break block17;
                        object = AttributeType.INT_TYPE;
                        object3 = typedArray.getInteger(n2, -1);
                        break block12;
                    }
                    object = object2;
                    object3 = bl;
                    object4 = bl2;
                    if (n2 != R.styleable.CustomAttribute_customStringValue) break block9;
                    object = AttributeType.STRING_TYPE;
                    object3 = typedArray.getString(n2);
                }
                object4 = object;
                object = object2;
            }
            object2 = object;
            bl = object3;
            bl2 = object4;
        }
        if (object2 != null && bl != null) {
            hashMap.put((String)object2, new ConstraintAttribute((String)object2, (AttributeType)((Object)bl2), bl));
        }
        typedArray.recycle();
    }

    public static void setAttributes(View view, HashMap<String, ConstraintAttribute> hashMap) {
        Class<?> class_ = view.getClass();
        Iterator<String> iterator2 = hashMap.keySet().iterator();
        block13 : while (iterator2.hasNext()) {
            CharSequence charSequence = iterator2.next();
            Object object = hashMap.get(charSequence);
            CharSequence charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append("set");
            ((StringBuilder)charSequence2).append((String)charSequence);
            charSequence2 = ((StringBuilder)charSequence2).toString();
            try {
                switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[((ConstraintAttribute)object).mType.ordinal()]) {
                    default: {
                        continue block13;
                    }
                    case 7: {
                        class_.getMethod((String)charSequence2, Float.TYPE).invoke((Object)view, Float.valueOf(((ConstraintAttribute)object).mFloatValue));
                        continue block13;
                    }
                    case 6: {
                        class_.getMethod((String)charSequence2, Boolean.TYPE).invoke((Object)view, ((ConstraintAttribute)object).mBooleanValue);
                        continue block13;
                    }
                    case 5: {
                        class_.getMethod((String)charSequence2, CharSequence.class).invoke((Object)view, ((ConstraintAttribute)object).mStringValue);
                        continue block13;
                    }
                    case 4: {
                        class_.getMethod((String)charSequence2, Float.TYPE).invoke((Object)view, Float.valueOf(((ConstraintAttribute)object).mFloatValue));
                        continue block13;
                    }
                    case 3: {
                        class_.getMethod((String)charSequence2, Integer.TYPE).invoke((Object)view, ((ConstraintAttribute)object).mIntegerValue);
                        continue block13;
                    }
                    case 2: {
                        Method method = class_.getMethod((String)charSequence2, Drawable.class);
                        ColorDrawable colorDrawable = new ColorDrawable();
                        colorDrawable.setColor(((ConstraintAttribute)object).mColorValue);
                        method.invoke((Object)view, new Object[]{colorDrawable});
                        continue block13;
                    }
                    case 1: 
                }
                class_.getMethod((String)charSequence2, Integer.TYPE).invoke((Object)view, ((ConstraintAttribute)object).mColorValue);
            }
            catch (InvocationTargetException invocationTargetException) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append(" Custom Attribute \"");
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append("\" not found on ");
                ((StringBuilder)charSequence2).append(class_.getName());
                Log.e((String)TAG, (String)((StringBuilder)charSequence2).toString());
                invocationTargetException.printStackTrace();
            }
            catch (IllegalAccessException illegalAccessException) {
                charSequence2 = new StringBuilder();
                ((StringBuilder)charSequence2).append(" Custom Attribute \"");
                ((StringBuilder)charSequence2).append((String)charSequence);
                ((StringBuilder)charSequence2).append("\" not found on ");
                ((StringBuilder)charSequence2).append(class_.getName());
                Log.e((String)TAG, (String)((StringBuilder)charSequence2).toString());
                illegalAccessException.printStackTrace();
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.e((String)TAG, (String)noSuchMethodException.getMessage());
                object = new StringBuilder();
                ((StringBuilder)object).append(" Custom Attribute \"");
                ((StringBuilder)object).append((String)charSequence);
                ((StringBuilder)object).append("\" not found on ");
                ((StringBuilder)object).append(class_.getName());
                Log.e((String)TAG, (String)((StringBuilder)object).toString());
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append(class_.getName());
                ((StringBuilder)charSequence).append(" must have a method ");
                ((StringBuilder)charSequence).append((String)charSequence2);
                Log.e((String)TAG, (String)((StringBuilder)charSequence).toString());
            }
        }
    }

    public boolean diff(ConstraintAttribute constraintAttribute) {
        boolean bl = false;
        boolean bl2 = false;
        boolean bl3 = false;
        boolean bl4 = false;
        boolean bl5 = false;
        boolean bl6 = false;
        boolean bl7 = bl5;
        if (constraintAttribute == null) return bl7;
        if (this.mType != constraintAttribute.mType) {
            return bl5;
        }
        switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
            default: {
                return false;
            }
            case 7: {
                bl7 = bl6;
                if (this.mFloatValue != constraintAttribute.mFloatValue) return bl7;
                return true;
            }
            case 6: {
                bl7 = bl;
                if (this.mBooleanValue != constraintAttribute.mBooleanValue) return bl7;
                return true;
            }
            case 5: {
                bl7 = bl2;
                if (this.mIntegerValue != constraintAttribute.mIntegerValue) return bl7;
                return true;
            }
            case 4: {
                bl7 = bl3;
                if (this.mFloatValue != constraintAttribute.mFloatValue) return bl7;
                return true;
            }
            case 3: {
                bl7 = bl4;
                if (this.mIntegerValue != constraintAttribute.mIntegerValue) return bl7;
                return true;
            }
            case 1: 
            case 2: 
        }
        bl7 = bl5;
        if (this.mColorValue != constraintAttribute.mColorValue) return bl7;
        return true;
    }

    public AttributeType getType() {
        return this.mType;
    }

    public float getValueToInterpolate() {
        switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
            default: {
                return Float.NaN;
            }
            case 7: {
                return this.mFloatValue;
            }
            case 6: {
                if (!this.mBooleanValue) return 1.0f;
                return 0.0f;
            }
            case 5: {
                throw new RuntimeException("Cannot interpolate String");
            }
            case 4: {
                return this.mFloatValue;
            }
            case 3: {
                return this.mIntegerValue;
            }
            case 1: 
            case 2: 
        }
        throw new RuntimeException("Color does not have a single color to interpolate");
    }

    public void getValuesToInterpolate(float[] arrf) {
        switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
            default: {
                return;
            }
            case 7: {
                arrf[0] = this.mFloatValue;
                return;
            }
            case 6: {
                float f = this.mBooleanValue ? 0.0f : 1.0f;
                arrf[0] = f;
                return;
            }
            case 5: {
                throw new RuntimeException("Color does not have a single color to interpolate");
            }
            case 4: {
                arrf[0] = this.mFloatValue;
                return;
            }
            case 3: {
                arrf[0] = this.mIntegerValue;
                return;
            }
            case 1: 
            case 2: 
        }
        int n = this.mColorValue;
        float f = (float)Math.pow((float)(n >> 16 & 255) / 255.0f, 2.2);
        float f2 = (float)Math.pow((float)(n >> 8 & 255) / 255.0f, 2.2);
        float f3 = (float)Math.pow((float)(n & 255) / 255.0f, 2.2);
        arrf[0] = f;
        arrf[1] = f2;
        arrf[2] = f3;
        arrf[3] = (float)(n >> 24 & 255) / 255.0f;
    }

    public int noOfInterpValues() {
        int n = 1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()];
        if (n == 1) return 4;
        if (n == 2) return 4;
        return 1;
    }

    public void setColorValue(int n) {
        this.mColorValue = n;
    }

    public void setFloatValue(float f) {
        this.mFloatValue = f;
    }

    public void setIntValue(int n) {
        this.mIntegerValue = n;
    }

    /*
     * Exception decompiling
     */
    public void setInterpolatedValue(View var1_1, float[] var2_3) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [5[CASE]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    public void setStringValue(String string2) {
        this.mStringValue = string2;
    }

    public void setValue(Object object) {
        switch (1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()]) {
            default: {
                return;
            }
            case 7: {
                this.mFloatValue = ((Float)object).floatValue();
                return;
            }
            case 6: {
                this.mBooleanValue = (Boolean)object;
                return;
            }
            case 5: {
                this.mStringValue = (String)object;
                return;
            }
            case 4: {
                this.mFloatValue = ((Float)object).floatValue();
                return;
            }
            case 3: {
                this.mIntegerValue = (Integer)object;
                return;
            }
            case 1: 
            case 2: 
        }
        this.mColorValue = (Integer)object;
    }

    public void setValue(float[] arrf) {
        int n = 1.$SwitchMap$androidx$constraintlayout$widget$ConstraintAttribute$AttributeType[this.mType.ordinal()];
        boolean bl = false;
        switch (n) {
            default: {
                return;
            }
            case 7: {
                this.mFloatValue = arrf[0];
                return;
            }
            case 6: {
                if ((double)arrf[0] > 0.5) {
                    bl = true;
                }
                this.mBooleanValue = bl;
                return;
            }
            case 5: {
                throw new RuntimeException("Color does not have a single color to interpolate");
            }
            case 4: {
                this.mFloatValue = arrf[0];
                return;
            }
            case 3: {
                this.mIntegerValue = (int)arrf[0];
                return;
            }
            case 1: 
            case 2: 
        }
        this.mColorValue = n = Color.HSVToColor((float[])arrf);
        this.mColorValue = ConstraintAttribute.clamp((int)(arrf[3] * 255.0f)) << 24 | n & 16777215;
    }

    public static final class AttributeType
    extends Enum<AttributeType> {
        private static final /* synthetic */ AttributeType[] $VALUES;
        public static final /* enum */ AttributeType BOOLEAN_TYPE;
        public static final /* enum */ AttributeType COLOR_DRAWABLE_TYPE;
        public static final /* enum */ AttributeType COLOR_TYPE;
        public static final /* enum */ AttributeType DIMENSION_TYPE;
        public static final /* enum */ AttributeType FLOAT_TYPE;
        public static final /* enum */ AttributeType INT_TYPE;
        public static final /* enum */ AttributeType STRING_TYPE;

        static {
            AttributeType attributeType;
            INT_TYPE = new AttributeType();
            FLOAT_TYPE = new AttributeType();
            COLOR_TYPE = new AttributeType();
            COLOR_DRAWABLE_TYPE = new AttributeType();
            STRING_TYPE = new AttributeType();
            BOOLEAN_TYPE = new AttributeType();
            DIMENSION_TYPE = attributeType = new AttributeType();
            $VALUES = new AttributeType[]{INT_TYPE, FLOAT_TYPE, COLOR_TYPE, COLOR_DRAWABLE_TYPE, STRING_TYPE, BOOLEAN_TYPE, attributeType};
        }

        public static AttributeType valueOf(String string2) {
            return Enum.valueOf(AttributeType.class, string2);
        }

        public static AttributeType[] values() {
            return (AttributeType[])$VALUES.clone();
        }
    }

}


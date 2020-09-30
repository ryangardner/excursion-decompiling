/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.AnimatorInflater
 *  android.animation.AnimatorSet
 *  android.animation.Keyframe
 *  android.animation.ObjectAnimator
 *  android.animation.PropertyValuesHolder
 *  android.animation.TimeInterpolator
 *  android.animation.TypeEvaluator
 *  android.animation.ValueAnimator
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.graphics.Path
 *  android.graphics.PathMeasure
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.Xml
 *  android.view.InflateException
 */
package androidx.vectordrawable.graphics.drawable;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.animation.TimeInterpolator;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import android.view.InflateException;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.graphics.PathParser;
import androidx.vectordrawable.graphics.drawable.AndroidResources;
import androidx.vectordrawable.graphics.drawable.AnimationUtilsCompat;
import androidx.vectordrawable.graphics.drawable.ArgbEvaluator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class AnimatorInflaterCompat {
    private static final boolean DBG_ANIMATOR_INFLATER = false;
    private static final int MAX_NUM_POINTS = 100;
    private static final String TAG = "AnimatorInflater";
    private static final int TOGETHER = 0;
    private static final int VALUE_TYPE_COLOR = 3;
    private static final int VALUE_TYPE_FLOAT = 0;
    private static final int VALUE_TYPE_INT = 1;
    private static final int VALUE_TYPE_PATH = 2;
    private static final int VALUE_TYPE_UNDEFINED = 4;

    private AnimatorInflaterCompat() {
    }

    private static Animator createAnimatorFromXml(Context context, Resources resources, Resources.Theme theme, XmlPullParser xmlPullParser, float f) throws XmlPullParserException, IOException {
        return AnimatorInflaterCompat.createAnimatorFromXml(context, resources, theme, xmlPullParser, Xml.asAttributeSet((XmlPullParser)xmlPullParser), null, 0, f);
    }

    private static Animator createAnimatorFromXml(Context object, Resources object2, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet, AnimatorSet animatorSet, int n, float f) throws XmlPullParserException, IOException {
        int n2;
        int n3;
        int n4 = xmlPullParser.getDepth();
        PropertyValuesHolder[] arrpropertyValuesHolder = null;
        ArrayList<PropertyValuesHolder[]> arrayList = null;
        do {
            int n5 = xmlPullParser.next();
            n2 = 0;
            n3 = 0;
            if (n5 == 3 && xmlPullParser.getDepth() <= n4 || n5 == 1) break;
            if (n5 != 2) continue;
            PropertyValuesHolder[] arrpropertyValuesHolder2 = xmlPullParser.getName();
            if (arrpropertyValuesHolder2.equals("objectAnimator")) {
                arrpropertyValuesHolder2 = AnimatorInflaterCompat.loadObjectAnimator((Context)object, (Resources)object2, theme, attributeSet, f, xmlPullParser);
            } else if (arrpropertyValuesHolder2.equals("animator")) {
                arrpropertyValuesHolder2 = AnimatorInflaterCompat.loadAnimator((Context)object, (Resources)object2, theme, attributeSet, null, f, xmlPullParser);
            } else if (arrpropertyValuesHolder2.equals("set")) {
                arrpropertyValuesHolder2 = new AnimatorSet();
                arrpropertyValuesHolder = TypedArrayUtils.obtainAttributes((Resources)object2, theme, attributeSet, AndroidResources.STYLEABLE_ANIMATOR_SET);
                n2 = TypedArrayUtils.getNamedInt((TypedArray)arrpropertyValuesHolder, xmlPullParser, "ordering", 0, 0);
                AnimatorInflaterCompat.createAnimatorFromXml((Context)object, (Resources)object2, theme, xmlPullParser, attributeSet, (AnimatorSet)arrpropertyValuesHolder2, n2, f);
                arrpropertyValuesHolder.recycle();
            } else {
                if (!arrpropertyValuesHolder2.equals("propertyValuesHolder")) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown animator name: ");
                    ((StringBuilder)object).append(xmlPullParser.getName());
                    throw new RuntimeException(((StringBuilder)object).toString());
                }
                arrpropertyValuesHolder2 = AnimatorInflaterCompat.loadValues((Context)object, (Resources)object2, theme, xmlPullParser, Xml.asAttributeSet((XmlPullParser)xmlPullParser));
                if (arrpropertyValuesHolder2 != null && arrpropertyValuesHolder instanceof ValueAnimator) {
                    ((ValueAnimator)arrpropertyValuesHolder).setValues(arrpropertyValuesHolder2);
                }
                n3 = 1;
                arrpropertyValuesHolder2 = arrpropertyValuesHolder;
            }
            arrpropertyValuesHolder = arrpropertyValuesHolder2;
            if (animatorSet == null) continue;
            arrpropertyValuesHolder = arrpropertyValuesHolder2;
            if (n3 != 0) continue;
            ArrayList<PropertyValuesHolder[]> arrayList2 = arrayList;
            if (arrayList == null) {
                arrayList2 = new ArrayList<PropertyValuesHolder[]>();
            }
            arrayList2.add(arrpropertyValuesHolder2);
            arrpropertyValuesHolder = arrpropertyValuesHolder2;
            arrayList = arrayList2;
        } while (true);
        if (animatorSet == null) return arrpropertyValuesHolder;
        if (arrayList == null) return arrpropertyValuesHolder;
        object = new Animator[arrayList.size()];
        object2 = arrayList.iterator();
        n3 = n2;
        while (object2.hasNext()) {
            object[n3] = (Animator)object2.next();
            ++n3;
        }
        if (n == 0) {
            animatorSet.playTogether((Animator[])object);
            return arrpropertyValuesHolder;
        }
        animatorSet.playSequentially((Animator[])object);
        return arrpropertyValuesHolder;
    }

    private static Keyframe createNewKeyframe(Keyframe keyframe, float f) {
        if (keyframe.getType() == Float.TYPE) {
            return Keyframe.ofFloat((float)f);
        }
        if (keyframe.getType() != Integer.TYPE) return Keyframe.ofObject((float)f);
        return Keyframe.ofInt((float)f);
    }

    private static void distributeKeyframes(Keyframe[] arrkeyframe, float f, int n, int n2) {
        f /= (float)(n2 - n + 2);
        while (n <= n2) {
            arrkeyframe[n].setFraction(arrkeyframe[n - 1].getFraction() + f);
            ++n;
        }
    }

    private static void dumpKeyframes(Object[] arrobject, String object) {
        if (arrobject == null) return;
        if (arrobject.length == 0) {
            return;
        }
        Log.d((String)TAG, (String)object);
        int n = arrobject.length;
        int n2 = 0;
        while (n2 < n) {
            Keyframe keyframe = (Keyframe)arrobject[n2];
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Keyframe ");
            stringBuilder.append(n2);
            stringBuilder.append(": fraction ");
            float f = keyframe.getFraction();
            String string2 = "null";
            object = f < 0.0f ? "null" : Float.valueOf(keyframe.getFraction());
            stringBuilder.append(object);
            stringBuilder.append(", , value : ");
            object = string2;
            if (keyframe.hasValue()) {
                object = keyframe.getValue();
            }
            stringBuilder.append(object);
            Log.d((String)TAG, (String)stringBuilder.toString());
            ++n2;
        }
    }

    private static PropertyValuesHolder getPVH(TypedArray object, int n, int n2, int n3, String string2) {
        Object object2 = object.peekValue(n2);
        boolean bl = object2 != null;
        int n4 = bl ? ((TypedValue)object2).type : 0;
        object2 = object.peekValue(n3);
        boolean bl2 = object2 != null;
        int n5 = bl2 ? ((TypedValue)object2).type : 0;
        int n6 = n;
        if (n == 4) {
            n6 = bl && AnimatorInflaterCompat.isColorType(n4) || bl2 && AnimatorInflaterCompat.isColorType(n5) ? 3 : 0;
        }
        n = n6 == 0 ? 1 : 0;
        object2 = null;
        String string3 = null;
        if (n6 == 2) {
            String string4 = object.getString(n2);
            string3 = object.getString(n3);
            PathParser.PathDataNode[] arrpathDataNode = PathParser.createNodesFromPathData(string4);
            PathParser.PathDataNode[] arrpathDataNode2 = PathParser.createNodesFromPathData(string3);
            if (arrpathDataNode == null) {
                object = object2;
                if (arrpathDataNode2 == null) return object;
            }
            if (arrpathDataNode == null) {
                object = object2;
                if (arrpathDataNode2 == null) return object;
                return PropertyValuesHolder.ofObject((String)string2, (TypeEvaluator)new PathDataEvaluator(), (Object[])new Object[]{arrpathDataNode2});
            }
            object = new PathDataEvaluator();
            if (arrpathDataNode2 == null) {
                return PropertyValuesHolder.ofObject((String)string2, (TypeEvaluator)object, (Object[])new Object[]{arrpathDataNode});
            }
            if (PathParser.canMorph(arrpathDataNode, arrpathDataNode2)) {
                return PropertyValuesHolder.ofObject((String)string2, (TypeEvaluator)object, (Object[])new Object[]{arrpathDataNode, arrpathDataNode2});
            }
            object = new StringBuilder();
            ((StringBuilder)object).append(" Can't morph from ");
            ((StringBuilder)object).append(string4);
            ((StringBuilder)object).append(" to ");
            ((StringBuilder)object).append(string3);
            throw new InflateException(((StringBuilder)object).toString());
        }
        ArgbEvaluator argbEvaluator = n6 == 3 ? ArgbEvaluator.getInstance() : null;
        if (n != 0) {
            if (bl) {
                float f = n4 == 5 ? object.getDimension(n2, 0.0f) : object.getFloat(n2, 0.0f);
                if (bl2) {
                    float f2 = n5 == 5 ? object.getDimension(n3, 0.0f) : object.getFloat(n3, 0.0f);
                    object = PropertyValuesHolder.ofFloat((String)string2, (float[])new float[]{f, f2});
                } else {
                    object = PropertyValuesHolder.ofFloat((String)string2, (float[])new float[]{f});
                }
            } else {
                float f = n5 == 5 ? object.getDimension(n3, 0.0f) : object.getFloat(n3, 0.0f);
                object = PropertyValuesHolder.ofFloat((String)string2, (float[])new float[]{f});
            }
            object2 = object;
        } else if (bl) {
            n = n4 == 5 ? (int)object.getDimension(n2, 0.0f) : (AnimatorInflaterCompat.isColorType(n4) ? object.getColor(n2, 0) : object.getInt(n2, 0));
            if (bl2) {
                n2 = n5 == 5 ? (int)object.getDimension(n3, 0.0f) : (AnimatorInflaterCompat.isColorType(n5) ? object.getColor(n3, 0) : object.getInt(n3, 0));
                object2 = PropertyValuesHolder.ofInt((String)string2, (int[])new int[]{n, n2});
            } else {
                object2 = PropertyValuesHolder.ofInt((String)string2, (int[])new int[]{n});
            }
        } else {
            object2 = string3;
            if (bl2) {
                n = n5 == 5 ? (int)object.getDimension(n3, 0.0f) : (AnimatorInflaterCompat.isColorType(n5) ? object.getColor(n3, 0) : object.getInt(n3, 0));
                object2 = PropertyValuesHolder.ofInt((String)string2, (int[])new int[]{n});
            }
        }
        object = object2;
        if (object2 == null) return object;
        object = object2;
        if (argbEvaluator == null) return object;
        object2.setEvaluator((TypeEvaluator)argbEvaluator);
        return object2;
    }

    private static int inferValueTypeFromValues(TypedArray object, int n, int n2) {
        TypedValue typedValue = object.peekValue(n);
        int n3 = 1;
        int n4 = 0;
        n = typedValue != null ? 1 : 0;
        int n5 = n != 0 ? typedValue.type : 0;
        object = object.peekValue(n2);
        n2 = object != null ? n3 : 0;
        n3 = n2 != 0 ? object.type : 0;
        if (n != 0) {
            if (AnimatorInflaterCompat.isColorType(n5)) return 3;
        }
        n = n4;
        if (n2 == 0) return n;
        n = n4;
        if (!AnimatorInflaterCompat.isColorType(n3)) return n;
        return 3;
    }

    private static int inferValueTypeOfKeyframe(Resources resources, Resources.Theme object, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        resources = TypedArrayUtils.obtainAttributes(resources, object, attributeSet, AndroidResources.STYLEABLE_KEYFRAME);
        int n = 0;
        object = TypedArrayUtils.peekNamedValue((TypedArray)resources, xmlPullParser, "value", 0);
        boolean bl = object != null;
        int n2 = n;
        if (bl) {
            n2 = n;
            if (AnimatorInflaterCompat.isColorType(object.type)) {
                n2 = 3;
            }
        }
        resources.recycle();
        return n2;
    }

    private static boolean isColorType(int n) {
        if (n < 28) return false;
        if (n > 31) return false;
        return true;
    }

    public static Animator loadAnimator(Context context, int n) throws Resources.NotFoundException {
        if (Build.VERSION.SDK_INT < 24) return AnimatorInflaterCompat.loadAnimator(context, context.getResources(), context.getTheme(), n);
        return AnimatorInflater.loadAnimator((Context)context, (int)n);
    }

    public static Animator loadAnimator(Context context, Resources resources, Resources.Theme theme, int n) throws Resources.NotFoundException {
        return AnimatorInflaterCompat.loadAnimator(context, resources, theme, n, 1.0f);
    }

    /*
     * Exception decompiling
     */
    public static Animator loadAnimator(Context var0, Resources var1_2, Resources.Theme var2_4, int var3_6, float var4_7) throws Resources.NotFoundException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 1[TRYBLOCK]
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

    private static ValueAnimator loadAnimator(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, ValueAnimator valueAnimator, float f, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
        TypedArray typedArray = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_ANIMATOR);
        theme = TypedArrayUtils.obtainAttributes(resources, theme, attributeSet, AndroidResources.STYLEABLE_PROPERTY_ANIMATOR);
        resources = valueAnimator;
        if (valueAnimator == null) {
            resources = new ValueAnimator();
        }
        AnimatorInflaterCompat.parseAnimatorFromTypeArray((ValueAnimator)resources, typedArray, (TypedArray)theme, f, xmlPullParser);
        int n = TypedArrayUtils.getNamedResourceId(typedArray, xmlPullParser, "interpolator", 0, 0);
        if (n > 0) {
            resources.setInterpolator((TimeInterpolator)AnimationUtilsCompat.loadInterpolator(context, n));
        }
        typedArray.recycle();
        if (theme == null) return resources;
        theme.recycle();
        return resources;
    }

    private static Keyframe loadKeyframe(Context context, Resources object, Resources.Theme theme, AttributeSet attributeSet, int n, XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        theme = TypedArrayUtils.obtainAttributes(object, theme, attributeSet, AndroidResources.STYLEABLE_KEYFRAME);
        float f = TypedArrayUtils.getNamedFloat((TypedArray)theme, xmlPullParser, "fraction", 3, -1.0f);
        object = TypedArrayUtils.peekNamedValue((TypedArray)theme, xmlPullParser, "value", 0);
        boolean bl = object != null;
        int n2 = n;
        if (n == 4) {
            n2 = bl && AnimatorInflaterCompat.isColorType(object.type) ? 3 : 0;
        }
        object = bl ? (n2 != 0 ? (n2 != 1 && n2 != 3 ? null : Keyframe.ofInt((float)f, (int)TypedArrayUtils.getNamedInt((TypedArray)theme, xmlPullParser, "value", 0, 0))) : Keyframe.ofFloat((float)f, (float)TypedArrayUtils.getNamedFloat((TypedArray)theme, xmlPullParser, "value", 0, 0.0f))) : (n2 == 0 ? Keyframe.ofFloat((float)f) : Keyframe.ofInt((float)f));
        n = TypedArrayUtils.getNamedResourceId((TypedArray)theme, xmlPullParser, "interpolator", 1, 0);
        if (n > 0) {
            object.setInterpolator((TimeInterpolator)AnimationUtilsCompat.loadInterpolator(context, n));
        }
        theme.recycle();
        return object;
    }

    private static ObjectAnimator loadObjectAnimator(Context context, Resources resources, Resources.Theme theme, AttributeSet attributeSet, float f, XmlPullParser xmlPullParser) throws Resources.NotFoundException {
        ObjectAnimator objectAnimator = new ObjectAnimator();
        AnimatorInflaterCompat.loadAnimator(context, resources, theme, attributeSet, (ValueAnimator)objectAnimator, f, xmlPullParser);
        return objectAnimator;
    }

    private static PropertyValuesHolder loadPvh(Context resources, Resources resources2, Resources.Theme theme, XmlPullParser xmlPullParser, String string2, int n) throws XmlPullParserException, IOException {
        Object var6_6 = null;
        ArrayList<Keyframe> arrayList = null;
        int n2 = n;
        while ((n = xmlPullParser.next()) != 3 && n != 1) {
            if (!xmlPullParser.getName().equals("keyframe")) continue;
            n = n2;
            if (n2 == 4) {
                n = AnimatorInflaterCompat.inferValueTypeOfKeyframe(resources2, theme, Xml.asAttributeSet((XmlPullParser)xmlPullParser), xmlPullParser);
            }
            Keyframe keyframe = AnimatorInflaterCompat.loadKeyframe((Context)resources, resources2, theme, Xml.asAttributeSet((XmlPullParser)xmlPullParser), n, xmlPullParser);
            ArrayList<Keyframe> arrayList2 = arrayList;
            if (keyframe != null) {
                arrayList2 = arrayList;
                if (arrayList == null) {
                    arrayList2 = new ArrayList<Keyframe>();
                }
                arrayList2.add(keyframe);
            }
            xmlPullParser.next();
            arrayList = arrayList2;
            n2 = n;
        }
        resources = var6_6;
        if (arrayList == null) return resources;
        int n3 = arrayList.size();
        resources = var6_6;
        if (n3 <= 0) return resources;
        int n4 = 0;
        resources = (Keyframe)arrayList.get(0);
        resources2 = (Keyframe)arrayList.get(n3 - 1);
        float f = resources2.getFraction();
        n = n3;
        if (f < 1.0f) {
            if (f < 0.0f) {
                resources2.setFraction(1.0f);
                n = n3;
            } else {
                arrayList.add(arrayList.size(), AnimatorInflaterCompat.createNewKeyframe((Keyframe)resources2, 1.0f));
                n = n3 + 1;
            }
        }
        f = resources.getFraction();
        n3 = n;
        if (f != 0.0f) {
            if (f < 0.0f) {
                resources.setFraction(0.0f);
                n3 = n;
            } else {
                arrayList.add(0, AnimatorInflaterCompat.createNewKeyframe((Keyframe)resources, 0.0f));
                n3 = n + 1;
            }
        }
        resources = new Keyframe[n3];
        arrayList.toArray((T[])resources);
        n = n4;
        do {
            if (n >= n3) {
                resources = resources2 = PropertyValuesHolder.ofKeyframe((String)string2, (Keyframe[])resources);
                if (n2 != 3) return resources;
                resources2.setEvaluator((TypeEvaluator)ArgbEvaluator.getInstance());
                return resources2;
            }
            resources2 = resources[n];
            if (resources2.getFraction() < 0.0f) {
                if (n == 0) {
                    resources2.setFraction(0.0f);
                } else {
                    int n5 = n3 - 1;
                    if (n == n5) {
                        resources2.setFraction(1.0f);
                    } else {
                        n4 = n + 1;
                        int n6 = n;
                        while (n4 < n5 && !(resources[n4].getFraction() >= 0.0f)) {
                            n6 = n4++;
                        }
                        AnimatorInflaterCompat.distributeKeyframes((Keyframe[])resources, resources[n6 + 1].getFraction() - resources[n - 1].getFraction(), n, n6);
                    }
                }
            }
            ++n;
        } while (true);
    }

    private static PropertyValuesHolder[] loadValues(Context arrpropertyValuesHolder, Resources arrpropertyValuesHolder2, Resources.Theme theme, XmlPullParser xmlPullParser, AttributeSet attributeSet) throws XmlPullParserException, IOException {
        int n;
        int n2;
        Object var5_5 = null;
        ArrayList arrayList = null;
        do {
            n = xmlPullParser.getEventType();
            n2 = 0;
            if (n == 3 || n == 1) break;
            if (n != 2) {
                xmlPullParser.next();
                continue;
            }
            if (xmlPullParser.getName().equals("propertyValuesHolder")) {
                TypedArray typedArray = TypedArrayUtils.obtainAttributes((Resources)arrpropertyValuesHolder2, theme, attributeSet, AndroidResources.STYLEABLE_PROPERTY_VALUES_HOLDER);
                String string2 = TypedArrayUtils.getNamedString(typedArray, xmlPullParser, "propertyName", 3);
                n2 = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "valueType", 2, 4);
                Object object = AnimatorInflaterCompat.loadPvh((Context)arrpropertyValuesHolder, (Resources)arrpropertyValuesHolder2, theme, xmlPullParser, string2, n2);
                PropertyValuesHolder propertyValuesHolder = object;
                if (object == null) {
                    propertyValuesHolder = AnimatorInflaterCompat.getPVH(typedArray, n2, 0, 1, string2);
                }
                object = arrayList;
                if (propertyValuesHolder != null) {
                    object = arrayList;
                    if (arrayList == null) {
                        object = new ArrayList();
                    }
                    ((ArrayList)object).add(propertyValuesHolder);
                }
                typedArray.recycle();
                arrayList = object;
            }
            xmlPullParser.next();
        } while (true);
        arrpropertyValuesHolder = var5_5;
        if (arrayList == null) return arrpropertyValuesHolder;
        n = arrayList.size();
        arrpropertyValuesHolder2 = new PropertyValuesHolder[n];
        do {
            arrpropertyValuesHolder = arrpropertyValuesHolder2;
            if (n2 >= n) return arrpropertyValuesHolder;
            arrpropertyValuesHolder2[n2] = (PropertyValuesHolder)arrayList.get(n2);
            ++n2;
        } while (true);
    }

    private static void parseAnimatorFromTypeArray(ValueAnimator valueAnimator, TypedArray typedArray, TypedArray typedArray2, float f, XmlPullParser xmlPullParser) {
        int n;
        long l = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "duration", 1, 300);
        long l2 = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "startOffset", 2, 0);
        int n2 = n = TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "valueType", 7, 4);
        if (TypedArrayUtils.hasAttribute(xmlPullParser, "valueFrom")) {
            n2 = n;
            if (TypedArrayUtils.hasAttribute(xmlPullParser, "valueTo")) {
                int n3 = n;
                if (n == 4) {
                    n3 = AnimatorInflaterCompat.inferValueTypeFromValues(typedArray, 5, 6);
                }
                PropertyValuesHolder propertyValuesHolder = AnimatorInflaterCompat.getPVH(typedArray, n3, 5, 6, "");
                n2 = n3;
                if (propertyValuesHolder != null) {
                    valueAnimator.setValues(new PropertyValuesHolder[]{propertyValuesHolder});
                    n2 = n3;
                }
            }
        }
        valueAnimator.setDuration(l);
        valueAnimator.setStartDelay(l2);
        valueAnimator.setRepeatCount(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "repeatCount", 3, 0));
        valueAnimator.setRepeatMode(TypedArrayUtils.getNamedInt(typedArray, xmlPullParser, "repeatMode", 4, 1));
        if (typedArray2 == null) return;
        AnimatorInflaterCompat.setupObjectAnimator(valueAnimator, typedArray2, n2, f, xmlPullParser);
    }

    private static void setupObjectAnimator(ValueAnimator object, TypedArray typedArray, int n, float f, XmlPullParser object2) {
        ObjectAnimator objectAnimator = (ObjectAnimator)object;
        object = TypedArrayUtils.getNamedString(typedArray, (XmlPullParser)object2, "pathData", 1);
        if (object == null) {
            objectAnimator.setPropertyName(TypedArrayUtils.getNamedString(typedArray, (XmlPullParser)object2, "propertyName", 0));
            return;
        }
        String string2 = TypedArrayUtils.getNamedString(typedArray, (XmlPullParser)object2, "propertyXName", 2);
        object2 = TypedArrayUtils.getNamedString(typedArray, (XmlPullParser)object2, "propertyYName", 3);
        if (n != 2) {
            // empty if block
        }
        if (string2 == null && object2 == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append(typedArray.getPositionDescription());
            ((StringBuilder)object).append(" propertyXName or propertyYName is needed for PathData");
            throw new InflateException(((StringBuilder)object).toString());
        }
        AnimatorInflaterCompat.setupPathMotion(PathParser.createPathFromPathData((String)object), objectAnimator, f * 0.5f, string2, (String)object2);
    }

    private static void setupPathMotion(Path object, ObjectAnimator objectAnimator, float f, String string2, String string3) {
        float f2;
        PathMeasure pathMeasure = new PathMeasure(object, false);
        ArrayList<Float> arrayList = new ArrayList<Float>();
        float f3 = 0.0f;
        arrayList.add(Float.valueOf(0.0f));
        float f4 = 0.0f;
        do {
            f2 = f4 + pathMeasure.getLength();
            arrayList.add(Float.valueOf(f2));
            f4 = f2;
        } while (pathMeasure.nextContour());
        object = new PathMeasure(object, false);
        int n = Math.min(100, (int)(f2 / f) + 1);
        float[] arrf = new float[n];
        float[] arrf2 = new float[n];
        float[] arrf3 = new float[2];
        f4 = f2 / (float)(n - 1);
        int n2 = 0;
        int n3 = 0;
        f = f3;
        do {
            pathMeasure = null;
            if (n2 >= n) break;
            object.getPosTan(f - ((Float)arrayList.get(n3)).floatValue(), arrf3, null);
            arrf[n2] = arrf3[0];
            arrf2[n2] = arrf3[1];
            f += f4;
            int n4 = n3 + 1;
            int n5 = n3;
            if (n4 < arrayList.size()) {
                n5 = n3;
                if (f > ((Float)arrayList.get(n4)).floatValue()) {
                    object.nextContour();
                    n5 = n4;
                }
            }
            ++n2;
            n3 = n5;
        } while (true);
        object = string2 != null ? PropertyValuesHolder.ofFloat((String)string2, (float[])arrf) : null;
        string2 = pathMeasure;
        if (string3 != null) {
            string2 = PropertyValuesHolder.ofFloat((String)string3, (float[])arrf2);
        }
        if (object == null) {
            objectAnimator.setValues(new PropertyValuesHolder[]{string2});
            return;
        }
        if (string2 == null) {
            objectAnimator.setValues(new PropertyValuesHolder[]{object});
            return;
        }
        objectAnimator.setValues(new PropertyValuesHolder[]{object, string2});
    }

    private static class PathDataEvaluator
    implements TypeEvaluator<PathParser.PathDataNode[]> {
        private PathParser.PathDataNode[] mNodeArray;

        PathDataEvaluator() {
        }

        PathDataEvaluator(PathParser.PathDataNode[] arrpathDataNode) {
            this.mNodeArray = arrpathDataNode;
        }

        public PathParser.PathDataNode[] evaluate(float f, PathParser.PathDataNode[] arrpathDataNode, PathParser.PathDataNode[] arrpathDataNode2) {
            if (!PathParser.canMorph(arrpathDataNode, arrpathDataNode2)) throw new IllegalArgumentException("Can't interpolate between two incompatible pathData");
            if (!PathParser.canMorph(this.mNodeArray, arrpathDataNode)) {
                this.mNodeArray = PathParser.deepCopyNodes(arrpathDataNode);
            }
            int n = 0;
            while (n < arrpathDataNode.length) {
                this.mNodeArray[n].interpolatePathDataNode(arrpathDataNode[n], arrpathDataNode2[n], f);
                ++n;
            }
            return this.mNodeArray;
        }
    }

}


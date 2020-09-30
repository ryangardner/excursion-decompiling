/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.TypedArray
 *  android.util.AttributeSet
 *  android.view.InflateException
 *  android.view.ViewGroup
 */
package androidx.transition;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.InflateException;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.content.res.TypedArrayUtils;
import androidx.transition.ArcMotion;
import androidx.transition.AutoTransition;
import androidx.transition.ChangeBounds;
import androidx.transition.ChangeClipBounds;
import androidx.transition.ChangeImageTransform;
import androidx.transition.ChangeScroll;
import androidx.transition.ChangeTransform;
import androidx.transition.Explode;
import androidx.transition.Fade;
import androidx.transition.PathMotion;
import androidx.transition.PatternPathMotion;
import androidx.transition.Scene;
import androidx.transition.Slide;
import androidx.transition.Styleable;
import androidx.transition.Transition;
import androidx.transition.TransitionManager;
import androidx.transition.TransitionSet;
import java.io.IOException;
import java.lang.reflect.Constructor;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class TransitionInflater {
    private static final ArrayMap<String, Constructor<?>> CONSTRUCTORS;
    private static final Class<?>[] CONSTRUCTOR_SIGNATURE;
    private final Context mContext;

    static {
        CONSTRUCTOR_SIGNATURE = new Class[]{Context.class, AttributeSet.class};
        CONSTRUCTORS = new ArrayMap();
    }

    private TransitionInflater(Context context) {
        this.mContext = context;
    }

    /*
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     * Converted monitor instructions to comments
     */
    private Object createCustom(AttributeSet object, Class<?> class_, String object22) {
        void var2_2;
        Constructor constructor;
        Constructor constructor2;
        String string2 = object.getAttributeValue(null, "class");
        if (string2 == null) {
            object = new StringBuilder();
            object.append((String)((Object)constructor2));
            object.append(" tag must have a 'class' attribute");
            throw new InflateException(object.toString());
        }
        try {
            ArrayMap<String, Constructor<?>> arrayMap = CONSTRUCTORS;
            // MONITORENTER : arrayMap
        }
        catch (Exception object22) {
            object = new StringBuilder();
            object.append("Could not instantiate ");
            object.append(var2_2);
            object.append(" class ");
            object.append(string2);
            throw new InflateException(object.toString(), (Throwable)object22);
        }
        constructor2 = constructor = (Constructor)CONSTRUCTORS.get(string2);
        if (constructor == null) {
            Class class_2 = Class.forName(string2, false, this.mContext.getClassLoader()).asSubclass(var2_2);
            constructor2 = constructor;
            if (class_2 != null) {
                constructor2 = class_2.getConstructor(CONSTRUCTOR_SIGNATURE);
                constructor2.setAccessible(true);
                CONSTRUCTORS.put(string2, constructor2);
            }
        }
        object = constructor2.newInstance(new Object[]{this.mContext, object});
        // MONITOREXIT : arrayMap
        return object;
    }

    private Transition createTransitionFromXml(XmlPullParser xmlPullParser, AttributeSet object, Transition transition) throws XmlPullParserException, IOException {
        int n = xmlPullParser.getDepth();
        TransitionSet transitionSet = transition instanceof TransitionSet ? (TransitionSet)transition : null;
        block0 : do {
            Object object2 = null;
            do {
                int n2;
                if ((n2 = xmlPullParser.next()) == 3) {
                    if (xmlPullParser.getDepth() <= n) return object2;
                }
                if (n2 == 1) return object2;
                if (n2 != 2) continue;
                Object object3 = xmlPullParser.getName();
                if ("fade".equals(object3)) {
                    object3 = new Fade(this.mContext, (AttributeSet)object);
                } else if ("changeBounds".equals(object3)) {
                    object3 = new ChangeBounds(this.mContext, (AttributeSet)object);
                } else if ("slide".equals(object3)) {
                    object3 = new Slide(this.mContext, (AttributeSet)object);
                } else if ("explode".equals(object3)) {
                    object3 = new Explode(this.mContext, (AttributeSet)object);
                } else if ("changeImageTransform".equals(object3)) {
                    object3 = new ChangeImageTransform(this.mContext, (AttributeSet)object);
                } else if ("changeTransform".equals(object3)) {
                    object3 = new ChangeTransform(this.mContext, (AttributeSet)object);
                } else if ("changeClipBounds".equals(object3)) {
                    object3 = new ChangeClipBounds(this.mContext, (AttributeSet)object);
                } else if ("autoTransition".equals(object3)) {
                    object3 = new AutoTransition(this.mContext, (AttributeSet)object);
                } else if ("changeScroll".equals(object3)) {
                    object3 = new ChangeScroll(this.mContext, (AttributeSet)object);
                } else if ("transitionSet".equals(object3)) {
                    object3 = new TransitionSet(this.mContext, (AttributeSet)object);
                } else if ("transition".equals(object3)) {
                    object3 = (Transition)this.createCustom((AttributeSet)object, Transition.class, "transition");
                } else if ("targets".equals(object3)) {
                    this.getTargetIds(xmlPullParser, (AttributeSet)object, transition);
                    object3 = object2;
                } else if ("arcMotion".equals(object3)) {
                    if (transition == null) throw new RuntimeException("Invalid use of arcMotion element");
                    transition.setPathMotion(new ArcMotion(this.mContext, (AttributeSet)object));
                    object3 = object2;
                } else if ("pathMotion".equals(object3)) {
                    if (transition == null) throw new RuntimeException("Invalid use of pathMotion element");
                    transition.setPathMotion((PathMotion)this.createCustom((AttributeSet)object, PathMotion.class, "pathMotion"));
                    object3 = object2;
                } else {
                    if (!"patternPathMotion".equals(object3)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Unknown scene name: ");
                        ((StringBuilder)object).append(xmlPullParser.getName());
                        throw new RuntimeException(((StringBuilder)object).toString());
                    }
                    if (transition == null) throw new RuntimeException("Invalid use of patternPathMotion element");
                    transition.setPathMotion(new PatternPathMotion(this.mContext, (AttributeSet)object));
                    object3 = object2;
                }
                object2 = object3;
                if (object3 == null) continue;
                if (!xmlPullParser.isEmptyElementTag()) {
                    this.createTransitionFromXml(xmlPullParser, (AttributeSet)object, (Transition)object3);
                }
                if (transitionSet != null) {
                    transitionSet.addTransition((Transition)object3);
                    continue block0;
                }
                if (transition != null) throw new InflateException("Could not add transition to another transition.");
                object2 = object3;
            } while (true);
            break;
        } while (true);
    }

    private TransitionManager createTransitionManagerFromXml(XmlPullParser xmlPullParser, AttributeSet object, ViewGroup viewGroup) throws XmlPullParserException, IOException {
        int n = xmlPullParser.getDepth();
        TransitionManager transitionManager = null;
        do {
            int n2;
            if ((n2 = xmlPullParser.next()) == 3) {
                if (xmlPullParser.getDepth() <= n) return transitionManager;
            }
            if (n2 == 1) return transitionManager;
            if (n2 != 2) continue;
            String string2 = xmlPullParser.getName();
            if (string2.equals("transitionManager")) {
                transitionManager = new TransitionManager();
                continue;
            }
            if (!string2.equals("transition") || transitionManager == null) break;
            this.loadTransition((AttributeSet)object, xmlPullParser, viewGroup, transitionManager);
        } while (true);
        object = new StringBuilder();
        ((StringBuilder)object).append("Unknown scene name: ");
        ((StringBuilder)object).append(xmlPullParser.getName());
        throw new RuntimeException(((StringBuilder)object).toString());
    }

    public static TransitionInflater from(Context context) {
        return new TransitionInflater(context);
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private void getTargetIds(XmlPullParser var1_1, AttributeSet var2_3, Transition var3_4) throws XmlPullParserException, IOException {
        var4_5 = var1_1.getDepth();
        do {
            block5 : {
                block9 : {
                    block8 : {
                        block7 : {
                            block6 : {
                                if ((var5_6 = var1_1.next()) == 3) {
                                    if (var1_1.getDepth() <= var4_5) return;
                                }
                                if (var5_6 == 1) return;
                                if (var5_6 != 2) continue;
                                if (!var1_1.getName().equals("target")) {
                                    var2_3 = new StringBuilder();
                                    var2_3.append("Unknown scene name: ");
                                    var2_3.append(var1_1.getName());
                                    throw new RuntimeException(var2_3.toString());
                                }
                                var6_7 = this.mContext.obtainStyledAttributes((AttributeSet)var2_3, Styleable.TRANSITION_TARGET);
                                var5_6 = TypedArrayUtils.getNamedResourceId(var6_7, var1_1, "targetId", 1, 0);
                                if (var5_6 == 0) break block6;
                                var3_4.addTarget(var5_6);
                                break block5;
                            }
                            var5_6 = TypedArrayUtils.getNamedResourceId(var6_7, var1_1, "excludeId", 2, 0);
                            if (var5_6 == 0) break block7;
                            var3_4.excludeTarget(var5_6, true);
                            break block5;
                        }
                        var7_8 = TypedArrayUtils.getNamedString(var6_7, var1_1, "targetName", 4);
                        if (var7_8 == null) break block8;
                        var3_4.addTarget(var7_8);
                        break block5;
                    }
                    var7_8 = TypedArrayUtils.getNamedString(var6_7, var1_1, "excludeName", 5);
                    if (var7_8 == null) break block9;
                    var3_4.excludeTarget(var7_8, true);
                    break block5;
                }
                var8_9 = TypedArrayUtils.getNamedString(var6_7, var1_1, "excludeClass", 3);
                if (var8_9 == null) ** GOTO lbl46
                var7_8 = var8_9;
                var3_4.excludeTarget(Class.forName(var8_9), true);
                break block5;
lbl46: // 1 sources:
                var7_8 = var8_9;
                var8_9 = TypedArrayUtils.getNamedString(var6_7, var1_1, "targetClass", 0);
                if (var8_9 == null) break block5;
                var7_8 = var8_9;
                var3_4.addTarget(Class.forName(var8_9));
            }
            var6_7.recycle();
        } while (true);
        catch (ClassNotFoundException var1_2) {
            var6_7.recycle();
            var2_3 = new StringBuilder();
            var2_3.append("Could not create ");
            var2_3.append(var7_8);
            throw new RuntimeException(var2_3.toString(), var1_2);
        }
    }

    private void loadTransition(AttributeSet object, XmlPullParser object2, ViewGroup object3, TransitionManager transitionManager) throws Resources.NotFoundException {
        TypedArray typedArray = this.mContext.obtainStyledAttributes((AttributeSet)object, Styleable.TRANSITION_MANAGER);
        int n = TypedArrayUtils.getNamedResourceId(typedArray, (XmlPullParser)object2, "transition", 2, -1);
        int n2 = TypedArrayUtils.getNamedResourceId(typedArray, (XmlPullParser)object2, "fromScene", 0, -1);
        Object var8_8 = null;
        object = n2 < 0 ? null : Scene.getSceneForLayout(object3, n2, this.mContext);
        n2 = TypedArrayUtils.getNamedResourceId(typedArray, (XmlPullParser)object2, "toScene", 1, -1);
        object2 = n2 < 0 ? var8_8 : Scene.getSceneForLayout(object3, n2, this.mContext);
        if (n >= 0 && (object3 = this.inflateTransition(n)) != null) {
            if (object2 == null) {
                object = new StringBuilder();
                ((StringBuilder)object).append("No toScene for transition ID ");
                ((StringBuilder)object).append(n);
                throw new RuntimeException(((StringBuilder)object).toString());
            }
            if (object == null) {
                transitionManager.setTransition((Scene)object2, (Transition)object3);
            } else {
                transitionManager.setTransition((Scene)object, (Scene)object2, (Transition)object3);
            }
        }
        typedArray.recycle();
    }

    /*
     * Exception decompiling
     */
    public Transition inflateTransition(int var1_1) {
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

    /*
     * Exception decompiling
     */
    public TransitionManager inflateTransitionManager(int var1_1, ViewGroup var2_2) {
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
}


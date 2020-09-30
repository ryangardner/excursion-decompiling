/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.RectF
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.util.Xml
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.animation.AccelerateDecelerateInterpolator
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AnimationUtils
 *  android.view.animation.AnticipateInterpolator
 *  android.view.animation.BounceInterpolator
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 */
package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.Key;
import androidx.constraintlayout.motion.widget.KeyFrames;
import androidx.constraintlayout.motion.widget.MotionController;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.motion.widget.TouchResponse;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.StateSet;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;

public class MotionScene {
    static final int ANTICIPATE = 4;
    static final int BOUNCE = 5;
    private static final boolean DEBUG = false;
    static final int EASE_IN = 1;
    static final int EASE_IN_OUT = 0;
    static final int EASE_OUT = 2;
    private static final int INTERPOLATOR_REFRENCE_ID = -2;
    public static final int LAYOUT_HONOR_REQUEST = 1;
    public static final int LAYOUT_IGNORE_REQUEST = 0;
    static final int LINEAR = 3;
    private static final int SPLINE_STRING = -1;
    public static final String TAG = "MotionScene";
    static final int TRANSITION_BACKWARD = 0;
    static final int TRANSITION_FORWARD = 1;
    public static final int UNSET = -1;
    private boolean DEBUG_DESKTOP = false;
    private ArrayList<Transition> mAbstractTransitionList = new ArrayList();
    private HashMap<String, Integer> mConstraintSetIdMap = new HashMap();
    private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray();
    Transition mCurrentTransition = null;
    private int mDefaultDuration = 400;
    private Transition mDefaultTransition = null;
    private SparseIntArray mDeriveMap = new SparseIntArray();
    private boolean mDisableAutoTransition = false;
    private MotionEvent mLastTouchDown;
    float mLastTouchX;
    float mLastTouchY;
    private int mLayoutDuringTransition = 0;
    private final MotionLayout mMotionLayout;
    private boolean mMotionOutsideRegion = false;
    private boolean mRtl;
    StateSet mStateSet = null;
    private ArrayList<Transition> mTransitionList = new ArrayList();
    private MotionLayout.MotionTracker mVelocityTracker;

    MotionScene(Context context, MotionLayout motionLayout, int n) {
        this.mMotionLayout = motionLayout;
        this.load(context, n);
        this.mConstraintSetMap.put(R.id.motion_base, (Object)new ConstraintSet());
        this.mConstraintSetIdMap.put("motion_base", R.id.motion_base);
    }

    public MotionScene(MotionLayout motionLayout) {
        this.mMotionLayout = motionLayout;
    }

    private int getId(Context object, String string2) {
        int n;
        int n2;
        if (string2.contains("/")) {
            CharSequence charSequence = string2.substring(string2.indexOf(47) + 1);
            n2 = n = object.getResources().getIdentifier((String)charSequence, "id", object.getPackageName());
            if (this.DEBUG_DESKTOP) {
                object = System.out;
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("id getMap res = ");
                ((StringBuilder)charSequence).append(n);
                ((PrintStream)object).println(((StringBuilder)charSequence).toString());
                n2 = n;
            }
        } else {
            n2 = -1;
        }
        n = n2;
        if (n2 != -1) return n;
        if (string2 != null && string2.length() > 1) {
            return Integer.parseInt(string2.substring(1));
        }
        Log.e((String)TAG, (String)"error in parsing id");
        return n2;
    }

    private int getIndex(Transition transition) {
        int n = transition.mId;
        if (n == -1) throw new IllegalArgumentException("The transition must have an id");
        int n2 = 0;
        while (n2 < this.mTransitionList.size()) {
            if (this.mTransitionList.get(n2).mId == n) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    private int getRealID(int n) {
        StateSet stateSet = this.mStateSet;
        if (stateSet == null) return n;
        int n2 = stateSet.stateGetConstraintID(n, -1, -1);
        if (n2 == -1) return n;
        return n2;
    }

    private boolean hasCycleDependency(int n) {
        int n2 = this.mDeriveMap.get(n);
        int n3 = this.mDeriveMap.size();
        while (n2 > 0) {
            if (n2 == n) {
                return true;
            }
            if (n3 < 0) {
                return true;
            }
            n2 = this.mDeriveMap.get(n2);
            --n3;
        }
        return false;
    }

    private boolean isProcessingTouch() {
        if (this.mVelocityTracker == null) return false;
        return true;
    }

    /*
     * Exception decompiling
     */
    private void load(Context var1_1, int var2_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 23[UNCONDITIONALDOLOOP]
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
     * Unable to fully structure code
     */
    private void parseConstraintSet(Context var1_1, XmlPullParser var2_2) {
        var3_3 = new ConstraintSet();
        var3_3.setForceId(false);
        var4_4 = var2_2.getAttributeCount();
        var6_6 = -1;
        var7_7 = -1;
        for (var5_5 = 0; var5_5 < var4_4; ++var5_5) {
            block8 : {
                block7 : {
                    var8_8 = var2_2.getAttributeName(var5_5);
                    var9_9 = var2_2.getAttributeValue(var5_5);
                    if (this.DEBUG_DESKTOP) {
                        var10_10 = System.out;
                        var11_11 = new StringBuilder();
                        var11_11.append("id string = ");
                        var11_11.append(var9_9);
                        var10_10.println(var11_11.toString());
                    }
                    if ((var12_12 = var8_8.hashCode()) == -1496482599) break block7;
                    if (var12_12 != 3355 || !var8_8.equals("id")) ** GOTO lbl-1000
                    var12_12 = 0;
                    break block8;
                }
                if (var8_8.equals("deriveConstraintsFrom")) {
                    var12_12 = 1;
                } else lbl-1000: // 2 sources:
                {
                    var12_12 = -1;
                }
            }
            if (var12_12 != 0) {
                if (var12_12 != 1) continue;
                var7_7 = this.getId(var1_1, var9_9);
                continue;
            }
            var6_6 = this.getId(var1_1, var9_9);
            this.mConstraintSetIdMap.put(MotionScene.stripID(var9_9), var6_6);
        }
        if (var6_6 == -1) return;
        if (this.mMotionLayout.mDebugPath != 0) {
            var3_3.setValidateOnParse(true);
        }
        var3_3.load(var1_1, var2_2);
        if (var7_7 != -1) {
            this.mDeriveMap.put(var6_6, var7_7);
        }
        this.mConstraintSetMap.put(var6_6, (Object)var3_3);
    }

    private void parseMotionSceneTags(Context context, XmlPullParser xmlPullParser) {
        context = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), R.styleable.MotionScene);
        int n = context.getIndexCount();
        int n2 = 0;
        do {
            if (n2 >= n) {
                context.recycle();
                return;
            }
            int n3 = context.getIndex(n2);
            if (n3 == R.styleable.MotionScene_defaultDuration) {
                this.mDefaultDuration = context.getInt(n3, this.mDefaultDuration);
            } else if (n3 == R.styleable.MotionScene_layoutDuringTransition) {
                this.mLayoutDuringTransition = context.getInteger(n3, 0);
            }
            ++n2;
        } while (true);
    }

    private void readConstraintChain(int n) {
        int n2 = this.mDeriveMap.get(n);
        if (n2 <= 0) return;
        this.readConstraintChain(this.mDeriveMap.get(n));
        Object object = (ConstraintSet)this.mConstraintSetMap.get(n);
        ConstraintSet constraintSet = (ConstraintSet)this.mConstraintSetMap.get(n2);
        if (constraintSet == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("ERROR! invalid deriveConstraintsFrom: @id/");
            ((StringBuilder)object).append(Debug.getName(this.mMotionLayout.getContext(), n2));
            Log.e((String)TAG, (String)((StringBuilder)object).toString());
            return;
        }
        ((ConstraintSet)object).readFallback(constraintSet);
        this.mDeriveMap.put(n, -1);
    }

    public static String stripID(String string2) {
        if (string2 == null) {
            return "";
        }
        int n = string2.indexOf(47);
        if (n >= 0) return string2.substring(n + 1);
        return string2;
    }

    /*
     * Unable to fully structure code
     */
    public void addOnClickListeners(MotionLayout var1_1, int var2_2) {
        for (Transition var4_5 : this.mTransitionList) {
            if (Transition.access$400(var4_5).size() <= 0) continue;
            var4_6 = Transition.access$400(var4_5).iterator();
            while (var4_6.hasNext()) {
                ((Transition.TransitionOnClick)var4_6.next()).removeOnClickListeners(var1_1);
            }
        }
        for (Transition var4_8 : this.mAbstractTransitionList) {
            if (Transition.access$400(var4_8).size() <= 0) continue;
            var4_9 = Transition.access$400(var4_8).iterator();
            while (var4_9.hasNext()) {
                ((Transition.TransitionOnClick)var4_9.next()).removeOnClickListeners(var1_1);
            }
        }
        for (Transition var4_11 : this.mTransitionList) {
            if (Transition.access$400(var4_11).size() <= 0) continue;
            var3_3 = Transition.access$400(var4_11).iterator();
            while (var3_3.hasNext()) {
                ((Transition.TransitionOnClick)var3_3.next()).addOnClickListeners(var1_1, var2_2, var4_11);
            }
        }
        var5_14 = this.mAbstractTransitionList.iterator();
        block6 : do lbl-1000: // 3 sources:
        {
            if (var5_14.hasNext() == false) return;
            var4_13 = var5_14.next();
            if (Transition.access$400(var4_13).size() <= 0) ** GOTO lbl-1000
            var3_3 = Transition.access$400(var4_13).iterator();
            do {
                if (!var3_3.hasNext()) continue block6;
                ((Transition.TransitionOnClick)var3_3.next()).addOnClickListeners(var1_1, var2_2, var4_13);
            } while (true);
            break;
        } while (true);
    }

    public void addTransition(Transition transition) {
        int n = this.getIndex(transition);
        if (n == -1) {
            this.mTransitionList.add(transition);
            return;
        }
        this.mTransitionList.set(n, transition);
    }

    /*
     * Unable to fully structure code
     */
    boolean autoTransition(MotionLayout var1_1, int var2_2) {
        if (this.isProcessingTouch()) {
            return false;
        }
        if (this.mDisableAutoTransition) {
            return false;
        }
        var3_3 = this.mTransitionList.iterator();
        do lbl-1000: // 3 sources:
        {
            if (var3_3.hasNext() == false) return false;
            var4_4 = var3_3.next();
            if (Transition.access$600(var4_4) == 0) ** GOTO lbl-1000
            if (var2_2 != Transition.access$100(var4_4) || Transition.access$600(var4_4) != 4 && Transition.access$600(var4_4) != 2) continue;
            var1_1.setState(MotionLayout.TransitionState.FINISHED);
            var1_1.setTransition(var4_4);
            if (Transition.access$600(var4_4) == 4) {
                var1_1.transitionToEnd();
                var1_1.setState(MotionLayout.TransitionState.SETUP);
                var1_1.setState(MotionLayout.TransitionState.MOVING);
                return true;
            }
            var1_1.setProgress(1.0f);
            var1_1.evaluate(true);
            var1_1.setState(MotionLayout.TransitionState.SETUP);
            var1_1.setState(MotionLayout.TransitionState.MOVING);
            var1_1.setState(MotionLayout.TransitionState.FINISHED);
            return true;
        } while (var2_2 != Transition.access$000(var4_4) || Transition.access$600(var4_4) != 3 && Transition.access$600(var4_4) != 1);
        var1_1.setState(MotionLayout.TransitionState.FINISHED);
        var1_1.setTransition(var4_4);
        if (Transition.access$600(var4_4) == 3) {
            var1_1.transitionToStart();
            var1_1.setState(MotionLayout.TransitionState.SETUP);
            var1_1.setState(MotionLayout.TransitionState.MOVING);
            return true;
        }
        var1_1.setProgress(0.0f);
        var1_1.evaluate(true);
        var1_1.setState(MotionLayout.TransitionState.SETUP);
        var1_1.setState(MotionLayout.TransitionState.MOVING);
        var1_1.setState(MotionLayout.TransitionState.FINISHED);
        return true;
    }

    public Transition bestTransitionFor(int n, float f, float f2, MotionEvent motionEvent) {
        if (n == -1) return this.mCurrentTransition;
        Object object = this.getTransitionsWithState(n);
        float f3 = 0.0f;
        Object object2 = null;
        RectF rectF = new RectF();
        Iterator<Transition> iterator2 = object.iterator();
        while (iterator2.hasNext()) {
            float f4;
            object = iterator2.next();
            if (((Transition)object).mDisable || ((Transition)object).mTouchResponse == null) continue;
            ((Transition)object).mTouchResponse.setRTL(this.mRtl);
            RectF rectF2 = ((Transition)object).mTouchResponse.getTouchRegion(this.mMotionLayout, rectF);
            if (rectF2 != null && motionEvent != null && !rectF2.contains(motionEvent.getX(), motionEvent.getY()) || (rectF2 = ((Transition)object).mTouchResponse.getTouchRegion(this.mMotionLayout, rectF)) != null && motionEvent != null && !rectF2.contains(motionEvent.getX(), motionEvent.getY())) continue;
            float f5 = ((Transition)object).mTouchResponse.dot(f, f2);
            f4 = f5 * (f4 = ((Transition)object).mConstraintSetEnd == n ? -1.0f : 1.1f);
            if (!(f4 > f3)) continue;
            object2 = object;
            f3 = f4;
        }
        return object2;
    }

    public void disableAutoTransition(boolean bl) {
        this.mDisableAutoTransition = bl;
    }

    public int gatPathMotionArc() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) return -1;
        return transition.mPathMotionArc;
    }

    ConstraintSet getConstraintSet(int n) {
        return this.getConstraintSet(n, -1, -1);
    }

    ConstraintSet getConstraintSet(int n, int n2, int n3) {
        SparseArray<ConstraintSet> sparseArray;
        if (this.DEBUG_DESKTOP) {
            sparseArray = System.out;
            Appendable appendable = new StringBuilder();
            ((StringBuilder)appendable).append("id ");
            ((StringBuilder)appendable).append(n);
            sparseArray.println(((StringBuilder)appendable).toString());
            appendable = System.out;
            sparseArray = new StringBuilder();
            sparseArray.append("size ");
            sparseArray.append(this.mConstraintSetMap.size());
            ((PrintStream)appendable).println(sparseArray.toString());
        }
        sparseArray = this.mStateSet;
        int n4 = n;
        if (sparseArray != null) {
            n2 = sparseArray.stateGetConstraintID(n, n2, n3);
            n4 = n;
            if (n2 != -1) {
                n4 = n2;
            }
        }
        if (this.mConstraintSetMap.get(n4) != null) return (ConstraintSet)this.mConstraintSetMap.get(n4);
        sparseArray = new StringBuilder();
        sparseArray.append("Warning could not find ConstraintSet id/");
        sparseArray.append(Debug.getName(this.mMotionLayout.getContext(), n4));
        sparseArray.append(" In MotionScene");
        Log.e((String)TAG, (String)sparseArray.toString());
        sparseArray = this.mConstraintSetMap;
        return (ConstraintSet)sparseArray.get(sparseArray.keyAt(0));
    }

    public ConstraintSet getConstraintSet(Context context, String string2) {
        Object object;
        Appendable appendable;
        if (this.DEBUG_DESKTOP) {
            appendable = System.out;
            object = new StringBuilder();
            ((StringBuilder)object).append("id ");
            ((StringBuilder)object).append(string2);
            ((PrintStream)appendable).println(((StringBuilder)object).toString());
            object = System.out;
            appendable = new StringBuilder();
            ((StringBuilder)appendable).append("size ");
            ((StringBuilder)appendable).append(this.mConstraintSetMap.size());
            ((PrintStream)object).println(((StringBuilder)appendable).toString());
        }
        int n = 0;
        while (n < this.mConstraintSetMap.size()) {
            int n2 = this.mConstraintSetMap.keyAt(n);
            object = context.getResources().getResourceName(n2);
            if (this.DEBUG_DESKTOP) {
                appendable = System.out;
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Id for <");
                stringBuilder.append(n);
                stringBuilder.append("> is <");
                stringBuilder.append((String)object);
                stringBuilder.append("> looking for <");
                stringBuilder.append(string2);
                stringBuilder.append(">");
                ((PrintStream)appendable).println(stringBuilder.toString());
            }
            if (string2.equals(object)) {
                return (ConstraintSet)this.mConstraintSetMap.get(n2);
            }
            ++n;
        }
        return null;
    }

    public int[] getConstraintSetIds() {
        int n = this.mConstraintSetMap.size();
        int[] arrn = new int[n];
        int n2 = 0;
        while (n2 < n) {
            arrn[n2] = this.mConstraintSetMap.keyAt(n2);
            ++n2;
        }
        return arrn;
    }

    public ArrayList<Transition> getDefinedTransitions() {
        return this.mTransitionList;
    }

    public int getDuration() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) return this.mDefaultDuration;
        return transition.mDuration;
    }

    int getEndId() {
        Transition transition = this.mCurrentTransition;
        if (transition != null) return transition.mConstraintSetEnd;
        return -1;
    }

    public Interpolator getInterpolator() {
        int n = this.mCurrentTransition.mDefaultInterpolator;
        if (n == -2) return AnimationUtils.loadInterpolator((Context)this.mMotionLayout.getContext(), (int)this.mCurrentTransition.mDefaultInterpolatorID);
        if (n == -1) return new Interpolator(Easing.getInterpolator(this.mCurrentTransition.mDefaultInterpolatorString)){
            final /* synthetic */ Easing val$easing;
            {
                this.val$easing = easing;
            }

            public float getInterpolation(float f) {
                return (float)this.val$easing.get(f);
            }
        };
        if (n == 0) return new AccelerateDecelerateInterpolator();
        if (n == 1) return new AccelerateInterpolator();
        if (n == 2) return new DecelerateInterpolator();
        if (n == 4) return new AnticipateInterpolator();
        if (n == 5) return new BounceInterpolator();
        return null;
    }

    /*
     * Exception decompiling
     */
    Key getKeyFrame(Context var1_1, int var2_2, int var3_3, int var4_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[DOLOOP]], but top level block is 2[DOLOOP]
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

    public void getKeyFrames(MotionController motionController) {
        Object object = this.mCurrentTransition;
        if (object == null) {
            object = this.mDefaultTransition;
            if (object == null) return;
            object = ((Transition)object).mKeyFramesList.iterator();
            while (object.hasNext()) {
                ((KeyFrames)object.next()).addFrames(motionController);
            }
            return;
        }
        object = ((Transition)object).mKeyFramesList.iterator();
        while (object.hasNext()) {
            ((KeyFrames)object.next()).addFrames(motionController);
        }
    }

    float getMaxAcceleration() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) return 0.0f;
        if (transition.mTouchResponse == null) return 0.0f;
        return this.mCurrentTransition.mTouchResponse.getMaxAcceleration();
    }

    float getMaxVelocity() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) return 0.0f;
        if (transition.mTouchResponse == null) return 0.0f;
        return this.mCurrentTransition.mTouchResponse.getMaxVelocity();
    }

    boolean getMoveWhenScrollAtTop() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) return false;
        if (transition.mTouchResponse == null) return false;
        return this.mCurrentTransition.mTouchResponse.getMoveWhenScrollAtTop();
    }

    public float getPathPercent(View view, int n) {
        return 0.0f;
    }

    float getProgressDirection(float f, float f2) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) return 0.0f;
        if (transition.mTouchResponse == null) return 0.0f;
        return this.mCurrentTransition.mTouchResponse.getProgressDirection(f, f2);
    }

    public float getStaggered() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) return 0.0f;
        return transition.mStagger;
    }

    int getStartId() {
        Transition transition = this.mCurrentTransition;
        if (transition != null) return transition.mConstraintSetStart;
        return -1;
    }

    public Transition getTransitionById(int n) {
        Transition transition;
        Iterator<Transition> iterator2 = this.mTransitionList.iterator();
        do {
            if (!iterator2.hasNext()) return null;
        } while ((transition = iterator2.next()).mId != n);
        return transition;
    }

    int getTransitionDirection(int n) {
        Iterator<Transition> iterator2 = this.mTransitionList.iterator();
        do {
            if (!iterator2.hasNext()) return 1;
        } while (iterator2.next().mConstraintSetStart != n);
        return 0;
    }

    public List<Transition> getTransitionsWithState(int n) {
        n = this.getRealID(n);
        ArrayList<Transition> arrayList = new ArrayList<Transition>();
        Iterator<Transition> iterator2 = this.mTransitionList.iterator();
        while (iterator2.hasNext()) {
            Transition transition = iterator2.next();
            if (transition.mConstraintSetStart != n && transition.mConstraintSetEnd != n) continue;
            arrayList.add(transition);
        }
        return arrayList;
    }

    boolean hasKeyFramePosition(View view, int n) {
        Object object = this.mCurrentTransition;
        if (object == null) {
            return false;
        }
        Iterator iterator2 = ((Transition)object).mKeyFramesList.iterator();
        block0 : do {
            if (!iterator2.hasNext()) return false;
            object = ((KeyFrames)iterator2.next()).getKeyFramesForView(view.getId()).iterator();
            do {
                if (!object.hasNext()) continue block0;
            } while (((Key)object.next()).mFramePosition != n);
            break;
        } while (true);
        return true;
    }

    public int lookUpConstraintId(String string2) {
        return this.mConstraintSetIdMap.get(string2);
    }

    public String lookUpConstraintName(int n) {
        Map.Entry<String, Integer> entry;
        Iterator<Map.Entry<String, Integer>> iterator2 = this.mConstraintSetIdMap.entrySet().iterator();
        do {
            if (!iterator2.hasNext()) return null;
        } while ((entry = iterator2.next()).getValue() != n);
        return entry.getKey();
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
    }

    void processScrollMove(float f, float f2) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) return;
        if (transition.mTouchResponse == null) return;
        this.mCurrentTransition.mTouchResponse.scrollMove(f, f2);
    }

    void processScrollUp(float f, float f2) {
        Transition transition = this.mCurrentTransition;
        if (transition == null) return;
        if (transition.mTouchResponse == null) return;
        this.mCurrentTransition.mTouchResponse.scrollUp(f, f2);
    }

    void processTouchEvent(MotionEvent object, int n, MotionLayout motionLayout) {
        Object object2 = new RectF();
        if (this.mVelocityTracker == null) {
            this.mVelocityTracker = this.mMotionLayout.obtainVelocityTracker();
        }
        this.mVelocityTracker.addMovement((MotionEvent)object);
        if (n != -1) {
            int n2 = object.getAction();
            boolean bl = false;
            if (n2 != 0) {
                if (n2 == 2) {
                    Object object3;
                    float f = object.getRawY() - this.mLastTouchY;
                    float f2 = object.getRawX() - this.mLastTouchX;
                    if ((double)f2 == 0.0) {
                        if ((double)f == 0.0) return;
                    }
                    if ((object3 = this.mLastTouchDown) == null) {
                        return;
                    }
                    if ((object3 = this.bestTransitionFor(n, f2, f, (MotionEvent)object3)) != null) {
                        motionLayout.setTransition((Transition)object3);
                        object2 = this.mCurrentTransition.mTouchResponse.getTouchRegion(this.mMotionLayout, (RectF)object2);
                        boolean bl2 = bl;
                        if (object2 != null) {
                            bl2 = bl;
                            if (!object2.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                                bl2 = true;
                            }
                        }
                        this.mMotionOutsideRegion = bl2;
                        this.mCurrentTransition.mTouchResponse.setUpTouchEvent(this.mLastTouchX, this.mLastTouchY);
                    }
                }
            } else {
                this.mLastTouchX = object.getRawX();
                this.mLastTouchY = object.getRawY();
                this.mLastTouchDown = object;
                if (this.mCurrentTransition.mTouchResponse == null) return;
                object = this.mCurrentTransition.mTouchResponse.getLimitBoundsTo(this.mMotionLayout, (RectF)object2);
                if (object != null && !object.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                    this.mLastTouchDown = null;
                    return;
                }
                object = this.mCurrentTransition.mTouchResponse.getTouchRegion(this.mMotionLayout, (RectF)object2);
                this.mMotionOutsideRegion = object != null && !object.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY());
                this.mCurrentTransition.mTouchResponse.setDown(this.mLastTouchX, this.mLastTouchY);
                return;
            }
        }
        if ((object2 = this.mCurrentTransition) != null && ((Transition)object2).mTouchResponse != null && !this.mMotionOutsideRegion) {
            this.mCurrentTransition.mTouchResponse.processTouchEvent((MotionEvent)object, this.mVelocityTracker, n, this);
        }
        this.mLastTouchX = object.getRawX();
        this.mLastTouchY = object.getRawY();
        if (object.getAction() != 1) return;
        object = this.mVelocityTracker;
        if (object == null) return;
        object.recycle();
        this.mVelocityTracker = null;
        if (motionLayout.mCurrentState == -1) return;
        this.autoTransition(motionLayout, motionLayout.mCurrentState);
    }

    void readFallback(MotionLayout motionLayout) {
        int n = 0;
        int n2 = 0;
        do {
            int n3 = n;
            if (n2 >= this.mConstraintSetMap.size()) {
                while (n3 < this.mConstraintSetMap.size()) {
                    ((ConstraintSet)this.mConstraintSetMap.valueAt(n3)).readFallback(motionLayout);
                    ++n3;
                }
                return;
            }
            n3 = this.mConstraintSetMap.keyAt(n2);
            if (this.hasCycleDependency(n3)) {
                Log.e((String)TAG, (String)"Cannot be derived from yourself");
                return;
            }
            this.readConstraintChain(n3);
            ++n2;
        } while (true);
    }

    public void removeTransition(Transition transition) {
        int n = this.getIndex(transition);
        if (n == -1) return;
        this.mTransitionList.remove(n);
    }

    public void setConstraintSet(int n, ConstraintSet constraintSet) {
        this.mConstraintSetMap.put(n, (Object)constraintSet);
    }

    public void setDuration(int n) {
        Transition transition = this.mCurrentTransition;
        if (transition != null) {
            transition.setDuration(n);
            return;
        }
        this.mDefaultDuration = n;
    }

    public void setKeyframe(View view, int n, String string2, Object object) {
        Object object2 = this.mCurrentTransition;
        if (object2 == null) {
            return;
        }
        Iterator iterator2 = ((Transition)object2).mKeyFramesList.iterator();
        block0 : while (iterator2.hasNext()) {
            object2 = ((KeyFrames)iterator2.next()).getKeyFramesForView(view.getId()).iterator();
            do {
                if (!object2.hasNext()) continue block0;
                if (((Key)object2.next()).mFramePosition != n) continue;
                float f = object != null ? ((Float)object).floatValue() : 0.0f;
                string2.equalsIgnoreCase("app:PerpendicularPath_percent");
            } while (true);
            break;
        }
        return;
    }

    public void setRtl(boolean bl) {
        this.mRtl = bl;
        Transition transition = this.mCurrentTransition;
        if (transition == null) return;
        if (transition.mTouchResponse == null) return;
        this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
    }

    /*
     * WARNING - void declaration
     */
    void setTransition(int n, int n2) {
        int n3;
        int n4;
        void var3_7;
        block6 : {
            int n5;
            block5 : {
                block4 : {
                    StateSet object2 = this.mStateSet;
                    if (object2 == null) break block4;
                    n3 = object2.stateGetConstraintID(n, -1, -1);
                    if (n3 == -1) {
                        n3 = n;
                    }
                    n4 = this.mStateSet.stateGetConstraintID(n2, -1, -1);
                    n5 = n3;
                    if (n4 == -1) break block5;
                    break block6;
                }
                n5 = n;
            }
            n4 = n2;
            n3 = n5;
        }
        for (Transition transition : this.mTransitionList) {
            if ((transition.mConstraintSetEnd != n4 || transition.mConstraintSetStart != n3) && (transition.mConstraintSetEnd != n2 || transition.mConstraintSetStart != n)) continue;
            this.mCurrentTransition = transition;
            if (transition == null) return;
            if (transition.mTouchResponse == null) return;
            this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
            return;
        }
        Transition transition = this.mDefaultTransition;
        for (Transition transition2 : this.mAbstractTransitionList) {
            if (transition2.mConstraintSetEnd != n2) continue;
            Transition transition3 = transition2;
        }
        Transition transition4 = new Transition(this, (Transition)var3_7);
        transition4.mConstraintSetStart = n3;
        transition4.mConstraintSetEnd = n4;
        if (n3 != -1) {
            this.mTransitionList.add(transition4);
        }
        this.mCurrentTransition = transition4;
    }

    public void setTransition(Transition transition) {
        this.mCurrentTransition = transition;
        if (transition == null) return;
        if (transition.mTouchResponse == null) return;
        this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
    }

    void setupTouch() {
        Transition transition = this.mCurrentTransition;
        if (transition == null) return;
        if (transition.mTouchResponse == null) return;
        this.mCurrentTransition.mTouchResponse.setupTouch();
    }

    boolean supportTouch() {
        Object object;
        boolean bl;
        block1 : {
            object = this.mTransitionList.iterator();
            do {
                boolean bl2 = object.hasNext();
                bl = true;
                if (!bl2) break block1;
            } while (object.next().mTouchResponse == null);
            return true;
        }
        object = this.mCurrentTransition;
        if (object == null) return false;
        if (((Transition)object).mTouchResponse == null) return false;
        return bl;
    }

    public boolean validateLayout(MotionLayout motionLayout) {
        if (motionLayout != this.mMotionLayout) return false;
        if (motionLayout.mScene != this) return false;
        return true;
    }

    public static class Transition {
        public static final int AUTO_ANIMATE_TO_END = 4;
        public static final int AUTO_ANIMATE_TO_START = 3;
        public static final int AUTO_JUMP_TO_END = 2;
        public static final int AUTO_JUMP_TO_START = 1;
        public static final int AUTO_NONE = 0;
        static final int TRANSITION_FLAG_FIRST_DRAW = 1;
        private int mAutoTransition = 0;
        private int mConstraintSetEnd = -1;
        private int mConstraintSetStart = -1;
        private int mDefaultInterpolator = 0;
        private int mDefaultInterpolatorID = -1;
        private String mDefaultInterpolatorString = null;
        private boolean mDisable = false;
        private int mDuration = 400;
        private int mId = -1;
        private boolean mIsAbstract = false;
        private ArrayList<KeyFrames> mKeyFramesList = new ArrayList();
        private int mLayoutDuringTransition = 0;
        private final MotionScene mMotionScene;
        private ArrayList<TransitionOnClick> mOnClicks = new ArrayList();
        private int mPathMotionArc = -1;
        private float mStagger = 0.0f;
        private TouchResponse mTouchResponse = null;
        private int mTransitionFlags = 0;

        public Transition(int n, MotionScene motionScene, int n2, int n3) {
            this.mId = n;
            this.mMotionScene = motionScene;
            this.mConstraintSetStart = n2;
            this.mConstraintSetEnd = n3;
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
        }

        Transition(MotionScene motionScene, Context context, XmlPullParser xmlPullParser) {
            this.mDuration = motionScene.mDefaultDuration;
            this.mLayoutDuringTransition = motionScene.mLayoutDuringTransition;
            this.mMotionScene = motionScene;
            this.fillFromAttributeList(motionScene, context, Xml.asAttributeSet((XmlPullParser)xmlPullParser));
        }

        Transition(MotionScene motionScene, Transition transition) {
            this.mMotionScene = motionScene;
            if (transition == null) return;
            this.mPathMotionArc = transition.mPathMotionArc;
            this.mDefaultInterpolator = transition.mDefaultInterpolator;
            this.mDefaultInterpolatorString = transition.mDefaultInterpolatorString;
            this.mDefaultInterpolatorID = transition.mDefaultInterpolatorID;
            this.mDuration = transition.mDuration;
            this.mKeyFramesList = transition.mKeyFramesList;
            this.mStagger = transition.mStagger;
            this.mLayoutDuringTransition = transition.mLayoutDuringTransition;
        }

        static /* synthetic */ boolean access$1200(Transition transition) {
            return transition.mIsAbstract;
        }

        static /* synthetic */ TouchResponse access$202(Transition transition, TouchResponse touchResponse) {
            transition.mTouchResponse = touchResponse;
            return touchResponse;
        }

        static /* synthetic */ ArrayList access$400(Transition transition) {
            return transition.mOnClicks;
        }

        static /* synthetic */ int access$600(Transition transition) {
            return transition.mAutoTransition;
        }

        private void fill(MotionScene motionScene, Context context, TypedArray typedArray) {
            int n = typedArray.getIndexCount();
            int n2 = 0;
            do {
                Object object;
                if (n2 >= n) {
                    if (this.mConstraintSetStart != -1) return;
                    this.mIsAbstract = true;
                    return;
                }
                int n3 = typedArray.getIndex(n2);
                if (n3 == R.styleable.Transition_constraintSetEnd) {
                    this.mConstraintSetEnd = typedArray.getResourceId(n3, this.mConstraintSetEnd);
                    if ("layout".equals(context.getResources().getResourceTypeName(this.mConstraintSetEnd))) {
                        object = new ConstraintSet();
                        ((ConstraintSet)object).load(context, this.mConstraintSetEnd);
                        motionScene.mConstraintSetMap.append(this.mConstraintSetEnd, object);
                    }
                } else if (n3 == R.styleable.Transition_constraintSetStart) {
                    this.mConstraintSetStart = typedArray.getResourceId(n3, this.mConstraintSetStart);
                    if ("layout".equals(context.getResources().getResourceTypeName(this.mConstraintSetStart))) {
                        object = new ConstraintSet();
                        ((ConstraintSet)object).load(context, this.mConstraintSetStart);
                        motionScene.mConstraintSetMap.append(this.mConstraintSetStart, object);
                    }
                } else if (n3 == R.styleable.Transition_motionInterpolator) {
                    object = typedArray.peekValue(n3);
                    if (((TypedValue)object).type == 1) {
                        this.mDefaultInterpolatorID = n3 = typedArray.getResourceId(n3, -1);
                        if (n3 != -1) {
                            this.mDefaultInterpolator = -2;
                        }
                    } else if (((TypedValue)object).type == 3) {
                        this.mDefaultInterpolatorString = object = typedArray.getString(n3);
                        if (((String)object).indexOf("/") > 0) {
                            this.mDefaultInterpolatorID = typedArray.getResourceId(n3, -1);
                            this.mDefaultInterpolator = -2;
                        } else {
                            this.mDefaultInterpolator = -1;
                        }
                    } else {
                        this.mDefaultInterpolator = typedArray.getInteger(n3, this.mDefaultInterpolator);
                    }
                } else if (n3 == R.styleable.Transition_duration) {
                    this.mDuration = typedArray.getInt(n3, this.mDuration);
                } else if (n3 == R.styleable.Transition_staggered) {
                    this.mStagger = typedArray.getFloat(n3, this.mStagger);
                } else if (n3 == R.styleable.Transition_autoTransition) {
                    this.mAutoTransition = typedArray.getInteger(n3, this.mAutoTransition);
                } else if (n3 == R.styleable.Transition_android_id) {
                    this.mId = typedArray.getResourceId(n3, this.mId);
                } else if (n3 == R.styleable.Transition_transitionDisable) {
                    this.mDisable = typedArray.getBoolean(n3, this.mDisable);
                } else if (n3 == R.styleable.Transition_pathMotionArc) {
                    this.mPathMotionArc = typedArray.getInteger(n3, -1);
                } else if (n3 == R.styleable.Transition_layoutDuringTransition) {
                    this.mLayoutDuringTransition = typedArray.getInteger(n3, 0);
                } else if (n3 == R.styleable.Transition_transitionFlags) {
                    this.mTransitionFlags = typedArray.getInteger(n3, 0);
                }
                ++n2;
            } while (true);
        }

        private void fillFromAttributeList(MotionScene motionScene, Context context, AttributeSet attributeSet) {
            attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.Transition);
            this.fill(motionScene, context, (TypedArray)attributeSet);
            attributeSet.recycle();
        }

        public void addOnClick(Context context, XmlPullParser xmlPullParser) {
            this.mOnClicks.add(new TransitionOnClick(context, this, xmlPullParser));
        }

        public String debugString(Context object) {
            String string2 = this.mConstraintSetStart == -1 ? "null" : object.getResources().getResourceEntryName(this.mConstraintSetStart);
            if (this.mConstraintSetEnd == -1) {
                object = new StringBuilder();
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" -> null");
                return ((StringBuilder)object).toString();
            }
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(string2);
            stringBuilder.append(" -> ");
            stringBuilder.append(object.getResources().getResourceEntryName(this.mConstraintSetEnd));
            return stringBuilder.toString();
        }

        public int getDuration() {
            return this.mDuration;
        }

        public int getEndConstraintSetId() {
            return this.mConstraintSetEnd;
        }

        public int getId() {
            return this.mId;
        }

        public List<KeyFrames> getKeyFrameList() {
            return this.mKeyFramesList;
        }

        public int getLayoutDuringTransition() {
            return this.mLayoutDuringTransition;
        }

        public List<TransitionOnClick> getOnClickList() {
            return this.mOnClicks;
        }

        public int getPathMotionArc() {
            return this.mPathMotionArc;
        }

        public float getStagger() {
            return this.mStagger;
        }

        public int getStartConstraintSetId() {
            return this.mConstraintSetStart;
        }

        public TouchResponse getTouchResponse() {
            return this.mTouchResponse;
        }

        public boolean isEnabled() {
            return this.mDisable ^ true;
        }

        public boolean isTransitionFlag(int n) {
            if ((n & this.mTransitionFlags) == 0) return false;
            return true;
        }

        public void setDuration(int n) {
            this.mDuration = n;
        }

        public void setEnable(boolean bl) {
            this.mDisable = bl ^ true;
        }

        public void setPathMotionArc(int n) {
            this.mPathMotionArc = n;
        }

        public void setStagger(float f) {
            this.mStagger = f;
        }

        static class TransitionOnClick
        implements View.OnClickListener {
            public static final int ANIM_TOGGLE = 17;
            public static final int ANIM_TO_END = 1;
            public static final int ANIM_TO_START = 16;
            public static final int JUMP_TO_END = 256;
            public static final int JUMP_TO_START = 4096;
            int mMode = 17;
            int mTargetId = -1;
            private final Transition mTransition;

            public TransitionOnClick(Context context, Transition transition, XmlPullParser xmlPullParser) {
                this.mTransition = transition;
                context = context.obtainStyledAttributes(Xml.asAttributeSet((XmlPullParser)xmlPullParser), R.styleable.OnClick);
                int n = context.getIndexCount();
                int n2 = 0;
                do {
                    if (n2 >= n) {
                        context.recycle();
                        return;
                    }
                    int n3 = context.getIndex(n2);
                    if (n3 == R.styleable.OnClick_targetId) {
                        this.mTargetId = context.getResourceId(n3, this.mTargetId);
                    } else if (n3 == R.styleable.OnClick_clickAction) {
                        this.mMode = context.getInt(n3, this.mMode);
                    }
                    ++n2;
                } while (true);
            }

            public void addOnClickListeners(MotionLayout object, int n, Transition transition) {
                int n2 = this.mTargetId;
                if (n2 != -1) {
                    object = object.findViewById(n2);
                }
                if (object == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("OnClick could not find id ");
                    ((StringBuilder)object).append(this.mTargetId);
                    Log.e((String)MotionScene.TAG, (String)((StringBuilder)object).toString());
                    return;
                }
                int n3 = transition.mConstraintSetStart;
                int n4 = transition.mConstraintSetEnd;
                if (n3 == -1) {
                    object.setOnClickListener((View.OnClickListener)this);
                    return;
                }
                n2 = this.mMode;
                int n5 = 1;
                n2 = (n2 & 1) != 0 && n == n3 ? 1 : 0;
                int n6 = (this.mMode & 256) != 0 && n == n3 ? 1 : 0;
                n3 = (this.mMode & 1) != 0 && n == n3 ? 1 : 0;
                int n7 = (this.mMode & 16) != 0 && n == n4 ? 1 : 0;
                n = (this.mMode & 4096) != 0 && n == n4 ? n5 : 0;
                if ((n3 | (n2 | n6) | n7 | n) == 0) return;
                object.setOnClickListener((View.OnClickListener)this);
            }

            boolean isTransitionViable(Transition transition, MotionLayout motionLayout) {
                Transition transition2 = this.mTransition;
                boolean bl = true;
                boolean bl2 = true;
                if (transition2 == transition) {
                    return true;
                }
                int n = transition2.mConstraintSetEnd;
                int n2 = this.mTransition.mConstraintSetStart;
                if (n2 == -1) {
                    if (motionLayout.mCurrentState == n) return false;
                    return bl2;
                }
                bl2 = bl;
                if (motionLayout.mCurrentState == n2) return bl2;
                if (motionLayout.mCurrentState != n) return false;
                return bl;
            }

            public void onClick(View object) {
                int n;
                object = this.mTransition.mMotionScene.mMotionLayout;
                if (!((MotionLayout)object).isInteractionEnabled()) {
                    return;
                }
                if (this.mTransition.mConstraintSetStart == -1) {
                    int n2 = ((MotionLayout)object).getCurrentState();
                    if (n2 == -1) {
                        ((MotionLayout)object).transitionToState(this.mTransition.mConstraintSetEnd);
                        return;
                    }
                    Transition transition = new Transition(this.mTransition.mMotionScene, this.mTransition);
                    transition.mConstraintSetStart = n2;
                    transition.mConstraintSetEnd = this.mTransition.mConstraintSetEnd;
                    ((MotionLayout)object).setTransition(transition);
                    ((MotionLayout)object).transitionToEnd();
                    return;
                }
                Transition transition = Transition.access$700((Transition)this.mTransition).mCurrentTransition;
                int n3 = this.mMode;
                int n4 = 0;
                n3 = (n3 & 1) == 0 && (n3 & 256) == 0 ? 0 : 1;
                int n5 = this.mMode;
                n5 = (n5 & 16) == 0 && (n5 & 4096) == 0 ? 0 : 1;
                int n6 = n3 != 0 && n5 != 0 ? 1 : 0;
                if (n6 != 0) {
                    Transition transition2 = Transition.access$700((Transition)this.mTransition).mCurrentTransition;
                    Transition transition3 = this.mTransition;
                    if (transition2 != transition3) {
                        ((MotionLayout)object).setTransition(transition3);
                    }
                    n = n5;
                    n6 = n4;
                    if (((MotionLayout)object).getCurrentState() != ((MotionLayout)object).getEndState()) {
                        if (((MotionLayout)object).getProgress() > 0.5f) {
                            n = n5;
                            n6 = n4;
                        } else {
                            n = 0;
                            n6 = n3;
                        }
                    }
                } else {
                    n6 = n3;
                    n = n5;
                }
                if (!this.isTransitionViable(transition, (MotionLayout)object)) return;
                if (n6 != 0 && (this.mMode & 1) != 0) {
                    ((MotionLayout)object).setTransition(this.mTransition);
                    ((MotionLayout)object).transitionToEnd();
                    return;
                }
                if (n != 0 && (this.mMode & 16) != 0) {
                    ((MotionLayout)object).setTransition(this.mTransition);
                    ((MotionLayout)object).transitionToStart();
                    return;
                }
                if (n6 != 0 && (this.mMode & 256) != 0) {
                    ((MotionLayout)object).setTransition(this.mTransition);
                    ((MotionLayout)object).setProgress(1.0f);
                    return;
                }
                if (n == 0) return;
                if ((this.mMode & 4096) == 0) return;
                ((MotionLayout)object).setTransition(this.mTransition);
                ((MotionLayout)object).setProgress(0.0f);
            }

            public void removeOnClickListeners(MotionLayout object) {
                int n = this.mTargetId;
                if (n == -1) {
                    return;
                }
                if ((object = object.findViewById(n)) == null) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append(" (*)  could not find id ");
                    ((StringBuilder)object).append(this.mTargetId);
                    Log.e((String)MotionScene.TAG, (String)((StringBuilder)object).toString());
                    return;
                }
                object.setOnClickListener(null);
            }
        }

    }

}


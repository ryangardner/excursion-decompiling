/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.graphics.Color
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.SparseArray
 *  android.util.SparseIntArray
 *  android.util.Xml
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 */
package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import androidx.constraintlayout.widget.Barrier;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintHelper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Constraints;
import androidx.constraintlayout.widget.Guideline;
import androidx.constraintlayout.widget.R;
import java.io.IOException;
import java.io.PrintStream;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ConstraintSet {
    private static final int ALPHA = 43;
    private static final int ANIMATE_RELATIVE_TO = 64;
    private static final int BARRIER_ALLOWS_GONE_WIDGETS = 75;
    private static final int BARRIER_DIRECTION = 72;
    private static final int BARRIER_MARGIN = 73;
    private static final int BARRIER_TYPE = 1;
    public static final int BASELINE = 5;
    private static final int BASELINE_TO_BASELINE = 1;
    public static final int BOTTOM = 4;
    private static final int BOTTOM_MARGIN = 2;
    private static final int BOTTOM_TO_BOTTOM = 3;
    private static final int BOTTOM_TO_TOP = 4;
    public static final int CHAIN_PACKED = 2;
    public static final int CHAIN_SPREAD = 0;
    public static final int CHAIN_SPREAD_INSIDE = 1;
    private static final int CHAIN_USE_RTL = 71;
    private static final int CIRCLE = 61;
    private static final int CIRCLE_ANGLE = 63;
    private static final int CIRCLE_RADIUS = 62;
    private static final int CONSTRAINED_HEIGHT = 81;
    private static final int CONSTRAINED_WIDTH = 80;
    private static final int CONSTRAINT_REFERENCED_IDS = 74;
    private static final int CONSTRAINT_TAG = 77;
    private static final boolean DEBUG = false;
    private static final int DIMENSION_RATIO = 5;
    private static final int DRAW_PATH = 66;
    private static final int EDITOR_ABSOLUTE_X = 6;
    private static final int EDITOR_ABSOLUTE_Y = 7;
    private static final int ELEVATION = 44;
    public static final int END = 7;
    private static final int END_MARGIN = 8;
    private static final int END_TO_END = 9;
    private static final int END_TO_START = 10;
    private static final String ERROR_MESSAGE = "XML parser error must be within a Constraint ";
    public static final int GONE = 8;
    private static final int GONE_BOTTOM_MARGIN = 11;
    private static final int GONE_END_MARGIN = 12;
    private static final int GONE_LEFT_MARGIN = 13;
    private static final int GONE_RIGHT_MARGIN = 14;
    private static final int GONE_START_MARGIN = 15;
    private static final int GONE_TOP_MARGIN = 16;
    private static final int GUIDE_BEGIN = 17;
    private static final int GUIDE_END = 18;
    private static final int GUIDE_PERCENT = 19;
    private static final int HEIGHT_DEFAULT = 55;
    private static final int HEIGHT_MAX = 57;
    private static final int HEIGHT_MIN = 59;
    private static final int HEIGHT_PERCENT = 70;
    public static final int HORIZONTAL = 0;
    private static final int HORIZONTAL_BIAS = 20;
    public static final int HORIZONTAL_GUIDELINE = 0;
    private static final int HORIZONTAL_STYLE = 41;
    private static final int HORIZONTAL_WEIGHT = 39;
    public static final int INVISIBLE = 4;
    private static final int LAYOUT_HEIGHT = 21;
    private static final int LAYOUT_VISIBILITY = 22;
    private static final int LAYOUT_WIDTH = 23;
    public static final int LEFT = 1;
    private static final int LEFT_MARGIN = 24;
    private static final int LEFT_TO_LEFT = 25;
    private static final int LEFT_TO_RIGHT = 26;
    public static final int MATCH_CONSTRAINT = 0;
    public static final int MATCH_CONSTRAINT_SPREAD = 0;
    public static final int MATCH_CONSTRAINT_WRAP = 1;
    private static final int MOTION_STAGGER = 79;
    private static final int ORIENTATION = 27;
    public static final int PARENT_ID = 0;
    private static final int PATH_MOTION_ARC = 76;
    private static final int PROGRESS = 68;
    public static final int RIGHT = 2;
    private static final int RIGHT_MARGIN = 28;
    private static final int RIGHT_TO_LEFT = 29;
    private static final int RIGHT_TO_RIGHT = 30;
    private static final int ROTATION = 60;
    private static final int ROTATION_X = 45;
    private static final int ROTATION_Y = 46;
    private static final int SCALE_X = 47;
    private static final int SCALE_Y = 48;
    public static final int START = 6;
    private static final int START_MARGIN = 31;
    private static final int START_TO_END = 32;
    private static final int START_TO_START = 33;
    private static final String TAG = "ConstraintSet";
    public static final int TOP = 3;
    private static final int TOP_MARGIN = 34;
    private static final int TOP_TO_BOTTOM = 35;
    private static final int TOP_TO_TOP = 36;
    private static final int TRANSFORM_PIVOT_X = 49;
    private static final int TRANSFORM_PIVOT_Y = 50;
    private static final int TRANSITION_EASING = 65;
    private static final int TRANSITION_PATH_ROTATE = 67;
    private static final int TRANSLATION_X = 51;
    private static final int TRANSLATION_Y = 52;
    private static final int TRANSLATION_Z = 53;
    public static final int UNSET = -1;
    private static final int UNUSED = 82;
    public static final int VERTICAL = 1;
    private static final int VERTICAL_BIAS = 37;
    public static final int VERTICAL_GUIDELINE = 1;
    private static final int VERTICAL_STYLE = 42;
    private static final int VERTICAL_WEIGHT = 40;
    private static final int VIEW_ID = 38;
    private static final int[] VISIBILITY_FLAGS;
    private static final int VISIBILITY_MODE = 78;
    public static final int VISIBILITY_MODE_IGNORE = 1;
    public static final int VISIBILITY_MODE_NORMAL = 0;
    public static final int VISIBLE = 0;
    private static final int WIDTH_DEFAULT = 54;
    private static final int WIDTH_MAX = 56;
    private static final int WIDTH_MIN = 58;
    private static final int WIDTH_PERCENT = 69;
    public static final int WRAP_CONTENT = -2;
    private static SparseIntArray mapToConstant;
    private HashMap<Integer, Constraint> mConstraints = new HashMap();
    private boolean mForceId = true;
    private HashMap<String, ConstraintAttribute> mSavedAttributes = new HashMap();
    private boolean mValidate;

    static {
        SparseIntArray sparseIntArray;
        VISIBILITY_FLAGS = new int[]{0, 4, 8};
        mapToConstant = sparseIntArray = new SparseIntArray();
        sparseIntArray.append(R.styleable.Constraint_layout_constraintLeft_toLeftOf, 25);
        mapToConstant.append(R.styleable.Constraint_layout_constraintLeft_toRightOf, 26);
        mapToConstant.append(R.styleable.Constraint_layout_constraintRight_toLeftOf, 29);
        mapToConstant.append(R.styleable.Constraint_layout_constraintRight_toRightOf, 30);
        mapToConstant.append(R.styleable.Constraint_layout_constraintTop_toTopOf, 36);
        mapToConstant.append(R.styleable.Constraint_layout_constraintTop_toBottomOf, 35);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_toTopOf, 4);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_toBottomOf, 3);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_toBaselineOf, 1);
        mapToConstant.append(R.styleable.Constraint_layout_editor_absoluteX, 6);
        mapToConstant.append(R.styleable.Constraint_layout_editor_absoluteY, 7);
        mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_begin, 17);
        mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_end, 18);
        mapToConstant.append(R.styleable.Constraint_layout_constraintGuide_percent, 19);
        mapToConstant.append(R.styleable.Constraint_android_orientation, 27);
        mapToConstant.append(R.styleable.Constraint_layout_constraintStart_toEndOf, 32);
        mapToConstant.append(R.styleable.Constraint_layout_constraintStart_toStartOf, 33);
        mapToConstant.append(R.styleable.Constraint_layout_constraintEnd_toStartOf, 10);
        mapToConstant.append(R.styleable.Constraint_layout_constraintEnd_toEndOf, 9);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginLeft, 13);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginTop, 16);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginRight, 14);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginBottom, 11);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginStart, 15);
        mapToConstant.append(R.styleable.Constraint_layout_goneMarginEnd, 12);
        mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_weight, 40);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_weight, 39);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_chainStyle, 41);
        mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_chainStyle, 42);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHorizontal_bias, 20);
        mapToConstant.append(R.styleable.Constraint_layout_constraintVertical_bias, 37);
        mapToConstant.append(R.styleable.Constraint_layout_constraintDimensionRatio, 5);
        mapToConstant.append(R.styleable.Constraint_layout_constraintLeft_creator, 82);
        mapToConstant.append(R.styleable.Constraint_layout_constraintTop_creator, 82);
        mapToConstant.append(R.styleable.Constraint_layout_constraintRight_creator, 82);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBottom_creator, 82);
        mapToConstant.append(R.styleable.Constraint_layout_constraintBaseline_creator, 82);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginLeft, 24);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginRight, 28);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginStart, 31);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginEnd, 8);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginTop, 34);
        mapToConstant.append(R.styleable.Constraint_android_layout_marginBottom, 2);
        mapToConstant.append(R.styleable.Constraint_android_layout_width, 23);
        mapToConstant.append(R.styleable.Constraint_android_layout_height, 21);
        mapToConstant.append(R.styleable.Constraint_android_visibility, 22);
        mapToConstant.append(R.styleable.Constraint_android_alpha, 43);
        mapToConstant.append(R.styleable.Constraint_android_elevation, 44);
        mapToConstant.append(R.styleable.Constraint_android_rotationX, 45);
        mapToConstant.append(R.styleable.Constraint_android_rotationY, 46);
        mapToConstant.append(R.styleable.Constraint_android_rotation, 60);
        mapToConstant.append(R.styleable.Constraint_android_scaleX, 47);
        mapToConstant.append(R.styleable.Constraint_android_scaleY, 48);
        mapToConstant.append(R.styleable.Constraint_android_transformPivotX, 49);
        mapToConstant.append(R.styleable.Constraint_android_transformPivotY, 50);
        mapToConstant.append(R.styleable.Constraint_android_translationX, 51);
        mapToConstant.append(R.styleable.Constraint_android_translationY, 52);
        mapToConstant.append(R.styleable.Constraint_android_translationZ, 53);
        mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_default, 54);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_default, 55);
        mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_max, 56);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_max, 57);
        mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_min, 58);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_min, 59);
        mapToConstant.append(R.styleable.Constraint_layout_constraintCircle, 61);
        mapToConstant.append(R.styleable.Constraint_layout_constraintCircleRadius, 62);
        mapToConstant.append(R.styleable.Constraint_layout_constraintCircleAngle, 63);
        mapToConstant.append(R.styleable.Constraint_animate_relativeTo, 64);
        mapToConstant.append(R.styleable.Constraint_transitionEasing, 65);
        mapToConstant.append(R.styleable.Constraint_drawPath, 66);
        mapToConstant.append(R.styleable.Constraint_transitionPathRotate, 67);
        mapToConstant.append(R.styleable.Constraint_motionStagger, 79);
        mapToConstant.append(R.styleable.Constraint_android_id, 38);
        mapToConstant.append(R.styleable.Constraint_motionProgress, 68);
        mapToConstant.append(R.styleable.Constraint_layout_constraintWidth_percent, 69);
        mapToConstant.append(R.styleable.Constraint_layout_constraintHeight_percent, 70);
        mapToConstant.append(R.styleable.Constraint_chainUseRtl, 71);
        mapToConstant.append(R.styleable.Constraint_barrierDirection, 72);
        mapToConstant.append(R.styleable.Constraint_barrierMargin, 73);
        mapToConstant.append(R.styleable.Constraint_constraint_referenced_ids, 74);
        mapToConstant.append(R.styleable.Constraint_barrierAllowsGoneWidgets, 75);
        mapToConstant.append(R.styleable.Constraint_pathMotionArc, 76);
        mapToConstant.append(R.styleable.Constraint_layout_constraintTag, 77);
        mapToConstant.append(R.styleable.Constraint_visibilityMode, 78);
        mapToConstant.append(R.styleable.Constraint_layout_constrainedWidth, 80);
        mapToConstant.append(R.styleable.Constraint_layout_constrainedHeight, 81);
    }

    static /* synthetic */ int access$100(TypedArray typedArray, int n, int n2) {
        return ConstraintSet.lookupID(typedArray, n, n2);
    }

    private void addAttributes(ConstraintAttribute.AttributeType object, String ... arrstring) {
        int n = 0;
        while (n < arrstring.length) {
            ConstraintAttribute constraintAttribute;
            if (this.mSavedAttributes.containsKey(arrstring[n])) {
                constraintAttribute = this.mSavedAttributes.get(arrstring[n]);
                if (constraintAttribute.getType() != object) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("ConstraintAttribute is already a ");
                    ((StringBuilder)object).append(constraintAttribute.getType().name());
                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                }
            } else {
                constraintAttribute = new ConstraintAttribute(arrstring[n], (ConstraintAttribute.AttributeType)((Object)object));
                this.mSavedAttributes.put(arrstring[n], constraintAttribute);
            }
            ++n;
        }
    }

    private int[] convertReferenceString(View arrn, String arrn2) {
        String[] arrstring = arrn2.split(",");
        Context context = arrn.getContext();
        arrn2 = new int[arrstring.length];
        int n = 0;
        int n2 = 0;
        do {
            int n3;
            if (n >= arrstring.length) {
                arrn = arrn2;
                if (n2 == arrstring.length) return arrn;
                return Arrays.copyOf(arrn2, n2);
            }
            Object object = arrstring[n].trim();
            try {
                n3 = R.id.class.getField((String)object).getInt(null);
            }
            catch (Exception exception) {
                n3 = 0;
            }
            int n4 = n3;
            if (n3 == 0) {
                n4 = context.getResources().getIdentifier((String)object, "id", context.getPackageName());
            }
            n3 = n4;
            if (n4 == 0) {
                n3 = n4;
                if (arrn.isInEditMode()) {
                    n3 = n4;
                    if (arrn.getParent() instanceof ConstraintLayout) {
                        object = ((ConstraintLayout)arrn.getParent()).getDesignInformation(0, object);
                        n3 = n4;
                        if (object != null) {
                            n3 = n4;
                            if (object instanceof Integer) {
                                n3 = (Integer)object;
                            }
                        }
                    }
                }
            }
            arrn2[n2] = n3;
            ++n;
            ++n2;
        } while (true);
    }

    private void createHorizontalChain(int n, int n2, int n3, int n4, int[] arrn, float[] arrf, int n5, int n6, int n7) {
        if (arrn.length < 2) throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        if (arrf != null) {
            if (arrf.length != arrn.length) throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
        if (arrf != null) {
            this.get((int)arrn[0]).layout.horizontalWeight = arrf[0];
        }
        this.get((int)arrn[0]).layout.horizontalChainStyle = n5;
        this.connect(arrn[0], n6, n, n2, -1);
        n = 1;
        do {
            if (n >= arrn.length) {
                this.connect(arrn[arrn.length - 1], n7, n3, n4, -1);
                return;
            }
            n2 = arrn[n];
            n5 = arrn[n];
            n2 = n - 1;
            this.connect(n5, n6, arrn[n2], n7, -1);
            this.connect(arrn[n2], n7, arrn[n], n6, -1);
            if (arrf != null) {
                this.get((int)arrn[n]).layout.horizontalWeight = arrf[n];
            }
            ++n;
        } while (true);
    }

    private Constraint fillFromAttributeList(Context context, AttributeSet attributeSet) {
        Constraint constraint = new Constraint();
        attributeSet = context.obtainStyledAttributes(attributeSet, R.styleable.Constraint);
        this.populateConstraint(context, constraint, (TypedArray)attributeSet);
        attributeSet.recycle();
        return constraint;
    }

    private Constraint get(int n) {
        if (this.mConstraints.containsKey(n)) return this.mConstraints.get(n);
        this.mConstraints.put(n, new Constraint());
        return this.mConstraints.get(n);
    }

    private static int lookupID(TypedArray typedArray, int n, int n2) {
        int n3;
        n2 = n3 = typedArray.getResourceId(n, n2);
        if (n3 != -1) return n2;
        return typedArray.getInt(n, -1);
    }

    private void populateConstraint(Context object, Constraint constraint, TypedArray typedArray) {
        int n = typedArray.getIndexCount();
        int n2 = 0;
        while (n2 < n) {
            int n3 = typedArray.getIndex(n2);
            if (n3 != R.styleable.Constraint_android_id && R.styleable.Constraint_android_layout_marginStart != n3 && R.styleable.Constraint_android_layout_marginEnd != n3) {
                constraint.motion.mApply = true;
                constraint.layout.mApply = true;
                constraint.propertySet.mApply = true;
                constraint.transform.mApply = true;
            }
            switch (mapToConstant.get(n3)) {
                default: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Unknown attribute 0x");
                    ((StringBuilder)object).append(Integer.toHexString(n3));
                    ((StringBuilder)object).append("   ");
                    ((StringBuilder)object).append(mapToConstant.get(n3));
                    Log.w((String)TAG, (String)((StringBuilder)object).toString());
                    break;
                }
                case 82: {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("unused attribute 0x");
                    ((StringBuilder)object).append(Integer.toHexString(n3));
                    ((StringBuilder)object).append("   ");
                    ((StringBuilder)object).append(mapToConstant.get(n3));
                    Log.w((String)TAG, (String)((StringBuilder)object).toString());
                    break;
                }
                case 81: {
                    constraint.layout.constrainedHeight = typedArray.getBoolean(n3, constraint.layout.constrainedHeight);
                    break;
                }
                case 80: {
                    constraint.layout.constrainedWidth = typedArray.getBoolean(n3, constraint.layout.constrainedWidth);
                    break;
                }
                case 79: {
                    constraint.motion.mMotionStagger = typedArray.getFloat(n3, constraint.motion.mMotionStagger);
                    break;
                }
                case 78: {
                    constraint.propertySet.mVisibilityMode = typedArray.getInt(n3, constraint.propertySet.mVisibilityMode);
                    break;
                }
                case 77: {
                    constraint.layout.mConstraintTag = typedArray.getString(n3);
                    break;
                }
                case 76: {
                    constraint.motion.mPathMotionArc = typedArray.getInt(n3, constraint.motion.mPathMotionArc);
                    break;
                }
                case 75: {
                    constraint.layout.mBarrierAllowsGoneWidgets = typedArray.getBoolean(n3, constraint.layout.mBarrierAllowsGoneWidgets);
                    break;
                }
                case 74: {
                    constraint.layout.mReferenceIdString = typedArray.getString(n3);
                    break;
                }
                case 73: {
                    constraint.layout.mBarrierMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.mBarrierMargin);
                    break;
                }
                case 72: {
                    constraint.layout.mBarrierDirection = typedArray.getInt(n3, constraint.layout.mBarrierDirection);
                    break;
                }
                case 71: {
                    Log.e((String)TAG, (String)"CURRENTLY UNSUPPORTED");
                    break;
                }
                case 70: {
                    constraint.layout.heightPercent = typedArray.getFloat(n3, 1.0f);
                    break;
                }
                case 69: {
                    constraint.layout.widthPercent = typedArray.getFloat(n3, 1.0f);
                    break;
                }
                case 68: {
                    constraint.propertySet.mProgress = typedArray.getFloat(n3, constraint.propertySet.mProgress);
                    break;
                }
                case 67: {
                    constraint.motion.mPathRotate = typedArray.getFloat(n3, constraint.motion.mPathRotate);
                    break;
                }
                case 66: {
                    constraint.motion.mDrawPath = typedArray.getInt(n3, 0);
                    break;
                }
                case 65: {
                    if (typedArray.peekValue((int)n3).type == 3) {
                        constraint.motion.mTransitionEasing = typedArray.getString(n3);
                        break;
                    }
                    constraint.motion.mTransitionEasing = Easing.NAMED_EASING[typedArray.getInteger(n3, 0)];
                    break;
                }
                case 64: {
                    constraint.motion.mAnimateRelativeTo = ConstraintSet.lookupID(typedArray, n3, constraint.motion.mAnimateRelativeTo);
                    break;
                }
                case 63: {
                    constraint.layout.circleAngle = typedArray.getFloat(n3, constraint.layout.circleAngle);
                    break;
                }
                case 62: {
                    constraint.layout.circleRadius = typedArray.getDimensionPixelSize(n3, constraint.layout.circleRadius);
                    break;
                }
                case 61: {
                    constraint.layout.circleConstraint = ConstraintSet.lookupID(typedArray, n3, constraint.layout.circleConstraint);
                    break;
                }
                case 60: {
                    constraint.transform.rotation = typedArray.getFloat(n3, constraint.transform.rotation);
                    break;
                }
                case 59: {
                    constraint.layout.heightMin = typedArray.getDimensionPixelSize(n3, constraint.layout.heightMin);
                    break;
                }
                case 58: {
                    constraint.layout.widthMin = typedArray.getDimensionPixelSize(n3, constraint.layout.widthMin);
                    break;
                }
                case 57: {
                    constraint.layout.heightMax = typedArray.getDimensionPixelSize(n3, constraint.layout.heightMax);
                    break;
                }
                case 56: {
                    constraint.layout.widthMax = typedArray.getDimensionPixelSize(n3, constraint.layout.widthMax);
                    break;
                }
                case 55: {
                    constraint.layout.heightDefault = typedArray.getInt(n3, constraint.layout.heightDefault);
                    break;
                }
                case 54: {
                    constraint.layout.widthDefault = typedArray.getInt(n3, constraint.layout.widthDefault);
                    break;
                }
                case 53: {
                    if (Build.VERSION.SDK_INT < 21) break;
                    constraint.transform.translationZ = typedArray.getDimension(n3, constraint.transform.translationZ);
                    break;
                }
                case 52: {
                    constraint.transform.translationY = typedArray.getDimension(n3, constraint.transform.translationY);
                    break;
                }
                case 51: {
                    constraint.transform.translationX = typedArray.getDimension(n3, constraint.transform.translationX);
                    break;
                }
                case 50: {
                    constraint.transform.transformPivotY = typedArray.getDimension(n3, constraint.transform.transformPivotY);
                    break;
                }
                case 49: {
                    constraint.transform.transformPivotX = typedArray.getDimension(n3, constraint.transform.transformPivotX);
                    break;
                }
                case 48: {
                    constraint.transform.scaleY = typedArray.getFloat(n3, constraint.transform.scaleY);
                    break;
                }
                case 47: {
                    constraint.transform.scaleX = typedArray.getFloat(n3, constraint.transform.scaleX);
                    break;
                }
                case 46: {
                    constraint.transform.rotationY = typedArray.getFloat(n3, constraint.transform.rotationY);
                    break;
                }
                case 45: {
                    constraint.transform.rotationX = typedArray.getFloat(n3, constraint.transform.rotationX);
                    break;
                }
                case 44: {
                    if (Build.VERSION.SDK_INT < 21) break;
                    constraint.transform.applyElevation = true;
                    constraint.transform.elevation = typedArray.getDimension(n3, constraint.transform.elevation);
                    break;
                }
                case 43: {
                    constraint.propertySet.alpha = typedArray.getFloat(n3, constraint.propertySet.alpha);
                    break;
                }
                case 42: {
                    constraint.layout.verticalChainStyle = typedArray.getInt(n3, constraint.layout.verticalChainStyle);
                    break;
                }
                case 41: {
                    constraint.layout.horizontalChainStyle = typedArray.getInt(n3, constraint.layout.horizontalChainStyle);
                    break;
                }
                case 40: {
                    constraint.layout.verticalWeight = typedArray.getFloat(n3, constraint.layout.verticalWeight);
                    break;
                }
                case 39: {
                    constraint.layout.horizontalWeight = typedArray.getFloat(n3, constraint.layout.horizontalWeight);
                    break;
                }
                case 38: {
                    constraint.mViewId = typedArray.getResourceId(n3, constraint.mViewId);
                    break;
                }
                case 37: {
                    constraint.layout.verticalBias = typedArray.getFloat(n3, constraint.layout.verticalBias);
                    break;
                }
                case 36: {
                    constraint.layout.topToTop = ConstraintSet.lookupID(typedArray, n3, constraint.layout.topToTop);
                    break;
                }
                case 35: {
                    constraint.layout.topToBottom = ConstraintSet.lookupID(typedArray, n3, constraint.layout.topToBottom);
                    break;
                }
                case 34: {
                    constraint.layout.topMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.topMargin);
                    break;
                }
                case 33: {
                    constraint.layout.startToStart = ConstraintSet.lookupID(typedArray, n3, constraint.layout.startToStart);
                    break;
                }
                case 32: {
                    constraint.layout.startToEnd = ConstraintSet.lookupID(typedArray, n3, constraint.layout.startToEnd);
                    break;
                }
                case 31: {
                    if (Build.VERSION.SDK_INT < 17) break;
                    constraint.layout.startMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.startMargin);
                    break;
                }
                case 30: {
                    constraint.layout.rightToRight = ConstraintSet.lookupID(typedArray, n3, constraint.layout.rightToRight);
                    break;
                }
                case 29: {
                    constraint.layout.rightToLeft = ConstraintSet.lookupID(typedArray, n3, constraint.layout.rightToLeft);
                    break;
                }
                case 28: {
                    constraint.layout.rightMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.rightMargin);
                    break;
                }
                case 27: {
                    constraint.layout.orientation = typedArray.getInt(n3, constraint.layout.orientation);
                    break;
                }
                case 26: {
                    constraint.layout.leftToRight = ConstraintSet.lookupID(typedArray, n3, constraint.layout.leftToRight);
                    break;
                }
                case 25: {
                    constraint.layout.leftToLeft = ConstraintSet.lookupID(typedArray, n3, constraint.layout.leftToLeft);
                    break;
                }
                case 24: {
                    constraint.layout.leftMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.leftMargin);
                    break;
                }
                case 23: {
                    constraint.layout.mWidth = typedArray.getLayoutDimension(n3, constraint.layout.mWidth);
                    break;
                }
                case 22: {
                    constraint.propertySet.visibility = typedArray.getInt(n3, constraint.propertySet.visibility);
                    constraint.propertySet.visibility = VISIBILITY_FLAGS[constraint.propertySet.visibility];
                    break;
                }
                case 21: {
                    constraint.layout.mHeight = typedArray.getLayoutDimension(n3, constraint.layout.mHeight);
                    break;
                }
                case 20: {
                    constraint.layout.horizontalBias = typedArray.getFloat(n3, constraint.layout.horizontalBias);
                    break;
                }
                case 19: {
                    constraint.layout.guidePercent = typedArray.getFloat(n3, constraint.layout.guidePercent);
                    break;
                }
                case 18: {
                    constraint.layout.guideEnd = typedArray.getDimensionPixelOffset(n3, constraint.layout.guideEnd);
                    break;
                }
                case 17: {
                    constraint.layout.guideBegin = typedArray.getDimensionPixelOffset(n3, constraint.layout.guideBegin);
                    break;
                }
                case 16: {
                    constraint.layout.goneTopMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.goneTopMargin);
                    break;
                }
                case 15: {
                    constraint.layout.goneStartMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.goneStartMargin);
                    break;
                }
                case 14: {
                    constraint.layout.goneRightMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.goneRightMargin);
                    break;
                }
                case 13: {
                    constraint.layout.goneLeftMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.goneLeftMargin);
                    break;
                }
                case 12: {
                    constraint.layout.goneEndMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.goneEndMargin);
                    break;
                }
                case 11: {
                    constraint.layout.goneBottomMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.goneBottomMargin);
                    break;
                }
                case 10: {
                    constraint.layout.endToStart = ConstraintSet.lookupID(typedArray, n3, constraint.layout.endToStart);
                    break;
                }
                case 9: {
                    constraint.layout.endToEnd = ConstraintSet.lookupID(typedArray, n3, constraint.layout.endToEnd);
                    break;
                }
                case 8: {
                    if (Build.VERSION.SDK_INT < 17) break;
                    constraint.layout.endMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.endMargin);
                    break;
                }
                case 7: {
                    constraint.layout.editorAbsoluteY = typedArray.getDimensionPixelOffset(n3, constraint.layout.editorAbsoluteY);
                    break;
                }
                case 6: {
                    constraint.layout.editorAbsoluteX = typedArray.getDimensionPixelOffset(n3, constraint.layout.editorAbsoluteX);
                    break;
                }
                case 5: {
                    constraint.layout.dimensionRatio = typedArray.getString(n3);
                    break;
                }
                case 4: {
                    constraint.layout.bottomToTop = ConstraintSet.lookupID(typedArray, n3, constraint.layout.bottomToTop);
                    break;
                }
                case 3: {
                    constraint.layout.bottomToBottom = ConstraintSet.lookupID(typedArray, n3, constraint.layout.bottomToBottom);
                    break;
                }
                case 2: {
                    constraint.layout.bottomMargin = typedArray.getDimensionPixelSize(n3, constraint.layout.bottomMargin);
                    break;
                }
                case 1: {
                    constraint.layout.baselineToBaseline = ConstraintSet.lookupID(typedArray, n3, constraint.layout.baselineToBaseline);
                }
            }
            ++n2;
        }
    }

    private String sideToString(int n) {
        switch (n) {
            default: {
                return "undefined";
            }
            case 7: {
                return "end";
            }
            case 6: {
                return "start";
            }
            case 5: {
                return "baseline";
            }
            case 4: {
                return "bottom";
            }
            case 3: {
                return "top";
            }
            case 2: {
                return "right";
            }
            case 1: 
        }
        return "left";
    }

    private static String[] splitString(String object) {
        char[] arrc = ((String)object).toCharArray();
        object = new ArrayList();
        int n = 0;
        int n2 = 0;
        boolean bl = false;
        do {
            boolean bl2;
            int n3;
            if (n >= arrc.length) {
                ((ArrayList)object).add(new String(arrc, n2, arrc.length - n2));
                return ((ArrayList)object).toArray(new String[((ArrayList)object).size()]);
            }
            if (arrc[n] == ',' && !bl) {
                ((ArrayList)object).add(new String(arrc, n2, n - n2));
                n3 = n + 1;
                bl2 = bl;
            } else {
                n3 = n2;
                bl2 = bl;
                if (arrc[n] == '\"') {
                    bl2 = bl ^ true;
                    n3 = n2;
                }
            }
            ++n;
            n2 = n3;
            bl = bl2;
        } while (true);
    }

    public void addColorAttributes(String ... arrstring) {
        this.addAttributes(ConstraintAttribute.AttributeType.COLOR_TYPE, arrstring);
    }

    public void addFloatAttributes(String ... arrstring) {
        this.addAttributes(ConstraintAttribute.AttributeType.FLOAT_TYPE, arrstring);
    }

    public void addIntAttributes(String ... arrstring) {
        this.addAttributes(ConstraintAttribute.AttributeType.INT_TYPE, arrstring);
    }

    public void addStringAttributes(String ... arrstring) {
        this.addAttributes(ConstraintAttribute.AttributeType.STRING_TYPE, arrstring);
    }

    public void addToHorizontalChain(int n, int n2, int n3) {
        int n4 = n2 == 0 ? 1 : 2;
        this.connect(n, 1, n2, n4, 0);
        n4 = n3 == 0 ? 2 : 1;
        this.connect(n, 2, n3, n4, 0);
        if (n2 != 0) {
            this.connect(n2, 2, n, 1, 0);
        }
        if (n3 == 0) return;
        this.connect(n3, 1, n, 2, 0);
    }

    public void addToHorizontalChainRTL(int n, int n2, int n3) {
        int n4 = n2 == 0 ? 6 : 7;
        this.connect(n, 6, n2, n4, 0);
        n4 = n3 == 0 ? 7 : 6;
        this.connect(n, 7, n3, n4, 0);
        if (n2 != 0) {
            this.connect(n2, 7, n, 6, 0);
        }
        if (n3 == 0) return;
        this.connect(n3, 6, n, 7, 0);
    }

    public void addToVerticalChain(int n, int n2, int n3) {
        int n4 = n2 == 0 ? 3 : 4;
        this.connect(n, 3, n2, n4, 0);
        n4 = n3 == 0 ? 4 : 3;
        this.connect(n, 4, n3, n4, 0);
        if (n2 != 0) {
            this.connect(n2, 4, n, 3, 0);
        }
        if (n3 == 0) return;
        this.connect(n3, 3, n, 4, 0);
    }

    public void applyCustomAttributes(ConstraintLayout constraintLayout) {
        int n = constraintLayout.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view = constraintLayout.getChildAt(n2);
            int n3 = view.getId();
            if (!this.mConstraints.containsKey(n3)) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("id unknown ");
                stringBuilder.append(Debug.getName(view));
                Log.v((String)TAG, (String)stringBuilder.toString());
            } else {
                if (this.mForceId) {
                    if (n3 == -1) throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
                }
                if (this.mConstraints.containsKey(n3)) {
                    ConstraintAttribute.setAttributes(view, this.mConstraints.get((Object)Integer.valueOf((int)n3)).mCustomConstraints);
                }
            }
            ++n2;
        }
    }

    public void applyTo(ConstraintLayout constraintLayout) {
        this.applyToInternal(constraintLayout, true);
        constraintLayout.setConstraintSet(null);
        constraintLayout.requestLayout();
    }

    public void applyToHelper(ConstraintHelper constraintHelper, ConstraintWidget constraintWidget, ConstraintLayout.LayoutParams layoutParams, SparseArray<ConstraintWidget> sparseArray) {
        int n = constraintHelper.getId();
        if (!this.mConstraints.containsKey(n)) return;
        Constraint constraint = this.mConstraints.get(n);
        if (!(constraintWidget instanceof HelperWidget)) return;
        constraintHelper.loadParameters(constraint, (HelperWidget)constraintWidget, layoutParams, sparseArray);
    }

    void applyToInternal(ConstraintLayout constraintLayout, boolean bl) {
        Object object;
        Object object2;
        Object object3;
        int n = constraintLayout.getChildCount();
        Object object4 = new HashSet<Integer>(this.mConstraints.keySet());
        for (int i = 0; i < n; ++i) {
            object2 = constraintLayout.getChildAt(i);
            int n2 = object2.getId();
            if (!this.mConstraints.containsKey(n2)) {
                object3 = new StringBuilder();
                ((StringBuilder)object3).append("id unknown ");
                ((StringBuilder)object3).append(Debug.getName((View)object2));
                Log.w((String)TAG, (String)((StringBuilder)object3).toString());
                continue;
            }
            if (this.mForceId) {
                if (n2 == -1) throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (n2 == -1) continue;
            if (this.mConstraints.containsKey(n2)) {
                ((HashSet)object4).remove(n2);
                object3 = this.mConstraints.get(n2);
                if (object2 instanceof Barrier) {
                    object3.layout.mHelperType = 1;
                }
                if (object3.layout.mHelperType != -1 && object3.layout.mHelperType == 1) {
                    object = (Barrier)((Object)object2);
                    object.setId(n2);
                    ((Barrier)((Object)object)).setType(object3.layout.mBarrierDirection);
                    ((Barrier)((Object)object)).setMargin(object3.layout.mBarrierMargin);
                    ((Barrier)((Object)object)).setAllowsGoneWidget(object3.layout.mBarrierAllowsGoneWidgets);
                    if (object3.layout.mReferenceIds != null) {
                        ((ConstraintHelper)((Object)object)).setReferencedIds(object3.layout.mReferenceIds);
                    } else if (object3.layout.mReferenceIdString != null) {
                        object3.layout.mReferenceIds = this.convertReferenceString((View)object, object3.layout.mReferenceIdString);
                        ((ConstraintHelper)((Object)object)).setReferencedIds(object3.layout.mReferenceIds);
                    }
                }
                object = (ConstraintLayout.LayoutParams)object2.getLayoutParams();
                ((ConstraintLayout.LayoutParams)((Object)object)).validate();
                ((Constraint)object3).applyTo((ConstraintLayout.LayoutParams)((Object)object));
                if (bl) {
                    ConstraintAttribute.setAttributes((View)object2, ((Constraint)object3).mCustomConstraints);
                }
                object2.setLayoutParams((ViewGroup.LayoutParams)object);
                if (object3.propertySet.mVisibilityMode == 0) {
                    object2.setVisibility(object3.propertySet.visibility);
                }
                if (Build.VERSION.SDK_INT < 17) continue;
                object2.setAlpha(object3.propertySet.alpha);
                object2.setRotation(object3.transform.rotation);
                object2.setRotationX(object3.transform.rotationX);
                object2.setRotationY(object3.transform.rotationY);
                object2.setScaleX(object3.transform.scaleX);
                object2.setScaleY(object3.transform.scaleY);
                if (!Float.isNaN(object3.transform.transformPivotX)) {
                    object2.setPivotX(object3.transform.transformPivotX);
                }
                if (!Float.isNaN(object3.transform.transformPivotY)) {
                    object2.setPivotY(object3.transform.transformPivotY);
                }
                object2.setTranslationX(object3.transform.translationX);
                object2.setTranslationY(object3.transform.translationY);
                if (Build.VERSION.SDK_INT < 21) continue;
                object2.setTranslationZ(object3.transform.translationZ);
                if (!object3.transform.applyElevation) continue;
                object2.setElevation(object3.transform.elevation);
                continue;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("WARNING NO CONSTRAINTS for view ");
            ((StringBuilder)object2).append(n2);
            Log.v((String)TAG, (String)((StringBuilder)object2).toString());
        }
        object2 = ((HashSet)object4).iterator();
        while (object2.hasNext()) {
            object3 = (Integer)object2.next();
            object4 = this.mConstraints.get(object3);
            if (object4.layout.mHelperType != -1 && object4.layout.mHelperType == 1) {
                Barrier barrier = new Barrier(constraintLayout.getContext());
                barrier.setId(((Integer)object3).intValue());
                if (object4.layout.mReferenceIds != null) {
                    barrier.setReferencedIds(object4.layout.mReferenceIds);
                } else if (object4.layout.mReferenceIdString != null) {
                    object4.layout.mReferenceIds = this.convertReferenceString(barrier, object4.layout.mReferenceIdString);
                    barrier.setReferencedIds(object4.layout.mReferenceIds);
                }
                barrier.setType(object4.layout.mBarrierDirection);
                barrier.setMargin(object4.layout.mBarrierMargin);
                object = constraintLayout.generateDefaultLayoutParams();
                barrier.validateParams();
                ((Constraint)object4).applyTo((ConstraintLayout.LayoutParams)((Object)object));
                constraintLayout.addView((View)barrier, (ViewGroup.LayoutParams)object);
            }
            if (!object4.layout.mIsGuideline) continue;
            object = new Guideline(constraintLayout.getContext());
            object.setId(((Integer)object3).intValue());
            object3 = constraintLayout.generateDefaultLayoutParams();
            ((Constraint)object4).applyTo((ConstraintLayout.LayoutParams)((Object)object3));
            constraintLayout.addView((View)object, (ViewGroup.LayoutParams)object3);
        }
    }

    public void applyToLayoutParams(int n, ConstraintLayout.LayoutParams layoutParams) {
        if (!this.mConstraints.containsKey(n)) return;
        this.mConstraints.get(n).applyTo(layoutParams);
    }

    public void applyToWithoutCustom(ConstraintLayout constraintLayout) {
        this.applyToInternal(constraintLayout, false);
        constraintLayout.setConstraintSet(null);
    }

    public void center(int n, int n2, int n3, int n4, int n5, int n6, int n7, float f) {
        if (n4 < 0) throw new IllegalArgumentException("margin must be > 0");
        if (n7 < 0) throw new IllegalArgumentException("margin must be > 0");
        if (f <= 0.0f) throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
        if (f > 1.0f) throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
        if (n3 != 1 && n3 != 2) {
            if (n3 != 6 && n3 != 7) {
                this.connect(n, 3, n2, n3, n4);
                this.connect(n, 4, n5, n6, n7);
                this.mConstraints.get((Object)Integer.valueOf((int)n)).layout.verticalBias = f;
                return;
            }
            this.connect(n, 6, n2, n3, n4);
            this.connect(n, 7, n5, n6, n7);
            this.mConstraints.get((Object)Integer.valueOf((int)n)).layout.horizontalBias = f;
            return;
        }
        this.connect(n, 1, n2, n3, n4);
        this.connect(n, 2, n5, n6, n7);
        this.mConstraints.get((Object)Integer.valueOf((int)n)).layout.horizontalBias = f;
    }

    public void centerHorizontally(int n, int n2) {
        if (n2 == 0) {
            this.center(n, 0, 1, 0, 0, 2, 0, 0.5f);
            return;
        }
        this.center(n, n2, 2, 0, n2, 1, 0, 0.5f);
    }

    public void centerHorizontally(int n, int n2, int n3, int n4, int n5, int n6, int n7, float f) {
        this.connect(n, 1, n2, n3, n4);
        this.connect(n, 2, n5, n6, n7);
        this.mConstraints.get((Object)Integer.valueOf((int)n)).layout.horizontalBias = f;
    }

    public void centerHorizontallyRtl(int n, int n2) {
        if (n2 == 0) {
            this.center(n, 0, 6, 0, 0, 7, 0, 0.5f);
            return;
        }
        this.center(n, n2, 7, 0, n2, 6, 0, 0.5f);
    }

    public void centerHorizontallyRtl(int n, int n2, int n3, int n4, int n5, int n6, int n7, float f) {
        this.connect(n, 6, n2, n3, n4);
        this.connect(n, 7, n5, n6, n7);
        this.mConstraints.get((Object)Integer.valueOf((int)n)).layout.horizontalBias = f;
    }

    public void centerVertically(int n, int n2) {
        if (n2 == 0) {
            this.center(n, 0, 3, 0, 0, 4, 0, 0.5f);
            return;
        }
        this.center(n, n2, 4, 0, n2, 3, 0, 0.5f);
    }

    public void centerVertically(int n, int n2, int n3, int n4, int n5, int n6, int n7, float f) {
        this.connect(n, 3, n2, n3, n4);
        this.connect(n, 4, n5, n6, n7);
        this.mConstraints.get((Object)Integer.valueOf((int)n)).layout.verticalBias = f;
    }

    public void clear(int n) {
        this.mConstraints.remove(n);
    }

    public void clear(int n, int n2) {
        if (!this.mConstraints.containsKey(n)) return;
        Constraint constraint = this.mConstraints.get(n);
        switch (n2) {
            default: {
                throw new IllegalArgumentException("unknown constraint");
            }
            case 7: {
                constraint.layout.endToStart = -1;
                constraint.layout.endToEnd = -1;
                constraint.layout.endMargin = -1;
                constraint.layout.goneEndMargin = -1;
                return;
            }
            case 6: {
                constraint.layout.startToEnd = -1;
                constraint.layout.startToStart = -1;
                constraint.layout.startMargin = -1;
                constraint.layout.goneStartMargin = -1;
                return;
            }
            case 5: {
                constraint.layout.baselineToBaseline = -1;
                return;
            }
            case 4: {
                constraint.layout.bottomToTop = -1;
                constraint.layout.bottomToBottom = -1;
                constraint.layout.bottomMargin = -1;
                constraint.layout.goneBottomMargin = -1;
                return;
            }
            case 3: {
                constraint.layout.topToBottom = -1;
                constraint.layout.topToTop = -1;
                constraint.layout.topMargin = -1;
                constraint.layout.goneTopMargin = -1;
                return;
            }
            case 2: {
                constraint.layout.rightToRight = -1;
                constraint.layout.rightToLeft = -1;
                constraint.layout.rightMargin = -1;
                constraint.layout.goneRightMargin = -1;
                return;
            }
            case 1: 
        }
        constraint.layout.leftToRight = -1;
        constraint.layout.leftToLeft = -1;
        constraint.layout.leftMargin = -1;
        constraint.layout.goneLeftMargin = -1;
    }

    public void clone(Context context, int n) {
        this.clone((ConstraintLayout)LayoutInflater.from((Context)context).inflate(n, null));
    }

    public void clone(ConstraintLayout constraintLayout) {
        int n = constraintLayout.getChildCount();
        this.mConstraints.clear();
        int n2 = 0;
        while (n2 < n) {
            View view = constraintLayout.getChildAt(n2);
            Object object = (ConstraintLayout.LayoutParams)view.getLayoutParams();
            int n3 = view.getId();
            if (this.mForceId) {
                if (n3 == -1) throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.mConstraints.containsKey(n3)) {
                this.mConstraints.put(n3, new Constraint());
            }
            Constraint constraint = this.mConstraints.get(n3);
            constraint.mCustomConstraints = ConstraintAttribute.extractAttributes(this.mSavedAttributes, view);
            constraint.fillFrom(n3, (ConstraintLayout.LayoutParams)((Object)object));
            constraint.propertySet.visibility = view.getVisibility();
            if (Build.VERSION.SDK_INT >= 17) {
                constraint.propertySet.alpha = view.getAlpha();
                constraint.transform.rotation = view.getRotation();
                constraint.transform.rotationX = view.getRotationX();
                constraint.transform.rotationY = view.getRotationY();
                constraint.transform.scaleX = view.getScaleX();
                constraint.transform.scaleY = view.getScaleY();
                float f = view.getPivotX();
                float f2 = view.getPivotY();
                if ((double)f != 0.0 || (double)f2 != 0.0) {
                    constraint.transform.transformPivotX = f;
                    constraint.transform.transformPivotY = f2;
                }
                constraint.transform.translationX = view.getTranslationX();
                constraint.transform.translationY = view.getTranslationY();
                if (Build.VERSION.SDK_INT >= 21) {
                    constraint.transform.translationZ = view.getTranslationZ();
                    if (constraint.transform.applyElevation) {
                        constraint.transform.elevation = view.getElevation();
                    }
                }
            }
            if (view instanceof Barrier) {
                object = (Barrier)view;
                constraint.layout.mBarrierAllowsGoneWidgets = ((Barrier)((Object)object)).allowsGoneWidget();
                constraint.layout.mReferenceIds = ((ConstraintHelper)((Object)object)).getReferencedIds();
                constraint.layout.mBarrierDirection = ((Barrier)((Object)object)).getType();
                constraint.layout.mBarrierMargin = ((Barrier)((Object)object)).getMargin();
            }
            ++n2;
        }
    }

    public void clone(ConstraintSet constraintSet) {
        this.mConstraints.clear();
        Iterator<Integer> iterator2 = constraintSet.mConstraints.keySet().iterator();
        while (iterator2.hasNext()) {
            Integer n = iterator2.next();
            this.mConstraints.put(n, constraintSet.mConstraints.get(n).clone());
        }
    }

    public void clone(Constraints constraints) {
        int n = constraints.getChildCount();
        this.mConstraints.clear();
        int n2 = 0;
        while (n2 < n) {
            View view = constraints.getChildAt(n2);
            Constraints.LayoutParams layoutParams = (Constraints.LayoutParams)view.getLayoutParams();
            int n3 = view.getId();
            if (this.mForceId) {
                if (n3 == -1) throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.mConstraints.containsKey(n3)) {
                this.mConstraints.put(n3, new Constraint());
            }
            Constraint constraint = this.mConstraints.get(n3);
            if (view instanceof ConstraintHelper) {
                constraint.fillFromConstraints((ConstraintHelper)view, n3, layoutParams);
            }
            constraint.fillFromConstraints(n3, layoutParams);
            ++n2;
        }
    }

    public void connect(int n, int n2, int n3, int n4) {
        if (!this.mConstraints.containsKey(n)) {
            this.mConstraints.put(n, new Constraint());
        }
        Object object = this.mConstraints.get(n);
        switch (n2) {
            default: {
                object = new StringBuilder();
                ((StringBuilder)object).append(this.sideToString(n2));
                ((StringBuilder)object).append(" to ");
                ((StringBuilder)object).append(this.sideToString(n4));
                ((StringBuilder)object).append(" unknown");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            case 7: {
                if (n4 == 7) {
                    object.layout.endToEnd = n3;
                    object.layout.endToStart = -1;
                    return;
                }
                if (n4 == 6) {
                    object.layout.endToStart = n3;
                    object.layout.endToEnd = -1;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("right to ");
                ((StringBuilder)object).append(this.sideToString(n4));
                ((StringBuilder)object).append(" undefined");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            case 6: {
                if (n4 == 6) {
                    object.layout.startToStart = n3;
                    object.layout.startToEnd = -1;
                    return;
                }
                if (n4 == 7) {
                    object.layout.startToEnd = n3;
                    object.layout.startToStart = -1;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("right to ");
                ((StringBuilder)object).append(this.sideToString(n4));
                ((StringBuilder)object).append(" undefined");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            case 5: {
                if (n4 == 5) {
                    object.layout.baselineToBaseline = n3;
                    object.layout.bottomToBottom = -1;
                    object.layout.bottomToTop = -1;
                    object.layout.topToTop = -1;
                    object.layout.topToBottom = -1;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("right to ");
                ((StringBuilder)object).append(this.sideToString(n4));
                ((StringBuilder)object).append(" undefined");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            case 4: {
                if (n4 == 4) {
                    object.layout.bottomToBottom = n3;
                    object.layout.bottomToTop = -1;
                    object.layout.baselineToBaseline = -1;
                    return;
                }
                if (n4 == 3) {
                    object.layout.bottomToTop = n3;
                    object.layout.bottomToBottom = -1;
                    object.layout.baselineToBaseline = -1;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("right to ");
                ((StringBuilder)object).append(this.sideToString(n4));
                ((StringBuilder)object).append(" undefined");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            case 3: {
                if (n4 == 3) {
                    object.layout.topToTop = n3;
                    object.layout.topToBottom = -1;
                    object.layout.baselineToBaseline = -1;
                    return;
                }
                if (n4 == 4) {
                    object.layout.topToBottom = n3;
                    object.layout.topToTop = -1;
                    object.layout.baselineToBaseline = -1;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("right to ");
                ((StringBuilder)object).append(this.sideToString(n4));
                ((StringBuilder)object).append(" undefined");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            case 2: {
                if (n4 == 1) {
                    object.layout.rightToLeft = n3;
                    object.layout.rightToRight = -1;
                    return;
                }
                if (n4 == 2) {
                    object.layout.rightToRight = n3;
                    object.layout.rightToLeft = -1;
                    return;
                }
                object = new StringBuilder();
                ((StringBuilder)object).append("right to ");
                ((StringBuilder)object).append(this.sideToString(n4));
                ((StringBuilder)object).append(" undefined");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            case 1: 
        }
        if (n4 == 1) {
            object.layout.leftToLeft = n3;
            object.layout.leftToRight = -1;
            return;
        }
        if (n4 == 2) {
            object.layout.leftToRight = n3;
            object.layout.leftToLeft = -1;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("left to ");
        ((StringBuilder)object).append(this.sideToString(n4));
        ((StringBuilder)object).append(" undefined");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public void connect(int n, int n2, int n3, int n4, int n5) {
        Object object;
        block31 : {
            block30 : {
                block29 : {
                    if (!this.mConstraints.containsKey(n)) {
                        this.mConstraints.put(n, new Constraint());
                    }
                    object = this.mConstraints.get(n);
                    switch (n2) {
                        default: {
                            object = new StringBuilder();
                            ((StringBuilder)object).append(this.sideToString(n2));
                            ((StringBuilder)object).append(" to ");
                            ((StringBuilder)object).append(this.sideToString(n4));
                            ((StringBuilder)object).append(" unknown");
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        case 7: {
                            if (n4 == 7) {
                                object.layout.endToEnd = n3;
                                object.layout.endToStart = -1;
                            } else {
                                if (n4 != 6) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("right to ");
                                    ((StringBuilder)object).append(this.sideToString(n4));
                                    ((StringBuilder)object).append(" undefined");
                                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                                }
                                object.layout.endToStart = n3;
                                object.layout.endToEnd = -1;
                            }
                            object.layout.endMargin = n5;
                            return;
                        }
                        case 6: {
                            if (n4 == 6) {
                                object.layout.startToStart = n3;
                                object.layout.startToEnd = -1;
                            } else {
                                if (n4 != 7) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("right to ");
                                    ((StringBuilder)object).append(this.sideToString(n4));
                                    ((StringBuilder)object).append(" undefined");
                                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                                }
                                object.layout.startToEnd = n3;
                                object.layout.startToStart = -1;
                            }
                            object.layout.startMargin = n5;
                            return;
                        }
                        case 5: {
                            if (n4 == 5) {
                                object.layout.baselineToBaseline = n3;
                                object.layout.bottomToBottom = -1;
                                object.layout.bottomToTop = -1;
                                object.layout.topToTop = -1;
                                object.layout.topToBottom = -1;
                                return;
                            }
                            object = new StringBuilder();
                            ((StringBuilder)object).append("right to ");
                            ((StringBuilder)object).append(this.sideToString(n4));
                            ((StringBuilder)object).append(" undefined");
                            throw new IllegalArgumentException(((StringBuilder)object).toString());
                        }
                        case 4: {
                            if (n4 == 4) {
                                object.layout.bottomToBottom = n3;
                                object.layout.bottomToTop = -1;
                                object.layout.baselineToBaseline = -1;
                            } else {
                                if (n4 != 3) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("right to ");
                                    ((StringBuilder)object).append(this.sideToString(n4));
                                    ((StringBuilder)object).append(" undefined");
                                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                                }
                                object.layout.bottomToTop = n3;
                                object.layout.bottomToBottom = -1;
                                object.layout.baselineToBaseline = -1;
                            }
                            object.layout.bottomMargin = n5;
                            return;
                        }
                        case 3: {
                            if (n4 == 3) {
                                object.layout.topToTop = n3;
                                object.layout.topToBottom = -1;
                                object.layout.baselineToBaseline = -1;
                            } else {
                                if (n4 != 4) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("right to ");
                                    ((StringBuilder)object).append(this.sideToString(n4));
                                    ((StringBuilder)object).append(" undefined");
                                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                                }
                                object.layout.topToBottom = n3;
                                object.layout.topToTop = -1;
                                object.layout.baselineToBaseline = -1;
                            }
                            object.layout.topMargin = n5;
                            return;
                        }
                        case 2: {
                            if (n4 == 1) {
                                object.layout.rightToLeft = n3;
                                object.layout.rightToRight = -1;
                            } else {
                                if (n4 != 2) {
                                    object = new StringBuilder();
                                    ((StringBuilder)object).append("right to ");
                                    ((StringBuilder)object).append(this.sideToString(n4));
                                    ((StringBuilder)object).append(" undefined");
                                    throw new IllegalArgumentException(((StringBuilder)object).toString());
                                }
                                object.layout.rightToRight = n3;
                                object.layout.rightToLeft = -1;
                            }
                            object.layout.rightMargin = n5;
                            return;
                        }
                        case 1: 
                    }
                    if (n4 != 1) break block29;
                    object.layout.leftToLeft = n3;
                    object.layout.leftToRight = -1;
                    break block30;
                }
                if (n4 != 2) break block31;
                object.layout.leftToRight = n3;
                object.layout.leftToLeft = -1;
            }
            object.layout.leftMargin = n5;
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Left to ");
        ((StringBuilder)object).append(this.sideToString(n4));
        ((StringBuilder)object).append(" undefined");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public void constrainCircle(int n, int n2, int n3, float f) {
        Constraint constraint = this.get(n);
        constraint.layout.circleConstraint = n2;
        constraint.layout.circleRadius = n3;
        constraint.layout.circleAngle = f;
    }

    public void constrainDefaultHeight(int n, int n2) {
        this.get((int)n).layout.heightDefault = n2;
    }

    public void constrainDefaultWidth(int n, int n2) {
        this.get((int)n).layout.widthDefault = n2;
    }

    public void constrainHeight(int n, int n2) {
        this.get((int)n).layout.mHeight = n2;
    }

    public void constrainMaxHeight(int n, int n2) {
        this.get((int)n).layout.heightMax = n2;
    }

    public void constrainMaxWidth(int n, int n2) {
        this.get((int)n).layout.widthMax = n2;
    }

    public void constrainMinHeight(int n, int n2) {
        this.get((int)n).layout.heightMin = n2;
    }

    public void constrainMinWidth(int n, int n2) {
        this.get((int)n).layout.widthMin = n2;
    }

    public void constrainPercentHeight(int n, float f) {
        this.get((int)n).layout.heightPercent = f;
    }

    public void constrainPercentWidth(int n, float f) {
        this.get((int)n).layout.widthPercent = f;
    }

    public void constrainWidth(int n, int n2) {
        this.get((int)n).layout.mWidth = n2;
    }

    public void constrainedHeight(int n, boolean bl) {
        this.get((int)n).layout.constrainedHeight = bl;
    }

    public void constrainedWidth(int n, boolean bl) {
        this.get((int)n).layout.constrainedWidth = bl;
    }

    public void create(int n, int n2) {
        Constraint constraint = this.get(n);
        constraint.layout.mIsGuideline = true;
        constraint.layout.orientation = n2;
    }

    public void createBarrier(int n, int n2, int n3, int ... arrn) {
        Constraint constraint = this.get(n);
        constraint.layout.mHelperType = 1;
        constraint.layout.mBarrierDirection = n2;
        constraint.layout.mBarrierMargin = n3;
        constraint.layout.mIsGuideline = false;
        constraint.layout.mReferenceIds = arrn;
    }

    public void createHorizontalChain(int n, int n2, int n3, int n4, int[] arrn, float[] arrf, int n5) {
        this.createHorizontalChain(n, n2, n3, n4, arrn, arrf, n5, 1, 2);
    }

    public void createHorizontalChainRtl(int n, int n2, int n3, int n4, int[] arrn, float[] arrf, int n5) {
        this.createHorizontalChain(n, n2, n3, n4, arrn, arrf, n5, 6, 7);
    }

    public void createVerticalChain(int n, int n2, int n3, int n4, int[] arrn, float[] arrf, int n5) {
        if (arrn.length < 2) throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        if (arrf != null) {
            if (arrf.length != arrn.length) throw new IllegalArgumentException("must have 2 or more widgets in a chain");
        }
        if (arrf != null) {
            this.get((int)arrn[0]).layout.verticalWeight = arrf[0];
        }
        this.get((int)arrn[0]).layout.verticalChainStyle = n5;
        this.connect(arrn[0], 3, n, n2, 0);
        n = 1;
        do {
            if (n >= arrn.length) {
                this.connect(arrn[arrn.length - 1], 4, n3, n4, 0);
                return;
            }
            n2 = arrn[n];
            n2 = arrn[n];
            n5 = n - 1;
            this.connect(n2, 3, arrn[n5], 4, 0);
            this.connect(arrn[n5], 4, arrn[n], 3, 0);
            if (arrf != null) {
                this.get((int)arrn[n]).layout.verticalWeight = arrf[n];
            }
            ++n;
        } while (true);
    }

    public void dump(MotionScene motionScene, int ... object) {
        Serializable serializable;
        int n;
        Object object2 = this.mConstraints.keySet();
        int n2 = ((int[])object).length;
        int n3 = 0;
        if (n2 != 0) {
            serializable = new HashSet<Integer>();
            n = ((int[])object).length;
            n2 = 0;
            do {
                object2 = serializable;
                if (n2 < n) {
                    ((HashSet)serializable).add(object[n2]);
                    ++n2;
                    continue;
                }
                break;
            } while (true);
        } else {
            object2 = new HashSet<Integer>((Collection<Integer>)object2);
        }
        object = System.out;
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append(((HashSet)object2).size());
        ((StringBuilder)serializable).append(" constraints");
        ((PrintStream)object).println(((StringBuilder)serializable).toString());
        object = new StringBuilder();
        serializable = ((AbstractCollection)object2).toArray(new Integer[0]);
        n = ((Serializable)serializable).length;
        n2 = n3;
        do {
            if (n2 >= n) {
                System.out.println(((StringBuilder)object).toString());
                return;
            }
            object2 = serializable[n2];
            Constraint constraint = this.mConstraints.get(object2);
            ((StringBuilder)object).append("<Constraint id=");
            ((StringBuilder)object).append(object2);
            ((StringBuilder)object).append(" \n");
            constraint.layout.dump(motionScene, (StringBuilder)object);
            ((StringBuilder)object).append("/>\n");
            ++n2;
        } while (true);
    }

    public boolean getApplyElevation(int n) {
        return this.get((int)n).transform.applyElevation;
    }

    public Constraint getConstraint(int n) {
        if (!this.mConstraints.containsKey(n)) return null;
        return this.mConstraints.get(n);
    }

    public HashMap<String, ConstraintAttribute> getCustomAttributeSet() {
        return this.mSavedAttributes;
    }

    public int getHeight(int n) {
        return this.get((int)n).layout.mHeight;
    }

    public int[] getKnownIds() {
        Integer[] arrinteger = this.mConstraints.keySet();
        int n = 0;
        arrinteger = arrinteger.toArray(new Integer[0]);
        int n2 = arrinteger.length;
        int[] arrn = new int[n2];
        while (n < n2) {
            arrn[n] = arrinteger[n];
            ++n;
        }
        return arrn;
    }

    public Constraint getParameters(int n) {
        return this.get(n);
    }

    public int[] getReferencedIds(int n) {
        Constraint constraint = this.get(n);
        if (constraint.layout.mReferenceIds != null) return Arrays.copyOf(constraint.layout.mReferenceIds, constraint.layout.mReferenceIds.length);
        return new int[0];
    }

    public int getVisibility(int n) {
        return this.get((int)n).propertySet.visibility;
    }

    public int getVisibilityMode(int n) {
        return this.get((int)n).propertySet.mVisibilityMode;
    }

    public int getWidth(int n) {
        return this.get((int)n).layout.mWidth;
    }

    public boolean isForceId() {
        return this.mForceId;
    }

    public void load(Context context, int n) {
        XmlResourceParser xmlResourceParser = context.getResources().getXml(n);
        try {
            n = xmlResourceParser.getEventType();
            while (n != 1) {
                if (n != 0) {
                    if (n == 2) {
                        String string2 = xmlResourceParser.getName();
                        Constraint constraint = this.fillFromAttributeList(context, Xml.asAttributeSet((XmlPullParser)xmlResourceParser));
                        if (string2.equalsIgnoreCase("Guideline")) {
                            constraint.layout.mIsGuideline = true;
                        }
                        this.mConstraints.put(constraint.mViewId, constraint);
                    }
                } else {
                    xmlResourceParser.getName();
                }
                n = xmlResourceParser.next();
            }
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
            return;
        }
        catch (XmlPullParserException xmlPullParserException) {
            xmlPullParserException.printStackTrace();
        }
    }

    /*
     * Exception decompiling
     */
    public void load(Context var1_1, XmlPullParser var2_4) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 24[WHILELOOP]
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

    public void parseColorAttributes(Constraint constraint, String arrstring) {
        arrstring = arrstring.split(",");
        int n = 0;
        while (n < arrstring.length) {
            Object object = arrstring[n].split("=");
            if (((String[])object).length != 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append(" Unable to parse ");
                ((StringBuilder)object).append(arrstring[n]);
                Log.w((String)TAG, (String)((StringBuilder)object).toString());
            } else {
                constraint.setColorValue(object[0], Color.parseColor((String)object[1]));
            }
            ++n;
        }
    }

    public void parseFloatAttributes(Constraint constraint, String arrstring) {
        arrstring = arrstring.split(",");
        int n = 0;
        while (n < arrstring.length) {
            Object object = arrstring[n].split("=");
            if (((String[])object).length != 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append(" Unable to parse ");
                ((StringBuilder)object).append(arrstring[n]);
                Log.w((String)TAG, (String)((StringBuilder)object).toString());
            } else {
                constraint.setFloatValue(object[0], Float.parseFloat((String)object[1]));
            }
            ++n;
        }
    }

    public void parseIntAttributes(Constraint constraint, String arrstring) {
        arrstring = arrstring.split(",");
        int n = 0;
        while (n < arrstring.length) {
            Object object = arrstring[n].split("=");
            if (((String[])object).length != 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append(" Unable to parse ");
                ((StringBuilder)object).append(arrstring[n]);
                Log.w((String)TAG, (String)((StringBuilder)object).toString());
            } else {
                constraint.setFloatValue(object[0], Integer.decode((String)object[1]).intValue());
            }
            ++n;
        }
    }

    public void parseStringAttributes(Constraint constraint, String charSequence) {
        String[] arrstring = ConstraintSet.splitString((String)charSequence);
        int n = 0;
        while (n < arrstring.length) {
            String[] arrstring2 = arrstring[n].split("=");
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(" Unable to parse ");
            ((StringBuilder)charSequence).append(arrstring[n]);
            Log.w((String)TAG, (String)((StringBuilder)charSequence).toString());
            constraint.setStringValue(arrstring2[0], arrstring2[1]);
            ++n;
        }
    }

    public void readFallback(ConstraintLayout constraintLayout) {
        int n = constraintLayout.getChildCount();
        int n2 = 0;
        while (n2 < n) {
            View view = constraintLayout.getChildAt(n2);
            Object object = (ConstraintLayout.LayoutParams)view.getLayoutParams();
            int n3 = view.getId();
            if (this.mForceId) {
                if (n3 == -1) throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }
            if (!this.mConstraints.containsKey(n3)) {
                this.mConstraints.put(n3, new Constraint());
            }
            Constraint constraint = this.mConstraints.get(n3);
            if (!constraint.layout.mApply) {
                constraint.fillFrom(n3, (ConstraintLayout.LayoutParams)((Object)object));
                if (view instanceof ConstraintHelper) {
                    constraint.layout.mReferenceIds = ((ConstraintHelper)view).getReferencedIds();
                    if (view instanceof Barrier) {
                        object = (Barrier)view;
                        constraint.layout.mBarrierAllowsGoneWidgets = ((Barrier)((Object)object)).allowsGoneWidget();
                        constraint.layout.mBarrierDirection = ((Barrier)((Object)object)).getType();
                        constraint.layout.mBarrierMargin = ((Barrier)((Object)object)).getMargin();
                    }
                }
                constraint.layout.mApply = true;
            }
            if (!constraint.propertySet.mApply) {
                constraint.propertySet.visibility = view.getVisibility();
                constraint.propertySet.alpha = view.getAlpha();
                constraint.propertySet.mApply = true;
            }
            if (Build.VERSION.SDK_INT >= 17 && !constraint.transform.mApply) {
                constraint.transform.mApply = true;
                constraint.transform.rotation = view.getRotation();
                constraint.transform.rotationX = view.getRotationX();
                constraint.transform.rotationY = view.getRotationY();
                constraint.transform.scaleX = view.getScaleX();
                constraint.transform.scaleY = view.getScaleY();
                float f = view.getPivotX();
                float f2 = view.getPivotY();
                if ((double)f != 0.0 || (double)f2 != 0.0) {
                    constraint.transform.transformPivotX = f;
                    constraint.transform.transformPivotY = f2;
                }
                constraint.transform.translationX = view.getTranslationX();
                constraint.transform.translationY = view.getTranslationY();
                if (Build.VERSION.SDK_INT >= 21) {
                    constraint.transform.translationZ = view.getTranslationZ();
                    if (constraint.transform.applyElevation) {
                        constraint.transform.elevation = view.getElevation();
                    }
                }
            }
            ++n2;
        }
    }

    public void readFallback(ConstraintSet constraintSet) {
        Iterator<Integer> iterator2 = constraintSet.mConstraints.keySet().iterator();
        block0 : while (iterator2.hasNext()) {
            Object object = iterator2.next();
            int n = (Integer)object;
            Constraint constraint = constraintSet.mConstraints.get(object);
            if (!this.mConstraints.containsKey(n)) {
                this.mConstraints.put(n, new Constraint());
            }
            Constraint constraint2 = this.mConstraints.get(n);
            if (!constraint2.layout.mApply) {
                constraint2.layout.copyFrom(constraint.layout);
            }
            if (!constraint2.propertySet.mApply) {
                constraint2.propertySet.copyFrom(constraint.propertySet);
            }
            if (!constraint2.transform.mApply) {
                constraint2.transform.copyFrom(constraint.transform);
            }
            if (!constraint2.motion.mApply) {
                constraint2.motion.copyFrom(constraint.motion);
            }
            object = constraint.mCustomConstraints.keySet().iterator();
            do {
                if (!object.hasNext()) continue block0;
                String string2 = (String)object.next();
                if (constraint2.mCustomConstraints.containsKey(string2)) continue;
                constraint2.mCustomConstraints.put(string2, constraint.mCustomConstraints.get(string2));
            } while (true);
            break;
        }
        return;
    }

    public void removeAttribute(String string2) {
        this.mSavedAttributes.remove(string2);
    }

    public void removeFromHorizontalChain(int n) {
        if (!this.mConstraints.containsKey(n)) return;
        Constraint constraint = this.mConstraints.get(n);
        int n2 = constraint.layout.leftToRight;
        int n3 = constraint.layout.rightToLeft;
        if (n2 == -1 && n3 == -1) {
            int n4 = constraint.layout.startToEnd;
            n3 = constraint.layout.endToStart;
            if (n4 != -1 || n3 != -1) {
                if (n4 != -1 && n3 != -1) {
                    this.connect(n4, 7, n3, 6, 0);
                    this.connect(n3, 6, n2, 7, 0);
                } else if (n2 != -1 || n3 != -1) {
                    if (constraint.layout.rightToRight != -1) {
                        this.connect(n2, 7, constraint.layout.rightToRight, 7, 0);
                    } else if (constraint.layout.leftToLeft != -1) {
                        this.connect(n3, 6, constraint.layout.leftToLeft, 6, 0);
                    }
                }
            }
            this.clear(n, 6);
            this.clear(n, 7);
            return;
        }
        if (n2 != -1 && n3 != -1) {
            this.connect(n2, 2, n3, 1, 0);
            this.connect(n3, 1, n2, 2, 0);
        } else if (n2 != -1 || n3 != -1) {
            if (constraint.layout.rightToRight != -1) {
                this.connect(n2, 2, constraint.layout.rightToRight, 2, 0);
            } else if (constraint.layout.leftToLeft != -1) {
                this.connect(n3, 1, constraint.layout.leftToLeft, 1, 0);
            }
        }
        this.clear(n, 1);
        this.clear(n, 2);
    }

    public void removeFromVerticalChain(int n) {
        if (this.mConstraints.containsKey(n)) {
            Constraint constraint = this.mConstraints.get(n);
            int n2 = constraint.layout.topToBottom;
            int n3 = constraint.layout.bottomToTop;
            if (n2 != -1 || n3 != -1) {
                if (n2 != -1 && n3 != -1) {
                    this.connect(n2, 4, n3, 3, 0);
                    this.connect(n3, 3, n2, 4, 0);
                } else if (n2 != -1 || n3 != -1) {
                    if (constraint.layout.bottomToBottom != -1) {
                        this.connect(n2, 4, constraint.layout.bottomToBottom, 4, 0);
                    } else if (constraint.layout.topToTop != -1) {
                        this.connect(n3, 3, constraint.layout.topToTop, 3, 0);
                    }
                }
            }
        }
        this.clear(n, 3);
        this.clear(n, 4);
    }

    public void setAlpha(int n, float f) {
        this.get((int)n).propertySet.alpha = f;
    }

    public void setApplyElevation(int n, boolean bl) {
        if (Build.VERSION.SDK_INT < 21) return;
        this.get((int)n).transform.applyElevation = bl;
    }

    public void setBarrierType(int n, int n2) {
        this.get((int)n).layout.mHelperType = n2;
    }

    public void setColorValue(int n, String string2, int n2) {
        this.get(n).setColorValue(string2, n2);
    }

    public void setDimensionRatio(int n, String string2) {
        this.get((int)n).layout.dimensionRatio = string2;
    }

    public void setEditorAbsoluteX(int n, int n2) {
        this.get((int)n).layout.editorAbsoluteX = n2;
    }

    public void setEditorAbsoluteY(int n, int n2) {
        this.get((int)n).layout.editorAbsoluteY = n2;
    }

    public void setElevation(int n, float f) {
        if (Build.VERSION.SDK_INT < 21) return;
        this.get((int)n).transform.elevation = f;
        this.get((int)n).transform.applyElevation = true;
    }

    public void setFloatValue(int n, String string2, float f) {
        this.get(n).setFloatValue(string2, f);
    }

    public void setForceId(boolean bl) {
        this.mForceId = bl;
    }

    public void setGoneMargin(int n, int n2, int n3) {
        Constraint constraint = this.get(n);
        switch (n2) {
            default: {
                throw new IllegalArgumentException("unknown constraint");
            }
            case 7: {
                constraint.layout.goneEndMargin = n3;
                return;
            }
            case 6: {
                constraint.layout.goneStartMargin = n3;
                return;
            }
            case 5: {
                throw new IllegalArgumentException("baseline does not support margins");
            }
            case 4: {
                constraint.layout.goneBottomMargin = n3;
                return;
            }
            case 3: {
                constraint.layout.goneTopMargin = n3;
                return;
            }
            case 2: {
                constraint.layout.goneRightMargin = n3;
                return;
            }
            case 1: 
        }
        constraint.layout.goneLeftMargin = n3;
    }

    public void setGuidelineBegin(int n, int n2) {
        this.get((int)n).layout.guideBegin = n2;
        this.get((int)n).layout.guideEnd = -1;
        this.get((int)n).layout.guidePercent = -1.0f;
    }

    public void setGuidelineEnd(int n, int n2) {
        this.get((int)n).layout.guideEnd = n2;
        this.get((int)n).layout.guideBegin = -1;
        this.get((int)n).layout.guidePercent = -1.0f;
    }

    public void setGuidelinePercent(int n, float f) {
        this.get((int)n).layout.guidePercent = f;
        this.get((int)n).layout.guideEnd = -1;
        this.get((int)n).layout.guideBegin = -1;
    }

    public void setHorizontalBias(int n, float f) {
        this.get((int)n).layout.horizontalBias = f;
    }

    public void setHorizontalChainStyle(int n, int n2) {
        this.get((int)n).layout.horizontalChainStyle = n2;
    }

    public void setHorizontalWeight(int n, float f) {
        this.get((int)n).layout.horizontalWeight = f;
    }

    public void setIntValue(int n, String string2, int n2) {
        this.get(n).setIntValue(string2, n2);
    }

    public void setMargin(int n, int n2, int n3) {
        Constraint constraint = this.get(n);
        switch (n2) {
            default: {
                throw new IllegalArgumentException("unknown constraint");
            }
            case 7: {
                constraint.layout.endMargin = n3;
                return;
            }
            case 6: {
                constraint.layout.startMargin = n3;
                return;
            }
            case 5: {
                throw new IllegalArgumentException("baseline does not support margins");
            }
            case 4: {
                constraint.layout.bottomMargin = n3;
                return;
            }
            case 3: {
                constraint.layout.topMargin = n3;
                return;
            }
            case 2: {
                constraint.layout.rightMargin = n3;
                return;
            }
            case 1: 
        }
        constraint.layout.leftMargin = n3;
    }

    public void setReferencedIds(int n, int ... arrn) {
        this.get((int)n).layout.mReferenceIds = arrn;
    }

    public void setRotation(int n, float f) {
        this.get((int)n).transform.rotation = f;
    }

    public void setRotationX(int n, float f) {
        this.get((int)n).transform.rotationX = f;
    }

    public void setRotationY(int n, float f) {
        this.get((int)n).transform.rotationY = f;
    }

    public void setScaleX(int n, float f) {
        this.get((int)n).transform.scaleX = f;
    }

    public void setScaleY(int n, float f) {
        this.get((int)n).transform.scaleY = f;
    }

    public void setStringValue(int n, String string2, String string3) {
        this.get(n).setStringValue(string2, string3);
    }

    public void setTransformPivot(int n, float f, float f2) {
        Constraint constraint = this.get(n);
        constraint.transform.transformPivotY = f2;
        constraint.transform.transformPivotX = f;
    }

    public void setTransformPivotX(int n, float f) {
        this.get((int)n).transform.transformPivotX = f;
    }

    public void setTransformPivotY(int n, float f) {
        this.get((int)n).transform.transformPivotY = f;
    }

    public void setTranslation(int n, float f, float f2) {
        Constraint constraint = this.get(n);
        constraint.transform.translationX = f;
        constraint.transform.translationY = f2;
    }

    public void setTranslationX(int n, float f) {
        this.get((int)n).transform.translationX = f;
    }

    public void setTranslationY(int n, float f) {
        this.get((int)n).transform.translationY = f;
    }

    public void setTranslationZ(int n, float f) {
        if (Build.VERSION.SDK_INT < 21) return;
        this.get((int)n).transform.translationZ = f;
    }

    public void setValidateOnParse(boolean bl) {
        this.mValidate = bl;
    }

    public void setVerticalBias(int n, float f) {
        this.get((int)n).layout.verticalBias = f;
    }

    public void setVerticalChainStyle(int n, int n2) {
        this.get((int)n).layout.verticalChainStyle = n2;
    }

    public void setVerticalWeight(int n, float f) {
        this.get((int)n).layout.verticalWeight = f;
    }

    public void setVisibility(int n, int n2) {
        this.get((int)n).propertySet.visibility = n2;
    }

    public void setVisibilityMode(int n, int n2) {
        this.get((int)n).propertySet.mVisibilityMode = n2;
    }

    public static class Constraint {
        public final Layout layout = new Layout();
        public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap();
        int mViewId;
        public final Motion motion = new Motion();
        public final PropertySet propertySet = new PropertySet();
        public final Transform transform = new Transform();

        private void fillFrom(int n, ConstraintLayout.LayoutParams layoutParams) {
            this.mViewId = n;
            this.layout.leftToLeft = layoutParams.leftToLeft;
            this.layout.leftToRight = layoutParams.leftToRight;
            this.layout.rightToLeft = layoutParams.rightToLeft;
            this.layout.rightToRight = layoutParams.rightToRight;
            this.layout.topToTop = layoutParams.topToTop;
            this.layout.topToBottom = layoutParams.topToBottom;
            this.layout.bottomToTop = layoutParams.bottomToTop;
            this.layout.bottomToBottom = layoutParams.bottomToBottom;
            this.layout.baselineToBaseline = layoutParams.baselineToBaseline;
            this.layout.startToEnd = layoutParams.startToEnd;
            this.layout.startToStart = layoutParams.startToStart;
            this.layout.endToStart = layoutParams.endToStart;
            this.layout.endToEnd = layoutParams.endToEnd;
            this.layout.horizontalBias = layoutParams.horizontalBias;
            this.layout.verticalBias = layoutParams.verticalBias;
            this.layout.dimensionRatio = layoutParams.dimensionRatio;
            this.layout.circleConstraint = layoutParams.circleConstraint;
            this.layout.circleRadius = layoutParams.circleRadius;
            this.layout.circleAngle = layoutParams.circleAngle;
            this.layout.editorAbsoluteX = layoutParams.editorAbsoluteX;
            this.layout.editorAbsoluteY = layoutParams.editorAbsoluteY;
            this.layout.orientation = layoutParams.orientation;
            this.layout.guidePercent = layoutParams.guidePercent;
            this.layout.guideBegin = layoutParams.guideBegin;
            this.layout.guideEnd = layoutParams.guideEnd;
            this.layout.mWidth = layoutParams.width;
            this.layout.mHeight = layoutParams.height;
            this.layout.leftMargin = layoutParams.leftMargin;
            this.layout.rightMargin = layoutParams.rightMargin;
            this.layout.topMargin = layoutParams.topMargin;
            this.layout.bottomMargin = layoutParams.bottomMargin;
            this.layout.verticalWeight = layoutParams.verticalWeight;
            this.layout.horizontalWeight = layoutParams.horizontalWeight;
            this.layout.verticalChainStyle = layoutParams.verticalChainStyle;
            this.layout.horizontalChainStyle = layoutParams.horizontalChainStyle;
            this.layout.constrainedWidth = layoutParams.constrainedWidth;
            this.layout.constrainedHeight = layoutParams.constrainedHeight;
            this.layout.widthDefault = layoutParams.matchConstraintDefaultWidth;
            this.layout.heightDefault = layoutParams.matchConstraintDefaultHeight;
            this.layout.widthMax = layoutParams.matchConstraintMaxWidth;
            this.layout.heightMax = layoutParams.matchConstraintMaxHeight;
            this.layout.widthMin = layoutParams.matchConstraintMinWidth;
            this.layout.heightMin = layoutParams.matchConstraintMinHeight;
            this.layout.widthPercent = layoutParams.matchConstraintPercentWidth;
            this.layout.heightPercent = layoutParams.matchConstraintPercentHeight;
            this.layout.mConstraintTag = layoutParams.constraintTag;
            this.layout.goneTopMargin = layoutParams.goneTopMargin;
            this.layout.goneBottomMargin = layoutParams.goneBottomMargin;
            this.layout.goneLeftMargin = layoutParams.goneLeftMargin;
            this.layout.goneRightMargin = layoutParams.goneRightMargin;
            this.layout.goneStartMargin = layoutParams.goneStartMargin;
            this.layout.goneEndMargin = layoutParams.goneEndMargin;
            if (Build.VERSION.SDK_INT < 17) return;
            this.layout.endMargin = layoutParams.getMarginEnd();
            this.layout.startMargin = layoutParams.getMarginStart();
        }

        private void fillFromConstraints(int n, Constraints.LayoutParams layoutParams) {
            this.fillFrom(n, layoutParams);
            this.propertySet.alpha = layoutParams.alpha;
            this.transform.rotation = layoutParams.rotation;
            this.transform.rotationX = layoutParams.rotationX;
            this.transform.rotationY = layoutParams.rotationY;
            this.transform.scaleX = layoutParams.scaleX;
            this.transform.scaleY = layoutParams.scaleY;
            this.transform.transformPivotX = layoutParams.transformPivotX;
            this.transform.transformPivotY = layoutParams.transformPivotY;
            this.transform.translationX = layoutParams.translationX;
            this.transform.translationY = layoutParams.translationY;
            this.transform.translationZ = layoutParams.translationZ;
            this.transform.elevation = layoutParams.elevation;
            this.transform.applyElevation = layoutParams.applyElevation;
        }

        private void fillFromConstraints(ConstraintHelper constraintHelper, int n, Constraints.LayoutParams layoutParams) {
            this.fillFromConstraints(n, layoutParams);
            if (!(constraintHelper instanceof Barrier)) return;
            this.layout.mHelperType = 1;
            constraintHelper = (Barrier)constraintHelper;
            this.layout.mBarrierDirection = ((Barrier)constraintHelper).getType();
            this.layout.mReferenceIds = constraintHelper.getReferencedIds();
            this.layout.mBarrierMargin = ((Barrier)constraintHelper).getMargin();
        }

        private ConstraintAttribute get(String object, ConstraintAttribute.AttributeType object2) {
            if (!this.mCustomConstraints.containsKey(object)) {
                object2 = new ConstraintAttribute((String)object, (ConstraintAttribute.AttributeType)((Object)object2));
                this.mCustomConstraints.put((String)object, (ConstraintAttribute)object2);
                return object2;
            }
            if (((ConstraintAttribute)(object = this.mCustomConstraints.get(object))).getType() == object2) {
                return object;
            }
            object2 = new StringBuilder();
            ((StringBuilder)object2).append("ConstraintAttribute is already a ");
            ((StringBuilder)object2).append(((ConstraintAttribute)object).getType().name());
            throw new IllegalArgumentException(((StringBuilder)object2).toString());
        }

        private void setColorValue(String string2, int n) {
            this.get(string2, ConstraintAttribute.AttributeType.COLOR_TYPE).setColorValue(n);
        }

        private void setFloatValue(String string2, float f) {
            this.get(string2, ConstraintAttribute.AttributeType.FLOAT_TYPE).setFloatValue(f);
        }

        private void setIntValue(String string2, int n) {
            this.get(string2, ConstraintAttribute.AttributeType.INT_TYPE).setIntValue(n);
        }

        private void setStringValue(String string2, String string3) {
            this.get(string2, ConstraintAttribute.AttributeType.STRING_TYPE).setStringValue(string3);
        }

        public void applyTo(ConstraintLayout.LayoutParams layoutParams) {
            layoutParams.leftToLeft = this.layout.leftToLeft;
            layoutParams.leftToRight = this.layout.leftToRight;
            layoutParams.rightToLeft = this.layout.rightToLeft;
            layoutParams.rightToRight = this.layout.rightToRight;
            layoutParams.topToTop = this.layout.topToTop;
            layoutParams.topToBottom = this.layout.topToBottom;
            layoutParams.bottomToTop = this.layout.bottomToTop;
            layoutParams.bottomToBottom = this.layout.bottomToBottom;
            layoutParams.baselineToBaseline = this.layout.baselineToBaseline;
            layoutParams.startToEnd = this.layout.startToEnd;
            layoutParams.startToStart = this.layout.startToStart;
            layoutParams.endToStart = this.layout.endToStart;
            layoutParams.endToEnd = this.layout.endToEnd;
            layoutParams.leftMargin = this.layout.leftMargin;
            layoutParams.rightMargin = this.layout.rightMargin;
            layoutParams.topMargin = this.layout.topMargin;
            layoutParams.bottomMargin = this.layout.bottomMargin;
            layoutParams.goneStartMargin = this.layout.goneStartMargin;
            layoutParams.goneEndMargin = this.layout.goneEndMargin;
            layoutParams.goneTopMargin = this.layout.goneTopMargin;
            layoutParams.goneBottomMargin = this.layout.goneBottomMargin;
            layoutParams.horizontalBias = this.layout.horizontalBias;
            layoutParams.verticalBias = this.layout.verticalBias;
            layoutParams.circleConstraint = this.layout.circleConstraint;
            layoutParams.circleRadius = this.layout.circleRadius;
            layoutParams.circleAngle = this.layout.circleAngle;
            layoutParams.dimensionRatio = this.layout.dimensionRatio;
            layoutParams.editorAbsoluteX = this.layout.editorAbsoluteX;
            layoutParams.editorAbsoluteY = this.layout.editorAbsoluteY;
            layoutParams.verticalWeight = this.layout.verticalWeight;
            layoutParams.horizontalWeight = this.layout.horizontalWeight;
            layoutParams.verticalChainStyle = this.layout.verticalChainStyle;
            layoutParams.horizontalChainStyle = this.layout.horizontalChainStyle;
            layoutParams.constrainedWidth = this.layout.constrainedWidth;
            layoutParams.constrainedHeight = this.layout.constrainedHeight;
            layoutParams.matchConstraintDefaultWidth = this.layout.widthDefault;
            layoutParams.matchConstraintDefaultHeight = this.layout.heightDefault;
            layoutParams.matchConstraintMaxWidth = this.layout.widthMax;
            layoutParams.matchConstraintMaxHeight = this.layout.heightMax;
            layoutParams.matchConstraintMinWidth = this.layout.widthMin;
            layoutParams.matchConstraintMinHeight = this.layout.heightMin;
            layoutParams.matchConstraintPercentWidth = this.layout.widthPercent;
            layoutParams.matchConstraintPercentHeight = this.layout.heightPercent;
            layoutParams.orientation = this.layout.orientation;
            layoutParams.guidePercent = this.layout.guidePercent;
            layoutParams.guideBegin = this.layout.guideBegin;
            layoutParams.guideEnd = this.layout.guideEnd;
            layoutParams.width = this.layout.mWidth;
            layoutParams.height = this.layout.mHeight;
            if (this.layout.mConstraintTag != null) {
                layoutParams.constraintTag = this.layout.mConstraintTag;
            }
            if (Build.VERSION.SDK_INT >= 17) {
                layoutParams.setMarginStart(this.layout.startMargin);
                layoutParams.setMarginEnd(this.layout.endMargin);
            }
            layoutParams.validate();
        }

        public Constraint clone() {
            Constraint constraint = new Constraint();
            constraint.layout.copyFrom(this.layout);
            constraint.motion.copyFrom(this.motion);
            constraint.propertySet.copyFrom(this.propertySet);
            constraint.transform.copyFrom(this.transform);
            constraint.mViewId = this.mViewId;
            return constraint;
        }
    }

    public static class Layout {
        private static final int BARRIER_ALLOWS_GONE_WIDGETS = 75;
        private static final int BARRIER_DIRECTION = 72;
        private static final int BARRIER_MARGIN = 73;
        private static final int BASELINE_TO_BASELINE = 1;
        private static final int BOTTOM_MARGIN = 2;
        private static final int BOTTOM_TO_BOTTOM = 3;
        private static final int BOTTOM_TO_TOP = 4;
        private static final int CHAIN_USE_RTL = 71;
        private static final int CIRCLE = 61;
        private static final int CIRCLE_ANGLE = 63;
        private static final int CIRCLE_RADIUS = 62;
        private static final int CONSTRAINT_REFERENCED_IDS = 74;
        private static final int DIMENSION_RATIO = 5;
        private static final int EDITOR_ABSOLUTE_X = 6;
        private static final int EDITOR_ABSOLUTE_Y = 7;
        private static final int END_MARGIN = 8;
        private static final int END_TO_END = 9;
        private static final int END_TO_START = 10;
        private static final int GONE_BOTTOM_MARGIN = 11;
        private static final int GONE_END_MARGIN = 12;
        private static final int GONE_LEFT_MARGIN = 13;
        private static final int GONE_RIGHT_MARGIN = 14;
        private static final int GONE_START_MARGIN = 15;
        private static final int GONE_TOP_MARGIN = 16;
        private static final int GUIDE_BEGIN = 17;
        private static final int GUIDE_END = 18;
        private static final int GUIDE_PERCENT = 19;
        private static final int HEIGHT_PERCENT = 70;
        private static final int HORIZONTAL_BIAS = 20;
        private static final int HORIZONTAL_STYLE = 39;
        private static final int HORIZONTAL_WEIGHT = 37;
        private static final int LAYOUT_HEIGHT = 21;
        private static final int LAYOUT_WIDTH = 22;
        private static final int LEFT_MARGIN = 23;
        private static final int LEFT_TO_LEFT = 24;
        private static final int LEFT_TO_RIGHT = 25;
        private static final int ORIENTATION = 26;
        private static final int RIGHT_MARGIN = 27;
        private static final int RIGHT_TO_LEFT = 28;
        private static final int RIGHT_TO_RIGHT = 29;
        private static final int START_MARGIN = 30;
        private static final int START_TO_END = 31;
        private static final int START_TO_START = 32;
        private static final int TOP_MARGIN = 33;
        private static final int TOP_TO_BOTTOM = 34;
        private static final int TOP_TO_TOP = 35;
        public static final int UNSET = -1;
        private static final int UNUSED = 76;
        private static final int VERTICAL_BIAS = 36;
        private static final int VERTICAL_STYLE = 40;
        private static final int VERTICAL_WEIGHT = 38;
        private static final int WIDTH_PERCENT = 69;
        private static SparseIntArray mapToConstant;
        public int baselineToBaseline = -1;
        public int bottomMargin = -1;
        public int bottomToBottom = -1;
        public int bottomToTop = -1;
        public float circleAngle = 0.0f;
        public int circleConstraint = -1;
        public int circleRadius = 0;
        public boolean constrainedHeight = false;
        public boolean constrainedWidth = false;
        public String dimensionRatio = null;
        public int editorAbsoluteX = -1;
        public int editorAbsoluteY = -1;
        public int endMargin = -1;
        public int endToEnd = -1;
        public int endToStart = -1;
        public int goneBottomMargin = -1;
        public int goneEndMargin = -1;
        public int goneLeftMargin = -1;
        public int goneRightMargin = -1;
        public int goneStartMargin = -1;
        public int goneTopMargin = -1;
        public int guideBegin = -1;
        public int guideEnd = -1;
        public float guidePercent = -1.0f;
        public int heightDefault = 0;
        public int heightMax = -1;
        public int heightMin = -1;
        public float heightPercent = 1.0f;
        public float horizontalBias = 0.5f;
        public int horizontalChainStyle = 0;
        public float horizontalWeight = -1.0f;
        public int leftMargin = -1;
        public int leftToLeft = -1;
        public int leftToRight = -1;
        public boolean mApply = false;
        public boolean mBarrierAllowsGoneWidgets = true;
        public int mBarrierDirection = -1;
        public int mBarrierMargin = 0;
        public String mConstraintTag;
        public int mHeight;
        public int mHelperType = -1;
        public boolean mIsGuideline = false;
        public String mReferenceIdString;
        public int[] mReferenceIds;
        public int mWidth;
        public int orientation = -1;
        public int rightMargin = -1;
        public int rightToLeft = -1;
        public int rightToRight = -1;
        public int startMargin = -1;
        public int startToEnd = -1;
        public int startToStart = -1;
        public int topMargin = -1;
        public int topToBottom = -1;
        public int topToTop = -1;
        public float verticalBias = 0.5f;
        public int verticalChainStyle = 0;
        public float verticalWeight = -1.0f;
        public int widthDefault = 0;
        public int widthMax = -1;
        public int widthMin = -1;
        public float widthPercent = 1.0f;

        static {
            SparseIntArray sparseIntArray;
            mapToConstant = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(R.styleable.Layout_layout_constraintLeft_toLeftOf, 24);
            mapToConstant.append(R.styleable.Layout_layout_constraintLeft_toRightOf, 25);
            mapToConstant.append(R.styleable.Layout_layout_constraintRight_toLeftOf, 28);
            mapToConstant.append(R.styleable.Layout_layout_constraintRight_toRightOf, 29);
            mapToConstant.append(R.styleable.Layout_layout_constraintTop_toTopOf, 35);
            mapToConstant.append(R.styleable.Layout_layout_constraintTop_toBottomOf, 34);
            mapToConstant.append(R.styleable.Layout_layout_constraintBottom_toTopOf, 4);
            mapToConstant.append(R.styleable.Layout_layout_constraintBottom_toBottomOf, 3);
            mapToConstant.append(R.styleable.Layout_layout_constraintBaseline_toBaselineOf, 1);
            mapToConstant.append(R.styleable.Layout_layout_editor_absoluteX, 6);
            mapToConstant.append(R.styleable.Layout_layout_editor_absoluteY, 7);
            mapToConstant.append(R.styleable.Layout_layout_constraintGuide_begin, 17);
            mapToConstant.append(R.styleable.Layout_layout_constraintGuide_end, 18);
            mapToConstant.append(R.styleable.Layout_layout_constraintGuide_percent, 19);
            mapToConstant.append(R.styleable.Layout_android_orientation, 26);
            mapToConstant.append(R.styleable.Layout_layout_constraintStart_toEndOf, 31);
            mapToConstant.append(R.styleable.Layout_layout_constraintStart_toStartOf, 32);
            mapToConstant.append(R.styleable.Layout_layout_constraintEnd_toStartOf, 10);
            mapToConstant.append(R.styleable.Layout_layout_constraintEnd_toEndOf, 9);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginLeft, 13);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginTop, 16);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginRight, 14);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginBottom, 11);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginStart, 15);
            mapToConstant.append(R.styleable.Layout_layout_goneMarginEnd, 12);
            mapToConstant.append(R.styleable.Layout_layout_constraintVertical_weight, 38);
            mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_weight, 37);
            mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_chainStyle, 39);
            mapToConstant.append(R.styleable.Layout_layout_constraintVertical_chainStyle, 40);
            mapToConstant.append(R.styleable.Layout_layout_constraintHorizontal_bias, 20);
            mapToConstant.append(R.styleable.Layout_layout_constraintVertical_bias, 36);
            mapToConstant.append(R.styleable.Layout_layout_constraintDimensionRatio, 5);
            mapToConstant.append(R.styleable.Layout_layout_constraintLeft_creator, 76);
            mapToConstant.append(R.styleable.Layout_layout_constraintTop_creator, 76);
            mapToConstant.append(R.styleable.Layout_layout_constraintRight_creator, 76);
            mapToConstant.append(R.styleable.Layout_layout_constraintBottom_creator, 76);
            mapToConstant.append(R.styleable.Layout_layout_constraintBaseline_creator, 76);
            mapToConstant.append(R.styleable.Layout_android_layout_marginLeft, 23);
            mapToConstant.append(R.styleable.Layout_android_layout_marginRight, 27);
            mapToConstant.append(R.styleable.Layout_android_layout_marginStart, 30);
            mapToConstant.append(R.styleable.Layout_android_layout_marginEnd, 8);
            mapToConstant.append(R.styleable.Layout_android_layout_marginTop, 33);
            mapToConstant.append(R.styleable.Layout_android_layout_marginBottom, 2);
            mapToConstant.append(R.styleable.Layout_android_layout_width, 22);
            mapToConstant.append(R.styleable.Layout_android_layout_height, 21);
            mapToConstant.append(R.styleable.Layout_layout_constraintCircle, 61);
            mapToConstant.append(R.styleable.Layout_layout_constraintCircleRadius, 62);
            mapToConstant.append(R.styleable.Layout_layout_constraintCircleAngle, 63);
            mapToConstant.append(R.styleable.Layout_layout_constraintWidth_percent, 69);
            mapToConstant.append(R.styleable.Layout_layout_constraintHeight_percent, 70);
            mapToConstant.append(R.styleable.Layout_chainUseRtl, 71);
            mapToConstant.append(R.styleable.Layout_barrierDirection, 72);
            mapToConstant.append(R.styleable.Layout_barrierMargin, 73);
            mapToConstant.append(R.styleable.Layout_constraint_referenced_ids, 74);
            mapToConstant.append(R.styleable.Layout_barrierAllowsGoneWidgets, 75);
        }

        public void copyFrom(Layout layout2) {
            this.mIsGuideline = layout2.mIsGuideline;
            this.mWidth = layout2.mWidth;
            this.mApply = layout2.mApply;
            this.mHeight = layout2.mHeight;
            this.guideBegin = layout2.guideBegin;
            this.guideEnd = layout2.guideEnd;
            this.guidePercent = layout2.guidePercent;
            this.leftToLeft = layout2.leftToLeft;
            this.leftToRight = layout2.leftToRight;
            this.rightToLeft = layout2.rightToLeft;
            this.rightToRight = layout2.rightToRight;
            this.topToTop = layout2.topToTop;
            this.topToBottom = layout2.topToBottom;
            this.bottomToTop = layout2.bottomToTop;
            this.bottomToBottom = layout2.bottomToBottom;
            this.baselineToBaseline = layout2.baselineToBaseline;
            this.startToEnd = layout2.startToEnd;
            this.startToStart = layout2.startToStart;
            this.endToStart = layout2.endToStart;
            this.endToEnd = layout2.endToEnd;
            this.horizontalBias = layout2.horizontalBias;
            this.verticalBias = layout2.verticalBias;
            this.dimensionRatio = layout2.dimensionRatio;
            this.circleConstraint = layout2.circleConstraint;
            this.circleRadius = layout2.circleRadius;
            this.circleAngle = layout2.circleAngle;
            this.editorAbsoluteX = layout2.editorAbsoluteX;
            this.editorAbsoluteY = layout2.editorAbsoluteY;
            this.orientation = layout2.orientation;
            this.leftMargin = layout2.leftMargin;
            this.rightMargin = layout2.rightMargin;
            this.topMargin = layout2.topMargin;
            this.bottomMargin = layout2.bottomMargin;
            this.endMargin = layout2.endMargin;
            this.startMargin = layout2.startMargin;
            this.goneLeftMargin = layout2.goneLeftMargin;
            this.goneTopMargin = layout2.goneTopMargin;
            this.goneRightMargin = layout2.goneRightMargin;
            this.goneBottomMargin = layout2.goneBottomMargin;
            this.goneEndMargin = layout2.goneEndMargin;
            this.goneStartMargin = layout2.goneStartMargin;
            this.verticalWeight = layout2.verticalWeight;
            this.horizontalWeight = layout2.horizontalWeight;
            this.horizontalChainStyle = layout2.horizontalChainStyle;
            this.verticalChainStyle = layout2.verticalChainStyle;
            this.widthDefault = layout2.widthDefault;
            this.heightDefault = layout2.heightDefault;
            this.widthMax = layout2.widthMax;
            this.heightMax = layout2.heightMax;
            this.widthMin = layout2.widthMin;
            this.heightMin = layout2.heightMin;
            this.widthPercent = layout2.widthPercent;
            this.heightPercent = layout2.heightPercent;
            this.mBarrierDirection = layout2.mBarrierDirection;
            this.mBarrierMargin = layout2.mBarrierMargin;
            this.mHelperType = layout2.mHelperType;
            this.mConstraintTag = layout2.mConstraintTag;
            int[] arrn = layout2.mReferenceIds;
            this.mReferenceIds = arrn != null ? Arrays.copyOf(arrn, arrn.length) : null;
            this.mReferenceIdString = layout2.mReferenceIdString;
            this.constrainedWidth = layout2.constrainedWidth;
            this.constrainedHeight = layout2.constrainedHeight;
            this.mBarrierAllowsGoneWidgets = layout2.mBarrierAllowsGoneWidgets;
        }

        public void dump(MotionScene motionScene, StringBuilder stringBuilder) {
            Field[] arrfield = this.getClass().getDeclaredFields();
            stringBuilder.append("\n");
            int n = 0;
            while (n < arrfield.length) {
                Object object = arrfield[n];
                String string2 = ((Field)object).getName();
                if (!Modifier.isStatic(((Field)object).getModifiers())) {
                    try {
                        Object object2 = ((Field)object).get(this);
                        Class<?> class_ = ((Field)object).getType();
                        object = Integer.TYPE;
                        if (class_ == object) {
                            object = (Integer)object2;
                            if ((Integer)object != -1) {
                                object2 = motionScene.lookUpConstraintName((Integer)object);
                                stringBuilder.append("    ");
                                stringBuilder.append(string2);
                                stringBuilder.append(" = \"");
                                if (object2 == null) {
                                    object2 = object;
                                }
                                stringBuilder.append(object2);
                                stringBuilder.append("\"\n");
                            }
                        } else if (class_ == Float.TYPE && ((Float)(object2 = (Float)object2)).floatValue() != -1.0f) {
                            stringBuilder.append("    ");
                            stringBuilder.append(string2);
                            stringBuilder.append(" = \"");
                            stringBuilder.append(object2);
                            stringBuilder.append("\"\n");
                        }
                    }
                    catch (IllegalAccessException illegalAccessException) {
                        illegalAccessException.printStackTrace();
                    }
                }
                ++n;
            }
        }

        /*
         * Unable to fully structure code
         */
        void fillFromAttributeList(Context var1_1, AttributeSet var2_2) {
            var1_1 = var1_1.obtainStyledAttributes((AttributeSet)var2_2, R.styleable.Layout);
            this.mApply = true;
            var3_3 = var1_1.getIndexCount();
            var4_4 = 0;
            do {
                if (var4_4 >= var3_3) {
                    var1_1.recycle();
                    return;
                }
                var5_5 = var1_1.getIndex(var4_4);
                var6_6 = Layout.mapToConstant.get(var5_5);
                if (var6_6 != 80) {
                    if (var6_6 != 81) {
                        switch (var6_6) {
                            default: {
                                switch (var6_6) {
                                    default: {
                                        switch (var6_6) {
                                            default: {
                                                switch (var6_6) {
                                                    default: {
                                                        var2_2 = new StringBuilder();
                                                        var2_2.append("Unknown attribute 0x");
                                                        var2_2.append(Integer.toHexString(var5_5));
                                                        var2_2.append("   ");
                                                        var2_2.append(Layout.mapToConstant.get(var5_5));
                                                        Log.w((String)"ConstraintSet", (String)var2_2.toString());
                                                        ** break;
                                                    }
                                                    case 77: {
                                                        this.mConstraintTag = var1_1.getString(var5_5);
                                                        ** break;
                                                    }
                                                    case 76: {
                                                        var2_2 = new StringBuilder();
                                                        var2_2.append("unused attribute 0x");
                                                        var2_2.append(Integer.toHexString(var5_5));
                                                        var2_2.append("   ");
                                                        var2_2.append(Layout.mapToConstant.get(var5_5));
                                                        Log.w((String)"ConstraintSet", (String)var2_2.toString());
                                                        ** break;
                                                    }
                                                    case 75: {
                                                        this.mBarrierAllowsGoneWidgets = var1_1.getBoolean(var5_5, this.mBarrierAllowsGoneWidgets);
                                                        ** break;
                                                    }
                                                    case 74: {
                                                        this.mReferenceIdString = var1_1.getString(var5_5);
                                                        ** break;
                                                    }
                                                    case 73: {
                                                        this.mBarrierMargin = var1_1.getDimensionPixelSize(var5_5, this.mBarrierMargin);
                                                        ** break;
                                                    }
                                                    case 72: {
                                                        this.mBarrierDirection = var1_1.getInt(var5_5, this.mBarrierDirection);
                                                        ** break;
                                                    }
                                                    case 71: {
                                                        Log.e((String)"ConstraintSet", (String)"CURRENTLY UNSUPPORTED");
                                                        ** break;
                                                    }
                                                    case 70: {
                                                        this.heightPercent = var1_1.getFloat(var5_5, 1.0f);
                                                        ** break;
                                                    }
                                                    case 69: 
                                                }
                                                this.widthPercent = var1_1.getFloat(var5_5, 1.0f);
                                                ** break;
                                            }
                                            case 63: {
                                                this.circleAngle = var1_1.getFloat(var5_5, this.circleAngle);
                                                ** break;
                                            }
                                            case 62: {
                                                this.circleRadius = var1_1.getDimensionPixelSize(var5_5, this.circleRadius);
                                                ** break;
                                            }
                                            case 61: 
                                        }
                                        this.circleConstraint = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.circleConstraint);
                                        ** break;
                                    }
                                    case 59: {
                                        this.heightMin = var1_1.getDimensionPixelSize(var5_5, this.heightMin);
                                        ** break;
                                    }
                                    case 58: {
                                        this.widthMin = var1_1.getDimensionPixelSize(var5_5, this.widthMin);
                                        ** break;
                                    }
                                    case 57: {
                                        this.heightMax = var1_1.getDimensionPixelSize(var5_5, this.heightMax);
                                        ** break;
                                    }
                                    case 56: {
                                        this.widthMax = var1_1.getDimensionPixelSize(var5_5, this.widthMax);
                                        ** break;
                                    }
                                    case 55: {
                                        this.heightDefault = var1_1.getInt(var5_5, this.heightDefault);
                                        ** break;
                                    }
                                    case 54: 
                                }
                                this.widthDefault = var1_1.getInt(var5_5, this.widthDefault);
                                ** break;
                            }
                            case 40: {
                                this.verticalChainStyle = var1_1.getInt(var5_5, this.verticalChainStyle);
                                ** break;
                            }
                            case 39: {
                                this.horizontalChainStyle = var1_1.getInt(var5_5, this.horizontalChainStyle);
                                ** break;
                            }
                            case 38: {
                                this.verticalWeight = var1_1.getFloat(var5_5, this.verticalWeight);
                                ** break;
                            }
                            case 37: {
                                this.horizontalWeight = var1_1.getFloat(var5_5, this.horizontalWeight);
                                ** break;
                            }
                            case 36: {
                                this.verticalBias = var1_1.getFloat(var5_5, this.verticalBias);
                                ** break;
                            }
                            case 35: {
                                this.topToTop = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.topToTop);
                                ** break;
                            }
                            case 34: {
                                this.topToBottom = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.topToBottom);
                                ** break;
                            }
                            case 33: {
                                this.topMargin = var1_1.getDimensionPixelSize(var5_5, this.topMargin);
                                ** break;
                            }
                            case 32: {
                                this.startToStart = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.startToStart);
                                ** break;
                            }
                            case 31: {
                                this.startToEnd = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.startToEnd);
                                ** break;
                            }
                            case 30: {
                                if (Build.VERSION.SDK_INT < 17) break;
                                this.startMargin = var1_1.getDimensionPixelSize(var5_5, this.startMargin);
                                ** break;
                            }
                            case 29: {
                                this.rightToRight = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.rightToRight);
                                ** break;
                            }
                            case 28: {
                                this.rightToLeft = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.rightToLeft);
                                ** break;
                            }
                            case 27: {
                                this.rightMargin = var1_1.getDimensionPixelSize(var5_5, this.rightMargin);
                                ** break;
                            }
                            case 26: {
                                this.orientation = var1_1.getInt(var5_5, this.orientation);
                                ** break;
                            }
                            case 25: {
                                this.leftToRight = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.leftToRight);
                                ** break;
                            }
                            case 24: {
                                this.leftToLeft = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.leftToLeft);
                                ** break;
                            }
                            case 23: {
                                this.leftMargin = var1_1.getDimensionPixelSize(var5_5, this.leftMargin);
                                ** break;
                            }
                            case 22: {
                                this.mWidth = var1_1.getLayoutDimension(var5_5, this.mWidth);
                                ** break;
                            }
                            case 21: {
                                this.mHeight = var1_1.getLayoutDimension(var5_5, this.mHeight);
                                ** break;
                            }
                            case 20: {
                                this.horizontalBias = var1_1.getFloat(var5_5, this.horizontalBias);
                                ** break;
                            }
                            case 19: {
                                this.guidePercent = var1_1.getFloat(var5_5, this.guidePercent);
                                ** break;
                            }
                            case 18: {
                                this.guideEnd = var1_1.getDimensionPixelOffset(var5_5, this.guideEnd);
                                ** break;
                            }
                            case 17: {
                                this.guideBegin = var1_1.getDimensionPixelOffset(var5_5, this.guideBegin);
                                ** break;
                            }
                            case 16: {
                                this.goneTopMargin = var1_1.getDimensionPixelSize(var5_5, this.goneTopMargin);
                                ** break;
                            }
                            case 15: {
                                this.goneStartMargin = var1_1.getDimensionPixelSize(var5_5, this.goneStartMargin);
                                ** break;
                            }
                            case 14: {
                                this.goneRightMargin = var1_1.getDimensionPixelSize(var5_5, this.goneRightMargin);
                                ** break;
                            }
                            case 13: {
                                this.goneLeftMargin = var1_1.getDimensionPixelSize(var5_5, this.goneLeftMargin);
                                ** break;
                            }
                            case 12: {
                                this.goneEndMargin = var1_1.getDimensionPixelSize(var5_5, this.goneEndMargin);
                                ** break;
                            }
                            case 11: {
                                this.goneBottomMargin = var1_1.getDimensionPixelSize(var5_5, this.goneBottomMargin);
                                ** break;
                            }
                            case 10: {
                                this.endToStart = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.endToStart);
                                ** break;
                            }
                            case 9: {
                                this.endToEnd = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.endToEnd);
                                ** break;
                            }
                            case 8: {
                                if (Build.VERSION.SDK_INT < 17) break;
                                this.endMargin = var1_1.getDimensionPixelSize(var5_5, this.endMargin);
                                ** break;
                            }
                            case 7: {
                                this.editorAbsoluteY = var1_1.getDimensionPixelOffset(var5_5, this.editorAbsoluteY);
                                ** break;
                            }
                            case 6: {
                                this.editorAbsoluteX = var1_1.getDimensionPixelOffset(var5_5, this.editorAbsoluteX);
                                ** break;
                            }
                            case 5: {
                                this.dimensionRatio = var1_1.getString(var5_5);
                                ** break;
                            }
                            case 4: {
                                this.bottomToTop = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.bottomToTop);
                                ** break;
                            }
                            case 3: {
                                this.bottomToBottom = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.bottomToBottom);
                                ** break;
                            }
                            case 2: {
                                this.bottomMargin = var1_1.getDimensionPixelSize(var5_5, this.bottomMargin);
                                ** break;
                            }
                            case 1: {
                                this.baselineToBaseline = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.baselineToBaseline);
                                ** break;
lbl220: // 59 sources:
                                break;
                            }
                        }
                    } else {
                        this.constrainedHeight = var1_1.getBoolean(var5_5, this.constrainedHeight);
                    }
                } else {
                    this.constrainedWidth = var1_1.getBoolean(var5_5, this.constrainedWidth);
                }
                ++var4_4;
            } while (true);
        }
    }

    public static class Motion {
        private static final int ANIMATE_RELATIVE_TO = 5;
        private static final int MOTION_DRAW_PATH = 4;
        private static final int MOTION_STAGGER = 6;
        private static final int PATH_MOTION_ARC = 2;
        private static final int TRANSITION_EASING = 3;
        private static final int TRANSITION_PATH_ROTATE = 1;
        private static SparseIntArray mapToConstant;
        public int mAnimateRelativeTo = -1;
        public boolean mApply = false;
        public int mDrawPath = 0;
        public float mMotionStagger = Float.NaN;
        public int mPathMotionArc = -1;
        public float mPathRotate = Float.NaN;
        public String mTransitionEasing = null;

        static {
            SparseIntArray sparseIntArray;
            mapToConstant = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(R.styleable.Motion_motionPathRotate, 1);
            mapToConstant.append(R.styleable.Motion_pathMotionArc, 2);
            mapToConstant.append(R.styleable.Motion_transitionEasing, 3);
            mapToConstant.append(R.styleable.Motion_drawPath, 4);
            mapToConstant.append(R.styleable.Motion_animate_relativeTo, 5);
            mapToConstant.append(R.styleable.Motion_motionStagger, 6);
        }

        public void copyFrom(Motion motion) {
            this.mApply = motion.mApply;
            this.mAnimateRelativeTo = motion.mAnimateRelativeTo;
            this.mTransitionEasing = motion.mTransitionEasing;
            this.mPathMotionArc = motion.mPathMotionArc;
            this.mDrawPath = motion.mDrawPath;
            this.mPathRotate = motion.mPathRotate;
            this.mMotionStagger = motion.mMotionStagger;
        }

        /*
         * Unable to fully structure code
         */
        void fillFromAttributeList(Context var1_1, AttributeSet var2_2) {
            var1_1 = var1_1.obtainStyledAttributes(var2_2, R.styleable.Motion);
            this.mApply = true;
            var3_3 = var1_1.getIndexCount();
            var4_4 = 0;
            do {
                if (var4_4 >= var3_3) {
                    var1_1.recycle();
                    return;
                }
                var5_5 = var1_1.getIndex(var4_4);
                switch (Motion.mapToConstant.get(var5_5)) {
                    default: {
                        ** break;
                    }
                    case 6: {
                        this.mMotionStagger = var1_1.getFloat(var5_5, this.mMotionStagger);
                        ** break;
                    }
                    case 5: {
                        this.mAnimateRelativeTo = ConstraintSet.access$100((TypedArray)var1_1, var5_5, this.mAnimateRelativeTo);
                        ** break;
                    }
                    case 4: {
                        this.mDrawPath = var1_1.getInt(var5_5, 0);
                        ** break;
                    }
                    case 3: {
                        if (var1_1.peekValue((int)var5_5).type == 3) {
                            this.mTransitionEasing = var1_1.getString(var5_5);
                            ** break;
                        }
                        this.mTransitionEasing = Easing.NAMED_EASING[var1_1.getInteger(var5_5, 0)];
                        ** break;
                    }
                    case 2: {
                        this.mPathMotionArc = var1_1.getInt(var5_5, this.mPathMotionArc);
                        ** break;
                    }
                    case 1: 
                }
                this.mPathRotate = var1_1.getFloat(var5_5, this.mPathRotate);
lbl33: // 8 sources:
                ++var4_4;
            } while (true);
        }
    }

    public static class PropertySet {
        public float alpha = 1.0f;
        public boolean mApply = false;
        public float mProgress = Float.NaN;
        public int mVisibilityMode = 0;
        public int visibility = 0;

        public void copyFrom(PropertySet propertySet) {
            this.mApply = propertySet.mApply;
            this.visibility = propertySet.visibility;
            this.alpha = propertySet.alpha;
            this.mProgress = propertySet.mProgress;
            this.mVisibilityMode = propertySet.mVisibilityMode;
        }

        void fillFromAttributeList(Context context, AttributeSet attributeSet) {
            context = context.obtainStyledAttributes(attributeSet, R.styleable.PropertySet);
            this.mApply = true;
            int n = context.getIndexCount();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    context.recycle();
                    return;
                }
                int n3 = context.getIndex(n2);
                if (n3 == R.styleable.PropertySet_android_alpha) {
                    this.alpha = context.getFloat(n3, this.alpha);
                } else if (n3 == R.styleable.PropertySet_android_visibility) {
                    this.visibility = context.getInt(n3, this.visibility);
                    this.visibility = VISIBILITY_FLAGS[this.visibility];
                } else if (n3 == R.styleable.PropertySet_visibilityMode) {
                    this.mVisibilityMode = context.getInt(n3, this.mVisibilityMode);
                } else if (n3 == R.styleable.PropertySet_motionProgress) {
                    this.mProgress = context.getFloat(n3, this.mProgress);
                }
                ++n2;
            } while (true);
        }
    }

    public static class Transform {
        private static final int ELEVATION = 11;
        private static final int ROTATION = 1;
        private static final int ROTATION_X = 2;
        private static final int ROTATION_Y = 3;
        private static final int SCALE_X = 4;
        private static final int SCALE_Y = 5;
        private static final int TRANSFORM_PIVOT_X = 6;
        private static final int TRANSFORM_PIVOT_Y = 7;
        private static final int TRANSLATION_X = 8;
        private static final int TRANSLATION_Y = 9;
        private static final int TRANSLATION_Z = 10;
        private static SparseIntArray mapToConstant;
        public boolean applyElevation = false;
        public float elevation = 0.0f;
        public boolean mApply = false;
        public float rotation = 0.0f;
        public float rotationX = 0.0f;
        public float rotationY = 0.0f;
        public float scaleX = 1.0f;
        public float scaleY = 1.0f;
        public float transformPivotX = Float.NaN;
        public float transformPivotY = Float.NaN;
        public float translationX = 0.0f;
        public float translationY = 0.0f;
        public float translationZ = 0.0f;

        static {
            SparseIntArray sparseIntArray;
            mapToConstant = sparseIntArray = new SparseIntArray();
            sparseIntArray.append(R.styleable.Transform_android_rotation, 1);
            mapToConstant.append(R.styleable.Transform_android_rotationX, 2);
            mapToConstant.append(R.styleable.Transform_android_rotationY, 3);
            mapToConstant.append(R.styleable.Transform_android_scaleX, 4);
            mapToConstant.append(R.styleable.Transform_android_scaleY, 5);
            mapToConstant.append(R.styleable.Transform_android_transformPivotX, 6);
            mapToConstant.append(R.styleable.Transform_android_transformPivotY, 7);
            mapToConstant.append(R.styleable.Transform_android_translationX, 8);
            mapToConstant.append(R.styleable.Transform_android_translationY, 9);
            mapToConstant.append(R.styleable.Transform_android_translationZ, 10);
            mapToConstant.append(R.styleable.Transform_android_elevation, 11);
        }

        public void copyFrom(Transform transform) {
            this.mApply = transform.mApply;
            this.rotation = transform.rotation;
            this.rotationX = transform.rotationX;
            this.rotationY = transform.rotationY;
            this.scaleX = transform.scaleX;
            this.scaleY = transform.scaleY;
            this.transformPivotX = transform.transformPivotX;
            this.transformPivotY = transform.transformPivotY;
            this.translationX = transform.translationX;
            this.translationY = transform.translationY;
            this.translationZ = transform.translationZ;
            this.applyElevation = transform.applyElevation;
            this.elevation = transform.elevation;
        }

        void fillFromAttributeList(Context context, AttributeSet attributeSet) {
            context = context.obtainStyledAttributes(attributeSet, R.styleable.Transform);
            this.mApply = true;
            int n = context.getIndexCount();
            int n2 = 0;
            do {
                if (n2 >= n) {
                    context.recycle();
                    return;
                }
                int n3 = context.getIndex(n2);
                switch (mapToConstant.get(n3)) {
                    default: {
                        break;
                    }
                    case 11: {
                        if (Build.VERSION.SDK_INT < 21) break;
                        this.applyElevation = true;
                        this.elevation = context.getDimension(n3, this.elevation);
                        break;
                    }
                    case 10: {
                        if (Build.VERSION.SDK_INT < 21) break;
                        this.translationZ = context.getDimension(n3, this.translationZ);
                        break;
                    }
                    case 9: {
                        this.translationY = context.getDimension(n3, this.translationY);
                        break;
                    }
                    case 8: {
                        this.translationX = context.getDimension(n3, this.translationX);
                        break;
                    }
                    case 7: {
                        this.transformPivotY = context.getDimension(n3, this.transformPivotY);
                        break;
                    }
                    case 6: {
                        this.transformPivotX = context.getDimension(n3, this.transformPivotX);
                        break;
                    }
                    case 5: {
                        this.scaleY = context.getFloat(n3, this.scaleY);
                        break;
                    }
                    case 4: {
                        this.scaleX = context.getFloat(n3, this.scaleX);
                        break;
                    }
                    case 3: {
                        this.rotationY = context.getFloat(n3, this.rotationY);
                        break;
                    }
                    case 2: {
                        this.rotationX = context.getFloat(n3, this.rotationX);
                        break;
                    }
                    case 1: {
                        this.rotation = context.getFloat(n3, this.rotation);
                    }
                }
                ++n2;
            } while (true);
        }
    }

}


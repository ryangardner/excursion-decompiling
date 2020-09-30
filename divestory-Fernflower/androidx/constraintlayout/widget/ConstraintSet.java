package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Color;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.Xml;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.motion.widget.Debug;
import androidx.constraintlayout.motion.widget.MotionScene;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.io.IOException;
import java.io.PrintStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
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
   private static final int[] VISIBILITY_FLAGS = new int[]{0, 4, 8};
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
   private HashMap<Integer, ConstraintSet.Constraint> mConstraints = new HashMap();
   private boolean mForceId = true;
   private HashMap<String, ConstraintAttribute> mSavedAttributes = new HashMap();
   private boolean mValidate;

   static {
      SparseIntArray var0 = new SparseIntArray();
      mapToConstant = var0;
      var0.append(R.styleable.Constraint_layout_constraintLeft_toLeftOf, 25);
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

   private void addAttributes(ConstraintAttribute.AttributeType var1, String... var2) {
      for(int var3 = 0; var3 < var2.length; ++var3) {
         ConstraintAttribute var4;
         if (this.mSavedAttributes.containsKey(var2[var3])) {
            var4 = (ConstraintAttribute)this.mSavedAttributes.get(var2[var3]);
            if (var4.getType() != var1) {
               StringBuilder var5 = new StringBuilder();
               var5.append("ConstraintAttribute is already a ");
               var5.append(var4.getType().name());
               throw new IllegalArgumentException(var5.toString());
            }
         } else {
            var4 = new ConstraintAttribute(var2[var3], var1);
            this.mSavedAttributes.put(var2[var3], var4);
         }
      }

   }

   private int[] convertReferenceString(View var1, String var2) {
      String[] var3 = var2.split(",");
      Context var4 = var1.getContext();
      int[] var13 = new int[var3.length];
      int var5 = 0;

      int var6;
      for(var6 = 0; var5 < var3.length; ++var6) {
         String var7 = var3[var5].trim();

         int var8;
         try {
            var8 = R.id.class.getField(var7).getInt((Object)null);
         } catch (Exception var11) {
            var8 = 0;
         }

         int var10 = var8;
         if (var8 == 0) {
            var10 = var4.getResources().getIdentifier(var7, "id", var4.getPackageName());
         }

         var8 = var10;
         if (var10 == 0) {
            var8 = var10;
            if (var1.isInEditMode()) {
               var8 = var10;
               if (var1.getParent() instanceof ConstraintLayout) {
                  Object var14 = ((ConstraintLayout)var1.getParent()).getDesignInformation(0, var7);
                  var8 = var10;
                  if (var14 != null) {
                     var8 = var10;
                     if (var14 instanceof Integer) {
                        var8 = (Integer)var14;
                     }
                  }
               }
            }
         }

         var13[var6] = var8;
         ++var5;
      }

      int[] var12 = var13;
      if (var6 != var3.length) {
         var12 = Arrays.copyOf(var13, var6);
      }

      return var12;
   }

   private void createHorizontalChain(int var1, int var2, int var3, int var4, int[] var5, float[] var6, int var7, int var8, int var9) {
      if (var5.length >= 2) {
         if (var6 != null && var6.length != var5.length) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
         } else {
            if (var6 != null) {
               this.get(var5[0]).layout.horizontalWeight = var6[0];
            }

            this.get(var5[0]).layout.horizontalChainStyle = var7;
            this.connect(var5[0], var8, var1, var2, -1);

            for(var1 = 1; var1 < var5.length; ++var1) {
               var7 = var5[var1];
               var2 = var1 - 1;
               this.connect(var7, var8, var5[var2], var9, -1);
               this.connect(var5[var2], var9, var5[var1], var8, -1);
               if (var6 != null) {
                  this.get(var5[var1]).layout.horizontalWeight = var6[var1];
               }
            }

            this.connect(var5[var5.length - 1], var9, var3, var4, -1);
         }
      } else {
         throw new IllegalArgumentException("must have 2 or more widgets in a chain");
      }
   }

   private ConstraintSet.Constraint fillFromAttributeList(Context var1, AttributeSet var2) {
      ConstraintSet.Constraint var3 = new ConstraintSet.Constraint();
      TypedArray var4 = var1.obtainStyledAttributes(var2, R.styleable.Constraint);
      this.populateConstraint(var1, var3, var4);
      var4.recycle();
      return var3;
   }

   private ConstraintSet.Constraint get(int var1) {
      if (!this.mConstraints.containsKey(var1)) {
         this.mConstraints.put(var1, new ConstraintSet.Constraint());
      }

      return (ConstraintSet.Constraint)this.mConstraints.get(var1);
   }

   private static int lookupID(TypedArray var0, int var1, int var2) {
      int var3 = var0.getResourceId(var1, var2);
      var2 = var3;
      if (var3 == -1) {
         var2 = var0.getInt(var1, -1);
      }

      return var2;
   }

   private void populateConstraint(Context var1, ConstraintSet.Constraint var2, TypedArray var3) {
      int var4 = var3.getIndexCount();

      for(int var5 = 0; var5 < var4; ++var5) {
         int var6 = var3.getIndex(var5);
         if (var6 != R.styleable.Constraint_android_id && R.styleable.Constraint_android_layout_marginStart != var6 && R.styleable.Constraint_android_layout_marginEnd != var6) {
            var2.motion.mApply = true;
            var2.layout.mApply = true;
            var2.propertySet.mApply = true;
            var2.transform.mApply = true;
         }

         StringBuilder var7;
         switch(mapToConstant.get(var6)) {
         case 1:
            var2.layout.baselineToBaseline = lookupID(var3, var6, var2.layout.baselineToBaseline);
            break;
         case 2:
            var2.layout.bottomMargin = var3.getDimensionPixelSize(var6, var2.layout.bottomMargin);
            break;
         case 3:
            var2.layout.bottomToBottom = lookupID(var3, var6, var2.layout.bottomToBottom);
            break;
         case 4:
            var2.layout.bottomToTop = lookupID(var3, var6, var2.layout.bottomToTop);
            break;
         case 5:
            var2.layout.dimensionRatio = var3.getString(var6);
            break;
         case 6:
            var2.layout.editorAbsoluteX = var3.getDimensionPixelOffset(var6, var2.layout.editorAbsoluteX);
            break;
         case 7:
            var2.layout.editorAbsoluteY = var3.getDimensionPixelOffset(var6, var2.layout.editorAbsoluteY);
            break;
         case 8:
            if (VERSION.SDK_INT >= 17) {
               var2.layout.endMargin = var3.getDimensionPixelSize(var6, var2.layout.endMargin);
            }
            break;
         case 9:
            var2.layout.endToEnd = lookupID(var3, var6, var2.layout.endToEnd);
            break;
         case 10:
            var2.layout.endToStart = lookupID(var3, var6, var2.layout.endToStart);
            break;
         case 11:
            var2.layout.goneBottomMargin = var3.getDimensionPixelSize(var6, var2.layout.goneBottomMargin);
            break;
         case 12:
            var2.layout.goneEndMargin = var3.getDimensionPixelSize(var6, var2.layout.goneEndMargin);
            break;
         case 13:
            var2.layout.goneLeftMargin = var3.getDimensionPixelSize(var6, var2.layout.goneLeftMargin);
            break;
         case 14:
            var2.layout.goneRightMargin = var3.getDimensionPixelSize(var6, var2.layout.goneRightMargin);
            break;
         case 15:
            var2.layout.goneStartMargin = var3.getDimensionPixelSize(var6, var2.layout.goneStartMargin);
            break;
         case 16:
            var2.layout.goneTopMargin = var3.getDimensionPixelSize(var6, var2.layout.goneTopMargin);
            break;
         case 17:
            var2.layout.guideBegin = var3.getDimensionPixelOffset(var6, var2.layout.guideBegin);
            break;
         case 18:
            var2.layout.guideEnd = var3.getDimensionPixelOffset(var6, var2.layout.guideEnd);
            break;
         case 19:
            var2.layout.guidePercent = var3.getFloat(var6, var2.layout.guidePercent);
            break;
         case 20:
            var2.layout.horizontalBias = var3.getFloat(var6, var2.layout.horizontalBias);
            break;
         case 21:
            var2.layout.mHeight = var3.getLayoutDimension(var6, var2.layout.mHeight);
            break;
         case 22:
            var2.propertySet.visibility = var3.getInt(var6, var2.propertySet.visibility);
            var2.propertySet.visibility = VISIBILITY_FLAGS[var2.propertySet.visibility];
            break;
         case 23:
            var2.layout.mWidth = var3.getLayoutDimension(var6, var2.layout.mWidth);
            break;
         case 24:
            var2.layout.leftMargin = var3.getDimensionPixelSize(var6, var2.layout.leftMargin);
            break;
         case 25:
            var2.layout.leftToLeft = lookupID(var3, var6, var2.layout.leftToLeft);
            break;
         case 26:
            var2.layout.leftToRight = lookupID(var3, var6, var2.layout.leftToRight);
            break;
         case 27:
            var2.layout.orientation = var3.getInt(var6, var2.layout.orientation);
            break;
         case 28:
            var2.layout.rightMargin = var3.getDimensionPixelSize(var6, var2.layout.rightMargin);
            break;
         case 29:
            var2.layout.rightToLeft = lookupID(var3, var6, var2.layout.rightToLeft);
            break;
         case 30:
            var2.layout.rightToRight = lookupID(var3, var6, var2.layout.rightToRight);
            break;
         case 31:
            if (VERSION.SDK_INT >= 17) {
               var2.layout.startMargin = var3.getDimensionPixelSize(var6, var2.layout.startMargin);
            }
            break;
         case 32:
            var2.layout.startToEnd = lookupID(var3, var6, var2.layout.startToEnd);
            break;
         case 33:
            var2.layout.startToStart = lookupID(var3, var6, var2.layout.startToStart);
            break;
         case 34:
            var2.layout.topMargin = var3.getDimensionPixelSize(var6, var2.layout.topMargin);
            break;
         case 35:
            var2.layout.topToBottom = lookupID(var3, var6, var2.layout.topToBottom);
            break;
         case 36:
            var2.layout.topToTop = lookupID(var3, var6, var2.layout.topToTop);
            break;
         case 37:
            var2.layout.verticalBias = var3.getFloat(var6, var2.layout.verticalBias);
            break;
         case 38:
            var2.mViewId = var3.getResourceId(var6, var2.mViewId);
            break;
         case 39:
            var2.layout.horizontalWeight = var3.getFloat(var6, var2.layout.horizontalWeight);
            break;
         case 40:
            var2.layout.verticalWeight = var3.getFloat(var6, var2.layout.verticalWeight);
            break;
         case 41:
            var2.layout.horizontalChainStyle = var3.getInt(var6, var2.layout.horizontalChainStyle);
            break;
         case 42:
            var2.layout.verticalChainStyle = var3.getInt(var6, var2.layout.verticalChainStyle);
            break;
         case 43:
            var2.propertySet.alpha = var3.getFloat(var6, var2.propertySet.alpha);
            break;
         case 44:
            if (VERSION.SDK_INT >= 21) {
               var2.transform.applyElevation = true;
               var2.transform.elevation = var3.getDimension(var6, var2.transform.elevation);
            }
            break;
         case 45:
            var2.transform.rotationX = var3.getFloat(var6, var2.transform.rotationX);
            break;
         case 46:
            var2.transform.rotationY = var3.getFloat(var6, var2.transform.rotationY);
            break;
         case 47:
            var2.transform.scaleX = var3.getFloat(var6, var2.transform.scaleX);
            break;
         case 48:
            var2.transform.scaleY = var3.getFloat(var6, var2.transform.scaleY);
            break;
         case 49:
            var2.transform.transformPivotX = var3.getDimension(var6, var2.transform.transformPivotX);
            break;
         case 50:
            var2.transform.transformPivotY = var3.getDimension(var6, var2.transform.transformPivotY);
            break;
         case 51:
            var2.transform.translationX = var3.getDimension(var6, var2.transform.translationX);
            break;
         case 52:
            var2.transform.translationY = var3.getDimension(var6, var2.transform.translationY);
            break;
         case 53:
            if (VERSION.SDK_INT >= 21) {
               var2.transform.translationZ = var3.getDimension(var6, var2.transform.translationZ);
            }
            break;
         case 54:
            var2.layout.widthDefault = var3.getInt(var6, var2.layout.widthDefault);
            break;
         case 55:
            var2.layout.heightDefault = var3.getInt(var6, var2.layout.heightDefault);
            break;
         case 56:
            var2.layout.widthMax = var3.getDimensionPixelSize(var6, var2.layout.widthMax);
            break;
         case 57:
            var2.layout.heightMax = var3.getDimensionPixelSize(var6, var2.layout.heightMax);
            break;
         case 58:
            var2.layout.widthMin = var3.getDimensionPixelSize(var6, var2.layout.widthMin);
            break;
         case 59:
            var2.layout.heightMin = var3.getDimensionPixelSize(var6, var2.layout.heightMin);
            break;
         case 60:
            var2.transform.rotation = var3.getFloat(var6, var2.transform.rotation);
            break;
         case 61:
            var2.layout.circleConstraint = lookupID(var3, var6, var2.layout.circleConstraint);
            break;
         case 62:
            var2.layout.circleRadius = var3.getDimensionPixelSize(var6, var2.layout.circleRadius);
            break;
         case 63:
            var2.layout.circleAngle = var3.getFloat(var6, var2.layout.circleAngle);
            break;
         case 64:
            var2.motion.mAnimateRelativeTo = lookupID(var3, var6, var2.motion.mAnimateRelativeTo);
            break;
         case 65:
            if (var3.peekValue(var6).type == 3) {
               var2.motion.mTransitionEasing = var3.getString(var6);
            } else {
               var2.motion.mTransitionEasing = Easing.NAMED_EASING[var3.getInteger(var6, 0)];
            }
            break;
         case 66:
            var2.motion.mDrawPath = var3.getInt(var6, 0);
            break;
         case 67:
            var2.motion.mPathRotate = var3.getFloat(var6, var2.motion.mPathRotate);
            break;
         case 68:
            var2.propertySet.mProgress = var3.getFloat(var6, var2.propertySet.mProgress);
            break;
         case 69:
            var2.layout.widthPercent = var3.getFloat(var6, 1.0F);
            break;
         case 70:
            var2.layout.heightPercent = var3.getFloat(var6, 1.0F);
            break;
         case 71:
            Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
            break;
         case 72:
            var2.layout.mBarrierDirection = var3.getInt(var6, var2.layout.mBarrierDirection);
            break;
         case 73:
            var2.layout.mBarrierMargin = var3.getDimensionPixelSize(var6, var2.layout.mBarrierMargin);
            break;
         case 74:
            var2.layout.mReferenceIdString = var3.getString(var6);
            break;
         case 75:
            var2.layout.mBarrierAllowsGoneWidgets = var3.getBoolean(var6, var2.layout.mBarrierAllowsGoneWidgets);
            break;
         case 76:
            var2.motion.mPathMotionArc = var3.getInt(var6, var2.motion.mPathMotionArc);
            break;
         case 77:
            var2.layout.mConstraintTag = var3.getString(var6);
            break;
         case 78:
            var2.propertySet.mVisibilityMode = var3.getInt(var6, var2.propertySet.mVisibilityMode);
            break;
         case 79:
            var2.motion.mMotionStagger = var3.getFloat(var6, var2.motion.mMotionStagger);
            break;
         case 80:
            var2.layout.constrainedWidth = var3.getBoolean(var6, var2.layout.constrainedWidth);
            break;
         case 81:
            var2.layout.constrainedHeight = var3.getBoolean(var6, var2.layout.constrainedHeight);
            break;
         case 82:
            var7 = new StringBuilder();
            var7.append("unused attribute 0x");
            var7.append(Integer.toHexString(var6));
            var7.append("   ");
            var7.append(mapToConstant.get(var6));
            Log.w("ConstraintSet", var7.toString());
            break;
         default:
            var7 = new StringBuilder();
            var7.append("Unknown attribute 0x");
            var7.append(Integer.toHexString(var6));
            var7.append("   ");
            var7.append(mapToConstant.get(var6));
            Log.w("ConstraintSet", var7.toString());
         }
      }

   }

   private String sideToString(int var1) {
      switch(var1) {
      case 1:
         return "left";
      case 2:
         return "right";
      case 3:
         return "top";
      case 4:
         return "bottom";
      case 5:
         return "baseline";
      case 6:
         return "start";
      case 7:
         return "end";
      default:
         return "undefined";
      }
   }

   private static String[] splitString(String var0) {
      char[] var1 = var0.toCharArray();
      ArrayList var7 = new ArrayList();
      int var2 = 0;
      int var3 = 0;

      boolean var6;
      for(boolean var4 = false; var2 < var1.length; var4 = var6) {
         int var5;
         if (var1[var2] == ',' && !var4) {
            var7.add(new String(var1, var3, var2 - var3));
            var5 = var2 + 1;
            var6 = var4;
         } else {
            var5 = var3;
            var6 = var4;
            if (var1[var2] == '"') {
               var6 = var4 ^ true;
               var5 = var3;
            }
         }

         ++var2;
         var3 = var5;
      }

      var7.add(new String(var1, var3, var1.length - var3));
      return (String[])var7.toArray(new String[var7.size()]);
   }

   public void addColorAttributes(String... var1) {
      this.addAttributes(ConstraintAttribute.AttributeType.COLOR_TYPE, var1);
   }

   public void addFloatAttributes(String... var1) {
      this.addAttributes(ConstraintAttribute.AttributeType.FLOAT_TYPE, var1);
   }

   public void addIntAttributes(String... var1) {
      this.addAttributes(ConstraintAttribute.AttributeType.INT_TYPE, var1);
   }

   public void addStringAttributes(String... var1) {
      this.addAttributes(ConstraintAttribute.AttributeType.STRING_TYPE, var1);
   }

   public void addToHorizontalChain(int var1, int var2, int var3) {
      byte var4;
      if (var2 == 0) {
         var4 = 1;
      } else {
         var4 = 2;
      }

      this.connect(var1, 1, var2, var4, 0);
      if (var3 == 0) {
         var4 = 2;
      } else {
         var4 = 1;
      }

      this.connect(var1, 2, var3, var4, 0);
      if (var2 != 0) {
         this.connect(var2, 2, var1, 1, 0);
      }

      if (var3 != 0) {
         this.connect(var3, 1, var1, 2, 0);
      }

   }

   public void addToHorizontalChainRTL(int var1, int var2, int var3) {
      byte var4;
      if (var2 == 0) {
         var4 = 6;
      } else {
         var4 = 7;
      }

      this.connect(var1, 6, var2, var4, 0);
      if (var3 == 0) {
         var4 = 7;
      } else {
         var4 = 6;
      }

      this.connect(var1, 7, var3, var4, 0);
      if (var2 != 0) {
         this.connect(var2, 7, var1, 6, 0);
      }

      if (var3 != 0) {
         this.connect(var3, 6, var1, 7, 0);
      }

   }

   public void addToVerticalChain(int var1, int var2, int var3) {
      byte var4;
      if (var2 == 0) {
         var4 = 3;
      } else {
         var4 = 4;
      }

      this.connect(var1, 3, var2, var4, 0);
      if (var3 == 0) {
         var4 = 4;
      } else {
         var4 = 3;
      }

      this.connect(var1, 4, var3, var4, 0);
      if (var2 != 0) {
         this.connect(var2, 4, var1, 3, 0);
      }

      if (var3 != 0) {
         this.connect(var3, 3, var1, 4, 0);
      }

   }

   public void applyCustomAttributes(ConstraintLayout var1) {
      int var2 = var1.getChildCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         View var4 = var1.getChildAt(var3);
         int var5 = var4.getId();
         if (!this.mConstraints.containsKey(var5)) {
            StringBuilder var6 = new StringBuilder();
            var6.append("id unknown ");
            var6.append(Debug.getName(var4));
            Log.v("ConstraintSet", var6.toString());
         } else {
            if (this.mForceId && var5 == -1) {
               throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }

            if (this.mConstraints.containsKey(var5)) {
               ConstraintAttribute.setAttributes(var4, ((ConstraintSet.Constraint)this.mConstraints.get(var5)).mCustomConstraints);
            }
         }
      }

   }

   public void applyTo(ConstraintLayout var1) {
      this.applyToInternal(var1, true);
      var1.setConstraintSet((ConstraintSet)null);
      var1.requestLayout();
   }

   public void applyToHelper(ConstraintHelper var1, ConstraintWidget var2, ConstraintLayout.LayoutParams var3, SparseArray<ConstraintWidget> var4) {
      int var5 = var1.getId();
      if (this.mConstraints.containsKey(var5)) {
         ConstraintSet.Constraint var6 = (ConstraintSet.Constraint)this.mConstraints.get(var5);
         if (var2 instanceof HelperWidget) {
            var1.loadParameters(var6, (HelperWidget)var2, var3, var4);
         }
      }

   }

   void applyToInternal(ConstraintLayout var1, boolean var2) {
      int var3 = var1.getChildCount();
      HashSet var4 = new HashSet(this.mConstraints.keySet());

      ConstraintLayout.LayoutParams var17;
      for(int var5 = 0; var5 < var3; ++var5) {
         View var6 = var1.getChildAt(var5);
         int var7 = var6.getId();
         if (!this.mConstraints.containsKey(var7)) {
            StringBuilder var8 = new StringBuilder();
            var8.append("id unknown ");
            var8.append(Debug.getName(var6));
            Log.w("ConstraintSet", var8.toString());
         } else {
            if (this.mForceId && var7 == -1) {
               throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
            }

            if (var7 != -1) {
               if (this.mConstraints.containsKey(var7)) {
                  var4.remove(var7);
                  ConstraintSet.Constraint var14 = (ConstraintSet.Constraint)this.mConstraints.get(var7);
                  if (var6 instanceof Barrier) {
                     var14.layout.mHelperType = 1;
                  }

                  if (var14.layout.mHelperType != -1 && var14.layout.mHelperType == 1) {
                     Barrier var9 = (Barrier)var6;
                     var9.setId(var7);
                     var9.setType(var14.layout.mBarrierDirection);
                     var9.setMargin(var14.layout.mBarrierMargin);
                     var9.setAllowsGoneWidget(var14.layout.mBarrierAllowsGoneWidgets);
                     if (var14.layout.mReferenceIds != null) {
                        var9.setReferencedIds(var14.layout.mReferenceIds);
                     } else if (var14.layout.mReferenceIdString != null) {
                        var14.layout.mReferenceIds = this.convertReferenceString(var9, var14.layout.mReferenceIdString);
                        var9.setReferencedIds(var14.layout.mReferenceIds);
                     }
                  }

                  var17 = (ConstraintLayout.LayoutParams)var6.getLayoutParams();
                  var17.validate();
                  var14.applyTo(var17);
                  if (var2) {
                     ConstraintAttribute.setAttributes(var6, var14.mCustomConstraints);
                  }

                  var6.setLayoutParams(var17);
                  if (var14.propertySet.mVisibilityMode == 0) {
                     var6.setVisibility(var14.propertySet.visibility);
                  }

                  if (VERSION.SDK_INT >= 17) {
                     var6.setAlpha(var14.propertySet.alpha);
                     var6.setRotation(var14.transform.rotation);
                     var6.setRotationX(var14.transform.rotationX);
                     var6.setRotationY(var14.transform.rotationY);
                     var6.setScaleX(var14.transform.scaleX);
                     var6.setScaleY(var14.transform.scaleY);
                     if (!Float.isNaN(var14.transform.transformPivotX)) {
                        var6.setPivotX(var14.transform.transformPivotX);
                     }

                     if (!Float.isNaN(var14.transform.transformPivotY)) {
                        var6.setPivotY(var14.transform.transformPivotY);
                     }

                     var6.setTranslationX(var14.transform.translationX);
                     var6.setTranslationY(var14.transform.translationY);
                     if (VERSION.SDK_INT >= 21) {
                        var6.setTranslationZ(var14.transform.translationZ);
                        if (var14.transform.applyElevation) {
                           var6.setElevation(var14.transform.elevation);
                        }
                     }
                  }
               } else {
                  StringBuilder var12 = new StringBuilder();
                  var12.append("WARNING NO CONSTRAINTS for view ");
                  var12.append(var7);
                  Log.v("ConstraintSet", var12.toString());
               }
            }
         }
      }

      Iterator var13 = var4.iterator();

      while(var13.hasNext()) {
         Integer var15 = (Integer)var13.next();
         ConstraintSet.Constraint var11 = (ConstraintSet.Constraint)this.mConstraints.get(var15);
         if (var11.layout.mHelperType != -1 && var11.layout.mHelperType == 1) {
            Barrier var10 = new Barrier(var1.getContext());
            var10.setId(var15);
            if (var11.layout.mReferenceIds != null) {
               var10.setReferencedIds(var11.layout.mReferenceIds);
            } else if (var11.layout.mReferenceIdString != null) {
               var11.layout.mReferenceIds = this.convertReferenceString(var10, var11.layout.mReferenceIdString);
               var10.setReferencedIds(var11.layout.mReferenceIds);
            }

            var10.setType(var11.layout.mBarrierDirection);
            var10.setMargin(var11.layout.mBarrierMargin);
            var17 = var1.generateDefaultLayoutParams();
            var10.validateParams();
            var11.applyTo(var17);
            var1.addView(var10, var17);
         }

         if (var11.layout.mIsGuideline) {
            Guideline var18 = new Guideline(var1.getContext());
            var18.setId(var15);
            ConstraintLayout.LayoutParams var16 = var1.generateDefaultLayoutParams();
            var11.applyTo(var16);
            var1.addView(var18, var16);
         }
      }

   }

   public void applyToLayoutParams(int var1, ConstraintLayout.LayoutParams var2) {
      if (this.mConstraints.containsKey(var1)) {
         ((ConstraintSet.Constraint)this.mConstraints.get(var1)).applyTo(var2);
      }

   }

   public void applyToWithoutCustom(ConstraintLayout var1) {
      this.applyToInternal(var1, false);
      var1.setConstraintSet((ConstraintSet)null);
   }

   public void center(int var1, int var2, int var3, int var4, int var5, int var6, int var7, float var8) {
      if (var4 >= 0) {
         if (var7 < 0) {
            throw new IllegalArgumentException("margin must be > 0");
         } else if (var8 > 0.0F && var8 <= 1.0F) {
            if (var3 != 1 && var3 != 2) {
               if (var3 != 6 && var3 != 7) {
                  this.connect(var1, 3, var2, var3, var4);
                  this.connect(var1, 4, var5, var6, var7);
                  ((ConstraintSet.Constraint)this.mConstraints.get(var1)).layout.verticalBias = var8;
               } else {
                  this.connect(var1, 6, var2, var3, var4);
                  this.connect(var1, 7, var5, var6, var7);
                  ((ConstraintSet.Constraint)this.mConstraints.get(var1)).layout.horizontalBias = var8;
               }
            } else {
               this.connect(var1, 1, var2, var3, var4);
               this.connect(var1, 2, var5, var6, var7);
               ((ConstraintSet.Constraint)this.mConstraints.get(var1)).layout.horizontalBias = var8;
            }

         } else {
            throw new IllegalArgumentException("bias must be between 0 and 1 inclusive");
         }
      } else {
         throw new IllegalArgumentException("margin must be > 0");
      }
   }

   public void centerHorizontally(int var1, int var2) {
      if (var2 == 0) {
         this.center(var1, 0, 1, 0, 0, 2, 0, 0.5F);
      } else {
         this.center(var1, var2, 2, 0, var2, 1, 0, 0.5F);
      }

   }

   public void centerHorizontally(int var1, int var2, int var3, int var4, int var5, int var6, int var7, float var8) {
      this.connect(var1, 1, var2, var3, var4);
      this.connect(var1, 2, var5, var6, var7);
      ((ConstraintSet.Constraint)this.mConstraints.get(var1)).layout.horizontalBias = var8;
   }

   public void centerHorizontallyRtl(int var1, int var2) {
      if (var2 == 0) {
         this.center(var1, 0, 6, 0, 0, 7, 0, 0.5F);
      } else {
         this.center(var1, var2, 7, 0, var2, 6, 0, 0.5F);
      }

   }

   public void centerHorizontallyRtl(int var1, int var2, int var3, int var4, int var5, int var6, int var7, float var8) {
      this.connect(var1, 6, var2, var3, var4);
      this.connect(var1, 7, var5, var6, var7);
      ((ConstraintSet.Constraint)this.mConstraints.get(var1)).layout.horizontalBias = var8;
   }

   public void centerVertically(int var1, int var2) {
      if (var2 == 0) {
         this.center(var1, 0, 3, 0, 0, 4, 0, 0.5F);
      } else {
         this.center(var1, var2, 4, 0, var2, 3, 0, 0.5F);
      }

   }

   public void centerVertically(int var1, int var2, int var3, int var4, int var5, int var6, int var7, float var8) {
      this.connect(var1, 3, var2, var3, var4);
      this.connect(var1, 4, var5, var6, var7);
      ((ConstraintSet.Constraint)this.mConstraints.get(var1)).layout.verticalBias = var8;
   }

   public void clear(int var1) {
      this.mConstraints.remove(var1);
   }

   public void clear(int var1, int var2) {
      if (this.mConstraints.containsKey(var1)) {
         ConstraintSet.Constraint var3 = (ConstraintSet.Constraint)this.mConstraints.get(var1);
         switch(var2) {
         case 1:
            var3.layout.leftToRight = -1;
            var3.layout.leftToLeft = -1;
            var3.layout.leftMargin = -1;
            var3.layout.goneLeftMargin = -1;
            break;
         case 2:
            var3.layout.rightToRight = -1;
            var3.layout.rightToLeft = -1;
            var3.layout.rightMargin = -1;
            var3.layout.goneRightMargin = -1;
            break;
         case 3:
            var3.layout.topToBottom = -1;
            var3.layout.topToTop = -1;
            var3.layout.topMargin = -1;
            var3.layout.goneTopMargin = -1;
            break;
         case 4:
            var3.layout.bottomToTop = -1;
            var3.layout.bottomToBottom = -1;
            var3.layout.bottomMargin = -1;
            var3.layout.goneBottomMargin = -1;
            break;
         case 5:
            var3.layout.baselineToBaseline = -1;
            break;
         case 6:
            var3.layout.startToEnd = -1;
            var3.layout.startToStart = -1;
            var3.layout.startMargin = -1;
            var3.layout.goneStartMargin = -1;
            break;
         case 7:
            var3.layout.endToStart = -1;
            var3.layout.endToEnd = -1;
            var3.layout.endMargin = -1;
            var3.layout.goneEndMargin = -1;
            break;
         default:
            throw new IllegalArgumentException("unknown constraint");
         }
      }

   }

   public void clone(Context var1, int var2) {
      this.clone((ConstraintLayout)LayoutInflater.from(var1).inflate(var2, (ViewGroup)null));
   }

   public void clone(ConstraintLayout var1) {
      int var2 = var1.getChildCount();
      this.mConstraints.clear();

      for(int var3 = 0; var3 < var2; ++var3) {
         View var4 = var1.getChildAt(var3);
         ConstraintLayout.LayoutParams var5 = (ConstraintLayout.LayoutParams)var4.getLayoutParams();
         int var6 = var4.getId();
         if (this.mForceId && var6 == -1) {
            throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
         }

         if (!this.mConstraints.containsKey(var6)) {
            this.mConstraints.put(var6, new ConstraintSet.Constraint());
         }

         ConstraintSet.Constraint var7 = (ConstraintSet.Constraint)this.mConstraints.get(var6);
         var7.mCustomConstraints = ConstraintAttribute.extractAttributes(this.mSavedAttributes, var4);
         var7.fillFrom(var6, var5);
         var7.propertySet.visibility = var4.getVisibility();
         if (VERSION.SDK_INT >= 17) {
            var7.propertySet.alpha = var4.getAlpha();
            var7.transform.rotation = var4.getRotation();
            var7.transform.rotationX = var4.getRotationX();
            var7.transform.rotationY = var4.getRotationY();
            var7.transform.scaleX = var4.getScaleX();
            var7.transform.scaleY = var4.getScaleY();
            float var8 = var4.getPivotX();
            float var9 = var4.getPivotY();
            if ((double)var8 != 0.0D || (double)var9 != 0.0D) {
               var7.transform.transformPivotX = var8;
               var7.transform.transformPivotY = var9;
            }

            var7.transform.translationX = var4.getTranslationX();
            var7.transform.translationY = var4.getTranslationY();
            if (VERSION.SDK_INT >= 21) {
               var7.transform.translationZ = var4.getTranslationZ();
               if (var7.transform.applyElevation) {
                  var7.transform.elevation = var4.getElevation();
               }
            }
         }

         if (var4 instanceof Barrier) {
            Barrier var10 = (Barrier)var4;
            var7.layout.mBarrierAllowsGoneWidgets = var10.allowsGoneWidget();
            var7.layout.mReferenceIds = var10.getReferencedIds();
            var7.layout.mBarrierDirection = var10.getType();
            var7.layout.mBarrierMargin = var10.getMargin();
         }
      }

   }

   public void clone(ConstraintSet var1) {
      this.mConstraints.clear();
      Iterator var2 = var1.mConstraints.keySet().iterator();

      while(var2.hasNext()) {
         Integer var3 = (Integer)var2.next();
         this.mConstraints.put(var3, ((ConstraintSet.Constraint)var1.mConstraints.get(var3)).clone());
      }

   }

   public void clone(Constraints var1) {
      int var2 = var1.getChildCount();
      this.mConstraints.clear();

      for(int var3 = 0; var3 < var2; ++var3) {
         View var4 = var1.getChildAt(var3);
         Constraints.LayoutParams var5 = (Constraints.LayoutParams)var4.getLayoutParams();
         int var6 = var4.getId();
         if (this.mForceId && var6 == -1) {
            throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
         }

         if (!this.mConstraints.containsKey(var6)) {
            this.mConstraints.put(var6, new ConstraintSet.Constraint());
         }

         ConstraintSet.Constraint var7 = (ConstraintSet.Constraint)this.mConstraints.get(var6);
         if (var4 instanceof ConstraintHelper) {
            var7.fillFromConstraints((ConstraintHelper)var4, var6, var5);
         }

         var7.fillFromConstraints(var6, var5);
      }

   }

   public void connect(int var1, int var2, int var3, int var4) {
      if (!this.mConstraints.containsKey(var1)) {
         this.mConstraints.put(var1, new ConstraintSet.Constraint());
      }

      ConstraintSet.Constraint var5 = (ConstraintSet.Constraint)this.mConstraints.get(var1);
      StringBuilder var6;
      switch(var2) {
      case 1:
         if (var4 == 1) {
            var5.layout.leftToLeft = var3;
            var5.layout.leftToRight = -1;
         } else {
            if (var4 != 2) {
               var6 = new StringBuilder();
               var6.append("left to ");
               var6.append(this.sideToString(var4));
               var6.append(" undefined");
               throw new IllegalArgumentException(var6.toString());
            }

            var5.layout.leftToRight = var3;
            var5.layout.leftToLeft = -1;
         }
         break;
      case 2:
         if (var4 == 1) {
            var5.layout.rightToLeft = var3;
            var5.layout.rightToRight = -1;
         } else {
            if (var4 != 2) {
               var6 = new StringBuilder();
               var6.append("right to ");
               var6.append(this.sideToString(var4));
               var6.append(" undefined");
               throw new IllegalArgumentException(var6.toString());
            }

            var5.layout.rightToRight = var3;
            var5.layout.rightToLeft = -1;
         }
         break;
      case 3:
         if (var4 == 3) {
            var5.layout.topToTop = var3;
            var5.layout.topToBottom = -1;
            var5.layout.baselineToBaseline = -1;
         } else {
            if (var4 != 4) {
               var6 = new StringBuilder();
               var6.append("right to ");
               var6.append(this.sideToString(var4));
               var6.append(" undefined");
               throw new IllegalArgumentException(var6.toString());
            }

            var5.layout.topToBottom = var3;
            var5.layout.topToTop = -1;
            var5.layout.baselineToBaseline = -1;
         }
         break;
      case 4:
         if (var4 == 4) {
            var5.layout.bottomToBottom = var3;
            var5.layout.bottomToTop = -1;
            var5.layout.baselineToBaseline = -1;
         } else {
            if (var4 != 3) {
               var6 = new StringBuilder();
               var6.append("right to ");
               var6.append(this.sideToString(var4));
               var6.append(" undefined");
               throw new IllegalArgumentException(var6.toString());
            }

            var5.layout.bottomToTop = var3;
            var5.layout.bottomToBottom = -1;
            var5.layout.baselineToBaseline = -1;
         }
         break;
      case 5:
         if (var4 != 5) {
            var6 = new StringBuilder();
            var6.append("right to ");
            var6.append(this.sideToString(var4));
            var6.append(" undefined");
            throw new IllegalArgumentException(var6.toString());
         }

         var5.layout.baselineToBaseline = var3;
         var5.layout.bottomToBottom = -1;
         var5.layout.bottomToTop = -1;
         var5.layout.topToTop = -1;
         var5.layout.topToBottom = -1;
         break;
      case 6:
         if (var4 == 6) {
            var5.layout.startToStart = var3;
            var5.layout.startToEnd = -1;
         } else {
            if (var4 != 7) {
               var6 = new StringBuilder();
               var6.append("right to ");
               var6.append(this.sideToString(var4));
               var6.append(" undefined");
               throw new IllegalArgumentException(var6.toString());
            }

            var5.layout.startToEnd = var3;
            var5.layout.startToStart = -1;
         }
         break;
      case 7:
         if (var4 == 7) {
            var5.layout.endToEnd = var3;
            var5.layout.endToStart = -1;
         } else {
            if (var4 != 6) {
               var6 = new StringBuilder();
               var6.append("right to ");
               var6.append(this.sideToString(var4));
               var6.append(" undefined");
               throw new IllegalArgumentException(var6.toString());
            }

            var5.layout.endToStart = var3;
            var5.layout.endToEnd = -1;
         }
         break;
      default:
         var6 = new StringBuilder();
         var6.append(this.sideToString(var2));
         var6.append(" to ");
         var6.append(this.sideToString(var4));
         var6.append(" unknown");
         throw new IllegalArgumentException(var6.toString());
      }

   }

   public void connect(int var1, int var2, int var3, int var4, int var5) {
      if (!this.mConstraints.containsKey(var1)) {
         this.mConstraints.put(var1, new ConstraintSet.Constraint());
      }

      ConstraintSet.Constraint var6 = (ConstraintSet.Constraint)this.mConstraints.get(var1);
      StringBuilder var7;
      switch(var2) {
      case 1:
         if (var4 == 1) {
            var6.layout.leftToLeft = var3;
            var6.layout.leftToRight = -1;
         } else {
            if (var4 != 2) {
               var7 = new StringBuilder();
               var7.append("Left to ");
               var7.append(this.sideToString(var4));
               var7.append(" undefined");
               throw new IllegalArgumentException(var7.toString());
            }

            var6.layout.leftToRight = var3;
            var6.layout.leftToLeft = -1;
         }

         var6.layout.leftMargin = var5;
         break;
      case 2:
         if (var4 == 1) {
            var6.layout.rightToLeft = var3;
            var6.layout.rightToRight = -1;
         } else {
            if (var4 != 2) {
               var7 = new StringBuilder();
               var7.append("right to ");
               var7.append(this.sideToString(var4));
               var7.append(" undefined");
               throw new IllegalArgumentException(var7.toString());
            }

            var6.layout.rightToRight = var3;
            var6.layout.rightToLeft = -1;
         }

         var6.layout.rightMargin = var5;
         break;
      case 3:
         if (var4 == 3) {
            var6.layout.topToTop = var3;
            var6.layout.topToBottom = -1;
            var6.layout.baselineToBaseline = -1;
         } else {
            if (var4 != 4) {
               var7 = new StringBuilder();
               var7.append("right to ");
               var7.append(this.sideToString(var4));
               var7.append(" undefined");
               throw new IllegalArgumentException(var7.toString());
            }

            var6.layout.topToBottom = var3;
            var6.layout.topToTop = -1;
            var6.layout.baselineToBaseline = -1;
         }

         var6.layout.topMargin = var5;
         break;
      case 4:
         if (var4 == 4) {
            var6.layout.bottomToBottom = var3;
            var6.layout.bottomToTop = -1;
            var6.layout.baselineToBaseline = -1;
         } else {
            if (var4 != 3) {
               var7 = new StringBuilder();
               var7.append("right to ");
               var7.append(this.sideToString(var4));
               var7.append(" undefined");
               throw new IllegalArgumentException(var7.toString());
            }

            var6.layout.bottomToTop = var3;
            var6.layout.bottomToBottom = -1;
            var6.layout.baselineToBaseline = -1;
         }

         var6.layout.bottomMargin = var5;
         break;
      case 5:
         if (var4 != 5) {
            var7 = new StringBuilder();
            var7.append("right to ");
            var7.append(this.sideToString(var4));
            var7.append(" undefined");
            throw new IllegalArgumentException(var7.toString());
         }

         var6.layout.baselineToBaseline = var3;
         var6.layout.bottomToBottom = -1;
         var6.layout.bottomToTop = -1;
         var6.layout.topToTop = -1;
         var6.layout.topToBottom = -1;
         break;
      case 6:
         if (var4 == 6) {
            var6.layout.startToStart = var3;
            var6.layout.startToEnd = -1;
         } else {
            if (var4 != 7) {
               var7 = new StringBuilder();
               var7.append("right to ");
               var7.append(this.sideToString(var4));
               var7.append(" undefined");
               throw new IllegalArgumentException(var7.toString());
            }

            var6.layout.startToEnd = var3;
            var6.layout.startToStart = -1;
         }

         var6.layout.startMargin = var5;
         break;
      case 7:
         if (var4 == 7) {
            var6.layout.endToEnd = var3;
            var6.layout.endToStart = -1;
         } else {
            if (var4 != 6) {
               var7 = new StringBuilder();
               var7.append("right to ");
               var7.append(this.sideToString(var4));
               var7.append(" undefined");
               throw new IllegalArgumentException(var7.toString());
            }

            var6.layout.endToStart = var3;
            var6.layout.endToEnd = -1;
         }

         var6.layout.endMargin = var5;
         break;
      default:
         var7 = new StringBuilder();
         var7.append(this.sideToString(var2));
         var7.append(" to ");
         var7.append(this.sideToString(var4));
         var7.append(" unknown");
         throw new IllegalArgumentException(var7.toString());
      }

   }

   public void constrainCircle(int var1, int var2, int var3, float var4) {
      ConstraintSet.Constraint var5 = this.get(var1);
      var5.layout.circleConstraint = var2;
      var5.layout.circleRadius = var3;
      var5.layout.circleAngle = var4;
   }

   public void constrainDefaultHeight(int var1, int var2) {
      this.get(var1).layout.heightDefault = var2;
   }

   public void constrainDefaultWidth(int var1, int var2) {
      this.get(var1).layout.widthDefault = var2;
   }

   public void constrainHeight(int var1, int var2) {
      this.get(var1).layout.mHeight = var2;
   }

   public void constrainMaxHeight(int var1, int var2) {
      this.get(var1).layout.heightMax = var2;
   }

   public void constrainMaxWidth(int var1, int var2) {
      this.get(var1).layout.widthMax = var2;
   }

   public void constrainMinHeight(int var1, int var2) {
      this.get(var1).layout.heightMin = var2;
   }

   public void constrainMinWidth(int var1, int var2) {
      this.get(var1).layout.widthMin = var2;
   }

   public void constrainPercentHeight(int var1, float var2) {
      this.get(var1).layout.heightPercent = var2;
   }

   public void constrainPercentWidth(int var1, float var2) {
      this.get(var1).layout.widthPercent = var2;
   }

   public void constrainWidth(int var1, int var2) {
      this.get(var1).layout.mWidth = var2;
   }

   public void constrainedHeight(int var1, boolean var2) {
      this.get(var1).layout.constrainedHeight = var2;
   }

   public void constrainedWidth(int var1, boolean var2) {
      this.get(var1).layout.constrainedWidth = var2;
   }

   public void create(int var1, int var2) {
      ConstraintSet.Constraint var3 = this.get(var1);
      var3.layout.mIsGuideline = true;
      var3.layout.orientation = var2;
   }

   public void createBarrier(int var1, int var2, int var3, int... var4) {
      ConstraintSet.Constraint var5 = this.get(var1);
      var5.layout.mHelperType = 1;
      var5.layout.mBarrierDirection = var2;
      var5.layout.mBarrierMargin = var3;
      var5.layout.mIsGuideline = false;
      var5.layout.mReferenceIds = var4;
   }

   public void createHorizontalChain(int var1, int var2, int var3, int var4, int[] var5, float[] var6, int var7) {
      this.createHorizontalChain(var1, var2, var3, var4, var5, var6, var7, 1, 2);
   }

   public void createHorizontalChainRtl(int var1, int var2, int var3, int var4, int[] var5, float[] var6, int var7) {
      this.createHorizontalChain(var1, var2, var3, var4, var5, var6, var7, 6, 7);
   }

   public void createVerticalChain(int var1, int var2, int var3, int var4, int[] var5, float[] var6, int var7) {
      if (var5.length >= 2) {
         if (var6 != null && var6.length != var5.length) {
            throw new IllegalArgumentException("must have 2 or more widgets in a chain");
         } else {
            if (var6 != null) {
               this.get(var5[0]).layout.verticalWeight = var6[0];
            }

            this.get(var5[0]).layout.verticalChainStyle = var7;
            this.connect(var5[0], 3, var1, var2, 0);

            for(var1 = 1; var1 < var5.length; ++var1) {
               var2 = var5[var1];
               var7 = var1 - 1;
               this.connect(var2, 3, var5[var7], 4, 0);
               this.connect(var5[var7], 4, var5[var1], 3, 0);
               if (var6 != null) {
                  this.get(var5[var1]).layout.verticalWeight = var6[var1];
               }
            }

            this.connect(var5[var5.length - 1], 4, var3, var4, 0);
         }
      } else {
         throw new IllegalArgumentException("must have 2 or more widgets in a chain");
      }
   }

   public void dump(MotionScene var1, int... var2) {
      Set var3 = this.mConstraints.keySet();
      int var4 = var2.length;
      byte var5 = 0;
      int var7;
      HashSet var11;
      if (var4 != 0) {
         HashSet var6 = new HashSet();
         var7 = var2.length;
         var4 = 0;

         while(true) {
            var11 = var6;
            if (var4 >= var7) {
               break;
            }

            var6.add(var2[var4]);
            ++var4;
         }
      } else {
         var11 = new HashSet(var3);
      }

      PrintStream var9 = System.out;
      StringBuilder var13 = new StringBuilder();
      var13.append(var11.size());
      var13.append(" constraints");
      var9.println(var13.toString());
      StringBuilder var10 = new StringBuilder();
      Integer[] var14 = (Integer[])var11.toArray(new Integer[0]);
      var7 = var14.length;

      for(var4 = var5; var4 < var7; ++var4) {
         Integer var12 = var14[var4];
         ConstraintSet.Constraint var8 = (ConstraintSet.Constraint)this.mConstraints.get(var12);
         var10.append("<Constraint id=");
         var10.append(var12);
         var10.append(" \n");
         var8.layout.dump(var1, var10);
         var10.append("/>\n");
      }

      System.out.println(var10.toString());
   }

   public boolean getApplyElevation(int var1) {
      return this.get(var1).transform.applyElevation;
   }

   public ConstraintSet.Constraint getConstraint(int var1) {
      return this.mConstraints.containsKey(var1) ? (ConstraintSet.Constraint)this.mConstraints.get(var1) : null;
   }

   public HashMap<String, ConstraintAttribute> getCustomAttributeSet() {
      return this.mSavedAttributes;
   }

   public int getHeight(int var1) {
      return this.get(var1).layout.mHeight;
   }

   public int[] getKnownIds() {
      Set var1 = this.mConstraints.keySet();
      int var2 = 0;
      Integer[] var5 = (Integer[])var1.toArray(new Integer[0]);
      int var3 = var5.length;

      int[] var4;
      for(var4 = new int[var3]; var2 < var3; ++var2) {
         var4[var2] = var5[var2];
      }

      return var4;
   }

   public ConstraintSet.Constraint getParameters(int var1) {
      return this.get(var1);
   }

   public int[] getReferencedIds(int var1) {
      ConstraintSet.Constraint var2 = this.get(var1);
      return var2.layout.mReferenceIds == null ? new int[0] : Arrays.copyOf(var2.layout.mReferenceIds, var2.layout.mReferenceIds.length);
   }

   public int getVisibility(int var1) {
      return this.get(var1).propertySet.visibility;
   }

   public int getVisibilityMode(int var1) {
      return this.get(var1).propertySet.mVisibilityMode;
   }

   public int getWidth(int var1) {
      return this.get(var1).layout.mWidth;
   }

   public boolean isForceId() {
      return this.mForceId;
   }

   public void load(Context var1, int var2) {
      XmlResourceParser var3 = var1.getResources().getXml(var2);

      XmlPullParserException var18;
      label75: {
         IOException var10000;
         label61: {
            boolean var10001;
            try {
               var2 = var3.getEventType();
            } catch (XmlPullParserException var14) {
               var18 = var14;
               var10001 = false;
               break label75;
            } catch (IOException var15) {
               var10000 = var15;
               var10001 = false;
               break label61;
            }

            while(true) {
               if (var2 == 1) {
                  return;
               }

               if (var2 != 0) {
                  if (var2 == 2) {
                     ConstraintSet.Constraint var5;
                     try {
                        String var4 = var3.getName();
                        var5 = this.fillFromAttributeList(var1, Xml.asAttributeSet(var3));
                        if (var4.equalsIgnoreCase("Guideline")) {
                           var5.layout.mIsGuideline = true;
                        }
                     } catch (XmlPullParserException var12) {
                        var18 = var12;
                        var10001 = false;
                        break label75;
                     } catch (IOException var13) {
                        var10000 = var13;
                        var10001 = false;
                        break;
                     }

                     try {
                        this.mConstraints.put(var5.mViewId, var5);
                     } catch (XmlPullParserException var10) {
                        var18 = var10;
                        var10001 = false;
                        break label75;
                     } catch (IOException var11) {
                        var10000 = var11;
                        var10001 = false;
                        break;
                     }
                  }
               } else {
                  try {
                     var3.getName();
                  } catch (XmlPullParserException var8) {
                     var18 = var8;
                     var10001 = false;
                     break label75;
                  } catch (IOException var9) {
                     var10000 = var9;
                     var10001 = false;
                     break;
                  }
               }

               try {
                  var2 = var3.next();
               } catch (XmlPullParserException var6) {
                  var18 = var6;
                  var10001 = false;
                  break label75;
               } catch (IOException var7) {
                  var10000 = var7;
                  var10001 = false;
                  break;
               }
            }
         }

         IOException var16 = var10000;
         var16.printStackTrace();
         return;
      }

      XmlPullParserException var17 = var18;
      var17.printStackTrace();
   }

   public void load(Context var1, XmlPullParser var2) {
      XmlPullParserException var71;
      label346: {
         IOException var10000;
         label320: {
            int var3;
            boolean var10001;
            try {
               var3 = var2.getEventType();
            } catch (XmlPullParserException var41) {
               var71 = var41;
               var10001 = false;
               break label346;
            } catch (IOException var42) {
               var10000 = var42;
               var10001 = false;
               break label320;
            }

            ConstraintSet.Constraint var4 = null;

            label315:
            while(true) {
               if (var3 == 1) {
                  return;
               }

               ConstraintSet.Constraint var68;
               if (var3 == 0) {
                  try {
                     var2.getName();
                  } catch (XmlPullParserException var23) {
                     var71 = var23;
                     var10001 = false;
                     break label346;
                  } catch (IOException var24) {
                     var10000 = var24;
                     var10001 = false;
                     break;
                  }

                  var68 = var4;
               } else {
                  byte var5 = 3;
                  if (var3 != 2) {
                     if (var3 != 3) {
                        var68 = var4;
                     } else {
                        label338: {
                           String var7;
                           try {
                              var7 = var2.getName();
                              if ("ConstraintSet".equals(var7)) {
                                 return;
                              }
                           } catch (XmlPullParserException var61) {
                              var71 = var61;
                              var10001 = false;
                              break label346;
                           } catch (IOException var62) {
                              var10000 = var62;
                              var10001 = false;
                              break;
                           }

                           var68 = var4;

                           try {
                              if (!var7.equalsIgnoreCase("Constraint")) {
                                 break label338;
                              }

                              this.mConstraints.put(var4.mViewId, var4);
                           } catch (XmlPullParserException var59) {
                              var71 = var59;
                              var10001 = false;
                              break label346;
                           } catch (IOException var60) {
                              var10000 = var60;
                              var10001 = false;
                              break;
                           }

                           var68 = null;
                        }
                     }
                  } else {
                     byte var67;
                     label291: {
                        label290: {
                           label324: {
                              String var6;
                              label325: {
                                 label326: {
                                    label327: {
                                       label328: {
                                          label329: {
                                             label330: {
                                                label282: {
                                                   try {
                                                      var6 = var2.getName();
                                                      switch(var6.hashCode()) {
                                                      case -2025855158:
                                                         break label282;
                                                      case -1984451626:
                                                         break label329;
                                                      case -1269513683:
                                                         break label328;
                                                      case -1238332596:
                                                         break label327;
                                                      case -71750448:
                                                         break label326;
                                                      case 1331510167:
                                                         break label325;
                                                      case 1791837707:
                                                         break label330;
                                                      case 1803088381:
                                                         break;
                                                      default:
                                                         break label324;
                                                      }
                                                   } catch (XmlPullParserException var57) {
                                                      var71 = var57;
                                                      var10001 = false;
                                                      break label346;
                                                   } catch (IOException var58) {
                                                      var10000 = var58;
                                                      var10001 = false;
                                                      break;
                                                   }

                                                   try {
                                                      if (var6.equals("Constraint")) {
                                                         break label290;
                                                      }
                                                      break label324;
                                                   } catch (XmlPullParserException var43) {
                                                      var71 = var43;
                                                      var10001 = false;
                                                      break label346;
                                                   } catch (IOException var44) {
                                                      var10000 = var44;
                                                      var10001 = false;
                                                      break;
                                                   }
                                                }

                                                boolean var8;
                                                try {
                                                   var8 = var6.equals("Layout");
                                                } catch (XmlPullParserException var19) {
                                                   var71 = var19;
                                                   var10001 = false;
                                                   break label346;
                                                } catch (IOException var20) {
                                                   var10000 = var20;
                                                   var10001 = false;
                                                   break;
                                                }

                                                if (var8) {
                                                   var67 = 5;
                                                   break label291;
                                                }
                                                break label324;
                                             }

                                             try {
                                                if (!var6.equals("CustomAttribute")) {
                                                   break label324;
                                                }
                                             } catch (XmlPullParserException var45) {
                                                var71 = var45;
                                                var10001 = false;
                                                break label346;
                                             } catch (IOException var46) {
                                                var10000 = var46;
                                                var10001 = false;
                                                break;
                                             }

                                             var67 = 7;
                                             break label291;
                                          }

                                          try {
                                             if (!var6.equals("Motion")) {
                                                break label324;
                                             }
                                          } catch (XmlPullParserException var55) {
                                             var71 = var55;
                                             var10001 = false;
                                             break label346;
                                          } catch (IOException var56) {
                                             var10000 = var56;
                                             var10001 = false;
                                             break;
                                          }

                                          var67 = 6;
                                          break label291;
                                       }

                                       try {
                                          if (!var6.equals("PropertySet")) {
                                             break label324;
                                          }
                                       } catch (XmlPullParserException var53) {
                                          var71 = var53;
                                          var10001 = false;
                                          break label346;
                                       } catch (IOException var54) {
                                          var10000 = var54;
                                          var10001 = false;
                                          break;
                                       }

                                       var67 = var5;
                                       break label291;
                                    }

                                    try {
                                       if (!var6.equals("Transform")) {
                                          break label324;
                                       }
                                    } catch (XmlPullParserException var51) {
                                       var71 = var51;
                                       var10001 = false;
                                       break label346;
                                    } catch (IOException var52) {
                                       var10000 = var52;
                                       var10001 = false;
                                       break;
                                    }

                                    var67 = 4;
                                    break label291;
                                 }

                                 try {
                                    if (!var6.equals("Guideline")) {
                                       break label324;
                                    }
                                 } catch (XmlPullParserException var49) {
                                    var71 = var49;
                                    var10001 = false;
                                    break label346;
                                 } catch (IOException var50) {
                                    var10000 = var50;
                                    var10001 = false;
                                    break;
                                 }

                                 var67 = 1;
                                 break label291;
                              }

                              try {
                                 if (!var6.equals("Barrier")) {
                                    break label324;
                                 }
                              } catch (XmlPullParserException var47) {
                                 var71 = var47;
                                 var10001 = false;
                                 break label346;
                              } catch (IOException var48) {
                                 var10000 = var48;
                                 var10001 = false;
                                 break;
                              }

                              var67 = 2;
                              break label291;
                           }

                           var67 = -1;
                           break label291;
                        }

                        var67 = 0;
                     }

                     RuntimeException var63;
                     StringBuilder var64;
                     StringBuilder var69;
                     RuntimeException var70;
                     switch(var67) {
                     case 0:
                        try {
                           var68 = this.fillFromAttributeList(var1, Xml.asAttributeSet(var2));
                           break;
                        } catch (XmlPullParserException var25) {
                           var71 = var25;
                           var10001 = false;
                           break label346;
                        } catch (IOException var26) {
                           var10000 = var26;
                           var10001 = false;
                           break label315;
                        }
                     case 1:
                        try {
                           var68 = this.fillFromAttributeList(var1, Xml.asAttributeSet(var2));
                           var68.layout.mIsGuideline = true;
                           var68.layout.mApply = true;
                           break;
                        } catch (XmlPullParserException var27) {
                           var71 = var27;
                           var10001 = false;
                           break label346;
                        } catch (IOException var28) {
                           var10000 = var28;
                           var10001 = false;
                           break label315;
                        }
                     case 2:
                        try {
                           var68 = this.fillFromAttributeList(var1, Xml.asAttributeSet(var2));
                           var68.layout.mHelperType = 1;
                           break;
                        } catch (XmlPullParserException var29) {
                           var71 = var29;
                           var10001 = false;
                           break label346;
                        } catch (IOException var30) {
                           var10000 = var30;
                           var10001 = false;
                           break label315;
                        }
                     case 3:
                        if (var4 == null) {
                           try {
                              var69 = new StringBuilder();
                              var69.append("XML parser error must be within a Constraint ");
                              var69.append(var2.getLineNumber());
                              var63 = new RuntimeException(var69.toString());
                              throw var63;
                           } catch (XmlPullParserException var17) {
                              var71 = var17;
                              var10001 = false;
                              break label346;
                           } catch (IOException var18) {
                              var10000 = var18;
                              var10001 = false;
                              break label315;
                           }
                        }

                        try {
                           var4.propertySet.fillFromAttributeList(var1, Xml.asAttributeSet(var2));
                        } catch (XmlPullParserException var31) {
                           var71 = var31;
                           var10001 = false;
                           break label346;
                        } catch (IOException var32) {
                           var10000 = var32;
                           var10001 = false;
                           break label315;
                        }

                        var68 = var4;
                        break;
                     case 4:
                        if (var4 == null) {
                           try {
                              var64 = new StringBuilder();
                              var64.append("XML parser error must be within a Constraint ");
                              var64.append(var2.getLineNumber());
                              var70 = new RuntimeException(var64.toString());
                              throw var70;
                           } catch (XmlPullParserException var15) {
                              var71 = var15;
                              var10001 = false;
                              break label346;
                           } catch (IOException var16) {
                              var10000 = var16;
                              var10001 = false;
                              break label315;
                           }
                        }

                        try {
                           var4.transform.fillFromAttributeList(var1, Xml.asAttributeSet(var2));
                        } catch (XmlPullParserException var33) {
                           var71 = var33;
                           var10001 = false;
                           break label346;
                        } catch (IOException var34) {
                           var10000 = var34;
                           var10001 = false;
                           break label315;
                        }

                        var68 = var4;
                        break;
                     case 5:
                        if (var4 == null) {
                           try {
                              var64 = new StringBuilder();
                              var64.append("XML parser error must be within a Constraint ");
                              var64.append(var2.getLineNumber());
                              var70 = new RuntimeException(var64.toString());
                              throw var70;
                           } catch (XmlPullParserException var13) {
                              var71 = var13;
                              var10001 = false;
                              break label346;
                           } catch (IOException var14) {
                              var10000 = var14;
                              var10001 = false;
                              break label315;
                           }
                        }

                        try {
                           var4.layout.fillFromAttributeList(var1, Xml.asAttributeSet(var2));
                        } catch (XmlPullParserException var35) {
                           var71 = var35;
                           var10001 = false;
                           break label346;
                        } catch (IOException var36) {
                           var10000 = var36;
                           var10001 = false;
                           break label315;
                        }

                        var68 = var4;
                        break;
                     case 6:
                        if (var4 == null) {
                           try {
                              var69 = new StringBuilder();
                              var69.append("XML parser error must be within a Constraint ");
                              var69.append(var2.getLineNumber());
                              var63 = new RuntimeException(var69.toString());
                              throw var63;
                           } catch (XmlPullParserException var11) {
                              var71 = var11;
                              var10001 = false;
                              break label346;
                           } catch (IOException var12) {
                              var10000 = var12;
                              var10001 = false;
                              break label315;
                           }
                        }

                        try {
                           var4.motion.fillFromAttributeList(var1, Xml.asAttributeSet(var2));
                        } catch (XmlPullParserException var37) {
                           var71 = var37;
                           var10001 = false;
                           break label346;
                        } catch (IOException var38) {
                           var10000 = var38;
                           var10001 = false;
                           break label315;
                        }

                        var68 = var4;
                        break;
                     case 7:
                        if (var4 == null) {
                           try {
                              var69 = new StringBuilder();
                              var69.append("XML parser error must be within a Constraint ");
                              var69.append(var2.getLineNumber());
                              var63 = new RuntimeException(var69.toString());
                              throw var63;
                           } catch (XmlPullParserException var9) {
                              var71 = var9;
                              var10001 = false;
                              break label346;
                           } catch (IOException var10) {
                              var10000 = var10;
                              var10001 = false;
                              break label315;
                           }
                        }

                        try {
                           ConstraintAttribute.parse(var1, var2, var4.mCustomConstraints);
                        } catch (XmlPullParserException var39) {
                           var71 = var39;
                           var10001 = false;
                           break label346;
                        } catch (IOException var40) {
                           var10000 = var40;
                           var10001 = false;
                           break label315;
                        }

                        var68 = var4;
                        break;
                     default:
                        var68 = var4;
                     }
                  }
               }

               try {
                  var3 = var2.next();
               } catch (XmlPullParserException var21) {
                  var71 = var21;
                  var10001 = false;
                  break label346;
               } catch (IOException var22) {
                  var10000 = var22;
                  var10001 = false;
                  break;
               }

               var4 = var68;
            }
         }

         IOException var65 = var10000;
         var65.printStackTrace();
         return;
      }

      XmlPullParserException var66 = var71;
      var66.printStackTrace();
   }

   public void parseColorAttributes(ConstraintSet.Constraint var1, String var2) {
      String[] var5 = var2.split(",");

      for(int var3 = 0; var3 < var5.length; ++var3) {
         String[] var4 = var5[var3].split("=");
         if (var4.length != 2) {
            StringBuilder var6 = new StringBuilder();
            var6.append(" Unable to parse ");
            var6.append(var5[var3]);
            Log.w("ConstraintSet", var6.toString());
         } else {
            var1.setColorValue(var4[0], Color.parseColor(var4[1]));
         }
      }

   }

   public void parseFloatAttributes(ConstraintSet.Constraint var1, String var2) {
      String[] var5 = var2.split(",");

      for(int var3 = 0; var3 < var5.length; ++var3) {
         String[] var4 = var5[var3].split("=");
         if (var4.length != 2) {
            StringBuilder var6 = new StringBuilder();
            var6.append(" Unable to parse ");
            var6.append(var5[var3]);
            Log.w("ConstraintSet", var6.toString());
         } else {
            var1.setFloatValue(var4[0], Float.parseFloat(var4[1]));
         }
      }

   }

   public void parseIntAttributes(ConstraintSet.Constraint var1, String var2) {
      String[] var5 = var2.split(",");

      for(int var3 = 0; var3 < var5.length; ++var3) {
         String[] var4 = var5[var3].split("=");
         if (var4.length != 2) {
            StringBuilder var6 = new StringBuilder();
            var6.append(" Unable to parse ");
            var6.append(var5[var3]);
            Log.w("ConstraintSet", var6.toString());
         } else {
            var1.setFloatValue(var4[0], (float)Integer.decode(var4[1]));
         }
      }

   }

   public void parseStringAttributes(ConstraintSet.Constraint var1, String var2) {
      String[] var3 = splitString(var2);

      for(int var4 = 0; var4 < var3.length; ++var4) {
         String[] var5 = var3[var4].split("=");
         StringBuilder var6 = new StringBuilder();
         var6.append(" Unable to parse ");
         var6.append(var3[var4]);
         Log.w("ConstraintSet", var6.toString());
         var1.setStringValue(var5[0], var5[1]);
      }

   }

   public void readFallback(ConstraintLayout var1) {
      int var2 = var1.getChildCount();

      for(int var3 = 0; var3 < var2; ++var3) {
         View var4 = var1.getChildAt(var3);
         ConstraintLayout.LayoutParams var5 = (ConstraintLayout.LayoutParams)var4.getLayoutParams();
         int var6 = var4.getId();
         if (this.mForceId && var6 == -1) {
            throw new RuntimeException("All children of ConstraintLayout must have ids to use ConstraintSet");
         }

         if (!this.mConstraints.containsKey(var6)) {
            this.mConstraints.put(var6, new ConstraintSet.Constraint());
         }

         ConstraintSet.Constraint var7 = (ConstraintSet.Constraint)this.mConstraints.get(var6);
         if (!var7.layout.mApply) {
            var7.fillFrom(var6, var5);
            if (var4 instanceof ConstraintHelper) {
               var7.layout.mReferenceIds = ((ConstraintHelper)var4).getReferencedIds();
               if (var4 instanceof Barrier) {
                  Barrier var10 = (Barrier)var4;
                  var7.layout.mBarrierAllowsGoneWidgets = var10.allowsGoneWidget();
                  var7.layout.mBarrierDirection = var10.getType();
                  var7.layout.mBarrierMargin = var10.getMargin();
               }
            }

            var7.layout.mApply = true;
         }

         if (!var7.propertySet.mApply) {
            var7.propertySet.visibility = var4.getVisibility();
            var7.propertySet.alpha = var4.getAlpha();
            var7.propertySet.mApply = true;
         }

         if (VERSION.SDK_INT >= 17 && !var7.transform.mApply) {
            var7.transform.mApply = true;
            var7.transform.rotation = var4.getRotation();
            var7.transform.rotationX = var4.getRotationX();
            var7.transform.rotationY = var4.getRotationY();
            var7.transform.scaleX = var4.getScaleX();
            var7.transform.scaleY = var4.getScaleY();
            float var8 = var4.getPivotX();
            float var9 = var4.getPivotY();
            if ((double)var8 != 0.0D || (double)var9 != 0.0D) {
               var7.transform.transformPivotX = var8;
               var7.transform.transformPivotY = var9;
            }

            var7.transform.translationX = var4.getTranslationX();
            var7.transform.translationY = var4.getTranslationY();
            if (VERSION.SDK_INT >= 21) {
               var7.transform.translationZ = var4.getTranslationZ();
               if (var7.transform.applyElevation) {
                  var7.transform.elevation = var4.getElevation();
               }
            }
         }
      }

   }

   public void readFallback(ConstraintSet var1) {
      Iterator var2 = var1.mConstraints.keySet().iterator();

      while(var2.hasNext()) {
         Integer var3 = (Integer)var2.next();
         int var4 = var3;
         ConstraintSet.Constraint var5 = (ConstraintSet.Constraint)var1.mConstraints.get(var3);
         if (!this.mConstraints.containsKey(var4)) {
            this.mConstraints.put(var4, new ConstraintSet.Constraint());
         }

         ConstraintSet.Constraint var6 = (ConstraintSet.Constraint)this.mConstraints.get(var4);
         if (!var6.layout.mApply) {
            var6.layout.copyFrom(var5.layout);
         }

         if (!var6.propertySet.mApply) {
            var6.propertySet.copyFrom(var5.propertySet);
         }

         if (!var6.transform.mApply) {
            var6.transform.copyFrom(var5.transform);
         }

         if (!var6.motion.mApply) {
            var6.motion.copyFrom(var5.motion);
         }

         Iterator var8 = var5.mCustomConstraints.keySet().iterator();

         while(var8.hasNext()) {
            String var7 = (String)var8.next();
            if (!var6.mCustomConstraints.containsKey(var7)) {
               var6.mCustomConstraints.put(var7, var5.mCustomConstraints.get(var7));
            }
         }
      }

   }

   public void removeAttribute(String var1) {
      this.mSavedAttributes.remove(var1);
   }

   public void removeFromHorizontalChain(int var1) {
      if (this.mConstraints.containsKey(var1)) {
         ConstraintSet.Constraint var2 = (ConstraintSet.Constraint)this.mConstraints.get(var1);
         int var3 = var2.layout.leftToRight;
         int var4 = var2.layout.rightToLeft;
         if (var3 == -1 && var4 == -1) {
            int var5 = var2.layout.startToEnd;
            var4 = var2.layout.endToStart;
            if (var5 != -1 || var4 != -1) {
               if (var5 != -1 && var4 != -1) {
                  this.connect(var5, 7, var4, 6, 0);
                  this.connect(var4, 6, var3, 7, 0);
               } else if (var3 != -1 || var4 != -1) {
                  if (var2.layout.rightToRight != -1) {
                     this.connect(var3, 7, var2.layout.rightToRight, 7, 0);
                  } else if (var2.layout.leftToLeft != -1) {
                     this.connect(var4, 6, var2.layout.leftToLeft, 6, 0);
                  }
               }
            }

            this.clear(var1, 6);
            this.clear(var1, 7);
         } else {
            if (var3 != -1 && var4 != -1) {
               this.connect(var3, 2, var4, 1, 0);
               this.connect(var4, 1, var3, 2, 0);
            } else if (var3 != -1 || var4 != -1) {
               if (var2.layout.rightToRight != -1) {
                  this.connect(var3, 2, var2.layout.rightToRight, 2, 0);
               } else if (var2.layout.leftToLeft != -1) {
                  this.connect(var4, 1, var2.layout.leftToLeft, 1, 0);
               }
            }

            this.clear(var1, 1);
            this.clear(var1, 2);
         }
      }

   }

   public void removeFromVerticalChain(int var1) {
      if (this.mConstraints.containsKey(var1)) {
         ConstraintSet.Constraint var2 = (ConstraintSet.Constraint)this.mConstraints.get(var1);
         int var3 = var2.layout.topToBottom;
         int var4 = var2.layout.bottomToTop;
         if (var3 != -1 || var4 != -1) {
            if (var3 != -1 && var4 != -1) {
               this.connect(var3, 4, var4, 3, 0);
               this.connect(var4, 3, var3, 4, 0);
            } else if (var3 != -1 || var4 != -1) {
               if (var2.layout.bottomToBottom != -1) {
                  this.connect(var3, 4, var2.layout.bottomToBottom, 4, 0);
               } else if (var2.layout.topToTop != -1) {
                  this.connect(var4, 3, var2.layout.topToTop, 3, 0);
               }
            }
         }
      }

      this.clear(var1, 3);
      this.clear(var1, 4);
   }

   public void setAlpha(int var1, float var2) {
      this.get(var1).propertySet.alpha = var2;
   }

   public void setApplyElevation(int var1, boolean var2) {
      if (VERSION.SDK_INT >= 21) {
         this.get(var1).transform.applyElevation = var2;
      }

   }

   public void setBarrierType(int var1, int var2) {
      this.get(var1).layout.mHelperType = var2;
   }

   public void setColorValue(int var1, String var2, int var3) {
      this.get(var1).setColorValue(var2, var3);
   }

   public void setDimensionRatio(int var1, String var2) {
      this.get(var1).layout.dimensionRatio = var2;
   }

   public void setEditorAbsoluteX(int var1, int var2) {
      this.get(var1).layout.editorAbsoluteX = var2;
   }

   public void setEditorAbsoluteY(int var1, int var2) {
      this.get(var1).layout.editorAbsoluteY = var2;
   }

   public void setElevation(int var1, float var2) {
      if (VERSION.SDK_INT >= 21) {
         this.get(var1).transform.elevation = var2;
         this.get(var1).transform.applyElevation = true;
      }

   }

   public void setFloatValue(int var1, String var2, float var3) {
      this.get(var1).setFloatValue(var2, var3);
   }

   public void setForceId(boolean var1) {
      this.mForceId = var1;
   }

   public void setGoneMargin(int var1, int var2, int var3) {
      ConstraintSet.Constraint var4 = this.get(var1);
      switch(var2) {
      case 1:
         var4.layout.goneLeftMargin = var3;
         break;
      case 2:
         var4.layout.goneRightMargin = var3;
         break;
      case 3:
         var4.layout.goneTopMargin = var3;
         break;
      case 4:
         var4.layout.goneBottomMargin = var3;
         break;
      case 5:
         throw new IllegalArgumentException("baseline does not support margins");
      case 6:
         var4.layout.goneStartMargin = var3;
         break;
      case 7:
         var4.layout.goneEndMargin = var3;
         break;
      default:
         throw new IllegalArgumentException("unknown constraint");
      }

   }

   public void setGuidelineBegin(int var1, int var2) {
      this.get(var1).layout.guideBegin = var2;
      this.get(var1).layout.guideEnd = -1;
      this.get(var1).layout.guidePercent = -1.0F;
   }

   public void setGuidelineEnd(int var1, int var2) {
      this.get(var1).layout.guideEnd = var2;
      this.get(var1).layout.guideBegin = -1;
      this.get(var1).layout.guidePercent = -1.0F;
   }

   public void setGuidelinePercent(int var1, float var2) {
      this.get(var1).layout.guidePercent = var2;
      this.get(var1).layout.guideEnd = -1;
      this.get(var1).layout.guideBegin = -1;
   }

   public void setHorizontalBias(int var1, float var2) {
      this.get(var1).layout.horizontalBias = var2;
   }

   public void setHorizontalChainStyle(int var1, int var2) {
      this.get(var1).layout.horizontalChainStyle = var2;
   }

   public void setHorizontalWeight(int var1, float var2) {
      this.get(var1).layout.horizontalWeight = var2;
   }

   public void setIntValue(int var1, String var2, int var3) {
      this.get(var1).setIntValue(var2, var3);
   }

   public void setMargin(int var1, int var2, int var3) {
      ConstraintSet.Constraint var4 = this.get(var1);
      switch(var2) {
      case 1:
         var4.layout.leftMargin = var3;
         break;
      case 2:
         var4.layout.rightMargin = var3;
         break;
      case 3:
         var4.layout.topMargin = var3;
         break;
      case 4:
         var4.layout.bottomMargin = var3;
         break;
      case 5:
         throw new IllegalArgumentException("baseline does not support margins");
      case 6:
         var4.layout.startMargin = var3;
         break;
      case 7:
         var4.layout.endMargin = var3;
         break;
      default:
         throw new IllegalArgumentException("unknown constraint");
      }

   }

   public void setReferencedIds(int var1, int... var2) {
      this.get(var1).layout.mReferenceIds = var2;
   }

   public void setRotation(int var1, float var2) {
      this.get(var1).transform.rotation = var2;
   }

   public void setRotationX(int var1, float var2) {
      this.get(var1).transform.rotationX = var2;
   }

   public void setRotationY(int var1, float var2) {
      this.get(var1).transform.rotationY = var2;
   }

   public void setScaleX(int var1, float var2) {
      this.get(var1).transform.scaleX = var2;
   }

   public void setScaleY(int var1, float var2) {
      this.get(var1).transform.scaleY = var2;
   }

   public void setStringValue(int var1, String var2, String var3) {
      this.get(var1).setStringValue(var2, var3);
   }

   public void setTransformPivot(int var1, float var2, float var3) {
      ConstraintSet.Constraint var4 = this.get(var1);
      var4.transform.transformPivotY = var3;
      var4.transform.transformPivotX = var2;
   }

   public void setTransformPivotX(int var1, float var2) {
      this.get(var1).transform.transformPivotX = var2;
   }

   public void setTransformPivotY(int var1, float var2) {
      this.get(var1).transform.transformPivotY = var2;
   }

   public void setTranslation(int var1, float var2, float var3) {
      ConstraintSet.Constraint var4 = this.get(var1);
      var4.transform.translationX = var2;
      var4.transform.translationY = var3;
   }

   public void setTranslationX(int var1, float var2) {
      this.get(var1).transform.translationX = var2;
   }

   public void setTranslationY(int var1, float var2) {
      this.get(var1).transform.translationY = var2;
   }

   public void setTranslationZ(int var1, float var2) {
      if (VERSION.SDK_INT >= 21) {
         this.get(var1).transform.translationZ = var2;
      }

   }

   public void setValidateOnParse(boolean var1) {
      this.mValidate = var1;
   }

   public void setVerticalBias(int var1, float var2) {
      this.get(var1).layout.verticalBias = var2;
   }

   public void setVerticalChainStyle(int var1, int var2) {
      this.get(var1).layout.verticalChainStyle = var2;
   }

   public void setVerticalWeight(int var1, float var2) {
      this.get(var1).layout.verticalWeight = var2;
   }

   public void setVisibility(int var1, int var2) {
      this.get(var1).propertySet.visibility = var2;
   }

   public void setVisibilityMode(int var1, int var2) {
      this.get(var1).propertySet.mVisibilityMode = var2;
   }

   public static class Constraint {
      public final ConstraintSet.Layout layout = new ConstraintSet.Layout();
      public HashMap<String, ConstraintAttribute> mCustomConstraints = new HashMap();
      int mViewId;
      public final ConstraintSet.Motion motion = new ConstraintSet.Motion();
      public final ConstraintSet.PropertySet propertySet = new ConstraintSet.PropertySet();
      public final ConstraintSet.Transform transform = new ConstraintSet.Transform();

      private void fillFrom(int var1, ConstraintLayout.LayoutParams var2) {
         this.mViewId = var1;
         this.layout.leftToLeft = var2.leftToLeft;
         this.layout.leftToRight = var2.leftToRight;
         this.layout.rightToLeft = var2.rightToLeft;
         this.layout.rightToRight = var2.rightToRight;
         this.layout.topToTop = var2.topToTop;
         this.layout.topToBottom = var2.topToBottom;
         this.layout.bottomToTop = var2.bottomToTop;
         this.layout.bottomToBottom = var2.bottomToBottom;
         this.layout.baselineToBaseline = var2.baselineToBaseline;
         this.layout.startToEnd = var2.startToEnd;
         this.layout.startToStart = var2.startToStart;
         this.layout.endToStart = var2.endToStart;
         this.layout.endToEnd = var2.endToEnd;
         this.layout.horizontalBias = var2.horizontalBias;
         this.layout.verticalBias = var2.verticalBias;
         this.layout.dimensionRatio = var2.dimensionRatio;
         this.layout.circleConstraint = var2.circleConstraint;
         this.layout.circleRadius = var2.circleRadius;
         this.layout.circleAngle = var2.circleAngle;
         this.layout.editorAbsoluteX = var2.editorAbsoluteX;
         this.layout.editorAbsoluteY = var2.editorAbsoluteY;
         this.layout.orientation = var2.orientation;
         this.layout.guidePercent = var2.guidePercent;
         this.layout.guideBegin = var2.guideBegin;
         this.layout.guideEnd = var2.guideEnd;
         this.layout.mWidth = var2.width;
         this.layout.mHeight = var2.height;
         this.layout.leftMargin = var2.leftMargin;
         this.layout.rightMargin = var2.rightMargin;
         this.layout.topMargin = var2.topMargin;
         this.layout.bottomMargin = var2.bottomMargin;
         this.layout.verticalWeight = var2.verticalWeight;
         this.layout.horizontalWeight = var2.horizontalWeight;
         this.layout.verticalChainStyle = var2.verticalChainStyle;
         this.layout.horizontalChainStyle = var2.horizontalChainStyle;
         this.layout.constrainedWidth = var2.constrainedWidth;
         this.layout.constrainedHeight = var2.constrainedHeight;
         this.layout.widthDefault = var2.matchConstraintDefaultWidth;
         this.layout.heightDefault = var2.matchConstraintDefaultHeight;
         this.layout.widthMax = var2.matchConstraintMaxWidth;
         this.layout.heightMax = var2.matchConstraintMaxHeight;
         this.layout.widthMin = var2.matchConstraintMinWidth;
         this.layout.heightMin = var2.matchConstraintMinHeight;
         this.layout.widthPercent = var2.matchConstraintPercentWidth;
         this.layout.heightPercent = var2.matchConstraintPercentHeight;
         this.layout.mConstraintTag = var2.constraintTag;
         this.layout.goneTopMargin = var2.goneTopMargin;
         this.layout.goneBottomMargin = var2.goneBottomMargin;
         this.layout.goneLeftMargin = var2.goneLeftMargin;
         this.layout.goneRightMargin = var2.goneRightMargin;
         this.layout.goneStartMargin = var2.goneStartMargin;
         this.layout.goneEndMargin = var2.goneEndMargin;
         if (VERSION.SDK_INT >= 17) {
            this.layout.endMargin = var2.getMarginEnd();
            this.layout.startMargin = var2.getMarginStart();
         }

      }

      private void fillFromConstraints(int var1, Constraints.LayoutParams var2) {
         this.fillFrom(var1, var2);
         this.propertySet.alpha = var2.alpha;
         this.transform.rotation = var2.rotation;
         this.transform.rotationX = var2.rotationX;
         this.transform.rotationY = var2.rotationY;
         this.transform.scaleX = var2.scaleX;
         this.transform.scaleY = var2.scaleY;
         this.transform.transformPivotX = var2.transformPivotX;
         this.transform.transformPivotY = var2.transformPivotY;
         this.transform.translationX = var2.translationX;
         this.transform.translationY = var2.translationY;
         this.transform.translationZ = var2.translationZ;
         this.transform.elevation = var2.elevation;
         this.transform.applyElevation = var2.applyElevation;
      }

      private void fillFromConstraints(ConstraintHelper var1, int var2, Constraints.LayoutParams var3) {
         this.fillFromConstraints(var2, var3);
         if (var1 instanceof Barrier) {
            this.layout.mHelperType = 1;
            Barrier var4 = (Barrier)var1;
            this.layout.mBarrierDirection = var4.getType();
            this.layout.mReferenceIds = var4.getReferencedIds();
            this.layout.mBarrierMargin = var4.getMargin();
         }

      }

      private ConstraintAttribute get(String var1, ConstraintAttribute.AttributeType var2) {
         ConstraintAttribute var3;
         if (this.mCustomConstraints.containsKey(var1)) {
            var3 = (ConstraintAttribute)this.mCustomConstraints.get(var1);
            if (var3.getType() != var2) {
               StringBuilder var4 = new StringBuilder();
               var4.append("ConstraintAttribute is already a ");
               var4.append(var3.getType().name());
               throw new IllegalArgumentException(var4.toString());
            }
         } else {
            ConstraintAttribute var5 = new ConstraintAttribute(var1, var2);
            this.mCustomConstraints.put(var1, var5);
            var3 = var5;
         }

         return var3;
      }

      private void setColorValue(String var1, int var2) {
         this.get(var1, ConstraintAttribute.AttributeType.COLOR_TYPE).setColorValue(var2);
      }

      private void setFloatValue(String var1, float var2) {
         this.get(var1, ConstraintAttribute.AttributeType.FLOAT_TYPE).setFloatValue(var2);
      }

      private void setIntValue(String var1, int var2) {
         this.get(var1, ConstraintAttribute.AttributeType.INT_TYPE).setIntValue(var2);
      }

      private void setStringValue(String var1, String var2) {
         this.get(var1, ConstraintAttribute.AttributeType.STRING_TYPE).setStringValue(var2);
      }

      public void applyTo(ConstraintLayout.LayoutParams var1) {
         var1.leftToLeft = this.layout.leftToLeft;
         var1.leftToRight = this.layout.leftToRight;
         var1.rightToLeft = this.layout.rightToLeft;
         var1.rightToRight = this.layout.rightToRight;
         var1.topToTop = this.layout.topToTop;
         var1.topToBottom = this.layout.topToBottom;
         var1.bottomToTop = this.layout.bottomToTop;
         var1.bottomToBottom = this.layout.bottomToBottom;
         var1.baselineToBaseline = this.layout.baselineToBaseline;
         var1.startToEnd = this.layout.startToEnd;
         var1.startToStart = this.layout.startToStart;
         var1.endToStart = this.layout.endToStart;
         var1.endToEnd = this.layout.endToEnd;
         var1.leftMargin = this.layout.leftMargin;
         var1.rightMargin = this.layout.rightMargin;
         var1.topMargin = this.layout.topMargin;
         var1.bottomMargin = this.layout.bottomMargin;
         var1.goneStartMargin = this.layout.goneStartMargin;
         var1.goneEndMargin = this.layout.goneEndMargin;
         var1.goneTopMargin = this.layout.goneTopMargin;
         var1.goneBottomMargin = this.layout.goneBottomMargin;
         var1.horizontalBias = this.layout.horizontalBias;
         var1.verticalBias = this.layout.verticalBias;
         var1.circleConstraint = this.layout.circleConstraint;
         var1.circleRadius = this.layout.circleRadius;
         var1.circleAngle = this.layout.circleAngle;
         var1.dimensionRatio = this.layout.dimensionRatio;
         var1.editorAbsoluteX = this.layout.editorAbsoluteX;
         var1.editorAbsoluteY = this.layout.editorAbsoluteY;
         var1.verticalWeight = this.layout.verticalWeight;
         var1.horizontalWeight = this.layout.horizontalWeight;
         var1.verticalChainStyle = this.layout.verticalChainStyle;
         var1.horizontalChainStyle = this.layout.horizontalChainStyle;
         var1.constrainedWidth = this.layout.constrainedWidth;
         var1.constrainedHeight = this.layout.constrainedHeight;
         var1.matchConstraintDefaultWidth = this.layout.widthDefault;
         var1.matchConstraintDefaultHeight = this.layout.heightDefault;
         var1.matchConstraintMaxWidth = this.layout.widthMax;
         var1.matchConstraintMaxHeight = this.layout.heightMax;
         var1.matchConstraintMinWidth = this.layout.widthMin;
         var1.matchConstraintMinHeight = this.layout.heightMin;
         var1.matchConstraintPercentWidth = this.layout.widthPercent;
         var1.matchConstraintPercentHeight = this.layout.heightPercent;
         var1.orientation = this.layout.orientation;
         var1.guidePercent = this.layout.guidePercent;
         var1.guideBegin = this.layout.guideBegin;
         var1.guideEnd = this.layout.guideEnd;
         var1.width = this.layout.mWidth;
         var1.height = this.layout.mHeight;
         if (this.layout.mConstraintTag != null) {
            var1.constraintTag = this.layout.mConstraintTag;
         }

         if (VERSION.SDK_INT >= 17) {
            var1.setMarginStart(this.layout.startMargin);
            var1.setMarginEnd(this.layout.endMargin);
         }

         var1.validate();
      }

      public ConstraintSet.Constraint clone() {
         ConstraintSet.Constraint var1 = new ConstraintSet.Constraint();
         var1.layout.copyFrom(this.layout);
         var1.motion.copyFrom(this.motion);
         var1.propertySet.copyFrom(this.propertySet);
         var1.transform.copyFrom(this.transform);
         var1.mViewId = this.mViewId;
         return var1;
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
      public float circleAngle = 0.0F;
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
      public float guidePercent = -1.0F;
      public int heightDefault = 0;
      public int heightMax = -1;
      public int heightMin = -1;
      public float heightPercent = 1.0F;
      public float horizontalBias = 0.5F;
      public int horizontalChainStyle = 0;
      public float horizontalWeight = -1.0F;
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
      public float verticalBias = 0.5F;
      public int verticalChainStyle = 0;
      public float verticalWeight = -1.0F;
      public int widthDefault = 0;
      public int widthMax = -1;
      public int widthMin = -1;
      public float widthPercent = 1.0F;

      static {
         SparseIntArray var0 = new SparseIntArray();
         mapToConstant = var0;
         var0.append(R.styleable.Layout_layout_constraintLeft_toLeftOf, 24);
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

      public void copyFrom(ConstraintSet.Layout var1) {
         this.mIsGuideline = var1.mIsGuideline;
         this.mWidth = var1.mWidth;
         this.mApply = var1.mApply;
         this.mHeight = var1.mHeight;
         this.guideBegin = var1.guideBegin;
         this.guideEnd = var1.guideEnd;
         this.guidePercent = var1.guidePercent;
         this.leftToLeft = var1.leftToLeft;
         this.leftToRight = var1.leftToRight;
         this.rightToLeft = var1.rightToLeft;
         this.rightToRight = var1.rightToRight;
         this.topToTop = var1.topToTop;
         this.topToBottom = var1.topToBottom;
         this.bottomToTop = var1.bottomToTop;
         this.bottomToBottom = var1.bottomToBottom;
         this.baselineToBaseline = var1.baselineToBaseline;
         this.startToEnd = var1.startToEnd;
         this.startToStart = var1.startToStart;
         this.endToStart = var1.endToStart;
         this.endToEnd = var1.endToEnd;
         this.horizontalBias = var1.horizontalBias;
         this.verticalBias = var1.verticalBias;
         this.dimensionRatio = var1.dimensionRatio;
         this.circleConstraint = var1.circleConstraint;
         this.circleRadius = var1.circleRadius;
         this.circleAngle = var1.circleAngle;
         this.editorAbsoluteX = var1.editorAbsoluteX;
         this.editorAbsoluteY = var1.editorAbsoluteY;
         this.orientation = var1.orientation;
         this.leftMargin = var1.leftMargin;
         this.rightMargin = var1.rightMargin;
         this.topMargin = var1.topMargin;
         this.bottomMargin = var1.bottomMargin;
         this.endMargin = var1.endMargin;
         this.startMargin = var1.startMargin;
         this.goneLeftMargin = var1.goneLeftMargin;
         this.goneTopMargin = var1.goneTopMargin;
         this.goneRightMargin = var1.goneRightMargin;
         this.goneBottomMargin = var1.goneBottomMargin;
         this.goneEndMargin = var1.goneEndMargin;
         this.goneStartMargin = var1.goneStartMargin;
         this.verticalWeight = var1.verticalWeight;
         this.horizontalWeight = var1.horizontalWeight;
         this.horizontalChainStyle = var1.horizontalChainStyle;
         this.verticalChainStyle = var1.verticalChainStyle;
         this.widthDefault = var1.widthDefault;
         this.heightDefault = var1.heightDefault;
         this.widthMax = var1.widthMax;
         this.heightMax = var1.heightMax;
         this.widthMin = var1.widthMin;
         this.heightMin = var1.heightMin;
         this.widthPercent = var1.widthPercent;
         this.heightPercent = var1.heightPercent;
         this.mBarrierDirection = var1.mBarrierDirection;
         this.mBarrierMargin = var1.mBarrierMargin;
         this.mHelperType = var1.mHelperType;
         this.mConstraintTag = var1.mConstraintTag;
         int[] var2 = var1.mReferenceIds;
         if (var2 != null) {
            this.mReferenceIds = Arrays.copyOf(var2, var2.length);
         } else {
            this.mReferenceIds = null;
         }

         this.mReferenceIdString = var1.mReferenceIdString;
         this.constrainedWidth = var1.constrainedWidth;
         this.constrainedHeight = var1.constrainedHeight;
         this.mBarrierAllowsGoneWidgets = var1.mBarrierAllowsGoneWidgets;
      }

      public void dump(MotionScene var1, StringBuilder var2) {
         Field[] var3 = this.getClass().getDeclaredFields();
         var2.append("\n");

         for(int var4 = 0; var4 < var3.length; ++var4) {
            Field var5 = var3[var4];
            String var6 = var5.getName();
            if (!Modifier.isStatic(var5.getModifiers())) {
               IllegalAccessException var10000;
               label55: {
                  Object var7;
                  Class var8;
                  Class var13;
                  boolean var10001;
                  try {
                     var7 = var5.get(this);
                     var8 = var5.getType();
                     var13 = Integer.TYPE;
                  } catch (IllegalAccessException var12) {
                     var10000 = var12;
                     var10001 = false;
                     break label55;
                  }

                  if (var8 == var13) {
                     label65: {
                        Integer var14;
                        try {
                           var14 = (Integer)var7;
                           if (var14 == -1) {
                              continue;
                           }

                           var7 = var1.lookUpConstraintName(var14);
                           var2.append("    ");
                           var2.append(var6);
                           var2.append(" = \"");
                        } catch (IllegalAccessException var10) {
                           var10000 = var10;
                           var10001 = false;
                           break label65;
                        }

                        if (var7 == null) {
                           var7 = var14;
                        }

                        try {
                           var2.append(var7);
                           var2.append("\"\n");
                           continue;
                        } catch (IllegalAccessException var9) {
                           var10000 = var9;
                           var10001 = false;
                        }
                     }
                  } else {
                     try {
                        if (var8 == Float.TYPE) {
                           Float var16 = (Float)var7;
                           if (var16 != -1.0F) {
                              var2.append("    ");
                              var2.append(var6);
                              var2.append(" = \"");
                              var2.append(var16);
                              var2.append("\"\n");
                           }
                        }
                        continue;
                     } catch (IllegalAccessException var11) {
                        var10000 = var11;
                        var10001 = false;
                     }
                  }
               }

               IllegalAccessException var15 = var10000;
               var15.printStackTrace();
            }
         }

      }

      void fillFromAttributeList(Context var1, AttributeSet var2) {
         TypedArray var7 = var1.obtainStyledAttributes(var2, R.styleable.Layout);
         this.mApply = true;
         int var3 = var7.getIndexCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var7.getIndex(var4);
            int var6 = mapToConstant.get(var5);
            if (var6 != 80) {
               if (var6 != 81) {
                  switch(var6) {
                  case 1:
                     this.baselineToBaseline = ConstraintSet.lookupID(var7, var5, this.baselineToBaseline);
                     break;
                  case 2:
                     this.bottomMargin = var7.getDimensionPixelSize(var5, this.bottomMargin);
                     break;
                  case 3:
                     this.bottomToBottom = ConstraintSet.lookupID(var7, var5, this.bottomToBottom);
                     break;
                  case 4:
                     this.bottomToTop = ConstraintSet.lookupID(var7, var5, this.bottomToTop);
                     break;
                  case 5:
                     this.dimensionRatio = var7.getString(var5);
                     break;
                  case 6:
                     this.editorAbsoluteX = var7.getDimensionPixelOffset(var5, this.editorAbsoluteX);
                     break;
                  case 7:
                     this.editorAbsoluteY = var7.getDimensionPixelOffset(var5, this.editorAbsoluteY);
                     break;
                  case 8:
                     if (VERSION.SDK_INT >= 17) {
                        this.endMargin = var7.getDimensionPixelSize(var5, this.endMargin);
                     }
                     break;
                  case 9:
                     this.endToEnd = ConstraintSet.lookupID(var7, var5, this.endToEnd);
                     break;
                  case 10:
                     this.endToStart = ConstraintSet.lookupID(var7, var5, this.endToStart);
                     break;
                  case 11:
                     this.goneBottomMargin = var7.getDimensionPixelSize(var5, this.goneBottomMargin);
                     break;
                  case 12:
                     this.goneEndMargin = var7.getDimensionPixelSize(var5, this.goneEndMargin);
                     break;
                  case 13:
                     this.goneLeftMargin = var7.getDimensionPixelSize(var5, this.goneLeftMargin);
                     break;
                  case 14:
                     this.goneRightMargin = var7.getDimensionPixelSize(var5, this.goneRightMargin);
                     break;
                  case 15:
                     this.goneStartMargin = var7.getDimensionPixelSize(var5, this.goneStartMargin);
                     break;
                  case 16:
                     this.goneTopMargin = var7.getDimensionPixelSize(var5, this.goneTopMargin);
                     break;
                  case 17:
                     this.guideBegin = var7.getDimensionPixelOffset(var5, this.guideBegin);
                     break;
                  case 18:
                     this.guideEnd = var7.getDimensionPixelOffset(var5, this.guideEnd);
                     break;
                  case 19:
                     this.guidePercent = var7.getFloat(var5, this.guidePercent);
                     break;
                  case 20:
                     this.horizontalBias = var7.getFloat(var5, this.horizontalBias);
                     break;
                  case 21:
                     this.mHeight = var7.getLayoutDimension(var5, this.mHeight);
                     break;
                  case 22:
                     this.mWidth = var7.getLayoutDimension(var5, this.mWidth);
                     break;
                  case 23:
                     this.leftMargin = var7.getDimensionPixelSize(var5, this.leftMargin);
                     break;
                  case 24:
                     this.leftToLeft = ConstraintSet.lookupID(var7, var5, this.leftToLeft);
                     break;
                  case 25:
                     this.leftToRight = ConstraintSet.lookupID(var7, var5, this.leftToRight);
                     break;
                  case 26:
                     this.orientation = var7.getInt(var5, this.orientation);
                     break;
                  case 27:
                     this.rightMargin = var7.getDimensionPixelSize(var5, this.rightMargin);
                     break;
                  case 28:
                     this.rightToLeft = ConstraintSet.lookupID(var7, var5, this.rightToLeft);
                     break;
                  case 29:
                     this.rightToRight = ConstraintSet.lookupID(var7, var5, this.rightToRight);
                     break;
                  case 30:
                     if (VERSION.SDK_INT >= 17) {
                        this.startMargin = var7.getDimensionPixelSize(var5, this.startMargin);
                     }
                     break;
                  case 31:
                     this.startToEnd = ConstraintSet.lookupID(var7, var5, this.startToEnd);
                     break;
                  case 32:
                     this.startToStart = ConstraintSet.lookupID(var7, var5, this.startToStart);
                     break;
                  case 33:
                     this.topMargin = var7.getDimensionPixelSize(var5, this.topMargin);
                     break;
                  case 34:
                     this.topToBottom = ConstraintSet.lookupID(var7, var5, this.topToBottom);
                     break;
                  case 35:
                     this.topToTop = ConstraintSet.lookupID(var7, var5, this.topToTop);
                     break;
                  case 36:
                     this.verticalBias = var7.getFloat(var5, this.verticalBias);
                     break;
                  case 37:
                     this.horizontalWeight = var7.getFloat(var5, this.horizontalWeight);
                     break;
                  case 38:
                     this.verticalWeight = var7.getFloat(var5, this.verticalWeight);
                     break;
                  case 39:
                     this.horizontalChainStyle = var7.getInt(var5, this.horizontalChainStyle);
                     break;
                  case 40:
                     this.verticalChainStyle = var7.getInt(var5, this.verticalChainStyle);
                     break;
                  default:
                     switch(var6) {
                     case 54:
                        this.widthDefault = var7.getInt(var5, this.widthDefault);
                        break;
                     case 55:
                        this.heightDefault = var7.getInt(var5, this.heightDefault);
                        break;
                     case 56:
                        this.widthMax = var7.getDimensionPixelSize(var5, this.widthMax);
                        break;
                     case 57:
                        this.heightMax = var7.getDimensionPixelSize(var5, this.heightMax);
                        break;
                     case 58:
                        this.widthMin = var7.getDimensionPixelSize(var5, this.widthMin);
                        break;
                     case 59:
                        this.heightMin = var7.getDimensionPixelSize(var5, this.heightMin);
                        break;
                     default:
                        switch(var6) {
                        case 61:
                           this.circleConstraint = ConstraintSet.lookupID(var7, var5, this.circleConstraint);
                           break;
                        case 62:
                           this.circleRadius = var7.getDimensionPixelSize(var5, this.circleRadius);
                           break;
                        case 63:
                           this.circleAngle = var7.getFloat(var5, this.circleAngle);
                           break;
                        default:
                           StringBuilder var8;
                           switch(var6) {
                           case 69:
                              this.widthPercent = var7.getFloat(var5, 1.0F);
                              break;
                           case 70:
                              this.heightPercent = var7.getFloat(var5, 1.0F);
                              break;
                           case 71:
                              Log.e("ConstraintSet", "CURRENTLY UNSUPPORTED");
                              break;
                           case 72:
                              this.mBarrierDirection = var7.getInt(var5, this.mBarrierDirection);
                              break;
                           case 73:
                              this.mBarrierMargin = var7.getDimensionPixelSize(var5, this.mBarrierMargin);
                              break;
                           case 74:
                              this.mReferenceIdString = var7.getString(var5);
                              break;
                           case 75:
                              this.mBarrierAllowsGoneWidgets = var7.getBoolean(var5, this.mBarrierAllowsGoneWidgets);
                              break;
                           case 76:
                              var8 = new StringBuilder();
                              var8.append("unused attribute 0x");
                              var8.append(Integer.toHexString(var5));
                              var8.append("   ");
                              var8.append(mapToConstant.get(var5));
                              Log.w("ConstraintSet", var8.toString());
                              break;
                           case 77:
                              this.mConstraintTag = var7.getString(var5);
                              break;
                           default:
                              var8 = new StringBuilder();
                              var8.append("Unknown attribute 0x");
                              var8.append(Integer.toHexString(var5));
                              var8.append("   ");
                              var8.append(mapToConstant.get(var5));
                              Log.w("ConstraintSet", var8.toString());
                           }
                        }
                     }
                  }
               } else {
                  this.constrainedHeight = var7.getBoolean(var5, this.constrainedHeight);
               }
            } else {
               this.constrainedWidth = var7.getBoolean(var5, this.constrainedWidth);
            }
         }

         var7.recycle();
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
         SparseIntArray var0 = new SparseIntArray();
         mapToConstant = var0;
         var0.append(R.styleable.Motion_motionPathRotate, 1);
         mapToConstant.append(R.styleable.Motion_pathMotionArc, 2);
         mapToConstant.append(R.styleable.Motion_transitionEasing, 3);
         mapToConstant.append(R.styleable.Motion_drawPath, 4);
         mapToConstant.append(R.styleable.Motion_animate_relativeTo, 5);
         mapToConstant.append(R.styleable.Motion_motionStagger, 6);
      }

      public void copyFrom(ConstraintSet.Motion var1) {
         this.mApply = var1.mApply;
         this.mAnimateRelativeTo = var1.mAnimateRelativeTo;
         this.mTransitionEasing = var1.mTransitionEasing;
         this.mPathMotionArc = var1.mPathMotionArc;
         this.mDrawPath = var1.mDrawPath;
         this.mPathRotate = var1.mPathRotate;
         this.mMotionStagger = var1.mMotionStagger;
      }

      void fillFromAttributeList(Context var1, AttributeSet var2) {
         TypedArray var6 = var1.obtainStyledAttributes(var2, R.styleable.Motion);
         this.mApply = true;
         int var3 = var6.getIndexCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var6.getIndex(var4);
            switch(mapToConstant.get(var5)) {
            case 1:
               this.mPathRotate = var6.getFloat(var5, this.mPathRotate);
               break;
            case 2:
               this.mPathMotionArc = var6.getInt(var5, this.mPathMotionArc);
               break;
            case 3:
               if (var6.peekValue(var5).type == 3) {
                  this.mTransitionEasing = var6.getString(var5);
               } else {
                  this.mTransitionEasing = Easing.NAMED_EASING[var6.getInteger(var5, 0)];
               }
               break;
            case 4:
               this.mDrawPath = var6.getInt(var5, 0);
               break;
            case 5:
               this.mAnimateRelativeTo = ConstraintSet.lookupID(var6, var5, this.mAnimateRelativeTo);
               break;
            case 6:
               this.mMotionStagger = var6.getFloat(var5, this.mMotionStagger);
            }
         }

         var6.recycle();
      }
   }

   public static class PropertySet {
      public float alpha = 1.0F;
      public boolean mApply = false;
      public float mProgress = Float.NaN;
      public int mVisibilityMode = 0;
      public int visibility = 0;

      public void copyFrom(ConstraintSet.PropertySet var1) {
         this.mApply = var1.mApply;
         this.visibility = var1.visibility;
         this.alpha = var1.alpha;
         this.mProgress = var1.mProgress;
         this.mVisibilityMode = var1.mVisibilityMode;
      }

      void fillFromAttributeList(Context var1, AttributeSet var2) {
         TypedArray var6 = var1.obtainStyledAttributes(var2, R.styleable.PropertySet);
         this.mApply = true;
         int var3 = var6.getIndexCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var6.getIndex(var4);
            if (var5 == R.styleable.PropertySet_android_alpha) {
               this.alpha = var6.getFloat(var5, this.alpha);
            } else if (var5 == R.styleable.PropertySet_android_visibility) {
               this.visibility = var6.getInt(var5, this.visibility);
               this.visibility = ConstraintSet.VISIBILITY_FLAGS[this.visibility];
            } else if (var5 == R.styleable.PropertySet_visibilityMode) {
               this.mVisibilityMode = var6.getInt(var5, this.mVisibilityMode);
            } else if (var5 == R.styleable.PropertySet_motionProgress) {
               this.mProgress = var6.getFloat(var5, this.mProgress);
            }
         }

         var6.recycle();
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
      public float elevation = 0.0F;
      public boolean mApply = false;
      public float rotation = 0.0F;
      public float rotationX = 0.0F;
      public float rotationY = 0.0F;
      public float scaleX = 1.0F;
      public float scaleY = 1.0F;
      public float transformPivotX = Float.NaN;
      public float transformPivotY = Float.NaN;
      public float translationX = 0.0F;
      public float translationY = 0.0F;
      public float translationZ = 0.0F;

      static {
         SparseIntArray var0 = new SparseIntArray();
         mapToConstant = var0;
         var0.append(R.styleable.Transform_android_rotation, 1);
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

      public void copyFrom(ConstraintSet.Transform var1) {
         this.mApply = var1.mApply;
         this.rotation = var1.rotation;
         this.rotationX = var1.rotationX;
         this.rotationY = var1.rotationY;
         this.scaleX = var1.scaleX;
         this.scaleY = var1.scaleY;
         this.transformPivotX = var1.transformPivotX;
         this.transformPivotY = var1.transformPivotY;
         this.translationX = var1.translationX;
         this.translationY = var1.translationY;
         this.translationZ = var1.translationZ;
         this.applyElevation = var1.applyElevation;
         this.elevation = var1.elevation;
      }

      void fillFromAttributeList(Context var1, AttributeSet var2) {
         TypedArray var6 = var1.obtainStyledAttributes(var2, R.styleable.Transform);
         this.mApply = true;
         int var3 = var6.getIndexCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var6.getIndex(var4);
            switch(mapToConstant.get(var5)) {
            case 1:
               this.rotation = var6.getFloat(var5, this.rotation);
               break;
            case 2:
               this.rotationX = var6.getFloat(var5, this.rotationX);
               break;
            case 3:
               this.rotationY = var6.getFloat(var5, this.rotationY);
               break;
            case 4:
               this.scaleX = var6.getFloat(var5, this.scaleX);
               break;
            case 5:
               this.scaleY = var6.getFloat(var5, this.scaleY);
               break;
            case 6:
               this.transformPivotX = var6.getDimension(var5, this.transformPivotX);
               break;
            case 7:
               this.transformPivotY = var6.getDimension(var5, this.transformPivotY);
               break;
            case 8:
               this.translationX = var6.getDimension(var5, this.translationX);
               break;
            case 9:
               this.translationY = var6.getDimension(var5, this.translationY);
               break;
            case 10:
               if (VERSION.SDK_INT >= 21) {
                  this.translationZ = var6.getDimension(var5, this.translationZ);
               }
               break;
            case 11:
               if (VERSION.SDK_INT >= 21) {
                  this.applyElevation = true;
                  this.elevation = var6.getDimension(var5, this.elevation);
               }
            }
         }

         var6.recycle();
      }
   }
}

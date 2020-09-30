package androidx.constraintlayout.motion.widget;

import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import java.io.PrintStream;
import java.util.HashMap;

public class DesignTool implements ProxyInterface {
   private static final boolean DEBUG = false;
   private static final String TAG = "DesignTool";
   static final HashMap<Pair<Integer, Integer>, String> allAttributes = new HashMap();
   static final HashMap<String, String> allMargins = new HashMap();
   private String mLastEndState = null;
   private int mLastEndStateId = -1;
   private String mLastStartState = null;
   private int mLastStartStateId = -1;
   private final MotionLayout mMotionLayout;
   private MotionScene mSceneCache;

   static {
      HashMap var0 = allAttributes;
      Integer var1 = 4;
      var0.put(Pair.create(var1, var1), "layout_constraintBottom_toBottomOf");
      HashMap var2 = allAttributes;
      Integer var3 = 3;
      var2.put(Pair.create(var1, var3), "layout_constraintBottom_toTopOf");
      allAttributes.put(Pair.create(var3, var1), "layout_constraintTop_toBottomOf");
      allAttributes.put(Pair.create(var3, var3), "layout_constraintTop_toTopOf");
      var0 = allAttributes;
      var1 = 6;
      var0.put(Pair.create(var1, var1), "layout_constraintStart_toStartOf");
      var2 = allAttributes;
      var3 = 7;
      var2.put(Pair.create(var1, var3), "layout_constraintStart_toEndOf");
      allAttributes.put(Pair.create(var3, var1), "layout_constraintEnd_toStartOf");
      allAttributes.put(Pair.create(var3, var3), "layout_constraintEnd_toEndOf");
      var0 = allAttributes;
      var1 = 1;
      var0.put(Pair.create(var1, var1), "layout_constraintLeft_toLeftOf");
      var2 = allAttributes;
      var3 = 2;
      var2.put(Pair.create(var1, var3), "layout_constraintLeft_toRightOf");
      allAttributes.put(Pair.create(var3, var3), "layout_constraintRight_toRightOf");
      allAttributes.put(Pair.create(var3, var1), "layout_constraintRight_toLeftOf");
      var0 = allAttributes;
      var1 = 5;
      var0.put(Pair.create(var1, var1), "layout_constraintBaseline_toBaselineOf");
      allMargins.put("layout_constraintBottom_toBottomOf", "layout_marginBottom");
      allMargins.put("layout_constraintBottom_toTopOf", "layout_marginBottom");
      allMargins.put("layout_constraintTop_toBottomOf", "layout_marginTop");
      allMargins.put("layout_constraintTop_toTopOf", "layout_marginTop");
      allMargins.put("layout_constraintStart_toStartOf", "layout_marginStart");
      allMargins.put("layout_constraintStart_toEndOf", "layout_marginStart");
      allMargins.put("layout_constraintEnd_toStartOf", "layout_marginEnd");
      allMargins.put("layout_constraintEnd_toEndOf", "layout_marginEnd");
      allMargins.put("layout_constraintLeft_toLeftOf", "layout_marginLeft");
      allMargins.put("layout_constraintLeft_toRightOf", "layout_marginLeft");
      allMargins.put("layout_constraintRight_toRightOf", "layout_marginRight");
      allMargins.put("layout_constraintRight_toLeftOf", "layout_marginRight");
   }

   public DesignTool(MotionLayout var1) {
      this.mMotionLayout = var1;
   }

   private static void Connect(int var0, ConstraintSet var1, View var2, HashMap<String, String> var3, int var4, int var5) {
      String var6 = (String)allAttributes.get(Pair.create(var4, var5));
      String var7 = (String)var3.get(var6);
      if (var7 != null) {
         var6 = (String)allMargins.get(var6);
         if (var6 != null) {
            var0 = GetPxFromDp(var0, (String)var3.get(var6));
         } else {
            var0 = 0;
         }

         int var8 = Integer.parseInt(var7);
         var1.connect(var2.getId(), var4, var8, var5, var0);
      }

   }

   private static int GetPxFromDp(int var0, String var1) {
      if (var1 == null) {
         return 0;
      } else {
         int var2 = var1.indexOf(100);
         return var2 == -1 ? 0 : (int)((float)(Integer.valueOf(var1.substring(0, var2)) * var0) / 160.0F);
      }
   }

   private static void SetAbsolutePositions(int var0, ConstraintSet var1, View var2, HashMap<String, String> var3) {
      String var4 = (String)var3.get("layout_editor_absoluteX");
      if (var4 != null) {
         var1.setEditorAbsoluteX(var2.getId(), GetPxFromDp(var0, var4));
      }

      String var5 = (String)var3.get("layout_editor_absoluteY");
      if (var5 != null) {
         var1.setEditorAbsoluteY(var2.getId(), GetPxFromDp(var0, var5));
      }

   }

   private static void SetBias(ConstraintSet var0, View var1, HashMap<String, String> var2, int var3) {
      String var4;
      if (var3 == 1) {
         var4 = "layout_constraintVertical_bias";
      } else {
         var4 = "layout_constraintHorizontal_bias";
      }

      String var5 = (String)var2.get(var4);
      if (var5 != null) {
         if (var3 == 0) {
            var0.setHorizontalBias(var1.getId(), Float.parseFloat(var5));
         } else if (var3 == 1) {
            var0.setVerticalBias(var1.getId(), Float.parseFloat(var5));
         }
      }

   }

   private static void SetDimensions(int var0, ConstraintSet var1, View var2, HashMap<String, String> var3, int var4) {
      String var5;
      if (var4 == 1) {
         var5 = "layout_height";
      } else {
         var5 = "layout_width";
      }

      String var7 = (String)var3.get(var5);
      if (var7 != null) {
         int var6 = -2;
         if (!var7.equalsIgnoreCase("wrap_content")) {
            var6 = GetPxFromDp(var0, var7);
         }

         if (var4 == 0) {
            var1.constrainWidth(var2.getId(), var6);
         } else {
            var1.constrainHeight(var2.getId(), var6);
         }
      }

   }

   public int designAccess(int var1, String var2, Object var3, float[] var4, int var5, float[] var6, int var7) {
      View var8 = (View)var3;
      MotionController var9;
      if (var1 != 0) {
         if (this.mMotionLayout.mScene == null) {
            return -1;
         }

         if (var8 == null) {
            return -1;
         }

         MotionController var10 = (MotionController)this.mMotionLayout.mFrameArrayList.get(var8);
         var9 = var10;
         if (var10 == null) {
            return -1;
         }
      } else {
         var9 = null;
      }

      if (var1 != 0) {
         if (var1 != 1) {
            if (var1 != 2) {
               if (var1 != 3) {
                  return -1;
               } else {
                  var1 = this.mMotionLayout.mScene.getDuration() / 16;
                  return var9.getAttributeValues(var2, var6, var7);
               }
            } else {
               var1 = this.mMotionLayout.mScene.getDuration() / 16;
               var9.buildKeyFrames(var6, (int[])null);
               return var1;
            }
         } else {
            var1 = this.mMotionLayout.mScene.getDuration() / 16;
            var9.buildPath(var6, var1);
            return var1;
         }
      } else {
         return 1;
      }
   }

   public void disableAutoTransition(boolean var1) {
      this.mMotionLayout.disableAutoTransition(var1);
   }

   public void dumpConstraintSet(String var1) {
      if (this.mMotionLayout.mScene == null) {
         this.mMotionLayout.mScene = this.mSceneCache;
      }

      int var2 = this.mMotionLayout.lookUpConstraintId(var1);
      PrintStream var3 = System.out;
      StringBuilder var4 = new StringBuilder();
      var4.append(" dumping  ");
      var4.append(var1);
      var4.append(" (");
      var4.append(var2);
      var4.append(")");
      var3.println(var4.toString());

      try {
         this.mMotionLayout.mScene.getConstraintSet(var2).dump(this.mMotionLayout.mScene);
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public int getAnimationKeyFrames(Object var1, float[] var2) {
      if (this.mMotionLayout.mScene == null) {
         return -1;
      } else {
         int var3 = this.mMotionLayout.mScene.getDuration() / 16;
         MotionController var4 = (MotionController)this.mMotionLayout.mFrameArrayList.get(var1);
         if (var4 == null) {
            return 0;
         } else {
            var4.buildKeyFrames(var2, (int[])null);
            return var3;
         }
      }
   }

   public int getAnimationPath(Object var1, float[] var2, int var3) {
      if (this.mMotionLayout.mScene == null) {
         return -1;
      } else {
         MotionController var4 = (MotionController)this.mMotionLayout.mFrameArrayList.get(var1);
         if (var4 == null) {
            return 0;
         } else {
            var4.buildPath(var2, var3);
            return var3;
         }
      }
   }

   public void getAnimationRectangles(Object var1, float[] var2) {
      if (this.mMotionLayout.mScene != null) {
         int var3 = this.mMotionLayout.mScene.getDuration() / 16;
         MotionController var4 = (MotionController)this.mMotionLayout.mFrameArrayList.get(var1);
         if (var4 != null) {
            var4.buildRectangles(var2, var3);
         }
      }
   }

   public String getEndState() {
      int var1 = this.mMotionLayout.getEndState();
      if (this.mLastEndStateId == var1) {
         return this.mLastEndState;
      } else {
         String var2 = this.mMotionLayout.getConstraintSetNames(var1);
         if (var2 != null) {
            this.mLastEndState = var2;
            this.mLastEndStateId = var1;
         }

         return var2;
      }
   }

   public int getKeyFrameInfo(Object var1, int var2, int[] var3) {
      MotionController var4 = (MotionController)this.mMotionLayout.mFrameArrayList.get((View)var1);
      return var4 == null ? 0 : var4.getKeyFrameInfo(var2, var3);
   }

   public float getKeyFramePosition(Object var1, int var2, float var3, float var4) {
      return ((MotionController)this.mMotionLayout.mFrameArrayList.get((View)var1)).getKeyFrameParameter(var2, var3, var4);
   }

   public int getKeyFramePositions(Object var1, int[] var2, float[] var3) {
      MotionController var4 = (MotionController)this.mMotionLayout.mFrameArrayList.get((View)var1);
      return var4 == null ? 0 : var4.getkeyFramePositions(var2, var3);
   }

   public Object getKeyframe(int var1, int var2, int var3) {
      return this.mMotionLayout.mScene == null ? null : this.mMotionLayout.mScene.getKeyFrame(this.mMotionLayout.getContext(), var1, var2, var3);
   }

   public Object getKeyframe(Object var1, int var2, int var3) {
      if (this.mMotionLayout.mScene == null) {
         return null;
      } else {
         int var4 = ((View)var1).getId();
         return this.mMotionLayout.mScene.getKeyFrame(this.mMotionLayout.getContext(), var2, var4, var3);
      }
   }

   public Object getKeyframeAtLocation(Object var1, float var2, float var3) {
      View var4 = (View)var1;
      if (this.mMotionLayout.mScene == null) {
         return -1;
      } else if (var4 != null) {
         MotionController var5 = (MotionController)this.mMotionLayout.mFrameArrayList.get(var4);
         if (var5 == null) {
            return null;
         } else {
            ViewGroup var6 = (ViewGroup)var4.getParent();
            return var5.getPositionKeyframe(var6.getWidth(), var6.getHeight(), var2, var3);
         }
      } else {
         return null;
      }
   }

   public Boolean getPositionKeyframe(Object var1, Object var2, float var3, float var4, String[] var5, float[] var6) {
      if (var1 instanceof KeyPositionBase) {
         KeyPositionBase var7 = (KeyPositionBase)var1;
         HashMap var8 = this.mMotionLayout.mFrameArrayList;
         View var9 = (View)var2;
         ((MotionController)var8.get(var9)).positionKeyframe(var9, var7, var3, var4, var5, var6);
         this.mMotionLayout.rebuildScene();
         this.mMotionLayout.mInTransition = true;
         return true;
      } else {
         return false;
      }
   }

   public float getProgress() {
      return this.mMotionLayout.getProgress();
   }

   public String getStartState() {
      int var1 = this.mMotionLayout.getStartState();
      if (this.mLastStartStateId == var1) {
         return this.mLastStartState;
      } else {
         String var2 = this.mMotionLayout.getConstraintSetNames(var1);
         if (var2 != null) {
            this.mLastStartState = var2;
            this.mLastStartStateId = var1;
         }

         return this.mMotionLayout.getConstraintSetNames(var1);
      }
   }

   public String getState() {
      if (this.mLastStartState != null && this.mLastEndState != null) {
         float var1 = this.getProgress();
         if (var1 <= 0.01F) {
            return this.mLastStartState;
         }

         if (var1 >= 0.99F) {
            return this.mLastEndState;
         }
      }

      return this.mLastStartState;
   }

   public long getTransitionTimeMs() {
      return this.mMotionLayout.getTransitionTimeMs();
   }

   public boolean isInTransition() {
      boolean var1;
      if (this.mLastStartState != null && this.mLastEndState != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setAttributes(int var1, String var2, Object var3, Object var4) {
      View var7 = (View)var3;
      HashMap var8 = (HashMap)var4;
      int var5 = this.mMotionLayout.lookUpConstraintId(var2);
      ConstraintSet var6 = this.mMotionLayout.mScene.getConstraintSet(var5);
      if (var6 != null) {
         var6.clear(var7.getId());
         SetDimensions(var1, var6, var7, var8, 0);
         SetDimensions(var1, var6, var7, var8, 1);
         Connect(var1, var6, var7, var8, 6, 6);
         Connect(var1, var6, var7, var8, 6, 7);
         Connect(var1, var6, var7, var8, 7, 7);
         Connect(var1, var6, var7, var8, 7, 6);
         Connect(var1, var6, var7, var8, 1, 1);
         Connect(var1, var6, var7, var8, 1, 2);
         Connect(var1, var6, var7, var8, 2, 2);
         Connect(var1, var6, var7, var8, 2, 1);
         Connect(var1, var6, var7, var8, 3, 3);
         Connect(var1, var6, var7, var8, 3, 4);
         Connect(var1, var6, var7, var8, 4, 3);
         Connect(var1, var6, var7, var8, 4, 4);
         Connect(var1, var6, var7, var8, 5, 5);
         SetBias(var6, var7, var8, 0);
         SetBias(var6, var7, var8, 1);
         SetAbsolutePositions(var1, var6, var7, var8);
         this.mMotionLayout.updateState(var5, var6);
         this.mMotionLayout.requestLayout();
      }
   }

   public void setKeyFrame(Object var1, int var2, String var3, Object var4) {
      if (this.mMotionLayout.mScene != null) {
         this.mMotionLayout.mScene.setKeyframe((View)var1, var2, var3, var4);
         this.mMotionLayout.mTransitionGoalPosition = (float)var2 / 100.0F;
         this.mMotionLayout.mTransitionLastPosition = 0.0F;
         this.mMotionLayout.rebuildScene();
         this.mMotionLayout.evaluate(true);
      }

   }

   public boolean setKeyFramePosition(Object var1, int var2, int var3, float var4, float var5) {
      if (this.mMotionLayout.mScene != null) {
         MotionController var6 = (MotionController)this.mMotionLayout.mFrameArrayList.get(var1);
         var2 = (int)(this.mMotionLayout.mTransitionPosition * 100.0F);
         if (var6 != null) {
            MotionScene var7 = this.mMotionLayout.mScene;
            View var9 = (View)var1;
            if (var7.hasKeyFramePosition(var9, var2)) {
               float var8 = var6.getKeyFrameParameter(2, var4, var5);
               var4 = var6.getKeyFrameParameter(5, var4, var5);
               this.mMotionLayout.mScene.setKeyframe(var9, var2, "motion:percentX", var8);
               this.mMotionLayout.mScene.setKeyframe(var9, var2, "motion:percentY", var4);
               this.mMotionLayout.rebuildScene();
               this.mMotionLayout.evaluate(true);
               this.mMotionLayout.invalidate();
               return true;
            }
         }
      }

      return false;
   }

   public void setKeyframe(Object var1, String var2, Object var3) {
      if (var1 instanceof Key) {
         ((Key)var1).setValue(var2, var3);
         this.mMotionLayout.rebuildScene();
         this.mMotionLayout.mInTransition = true;
      }

   }

   public void setState(String var1) {
      String var2 = var1;
      if (var1 == null) {
         var2 = "motion_base";
      }

      if (this.mLastStartState != var2) {
         this.mLastStartState = var2;
         this.mLastEndState = null;
         if (this.mMotionLayout.mScene == null) {
            this.mMotionLayout.mScene = this.mSceneCache;
         }

         int var3;
         if (var2 != null) {
            var3 = this.mMotionLayout.lookUpConstraintId(var2);
         } else {
            var3 = R.id.motion_base;
         }

         this.mLastStartStateId = var3;
         if (var3 != 0) {
            if (var3 == this.mMotionLayout.getStartState()) {
               this.mMotionLayout.setProgress(0.0F);
            } else if (var3 == this.mMotionLayout.getEndState()) {
               this.mMotionLayout.setProgress(1.0F);
            } else {
               this.mMotionLayout.transitionToState(var3);
               this.mMotionLayout.setProgress(1.0F);
            }
         }

         this.mMotionLayout.requestLayout();
      }
   }

   public void setToolPosition(float var1) {
      if (this.mMotionLayout.mScene == null) {
         this.mMotionLayout.mScene = this.mSceneCache;
      }

      this.mMotionLayout.setProgress(var1);
      this.mMotionLayout.evaluate(true);
      this.mMotionLayout.requestLayout();
      this.mMotionLayout.invalidate();
   }

   public void setTransition(String var1, String var2) {
      if (this.mMotionLayout.mScene == null) {
         this.mMotionLayout.mScene = this.mSceneCache;
      }

      int var3 = this.mMotionLayout.lookUpConstraintId(var1);
      int var4 = this.mMotionLayout.lookUpConstraintId(var2);
      this.mMotionLayout.setTransition(var3, var4);
      this.mLastStartStateId = var3;
      this.mLastEndStateId = var4;
      this.mLastStartState = var1;
      this.mLastEndState = var2;
   }

   public void setViewDebug(Object var1, int var2) {
      MotionController var3 = (MotionController)this.mMotionLayout.mFrameArrayList.get(var1);
      if (var3 != null) {
         var3.setDrawPath(var2);
         this.mMotionLayout.invalidate();
      }

   }
}

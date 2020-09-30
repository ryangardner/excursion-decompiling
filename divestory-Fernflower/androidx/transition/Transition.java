package androidx.transition;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TimeInterpolator;
import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.InflateException;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import androidx.collection.ArrayMap;
import androidx.collection.LongSparseArray;
import androidx.core.content.res.TypedArrayUtils;
import androidx.core.view.ViewCompat;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

public abstract class Transition implements Cloneable {
   static final boolean DBG = false;
   private static final int[] DEFAULT_MATCH_ORDER = new int[]{2, 1, 3, 4};
   private static final String LOG_TAG = "Transition";
   private static final int MATCH_FIRST = 1;
   public static final int MATCH_ID = 3;
   private static final String MATCH_ID_STR = "id";
   public static final int MATCH_INSTANCE = 1;
   private static final String MATCH_INSTANCE_STR = "instance";
   public static final int MATCH_ITEM_ID = 4;
   private static final String MATCH_ITEM_ID_STR = "itemId";
   private static final int MATCH_LAST = 4;
   public static final int MATCH_NAME = 2;
   private static final String MATCH_NAME_STR = "name";
   private static final PathMotion STRAIGHT_PATH_MOTION = new PathMotion() {
      public Path getPath(float var1, float var2, float var3, float var4) {
         Path var5 = new Path();
         var5.moveTo(var1, var2);
         var5.lineTo(var3, var4);
         return var5;
      }
   };
   private static ThreadLocal<ArrayMap<Animator, Transition.AnimationInfo>> sRunningAnimators = new ThreadLocal();
   private ArrayList<Animator> mAnimators;
   boolean mCanRemoveViews;
   ArrayList<Animator> mCurrentAnimators;
   long mDuration = -1L;
   private TransitionValuesMaps mEndValues = new TransitionValuesMaps();
   private ArrayList<TransitionValues> mEndValuesList;
   private boolean mEnded;
   private Transition.EpicenterCallback mEpicenterCallback;
   private TimeInterpolator mInterpolator = null;
   private ArrayList<Transition.TransitionListener> mListeners;
   private int[] mMatchOrder;
   private String mName = this.getClass().getName();
   private ArrayMap<String, String> mNameOverrides;
   private int mNumInstances;
   TransitionSet mParent = null;
   private PathMotion mPathMotion;
   private boolean mPaused;
   TransitionPropagation mPropagation;
   private ViewGroup mSceneRoot;
   private long mStartDelay = -1L;
   private TransitionValuesMaps mStartValues = new TransitionValuesMaps();
   private ArrayList<TransitionValues> mStartValuesList;
   private ArrayList<View> mTargetChildExcludes = null;
   private ArrayList<View> mTargetExcludes = null;
   private ArrayList<Integer> mTargetIdChildExcludes = null;
   private ArrayList<Integer> mTargetIdExcludes = null;
   ArrayList<Integer> mTargetIds = new ArrayList();
   private ArrayList<String> mTargetNameExcludes = null;
   private ArrayList<String> mTargetNames = null;
   private ArrayList<Class<?>> mTargetTypeChildExcludes = null;
   private ArrayList<Class<?>> mTargetTypeExcludes = null;
   private ArrayList<Class<?>> mTargetTypes = null;
   ArrayList<View> mTargets = new ArrayList();

   public Transition() {
      this.mMatchOrder = DEFAULT_MATCH_ORDER;
      this.mSceneRoot = null;
      this.mCanRemoveViews = false;
      this.mCurrentAnimators = new ArrayList();
      this.mNumInstances = 0;
      this.mPaused = false;
      this.mEnded = false;
      this.mListeners = null;
      this.mAnimators = new ArrayList();
      this.mPathMotion = STRAIGHT_PATH_MOTION;
   }

   public Transition(Context var1, AttributeSet var2) {
      this.mMatchOrder = DEFAULT_MATCH_ORDER;
      this.mSceneRoot = null;
      this.mCanRemoveViews = false;
      this.mCurrentAnimators = new ArrayList();
      this.mNumInstances = 0;
      this.mPaused = false;
      this.mEnded = false;
      this.mListeners = null;
      this.mAnimators = new ArrayList();
      this.mPathMotion = STRAIGHT_PATH_MOTION;
      TypedArray var3 = var1.obtainStyledAttributes(var2, Styleable.TRANSITION);
      XmlResourceParser var8 = (XmlResourceParser)var2;
      long var4 = (long)TypedArrayUtils.getNamedInt(var3, var8, "duration", 1, -1);
      if (var4 >= 0L) {
         this.setDuration(var4);
      }

      var4 = (long)TypedArrayUtils.getNamedInt(var3, var8, "startDelay", 2, -1);
      if (var4 > 0L) {
         this.setStartDelay(var4);
      }

      int var6 = TypedArrayUtils.getNamedResourceId(var3, var8, "interpolator", 0, 0);
      if (var6 > 0) {
         this.setInterpolator(AnimationUtils.loadInterpolator(var1, var6));
      }

      String var7 = TypedArrayUtils.getNamedString(var3, var8, "matchOrder", 3);
      if (var7 != null) {
         this.setMatchOrder(parseMatchOrder(var7));
      }

      var3.recycle();
   }

   private void addUnmatched(ArrayMap<View, TransitionValues> var1, ArrayMap<View, TransitionValues> var2) {
      byte var3 = 0;
      int var4 = 0;

      while(true) {
         int var5 = var3;
         if (var4 >= var1.size()) {
            for(; var5 < var2.size(); ++var5) {
               TransitionValues var7 = (TransitionValues)var2.valueAt(var5);
               if (this.isValidTarget(var7.view)) {
                  this.mEndValuesList.add(var7);
                  this.mStartValuesList.add((Object)null);
               }
            }

            return;
         }

         TransitionValues var6 = (TransitionValues)var1.valueAt(var4);
         if (this.isValidTarget(var6.view)) {
            this.mStartValuesList.add(var6);
            this.mEndValuesList.add((Object)null);
         }

         ++var4;
      }
   }

   private static void addViewValues(TransitionValuesMaps var0, View var1, TransitionValues var2) {
      var0.mViewValues.put(var1, var2);
      int var3 = var1.getId();
      if (var3 >= 0) {
         if (var0.mIdValues.indexOfKey(var3) >= 0) {
            var0.mIdValues.put(var3, (Object)null);
         } else {
            var0.mIdValues.put(var3, var1);
         }
      }

      String var6 = ViewCompat.getTransitionName(var1);
      if (var6 != null) {
         if (var0.mNameValues.containsKey(var6)) {
            var0.mNameValues.put(var6, (Object)null);
         } else {
            var0.mNameValues.put(var6, var1);
         }
      }

      if (var1.getParent() instanceof ListView) {
         ListView var7 = (ListView)var1.getParent();
         if (var7.getAdapter().hasStableIds()) {
            long var4 = var7.getItemIdAtPosition(var7.getPositionForView(var1));
            if (var0.mItemIdValues.indexOfKey(var4) >= 0) {
               var1 = (View)var0.mItemIdValues.get(var4);
               if (var1 != null) {
                  ViewCompat.setHasTransientState(var1, false);
                  var0.mItemIdValues.put(var4, (Object)null);
               }
            } else {
               ViewCompat.setHasTransientState(var1, true);
               var0.mItemIdValues.put(var4, var1);
            }
         }
      }

   }

   private static boolean alreadyContains(int[] var0, int var1) {
      int var2 = var0[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         if (var0[var3] == var2) {
            return true;
         }
      }

      return false;
   }

   private void captureHierarchy(View var1, boolean var2) {
      if (var1 != null) {
         int var3 = var1.getId();
         ArrayList var4 = this.mTargetIdExcludes;
         if (var4 == null || !var4.contains(var3)) {
            var4 = this.mTargetExcludes;
            if (var4 == null || !var4.contains(var1)) {
               var4 = this.mTargetTypeExcludes;
               byte var5 = 0;
               int var6;
               int var7;
               if (var4 != null) {
                  var6 = var4.size();

                  for(var7 = 0; var7 < var6; ++var7) {
                     if (((Class)this.mTargetTypeExcludes.get(var7)).isInstance(var1)) {
                        return;
                     }
                  }
               }

               if (var1.getParent() instanceof ViewGroup) {
                  TransitionValues var9 = new TransitionValues(var1);
                  if (var2) {
                     this.captureStartValues(var9);
                  } else {
                     this.captureEndValues(var9);
                  }

                  var9.mTargetedTransitions.add(this);
                  this.capturePropagationValues(var9);
                  if (var2) {
                     addViewValues(this.mStartValues, var1, var9);
                  } else {
                     addViewValues(this.mEndValues, var1, var9);
                  }
               }

               if (var1 instanceof ViewGroup) {
                  var4 = this.mTargetIdChildExcludes;
                  if (var4 != null && var4.contains(var3)) {
                     return;
                  }

                  var4 = this.mTargetChildExcludes;
                  if (var4 != null && var4.contains(var1)) {
                     return;
                  }

                  var4 = this.mTargetTypeChildExcludes;
                  if (var4 != null) {
                     var6 = var4.size();

                     for(var7 = 0; var7 < var6; ++var7) {
                        if (((Class)this.mTargetTypeChildExcludes.get(var7)).isInstance(var1)) {
                           return;
                        }
                     }
                  }

                  ViewGroup var8 = (ViewGroup)var1;

                  for(var7 = var5; var7 < var8.getChildCount(); ++var7) {
                     this.captureHierarchy(var8.getChildAt(var7), var2);
                  }
               }

            }
         }
      }
   }

   private ArrayList<Integer> excludeId(ArrayList<Integer> var1, int var2, boolean var3) {
      ArrayList var4 = var1;
      if (var2 > 0) {
         if (var3) {
            var4 = Transition.ArrayListManager.add(var1, var2);
         } else {
            var4 = Transition.ArrayListManager.remove(var1, var2);
         }
      }

      return var4;
   }

   private static <T> ArrayList<T> excludeObject(ArrayList<T> var0, T var1, boolean var2) {
      ArrayList var3 = var0;
      if (var1 != null) {
         if (var2) {
            var3 = Transition.ArrayListManager.add(var0, var1);
         } else {
            var3 = Transition.ArrayListManager.remove(var0, var1);
         }
      }

      return var3;
   }

   private ArrayList<Class<?>> excludeType(ArrayList<Class<?>> var1, Class<?> var2, boolean var3) {
      ArrayList var4 = var1;
      if (var2 != null) {
         if (var3) {
            var4 = Transition.ArrayListManager.add(var1, var2);
         } else {
            var4 = Transition.ArrayListManager.remove(var1, var2);
         }
      }

      return var4;
   }

   private ArrayList<View> excludeView(ArrayList<View> var1, View var2, boolean var3) {
      ArrayList var4 = var1;
      if (var2 != null) {
         if (var3) {
            var4 = Transition.ArrayListManager.add(var1, var2);
         } else {
            var4 = Transition.ArrayListManager.remove(var1, var2);
         }
      }

      return var4;
   }

   private static ArrayMap<Animator, Transition.AnimationInfo> getRunningAnimators() {
      ArrayMap var0 = (ArrayMap)sRunningAnimators.get();
      ArrayMap var1 = var0;
      if (var0 == null) {
         var1 = new ArrayMap();
         sRunningAnimators.set(var1);
      }

      return var1;
   }

   private static boolean isValidMatch(int var0) {
      boolean var1 = true;
      if (var0 < 1 || var0 > 4) {
         var1 = false;
      }

      return var1;
   }

   private static boolean isValueChanged(TransitionValues var0, TransitionValues var1, String var2) {
      Object var5 = var0.values.get(var2);
      Object var6 = var1.values.get(var2);
      boolean var3 = true;
      boolean var4;
      if (var5 == null && var6 == null) {
         var4 = false;
      } else {
         var4 = var3;
         if (var5 != null) {
            if (var6 == null) {
               var4 = var3;
            } else {
               var4 = true ^ var5.equals(var6);
            }
         }
      }

      return var4;
   }

   private void matchIds(ArrayMap<View, TransitionValues> var1, ArrayMap<View, TransitionValues> var2, SparseArray<View> var3, SparseArray<View> var4) {
      int var5 = var3.size();

      for(int var6 = 0; var6 < var5; ++var6) {
         View var7 = (View)var3.valueAt(var6);
         if (var7 != null && this.isValidTarget(var7)) {
            View var8 = (View)var4.get(var3.keyAt(var6));
            if (var8 != null && this.isValidTarget(var8)) {
               TransitionValues var9 = (TransitionValues)var1.get(var7);
               TransitionValues var10 = (TransitionValues)var2.get(var8);
               if (var9 != null && var10 != null) {
                  this.mStartValuesList.add(var9);
                  this.mEndValuesList.add(var10);
                  var1.remove(var7);
                  var2.remove(var8);
               }
            }
         }
      }

   }

   private void matchInstances(ArrayMap<View, TransitionValues> var1, ArrayMap<View, TransitionValues> var2) {
      for(int var3 = var1.size() - 1; var3 >= 0; --var3) {
         View var4 = (View)var1.keyAt(var3);
         if (var4 != null && this.isValidTarget(var4)) {
            TransitionValues var6 = (TransitionValues)var2.remove(var4);
            if (var6 != null && this.isValidTarget(var6.view)) {
               TransitionValues var5 = (TransitionValues)var1.removeAt(var3);
               this.mStartValuesList.add(var5);
               this.mEndValuesList.add(var6);
            }
         }
      }

   }

   private void matchItemIds(ArrayMap<View, TransitionValues> var1, ArrayMap<View, TransitionValues> var2, LongSparseArray<View> var3, LongSparseArray<View> var4) {
      int var5 = var3.size();

      for(int var6 = 0; var6 < var5; ++var6) {
         View var7 = (View)var3.valueAt(var6);
         if (var7 != null && this.isValidTarget(var7)) {
            View var8 = (View)var4.get(var3.keyAt(var6));
            if (var8 != null && this.isValidTarget(var8)) {
               TransitionValues var9 = (TransitionValues)var1.get(var7);
               TransitionValues var10 = (TransitionValues)var2.get(var8);
               if (var9 != null && var10 != null) {
                  this.mStartValuesList.add(var9);
                  this.mEndValuesList.add(var10);
                  var1.remove(var7);
                  var2.remove(var8);
               }
            }
         }
      }

   }

   private void matchNames(ArrayMap<View, TransitionValues> var1, ArrayMap<View, TransitionValues> var2, ArrayMap<String, View> var3, ArrayMap<String, View> var4) {
      int var5 = var3.size();

      for(int var6 = 0; var6 < var5; ++var6) {
         View var7 = (View)var3.valueAt(var6);
         if (var7 != null && this.isValidTarget(var7)) {
            View var8 = (View)var4.get(var3.keyAt(var6));
            if (var8 != null && this.isValidTarget(var8)) {
               TransitionValues var9 = (TransitionValues)var1.get(var7);
               TransitionValues var10 = (TransitionValues)var2.get(var8);
               if (var9 != null && var10 != null) {
                  this.mStartValuesList.add(var9);
                  this.mEndValuesList.add(var10);
                  var1.remove(var7);
                  var2.remove(var8);
               }
            }
         }
      }

   }

   private void matchStartAndEnd(TransitionValuesMaps var1, TransitionValuesMaps var2) {
      ArrayMap var3 = new ArrayMap(var1.mViewValues);
      ArrayMap var4 = new ArrayMap(var2.mViewValues);
      int var5 = 0;

      while(true) {
         int[] var6 = this.mMatchOrder;
         if (var5 >= var6.length) {
            this.addUnmatched(var3, var4);
            return;
         }

         int var7 = var6[var5];
         if (var7 != 1) {
            if (var7 != 2) {
               if (var7 != 3) {
                  if (var7 == 4) {
                     this.matchItemIds(var3, var4, var1.mItemIdValues, var2.mItemIdValues);
                  }
               } else {
                  this.matchIds(var3, var4, var1.mIdValues, var2.mIdValues);
               }
            } else {
               this.matchNames(var3, var4, var1.mNameValues, var2.mNameValues);
            }
         } else {
            this.matchInstances(var3, var4);
         }

         ++var5;
      }
   }

   private static int[] parseMatchOrder(String var0) {
      StringTokenizer var1 = new StringTokenizer(var0, ",");
      int[] var4 = new int[var1.countTokens()];

      for(int var2 = 0; var1.hasMoreTokens(); ++var2) {
         String var3 = var1.nextToken().trim();
         if ("id".equalsIgnoreCase(var3)) {
            var4[var2] = 3;
         } else if ("instance".equalsIgnoreCase(var3)) {
            var4[var2] = 1;
         } else if ("name".equalsIgnoreCase(var3)) {
            var4[var2] = 2;
         } else if ("itemId".equalsIgnoreCase(var3)) {
            var4[var2] = 4;
         } else {
            if (!var3.isEmpty()) {
               StringBuilder var5 = new StringBuilder();
               var5.append("Unknown match type in matchOrder: '");
               var5.append(var3);
               var5.append("'");
               throw new InflateException(var5.toString());
            }

            int[] var6 = new int[var4.length - 1];
            System.arraycopy(var4, 0, var6, 0, var2);
            --var2;
            var4 = var6;
         }
      }

      return var4;
   }

   private void runAnimator(Animator var1, final ArrayMap<Animator, Transition.AnimationInfo> var2) {
      if (var1 != null) {
         var1.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator var1) {
               var2.remove(var1);
               Transition.this.mCurrentAnimators.remove(var1);
            }

            public void onAnimationStart(Animator var1) {
               Transition.this.mCurrentAnimators.add(var1);
            }
         });
         this.animate(var1);
      }

   }

   public Transition addListener(Transition.TransitionListener var1) {
      if (this.mListeners == null) {
         this.mListeners = new ArrayList();
      }

      this.mListeners.add(var1);
      return this;
   }

   public Transition addTarget(int var1) {
      if (var1 != 0) {
         this.mTargetIds.add(var1);
      }

      return this;
   }

   public Transition addTarget(View var1) {
      this.mTargets.add(var1);
      return this;
   }

   public Transition addTarget(Class<?> var1) {
      if (this.mTargetTypes == null) {
         this.mTargetTypes = new ArrayList();
      }

      this.mTargetTypes.add(var1);
      return this;
   }

   public Transition addTarget(String var1) {
      if (this.mTargetNames == null) {
         this.mTargetNames = new ArrayList();
      }

      this.mTargetNames.add(var1);
      return this;
   }

   protected void animate(Animator var1) {
      if (var1 == null) {
         this.end();
      } else {
         if (this.getDuration() >= 0L) {
            var1.setDuration(this.getDuration());
         }

         if (this.getStartDelay() >= 0L) {
            var1.setStartDelay(this.getStartDelay() + var1.getStartDelay());
         }

         if (this.getInterpolator() != null) {
            var1.setInterpolator(this.getInterpolator());
         }

         var1.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator var1) {
               Transition.this.end();
               var1.removeListener(this);
            }
         });
         var1.start();
      }

   }

   protected void cancel() {
      int var1;
      for(var1 = this.mCurrentAnimators.size() - 1; var1 >= 0; --var1) {
         ((Animator)this.mCurrentAnimators.get(var1)).cancel();
      }

      ArrayList var2 = this.mListeners;
      if (var2 != null && var2.size() > 0) {
         var2 = (ArrayList)this.mListeners.clone();
         int var3 = var2.size();

         for(var1 = 0; var1 < var3; ++var1) {
            ((Transition.TransitionListener)var2.get(var1)).onTransitionCancel(this);
         }
      }

   }

   public abstract void captureEndValues(TransitionValues var1);

   void capturePropagationValues(TransitionValues var1) {
      if (this.mPropagation != null && !var1.values.isEmpty()) {
         String[] var2 = this.mPropagation.getPropagationProperties();
         if (var2 == null) {
            return;
         }

         boolean var3 = false;
         int var4 = 0;

         boolean var5;
         while(true) {
            if (var4 >= var2.length) {
               var5 = true;
               break;
            }

            if (!var1.values.containsKey(var2[var4])) {
               var5 = var3;
               break;
            }

            ++var4;
         }

         if (!var5) {
            this.mPropagation.captureValues(var1);
         }
      }

   }

   public abstract void captureStartValues(TransitionValues var1);

   void captureValues(ViewGroup var1, boolean var2) {
      int var3;
      byte var4;
      View var6;
      label98: {
         this.clearValues(var2);
         var3 = this.mTargetIds.size();
         var4 = 0;
         if (var3 > 0 || this.mTargets.size() > 0) {
            ArrayList var5 = this.mTargetNames;
            if (var5 == null || var5.isEmpty()) {
               var5 = this.mTargetTypes;
               if (var5 == null || var5.isEmpty()) {
                  TransitionValues var12;
                  for(var3 = 0; var3 < this.mTargetIds.size(); ++var3) {
                     var6 = var1.findViewById((Integer)this.mTargetIds.get(var3));
                     if (var6 != null) {
                        var12 = new TransitionValues(var6);
                        if (var2) {
                           this.captureStartValues(var12);
                        } else {
                           this.captureEndValues(var12);
                        }

                        var12.mTargetedTransitions.add(this);
                        this.capturePropagationValues(var12);
                        if (var2) {
                           addViewValues(this.mStartValues, var6, var12);
                        } else {
                           addViewValues(this.mEndValues, var6, var12);
                        }
                     }
                  }

                  var3 = 0;

                  while(true) {
                     if (var3 >= this.mTargets.size()) {
                        break label98;
                     }

                     View var9 = (View)this.mTargets.get(var3);
                     var12 = new TransitionValues(var9);
                     if (var2) {
                        this.captureStartValues(var12);
                     } else {
                        this.captureEndValues(var12);
                     }

                     var12.mTargetedTransitions.add(this);
                     this.capturePropagationValues(var12);
                     if (var2) {
                        addViewValues(this.mStartValues, var9, var12);
                     } else {
                        addViewValues(this.mEndValues, var9, var12);
                     }

                     ++var3;
                  }
               }
            }
         }

         this.captureHierarchy(var1, var2);
      }

      if (!var2) {
         ArrayMap var10 = this.mNameOverrides;
         if (var10 != null) {
            int var7 = var10.size();
            ArrayList var11 = new ArrayList(var7);
            var3 = 0;

            while(true) {
               int var8 = var4;
               String var13;
               if (var3 >= var7) {
                  for(; var8 < var7; ++var8) {
                     var6 = (View)var11.get(var8);
                     if (var6 != null) {
                        var13 = (String)this.mNameOverrides.valueAt(var8);
                        this.mStartValues.mNameValues.put(var13, var6);
                     }
                  }
                  break;
               }

               var13 = (String)this.mNameOverrides.keyAt(var3);
               var11.add(this.mStartValues.mNameValues.remove(var13));
               ++var3;
            }
         }
      }

   }

   void clearValues(boolean var1) {
      if (var1) {
         this.mStartValues.mViewValues.clear();
         this.mStartValues.mIdValues.clear();
         this.mStartValues.mItemIdValues.clear();
      } else {
         this.mEndValues.mViewValues.clear();
         this.mEndValues.mIdValues.clear();
         this.mEndValues.mItemIdValues.clear();
      }

   }

   public Transition clone() {
      try {
         Transition var1 = (Transition)super.clone();
         ArrayList var2 = new ArrayList();
         var1.mAnimators = var2;
         TransitionValuesMaps var4 = new TransitionValuesMaps();
         var1.mStartValues = var4;
         var4 = new TransitionValuesMaps();
         var1.mEndValues = var4;
         var1.mStartValuesList = null;
         var1.mEndValuesList = null;
         return var1;
      } catch (CloneNotSupportedException var3) {
         return null;
      }
   }

   public Animator createAnimator(ViewGroup var1, TransitionValues var2, TransitionValues var3) {
      return null;
   }

   protected void createAnimators(ViewGroup var1, TransitionValuesMaps var2, TransitionValuesMaps var3, ArrayList<TransitionValues> var4, ArrayList<TransitionValues> var5) {
      ArrayMap var6 = getRunningAnimators();
      SparseIntArray var7 = new SparseIntArray();
      int var8 = var4.size();
      long var9 = Long.MAX_VALUE;

      int var11;
      long var15;
      int var27;
      for(var11 = 0; var11 < var8; var9 = var15) {
         TransitionValues var12 = (TransitionValues)var4.get(var11);
         TransitionValues var23 = (TransitionValues)var5.get(var11);
         TransitionValues var13 = var12;
         if (var12 != null) {
            var13 = var12;
            if (!var12.mTargetedTransitions.contains(this)) {
               var13 = null;
            }
         }

         TransitionValues var14 = var23;
         if (var23 != null) {
            var14 = var23;
            if (!var23.mTargetedTransitions.contains(this)) {
               var14 = null;
            }
         }

         label118: {
            if (var13 != null || var14 != null) {
               boolean var17;
               if (var13 != null && var14 != null && !this.isTransitionRequired(var13, var14)) {
                  var17 = false;
               } else {
                  var17 = true;
               }

               if (var17) {
                  Animator var24 = this.createAnimator(var1, var13, var14);
                  if (var24 != null) {
                     View var18;
                     View var25;
                     Animator var29;
                     if (var14 != null) {
                        var18 = var14.view;
                        String[] var19 = this.getTransitionProperties();
                        if (var19 != null && var19.length > 0) {
                           TransitionValues var20 = new TransitionValues(var18);
                           var12 = (TransitionValues)var3.mViewValues.get(var18);
                           var27 = var11;
                           int var21;
                           if (var12 != null) {
                              var21 = 0;

                              while(true) {
                                 var27 = var11;
                                 if (var21 >= var19.length) {
                                    break;
                                 }

                                 var20.values.put(var19[var21], var12.values.get(var19[var21]));
                                 ++var21;
                              }
                           }

                           var11 = var27;
                           var21 = var6.size();
                           var27 = 0;

                           while(true) {
                              if (var27 >= var21) {
                                 var12 = var20;
                                 break;
                              }

                              Transition.AnimationInfo var26 = (Transition.AnimationInfo)var6.get((Animator)var6.keyAt(var27));
                              if (var26.mValues != null && var26.mView == var18 && var26.mName.equals(this.getName()) && var26.mValues.equals(var20)) {
                                 var24 = null;
                                 var12 = var20;
                                 break;
                              }

                              ++var27;
                           }
                        } else {
                           var12 = null;
                        }

                        var29 = var24;
                        var25 = var18;
                     } else {
                        var18 = var13.view;
                        var29 = var24;
                        var12 = null;
                        var25 = var18;
                     }

                     var15 = var9;
                     var27 = var11;
                     if (var29 != null) {
                        TransitionPropagation var28 = this.mPropagation;
                        var15 = var9;
                        if (var28 != null) {
                           var15 = var28.getStartDelay(var1, this, var13, var14);
                           var7.put(this.mAnimators.size(), (int)var15);
                           var15 = Math.min(var15, var9);
                        }

                        var6.put(var29, new Transition.AnimationInfo(var25, this.getName(), this, ViewUtils.getWindowId(var1), var12));
                        this.mAnimators.add(var29);
                        var27 = var11;
                     }
                     break label118;
                  }
               }
            }

            var15 = var9;
            var27 = var11;
         }

         var11 = var27 + 1;
      }

      if (var7.size() != 0) {
         for(var11 = 0; var11 < var7.size(); ++var11) {
            var27 = var7.keyAt(var11);
            Animator var22 = (Animator)this.mAnimators.get(var27);
            var22.setStartDelay((long)var7.valueAt(var11) - var9 + var22.getStartDelay());
         }
      }

   }

   protected void end() {
      int var1 = this.mNumInstances - 1;
      this.mNumInstances = var1;
      if (var1 == 0) {
         ArrayList var2 = this.mListeners;
         if (var2 != null && var2.size() > 0) {
            var2 = (ArrayList)this.mListeners.clone();
            int var3 = var2.size();

            for(var1 = 0; var1 < var3; ++var1) {
               ((Transition.TransitionListener)var2.get(var1)).onTransitionEnd(this);
            }
         }

         View var4;
         for(var1 = 0; var1 < this.mStartValues.mItemIdValues.size(); ++var1) {
            var4 = (View)this.mStartValues.mItemIdValues.valueAt(var1);
            if (var4 != null) {
               ViewCompat.setHasTransientState(var4, false);
            }
         }

         for(var1 = 0; var1 < this.mEndValues.mItemIdValues.size(); ++var1) {
            var4 = (View)this.mEndValues.mItemIdValues.valueAt(var1);
            if (var4 != null) {
               ViewCompat.setHasTransientState(var4, false);
            }
         }

         this.mEnded = true;
      }

   }

   public Transition excludeChildren(int var1, boolean var2) {
      this.mTargetIdChildExcludes = this.excludeId(this.mTargetIdChildExcludes, var1, var2);
      return this;
   }

   public Transition excludeChildren(View var1, boolean var2) {
      this.mTargetChildExcludes = this.excludeView(this.mTargetChildExcludes, var1, var2);
      return this;
   }

   public Transition excludeChildren(Class<?> var1, boolean var2) {
      this.mTargetTypeChildExcludes = this.excludeType(this.mTargetTypeChildExcludes, var1, var2);
      return this;
   }

   public Transition excludeTarget(int var1, boolean var2) {
      this.mTargetIdExcludes = this.excludeId(this.mTargetIdExcludes, var1, var2);
      return this;
   }

   public Transition excludeTarget(View var1, boolean var2) {
      this.mTargetExcludes = this.excludeView(this.mTargetExcludes, var1, var2);
      return this;
   }

   public Transition excludeTarget(Class<?> var1, boolean var2) {
      this.mTargetTypeExcludes = this.excludeType(this.mTargetTypeExcludes, var1, var2);
      return this;
   }

   public Transition excludeTarget(String var1, boolean var2) {
      this.mTargetNameExcludes = excludeObject(this.mTargetNameExcludes, var1, var2);
      return this;
   }

   void forceToEnd(ViewGroup var1) {
      ArrayMap var2 = getRunningAnimators();
      int var3 = var2.size();
      if (var1 != null && var3 != 0) {
         WindowIdImpl var5 = ViewUtils.getWindowId(var1);
         ArrayMap var4 = new ArrayMap(var2);
         var2.clear();
         --var3;

         for(; var3 >= 0; --var3) {
            Transition.AnimationInfo var6 = (Transition.AnimationInfo)var4.valueAt(var3);
            if (var6.mView != null && var5 != null && var5.equals(var6.mWindowId)) {
               ((Animator)var4.keyAt(var3)).end();
            }
         }
      }

   }

   public long getDuration() {
      return this.mDuration;
   }

   public Rect getEpicenter() {
      Transition.EpicenterCallback var1 = this.mEpicenterCallback;
      return var1 == null ? null : var1.onGetEpicenter(this);
   }

   public Transition.EpicenterCallback getEpicenterCallback() {
      return this.mEpicenterCallback;
   }

   public TimeInterpolator getInterpolator() {
      return this.mInterpolator;
   }

   TransitionValues getMatchedTransitionValues(View var1, boolean var2) {
      TransitionSet var3 = this.mParent;
      if (var3 != null) {
         return var3.getMatchedTransitionValues(var1, var2);
      } else {
         ArrayList var12;
         if (var2) {
            var12 = this.mStartValuesList;
         } else {
            var12 = this.mEndValuesList;
         }

         Object var4 = null;
         if (var12 == null) {
            return null;
         } else {
            int var5 = var12.size();
            byte var6 = -1;
            int var7 = 0;

            int var8;
            while(true) {
               var8 = var6;
               if (var7 >= var5) {
                  break;
               }

               TransitionValues var9 = (TransitionValues)var12.get(var7);
               if (var9 == null) {
                  return null;
               }

               if (var9.view == var1) {
                  var8 = var7;
                  break;
               }

               ++var7;
            }

            TransitionValues var10 = (TransitionValues)var4;
            if (var8 >= 0) {
               ArrayList var11;
               if (var2) {
                  var11 = this.mEndValuesList;
               } else {
                  var11 = this.mStartValuesList;
               }

               var10 = (TransitionValues)var11.get(var8);
            }

            return var10;
         }
      }
   }

   public String getName() {
      return this.mName;
   }

   public PathMotion getPathMotion() {
      return this.mPathMotion;
   }

   public TransitionPropagation getPropagation() {
      return this.mPropagation;
   }

   public long getStartDelay() {
      return this.mStartDelay;
   }

   public List<Integer> getTargetIds() {
      return this.mTargetIds;
   }

   public List<String> getTargetNames() {
      return this.mTargetNames;
   }

   public List<Class<?>> getTargetTypes() {
      return this.mTargetTypes;
   }

   public List<View> getTargets() {
      return this.mTargets;
   }

   public String[] getTransitionProperties() {
      return null;
   }

   public TransitionValues getTransitionValues(View var1, boolean var2) {
      TransitionSet var3 = this.mParent;
      if (var3 != null) {
         return var3.getTransitionValues(var1, var2);
      } else {
         TransitionValuesMaps var4;
         if (var2) {
            var4 = this.mStartValues;
         } else {
            var4 = this.mEndValues;
         }

         return (TransitionValues)var4.mViewValues.get(var1);
      }
   }

   public boolean isTransitionRequired(TransitionValues var1, TransitionValues var2) {
      boolean var3 = false;
      boolean var4 = var3;
      if (var1 != null) {
         var4 = var3;
         if (var2 != null) {
            String[] var5 = this.getTransitionProperties();
            if (var5 != null) {
               int var6 = var5.length;
               int var7 = 0;

               while(true) {
                  var4 = var3;
                  if (var7 >= var6) {
                     return var4;
                  }

                  if (isValueChanged(var1, var2, var5[var7])) {
                     break;
                  }

                  ++var7;
               }
            } else {
               Iterator var8 = var1.values.keySet().iterator();

               do {
                  var4 = var3;
                  if (!var8.hasNext()) {
                     return var4;
                  }
               } while(!isValueChanged(var1, var2, (String)var8.next()));
            }

            var4 = true;
         }
      }

      return var4;
   }

   boolean isValidTarget(View var1) {
      int var2 = var1.getId();
      ArrayList var3 = this.mTargetIdExcludes;
      if (var3 != null && var3.contains(var2)) {
         return false;
      } else {
         var3 = this.mTargetExcludes;
         if (var3 != null && var3.contains(var1)) {
            return false;
         } else {
            var3 = this.mTargetTypeExcludes;
            int var5;
            if (var3 != null) {
               int var4 = var3.size();

               for(var5 = 0; var5 < var4; ++var5) {
                  if (((Class)this.mTargetTypeExcludes.get(var5)).isInstance(var1)) {
                     return false;
                  }
               }
            }

            if (this.mTargetNameExcludes != null && ViewCompat.getTransitionName(var1) != null && this.mTargetNameExcludes.contains(ViewCompat.getTransitionName(var1))) {
               return false;
            } else {
               if (this.mTargetIds.size() == 0 && this.mTargets.size() == 0) {
                  var3 = this.mTargetTypes;
                  if (var3 == null || var3.isEmpty()) {
                     var3 = this.mTargetNames;
                     if (var3 == null || var3.isEmpty()) {
                        return true;
                     }
                  }
               }

               if (!this.mTargetIds.contains(var2) && !this.mTargets.contains(var1)) {
                  var3 = this.mTargetNames;
                  if (var3 != null && var3.contains(ViewCompat.getTransitionName(var1))) {
                     return true;
                  } else {
                     if (this.mTargetTypes != null) {
                        for(var5 = 0; var5 < this.mTargetTypes.size(); ++var5) {
                           if (((Class)this.mTargetTypes.get(var5)).isInstance(var1)) {
                              return true;
                           }
                        }
                     }

                     return false;
                  }
               } else {
                  return true;
               }
            }
         }
      }
   }

   public void pause(View var1) {
      if (!this.mEnded) {
         ArrayMap var2 = getRunningAnimators();
         int var3 = var2.size();
         WindowIdImpl var4 = ViewUtils.getWindowId(var1);
         --var3;

         for(; var3 >= 0; --var3) {
            Transition.AnimationInfo var6 = (Transition.AnimationInfo)var2.valueAt(var3);
            if (var6.mView != null && var4.equals(var6.mWindowId)) {
               AnimatorUtils.pause((Animator)var2.keyAt(var3));
            }
         }

         ArrayList var7 = this.mListeners;
         if (var7 != null && var7.size() > 0) {
            var7 = (ArrayList)this.mListeners.clone();
            int var5 = var7.size();

            for(var3 = 0; var3 < var5; ++var3) {
               ((Transition.TransitionListener)var7.get(var3)).onTransitionPause(this);
            }
         }

         this.mPaused = true;
      }

   }

   void playTransition(ViewGroup var1) {
      this.mStartValuesList = new ArrayList();
      this.mEndValuesList = new ArrayList();
      this.matchStartAndEnd(this.mStartValues, this.mEndValues);
      ArrayMap var2 = getRunningAnimators();
      int var3 = var2.size();
      WindowIdImpl var4 = ViewUtils.getWindowId(var1);
      --var3;

      for(; var3 >= 0; --var3) {
         Animator var5 = (Animator)var2.keyAt(var3);
         if (var5 != null) {
            Transition.AnimationInfo var6 = (Transition.AnimationInfo)var2.get(var5);
            if (var6 != null && var6.mView != null && var4.equals(var6.mWindowId)) {
               TransitionValues var7 = var6.mValues;
               View var8 = var6.mView;
               TransitionValues var9 = this.getTransitionValues(var8, true);
               TransitionValues var10 = this.getMatchedTransitionValues(var8, true);
               TransitionValues var11 = var10;
               if (var9 == null) {
                  var11 = var10;
                  if (var10 == null) {
                     var11 = (TransitionValues)this.mEndValues.mViewValues.get(var8);
                  }
               }

               boolean var12;
               if ((var9 != null || var11 != null) && var6.mTransition.isTransitionRequired(var7, var11)) {
                  var12 = true;
               } else {
                  var12 = false;
               }

               if (var12) {
                  if (!var5.isRunning() && !var5.isStarted()) {
                     var2.remove(var5);
                  } else {
                     var5.cancel();
                  }
               }
            }
         }
      }

      this.createAnimators(var1, this.mStartValues, this.mEndValues, this.mStartValuesList, this.mEndValuesList);
      this.runAnimators();
   }

   public Transition removeListener(Transition.TransitionListener var1) {
      ArrayList var2 = this.mListeners;
      if (var2 == null) {
         return this;
      } else {
         var2.remove(var1);
         if (this.mListeners.size() == 0) {
            this.mListeners = null;
         }

         return this;
      }
   }

   public Transition removeTarget(int var1) {
      if (var1 != 0) {
         this.mTargetIds.remove(var1);
      }

      return this;
   }

   public Transition removeTarget(View var1) {
      this.mTargets.remove(var1);
      return this;
   }

   public Transition removeTarget(Class<?> var1) {
      ArrayList var2 = this.mTargetTypes;
      if (var2 != null) {
         var2.remove(var1);
      }

      return this;
   }

   public Transition removeTarget(String var1) {
      ArrayList var2 = this.mTargetNames;
      if (var2 != null) {
         var2.remove(var1);
      }

      return this;
   }

   public void resume(View var1) {
      if (this.mPaused) {
         if (!this.mEnded) {
            ArrayMap var2 = getRunningAnimators();
            int var3 = var2.size();
            WindowIdImpl var6 = ViewUtils.getWindowId(var1);
            --var3;

            for(; var3 >= 0; --var3) {
               Transition.AnimationInfo var4 = (Transition.AnimationInfo)var2.valueAt(var3);
               if (var4.mView != null && var6.equals(var4.mWindowId)) {
                  AnimatorUtils.resume((Animator)var2.keyAt(var3));
               }
            }

            ArrayList var7 = this.mListeners;
            if (var7 != null && var7.size() > 0) {
               var7 = (ArrayList)this.mListeners.clone();
               int var5 = var7.size();

               for(var3 = 0; var3 < var5; ++var3) {
                  ((Transition.TransitionListener)var7.get(var3)).onTransitionResume(this);
               }
            }
         }

         this.mPaused = false;
      }

   }

   protected void runAnimators() {
      this.start();
      ArrayMap var1 = getRunningAnimators();
      Iterator var2 = this.mAnimators.iterator();

      while(var2.hasNext()) {
         Animator var3 = (Animator)var2.next();
         if (var1.containsKey(var3)) {
            this.start();
            this.runAnimator(var3, var1);
         }
      }

      this.mAnimators.clear();
      this.end();
   }

   void setCanRemoveViews(boolean var1) {
      this.mCanRemoveViews = var1;
   }

   public Transition setDuration(long var1) {
      this.mDuration = var1;
      return this;
   }

   public void setEpicenterCallback(Transition.EpicenterCallback var1) {
      this.mEpicenterCallback = var1;
   }

   public Transition setInterpolator(TimeInterpolator var1) {
      this.mInterpolator = var1;
      return this;
   }

   public void setMatchOrder(int... var1) {
      if (var1 != null && var1.length != 0) {
         for(int var2 = 0; var2 < var1.length; ++var2) {
            if (!isValidMatch(var1[var2])) {
               throw new IllegalArgumentException("matches contains invalid value");
            }

            if (alreadyContains(var1, var2)) {
               throw new IllegalArgumentException("matches contains a duplicate value");
            }
         }

         this.mMatchOrder = (int[])var1.clone();
      } else {
         this.mMatchOrder = DEFAULT_MATCH_ORDER;
      }

   }

   public void setPathMotion(PathMotion var1) {
      if (var1 == null) {
         this.mPathMotion = STRAIGHT_PATH_MOTION;
      } else {
         this.mPathMotion = var1;
      }

   }

   public void setPropagation(TransitionPropagation var1) {
      this.mPropagation = var1;
   }

   Transition setSceneRoot(ViewGroup var1) {
      this.mSceneRoot = var1;
      return this;
   }

   public Transition setStartDelay(long var1) {
      this.mStartDelay = var1;
      return this;
   }

   protected void start() {
      if (this.mNumInstances == 0) {
         ArrayList var1 = this.mListeners;
         if (var1 != null && var1.size() > 0) {
            var1 = (ArrayList)this.mListeners.clone();
            int var2 = var1.size();

            for(int var3 = 0; var3 < var2; ++var3) {
               ((Transition.TransitionListener)var1.get(var3)).onTransitionStart(this);
            }
         }

         this.mEnded = false;
      }

      ++this.mNumInstances;
   }

   public String toString() {
      return this.toString("");
   }

   String toString(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append(var1);
      var2.append(this.getClass().getSimpleName());
      var2.append("@");
      var2.append(Integer.toHexString(this.hashCode()));
      var2.append(": ");
      var1 = var2.toString();
      String var6 = var1;
      if (this.mDuration != -1L) {
         var2 = new StringBuilder();
         var2.append(var1);
         var2.append("dur(");
         var2.append(this.mDuration);
         var2.append(") ");
         var6 = var2.toString();
      }

      var1 = var6;
      StringBuilder var5;
      if (this.mStartDelay != -1L) {
         var5 = new StringBuilder();
         var5.append(var6);
         var5.append("dly(");
         var5.append(this.mStartDelay);
         var5.append(") ");
         var1 = var5.toString();
      }

      var6 = var1;
      if (this.mInterpolator != null) {
         var2 = new StringBuilder();
         var2.append(var1);
         var2.append("interp(");
         var2.append(this.mInterpolator);
         var2.append(") ");
         var6 = var2.toString();
      }

      if (this.mTargetIds.size() <= 0) {
         var1 = var6;
         if (this.mTargets.size() <= 0) {
            return var1;
         }
      }

      var5 = new StringBuilder();
      var5.append(var6);
      var5.append("tgts(");
      var6 = var5.toString();
      int var3 = this.mTargetIds.size();
      byte var4 = 0;
      var1 = var6;
      if (var3 > 0) {
         var3 = 0;

         while(true) {
            var1 = var6;
            if (var3 >= this.mTargetIds.size()) {
               break;
            }

            var1 = var6;
            if (var3 > 0) {
               var5 = new StringBuilder();
               var5.append(var6);
               var5.append(", ");
               var1 = var5.toString();
            }

            var2 = new StringBuilder();
            var2.append(var1);
            var2.append(this.mTargetIds.get(var3));
            var6 = var2.toString();
            ++var3;
         }
      }

      var6 = var1;
      if (this.mTargets.size() > 0) {
         var3 = var4;

         while(true) {
            var6 = var1;
            if (var3 >= this.mTargets.size()) {
               break;
            }

            var6 = var1;
            if (var3 > 0) {
               var2 = new StringBuilder();
               var2.append(var1);
               var2.append(", ");
               var6 = var2.toString();
            }

            var5 = new StringBuilder();
            var5.append(var6);
            var5.append(this.mTargets.get(var3));
            var1 = var5.toString();
            ++var3;
         }
      }

      var5 = new StringBuilder();
      var5.append(var6);
      var5.append(")");
      var1 = var5.toString();
      return var1;
   }

   private static class AnimationInfo {
      String mName;
      Transition mTransition;
      TransitionValues mValues;
      View mView;
      WindowIdImpl mWindowId;

      AnimationInfo(View var1, String var2, Transition var3, WindowIdImpl var4, TransitionValues var5) {
         this.mView = var1;
         this.mName = var2;
         this.mValues = var5;
         this.mWindowId = var4;
         this.mTransition = var3;
      }
   }

   private static class ArrayListManager {
      static <T> ArrayList<T> add(ArrayList<T> var0, T var1) {
         ArrayList var2 = var0;
         if (var0 == null) {
            var2 = new ArrayList();
         }

         if (!var2.contains(var1)) {
            var2.add(var1);
         }

         return var2;
      }

      static <T> ArrayList<T> remove(ArrayList<T> var0, T var1) {
         ArrayList var2 = var0;
         if (var0 != null) {
            var0.remove(var1);
            var2 = var0;
            if (var0.isEmpty()) {
               var2 = null;
            }
         }

         return var2;
      }
   }

   public abstract static class EpicenterCallback {
      public abstract Rect onGetEpicenter(Transition var1);
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface MatchOrder {
   }

   public interface TransitionListener {
      void onTransitionCancel(Transition var1);

      void onTransitionEnd(Transition var1);

      void onTransitionPause(Transition var1);

      void onTransitionResume(Transition var1);

      void onTransitionStart(Transition var1);
   }
}

package androidx.fragment.app;

import android.graphics.Rect;
import android.os.Build.VERSION;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.collection.ArrayMap;
import androidx.core.app.SharedElementCallback;
import androidx.core.os.CancellationSignal;
import androidx.core.view.OneShotPreDrawListener;
import androidx.core.view.ViewCompat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

class FragmentTransition {
   private static final int[] INVERSE_OPS = new int[]{0, 3, 0, 1, 5, 4, 7, 6, 9, 8, 10};
   private static final FragmentTransitionImpl PLATFORM_IMPL;
   private static final FragmentTransitionImpl SUPPORT_IMPL;

   static {
      FragmentTransitionCompat21 var0;
      if (VERSION.SDK_INT >= 21) {
         var0 = new FragmentTransitionCompat21();
      } else {
         var0 = null;
      }

      PLATFORM_IMPL = var0;
      SUPPORT_IMPL = resolveSupportImpl();
   }

   private FragmentTransition() {
   }

   private static void addSharedElementsWithMatchingNames(ArrayList<View> var0, ArrayMap<String, View> var1, Collection<String> var2) {
      for(int var3 = var1.size() - 1; var3 >= 0; --var3) {
         View var4 = (View)var1.valueAt(var3);
         if (var2.contains(ViewCompat.getTransitionName(var4))) {
            var0.add(var4);
         }
      }

   }

   private static void addToFirstInLastOut(BackStackRecord var0, FragmentTransaction.Op var1, SparseArray<FragmentTransition.FragmentContainerTransition> var2, boolean var3, boolean var4) {
      Fragment var5 = var1.mFragment;
      if (var5 != null) {
         int var6 = var5.mContainerId;
         if (var6 != 0) {
            int var7;
            if (var3) {
               var7 = INVERSE_OPS[var1.mCmd];
            } else {
               var7 = var1.mCmd;
            }

            boolean var9;
            boolean var10;
            boolean var11;
            boolean var15;
            label145: {
               boolean var8;
               label144: {
                  label143: {
                     label142: {
                        label141: {
                           label140: {
                              label139: {
                                 label138: {
                                    label152: {
                                       var8 = false;
                                       var9 = false;
                                       if (var7 != 1) {
                                          if (var7 == 3) {
                                             break label139;
                                          }

                                          if (var7 == 4) {
                                             if (var4) {
                                                if (var5.mHiddenChanged && var5.mAdded && var5.mHidden) {
                                                   break label142;
                                                }
                                             } else if (var5.mAdded && !var5.mHidden) {
                                                break label142;
                                             }
                                             break label141;
                                          }

                                          if (var7 == 5) {
                                             if (!var4) {
                                                var9 = var5.mHidden;
                                                break label140;
                                             }

                                             if (var5.mHiddenChanged && !var5.mHidden && var5.mAdded) {
                                                break label138;
                                             }
                                             break label152;
                                          }

                                          if (var7 == 6) {
                                             break label139;
                                          }

                                          if (var7 != 7) {
                                             var15 = false;
                                             break label143;
                                          }
                                       }

                                       if (var4) {
                                          var9 = var5.mIsNewlyAdded;
                                          break label140;
                                       }

                                       if (!var5.mAdded && !var5.mHidden) {
                                          break label138;
                                       }
                                    }

                                    var9 = false;
                                    break label140;
                                 }

                                 var9 = true;
                                 break label140;
                              }

                              if (var4) {
                                 if (!var5.mAdded && var5.mView != null && var5.mView.getVisibility() == 0 && var5.mPostponedAlpha >= 0.0F) {
                                    break label142;
                                 }
                              } else if (var5.mAdded && !var5.mHidden) {
                                 break label142;
                              }
                              break label141;
                           }

                           var15 = true;
                           break label143;
                        }

                        var15 = false;
                        break label144;
                     }

                     var15 = true;
                     break label144;
                  }

                  var10 = false;
                  var11 = false;
                  break label145;
               }

               boolean var12 = false;
               var10 = true;
               var9 = var8;
               var11 = var15;
               var15 = var12;
            }

            FragmentTransition.FragmentContainerTransition var13 = (FragmentTransition.FragmentContainerTransition)var2.get(var6);
            FragmentTransition.FragmentContainerTransition var14 = var13;
            if (var9) {
               var14 = ensureContainer(var13, var2, var6);
               var14.lastIn = var5;
               var14.lastInIsPop = var3;
               var14.lastInTransaction = var0;
            }

            if (!var4 && var15) {
               if (var14 != null && var14.firstOut == var5) {
                  var14.firstOut = null;
               }

               FragmentManager var16 = var0.mManager;
               if (var5.mState < 1 && var16.mCurState >= 1 && !var0.mReorderingAllowed) {
                  var16.makeActive(var5);
                  var16.moveToState(var5, 1);
               }
            }

            var13 = var14;
            if (var11) {
               label90: {
                  if (var14 != null) {
                     var13 = var14;
                     if (var14.firstOut != null) {
                        break label90;
                     }
                  }

                  var13 = ensureContainer(var14, var2, var6);
                  var13.firstOut = var5;
                  var13.firstOutIsPop = var3;
                  var13.firstOutTransaction = var0;
               }
            }

            if (!var4 && var10 && var13 != null && var13.lastIn == var5) {
               var13.lastIn = null;
            }

         }
      }
   }

   public static void calculateFragments(BackStackRecord var0, SparseArray<FragmentTransition.FragmentContainerTransition> var1, boolean var2) {
      int var3 = var0.mOps.size();

      for(int var4 = 0; var4 < var3; ++var4) {
         addToFirstInLastOut(var0, (FragmentTransaction.Op)var0.mOps.get(var4), var1, false, var2);
      }

   }

   private static ArrayMap<String, String> calculateNameOverrides(int var0, ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2, int var3, int var4) {
      ArrayMap var5 = new ArrayMap();
      --var4;

      for(; var4 >= var3; --var4) {
         BackStackRecord var6 = (BackStackRecord)var1.get(var4);
         if (var6.interactsWith(var0)) {
            boolean var7 = (Boolean)var2.get(var4);
            if (var6.mSharedElementSourceNames != null) {
               int var8 = var6.mSharedElementSourceNames.size();
               ArrayList var9;
               ArrayList var10;
               if (var7) {
                  var9 = var6.mSharedElementSourceNames;
                  var10 = var6.mSharedElementTargetNames;
               } else {
                  var10 = var6.mSharedElementSourceNames;
                  var9 = var6.mSharedElementTargetNames;
               }

               for(int var11 = 0; var11 < var8; ++var11) {
                  String var14 = (String)var10.get(var11);
                  String var12 = (String)var9.get(var11);
                  String var13 = (String)var5.remove(var12);
                  if (var13 != null) {
                     var5.put(var14, var13);
                  } else {
                     var5.put(var14, var12);
                  }
               }
            }
         }
      }

      return var5;
   }

   public static void calculatePopFragments(BackStackRecord var0, SparseArray<FragmentTransition.FragmentContainerTransition> var1, boolean var2) {
      if (var0.mManager.mContainer.onHasView()) {
         for(int var3 = var0.mOps.size() - 1; var3 >= 0; --var3) {
            addToFirstInLastOut(var0, (FragmentTransaction.Op)var0.mOps.get(var3), var1, true, var2);
         }

      }
   }

   static void callSharedElementStartEnd(Fragment var0, Fragment var1, boolean var2, ArrayMap<String, View> var3, boolean var4) {
      SharedElementCallback var8;
      if (var2) {
         var8 = var1.getEnterTransitionCallback();
      } else {
         var8 = var0.getEnterTransitionCallback();
      }

      if (var8 != null) {
         ArrayList var9 = new ArrayList();
         ArrayList var5 = new ArrayList();
         int var6 = 0;
         int var7;
         if (var3 == null) {
            var7 = 0;
         } else {
            var7 = var3.size();
         }

         while(var6 < var7) {
            var5.add(var3.keyAt(var6));
            var9.add(var3.valueAt(var6));
            ++var6;
         }

         if (var4) {
            var8.onSharedElementStart(var5, var9, (List)null);
         } else {
            var8.onSharedElementEnd(var5, var9, (List)null);
         }
      }

   }

   private static boolean canHandleAll(FragmentTransitionImpl var0, List<Object> var1) {
      int var2 = var1.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         if (!var0.canHandle(var1.get(var3))) {
            return false;
         }
      }

      return true;
   }

   static ArrayMap<String, View> captureInSharedElements(FragmentTransitionImpl var0, ArrayMap<String, String> var1, Object var2, FragmentTransition.FragmentContainerTransition var3) {
      Fragment var4 = var3.lastIn;
      View var5 = var4.getView();
      if (!var1.isEmpty() && var2 != null && var5 != null) {
         ArrayMap var6 = new ArrayMap();
         var0.findNamedViews(var6, var5);
         BackStackRecord var8 = var3.lastInTransaction;
         ArrayList var9;
         SharedElementCallback var10;
         if (var3.lastInIsPop) {
            var10 = var4.getExitTransitionCallback();
            var9 = var8.mSharedElementSourceNames;
         } else {
            var10 = var4.getEnterTransitionCallback();
            var9 = var8.mSharedElementTargetNames;
         }

         if (var9 != null) {
            var6.retainAll(var9);
            var6.retainAll(var1.values());
         }

         if (var10 != null) {
            var10.onMapSharedElements(var9, var6);

            for(int var7 = var9.size() - 1; var7 >= 0; --var7) {
               String var11 = (String)var9.get(var7);
               View var12 = (View)var6.get(var11);
               if (var12 == null) {
                  String var13 = findKeyForValue(var1, var11);
                  if (var13 != null) {
                     var1.remove(var13);
                  }
               } else if (!var11.equals(ViewCompat.getTransitionName(var12))) {
                  var11 = findKeyForValue(var1, var11);
                  if (var11 != null) {
                     var1.put(var11, ViewCompat.getTransitionName(var12));
                  }
               }
            }
         } else {
            retainValues(var1, var6);
         }

         return var6;
      } else {
         var1.clear();
         return null;
      }
   }

   private static ArrayMap<String, View> captureOutSharedElements(FragmentTransitionImpl var0, ArrayMap<String, String> var1, Object var2, FragmentTransition.FragmentContainerTransition var3) {
      if (!var1.isEmpty() && var2 != null) {
         Fragment var8 = var3.firstOut;
         ArrayMap var4 = new ArrayMap();
         var0.findNamedViews(var4, var8.requireView());
         BackStackRecord var6 = var3.firstOutTransaction;
         ArrayList var7;
         SharedElementCallback var9;
         if (var3.firstOutIsPop) {
            var9 = var8.getEnterTransitionCallback();
            var7 = var6.mSharedElementTargetNames;
         } else {
            var9 = var8.getExitTransitionCallback();
            var7 = var6.mSharedElementSourceNames;
         }

         if (var7 != null) {
            var4.retainAll(var7);
         }

         if (var9 != null) {
            var9.onMapSharedElements(var7, var4);

            for(int var5 = var7.size() - 1; var5 >= 0; --var5) {
               String var10 = (String)var7.get(var5);
               View var11 = (View)var4.get(var10);
               if (var11 == null) {
                  var1.remove(var10);
               } else if (!var10.equals(ViewCompat.getTransitionName(var11))) {
                  var10 = (String)var1.remove(var10);
                  var1.put(ViewCompat.getTransitionName(var11), var10);
               }
            }
         } else {
            var1.retainAll(var4.keySet());
         }

         return var4;
      } else {
         var1.clear();
         return null;
      }
   }

   private static FragmentTransitionImpl chooseImpl(Fragment var0, Fragment var1) {
      ArrayList var2 = new ArrayList();
      Object var4;
      if (var0 != null) {
         Object var3 = var0.getExitTransition();
         if (var3 != null) {
            var2.add(var3);
         }

         var3 = var0.getReturnTransition();
         if (var3 != null) {
            var2.add(var3);
         }

         var4 = var0.getSharedElementReturnTransition();
         if (var4 != null) {
            var2.add(var4);
         }
      }

      if (var1 != null) {
         var4 = var1.getEnterTransition();
         if (var4 != null) {
            var2.add(var4);
         }

         var4 = var1.getReenterTransition();
         if (var4 != null) {
            var2.add(var4);
         }

         var4 = var1.getSharedElementEnterTransition();
         if (var4 != null) {
            var2.add(var4);
         }
      }

      if (var2.isEmpty()) {
         return null;
      } else {
         FragmentTransitionImpl var5 = PLATFORM_IMPL;
         if (var5 != null && canHandleAll(var5, var2)) {
            return PLATFORM_IMPL;
         } else {
            var5 = SUPPORT_IMPL;
            if (var5 != null && canHandleAll(var5, var2)) {
               return SUPPORT_IMPL;
            } else if (PLATFORM_IMPL == null && SUPPORT_IMPL == null) {
               return null;
            } else {
               throw new IllegalArgumentException("Invalid Transition types");
            }
         }
      }
   }

   static ArrayList<View> configureEnteringExitingViews(FragmentTransitionImpl var0, Object var1, Fragment var2, ArrayList<View> var3, View var4) {
      ArrayList var7;
      if (var1 != null) {
         ArrayList var5 = new ArrayList();
         View var6 = var2.getView();
         if (var6 != null) {
            var0.captureTransitioningViews(var5, var6);
         }

         if (var3 != null) {
            var5.removeAll(var3);
         }

         var7 = var5;
         if (!var5.isEmpty()) {
            var5.add(var4);
            var0.addTargets(var1, var5);
            var7 = var5;
         }
      } else {
         var7 = null;
      }

      return var7;
   }

   private static Object configureSharedElementsOrdered(final FragmentTransitionImpl var0, ViewGroup var1, final View var2, final ArrayMap<String, String> var3, final FragmentTransition.FragmentContainerTransition var4, final ArrayList<View> var5, final ArrayList<View> var6, final Object var7, Object var8) {
      final Fragment var9 = var4.lastIn;
      final Fragment var10 = var4.firstOut;
      if (var9 != null && var10 != null) {
         final boolean var11 = var4.lastInIsPop;
         final Object var12;
         if (var3.isEmpty()) {
            var12 = null;
         } else {
            var12 = getSharedElementTransition(var0, var9, var10, var11);
         }

         ArrayMap var13 = captureOutSharedElements(var0, var3, var12, var4);
         if (var3.isEmpty()) {
            var12 = null;
         } else {
            var5.addAll(var13.values());
         }

         if (var7 == null && var8 == null && var12 == null) {
            return null;
         } else {
            callSharedElementStartEnd(var9, var10, var11, var13, true);
            final Rect var15;
            if (var12 != null) {
               Rect var14 = new Rect();
               var0.setSharedElementTargets(var12, var2, var5);
               setOutEpicenter(var0, var12, var8, var13, var4.firstOutIsPop, var4.firstOutTransaction);
               var15 = var14;
               if (var7 != null) {
                  var0.setEpicenter(var7, var14);
                  var15 = var14;
               }
            } else {
               var15 = null;
            }

            OneShotPreDrawListener.add(var1, new Runnable() {
               public void run() {
                  ArrayMap var1 = FragmentTransition.captureInSharedElements(var0, var3, var12, var4);
                  if (var1 != null) {
                     var6.addAll(var1.values());
                     var6.add(var2);
                  }

                  FragmentTransition.callSharedElementStartEnd(var9, var10, var11, var1, false);
                  Object var2x = var12;
                  if (var2x != null) {
                     var0.swapSharedElementTargets(var2x, var5, var6);
                     View var3x = FragmentTransition.getInEpicenterView(var1, var4, var7, var11);
                     if (var3x != null) {
                        var0.getBoundsOnScreen(var3x, var15);
                     }
                  }

               }
            });
            return var12;
         }
      } else {
         return null;
      }
   }

   private static Object configureSharedElementsReordered(final FragmentTransitionImpl var0, ViewGroup var1, View var2, ArrayMap<String, String> var3, FragmentTransition.FragmentContainerTransition var4, ArrayList<View> var5, ArrayList<View> var6, Object var7, Object var8) {
      final Fragment var9 = var4.lastIn;
      final Fragment var10 = var4.firstOut;
      if (var9 != null) {
         var9.requireView().setVisibility(0);
      }

      if (var9 != null && var10 != null) {
         final boolean var11 = var4.lastInIsPop;
         Object var12;
         if (var3.isEmpty()) {
            var12 = null;
         } else {
            var12 = getSharedElementTransition(var0, var9, var10, var11);
         }

         ArrayMap var13 = captureOutSharedElements(var0, var3, var12, var4);
         final ArrayMap var14 = captureInSharedElements(var0, var3, var12, var4);
         Object var16;
         if (var3.isEmpty()) {
            if (var13 != null) {
               var13.clear();
            }

            if (var14 != null) {
               var14.clear();
            }

            var16 = null;
         } else {
            addSharedElementsWithMatchingNames(var5, var13, var3.keySet());
            addSharedElementsWithMatchingNames(var6, var14, var3.values());
            var16 = var12;
         }

         if (var7 == null && var8 == null && var16 == null) {
            return null;
         } else {
            callSharedElementStartEnd(var9, var10, var11, var13, true);
            final Object var15;
            final View var17;
            if (var16 != null) {
               var6.add(var2);
               var0.setSharedElementTargets(var16, var2, var5);
               setOutEpicenter(var0, var16, var8, var13, var4.firstOutIsPop, var4.firstOutTransaction);
               var15 = new Rect();
               var17 = getInEpicenterView(var14, var4, var7, var11);
               if (var17 != null) {
                  var0.setEpicenter(var7, (Rect)var15);
               }
            } else {
               var17 = null;
               var15 = var17;
            }

            OneShotPreDrawListener.add(var1, new Runnable() {
               public void run() {
                  FragmentTransition.callSharedElementStartEnd(var9, var10, var11, var14, false);
                  View var1 = var17;
                  if (var1 != null) {
                     var0.getBoundsOnScreen(var1, (Rect)var15);
                  }

               }
            });
            return var16;
         }
      } else {
         return null;
      }
   }

   private static void configureTransitionsOrdered(FragmentManager var0, int var1, FragmentTransition.FragmentContainerTransition var2, View var3, ArrayMap<String, String> var4, final FragmentTransition.Callback var5) {
      ViewGroup var17;
      if (var0.mContainer.onHasView()) {
         var17 = (ViewGroup)var0.mContainer.onFindViewById(var1);
      } else {
         var17 = null;
      }

      if (var17 != null) {
         Fragment var6 = var2.lastIn;
         final Fragment var7 = var2.firstOut;
         FragmentTransitionImpl var8 = chooseImpl(var7, var6);
         if (var8 != null) {
            boolean var9 = var2.lastInIsPop;
            boolean var10 = var2.firstOutIsPop;
            Object var11 = getEnterTransition(var8, var6, var9);
            Object var12 = getExitTransition(var8, var7, var10);
            ArrayList var13 = new ArrayList();
            ArrayList var14 = new ArrayList();
            Object var15 = configureSharedElementsOrdered(var8, var17, var3, var4, var2, var13, var14, var11, var12);
            if (var11 != null || var15 != null || var12 != null) {
               ArrayList var16 = configureEnteringExitingViews(var8, var12, var7, var13, var3);
               if (var16 == null || var16.isEmpty()) {
                  var12 = null;
               }

               var8.addTarget(var11, var3);
               Object var18 = mergeTransitions(var8, var11, var12, var15, var6, var2.lastInIsPop);
               if (var7 != null && var16 != null && (var16.size() > 0 || var13.size() > 0)) {
                  final CancellationSignal var20 = new CancellationSignal();
                  var5.onStart(var7, var20);
                  var8.setListenerForTransitionEnd(var7, var18, var20, new Runnable() {
                     public void run() {
                        var5.onComplete(var7, var20);
                     }
                  });
               }

               if (var18 != null) {
                  ArrayList var19 = new ArrayList();
                  var8.scheduleRemoveTargets(var18, var11, var19, var12, var16, var15, var14);
                  scheduleTargetChange(var8, var17, var6, var3, var14, var11, var19, var12, var16);
                  var8.setNameOverridesOrdered(var17, var14, var4);
                  var8.beginDelayedTransition(var17, var18);
                  var8.scheduleNameReset(var17, var14, var4);
               }

            }
         }
      }
   }

   private static void configureTransitionsReordered(FragmentManager var0, int var1, FragmentTransition.FragmentContainerTransition var2, View var3, ArrayMap<String, String> var4, final FragmentTransition.Callback var5) {
      ViewGroup var17;
      if (var0.mContainer.onHasView()) {
         var17 = (ViewGroup)var0.mContainer.onFindViewById(var1);
      } else {
         var17 = null;
      }

      if (var17 != null) {
         Fragment var6 = var2.lastIn;
         final Fragment var7 = var2.firstOut;
         FragmentTransitionImpl var8 = chooseImpl(var7, var6);
         if (var8 != null) {
            boolean var9 = var2.lastInIsPop;
            boolean var10 = var2.firstOutIsPop;
            ArrayList var11 = new ArrayList();
            ArrayList var12 = new ArrayList();
            Object var13 = getEnterTransition(var8, var6, var9);
            Object var14 = getExitTransition(var8, var7, var10);
            Object var15 = configureSharedElementsReordered(var8, var17, var3, var4, var2, var12, var11, var13, var14);
            if (var13 != null || var15 != null || var14 != null) {
               Object var18 = var14;
               ArrayList var22 = configureEnteringExitingViews(var8, var14, var7, var12, var3);
               ArrayList var19 = configureEnteringExitingViews(var8, var13, var6, var11, var3);
               setViewVisibility(var19, 4);
               Object var21 = mergeTransitions(var8, var13, var18, var15, var6, var9);
               if (var7 != null && var22 != null && (var22.size() > 0 || var12.size() > 0)) {
                  final CancellationSignal var16 = new CancellationSignal();
                  var5.onStart(var7, var16);
                  var8.setListenerForTransitionEnd(var7, var21, var16, new Runnable() {
                     public void run() {
                        var5.onComplete(var7, var16);
                     }
                  });
               }

               if (var21 != null) {
                  replaceHide(var8, var18, var7, var22);
                  ArrayList var20 = var8.prepareSetNameOverridesReordered(var11);
                  var8.scheduleRemoveTargets(var21, var13, var19, var18, var22, var15, var11);
                  var8.beginDelayedTransition(var17, var21);
                  var8.setNameOverridesReordered(var17, var12, var11, var20, var4);
                  setViewVisibility(var19, 0);
                  var8.swapSharedElementTargets(var15, var12, var11);
               }

            }
         }
      }
   }

   private static FragmentTransition.FragmentContainerTransition ensureContainer(FragmentTransition.FragmentContainerTransition var0, SparseArray<FragmentTransition.FragmentContainerTransition> var1, int var2) {
      FragmentTransition.FragmentContainerTransition var3 = var0;
      if (var0 == null) {
         var3 = new FragmentTransition.FragmentContainerTransition();
         var1.put(var2, var3);
      }

      return var3;
   }

   private static String findKeyForValue(ArrayMap<String, String> var0, String var1) {
      int var2 = var0.size();

      for(int var3 = 0; var3 < var2; ++var3) {
         if (var1.equals(var0.valueAt(var3))) {
            return (String)var0.keyAt(var3);
         }
      }

      return null;
   }

   private static Object getEnterTransition(FragmentTransitionImpl var0, Fragment var1, boolean var2) {
      if (var1 == null) {
         return null;
      } else {
         Object var3;
         if (var2) {
            var3 = var1.getReenterTransition();
         } else {
            var3 = var1.getEnterTransition();
         }

         return var0.cloneTransition(var3);
      }
   }

   private static Object getExitTransition(FragmentTransitionImpl var0, Fragment var1, boolean var2) {
      if (var1 == null) {
         return null;
      } else {
         Object var3;
         if (var2) {
            var3 = var1.getReturnTransition();
         } else {
            var3 = var1.getExitTransition();
         }

         return var0.cloneTransition(var3);
      }
   }

   static View getInEpicenterView(ArrayMap<String, View> var0, FragmentTransition.FragmentContainerTransition var1, Object var2, boolean var3) {
      BackStackRecord var4 = var1.lastInTransaction;
      if (var2 != null && var0 != null && var4.mSharedElementSourceNames != null && !var4.mSharedElementSourceNames.isEmpty()) {
         String var5;
         if (var3) {
            var5 = (String)var4.mSharedElementSourceNames.get(0);
         } else {
            var5 = (String)var4.mSharedElementTargetNames.get(0);
         }

         return (View)var0.get(var5);
      } else {
         return null;
      }
   }

   private static Object getSharedElementTransition(FragmentTransitionImpl var0, Fragment var1, Fragment var2, boolean var3) {
      if (var1 != null && var2 != null) {
         Object var4;
         if (var3) {
            var4 = var2.getSharedElementReturnTransition();
         } else {
            var4 = var1.getSharedElementEnterTransition();
         }

         return var0.wrapTransitionInSet(var0.cloneTransition(var4));
      } else {
         return null;
      }
   }

   private static Object mergeTransitions(FragmentTransitionImpl var0, Object var1, Object var2, Object var3, Fragment var4, boolean var5) {
      if (var1 != null && var2 != null && var4 != null) {
         if (var5) {
            var5 = var4.getAllowReturnTransitionOverlap();
         } else {
            var5 = var4.getAllowEnterTransitionOverlap();
         }
      } else {
         var5 = true;
      }

      Object var6;
      if (var5) {
         var6 = var0.mergeTransitionsTogether(var2, var1, var3);
      } else {
         var6 = var0.mergeTransitionsInSequence(var2, var1, var3);
      }

      return var6;
   }

   private static void replaceHide(FragmentTransitionImpl var0, Object var1, Fragment var2, final ArrayList<View> var3) {
      if (var2 != null && var1 != null && var2.mAdded && var2.mHidden && var2.mHiddenChanged) {
         var2.setHideReplaced(true);
         var0.scheduleHideFragmentView(var1, var2.getView(), var3);
         OneShotPreDrawListener.add(var2.mContainer, new Runnable() {
            public void run() {
               FragmentTransition.setViewVisibility(var3, 4);
            }
         });
      }

   }

   private static FragmentTransitionImpl resolveSupportImpl() {
      try {
         FragmentTransitionImpl var0 = (FragmentTransitionImpl)Class.forName("androidx.transition.FragmentTransitionSupport").getDeclaredConstructor().newInstance();
         return var0;
      } catch (Exception var1) {
         return null;
      }
   }

   private static void retainValues(ArrayMap<String, String> var0, ArrayMap<String, View> var1) {
      for(int var2 = var0.size() - 1; var2 >= 0; --var2) {
         if (!var1.containsKey((String)var0.valueAt(var2))) {
            var0.removeAt(var2);
         }
      }

   }

   private static void scheduleTargetChange(final FragmentTransitionImpl var0, ViewGroup var1, final Fragment var2, final View var3, final ArrayList<View> var4, final Object var5, final ArrayList<View> var6, final Object var7, final ArrayList<View> var8) {
      OneShotPreDrawListener.add(var1, new Runnable() {
         public void run() {
            Object var1 = var5;
            ArrayList var2x;
            if (var1 != null) {
               var0.removeTarget(var1, var3);
               var2x = FragmentTransition.configureEnteringExitingViews(var0, var5, var2, var4, var3);
               var6.addAll(var2x);
            }

            if (var8 != null) {
               if (var7 != null) {
                  var2x = new ArrayList();
                  var2x.add(var3);
                  var0.replaceTargets(var7, var8, var2x);
               }

               var8.clear();
               var8.add(var3);
            }

         }
      });
   }

   private static void setOutEpicenter(FragmentTransitionImpl var0, Object var1, Object var2, ArrayMap<String, View> var3, boolean var4, BackStackRecord var5) {
      if (var5.mSharedElementSourceNames != null && !var5.mSharedElementSourceNames.isEmpty()) {
         String var6;
         if (var4) {
            var6 = (String)var5.mSharedElementTargetNames.get(0);
         } else {
            var6 = (String)var5.mSharedElementSourceNames.get(0);
         }

         View var7 = (View)var3.get(var6);
         var0.setEpicenter(var1, var7);
         if (var2 != null) {
            var0.setEpicenter(var2, var7);
         }
      }

   }

   static void setViewVisibility(ArrayList<View> var0, int var1) {
      if (var0 != null) {
         for(int var2 = var0.size() - 1; var2 >= 0; --var2) {
            ((View)var0.get(var2)).setVisibility(var1);
         }

      }
   }

   static void startTransitions(FragmentManager var0, ArrayList<BackStackRecord> var1, ArrayList<Boolean> var2, int var3, int var4, boolean var5, FragmentTransition.Callback var6) {
      if (var0.mCurState >= 1) {
         SparseArray var7 = new SparseArray();

         int var8;
         for(var8 = var3; var8 < var4; ++var8) {
            BackStackRecord var9 = (BackStackRecord)var1.get(var8);
            if ((Boolean)var2.get(var8)) {
               calculatePopFragments(var9, var7, var5);
            } else {
               calculateFragments(var9, var7, var5);
            }
         }

         if (var7.size() != 0) {
            View var14 = new View(var0.mHost.getContext());
            int var10 = var7.size();

            for(var8 = 0; var8 < var10; ++var8) {
               int var11 = var7.keyAt(var8);
               ArrayMap var12 = calculateNameOverrides(var11, var1, var2, var3, var4);
               FragmentTransition.FragmentContainerTransition var13 = (FragmentTransition.FragmentContainerTransition)var7.valueAt(var8);
               if (var5) {
                  configureTransitionsReordered(var0, var11, var13, var14, var12, var6);
               } else {
                  configureTransitionsOrdered(var0, var11, var13, var14, var12, var6);
               }
            }
         }

      }
   }

   static boolean supportsTransition() {
      boolean var0;
      if (PLATFORM_IMPL == null && SUPPORT_IMPL == null) {
         var0 = false;
      } else {
         var0 = true;
      }

      return var0;
   }

   interface Callback {
      void onComplete(Fragment var1, CancellationSignal var2);

      void onStart(Fragment var1, CancellationSignal var2);
   }

   static class FragmentContainerTransition {
      public Fragment firstOut;
      public boolean firstOutIsPop;
      public BackStackRecord firstOutTransaction;
      public Fragment lastIn;
      public boolean lastInIsPop;
      public BackStackRecord lastInTransaction;
   }
}

package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.util.TypedValue;
import android.util.Xml;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import androidx.constraintlayout.motion.utils.Easing;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;
import androidx.constraintlayout.widget.R;
import androidx.constraintlayout.widget.StateSet;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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
   private ArrayList<MotionScene.Transition> mAbstractTransitionList = new ArrayList();
   private HashMap<String, Integer> mConstraintSetIdMap = new HashMap();
   private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray();
   MotionScene.Transition mCurrentTransition = null;
   private int mDefaultDuration = 400;
   private MotionScene.Transition mDefaultTransition = null;
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
   private ArrayList<MotionScene.Transition> mTransitionList = new ArrayList();
   private MotionLayout.MotionTracker mVelocityTracker;

   MotionScene(Context var1, MotionLayout var2, int var3) {
      this.mMotionLayout = var2;
      this.load(var1, var3);
      this.mConstraintSetMap.put(R.id.motion_base, new ConstraintSet());
      this.mConstraintSetIdMap.put("motion_base", R.id.motion_base);
   }

   public MotionScene(MotionLayout var1) {
      this.mMotionLayout = var1;
   }

   private int getId(Context var1, String var2) {
      int var4;
      int var5;
      if (var2.contains("/")) {
         String var3 = var2.substring(var2.indexOf(47) + 1);
         var4 = var1.getResources().getIdentifier(var3, "id", var1.getPackageName());
         var5 = var4;
         if (this.DEBUG_DESKTOP) {
            PrintStream var6 = System.out;
            StringBuilder var7 = new StringBuilder();
            var7.append("id getMap res = ");
            var7.append(var4);
            var6.println(var7.toString());
            var5 = var4;
         }
      } else {
         var5 = -1;
      }

      var4 = var5;
      if (var5 == -1) {
         if (var2 != null && var2.length() > 1) {
            var4 = Integer.parseInt(var2.substring(1));
         } else {
            Log.e("MotionScene", "error in parsing id");
            var4 = var5;
         }
      }

      return var4;
   }

   private int getIndex(MotionScene.Transition var1) {
      int var2 = var1.mId;
      if (var2 != -1) {
         for(int var3 = 0; var3 < this.mTransitionList.size(); ++var3) {
            if (((MotionScene.Transition)this.mTransitionList.get(var3)).mId == var2) {
               return var3;
            }
         }

         return -1;
      } else {
         throw new IllegalArgumentException("The transition must have an id");
      }
   }

   private int getRealID(int var1) {
      StateSet var2 = this.mStateSet;
      if (var2 != null) {
         int var3 = var2.stateGetConstraintID(var1, -1, -1);
         if (var3 != -1) {
            return var3;
         }
      }

      return var1;
   }

   private boolean hasCycleDependency(int var1) {
      int var2 = this.mDeriveMap.get(var1);

      for(int var3 = this.mDeriveMap.size(); var2 > 0; --var3) {
         if (var2 == var1) {
            return true;
         }

         if (var3 < 0) {
            return true;
         }

         var2 = this.mDeriveMap.get(var2);
      }

      return false;
   }

   private boolean isProcessingTouch() {
      boolean var1;
      if (this.mVelocityTracker != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   private void load(Context var1, int var2) {
      XmlResourceParser var3 = var1.getResources().getXml(var2);
      MotionScene.Transition var4 = null;

      XmlPullParserException var69;
      label285: {
         IOException var10000;
         label265: {
            int var5;
            boolean var10001;
            try {
               var5 = var3.getEventType();
            } catch (XmlPullParserException var58) {
               var69 = var58;
               var10001 = false;
               break label285;
            } catch (IOException var59) {
               var10000 = var59;
               var10001 = false;
               break label265;
            }

            label262:
            while(true) {
               byte var6 = 1;
               if (var5 == 1) {
                  return;
               }

               if (var5 != 0) {
                  if (var5 == 2) {
                     String var7;
                     StringBuilder var9;
                     try {
                        var7 = var3.getName();
                        if (this.DEBUG_DESKTOP) {
                           PrintStream var8 = System.out;
                           var9 = new StringBuilder();
                           var9.append("parsing = ");
                           var9.append(var7);
                           var8.println(var9.toString());
                        }
                     } catch (XmlPullParserException var56) {
                        var69 = var56;
                        var10001 = false;
                        break label285;
                     } catch (IOException var57) {
                        var10000 = var57;
                        var10001 = false;
                        break;
                     }

                     try {
                        var5 = var7.hashCode();
                     } catch (XmlPullParserException var36) {
                        var69 = var36;
                        var10001 = false;
                        break label285;
                     } catch (IOException var37) {
                        var10000 = var37;
                        var10001 = false;
                        break;
                     }

                     byte var62;
                     label250: {
                        label249: {
                           label248: {
                              label247: {
                                 label246: {
                                    label245: {
                                       switch(var5) {
                                       case -1349929691:
                                          try {
                                             if (var7.equals("ConstraintSet")) {
                                                break label245;
                                             }
                                             break;
                                          } catch (XmlPullParserException var50) {
                                             var69 = var50;
                                             var10001 = false;
                                             break label285;
                                          } catch (IOException var51) {
                                             var10000 = var51;
                                             var10001 = false;
                                             break label262;
                                          }
                                       case -1239391468:
                                          try {
                                             if (var7.equals("KeyFrameSet")) {
                                                break label246;
                                             }
                                             break;
                                          } catch (XmlPullParserException var48) {
                                             var69 = var48;
                                             var10001 = false;
                                             break label285;
                                          } catch (IOException var49) {
                                             var10000 = var49;
                                             var10001 = false;
                                             break label262;
                                          }
                                       case 269306229:
                                          try {
                                             if (var7.equals("Transition")) {
                                                break label247;
                                             }
                                             break;
                                          } catch (XmlPullParserException var46) {
                                             var69 = var46;
                                             var10001 = false;
                                             break label285;
                                          } catch (IOException var47) {
                                             var10000 = var47;
                                             var10001 = false;
                                             break label262;
                                          }
                                       case 312750793:
                                          try {
                                             if (var7.equals("OnClick")) {
                                                break label248;
                                             }
                                             break;
                                          } catch (XmlPullParserException var44) {
                                             var69 = var44;
                                             var10001 = false;
                                             break label285;
                                          } catch (IOException var45) {
                                             var10000 = var45;
                                             var10001 = false;
                                             break label262;
                                          }
                                       case 327855227:
                                          try {
                                             if (var7.equals("OnSwipe")) {
                                                break label249;
                                             }
                                             break;
                                          } catch (XmlPullParserException var42) {
                                             var69 = var42;
                                             var10001 = false;
                                             break label285;
                                          } catch (IOException var43) {
                                             var10000 = var43;
                                             var10001 = false;
                                             break label262;
                                          }
                                       case 793277014:
                                          try {
                                             if (!var7.equals("MotionScene")) {
                                                break;
                                             }
                                          } catch (XmlPullParserException var54) {
                                             var69 = var54;
                                             var10001 = false;
                                             break label285;
                                          } catch (IOException var55) {
                                             var10000 = var55;
                                             var10001 = false;
                                             break label262;
                                          }

                                          var62 = 0;
                                          break label250;
                                       case 1382829617:
                                          label238: {
                                             try {
                                                if (!var7.equals("StateSet")) {
                                                   break label238;
                                                }
                                             } catch (XmlPullParserException var52) {
                                                var69 = var52;
                                                var10001 = false;
                                                break label285;
                                             } catch (IOException var53) {
                                                var10000 = var53;
                                                var10001 = false;
                                                break label262;
                                             }

                                             var62 = 4;
                                             break label250;
                                          }
                                       }

                                       var62 = -1;
                                       break label250;
                                    }

                                    var62 = 5;
                                    break label250;
                                 }

                                 var62 = 6;
                                 break label250;
                              }

                              var62 = var6;
                              break label250;
                           }

                           var62 = 3;
                           break label250;
                        }

                        var62 = 2;
                     }

                     switch(var62) {
                     case 0:
                        try {
                           this.parseMotionSceneTags(var1, var3);
                           break;
                        } catch (XmlPullParserException var16) {
                           var69 = var16;
                           var10001 = false;
                           break label285;
                        } catch (IOException var17) {
                           var10000 = var17;
                           var10001 = false;
                           break label262;
                        }
                     case 1:
                        label216: {
                           try {
                              ArrayList var67 = this.mTransitionList;
                              var4 = new MotionScene.Transition(this, var1, var3);
                              var67.add(var4);
                              if (this.mCurrentTransition != null || var4.mIsAbstract) {
                                 break label216;
                              }

                              this.mCurrentTransition = var4;
                           } catch (XmlPullParserException var40) {
                              var69 = var40;
                              var10001 = false;
                              break label285;
                           } catch (IOException var41) {
                              var10000 = var41;
                              var10001 = false;
                              break label262;
                           }

                           if (var4 != null) {
                              try {
                                 if (var4.mTouchResponse != null) {
                                    this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
                                 }
                              } catch (XmlPullParserException var22) {
                                 var69 = var22;
                                 var10001 = false;
                                 break label285;
                              } catch (IOException var23) {
                                 var10000 = var23;
                                 var10001 = false;
                                 break label262;
                              }
                           }
                        }

                        label206: {
                           try {
                              if (!var4.mIsAbstract) {
                                 break;
                              }

                              if (var4.mConstraintSetEnd == -1) {
                                 this.mDefaultTransition = var4;
                                 break label206;
                              }
                           } catch (XmlPullParserException var38) {
                              var69 = var38;
                              var10001 = false;
                              break label285;
                           } catch (IOException var39) {
                              var10000 = var39;
                              var10001 = false;
                              break label262;
                           }

                           try {
                              this.mAbstractTransitionList.add(var4);
                           } catch (XmlPullParserException var20) {
                              var69 = var20;
                              var10001 = false;
                              break label285;
                           } catch (IOException var21) {
                              var10000 = var21;
                              var10001 = false;
                              break label262;
                           }
                        }

                        try {
                           this.mTransitionList.remove(var4);
                           break;
                        } catch (XmlPullParserException var18) {
                           var69 = var18;
                           var10001 = false;
                           break label285;
                        } catch (IOException var19) {
                           var10000 = var19;
                           var10001 = false;
                           break label262;
                        }
                     case 2:
                        if (var4 == null) {
                           try {
                              String var68 = var1.getResources().getResourceEntryName(var2);
                              var5 = var3.getLineNumber();
                              StringBuilder var65 = new StringBuilder();
                              var65.append(" OnSwipe (");
                              var65.append(var68);
                              var65.append(".xml:");
                              var65.append(var5);
                              var65.append(")");
                              Log.v("MotionScene", var65.toString());
                           } catch (XmlPullParserException var26) {
                              var69 = var26;
                              var10001 = false;
                              break label285;
                           } catch (IOException var27) {
                              var10000 = var27;
                              var10001 = false;
                              break label262;
                           }
                        }

                        try {
                           TouchResponse var66 = new TouchResponse(var1, this.mMotionLayout, var3);
                           var4.mTouchResponse = var66;
                           break;
                        } catch (XmlPullParserException var24) {
                           var69 = var24;
                           var10001 = false;
                           break label285;
                        } catch (IOException var25) {
                           var10000 = var25;
                           var10001 = false;
                           break label262;
                        }
                     case 3:
                        try {
                           var4.addOnClick(var1, var3);
                           break;
                        } catch (XmlPullParserException var28) {
                           var69 = var28;
                           var10001 = false;
                           break label285;
                        } catch (IOException var29) {
                           var10000 = var29;
                           var10001 = false;
                           break label262;
                        }
                     case 4:
                        try {
                           StateSet var64 = new StateSet(var1, var3);
                           this.mStateSet = var64;
                           break;
                        } catch (XmlPullParserException var30) {
                           var69 = var30;
                           var10001 = false;
                           break label285;
                        } catch (IOException var31) {
                           var10000 = var31;
                           var10001 = false;
                           break label262;
                        }
                     case 5:
                        try {
                           this.parseConstraintSet(var1, var3);
                           break;
                        } catch (XmlPullParserException var32) {
                           var69 = var32;
                           var10001 = false;
                           break label285;
                        } catch (IOException var33) {
                           var10000 = var33;
                           var10001 = false;
                           break label262;
                        }
                     case 6:
                        try {
                           KeyFrames var63 = new KeyFrames(var1, var3);
                           var4.mKeyFramesList.add(var63);
                           break;
                        } catch (XmlPullParserException var34) {
                           var69 = var34;
                           var10001 = false;
                           break label285;
                        } catch (IOException var35) {
                           var10000 = var35;
                           var10001 = false;
                           break label262;
                        }
                     default:
                        try {
                           var9 = new StringBuilder();
                           var9.append("WARNING UNKNOWN ATTRIBUTE ");
                           var9.append(var7);
                           Log.v("MotionScene", var9.toString());
                        } catch (XmlPullParserException var14) {
                           var69 = var14;
                           var10001 = false;
                           break label285;
                        } catch (IOException var15) {
                           var10000 = var15;
                           var10001 = false;
                           break label262;
                        }
                     }
                  }
               } else {
                  try {
                     var3.getName();
                  } catch (XmlPullParserException var12) {
                     var69 = var12;
                     var10001 = false;
                     break label285;
                  } catch (IOException var13) {
                     var10000 = var13;
                     var10001 = false;
                     break;
                  }
               }

               try {
                  var5 = var3.next();
               } catch (XmlPullParserException var10) {
                  var69 = var10;
                  var10001 = false;
                  break label285;
               } catch (IOException var11) {
                  var10000 = var11;
                  var10001 = false;
                  break;
               }
            }
         }

         IOException var60 = var10000;
         var60.printStackTrace();
         return;
      }

      XmlPullParserException var61 = var69;
      var61.printStackTrace();
   }

   private void parseConstraintSet(Context var1, XmlPullParser var2) {
      ConstraintSet var3 = new ConstraintSet();
      var3.setForceId(false);
      int var4 = var2.getAttributeCount();
      int var5 = 0;
      int var6 = -1;

      int var7;
      for(var7 = -1; var5 < var4; ++var5) {
         String var8 = var2.getAttributeName(var5);
         String var9 = var2.getAttributeValue(var5);
         if (this.DEBUG_DESKTOP) {
            PrintStream var10 = System.out;
            StringBuilder var11 = new StringBuilder();
            var11.append("id string = ");
            var11.append(var9);
            var10.println(var11.toString());
         }

         byte var13;
         label46: {
            int var12 = var8.hashCode();
            if (var12 != -1496482599) {
               if (var12 == 3355 && var8.equals("id")) {
                  var13 = 0;
                  break label46;
               }
            } else if (var8.equals("deriveConstraintsFrom")) {
               var13 = 1;
               break label46;
            }

            var13 = -1;
         }

         if (var13 != 0) {
            if (var13 == 1) {
               var7 = this.getId(var1, var9);
            }
         } else {
            var6 = this.getId(var1, var9);
            this.mConstraintSetIdMap.put(stripID(var9), var6);
         }
      }

      if (var6 != -1) {
         if (this.mMotionLayout.mDebugPath != 0) {
            var3.setValidateOnParse(true);
         }

         var3.load(var1, var2);
         if (var7 != -1) {
            this.mDeriveMap.put(var6, var7);
         }

         this.mConstraintSetMap.put(var6, var3);
      }

   }

   private void parseMotionSceneTags(Context var1, XmlPullParser var2) {
      TypedArray var6 = var1.obtainStyledAttributes(Xml.asAttributeSet(var2), R.styleable.MotionScene);
      int var3 = var6.getIndexCount();

      for(int var4 = 0; var4 < var3; ++var4) {
         int var5 = var6.getIndex(var4);
         if (var5 == R.styleable.MotionScene_defaultDuration) {
            this.mDefaultDuration = var6.getInt(var5, this.mDefaultDuration);
         } else if (var5 == R.styleable.MotionScene_layoutDuringTransition) {
            this.mLayoutDuringTransition = var6.getInteger(var5, 0);
         }
      }

      var6.recycle();
   }

   private void readConstraintChain(int var1) {
      int var2 = this.mDeriveMap.get(var1);
      if (var2 > 0) {
         this.readConstraintChain(this.mDeriveMap.get(var1));
         ConstraintSet var3 = (ConstraintSet)this.mConstraintSetMap.get(var1);
         ConstraintSet var4 = (ConstraintSet)this.mConstraintSetMap.get(var2);
         if (var4 == null) {
            StringBuilder var5 = new StringBuilder();
            var5.append("ERROR! invalid deriveConstraintsFrom: @id/");
            var5.append(Debug.getName(this.mMotionLayout.getContext(), var2));
            Log.e("MotionScene", var5.toString());
            return;
         }

         var3.readFallback(var4);
         this.mDeriveMap.put(var1, -1);
      }

   }

   public static String stripID(String var0) {
      if (var0 == null) {
         return "";
      } else {
         int var1 = var0.indexOf(47);
         return var1 < 0 ? var0 : var0.substring(var1 + 1);
      }
   }

   public void addOnClickListeners(MotionLayout var1, int var2) {
      Iterator var3 = this.mTransitionList.iterator();

      while(true) {
         MotionScene.Transition var4;
         Iterator var6;
         do {
            if (!var3.hasNext()) {
               var3 = this.mAbstractTransitionList.iterator();

               while(true) {
                  do {
                     if (!var3.hasNext()) {
                        Iterator var5 = this.mTransitionList.iterator();

                        while(true) {
                           do {
                              if (!var5.hasNext()) {
                                 var5 = this.mAbstractTransitionList.iterator();

                                 while(true) {
                                    do {
                                       if (!var5.hasNext()) {
                                          return;
                                       }

                                       var4 = (MotionScene.Transition)var5.next();
                                    } while(var4.mOnClicks.size() <= 0);

                                    var3 = var4.mOnClicks.iterator();

                                    while(var3.hasNext()) {
                                       ((MotionScene.Transition.TransitionOnClick)var3.next()).addOnClickListeners(var1, var2, var4);
                                    }
                                 }
                              }

                              var4 = (MotionScene.Transition)var5.next();
                           } while(var4.mOnClicks.size() <= 0);

                           var3 = var4.mOnClicks.iterator();

                           while(var3.hasNext()) {
                              ((MotionScene.Transition.TransitionOnClick)var3.next()).addOnClickListeners(var1, var2, var4);
                           }
                        }
                     }

                     var4 = (MotionScene.Transition)var3.next();
                  } while(var4.mOnClicks.size() <= 0);

                  var6 = var4.mOnClicks.iterator();

                  while(var6.hasNext()) {
                     ((MotionScene.Transition.TransitionOnClick)var6.next()).removeOnClickListeners(var1);
                  }
               }
            }

            var4 = (MotionScene.Transition)var3.next();
         } while(var4.mOnClicks.size() <= 0);

         var6 = var4.mOnClicks.iterator();

         while(var6.hasNext()) {
            ((MotionScene.Transition.TransitionOnClick)var6.next()).removeOnClickListeners(var1);
         }
      }
   }

   public void addTransition(MotionScene.Transition var1) {
      int var2 = this.getIndex(var1);
      if (var2 == -1) {
         this.mTransitionList.add(var1);
      } else {
         this.mTransitionList.set(var2, var1);
      }

   }

   boolean autoTransition(MotionLayout var1, int var2) {
      if (this.isProcessingTouch()) {
         return false;
      } else if (this.mDisableAutoTransition) {
         return false;
      } else {
         Iterator var3 = this.mTransitionList.iterator();

         while(true) {
            MotionScene.Transition var4;
            do {
               if (!var3.hasNext()) {
                  return false;
               }

               var4 = (MotionScene.Transition)var3.next();
            } while(var4.mAutoTransition == 0);

            if (var2 != var4.mConstraintSetStart || var4.mAutoTransition != 4 && var4.mAutoTransition != 2) {
               if (var2 != var4.mConstraintSetEnd || var4.mAutoTransition != 3 && var4.mAutoTransition != 1) {
                  continue;
               }

               var1.setState(MotionLayout.TransitionState.FINISHED);
               var1.setTransition(var4);
               if (var4.mAutoTransition == 3) {
                  var1.transitionToStart();
                  var1.setState(MotionLayout.TransitionState.SETUP);
                  var1.setState(MotionLayout.TransitionState.MOVING);
               } else {
                  var1.setProgress(0.0F);
                  var1.evaluate(true);
                  var1.setState(MotionLayout.TransitionState.SETUP);
                  var1.setState(MotionLayout.TransitionState.MOVING);
                  var1.setState(MotionLayout.TransitionState.FINISHED);
               }

               return true;
            }

            var1.setState(MotionLayout.TransitionState.FINISHED);
            var1.setTransition(var4);
            if (var4.mAutoTransition == 4) {
               var1.transitionToEnd();
               var1.setState(MotionLayout.TransitionState.SETUP);
               var1.setState(MotionLayout.TransitionState.MOVING);
            } else {
               var1.setProgress(1.0F);
               var1.evaluate(true);
               var1.setState(MotionLayout.TransitionState.SETUP);
               var1.setState(MotionLayout.TransitionState.MOVING);
               var1.setState(MotionLayout.TransitionState.FINISHED);
            }

            return true;
         }
      }
   }

   public MotionScene.Transition bestTransitionFor(int var1, float var2, float var3, MotionEvent var4) {
      if (var1 == -1) {
         return this.mCurrentTransition;
      } else {
         List var5 = this.getTransitionsWithState(var1);
         float var6 = 0.0F;
         MotionScene.Transition var7 = null;
         RectF var8 = new RectF();
         Iterator var9 = var5.iterator();

         while(true) {
            RectF var10;
            MotionScene.Transition var13;
            do {
               do {
                  do {
                     do {
                        if (!var9.hasNext()) {
                           return var7;
                        }

                        var13 = (MotionScene.Transition)var9.next();
                     } while(var13.mDisable);
                  } while(var13.mTouchResponse == null);

                  var13.mTouchResponse.setRTL(this.mRtl);
                  var10 = var13.mTouchResponse.getTouchRegion(this.mMotionLayout, var8);
               } while(var10 != null && var4 != null && !var10.contains(var4.getX(), var4.getY()));

               var10 = var13.mTouchResponse.getTouchRegion(this.mMotionLayout, var8);
            } while(var10 != null && var4 != null && !var10.contains(var4.getX(), var4.getY()));

            float var11 = var13.mTouchResponse.dot(var2, var3);
            float var12;
            if (var13.mConstraintSetEnd == var1) {
               var12 = -1.0F;
            } else {
               var12 = 1.1F;
            }

            var12 = var11 * var12;
            if (var12 > var6) {
               var7 = var13;
               var6 = var12;
            }
         }
      }
   }

   public void disableAutoTransition(boolean var1) {
      this.mDisableAutoTransition = var1;
   }

   public int gatPathMotionArc() {
      MotionScene.Transition var1 = this.mCurrentTransition;
      int var2;
      if (var1 != null) {
         var2 = var1.mPathMotionArc;
      } else {
         var2 = -1;
      }

      return var2;
   }

   ConstraintSet getConstraintSet(int var1) {
      return this.getConstraintSet(var1, -1, -1);
   }

   ConstraintSet getConstraintSet(int var1, int var2, int var3) {
      StringBuilder var7;
      if (this.DEBUG_DESKTOP) {
         PrintStream var4 = System.out;
         StringBuilder var5 = new StringBuilder();
         var5.append("id ");
         var5.append(var1);
         var4.println(var5.toString());
         PrintStream var9 = System.out;
         var7 = new StringBuilder();
         var7.append("size ");
         var7.append(this.mConstraintSetMap.size());
         var9.println(var7.toString());
      }

      StateSet var8 = this.mStateSet;
      int var6 = var1;
      if (var8 != null) {
         var2 = var8.stateGetConstraintID(var1, var2, var3);
         var6 = var1;
         if (var2 != -1) {
            var6 = var2;
         }
      }

      if (this.mConstraintSetMap.get(var6) == null) {
         var7 = new StringBuilder();
         var7.append("Warning could not find ConstraintSet id/");
         var7.append(Debug.getName(this.mMotionLayout.getContext(), var6));
         var7.append(" In MotionScene");
         Log.e("MotionScene", var7.toString());
         SparseArray var10 = this.mConstraintSetMap;
         return (ConstraintSet)var10.get(var10.keyAt(0));
      } else {
         return (ConstraintSet)this.mConstraintSetMap.get(var6);
      }
   }

   public ConstraintSet getConstraintSet(Context var1, String var2) {
      PrintStream var3;
      if (this.DEBUG_DESKTOP) {
         var3 = System.out;
         StringBuilder var4 = new StringBuilder();
         var4.append("id ");
         var4.append(var2);
         var3.println(var4.toString());
         PrintStream var9 = System.out;
         StringBuilder var8 = new StringBuilder();
         var8.append("size ");
         var8.append(this.mConstraintSetMap.size());
         var9.println(var8.toString());
      }

      for(int var5 = 0; var5 < this.mConstraintSetMap.size(); ++var5) {
         int var6 = this.mConstraintSetMap.keyAt(var5);
         String var10 = var1.getResources().getResourceName(var6);
         if (this.DEBUG_DESKTOP) {
            var3 = System.out;
            StringBuilder var7 = new StringBuilder();
            var7.append("Id for <");
            var7.append(var5);
            var7.append("> is <");
            var7.append(var10);
            var7.append("> looking for <");
            var7.append(var2);
            var7.append(">");
            var3.println(var7.toString());
         }

         if (var2.equals(var10)) {
            return (ConstraintSet)this.mConstraintSetMap.get(var6);
         }
      }

      return null;
   }

   public int[] getConstraintSetIds() {
      int var1 = this.mConstraintSetMap.size();
      int[] var2 = new int[var1];

      for(int var3 = 0; var3 < var1; ++var3) {
         var2[var3] = this.mConstraintSetMap.keyAt(var3);
      }

      return var2;
   }

   public ArrayList<MotionScene.Transition> getDefinedTransitions() {
      return this.mTransitionList;
   }

   public int getDuration() {
      MotionScene.Transition var1 = this.mCurrentTransition;
      return var1 != null ? var1.mDuration : this.mDefaultDuration;
   }

   int getEndId() {
      MotionScene.Transition var1 = this.mCurrentTransition;
      return var1 == null ? -1 : var1.mConstraintSetEnd;
   }

   public Interpolator getInterpolator() {
      int var1 = this.mCurrentTransition.mDefaultInterpolator;
      if (var1 != -2) {
         if (var1 != -1) {
            if (var1 != 0) {
               if (var1 != 1) {
                  if (var1 != 2) {
                     if (var1 != 4) {
                        return var1 != 5 ? null : new BounceInterpolator();
                     } else {
                        return new AnticipateInterpolator();
                     }
                  } else {
                     return new DecelerateInterpolator();
                  }
               } else {
                  return new AccelerateInterpolator();
               }
            } else {
               return new AccelerateDecelerateInterpolator();
            }
         } else {
            return new Interpolator(Easing.getInterpolator(this.mCurrentTransition.mDefaultInterpolatorString)) {
               // $FF: synthetic field
               final Easing val$easing;

               {
                  this.val$easing = var2;
               }

               public float getInterpolation(float var1) {
                  return (float)this.val$easing.get((double)var1);
               }
            };
         }
      } else {
         return AnimationUtils.loadInterpolator(this.mMotionLayout.getContext(), this.mCurrentTransition.mDefaultInterpolatorID);
      }
   }

   Key getKeyFrame(Context var1, int var2, int var3, int var4) {
      MotionScene.Transition var9 = this.mCurrentTransition;
      if (var9 == null) {
         return null;
      } else {
         Iterator var5 = var9.mKeyFramesList.iterator();

         label37:
         while(var5.hasNext()) {
            KeyFrames var6 = (KeyFrames)var5.next();
            Iterator var10 = var6.getKeys().iterator();

            while(true) {
               Integer var7;
               do {
                  if (!var10.hasNext()) {
                     continue label37;
                  }

                  var7 = (Integer)var10.next();
               } while(var3 != var7);

               Iterator var8 = var6.getKeyFramesForView(var7).iterator();

               while(var8.hasNext()) {
                  Key var11 = (Key)var8.next();
                  if (var11.mFramePosition == var4 && var11.mType == var2) {
                     return var11;
                  }
               }
            }
         }

         return null;
      }
   }

   public void getKeyFrames(MotionController var1) {
      MotionScene.Transition var2 = this.mCurrentTransition;
      Iterator var3;
      if (var2 == null) {
         var2 = this.mDefaultTransition;
         if (var2 != null) {
            var3 = var2.mKeyFramesList.iterator();

            while(var3.hasNext()) {
               ((KeyFrames)var3.next()).addFrames(var1);
            }
         }

      } else {
         var3 = var2.mKeyFramesList.iterator();

         while(var3.hasNext()) {
            ((KeyFrames)var3.next()).addFrames(var1);
         }

      }
   }

   float getMaxAcceleration() {
      MotionScene.Transition var1 = this.mCurrentTransition;
      return var1 != null && var1.mTouchResponse != null ? this.mCurrentTransition.mTouchResponse.getMaxAcceleration() : 0.0F;
   }

   float getMaxVelocity() {
      MotionScene.Transition var1 = this.mCurrentTransition;
      return var1 != null && var1.mTouchResponse != null ? this.mCurrentTransition.mTouchResponse.getMaxVelocity() : 0.0F;
   }

   boolean getMoveWhenScrollAtTop() {
      MotionScene.Transition var1 = this.mCurrentTransition;
      return var1 != null && var1.mTouchResponse != null ? this.mCurrentTransition.mTouchResponse.getMoveWhenScrollAtTop() : false;
   }

   public float getPathPercent(View var1, int var2) {
      return 0.0F;
   }

   float getProgressDirection(float var1, float var2) {
      MotionScene.Transition var3 = this.mCurrentTransition;
      return var3 != null && var3.mTouchResponse != null ? this.mCurrentTransition.mTouchResponse.getProgressDirection(var1, var2) : 0.0F;
   }

   public float getStaggered() {
      MotionScene.Transition var1 = this.mCurrentTransition;
      return var1 != null ? var1.mStagger : 0.0F;
   }

   int getStartId() {
      MotionScene.Transition var1 = this.mCurrentTransition;
      return var1 == null ? -1 : var1.mConstraintSetStart;
   }

   public MotionScene.Transition getTransitionById(int var1) {
      Iterator var2 = this.mTransitionList.iterator();

      MotionScene.Transition var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (MotionScene.Transition)var2.next();
      } while(var3.mId != var1);

      return var3;
   }

   int getTransitionDirection(int var1) {
      Iterator var2 = this.mTransitionList.iterator();

      do {
         if (!var2.hasNext()) {
            return 1;
         }
      } while(((MotionScene.Transition)var2.next()).mConstraintSetStart != var1);

      return 0;
   }

   public List<MotionScene.Transition> getTransitionsWithState(int var1) {
      var1 = this.getRealID(var1);
      ArrayList var2 = new ArrayList();
      Iterator var3 = this.mTransitionList.iterator();

      while(true) {
         MotionScene.Transition var4;
         do {
            if (!var3.hasNext()) {
               return var2;
            }

            var4 = (MotionScene.Transition)var3.next();
         } while(var4.mConstraintSetStart != var1 && var4.mConstraintSetEnd != var1);

         var2.add(var4);
      }
   }

   boolean hasKeyFramePosition(View var1, int var2) {
      MotionScene.Transition var3 = this.mCurrentTransition;
      if (var3 == null) {
         return false;
      } else {
         Iterator var4 = var3.mKeyFramesList.iterator();

         while(var4.hasNext()) {
            Iterator var5 = ((KeyFrames)var4.next()).getKeyFramesForView(var1.getId()).iterator();

            while(var5.hasNext()) {
               if (((Key)var5.next()).mFramePosition == var2) {
                  return true;
               }
            }
         }

         return false;
      }
   }

   public int lookUpConstraintId(String var1) {
      return (Integer)this.mConstraintSetIdMap.get(var1);
   }

   public String lookUpConstraintName(int var1) {
      Iterator var2 = this.mConstraintSetIdMap.entrySet().iterator();

      Entry var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (Entry)var2.next();
      } while((Integer)var3.getValue() != var1);

      return (String)var3.getKey();
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
   }

   void processScrollMove(float var1, float var2) {
      MotionScene.Transition var3 = this.mCurrentTransition;
      if (var3 != null && var3.mTouchResponse != null) {
         this.mCurrentTransition.mTouchResponse.scrollMove(var1, var2);
      }

   }

   void processScrollUp(float var1, float var2) {
      MotionScene.Transition var3 = this.mCurrentTransition;
      if (var3 != null && var3.mTouchResponse != null) {
         this.mCurrentTransition.mTouchResponse.scrollUp(var1, var2);
      }

   }

   void processTouchEvent(MotionEvent var1, int var2, MotionLayout var3) {
      RectF var4 = new RectF();
      if (this.mVelocityTracker == null) {
         this.mVelocityTracker = this.mMotionLayout.obtainVelocityTracker();
      }

      this.mVelocityTracker.addMovement(var1);
      if (var2 != -1) {
         int var5 = var1.getAction();
         boolean var6 = false;
         if (var5 == 0) {
            this.mLastTouchX = var1.getRawX();
            this.mLastTouchY = var1.getRawY();
            this.mLastTouchDown = var1;
            if (this.mCurrentTransition.mTouchResponse != null) {
               RectF var12 = this.mCurrentTransition.mTouchResponse.getLimitBoundsTo(this.mMotionLayout, var4);
               if (var12 != null && !var12.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                  this.mLastTouchDown = null;
                  return;
               }

               var12 = this.mCurrentTransition.mTouchResponse.getTouchRegion(this.mMotionLayout, var4);
               if (var12 != null && !var12.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                  this.mMotionOutsideRegion = true;
               } else {
                  this.mMotionOutsideRegion = false;
               }

               this.mCurrentTransition.mTouchResponse.setDown(this.mLastTouchX, this.mLastTouchY);
            }

            return;
         }

         if (var5 == 2) {
            float var7 = var1.getRawY() - this.mLastTouchY;
            float var8 = var1.getRawX() - this.mLastTouchX;
            if ((double)var8 == 0.0D && (double)var7 == 0.0D) {
               return;
            }

            MotionEvent var9 = this.mLastTouchDown;
            if (var9 == null) {
               return;
            }

            MotionScene.Transition var14 = this.bestTransitionFor(var2, var8, var7, var9);
            if (var14 != null) {
               var3.setTransition(var14);
               var4 = this.mCurrentTransition.mTouchResponse.getTouchRegion(this.mMotionLayout, var4);
               boolean var10 = var6;
               if (var4 != null) {
                  var10 = var6;
                  if (!var4.contains(this.mLastTouchDown.getX(), this.mLastTouchDown.getY())) {
                     var10 = true;
                  }
               }

               this.mMotionOutsideRegion = var10;
               this.mCurrentTransition.mTouchResponse.setUpTouchEvent(this.mLastTouchX, this.mLastTouchY);
            }
         }
      }

      MotionScene.Transition var13 = this.mCurrentTransition;
      if (var13 != null && var13.mTouchResponse != null && !this.mMotionOutsideRegion) {
         this.mCurrentTransition.mTouchResponse.processTouchEvent(var1, this.mVelocityTracker, var2, this);
      }

      this.mLastTouchX = var1.getRawX();
      this.mLastTouchY = var1.getRawY();
      if (var1.getAction() == 1) {
         MotionLayout.MotionTracker var11 = this.mVelocityTracker;
         if (var11 != null) {
            var11.recycle();
            this.mVelocityTracker = null;
            if (var3.mCurrentState != -1) {
               this.autoTransition(var3, var3.mCurrentState);
            }
         }
      }

   }

   void readFallback(MotionLayout var1) {
      byte var2 = 0;
      int var3 = 0;

      while(true) {
         int var4 = var2;
         if (var3 >= this.mConstraintSetMap.size()) {
            while(var4 < this.mConstraintSetMap.size()) {
               ((ConstraintSet)this.mConstraintSetMap.valueAt(var4)).readFallback((ConstraintLayout)var1);
               ++var4;
            }

            return;
         }

         var4 = this.mConstraintSetMap.keyAt(var3);
         if (this.hasCycleDependency(var4)) {
            Log.e("MotionScene", "Cannot be derived from yourself");
            return;
         }

         this.readConstraintChain(var4);
         ++var3;
      }
   }

   public void removeTransition(MotionScene.Transition var1) {
      int var2 = this.getIndex(var1);
      if (var2 != -1) {
         this.mTransitionList.remove(var2);
      }

   }

   public void setConstraintSet(int var1, ConstraintSet var2) {
      this.mConstraintSetMap.put(var1, var2);
   }

   public void setDuration(int var1) {
      MotionScene.Transition var2 = this.mCurrentTransition;
      if (var2 != null) {
         var2.setDuration(var1);
      } else {
         this.mDefaultDuration = var1;
      }

   }

   public void setKeyframe(View var1, int var2, String var3, Object var4) {
      MotionScene.Transition var5 = this.mCurrentTransition;
      if (var5 != null) {
         Iterator var6 = var5.mKeyFramesList.iterator();

         while(var6.hasNext()) {
            Iterator var8 = ((KeyFrames)var6.next()).getKeyFramesForView(var1.getId()).iterator();

            while(var8.hasNext()) {
               if (((Key)var8.next()).mFramePosition == var2) {
                  float var7;
                  if (var4 != null) {
                     var7 = (Float)var4;
                  } else {
                     var7 = 0.0F;
                  }

                  var3.equalsIgnoreCase("app:PerpendicularPath_percent");
               }
            }
         }

      }
   }

   public void setRtl(boolean var1) {
      this.mRtl = var1;
      MotionScene.Transition var2 = this.mCurrentTransition;
      if (var2 != null && var2.mTouchResponse != null) {
         this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
      }

   }

   void setTransition(int var1, int var2) {
      int var4;
      int var5;
      label56: {
         StateSet var3 = this.mStateSet;
         int var6;
         if (var3 != null) {
            var4 = var3.stateGetConstraintID(var1, -1, -1);
            if (var4 == -1) {
               var4 = var1;
            }

            var5 = this.mStateSet.stateGetConstraintID(var2, -1, -1);
            var6 = var4;
            if (var5 != -1) {
               break label56;
            }
         } else {
            var6 = var1;
         }

         var5 = var2;
         var4 = var6;
      }

      Iterator var7 = this.mTransitionList.iterator();

      MotionScene.Transition var9;
      do {
         if (!var7.hasNext()) {
            var9 = this.mDefaultTransition;
            Iterator var8 = this.mAbstractTransitionList.iterator();

            while(var8.hasNext()) {
               MotionScene.Transition var10 = (MotionScene.Transition)var8.next();
               if (var10.mConstraintSetEnd == var2) {
                  var9 = var10;
               }
            }

            var9 = new MotionScene.Transition(this, var9);
            var9.mConstraintSetStart = var4;
            var9.mConstraintSetEnd = var5;
            if (var4 != -1) {
               this.mTransitionList.add(var9);
            }

            this.mCurrentTransition = var9;
            return;
         }

         var9 = (MotionScene.Transition)var7.next();
      } while((var9.mConstraintSetEnd != var5 || var9.mConstraintSetStart != var4) && (var9.mConstraintSetEnd != var2 || var9.mConstraintSetStart != var1));

      this.mCurrentTransition = var9;
      if (var9 != null && var9.mTouchResponse != null) {
         this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
      }

   }

   public void setTransition(MotionScene.Transition var1) {
      this.mCurrentTransition = var1;
      if (var1 != null && var1.mTouchResponse != null) {
         this.mCurrentTransition.mTouchResponse.setRTL(this.mRtl);
      }

   }

   void setupTouch() {
      MotionScene.Transition var1 = this.mCurrentTransition;
      if (var1 != null && var1.mTouchResponse != null) {
         this.mCurrentTransition.mTouchResponse.setupTouch();
      }

   }

   boolean supportTouch() {
      Iterator var1 = this.mTransitionList.iterator();

      do {
         boolean var2 = var1.hasNext();
         boolean var3 = true;
         if (!var2) {
            MotionScene.Transition var4 = this.mCurrentTransition;
            if (var4 == null || var4.mTouchResponse == null) {
               var3 = false;
            }

            return var3;
         }
      } while(((MotionScene.Transition)var1.next()).mTouchResponse == null);

      return true;
   }

   public boolean validateLayout(MotionLayout var1) {
      boolean var2;
      if (var1 == this.mMotionLayout && var1.mScene == this) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
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
      private ArrayList<MotionScene.Transition.TransitionOnClick> mOnClicks = new ArrayList();
      private int mPathMotionArc = -1;
      private float mStagger = 0.0F;
      private TouchResponse mTouchResponse = null;
      private int mTransitionFlags = 0;

      public Transition(int var1, MotionScene var2, int var3, int var4) {
         this.mId = var1;
         this.mMotionScene = var2;
         this.mConstraintSetStart = var3;
         this.mConstraintSetEnd = var4;
         this.mDuration = var2.mDefaultDuration;
         this.mLayoutDuringTransition = var2.mLayoutDuringTransition;
      }

      Transition(MotionScene var1, Context var2, XmlPullParser var3) {
         this.mDuration = var1.mDefaultDuration;
         this.mLayoutDuringTransition = var1.mLayoutDuringTransition;
         this.mMotionScene = var1;
         this.fillFromAttributeList(var1, var2, Xml.asAttributeSet(var3));
      }

      Transition(MotionScene var1, MotionScene.Transition var2) {
         this.mMotionScene = var1;
         if (var2 != null) {
            this.mPathMotionArc = var2.mPathMotionArc;
            this.mDefaultInterpolator = var2.mDefaultInterpolator;
            this.mDefaultInterpolatorString = var2.mDefaultInterpolatorString;
            this.mDefaultInterpolatorID = var2.mDefaultInterpolatorID;
            this.mDuration = var2.mDuration;
            this.mKeyFramesList = var2.mKeyFramesList;
            this.mStagger = var2.mStagger;
            this.mLayoutDuringTransition = var2.mLayoutDuringTransition;
         }

      }

      private void fill(MotionScene var1, Context var2, TypedArray var3) {
         int var4 = var3.getIndexCount();

         for(int var5 = 0; var5 < var4; ++var5) {
            int var6 = var3.getIndex(var5);
            ConstraintSet var7;
            if (var6 == R.styleable.Transition_constraintSetEnd) {
               this.mConstraintSetEnd = var3.getResourceId(var6, this.mConstraintSetEnd);
               if ("layout".equals(var2.getResources().getResourceTypeName(this.mConstraintSetEnd))) {
                  var7 = new ConstraintSet();
                  var7.load(var2, this.mConstraintSetEnd);
                  var1.mConstraintSetMap.append(this.mConstraintSetEnd, var7);
               }
            } else if (var6 == R.styleable.Transition_constraintSetStart) {
               this.mConstraintSetStart = var3.getResourceId(var6, this.mConstraintSetStart);
               if ("layout".equals(var2.getResources().getResourceTypeName(this.mConstraintSetStart))) {
                  var7 = new ConstraintSet();
                  var7.load(var2, this.mConstraintSetStart);
                  var1.mConstraintSetMap.append(this.mConstraintSetStart, var7);
               }
            } else if (var6 == R.styleable.Transition_motionInterpolator) {
               TypedValue var8 = var3.peekValue(var6);
               if (var8.type == 1) {
                  var6 = var3.getResourceId(var6, -1);
                  this.mDefaultInterpolatorID = var6;
                  if (var6 != -1) {
                     this.mDefaultInterpolator = -2;
                  }
               } else if (var8.type == 3) {
                  String var9 = var3.getString(var6);
                  this.mDefaultInterpolatorString = var9;
                  if (var9.indexOf("/") > 0) {
                     this.mDefaultInterpolatorID = var3.getResourceId(var6, -1);
                     this.mDefaultInterpolator = -2;
                  } else {
                     this.mDefaultInterpolator = -1;
                  }
               } else {
                  this.mDefaultInterpolator = var3.getInteger(var6, this.mDefaultInterpolator);
               }
            } else if (var6 == R.styleable.Transition_duration) {
               this.mDuration = var3.getInt(var6, this.mDuration);
            } else if (var6 == R.styleable.Transition_staggered) {
               this.mStagger = var3.getFloat(var6, this.mStagger);
            } else if (var6 == R.styleable.Transition_autoTransition) {
               this.mAutoTransition = var3.getInteger(var6, this.mAutoTransition);
            } else if (var6 == R.styleable.Transition_android_id) {
               this.mId = var3.getResourceId(var6, this.mId);
            } else if (var6 == R.styleable.Transition_transitionDisable) {
               this.mDisable = var3.getBoolean(var6, this.mDisable);
            } else if (var6 == R.styleable.Transition_pathMotionArc) {
               this.mPathMotionArc = var3.getInteger(var6, -1);
            } else if (var6 == R.styleable.Transition_layoutDuringTransition) {
               this.mLayoutDuringTransition = var3.getInteger(var6, 0);
            } else if (var6 == R.styleable.Transition_transitionFlags) {
               this.mTransitionFlags = var3.getInteger(var6, 0);
            }
         }

         if (this.mConstraintSetStart == -1) {
            this.mIsAbstract = true;
         }

      }

      private void fillFromAttributeList(MotionScene var1, Context var2, AttributeSet var3) {
         TypedArray var4 = var2.obtainStyledAttributes(var3, R.styleable.Transition);
         this.fill(var1, var2, var4);
         var4.recycle();
      }

      public void addOnClick(Context var1, XmlPullParser var2) {
         this.mOnClicks.add(new MotionScene.Transition.TransitionOnClick(var1, this, var2));
      }

      public String debugString(Context var1) {
         String var2;
         if (this.mConstraintSetStart == -1) {
            var2 = "null";
         } else {
            var2 = var1.getResources().getResourceEntryName(this.mConstraintSetStart);
         }

         String var5;
         if (this.mConstraintSetEnd == -1) {
            StringBuilder var4 = new StringBuilder();
            var4.append(var2);
            var4.append(" -> null");
            var5 = var4.toString();
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append(var2);
            var3.append(" -> ");
            var3.append(var1.getResources().getResourceEntryName(this.mConstraintSetEnd));
            var5 = var3.toString();
         }

         return var5;
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

      public List<MotionScene.Transition.TransitionOnClick> getOnClickList() {
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

      public boolean isTransitionFlag(int var1) {
         boolean var2;
         if ((var1 & this.mTransitionFlags) != 0) {
            var2 = true;
         } else {
            var2 = false;
         }

         return var2;
      }

      public void setDuration(int var1) {
         this.mDuration = var1;
      }

      public void setEnable(boolean var1) {
         this.mDisable = var1 ^ true;
      }

      public void setPathMotionArc(int var1) {
         this.mPathMotionArc = var1;
      }

      public void setStagger(float var1) {
         this.mStagger = var1;
      }

      static class TransitionOnClick implements OnClickListener {
         public static final int ANIM_TOGGLE = 17;
         public static final int ANIM_TO_END = 1;
         public static final int ANIM_TO_START = 16;
         public static final int JUMP_TO_END = 256;
         public static final int JUMP_TO_START = 4096;
         int mMode = 17;
         int mTargetId = -1;
         private final MotionScene.Transition mTransition;

         public TransitionOnClick(Context var1, MotionScene.Transition var2, XmlPullParser var3) {
            this.mTransition = var2;
            TypedArray var7 = var1.obtainStyledAttributes(Xml.asAttributeSet(var3), R.styleable.OnClick);
            int var4 = var7.getIndexCount();

            for(int var5 = 0; var5 < var4; ++var5) {
               int var6 = var7.getIndex(var5);
               if (var6 == R.styleable.OnClick_targetId) {
                  this.mTargetId = var7.getResourceId(var6, this.mTargetId);
               } else if (var6 == R.styleable.OnClick_clickAction) {
                  this.mMode = var7.getInt(var6, this.mMode);
               }
            }

            var7.recycle();
         }

         public void addOnClickListeners(MotionLayout var1, int var2, MotionScene.Transition var3) {
            int var4 = this.mTargetId;
            if (var4 != -1) {
               var1 = ((MotionLayout)var1).findViewById(var4);
            }

            if (var1 == null) {
               StringBuilder var10 = new StringBuilder();
               var10.append("OnClick could not find id ");
               var10.append(this.mTargetId);
               Log.e("MotionScene", var10.toString());
            } else {
               int var5 = var3.mConstraintSetStart;
               int var6 = var3.mConstraintSetEnd;
               if (var5 == -1) {
                  ((View)var1).setOnClickListener(this);
               } else {
                  var4 = this.mMode;
                  boolean var7 = true;
                  boolean var12;
                  if ((var4 & 1) != 0 && var2 == var5) {
                     var12 = true;
                  } else {
                     var12 = false;
                  }

                  boolean var8;
                  if ((this.mMode & 256) != 0 && var2 == var5) {
                     var8 = true;
                  } else {
                     var8 = false;
                  }

                  boolean var13;
                  if ((this.mMode & 1) != 0 && var2 == var5) {
                     var13 = true;
                  } else {
                     var13 = false;
                  }

                  boolean var9;
                  if ((this.mMode & 16) != 0 && var2 == var6) {
                     var9 = true;
                  } else {
                     var9 = false;
                  }

                  boolean var11;
                  if ((this.mMode & 4096) != 0 && var2 == var6) {
                     var11 = var7;
                  } else {
                     var11 = false;
                  }

                  if (var13 | var12 | var8 | var9 | var11) {
                     ((View)var1).setOnClickListener(this);
                  }

               }
            }
         }

         boolean isTransitionViable(MotionScene.Transition var1, MotionLayout var2) {
            MotionScene.Transition var3 = this.mTransition;
            boolean var4 = true;
            boolean var5 = true;
            if (var3 == var1) {
               return true;
            } else {
               int var6 = var3.mConstraintSetEnd;
               int var7 = this.mTransition.mConstraintSetStart;
               if (var7 == -1) {
                  if (var2.mCurrentState == var6) {
                     var5 = false;
                  }

                  return var5;
               } else {
                  var5 = var4;
                  if (var2.mCurrentState != var7) {
                     if (var2.mCurrentState == var6) {
                        var5 = var4;
                     } else {
                        var5 = false;
                     }
                  }

                  return var5;
               }
            }
         }

         public void onClick(View var1) {
            MotionLayout var10 = this.mTransition.mMotionScene.mMotionLayout;
            if (var10.isInteractionEnabled()) {
               int var2;
               MotionScene.Transition var3;
               if (this.mTransition.mConstraintSetStart == -1) {
                  var2 = var10.getCurrentState();
                  if (var2 == -1) {
                     var10.transitionToState(this.mTransition.mConstraintSetEnd);
                  } else {
                     var3 = new MotionScene.Transition(this.mTransition.mMotionScene, this.mTransition);
                     var3.mConstraintSetStart = var2;
                     var3.mConstraintSetEnd = this.mTransition.mConstraintSetEnd;
                     var10.setTransition(var3);
                     var10.transitionToEnd();
                  }
               } else {
                  MotionScene.Transition var4 = this.mTransition.mMotionScene.mCurrentTransition;
                  var2 = this.mMode;
                  boolean var5 = false;
                  boolean var11;
                  if ((var2 & 1) == 0 && (var2 & 256) == 0) {
                     var11 = false;
                  } else {
                     var11 = true;
                  }

                  int var6 = this.mMode;
                  boolean var12;
                  if ((var6 & 16) == 0 && (var6 & 4096) == 0) {
                     var12 = false;
                  } else {
                     var12 = true;
                  }

                  boolean var7;
                  if (var11 && var12) {
                     var7 = true;
                  } else {
                     var7 = false;
                  }

                  boolean var9;
                  if (var7) {
                     var3 = this.mTransition.mMotionScene.mCurrentTransition;
                     MotionScene.Transition var8 = this.mTransition;
                     if (var3 != var8) {
                        var10.setTransition(var8);
                     }

                     var9 = var12;
                     var7 = var5;
                     if (var10.getCurrentState() != var10.getEndState()) {
                        if (var10.getProgress() > 0.5F) {
                           var9 = var12;
                           var7 = var5;
                        } else {
                           var9 = false;
                           var7 = var11;
                        }
                     }
                  } else {
                     var7 = var11;
                     var9 = var12;
                  }

                  if (this.isTransitionViable(var4, var10)) {
                     if (var7 && (this.mMode & 1) != 0) {
                        var10.setTransition(this.mTransition);
                        var10.transitionToEnd();
                     } else if (var9 && (this.mMode & 16) != 0) {
                        var10.setTransition(this.mTransition);
                        var10.transitionToStart();
                     } else if (var7 && (this.mMode & 256) != 0) {
                        var10.setTransition(this.mTransition);
                        var10.setProgress(1.0F);
                     } else if (var9 && (this.mMode & 4096) != 0) {
                        var10.setTransition(this.mTransition);
                        var10.setProgress(0.0F);
                     }
                  }

               }
            }
         }

         public void removeOnClickListeners(MotionLayout var1) {
            int var2 = this.mTargetId;
            if (var2 != -1) {
               View var3 = var1.findViewById(var2);
               if (var3 == null) {
                  StringBuilder var4 = new StringBuilder();
                  var4.append(" (*)  could not find id ");
                  var4.append(this.mTargetId);
                  Log.e("MotionScene", var4.toString());
               } else {
                  var3.setOnClickListener((OnClickListener)null);
               }
            }
         }
      }
   }
}

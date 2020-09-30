package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class StateSet {
   private static final boolean DEBUG = false;
   public static final String TAG = "ConstraintLayoutStates";
   private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray();
   private ConstraintsChangedListener mConstraintsChangedListener = null;
   int mCurrentConstraintNumber = -1;
   int mCurrentStateId = -1;
   ConstraintSet mDefaultConstraintSet;
   int mDefaultState = -1;
   private SparseArray<StateSet.State> mStateList = new SparseArray();

   public StateSet(Context var1, XmlPullParser var2) {
      this.load(var1, var2);
   }

   private void load(Context var1, XmlPullParser var2) {
      TypedArray var3 = var1.obtainStyledAttributes(Xml.asAttributeSet(var2), R.styleable.StateSet);
      int var4 = var3.getIndexCount();

      int var5;
      for(var5 = 0; var5 < var4; ++var5) {
         int var6 = var3.getIndex(var5);
         if (var6 == R.styleable.StateSet_defaultState) {
            this.mDefaultState = var3.getResourceId(var6, this.mDefaultState);
         }
      }

      StateSet.State var39 = null;

      XmlPullParserException var43;
      label203: {
         IOException var10000;
         label178: {
            boolean var10001;
            try {
               var5 = var2.getEventType();
            } catch (XmlPullParserException var35) {
               var43 = var35;
               var10001 = false;
               break label203;
            } catch (IOException var36) {
               var10000 = var36;
               var10001 = false;
               break label178;
            }

            while(true) {
               if (var5 == 1) {
                  return;
               }

               StateSet.State var7;
               if (var5 != 0) {
                  if (var5 != 2) {
                     if (var5 != 3) {
                        var7 = var39;
                     } else {
                        var7 = var39;

                        try {
                           if ("StateSet".equals(var2.getName())) {
                              return;
                           }
                        } catch (XmlPullParserException var11) {
                           var43 = var11;
                           var10001 = false;
                           break label203;
                        } catch (IOException var12) {
                           var10000 = var12;
                           var10001 = false;
                           break;
                        }
                     }
                  } else {
                     String var8;
                     try {
                        var8 = var2.getName();
                     } catch (XmlPullParserException var23) {
                        var43 = var23;
                        var10001 = false;
                        break label203;
                     } catch (IOException var24) {
                        var10000 = var24;
                        var10001 = false;
                        break;
                     }

                     byte var40 = -1;

                     label193: {
                        label194: {
                           label195: {
                              label196: {
                                 try {
                                    switch(var8.hashCode()) {
                                    case 80204913:
                                       break label195;
                                    case 1301459538:
                                       break label194;
                                    case 1382829617:
                                       break;
                                    case 1901439077:
                                       break label196;
                                    default:
                                       break label193;
                                    }
                                 } catch (XmlPullParserException var33) {
                                    var43 = var33;
                                    var10001 = false;
                                    break label203;
                                 } catch (IOException var34) {
                                    var10000 = var34;
                                    var10001 = false;
                                    break;
                                 }

                                 try {
                                    if (!var8.equals("StateSet")) {
                                       break label193;
                                    }
                                 } catch (XmlPullParserException var27) {
                                    var43 = var27;
                                    var10001 = false;
                                    break label203;
                                 } catch (IOException var28) {
                                    var10000 = var28;
                                    var10001 = false;
                                    break;
                                 }

                                 var40 = 1;
                                 break label193;
                              }

                              try {
                                 if (!var8.equals("Variant")) {
                                    break label193;
                                 }
                              } catch (XmlPullParserException var25) {
                                 var43 = var25;
                                 var10001 = false;
                                 break label203;
                              } catch (IOException var26) {
                                 var10000 = var26;
                                 var10001 = false;
                                 break;
                              }

                              var40 = 3;
                              break label193;
                           }

                           try {
                              if (!var8.equals("State")) {
                                 break label193;
                              }
                           } catch (XmlPullParserException var31) {
                              var43 = var31;
                              var10001 = false;
                              break label203;
                           } catch (IOException var32) {
                              var10000 = var32;
                              var10001 = false;
                              break;
                           }

                           var40 = 2;
                           break label193;
                        }

                        try {
                           if (!var8.equals("LayoutDescription")) {
                              break label193;
                           }
                        } catch (XmlPullParserException var29) {
                           var43 = var29;
                           var10001 = false;
                           break label203;
                        } catch (IOException var30) {
                           var10000 = var30;
                           var10001 = false;
                           break;
                        }

                        var40 = 0;
                     }

                     var7 = var39;
                     if (var40 != 0) {
                        var7 = var39;
                        if (var40 != 1) {
                           if (var40 != 2) {
                              if (var40 != 3) {
                                 try {
                                    StringBuilder var42 = new StringBuilder();
                                    var42.append("unknown tag ");
                                    var42.append(var8);
                                    Log.v("ConstraintLayoutStates", var42.toString());
                                 } catch (XmlPullParserException var21) {
                                    var43 = var21;
                                    var10001 = false;
                                    break label203;
                                 } catch (IOException var22) {
                                    var10000 = var22;
                                    var10001 = false;
                                    break;
                                 }

                                 var7 = var39;
                              } else {
                                 StateSet.Variant var41;
                                 try {
                                    var41 = new StateSet.Variant(var1, var2);
                                 } catch (XmlPullParserException var19) {
                                    var43 = var19;
                                    var10001 = false;
                                    break label203;
                                 } catch (IOException var20) {
                                    var10000 = var20;
                                    var10001 = false;
                                    break;
                                 }

                                 var7 = var39;
                                 if (var39 != null) {
                                    try {
                                       var39.add(var41);
                                    } catch (XmlPullParserException var17) {
                                       var43 = var17;
                                       var10001 = false;
                                       break label203;
                                    } catch (IOException var18) {
                                       var10000 = var18;
                                       var10001 = false;
                                       break;
                                    }

                                    var7 = var39;
                                 }
                              }
                           } else {
                              try {
                                 var7 = new StateSet.State(var1, var2);
                                 this.mStateList.put(var7.mId, var7);
                              } catch (XmlPullParserException var15) {
                                 var43 = var15;
                                 var10001 = false;
                                 break label203;
                              } catch (IOException var16) {
                                 var10000 = var16;
                                 var10001 = false;
                                 break;
                              }
                           }
                        }
                     }
                  }
               } else {
                  try {
                     var2.getName();
                  } catch (XmlPullParserException var13) {
                     var43 = var13;
                     var10001 = false;
                     break label203;
                  } catch (IOException var14) {
                     var10000 = var14;
                     var10001 = false;
                     break;
                  }

                  var7 = var39;
               }

               try {
                  var5 = var2.next();
               } catch (XmlPullParserException var9) {
                  var43 = var9;
                  var10001 = false;
                  break label203;
               } catch (IOException var10) {
                  var10000 = var10;
                  var10001 = false;
                  break;
               }

               var39 = var7;
            }
         }

         IOException var37 = var10000;
         var37.printStackTrace();
         return;
      }

      XmlPullParserException var38 = var43;
      var38.printStackTrace();
   }

   public int convertToConstraintSet(int var1, int var2, float var3, float var4) {
      StateSet.State var5 = (StateSet.State)this.mStateList.get(var2);
      if (var5 == null) {
         return var2;
      } else if (var3 != -1.0F && var4 != -1.0F) {
         StateSet.Variant var9 = null;
         Iterator var7 = var5.mVariants.iterator();

         while(var7.hasNext()) {
            StateSet.Variant var8 = (StateSet.Variant)var7.next();
            if (var8.match(var3, var4)) {
               if (var1 == var8.mConstraintID) {
                  return var1;
               }

               var9 = var8;
            }
         }

         if (var9 != null) {
            return var9.mConstraintID;
         } else {
            return var5.mConstraintID;
         }
      } else if (var5.mConstraintID == var1) {
         return var1;
      } else {
         Iterator var6 = var5.mVariants.iterator();

         do {
            if (!var6.hasNext()) {
               return var5.mConstraintID;
            }
         } while(var1 != ((StateSet.Variant)var6.next()).mConstraintID);

         return var1;
      }
   }

   public boolean needsToChange(int var1, float var2, float var3) {
      int var4 = this.mCurrentStateId;
      if (var4 != var1) {
         return true;
      } else {
         Object var5;
         if (var1 == -1) {
            var5 = this.mStateList.valueAt(0);
         } else {
            var5 = this.mStateList.get(var4);
         }

         StateSet.State var6 = (StateSet.State)var5;
         if (this.mCurrentConstraintNumber != -1 && ((StateSet.Variant)var6.mVariants.get(this.mCurrentConstraintNumber)).match(var2, var3)) {
            return false;
         } else {
            return this.mCurrentConstraintNumber != var6.findMatch(var2, var3);
         }
      }
   }

   public void setOnConstraintsChanged(ConstraintsChangedListener var1) {
      this.mConstraintsChangedListener = var1;
   }

   public int stateGetConstraintID(int var1, int var2, int var3) {
      return this.updateConstraints(-1, var1, (float)var2, (float)var3);
   }

   public int updateConstraints(int var1, int var2, float var3, float var4) {
      StateSet.State var5;
      if (var1 == var2) {
         if (var2 == -1) {
            var5 = (StateSet.State)this.mStateList.valueAt(0);
         } else {
            var5 = (StateSet.State)this.mStateList.get(this.mCurrentStateId);
         }

         if (var5 == null) {
            return -1;
         } else if (this.mCurrentConstraintNumber != -1 && ((StateSet.Variant)var5.mVariants.get(var1)).match(var3, var4)) {
            return var1;
         } else {
            var2 = var5.findMatch(var3, var4);
            if (var1 == var2) {
               return var1;
            } else {
               if (var2 == -1) {
                  var1 = var5.mConstraintID;
               } else {
                  var1 = ((StateSet.Variant)var5.mVariants.get(var2)).mConstraintID;
               }

               return var1;
            }
         }
      } else {
         var5 = (StateSet.State)this.mStateList.get(var2);
         if (var5 == null) {
            return -1;
         } else {
            var1 = var5.findMatch(var3, var4);
            if (var1 == -1) {
               var1 = var5.mConstraintID;
            } else {
               var1 = ((StateSet.Variant)var5.mVariants.get(var1)).mConstraintID;
            }

            return var1;
         }
      }
   }

   static class State {
      int mConstraintID = -1;
      int mId;
      boolean mIsLayout;
      ArrayList<StateSet.Variant> mVariants = new ArrayList();

      public State(Context var1, XmlPullParser var2) {
         int var3 = 0;
         this.mIsLayout = false;
         TypedArray var7 = var1.obtainStyledAttributes(Xml.asAttributeSet(var2), R.styleable.State);

         for(int var4 = var7.getIndexCount(); var3 < var4; ++var3) {
            int var5 = var7.getIndex(var3);
            if (var5 == R.styleable.State_android_id) {
               this.mId = var7.getResourceId(var5, this.mId);
            } else if (var5 == R.styleable.State_constraints) {
               this.mConstraintID = var7.getResourceId(var5, this.mConstraintID);
               String var6 = var1.getResources().getResourceTypeName(this.mConstraintID);
               var1.getResources().getResourceName(this.mConstraintID);
               if ("layout".equals(var6)) {
                  this.mIsLayout = true;
               }
            }
         }

         var7.recycle();
      }

      void add(StateSet.Variant var1) {
         this.mVariants.add(var1);
      }

      public int findMatch(float var1, float var2) {
         for(int var3 = 0; var3 < this.mVariants.size(); ++var3) {
            if (((StateSet.Variant)this.mVariants.get(var3)).match(var1, var2)) {
               return var3;
            }
         }

         return -1;
      }
   }

   static class Variant {
      int mConstraintID = -1;
      int mId;
      boolean mIsLayout;
      float mMaxHeight = Float.NaN;
      float mMaxWidth = Float.NaN;
      float mMinHeight = Float.NaN;
      float mMinWidth = Float.NaN;

      public Variant(Context var1, XmlPullParser var2) {
         int var3 = 0;
         this.mIsLayout = false;
         TypedArray var4 = var1.obtainStyledAttributes(Xml.asAttributeSet(var2), R.styleable.Variant);

         for(int var5 = var4.getIndexCount(); var3 < var5; ++var3) {
            int var6 = var4.getIndex(var3);
            if (var6 == R.styleable.Variant_constraints) {
               this.mConstraintID = var4.getResourceId(var6, this.mConstraintID);
               String var7 = var1.getResources().getResourceTypeName(this.mConstraintID);
               var1.getResources().getResourceName(this.mConstraintID);
               if ("layout".equals(var7)) {
                  this.mIsLayout = true;
               }
            } else if (var6 == R.styleable.Variant_region_heightLessThan) {
               this.mMaxHeight = var4.getDimension(var6, this.mMaxHeight);
            } else if (var6 == R.styleable.Variant_region_heightMoreThan) {
               this.mMinHeight = var4.getDimension(var6, this.mMinHeight);
            } else if (var6 == R.styleable.Variant_region_widthLessThan) {
               this.mMaxWidth = var4.getDimension(var6, this.mMaxWidth);
            } else if (var6 == R.styleable.Variant_region_widthMoreThan) {
               this.mMinWidth = var4.getDimension(var6, this.mMinWidth);
            } else {
               Log.v("ConstraintLayoutStates", "Unknown tag");
            }
         }

         var4.recycle();
      }

      boolean match(float var1, float var2) {
         if (!Float.isNaN(this.mMinWidth) && var1 < this.mMinWidth) {
            return false;
         } else if (!Float.isNaN(this.mMinHeight) && var2 < this.mMinHeight) {
            return false;
         } else if (!Float.isNaN(this.mMaxWidth) && var1 > this.mMaxWidth) {
            return false;
         } else {
            return Float.isNaN(this.mMaxHeight) || var2 <= this.mMaxHeight;
         }
      }
   }
}

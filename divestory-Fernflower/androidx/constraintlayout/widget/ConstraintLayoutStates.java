package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.util.Log;
import android.util.SparseArray;
import android.util.Xml;
import java.io.IOException;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class ConstraintLayoutStates {
   private static final boolean DEBUG = false;
   public static final String TAG = "ConstraintLayoutStates";
   private final ConstraintLayout mConstraintLayout;
   private SparseArray<ConstraintSet> mConstraintSetMap = new SparseArray();
   private ConstraintsChangedListener mConstraintsChangedListener = null;
   int mCurrentConstraintNumber = -1;
   int mCurrentStateId = -1;
   ConstraintSet mDefaultConstraintSet;
   private SparseArray<ConstraintLayoutStates.State> mStateList = new SparseArray();

   ConstraintLayoutStates(Context var1, ConstraintLayout var2, int var3) {
      this.mConstraintLayout = var2;
      this.load(var1, var3);
   }

   private void load(Context var1, int var2) {
      XmlResourceParser var3 = var1.getResources().getXml(var2);
      ConstraintLayoutStates.State var4 = null;

      XmlPullParserException var42;
      label201: {
         IOException var10000;
         label182: {
            boolean var10001;
            try {
               var2 = var3.getEventType();
            } catch (XmlPullParserException var35) {
               var42 = var35;
               var10001 = false;
               break label201;
            } catch (IOException var36) {
               var10000 = var36;
               var10001 = false;
               break label182;
            }

            while(true) {
               if (var2 == 1) {
                  return;
               }

               ConstraintLayoutStates.State var5;
               if (var2 != 0) {
                  if (var2 != 2) {
                     var5 = var4;
                  } else {
                     String var6;
                     try {
                        var6 = var3.getName();
                     } catch (XmlPullParserException var21) {
                        var42 = var21;
                        var10001 = false;
                        break label201;
                     } catch (IOException var22) {
                        var10000 = var22;
                        var10001 = false;
                        break;
                     }

                     byte var39 = -1;

                     label190: {
                        label191: {
                           label192: {
                              label193: {
                                 label194: {
                                    try {
                                       switch(var6.hashCode()) {
                                       case -1349929691:
                                          break label192;
                                       case 80204913:
                                          break label191;
                                       case 1382829617:
                                          break;
                                       case 1657696882:
                                          break label194;
                                       case 1901439077:
                                          break label193;
                                       default:
                                          break label190;
                                       }
                                    } catch (XmlPullParserException var33) {
                                       var42 = var33;
                                       var10001 = false;
                                       break label201;
                                    } catch (IOException var34) {
                                       var10000 = var34;
                                       var10001 = false;
                                       break;
                                    }

                                    try {
                                       if (!var6.equals("StateSet")) {
                                          break label190;
                                       }
                                    } catch (XmlPullParserException var27) {
                                       var42 = var27;
                                       var10001 = false;
                                       break label201;
                                    } catch (IOException var28) {
                                       var10000 = var28;
                                       var10001 = false;
                                       break;
                                    }

                                    var39 = 1;
                                    break label190;
                                 }

                                 try {
                                    if (!var6.equals("layoutDescription")) {
                                       break label190;
                                    }
                                 } catch (XmlPullParserException var25) {
                                    var42 = var25;
                                    var10001 = false;
                                    break label201;
                                 } catch (IOException var26) {
                                    var10000 = var26;
                                    var10001 = false;
                                    break;
                                 }

                                 var39 = 0;
                                 break label190;
                              }

                              try {
                                 if (!var6.equals("Variant")) {
                                    break label190;
                                 }
                              } catch (XmlPullParserException var23) {
                                 var42 = var23;
                                 var10001 = false;
                                 break label201;
                              } catch (IOException var24) {
                                 var10000 = var24;
                                 var10001 = false;
                                 break;
                              }

                              var39 = 3;
                              break label190;
                           }

                           try {
                              if (!var6.equals("ConstraintSet")) {
                                 break label190;
                              }
                           } catch (XmlPullParserException var31) {
                              var42 = var31;
                              var10001 = false;
                              break label201;
                           } catch (IOException var32) {
                              var10000 = var32;
                              var10001 = false;
                              break;
                           }

                           var39 = 4;
                           break label190;
                        }

                        try {
                           if (!var6.equals("State")) {
                              break label190;
                           }
                        } catch (XmlPullParserException var29) {
                           var42 = var29;
                           var10001 = false;
                           break label201;
                        } catch (IOException var30) {
                           var10000 = var30;
                           var10001 = false;
                           break;
                        }

                        var39 = 2;
                     }

                     var5 = var4;
                     if (var39 != 0) {
                        var5 = var4;
                        if (var39 != 1) {
                           if (var39 != 2) {
                              if (var39 != 3) {
                                 if (var39 != 4) {
                                    try {
                                       StringBuilder var40 = new StringBuilder();
                                       var40.append("unknown tag ");
                                       var40.append(var6);
                                       Log.v("ConstraintLayoutStates", var40.toString());
                                    } catch (XmlPullParserException var19) {
                                       var42 = var19;
                                       var10001 = false;
                                       break label201;
                                    } catch (IOException var20) {
                                       var10000 = var20;
                                       var10001 = false;
                                       break;
                                    }

                                    var5 = var4;
                                 } else {
                                    try {
                                       this.parseConstraintSet(var1, var3);
                                    } catch (XmlPullParserException var17) {
                                       var42 = var17;
                                       var10001 = false;
                                       break label201;
                                    } catch (IOException var18) {
                                       var10000 = var18;
                                       var10001 = false;
                                       break;
                                    }

                                    var5 = var4;
                                 }
                              } else {
                                 ConstraintLayoutStates.Variant var41;
                                 try {
                                    var41 = new ConstraintLayoutStates.Variant(var1, var3);
                                 } catch (XmlPullParserException var15) {
                                    var42 = var15;
                                    var10001 = false;
                                    break label201;
                                 } catch (IOException var16) {
                                    var10000 = var16;
                                    var10001 = false;
                                    break;
                                 }

                                 var5 = var4;
                                 if (var4 != null) {
                                    try {
                                       var4.add(var41);
                                    } catch (XmlPullParserException var13) {
                                       var42 = var13;
                                       var10001 = false;
                                       break label201;
                                    } catch (IOException var14) {
                                       var10000 = var14;
                                       var10001 = false;
                                       break;
                                    }

                                    var5 = var4;
                                 }
                              }
                           } else {
                              try {
                                 var5 = new ConstraintLayoutStates.State(var1, var3);
                                 this.mStateList.put(var5.mId, var5);
                              } catch (XmlPullParserException var11) {
                                 var42 = var11;
                                 var10001 = false;
                                 break label201;
                              } catch (IOException var12) {
                                 var10000 = var12;
                                 var10001 = false;
                                 break;
                              }
                           }
                        }
                     }
                  }
               } else {
                  try {
                     var3.getName();
                  } catch (XmlPullParserException var9) {
                     var42 = var9;
                     var10001 = false;
                     break label201;
                  } catch (IOException var10) {
                     var10000 = var10;
                     var10001 = false;
                     break;
                  }

                  var5 = var4;
               }

               try {
                  var2 = var3.next();
               } catch (XmlPullParserException var7) {
                  var42 = var7;
                  var10001 = false;
                  break label201;
               } catch (IOException var8) {
                  var10000 = var8;
                  var10001 = false;
                  break;
               }

               var4 = var5;
            }
         }

         IOException var37 = var10000;
         var37.printStackTrace();
         return;
      }

      XmlPullParserException var38 = var42;
      var38.printStackTrace();
   }

   private void parseConstraintSet(Context var1, XmlPullParser var2) {
      ConstraintSet var3 = new ConstraintSet();
      int var4 = var2.getAttributeCount();

      for(int var5 = 0; var5 < var4; ++var5) {
         if ("id".equals(var2.getAttributeName(var5))) {
            String var6 = var2.getAttributeValue(var5);
            if (var6.contains("/")) {
               String var7 = var6.substring(var6.indexOf(47) + 1);
               var5 = var1.getResources().getIdentifier(var7, "id", var1.getPackageName());
            } else {
               var5 = -1;
            }

            var4 = var5;
            if (var5 == -1) {
               if (var6 != null && var6.length() > 1) {
                  var4 = Integer.parseInt(var6.substring(1));
               } else {
                  Log.e("ConstraintLayoutStates", "error in parsing id");
                  var4 = var5;
               }
            }

            var3.load(var1, var2);
            this.mConstraintSetMap.put(var4, var3);
            break;
         }
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

         ConstraintLayoutStates.State var6 = (ConstraintLayoutStates.State)var5;
         if (this.mCurrentConstraintNumber != -1 && ((ConstraintLayoutStates.Variant)var6.mVariants.get(this.mCurrentConstraintNumber)).match(var2, var3)) {
            return false;
         } else {
            return this.mCurrentConstraintNumber != var6.findMatch(var2, var3);
         }
      }
   }

   public void setOnConstraintsChanged(ConstraintsChangedListener var1) {
      this.mConstraintsChangedListener = var1;
   }

   public void updateConstraints(int var1, float var2, float var3) {
      int var4 = this.mCurrentStateId;
      ConstraintsChangedListener var8;
      if (var4 == var1) {
         ConstraintLayoutStates.State var5;
         if (var1 == -1) {
            var5 = (ConstraintLayoutStates.State)this.mStateList.valueAt(0);
         } else {
            var5 = (ConstraintLayoutStates.State)this.mStateList.get(var4);
         }

         if (this.mCurrentConstraintNumber != -1 && ((ConstraintLayoutStates.Variant)var5.mVariants.get(this.mCurrentConstraintNumber)).match(var2, var3)) {
            return;
         }

         var4 = var5.findMatch(var2, var3);
         if (this.mCurrentConstraintNumber == var4) {
            return;
         }

         ConstraintSet var6;
         if (var4 == -1) {
            var6 = this.mDefaultConstraintSet;
         } else {
            var6 = ((ConstraintLayoutStates.Variant)var5.mVariants.get(var4)).mConstraintSet;
         }

         if (var4 == -1) {
            var1 = var5.mConstraintID;
         } else {
            var1 = ((ConstraintLayoutStates.Variant)var5.mVariants.get(var4)).mConstraintID;
         }

         if (var6 == null) {
            return;
         }

         this.mCurrentConstraintNumber = var4;
         var8 = this.mConstraintsChangedListener;
         if (var8 != null) {
            var8.preLayoutChange(-1, var1);
         }

         var6.applyTo(this.mConstraintLayout);
         var8 = this.mConstraintsChangedListener;
         if (var8 != null) {
            var8.postLayoutChange(-1, var1);
         }
      } else {
         this.mCurrentStateId = var1;
         ConstraintLayoutStates.State var9 = (ConstraintLayoutStates.State)this.mStateList.get(var1);
         int var7 = var9.findMatch(var2, var3);
         ConstraintSet var10;
         if (var7 == -1) {
            var10 = var9.mConstraintSet;
         } else {
            var10 = ((ConstraintLayoutStates.Variant)var9.mVariants.get(var7)).mConstraintSet;
         }

         if (var7 == -1) {
            var4 = var9.mConstraintID;
         } else {
            var4 = ((ConstraintLayoutStates.Variant)var9.mVariants.get(var7)).mConstraintID;
         }

         if (var10 == null) {
            StringBuilder var12 = new StringBuilder();
            var12.append("NO Constraint set found ! id=");
            var12.append(var1);
            var12.append(", dim =");
            var12.append(var2);
            var12.append(", ");
            var12.append(var3);
            Log.v("ConstraintLayoutStates", var12.toString());
            return;
         }

         this.mCurrentConstraintNumber = var7;
         ConstraintsChangedListener var11 = this.mConstraintsChangedListener;
         if (var11 != null) {
            var11.preLayoutChange(var1, var4);
         }

         var10.applyTo(this.mConstraintLayout);
         var8 = this.mConstraintsChangedListener;
         if (var8 != null) {
            var8.postLayoutChange(var1, var4);
         }
      }

   }

   static class State {
      int mConstraintID = -1;
      ConstraintSet mConstraintSet;
      int mId;
      ArrayList<ConstraintLayoutStates.Variant> mVariants = new ArrayList();

      public State(Context var1, XmlPullParser var2) {
         TypedArray var7 = var1.obtainStyledAttributes(Xml.asAttributeSet(var2), R.styleable.State);
         int var3 = var7.getIndexCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var7.getIndex(var4);
            if (var5 == R.styleable.State_android_id) {
               this.mId = var7.getResourceId(var5, this.mId);
            } else if (var5 == R.styleable.State_constraints) {
               this.mConstraintID = var7.getResourceId(var5, this.mConstraintID);
               String var6 = var1.getResources().getResourceTypeName(this.mConstraintID);
               var1.getResources().getResourceName(this.mConstraintID);
               if ("layout".equals(var6)) {
                  ConstraintSet var8 = new ConstraintSet();
                  this.mConstraintSet = var8;
                  var8.clone(var1, this.mConstraintID);
               }
            }
         }

         var7.recycle();
      }

      void add(ConstraintLayoutStates.Variant var1) {
         this.mVariants.add(var1);
      }

      public int findMatch(float var1, float var2) {
         for(int var3 = 0; var3 < this.mVariants.size(); ++var3) {
            if (((ConstraintLayoutStates.Variant)this.mVariants.get(var3)).match(var1, var2)) {
               return var3;
            }
         }

         return -1;
      }
   }

   static class Variant {
      int mConstraintID = -1;
      ConstraintSet mConstraintSet;
      int mId;
      float mMaxHeight = Float.NaN;
      float mMaxWidth = Float.NaN;
      float mMinHeight = Float.NaN;
      float mMinWidth = Float.NaN;

      public Variant(Context var1, XmlPullParser var2) {
         TypedArray var7 = var1.obtainStyledAttributes(Xml.asAttributeSet(var2), R.styleable.Variant);
         int var3 = var7.getIndexCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var7.getIndex(var4);
            if (var5 == R.styleable.Variant_constraints) {
               this.mConstraintID = var7.getResourceId(var5, this.mConstraintID);
               String var6 = var1.getResources().getResourceTypeName(this.mConstraintID);
               var1.getResources().getResourceName(this.mConstraintID);
               if ("layout".equals(var6)) {
                  ConstraintSet var8 = new ConstraintSet();
                  this.mConstraintSet = var8;
                  var8.clone(var1, this.mConstraintID);
               }
            } else if (var5 == R.styleable.Variant_region_heightLessThan) {
               this.mMaxHeight = var7.getDimension(var5, this.mMaxHeight);
            } else if (var5 == R.styleable.Variant_region_heightMoreThan) {
               this.mMinHeight = var7.getDimension(var5, this.mMinHeight);
            } else if (var5 == R.styleable.Variant_region_widthLessThan) {
               this.mMaxWidth = var7.getDimension(var5, this.mMaxWidth);
            } else if (var5 == R.styleable.Variant_region_widthMoreThan) {
               this.mMinWidth = var7.getDimension(var5, this.mMinWidth);
            } else {
               Log.v("ConstraintLayoutStates", "Unknown tag");
            }
         }

         var7.recycle();
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

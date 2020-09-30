package androidx.constraintlayout.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.Resources.NotFoundException;
import android.graphics.Canvas;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewParent;
import android.view.ViewGroup.LayoutParams;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.Arrays;
import java.util.HashMap;

public abstract class ConstraintHelper extends View {
   protected int mCount;
   protected Helper mHelperWidget;
   protected int[] mIds = new int[32];
   private HashMap<Integer, String> mMap = new HashMap();
   protected String mReferenceIds;
   protected boolean mUseViewMeasure = false;
   private View[] mViews = null;
   protected Context myContext;

   public ConstraintHelper(Context var1) {
      super(var1);
      this.myContext = var1;
      this.init((AttributeSet)null);
   }

   public ConstraintHelper(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.myContext = var1;
      this.init(var2);
   }

   public ConstraintHelper(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.myContext = var1;
      this.init(var2);
   }

   private void addID(String var1) {
      if (var1 != null && var1.length() != 0) {
         if (this.myContext == null) {
            return;
         }

         var1 = var1.trim();
         if (this.getParent() instanceof ConstraintLayout) {
            ConstraintLayout var2 = (ConstraintLayout)this.getParent();
         }

         int var3 = this.findId(var1);
         if (var3 != 0) {
            this.mMap.put(var3, var1);
            this.addRscID(var3);
         } else {
            StringBuilder var4 = new StringBuilder();
            var4.append("Could not find id of \"");
            var4.append(var1);
            var4.append("\"");
            Log.w("ConstraintHelper", var4.toString());
         }
      }

   }

   private void addRscID(int var1) {
      if (var1 != this.getId()) {
         int var2 = this.mCount;
         int[] var3 = this.mIds;
         if (var2 + 1 > var3.length) {
            this.mIds = Arrays.copyOf(var3, var3.length * 2);
         }

         var3 = this.mIds;
         var2 = this.mCount;
         var3[var2] = var1;
         this.mCount = var2 + 1;
      }
   }

   private int[] convertReferenceString(View var1, String var2) {
      String[] var3 = var2.split(",");
      var1.getContext();
      int[] var9 = new int[var3.length];
      int var4 = 0;

      int var5;
      int var7;
      for(var5 = 0; var4 < var3.length; var5 = var7) {
         int var6 = this.findId(var3[var4].trim());
         var7 = var5;
         if (var6 != 0) {
            var9[var5] = var6;
            var7 = var5 + 1;
         }

         ++var4;
      }

      int[] var8 = var9;
      if (var5 != var3.length) {
         var8 = Arrays.copyOf(var9, var5);
      }

      return var8;
   }

   private int findId(ConstraintLayout var1, String var2) {
      if (var2 != null && var1 != null) {
         Resources var3 = this.myContext.getResources();
         if (var3 == null) {
            return 0;
         }

         int var4 = var1.getChildCount();

         for(int var5 = 0; var5 < var4; ++var5) {
            View var6 = var1.getChildAt(var5);
            if (var6.getId() != -1) {
               String var7 = null;

               label33: {
                  String var8;
                  try {
                     var8 = var3.getResourceEntryName(var6.getId());
                  } catch (NotFoundException var9) {
                     break label33;
                  }

                  var7 = var8;
               }

               if (var2.equals(var7)) {
                  return var6.getId();
               }
            }
         }
      }

      return 0;
   }

   private int findId(String var1) {
      ConstraintLayout var2;
      if (this.getParent() instanceof ConstraintLayout) {
         var2 = (ConstraintLayout)this.getParent();
      } else {
         var2 = null;
      }

      boolean var3 = this.isInEditMode();
      byte var4 = 0;
      int var5 = var4;
      if (var3) {
         var5 = var4;
         if (var2 != null) {
            Object var6 = var2.getDesignInformation(0, var1);
            var5 = var4;
            if (var6 instanceof Integer) {
               var5 = (Integer)var6;
            }
         }
      }

      int var8 = var5;
      if (var5 == 0) {
         var8 = var5;
         if (var2 != null) {
            var8 = this.findId(var2, var1);
         }
      }

      var5 = var8;
      if (var8 == 0) {
         try {
            var5 = R.id.class.getField(var1).getInt((Object)null);
         } catch (Exception var7) {
            var5 = var8;
         }
      }

      var8 = var5;
      if (var5 == 0) {
         var8 = this.myContext.getResources().getIdentifier(var1, "id", this.myContext.getPackageName());
      }

      return var8;
   }

   public void addView(View var1) {
      if (var1 != this) {
         if (var1.getId() == -1) {
            Log.e("ConstraintHelper", "Views added to a ConstraintHelper need to have an id");
         } else if (var1.getParent() == null) {
            Log.e("ConstraintHelper", "Views added to a ConstraintHelper need to have a parent");
         } else {
            this.mReferenceIds = null;
            this.addRscID(var1.getId());
            this.requestLayout();
         }
      }
   }

   protected void applyLayoutFeatures() {
      ViewParent var1 = this.getParent();
      if (var1 != null && var1 instanceof ConstraintLayout) {
         this.applyLayoutFeatures((ConstraintLayout)var1);
      }

   }

   protected void applyLayoutFeatures(ConstraintLayout var1) {
      int var2 = this.getVisibility();
      float var3;
      if (VERSION.SDK_INT >= 21) {
         var3 = this.getElevation();
      } else {
         var3 = 0.0F;
      }

      for(int var4 = 0; var4 < this.mCount; ++var4) {
         View var5 = var1.getViewById(this.mIds[var4]);
         if (var5 != null) {
            var5.setVisibility(var2);
            if (var3 > 0.0F && VERSION.SDK_INT >= 21) {
               var5.setTranslationZ(var5.getTranslationZ() + var3);
            }
         }
      }

   }

   public int[] getReferencedIds() {
      return Arrays.copyOf(this.mIds, this.mCount);
   }

   protected View[] getViews(ConstraintLayout var1) {
      View[] var2 = this.mViews;
      if (var2 == null || var2.length != this.mCount) {
         this.mViews = new View[this.mCount];
      }

      for(int var3 = 0; var3 < this.mCount; ++var3) {
         int var4 = this.mIds[var3];
         this.mViews[var3] = var1.getViewById(var4);
      }

      return this.mViews;
   }

   protected void init(AttributeSet var1) {
      if (var1 != null) {
         TypedArray var2 = this.getContext().obtainStyledAttributes(var1, R.styleable.ConstraintLayout_Layout);
         int var3 = var2.getIndexCount();

         for(int var4 = 0; var4 < var3; ++var4) {
            int var5 = var2.getIndex(var4);
            if (var5 == R.styleable.ConstraintLayout_Layout_constraint_referenced_ids) {
               String var6 = var2.getString(var5);
               this.mReferenceIds = var6;
               this.setIds(var6);
            }
         }
      }

   }

   public void loadParameters(ConstraintSet.Constraint var1, HelperWidget var2, ConstraintLayout.LayoutParams var3, SparseArray<ConstraintWidget> var4) {
      if (var1.layout.mReferenceIds != null) {
         this.setReferencedIds(var1.layout.mReferenceIds);
      } else if (var1.layout.mReferenceIdString != null && var1.layout.mReferenceIdString.length() > 0) {
         var1.layout.mReferenceIds = this.convertReferenceString(this, var1.layout.mReferenceIdString);
      }

      var2.removeAllIds();
      if (var1.layout.mReferenceIds != null) {
         for(int var5 = 0; var5 < var1.layout.mReferenceIds.length; ++var5) {
            ConstraintWidget var6 = (ConstraintWidget)var4.get(var1.layout.mReferenceIds[var5]);
            if (var6 != null) {
               var2.add(var6);
            }
         }
      }

   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      String var1 = this.mReferenceIds;
      if (var1 != null) {
         this.setIds(var1);
      }

   }

   public void onDraw(Canvas var1) {
   }

   protected void onMeasure(int var1, int var2) {
      if (this.mUseViewMeasure) {
         super.onMeasure(var1, var2);
      } else {
         this.setMeasuredDimension(0, 0);
      }

   }

   public void removeView(View var1) {
      int var2 = var1.getId();
      if (var2 != -1) {
         this.mReferenceIds = null;

         label26:
         for(int var3 = 0; var3 < this.mCount; ++var3) {
            if (this.mIds[var3] == var2) {
               while(true) {
                  var2 = this.mCount;
                  if (var3 >= var2 - 1) {
                     this.mIds[var2 - 1] = 0;
                     this.mCount = var2 - 1;
                     break label26;
                  }

                  int[] var4 = this.mIds;
                  var2 = var3 + 1;
                  var4[var3] = var4[var2];
                  var3 = var2;
               }
            }
         }

         this.requestLayout();
      }
   }

   public void resolveRtl(ConstraintWidget var1, boolean var2) {
   }

   protected void setIds(String var1) {
      this.mReferenceIds = var1;
      if (var1 != null) {
         int var2 = 0;
         this.mCount = 0;

         while(true) {
            int var3 = var1.indexOf(44, var2);
            if (var3 == -1) {
               this.addID(var1.substring(var2));
               return;
            }

            this.addID(var1.substring(var2, var3));
            var2 = var3 + 1;
         }
      }
   }

   public void setReferencedIds(int[] var1) {
      this.mReferenceIds = null;
      int var2 = 0;

      for(this.mCount = 0; var2 < var1.length; ++var2) {
         this.addRscID(var1[var2]);
      }

   }

   public void updatePostConstraints(ConstraintLayout var1) {
   }

   public void updatePostLayout(ConstraintLayout var1) {
   }

   public void updatePostMeasure(ConstraintLayout var1) {
   }

   public void updatePreDraw(ConstraintLayout var1) {
   }

   public void updatePreLayout(ConstraintWidgetContainer var1, Helper var2, SparseArray<ConstraintWidget> var3) {
      var2.removeAllIds();

      for(int var4 = 0; var4 < this.mCount; ++var4) {
         var2.add((ConstraintWidget)var3.get(this.mIds[var4]));
      }

   }

   public void updatePreLayout(ConstraintLayout var1) {
      if (this.isInEditMode()) {
         this.setIds(this.mReferenceIds);
      }

      Helper var2 = this.mHelperWidget;
      if (var2 != null) {
         var2.removeAllIds();

         for(int var3 = 0; var3 < this.mCount; ++var3) {
            int var4 = this.mIds[var3];
            View var5 = var1.getViewById(var4);
            View var7 = var5;
            if (var5 == null) {
               String var6 = (String)this.mMap.get(var4);
               var4 = this.findId(var1, var6);
               var7 = var5;
               if (var4 != 0) {
                  this.mIds[var3] = var4;
                  this.mMap.put(var4, var6);
                  var7 = var1.getViewById(var4);
               }
            }

            if (var7 != null) {
               this.mHelperWidget.add(var1.getViewWidget(var7));
            }
         }

         this.mHelperWidget.updateConstraints(var1.mLayoutWidget);
      }
   }

   public void validateParams() {
      if (this.mHelperWidget != null) {
         LayoutParams var1 = this.getLayoutParams();
         if (var1 instanceof ConstraintLayout.LayoutParams) {
            ((ConstraintLayout.LayoutParams)var1).widget = (ConstraintWidget)this.mHelperWidget;
         }

      }
   }
}

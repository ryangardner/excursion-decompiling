package androidx.constraintlayout.solver.state;

import androidx.constraintlayout.solver.state.helpers.AlignHorizontallyReference;
import androidx.constraintlayout.solver.state.helpers.AlignVerticallyReference;
import androidx.constraintlayout.solver.state.helpers.BarrierReference;
import androidx.constraintlayout.solver.state.helpers.GuidelineReference;
import androidx.constraintlayout.solver.state.helpers.HorizontalChainReference;
import androidx.constraintlayout.solver.state.helpers.VerticalChainReference;
import androidx.constraintlayout.solver.widgets.ConstraintWidget;
import androidx.constraintlayout.solver.widgets.ConstraintWidgetContainer;
import androidx.constraintlayout.solver.widgets.HelperWidget;
import java.util.HashMap;
import java.util.Iterator;

public class State {
   static final int CONSTRAINT_RATIO = 2;
   static final int CONSTRAINT_SPREAD = 0;
   static final int CONSTRAINT_WRAP = 1;
   public static final Integer PARENT = 0;
   static final int UNKNOWN = -1;
   protected HashMap<Object, HelperReference> mHelperReferences = new HashMap();
   public final ConstraintReference mParent;
   protected HashMap<Object, Reference> mReferences = new HashMap();
   private int numHelpers;

   public State() {
      ConstraintReference var1 = new ConstraintReference(this);
      this.mParent = var1;
      this.numHelpers = 0;
      this.mReferences.put(PARENT, var1);
   }

   private String createHelperKey() {
      StringBuilder var1 = new StringBuilder();
      var1.append("__HELPER_KEY_");
      int var2 = this.numHelpers++;
      var1.append(var2);
      var1.append("__");
      return var1.toString();
   }

   public void apply(ConstraintWidgetContainer var1) {
      var1.removeAllChildren();
      this.mParent.getWidth().apply(this, var1, 0);
      this.mParent.getHeight().apply(this, var1, 1);
      Iterator var2 = this.mHelperReferences.keySet().iterator();

      Object var6;
      while(var2.hasNext()) {
         Object var3 = var2.next();
         HelperWidget var4 = ((HelperReference)this.mHelperReferences.get(var3)).getHelperWidget();
         if (var4 != null) {
            Reference var5 = (Reference)this.mReferences.get(var3);
            var6 = var5;
            if (var5 == null) {
               var6 = this.constraints(var3);
            }

            ((Reference)var6).setConstraintWidget(var4);
         }
      }

      Iterator var13 = this.mReferences.keySet().iterator();

      Reference var8;
      while(var13.hasNext()) {
         Object var10 = var13.next();
         var8 = (Reference)this.mReferences.get(var10);
         if (var8 != this.mParent) {
            ConstraintWidget var11 = var8.getConstraintWidget();
            var11.setParent((ConstraintWidget)null);
            if (var8 instanceof GuidelineReference) {
               var8.apply();
            }

            var1.add(var11);
         } else {
            var8.setConstraintWidget(var1);
         }
      }

      Iterator var7 = this.mHelperReferences.keySet().iterator();

      while(true) {
         HelperReference var14;
         do {
            if (!var7.hasNext()) {
               var7 = this.mReferences.keySet().iterator();

               while(var7.hasNext()) {
                  var6 = var7.next();
                  ((Reference)this.mReferences.get(var6)).apply();
               }

               return;
            }

            var6 = var7.next();
            var14 = (HelperReference)this.mHelperReferences.get(var6);
         } while(var14.getHelperWidget() == null);

         Iterator var12 = var14.mReferences.iterator();

         while(var12.hasNext()) {
            Object var9 = var12.next();
            var8 = (Reference)this.mReferences.get(var9);
            var14.getHelperWidget().add(var8.getConstraintWidget());
         }

         var14.apply();
      }
   }

   public BarrierReference barrier(Object var1, State.Direction var2) {
      BarrierReference var3 = (BarrierReference)this.helper(var1, State.Helper.BARRIER);
      var3.setBarrierDirection(var2);
      return var3;
   }

   public AlignHorizontallyReference centerHorizontally(Object... var1) {
      AlignHorizontallyReference var2 = (AlignHorizontallyReference)this.helper((Object)null, State.Helper.ALIGN_HORIZONTALLY);
      var2.add(var1);
      return var2;
   }

   public AlignVerticallyReference centerVertically(Object... var1) {
      AlignVerticallyReference var2 = (AlignVerticallyReference)this.helper((Object)null, State.Helper.ALIGN_VERTICALLY);
      var2.add(var1);
      return var2;
   }

   public ConstraintReference constraints(Object var1) {
      Reference var2 = (Reference)this.mReferences.get(var1);
      Object var3 = var2;
      if (var2 == null) {
         var3 = this.createConstraintReference(var1);
         this.mReferences.put(var1, var3);
         ((Reference)var3).setKey(var1);
      }

      return var3 instanceof ConstraintReference ? (ConstraintReference)var3 : null;
   }

   public int convertDimension(Object var1) {
      if (var1 instanceof Float) {
         return ((Float)var1).intValue();
      } else {
         return var1 instanceof Integer ? (Integer)var1 : 0;
      }
   }

   public ConstraintReference createConstraintReference(Object var1) {
      return new ConstraintReference(this);
   }

   public void directMapping() {
      Iterator var1 = this.mReferences.keySet().iterator();

      while(var1.hasNext()) {
         Object var2 = var1.next();
         this.constraints(var2).setView(var2);
      }

   }

   public GuidelineReference guideline(Object var1, int var2) {
      Reference var3 = (Reference)this.mReferences.get(var1);
      Object var4 = var3;
      if (var3 == null) {
         var4 = new GuidelineReference(this);
         ((GuidelineReference)var4).setOrientation(var2);
         ((GuidelineReference)var4).setKey(var1);
         this.mReferences.put(var1, var4);
      }

      return (GuidelineReference)var4;
   }

   public State height(Dimension var1) {
      return this.setHeight(var1);
   }

   public HelperReference helper(Object var1, State.Helper var2) {
      Object var3 = var1;
      if (var1 == null) {
         var3 = this.createHelperKey();
      }

      HelperReference var4 = (HelperReference)this.mHelperReferences.get(var3);
      var1 = var4;
      if (var4 == null) {
         int var5 = null.$SwitchMap$androidx$constraintlayout$solver$state$State$Helper[var2.ordinal()];
         if (var5 != 1) {
            if (var5 != 2) {
               if (var5 != 3) {
                  if (var5 != 4) {
                     if (var5 != 5) {
                        var1 = new HelperReference(this, var2);
                     } else {
                        var1 = new BarrierReference(this);
                     }
                  } else {
                     var1 = new AlignVerticallyReference(this);
                  }
               } else {
                  var1 = new AlignHorizontallyReference(this);
               }
            } else {
               var1 = new VerticalChainReference(this);
            }
         } else {
            var1 = new HorizontalChainReference(this);
         }

         this.mHelperReferences.put(var3, var1);
      }

      return (HelperReference)var1;
   }

   public HorizontalChainReference horizontalChain(Object... var1) {
      HorizontalChainReference var2 = (HorizontalChainReference)this.helper((Object)null, State.Helper.HORIZONTAL_CHAIN);
      var2.add(var1);
      return var2;
   }

   public GuidelineReference horizontalGuideline(Object var1) {
      return this.guideline(var1, 0);
   }

   public void map(Object var1, Object var2) {
      this.constraints(var1).setView(var2);
   }

   Reference reference(Object var1) {
      return (Reference)this.mReferences.get(var1);
   }

   public void reset() {
      this.mHelperReferences.clear();
   }

   public State setHeight(Dimension var1) {
      this.mParent.setHeight(var1);
      return this;
   }

   public State setWidth(Dimension var1) {
      this.mParent.setWidth(var1);
      return this;
   }

   public VerticalChainReference verticalChain(Object... var1) {
      VerticalChainReference var2 = (VerticalChainReference)this.helper((Object)null, State.Helper.VERTICAL_CHAIN);
      var2.add(var1);
      return var2;
   }

   public GuidelineReference verticalGuideline(Object var1) {
      return this.guideline(var1, 1);
   }

   public State width(Dimension var1) {
      return this.setWidth(var1);
   }

   public static enum Chain {
      PACKED,
      SPREAD,
      SPREAD_INSIDE;

      static {
         State.Chain var0 = new State.Chain("PACKED", 2);
         PACKED = var0;
      }
   }

   public static enum Constraint {
      BASELINE_TO_BASELINE,
      BOTTOM_TO_BOTTOM,
      BOTTOM_TO_TOP,
      CENTER_HORIZONTALLY,
      CENTER_VERTICALLY,
      END_TO_END,
      END_TO_START,
      LEFT_TO_LEFT,
      LEFT_TO_RIGHT,
      RIGHT_TO_LEFT,
      RIGHT_TO_RIGHT,
      START_TO_END,
      START_TO_START,
      TOP_TO_BOTTOM,
      TOP_TO_TOP;

      static {
         State.Constraint var0 = new State.Constraint("CENTER_VERTICALLY", 14);
         CENTER_VERTICALLY = var0;
      }
   }

   public static enum Direction {
      BOTTOM,
      END,
      LEFT,
      RIGHT,
      START,
      TOP;

      static {
         State.Direction var0 = new State.Direction("BOTTOM", 5);
         BOTTOM = var0;
      }
   }

   public static enum Helper {
      ALIGN_HORIZONTALLY,
      ALIGN_VERTICALLY,
      BARRIER,
      FLOW,
      HORIZONTAL_CHAIN,
      LAYER,
      VERTICAL_CHAIN;

      static {
         State.Helper var0 = new State.Helper("FLOW", 6);
         FLOW = var0;
      }
   }
}

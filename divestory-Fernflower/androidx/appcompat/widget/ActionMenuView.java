package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.ContextThemeWrapper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.MeasureSpec;
import android.view.ViewDebug.ExportedProperty;
import android.view.accessibility.AccessibilityEvent;
import androidx.appcompat.view.menu.ActionMenuItemView;
import androidx.appcompat.view.menu.MenuBuilder;
import androidx.appcompat.view.menu.MenuItemImpl;
import androidx.appcompat.view.menu.MenuPresenter;
import androidx.appcompat.view.menu.MenuView;

public class ActionMenuView extends LinearLayoutCompat implements MenuBuilder.ItemInvoker, MenuView {
   static final int GENERATED_ITEM_PADDING = 4;
   static final int MIN_CELL_SIZE = 56;
   private static final String TAG = "ActionMenuView";
   private MenuPresenter.Callback mActionMenuPresenterCallback;
   private boolean mFormatItems;
   private int mFormatItemsWidth;
   private int mGeneratedItemPadding;
   private MenuBuilder mMenu;
   MenuBuilder.Callback mMenuBuilderCallback;
   private int mMinCellSize;
   ActionMenuView.OnMenuItemClickListener mOnMenuItemClickListener;
   private Context mPopupContext;
   private int mPopupTheme;
   private ActionMenuPresenter mPresenter;
   private boolean mReserveOverflow;

   public ActionMenuView(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public ActionMenuView(Context var1, AttributeSet var2) {
      super(var1, var2);
      this.setBaselineAligned(false);
      float var3 = var1.getResources().getDisplayMetrics().density;
      this.mMinCellSize = (int)(56.0F * var3);
      this.mGeneratedItemPadding = (int)(var3 * 4.0F);
      this.mPopupContext = var1;
      this.mPopupTheme = 0;
   }

   static int measureChildForCells(View var0, int var1, int var2, int var3, int var4) {
      ActionMenuView.LayoutParams var5 = (ActionMenuView.LayoutParams)var0.getLayoutParams();
      int var6 = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(var3) - var4, MeasureSpec.getMode(var3));
      ActionMenuItemView var7;
      if (var0 instanceof ActionMenuItemView) {
         var7 = (ActionMenuItemView)var0;
      } else {
         var7 = null;
      }

      boolean var8 = true;
      boolean var11;
      if (var7 != null && var7.hasText()) {
         var11 = true;
      } else {
         var11 = false;
      }

      byte var12 = 2;
      if (var2 > 0 && (!var11 || var2 >= 2)) {
         var0.measure(MeasureSpec.makeMeasureSpec(var2 * var1, Integer.MIN_VALUE), var6);
         int var9 = var0.getMeasuredWidth();
         int var10 = var9 / var1;
         var2 = var10;
         if (var9 % var1 != 0) {
            var2 = var10 + 1;
         }

         if (var11 && var2 < 2) {
            var2 = var12;
         }
      } else {
         var2 = 0;
      }

      if (var5.isOverflowButton || !var11) {
         var8 = false;
      }

      var5.expandable = var8;
      var5.cellsUsed = var2;
      var0.measure(MeasureSpec.makeMeasureSpec(var1 * var2, 1073741824), var6);
      return var2;
   }

   private void onMeasureExactFormat(int var1, int var2) {
      int var3 = MeasureSpec.getMode(var2);
      int var4 = MeasureSpec.getSize(var1);
      int var5 = MeasureSpec.getSize(var2);
      int var6 = this.getPaddingLeft();
      var1 = this.getPaddingRight();
      int var7 = this.getPaddingTop() + this.getPaddingBottom();
      int var8 = getChildMeasureSpec(var2, var7, -2);
      int var9 = var4 - (var6 + var1);
      var2 = this.mMinCellSize;
      var1 = var9 / var2;
      if (var1 == 0) {
         this.setMeasuredDimension(var9, 0);
      } else {
         int var10 = var2 + var9 % var2 / var1;
         int var11 = this.getChildCount();
         var6 = 0;
         int var12 = 0;
         boolean var34 = false;
         int var13 = 0;
         int var14 = 0;
         int var15 = 0;

         long var16;
         View var18;
         ActionMenuView.LayoutParams var20;
         for(var16 = 0L; var12 < var11; var15 = var2) {
            var18 = this.getChildAt(var12);
            if (var18.getVisibility() == 8) {
               var2 = var15;
            } else {
               boolean var19 = var18 instanceof ActionMenuItemView;
               ++var13;
               if (var19) {
                  var2 = this.mGeneratedItemPadding;
                  var18.setPadding(var2, 0, var2, 0);
               }

               var20 = (ActionMenuView.LayoutParams)var18.getLayoutParams();
               var20.expanded = false;
               var20.extraPixels = 0;
               var20.cellsUsed = 0;
               var20.expandable = false;
               var20.leftMargin = 0;
               var20.rightMargin = 0;
               if (var19 && ((ActionMenuItemView)var18).hasText()) {
                  var19 = true;
               } else {
                  var19 = false;
               }

               var20.preventEdgeOffset = var19;
               if (var20.isOverflowButton) {
                  var2 = 1;
               } else {
                  var2 = var1;
               }

               int var21 = measureChildForCells(var18, var10, var2, var8, var7);
               var14 = Math.max(var14, var21);
               var2 = var15;
               if (var20.expandable) {
                  var2 = var15 + 1;
               }

               if (var20.isOverflowButton) {
                  var34 = true;
               }

               var1 -= var21;
               var6 = Math.max(var6, var18.getMeasuredHeight());
               if (var21 == 1) {
                  var16 |= (long)(1 << var12);
               }
            }

            ++var12;
         }

         boolean var37;
         if (var34 && var13 == 2) {
            var37 = true;
         } else {
            var37 = false;
         }

         boolean var35 = false;
         var7 = var1;
         boolean var39 = var37;

         boolean var33;
         while(true) {
            if (var15 <= 0 || var7 <= 0) {
               var33 = var35;
               var2 = var6;
               break;
            }

            int var22 = 0;
            int var23 = 0;
            var9 = Integer.MAX_VALUE;

            long var24;
            long var27;
            for(var24 = 0L; var23 < var11; var24 = var27) {
               var20 = (ActionMenuView.LayoutParams)this.getChildAt(var23).getLayoutParams();
               int var26;
               if (!var20.expandable) {
                  var1 = var22;
                  var26 = var9;
                  var27 = var24;
               } else if (var20.cellsUsed < var9) {
                  var26 = var20.cellsUsed;
                  var27 = 1L << var23;
                  var1 = 1;
               } else {
                  var1 = var22;
                  var26 = var9;
                  var27 = var24;
                  if (var20.cellsUsed == var9) {
                     var1 = var22 + 1;
                     var27 = var24 | 1L << var23;
                     var26 = var9;
                  }
               }

               ++var23;
               var22 = var1;
               var9 = var26;
            }

            var33 = var35;
            var2 = var6;
            var16 |= var24;
            if (var22 > var7) {
               break;
            }

            for(var1 = 0; var1 < var11; ++var1) {
               var18 = this.getChildAt(var1);
               var20 = (ActionMenuView.LayoutParams)var18.getLayoutParams();
               long var29 = (long)(1 << var1);
               if ((var24 & var29) == 0L) {
                  var27 = var16;
                  if (var20.cellsUsed == var9 + 1) {
                     var27 = var16 | var29;
                  }

                  var16 = var27;
               } else {
                  if (var39 && var20.preventEdgeOffset && var7 == 1) {
                     var6 = this.mGeneratedItemPadding;
                     var18.setPadding(var6 + var10, 0, var6, 0);
                  }

                  ++var20.cellsUsed;
                  var20.expanded = true;
                  --var7;
               }
            }

            var6 = var6;
            var35 = true;
         }

         boolean var36;
         if (!var34 && var13 == 1) {
            var36 = true;
         } else {
            var36 = false;
         }

         boolean var38;
         if (var7 <= 0 || var16 == 0L || var7 >= var13 - 1 && !var36 && var14 <= 1) {
            var38 = var33;
         } else {
            float var31 = (float)Long.bitCount(var16);
            if (!var36) {
               float var32 = var31;
               if ((var16 & 1L) != 0L) {
                  var32 = var31;
                  if (!((ActionMenuView.LayoutParams)this.getChildAt(0).getLayoutParams()).preventEdgeOffset) {
                     var32 = var31 - 0.5F;
                  }
               }

               var6 = var11 - 1;
               var31 = var32;
               if ((var16 & (long)(1 << var6)) != 0L) {
                  var31 = var32;
                  if (!((ActionMenuView.LayoutParams)this.getChildAt(var6).getLayoutParams()).preventEdgeOffset) {
                     var31 = var32 - 0.5F;
                  }
               }
            }

            if (var31 > 0.0F) {
               var6 = (int)((float)(var7 * var10) / var31);
            } else {
               var6 = 0;
            }

            var4 = 0;

            while(true) {
               var38 = var33;
               if (var4 >= var11) {
                  break;
               }

               if ((var16 & (long)(1 << var4)) == 0L) {
                  var38 = var33;
               } else {
                  label236: {
                     var18 = this.getChildAt(var4);
                     var20 = (ActionMenuView.LayoutParams)var18.getLayoutParams();
                     if (var18 instanceof ActionMenuItemView) {
                        var20.extraPixels = var6;
                        var20.expanded = true;
                        if (var4 == 0 && !var20.preventEdgeOffset) {
                           var20.leftMargin = -var6 / 2;
                        }
                     } else {
                        if (!var20.isOverflowButton) {
                           if (var4 != 0) {
                              var20.leftMargin = var6 / 2;
                           }

                           var38 = var33;
                           if (var4 != var11 - 1) {
                              var20.rightMargin = var6 / 2;
                              var38 = var33;
                           }
                           break label236;
                        }

                        var20.extraPixels = var6;
                        var20.expanded = true;
                        var20.rightMargin = -var6 / 2;
                     }

                     var38 = true;
                  }
               }

               ++var4;
               var33 = var38;
            }
         }

         if (var38) {
            for(var1 = 0; var1 < var11; ++var1) {
               var18 = this.getChildAt(var1);
               var20 = (ActionMenuView.LayoutParams)var18.getLayoutParams();
               if (var20.expanded) {
                  var18.measure(MeasureSpec.makeMeasureSpec(var20.cellsUsed * var10 + var20.extraPixels, 1073741824), var8);
               }
            }
         }

         if (var3 == 1073741824) {
            var2 = var5;
         }

         this.setMeasuredDimension(var9, var2);
      }
   }

   protected boolean checkLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      return var1 instanceof ActionMenuView.LayoutParams;
   }

   public void dismissPopupMenus() {
      ActionMenuPresenter var1 = this.mPresenter;
      if (var1 != null) {
         var1.dismissPopupMenus();
      }

   }

   public boolean dispatchPopulateAccessibilityEvent(AccessibilityEvent var1) {
      return false;
   }

   protected ActionMenuView.LayoutParams generateDefaultLayoutParams() {
      ActionMenuView.LayoutParams var1 = new ActionMenuView.LayoutParams(-2, -2);
      var1.gravity = 16;
      return var1;
   }

   public ActionMenuView.LayoutParams generateLayoutParams(AttributeSet var1) {
      return new ActionMenuView.LayoutParams(this.getContext(), var1);
   }

   protected ActionMenuView.LayoutParams generateLayoutParams(android.view.ViewGroup.LayoutParams var1) {
      if (var1 != null) {
         ActionMenuView.LayoutParams var2;
         if (var1 instanceof ActionMenuView.LayoutParams) {
            var2 = new ActionMenuView.LayoutParams((ActionMenuView.LayoutParams)var1);
         } else {
            var2 = new ActionMenuView.LayoutParams(var1);
         }

         if (var2.gravity <= 0) {
            var2.gravity = 16;
         }

         return var2;
      } else {
         return this.generateDefaultLayoutParams();
      }
   }

   public ActionMenuView.LayoutParams generateOverflowButtonLayoutParams() {
      ActionMenuView.LayoutParams var1 = this.generateDefaultLayoutParams();
      var1.isOverflowButton = true;
      return var1;
   }

   public Menu getMenu() {
      if (this.mMenu == null) {
         Context var1 = this.getContext();
         MenuBuilder var2 = new MenuBuilder(var1);
         this.mMenu = var2;
         var2.setCallback(new ActionMenuView.MenuBuilderCallback());
         ActionMenuPresenter var3 = new ActionMenuPresenter(var1);
         this.mPresenter = var3;
         var3.setReserveOverflow(true);
         ActionMenuPresenter var5 = this.mPresenter;
         Object var4 = this.mActionMenuPresenterCallback;
         if (var4 == null) {
            var4 = new ActionMenuView.ActionMenuPresenterCallback();
         }

         var5.setCallback((MenuPresenter.Callback)var4);
         this.mMenu.addMenuPresenter(this.mPresenter, this.mPopupContext);
         this.mPresenter.setMenuView(this);
      }

      return this.mMenu;
   }

   public Drawable getOverflowIcon() {
      this.getMenu();
      return this.mPresenter.getOverflowIcon();
   }

   public int getPopupTheme() {
      return this.mPopupTheme;
   }

   public int getWindowAnimations() {
      return 0;
   }

   protected boolean hasSupportDividerBeforeChildAt(int var1) {
      boolean var2 = false;
      if (var1 == 0) {
         return false;
      } else {
         View var3 = this.getChildAt(var1 - 1);
         View var4 = this.getChildAt(var1);
         boolean var5 = var2;
         if (var1 < this.getChildCount()) {
            var5 = var2;
            if (var3 instanceof ActionMenuView.ActionMenuChildView) {
               var5 = false | ((ActionMenuView.ActionMenuChildView)var3).needsDividerAfter();
            }
         }

         var2 = var5;
         if (var1 > 0) {
            var2 = var5;
            if (var4 instanceof ActionMenuView.ActionMenuChildView) {
               var2 = var5 | ((ActionMenuView.ActionMenuChildView)var4).needsDividerBefore();
            }
         }

         return var2;
      }
   }

   public boolean hideOverflowMenu() {
      ActionMenuPresenter var1 = this.mPresenter;
      boolean var2;
      if (var1 != null && var1.hideOverflowMenu()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public void initialize(MenuBuilder var1) {
      this.mMenu = var1;
   }

   public boolean invokeItem(MenuItemImpl var1) {
      return this.mMenu.performItemAction(var1, 0);
   }

   public boolean isOverflowMenuShowPending() {
      ActionMenuPresenter var1 = this.mPresenter;
      boolean var2;
      if (var1 != null && var1.isOverflowMenuShowPending()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isOverflowMenuShowing() {
      ActionMenuPresenter var1 = this.mPresenter;
      boolean var2;
      if (var1 != null && var1.isOverflowMenuShowing()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isOverflowReserved() {
      return this.mReserveOverflow;
   }

   public void onConfigurationChanged(Configuration var1) {
      super.onConfigurationChanged(var1);
      ActionMenuPresenter var2 = this.mPresenter;
      if (var2 != null) {
         var2.updateMenuView(false);
         if (this.mPresenter.isOverflowMenuShowing()) {
            this.mPresenter.hideOverflowMenu();
            this.mPresenter.showOverflowMenu();
         }
      }

   }

   public void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      this.dismissPopupMenus();
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      if (!this.mFormatItems) {
         super.onLayout(var1, var2, var3, var4, var5);
      } else {
         int var6 = this.getChildCount();
         int var7 = (var5 - var3) / 2;
         int var8 = this.getDividerWidth();
         int var9 = var4 - var2;
         var2 = var9 - this.getPaddingRight() - this.getPaddingLeft();
         var1 = ViewUtils.isLayoutRtl(this);
         var4 = 0;
         byte var16 = 0;

         View var10;
         int var12;
         int var14;
         for(var3 = 0; var4 < var6; ++var4) {
            var10 = this.getChildAt(var4);
            if (var10.getVisibility() != 8) {
               ActionMenuView.LayoutParams var11 = (ActionMenuView.LayoutParams)var10.getLayoutParams();
               if (var11.isOverflowButton) {
                  var12 = var10.getMeasuredWidth();
                  var5 = var12;
                  if (this.hasSupportDividerBeforeChildAt(var4)) {
                     var5 = var12 + var8;
                  }

                  int var13 = var10.getMeasuredHeight();
                  if (var1) {
                     var14 = this.getPaddingLeft() + var11.leftMargin;
                     var12 = var14 + var5;
                  } else {
                     var12 = this.getWidth() - this.getPaddingRight() - var11.rightMargin;
                     var14 = var12 - var5;
                  }

                  int var15 = var7 - var13 / 2;
                  var10.layout(var14, var15, var12, var13 + var15);
                  var2 -= var5;
                  var16 = 1;
               } else {
                  var2 -= var10.getMeasuredWidth() + var11.leftMargin + var11.rightMargin;
                  this.hasSupportDividerBeforeChildAt(var4);
                  ++var3;
               }
            }
         }

         if (var6 == 1 && var16 == 0) {
            var10 = this.getChildAt(0);
            var3 = var10.getMeasuredWidth();
            var2 = var10.getMeasuredHeight();
            var4 = var9 / 2 - var3 / 2;
            var5 = var7 - var2 / 2;
            var10.layout(var4, var5, var3 + var4, var2 + var5);
         } else {
            var3 -= var16 ^ 1;
            if (var3 > 0) {
               var2 /= var3;
            } else {
               var2 = 0;
            }

            var5 = Math.max(0, var2);
            ActionMenuView.LayoutParams var17;
            View var18;
            if (var1) {
               var3 = this.getWidth() - this.getPaddingRight();

               for(var2 = 0; var2 < var6; var3 = var4) {
                  var18 = this.getChildAt(var2);
                  var17 = (ActionMenuView.LayoutParams)var18.getLayoutParams();
                  var4 = var3;
                  if (var18.getVisibility() != 8) {
                     if (var17.isOverflowButton) {
                        var4 = var3;
                     } else {
                        var14 = var3 - var17.rightMargin;
                        var3 = var18.getMeasuredWidth();
                        var4 = var18.getMeasuredHeight();
                        var12 = var7 - var4 / 2;
                        var18.layout(var14 - var3, var12, var14, var4 + var12);
                        var4 = var14 - (var3 + var17.leftMargin + var5);
                     }
                  }

                  ++var2;
               }
            } else {
               var4 = this.getPaddingLeft();

               for(var2 = 0; var2 < var6; var4 = var3) {
                  var18 = this.getChildAt(var2);
                  var17 = (ActionMenuView.LayoutParams)var18.getLayoutParams();
                  var3 = var4;
                  if (var18.getVisibility() != 8) {
                     if (var17.isOverflowButton) {
                        var3 = var4;
                     } else {
                        var14 = var4 + var17.leftMargin;
                        var3 = var18.getMeasuredWidth();
                        var4 = var18.getMeasuredHeight();
                        var12 = var7 - var4 / 2;
                        var18.layout(var14, var12, var14 + var3, var4 + var12);
                        var3 = var14 + var3 + var17.rightMargin + var5;
                     }
                  }

                  ++var2;
               }
            }

         }
      }
   }

   protected void onMeasure(int var1, int var2) {
      boolean var3 = this.mFormatItems;
      boolean var4;
      if (MeasureSpec.getMode(var1) == 1073741824) {
         var4 = true;
      } else {
         var4 = false;
      }

      this.mFormatItems = var4;
      if (var3 != var4) {
         this.mFormatItemsWidth = 0;
      }

      int var5 = MeasureSpec.getSize(var1);
      if (this.mFormatItems) {
         MenuBuilder var6 = this.mMenu;
         if (var6 != null && var5 != this.mFormatItemsWidth) {
            this.mFormatItemsWidth = var5;
            var6.onItemsChanged(true);
         }
      }

      int var7 = this.getChildCount();
      if (this.mFormatItems && var7 > 0) {
         this.onMeasureExactFormat(var1, var2);
      } else {
         for(var5 = 0; var5 < var7; ++var5) {
            ActionMenuView.LayoutParams var8 = (ActionMenuView.LayoutParams)this.getChildAt(var5).getLayoutParams();
            var8.rightMargin = 0;
            var8.leftMargin = 0;
         }

         super.onMeasure(var1, var2);
      }

   }

   public MenuBuilder peekMenu() {
      return this.mMenu;
   }

   public void setExpandedActionViewsExclusive(boolean var1) {
      this.mPresenter.setExpandedActionViewsExclusive(var1);
   }

   public void setMenuCallbacks(MenuPresenter.Callback var1, MenuBuilder.Callback var2) {
      this.mActionMenuPresenterCallback = var1;
      this.mMenuBuilderCallback = var2;
   }

   public void setOnMenuItemClickListener(ActionMenuView.OnMenuItemClickListener var1) {
      this.mOnMenuItemClickListener = var1;
   }

   public void setOverflowIcon(Drawable var1) {
      this.getMenu();
      this.mPresenter.setOverflowIcon(var1);
   }

   public void setOverflowReserved(boolean var1) {
      this.mReserveOverflow = var1;
   }

   public void setPopupTheme(int var1) {
      if (this.mPopupTheme != var1) {
         this.mPopupTheme = var1;
         if (var1 == 0) {
            this.mPopupContext = this.getContext();
         } else {
            this.mPopupContext = new ContextThemeWrapper(this.getContext(), var1);
         }
      }

   }

   public void setPresenter(ActionMenuPresenter var1) {
      this.mPresenter = var1;
      var1.setMenuView(this);
   }

   public boolean showOverflowMenu() {
      ActionMenuPresenter var1 = this.mPresenter;
      boolean var2;
      if (var1 != null && var1.showOverflowMenu()) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public interface ActionMenuChildView {
      boolean needsDividerAfter();

      boolean needsDividerBefore();
   }

   private static class ActionMenuPresenterCallback implements MenuPresenter.Callback {
      ActionMenuPresenterCallback() {
      }

      public void onCloseMenu(MenuBuilder var1, boolean var2) {
      }

      public boolean onOpenSubMenu(MenuBuilder var1) {
         return false;
      }
   }

   public static class LayoutParams extends LinearLayoutCompat.LayoutParams {
      @ExportedProperty
      public int cellsUsed;
      @ExportedProperty
      public boolean expandable;
      boolean expanded;
      @ExportedProperty
      public int extraPixels;
      @ExportedProperty
      public boolean isOverflowButton;
      @ExportedProperty
      public boolean preventEdgeOffset;

      public LayoutParams(int var1, int var2) {
         super(var1, var2);
         this.isOverflowButton = false;
      }

      LayoutParams(int var1, int var2, boolean var3) {
         super(var1, var2);
         this.isOverflowButton = var3;
      }

      public LayoutParams(Context var1, AttributeSet var2) {
         super(var1, var2);
      }

      public LayoutParams(android.view.ViewGroup.LayoutParams var1) {
         super(var1);
      }

      public LayoutParams(ActionMenuView.LayoutParams var1) {
         super((android.view.ViewGroup.LayoutParams)var1);
         this.isOverflowButton = var1.isOverflowButton;
      }
   }

   private class MenuBuilderCallback implements MenuBuilder.Callback {
      MenuBuilderCallback() {
      }

      public boolean onMenuItemSelected(MenuBuilder var1, MenuItem var2) {
         boolean var3;
         if (ActionMenuView.this.mOnMenuItemClickListener != null && ActionMenuView.this.mOnMenuItemClickListener.onMenuItemClick(var2)) {
            var3 = true;
         } else {
            var3 = false;
         }

         return var3;
      }

      public void onMenuModeChange(MenuBuilder var1) {
         if (ActionMenuView.this.mMenuBuilderCallback != null) {
            ActionMenuView.this.mMenuBuilderCallback.onMenuModeChange(var1);
         }

      }
   }

   public interface OnMenuItemClickListener {
      boolean onMenuItemClick(MenuItem var1);
   }
}

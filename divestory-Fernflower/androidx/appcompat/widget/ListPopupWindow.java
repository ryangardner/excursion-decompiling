package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Build.VERSION;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.KeyEvent.DispatcherState;
import android.view.View.MeasureSpec;
import android.view.View.OnTouchListener;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow.OnDismissListener;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.view.ViewCompat;
import androidx.core.widget.PopupWindowCompat;
import java.lang.reflect.Method;

public class ListPopupWindow implements ShowableListMenu {
   private static final boolean DEBUG = false;
   static final int EXPAND_LIST_TIMEOUT = 250;
   public static final int INPUT_METHOD_FROM_FOCUSABLE = 0;
   public static final int INPUT_METHOD_NEEDED = 1;
   public static final int INPUT_METHOD_NOT_NEEDED = 2;
   public static final int MATCH_PARENT = -1;
   public static final int POSITION_PROMPT_ABOVE = 0;
   public static final int POSITION_PROMPT_BELOW = 1;
   private static final String TAG = "ListPopupWindow";
   public static final int WRAP_CONTENT = -2;
   private static Method sGetMaxAvailableHeightMethod;
   private static Method sSetClipToWindowEnabledMethod;
   private static Method sSetEpicenterBoundsMethod;
   private ListAdapter mAdapter;
   private Context mContext;
   private boolean mDropDownAlwaysVisible;
   private View mDropDownAnchorView;
   private int mDropDownGravity;
   private int mDropDownHeight;
   private int mDropDownHorizontalOffset;
   DropDownListView mDropDownList;
   private Drawable mDropDownListHighlight;
   private int mDropDownVerticalOffset;
   private boolean mDropDownVerticalOffsetSet;
   private int mDropDownWidth;
   private int mDropDownWindowLayoutType;
   private Rect mEpicenterBounds;
   private boolean mForceIgnoreOutsideTouch;
   final Handler mHandler;
   private final ListPopupWindow.ListSelectorHider mHideSelector;
   private OnItemClickListener mItemClickListener;
   private OnItemSelectedListener mItemSelectedListener;
   int mListItemExpandMaximum;
   private boolean mModal;
   private DataSetObserver mObserver;
   private boolean mOverlapAnchor;
   private boolean mOverlapAnchorSet;
   PopupWindow mPopup;
   private int mPromptPosition;
   private View mPromptView;
   final ListPopupWindow.ResizePopupRunnable mResizePopupRunnable;
   private final ListPopupWindow.PopupScrollListener mScrollListener;
   private Runnable mShowDropDownRunnable;
   private final Rect mTempRect;
   private final ListPopupWindow.PopupTouchInterceptor mTouchInterceptor;

   static {
      if (VERSION.SDK_INT <= 28) {
         try {
            sSetClipToWindowEnabledMethod = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
         } catch (NoSuchMethodException var3) {
            Log.i("ListPopupWindow", "Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
         }

         try {
            sSetEpicenterBoundsMethod = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
         } catch (NoSuchMethodException var2) {
            Log.i("ListPopupWindow", "Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
         }
      }

      if (VERSION.SDK_INT <= 23) {
         try {
            sGetMaxAvailableHeightMethod = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE);
         } catch (NoSuchMethodException var1) {
            Log.i("ListPopupWindow", "Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
         }
      }

   }

   public ListPopupWindow(Context var1) {
      this(var1, (AttributeSet)null, R.attr.listPopupWindowStyle);
   }

   public ListPopupWindow(Context var1, AttributeSet var2) {
      this(var1, var2, R.attr.listPopupWindowStyle);
   }

   public ListPopupWindow(Context var1, AttributeSet var2, int var3) {
      this(var1, var2, var3, 0);
   }

   public ListPopupWindow(Context var1, AttributeSet var2, int var3, int var4) {
      this.mDropDownHeight = -2;
      this.mDropDownWidth = -2;
      this.mDropDownWindowLayoutType = 1002;
      this.mDropDownGravity = 0;
      this.mDropDownAlwaysVisible = false;
      this.mForceIgnoreOutsideTouch = false;
      this.mListItemExpandMaximum = Integer.MAX_VALUE;
      this.mPromptPosition = 0;
      this.mResizePopupRunnable = new ListPopupWindow.ResizePopupRunnable();
      this.mTouchInterceptor = new ListPopupWindow.PopupTouchInterceptor();
      this.mScrollListener = new ListPopupWindow.PopupScrollListener();
      this.mHideSelector = new ListPopupWindow.ListSelectorHider();
      this.mTempRect = new Rect();
      this.mContext = var1;
      this.mHandler = new Handler(var1.getMainLooper());
      TypedArray var5 = var1.obtainStyledAttributes(var2, R.styleable.ListPopupWindow, var3, var4);
      this.mDropDownHorizontalOffset = var5.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
      int var6 = var5.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
      this.mDropDownVerticalOffset = var6;
      if (var6 != 0) {
         this.mDropDownVerticalOffsetSet = true;
      }

      var5.recycle();
      AppCompatPopupWindow var7 = new AppCompatPopupWindow(var1, var2, var3, var4);
      this.mPopup = var7;
      var7.setInputMethodMode(1);
   }

   private int buildDropDown() {
      DropDownListView var1 = this.mDropDownList;
      boolean var2 = true;
      int var6;
      int var7;
      LayoutParams var18;
      if (var1 == null) {
         Context var10 = this.mContext;
         this.mShowDropDownRunnable = new Runnable() {
            public void run() {
               View var1 = ListPopupWindow.this.getAnchorView();
               if (var1 != null && var1.getWindowToken() != null) {
                  ListPopupWindow.this.show();
               }

            }
         };
         DropDownListView var3 = this.createDropDownListView(var10, this.mModal ^ true);
         this.mDropDownList = var3;
         Drawable var4 = this.mDropDownListHighlight;
         if (var4 != null) {
            var3.setSelector(var4);
         }

         this.mDropDownList.setAdapter(this.mAdapter);
         this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
         this.mDropDownList.setFocusable(true);
         this.mDropDownList.setFocusableInTouchMode(true);
         this.mDropDownList.setOnItemSelectedListener(new OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> var1, View var2, int var3, long var4) {
               if (var3 != -1) {
                  DropDownListView var6 = ListPopupWindow.this.mDropDownList;
                  if (var6 != null) {
                     var6.setListSelectionHidden(false);
                  }
               }

            }

            public void onNothingSelected(AdapterView<?> var1) {
            }
         });
         this.mDropDownList.setOnScrollListener(this.mScrollListener);
         OnItemSelectedListener var14 = this.mItemSelectedListener;
         if (var14 != null) {
            this.mDropDownList.setOnItemSelectedListener(var14);
         }

         var3 = this.mDropDownList;
         View var17 = this.mPromptView;
         Object var11;
         if (var17 != null) {
            var11 = new LinearLayout(var10);
            ((LinearLayout)var11).setOrientation(1);
            LayoutParams var5 = new LayoutParams(-1, 0, 1.0F);
            var6 = this.mPromptPosition;
            if (var6 != 0) {
               if (var6 != 1) {
                  StringBuilder var16 = new StringBuilder();
                  var16.append("Invalid hint position ");
                  var16.append(this.mPromptPosition);
                  Log.e("ListPopupWindow", var16.toString());
               } else {
                  ((LinearLayout)var11).addView(var3, var5);
                  ((LinearLayout)var11).addView(var17);
               }
            } else {
               ((LinearLayout)var11).addView(var17);
               ((LinearLayout)var11).addView(var3, var5);
            }

            var6 = this.mDropDownWidth;
            if (var6 >= 0) {
               var7 = Integer.MIN_VALUE;
            } else {
               var6 = 0;
               var7 = 0;
            }

            var17.measure(MeasureSpec.makeMeasureSpec(var6, var7), 0);
            var18 = (LayoutParams)var17.getLayoutParams();
            var6 = var17.getMeasuredHeight() + var18.topMargin + var18.bottomMargin;
         } else {
            var6 = 0;
            var11 = var3;
         }

         this.mPopup.setContentView((View)var11);
      } else {
         ViewGroup var12 = (ViewGroup)this.mPopup.getContentView();
         View var13 = this.mPromptView;
         if (var13 != null) {
            var18 = (LayoutParams)var13.getLayoutParams();
            var6 = var13.getMeasuredHeight() + var18.topMargin + var18.bottomMargin;
         } else {
            var6 = 0;
         }
      }

      Drawable var15 = this.mPopup.getBackground();
      int var8;
      if (var15 != null) {
         var15.getPadding(this.mTempRect);
         var7 = this.mTempRect.top + this.mTempRect.bottom;
         var8 = var7;
         if (!this.mDropDownVerticalOffsetSet) {
            this.mDropDownVerticalOffset = -this.mTempRect.top;
            var8 = var7;
         }
      } else {
         this.mTempRect.setEmpty();
         var8 = 0;
      }

      if (this.mPopup.getInputMethodMode() != 2) {
         var2 = false;
      }

      int var9 = this.getMaxAvailableHeight(this.getAnchorView(), this.mDropDownVerticalOffset, var2);
      if (!this.mDropDownAlwaysVisible && this.mDropDownHeight != -1) {
         var7 = this.mDropDownWidth;
         int var10001;
         if (var7 != -2) {
            if (var7 != -1) {
               var7 = MeasureSpec.makeMeasureSpec(var7, 1073741824);
            } else {
               var10001 = this.mTempRect.left + this.mTempRect.right;
               var7 = MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - var10001, 1073741824);
            }
         } else {
            var10001 = this.mTempRect.left + this.mTempRect.right;
            var7 = MeasureSpec.makeMeasureSpec(this.mContext.getResources().getDisplayMetrics().widthPixels - var10001, Integer.MIN_VALUE);
         }

         var9 = this.mDropDownList.measureHeightOfChildrenCompat(var7, 0, -1, var9 - var6, -1);
         var7 = var6;
         if (var9 > 0) {
            var7 = var6 + var8 + this.mDropDownList.getPaddingTop() + this.mDropDownList.getPaddingBottom();
         }

         return var9 + var7;
      } else {
         return var9 + var8;
      }
   }

   private int getMaxAvailableHeight(View var1, int var2, boolean var3) {
      if (VERSION.SDK_INT > 23) {
         return this.mPopup.getMaxAvailableHeight(var1, var2, var3);
      } else {
         Method var4 = sGetMaxAvailableHeightMethod;
         if (var4 != null) {
            try {
               int var5 = (Integer)var4.invoke(this.mPopup, var1, var2, var3);
               return var5;
            } catch (Exception var6) {
               Log.i("ListPopupWindow", "Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
            }
         }

         return this.mPopup.getMaxAvailableHeight(var1, var2);
      }
   }

   private static boolean isConfirmKey(int var0) {
      boolean var1;
      if (var0 != 66 && var0 != 23) {
         var1 = false;
      } else {
         var1 = true;
      }

      return var1;
   }

   private void removePromptView() {
      View var1 = this.mPromptView;
      if (var1 != null) {
         ViewParent var2 = var1.getParent();
         if (var2 instanceof ViewGroup) {
            ((ViewGroup)var2).removeView(this.mPromptView);
         }
      }

   }

   private void setPopupClipToScreenEnabled(boolean var1) {
      if (VERSION.SDK_INT <= 28) {
         Method var2 = sSetClipToWindowEnabledMethod;
         if (var2 != null) {
            try {
               var2.invoke(this.mPopup, var1);
            } catch (Exception var3) {
               Log.i("ListPopupWindow", "Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
         }
      } else {
         this.mPopup.setIsClippedToScreen(var1);
      }

   }

   public void clearListSelection() {
      DropDownListView var1 = this.mDropDownList;
      if (var1 != null) {
         var1.setListSelectionHidden(true);
         var1.requestLayout();
      }

   }

   public OnTouchListener createDragToOpenListener(View var1) {
      return new ForwardingListener(var1) {
         public ListPopupWindow getPopup() {
            return ListPopupWindow.this;
         }
      };
   }

   DropDownListView createDropDownListView(Context var1, boolean var2) {
      return new DropDownListView(var1, var2);
   }

   public void dismiss() {
      this.mPopup.dismiss();
      this.removePromptView();
      this.mPopup.setContentView((View)null);
      this.mDropDownList = null;
      this.mHandler.removeCallbacks(this.mResizePopupRunnable);
   }

   public View getAnchorView() {
      return this.mDropDownAnchorView;
   }

   public int getAnimationStyle() {
      return this.mPopup.getAnimationStyle();
   }

   public Drawable getBackground() {
      return this.mPopup.getBackground();
   }

   public Rect getEpicenterBounds() {
      Rect var1;
      if (this.mEpicenterBounds != null) {
         var1 = new Rect(this.mEpicenterBounds);
      } else {
         var1 = null;
      }

      return var1;
   }

   public int getHeight() {
      return this.mDropDownHeight;
   }

   public int getHorizontalOffset() {
      return this.mDropDownHorizontalOffset;
   }

   public int getInputMethodMode() {
      return this.mPopup.getInputMethodMode();
   }

   public ListView getListView() {
      return this.mDropDownList;
   }

   public int getPromptPosition() {
      return this.mPromptPosition;
   }

   public Object getSelectedItem() {
      return !this.isShowing() ? null : this.mDropDownList.getSelectedItem();
   }

   public long getSelectedItemId() {
      return !this.isShowing() ? Long.MIN_VALUE : this.mDropDownList.getSelectedItemId();
   }

   public int getSelectedItemPosition() {
      return !this.isShowing() ? -1 : this.mDropDownList.getSelectedItemPosition();
   }

   public View getSelectedView() {
      return !this.isShowing() ? null : this.mDropDownList.getSelectedView();
   }

   public int getSoftInputMode() {
      return this.mPopup.getSoftInputMode();
   }

   public int getVerticalOffset() {
      return !this.mDropDownVerticalOffsetSet ? 0 : this.mDropDownVerticalOffset;
   }

   public int getWidth() {
      return this.mDropDownWidth;
   }

   public boolean isDropDownAlwaysVisible() {
      return this.mDropDownAlwaysVisible;
   }

   public boolean isInputMethodNotNeeded() {
      boolean var1;
      if (this.mPopup.getInputMethodMode() == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isModal() {
      return this.mModal;
   }

   public boolean isShowing() {
      return this.mPopup.isShowing();
   }

   public boolean onKeyDown(int var1, KeyEvent var2) {
      if (this.isShowing() && var1 != 62 && (this.mDropDownList.getSelectedItemPosition() >= 0 || !isConfirmKey(var1))) {
         int var3 = this.mDropDownList.getSelectedItemPosition();
         boolean var4 = this.mPopup.isAboveAnchor() ^ true;
         ListAdapter var5 = this.mAdapter;
         int var6 = Integer.MAX_VALUE;
         int var7 = Integer.MIN_VALUE;
         if (var5 != null) {
            boolean var8 = var5.areAllItemsEnabled();
            if (var8) {
               var6 = 0;
            } else {
               var6 = this.mDropDownList.lookForSelectablePosition(0, true);
            }

            if (var8) {
               var7 = var5.getCount() - 1;
            } else {
               var7 = this.mDropDownList.lookForSelectablePosition(var5.getCount() - 1, false);
            }
         }

         if (var4 && var1 == 19 && var3 <= var6 || !var4 && var1 == 20 && var3 >= var7) {
            this.clearListSelection();
            this.mPopup.setInputMethodMode(1);
            this.show();
            return true;
         }

         this.mDropDownList.setListSelectionHidden(false);
         if (this.mDropDownList.onKeyDown(var1, var2)) {
            this.mPopup.setInputMethodMode(2);
            this.mDropDownList.requestFocusFromTouch();
            this.show();
            if (var1 == 19 || var1 == 20 || var1 == 23 || var1 == 66) {
               return true;
            }
         } else if (var4 && var1 == 20) {
            if (var3 == var7) {
               return true;
            }
         } else if (!var4 && var1 == 19 && var3 == var6) {
            return true;
         }
      }

      return false;
   }

   public boolean onKeyPreIme(int var1, KeyEvent var2) {
      if (var1 == 4 && this.isShowing()) {
         View var3 = this.mDropDownAnchorView;
         DispatcherState var4;
         if (var2.getAction() == 0 && var2.getRepeatCount() == 0) {
            var4 = var3.getKeyDispatcherState();
            if (var4 != null) {
               var4.startTracking(var2, this);
            }

            return true;
         }

         if (var2.getAction() == 1) {
            var4 = var3.getKeyDispatcherState();
            if (var4 != null) {
               var4.handleUpEvent(var2);
            }

            if (var2.isTracking() && !var2.isCanceled()) {
               this.dismiss();
               return true;
            }
         }
      }

      return false;
   }

   public boolean onKeyUp(int var1, KeyEvent var2) {
      if (this.isShowing() && this.mDropDownList.getSelectedItemPosition() >= 0) {
         boolean var3 = this.mDropDownList.onKeyUp(var1, var2);
         if (var3 && isConfirmKey(var1)) {
            this.dismiss();
         }

         return var3;
      } else {
         return false;
      }
   }

   public boolean performItemClick(int var1) {
      if (this.isShowing()) {
         if (this.mItemClickListener != null) {
            DropDownListView var2 = this.mDropDownList;
            View var3 = var2.getChildAt(var1 - var2.getFirstVisiblePosition());
            ListAdapter var4 = var2.getAdapter();
            this.mItemClickListener.onItemClick(var2, var3, var1, var4.getItemId(var1));
         }

         return true;
      } else {
         return false;
      }
   }

   public void postShow() {
      this.mHandler.post(this.mShowDropDownRunnable);
   }

   public void setAdapter(ListAdapter var1) {
      DataSetObserver var2 = this.mObserver;
      if (var2 == null) {
         this.mObserver = new ListPopupWindow.PopupDataSetObserver();
      } else {
         ListAdapter var3 = this.mAdapter;
         if (var3 != null) {
            var3.unregisterDataSetObserver(var2);
         }
      }

      this.mAdapter = var1;
      if (var1 != null) {
         var1.registerDataSetObserver(this.mObserver);
      }

      DropDownListView var4 = this.mDropDownList;
      if (var4 != null) {
         var4.setAdapter(this.mAdapter);
      }

   }

   public void setAnchorView(View var1) {
      this.mDropDownAnchorView = var1;
   }

   public void setAnimationStyle(int var1) {
      this.mPopup.setAnimationStyle(var1);
   }

   public void setBackgroundDrawable(Drawable var1) {
      this.mPopup.setBackgroundDrawable(var1);
   }

   public void setContentWidth(int var1) {
      Drawable var2 = this.mPopup.getBackground();
      if (var2 != null) {
         var2.getPadding(this.mTempRect);
         this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + var1;
      } else {
         this.setWidth(var1);
      }

   }

   public void setDropDownAlwaysVisible(boolean var1) {
      this.mDropDownAlwaysVisible = var1;
   }

   public void setDropDownGravity(int var1) {
      this.mDropDownGravity = var1;
   }

   public void setEpicenterBounds(Rect var1) {
      if (var1 != null) {
         var1 = new Rect(var1);
      } else {
         var1 = null;
      }

      this.mEpicenterBounds = var1;
   }

   public void setForceIgnoreOutsideTouch(boolean var1) {
      this.mForceIgnoreOutsideTouch = var1;
   }

   public void setHeight(int var1) {
      if (var1 < 0 && -2 != var1 && -1 != var1) {
         throw new IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
      } else {
         this.mDropDownHeight = var1;
      }
   }

   public void setHorizontalOffset(int var1) {
      this.mDropDownHorizontalOffset = var1;
   }

   public void setInputMethodMode(int var1) {
      this.mPopup.setInputMethodMode(var1);
   }

   void setListItemExpandMax(int var1) {
      this.mListItemExpandMaximum = var1;
   }

   public void setListSelector(Drawable var1) {
      this.mDropDownListHighlight = var1;
   }

   public void setModal(boolean var1) {
      this.mModal = var1;
      this.mPopup.setFocusable(var1);
   }

   public void setOnDismissListener(OnDismissListener var1) {
      this.mPopup.setOnDismissListener(var1);
   }

   public void setOnItemClickListener(OnItemClickListener var1) {
      this.mItemClickListener = var1;
   }

   public void setOnItemSelectedListener(OnItemSelectedListener var1) {
      this.mItemSelectedListener = var1;
   }

   public void setOverlapAnchor(boolean var1) {
      this.mOverlapAnchorSet = true;
      this.mOverlapAnchor = var1;
   }

   public void setPromptPosition(int var1) {
      this.mPromptPosition = var1;
   }

   public void setPromptView(View var1) {
      boolean var2 = this.isShowing();
      if (var2) {
         this.removePromptView();
      }

      this.mPromptView = var1;
      if (var2) {
         this.show();
      }

   }

   public void setSelection(int var1) {
      DropDownListView var2 = this.mDropDownList;
      if (this.isShowing() && var2 != null) {
         var2.setListSelectionHidden(false);
         var2.setSelection(var1);
         if (var2.getChoiceMode() != 0) {
            var2.setItemChecked(var1, true);
         }
      }

   }

   public void setSoftInputMode(int var1) {
      this.mPopup.setSoftInputMode(var1);
   }

   public void setVerticalOffset(int var1) {
      this.mDropDownVerticalOffset = var1;
      this.mDropDownVerticalOffsetSet = true;
   }

   public void setWidth(int var1) {
      this.mDropDownWidth = var1;
   }

   public void setWindowLayoutType(int var1) {
      this.mDropDownWindowLayoutType = var1;
   }

   public void show() {
      int var1 = this.buildDropDown();
      boolean var2 = this.isInputMethodNotNeeded();
      PopupWindowCompat.setWindowLayoutType(this.mPopup, this.mDropDownWindowLayoutType);
      boolean var3 = this.mPopup.isShowing();
      boolean var4 = true;
      int var5;
      int var6;
      PopupWindow var7;
      if (var3) {
         if (!ViewCompat.isAttachedToWindow(this.getAnchorView())) {
            return;
         }

         var5 = this.mDropDownWidth;
         if (var5 == -1) {
            var6 = -1;
         } else {
            var6 = var5;
            if (var5 == -2) {
               var6 = this.getAnchorView().getWidth();
            }
         }

         var5 = this.mDropDownHeight;
         if (var5 == -1) {
            if (!var2) {
               var1 = -1;
            }

            byte var11;
            if (var2) {
               var7 = this.mPopup;
               if (this.mDropDownWidth == -1) {
                  var11 = -1;
               } else {
                  var11 = 0;
               }

               var7.setWidth(var11);
               this.mPopup.setHeight(0);
            } else {
               var7 = this.mPopup;
               if (this.mDropDownWidth == -1) {
                  var11 = -1;
               } else {
                  var11 = 0;
               }

               var7.setWidth(var11);
               this.mPopup.setHeight(-1);
            }
         } else if (var5 != -2) {
            var1 = var5;
         }

         var7 = this.mPopup;
         if (this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) {
            var4 = false;
         }

         var7.setOutsideTouchable(var4);
         var7 = this.mPopup;
         View var8 = this.getAnchorView();
         var5 = this.mDropDownHorizontalOffset;
         int var9 = this.mDropDownVerticalOffset;
         if (var6 < 0) {
            var6 = -1;
         }

         if (var1 < 0) {
            var1 = -1;
         }

         var7.update(var8, var5, var9, var6, var1);
      } else {
         var5 = this.mDropDownWidth;
         if (var5 == -1) {
            var6 = -1;
         } else {
            var6 = var5;
            if (var5 == -2) {
               var6 = this.getAnchorView().getWidth();
            }
         }

         var5 = this.mDropDownHeight;
         if (var5 == -1) {
            var1 = -1;
         } else if (var5 != -2) {
            var1 = var5;
         }

         this.mPopup.setWidth(var6);
         this.mPopup.setHeight(var1);
         this.setPopupClipToScreenEnabled(true);
         var7 = this.mPopup;
         if (!this.mForceIgnoreOutsideTouch && !this.mDropDownAlwaysVisible) {
            var4 = true;
         } else {
            var4 = false;
         }

         var7.setOutsideTouchable(var4);
         this.mPopup.setTouchInterceptor(this.mTouchInterceptor);
         if (this.mOverlapAnchorSet) {
            PopupWindowCompat.setOverlapAnchor(this.mPopup, this.mOverlapAnchor);
         }

         if (VERSION.SDK_INT <= 28) {
            Method var12 = sSetEpicenterBoundsMethod;
            if (var12 != null) {
               try {
                  var12.invoke(this.mPopup, this.mEpicenterBounds);
               } catch (Exception var10) {
                  Log.e("ListPopupWindow", "Could not invoke setEpicenterBounds on PopupWindow", var10);
               }
            }
         } else {
            this.mPopup.setEpicenterBounds(this.mEpicenterBounds);
         }

         PopupWindowCompat.showAsDropDown(this.mPopup, this.getAnchorView(), this.mDropDownHorizontalOffset, this.mDropDownVerticalOffset, this.mDropDownGravity);
         this.mDropDownList.setSelection(-1);
         if (!this.mModal || this.mDropDownList.isInTouchMode()) {
            this.clearListSelection();
         }

         if (!this.mModal) {
            this.mHandler.post(this.mHideSelector);
         }
      }

   }

   private class ListSelectorHider implements Runnable {
      ListSelectorHider() {
      }

      public void run() {
         ListPopupWindow.this.clearListSelection();
      }
   }

   private class PopupDataSetObserver extends DataSetObserver {
      PopupDataSetObserver() {
      }

      public void onChanged() {
         if (ListPopupWindow.this.isShowing()) {
            ListPopupWindow.this.show();
         }

      }

      public void onInvalidated() {
         ListPopupWindow.this.dismiss();
      }
   }

   private class PopupScrollListener implements OnScrollListener {
      PopupScrollListener() {
      }

      public void onScroll(AbsListView var1, int var2, int var3, int var4) {
      }

      public void onScrollStateChanged(AbsListView var1, int var2) {
         if (var2 == 1 && !ListPopupWindow.this.isInputMethodNotNeeded() && ListPopupWindow.this.mPopup.getContentView() != null) {
            ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
            ListPopupWindow.this.mResizePopupRunnable.run();
         }

      }
   }

   private class PopupTouchInterceptor implements OnTouchListener {
      PopupTouchInterceptor() {
      }

      public boolean onTouch(View var1, MotionEvent var2) {
         int var3 = var2.getAction();
         int var4 = (int)var2.getX();
         int var5 = (int)var2.getY();
         if (var3 == 0 && ListPopupWindow.this.mPopup != null && ListPopupWindow.this.mPopup.isShowing() && var4 >= 0 && var4 < ListPopupWindow.this.mPopup.getWidth() && var5 >= 0 && var5 < ListPopupWindow.this.mPopup.getHeight()) {
            ListPopupWindow.this.mHandler.postDelayed(ListPopupWindow.this.mResizePopupRunnable, 250L);
         } else if (var3 == 1) {
            ListPopupWindow.this.mHandler.removeCallbacks(ListPopupWindow.this.mResizePopupRunnable);
         }

         return false;
      }
   }

   private class ResizePopupRunnable implements Runnable {
      ResizePopupRunnable() {
      }

      public void run() {
         if (ListPopupWindow.this.mDropDownList != null && ViewCompat.isAttachedToWindow(ListPopupWindow.this.mDropDownList) && ListPopupWindow.this.mDropDownList.getCount() > ListPopupWindow.this.mDropDownList.getChildCount() && ListPopupWindow.this.mDropDownList.getChildCount() <= ListPopupWindow.this.mListItemExpandMaximum) {
            ListPopupWindow.this.mPopup.setInputMethodMode(2);
            ListPopupWindow.this.show();
         }

      }
   }
}

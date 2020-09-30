package androidx.appcompat.widget;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.View.AccessibilityDelegate;
import android.view.View.MeasureSpec;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.PopupWindow.OnDismissListener;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.core.view.ActionProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class ActivityChooserView extends ViewGroup implements ActivityChooserModel.ActivityChooserModelClient {
   private final View mActivityChooserContent;
   private final Drawable mActivityChooserContentBackground;
   final ActivityChooserView.ActivityChooserViewAdapter mAdapter;
   private final ActivityChooserView.Callbacks mCallbacks;
   private int mDefaultActionButtonContentDescription;
   final FrameLayout mDefaultActivityButton;
   private final ImageView mDefaultActivityButtonImage;
   final FrameLayout mExpandActivityOverflowButton;
   private final ImageView mExpandActivityOverflowButtonImage;
   int mInitialActivityCount;
   private boolean mIsAttachedToWindow;
   boolean mIsSelectingDefaultActivity;
   private final int mListPopupMaxWidth;
   private ListPopupWindow mListPopupWindow;
   final DataSetObserver mModelDataSetObserver;
   OnDismissListener mOnDismissListener;
   private final OnGlobalLayoutListener mOnGlobalLayoutListener;
   ActionProvider mProvider;

   public ActivityChooserView(Context var1) {
      this(var1, (AttributeSet)null);
   }

   public ActivityChooserView(Context var1, AttributeSet var2) {
      this(var1, var2, 0);
   }

   public ActivityChooserView(Context var1, AttributeSet var2, int var3) {
      super(var1, var2, var3);
      this.mModelDataSetObserver = new DataSetObserver() {
         public void onChanged() {
            super.onChanged();
            ActivityChooserView.this.mAdapter.notifyDataSetChanged();
         }

         public void onInvalidated() {
            super.onInvalidated();
            ActivityChooserView.this.mAdapter.notifyDataSetInvalidated();
         }
      };
      this.mOnGlobalLayoutListener = new OnGlobalLayoutListener() {
         public void onGlobalLayout() {
            if (ActivityChooserView.this.isShowingPopup()) {
               if (!ActivityChooserView.this.isShown()) {
                  ActivityChooserView.this.getListPopupWindow().dismiss();
               } else {
                  ActivityChooserView.this.getListPopupWindow().show();
                  if (ActivityChooserView.this.mProvider != null) {
                     ActivityChooserView.this.mProvider.subUiVisibilityChanged(true);
                  }
               }
            }

         }
      };
      this.mInitialActivityCount = 4;
      TypedArray var4 = var1.obtainStyledAttributes(var2, R.styleable.ActivityChooserView, var3, 0);
      ViewCompat.saveAttributeDataForStyleable(this, var1, R.styleable.ActivityChooserView, var2, var4, var3, 0);
      this.mInitialActivityCount = var4.getInt(R.styleable.ActivityChooserView_initialActivityCount, 4);
      Drawable var6 = var4.getDrawable(R.styleable.ActivityChooserView_expandActivityOverflowButtonDrawable);
      var4.recycle();
      LayoutInflater.from(this.getContext()).inflate(R.layout.abc_activity_chooser_view, this, true);
      this.mCallbacks = new ActivityChooserView.Callbacks();
      View var8 = this.findViewById(R.id.activity_chooser_view_content);
      this.mActivityChooserContent = var8;
      this.mActivityChooserContentBackground = var8.getBackground();
      FrameLayout var9 = (FrameLayout)this.findViewById(R.id.default_activity_button);
      this.mDefaultActivityButton = var9;
      var9.setOnClickListener(this.mCallbacks);
      this.mDefaultActivityButton.setOnLongClickListener(this.mCallbacks);
      this.mDefaultActivityButtonImage = (ImageView)this.mDefaultActivityButton.findViewById(R.id.image);
      var9 = (FrameLayout)this.findViewById(R.id.expand_activities_button);
      var9.setOnClickListener(this.mCallbacks);
      var9.setAccessibilityDelegate(new AccessibilityDelegate() {
         public void onInitializeAccessibilityNodeInfo(View var1, AccessibilityNodeInfo var2) {
            super.onInitializeAccessibilityNodeInfo(var1, var2);
            AccessibilityNodeInfoCompat.wrap(var2).setCanOpenPopup(true);
         }
      });
      var9.setOnTouchListener(new ForwardingListener(var9) {
         public ShowableListMenu getPopup() {
            return ActivityChooserView.this.getListPopupWindow();
         }

         protected boolean onForwardingStarted() {
            ActivityChooserView.this.showPopup();
            return true;
         }

         protected boolean onForwardingStopped() {
            ActivityChooserView.this.dismissPopup();
            return true;
         }
      });
      this.mExpandActivityOverflowButton = var9;
      ImageView var10 = (ImageView)var9.findViewById(R.id.image);
      this.mExpandActivityOverflowButtonImage = var10;
      var10.setImageDrawable(var6);
      ActivityChooserView.ActivityChooserViewAdapter var7 = new ActivityChooserView.ActivityChooserViewAdapter();
      this.mAdapter = var7;
      var7.registerDataSetObserver(new DataSetObserver() {
         public void onChanged() {
            super.onChanged();
            ActivityChooserView.this.updateAppearance();
         }
      });
      Resources var5 = var1.getResources();
      this.mListPopupMaxWidth = Math.max(var5.getDisplayMetrics().widthPixels / 2, var5.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
   }

   public boolean dismissPopup() {
      if (this.isShowingPopup()) {
         this.getListPopupWindow().dismiss();
         ViewTreeObserver var1 = this.getViewTreeObserver();
         if (var1.isAlive()) {
            var1.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
         }
      }

      return true;
   }

   public ActivityChooserModel getDataModel() {
      return this.mAdapter.getDataModel();
   }

   ListPopupWindow getListPopupWindow() {
      if (this.mListPopupWindow == null) {
         ListPopupWindow var1 = new ListPopupWindow(this.getContext());
         this.mListPopupWindow = var1;
         var1.setAdapter(this.mAdapter);
         this.mListPopupWindow.setAnchorView(this);
         this.mListPopupWindow.setModal(true);
         this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
         this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
      }

      return this.mListPopupWindow;
   }

   public boolean isShowingPopup() {
      return this.getListPopupWindow().isShowing();
   }

   protected void onAttachedToWindow() {
      super.onAttachedToWindow();
      ActivityChooserModel var1 = this.mAdapter.getDataModel();
      if (var1 != null) {
         var1.registerObserver(this.mModelDataSetObserver);
      }

      this.mIsAttachedToWindow = true;
   }

   protected void onDetachedFromWindow() {
      super.onDetachedFromWindow();
      ActivityChooserModel var1 = this.mAdapter.getDataModel();
      if (var1 != null) {
         var1.unregisterObserver(this.mModelDataSetObserver);
      }

      ViewTreeObserver var2 = this.getViewTreeObserver();
      if (var2.isAlive()) {
         var2.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
      }

      if (this.isShowingPopup()) {
         this.dismissPopup();
      }

      this.mIsAttachedToWindow = false;
   }

   protected void onLayout(boolean var1, int var2, int var3, int var4, int var5) {
      this.mActivityChooserContent.layout(0, 0, var4 - var2, var5 - var3);
      if (!this.isShowingPopup()) {
         this.dismissPopup();
      }

   }

   protected void onMeasure(int var1, int var2) {
      View var3 = this.mActivityChooserContent;
      int var4 = var2;
      if (this.mDefaultActivityButton.getVisibility() != 0) {
         var4 = MeasureSpec.makeMeasureSpec(MeasureSpec.getSize(var2), 1073741824);
      }

      this.measureChild(var3, var1, var4);
      this.setMeasuredDimension(var3.getMeasuredWidth(), var3.getMeasuredHeight());
   }

   public void setActivityChooserModel(ActivityChooserModel var1) {
      this.mAdapter.setDataModel(var1);
      if (this.isShowingPopup()) {
         this.dismissPopup();
         this.showPopup();
      }

   }

   public void setDefaultActionButtonContentDescription(int var1) {
      this.mDefaultActionButtonContentDescription = var1;
   }

   public void setExpandActivityOverflowButtonContentDescription(int var1) {
      String var2 = this.getContext().getString(var1);
      this.mExpandActivityOverflowButtonImage.setContentDescription(var2);
   }

   public void setExpandActivityOverflowButtonDrawable(Drawable var1) {
      this.mExpandActivityOverflowButtonImage.setImageDrawable(var1);
   }

   public void setInitialActivityCount(int var1) {
      this.mInitialActivityCount = var1;
   }

   public void setOnDismissListener(OnDismissListener var1) {
      this.mOnDismissListener = var1;
   }

   public void setProvider(ActionProvider var1) {
      this.mProvider = var1;
   }

   public boolean showPopup() {
      if (!this.isShowingPopup() && this.mIsAttachedToWindow) {
         this.mIsSelectingDefaultActivity = false;
         this.showPopupUnchecked(this.mInitialActivityCount);
         return true;
      } else {
         return false;
      }
   }

   void showPopupUnchecked(int var1) {
      if (this.mAdapter.getDataModel() != null) {
         this.getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
         byte var2;
         if (this.mDefaultActivityButton.getVisibility() == 0) {
            var2 = 1;
         } else {
            var2 = 0;
         }

         int var3 = this.mAdapter.getActivityCount();
         if (var1 != Integer.MAX_VALUE && var3 > var1 + var2) {
            this.mAdapter.setShowFooterView(true);
            this.mAdapter.setMaxActivityCount(var1 - 1);
         } else {
            this.mAdapter.setShowFooterView(false);
            this.mAdapter.setMaxActivityCount(var1);
         }

         ListPopupWindow var4 = this.getListPopupWindow();
         if (!var4.isShowing()) {
            if (!this.mIsSelectingDefaultActivity && var2 != 0) {
               this.mAdapter.setShowDefaultActivity(false, false);
            } else {
               this.mAdapter.setShowDefaultActivity(true, (boolean)var2);
            }

            var4.setContentWidth(Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth));
            var4.show();
            ActionProvider var5 = this.mProvider;
            if (var5 != null) {
               var5.subUiVisibilityChanged(true);
            }

            var4.getListView().setContentDescription(this.getContext().getString(R.string.abc_activitychooserview_choose_application));
            var4.getListView().setSelector(new ColorDrawable(0));
         }

      } else {
         throw new IllegalStateException("No data model. Did you call #setDataModel?");
      }
   }

   void updateAppearance() {
      if (this.mAdapter.getCount() > 0) {
         this.mExpandActivityOverflowButton.setEnabled(true);
      } else {
         this.mExpandActivityOverflowButton.setEnabled(false);
      }

      int var1 = this.mAdapter.getActivityCount();
      int var2 = this.mAdapter.getHistorySize();
      if (var1 != 1 && (var1 <= 1 || var2 <= 0)) {
         this.mDefaultActivityButton.setVisibility(8);
      } else {
         this.mDefaultActivityButton.setVisibility(0);
         ResolveInfo var3 = this.mAdapter.getDefaultActivity();
         PackageManager var4 = this.getContext().getPackageManager();
         this.mDefaultActivityButtonImage.setImageDrawable(var3.loadIcon(var4));
         if (this.mDefaultActionButtonContentDescription != 0) {
            CharSequence var5 = var3.loadLabel(var4);
            String var6 = this.getContext().getString(this.mDefaultActionButtonContentDescription, new Object[]{var5});
            this.mDefaultActivityButton.setContentDescription(var6);
         }
      }

      if (this.mDefaultActivityButton.getVisibility() == 0) {
         this.mActivityChooserContent.setBackgroundDrawable(this.mActivityChooserContentBackground);
      } else {
         this.mActivityChooserContent.setBackgroundDrawable((Drawable)null);
      }

   }

   private class ActivityChooserViewAdapter extends BaseAdapter {
      private static final int ITEM_VIEW_TYPE_ACTIVITY = 0;
      private static final int ITEM_VIEW_TYPE_COUNT = 3;
      private static final int ITEM_VIEW_TYPE_FOOTER = 1;
      public static final int MAX_ACTIVITY_COUNT_DEFAULT = 4;
      public static final int MAX_ACTIVITY_COUNT_UNLIMITED = Integer.MAX_VALUE;
      private ActivityChooserModel mDataModel;
      private boolean mHighlightDefaultActivity;
      private int mMaxActivityCount = 4;
      private boolean mShowDefaultActivity;
      private boolean mShowFooterView;

      ActivityChooserViewAdapter() {
      }

      public int getActivityCount() {
         return this.mDataModel.getActivityCount();
      }

      public int getCount() {
         int var1 = this.mDataModel.getActivityCount();
         int var2 = var1;
         if (!this.mShowDefaultActivity) {
            var2 = var1;
            if (this.mDataModel.getDefaultActivity() != null) {
               var2 = var1 - 1;
            }
         }

         var1 = Math.min(var2, this.mMaxActivityCount);
         var2 = var1;
         if (this.mShowFooterView) {
            var2 = var1 + 1;
         }

         return var2;
      }

      public ActivityChooserModel getDataModel() {
         return this.mDataModel;
      }

      public ResolveInfo getDefaultActivity() {
         return this.mDataModel.getDefaultActivity();
      }

      public int getHistorySize() {
         return this.mDataModel.getHistorySize();
      }

      public Object getItem(int var1) {
         int var2 = this.getItemViewType(var1);
         if (var2 != 0) {
            if (var2 == 1) {
               return null;
            } else {
               throw new IllegalArgumentException();
            }
         } else {
            var2 = var1;
            if (!this.mShowDefaultActivity) {
               var2 = var1;
               if (this.mDataModel.getDefaultActivity() != null) {
                  var2 = var1 + 1;
               }
            }

            return this.mDataModel.getActivity(var2);
         }
      }

      public long getItemId(int var1) {
         return (long)var1;
      }

      public int getItemViewType(int var1) {
         return this.mShowFooterView && var1 == this.getCount() - 1 ? 1 : 0;
      }

      public boolean getShowDefaultActivity() {
         return this.mShowDefaultActivity;
      }

      public View getView(int var1, View var2, ViewGroup var3) {
         int var4 = this.getItemViewType(var1);
         View var5;
         if (var4 != 0) {
            if (var4 != 1) {
               throw new IllegalArgumentException();
            } else {
               if (var2 != null) {
                  var5 = var2;
                  if (var2.getId() == 1) {
                     return var5;
                  }
               }

               var5 = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, var3, false);
               var5.setId(1);
               ((TextView)var5.findViewById(R.id.title)).setText(ActivityChooserView.this.getContext().getString(R.string.abc_activity_chooser_view_see_all));
               return var5;
            }
         } else {
            label37: {
               if (var2 != null) {
                  var5 = var2;
                  if (var2.getId() == R.id.list_item) {
                     break label37;
                  }
               }

               var5 = LayoutInflater.from(ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, var3, false);
            }

            PackageManager var8 = ActivityChooserView.this.getContext().getPackageManager();
            ImageView var7 = (ImageView)var5.findViewById(R.id.icon);
            ResolveInfo var6 = (ResolveInfo)this.getItem(var1);
            var7.setImageDrawable(var6.loadIcon(var8));
            ((TextView)var5.findViewById(R.id.title)).setText(var6.loadLabel(var8));
            if (this.mShowDefaultActivity && var1 == 0 && this.mHighlightDefaultActivity) {
               var5.setActivated(true);
            } else {
               var5.setActivated(false);
            }

            return var5;
         }
      }

      public int getViewTypeCount() {
         return 3;
      }

      public int measureContentWidth() {
         int var1 = this.mMaxActivityCount;
         this.mMaxActivityCount = Integer.MAX_VALUE;
         int var2 = 0;
         int var3 = MeasureSpec.makeMeasureSpec(0, 0);
         int var4 = MeasureSpec.makeMeasureSpec(0, 0);
         int var5 = this.getCount();
         View var6 = null;

         int var7;
         for(var7 = 0; var2 < var5; ++var2) {
            var6 = this.getView(var2, var6, (ViewGroup)null);
            var6.measure(var3, var4);
            var7 = Math.max(var7, var6.getMeasuredWidth());
         }

         this.mMaxActivityCount = var1;
         return var7;
      }

      public void setDataModel(ActivityChooserModel var1) {
         ActivityChooserModel var2 = ActivityChooserView.this.mAdapter.getDataModel();
         if (var2 != null && ActivityChooserView.this.isShown()) {
            var2.unregisterObserver(ActivityChooserView.this.mModelDataSetObserver);
         }

         this.mDataModel = var1;
         if (var1 != null && ActivityChooserView.this.isShown()) {
            var1.registerObserver(ActivityChooserView.this.mModelDataSetObserver);
         }

         this.notifyDataSetChanged();
      }

      public void setMaxActivityCount(int var1) {
         if (this.mMaxActivityCount != var1) {
            this.mMaxActivityCount = var1;
            this.notifyDataSetChanged();
         }

      }

      public void setShowDefaultActivity(boolean var1, boolean var2) {
         if (this.mShowDefaultActivity != var1 || this.mHighlightDefaultActivity != var2) {
            this.mShowDefaultActivity = var1;
            this.mHighlightDefaultActivity = var2;
            this.notifyDataSetChanged();
         }

      }

      public void setShowFooterView(boolean var1) {
         if (this.mShowFooterView != var1) {
            this.mShowFooterView = var1;
            this.notifyDataSetChanged();
         }

      }
   }

   private class Callbacks implements OnItemClickListener, OnClickListener, OnLongClickListener, OnDismissListener {
      Callbacks() {
      }

      private void notifyOnDismissListener() {
         if (ActivityChooserView.this.mOnDismissListener != null) {
            ActivityChooserView.this.mOnDismissListener.onDismiss();
         }

      }

      public void onClick(View var1) {
         if (var1 == ActivityChooserView.this.mDefaultActivityButton) {
            ActivityChooserView.this.dismissPopup();
            ResolveInfo var3 = ActivityChooserView.this.mAdapter.getDefaultActivity();
            int var2 = ActivityChooserView.this.mAdapter.getDataModel().getActivityIndex(var3);
            Intent var4 = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(var2);
            if (var4 != null) {
               var4.addFlags(524288);
               ActivityChooserView.this.getContext().startActivity(var4);
            }
         } else {
            if (var1 != ActivityChooserView.this.mExpandActivityOverflowButton) {
               throw new IllegalArgumentException();
            }

            ActivityChooserView.this.mIsSelectingDefaultActivity = false;
            ActivityChooserView var5 = ActivityChooserView.this;
            var5.showPopupUnchecked(var5.mInitialActivityCount);
         }

      }

      public void onDismiss() {
         this.notifyOnDismissListener();
         if (ActivityChooserView.this.mProvider != null) {
            ActivityChooserView.this.mProvider.subUiVisibilityChanged(false);
         }

      }

      public void onItemClick(AdapterView<?> var1, View var2, int var3, long var4) {
         int var6 = ((ActivityChooserView.ActivityChooserViewAdapter)var1.getAdapter()).getItemViewType(var3);
         if (var6 != 0) {
            if (var6 != 1) {
               throw new IllegalArgumentException();
            }

            ActivityChooserView.this.showPopupUnchecked(Integer.MAX_VALUE);
         } else {
            ActivityChooserView.this.dismissPopup();
            if (ActivityChooserView.this.mIsSelectingDefaultActivity) {
               if (var3 > 0) {
                  ActivityChooserView.this.mAdapter.getDataModel().setDefaultActivity(var3);
               }
            } else {
               if (!ActivityChooserView.this.mAdapter.getShowDefaultActivity()) {
                  ++var3;
               }

               Intent var7 = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(var3);
               if (var7 != null) {
                  var7.addFlags(524288);
                  ActivityChooserView.this.getContext().startActivity(var7);
               }
            }
         }

      }

      public boolean onLongClick(View var1) {
         if (var1 == ActivityChooserView.this.mDefaultActivityButton) {
            if (ActivityChooserView.this.mAdapter.getCount() > 0) {
               ActivityChooserView.this.mIsSelectingDefaultActivity = true;
               ActivityChooserView var2 = ActivityChooserView.this;
               var2.showPopupUnchecked(var2.mInitialActivityCount);
            }

            return true;
         } else {
            throw new IllegalArgumentException();
         }
      }
   }

   public static class InnerLayout extends LinearLayout {
      private static final int[] TINT_ATTRS = new int[]{16842964};

      public InnerLayout(Context var1, AttributeSet var2) {
         super(var1, var2);
         TintTypedArray var3 = TintTypedArray.obtainStyledAttributes(var1, var2, TINT_ATTRS);
         this.setBackgroundDrawable(var3.getDrawable(0));
         var3.recycle();
      }
   }
}

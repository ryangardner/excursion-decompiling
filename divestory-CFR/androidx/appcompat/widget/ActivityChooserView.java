/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$AccessibilityDelegate
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.widget.Adapter
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.BaseAdapter
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.TextView
 */
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
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.appcompat.widget.ActivityChooserModel;
import androidx.appcompat.widget.ForwardingListener;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.view.ActionProvider;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class ActivityChooserView
extends ViewGroup
implements ActivityChooserModel.ActivityChooserModelClient {
    private final View mActivityChooserContent;
    private final Drawable mActivityChooserContentBackground;
    final ActivityChooserViewAdapter mAdapter;
    private final Callbacks mCallbacks;
    private int mDefaultActionButtonContentDescription;
    final FrameLayout mDefaultActivityButton;
    private final ImageView mDefaultActivityButtonImage;
    final FrameLayout mExpandActivityOverflowButton;
    private final ImageView mExpandActivityOverflowButtonImage;
    int mInitialActivityCount = 4;
    private boolean mIsAttachedToWindow;
    boolean mIsSelectingDefaultActivity;
    private final int mListPopupMaxWidth;
    private ListPopupWindow mListPopupWindow;
    final DataSetObserver mModelDataSetObserver = new DataSetObserver(){

        public void onChanged() {
            super.onChanged();
            ActivityChooserView.this.mAdapter.notifyDataSetChanged();
        }

        public void onInvalidated() {
            super.onInvalidated();
            ActivityChooserView.this.mAdapter.notifyDataSetInvalidated();
        }
    };
    PopupWindow.OnDismissListener mOnDismissListener;
    private final ViewTreeObserver.OnGlobalLayoutListener mOnGlobalLayoutListener = new ViewTreeObserver.OnGlobalLayoutListener(){

        public void onGlobalLayout() {
            if (!ActivityChooserView.this.isShowingPopup()) return;
            if (!ActivityChooserView.this.isShown()) {
                ActivityChooserView.this.getListPopupWindow().dismiss();
                return;
            }
            ActivityChooserView.this.getListPopupWindow().show();
            if (ActivityChooserView.this.mProvider == null) return;
            ActivityChooserView.this.mProvider.subUiVisibilityChanged(true);
        }
    };
    ActionProvider mProvider;

    public ActivityChooserView(Context context) {
        this(context, null);
    }

    public ActivityChooserView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public ActivityChooserView(Context context, AttributeSet object, int n) {
        super(context, object, n);
        TypedArray typedArray = context.obtainStyledAttributes(object, R.styleable.ActivityChooserView, n, 0);
        ViewCompat.saveAttributeDataForStyleable((View)this, context, R.styleable.ActivityChooserView, object, typedArray, n, 0);
        this.mInitialActivityCount = typedArray.getInt(R.styleable.ActivityChooserView_initialActivityCount, 4);
        object = typedArray.getDrawable(R.styleable.ActivityChooserView_expandActivityOverflowButtonDrawable);
        typedArray.recycle();
        LayoutInflater.from((Context)this.getContext()).inflate(R.layout.abc_activity_chooser_view, (ViewGroup)this, true);
        this.mCallbacks = new Callbacks();
        typedArray = this.findViewById(R.id.activity_chooser_view_content);
        this.mActivityChooserContent = typedArray;
        this.mActivityChooserContentBackground = typedArray.getBackground();
        typedArray = (FrameLayout)this.findViewById(R.id.default_activity_button);
        this.mDefaultActivityButton = typedArray;
        typedArray.setOnClickListener((View.OnClickListener)this.mCallbacks);
        this.mDefaultActivityButton.setOnLongClickListener((View.OnLongClickListener)this.mCallbacks);
        this.mDefaultActivityButtonImage = (ImageView)this.mDefaultActivityButton.findViewById(R.id.image);
        typedArray = (FrameLayout)this.findViewById(R.id.expand_activities_button);
        typedArray.setOnClickListener((View.OnClickListener)this.mCallbacks);
        typedArray.setAccessibilityDelegate(new View.AccessibilityDelegate(){

            public void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                AccessibilityNodeInfoCompat.wrap(accessibilityNodeInfo).setCanOpenPopup(true);
            }
        });
        typedArray.setOnTouchListener((View.OnTouchListener)new ForwardingListener((View)typedArray){

            @Override
            public ShowableListMenu getPopup() {
                return ActivityChooserView.this.getListPopupWindow();
            }

            @Override
            protected boolean onForwardingStarted() {
                ActivityChooserView.this.showPopup();
                return true;
            }

            @Override
            protected boolean onForwardingStopped() {
                ActivityChooserView.this.dismissPopup();
                return true;
            }
        });
        this.mExpandActivityOverflowButton = typedArray;
        typedArray = (ImageView)typedArray.findViewById(R.id.image);
        this.mExpandActivityOverflowButtonImage = typedArray;
        typedArray.setImageDrawable((Drawable)object);
        object = new ActivityChooserViewAdapter();
        this.mAdapter = object;
        object.registerDataSetObserver(new DataSetObserver(){

            public void onChanged() {
                super.onChanged();
                ActivityChooserView.this.updateAppearance();
            }
        });
        context = context.getResources();
        this.mListPopupMaxWidth = Math.max(context.getDisplayMetrics().widthPixels / 2, context.getDimensionPixelSize(R.dimen.abc_config_prefDialogWidth));
    }

    public boolean dismissPopup() {
        if (!this.isShowingPopup()) return true;
        this.getListPopupWindow().dismiss();
        ViewTreeObserver viewTreeObserver = this.getViewTreeObserver();
        if (!viewTreeObserver.isAlive()) return true;
        viewTreeObserver.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
        return true;
    }

    public ActivityChooserModel getDataModel() {
        return this.mAdapter.getDataModel();
    }

    ListPopupWindow getListPopupWindow() {
        ListPopupWindow listPopupWindow;
        if (this.mListPopupWindow != null) return this.mListPopupWindow;
        this.mListPopupWindow = listPopupWindow = new ListPopupWindow(this.getContext());
        listPopupWindow.setAdapter((ListAdapter)this.mAdapter);
        this.mListPopupWindow.setAnchorView((View)this);
        this.mListPopupWindow.setModal(true);
        this.mListPopupWindow.setOnItemClickListener(this.mCallbacks);
        this.mListPopupWindow.setOnDismissListener(this.mCallbacks);
        return this.mListPopupWindow;
    }

    public boolean isShowingPopup() {
        return this.getListPopupWindow().isShowing();
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        ActivityChooserModel activityChooserModel = this.mAdapter.getDataModel();
        if (activityChooserModel != null) {
            activityChooserModel.registerObserver((Object)this.mModelDataSetObserver);
        }
        this.mIsAttachedToWindow = true;
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        ActivityChooserModel activityChooserModel = this.mAdapter.getDataModel();
        if (activityChooserModel != null) {
            activityChooserModel.unregisterObserver((Object)this.mModelDataSetObserver);
        }
        if ((activityChooserModel = this.getViewTreeObserver()).isAlive()) {
            activityChooserModel.removeGlobalOnLayoutListener(this.mOnGlobalLayoutListener);
        }
        if (this.isShowingPopup()) {
            this.dismissPopup();
        }
        this.mIsAttachedToWindow = false;
    }

    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        this.mActivityChooserContent.layout(0, 0, n3 - n, n4 - n2);
        if (this.isShowingPopup()) return;
        this.dismissPopup();
    }

    protected void onMeasure(int n, int n2) {
        View view = this.mActivityChooserContent;
        int n3 = n2;
        if (this.mDefaultActivityButton.getVisibility() != 0) {
            n3 = View.MeasureSpec.makeMeasureSpec((int)View.MeasureSpec.getSize((int)n2), (int)1073741824);
        }
        this.measureChild(view, n, n3);
        this.setMeasuredDimension(view.getMeasuredWidth(), view.getMeasuredHeight());
    }

    @Override
    public void setActivityChooserModel(ActivityChooserModel activityChooserModel) {
        this.mAdapter.setDataModel(activityChooserModel);
        if (!this.isShowingPopup()) return;
        this.dismissPopup();
        this.showPopup();
    }

    public void setDefaultActionButtonContentDescription(int n) {
        this.mDefaultActionButtonContentDescription = n;
    }

    public void setExpandActivityOverflowButtonContentDescription(int n) {
        String string2 = this.getContext().getString(n);
        this.mExpandActivityOverflowButtonImage.setContentDescription((CharSequence)string2);
    }

    public void setExpandActivityOverflowButtonDrawable(Drawable drawable2) {
        this.mExpandActivityOverflowButtonImage.setImageDrawable(drawable2);
    }

    public void setInitialActivityCount(int n) {
        this.mInitialActivityCount = n;
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mOnDismissListener = onDismissListener;
    }

    public void setProvider(ActionProvider actionProvider) {
        this.mProvider = actionProvider;
    }

    public boolean showPopup() {
        if (this.isShowingPopup()) return false;
        if (!this.mIsAttachedToWindow) {
            return false;
        }
        this.mIsSelectingDefaultActivity = false;
        this.showPopupUnchecked(this.mInitialActivityCount);
        return true;
    }

    void showPopupUnchecked(int n) {
        if (this.mAdapter.getDataModel() == null) throw new IllegalStateException("No data model. Did you call #setDataModel?");
        this.getViewTreeObserver().addOnGlobalLayoutListener(this.mOnGlobalLayoutListener);
        boolean bl = this.mDefaultActivityButton.getVisibility() == 0;
        int n2 = this.mAdapter.getActivityCount();
        if (n != Integer.MAX_VALUE && n2 > n + bl) {
            this.mAdapter.setShowFooterView(true);
            this.mAdapter.setMaxActivityCount(n - 1);
        } else {
            this.mAdapter.setShowFooterView(false);
            this.mAdapter.setMaxActivityCount(n);
        }
        ListPopupWindow listPopupWindow = this.getListPopupWindow();
        if (listPopupWindow.isShowing()) return;
        if (!this.mIsSelectingDefaultActivity && bl) {
            this.mAdapter.setShowDefaultActivity(false, false);
        } else {
            this.mAdapter.setShowDefaultActivity(true, bl);
        }
        listPopupWindow.setContentWidth(Math.min(this.mAdapter.measureContentWidth(), this.mListPopupMaxWidth));
        listPopupWindow.show();
        ActionProvider actionProvider = this.mProvider;
        if (actionProvider != null) {
            actionProvider.subUiVisibilityChanged(true);
        }
        listPopupWindow.getListView().setContentDescription((CharSequence)this.getContext().getString(R.string.abc_activitychooserview_choose_application));
        listPopupWindow.getListView().setSelector((Drawable)new ColorDrawable(0));
    }

    void updateAppearance() {
        if (this.mAdapter.getCount() > 0) {
            this.mExpandActivityOverflowButton.setEnabled(true);
        } else {
            this.mExpandActivityOverflowButton.setEnabled(false);
        }
        int n = this.mAdapter.getActivityCount();
        int n2 = this.mAdapter.getHistorySize();
        if (n != 1 && (n <= 1 || n2 <= 0)) {
            this.mDefaultActivityButton.setVisibility(8);
        } else {
            this.mDefaultActivityButton.setVisibility(0);
            ResolveInfo resolveInfo = this.mAdapter.getDefaultActivity();
            Object object = this.getContext().getPackageManager();
            this.mDefaultActivityButtonImage.setImageDrawable(resolveInfo.loadIcon(object));
            if (this.mDefaultActionButtonContentDescription != 0) {
                object = resolveInfo.loadLabel(object);
                object = this.getContext().getString(this.mDefaultActionButtonContentDescription, new Object[]{object});
                this.mDefaultActivityButton.setContentDescription((CharSequence)object);
            }
        }
        if (this.mDefaultActivityButton.getVisibility() == 0) {
            this.mActivityChooserContent.setBackgroundDrawable(this.mActivityChooserContentBackground);
            return;
        }
        this.mActivityChooserContent.setBackgroundDrawable(null);
    }

    private class ActivityChooserViewAdapter
    extends BaseAdapter {
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
            int n;
            int n2 = n = this.mDataModel.getActivityCount();
            if (!this.mShowDefaultActivity) {
                n2 = n;
                if (this.mDataModel.getDefaultActivity() != null) {
                    n2 = n - 1;
                }
            }
            n2 = n = Math.min(n2, this.mMaxActivityCount);
            if (!this.mShowFooterView) return n2;
            return n + 1;
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

        public Object getItem(int n) {
            int n2 = this.getItemViewType(n);
            if (n2 != 0) {
                if (n2 != 1) throw new IllegalArgumentException();
                return null;
            }
            n2 = n;
            if (this.mShowDefaultActivity) return this.mDataModel.getActivity(n2);
            n2 = n;
            if (this.mDataModel.getDefaultActivity() == null) return this.mDataModel.getActivity(n2);
            n2 = n + 1;
            return this.mDataModel.getActivity(n2);
        }

        public long getItemId(int n) {
            return n;
        }

        public int getItemViewType(int n) {
            if (!this.mShowFooterView) return 0;
            if (n != this.getCount() - 1) return 0;
            return 1;
        }

        public boolean getShowDefaultActivity() {
            return this.mShowDefaultActivity;
        }

        public View getView(int n, View view, ViewGroup viewGroup) {
            View view2;
            block7 : {
                block6 : {
                    int n2 = this.getItemViewType(n);
                    if (n2 != 0) {
                        View view3;
                        if (n2 != 1) throw new IllegalArgumentException();
                        if (view != null) {
                            view3 = view;
                            if (view.getId() == 1) return view3;
                        }
                        view3 = LayoutInflater.from((Context)ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
                        view3.setId(1);
                        ((TextView)view3.findViewById(R.id.title)).setText((CharSequence)ActivityChooserView.this.getContext().getString(R.string.abc_activity_chooser_view_see_all));
                        return view3;
                    }
                    if (view == null) break block6;
                    view2 = view;
                    if (view.getId() == R.id.list_item) break block7;
                }
                view2 = LayoutInflater.from((Context)ActivityChooserView.this.getContext()).inflate(R.layout.abc_activity_chooser_view_list_item, viewGroup, false);
            }
            viewGroup = ActivityChooserView.this.getContext().getPackageManager();
            view = (ImageView)view2.findViewById(R.id.icon);
            ResolveInfo resolveInfo = (ResolveInfo)this.getItem(n);
            view.setImageDrawable(resolveInfo.loadIcon((PackageManager)viewGroup));
            ((TextView)view2.findViewById(R.id.title)).setText(resolveInfo.loadLabel((PackageManager)viewGroup));
            if (this.mShowDefaultActivity && n == 0 && this.mHighlightDefaultActivity) {
                view2.setActivated(true);
                return view2;
            }
            view2.setActivated(false);
            return view2;
        }

        public int getViewTypeCount() {
            return 3;
        }

        public int measureContentWidth() {
            int n = this.mMaxActivityCount;
            this.mMaxActivityCount = Integer.MAX_VALUE;
            int n2 = 0;
            int n3 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
            int n4 = View.MeasureSpec.makeMeasureSpec((int)0, (int)0);
            int n5 = this.getCount();
            View view = null;
            int n6 = 0;
            do {
                if (n2 >= n5) {
                    this.mMaxActivityCount = n;
                    return n6;
                }
                view = this.getView(n2, view, null);
                view.measure(n3, n4);
                n6 = Math.max(n6, view.getMeasuredWidth());
                ++n2;
            } while (true);
        }

        public void setDataModel(ActivityChooserModel activityChooserModel) {
            ActivityChooserModel activityChooserModel2 = ActivityChooserView.this.mAdapter.getDataModel();
            if (activityChooserModel2 != null && ActivityChooserView.this.isShown()) {
                activityChooserModel2.unregisterObserver((Object)ActivityChooserView.this.mModelDataSetObserver);
            }
            this.mDataModel = activityChooserModel;
            if (activityChooserModel != null && ActivityChooserView.this.isShown()) {
                activityChooserModel.registerObserver((Object)ActivityChooserView.this.mModelDataSetObserver);
            }
            this.notifyDataSetChanged();
        }

        public void setMaxActivityCount(int n) {
            if (this.mMaxActivityCount == n) return;
            this.mMaxActivityCount = n;
            this.notifyDataSetChanged();
        }

        public void setShowDefaultActivity(boolean bl, boolean bl2) {
            if (this.mShowDefaultActivity == bl) {
                if (this.mHighlightDefaultActivity == bl2) return;
            }
            this.mShowDefaultActivity = bl;
            this.mHighlightDefaultActivity = bl2;
            this.notifyDataSetChanged();
        }

        public void setShowFooterView(boolean bl) {
            if (this.mShowFooterView == bl) return;
            this.mShowFooterView = bl;
            this.notifyDataSetChanged();
        }
    }

    private class Callbacks
    implements AdapterView.OnItemClickListener,
    View.OnClickListener,
    View.OnLongClickListener,
    PopupWindow.OnDismissListener {
        Callbacks() {
        }

        private void notifyOnDismissListener() {
            if (ActivityChooserView.this.mOnDismissListener == null) return;
            ActivityChooserView.this.mOnDismissListener.onDismiss();
        }

        public void onClick(View object) {
            if (object == ActivityChooserView.this.mDefaultActivityButton) {
                ActivityChooserView.this.dismissPopup();
                object = ActivityChooserView.this.mAdapter.getDefaultActivity();
                int n = ActivityChooserView.this.mAdapter.getDataModel().getActivityIndex((ResolveInfo)object);
                object = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(n);
                if (object == null) return;
                object.addFlags(524288);
                ActivityChooserView.this.getContext().startActivity((Intent)object);
                return;
            }
            if (object != ActivityChooserView.this.mExpandActivityOverflowButton) throw new IllegalArgumentException();
            ActivityChooserView.this.mIsSelectingDefaultActivity = false;
            object = ActivityChooserView.this;
            ((ActivityChooserView)object).showPopupUnchecked(((ActivityChooserView)object).mInitialActivityCount);
        }

        public void onDismiss() {
            this.notifyOnDismissListener();
            if (ActivityChooserView.this.mProvider == null) return;
            ActivityChooserView.this.mProvider.subUiVisibilityChanged(false);
        }

        public void onItemClick(AdapterView<?> intent, View view, int n, long l) {
            int n2 = ((ActivityChooserViewAdapter)intent.getAdapter()).getItemViewType(n);
            if (n2 != 0) {
                if (n2 != 1) throw new IllegalArgumentException();
                ActivityChooserView.this.showPopupUnchecked(Integer.MAX_VALUE);
                return;
            }
            ActivityChooserView.this.dismissPopup();
            if (ActivityChooserView.this.mIsSelectingDefaultActivity) {
                if (n <= 0) return;
                ActivityChooserView.this.mAdapter.getDataModel().setDefaultActivity(n);
                return;
            }
            if (!ActivityChooserView.this.mAdapter.getShowDefaultActivity()) {
                ++n;
            }
            intent = ActivityChooserView.this.mAdapter.getDataModel().chooseActivity(n);
            if (intent == null) return;
            intent.addFlags(524288);
            ActivityChooserView.this.getContext().startActivity(intent);
        }

        public boolean onLongClick(View object) {
            if (object != ActivityChooserView.this.mDefaultActivityButton) throw new IllegalArgumentException();
            if (ActivityChooserView.this.mAdapter.getCount() <= 0) return true;
            ActivityChooserView.this.mIsSelectingDefaultActivity = true;
            object = ActivityChooserView.this;
            ((ActivityChooserView)object).showPopupUnchecked(((ActivityChooserView)object).mInitialActivityCount);
            return true;
        }
    }

    public static class InnerLayout
    extends LinearLayout {
        private static final int[] TINT_ATTRS = new int[]{16842964};

        public InnerLayout(Context object, AttributeSet attributeSet) {
            super((Context)object, attributeSet);
            object = TintTypedArray.obtainStyledAttributes((Context)object, attributeSet, TINT_ATTRS);
            this.setBackgroundDrawable(((TintTypedArray)object).getDrawable(0));
            ((TintTypedArray)object).recycle();
        }
    }

}


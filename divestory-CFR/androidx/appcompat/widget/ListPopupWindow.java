/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Looper
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.KeyEvent$DispatcherState
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import androidx.appcompat.R;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.appcompat.widget.AppCompatPopupWindow;
import androidx.appcompat.widget.DropDownListView;
import androidx.appcompat.widget.ForwardingListener;
import androidx.core.view.ViewCompat;
import androidx.core.widget.PopupWindowCompat;
import java.lang.reflect.Method;

public class ListPopupWindow
implements ShowableListMenu {
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
    private boolean mDropDownAlwaysVisible = false;
    private View mDropDownAnchorView;
    private int mDropDownGravity = 0;
    private int mDropDownHeight = -2;
    private int mDropDownHorizontalOffset;
    DropDownListView mDropDownList;
    private Drawable mDropDownListHighlight;
    private int mDropDownVerticalOffset;
    private boolean mDropDownVerticalOffsetSet;
    private int mDropDownWidth = -2;
    private int mDropDownWindowLayoutType = 1002;
    private Rect mEpicenterBounds;
    private boolean mForceIgnoreOutsideTouch = false;
    final Handler mHandler;
    private final ListSelectorHider mHideSelector = new ListSelectorHider();
    private AdapterView.OnItemClickListener mItemClickListener;
    private AdapterView.OnItemSelectedListener mItemSelectedListener;
    int mListItemExpandMaximum = Integer.MAX_VALUE;
    private boolean mModal;
    private DataSetObserver mObserver;
    private boolean mOverlapAnchor;
    private boolean mOverlapAnchorSet;
    PopupWindow mPopup;
    private int mPromptPosition = 0;
    private View mPromptView;
    final ResizePopupRunnable mResizePopupRunnable = new ResizePopupRunnable();
    private final PopupScrollListener mScrollListener = new PopupScrollListener();
    private Runnable mShowDropDownRunnable;
    private final Rect mTempRect = new Rect();
    private final PopupTouchInterceptor mTouchInterceptor = new PopupTouchInterceptor();

    static {
        if (Build.VERSION.SDK_INT <= 28) {
            try {
                sSetClipToWindowEnabledMethod = PopupWindow.class.getDeclaredMethod("setClipToScreenEnabled", Boolean.TYPE);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)TAG, (String)"Could not find method setClipToScreenEnabled() on PopupWindow. Oh well.");
            }
            try {
                sSetEpicenterBoundsMethod = PopupWindow.class.getDeclaredMethod("setEpicenterBounds", Rect.class);
            }
            catch (NoSuchMethodException noSuchMethodException) {
                Log.i((String)TAG, (String)"Could not find method setEpicenterBounds(Rect) on PopupWindow. Oh well.");
            }
        }
        if (Build.VERSION.SDK_INT > 23) return;
        try {
            sGetMaxAvailableHeightMethod = PopupWindow.class.getDeclaredMethod("getMaxAvailableHeight", View.class, Integer.TYPE, Boolean.TYPE);
            return;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            Log.i((String)TAG, (String)"Could not find method getMaxAvailableHeight(View, int, boolean) on PopupWindow. Oh well.");
        }
    }

    public ListPopupWindow(Context context) {
        this(context, null, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.listPopupWindowStyle);
    }

    public ListPopupWindow(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, 0);
    }

    public ListPopupWindow(Context object, AttributeSet attributeSet, int n, int n2) {
        int n3;
        this.mContext = object;
        this.mHandler = new Handler(object.getMainLooper());
        TypedArray typedArray = object.obtainStyledAttributes(attributeSet, R.styleable.ListPopupWindow, n, n2);
        this.mDropDownHorizontalOffset = typedArray.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownHorizontalOffset, 0);
        this.mDropDownVerticalOffset = n3 = typedArray.getDimensionPixelOffset(R.styleable.ListPopupWindow_android_dropDownVerticalOffset, 0);
        if (n3 != 0) {
            this.mDropDownVerticalOffsetSet = true;
        }
        typedArray.recycle();
        object = new AppCompatPopupWindow((Context)object, attributeSet, n, n2);
        this.mPopup = object;
        object.setInputMethodMode(1);
    }

    private int buildDropDown() {
        int n;
        int n2;
        int n3;
        DropDownListView dropDownListView = this.mDropDownList;
        boolean bl = true;
        if (dropDownListView == null) {
            dropDownListView = this.mContext;
            this.mShowDropDownRunnable = new Runnable(){

                @Override
                public void run() {
                    View view = ListPopupWindow.this.getAnchorView();
                    if (view == null) return;
                    if (view.getWindowToken() == null) return;
                    ListPopupWindow.this.show();
                }
            };
            Object object = this.createDropDownListView((Context)dropDownListView, this.mModal ^ true);
            this.mDropDownList = object;
            Drawable drawable2 = this.mDropDownListHighlight;
            if (drawable2 != null) {
                ((DropDownListView)((Object)object)).setSelector(drawable2);
            }
            this.mDropDownList.setAdapter(this.mAdapter);
            this.mDropDownList.setOnItemClickListener(this.mItemClickListener);
            this.mDropDownList.setFocusable(true);
            this.mDropDownList.setFocusableInTouchMode(true);
            this.mDropDownList.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

                public void onItemSelected(AdapterView<?> object, View view, int n, long l) {
                    if (n == -1) return;
                    object = ListPopupWindow.this.mDropDownList;
                    if (object == null) return;
                    object.setListSelectionHidden(false);
                }

                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
            this.mDropDownList.setOnScrollListener((AbsListView.OnScrollListener)this.mScrollListener);
            object = this.mItemSelectedListener;
            if (object != null) {
                this.mDropDownList.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)object);
            }
            object = this.mDropDownList;
            drawable2 = this.mPromptView;
            if (drawable2 != null) {
                dropDownListView = new LinearLayout((Context)dropDownListView);
                dropDownListView.setOrientation(1);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, 0, 1.0f);
                n2 = this.mPromptPosition;
                if (n2 != 0) {
                    if (n2 != 1) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append("Invalid hint position ");
                        ((StringBuilder)object).append(this.mPromptPosition);
                        Log.e((String)TAG, (String)((StringBuilder)object).toString());
                    } else {
                        dropDownListView.addView((View)object, (ViewGroup.LayoutParams)layoutParams);
                        dropDownListView.addView((View)drawable2);
                    }
                } else {
                    dropDownListView.addView((View)drawable2);
                    dropDownListView.addView((View)object, (ViewGroup.LayoutParams)layoutParams);
                }
                n2 = this.mDropDownWidth;
                if (n2 >= 0) {
                    n3 = Integer.MIN_VALUE;
                } else {
                    n2 = 0;
                    n3 = 0;
                }
                drawable2.measure(View.MeasureSpec.makeMeasureSpec((int)n2, (int)n3), 0);
                object = (LinearLayout.LayoutParams)drawable2.getLayoutParams();
                n2 = drawable2.getMeasuredHeight() + ((LinearLayout.LayoutParams)object).topMargin + ((LinearLayout.LayoutParams)object).bottomMargin;
            } else {
                n2 = 0;
                dropDownListView = object;
            }
            this.mPopup.setContentView((View)dropDownListView);
        } else {
            dropDownListView = (ViewGroup)this.mPopup.getContentView();
            dropDownListView = this.mPromptView;
            if (dropDownListView != null) {
                LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)dropDownListView.getLayoutParams();
                n2 = dropDownListView.getMeasuredHeight() + layoutParams.topMargin + layoutParams.bottomMargin;
            } else {
                n2 = 0;
            }
        }
        dropDownListView = this.mPopup.getBackground();
        if (dropDownListView != null) {
            dropDownListView.getPadding(this.mTempRect);
            n = n3 = this.mTempRect.top + this.mTempRect.bottom;
            if (!this.mDropDownVerticalOffsetSet) {
                this.mDropDownVerticalOffset = -this.mTempRect.top;
                n = n3;
            }
        } else {
            this.mTempRect.setEmpty();
            n = 0;
        }
        if (this.mPopup.getInputMethodMode() != 2) {
            bl = false;
        }
        int n4 = this.getMaxAvailableHeight(this.getAnchorView(), this.mDropDownVerticalOffset, bl);
        if (this.mDropDownAlwaysVisible) return n4 + n;
        if (this.mDropDownHeight == -1) {
            return n4 + n;
        }
        n3 = this.mDropDownWidth;
        n3 = n3 != -2 ? (n3 != -1 ? View.MeasureSpec.makeMeasureSpec((int)n3, (int)1073741824) : View.MeasureSpec.makeMeasureSpec((int)(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right)), (int)1073741824)) : View.MeasureSpec.makeMeasureSpec((int)(this.mContext.getResources().getDisplayMetrics().widthPixels - (this.mTempRect.left + this.mTempRect.right)), (int)Integer.MIN_VALUE);
        n4 = this.mDropDownList.measureHeightOfChildrenCompat(n3, 0, -1, n4 - n2, -1);
        n3 = n2;
        if (n4 <= 0) return n4 + n3;
        n3 = n2 + (n + (this.mDropDownList.getPaddingTop() + this.mDropDownList.getPaddingBottom()));
        return n4 + n3;
    }

    private int getMaxAvailableHeight(View view, int n, boolean bl) {
        if (Build.VERSION.SDK_INT > 23) return this.mPopup.getMaxAvailableHeight(view, n, bl);
        Method method = sGetMaxAvailableHeightMethod;
        if (method == null) return this.mPopup.getMaxAvailableHeight(view, n);
        try {
            return (Integer)method.invoke((Object)this.mPopup, new Object[]{view, n, bl});
        }
        catch (Exception exception) {
            Log.i((String)TAG, (String)"Could not call getMaxAvailableHeightMethod(View, int, boolean) on PopupWindow. Using the public version.");
        }
        return this.mPopup.getMaxAvailableHeight(view, n);
    }

    private static boolean isConfirmKey(int n) {
        if (n == 66) return true;
        if (n == 23) return true;
        return false;
    }

    private void removePromptView() {
        View view = this.mPromptView;
        if (view == null) return;
        if (!((view = view.getParent()) instanceof ViewGroup)) return;
        ((ViewGroup)view).removeView(this.mPromptView);
    }

    private void setPopupClipToScreenEnabled(boolean bl) {
        if (Build.VERSION.SDK_INT > 28) {
            this.mPopup.setIsClippedToScreen(bl);
            return;
        }
        Method method = sSetClipToWindowEnabledMethod;
        if (method == null) return;
        try {
            method.invoke((Object)this.mPopup, bl);
            return;
        }
        catch (Exception exception) {
            Log.i((String)TAG, (String)"Could not call setClipToScreenEnabled() on PopupWindow. Oh well.");
            return;
        }
    }

    public void clearListSelection() {
        DropDownListView dropDownListView = this.mDropDownList;
        if (dropDownListView == null) return;
        dropDownListView.setListSelectionHidden(true);
        dropDownListView.requestLayout();
    }

    public View.OnTouchListener createDragToOpenListener(View view) {
        return new ForwardingListener(view){

            @Override
            public ListPopupWindow getPopup() {
                return ListPopupWindow.this;
            }
        };
    }

    DropDownListView createDropDownListView(Context context, boolean bl) {
        return new DropDownListView(context, bl);
    }

    @Override
    public void dismiss() {
        this.mPopup.dismiss();
        this.removePromptView();
        this.mPopup.setContentView(null);
        this.mDropDownList = null;
        this.mHandler.removeCallbacks((Runnable)this.mResizePopupRunnable);
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
        if (this.mEpicenterBounds == null) return null;
        return new Rect(this.mEpicenterBounds);
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

    @Override
    public ListView getListView() {
        return this.mDropDownList;
    }

    public int getPromptPosition() {
        return this.mPromptPosition;
    }

    public Object getSelectedItem() {
        if (this.isShowing()) return this.mDropDownList.getSelectedItem();
        return null;
    }

    public long getSelectedItemId() {
        if (this.isShowing()) return this.mDropDownList.getSelectedItemId();
        return Long.MIN_VALUE;
    }

    public int getSelectedItemPosition() {
        if (this.isShowing()) return this.mDropDownList.getSelectedItemPosition();
        return -1;
    }

    public View getSelectedView() {
        if (this.isShowing()) return this.mDropDownList.getSelectedView();
        return null;
    }

    public int getSoftInputMode() {
        return this.mPopup.getSoftInputMode();
    }

    public int getVerticalOffset() {
        if (this.mDropDownVerticalOffsetSet) return this.mDropDownVerticalOffset;
        return 0;
    }

    public int getWidth() {
        return this.mDropDownWidth;
    }

    public boolean isDropDownAlwaysVisible() {
        return this.mDropDownAlwaysVisible;
    }

    public boolean isInputMethodNotNeeded() {
        if (this.mPopup.getInputMethodMode() != 2) return false;
        return true;
    }

    public boolean isModal() {
        return this.mModal;
    }

    @Override
    public boolean isShowing() {
        return this.mPopup.isShowing();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        if (!this.isShowing()) return false;
        if (n == 62) return false;
        if (this.mDropDownList.getSelectedItemPosition() < 0) {
            if (ListPopupWindow.isConfirmKey(n)) return false;
        }
        int n2 = this.mDropDownList.getSelectedItemPosition();
        boolean bl = this.mPopup.isAboveAnchor() ^ true;
        ListAdapter listAdapter = this.mAdapter;
        int n3 = Integer.MAX_VALUE;
        int n4 = Integer.MIN_VALUE;
        if (listAdapter != null) {
            boolean bl2 = listAdapter.areAllItemsEnabled();
            n3 = bl2 ? 0 : this.mDropDownList.lookForSelectablePosition(0, true);
            n4 = bl2 ? listAdapter.getCount() - 1 : this.mDropDownList.lookForSelectablePosition(listAdapter.getCount() - 1, false);
        }
        if (bl && n == 19 && n2 <= n3 || !bl && n == 20 && n2 >= n4) {
            this.clearListSelection();
            this.mPopup.setInputMethodMode(1);
            this.show();
            return true;
        }
        this.mDropDownList.setListSelectionHidden(false);
        if (this.mDropDownList.onKeyDown(n, keyEvent)) {
            this.mPopup.setInputMethodMode(2);
            this.mDropDownList.requestFocusFromTouch();
            this.show();
            if (n == 19) return true;
            if (n == 20) return true;
            if (n == 23) return true;
            if (n == 66) return true;
            return false;
        }
        if (bl && n == 20) {
            if (n2 != n4) return false;
            return true;
        }
        if (bl) return false;
        if (n != 19) return false;
        if (n2 != n3) return false;
        return true;
    }

    public boolean onKeyPreIme(int n, KeyEvent keyEvent) {
        if (n != 4) return false;
        if (!this.isShowing()) return false;
        View view = this.mDropDownAnchorView;
        if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
            if ((view = view.getKeyDispatcherState()) == null) return true;
            view.startTracking(keyEvent, (Object)this);
            return true;
        }
        if (keyEvent.getAction() != 1) return false;
        if ((view = view.getKeyDispatcherState()) != null) {
            view.handleUpEvent(keyEvent);
        }
        if (!keyEvent.isTracking()) return false;
        if (keyEvent.isCanceled()) return false;
        this.dismiss();
        return true;
    }

    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        if (!this.isShowing()) return false;
        if (this.mDropDownList.getSelectedItemPosition() < 0) return false;
        boolean bl = this.mDropDownList.onKeyUp(n, keyEvent);
        if (!bl) return bl;
        if (!ListPopupWindow.isConfirmKey(n)) return bl;
        this.dismiss();
        return bl;
    }

    public boolean performItemClick(int n) {
        if (!this.isShowing()) return false;
        if (this.mItemClickListener == null) return true;
        DropDownListView dropDownListView = this.mDropDownList;
        View view = dropDownListView.getChildAt(n - dropDownListView.getFirstVisiblePosition());
        ListAdapter listAdapter = dropDownListView.getAdapter();
        this.mItemClickListener.onItemClick((AdapterView)dropDownListView, view, n, listAdapter.getItemId(n));
        return true;
    }

    public void postShow() {
        this.mHandler.post(this.mShowDropDownRunnable);
    }

    public void setAdapter(ListAdapter object) {
        DataSetObserver dataSetObserver = this.mObserver;
        if (dataSetObserver == null) {
            this.mObserver = new PopupDataSetObserver();
        } else {
            ListAdapter listAdapter = this.mAdapter;
            if (listAdapter != null) {
                listAdapter.unregisterDataSetObserver(dataSetObserver);
            }
        }
        this.mAdapter = object;
        if (object != null) {
            object.registerDataSetObserver(this.mObserver);
        }
        if ((object = this.mDropDownList) == null) return;
        object.setAdapter(this.mAdapter);
    }

    public void setAnchorView(View view) {
        this.mDropDownAnchorView = view;
    }

    public void setAnimationStyle(int n) {
        this.mPopup.setAnimationStyle(n);
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        this.mPopup.setBackgroundDrawable(drawable2);
    }

    public void setContentWidth(int n) {
        Drawable drawable2 = this.mPopup.getBackground();
        if (drawable2 != null) {
            drawable2.getPadding(this.mTempRect);
            this.mDropDownWidth = this.mTempRect.left + this.mTempRect.right + n;
            return;
        }
        this.setWidth(n);
    }

    public void setDropDownAlwaysVisible(boolean bl) {
        this.mDropDownAlwaysVisible = bl;
    }

    public void setDropDownGravity(int n) {
        this.mDropDownGravity = n;
    }

    public void setEpicenterBounds(Rect object) {
        object = object != null ? new Rect(object) : null;
        this.mEpicenterBounds = object;
    }

    public void setForceIgnoreOutsideTouch(boolean bl) {
        this.mForceIgnoreOutsideTouch = bl;
    }

    public void setHeight(int n) {
        if (n < 0 && -2 != n) {
            if (-1 != n) throw new IllegalArgumentException("Invalid height. Must be a positive value, MATCH_PARENT, or WRAP_CONTENT.");
        }
        this.mDropDownHeight = n;
    }

    public void setHorizontalOffset(int n) {
        this.mDropDownHorizontalOffset = n;
    }

    public void setInputMethodMode(int n) {
        this.mPopup.setInputMethodMode(n);
    }

    void setListItemExpandMax(int n) {
        this.mListItemExpandMaximum = n;
    }

    public void setListSelector(Drawable drawable2) {
        this.mDropDownListHighlight = drawable2;
    }

    public void setModal(boolean bl) {
        this.mModal = bl;
        this.mPopup.setFocusable(bl);
    }

    public void setOnDismissListener(PopupWindow.OnDismissListener onDismissListener) {
        this.mPopup.setOnDismissListener(onDismissListener);
    }

    public void setOnItemClickListener(AdapterView.OnItemClickListener onItemClickListener) {
        this.mItemClickListener = onItemClickListener;
    }

    public void setOnItemSelectedListener(AdapterView.OnItemSelectedListener onItemSelectedListener) {
        this.mItemSelectedListener = onItemSelectedListener;
    }

    public void setOverlapAnchor(boolean bl) {
        this.mOverlapAnchorSet = true;
        this.mOverlapAnchor = bl;
    }

    public void setPromptPosition(int n) {
        this.mPromptPosition = n;
    }

    public void setPromptView(View view) {
        boolean bl = this.isShowing();
        if (bl) {
            this.removePromptView();
        }
        this.mPromptView = view;
        if (!bl) return;
        this.show();
    }

    public void setSelection(int n) {
        DropDownListView dropDownListView = this.mDropDownList;
        if (!this.isShowing()) return;
        if (dropDownListView == null) return;
        dropDownListView.setListSelectionHidden(false);
        dropDownListView.setSelection(n);
        if (dropDownListView.getChoiceMode() == 0) return;
        dropDownListView.setItemChecked(n, true);
    }

    public void setSoftInputMode(int n) {
        this.mPopup.setSoftInputMode(n);
    }

    public void setVerticalOffset(int n) {
        this.mDropDownVerticalOffset = n;
        this.mDropDownVerticalOffsetSet = true;
    }

    public void setWidth(int n) {
        this.mDropDownWidth = n;
    }

    public void setWindowLayoutType(int n) {
        this.mDropDownWindowLayoutType = n;
    }

    @Override
    public void show() {
        int n;
        int n2 = this.buildDropDown();
        boolean bl = this.isInputMethodNotNeeded();
        PopupWindowCompat.setWindowLayoutType(this.mPopup, this.mDropDownWindowLayoutType);
        boolean bl2 = this.mPopup.isShowing();
        boolean bl3 = true;
        if (bl2) {
            int n3;
            PopupWindow popupWindow;
            if (!ViewCompat.isAttachedToWindow(this.getAnchorView())) {
                return;
            }
            int n4 = this.mDropDownWidth;
            if (n4 == -1) {
                n3 = -1;
            } else {
                n3 = n4;
                if (n4 == -2) {
                    n3 = this.getAnchorView().getWidth();
                }
            }
            n4 = this.mDropDownHeight;
            if (n4 == -1) {
                if (!bl) {
                    n2 = -1;
                }
                if (bl) {
                    popupWindow = this.mPopup;
                    n4 = this.mDropDownWidth == -1 ? -1 : 0;
                    popupWindow.setWidth(n4);
                    this.mPopup.setHeight(0);
                } else {
                    popupWindow = this.mPopup;
                    n4 = this.mDropDownWidth == -1 ? -1 : 0;
                    popupWindow.setWidth(n4);
                    this.mPopup.setHeight(-1);
                }
            } else if (n4 != -2) {
                n2 = n4;
            }
            popupWindow = this.mPopup;
            if (this.mForceIgnoreOutsideTouch || this.mDropDownAlwaysVisible) {
                bl3 = false;
            }
            popupWindow.setOutsideTouchable(bl3);
            popupWindow = this.mPopup;
            View view = this.getAnchorView();
            n4 = this.mDropDownHorizontalOffset;
            int n5 = this.mDropDownVerticalOffset;
            if (n3 < 0) {
                n3 = -1;
            }
            if (n2 < 0) {
                n2 = -1;
            }
            popupWindow.update(view, n4, n5, n3, n2);
            return;
        }
        int n6 = this.mDropDownWidth;
        if (n6 == -1) {
            n = -1;
        } else {
            n = n6;
            if (n6 == -2) {
                n = this.getAnchorView().getWidth();
            }
        }
        n6 = this.mDropDownHeight;
        if (n6 == -1) {
            n2 = -1;
        } else if (n6 != -2) {
            n2 = n6;
        }
        this.mPopup.setWidth(n);
        this.mPopup.setHeight(n2);
        this.setPopupClipToScreenEnabled(true);
        Object object = this.mPopup;
        bl3 = !this.mForceIgnoreOutsideTouch && !this.mDropDownAlwaysVisible;
        object.setOutsideTouchable(bl3);
        this.mPopup.setTouchInterceptor((View.OnTouchListener)this.mTouchInterceptor);
        if (this.mOverlapAnchorSet) {
            PopupWindowCompat.setOverlapAnchor(this.mPopup, this.mOverlapAnchor);
        }
        if (Build.VERSION.SDK_INT <= 28) {
            object = sSetEpicenterBoundsMethod;
            if (object != null) {
                try {
                    ((Method)object).invoke((Object)this.mPopup, new Object[]{this.mEpicenterBounds});
                }
                catch (Exception exception) {
                    Log.e((String)TAG, (String)"Could not invoke setEpicenterBounds on PopupWindow", (Throwable)exception);
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
        if (this.mModal) return;
        this.mHandler.post((Runnable)this.mHideSelector);
    }

    private class ListSelectorHider
    implements Runnable {
        ListSelectorHider() {
        }

        @Override
        public void run() {
            ListPopupWindow.this.clearListSelection();
        }
    }

    private class PopupDataSetObserver
    extends DataSetObserver {
        PopupDataSetObserver() {
        }

        public void onChanged() {
            if (!ListPopupWindow.this.isShowing()) return;
            ListPopupWindow.this.show();
        }

        public void onInvalidated() {
            ListPopupWindow.this.dismiss();
        }
    }

    private class PopupScrollListener
    implements AbsListView.OnScrollListener {
        PopupScrollListener() {
        }

        public void onScroll(AbsListView absListView, int n, int n2, int n3) {
        }

        public void onScrollStateChanged(AbsListView absListView, int n) {
            if (n != 1) return;
            if (ListPopupWindow.this.isInputMethodNotNeeded()) return;
            if (ListPopupWindow.this.mPopup.getContentView() == null) return;
            ListPopupWindow.this.mHandler.removeCallbacks((Runnable)ListPopupWindow.this.mResizePopupRunnable);
            ListPopupWindow.this.mResizePopupRunnable.run();
        }
    }

    private class PopupTouchInterceptor
    implements View.OnTouchListener {
        PopupTouchInterceptor() {
        }

        public boolean onTouch(View view, MotionEvent motionEvent) {
            int n = motionEvent.getAction();
            int n2 = (int)motionEvent.getX();
            int n3 = (int)motionEvent.getY();
            if (n == 0 && ListPopupWindow.this.mPopup != null && ListPopupWindow.this.mPopup.isShowing() && n2 >= 0 && n2 < ListPopupWindow.this.mPopup.getWidth() && n3 >= 0 && n3 < ListPopupWindow.this.mPopup.getHeight()) {
                ListPopupWindow.this.mHandler.postDelayed((Runnable)ListPopupWindow.this.mResizePopupRunnable, 250L);
                return false;
            }
            if (n != 1) return false;
            ListPopupWindow.this.mHandler.removeCallbacks((Runnable)ListPopupWindow.this.mResizePopupRunnable);
            return false;
        }
    }

    private class ResizePopupRunnable
    implements Runnable {
        ResizePopupRunnable() {
        }

        @Override
        public void run() {
            if (ListPopupWindow.this.mDropDownList == null) return;
            if (!ViewCompat.isAttachedToWindow((View)ListPopupWindow.this.mDropDownList)) return;
            if (ListPopupWindow.this.mDropDownList.getCount() <= ListPopupWindow.this.mDropDownList.getChildCount()) return;
            if (ListPopupWindow.this.mDropDownList.getChildCount() > ListPopupWindow.this.mListItemExpandMaximum) return;
            ListPopupWindow.this.mPopup.setInputMethodMode(2);
            ListPopupWindow.this.show();
        }
    }

}


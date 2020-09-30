/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.database.DataSetObserver
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$BaseSavedState
 *  android.view.View$MeasureSpec
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnGlobalLayoutListener
 *  android.widget.Adapter
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ArrayAdapter
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 *  android.widget.ThemedSpinnerAdapter
 */
package androidx.appcompat.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import androidx.appcompat.R;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.content.res.AppCompatResources;
import androidx.appcompat.view.ContextThemeWrapper;
import androidx.appcompat.view.menu.ShowableListMenu;
import androidx.appcompat.widget.AppCompatBackgroundHelper;
import androidx.appcompat.widget.ForwardingListener;
import androidx.appcompat.widget.ListPopupWindow;
import androidx.appcompat.widget.ThemeUtils;
import androidx.appcompat.widget.ThemedSpinnerAdapter;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.view.TintableBackgroundView;
import androidx.core.view.ViewCompat;

public class AppCompatSpinner
extends Spinner
implements TintableBackgroundView {
    private static final int[] ATTRS_ANDROID_SPINNERMODE = new int[]{16843505};
    private static final int MAX_ITEMS_MEASURED = 15;
    private static final int MODE_DIALOG = 0;
    private static final int MODE_DROPDOWN = 1;
    private static final int MODE_THEME = -1;
    private static final String TAG = "AppCompatSpinner";
    private final AppCompatBackgroundHelper mBackgroundTintHelper;
    int mDropDownWidth;
    private ForwardingListener mForwardingListener;
    private SpinnerPopup mPopup;
    private final Context mPopupContext;
    private final boolean mPopupSet;
    private SpinnerAdapter mTempAdapter;
    final Rect mTempRect;

    public AppCompatSpinner(Context context) {
        this(context, null);
    }

    public AppCompatSpinner(Context context, int n) {
        this(context, null, R.attr.spinnerStyle, n);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.spinnerStyle);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n) {
        this(context, attributeSet, n, -1);
    }

    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n, int n2) {
        this(context, attributeSet, n, n2, null);
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled unnecessary exception pruning
     */
    public AppCompatSpinner(Context context, AttributeSet attributeSet, int n, int n2, Resources.Theme arrobject) {
        int n3;
        TintTypedArray tintTypedArray;
        Object object;
        block15 : {
            int n4;
            super(context, attributeSet, n);
            this.mTempRect = new Rect();
            ThemeUtils.checkAppCompatTheme((View)this, this.getContext());
            tintTypedArray = TintTypedArray.obtainStyledAttributes(context, attributeSet, R.styleable.Spinner, n, 0);
            this.mBackgroundTintHelper = new AppCompatBackgroundHelper((View)this);
            this.mPopupContext = arrobject != null ? new ContextThemeWrapper(context, (Resources.Theme)arrobject) : ((n4 = tintTypedArray.getResourceId(R.styleable.Spinner_popupTheme, 0)) != 0 ? new ContextThemeWrapper(context, n4) : context);
            object = null;
            n3 = n2;
            if (n2 == -1) {
                void var1_4;
                block18 : {
                    block16 : {
                        block17 : {
                            arrobject = context.obtainStyledAttributes(attributeSet, ATTRS_ANDROID_SPINNERMODE, n, 0);
                            n4 = n2;
                            object = arrobject;
                            try {
                                if (arrobject.hasValue(0)) {
                                    object = arrobject;
                                    n4 = arrobject.getInt(0, 0);
                                }
                                n3 = n4;
                                if (arrobject == null) break block15;
                                n2 = n4;
                                break block16;
                            }
                            catch (Exception exception) {
                                break block17;
                            }
                            catch (Throwable throwable) {
                                break block18;
                            }
                            catch (Exception exception) {
                                arrobject = null;
                            }
                        }
                        object = arrobject;
                        try {
                            void var10_15;
                            Log.i((String)TAG, (String)"Could not read android:spinnerMode", (Throwable)var10_15);
                            n3 = n2;
                            if (arrobject == null) break block15;
                        }
                        catch (Throwable throwable) {
                            // empty catch block
                        }
                    }
                    arrobject.recycle();
                    n3 = n2;
                    {
                        break block15;
                    }
                }
                if (object == null) throw var1_4;
                object.recycle();
                throw var1_4;
            }
        }
        if (n3 != 0) {
            if (n3 == 1) {
                arrobject = new DropdownPopup(this.mPopupContext, attributeSet, n);
                object = TintTypedArray.obtainStyledAttributes(this.mPopupContext, attributeSet, R.styleable.Spinner, n, 0);
                this.mDropDownWidth = ((TintTypedArray)object).getLayoutDimension(R.styleable.Spinner_android_dropDownWidth, -2);
                arrobject.setBackgroundDrawable(((TintTypedArray)object).getDrawable(R.styleable.Spinner_android_popupBackground));
                arrobject.setPromptText(tintTypedArray.getString(R.styleable.Spinner_android_prompt));
                ((TintTypedArray)object).recycle();
                this.mPopup = arrobject;
                this.mForwardingListener = new ForwardingListener((View)this, (DropdownPopup)arrobject){
                    final /* synthetic */ DropdownPopup val$popup;
                    {
                        this.val$popup = dropdownPopup;
                        super(view);
                    }

                    @Override
                    public ShowableListMenu getPopup() {
                        return this.val$popup;
                    }

                    @Override
                    public boolean onForwardingStarted() {
                        if (AppCompatSpinner.this.getInternalPopup().isShowing()) return true;
                        AppCompatSpinner.this.showPopup();
                        return true;
                    }
                };
            }
        } else {
            arrobject = new DialogPopup();
            this.mPopup = arrobject;
            arrobject.setPromptText(tintTypedArray.getString(R.styleable.Spinner_android_prompt));
        }
        arrobject = tintTypedArray.getTextArray(R.styleable.Spinner_android_entries);
        if (arrobject != null) {
            context = new ArrayAdapter(context, 17367048, arrobject);
            context.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            this.setAdapter((SpinnerAdapter)context);
        }
        tintTypedArray.recycle();
        this.mPopupSet = true;
        context = this.mTempAdapter;
        if (context != null) {
            this.setAdapter((SpinnerAdapter)context);
            this.mTempAdapter = null;
        }
        this.mBackgroundTintHelper.loadFromAttributes(attributeSet, n);
    }

    int compatMeasureContentWidth(SpinnerAdapter spinnerAdapter, Drawable drawable2) {
        int n = 0;
        if (spinnerAdapter == null) {
            return 0;
        }
        int n2 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredWidth(), (int)0);
        int n3 = View.MeasureSpec.makeMeasureSpec((int)this.getMeasuredHeight(), (int)0);
        int n4 = Math.max(0, this.getSelectedItemPosition());
        int n5 = Math.min(spinnerAdapter.getCount(), n4 + 15);
        int n6 = Math.max(0, n4 - (15 - (n5 - n4)));
        View view = null;
        n4 = 0;
        do {
            if (n6 >= n5) {
                n6 = n4;
                if (drawable2 == null) return n6;
                drawable2.getPadding(this.mTempRect);
                return n4 + (this.mTempRect.left + this.mTempRect.right);
            }
            int n7 = spinnerAdapter.getItemViewType(n6);
            int n8 = n;
            if (n7 != n) {
                view = null;
                n8 = n7;
            }
            if ((view = spinnerAdapter.getView(n6, view, (ViewGroup)this)).getLayoutParams() == null) {
                view.setLayoutParams(new ViewGroup.LayoutParams(-2, -2));
            }
            view.measure(n2, n3);
            n4 = Math.max(n4, view.getMeasuredWidth());
            ++n6;
            n = n8;
        } while (true);
    }

    protected void drawableStateChanged() {
        super.drawableStateChanged();
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return;
        appCompatBackgroundHelper.applySupportBackgroundTint();
    }

    public int getDropDownHorizontalOffset() {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            return spinnerPopup.getHorizontalOffset();
        }
        if (Build.VERSION.SDK_INT < 16) return 0;
        return super.getDropDownHorizontalOffset();
    }

    public int getDropDownVerticalOffset() {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            return spinnerPopup.getVerticalOffset();
        }
        if (Build.VERSION.SDK_INT < 16) return 0;
        return super.getDropDownVerticalOffset();
    }

    public int getDropDownWidth() {
        if (this.mPopup != null) {
            return this.mDropDownWidth;
        }
        if (Build.VERSION.SDK_INT < 16) return 0;
        return super.getDropDownWidth();
    }

    final SpinnerPopup getInternalPopup() {
        return this.mPopup;
    }

    public Drawable getPopupBackground() {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            return spinnerPopup.getBackground();
        }
        if (Build.VERSION.SDK_INT < 16) return null;
        return super.getPopupBackground();
    }

    public Context getPopupContext() {
        return this.mPopupContext;
    }

    public CharSequence getPrompt() {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup == null) return super.getPrompt();
        return spinnerPopup.getHintText();
    }

    @Override
    public ColorStateList getSupportBackgroundTintList() {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return null;
        return appCompatBackgroundHelper.getSupportBackgroundTintList();
    }

    @Override
    public PorterDuff.Mode getSupportBackgroundTintMode() {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return null;
        return appCompatBackgroundHelper.getSupportBackgroundTintMode();
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup == null) return;
        if (!spinnerPopup.isShowing()) return;
        this.mPopup.dismiss();
    }

    protected void onMeasure(int n, int n2) {
        super.onMeasure(n, n2);
        if (this.mPopup == null) return;
        if (View.MeasureSpec.getMode((int)n) != Integer.MIN_VALUE) return;
        this.setMeasuredDimension(Math.min(Math.max(this.getMeasuredWidth(), this.compatMeasureContentWidth(this.getAdapter(), this.getBackground())), View.MeasureSpec.getSize((int)n)), this.getMeasuredHeight());
    }

    public void onRestoreInstanceState(Parcelable object) {
        object = (SavedState)((Object)object);
        super.onRestoreInstanceState(object.getSuperState());
        if (!object.mShowDropdown) return;
        object = this.getViewTreeObserver();
        if (object == null) return;
        object.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener(){

            public void onGlobalLayout() {
                ViewTreeObserver viewTreeObserver;
                if (!AppCompatSpinner.this.getInternalPopup().isShowing()) {
                    AppCompatSpinner.this.showPopup();
                }
                if ((viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver()) == null) return;
                if (Build.VERSION.SDK_INT >= 16) {
                    viewTreeObserver.removeOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
                    return;
                }
                viewTreeObserver.removeGlobalOnLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)this);
            }
        });
    }

    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        SpinnerPopup spinnerPopup = this.mPopup;
        boolean bl = spinnerPopup != null && spinnerPopup.isShowing();
        savedState.mShowDropdown = bl;
        return savedState;
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        ForwardingListener forwardingListener = this.mForwardingListener;
        if (forwardingListener == null) return super.onTouchEvent(motionEvent);
        if (!forwardingListener.onTouch((View)this, motionEvent)) return super.onTouchEvent(motionEvent);
        return true;
    }

    public boolean performClick() {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup == null) return super.performClick();
        if (spinnerPopup.isShowing()) return true;
        this.showPopup();
        return true;
    }

    public void setAdapter(SpinnerAdapter spinnerAdapter) {
        Context context;
        if (!this.mPopupSet) {
            this.mTempAdapter = spinnerAdapter;
            return;
        }
        super.setAdapter(spinnerAdapter);
        if (this.mPopup == null) return;
        Context context2 = context = this.mPopupContext;
        if (context == null) {
            context2 = this.getContext();
        }
        this.mPopup.setAdapter(new DropDownAdapter(spinnerAdapter, context2.getTheme()));
    }

    public void setBackgroundDrawable(Drawable drawable2) {
        super.setBackgroundDrawable(drawable2);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return;
        appCompatBackgroundHelper.onSetBackgroundDrawable(drawable2);
    }

    public void setBackgroundResource(int n) {
        super.setBackgroundResource(n);
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return;
        appCompatBackgroundHelper.onSetBackgroundResource(n);
    }

    public void setDropDownHorizontalOffset(int n) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setHorizontalOriginalOffset(n);
            this.mPopup.setHorizontalOffset(n);
            return;
        }
        if (Build.VERSION.SDK_INT < 16) return;
        super.setDropDownHorizontalOffset(n);
    }

    public void setDropDownVerticalOffset(int n) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setVerticalOffset(n);
            return;
        }
        if (Build.VERSION.SDK_INT < 16) return;
        super.setDropDownVerticalOffset(n);
    }

    public void setDropDownWidth(int n) {
        if (this.mPopup != null) {
            this.mDropDownWidth = n;
            return;
        }
        if (Build.VERSION.SDK_INT < 16) return;
        super.setDropDownWidth(n);
    }

    public void setPopupBackgroundDrawable(Drawable drawable2) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setBackgroundDrawable(drawable2);
            return;
        }
        if (Build.VERSION.SDK_INT < 16) return;
        super.setPopupBackgroundDrawable(drawable2);
    }

    public void setPopupBackgroundResource(int n) {
        this.setPopupBackgroundDrawable(AppCompatResources.getDrawable(this.getPopupContext(), n));
    }

    public void setPrompt(CharSequence charSequence) {
        SpinnerPopup spinnerPopup = this.mPopup;
        if (spinnerPopup != null) {
            spinnerPopup.setPromptText(charSequence);
            return;
        }
        super.setPrompt(charSequence);
    }

    @Override
    public void setSupportBackgroundTintList(ColorStateList colorStateList) {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return;
        appCompatBackgroundHelper.setSupportBackgroundTintList(colorStateList);
    }

    @Override
    public void setSupportBackgroundTintMode(PorterDuff.Mode mode) {
        AppCompatBackgroundHelper appCompatBackgroundHelper = this.mBackgroundTintHelper;
        if (appCompatBackgroundHelper == null) return;
        appCompatBackgroundHelper.setSupportBackgroundTintMode(mode);
    }

    void showPopup() {
        if (Build.VERSION.SDK_INT >= 17) {
            this.mPopup.show(this.getTextDirection(), this.getTextAlignment());
            return;
        }
        this.mPopup.show(-1, -1);
    }

    class DialogPopup
    implements SpinnerPopup,
    DialogInterface.OnClickListener {
        private ListAdapter mListAdapter;
        AlertDialog mPopup;
        private CharSequence mPrompt;

        DialogPopup() {
        }

        @Override
        public void dismiss() {
            AlertDialog alertDialog = this.mPopup;
            if (alertDialog == null) return;
            alertDialog.dismiss();
            this.mPopup = null;
        }

        @Override
        public Drawable getBackground() {
            return null;
        }

        @Override
        public CharSequence getHintText() {
            return this.mPrompt;
        }

        @Override
        public int getHorizontalOffset() {
            return 0;
        }

        @Override
        public int getHorizontalOriginalOffset() {
            return 0;
        }

        @Override
        public int getVerticalOffset() {
            return 0;
        }

        @Override
        public boolean isShowing() {
            AlertDialog alertDialog = this.mPopup;
            if (alertDialog == null) return false;
            return alertDialog.isShowing();
        }

        public void onClick(DialogInterface dialogInterface, int n) {
            AppCompatSpinner.this.setSelection(n);
            if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                AppCompatSpinner.this.performItemClick(null, n, this.mListAdapter.getItemId(n));
            }
            this.dismiss();
        }

        @Override
        public void setAdapter(ListAdapter listAdapter) {
            this.mListAdapter = listAdapter;
        }

        @Override
        public void setBackgroundDrawable(Drawable drawable2) {
            Log.e((String)AppCompatSpinner.TAG, (String)"Cannot set popup background for MODE_DIALOG, ignoring");
        }

        @Override
        public void setHorizontalOffset(int n) {
            Log.e((String)AppCompatSpinner.TAG, (String)"Cannot set horizontal offset for MODE_DIALOG, ignoring");
        }

        @Override
        public void setHorizontalOriginalOffset(int n) {
            Log.e((String)AppCompatSpinner.TAG, (String)"Cannot set horizontal (original) offset for MODE_DIALOG, ignoring");
        }

        @Override
        public void setPromptText(CharSequence charSequence) {
            this.mPrompt = charSequence;
        }

        @Override
        public void setVerticalOffset(int n) {
            Log.e((String)AppCompatSpinner.TAG, (String)"Cannot set vertical offset for MODE_DIALOG, ignoring");
        }

        @Override
        public void show(int n, int n2) {
            if (this.mListAdapter == null) {
                return;
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(AppCompatSpinner.this.getPopupContext());
            Object object = this.mPrompt;
            if (object != null) {
                builder.setTitle((CharSequence)object);
            }
            this.mPopup = object = builder.setSingleChoiceItems(this.mListAdapter, AppCompatSpinner.this.getSelectedItemPosition(), (DialogInterface.OnClickListener)this).create();
            object = ((AlertDialog)object).getListView();
            if (Build.VERSION.SDK_INT >= 17) {
                object.setTextDirection(n);
                object.setTextAlignment(n2);
            }
            this.mPopup.show();
        }
    }

    private static class DropDownAdapter
    implements ListAdapter,
    SpinnerAdapter {
        private SpinnerAdapter mAdapter;
        private ListAdapter mListAdapter;

        public DropDownAdapter(SpinnerAdapter spinnerAdapter, Resources.Theme theme) {
            this.mAdapter = spinnerAdapter;
            if (spinnerAdapter instanceof ListAdapter) {
                this.mListAdapter = (ListAdapter)spinnerAdapter;
            }
            if (theme == null) return;
            if (Build.VERSION.SDK_INT >= 23 && spinnerAdapter instanceof android.widget.ThemedSpinnerAdapter) {
                if ((spinnerAdapter = (android.widget.ThemedSpinnerAdapter)spinnerAdapter).getDropDownViewTheme() == theme) return;
                spinnerAdapter.setDropDownViewTheme(theme);
                return;
            }
            if (!(spinnerAdapter instanceof ThemedSpinnerAdapter)) return;
            if ((spinnerAdapter = (ThemedSpinnerAdapter)spinnerAdapter).getDropDownViewTheme() != null) return;
            spinnerAdapter.setDropDownViewTheme(theme);
        }

        public boolean areAllItemsEnabled() {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter == null) return true;
            return listAdapter.areAllItemsEnabled();
        }

        public int getCount() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) return spinnerAdapter.getCount();
            return 0;
        }

        public View getDropDownView(int n, View view, ViewGroup viewGroup) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) return spinnerAdapter.getDropDownView(n, view, viewGroup);
            return null;
        }

        public Object getItem(int n) {
            Object object = this.mAdapter;
            if (object != null) return object.getItem(n);
            return null;
        }

        public long getItemId(int n) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter != null) return spinnerAdapter.getItemId(n);
            return -1L;
        }

        public int getItemViewType(int n) {
            return 0;
        }

        public View getView(int n, View view, ViewGroup viewGroup) {
            return this.getDropDownView(n, view, viewGroup);
        }

        public int getViewTypeCount() {
            return 1;
        }

        public boolean hasStableIds() {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) return false;
            if (!spinnerAdapter.hasStableIds()) return false;
            return true;
        }

        public boolean isEmpty() {
            if (this.getCount() != 0) return false;
            return true;
        }

        public boolean isEnabled(int n) {
            ListAdapter listAdapter = this.mListAdapter;
            if (listAdapter == null) return true;
            return listAdapter.isEnabled(n);
        }

        public void registerDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) return;
            spinnerAdapter.registerDataSetObserver(dataSetObserver);
        }

        public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {
            SpinnerAdapter spinnerAdapter = this.mAdapter;
            if (spinnerAdapter == null) return;
            spinnerAdapter.unregisterDataSetObserver(dataSetObserver);
        }
    }

    class DropdownPopup
    extends ListPopupWindow
    implements SpinnerPopup {
        ListAdapter mAdapter;
        private CharSequence mHintText;
        private int mOriginalHorizontalOffset;
        private final Rect mVisibleRect;

        public DropdownPopup(Context context, AttributeSet attributeSet, int n) {
            super(context, attributeSet, n);
            this.mVisibleRect = new Rect();
            this.setAnchorView((View)AppCompatSpinner.this);
            this.setModal(true);
            this.setPromptPosition(0);
            this.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                    AppCompatSpinner.this.setSelection(n);
                    if (AppCompatSpinner.this.getOnItemClickListener() != null) {
                        AppCompatSpinner.this.performItemClick(view, n, DropdownPopup.this.mAdapter.getItemId(n));
                    }
                    DropdownPopup.this.dismiss();
                }
            });
        }

        void computeContentWidth() {
            Drawable drawable2 = this.getBackground();
            int n = 0;
            if (drawable2 != null) {
                drawable2.getPadding(AppCompatSpinner.this.mTempRect);
                n = ViewUtils.isLayoutRtl((View)AppCompatSpinner.this) ? AppCompatSpinner.this.mTempRect.right : -AppCompatSpinner.this.mTempRect.left;
            } else {
                drawable2 = AppCompatSpinner.this.mTempRect;
                AppCompatSpinner.this.mTempRect.right = 0;
                drawable2.left = 0;
            }
            int n2 = AppCompatSpinner.this.getPaddingLeft();
            int n3 = AppCompatSpinner.this.getPaddingRight();
            int n4 = AppCompatSpinner.this.getWidth();
            if (AppCompatSpinner.this.mDropDownWidth == -2) {
                int n5 = AppCompatSpinner.this.compatMeasureContentWidth((SpinnerAdapter)this.mAdapter, this.getBackground());
                int n6 = AppCompatSpinner.this.getContext().getResources().getDisplayMetrics().widthPixels - AppCompatSpinner.this.mTempRect.left - AppCompatSpinner.this.mTempRect.right;
                int n7 = n5;
                if (n5 > n6) {
                    n7 = n6;
                }
                this.setContentWidth(Math.max(n7, n4 - n2 - n3));
            } else if (AppCompatSpinner.this.mDropDownWidth == -1) {
                this.setContentWidth(n4 - n2 - n3);
            } else {
                this.setContentWidth(AppCompatSpinner.this.mDropDownWidth);
            }
            n = ViewUtils.isLayoutRtl((View)AppCompatSpinner.this) ? (n += n4 - n3 - this.getWidth() - this.getHorizontalOriginalOffset()) : (n += n2 + this.getHorizontalOriginalOffset());
            this.setHorizontalOffset(n);
        }

        @Override
        public CharSequence getHintText() {
            return this.mHintText;
        }

        @Override
        public int getHorizontalOriginalOffset() {
            return this.mOriginalHorizontalOffset;
        }

        boolean isVisibleToUser(View view) {
            if (!ViewCompat.isAttachedToWindow(view)) return false;
            if (!view.getGlobalVisibleRect(this.mVisibleRect)) return false;
            return true;
        }

        @Override
        public void setAdapter(ListAdapter listAdapter) {
            super.setAdapter(listAdapter);
            this.mAdapter = listAdapter;
        }

        @Override
        public void setHorizontalOriginalOffset(int n) {
            this.mOriginalHorizontalOffset = n;
        }

        @Override
        public void setPromptText(CharSequence charSequence) {
            this.mHintText = charSequence;
        }

        @Override
        public void show(int n, int n2) {
            boolean bl = this.isShowing();
            this.computeContentWidth();
            this.setInputMethodMode(2);
            super.show();
            Object object = this.getListView();
            object.setChoiceMode(1);
            if (Build.VERSION.SDK_INT >= 17) {
                object.setTextDirection(n);
                object.setTextAlignment(n2);
            }
            this.setSelection(AppCompatSpinner.this.getSelectedItemPosition());
            if (bl) {
                return;
            }
            ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
            if (viewTreeObserver == null) return;
            object = new ViewTreeObserver.OnGlobalLayoutListener(){

                public void onGlobalLayout() {
                    DropdownPopup dropdownPopup = DropdownPopup.this;
                    if (!dropdownPopup.isVisibleToUser((View)dropdownPopup.AppCompatSpinner.this)) {
                        DropdownPopup.this.dismiss();
                        return;
                    }
                    DropdownPopup.this.computeContentWidth();
                    DropdownPopup.super.show();
                }
            };
            viewTreeObserver.addOnGlobalLayoutListener((ViewTreeObserver.OnGlobalLayoutListener)object);
            this.setOnDismissListener(new PopupWindow.OnDismissListener((ViewTreeObserver.OnGlobalLayoutListener)object){
                final /* synthetic */ ViewTreeObserver.OnGlobalLayoutListener val$layoutListener;
                {
                    this.val$layoutListener = onGlobalLayoutListener;
                }

                public void onDismiss() {
                    ViewTreeObserver viewTreeObserver = AppCompatSpinner.this.getViewTreeObserver();
                    if (viewTreeObserver == null) return;
                    viewTreeObserver.removeGlobalOnLayoutListener(this.val$layoutListener);
                }
            });
        }

    }

    static class SavedState
    extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean mShowDropdown;

        SavedState(Parcel parcel) {
            super(parcel);
            boolean bl = parcel.readByte() != 0;
            this.mShowDropdown = bl;
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeByte((byte)(this.mShowDropdown ? 1 : 0));
        }

    }

    static interface SpinnerPopup {
        public void dismiss();

        public Drawable getBackground();

        public CharSequence getHintText();

        public int getHorizontalOffset();

        public int getHorizontalOriginalOffset();

        public int getVerticalOffset();

        public boolean isShowing();

        public void setAdapter(ListAdapter var1);

        public void setBackgroundDrawable(Drawable var1);

        public void setHorizontalOffset(int var1);

        public void setHorizontalOriginalOffset(int var1);

        public void setPromptText(CharSequence var1);

        public void setVerticalOffset(int var1);

        public void show(int var1, int var2);
    }

}


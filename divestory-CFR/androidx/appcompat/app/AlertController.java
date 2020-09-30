/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.DialogInterface$OnClickListener
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.DialogInterface$OnKeyListener
 *  android.content.DialogInterface$OnMultiChoiceClickListener
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.TypedArray
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Message
 *  android.text.TextUtils
 *  android.util.AttributeSet
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.ViewParent
 *  android.view.ViewStub
 *  android.view.Window
 *  android.widget.AbsListView
 *  android.widget.AbsListView$OnScrollListener
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.Button
 *  android.widget.CheckedTextView
 *  android.widget.CursorAdapter
 *  android.widget.FrameLayout
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.SimpleCursorAdapter
 *  android.widget.TextView
 */
package androidx.appcompat.app;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewStub;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.CursorAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.app.AppCompatDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.core.view.ViewCompat;
import androidx.core.widget.NestedScrollView;
import java.lang.ref.WeakReference;

class AlertController {
    ListAdapter mAdapter;
    private int mAlertDialogLayout;
    private final View.OnClickListener mButtonHandler = new View.OnClickListener(){

        public void onClick(View object) {
            if ((object = object == AlertController.this.mButtonPositive && AlertController.this.mButtonPositiveMessage != null ? Message.obtain((Message)AlertController.this.mButtonPositiveMessage) : (object == AlertController.this.mButtonNegative && AlertController.this.mButtonNegativeMessage != null ? Message.obtain((Message)AlertController.this.mButtonNegativeMessage) : (object == AlertController.this.mButtonNeutral && AlertController.this.mButtonNeutralMessage != null ? Message.obtain((Message)AlertController.this.mButtonNeutralMessage) : null))) != null) {
                object.sendToTarget();
            }
            AlertController.this.mHandler.obtainMessage(1, (Object)AlertController.this.mDialog).sendToTarget();
        }
    };
    private final int mButtonIconDimen;
    Button mButtonNegative;
    private Drawable mButtonNegativeIcon;
    Message mButtonNegativeMessage;
    private CharSequence mButtonNegativeText;
    Button mButtonNeutral;
    private Drawable mButtonNeutralIcon;
    Message mButtonNeutralMessage;
    private CharSequence mButtonNeutralText;
    private int mButtonPanelLayoutHint = 0;
    private int mButtonPanelSideLayout;
    Button mButtonPositive;
    private Drawable mButtonPositiveIcon;
    Message mButtonPositiveMessage;
    private CharSequence mButtonPositiveText;
    int mCheckedItem = -1;
    private final Context mContext;
    private View mCustomTitleView;
    final AppCompatDialog mDialog;
    Handler mHandler;
    private Drawable mIcon;
    private int mIconId = 0;
    private ImageView mIconView;
    int mListItemLayout;
    int mListLayout;
    ListView mListView;
    private CharSequence mMessage;
    private TextView mMessageView;
    int mMultiChoiceItemLayout;
    NestedScrollView mScrollView;
    private boolean mShowTitle;
    int mSingleChoiceItemLayout;
    private CharSequence mTitle;
    private TextView mTitleView;
    private View mView;
    private int mViewLayoutResId;
    private int mViewSpacingBottom;
    private int mViewSpacingLeft;
    private int mViewSpacingRight;
    private boolean mViewSpacingSpecified = false;
    private int mViewSpacingTop;
    private final Window mWindow;

    public AlertController(Context context, AppCompatDialog appCompatDialog, Window window) {
        this.mContext = context;
        this.mDialog = appCompatDialog;
        this.mWindow = window;
        this.mHandler = new ButtonHandler((DialogInterface)appCompatDialog);
        context = context.obtainStyledAttributes(null, R.styleable.AlertDialog, R.attr.alertDialogStyle, 0);
        this.mAlertDialogLayout = context.getResourceId(R.styleable.AlertDialog_android_layout, 0);
        this.mButtonPanelSideLayout = context.getResourceId(R.styleable.AlertDialog_buttonPanelSideLayout, 0);
        this.mListLayout = context.getResourceId(R.styleable.AlertDialog_listLayout, 0);
        this.mMultiChoiceItemLayout = context.getResourceId(R.styleable.AlertDialog_multiChoiceItemLayout, 0);
        this.mSingleChoiceItemLayout = context.getResourceId(R.styleable.AlertDialog_singleChoiceItemLayout, 0);
        this.mListItemLayout = context.getResourceId(R.styleable.AlertDialog_listItemLayout, 0);
        this.mShowTitle = context.getBoolean(R.styleable.AlertDialog_showTitle, true);
        this.mButtonIconDimen = context.getDimensionPixelSize(R.styleable.AlertDialog_buttonIconDimen, 0);
        context.recycle();
        appCompatDialog.supportRequestWindowFeature(1);
    }

    static boolean canTextInput(View view) {
        int n;
        if (view.onCheckIsTextEditor()) {
            return true;
        }
        if (!(view instanceof ViewGroup)) {
            return false;
        }
        view = (ViewGroup)view;
        int n2 = view.getChildCount();
        do {
            if (n2 <= 0) return false;
            n2 = n = n2 - 1;
        } while (!AlertController.canTextInput(view.getChildAt(n)));
        return true;
    }

    private void centerButton(Button button) {
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)button.getLayoutParams();
        layoutParams.gravity = 1;
        layoutParams.weight = 0.5f;
        button.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
    }

    static void manageScrollIndicators(View view, View view2, View view3) {
        int n;
        int n2 = 0;
        if (view2 != null) {
            n = view.canScrollVertically(-1) ? 0 : 4;
            view2.setVisibility(n);
        }
        if (view3 == null) return;
        n = view.canScrollVertically(1) ? n2 : 4;
        view3.setVisibility(n);
    }

    private ViewGroup resolvePanel(View view, View view2) {
        ViewParent viewParent;
        if (view == null) {
            view = view2;
            if (!(view2 instanceof ViewStub)) return (ViewGroup)view;
            view = ((ViewStub)view2).inflate();
            return (ViewGroup)view;
        }
        if (view2 != null && (viewParent = view2.getParent()) instanceof ViewGroup) {
            ((ViewGroup)viewParent).removeView(view2);
        }
        view2 = view;
        if (!(view instanceof ViewStub)) return (ViewGroup)view2;
        view2 = ((ViewStub)view).inflate();
        return (ViewGroup)view2;
    }

    private int selectContentView() {
        int n = this.mButtonPanelSideLayout;
        if (n == 0) {
            return this.mAlertDialogLayout;
        }
        if (this.mButtonPanelLayoutHint != 1) return this.mAlertDialogLayout;
        return n;
    }

    private void setScrollIndicators(ViewGroup viewGroup, final View view, int n, int n2) {
        final View view2 = this.mWindow.findViewById(R.id.scrollIndicatorUp);
        View view3 = this.mWindow.findViewById(R.id.scrollIndicatorDown);
        if (Build.VERSION.SDK_INT >= 23) {
            ViewCompat.setScrollIndicators(view, n, n2);
            if (view2 != null) {
                viewGroup.removeView(view2);
            }
            if (view3 == null) return;
            viewGroup.removeView(view3);
            return;
        }
        view = view2;
        if (view2 != null) {
            view = view2;
            if ((n & 1) == 0) {
                viewGroup.removeView(view2);
                view = null;
            }
        }
        view2 = view3;
        if (view3 != null) {
            view2 = view3;
            if ((n & 2) == 0) {
                viewGroup.removeView(view3);
                view2 = null;
            }
        }
        if (view == null) {
            if (view2 == null) return;
        }
        if (this.mMessage != null) {
            this.mScrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener(){

                @Override
                public void onScrollChange(NestedScrollView nestedScrollView, int n, int n2, int n3, int n4) {
                    AlertController.manageScrollIndicators((View)nestedScrollView, view, view2);
                }
            });
            this.mScrollView.post(new Runnable(){

                @Override
                public void run() {
                    AlertController.manageScrollIndicators((View)AlertController.this.mScrollView, view, view2);
                }
            });
            return;
        }
        view3 = this.mListView;
        if (view3 != null) {
            view3.setOnScrollListener(new AbsListView.OnScrollListener(){

                public void onScroll(AbsListView absListView, int n, int n2, int n3) {
                    AlertController.manageScrollIndicators((View)absListView, view, view2);
                }

                public void onScrollStateChanged(AbsListView absListView, int n) {
                }
            });
            this.mListView.post(new Runnable(){

                @Override
                public void run() {
                    AlertController.manageScrollIndicators((View)AlertController.this.mListView, view, view2);
                }
            });
            return;
        }
        if (view != null) {
            viewGroup.removeView(view);
        }
        if (view2 == null) return;
        viewGroup.removeView(view2);
    }

    private void setupButtons(ViewGroup viewGroup) {
        Button button;
        int n;
        int n2;
        this.mButtonPositive = button = (Button)viewGroup.findViewById(16908313);
        button.setOnClickListener(this.mButtonHandler);
        boolean bl = TextUtils.isEmpty((CharSequence)this.mButtonPositiveText);
        int n3 = 1;
        if (bl && this.mButtonPositiveIcon == null) {
            this.mButtonPositive.setVisibility(8);
            n2 = 0;
        } else {
            this.mButtonPositive.setText(this.mButtonPositiveText);
            button = this.mButtonPositiveIcon;
            if (button != null) {
                n2 = this.mButtonIconDimen;
                button.setBounds(0, 0, n2, n2);
                this.mButtonPositive.setCompoundDrawables(this.mButtonPositiveIcon, null, null, null);
            }
            this.mButtonPositive.setVisibility(0);
            n2 = 1;
        }
        this.mButtonNegative = button = (Button)viewGroup.findViewById(16908314);
        button.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty((CharSequence)this.mButtonNegativeText) && this.mButtonNegativeIcon == null) {
            this.mButtonNegative.setVisibility(8);
        } else {
            this.mButtonNegative.setText(this.mButtonNegativeText);
            button = this.mButtonNegativeIcon;
            if (button != null) {
                n = this.mButtonIconDimen;
                button.setBounds(0, 0, n, n);
                this.mButtonNegative.setCompoundDrawables(this.mButtonNegativeIcon, null, null, null);
            }
            this.mButtonNegative.setVisibility(0);
            n2 |= 2;
        }
        this.mButtonNeutral = button = (Button)viewGroup.findViewById(16908315);
        button.setOnClickListener(this.mButtonHandler);
        if (TextUtils.isEmpty((CharSequence)this.mButtonNeutralText) && this.mButtonNeutralIcon == null) {
            this.mButtonNeutral.setVisibility(8);
        } else {
            this.mButtonNeutral.setText(this.mButtonNeutralText);
            button = this.mButtonNeutralIcon;
            if (button != null) {
                n = this.mButtonIconDimen;
                button.setBounds(0, 0, n, n);
                this.mButtonNeutral.setCompoundDrawables(this.mButtonNeutralIcon, null, null, null);
            }
            this.mButtonNeutral.setVisibility(0);
            n2 |= 4;
        }
        if (AlertController.shouldCenterSingleButton(this.mContext)) {
            if (n2 == 1) {
                this.centerButton(this.mButtonPositive);
            } else if (n2 == 2) {
                this.centerButton(this.mButtonNegative);
            } else if (n2 == 4) {
                this.centerButton(this.mButtonNeutral);
            }
        }
        n2 = n2 != 0 ? n3 : 0;
        if (n2 != 0) return;
        viewGroup.setVisibility(8);
    }

    private void setupContent(ViewGroup viewGroup) {
        TextView textView;
        Object object = (NestedScrollView)this.mWindow.findViewById(R.id.scrollView);
        this.mScrollView = object;
        object.setFocusable(false);
        this.mScrollView.setNestedScrollingEnabled(false);
        this.mMessageView = textView = (TextView)viewGroup.findViewById(16908299);
        if (textView == null) {
            return;
        }
        object = this.mMessage;
        if (object != null) {
            textView.setText((CharSequence)object);
            return;
        }
        textView.setVisibility(8);
        this.mScrollView.removeView((View)this.mMessageView);
        if (this.mListView != null) {
            viewGroup = (ViewGroup)this.mScrollView.getParent();
            int n = viewGroup.indexOfChild((View)this.mScrollView);
            viewGroup.removeViewAt(n);
            viewGroup.addView((View)this.mListView, n, new ViewGroup.LayoutParams(-1, -1));
            return;
        }
        viewGroup.setVisibility(8);
    }

    private void setupCustomContent(ViewGroup viewGroup) {
        Object object = this.mView;
        boolean bl = false;
        if (object == null) {
            object = this.mViewLayoutResId != 0 ? LayoutInflater.from((Context)this.mContext).inflate(this.mViewLayoutResId, viewGroup, false) : null;
        }
        if (object != null) {
            bl = true;
        }
        if (!bl || !AlertController.canTextInput(object)) {
            this.mWindow.setFlags(131072, 131072);
        }
        if (!bl) {
            viewGroup.setVisibility(8);
            return;
        }
        FrameLayout frameLayout = (FrameLayout)this.mWindow.findViewById(R.id.custom);
        frameLayout.addView(object, new ViewGroup.LayoutParams(-1, -1));
        if (this.mViewSpacingSpecified) {
            frameLayout.setPadding(this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
        }
        if (this.mListView == null) return;
        ((LinearLayoutCompat.LayoutParams)viewGroup.getLayoutParams()).weight = 0.0f;
    }

    private void setupTitle(ViewGroup viewGroup) {
        if (this.mCustomTitleView != null) {
            ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(-1, -2);
            viewGroup.addView(this.mCustomTitleView, 0, layoutParams);
            this.mWindow.findViewById(R.id.title_template).setVisibility(8);
            return;
        }
        this.mIconView = (ImageView)this.mWindow.findViewById(16908294);
        if (TextUtils.isEmpty((CharSequence)this.mTitle) ^ true && this.mShowTitle) {
            viewGroup = (TextView)this.mWindow.findViewById(R.id.alertTitle);
            this.mTitleView = viewGroup;
            viewGroup.setText(this.mTitle);
            int n = this.mIconId;
            if (n != 0) {
                this.mIconView.setImageResource(n);
                return;
            }
            viewGroup = this.mIcon;
            if (viewGroup != null) {
                this.mIconView.setImageDrawable((Drawable)viewGroup);
                return;
            }
            this.mTitleView.setPadding(this.mIconView.getPaddingLeft(), this.mIconView.getPaddingTop(), this.mIconView.getPaddingRight(), this.mIconView.getPaddingBottom());
            this.mIconView.setVisibility(8);
            return;
        }
        this.mWindow.findViewById(R.id.title_template).setVisibility(8);
        this.mIconView.setVisibility(8);
        viewGroup.setVisibility(8);
    }

    private void setupView() {
        View view = this.mWindow.findViewById(R.id.parentPanel);
        View view2 = view.findViewById(R.id.topPanel);
        View view3 = view.findViewById(R.id.contentPanel);
        Object object = view.findViewById(R.id.buttonPanel);
        view = (ViewGroup)view.findViewById(R.id.customPanel);
        this.setupCustomContent((ViewGroup)view);
        View view4 = view.findViewById(R.id.topPanel);
        View view5 = view.findViewById(R.id.contentPanel);
        View view6 = view.findViewById(R.id.buttonPanel);
        view2 = this.resolvePanel(view4, view2);
        view3 = this.resolvePanel(view5, view3);
        object = this.resolvePanel(view6, (View)object);
        this.setupContent((ViewGroup)view3);
        this.setupButtons((ViewGroup)object);
        this.setupTitle((ViewGroup)view2);
        int n = 0;
        int n2 = view != null && view.getVisibility() != 8 ? 1 : 0;
        int n3 = view2 != null && view2.getVisibility() != 8 ? 1 : 0;
        boolean bl = object != null && object.getVisibility() != 8;
        if (!bl && view3 != null && (object = view3.findViewById(R.id.textSpacerNoButtons)) != null) {
            object.setVisibility(0);
        }
        if (n3 != 0) {
            object = this.mScrollView;
            if (object != null) {
                object.setClipToPadding(true);
            }
            object = null;
            if (this.mMessage != null || this.mListView != null) {
                object = view2.findViewById(R.id.titleDividerNoCustom);
            }
            if (object != null) {
                object.setVisibility(0);
            }
        } else if (view3 != null && (object = view3.findViewById(R.id.textSpacerNoTitle)) != null) {
            object.setVisibility(0);
        }
        if ((object = this.mListView) instanceof RecycleListView) {
            ((RecycleListView)((Object)object)).setHasDecor(n3 != 0, bl);
        }
        if (n2 == 0) {
            object = this.mListView;
            if (object == null) {
                object = this.mScrollView;
            }
            if (object != null) {
                n2 = n;
                if (bl) {
                    n2 = 2;
                }
                this.setScrollIndicators((ViewGroup)view3, (View)object, n3 | n2, 3);
            }
        }
        if ((view3 = this.mListView) == null) return;
        object = this.mAdapter;
        if (object == null) return;
        view3.setAdapter((ListAdapter)object);
        n2 = this.mCheckedItem;
        if (n2 <= -1) return;
        view3.setItemChecked(n2, true);
        view3.setSelection(n2);
    }

    private static boolean shouldCenterSingleButton(Context context) {
        TypedValue typedValue = new TypedValue();
        context = context.getTheme();
        int n = R.attr.alertDialogCenterButtons;
        boolean bl = true;
        context.resolveAttribute(n, typedValue, true);
        if (typedValue.data == 0) return false;
        return bl;
    }

    public Button getButton(int n) {
        if (n == -3) return this.mButtonNeutral;
        if (n == -2) return this.mButtonNegative;
        if (n == -1) return this.mButtonPositive;
        return null;
    }

    public int getIconAttributeResId(int n) {
        TypedValue typedValue = new TypedValue();
        this.mContext.getTheme().resolveAttribute(n, typedValue, true);
        return typedValue.resourceId;
    }

    public ListView getListView() {
        return this.mListView;
    }

    public void installContent() {
        int n = this.selectContentView();
        this.mDialog.setContentView(n);
        this.setupView();
    }

    public boolean onKeyDown(int n, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.mScrollView;
        if (nestedScrollView == null) return false;
        if (!nestedScrollView.executeKeyEvent(keyEvent)) return false;
        return true;
    }

    public boolean onKeyUp(int n, KeyEvent keyEvent) {
        NestedScrollView nestedScrollView = this.mScrollView;
        if (nestedScrollView == null) return false;
        if (!nestedScrollView.executeKeyEvent(keyEvent)) return false;
        return true;
    }

    public void setButton(int n, CharSequence charSequence, DialogInterface.OnClickListener onClickListener, Message message, Drawable drawable2) {
        Message message2 = message;
        if (message == null) {
            message2 = message;
            if (onClickListener != null) {
                message2 = this.mHandler.obtainMessage(n, (Object)onClickListener);
            }
        }
        if (n == -3) {
            this.mButtonNeutralText = charSequence;
            this.mButtonNeutralMessage = message2;
            this.mButtonNeutralIcon = drawable2;
            return;
        }
        if (n != -2) {
            if (n != -1) throw new IllegalArgumentException("Button does not exist");
            this.mButtonPositiveText = charSequence;
            this.mButtonPositiveMessage = message2;
            this.mButtonPositiveIcon = drawable2;
            return;
        }
        this.mButtonNegativeText = charSequence;
        this.mButtonNegativeMessage = message2;
        this.mButtonNegativeIcon = drawable2;
    }

    public void setButtonPanelLayoutHint(int n) {
        this.mButtonPanelLayoutHint = n;
    }

    public void setCustomTitle(View view) {
        this.mCustomTitleView = view;
    }

    public void setIcon(int n) {
        this.mIcon = null;
        this.mIconId = n;
        ImageView imageView = this.mIconView;
        if (imageView == null) return;
        if (n != 0) {
            imageView.setVisibility(0);
            this.mIconView.setImageResource(this.mIconId);
            return;
        }
        imageView.setVisibility(8);
    }

    public void setIcon(Drawable drawable2) {
        this.mIcon = drawable2;
        this.mIconId = 0;
        ImageView imageView = this.mIconView;
        if (imageView == null) return;
        if (drawable2 != null) {
            imageView.setVisibility(0);
            this.mIconView.setImageDrawable(drawable2);
            return;
        }
        imageView.setVisibility(8);
    }

    public void setMessage(CharSequence charSequence) {
        this.mMessage = charSequence;
        TextView textView = this.mMessageView;
        if (textView == null) return;
        textView.setText(charSequence);
    }

    public void setTitle(CharSequence charSequence) {
        this.mTitle = charSequence;
        TextView textView = this.mTitleView;
        if (textView == null) return;
        textView.setText(charSequence);
    }

    public void setView(int n) {
        this.mView = null;
        this.mViewLayoutResId = n;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = false;
    }

    public void setView(View view, int n, int n2, int n3, int n4) {
        this.mView = view;
        this.mViewLayoutResId = 0;
        this.mViewSpacingSpecified = true;
        this.mViewSpacingLeft = n;
        this.mViewSpacingTop = n2;
        this.mViewSpacingRight = n3;
        this.mViewSpacingBottom = n4;
    }

    public static class AlertParams {
        public ListAdapter mAdapter;
        public boolean mCancelable;
        public int mCheckedItem = -1;
        public boolean[] mCheckedItems;
        public final Context mContext;
        public Cursor mCursor;
        public View mCustomTitleView;
        public boolean mForceInverseBackground;
        public Drawable mIcon;
        public int mIconAttrId = 0;
        public int mIconId = 0;
        public final LayoutInflater mInflater;
        public String mIsCheckedColumn;
        public boolean mIsMultiChoice;
        public boolean mIsSingleChoice;
        public CharSequence[] mItems;
        public String mLabelColumn;
        public CharSequence mMessage;
        public Drawable mNegativeButtonIcon;
        public DialogInterface.OnClickListener mNegativeButtonListener;
        public CharSequence mNegativeButtonText;
        public Drawable mNeutralButtonIcon;
        public DialogInterface.OnClickListener mNeutralButtonListener;
        public CharSequence mNeutralButtonText;
        public DialogInterface.OnCancelListener mOnCancelListener;
        public DialogInterface.OnMultiChoiceClickListener mOnCheckboxClickListener;
        public DialogInterface.OnClickListener mOnClickListener;
        public DialogInterface.OnDismissListener mOnDismissListener;
        public AdapterView.OnItemSelectedListener mOnItemSelectedListener;
        public DialogInterface.OnKeyListener mOnKeyListener;
        public OnPrepareListViewListener mOnPrepareListViewListener;
        public Drawable mPositiveButtonIcon;
        public DialogInterface.OnClickListener mPositiveButtonListener;
        public CharSequence mPositiveButtonText;
        public boolean mRecycleOnMeasure = true;
        public CharSequence mTitle;
        public View mView;
        public int mViewLayoutResId;
        public int mViewSpacingBottom;
        public int mViewSpacingLeft;
        public int mViewSpacingRight;
        public boolean mViewSpacingSpecified = false;
        public int mViewSpacingTop;

        public AlertParams(Context context) {
            this.mContext = context;
            this.mCancelable = true;
            this.mInflater = (LayoutInflater)context.getSystemService("layout_inflater");
        }

        private void createListView(final AlertController alertController) {
            Object object;
            final RecycleListView recycleListView = (RecycleListView)this.mInflater.inflate(alertController.mListLayout, null);
            if (this.mIsMultiChoice) {
                object = this.mCursor == null ? new ArrayAdapter<CharSequence>(this.mContext, alertController.mMultiChoiceItemLayout, 16908308, this.mItems){

                    public View getView(int n, View view, ViewGroup viewGroup) {
                        view = super.getView(n, view, viewGroup);
                        if (AlertParams.this.mCheckedItems == null) return view;
                        if (!AlertParams.this.mCheckedItems[n]) return view;
                        recycleListView.setItemChecked(n, true);
                        return view;
                    }
                } : new CursorAdapter(this.mContext, this.mCursor, false){
                    private final int mIsCheckedIndex;
                    private final int mLabelIndex;
                    {
                        super(context, cursor, bl);
                        AlertParams.this = this.getCursor();
                        this.mLabelIndex = AlertParams.this.getColumnIndexOrThrow(AlertParams.this.mLabelColumn);
                        this.mIsCheckedIndex = AlertParams.this.getColumnIndexOrThrow(AlertParams.this.mIsCheckedColumn);
                    }

                    public void bindView(View object, Context context, Cursor cursor) {
                        ((CheckedTextView)object.findViewById(16908308)).setText((CharSequence)cursor.getString(this.mLabelIndex));
                        object = recycleListView;
                        int n = cursor.getPosition();
                        int n2 = cursor.getInt(this.mIsCheckedIndex);
                        boolean bl = true;
                        if (n2 != 1) {
                            bl = false;
                        }
                        object.setItemChecked(n, bl);
                    }

                    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
                        return AlertParams.this.mInflater.inflate(alertController.mMultiChoiceItemLayout, viewGroup, false);
                    }
                };
            } else {
                int n = this.mIsSingleChoice ? alertController.mSingleChoiceItemLayout : alertController.mListItemLayout;
                if (this.mCursor != null) {
                    object = new SimpleCursorAdapter(this.mContext, n, this.mCursor, new String[]{this.mLabelColumn}, new int[]{16908308});
                } else {
                    object = this.mAdapter;
                    if (object == null) {
                        object = new CheckedItemAdapter(this.mContext, n, 16908308, this.mItems);
                    }
                }
            }
            OnPrepareListViewListener onPrepareListViewListener = this.mOnPrepareListViewListener;
            if (onPrepareListViewListener != null) {
                onPrepareListViewListener.onPrepareListView(recycleListView);
            }
            alertController.mAdapter = object;
            alertController.mCheckedItem = this.mCheckedItem;
            if (this.mOnClickListener != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                        AlertParams.this.mOnClickListener.onClick((DialogInterface)alertController.mDialog, n);
                        if (AlertParams.this.mIsSingleChoice) return;
                        alertController.mDialog.dismiss();
                    }
                });
            } else if (this.mOnCheckboxClickListener != null) {
                recycleListView.setOnItemClickListener(new AdapterView.OnItemClickListener(){

                    public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                        if (AlertParams.this.mCheckedItems != null) {
                            AlertParams.this.mCheckedItems[n] = recycleListView.isItemChecked(n);
                        }
                        AlertParams.this.mOnCheckboxClickListener.onClick((DialogInterface)alertController.mDialog, n, recycleListView.isItemChecked(n));
                    }
                });
            }
            object = this.mOnItemSelectedListener;
            if (object != null) {
                recycleListView.setOnItemSelectedListener((AdapterView.OnItemSelectedListener)object);
            }
            if (this.mIsSingleChoice) {
                recycleListView.setChoiceMode(1);
            } else if (this.mIsMultiChoice) {
                recycleListView.setChoiceMode(2);
            }
            alertController.mListView = recycleListView;
        }

        public void apply(AlertController alertController) {
            int n;
            Object object = this.mCustomTitleView;
            if (object != null) {
                alertController.setCustomTitle((View)object);
            } else {
                object = this.mTitle;
                if (object != null) {
                    alertController.setTitle((CharSequence)object);
                }
                if ((object = this.mIcon) != null) {
                    alertController.setIcon((Drawable)object);
                }
                if ((n = this.mIconId) != 0) {
                    alertController.setIcon(n);
                }
                if ((n = this.mIconAttrId) != 0) {
                    alertController.setIcon(alertController.getIconAttributeResId(n));
                }
            }
            object = this.mMessage;
            if (object != null) {
                alertController.setMessage((CharSequence)object);
            }
            if (this.mPositiveButtonText != null || this.mPositiveButtonIcon != null) {
                alertController.setButton(-1, this.mPositiveButtonText, this.mPositiveButtonListener, null, this.mPositiveButtonIcon);
            }
            if (this.mNegativeButtonText != null || this.mNegativeButtonIcon != null) {
                alertController.setButton(-2, this.mNegativeButtonText, this.mNegativeButtonListener, null, this.mNegativeButtonIcon);
            }
            if (this.mNeutralButtonText != null || this.mNeutralButtonIcon != null) {
                alertController.setButton(-3, this.mNeutralButtonText, this.mNeutralButtonListener, null, this.mNeutralButtonIcon);
            }
            if (this.mItems != null || this.mCursor != null || this.mAdapter != null) {
                this.createListView(alertController);
            }
            if ((object = this.mView) == null) {
                n = this.mViewLayoutResId;
                if (n == 0) return;
                alertController.setView(n);
                return;
            }
            if (this.mViewSpacingSpecified) {
                alertController.setView((View)object, this.mViewSpacingLeft, this.mViewSpacingTop, this.mViewSpacingRight, this.mViewSpacingBottom);
                return;
            }
            alertController.setView((View)object);
        }

        public static interface OnPrepareListViewListener {
            public void onPrepareListView(ListView var1);
        }

    }

    private static final class ButtonHandler
    extends Handler {
        private static final int MSG_DISMISS_DIALOG = 1;
        private WeakReference<DialogInterface> mDialog;

        public ButtonHandler(DialogInterface dialogInterface) {
            this.mDialog = new WeakReference<DialogInterface>(dialogInterface);
        }

        public void handleMessage(Message message) {
            int n = message.what;
            if (n != -3 && n != -2 && n != -1) {
                if (n != 1) {
                    return;
                }
                ((DialogInterface)message.obj).dismiss();
                return;
            }
            ((DialogInterface.OnClickListener)message.obj).onClick((DialogInterface)this.mDialog.get(), message.what);
        }
    }

    private static class CheckedItemAdapter
    extends ArrayAdapter<CharSequence> {
        public CheckedItemAdapter(Context context, int n, int n2, CharSequence[] arrcharSequence) {
            super(context, n, n2, (Object[])arrcharSequence);
        }

        public long getItemId(int n) {
            return n;
        }

        public boolean hasStableIds() {
            return true;
        }
    }

    public static class RecycleListView
    extends ListView {
        private final int mPaddingBottomNoButtons;
        private final int mPaddingTopNoTitle;

        public RecycleListView(Context context) {
            this(context, null);
        }

        public RecycleListView(Context context, AttributeSet attributeSet) {
            super(context, attributeSet);
            context = context.obtainStyledAttributes(attributeSet, R.styleable.RecycleListView);
            this.mPaddingBottomNoButtons = context.getDimensionPixelOffset(R.styleable.RecycleListView_paddingBottomNoButtons, -1);
            this.mPaddingTopNoTitle = context.getDimensionPixelOffset(R.styleable.RecycleListView_paddingTopNoTitle, -1);
        }

        public void setHasDecor(boolean bl, boolean bl2) {
            if (bl2) {
                if (bl) return;
            }
            int n = this.getPaddingLeft();
            int n2 = bl ? this.getPaddingTop() : this.mPaddingTopNoTitle;
            int n3 = this.getPaddingRight();
            int n4 = bl2 ? this.getPaddingBottom() : this.mPaddingBottomNoButtons;
            this.setPadding(n, n2, n3, n4);
        }
    }

}


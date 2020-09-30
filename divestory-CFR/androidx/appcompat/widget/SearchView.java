/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.app.SearchableInfo
 *  android.content.ActivityNotFoundException
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Editable
 *  android.text.SpannableStringBuilder
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.text.style.ImageSpan
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.view.KeyEvent
 *  android.view.KeyEvent$DispatcherState
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.TouchDelegate
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.View$OnKeyListener
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.AutoCompleteTextView
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package androidx.appcompat.widget;

import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.view.CollapsibleActionView;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.SuggestionsAdapter;
import androidx.appcompat.widget.TintTypedArray;
import androidx.appcompat.widget.TooltipCompat;
import androidx.appcompat.widget.ViewUtils;
import androidx.core.view.ViewCompat;
import androidx.cursoradapter.widget.CursorAdapter;
import androidx.customview.view.AbsSavedState;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public class SearchView
extends LinearLayoutCompat
implements CollapsibleActionView {
    static final boolean DBG = false;
    private static final String IME_OPTION_NO_MICROPHONE = "nm";
    static final String LOG_TAG = "SearchView";
    static final PreQAutoCompleteTextViewReflector PRE_API_29_HIDDEN_METHOD_INVOKER;
    private Bundle mAppSearchData;
    private boolean mClearingFocus;
    final ImageView mCloseButton;
    private final ImageView mCollapsedIcon;
    private int mCollapsedImeOptions;
    private final CharSequence mDefaultQueryHint;
    private final View mDropDownAnchor;
    private boolean mExpandedInActionView;
    final ImageView mGoButton;
    private boolean mIconified;
    private boolean mIconifiedByDefault;
    private int mMaxWidth;
    private CharSequence mOldQueryText;
    private final View.OnClickListener mOnClickListener = new View.OnClickListener(){

        public void onClick(View view) {
            if (view == SearchView.this.mSearchButton) {
                SearchView.this.onSearchClicked();
                return;
            }
            if (view == SearchView.this.mCloseButton) {
                SearchView.this.onCloseClicked();
                return;
            }
            if (view == SearchView.this.mGoButton) {
                SearchView.this.onSubmitQuery();
                return;
            }
            if (view == SearchView.this.mVoiceButton) {
                SearchView.this.onVoiceClicked();
                return;
            }
            if (view != SearchView.this.mSearchSrcTextView) return;
            SearchView.this.forceSuggestionQuery();
        }
    };
    private OnCloseListener mOnCloseListener;
    private final TextView.OnEditorActionListener mOnEditorActionListener = new TextView.OnEditorActionListener(){

        public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
            SearchView.this.onSubmitQuery();
            return true;
        }
    };
    private final AdapterView.OnItemClickListener mOnItemClickListener = new AdapterView.OnItemClickListener(){

        public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
            SearchView.this.onItemClicked(n, 0, null);
        }
    };
    private final AdapterView.OnItemSelectedListener mOnItemSelectedListener = new AdapterView.OnItemSelectedListener(){

        public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
            SearchView.this.onItemSelected(n);
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private OnQueryTextListener mOnQueryChangeListener;
    View.OnFocusChangeListener mOnQueryTextFocusChangeListener;
    private View.OnClickListener mOnSearchClickListener;
    private OnSuggestionListener mOnSuggestionListener;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache = new WeakHashMap();
    private CharSequence mQueryHint;
    private boolean mQueryRefinement;
    private Runnable mReleaseCursorRunnable = new Runnable(){

        @Override
        public void run() {
            if (!(SearchView.this.mSuggestionsAdapter instanceof SuggestionsAdapter)) return;
            SearchView.this.mSuggestionsAdapter.changeCursor(null);
        }
    };
    final ImageView mSearchButton;
    private final View mSearchEditFrame;
    private final Drawable mSearchHintIcon;
    private final View mSearchPlate;
    final SearchAutoComplete mSearchSrcTextView;
    private Rect mSearchSrcTextViewBounds = new Rect();
    private Rect mSearchSrtTextViewBoundsExpanded = new Rect();
    SearchableInfo mSearchable;
    private final View mSubmitArea;
    private boolean mSubmitButtonEnabled;
    private final int mSuggestionCommitIconResId;
    private final int mSuggestionRowLayout;
    CursorAdapter mSuggestionsAdapter;
    private int[] mTemp = new int[2];
    private int[] mTemp2 = new int[2];
    View.OnKeyListener mTextKeyListener = new View.OnKeyListener(){

        public boolean onKey(View object, int n, KeyEvent keyEvent) {
            if (SearchView.this.mSearchable == null) {
                return false;
            }
            if (SearchView.this.mSearchSrcTextView.isPopupShowing() && SearchView.this.mSearchSrcTextView.getListSelection() != -1) {
                return SearchView.this.onSuggestionsKey((View)object, n, keyEvent);
            }
            if (SearchView.this.mSearchSrcTextView.isEmpty()) return false;
            if (!keyEvent.hasNoModifiers()) return false;
            if (keyEvent.getAction() != 1) return false;
            if (n != 66) return false;
            object.cancelLongPress();
            object = SearchView.this;
            ((SearchView)object).launchQuerySearch(0, null, ((SearchView)object).mSearchSrcTextView.getText().toString());
            return true;
        }
    };
    private TextWatcher mTextWatcher = new TextWatcher(){

        public void afterTextChanged(Editable editable) {
        }

        public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }

        public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            SearchView.this.onTextChanged(charSequence);
        }
    };
    private UpdatableTouchDelegate mTouchDelegate;
    private final Runnable mUpdateDrawableStateRunnable = new Runnable(){

        @Override
        public void run() {
            SearchView.this.updateFocusedState();
        }
    };
    private CharSequence mUserQuery;
    private final Intent mVoiceAppSearchIntent;
    final ImageView mVoiceButton;
    private boolean mVoiceButtonEnabled;
    private final Intent mVoiceWebSearchIntent;

    static {
        PreQAutoCompleteTextViewReflector preQAutoCompleteTextViewReflector = Build.VERSION.SDK_INT < 29 ? new PreQAutoCompleteTextViewReflector() : null;
        PRE_API_29_HIDDEN_METHOD_INVOKER = preQAutoCompleteTextViewReflector;
    }

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.searchViewStyle);
    }

    public SearchView(Context object, AttributeSet object2, int n) {
        super((Context)object, (AttributeSet)object2, n);
        object2 = TintTypedArray.obtainStyledAttributes((Context)object, (AttributeSet)object2, R.styleable.SearchView, n, 0);
        LayoutInflater.from((Context)object).inflate(((TintTypedArray)object2).getResourceId(R.styleable.SearchView_layout, R.layout.abc_search_view), (ViewGroup)this, true);
        object = (SearchAutoComplete)this.findViewById(R.id.search_src_text);
        this.mSearchSrcTextView = object;
        ((SearchAutoComplete)object).setSearchView(this);
        this.mSearchEditFrame = this.findViewById(R.id.search_edit_frame);
        this.mSearchPlate = this.findViewById(R.id.search_plate);
        this.mSubmitArea = this.findViewById(R.id.submit_area);
        this.mSearchButton = (ImageView)this.findViewById(R.id.search_button);
        this.mGoButton = (ImageView)this.findViewById(R.id.search_go_btn);
        this.mCloseButton = (ImageView)this.findViewById(R.id.search_close_btn);
        this.mVoiceButton = (ImageView)this.findViewById(R.id.search_voice_btn);
        this.mCollapsedIcon = (ImageView)this.findViewById(R.id.search_mag_icon);
        ViewCompat.setBackground(this.mSearchPlate, ((TintTypedArray)object2).getDrawable(R.styleable.SearchView_queryBackground));
        ViewCompat.setBackground(this.mSubmitArea, ((TintTypedArray)object2).getDrawable(R.styleable.SearchView_submitBackground));
        this.mSearchButton.setImageDrawable(((TintTypedArray)object2).getDrawable(R.styleable.SearchView_searchIcon));
        this.mGoButton.setImageDrawable(((TintTypedArray)object2).getDrawable(R.styleable.SearchView_goIcon));
        this.mCloseButton.setImageDrawable(((TintTypedArray)object2).getDrawable(R.styleable.SearchView_closeIcon));
        this.mVoiceButton.setImageDrawable(((TintTypedArray)object2).getDrawable(R.styleable.SearchView_voiceIcon));
        this.mCollapsedIcon.setImageDrawable(((TintTypedArray)object2).getDrawable(R.styleable.SearchView_searchIcon));
        this.mSearchHintIcon = ((TintTypedArray)object2).getDrawable(R.styleable.SearchView_searchHintIcon);
        TooltipCompat.setTooltipText((View)this.mSearchButton, this.getResources().getString(R.string.abc_searchview_description_search));
        this.mSuggestionRowLayout = ((TintTypedArray)object2).getResourceId(R.styleable.SearchView_suggestionRowLayout, R.layout.abc_search_dropdown_item_icons_2line);
        this.mSuggestionCommitIconResId = ((TintTypedArray)object2).getResourceId(R.styleable.SearchView_commitIcon, 0);
        this.mSearchButton.setOnClickListener(this.mOnClickListener);
        this.mCloseButton.setOnClickListener(this.mOnClickListener);
        this.mGoButton.setOnClickListener(this.mOnClickListener);
        this.mVoiceButton.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.addTextChangedListener(this.mTextWatcher);
        this.mSearchSrcTextView.setOnEditorActionListener(this.mOnEditorActionListener);
        this.mSearchSrcTextView.setOnItemClickListener(this.mOnItemClickListener);
        this.mSearchSrcTextView.setOnItemSelectedListener(this.mOnItemSelectedListener);
        this.mSearchSrcTextView.setOnKeyListener(this.mTextKeyListener);
        this.mSearchSrcTextView.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public void onFocusChange(View view, boolean bl) {
                if (SearchView.this.mOnQueryTextFocusChangeListener == null) return;
                SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange((View)SearchView.this, bl);
            }
        });
        this.setIconifiedByDefault(((TintTypedArray)object2).getBoolean(R.styleable.SearchView_iconifiedByDefault, true));
        n = ((TintTypedArray)object2).getDimensionPixelSize(R.styleable.SearchView_android_maxWidth, -1);
        if (n != -1) {
            this.setMaxWidth(n);
        }
        this.mDefaultQueryHint = ((TintTypedArray)object2).getText(R.styleable.SearchView_defaultQueryHint);
        this.mQueryHint = ((TintTypedArray)object2).getText(R.styleable.SearchView_queryHint);
        n = ((TintTypedArray)object2).getInt(R.styleable.SearchView_android_imeOptions, -1);
        if (n != -1) {
            this.setImeOptions(n);
        }
        if ((n = ((TintTypedArray)object2).getInt(R.styleable.SearchView_android_inputType, -1)) != -1) {
            this.setInputType(n);
        }
        this.setFocusable(((TintTypedArray)object2).getBoolean(R.styleable.SearchView_android_focusable, true));
        ((TintTypedArray)object2).recycle();
        object = new Intent("android.speech.action.WEB_SEARCH");
        this.mVoiceWebSearchIntent = object;
        object.addFlags(268435456);
        this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        object = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.mVoiceAppSearchIntent = object;
        object.addFlags(268435456);
        object = this.findViewById(this.mSearchSrcTextView.getDropDownAnchor());
        this.mDropDownAnchor = object;
        if (object != null) {
            object.addOnLayoutChangeListener(new View.OnLayoutChangeListener(){

                public void onLayoutChange(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
                    SearchView.this.adjustDropDownSizeAndPosition();
                }
            });
        }
        this.updateViewsVisibility(this.mIconifiedByDefault);
        this.updateQueryHint();
    }

    private Intent createIntent(String string2, Uri uri, String string3, String string4, int n, String string5) {
        string2 = new Intent(string2);
        string2.addFlags(268435456);
        if (uri != null) {
            string2.setData(uri);
        }
        string2.putExtra("user_query", this.mUserQuery);
        if (string4 != null) {
            string2.putExtra("query", string4);
        }
        if (string3 != null) {
            string2.putExtra("intent_extra_data_key", string3);
        }
        if ((uri = this.mAppSearchData) != null) {
            string2.putExtra("app_data", (Bundle)uri);
        }
        if (n != 0) {
            string2.putExtra("action_key", n);
            string2.putExtra("action_msg", string5);
        }
        string2.setComponent(this.mSearchable.getSearchActivity());
        return string2;
    }

    private Intent createIntentFromSuggestion(Cursor object, int n, String string2) {
        try {
            String string3;
            String string4 = string3 = SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_action");
            if (string3 == null) {
                string4 = this.mSearchable.getSuggestIntentAction();
            }
            string3 = string4;
            if (string4 == null) {
                string3 = "android.intent.action.SEARCH";
            }
            CharSequence charSequence = SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_data");
            string4 = charSequence;
            if (charSequence == null) {
                string4 = this.mSearchable.getSuggestIntentData();
            }
            charSequence = string4;
            if (string4 != null) {
                String string5 = SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_data_id");
                charSequence = string4;
                if (string5 != null) {
                    charSequence = new StringBuilder();
                    ((StringBuilder)charSequence).append(string4);
                    ((StringBuilder)charSequence).append("/");
                    ((StringBuilder)charSequence).append(Uri.encode((String)string5));
                    charSequence = ((StringBuilder)charSequence).toString();
                }
            }
            string4 = charSequence == null ? null : Uri.parse((String)charSequence);
            charSequence = SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_query");
            return this.createIntent(string3, (Uri)string4, SuggestionsAdapter.getColumnString((Cursor)object, "suggest_intent_extra_data"), (String)charSequence, n, string2);
        }
        catch (RuntimeException runtimeException) {
            try {
                n = object.getPosition();
            }
            catch (RuntimeException runtimeException2) {
                n = -1;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Search suggestions cursor at row ");
            ((StringBuilder)object).append(n);
            ((StringBuilder)object).append(" returned exception.");
            Log.w((String)LOG_TAG, (String)((StringBuilder)object).toString(), (Throwable)runtimeException);
            return null;
        }
    }

    private Intent createVoiceAppSearchIntent(Intent object, SearchableInfo searchableInfo) {
        ComponentName componentName = searchableInfo.getSearchActivity();
        Object object2 = new Intent("android.intent.action.SEARCH");
        object2.setComponent(componentName);
        PendingIntent pendingIntent = PendingIntent.getActivity((Context)this.getContext(), (int)0, (Intent)object2, (int)1073741824);
        Bundle bundle = new Bundle();
        object2 = this.mAppSearchData;
        if (object2 != null) {
            bundle.putParcelable("app_data", (Parcelable)object2);
        }
        Intent intent = new Intent(object);
        int n = 1;
        Object object3 = this.getResources();
        object = searchableInfo.getVoiceLanguageModeId() != 0 ? object3.getString(searchableInfo.getVoiceLanguageModeId()) : "free_form";
        int n2 = searchableInfo.getVoicePromptTextId();
        Object var11_11 = null;
        object2 = n2 != 0 ? object3.getString(searchableInfo.getVoicePromptTextId()) : null;
        object3 = searchableInfo.getVoiceLanguageId() != 0 ? object3.getString(searchableInfo.getVoiceLanguageId()) : null;
        if (searchableInfo.getVoiceMaxResults() != 0) {
            n = searchableInfo.getVoiceMaxResults();
        }
        intent.putExtra("android.speech.extra.LANGUAGE_MODEL", (String)object);
        intent.putExtra("android.speech.extra.PROMPT", (String)object2);
        intent.putExtra("android.speech.extra.LANGUAGE", (String)object3);
        intent.putExtra("android.speech.extra.MAX_RESULTS", n);
        object = componentName == null ? var11_11 : componentName.flattenToShortString();
        intent.putExtra("calling_package", (String)object);
        intent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", (Parcelable)pendingIntent);
        intent.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent;
    }

    private Intent createVoiceWebSearchIntent(Intent object, SearchableInfo searchableInfo) {
        Intent intent = new Intent(object);
        object = searchableInfo.getSearchActivity();
        object = object == null ? null : object.flattenToShortString();
        intent.putExtra("calling_package", (String)object);
        return intent;
    }

    private void dismissSuggestions() {
        this.mSearchSrcTextView.dismissDropDown();
    }

    private void getChildBoundsWithinSearchView(View view, Rect rect) {
        view.getLocationInWindow(this.mTemp);
        this.getLocationInWindow(this.mTemp2);
        int[] arrn = this.mTemp;
        int n = arrn[1];
        int[] arrn2 = this.mTemp2;
        int n2 = arrn[0] - arrn2[0];
        rect.set(n2, n -= arrn2[1], view.getWidth() + n2, view.getHeight() + n);
    }

    private CharSequence getDecoratedHint(CharSequence charSequence) {
        if (!this.mIconifiedByDefault) return charSequence;
        if (this.mSearchHintIcon == null) {
            return charSequence;
        }
        int n = (int)((double)this.mSearchSrcTextView.getTextSize() * 1.25);
        this.mSearchHintIcon.setBounds(0, 0, n, n);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder((CharSequence)"   ");
        spannableStringBuilder.setSpan((Object)new ImageSpan(this.mSearchHintIcon), 1, 2, 33);
        spannableStringBuilder.append(charSequence);
        return spannableStringBuilder;
    }

    private int getPreferredHeight() {
        return this.getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_height);
    }

    private int getPreferredWidth() {
        return this.getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_width);
    }

    private boolean hasVoiceSearch() {
        boolean bl;
        SearchableInfo searchableInfo = this.mSearchable;
        boolean bl2 = bl = false;
        if (searchableInfo == null) return bl2;
        bl2 = bl;
        if (!searchableInfo.getVoiceSearchEnabled()) return bl2;
        searchableInfo = null;
        if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
            searchableInfo = this.mVoiceWebSearchIntent;
        } else if (this.mSearchable.getVoiceSearchLaunchRecognizer()) {
            searchableInfo = this.mVoiceAppSearchIntent;
        }
        bl2 = bl;
        if (searchableInfo == null) return bl2;
        bl2 = bl;
        if (this.getContext().getPackageManager().resolveActivity((Intent)searchableInfo, 65536) == null) return bl2;
        return true;
    }

    static boolean isLandscapeMode(Context context) {
        if (context.getResources().getConfiguration().orientation != 2) return false;
        return true;
    }

    private boolean isSubmitAreaEnabled() {
        if (!this.mSubmitButtonEnabled) {
            if (!this.mVoiceButtonEnabled) return false;
        }
        if (this.isIconified()) return false;
        return true;
    }

    private void launchIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        try {
            this.getContext().startActivity(intent);
            return;
        }
        catch (RuntimeException runtimeException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Failed launch activity: ");
            stringBuilder.append((Object)intent);
            Log.e((String)LOG_TAG, (String)stringBuilder.toString(), (Throwable)runtimeException);
        }
    }

    private boolean launchSuggestion(int n, int n2, String string2) {
        Cursor cursor = this.mSuggestionsAdapter.getCursor();
        if (cursor == null) return false;
        if (!cursor.moveToPosition(n)) return false;
        this.launchIntent(this.createIntentFromSuggestion(cursor, n2, string2));
        return true;
    }

    private void postUpdateFocusedState() {
        this.post(this.mUpdateDrawableStateRunnable);
    }

    private void rewriteQueryFromSuggestion(int n) {
        Editable editable = this.mSearchSrcTextView.getText();
        Object object = this.mSuggestionsAdapter.getCursor();
        if (object == null) {
            return;
        }
        if (!object.moveToPosition(n)) {
            this.setQuery((CharSequence)editable);
            return;
        }
        if ((object = this.mSuggestionsAdapter.convertToString((Cursor)object)) != null) {
            this.setQuery((CharSequence)object);
            return;
        }
        this.setQuery((CharSequence)editable);
    }

    private void setQuery(CharSequence charSequence) {
        this.mSearchSrcTextView.setText(charSequence);
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        int n = TextUtils.isEmpty((CharSequence)charSequence) ? 0 : charSequence.length();
        searchAutoComplete.setSelection(n);
    }

    private void updateCloseButton() {
        boolean bl = TextUtils.isEmpty((CharSequence)this.mSearchSrcTextView.getText());
        int n = 1;
        boolean bl2 = bl ^ true;
        int n2 = 0;
        int n3 = n;
        if (!bl2) {
            n3 = this.mIconifiedByDefault && !this.mExpandedInActionView ? n : 0;
        }
        int[] arrn = this.mCloseButton;
        n3 = n3 != 0 ? n2 : 8;
        arrn.setVisibility(n3);
        Drawable drawable2 = this.mCloseButton.getDrawable();
        if (drawable2 == null) return;
        arrn = bl2 ? ENABLED_STATE_SET : EMPTY_STATE_SET;
        drawable2.setState(arrn);
    }

    private void updateQueryHint() {
        CharSequence charSequence = this.getQueryHint();
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = "";
        }
        searchAutoComplete.setHint(this.getDecoratedHint(charSequence2));
    }

    private void updateSearchAutoComplete() {
        this.mSearchSrcTextView.setThreshold(this.mSearchable.getSuggestThreshold());
        this.mSearchSrcTextView.setImeOptions(this.mSearchable.getImeOptions());
        int n = this.mSearchable.getInputType();
        int n2 = 1;
        int n3 = n;
        if ((n & 15) == 1) {
            n3 = n &= -65537;
            if (this.mSearchable.getSuggestAuthority() != null) {
                n3 = n | 65536 | 524288;
            }
        }
        this.mSearchSrcTextView.setInputType(n3);
        CursorAdapter cursorAdapter = this.mSuggestionsAdapter;
        if (cursorAdapter != null) {
            cursorAdapter.changeCursor(null);
        }
        if (this.mSearchable.getSuggestAuthority() == null) return;
        this.mSuggestionsAdapter = cursorAdapter = new SuggestionsAdapter(this.getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
        this.mSearchSrcTextView.setAdapter((ListAdapter)cursorAdapter);
        cursorAdapter = (SuggestionsAdapter)this.mSuggestionsAdapter;
        n3 = n2;
        if (this.mQueryRefinement) {
            n3 = 2;
        }
        ((SuggestionsAdapter)cursorAdapter).setQueryRefinement(n3);
    }

    private void updateSubmitArea() {
        int n = this.isSubmitAreaEnabled() && (this.mGoButton.getVisibility() == 0 || this.mVoiceButton.getVisibility() == 0) ? 0 : 8;
        this.mSubmitArea.setVisibility(n);
    }

    private void updateSubmitButton(boolean bl) {
        int n = this.mSubmitButtonEnabled && this.isSubmitAreaEnabled() && this.hasFocus() && (bl || !this.mVoiceButtonEnabled) ? 0 : 8;
        this.mGoButton.setVisibility(n);
    }

    private void updateViewsVisibility(boolean bl) {
        boolean bl2;
        int n;
        block3 : {
            block2 : {
                this.mIconified = bl;
                int n2 = 0;
                n = bl ? 0 : 8;
                bl2 = TextUtils.isEmpty((CharSequence)this.mSearchSrcTextView.getText()) ^ true;
                this.mSearchButton.setVisibility(n);
                this.updateSubmitButton(bl2);
                View view = this.mSearchEditFrame;
                n = bl ? 8 : 0;
                view.setVisibility(n);
                if (this.mCollapsedIcon.getDrawable() == null) break block2;
                n = n2;
                if (!this.mIconifiedByDefault) break block3;
            }
            n = 8;
        }
        this.mCollapsedIcon.setVisibility(n);
        this.updateCloseButton();
        this.updateVoiceButton(bl2 ^ true);
        this.updateSubmitArea();
    }

    private void updateVoiceButton(boolean bl) {
        int n;
        boolean bl2 = this.mVoiceButtonEnabled;
        int n2 = n = 8;
        if (bl2) {
            n2 = n;
            if (!this.isIconified()) {
                n2 = n;
                if (bl) {
                    this.mGoButton.setVisibility(8);
                    n2 = 0;
                }
            }
        }
        this.mVoiceButton.setVisibility(n2);
    }

    void adjustDropDownSizeAndPosition() {
        if (this.mDropDownAnchor.getWidth() <= 1) return;
        Resources resources = this.getContext().getResources();
        int n = this.mSearchPlate.getPaddingLeft();
        Rect rect = new Rect();
        boolean bl = ViewUtils.isLayoutRtl((View)this);
        int n2 = this.mIconifiedByDefault ? resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_icon_width) + resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_text_padding_left) : 0;
        this.mSearchSrcTextView.getDropDownBackground().getPadding(rect);
        int n3 = bl ? -rect.left : n - (rect.left + n2);
        this.mSearchSrcTextView.setDropDownHorizontalOffset(n3);
        int n4 = this.mDropDownAnchor.getWidth();
        n3 = rect.left;
        int n5 = rect.right;
        this.mSearchSrcTextView.setDropDownWidth(n4 + n3 + n5 + n2 - n);
    }

    public void clearFocus() {
        this.mClearingFocus = true;
        super.clearFocus();
        this.mSearchSrcTextView.clearFocus();
        this.mSearchSrcTextView.setImeVisibility(false);
        this.mClearingFocus = false;
    }

    void forceSuggestionQuery() {
        if (Build.VERSION.SDK_INT >= 29) {
            this.mSearchSrcTextView.refreshAutoCompleteResults();
            return;
        }
        PRE_API_29_HIDDEN_METHOD_INVOKER.doBeforeTextChanged(this.mSearchSrcTextView);
        PRE_API_29_HIDDEN_METHOD_INVOKER.doAfterTextChanged(this.mSearchSrcTextView);
    }

    public int getImeOptions() {
        return this.mSearchSrcTextView.getImeOptions();
    }

    public int getInputType() {
        return this.mSearchSrcTextView.getInputType();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public CharSequence getQuery() {
        return this.mSearchSrcTextView.getText();
    }

    public CharSequence getQueryHint() {
        CharSequence charSequence = this.mQueryHint;
        if (charSequence != null) {
            return charSequence;
        }
        charSequence = this.mSearchable;
        if (charSequence == null) return this.mDefaultQueryHint;
        if (charSequence.getHintId() == 0) return this.mDefaultQueryHint;
        return this.getContext().getText(this.mSearchable.getHintId());
    }

    int getSuggestionCommitIconResId() {
        return this.mSuggestionCommitIconResId;
    }

    int getSuggestionRowLayout() {
        return this.mSuggestionRowLayout;
    }

    public CursorAdapter getSuggestionsAdapter() {
        return this.mSuggestionsAdapter;
    }

    public boolean isIconfiedByDefault() {
        return this.mIconifiedByDefault;
    }

    public boolean isIconified() {
        return this.mIconified;
    }

    public boolean isQueryRefinementEnabled() {
        return this.mQueryRefinement;
    }

    public boolean isSubmitButtonEnabled() {
        return this.mSubmitButtonEnabled;
    }

    void launchQuerySearch(int n, String string2, String string3) {
        string2 = this.createIntent("android.intent.action.SEARCH", null, null, string3, n, string2);
        this.getContext().startActivity((Intent)string2);
    }

    @Override
    public void onActionViewCollapsed() {
        this.setQuery("", false);
        this.clearFocus();
        this.updateViewsVisibility(true);
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions);
        this.mExpandedInActionView = false;
    }

    @Override
    public void onActionViewExpanded() {
        int n;
        if (this.mExpandedInActionView) {
            return;
        }
        this.mExpandedInActionView = true;
        this.mCollapsedImeOptions = n = this.mSearchSrcTextView.getImeOptions();
        this.mSearchSrcTextView.setImeOptions(n | 33554432);
        this.mSearchSrcTextView.setText((CharSequence)"");
        this.setIconified(false);
    }

    void onCloseClicked() {
        if (!TextUtils.isEmpty((CharSequence)this.mSearchSrcTextView.getText())) {
            this.mSearchSrcTextView.setText((CharSequence)"");
            this.mSearchSrcTextView.requestFocus();
            this.mSearchSrcTextView.setImeVisibility(true);
            return;
        }
        if (!this.mIconifiedByDefault) return;
        OnCloseListener onCloseListener = this.mOnCloseListener;
        if (onCloseListener != null) {
            if (onCloseListener.onClose()) return;
        }
        this.clearFocus();
        this.updateViewsVisibility(true);
    }

    protected void onDetachedFromWindow() {
        this.removeCallbacks(this.mUpdateDrawableStateRunnable);
        this.post(this.mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    boolean onItemClicked(int n, int n2, String object) {
        object = this.mOnSuggestionListener;
        if (object != null) {
            if (object.onSuggestionClick(n)) return false;
        }
        this.launchSuggestion(n, 0, null);
        this.mSearchSrcTextView.setImeVisibility(false);
        this.dismissSuggestions();
        return true;
    }

    boolean onItemSelected(int n) {
        OnSuggestionListener onSuggestionListener = this.mOnSuggestionListener;
        if (onSuggestionListener != null) {
            if (onSuggestionListener.onSuggestionSelect(n)) return false;
        }
        this.rewriteQueryFromSuggestion(n);
        return true;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        super.onLayout(bl, n, n2, n3, n4);
        if (!bl) return;
        this.getChildBoundsWithinSearchView((View)this.mSearchSrcTextView, this.mSearchSrcTextViewBounds);
        this.mSearchSrtTextViewBoundsExpanded.set(this.mSearchSrcTextViewBounds.left, 0, this.mSearchSrcTextViewBounds.right, n4 - n2);
        UpdatableTouchDelegate updatableTouchDelegate = this.mTouchDelegate;
        if (updatableTouchDelegate == null) {
            this.mTouchDelegate = updatableTouchDelegate = new UpdatableTouchDelegate(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds, (View)this.mSearchSrcTextView);
            this.setTouchDelegate((TouchDelegate)updatableTouchDelegate);
            return;
        }
        updatableTouchDelegate.setBounds(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds);
    }

    @Override
    protected void onMeasure(int n, int n2) {
        if (this.isIconified()) {
            super.onMeasure(n, n2);
            return;
        }
        int n3 = View.MeasureSpec.getMode((int)n);
        int n4 = View.MeasureSpec.getSize((int)n);
        if (n3 != Integer.MIN_VALUE) {
            if (n3 != 0) {
                if (n3 != 1073741824) {
                    n = n4;
                } else {
                    n3 = this.mMaxWidth;
                    n = n4;
                    if (n3 > 0) {
                        n = Math.min(n3, n4);
                    }
                }
            } else {
                n = this.mMaxWidth;
                if (n <= 0) {
                    n = this.getPreferredWidth();
                }
            }
        } else {
            n = this.mMaxWidth;
            n = n > 0 ? Math.min(n, n4) : Math.min(this.getPreferredWidth(), n4);
        }
        n4 = View.MeasureSpec.getMode((int)n2);
        n2 = View.MeasureSpec.getSize((int)n2);
        if (n4 != Integer.MIN_VALUE) {
            if (n4 == 0) {
                n2 = this.getPreferredHeight();
            }
        } else {
            n2 = Math.min(this.getPreferredHeight(), n2);
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)n, (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824));
    }

    void onQueryRefine(CharSequence charSequence) {
        this.setQuery(charSequence);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.updateViewsVisibility(parcelable.isIconified);
        this.requestLayout();
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isIconified = this.isIconified();
        return savedState;
    }

    void onSearchClicked() {
        this.updateViewsVisibility(false);
        this.mSearchSrcTextView.requestFocus();
        this.mSearchSrcTextView.setImeVisibility(true);
        View.OnClickListener onClickListener = this.mOnSearchClickListener;
        if (onClickListener == null) return;
        onClickListener.onClick((View)this);
    }

    void onSubmitQuery() {
        Editable editable = this.mSearchSrcTextView.getText();
        if (editable == null) return;
        if (TextUtils.getTrimmedLength((CharSequence)editable) <= 0) return;
        OnQueryTextListener onQueryTextListener = this.mOnQueryChangeListener;
        if (onQueryTextListener != null) {
            if (onQueryTextListener.onQueryTextSubmit(editable.toString())) return;
        }
        if (this.mSearchable != null) {
            this.launchQuerySearch(0, null, editable.toString());
        }
        this.mSearchSrcTextView.setImeVisibility(false);
        this.dismissSuggestions();
    }

    boolean onSuggestionsKey(View view, int n, KeyEvent keyEvent) {
        if (this.mSearchable == null) {
            return false;
        }
        if (this.mSuggestionsAdapter == null) {
            return false;
        }
        if (keyEvent.getAction() != 0) return false;
        if (!keyEvent.hasNoModifiers()) return false;
        if (n == 66) return this.onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null);
        if (n == 84) return this.onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null);
        if (n == 61) {
            return this.onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null);
        }
        if (n != 21 && n != 22) {
            if (n != 19) return false;
            if (this.mSearchSrcTextView.getListSelection() != 0) return false;
            return false;
        }
        n = n == 21 ? 0 : this.mSearchSrcTextView.length();
        this.mSearchSrcTextView.setSelection(n);
        this.mSearchSrcTextView.setListSelection(0);
        this.mSearchSrcTextView.clearListSelection();
        this.mSearchSrcTextView.ensureImeVisible();
        return true;
    }

    void onTextChanged(CharSequence charSequence) {
        Editable editable = this.mSearchSrcTextView.getText();
        this.mUserQuery = editable;
        boolean bl = TextUtils.isEmpty((CharSequence)editable) ^ true;
        this.updateSubmitButton(bl);
        this.updateVoiceButton(bl ^ true);
        this.updateCloseButton();
        this.updateSubmitArea();
        if (this.mOnQueryChangeListener != null && !TextUtils.equals((CharSequence)charSequence, (CharSequence)this.mOldQueryText)) {
            this.mOnQueryChangeListener.onQueryTextChange(charSequence.toString());
        }
        this.mOldQueryText = charSequence.toString();
    }

    void onTextFocusChanged() {
        this.updateViewsVisibility(this.isIconified());
        this.postUpdateFocusedState();
        if (!this.mSearchSrcTextView.hasFocus()) return;
        this.forceSuggestionQuery();
    }

    void onVoiceClicked() {
        SearchableInfo searchableInfo = this.mSearchable;
        if (searchableInfo == null) {
            return;
        }
        try {
            if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                searchableInfo = this.createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, searchableInfo);
                this.getContext().startActivity((Intent)searchableInfo);
                return;
            }
            if (!searchableInfo.getVoiceSearchLaunchRecognizer()) return;
            searchableInfo = this.createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, searchableInfo);
            this.getContext().startActivity((Intent)searchableInfo);
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            Log.w((String)LOG_TAG, (String)"Could not find voice search activity");
        }
    }

    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        this.postUpdateFocusedState();
    }

    public boolean requestFocus(int n, Rect rect) {
        if (this.mClearingFocus) {
            return false;
        }
        if (!this.isFocusable()) {
            return false;
        }
        if (this.isIconified()) return super.requestFocus(n, rect);
        boolean bl = this.mSearchSrcTextView.requestFocus(n, rect);
        if (!bl) return bl;
        this.updateViewsVisibility(false);
        return bl;
    }

    public void setAppSearchData(Bundle bundle) {
        this.mAppSearchData = bundle;
    }

    public void setIconified(boolean bl) {
        if (bl) {
            this.onCloseClicked();
            return;
        }
        this.onSearchClicked();
    }

    public void setIconifiedByDefault(boolean bl) {
        if (this.mIconifiedByDefault == bl) {
            return;
        }
        this.mIconifiedByDefault = bl;
        this.updateViewsVisibility(bl);
        this.updateQueryHint();
    }

    public void setImeOptions(int n) {
        this.mSearchSrcTextView.setImeOptions(n);
    }

    public void setInputType(int n) {
        this.mSearchSrcTextView.setInputType(n);
    }

    public void setMaxWidth(int n) {
        this.mMaxWidth = n;
        this.requestLayout();
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.mOnCloseListener = onCloseListener;
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.mOnQueryTextFocusChangeListener = onFocusChangeListener;
    }

    public void setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
        this.mOnQueryChangeListener = onQueryTextListener;
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.mOnSearchClickListener = onClickListener;
    }

    public void setOnSuggestionListener(OnSuggestionListener onSuggestionListener) {
        this.mOnSuggestionListener = onSuggestionListener;
    }

    public void setQuery(CharSequence charSequence, boolean bl) {
        this.mSearchSrcTextView.setText(charSequence);
        if (charSequence != null) {
            SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
            searchAutoComplete.setSelection(searchAutoComplete.length());
            this.mUserQuery = charSequence;
        }
        if (!bl) return;
        if (TextUtils.isEmpty((CharSequence)charSequence)) return;
        this.onSubmitQuery();
    }

    public void setQueryHint(CharSequence charSequence) {
        this.mQueryHint = charSequence;
        this.updateQueryHint();
    }

    public void setQueryRefinementEnabled(boolean bl) {
        this.mQueryRefinement = bl;
        CursorAdapter cursorAdapter = this.mSuggestionsAdapter;
        if (!(cursorAdapter instanceof SuggestionsAdapter)) return;
        cursorAdapter = (SuggestionsAdapter)cursorAdapter;
        int n = bl ? 2 : 1;
        ((SuggestionsAdapter)cursorAdapter).setQueryRefinement(n);
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        boolean bl;
        this.mSearchable = searchableInfo;
        if (searchableInfo != null) {
            this.updateSearchAutoComplete();
            this.updateQueryHint();
        }
        this.mVoiceButtonEnabled = bl = this.hasVoiceSearch();
        if (bl) {
            this.mSearchSrcTextView.setPrivateImeOptions(IME_OPTION_NO_MICROPHONE);
        }
        this.updateViewsVisibility(this.isIconified());
    }

    public void setSubmitButtonEnabled(boolean bl) {
        this.mSubmitButtonEnabled = bl;
        this.updateViewsVisibility(this.isIconified());
    }

    public void setSuggestionsAdapter(CursorAdapter cursorAdapter) {
        this.mSuggestionsAdapter = cursorAdapter;
        this.mSearchSrcTextView.setAdapter((ListAdapter)cursorAdapter);
    }

    void updateFocusedState() {
        int[] arrn = this.mSearchSrcTextView.hasFocus() ? FOCUSED_STATE_SET : EMPTY_STATE_SET;
        Drawable drawable2 = this.mSearchPlate.getBackground();
        if (drawable2 != null) {
            drawable2.setState(arrn);
        }
        if ((drawable2 = this.mSubmitArea.getBackground()) != null) {
            drawable2.setState(arrn);
        }
        this.invalidate();
    }

    public static interface OnCloseListener {
        public boolean onClose();
    }

    public static interface OnQueryTextListener {
        public boolean onQueryTextChange(String var1);

        public boolean onQueryTextSubmit(String var1);
    }

    public static interface OnSuggestionListener {
        public boolean onSuggestionClick(int var1);

        public boolean onSuggestionSelect(int var1);
    }

    private static class PreQAutoCompleteTextViewReflector {
        private Method mDoAfterTextChanged = null;
        private Method mDoBeforeTextChanged = null;
        private Method mEnsureImeVisible = null;

        PreQAutoCompleteTextViewReflector() {
            Method method;
            PreQAutoCompleteTextViewReflector.preApi29Check();
            try {
                this.mDoBeforeTextChanged = method = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                method.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            try {
                this.mDoAfterTextChanged = method = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                method.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            try {
                this.mEnsureImeVisible = method = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
                method.setAccessible(true);
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                return;
            }
        }

        private static void preApi29Check() {
            if (Build.VERSION.SDK_INT >= 29) throw new UnsupportedClassVersionError("This function can only be used for API Level < 29.");
        }

        void doAfterTextChanged(AutoCompleteTextView autoCompleteTextView) {
            PreQAutoCompleteTextViewReflector.preApi29Check();
            Method method = this.mDoAfterTextChanged;
            if (method == null) return;
            try {
                method.invoke((Object)autoCompleteTextView, new Object[0]);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        void doBeforeTextChanged(AutoCompleteTextView autoCompleteTextView) {
            PreQAutoCompleteTextViewReflector.preApi29Check();
            Method method = this.mDoBeforeTextChanged;
            if (method == null) return;
            try {
                method.invoke((Object)autoCompleteTextView, new Object[0]);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        void ensureImeVisible(AutoCompleteTextView autoCompleteTextView) {
            PreQAutoCompleteTextViewReflector.preApi29Check();
            Method method = this.mEnsureImeVisible;
            if (method == null) return;
            try {
                method.invoke((Object)autoCompleteTextView, true);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }
    }

    static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean isIconified;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.isIconified = (Boolean)parcel.readValue(null);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("SearchView.SavedState{");
            stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
            stringBuilder.append(" isIconified=");
            stringBuilder.append(this.isIconified);
            stringBuilder.append("}");
            return stringBuilder.toString();
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeValue((Object)this.isIconified);
        }

    }

    public static class SearchAutoComplete
    extends AppCompatAutoCompleteTextView {
        private boolean mHasPendingShowSoftInputRequest;
        final Runnable mRunShowSoftInputIfNecessary = new Runnable(){

            @Override
            public void run() {
                SearchAutoComplete.this.showSoftInputIfNecessary();
            }
        };
        private SearchView mSearchView;
        private int mThreshold = this.getThreshold();

        public SearchAutoComplete(Context context) {
            this(context, null);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int n) {
            super(context, attributeSet, n);
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = this.getResources().getConfiguration();
            int n = configuration.screenWidthDp;
            int n2 = configuration.screenHeightDp;
            if (n >= 960 && n2 >= 720 && configuration.orientation == 2) {
                return 256;
            }
            if (n >= 600) return 192;
            if (n < 640) return 160;
            if (n2 < 480) return 160;
            return 192;
        }

        public boolean enoughToFilter() {
            if (this.mThreshold <= 0) return true;
            if (super.enoughToFilter()) return true;
            return false;
        }

        void ensureImeVisible() {
            if (Build.VERSION.SDK_INT >= 29) {
                this.setInputMethodMode(1);
                if (!this.enoughToFilter()) return;
                this.showDropDown();
                return;
            }
            PRE_API_29_HIDDEN_METHOD_INVOKER.ensureImeVisible(this);
        }

        boolean isEmpty() {
            if (TextUtils.getTrimmedLength((CharSequence)this.getText()) != 0) return false;
            return true;
        }

        @Override
        public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            editorInfo = super.onCreateInputConnection(editorInfo);
            if (!this.mHasPendingShowSoftInputRequest) return editorInfo;
            this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
            this.post(this.mRunShowSoftInputIfNecessary);
            return editorInfo;
        }

        protected void onFinishInflate() {
            super.onFinishInflate();
            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
            this.setMinWidth((int)TypedValue.applyDimension((int)1, (float)this.getSearchViewTextMinWidthDp(), (DisplayMetrics)displayMetrics));
        }

        protected void onFocusChanged(boolean bl, int n, Rect rect) {
            super.onFocusChanged(bl, n, rect);
            this.mSearchView.onTextFocusChanged();
        }

        public boolean onKeyPreIme(int n, KeyEvent keyEvent) {
            if (n != 4) return super.onKeyPreIme(n, keyEvent);
            if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
                if (dispatcherState == null) return true;
                dispatcherState.startTracking(keyEvent, (Object)this);
                return true;
            }
            if (keyEvent.getAction() != 1) return super.onKeyPreIme(n, keyEvent);
            KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
            if (dispatcherState != null) {
                dispatcherState.handleUpEvent(keyEvent);
            }
            if (!keyEvent.isTracking()) return super.onKeyPreIme(n, keyEvent);
            if (keyEvent.isCanceled()) return super.onKeyPreIme(n, keyEvent);
            this.mSearchView.clearFocus();
            this.setImeVisibility(false);
            return true;
        }

        public void onWindowFocusChanged(boolean bl) {
            super.onWindowFocusChanged(bl);
            if (!bl) return;
            if (!this.mSearchView.hasFocus()) return;
            if (this.getVisibility() != 0) return;
            this.mHasPendingShowSoftInputRequest = true;
            if (!SearchView.isLandscapeMode(this.getContext())) return;
            this.ensureImeVisible();
        }

        public void performCompletion() {
        }

        protected void replaceText(CharSequence charSequence) {
        }

        void setImeVisibility(boolean bl) {
            InputMethodManager inputMethodManager = (InputMethodManager)this.getContext().getSystemService("input_method");
            if (!bl) {
                this.mHasPendingShowSoftInputRequest = false;
                this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
                inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
                return;
            }
            if (inputMethodManager.isActive((View)this)) {
                this.mHasPendingShowSoftInputRequest = false;
                this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
                inputMethodManager.showSoftInput((View)this, 0);
                return;
            }
            this.mHasPendingShowSoftInputRequest = true;
        }

        void setSearchView(SearchView searchView) {
            this.mSearchView = searchView;
        }

        public void setThreshold(int n) {
            super.setThreshold(n);
            this.mThreshold = n;
        }

        void showSoftInputIfNecessary() {
            if (!this.mHasPendingShowSoftInputRequest) return;
            ((InputMethodManager)this.getContext().getSystemService("input_method")).showSoftInput((View)this, 0);
            this.mHasPendingShowSoftInputRequest = false;
        }

    }

    private static class UpdatableTouchDelegate
    extends TouchDelegate {
        private final Rect mActualBounds;
        private boolean mDelegateTargeted;
        private final View mDelegateView;
        private final int mSlop;
        private final Rect mSlopBounds;
        private final Rect mTargetBounds;

        public UpdatableTouchDelegate(Rect rect, Rect rect2, View view) {
            super(rect, view);
            this.mSlop = ViewConfiguration.get((Context)view.getContext()).getScaledTouchSlop();
            this.mTargetBounds = new Rect();
            this.mSlopBounds = new Rect();
            this.mActualBounds = new Rect();
            this.setBounds(rect, rect2);
            this.mDelegateView = view;
        }

        /*
         * Unable to fully structure code
         */
        public boolean onTouchEvent(MotionEvent var1_1) {
            block6 : {
                block4 : {
                    block5 : {
                        var2_2 = (int)var1_1.getX();
                        var3_3 = (int)var1_1.getY();
                        var4_4 = var1_1.getAction();
                        var5_5 = true;
                        var6_6 = false;
                        if (var4_4 == 0) break block4;
                        if (var4_4 == 1 || var4_4 == 2) break block5;
                        if (var4_4 != 3) ** GOTO lbl-1000
                        var5_5 = this.mDelegateTargeted;
                        this.mDelegateTargeted = false;
                        ** GOTO lbl23
                    }
                    var5_5 = var7_7 = this.mDelegateTargeted;
                    if (!var7_7) ** GOTO lbl23
                    var5_5 = var7_7;
                    if (this.mSlopBounds.contains(var2_2, var3_3)) ** GOTO lbl23
                    var5_5 = var7_7;
                    var4_4 = 0;
                    break block6;
                }
                if (this.mTargetBounds.contains(var2_2, var3_3)) {
                    this.mDelegateTargeted = true;
lbl23: // 4 sources:
                    var4_4 = 1;
                } else lbl-1000: // 2 sources:
                {
                    var4_4 = 1;
                    var5_5 = false;
                }
            }
            var7_7 = var6_6;
            if (var5_5 == false) return var7_7;
            if (var4_4 != 0 && !this.mActualBounds.contains(var2_2, var3_3)) {
                var1_1.setLocation((float)(this.mDelegateView.getWidth() / 2), (float)(this.mDelegateView.getHeight() / 2));
                return this.mDelegateView.dispatchTouchEvent(var1_1);
            } else {
                var1_1.setLocation((float)(var2_2 - this.mActualBounds.left), (float)(var3_3 - this.mActualBounds.top));
            }
            return this.mDelegateView.dispatchTouchEvent(var1_1);
        }

        public void setBounds(Rect rect, Rect rect2) {
            this.mTargetBounds.set(rect);
            this.mSlopBounds.set(rect);
            rect = this.mSlopBounds;
            int n = this.mSlop;
            rect.inset(-n, -n);
            this.mActualBounds.set(rect2);
        }
    }

}


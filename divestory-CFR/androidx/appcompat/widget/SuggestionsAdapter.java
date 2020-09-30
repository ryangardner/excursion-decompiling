/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.SearchableInfo
 *  android.content.ComponentName
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.pm.ActivityInfo
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$NotFoundException
 *  android.content.res.Resources$Theme
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Bundle
 *  android.text.SpannableString
 *  android.text.TextUtils
 *  android.text.style.TextAppearanceSpan
 *  android.util.Log
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package androidx.appcompat.widget;

import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.R;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.cursoradapter.widget.ResourceCursorAdapter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.WeakHashMap;

class SuggestionsAdapter
extends ResourceCursorAdapter
implements View.OnClickListener {
    private static final boolean DBG = false;
    static final int INVALID_INDEX = -1;
    private static final String LOG_TAG = "SuggestionsAdapter";
    private static final int QUERY_LIMIT = 50;
    static final int REFINE_ALL = 2;
    static final int REFINE_BY_ENTRY = 1;
    static final int REFINE_NONE = 0;
    private boolean mClosed = false;
    private final int mCommitIconResId;
    private int mFlagsCol = -1;
    private int mIconName1Col = -1;
    private int mIconName2Col = -1;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
    private final Context mProviderContext;
    private int mQueryRefinement = 1;
    private final SearchView mSearchView;
    private final SearchableInfo mSearchable;
    private int mText1Col = -1;
    private int mText2Col = -1;
    private int mText2UrlCol = -1;
    private ColorStateList mUrlColor;

    public SuggestionsAdapter(Context context, SearchView searchView, SearchableInfo searchableInfo, WeakHashMap<String, Drawable.ConstantState> weakHashMap) {
        super(context, searchView.getSuggestionRowLayout(), null, true);
        this.mSearchView = searchView;
        this.mSearchable = searchableInfo;
        this.mCommitIconResId = searchView.getSuggestionCommitIconResId();
        this.mProviderContext = context;
        this.mOutsideDrawablesCache = weakHashMap;
    }

    private Drawable checkIconCache(String string2) {
        if ((string2 = this.mOutsideDrawablesCache.get(string2)) != null) return string2.newDrawable();
        return null;
    }

    private CharSequence formatUrl(CharSequence charSequence) {
        TypedValue typedValue;
        if (this.mUrlColor == null) {
            typedValue = new TypedValue();
            this.mContext.getTheme().resolveAttribute(R.attr.textColorSearchUrl, typedValue, true);
            this.mUrlColor = this.mContext.getResources().getColorStateList(typedValue.resourceId);
        }
        typedValue = new SpannableString(charSequence);
        typedValue.setSpan((Object)new TextAppearanceSpan(null, 0, 0, this.mUrlColor, null), 0, charSequence.length(), 33);
        return typedValue;
    }

    private Drawable getActivityIcon(ComponentName componentName) {
        ActivityInfo activityInfo;
        Object object = this.mContext.getPackageManager();
        try {
            activityInfo = object.getActivityInfo(componentName, 128);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            Log.w((String)LOG_TAG, (String)nameNotFoundException.toString());
            return null;
        }
        int n = activityInfo.getIconResource();
        if (n == 0) {
            return null;
        }
        if ((object = object.getDrawable(componentName.getPackageName(), n, activityInfo.applicationInfo)) != null) return object;
        object = new StringBuilder();
        ((StringBuilder)object).append("Invalid icon resource ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" for ");
        ((StringBuilder)object).append(componentName.flattenToShortString());
        Log.w((String)LOG_TAG, (String)((StringBuilder)object).toString());
        return null;
    }

    private Drawable getActivityIconWithCache(ComponentName object) {
        String string2 = object.flattenToShortString();
        boolean bl = this.mOutsideDrawablesCache.containsKey(string2);
        Object var4_4 = null;
        Drawable drawable2 = null;
        if (bl) {
            object = this.mOutsideDrawablesCache.get(string2);
            if (object != null) return object.newDrawable(this.mProviderContext.getResources());
            return drawable2;
        }
        drawable2 = this.getActivityIcon((ComponentName)object);
        object = drawable2 == null ? var4_4 : drawable2.getConstantState();
        this.mOutsideDrawablesCache.put(string2, (Drawable.ConstantState)object);
        return drawable2;
    }

    public static String getColumnString(Cursor cursor, String string2) {
        return SuggestionsAdapter.getStringOrNull(cursor, cursor.getColumnIndex(string2));
    }

    private Drawable getDefaultIcon1() {
        Drawable drawable2 = this.getActivityIconWithCache(this.mSearchable.getSearchActivity());
        if (drawable2 == null) return this.mContext.getPackageManager().getDefaultActivityIcon();
        return drawable2;
    }

    /*
     * Unable to fully structure code
     * Enabled unnecessary exception pruning
     */
    private Drawable getDrawable(Uri var1_1) {
        try {
            var2_2 = "android.resource".equals(var1_1.getScheme());
            if (var2_2) {
                try {
                    return this.getDrawableFromResourceUri(var1_1);
                }
                catch (Resources.NotFoundException var3_4) {
                    var4_9 = new StringBuilder();
                    var4_9.append("Resource does not exist: ");
                    var4_9.append((Object)var1_1);
                    var3_5 = new FileNotFoundException(var4_9.toString());
                    throw var3_5;
                }
            }
            var4_10 = this.mProviderContext.getContentResolver().openInputStream(var1_1);
            if (var4_10 == null) ** GOTO lbl57
        }
        catch (FileNotFoundException var4_13) {
            var3_6 = new StringBuilder();
            var3_6.append("Icon not found: ");
            var3_6.append((Object)var1_1);
            var3_6.append(", ");
            var3_6.append(var4_13.getMessage());
            Log.w((String)"SuggestionsAdapter", (String)var3_6.toString());
            return null;
        }
        var3_6 = Drawable.createFromStream((InputStream)var4_10, null);
        {
            catch (Throwable var3_7) {
                try {
                    var4_10.close();
                    throw var3_7;
                }
                catch (IOException var4_12) {
                    var5_15 = new StringBuilder();
                    var5_15.append("Error closing icon stream for ");
                    var5_15.append((Object)var1_1);
                    Log.e((String)"SuggestionsAdapter", (String)var5_15.toString(), (Throwable)var4_12);
                }
                throw var3_7;
            }
        }
        try {
            var4_10.close();
            return var3_6;
        }
        catch (IOException var4_11) {
            var5_14 = new StringBuilder();
            var5_14.append("Error closing icon stream for ");
            var5_14.append((Object)var1_1);
            Log.e((String)"SuggestionsAdapter", (String)var5_14.toString(), (Throwable)var4_11);
        }
        return var3_6;
lbl57: // 1 sources:
        var3_8 = new StringBuilder();
        var3_8.append("Failed to open ");
        var3_8.append((Object)var1_1);
        var4_10 = new FileNotFoundException(var3_8.toString());
        throw var4_10;
    }

    private Drawable getDrawableFromResourceValue(String string2) {
        StringBuilder stringBuilder;
        CharSequence charSequence = stringBuilder = null;
        if (string2 == null) return charSequence;
        charSequence = stringBuilder;
        if (string2.isEmpty()) return charSequence;
        if ("0".equals(string2)) {
            return stringBuilder;
        }
        try {
            int n = Integer.parseInt(string2);
            charSequence = new StringBuilder();
            charSequence.append("android.resource://");
            charSequence.append(this.mProviderContext.getPackageName());
            charSequence.append("/");
            charSequence.append(n);
            charSequence = charSequence.toString();
            stringBuilder = this.checkIconCache((String)charSequence);
            if (stringBuilder != null) {
                return stringBuilder;
            }
            stringBuilder = ContextCompat.getDrawable(this.mProviderContext, n);
            this.storeInIconCache((String)charSequence, (Drawable)stringBuilder);
            return stringBuilder;
        }
        catch (Resources.NotFoundException notFoundException) {
            StringBuilder stringBuilder2 = new StringBuilder();
            stringBuilder2.append("Icon resource not found: ");
            stringBuilder2.append(string2);
            Log.w((String)LOG_TAG, (String)stringBuilder2.toString());
            return null;
        }
        catch (NumberFormatException numberFormatException) {
            charSequence = this.checkIconCache(string2);
            if (charSequence != null) {
                return charSequence;
            }
            charSequence = this.getDrawable(Uri.parse((String)string2));
            this.storeInIconCache(string2, (Drawable)charSequence);
        }
        return charSequence;
    }

    private Drawable getIcon1(Cursor cursor) {
        int n = this.mIconName1Col;
        if (n == -1) {
            return null;
        }
        if ((cursor = this.getDrawableFromResourceValue(cursor.getString(n))) == null) return this.getDefaultIcon1();
        return cursor;
    }

    private Drawable getIcon2(Cursor cursor) {
        int n = this.mIconName2Col;
        if (n != -1) return this.getDrawableFromResourceValue(cursor.getString(n));
        return null;
    }

    private static String getStringOrNull(Cursor object, int n) {
        if (n == -1) {
            return null;
        }
        try {
            return object.getString(n);
        }
        catch (Exception exception) {
            Log.e((String)LOG_TAG, (String)"unexpected error retrieving valid column from cursor, did the remote process die?", (Throwable)exception);
            return null;
        }
    }

    private void setViewDrawable(ImageView imageView, Drawable drawable2, int n) {
        imageView.setImageDrawable(drawable2);
        if (drawable2 == null) {
            imageView.setVisibility(n);
            return;
        }
        imageView.setVisibility(0);
        drawable2.setVisible(false, false);
        drawable2.setVisible(true, false);
    }

    private void setViewText(TextView textView, CharSequence charSequence) {
        textView.setText(charSequence);
        if (TextUtils.isEmpty((CharSequence)charSequence)) {
            textView.setVisibility(8);
            return;
        }
        textView.setVisibility(0);
    }

    private void storeInIconCache(String string2, Drawable drawable2) {
        if (drawable2 == null) return;
        this.mOutsideDrawablesCache.put(string2, drawable2.getConstantState());
    }

    private void updateSpinnerState(Cursor object) {
        object = object != null ? object.getExtras() : null;
        if (object == null) return;
        if (!object.getBoolean("in_progress")) return;
    }

    @Override
    public void bindView(View object, Context object2, Cursor cursor) {
        int n;
        object2 = (ChildViewCache)object.getTag();
        int n2 = this.mFlagsCol;
        n2 = n2 != -1 ? cursor.getInt(n2) : 0;
        if (object2.mText1 != null) {
            object = SuggestionsAdapter.getStringOrNull(cursor, this.mText1Col);
            this.setViewText(object2.mText1, (CharSequence)object);
        }
        if (object2.mText2 != null) {
            object = SuggestionsAdapter.getStringOrNull(cursor, this.mText2UrlCol);
            object = object != null ? this.formatUrl((CharSequence)object) : SuggestionsAdapter.getStringOrNull(cursor, this.mText2Col);
            if (TextUtils.isEmpty((CharSequence)object)) {
                if (object2.mText1 != null) {
                    object2.mText1.setSingleLine(false);
                    object2.mText1.setMaxLines(2);
                }
            } else if (object2.mText1 != null) {
                object2.mText1.setSingleLine(true);
                object2.mText1.setMaxLines(1);
            }
            this.setViewText(object2.mText2, (CharSequence)object);
        }
        if (object2.mIcon1 != null) {
            this.setViewDrawable(object2.mIcon1, this.getIcon1(cursor), 4);
        }
        if (object2.mIcon2 != null) {
            this.setViewDrawable(object2.mIcon2, this.getIcon2(cursor), 8);
        }
        if ((n = this.mQueryRefinement) != 2 && (n != 1 || (n2 & 1) == 0)) {
            object2.mIconRefine.setVisibility(8);
            return;
        }
        object2.mIconRefine.setVisibility(0);
        object2.mIconRefine.setTag((Object)object2.mText1.getText());
        object2.mIconRefine.setOnClickListener((View.OnClickListener)this);
    }

    @Override
    public void changeCursor(Cursor cursor) {
        if (this.mClosed) {
            Log.w((String)LOG_TAG, (String)"Tried to change cursor after adapter was closed.");
            if (cursor == null) return;
            cursor.close();
            return;
        }
        try {
            super.changeCursor(cursor);
            if (cursor == null) return;
            this.mText1Col = cursor.getColumnIndex("suggest_text_1");
            this.mText2Col = cursor.getColumnIndex("suggest_text_2");
            this.mText2UrlCol = cursor.getColumnIndex("suggest_text_2_url");
            this.mIconName1Col = cursor.getColumnIndex("suggest_icon_1");
            this.mIconName2Col = cursor.getColumnIndex("suggest_icon_2");
            this.mFlagsCol = cursor.getColumnIndex("suggest_flags");
            return;
        }
        catch (Exception exception) {
            Log.e((String)LOG_TAG, (String)"error changing cursor and caching columns", (Throwable)exception);
        }
    }

    public void close() {
        this.changeCursor(null);
        this.mClosed = true;
    }

    @Override
    public CharSequence convertToString(Cursor object) {
        if (object == null) {
            return null;
        }
        String string2 = SuggestionsAdapter.getColumnString(object, "suggest_intent_query");
        if (string2 != null) {
            return string2;
        }
        if (this.mSearchable.shouldRewriteQueryFromData() && (string2 = SuggestionsAdapter.getColumnString(object, "suggest_intent_data")) != null) {
            return string2;
        }
        if (!this.mSearchable.shouldRewriteQueryFromText()) return null;
        if ((object = SuggestionsAdapter.getColumnString(object, "suggest_text_1")) == null) return null;
        return object;
    }

    Drawable getDrawableFromResourceUri(Uri uri) throws FileNotFoundException {
        Object object;
        String string2 = uri.getAuthority();
        if (TextUtils.isEmpty((CharSequence)string2)) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No authority: ");
            stringBuilder.append((Object)uri);
            throw new FileNotFoundException(stringBuilder.toString());
        }
        try {
            object = this.mContext.getPackageManager().getResourcesForApplication(string2);
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("No package found for authority: ");
            stringBuilder.append((Object)uri);
            throw new FileNotFoundException(stringBuilder.toString());
        }
        List list = uri.getPathSegments();
        if (list == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No path: ");
            ((StringBuilder)object).append((Object)uri);
            throw new FileNotFoundException(((StringBuilder)object).toString());
        }
        int n = list.size();
        if (n == 1) {
            try {
                n = Integer.parseInt((String)list.get(0));
            }
            catch (NumberFormatException numberFormatException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Single path segment is not a resource ID: ");
                stringBuilder.append((Object)uri);
                throw new FileNotFoundException(stringBuilder.toString());
            }
        } else {
            if (n != 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("More than two path segments: ");
                ((StringBuilder)object).append((Object)uri);
                throw new FileNotFoundException(((StringBuilder)object).toString());
            }
            n = object.getIdentifier((String)list.get(1), (String)list.get(0), string2);
        }
        if (n != 0) {
            return object.getDrawable(n);
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("No resource found for: ");
        ((StringBuilder)object).append((Object)uri);
        throw new FileNotFoundException(((StringBuilder)object).toString());
    }

    @Override
    public View getDropDownView(int n, View view, ViewGroup viewGroup) {
        try {
            return super.getDropDownView(n, view, viewGroup);
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)LOG_TAG, (String)"Search suggestions cursor threw exception.", (Throwable)runtimeException);
            viewGroup = this.newDropDownView(this.mContext, this.mCursor, viewGroup);
            if (viewGroup == null) return viewGroup;
            ((ChildViewCache)viewGroup.getTag()).mText1.setText((CharSequence)runtimeException.toString());
            return viewGroup;
        }
    }

    public int getQueryRefinement() {
        return this.mQueryRefinement;
    }

    Cursor getSearchManagerSuggestions(SearchableInfo arrstring, String string2, int n) {
        Object var4_4 = null;
        if (arrstring == null) {
            return null;
        }
        String string3 = arrstring.getSuggestAuthority();
        if (string3 == null) {
            return null;
        }
        string3 = new Uri.Builder().scheme("content").authority(string3).query("").fragment("");
        String string4 = arrstring.getSuggestPath();
        if (string4 != null) {
            string3.appendEncodedPath(string4);
        }
        string3.appendPath("search_suggest_query");
        string4 = arrstring.getSuggestSelection();
        if (string4 != null) {
            arrstring = new String[]{string2};
        } else {
            string3.appendPath(string2);
            arrstring = var4_4;
        }
        if (n > 0) {
            string3.appendQueryParameter("limit", String.valueOf(n));
        }
        string2 = string3.build();
        return this.mContext.getContentResolver().query((Uri)string2, null, string4, arrstring, null);
    }

    @Override
    public View getView(int n, View view, ViewGroup viewGroup) {
        try {
            return super.getView(n, view, viewGroup);
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)LOG_TAG, (String)"Search suggestions cursor threw exception.", (Throwable)runtimeException);
            viewGroup = this.newView(this.mContext, this.mCursor, viewGroup);
            if (viewGroup == null) return viewGroup;
            ((ChildViewCache)viewGroup.getTag()).mText1.setText((CharSequence)runtimeException.toString());
            return viewGroup;
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        context = super.newView(context, cursor, viewGroup);
        context.setTag((Object)new ChildViewCache((View)context));
        ((ImageView)context.findViewById(R.id.edit_query)).setImageResource(this.mCommitIconResId);
        return context;
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.updateSpinnerState(this.getCursor());
    }

    public void notifyDataSetInvalidated() {
        super.notifyDataSetInvalidated();
        this.updateSpinnerState(this.getCursor());
    }

    public void onClick(View object) {
        if (!((object = object.getTag()) instanceof CharSequence)) return;
        this.mSearchView.onQueryRefine((CharSequence)object);
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        charSequence = charSequence == null ? "" : charSequence.toString();
        if (this.mSearchView.getVisibility() != 0) return null;
        if (this.mSearchView.getWindowVisibility() != 0) {
            return null;
        }
        try {
            charSequence = this.getSearchManagerSuggestions(this.mSearchable, (String)charSequence, 50);
            if (charSequence == null) return null;
            charSequence.getCount();
            return charSequence;
        }
        catch (RuntimeException runtimeException) {
            Log.w((String)LOG_TAG, (String)"Search suggestions query threw an exception.", (Throwable)runtimeException);
        }
        return null;
    }

    public void setQueryRefinement(int n) {
        this.mQueryRefinement = n;
    }

    private static final class ChildViewCache {
        public final ImageView mIcon1;
        public final ImageView mIcon2;
        public final ImageView mIconRefine;
        public final TextView mText1;
        public final TextView mText2;

        public ChildViewCache(View view) {
            this.mText1 = (TextView)view.findViewById(16908308);
            this.mText2 = (TextView)view.findViewById(16908309);
            this.mIcon1 = (ImageView)view.findViewById(16908295);
            this.mIcon2 = (ImageView)view.findViewById(16908296);
            this.mIconRefine = (ImageView)view.findViewById(R.id.edit_query);
        }
    }

}


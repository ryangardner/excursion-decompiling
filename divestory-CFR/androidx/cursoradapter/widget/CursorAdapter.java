/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.database.DataSetObserver
 *  android.os.Handler
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.Filter
 *  android.widget.FilterQueryProvider
 *  android.widget.Filterable
 */
package androidx.cursoradapter.widget;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import androidx.cursoradapter.widget.CursorFilter;

public abstract class CursorAdapter
extends BaseAdapter
implements Filterable,
CursorFilter.CursorFilterClient {
    @Deprecated
    public static final int FLAG_AUTO_REQUERY = 1;
    public static final int FLAG_REGISTER_CONTENT_OBSERVER = 2;
    protected boolean mAutoRequery;
    protected ChangeObserver mChangeObserver;
    protected Context mContext;
    protected Cursor mCursor;
    protected CursorFilter mCursorFilter;
    protected DataSetObserver mDataSetObserver;
    protected boolean mDataValid;
    protected FilterQueryProvider mFilterQueryProvider;
    protected int mRowIDColumn;

    @Deprecated
    public CursorAdapter(Context context, Cursor cursor) {
        this.init(context, cursor, 1);
    }

    public CursorAdapter(Context context, Cursor cursor, int n) {
        this.init(context, cursor, n);
    }

    public CursorAdapter(Context context, Cursor cursor, boolean bl) {
        int n = bl ? 1 : 2;
        this.init(context, cursor, n);
    }

    public abstract void bindView(View var1, Context var2, Cursor var3);

    @Override
    public void changeCursor(Cursor cursor) {
        if ((cursor = this.swapCursor(cursor)) == null) return;
        cursor.close();
    }

    @Override
    public CharSequence convertToString(Cursor object) {
        if (object != null) return object.toString();
        return "";
    }

    public int getCount() {
        if (!this.mDataValid) return 0;
        Cursor cursor = this.mCursor;
        if (cursor == null) return 0;
        return cursor.getCount();
    }

    @Override
    public Cursor getCursor() {
        return this.mCursor;
    }

    public View getDropDownView(int n, View view, ViewGroup viewGroup) {
        if (!this.mDataValid) return null;
        this.mCursor.moveToPosition(n);
        View view2 = view;
        if (view == null) {
            view2 = this.newDropDownView(this.mContext, this.mCursor, viewGroup);
        }
        this.bindView(view2, this.mContext, this.mCursor);
        return view2;
    }

    public Filter getFilter() {
        if (this.mCursorFilter != null) return this.mCursorFilter;
        this.mCursorFilter = new CursorFilter(this);
        return this.mCursorFilter;
    }

    public FilterQueryProvider getFilterQueryProvider() {
        return this.mFilterQueryProvider;
    }

    public Object getItem(int n) {
        if (!this.mDataValid) return null;
        Cursor cursor = this.mCursor;
        if (cursor == null) return null;
        cursor.moveToPosition(n);
        return this.mCursor;
    }

    public long getItemId(int n) {
        if (!this.mDataValid) return 0L;
        Cursor cursor = this.mCursor;
        if (cursor == null) return 0L;
        if (!cursor.moveToPosition(n)) return 0L;
        return this.mCursor.getLong(this.mRowIDColumn);
    }

    public View getView(int n, View object, ViewGroup viewGroup) {
        if (!this.mDataValid) throw new IllegalStateException("this should only be called when the cursor is valid");
        if (!this.mCursor.moveToPosition(n)) {
            object = new StringBuilder();
            ((StringBuilder)object).append("couldn't move cursor to position ");
            ((StringBuilder)object).append(n);
            throw new IllegalStateException(((StringBuilder)object).toString());
        }
        View view = object;
        if (object == null) {
            view = this.newView(this.mContext, this.mCursor, viewGroup);
        }
        this.bindView(view, this.mContext, this.mCursor);
        return view;
    }

    public boolean hasStableIds() {
        return true;
    }

    void init(Context object, Cursor cursor, int n) {
        boolean bl = false;
        if ((n & 1) == 1) {
            n |= 2;
            this.mAutoRequery = true;
        } else {
            this.mAutoRequery = false;
        }
        if (cursor != null) {
            bl = true;
        }
        this.mCursor = cursor;
        this.mDataValid = bl;
        this.mContext = object;
        int n2 = bl ? cursor.getColumnIndexOrThrow("_id") : -1;
        this.mRowIDColumn = n2;
        if ((n & 2) == 2) {
            this.mChangeObserver = new ChangeObserver();
            this.mDataSetObserver = new MyDataSetObserver();
        } else {
            this.mChangeObserver = null;
            this.mDataSetObserver = null;
        }
        if (!bl) return;
        object = this.mChangeObserver;
        if (object != null) {
            cursor.registerContentObserver((ContentObserver)object);
        }
        if ((object = this.mDataSetObserver) == null) return;
        cursor.registerDataSetObserver((DataSetObserver)object);
    }

    @Deprecated
    protected void init(Context context, Cursor cursor, boolean bl) {
        int n = bl ? 1 : 2;
        this.init(context, cursor, n);
    }

    public View newDropDownView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.newView(context, cursor, viewGroup);
    }

    public abstract View newView(Context var1, Cursor var2, ViewGroup var3);

    protected void onContentChanged() {
        if (!this.mAutoRequery) return;
        Cursor cursor = this.mCursor;
        if (cursor == null) return;
        if (cursor.isClosed()) return;
        this.mDataValid = this.mCursor.requery();
    }

    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        FilterQueryProvider filterQueryProvider = this.mFilterQueryProvider;
        if (filterQueryProvider == null) return this.mCursor;
        return filterQueryProvider.runQuery(charSequence);
    }

    public void setFilterQueryProvider(FilterQueryProvider filterQueryProvider) {
        this.mFilterQueryProvider = filterQueryProvider;
    }

    public Cursor swapCursor(Cursor cursor) {
        ChangeObserver changeObserver;
        Cursor cursor2 = this.mCursor;
        if (cursor == cursor2) {
            return null;
        }
        if (cursor2 != null) {
            changeObserver = this.mChangeObserver;
            if (changeObserver != null) {
                cursor2.unregisterContentObserver((ContentObserver)changeObserver);
            }
            if ((changeObserver = this.mDataSetObserver) != null) {
                cursor2.unregisterDataSetObserver((DataSetObserver)changeObserver);
            }
        }
        this.mCursor = cursor;
        if (cursor == null) {
            this.mRowIDColumn = -1;
            this.mDataValid = false;
            this.notifyDataSetInvalidated();
            return cursor2;
        }
        changeObserver = this.mChangeObserver;
        if (changeObserver != null) {
            cursor.registerContentObserver((ContentObserver)changeObserver);
        }
        if ((changeObserver = this.mDataSetObserver) != null) {
            cursor.registerDataSetObserver((DataSetObserver)changeObserver);
        }
        this.mRowIDColumn = cursor.getColumnIndexOrThrow("_id");
        this.mDataValid = true;
        this.notifyDataSetChanged();
        return cursor2;
    }

    private class ChangeObserver
    extends ContentObserver {
        ChangeObserver() {
            super(new Handler());
        }

        public boolean deliverSelfNotifications() {
            return true;
        }

        public void onChange(boolean bl) {
            CursorAdapter.this.onContentChanged();
        }
    }

    private class MyDataSetObserver
    extends DataSetObserver {
        MyDataSetObserver() {
        }

        public void onChanged() {
            CursorAdapter.this.mDataValid = true;
            CursorAdapter.this.notifyDataSetChanged();
        }

        public void onInvalidated() {
            CursorAdapter.this.mDataValid = false;
            CursorAdapter.this.notifyDataSetInvalidated();
        }
    }

}


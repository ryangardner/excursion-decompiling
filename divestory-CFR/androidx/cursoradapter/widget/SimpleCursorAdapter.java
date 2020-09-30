/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.view.View
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package androidx.cursoradapter.widget;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.cursoradapter.widget.ResourceCursorAdapter;

public class SimpleCursorAdapter
extends ResourceCursorAdapter {
    private CursorToStringConverter mCursorToStringConverter;
    protected int[] mFrom;
    String[] mOriginalFrom;
    private int mStringConversionColumn = -1;
    protected int[] mTo;
    private ViewBinder mViewBinder;

    @Deprecated
    public SimpleCursorAdapter(Context context, int n, Cursor cursor, String[] arrstring, int[] arrn) {
        super(context, n, cursor);
        this.mTo = arrn;
        this.mOriginalFrom = arrstring;
        this.findColumns(cursor, arrstring);
    }

    public SimpleCursorAdapter(Context context, int n, Cursor cursor, String[] arrstring, int[] arrn, int n2) {
        super(context, n, cursor, n2);
        this.mTo = arrn;
        this.mOriginalFrom = arrstring;
        this.findColumns(cursor, arrstring);
    }

    private void findColumns(Cursor cursor, String[] arrstring) {
        if (cursor == null) {
            this.mFrom = null;
            return;
        }
        int n = arrstring.length;
        int[] arrn = this.mFrom;
        if (arrn == null || arrn.length != n) {
            this.mFrom = new int[n];
        }
        int n2 = 0;
        while (n2 < n) {
            this.mFrom[n2] = cursor.getColumnIndexOrThrow(arrstring[n2]);
            ++n2;
        }
    }

    @Override
    public void bindView(View object, Context object2, Cursor cursor) {
        ViewBinder viewBinder = this.mViewBinder;
        int[] arrn = this.mTo;
        int n = arrn.length;
        int[] arrn2 = this.mFrom;
        int n2 = 0;
        while (n2 < n) {
            boolean bl;
            View view = object.findViewById(arrn[n2]);
            if (view != null && !(bl = viewBinder != null ? viewBinder.setViewValue(view, cursor, arrn2[n2]) : false)) {
                String string2 = cursor.getString(arrn2[n2]);
                object2 = string2;
                if (string2 == null) {
                    object2 = "";
                }
                if (view instanceof TextView) {
                    this.setViewText((TextView)view, (String)object2);
                } else {
                    if (!(view instanceof ImageView)) {
                        object = new StringBuilder();
                        ((StringBuilder)object).append(view.getClass().getName());
                        ((StringBuilder)object).append(" is not a ");
                        ((StringBuilder)object).append(" view that can be bounds by this SimpleCursorAdapter");
                        throw new IllegalStateException(((StringBuilder)object).toString());
                    }
                    this.setViewImage((ImageView)view, (String)object2);
                }
            }
            ++n2;
        }
    }

    public void changeCursorAndColumns(Cursor cursor, String[] arrstring, int[] arrn) {
        this.mOriginalFrom = arrstring;
        this.mTo = arrn;
        this.findColumns(cursor, arrstring);
        super.changeCursor(cursor);
    }

    @Override
    public CharSequence convertToString(Cursor cursor) {
        CursorToStringConverter cursorToStringConverter = this.mCursorToStringConverter;
        if (cursorToStringConverter != null) {
            return cursorToStringConverter.convertToString(cursor);
        }
        int n = this.mStringConversionColumn;
        if (n <= -1) return super.convertToString(cursor);
        return cursor.getString(n);
    }

    public CursorToStringConverter getCursorToStringConverter() {
        return this.mCursorToStringConverter;
    }

    public int getStringConversionColumn() {
        return this.mStringConversionColumn;
    }

    public ViewBinder getViewBinder() {
        return this.mViewBinder;
    }

    public void setCursorToStringConverter(CursorToStringConverter cursorToStringConverter) {
        this.mCursorToStringConverter = cursorToStringConverter;
    }

    public void setStringConversionColumn(int n) {
        this.mStringConversionColumn = n;
    }

    public void setViewBinder(ViewBinder viewBinder) {
        this.mViewBinder = viewBinder;
    }

    public void setViewImage(ImageView imageView, String string2) {
        try {
            imageView.setImageResource(Integer.parseInt(string2));
            return;
        }
        catch (NumberFormatException numberFormatException) {
            imageView.setImageURI(Uri.parse((String)string2));
        }
    }

    public void setViewText(TextView textView, String string2) {
        textView.setText((CharSequence)string2);
    }

    @Override
    public Cursor swapCursor(Cursor cursor) {
        this.findColumns(cursor, this.mOriginalFrom);
        return super.swapCursor(cursor);
    }

    public static interface CursorToStringConverter {
        public CharSequence convertToString(Cursor var1);
    }

    public static interface ViewBinder {
        public boolean setViewValue(View var1, Cursor var2, int var3);
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.net.Uri
 */
package androidx.loader.content;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import androidx.core.content.ContentResolverCompat;
import androidx.core.os.CancellationSignal;
import androidx.core.os.OperationCanceledException;
import androidx.loader.content.AsyncTaskLoader;
import androidx.loader.content.Loader;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

public class CursorLoader
extends AsyncTaskLoader<Cursor> {
    CancellationSignal mCancellationSignal;
    Cursor mCursor;
    final Loader<Cursor> mObserver = new Loader.ForceLoadContentObserver();
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;
    Uri mUri;

    public CursorLoader(Context context) {
        super(context);
    }

    public CursorLoader(Context context, Uri uri, String[] arrstring, String string2, String[] arrstring2, String string3) {
        super(context);
        this.mUri = uri;
        this.mProjection = arrstring;
        this.mSelection = string2;
        this.mSelectionArgs = arrstring2;
        this.mSortOrder = string3;
    }

    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
        synchronized (this) {
            if (this.mCancellationSignal == null) return;
            this.mCancellationSignal.cancel();
            return;
        }
    }

    @Override
    public void deliverResult(Cursor cursor) {
        if (this.isReset()) {
            if (cursor == null) return;
            cursor.close();
            return;
        }
        Cursor cursor2 = this.mCursor;
        this.mCursor = cursor;
        if (this.isStarted()) {
            super.deliverResult(cursor);
        }
        if (cursor2 == null) return;
        if (cursor2 == cursor) return;
        if (cursor2.isClosed()) return;
        cursor2.close();
    }

    @Deprecated
    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string2, fileDescriptor, printWriter, arrstring);
        printWriter.print(string2);
        printWriter.print("mUri=");
        printWriter.println((Object)this.mUri);
        printWriter.print(string2);
        printWriter.print("mProjection=");
        printWriter.println(Arrays.toString(this.mProjection));
        printWriter.print(string2);
        printWriter.print("mSelection=");
        printWriter.println(this.mSelection);
        printWriter.print(string2);
        printWriter.print("mSelectionArgs=");
        printWriter.println(Arrays.toString(this.mSelectionArgs));
        printWriter.print(string2);
        printWriter.print("mSortOrder=");
        printWriter.println(this.mSortOrder);
        printWriter.print(string2);
        printWriter.print("mCursor=");
        printWriter.println((Object)this.mCursor);
        printWriter.print(string2);
        printWriter.print("mContentChanged=");
        printWriter.println(this.mContentChanged);
    }

    public String[] getProjection() {
        return this.mProjection;
    }

    public String getSelection() {
        return this.mSelection;
    }

    public String[] getSelectionArgs() {
        return this.mSelectionArgs;
    }

    public String getSortOrder() {
        return this.mSortOrder;
    }

    public Uri getUri() {
        return this.mUri;
    }

    /*
     * Enabled unnecessary exception pruning
     */
    @Override
    public Cursor loadInBackground() {
        CancellationSignal cancellationSignal;
        synchronized (this) {
            if (this.isLoadInBackgroundCanceled()) {
                OperationCanceledException operationCanceledException = new OperationCanceledException();
                throw operationCanceledException;
            }
            this.mCancellationSignal = cancellationSignal = new CancellationSignal();
        }
        try {
            cancellationSignal = ContentResolverCompat.query(this.getContext().getContentResolver(), this.mUri, this.mProjection, this.mSelection, this.mSelectionArgs, this.mSortOrder, this.mCancellationSignal);
            if (cancellationSignal == null) return cancellationSignal;
            try {
                cancellationSignal.getCount();
                cancellationSignal.registerContentObserver(this.mObserver);
                return cancellationSignal;
            }
            catch (RuntimeException runtimeException) {
                cancellationSignal.close();
                throw runtimeException;
            }
        }
        finally {
            synchronized (this) {
                this.mCancellationSignal = null;
            }
        }
    }

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor == null) return;
        if (cursor.isClosed()) return;
        cursor.close();
    }

    @Override
    protected void onReset() {
        super.onReset();
        this.onStopLoading();
        Cursor cursor = this.mCursor;
        if (cursor != null && !cursor.isClosed()) {
            this.mCursor.close();
        }
        this.mCursor = null;
    }

    @Override
    protected void onStartLoading() {
        Cursor cursor = this.mCursor;
        if (cursor != null) {
            this.deliverResult(cursor);
        }
        if (!this.takeContentChanged()) {
            if (this.mCursor != null) return;
        }
        this.forceLoad();
    }

    @Override
    protected void onStopLoading() {
        this.cancelLoad();
    }

    public void setProjection(String[] arrstring) {
        this.mProjection = arrstring;
    }

    public void setSelection(String string2) {
        this.mSelection = string2;
    }

    public void setSelectionArgs(String[] arrstring) {
        this.mSelectionArgs = arrstring;
    }

    public void setSortOrder(String string2) {
        this.mSortOrder = string2;
    }

    public void setUri(Uri uri) {
        this.mUri = uri;
    }
}


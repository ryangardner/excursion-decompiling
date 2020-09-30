/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.database.CharArrayBuffer
 *  android.database.Cursor
 *  android.database.CursorIndexOutOfBoundsException
 *  android.database.CursorWindow
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package com.google.android.gms.common.data;

import android.content.ContentValues;
import android.database.CharArrayBuffer;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.CursorWindow;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.data.zab;
import com.google.android.gms.common.data.zac;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.common.internal.safeparcel.AbstractSafeParcelable;
import com.google.android.gms.common.internal.safeparcel.SafeParcelWriter;
import com.google.android.gms.common.sqlite.CursorWrapper;
import java.io.Closeable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class DataHolder
extends AbstractSafeParcelable
implements Closeable {
    public static final Parcelable.Creator<DataHolder> CREATOR = new zac();
    private static final Builder zak = new zab(new String[0], null);
    private final int zaa;
    private final String[] zab;
    private Bundle zac;
    private final CursorWindow[] zad;
    private final int zae;
    private final Bundle zaf;
    private int[] zag;
    private int zah;
    private boolean zai = false;
    private boolean zaj = true;

    DataHolder(int n, String[] arrstring, CursorWindow[] arrcursorWindow, int n2, Bundle bundle) {
        this.zaa = n;
        this.zab = arrstring;
        this.zad = arrcursorWindow;
        this.zae = n2;
        this.zaf = bundle;
    }

    public DataHolder(Cursor cursor, int n, Bundle bundle) {
        this(new CursorWrapper(cursor), n, bundle);
    }

    private DataHolder(Builder builder, int n, Bundle bundle) {
        this(builder.zaa, DataHolder.zaa(builder, -1), n, null);
    }

    private DataHolder(Builder builder, int n, Bundle bundle, int n2) {
        this(builder.zaa, DataHolder.zaa(builder, -1), n, bundle);
    }

    /* synthetic */ DataHolder(Builder builder, int n, Bundle bundle, int n2, zab zab2) {
        this(builder, n, bundle, -1);
    }

    /* synthetic */ DataHolder(Builder builder, int n, Bundle bundle, zab zab2) {
        this(builder, n, null);
    }

    private DataHolder(CursorWrapper cursorWrapper, int n, Bundle bundle) {
        this(cursorWrapper.getColumnNames(), DataHolder.zaa(cursorWrapper), n, bundle);
    }

    public DataHolder(String[] arrstring, CursorWindow[] arrcursorWindow, int n, Bundle bundle) {
        this.zaa = 1;
        this.zab = Preconditions.checkNotNull(arrstring);
        this.zad = Preconditions.checkNotNull(arrcursorWindow);
        this.zae = n;
        this.zaf = bundle;
        this.zaa();
    }

    public static Builder builder(String[] arrstring) {
        return new Builder(arrstring, null, null);
    }

    public static DataHolder empty(int n) {
        return new DataHolder(zak, n, null);
    }

    private final void zaa(String string2, int n) {
        Bundle bundle = this.zac;
        if (bundle != null && bundle.containsKey(string2)) {
            if (this.isClosed()) throw new IllegalArgumentException("Buffer is closed.");
            if (n < 0) throw new CursorIndexOutOfBoundsException(n, this.zah);
            if (n >= this.zah) throw new CursorIndexOutOfBoundsException(n, this.zah);
            return;
        }
        if ((string2 = String.valueOf(string2)).length() != 0) {
            string2 = "No such column: ".concat(string2);
            throw new IllegalArgumentException(string2);
        }
        string2 = new String("No such column: ");
        throw new IllegalArgumentException(string2);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    private static CursorWindow[] zaa(Builder object, int n) {
        n = ((Builder)object).zaa.length;
        int n2 = 0;
        if (n == 0) {
            return new CursorWindow[0];
        }
        Object object2 = ((Builder)object).zab;
        int n3 = object2.size();
        Object object3 = new CursorWindow(false);
        ArrayList<Object> arrayList = new ArrayList<Object>();
        arrayList.add(object3);
        object3.setNumColumns(((Builder)object).zaa.length);
        n = 0;
        int n4 = 0;
        while (n < n3) {
            block20 : {
                block19 : {
                    CharSequence charSequence;
                    boolean bl = object3.allocRow();
                    if (!bl) {
                        object3 = new StringBuilder(72);
                        ((StringBuilder)object3).append("Allocating additional cursor window for large data set (row ");
                        ((StringBuilder)object3).append(n);
                        ((StringBuilder)object3).append(")");
                        Log.d((String)"DataHolder", (String)((StringBuilder)object3).toString());
                        charSequence = new CursorWindow(false);
                        charSequence.setStartPosition(n);
                        charSequence.setNumColumns(((Builder)object).zaa.length);
                        arrayList.add(charSequence);
                        object3 = charSequence;
                        if (!charSequence.allocRow()) {
                            Log.e((String)"DataHolder", (String)"Unable to allocate row to hold data.");
                            arrayList.remove(charSequence);
                            return arrayList.toArray((T[])new CursorWindow[arrayList.size()]);
                        }
                    }
                    Map map = (Map)object2.get(n);
                    bl = true;
                    for (int i = 0; i < ((Builder)object).zaa.length && bl; ++i) {
                        charSequence = ((Builder)object).zaa[i];
                        Object v = map.get(charSequence);
                        if (v == null) {
                            bl = object3.putNull(n, i);
                            continue;
                        }
                        if (v instanceof String) {
                            bl = object3.putString((String)v, n, i);
                            continue;
                        }
                        if (v instanceof Long) {
                            bl = object3.putLong(((Long)v).longValue(), n, i);
                            continue;
                        }
                        if (v instanceof Integer) {
                            bl = object3.putLong((long)((Integer)v).intValue(), n, i);
                            continue;
                        }
                        if (v instanceof Boolean) {
                            long l = (Boolean)v != false ? 1L : 0L;
                            bl = object3.putLong(l, n, i);
                            continue;
                        }
                        if (v instanceof byte[]) {
                            bl = object3.putBlob((byte[])v, n, i);
                            continue;
                        }
                        if (v instanceof Double) {
                            bl = object3.putDouble(((Double)v).doubleValue(), n, i);
                            continue;
                        }
                        if (v instanceof Float) {
                            bl = object3.putDouble((double)((Float)v).floatValue(), n, i);
                            continue;
                        }
                        object2 = String.valueOf(v);
                        n4 = String.valueOf(charSequence).length();
                        n = String.valueOf(object2).length();
                        object3 = new StringBuilder(n4 + 32 + n);
                        ((StringBuilder)object3).append("Unsupported object for column ");
                        ((StringBuilder)object3).append((String)charSequence);
                        ((StringBuilder)object3).append(": ");
                        ((StringBuilder)object3).append((String)object2);
                        object = new IllegalArgumentException(((StringBuilder)object3).toString());
                        throw object;
                    }
                    if (bl) break block19;
                    if (n4 == 0) {
                        charSequence = new StringBuilder(74);
                        ((StringBuilder)charSequence).append("Couldn't populate window data for row ");
                        ((StringBuilder)charSequence).append(n);
                        ((StringBuilder)charSequence).append(" - allocating new window.");
                        Log.d((String)"DataHolder", (String)((StringBuilder)charSequence).toString());
                        object3.freeLastRow();
                        object3 = new CursorWindow(false);
                        object3.setStartPosition(n);
                        object3.setNumColumns(((Builder)object).zaa.length);
                        arrayList.add(object3);
                        --n;
                        n4 = 1;
                        break block20;
                    }
                    object = new zaa("Could not add the value to a new CursorWindow. The size of value may be larger than what a CursorWindow can handle.");
                    throw object;
                }
                n4 = 0;
            }
            ++n;
        }
        return arrayList.toArray((T[])new CursorWindow[arrayList.size()]);
        catch (RuntimeException runtimeException) {
            n4 = arrayList.size();
            n = n2;
            while (n < n4) {
                ((CursorWindow)arrayList.get(n)).close();
                ++n;
            }
            throw runtimeException;
        }
    }

    private static CursorWindow[] zaa(CursorWrapper cursorWrapper) {
        int n;
        int n2;
        ArrayList<CursorWindow> arrayList = new ArrayList<CursorWindow>();
        try {
            int n3 = cursorWrapper.getCount();
            CursorWindow cursorWindow = cursorWrapper.getWindow();
            if (cursorWindow != null && cursorWindow.getStartPosition() == 0) {
                cursorWindow.acquireReference();
                cursorWrapper.setWindow(null);
                arrayList.add(cursorWindow);
                n = cursorWindow.getNumRows();
            } else {
                n = 0;
            }
            while (n < n3 && cursorWrapper.moveToPosition(n)) {
                cursorWindow = cursorWrapper.getWindow();
                if (cursorWindow != null) {
                    cursorWindow.acquireReference();
                    cursorWrapper.setWindow(null);
                } else {
                    cursorWindow = new CursorWindow(false);
                    cursorWindow.setStartPosition(n);
                    cursorWrapper.fillWindow(n, cursorWindow);
                }
                if (cursorWindow.getNumRows() == 0) break;
                arrayList.add(cursorWindow);
                n2 = cursorWindow.getStartPosition();
                n = cursorWindow.getNumRows();
            }
        }
        catch (Throwable throwable) {
            cursorWrapper.close();
            throw throwable;
        }
        {
            n = n2 + n;
            continue;
        }
        cursorWrapper.close();
        return arrayList.toArray((T[])new CursorWindow[arrayList.size()]);
    }

    @Override
    public final void close() {
        synchronized (this) {
            if (this.zai) return;
            this.zai = true;
            int n = 0;
            while (n < this.zad.length) {
                this.zad[n].close();
                ++n;
            }
            return;
        }
    }

    protected final void finalize() throws Throwable {
        try {
            if (!this.zaj) return;
            if (this.zad.length <= 0) return;
            if (this.isClosed()) return;
            this.close();
            String string2 = this.toString();
            int n = String.valueOf(string2).length();
            StringBuilder stringBuilder = new StringBuilder(n + 178);
            stringBuilder.append("Internal data leak within a DataBuffer object detected!  Be sure to explicitly call release() on all DataBuffer extending objects when you are done with them. (internal object: ");
            stringBuilder.append(string2);
            stringBuilder.append(")");
            Log.e((String)"DataBuffer", (String)stringBuilder.toString());
            return;
        }
        finally {
            Object.super.finalize();
        }
    }

    public final boolean getBoolean(String string2, int n, int n2) {
        this.zaa(string2, n);
        if (Long.valueOf(this.zad[n2].getLong(n, this.zac.getInt(string2))) != 1L) return false;
        return true;
    }

    public final byte[] getByteArray(String string2, int n, int n2) {
        this.zaa(string2, n);
        return this.zad[n2].getBlob(n, this.zac.getInt(string2));
    }

    public final int getCount() {
        return this.zah;
    }

    public final int getInteger(String string2, int n, int n2) {
        this.zaa(string2, n);
        return this.zad[n2].getInt(n, this.zac.getInt(string2));
    }

    public final long getLong(String string2, int n, int n2) {
        this.zaa(string2, n);
        return this.zad[n2].getLong(n, this.zac.getInt(string2));
    }

    public final Bundle getMetadata() {
        return this.zaf;
    }

    public final int getStatusCode() {
        return this.zae;
    }

    public final String getString(String string2, int n, int n2) {
        this.zaa(string2, n);
        return this.zad[n2].getString(n, this.zac.getInt(string2));
    }

    public final int getWindowIndex(int n) {
        int n2;
        block1 : {
            int[] arrn;
            int n3 = 0;
            boolean bl = n >= 0 && n < this.zah;
            Preconditions.checkState(bl);
            do {
                arrn = this.zag;
                n2 = ++n3;
                if (n3 >= arrn.length) break block1;
            } while (n >= arrn[n3]);
            n2 = n3 - 1;
        }
        n = n2;
        if (n2 != this.zag.length) return n;
        return n2 - 1;
    }

    public final boolean hasColumn(String string2) {
        return this.zac.containsKey(string2);
    }

    public final boolean hasNull(String string2, int n, int n2) {
        this.zaa(string2, n);
        return this.zad[n2].isNull(n, this.zac.getInt(string2));
    }

    public final boolean isClosed() {
        synchronized (this) {
            return this.zai;
        }
    }

    public final void writeToParcel(Parcel parcel, int n) {
        int n2 = SafeParcelWriter.beginObjectHeader(parcel);
        SafeParcelWriter.writeStringArray(parcel, 1, this.zab, false);
        SafeParcelWriter.writeTypedArray((Parcel)parcel, (int)2, (Parcelable[])this.zad, (int)n, (boolean)false);
        SafeParcelWriter.writeInt(parcel, 3, this.getStatusCode());
        SafeParcelWriter.writeBundle(parcel, 4, this.getMetadata(), false);
        SafeParcelWriter.writeInt(parcel, 1000, this.zaa);
        SafeParcelWriter.finishObjectHeader(parcel, n2);
        if ((n & 1) == 0) return;
        this.close();
    }

    public final float zaa(String string2, int n, int n2) {
        this.zaa(string2, n);
        return this.zad[n2].getFloat(n, this.zac.getInt(string2));
    }

    public final void zaa() {
        String[] arrstring;
        int n;
        this.zac = new Bundle();
        int n2 = 0;
        for (n = 0; n < (arrstring = this.zab).length; ++n) {
            this.zac.putInt(arrstring[n], n);
        }
        this.zag = new int[this.zad.length];
        int n3 = 0;
        n = n2;
        do {
            if (n >= (arrstring = this.zad).length) {
                this.zah = n3;
                return;
            }
            this.zag[n] = n3;
            n2 = arrstring[n].getStartPosition();
            n3 += this.zad[n].getNumRows() - (n3 - n2);
            ++n;
        } while (true);
    }

    public final void zaa(String string2, int n, int n2, CharArrayBuffer charArrayBuffer) {
        this.zaa(string2, n);
        this.zad[n2].copyStringToBuffer(n, this.zac.getInt(string2), charArrayBuffer);
    }

    public final double zab(String string2, int n, int n2) {
        this.zaa(string2, n);
        return this.zad[n2].getDouble(n, this.zac.getInt(string2));
    }

    public static class Builder {
        private final String[] zaa;
        private final ArrayList<HashMap<String, Object>> zab;
        private final String zac;
        private final HashMap<Object, Integer> zad;
        private boolean zae;
        private String zaf;

        private Builder(String[] arrstring, String string2) {
            this.zaa = Preconditions.checkNotNull(arrstring);
            this.zab = new ArrayList();
            this.zac = null;
            this.zad = new HashMap();
            this.zae = false;
            this.zaf = null;
        }

        /* synthetic */ Builder(String[] arrstring, String string2, zab zab2) {
            this(arrstring, null);
        }

        public DataHolder build(int n) {
            return new DataHolder(this, n, null, null);
        }

        public DataHolder build(int n, Bundle bundle) {
            return new DataHolder(this, n, bundle, -1, null);
        }

        public Builder withRow(ContentValues object) {
            Asserts.checkNotNull(object);
            HashMap<String, Object> hashMap = new HashMap<String, Object>(object.size());
            Iterator iterator2 = object.valueSet().iterator();
            while (iterator2.hasNext()) {
                object = (Map.Entry)iterator2.next();
                hashMap.put((String)object.getKey(), object.getValue());
            }
            return this.zaa(hashMap);
        }

        /*
         * Unable to fully structure code
         */
        public Builder zaa(HashMap<String, Object> var1_1) {
            Asserts.checkNotNull(var1_1);
            var2_2 = this.zac;
            if (var2_2 == null || (var2_2 = var1_1.get(var2_2)) == null) ** GOTO lbl8
            var4_4 = this.zad.get(var2_2);
            if (var4_4 == null) {
                this.zad.put(var2_2, this.zab.size());
lbl8: // 2 sources:
                var3_3 = -1;
            } else {
                var3_3 = var4_4;
            }
            if (var3_3 == -1) {
                this.zab.add(var1_1);
            } else {
                this.zab.remove(var3_3);
                this.zab.add(var3_3, var1_1);
            }
            this.zae = false;
            return this;
        }
    }

    public static final class zaa
    extends RuntimeException {
        public zaa(String string2) {
            super(string2);
        }
    }

}


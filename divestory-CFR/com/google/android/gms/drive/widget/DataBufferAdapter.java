/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.CursorIndexOutOfBoundsException
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.TextView
 */
package com.google.android.gms.drive.widget;

import android.content.Context;
import android.database.CursorIndexOutOfBoundsException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.google.android.gms.common.data.DataBuffer;
import com.google.android.gms.common.internal.GmsLogger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class DataBufferAdapter<T>
extends BaseAdapter {
    private static final GmsLogger zzbz = new GmsLogger("DataBufferAdapter", "");
    private final int fieldId;
    private final int resource;
    private final Context zzgw;
    private int zzmz;
    private final List<DataBuffer<T>> zzna;
    private final LayoutInflater zznb;
    private boolean zznc = true;

    public DataBufferAdapter(Context context, int n) {
        this(context, n, 0, new ArrayList<DataBuffer<T>>());
    }

    public DataBufferAdapter(Context context, int n, int n2) {
        this(context, n, n2, new ArrayList<DataBuffer<T>>());
    }

    public DataBufferAdapter(Context context, int n, int n2, List<DataBuffer<T>> list) {
        this.zzgw = context;
        this.zzmz = n;
        this.resource = n;
        this.fieldId = n2;
        this.zzna = list;
        this.zznb = (LayoutInflater)context.getSystemService("layout_inflater");
    }

    public DataBufferAdapter(Context context, int n, int n2, DataBuffer<T> ... arrdataBuffer) {
        this(context, n, n2, Arrays.asList(arrdataBuffer));
    }

    public DataBufferAdapter(Context context, int n, List<DataBuffer<T>> list) {
        this(context, n, 0, list);
    }

    public DataBufferAdapter(Context context, int n, DataBuffer<T> ... arrdataBuffer) {
        this(context, n, 0, Arrays.asList(arrdataBuffer));
    }

    private final View zza(int n, View view, ViewGroup object, int n2) {
        View view2 = view;
        if (view == null) {
            view2 = this.zznb.inflate(n2, (ViewGroup)object, false);
        }
        try {
            view = this.fieldId == 0 ? (TextView)view2 : (TextView)view2.findViewById(this.fieldId);
        }
        catch (ClassCastException classCastException) {
            zzbz.e("DataBufferAdapter", "You must supply a resource ID for a TextView", classCastException);
            throw new IllegalStateException("DataBufferAdapter requires the resource ID to be a TextView", classCastException);
        }
        object = this.getItem(n);
        if (object instanceof CharSequence) {
            view.setText((CharSequence)object);
            return view2;
        }
        view.setText((CharSequence)object.toString());
        return view2;
    }

    public void append(DataBuffer<T> dataBuffer) {
        this.zzna.add(dataBuffer);
        if (!this.zznc) return;
        this.notifyDataSetChanged();
    }

    public void clear() {
        Iterator<DataBuffer<T>> iterator2 = this.zzna.iterator();
        do {
            if (!iterator2.hasNext()) {
                this.zzna.clear();
                if (!this.zznc) return;
                this.notifyDataSetChanged();
                return;
            }
            iterator2.next().release();
        } while (true);
    }

    public Context getContext() {
        return this.zzgw;
    }

    public int getCount() {
        Iterator<DataBuffer<T>> iterator2 = this.zzna.iterator();
        int n = 0;
        while (iterator2.hasNext()) {
            n += iterator2.next().getCount();
        }
        return n;
    }

    public View getDropDownView(int n, View view, ViewGroup viewGroup) {
        return this.zza(n, view, viewGroup, this.zzmz);
    }

    public T getItem(int n) throws CursorIndexOutOfBoundsException {
        int n2;
        DataBuffer<T> dataBuffer;
        Iterator<DataBuffer<T>> iterator2;
        block4 : {
            iterator2 = this.zzna.iterator();
            n2 = n;
            while (iterator2.hasNext()) {
                dataBuffer = iterator2.next();
                int n3 = dataBuffer.getCount();
                if (n3 <= n2) {
                    n2 -= n3;
                    continue;
                }
                break block4;
            }
            throw new CursorIndexOutOfBoundsException(n, this.getCount());
        }
        try {
            iterator2 = dataBuffer.get(n2);
        }
        catch (CursorIndexOutOfBoundsException cursorIndexOutOfBoundsException) {
            throw new CursorIndexOutOfBoundsException(n, this.getCount());
        }
        return (T)iterator2;
    }

    public long getItemId(int n) {
        return n;
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        return this.zza(n, view, viewGroup, this.resource);
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        this.zznc = true;
    }

    public void setDropDownViewResource(int n) {
        this.zzmz = n;
    }

    public void setNotifyOnChange(boolean bl) {
        this.zznc = bl;
    }
}


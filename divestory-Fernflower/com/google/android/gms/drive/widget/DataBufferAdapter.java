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

public class DataBufferAdapter<T> extends BaseAdapter {
   private static final GmsLogger zzbz = new GmsLogger("DataBufferAdapter", "");
   private final int fieldId;
   private final int resource;
   private final Context zzgw;
   private int zzmz;
   private final List<DataBuffer<T>> zzna;
   private final LayoutInflater zznb;
   private boolean zznc;

   public DataBufferAdapter(Context var1, int var2) {
      this(var1, var2, 0, (List)(new ArrayList()));
   }

   public DataBufferAdapter(Context var1, int var2, int var3) {
      this(var1, var2, var3, (List)(new ArrayList()));
   }

   public DataBufferAdapter(Context var1, int var2, int var3, List<DataBuffer<T>> var4) {
      this.zznc = true;
      this.zzgw = var1;
      this.zzmz = var2;
      this.resource = var2;
      this.fieldId = var3;
      this.zzna = var4;
      this.zznb = (LayoutInflater)var1.getSystemService("layout_inflater");
   }

   public DataBufferAdapter(Context var1, int var2, int var3, DataBuffer<T>... var4) {
      this(var1, var2, var3, Arrays.asList(var4));
   }

   public DataBufferAdapter(Context var1, int var2, List<DataBuffer<T>> var3) {
      this(var1, var2, 0, (List)var3);
   }

   public DataBufferAdapter(Context var1, int var2, DataBuffer<T>... var3) {
      this(var1, var2, 0, (List)Arrays.asList(var3));
   }

   private final View zza(int var1, View var2, ViewGroup var3, int var4) {
      View var5 = var2;
      if (var2 == null) {
         var5 = this.zznb.inflate(var4, var3, false);
      }

      TextView var7;
      try {
         if (this.fieldId == 0) {
            var7 = (TextView)var5;
         } else {
            var7 = (TextView)var5.findViewById(this.fieldId);
         }
      } catch (ClassCastException var6) {
         zzbz.e("DataBufferAdapter", "You must supply a resource ID for a TextView", var6);
         throw new IllegalStateException("DataBufferAdapter requires the resource ID to be a TextView", var6);
      }

      Object var8 = this.getItem(var1);
      if (var8 instanceof CharSequence) {
         var7.setText((CharSequence)var8);
      } else {
         var7.setText(var8.toString());
      }

      return var5;
   }

   public void append(DataBuffer<T> var1) {
      this.zzna.add(var1);
      if (this.zznc) {
         this.notifyDataSetChanged();
      }

   }

   public void clear() {
      Iterator var1 = this.zzna.iterator();

      while(var1.hasNext()) {
         ((DataBuffer)var1.next()).release();
      }

      this.zzna.clear();
      if (this.zznc) {
         this.notifyDataSetChanged();
      }

   }

   public Context getContext() {
      return this.zzgw;
   }

   public int getCount() {
      Iterator var1 = this.zzna.iterator();

      int var2;
      for(var2 = 0; var1.hasNext(); var2 += ((DataBuffer)var1.next()).getCount()) {
      }

      return var2;
   }

   public View getDropDownView(int var1, View var2, ViewGroup var3) {
      return this.zza(var1, var2, var3, this.zzmz);
   }

   public T getItem(int var1) throws CursorIndexOutOfBoundsException {
      Iterator var2 = this.zzna.iterator();

      int var5;
      for(int var3 = var1; var2.hasNext(); var3 -= var5) {
         DataBuffer var4 = (DataBuffer)var2.next();
         var5 = var4.getCount();
         if (var5 > var3) {
            try {
               Object var7 = var4.get(var3);
               return var7;
            } catch (CursorIndexOutOfBoundsException var6) {
               throw new CursorIndexOutOfBoundsException(var1, this.getCount());
            }
         }
      }

      throw new CursorIndexOutOfBoundsException(var1, this.getCount());
   }

   public long getItemId(int var1) {
      return (long)var1;
   }

   public View getView(int var1, View var2, ViewGroup var3) {
      return this.zza(var1, var2, var3, this.resource);
   }

   public void notifyDataSetChanged() {
      super.notifyDataSetChanged();
      this.zznc = true;
   }

   public void setDropDownViewResource(int var1) {
      this.zzmz = var1;
   }

   public void setNotifyOnChange(boolean var1) {
      this.zznc = var1;
   }
}

package com.google.android.gms.common.data;

import android.database.CharArrayBuffer;
import android.net.Uri;
import com.google.android.gms.common.internal.Objects;
import com.google.android.gms.common.internal.Preconditions;

public class DataBufferRef {
   protected final DataHolder mDataHolder;
   protected int mDataRow;
   private int zaa;

   public DataBufferRef(DataHolder var1, int var2) {
      this.mDataHolder = (DataHolder)Preconditions.checkNotNull(var1);
      this.zaa(var2);
   }

   protected void copyToBuffer(String var1, CharArrayBuffer var2) {
      this.mDataHolder.zaa(var1, this.mDataRow, this.zaa, var2);
   }

   public boolean equals(Object var1) {
      if (var1 instanceof DataBufferRef) {
         DataBufferRef var2 = (DataBufferRef)var1;
         if (Objects.equal(var2.mDataRow, this.mDataRow) && Objects.equal(var2.zaa, this.zaa) && var2.mDataHolder == this.mDataHolder) {
            return true;
         }
      }

      return false;
   }

   protected boolean getBoolean(String var1) {
      return this.mDataHolder.getBoolean(var1, this.mDataRow, this.zaa);
   }

   protected byte[] getByteArray(String var1) {
      return this.mDataHolder.getByteArray(var1, this.mDataRow, this.zaa);
   }

   protected int getDataRow() {
      return this.mDataRow;
   }

   protected double getDouble(String var1) {
      return this.mDataHolder.zab(var1, this.mDataRow, this.zaa);
   }

   protected float getFloat(String var1) {
      return this.mDataHolder.zaa(var1, this.mDataRow, this.zaa);
   }

   protected int getInteger(String var1) {
      return this.mDataHolder.getInteger(var1, this.mDataRow, this.zaa);
   }

   protected long getLong(String var1) {
      return this.mDataHolder.getLong(var1, this.mDataRow, this.zaa);
   }

   protected String getString(String var1) {
      return this.mDataHolder.getString(var1, this.mDataRow, this.zaa);
   }

   public boolean hasColumn(String var1) {
      return this.mDataHolder.hasColumn(var1);
   }

   protected boolean hasNull(String var1) {
      return this.mDataHolder.hasNull(var1, this.mDataRow, this.zaa);
   }

   public int hashCode() {
      return Objects.hashCode(this.mDataRow, this.zaa, this.mDataHolder);
   }

   public boolean isDataValid() {
      return !this.mDataHolder.isClosed();
   }

   protected Uri parseUri(String var1) {
      var1 = this.mDataHolder.getString(var1, this.mDataRow, this.zaa);
      return var1 == null ? null : Uri.parse(var1);
   }

   protected final void zaa(int var1) {
      boolean var2;
      if (var1 >= 0 && var1 < this.mDataHolder.getCount()) {
         var2 = true;
      } else {
         var2 = false;
      }

      Preconditions.checkState(var2);
      this.mDataRow = var1;
      this.zaa = this.mDataHolder.getWindowIndex(var1);
   }
}

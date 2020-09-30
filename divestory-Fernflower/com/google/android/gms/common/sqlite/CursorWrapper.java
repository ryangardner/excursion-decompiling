package com.google.android.gms.common.sqlite;

import android.database.AbstractWindowedCursor;
import android.database.CrossProcessCursor;
import android.database.Cursor;
import android.database.CursorWindow;

public class CursorWrapper extends android.database.CursorWrapper implements CrossProcessCursor {
   private AbstractWindowedCursor zza;

   public CursorWrapper(Cursor var1) {
      super(var1);

      for(int var2 = 0; var2 < 10 && var1 instanceof android.database.CursorWrapper; ++var2) {
         var1 = ((android.database.CursorWrapper)var1).getWrappedCursor();
      }

      if (!(var1 instanceof AbstractWindowedCursor)) {
         String var3 = String.valueOf(var1.getClass().getName());
         if (var3.length() != 0) {
            var3 = "Unknown type: ".concat(var3);
         } else {
            var3 = new String("Unknown type: ");
         }

         throw new IllegalArgumentException(var3);
      } else {
         this.zza = (AbstractWindowedCursor)var1;
      }
   }

   public void fillWindow(int var1, CursorWindow var2) {
      this.zza.fillWindow(var1, var2);
   }

   public CursorWindow getWindow() {
      return this.zza.getWindow();
   }

   // $FF: synthetic method
   public Cursor getWrappedCursor() {
      return this.zza;
   }

   public boolean onMove(int var1, int var2) {
      return this.zza.onMove(var1, var2);
   }

   public void setWindow(CursorWindow var1) {
      this.zza.setWindow(var1);
   }
}

package com.google.android.gms.internal.drive;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;

final class zzmj extends zzmi<FieldDescriptorType, Object> {
   zzmj(int var1) {
      super(var1, (zzmj)null);
   }

   public final void zzbp() {
      if (!this.isImmutable()) {
         for(int var1 = 0; var1 < this.zzer(); ++var1) {
            Entry var2 = this.zzaw(var1);
            if (((zzkd)var2.getKey()).zzcs()) {
               var2.setValue(Collections.unmodifiableList((List)var2.getValue()));
            }
         }

         Iterator var4 = this.zzes().iterator();

         while(var4.hasNext()) {
            Entry var3 = (Entry)var4.next();
            if (((zzkd)var3.getKey()).zzcs()) {
               var3.setValue(Collections.unmodifiableList((List)var3.getValue()));
            }
         }
      }

      super.zzbp();
   }
}

package com.google.android.gms.internal.drive;

import java.io.IOException;

public abstract class zzit<MessageType extends zzit<MessageType, BuilderType>, BuilderType extends zziu<MessageType, BuilderType>> implements zzlq {
   private static boolean zznf;
   protected int zzne = 0;

   public final byte[] toByteArray() {
      try {
         byte[] var1 = new byte[this.zzcx()];
         zzjr var5 = zzjr.zzb(var1);
         this.zzb(var5);
         var5.zzcb();
         return var1;
      } catch (IOException var4) {
         String var3 = this.getClass().getName();
         StringBuilder var2 = new StringBuilder(String.valueOf(var3).length() + 62 + "byte array".length());
         var2.append("Serializing ");
         var2.append(var3);
         var2.append(" to a ");
         var2.append("byte array");
         var2.append(" threw an IOException (should never happen).");
         throw new RuntimeException(var2.toString(), var4);
      }
   }

   public final zzjc zzbl() {
      try {
         zzjk var1 = zzjc.zzu(this.zzcx());
         this.zzb(var1.zzby());
         zzjc var5 = var1.zzbx();
         return var5;
      } catch (IOException var4) {
         String var2 = this.getClass().getName();
         StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 62 + "ByteString".length());
         var3.append("Serializing ");
         var3.append(var2);
         var3.append(" to a ");
         var3.append("ByteString");
         var3.append(" threw an IOException (should never happen).");
         throw new RuntimeException(var3.toString(), var4);
      }
   }

   int zzbm() {
      throw new UnsupportedOperationException();
   }

   void zzo(int var1) {
      throw new UnsupportedOperationException();
   }
}

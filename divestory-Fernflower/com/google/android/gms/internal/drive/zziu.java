package com.google.android.gms.internal.drive;

public abstract class zziu<MessageType extends zzit<MessageType, BuilderType>, BuilderType extends zziu<MessageType, BuilderType>> implements zzlr {
   // $FF: synthetic method
   public Object clone() throws CloneNotSupportedException {
      return this.zzbn();
   }

   protected abstract BuilderType zza(MessageType var1);

   // $FF: synthetic method
   public final zzlr zza(zzlq var1) {
      if (this.zzda().getClass().isInstance(var1)) {
         return this.zza((zzit)var1);
      } else {
         throw new IllegalArgumentException("mergeFrom(MessageLite) can only merge messages of the same type.");
      }
   }

   public abstract BuilderType zzbn();
}

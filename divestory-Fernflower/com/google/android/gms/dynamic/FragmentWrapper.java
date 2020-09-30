package com.google.android.gms.dynamic;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import com.google.android.gms.common.internal.Preconditions;

public final class FragmentWrapper extends IFragmentWrapper.Stub {
   private Fragment zza;

   private FragmentWrapper(Fragment var1) {
      this.zza = var1;
   }

   public static FragmentWrapper wrap(Fragment var0) {
      return var0 != null ? new FragmentWrapper(var0) : null;
   }

   public final IObjectWrapper zza() {
      return ObjectWrapper.wrap(this.zza.getActivity());
   }

   public final void zza(Intent var1) {
      this.zza.startActivity(var1);
   }

   public final void zza(Intent var1, int var2) {
      this.zza.startActivityForResult(var1, var2);
   }

   public final void zza(IObjectWrapper var1) {
      View var2 = (View)ObjectWrapper.unwrap(var1);
      this.zza.registerForContextMenu((View)Preconditions.checkNotNull(var2));
   }

   public final void zza(boolean var1) {
      this.zza.setHasOptionsMenu(var1);
   }

   public final Bundle zzb() {
      return this.zza.getArguments();
   }

   public final void zzb(IObjectWrapper var1) {
      View var2 = (View)ObjectWrapper.unwrap(var1);
      this.zza.unregisterForContextMenu((View)Preconditions.checkNotNull(var2));
   }

   public final void zzb(boolean var1) {
      this.zza.setMenuVisibility(var1);
   }

   public final int zzc() {
      return this.zza.getId();
   }

   public final void zzc(boolean var1) {
      this.zza.setRetainInstance(var1);
   }

   public final IFragmentWrapper zzd() {
      return wrap(this.zza.getParentFragment());
   }

   public final void zzd(boolean var1) {
      this.zza.setUserVisibleHint(var1);
   }

   public final IObjectWrapper zze() {
      return ObjectWrapper.wrap(this.zza.getResources());
   }

   public final boolean zzf() {
      return this.zza.getRetainInstance();
   }

   public final String zzg() {
      return this.zza.getTag();
   }

   public final IFragmentWrapper zzh() {
      return wrap(this.zza.getTargetFragment());
   }

   public final int zzi() {
      return this.zza.getTargetRequestCode();
   }

   public final boolean zzj() {
      return this.zza.getUserVisibleHint();
   }

   public final IObjectWrapper zzk() {
      return ObjectWrapper.wrap(this.zza.getView());
   }

   public final boolean zzl() {
      return this.zza.isAdded();
   }

   public final boolean zzm() {
      return this.zza.isDetached();
   }

   public final boolean zzn() {
      return this.zza.isHidden();
   }

   public final boolean zzo() {
      return this.zza.isInLayout();
   }

   public final boolean zzp() {
      return this.zza.isRemoving();
   }

   public final boolean zzq() {
      return this.zza.isResumed();
   }

   public final boolean zzr() {
      return this.zza.isVisible();
   }
}

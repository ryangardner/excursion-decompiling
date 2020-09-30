/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.View
 */
package com.google.android.gms.dynamic;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.View;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import com.google.android.gms.common.internal.Preconditions;
import com.google.android.gms.dynamic.IFragmentWrapper;
import com.google.android.gms.dynamic.IObjectWrapper;
import com.google.android.gms.dynamic.ObjectWrapper;

public final class SupportFragmentWrapper
extends IFragmentWrapper.Stub {
    private Fragment zza;

    private SupportFragmentWrapper(Fragment fragment) {
        this.zza = fragment;
    }

    public static SupportFragmentWrapper wrap(Fragment fragment) {
        if (fragment == null) return null;
        return new SupportFragmentWrapper(fragment);
    }

    @Override
    public final IObjectWrapper zza() {
        return ObjectWrapper.wrap(this.zza.getActivity());
    }

    @Override
    public final void zza(Intent intent) {
        this.zza.startActivity(intent);
    }

    @Override
    public final void zza(Intent intent, int n) {
        this.zza.startActivityForResult(intent, n);
    }

    @Override
    public final void zza(IObjectWrapper iObjectWrapper) {
        iObjectWrapper = (View)ObjectWrapper.unwrap(iObjectWrapper);
        this.zza.registerForContextMenu((View)Preconditions.checkNotNull(iObjectWrapper));
    }

    @Override
    public final void zza(boolean bl) {
        this.zza.setHasOptionsMenu(bl);
    }

    @Override
    public final Bundle zzb() {
        return this.zza.getArguments();
    }

    @Override
    public final void zzb(IObjectWrapper iObjectWrapper) {
        iObjectWrapper = (View)ObjectWrapper.unwrap(iObjectWrapper);
        this.zza.unregisterForContextMenu((View)Preconditions.checkNotNull(iObjectWrapper));
    }

    @Override
    public final void zzb(boolean bl) {
        this.zza.setMenuVisibility(bl);
    }

    @Override
    public final int zzc() {
        return this.zza.getId();
    }

    @Override
    public final void zzc(boolean bl) {
        this.zza.setRetainInstance(bl);
    }

    @Override
    public final IFragmentWrapper zzd() {
        return SupportFragmentWrapper.wrap(this.zza.getParentFragment());
    }

    @Override
    public final void zzd(boolean bl) {
        this.zza.setUserVisibleHint(bl);
    }

    @Override
    public final IObjectWrapper zze() {
        return ObjectWrapper.wrap(this.zza.getResources());
    }

    @Override
    public final boolean zzf() {
        return this.zza.getRetainInstance();
    }

    @Override
    public final String zzg() {
        return this.zza.getTag();
    }

    @Override
    public final IFragmentWrapper zzh() {
        return SupportFragmentWrapper.wrap(this.zza.getTargetFragment());
    }

    @Override
    public final int zzi() {
        return this.zza.getTargetRequestCode();
    }

    @Override
    public final boolean zzj() {
        return this.zza.getUserVisibleHint();
    }

    @Override
    public final IObjectWrapper zzk() {
        return ObjectWrapper.wrap(this.zza.getView());
    }

    @Override
    public final boolean zzl() {
        return this.zza.isAdded();
    }

    @Override
    public final boolean zzm() {
        return this.zza.isDetached();
    }

    @Override
    public final boolean zzn() {
        return this.zza.isHidden();
    }

    @Override
    public final boolean zzo() {
        return this.zza.isInLayout();
    }

    @Override
    public final boolean zzp() {
        return this.zza.isRemoving();
    }

    @Override
    public final boolean zzq() {
        return this.zza.isResumed();
    }

    @Override
    public final boolean zzr() {
        return this.zza.isVisible();
    }
}


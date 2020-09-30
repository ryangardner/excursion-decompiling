/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.drive.events;

import com.google.android.gms.drive.DriveContents;

public abstract class OpenFileCallback {
    public abstract void onContents(DriveContents var1);

    public abstract void onError(Exception var1);

    public abstract void onProgress(long var1, long var3);
}


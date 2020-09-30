/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package androidx.media;

import android.os.Bundle;
import androidx.versionedparcelable.VersionedParcelable;

interface AudioAttributesImpl
extends VersionedParcelable {
    public Object getAudioAttributes();

    public int getContentType();

    public int getFlags();

    public int getLegacyStreamType();

    public int getRawLegacyStreamType();

    public int getUsage();

    public int getVolumeControlStream();

    public Bundle toBundle();
}


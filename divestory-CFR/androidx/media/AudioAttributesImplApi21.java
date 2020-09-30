/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.media.AudioAttributes
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 */
package androidx.media;

import android.media.AudioAttributes;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import androidx.media.AudioAttributesCompat;
import androidx.media.AudioAttributesImpl;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class AudioAttributesImplApi21
implements AudioAttributesImpl {
    private static final String TAG = "AudioAttributesCompat21";
    static Method sAudioAttributesToLegacyStreamType;
    AudioAttributes mAudioAttributes;
    int mLegacyStreamType = -1;

    AudioAttributesImplApi21() {
    }

    AudioAttributesImplApi21(AudioAttributes audioAttributes) {
        this(audioAttributes, -1);
    }

    AudioAttributesImplApi21(AudioAttributes audioAttributes, int n) {
        this.mAudioAttributes = audioAttributes;
        this.mLegacyStreamType = n;
    }

    public static AudioAttributesImpl fromBundle(Bundle bundle) {
        if (bundle == null) {
            return null;
        }
        AudioAttributes audioAttributes = (AudioAttributes)bundle.getParcelable("androidx.media.audio_attrs.FRAMEWORKS");
        if (audioAttributes != null) return new AudioAttributesImplApi21(audioAttributes, bundle.getInt("androidx.media.audio_attrs.LEGACY_STREAM_TYPE", -1));
        return null;
    }

    static Method getAudioAttributesToLegacyStreamTypeMethod() {
        try {
            if (sAudioAttributesToLegacyStreamType != null) return sAudioAttributesToLegacyStreamType;
            sAudioAttributesToLegacyStreamType = AudioAttributes.class.getMethod("toLegacyStreamType", AudioAttributes.class);
            return sAudioAttributesToLegacyStreamType;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return null;
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof AudioAttributesImplApi21)) {
            return false;
        }
        object = (AudioAttributesImplApi21)object;
        return this.mAudioAttributes.equals((Object)((AudioAttributesImplApi21)object).mAudioAttributes);
    }

    @Override
    public Object getAudioAttributes() {
        return this.mAudioAttributes;
    }

    @Override
    public int getContentType() {
        return this.mAudioAttributes.getContentType();
    }

    @Override
    public int getFlags() {
        return this.mAudioAttributes.getFlags();
    }

    /*
     * WARNING - void declaration
     */
    @Override
    public int getLegacyStreamType() {
        void var2_5;
        int n = this.mLegacyStreamType;
        if (n != -1) {
            return n;
        }
        Object object = AudioAttributesImplApi21.getAudioAttributesToLegacyStreamTypeMethod();
        if (object == null) {
            object = new StringBuilder();
            ((StringBuilder)object).append("No AudioAttributes#toLegacyStreamType() on API: ");
            ((StringBuilder)object).append(Build.VERSION.SDK_INT);
            Log.w((String)TAG, (String)((StringBuilder)object).toString());
            return -1;
        }
        try {
            return (Integer)((Method)object).invoke(null, new Object[]{this.mAudioAttributes});
        }
        catch (IllegalAccessException illegalAccessException) {
        }
        catch (InvocationTargetException invocationTargetException) {
            // empty catch block
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("getLegacyStreamType() failed on API: ");
        stringBuilder.append(Build.VERSION.SDK_INT);
        Log.w((String)TAG, (String)stringBuilder.toString(), (Throwable)var2_5);
        return -1;
    }

    @Override
    public int getRawLegacyStreamType() {
        return this.mLegacyStreamType;
    }

    @Override
    public int getUsage() {
        return this.mAudioAttributes.getUsage();
    }

    @Override
    public int getVolumeControlStream() {
        if (Build.VERSION.SDK_INT < 26) return AudioAttributesCompat.toVolumeStreamType(true, this.getFlags(), this.getUsage());
        return this.mAudioAttributes.getVolumeControlStream();
    }

    public int hashCode() {
        return this.mAudioAttributes.hashCode();
    }

    @Override
    public Bundle toBundle() {
        Bundle bundle = new Bundle();
        bundle.putParcelable("androidx.media.audio_attrs.FRAMEWORKS", (Parcelable)this.mAudioAttributes);
        int n = this.mLegacyStreamType;
        if (n == -1) return bundle;
        bundle.putInt("androidx.media.audio_attrs.LEGACY_STREAM_TYPE", n);
        return bundle;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("AudioAttributesCompat: audioattributes=");
        stringBuilder.append((Object)this.mAudioAttributes);
        return stringBuilder.toString();
    }
}


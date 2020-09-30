/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 *  android.util.Log
 */
package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompatApi21;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import androidx.collection.SimpleArrayMap;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.Set;

public final class MediaMetadataCompat
implements Parcelable {
    public static final Parcelable.Creator<MediaMetadataCompat> CREATOR;
    static final ArrayMap<String, Integer> METADATA_KEYS_TYPE;
    public static final String METADATA_KEY_ADVERTISEMENT = "android.media.metadata.ADVERTISEMENT";
    public static final String METADATA_KEY_ALBUM = "android.media.metadata.ALBUM";
    public static final String METADATA_KEY_ALBUM_ART = "android.media.metadata.ALBUM_ART";
    public static final String METADATA_KEY_ALBUM_ARTIST = "android.media.metadata.ALBUM_ARTIST";
    public static final String METADATA_KEY_ALBUM_ART_URI = "android.media.metadata.ALBUM_ART_URI";
    public static final String METADATA_KEY_ART = "android.media.metadata.ART";
    public static final String METADATA_KEY_ARTIST = "android.media.metadata.ARTIST";
    public static final String METADATA_KEY_ART_URI = "android.media.metadata.ART_URI";
    public static final String METADATA_KEY_AUTHOR = "android.media.metadata.AUTHOR";
    public static final String METADATA_KEY_BT_FOLDER_TYPE = "android.media.metadata.BT_FOLDER_TYPE";
    public static final String METADATA_KEY_COMPILATION = "android.media.metadata.COMPILATION";
    public static final String METADATA_KEY_COMPOSER = "android.media.metadata.COMPOSER";
    public static final String METADATA_KEY_DATE = "android.media.metadata.DATE";
    public static final String METADATA_KEY_DISC_NUMBER = "android.media.metadata.DISC_NUMBER";
    public static final String METADATA_KEY_DISPLAY_DESCRIPTION = "android.media.metadata.DISPLAY_DESCRIPTION";
    public static final String METADATA_KEY_DISPLAY_ICON = "android.media.metadata.DISPLAY_ICON";
    public static final String METADATA_KEY_DISPLAY_ICON_URI = "android.media.metadata.DISPLAY_ICON_URI";
    public static final String METADATA_KEY_DISPLAY_SUBTITLE = "android.media.metadata.DISPLAY_SUBTITLE";
    public static final String METADATA_KEY_DISPLAY_TITLE = "android.media.metadata.DISPLAY_TITLE";
    public static final String METADATA_KEY_DOWNLOAD_STATUS = "android.media.metadata.DOWNLOAD_STATUS";
    public static final String METADATA_KEY_DURATION = "android.media.metadata.DURATION";
    public static final String METADATA_KEY_GENRE = "android.media.metadata.GENRE";
    public static final String METADATA_KEY_MEDIA_ID = "android.media.metadata.MEDIA_ID";
    public static final String METADATA_KEY_MEDIA_URI = "android.media.metadata.MEDIA_URI";
    public static final String METADATA_KEY_NUM_TRACKS = "android.media.metadata.NUM_TRACKS";
    public static final String METADATA_KEY_RATING = "android.media.metadata.RATING";
    public static final String METADATA_KEY_TITLE = "android.media.metadata.TITLE";
    public static final String METADATA_KEY_TRACK_NUMBER = "android.media.metadata.TRACK_NUMBER";
    public static final String METADATA_KEY_USER_RATING = "android.media.metadata.USER_RATING";
    public static final String METADATA_KEY_WRITER = "android.media.metadata.WRITER";
    public static final String METADATA_KEY_YEAR = "android.media.metadata.YEAR";
    static final int METADATA_TYPE_BITMAP = 2;
    static final int METADATA_TYPE_LONG = 0;
    static final int METADATA_TYPE_RATING = 3;
    static final int METADATA_TYPE_TEXT = 1;
    private static final String[] PREFERRED_BITMAP_ORDER;
    private static final String[] PREFERRED_DESCRIPTION_ORDER;
    private static final String[] PREFERRED_URI_ORDER;
    private static final String TAG = "MediaMetadata";
    final Bundle mBundle;
    private MediaDescriptionCompat mDescription;
    private Object mMetadataObj;

    static {
        Object object = new ArrayMap<String, Integer>();
        METADATA_KEYS_TYPE = object;
        Integer n = 1;
        ((SimpleArrayMap)object).put(METADATA_KEY_TITLE, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ARTIST, n);
        Object object2 = METADATA_KEYS_TYPE;
        object = 0;
        ((SimpleArrayMap)object2).put((String)METADATA_KEY_DURATION, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_AUTHOR, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_WRITER, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPOSER, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_COMPILATION, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DATE, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_YEAR, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_GENRE, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_TRACK_NUMBER, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_NUM_TRACKS, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISC_NUMBER, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ARTIST, n);
        Object object3 = METADATA_KEYS_TYPE;
        object2 = 2;
        ((SimpleArrayMap)object3).put((String)METADATA_KEY_ART, (Integer)object2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ART_URI, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART, (Integer)object2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ALBUM_ART_URI, n);
        ArrayMap<String, Integer> arrayMap = METADATA_KEYS_TYPE;
        object3 = 3;
        arrayMap.put(METADATA_KEY_USER_RATING, (Integer)object3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_RATING, (Integer)object3);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_TITLE, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_SUBTITLE, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_DESCRIPTION, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON, (Integer)object2);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DISPLAY_ICON_URI, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_ID, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_BT_FOLDER_TYPE, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_MEDIA_URI, n);
        METADATA_KEYS_TYPE.put(METADATA_KEY_ADVERTISEMENT, (Integer)object);
        METADATA_KEYS_TYPE.put(METADATA_KEY_DOWNLOAD_STATUS, (Integer)object);
        PREFERRED_DESCRIPTION_ORDER = new String[]{METADATA_KEY_TITLE, METADATA_KEY_ARTIST, METADATA_KEY_ALBUM, METADATA_KEY_ALBUM_ARTIST, METADATA_KEY_WRITER, METADATA_KEY_AUTHOR, METADATA_KEY_COMPOSER};
        PREFERRED_BITMAP_ORDER = new String[]{METADATA_KEY_DISPLAY_ICON, METADATA_KEY_ART, METADATA_KEY_ALBUM_ART};
        PREFERRED_URI_ORDER = new String[]{METADATA_KEY_DISPLAY_ICON_URI, METADATA_KEY_ART_URI, METADATA_KEY_ALBUM_ART_URI};
        CREATOR = new Parcelable.Creator<MediaMetadataCompat>(){

            public MediaMetadataCompat createFromParcel(Parcel parcel) {
                return new MediaMetadataCompat(parcel);
            }

            public MediaMetadataCompat[] newArray(int n) {
                return new MediaMetadataCompat[n];
            }
        };
    }

    MediaMetadataCompat(Bundle bundle) {
        this.mBundle = bundle = new Bundle(bundle);
        MediaSessionCompat.ensureClassLoader(bundle);
    }

    MediaMetadataCompat(Parcel parcel) {
        this.mBundle = parcel.readBundle(MediaSessionCompat.class.getClassLoader());
    }

    public static MediaMetadataCompat fromMediaMetadata(Object object) {
        if (object == null) return null;
        if (Build.VERSION.SDK_INT < 21) return null;
        Parcel parcel = Parcel.obtain();
        MediaMetadataCompatApi21.writeToParcel(object, parcel, 0);
        parcel.setDataPosition(0);
        MediaMetadataCompat mediaMetadataCompat = (MediaMetadataCompat)CREATOR.createFromParcel(parcel);
        parcel.recycle();
        mediaMetadataCompat.mMetadataObj = object;
        return mediaMetadataCompat;
    }

    public boolean containsKey(String string2) {
        return this.mBundle.containsKey(string2);
    }

    public int describeContents() {
        return 0;
    }

    public Bitmap getBitmap(String string2) {
        try {
            return (Bitmap)this.mBundle.getParcelable(string2);
        }
        catch (Exception exception) {
            Log.w((String)TAG, (String)"Failed to retrieve a key as Bitmap.", (Throwable)exception);
            return null;
        }
    }

    public Bundle getBundle() {
        return new Bundle(this.mBundle);
    }

    public MediaDescriptionCompat getDescription() {
        String string2;
        Uri uri;
        Object object;
        Uri uri2;
        CharSequence[] arrcharSequence;
        block13 : {
            int n;
            block12 : {
                int n2;
                object = this.mDescription;
                if (object != null) {
                    return object;
                }
                string2 = this.getString(METADATA_KEY_MEDIA_ID);
                arrcharSequence = new CharSequence[3];
                object = this.getText(METADATA_KEY_DISPLAY_TITLE);
                if (!TextUtils.isEmpty((CharSequence)object)) {
                    arrcharSequence[0] = object;
                    arrcharSequence[1] = this.getText(METADATA_KEY_DISPLAY_SUBTITLE);
                    arrcharSequence[2] = this.getText(METADATA_KEY_DISPLAY_DESCRIPTION);
                } else {
                    n2 = 0;
                    for (n = 0; n2 < 3 && n < ((String[])(object = PREFERRED_DESCRIPTION_ORDER)).length; ++n) {
                        object = this.getText(object[n]);
                        int n3 = n2;
                        if (!TextUtils.isEmpty((CharSequence)object)) {
                            arrcharSequence[n2] = object;
                            n3 = n2 + 1;
                        }
                        n2 = n3;
                    }
                }
                n = 0;
                do {
                    object = PREFERRED_BITMAP_ORDER;
                    n2 = ((String[])object).length;
                    uri = null;
                    if (n >= n2) break;
                    if ((object = this.getBitmap(object[n])) == null) {
                        ++n;
                        continue;
                    }
                    break block12;
                    break;
                } while (true);
                object = null;
            }
            for (n = 0; n < ((String[])(uri2 = PREFERRED_URI_ORDER)).length; ++n) {
                if (TextUtils.isEmpty((CharSequence)(uri2 = this.getString(uri2[n])))) continue;
                uri2 = Uri.parse((String)uri2);
                break block13;
            }
            uri2 = null;
        }
        Object object2 = this.getString(METADATA_KEY_MEDIA_URI);
        if (!TextUtils.isEmpty((CharSequence)object2)) {
            uri = Uri.parse((String)object2);
        }
        object2 = new MediaDescriptionCompat.Builder();
        ((MediaDescriptionCompat.Builder)object2).setMediaId(string2);
        ((MediaDescriptionCompat.Builder)object2).setTitle(arrcharSequence[0]);
        ((MediaDescriptionCompat.Builder)object2).setSubtitle(arrcharSequence[1]);
        ((MediaDescriptionCompat.Builder)object2).setDescription(arrcharSequence[2]);
        ((MediaDescriptionCompat.Builder)object2).setIconBitmap((Bitmap)object);
        ((MediaDescriptionCompat.Builder)object2).setIconUri(uri2);
        ((MediaDescriptionCompat.Builder)object2).setMediaUri(uri);
        object = new Bundle();
        if (this.mBundle.containsKey(METADATA_KEY_BT_FOLDER_TYPE)) {
            object.putLong("android.media.extra.BT_FOLDER_TYPE", this.getLong(METADATA_KEY_BT_FOLDER_TYPE));
        }
        if (this.mBundle.containsKey(METADATA_KEY_DOWNLOAD_STATUS)) {
            object.putLong("android.media.extra.DOWNLOAD_STATUS", this.getLong(METADATA_KEY_DOWNLOAD_STATUS));
        }
        if (!object.isEmpty()) {
            ((MediaDescriptionCompat.Builder)object2).setExtras((Bundle)object);
        }
        object = ((MediaDescriptionCompat.Builder)object2).build();
        this.mDescription = object;
        return object;
    }

    public long getLong(String string2) {
        return this.mBundle.getLong(string2, 0L);
    }

    public Object getMediaMetadata() {
        if (this.mMetadataObj != null) return this.mMetadataObj;
        if (Build.VERSION.SDK_INT < 21) return this.mMetadataObj;
        Parcel parcel = Parcel.obtain();
        this.writeToParcel(parcel, 0);
        parcel.setDataPosition(0);
        this.mMetadataObj = MediaMetadataCompatApi21.createFromParcel(parcel);
        parcel.recycle();
        return this.mMetadataObj;
    }

    public RatingCompat getRating(String object) {
        try {
            if (Build.VERSION.SDK_INT < 19) return (RatingCompat)this.mBundle.getParcelable((String)object);
            return RatingCompat.fromRating((Object)this.mBundle.getParcelable((String)object));
        }
        catch (Exception exception) {
            Log.w((String)TAG, (String)"Failed to retrieve a key as Rating.", (Throwable)exception);
            return null;
        }
    }

    public String getString(String charSequence) {
        if ((charSequence = this.mBundle.getCharSequence((String)charSequence)) == null) return null;
        return charSequence.toString();
    }

    public CharSequence getText(String string2) {
        return this.mBundle.getCharSequence(string2);
    }

    public Set<String> keySet() {
        return this.mBundle.keySet();
    }

    public int size() {
        return this.mBundle.size();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeBundle(this.mBundle);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BitmapKey {
    }

    public static final class Builder {
        private final Bundle mBundle;

        public Builder() {
            this.mBundle = new Bundle();
        }

        public Builder(MediaMetadataCompat mediaMetadataCompat) {
            mediaMetadataCompat = new Bundle(mediaMetadataCompat.mBundle);
            this.mBundle = mediaMetadataCompat;
            MediaSessionCompat.ensureClassLoader((Bundle)mediaMetadataCompat);
        }

        public Builder(MediaMetadataCompat object, int n) {
            this((MediaMetadataCompat)object);
            object = this.mBundle.keySet().iterator();
            while (object.hasNext()) {
                String string2 = (String)object.next();
                Object object2 = this.mBundle.get(string2);
                if (!(object2 instanceof Bitmap) || (object2 = (Bitmap)object2).getHeight() <= n && object2.getWidth() <= n) continue;
                this.putBitmap(string2, this.scaleBitmap((Bitmap)object2, n));
            }
        }

        private Bitmap scaleBitmap(Bitmap bitmap, int n) {
            float f = n;
            f = Math.min(f / (float)bitmap.getWidth(), f / (float)bitmap.getHeight());
            n = (int)((float)bitmap.getHeight() * f);
            return Bitmap.createScaledBitmap((Bitmap)bitmap, (int)((int)((float)bitmap.getWidth() * f)), (int)n, (boolean)true);
        }

        public MediaMetadataCompat build() {
            return new MediaMetadataCompat(this.mBundle);
        }

        public Builder putBitmap(String string2, Bitmap object) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 2) {
                object = new StringBuilder();
                ((StringBuilder)object).append("The ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" key cannot be used to put a Bitmap");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            this.mBundle.putParcelable(string2, (Parcelable)object);
            return this;
        }

        public Builder putLong(String string2, long l) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 0) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("The ");
                stringBuilder.append(string2);
                stringBuilder.append(" key cannot be used to put a long");
                throw new IllegalArgumentException(stringBuilder.toString());
            }
            this.mBundle.putLong(string2, l);
            return this;
        }

        public Builder putRating(String string2, RatingCompat object) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 3) {
                object = new StringBuilder();
                ((StringBuilder)object).append("The ");
                ((StringBuilder)object).append(string2);
                ((StringBuilder)object).append(" key cannot be used to put a Rating");
                throw new IllegalArgumentException(((StringBuilder)object).toString());
            }
            if (Build.VERSION.SDK_INT >= 19) {
                this.mBundle.putParcelable(string2, (Parcelable)((RatingCompat)object).getRating());
                return this;
            }
            this.mBundle.putParcelable(string2, (Parcelable)object);
            return this;
        }

        public Builder putString(String string2, String charSequence) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 1) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("The ");
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(" key cannot be used to put a String");
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            this.mBundle.putCharSequence(string2, charSequence);
            return this;
        }

        public Builder putText(String string2, CharSequence charSequence) {
            if (METADATA_KEYS_TYPE.containsKey(string2) && (Integer)METADATA_KEYS_TYPE.get(string2) != 1) {
                charSequence = new StringBuilder();
                ((StringBuilder)charSequence).append("The ");
                ((StringBuilder)charSequence).append(string2);
                ((StringBuilder)charSequence).append(" key cannot be used to put a CharSequence");
                throw new IllegalArgumentException(((StringBuilder)charSequence).toString());
            }
            this.mBundle.putCharSequence(string2, charSequence);
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface LongKey {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface RatingKey {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface TextKey {
    }

}


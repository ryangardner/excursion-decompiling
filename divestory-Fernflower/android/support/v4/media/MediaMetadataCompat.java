package android.support.v4.media;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.os.Parcelable.Creator;
import android.support.v4.media.session.MediaSessionCompat;
import android.text.TextUtils;
import android.util.Log;
import androidx.collection.ArrayMap;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Iterator;
import java.util.Set;

public final class MediaMetadataCompat implements Parcelable {
   public static final Creator<MediaMetadataCompat> CREATOR;
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
      ArrayMap var0 = new ArrayMap();
      METADATA_KEYS_TYPE = var0;
      Integer var1 = 1;
      var0.put("android.media.metadata.TITLE", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.ARTIST", var1);
      ArrayMap var2 = METADATA_KEYS_TYPE;
      Integer var5 = 0;
      var2.put("android.media.metadata.DURATION", var5);
      METADATA_KEYS_TYPE.put("android.media.metadata.ALBUM", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.AUTHOR", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.WRITER", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.COMPOSER", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.COMPILATION", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.DATE", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.YEAR", var5);
      METADATA_KEYS_TYPE.put("android.media.metadata.GENRE", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.TRACK_NUMBER", var5);
      METADATA_KEYS_TYPE.put("android.media.metadata.NUM_TRACKS", var5);
      METADATA_KEYS_TYPE.put("android.media.metadata.DISC_NUMBER", var5);
      METADATA_KEYS_TYPE.put("android.media.metadata.ALBUM_ARTIST", var1);
      ArrayMap var3 = METADATA_KEYS_TYPE;
      Integer var6 = 2;
      var3.put("android.media.metadata.ART", var6);
      METADATA_KEYS_TYPE.put("android.media.metadata.ART_URI", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.ALBUM_ART", var6);
      METADATA_KEYS_TYPE.put("android.media.metadata.ALBUM_ART_URI", var1);
      ArrayMap var4 = METADATA_KEYS_TYPE;
      Integer var7 = 3;
      var4.put("android.media.metadata.USER_RATING", var7);
      METADATA_KEYS_TYPE.put("android.media.metadata.RATING", var7);
      METADATA_KEYS_TYPE.put("android.media.metadata.DISPLAY_TITLE", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.DISPLAY_SUBTITLE", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.DISPLAY_DESCRIPTION", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.DISPLAY_ICON", var6);
      METADATA_KEYS_TYPE.put("android.media.metadata.DISPLAY_ICON_URI", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.MEDIA_ID", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.BT_FOLDER_TYPE", var5);
      METADATA_KEYS_TYPE.put("android.media.metadata.MEDIA_URI", var1);
      METADATA_KEYS_TYPE.put("android.media.metadata.ADVERTISEMENT", var5);
      METADATA_KEYS_TYPE.put("android.media.metadata.DOWNLOAD_STATUS", var5);
      PREFERRED_DESCRIPTION_ORDER = new String[]{"android.media.metadata.TITLE", "android.media.metadata.ARTIST", "android.media.metadata.ALBUM", "android.media.metadata.ALBUM_ARTIST", "android.media.metadata.WRITER", "android.media.metadata.AUTHOR", "android.media.metadata.COMPOSER"};
      PREFERRED_BITMAP_ORDER = new String[]{"android.media.metadata.DISPLAY_ICON", "android.media.metadata.ART", "android.media.metadata.ALBUM_ART"};
      PREFERRED_URI_ORDER = new String[]{"android.media.metadata.DISPLAY_ICON_URI", "android.media.metadata.ART_URI", "android.media.metadata.ALBUM_ART_URI"};
      CREATOR = new Creator<MediaMetadataCompat>() {
         public MediaMetadataCompat createFromParcel(Parcel var1) {
            return new MediaMetadataCompat(var1);
         }

         public MediaMetadataCompat[] newArray(int var1) {
            return new MediaMetadataCompat[var1];
         }
      };
   }

   MediaMetadataCompat(Bundle var1) {
      var1 = new Bundle(var1);
      this.mBundle = var1;
      MediaSessionCompat.ensureClassLoader(var1);
   }

   MediaMetadataCompat(Parcel var1) {
      this.mBundle = var1.readBundle(MediaSessionCompat.class.getClassLoader());
   }

   public static MediaMetadataCompat fromMediaMetadata(Object var0) {
      if (var0 != null && VERSION.SDK_INT >= 21) {
         Parcel var1 = Parcel.obtain();
         MediaMetadataCompatApi21.writeToParcel(var0, var1, 0);
         var1.setDataPosition(0);
         MediaMetadataCompat var2 = (MediaMetadataCompat)CREATOR.createFromParcel(var1);
         var1.recycle();
         var2.mMetadataObj = var0;
         return var2;
      } else {
         return null;
      }
   }

   public boolean containsKey(String var1) {
      return this.mBundle.containsKey(var1);
   }

   public int describeContents() {
      return 0;
   }

   public Bitmap getBitmap(String var1) {
      Bitmap var3;
      try {
         var3 = (Bitmap)this.mBundle.getParcelable(var1);
      } catch (Exception var2) {
         Log.w("MediaMetadata", "Failed to retrieve a key as Bitmap.", var2);
         var3 = null;
      }

      return var3;
   }

   public Bundle getBundle() {
      return new Bundle(this.mBundle);
   }

   public MediaDescriptionCompat getDescription() {
      MediaDescriptionCompat var1 = this.mDescription;
      if (var1 != null) {
         return var1;
      } else {
         String var2 = this.getString("android.media.metadata.MEDIA_ID");
         CharSequence[] var3 = new CharSequence[3];
         CharSequence var10 = this.getText("android.media.metadata.DISPLAY_TITLE");
         int var4;
         int var5;
         String[] var11;
         if (!TextUtils.isEmpty(var10)) {
            var3[0] = var10;
            var3[1] = this.getText("android.media.metadata.DISPLAY_SUBTITLE");
            var3[2] = this.getText("android.media.metadata.DISPLAY_DESCRIPTION");
         } else {
            var4 = 0;

            int var6;
            for(var5 = 0; var4 < 3; var4 = var6) {
               var11 = PREFERRED_DESCRIPTION_ORDER;
               if (var5 >= var11.length) {
                  break;
               }

               var10 = this.getText(var11[var5]);
               var6 = var4;
               if (!TextUtils.isEmpty(var10)) {
                  var3[var4] = var10;
                  var6 = var4 + 1;
               }

               ++var5;
            }
         }

         var5 = 0;

         Uri var7;
         Bitmap var12;
         while(true) {
            var11 = PREFERRED_BITMAP_ORDER;
            var4 = var11.length;
            var7 = null;
            if (var5 >= var4) {
               var12 = null;
               break;
            }

            var12 = this.getBitmap(var11[var5]);
            if (var12 != null) {
               break;
            }

            ++var5;
         }

         var5 = 0;

         Uri var15;
         while(true) {
            String[] var8 = PREFERRED_URI_ORDER;
            if (var5 >= var8.length) {
               var15 = null;
               break;
            }

            String var14 = this.getString(var8[var5]);
            if (!TextUtils.isEmpty(var14)) {
               var15 = Uri.parse(var14);
               break;
            }

            ++var5;
         }

         String var9 = this.getString("android.media.metadata.MEDIA_URI");
         if (!TextUtils.isEmpty(var9)) {
            var7 = Uri.parse(var9);
         }

         MediaDescriptionCompat.Builder var16 = new MediaDescriptionCompat.Builder();
         var16.setMediaId(var2);
         var16.setTitle(var3[0]);
         var16.setSubtitle(var3[1]);
         var16.setDescription(var3[2]);
         var16.setIconBitmap(var12);
         var16.setIconUri(var15);
         var16.setMediaUri(var7);
         Bundle var13 = new Bundle();
         if (this.mBundle.containsKey("android.media.metadata.BT_FOLDER_TYPE")) {
            var13.putLong("android.media.extra.BT_FOLDER_TYPE", this.getLong("android.media.metadata.BT_FOLDER_TYPE"));
         }

         if (this.mBundle.containsKey("android.media.metadata.DOWNLOAD_STATUS")) {
            var13.putLong("android.media.extra.DOWNLOAD_STATUS", this.getLong("android.media.metadata.DOWNLOAD_STATUS"));
         }

         if (!var13.isEmpty()) {
            var16.setExtras(var13);
         }

         var1 = var16.build();
         this.mDescription = var1;
         return var1;
      }
   }

   public long getLong(String var1) {
      return this.mBundle.getLong(var1, 0L);
   }

   public Object getMediaMetadata() {
      if (this.mMetadataObj == null && VERSION.SDK_INT >= 21) {
         Parcel var1 = Parcel.obtain();
         this.writeToParcel(var1, 0);
         var1.setDataPosition(0);
         this.mMetadataObj = MediaMetadataCompatApi21.createFromParcel(var1);
         var1.recycle();
      }

      return this.mMetadataObj;
   }

   public RatingCompat getRating(String var1) {
      RatingCompat var3;
      try {
         if (VERSION.SDK_INT >= 19) {
            var3 = RatingCompat.fromRating(this.mBundle.getParcelable(var1));
         } else {
            var3 = (RatingCompat)this.mBundle.getParcelable(var1);
         }
      } catch (Exception var2) {
         Log.w("MediaMetadata", "Failed to retrieve a key as Rating.", var2);
         var3 = null;
      }

      return var3;
   }

   public String getString(String var1) {
      CharSequence var2 = this.mBundle.getCharSequence(var1);
      return var2 != null ? var2.toString() : null;
   }

   public CharSequence getText(String var1) {
      return this.mBundle.getCharSequence(var1);
   }

   public Set<String> keySet() {
      return this.mBundle.keySet();
   }

   public int size() {
      return this.mBundle.size();
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeBundle(this.mBundle);
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface BitmapKey {
   }

   public static final class Builder {
      private final Bundle mBundle;

      public Builder() {
         this.mBundle = new Bundle();
      }

      public Builder(MediaMetadataCompat var1) {
         Bundle var2 = new Bundle(var1.mBundle);
         this.mBundle = var2;
         MediaSessionCompat.ensureClassLoader(var2);
      }

      public Builder(MediaMetadataCompat var1, int var2) {
         this(var1);
         Iterator var5 = this.mBundle.keySet().iterator();

         while(true) {
            String var3;
            Bitmap var6;
            do {
               Object var4;
               do {
                  if (!var5.hasNext()) {
                     return;
                  }

                  var3 = (String)var5.next();
                  var4 = this.mBundle.get(var3);
               } while(!(var4 instanceof Bitmap));

               var6 = (Bitmap)var4;
            } while(var6.getHeight() <= var2 && var6.getWidth() <= var2);

            this.putBitmap(var3, this.scaleBitmap(var6, var2));
         }
      }

      private Bitmap scaleBitmap(Bitmap var1, int var2) {
         float var3 = (float)var2;
         var3 = Math.min(var3 / (float)var1.getWidth(), var3 / (float)var1.getHeight());
         var2 = (int)((float)var1.getHeight() * var3);
         return Bitmap.createScaledBitmap(var1, (int)((float)var1.getWidth() * var3), var2, true);
      }

      public MediaMetadataCompat build() {
         return new MediaMetadataCompat(this.mBundle);
      }

      public MediaMetadataCompat.Builder putBitmap(String var1, Bitmap var2) {
         if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(var1) && (Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(var1) != 2) {
            StringBuilder var3 = new StringBuilder();
            var3.append("The ");
            var3.append(var1);
            var3.append(" key cannot be used to put a Bitmap");
            throw new IllegalArgumentException(var3.toString());
         } else {
            this.mBundle.putParcelable(var1, var2);
            return this;
         }
      }

      public MediaMetadataCompat.Builder putLong(String var1, long var2) {
         if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(var1) && (Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(var1) != 0) {
            StringBuilder var4 = new StringBuilder();
            var4.append("The ");
            var4.append(var1);
            var4.append(" key cannot be used to put a long");
            throw new IllegalArgumentException(var4.toString());
         } else {
            this.mBundle.putLong(var1, var2);
            return this;
         }
      }

      public MediaMetadataCompat.Builder putRating(String var1, RatingCompat var2) {
         if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(var1) && (Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(var1) != 3) {
            StringBuilder var3 = new StringBuilder();
            var3.append("The ");
            var3.append(var1);
            var3.append(" key cannot be used to put a Rating");
            throw new IllegalArgumentException(var3.toString());
         } else {
            if (VERSION.SDK_INT >= 19) {
               this.mBundle.putParcelable(var1, (Parcelable)var2.getRating());
            } else {
               this.mBundle.putParcelable(var1, var2);
            }

            return this;
         }
      }

      public MediaMetadataCompat.Builder putString(String var1, String var2) {
         if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(var1) && (Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(var1) != 1) {
            StringBuilder var3 = new StringBuilder();
            var3.append("The ");
            var3.append(var1);
            var3.append(" key cannot be used to put a String");
            throw new IllegalArgumentException(var3.toString());
         } else {
            this.mBundle.putCharSequence(var1, var2);
            return this;
         }
      }

      public MediaMetadataCompat.Builder putText(String var1, CharSequence var2) {
         if (MediaMetadataCompat.METADATA_KEYS_TYPE.containsKey(var1) && (Integer)MediaMetadataCompat.METADATA_KEYS_TYPE.get(var1) != 1) {
            StringBuilder var3 = new StringBuilder();
            var3.append("The ");
            var3.append(var1);
            var3.append(" key cannot be used to put a CharSequence");
            throw new IllegalArgumentException(var3.toString());
         } else {
            this.mBundle.putCharSequence(var1, var2);
            return this;
         }
      }
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface LongKey {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface RatingKey {
   }

   @Retention(RetentionPolicy.SOURCE)
   public @interface TextKey {
   }
}

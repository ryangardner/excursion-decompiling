/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.media.Rating
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.util.Log
 */
package android.support.v4.media;

import android.media.Rating;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public final class RatingCompat
implements Parcelable {
    public static final Parcelable.Creator<RatingCompat> CREATOR = new Parcelable.Creator<RatingCompat>(){

        public RatingCompat createFromParcel(Parcel parcel) {
            return new RatingCompat(parcel.readInt(), parcel.readFloat());
        }

        public RatingCompat[] newArray(int n) {
            return new RatingCompat[n];
        }
    };
    public static final int RATING_3_STARS = 3;
    public static final int RATING_4_STARS = 4;
    public static final int RATING_5_STARS = 5;
    public static final int RATING_HEART = 1;
    public static final int RATING_NONE = 0;
    private static final float RATING_NOT_RATED = -1.0f;
    public static final int RATING_PERCENTAGE = 6;
    public static final int RATING_THUMB_UP_DOWN = 2;
    private static final String TAG = "Rating";
    private Object mRatingObj;
    private final int mRatingStyle;
    private final float mRatingValue;

    RatingCompat(int n, float f) {
        this.mRatingStyle = n;
        this.mRatingValue = f;
    }

    /*
     * Unable to fully structure code
     */
    public static RatingCompat fromRating(Object var0) {
        var2_2 = var1_1 = null;
        if (var0 == null) return var2_11;
        var2_3 = var1_1;
        if (Build.VERSION.SDK_INT < 19) return var2_11;
        var2_4 = (Rating)var0;
        var3_12 = var2_4.getRatingStyle();
        if (var2_4.isRated()) {
            switch (var3_12) {
                default: {
                    return null;
                }
                case 6: {
                    var2_5 = RatingCompat.newPercentageRating(var2_4.getPercentRating());
                    ** break;
                }
                case 3: 
                case 4: 
                case 5: {
                    var2_6 = RatingCompat.newStarRating(var3_12, var2_4.getStarRating());
                    ** break;
                }
                case 2: {
                    var2_7 = RatingCompat.newThumbRating(var2_4.isThumbUp());
                    ** break;
                }
                case 1: 
            }
            var2_8 = RatingCompat.newHeartRating(var2_4.hasHeart());
            ** break;
lbl23: // 4 sources:
        } else {
            var2_9 = RatingCompat.newUnratedRating(var3_12);
        }
        var2_10.mRatingObj = var0;
        return var2_11;
    }

    public static RatingCompat newHeartRating(boolean bl) {
        float f;
        if (bl) {
            f = 1.0f;
            return new RatingCompat(1, f);
        }
        f = 0.0f;
        return new RatingCompat(1, f);
    }

    public static RatingCompat newPercentageRating(float f) {
        if (!(f < 0.0f)) {
            if (!(f > 100.0f)) return new RatingCompat(6, f);
        }
        Log.e((String)TAG, (String)"Invalid percentage-based rating value");
        return null;
    }

    public static RatingCompat newStarRating(int n, float f) {
        float f2;
        if (n != 3) {
            if (n != 4) {
                if (n != 5) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append("Invalid rating style (");
                    stringBuilder.append(n);
                    stringBuilder.append(") for a star rating");
                    Log.e((String)TAG, (String)stringBuilder.toString());
                    return null;
                }
                f2 = 5.0f;
            } else {
                f2 = 4.0f;
            }
        } else {
            f2 = 3.0f;
        }
        if (!(f < 0.0f)) {
            if (!(f > f2)) return new RatingCompat(n, f);
        }
        Log.e((String)TAG, (String)"Trying to set out of range star-based rating");
        return null;
    }

    public static RatingCompat newThumbRating(boolean bl) {
        float f;
        if (bl) {
            f = 1.0f;
            return new RatingCompat(2, f);
        }
        f = 0.0f;
        return new RatingCompat(2, f);
    }

    public static RatingCompat newUnratedRating(int n) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
            case 2: 
            case 3: 
            case 4: 
            case 5: 
            case 6: 
        }
        return new RatingCompat(n, -1.0f);
    }

    public int describeContents() {
        return this.mRatingStyle;
    }

    public float getPercentRating() {
        if (this.mRatingStyle != 6) return -1.0f;
        if (this.isRated()) return this.mRatingValue;
        return -1.0f;
    }

    public Object getRating() {
        if (this.mRatingObj != null) return this.mRatingObj;
        if (Build.VERSION.SDK_INT < 19) return this.mRatingObj;
        if (!this.isRated()) {
            this.mRatingObj = Rating.newUnratedRating((int)this.mRatingStyle);
            return this.mRatingObj;
        }
        int n = this.mRatingStyle;
        switch (n) {
            default: {
                return null;
            }
            case 6: {
                this.mRatingObj = Rating.newPercentageRating((float)this.getPercentRating());
                return this.mRatingObj;
            }
            case 3: 
            case 4: 
            case 5: {
                this.mRatingObj = Rating.newStarRating((int)n, (float)this.getStarRating());
                return this.mRatingObj;
            }
            case 2: {
                this.mRatingObj = Rating.newThumbRating((boolean)this.isThumbUp());
                return this.mRatingObj;
            }
            case 1: 
        }
        this.mRatingObj = Rating.newHeartRating((boolean)this.hasHeart());
        return this.mRatingObj;
    }

    public int getRatingStyle() {
        return this.mRatingStyle;
    }

    public float getStarRating() {
        int n = this.mRatingStyle;
        if (n != 3 && n != 4 && n != 5) {
            return -1.0f;
        }
        if (!this.isRated()) return -1.0f;
        return this.mRatingValue;
    }

    public boolean hasHeart() {
        int n = this.mRatingStyle;
        boolean bl = false;
        if (n != 1) {
            return false;
        }
        if (this.mRatingValue != 1.0f) return bl;
        return true;
    }

    public boolean isRated() {
        if (!(this.mRatingValue >= 0.0f)) return false;
        return true;
    }

    public boolean isThumbUp() {
        int n = this.mRatingStyle;
        boolean bl = false;
        if (n != 2) {
            return false;
        }
        if (this.mRatingValue != 1.0f) return bl;
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Rating:style=");
        stringBuilder.append(this.mRatingStyle);
        stringBuilder.append(" rating=");
        float f = this.mRatingValue;
        String string2 = f < 0.0f ? "unrated" : String.valueOf(f);
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mRatingStyle);
        parcel.writeFloat(this.mRatingValue);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface StarStyle {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Style {
    }

}


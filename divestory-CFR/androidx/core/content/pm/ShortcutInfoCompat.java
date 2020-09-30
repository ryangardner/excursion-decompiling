/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Person
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.pm.ShortcutInfo
 *  android.content.pm.ShortcutInfo$Builder
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Icon
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcelable
 *  android.os.PersistableBundle
 *  android.text.TextUtils
 */
package androidx.core.content.pm;

import android.app.Person;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.ShortcutInfo;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Build;
import android.os.Parcelable;
import android.os.PersistableBundle;
import android.text.TextUtils;
import androidx.core.graphics.drawable.IconCompat;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ShortcutInfoCompat {
    private static final String EXTRA_LONG_LIVED = "extraLongLived";
    private static final String EXTRA_PERSON_ = "extraPerson_";
    private static final String EXTRA_PERSON_COUNT = "extraPersonCount";
    ComponentName mActivity;
    Set<String> mCategories;
    Context mContext;
    CharSequence mDisabledMessage;
    IconCompat mIcon;
    String mId;
    Intent[] mIntents;
    boolean mIsAlwaysBadged;
    boolean mIsLongLived;
    CharSequence mLabel;
    CharSequence mLongLabel;
    androidx.core.app.Person[] mPersons;
    int mRank;

    ShortcutInfoCompat() {
    }

    private PersistableBundle buildLegacyExtrasBundle() {
        PersistableBundle persistableBundle = new PersistableBundle();
        Object object = this.mPersons;
        if (object != null && ((androidx.core.app.Person[])object).length > 0) {
            persistableBundle.putInt(EXTRA_PERSON_COUNT, ((androidx.core.app.Person[])object).length);
            int n = 0;
            while (n < this.mPersons.length) {
                object = new StringBuilder();
                ((StringBuilder)object).append(EXTRA_PERSON_);
                int n2 = n + 1;
                ((StringBuilder)object).append(n2);
                persistableBundle.putPersistableBundle(((StringBuilder)object).toString(), this.mPersons[n].toPersistableBundle());
                n = n2;
            }
        }
        persistableBundle.putBoolean(EXTRA_LONG_LIVED, this.mIsLongLived);
        return persistableBundle;
    }

    static boolean getLongLivedFromExtra(PersistableBundle persistableBundle) {
        if (persistableBundle == null) return false;
        if (persistableBundle.containsKey(EXTRA_LONG_LIVED)) return persistableBundle.getBoolean(EXTRA_LONG_LIVED);
        return false;
    }

    static androidx.core.app.Person[] getPersonsFromExtra(PersistableBundle persistableBundle) {
        if (persistableBundle == null) return null;
        if (!persistableBundle.containsKey(EXTRA_PERSON_COUNT)) {
            return null;
        }
        int n = persistableBundle.getInt(EXTRA_PERSON_COUNT);
        androidx.core.app.Person[] arrperson = new androidx.core.app.Person[n];
        int n2 = 0;
        while (n2 < n) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append(EXTRA_PERSON_);
            int n3 = n2 + 1;
            stringBuilder.append(n3);
            arrperson[n2] = androidx.core.app.Person.fromPersistableBundle(persistableBundle.getPersistableBundle(stringBuilder.toString()));
            n2 = n3;
        }
        return arrperson;
    }

    Intent addToIntent(Intent intent) {
        Drawable drawable2 = this.mIntents;
        intent.putExtra("android.intent.extra.shortcut.INTENT", (Parcelable)drawable2[((Intent[])drawable2).length - 1]).putExtra("android.intent.extra.shortcut.NAME", this.mLabel.toString());
        if (this.mIcon == null) return intent;
        Drawable drawable3 = null;
        Object var4_5 = null;
        if (this.mIsAlwaysBadged) {
            PackageManager packageManager = this.mContext.getPackageManager();
            drawable3 = this.mActivity;
            drawable2 = var4_5;
            if (drawable3 != null) {
                try {
                    drawable2 = packageManager.getActivityIcon((ComponentName)drawable3);
                }
                catch (PackageManager.NameNotFoundException nameNotFoundException) {
                    drawable2 = var4_5;
                }
            }
            drawable3 = drawable2;
            if (drawable2 == null) {
                drawable3 = this.mContext.getApplicationInfo().loadIcon(packageManager);
            }
        }
        this.mIcon.addToShortcutIntent(intent, drawable3, this.mContext);
        return intent;
    }

    public ComponentName getActivity() {
        return this.mActivity;
    }

    public Set<String> getCategories() {
        return this.mCategories;
    }

    public CharSequence getDisabledMessage() {
        return this.mDisabledMessage;
    }

    public IconCompat getIcon() {
        return this.mIcon;
    }

    public String getId() {
        return this.mId;
    }

    public Intent getIntent() {
        Intent[] arrintent = this.mIntents;
        return arrintent[arrintent.length - 1];
    }

    public Intent[] getIntents() {
        Intent[] arrintent = this.mIntents;
        return Arrays.copyOf(arrintent, arrintent.length);
    }

    public CharSequence getLongLabel() {
        return this.mLongLabel;
    }

    public int getRank() {
        return this.mRank;
    }

    public CharSequence getShortLabel() {
        return this.mLabel;
    }

    public ShortcutInfo toShortcutInfo() {
        ShortcutInfo.Builder builder = new ShortcutInfo.Builder(this.mContext, this.mId).setShortLabel(this.mLabel).setIntents(this.mIntents);
        Object object = this.mIcon;
        if (object != null) {
            builder.setIcon(object.toIcon(this.mContext));
        }
        if (!TextUtils.isEmpty((CharSequence)this.mLongLabel)) {
            builder.setLongLabel(this.mLongLabel);
        }
        if (!TextUtils.isEmpty((CharSequence)this.mDisabledMessage)) {
            builder.setDisabledMessage(this.mDisabledMessage);
        }
        if ((object = this.mActivity) != null) {
            builder.setActivity((ComponentName)object);
        }
        if ((object = this.mCategories) != null) {
            builder.setCategories((Set)object);
        }
        builder.setRank(this.mRank);
        if (Build.VERSION.SDK_INT < 29) {
            builder.setExtras(this.buildLegacyExtrasBundle());
            return builder.build();
        }
        object = this.mPersons;
        if (object != null && ((androidx.core.app.Person[])object).length > 0) {
            int n = ((androidx.core.app.Person[])object).length;
            object = new Person[n];
            for (int i = 0; i < n; ++i) {
                object[i] = this.mPersons[i].toAndroidPerson();
            }
            builder.setPersons((Person[])object);
        }
        builder.setLongLived(this.mIsLongLived);
        return builder.build();
    }

    public static class Builder {
        private final ShortcutInfoCompat mInfo;

        public Builder(Context arrintent, ShortcutInfo shortcutInfo) {
            ShortcutInfoCompat shortcutInfoCompat;
            this.mInfo = shortcutInfoCompat = new ShortcutInfoCompat();
            shortcutInfoCompat.mContext = arrintent;
            this.mInfo.mId = shortcutInfo.getId();
            arrintent = shortcutInfo.getIntents();
            this.mInfo.mIntents = Arrays.copyOf(arrintent, arrintent.length);
            this.mInfo.mActivity = shortcutInfo.getActivity();
            this.mInfo.mLabel = shortcutInfo.getShortLabel();
            this.mInfo.mLongLabel = shortcutInfo.getLongLabel();
            this.mInfo.mDisabledMessage = shortcutInfo.getDisabledMessage();
            this.mInfo.mCategories = shortcutInfo.getCategories();
            this.mInfo.mPersons = ShortcutInfoCompat.getPersonsFromExtra(shortcutInfo.getExtras());
            this.mInfo.mRank = shortcutInfo.getRank();
        }

        public Builder(Context context, String string2) {
            ShortcutInfoCompat shortcutInfoCompat;
            this.mInfo = shortcutInfoCompat = new ShortcutInfoCompat();
            shortcutInfoCompat.mContext = context;
            this.mInfo.mId = string2;
        }

        public Builder(ShortcutInfoCompat shortcutInfoCompat) {
            ShortcutInfoCompat shortcutInfoCompat2;
            this.mInfo = shortcutInfoCompat2 = new ShortcutInfoCompat();
            shortcutInfoCompat2.mContext = shortcutInfoCompat.mContext;
            this.mInfo.mId = shortcutInfoCompat.mId;
            this.mInfo.mIntents = Arrays.copyOf(shortcutInfoCompat.mIntents, shortcutInfoCompat.mIntents.length);
            this.mInfo.mActivity = shortcutInfoCompat.mActivity;
            this.mInfo.mLabel = shortcutInfoCompat.mLabel;
            this.mInfo.mLongLabel = shortcutInfoCompat.mLongLabel;
            this.mInfo.mDisabledMessage = shortcutInfoCompat.mDisabledMessage;
            this.mInfo.mIcon = shortcutInfoCompat.mIcon;
            this.mInfo.mIsAlwaysBadged = shortcutInfoCompat.mIsAlwaysBadged;
            this.mInfo.mIsLongLived = shortcutInfoCompat.mIsLongLived;
            this.mInfo.mRank = shortcutInfoCompat.mRank;
            if (shortcutInfoCompat.mPersons != null) {
                this.mInfo.mPersons = Arrays.copyOf(shortcutInfoCompat.mPersons, shortcutInfoCompat.mPersons.length);
            }
            if (shortcutInfoCompat.mCategories == null) return;
            this.mInfo.mCategories = new HashSet<String>(shortcutInfoCompat.mCategories);
        }

        public ShortcutInfoCompat build() {
            if (TextUtils.isEmpty((CharSequence)this.mInfo.mLabel)) throw new IllegalArgumentException("Shortcut must have a non-empty label");
            if (this.mInfo.mIntents == null) throw new IllegalArgumentException("Shortcut must have an intent");
            if (this.mInfo.mIntents.length == 0) throw new IllegalArgumentException("Shortcut must have an intent");
            return this.mInfo;
        }

        public Builder setActivity(ComponentName componentName) {
            this.mInfo.mActivity = componentName;
            return this;
        }

        public Builder setAlwaysBadged() {
            this.mInfo.mIsAlwaysBadged = true;
            return this;
        }

        public Builder setCategories(Set<String> set) {
            this.mInfo.mCategories = set;
            return this;
        }

        public Builder setDisabledMessage(CharSequence charSequence) {
            this.mInfo.mDisabledMessage = charSequence;
            return this;
        }

        public Builder setIcon(IconCompat iconCompat) {
            this.mInfo.mIcon = iconCompat;
            return this;
        }

        public Builder setIntent(Intent intent) {
            return this.setIntents(new Intent[]{intent});
        }

        public Builder setIntents(Intent[] arrintent) {
            this.mInfo.mIntents = arrintent;
            return this;
        }

        public Builder setLongLabel(CharSequence charSequence) {
            this.mInfo.mLongLabel = charSequence;
            return this;
        }

        @Deprecated
        public Builder setLongLived() {
            this.mInfo.mIsLongLived = true;
            return this;
        }

        public Builder setLongLived(boolean bl) {
            this.mInfo.mIsLongLived = bl;
            return this;
        }

        public Builder setPerson(androidx.core.app.Person person) {
            return this.setPersons(new androidx.core.app.Person[]{person});
        }

        public Builder setPersons(androidx.core.app.Person[] arrperson) {
            this.mInfo.mPersons = arrperson;
            return this;
        }

        public Builder setRank(int n) {
            this.mInfo.mRank = n;
            return this;
        }

        public Builder setShortLabel(CharSequence charSequence) {
            this.mInfo.mLabel = charSequence;
            return this;
        }
    }

}


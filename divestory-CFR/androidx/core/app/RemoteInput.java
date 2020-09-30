/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.RemoteInput
 *  android.app.RemoteInput$Builder
 *  android.content.ClipData
 *  android.content.ClipData$Item
 *  android.content.ClipDescription
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package androidx.core.app;

import android.app.RemoteInput;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class RemoteInput {
    public static final int EDIT_CHOICES_BEFORE_SENDING_AUTO = 0;
    public static final int EDIT_CHOICES_BEFORE_SENDING_DISABLED = 1;
    public static final int EDIT_CHOICES_BEFORE_SENDING_ENABLED = 2;
    private static final String EXTRA_DATA_TYPE_RESULTS_DATA = "android.remoteinput.dataTypeResultsData";
    public static final String EXTRA_RESULTS_DATA = "android.remoteinput.resultsData";
    private static final String EXTRA_RESULTS_SOURCE = "android.remoteinput.resultsSource";
    public static final String RESULTS_CLIP_LABEL = "android.remoteinput.results";
    public static final int SOURCE_CHOICE = 1;
    public static final int SOURCE_FREE_FORM_INPUT = 0;
    private static final String TAG = "RemoteInput";
    private final boolean mAllowFreeFormTextInput;
    private final Set<String> mAllowedDataTypes;
    private final CharSequence[] mChoices;
    private final int mEditChoicesBeforeSending;
    private final Bundle mExtras;
    private final CharSequence mLabel;
    private final String mResultKey;

    RemoteInput(String string2, CharSequence charSequence, CharSequence[] arrcharSequence, boolean bl, int n, Bundle bundle, Set<String> set) {
        this.mResultKey = string2;
        this.mLabel = charSequence;
        this.mChoices = arrcharSequence;
        this.mAllowFreeFormTextInput = bl;
        this.mEditChoicesBeforeSending = n;
        this.mExtras = bundle;
        this.mAllowedDataTypes = set;
        if (this.getEditChoicesBeforeSending() != 2) return;
        if (!this.getAllowFreeFormInput()) throw new IllegalArgumentException("setEditChoicesBeforeSending requires setAllowFreeFormInput");
    }

    public static void addDataResultToIntent(RemoteInput remoteInput, Intent intent, Map<String, Uri> object) {
        Intent intent2;
        if (Build.VERSION.SDK_INT >= 26) {
            android.app.RemoteInput.addDataResultToIntent((android.app.RemoteInput)RemoteInput.fromCompat(remoteInput), (Intent)intent, object);
            return;
        }
        if (Build.VERSION.SDK_INT < 16) return;
        Intent intent3 = intent2 = RemoteInput.getClipDataIntentFromIntent(intent);
        if (intent2 == null) {
            intent3 = new Intent();
        }
        Iterator<Map.Entry<String, Uri>> iterator2 = object.entrySet().iterator();
        do {
            if (!iterator2.hasNext()) {
                intent.setClipData(ClipData.newIntent((CharSequence)RESULTS_CLIP_LABEL, (Intent)intent3));
                return;
            }
            object = iterator2.next();
            String string2 = (String)object.getKey();
            Uri uri = (Uri)object.getValue();
            if (string2 == null) continue;
            intent2 = intent3.getBundleExtra(RemoteInput.getExtraResultsKeyForData(string2));
            object = intent2;
            if (intent2 == null) {
                object = new Bundle();
            }
            object.putString(remoteInput.getResultKey(), uri.toString());
            intent3.putExtra(RemoteInput.getExtraResultsKeyForData(string2), (Bundle)object);
        } while (true);
    }

    public static void addResultsToIntent(RemoteInput[] arrremoteInput, Intent intent, Bundle bundle) {
        Intent intent2;
        if (Build.VERSION.SDK_INT >= 26) {
            android.app.RemoteInput.addResultsToIntent((android.app.RemoteInput[])RemoteInput.fromCompat(arrremoteInput), (Intent)intent, (Bundle)bundle);
            return;
        }
        int n = Build.VERSION.SDK_INT;
        int n2 = 0;
        if (n >= 20) {
            Object object = RemoteInput.getResultsFromIntent(intent);
            int n3 = RemoteInput.getResultsSource(intent);
            if (object != null) {
                object.putAll(bundle);
                bundle = object;
            }
            n = arrremoteInput.length;
            n2 = 0;
            do {
                if (n2 >= n) {
                    RemoteInput.setResultsSource(intent, n3);
                    return;
                }
                object = arrremoteInput[n2];
                Map<String, Uri> map = RemoteInput.getDataResultsFromIntent(intent, ((RemoteInput)object).getResultKey());
                android.app.RemoteInput.addResultsToIntent((android.app.RemoteInput[])RemoteInput.fromCompat(new RemoteInput[]{object}), (Intent)intent, (Bundle)bundle);
                if (map != null) {
                    RemoteInput.addDataResultToIntent((RemoteInput)object, intent, map);
                }
                ++n2;
            } while (true);
        }
        if (Build.VERSION.SDK_INT < 16) return;
        Intent intent3 = intent2 = RemoteInput.getClipDataIntentFromIntent(intent);
        if (intent2 == null) {
            intent3 = new Intent();
        }
        Object object = intent3.getBundleExtra(EXTRA_RESULTS_DATA);
        intent2 = object;
        if (object == null) {
            intent2 = new Bundle();
        }
        n = arrremoteInput.length;
        do {
            if (n2 >= n) {
                intent3.putExtra(EXTRA_RESULTS_DATA, (Bundle)intent2);
                intent.setClipData(ClipData.newIntent((CharSequence)RESULTS_CLIP_LABEL, (Intent)intent3));
                return;
            }
            RemoteInput remoteInput = arrremoteInput[n2];
            object = bundle.get(remoteInput.getResultKey());
            if (object instanceof CharSequence) {
                intent2.putCharSequence(remoteInput.getResultKey(), (CharSequence)object);
            }
            ++n2;
        } while (true);
    }

    static android.app.RemoteInput fromCompat(RemoteInput remoteInput) {
        RemoteInput.Builder builder = new RemoteInput.Builder(remoteInput.getResultKey()).setLabel(remoteInput.getLabel()).setChoices(remoteInput.getChoices()).setAllowFreeFormInput(remoteInput.getAllowFreeFormInput()).addExtras(remoteInput.getExtras());
        if (Build.VERSION.SDK_INT < 29) return builder.build();
        builder.setEditChoicesBeforeSending(remoteInput.getEditChoicesBeforeSending());
        return builder.build();
    }

    static android.app.RemoteInput[] fromCompat(RemoteInput[] arrremoteInput) {
        if (arrremoteInput == null) {
            return null;
        }
        android.app.RemoteInput[] arrremoteInput2 = new android.app.RemoteInput[arrremoteInput.length];
        int n = 0;
        while (n < arrremoteInput.length) {
            arrremoteInput2[n] = RemoteInput.fromCompat(arrremoteInput[n]);
            ++n;
        }
        return arrremoteInput2;
    }

    private static Intent getClipDataIntentFromIntent(Intent intent) {
        if ((intent = intent.getClipData()) == null) {
            return null;
        }
        ClipDescription clipDescription = intent.getDescription();
        if (!clipDescription.hasMimeType("text/vnd.android.intent")) {
            return null;
        }
        if (clipDescription.getLabel().toString().contentEquals(RESULTS_CLIP_LABEL)) return intent.getItemAt(0).getIntent();
        return null;
    }

    public static Map<String, Uri> getDataResultsFromIntent(Intent intent, String string2) {
        HashMap<String, Uri> hashMap;
        if (Build.VERSION.SDK_INT >= 26) {
            return android.app.RemoteInput.getDataResultsFromIntent((Intent)intent, (String)string2);
        }
        int n = Build.VERSION.SDK_INT;
        HashMap<String, Uri> hashMap2 = hashMap = null;
        if (n < 16) return hashMap2;
        if ((intent = RemoteInput.getClipDataIntentFromIntent(intent)) == null) {
            return null;
        }
        hashMap2 = new HashMap<String, Uri>();
        Iterator iterator2 = intent.getExtras().keySet().iterator();
        do {
            String string3;
            if (!iterator2.hasNext()) {
                if (!hashMap2.isEmpty()) return hashMap2;
                return hashMap;
            }
            String string4 = (String)iterator2.next();
            if (!string4.startsWith(EXTRA_DATA_TYPE_RESULTS_DATA) || (string3 = string4.substring(39)).isEmpty() || (string4 = intent.getBundleExtra(string4).getString(string2)) == null || string4.isEmpty()) continue;
            hashMap2.put(string3, Uri.parse((String)string4));
        } while (true);
    }

    private static String getExtraResultsKeyForData(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(EXTRA_DATA_TYPE_RESULTS_DATA);
        stringBuilder.append(string2);
        return stringBuilder.toString();
    }

    public static Bundle getResultsFromIntent(Intent intent) {
        if (Build.VERSION.SDK_INT >= 20) {
            return android.app.RemoteInput.getResultsFromIntent((Intent)intent);
        }
        if (Build.VERSION.SDK_INT < 16) return null;
        if ((intent = RemoteInput.getClipDataIntentFromIntent(intent)) != null) return (Bundle)intent.getExtras().getParcelable(EXTRA_RESULTS_DATA);
        return null;
    }

    public static int getResultsSource(Intent intent) {
        if (Build.VERSION.SDK_INT >= 28) {
            return android.app.RemoteInput.getResultsSource((Intent)intent);
        }
        if (Build.VERSION.SDK_INT < 16) return 0;
        if ((intent = RemoteInput.getClipDataIntentFromIntent(intent)) != null) return intent.getExtras().getInt(EXTRA_RESULTS_SOURCE, 0);
        return 0;
    }

    public static void setResultsSource(Intent intent, int n) {
        Intent intent2;
        if (Build.VERSION.SDK_INT >= 28) {
            android.app.RemoteInput.setResultsSource((Intent)intent, (int)n);
            return;
        }
        if (Build.VERSION.SDK_INT < 16) return;
        Intent intent3 = intent2 = RemoteInput.getClipDataIntentFromIntent(intent);
        if (intent2 == null) {
            intent3 = new Intent();
        }
        intent3.putExtra(EXTRA_RESULTS_SOURCE, n);
        intent.setClipData(ClipData.newIntent((CharSequence)RESULTS_CLIP_LABEL, (Intent)intent3));
    }

    public boolean getAllowFreeFormInput() {
        return this.mAllowFreeFormTextInput;
    }

    public Set<String> getAllowedDataTypes() {
        return this.mAllowedDataTypes;
    }

    public CharSequence[] getChoices() {
        return this.mChoices;
    }

    public int getEditChoicesBeforeSending() {
        return this.mEditChoicesBeforeSending;
    }

    public Bundle getExtras() {
        return this.mExtras;
    }

    public CharSequence getLabel() {
        return this.mLabel;
    }

    public String getResultKey() {
        return this.mResultKey;
    }

    public boolean isDataOnly() {
        if (this.getAllowFreeFormInput()) return false;
        if (this.getChoices() != null) {
            if (this.getChoices().length != 0) return false;
        }
        if (this.getAllowedDataTypes() == null) return false;
        if (this.getAllowedDataTypes().isEmpty()) return false;
        return true;
    }

    public static final class Builder {
        private boolean mAllowFreeFormTextInput = true;
        private final Set<String> mAllowedDataTypes = new HashSet<String>();
        private CharSequence[] mChoices;
        private int mEditChoicesBeforeSending = 0;
        private final Bundle mExtras = new Bundle();
        private CharSequence mLabel;
        private final String mResultKey;

        public Builder(String string2) {
            if (string2 == null) throw new IllegalArgumentException("Result key can't be null");
            this.mResultKey = string2;
        }

        public Builder addExtras(Bundle bundle) {
            if (bundle == null) return this;
            this.mExtras.putAll(bundle);
            return this;
        }

        public RemoteInput build() {
            return new RemoteInput(this.mResultKey, this.mLabel, this.mChoices, this.mAllowFreeFormTextInput, this.mEditChoicesBeforeSending, this.mExtras, this.mAllowedDataTypes);
        }

        public Bundle getExtras() {
            return this.mExtras;
        }

        public Builder setAllowDataType(String string2, boolean bl) {
            if (bl) {
                this.mAllowedDataTypes.add(string2);
                return this;
            }
            this.mAllowedDataTypes.remove(string2);
            return this;
        }

        public Builder setAllowFreeFormInput(boolean bl) {
            this.mAllowFreeFormTextInput = bl;
            return this;
        }

        public Builder setChoices(CharSequence[] arrcharSequence) {
            this.mChoices = arrcharSequence;
            return this;
        }

        public Builder setEditChoicesBeforeSending(int n) {
            this.mEditChoicesBeforeSending = n;
            return this;
        }

        public Builder setLabel(CharSequence charSequence) {
            this.mLabel = charSequence;
            return this;
        }
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface EditChoicesBeforeSending {
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface Source {
    }

}


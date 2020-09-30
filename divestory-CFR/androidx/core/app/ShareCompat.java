/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcelable
 *  android.text.Html
 *  android.text.Spanned
 *  android.util.Log
 *  android.view.ActionProvider
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.widget.ShareActionProvider
 */
package androidx.core.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import androidx.core.util.Preconditions;
import java.io.Serializable;
import java.util.ArrayList;

public final class ShareCompat {
    public static final String EXTRA_CALLING_ACTIVITY = "androidx.core.app.EXTRA_CALLING_ACTIVITY";
    public static final String EXTRA_CALLING_ACTIVITY_INTEROP = "android.support.v4.app.EXTRA_CALLING_ACTIVITY";
    public static final String EXTRA_CALLING_PACKAGE = "androidx.core.app.EXTRA_CALLING_PACKAGE";
    public static final String EXTRA_CALLING_PACKAGE_INTEROP = "android.support.v4.app.EXTRA_CALLING_PACKAGE";
    private static final String HISTORY_FILENAME_PREFIX = ".sharecompat_";

    private ShareCompat() {
    }

    public static void configureMenuItem(Menu object, int n, IntentBuilder intentBuilder) {
        if ((object = object.findItem(n)) != null) {
            ShareCompat.configureMenuItem((MenuItem)object, intentBuilder);
            return;
        }
        object = new StringBuilder();
        ((StringBuilder)object).append("Could not find menu item with id ");
        ((StringBuilder)object).append(n);
        ((StringBuilder)object).append(" in the supplied menu");
        throw new IllegalArgumentException(((StringBuilder)object).toString());
    }

    public static void configureMenuItem(MenuItem menuItem, IntentBuilder intentBuilder) {
        ActionProvider actionProvider = menuItem.getActionProvider();
        actionProvider = !(actionProvider instanceof ShareActionProvider) ? new ShareActionProvider(intentBuilder.getContext()) : (ShareActionProvider)actionProvider;
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(HISTORY_FILENAME_PREFIX);
        stringBuilder.append(intentBuilder.getContext().getClass().getName());
        actionProvider.setShareHistoryFileName(stringBuilder.toString());
        actionProvider.setShareIntent(intentBuilder.getIntent());
        menuItem.setActionProvider(actionProvider);
        if (Build.VERSION.SDK_INT >= 16) return;
        if (menuItem.hasSubMenu()) return;
        menuItem.setIntent(intentBuilder.createChooserIntent());
    }

    public static ComponentName getCallingActivity(Activity activity) {
        Intent intent = activity.getIntent();
        ComponentName componentName = activity.getCallingActivity();
        activity = componentName;
        if (componentName != null) return activity;
        return ShareCompat.getCallingActivity(intent);
    }

    static ComponentName getCallingActivity(Intent intent) {
        ComponentName componentName;
        ComponentName componentName2 = componentName = (ComponentName)intent.getParcelableExtra(EXTRA_CALLING_ACTIVITY);
        if (componentName != null) return componentName2;
        return (ComponentName)intent.getParcelableExtra(EXTRA_CALLING_ACTIVITY_INTEROP);
    }

    public static String getCallingPackage(Activity object) {
        Intent intent = object.getIntent();
        String string2 = object.getCallingPackage();
        object = string2;
        if (string2 != null) return object;
        object = string2;
        if (intent == null) return object;
        return ShareCompat.getCallingPackage(intent);
    }

    static String getCallingPackage(Intent intent) {
        String string2;
        String string3 = string2 = intent.getStringExtra(EXTRA_CALLING_PACKAGE);
        if (string2 != null) return string3;
        return intent.getStringExtra(EXTRA_CALLING_PACKAGE_INTEROP);
    }

    public static class IntentBuilder {
        private ArrayList<String> mBccAddresses;
        private ArrayList<String> mCcAddresses;
        private CharSequence mChooserTitle;
        private final Context mContext;
        private final Intent mIntent;
        private ArrayList<Uri> mStreams;
        private ArrayList<String> mToAddresses;

        private IntentBuilder(Context context, ComponentName componentName) {
            Intent intent;
            this.mContext = Preconditions.checkNotNull(context);
            this.mIntent = intent = new Intent().setAction("android.intent.action.SEND");
            intent.putExtra(ShareCompat.EXTRA_CALLING_PACKAGE, context.getPackageName());
            this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_PACKAGE_INTEROP, context.getPackageName());
            this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_ACTIVITY, (Parcelable)componentName);
            this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_ACTIVITY_INTEROP, (Parcelable)componentName);
            this.mIntent.addFlags(524288);
        }

        private void combineArrayExtra(String string2, ArrayList<String> arrayList) {
            String[] arrstring = this.mIntent.getStringArrayExtra(string2);
            int n = arrstring != null ? arrstring.length : 0;
            String[] arrstring2 = new String[arrayList.size() + n];
            arrayList.toArray(arrstring2);
            if (arrstring != null) {
                System.arraycopy(arrstring, 0, arrstring2, arrayList.size(), n);
            }
            this.mIntent.putExtra(string2, arrstring2);
        }

        private void combineArrayExtra(String string2, String[] arrstring) {
            Intent intent = this.getIntent();
            String[] arrstring2 = intent.getStringArrayExtra(string2);
            int n = arrstring2 != null ? arrstring2.length : 0;
            String[] arrstring3 = new String[arrstring.length + n];
            if (arrstring2 != null) {
                System.arraycopy(arrstring2, 0, arrstring3, 0, n);
            }
            System.arraycopy(arrstring, 0, arrstring3, n, arrstring.length);
            intent.putExtra(string2, arrstring3);
        }

        public static IntentBuilder from(Activity activity) {
            return IntentBuilder.from((Context)Preconditions.checkNotNull(activity), activity.getComponentName());
        }

        private static IntentBuilder from(Context context, ComponentName componentName) {
            return new IntentBuilder(context, componentName);
        }

        public IntentBuilder addEmailBcc(String string2) {
            if (this.mBccAddresses == null) {
                this.mBccAddresses = new ArrayList();
            }
            this.mBccAddresses.add(string2);
            return this;
        }

        public IntentBuilder addEmailBcc(String[] arrstring) {
            this.combineArrayExtra("android.intent.extra.BCC", arrstring);
            return this;
        }

        public IntentBuilder addEmailCc(String string2) {
            if (this.mCcAddresses == null) {
                this.mCcAddresses = new ArrayList();
            }
            this.mCcAddresses.add(string2);
            return this;
        }

        public IntentBuilder addEmailCc(String[] arrstring) {
            this.combineArrayExtra("android.intent.extra.CC", arrstring);
            return this;
        }

        public IntentBuilder addEmailTo(String string2) {
            if (this.mToAddresses == null) {
                this.mToAddresses = new ArrayList();
            }
            this.mToAddresses.add(string2);
            return this;
        }

        public IntentBuilder addEmailTo(String[] arrstring) {
            this.combineArrayExtra("android.intent.extra.EMAIL", arrstring);
            return this;
        }

        public IntentBuilder addStream(Uri uri) {
            Uri uri2 = (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
            if (this.mStreams == null && uri2 == null) {
                return this.setStream(uri);
            }
            if (this.mStreams == null) {
                this.mStreams = new ArrayList();
            }
            if (uri2 != null) {
                this.mIntent.removeExtra("android.intent.extra.STREAM");
                this.mStreams.add(uri2);
            }
            this.mStreams.add(uri);
            return this;
        }

        public Intent createChooserIntent() {
            return Intent.createChooser((Intent)this.getIntent(), (CharSequence)this.mChooserTitle);
        }

        Context getContext() {
            return this.mContext;
        }

        public Intent getIntent() {
            ArrayList<String> arrayList = this.mToAddresses;
            if (arrayList != null) {
                this.combineArrayExtra("android.intent.extra.EMAIL", arrayList);
                this.mToAddresses = null;
            }
            if ((arrayList = this.mCcAddresses) != null) {
                this.combineArrayExtra("android.intent.extra.CC", arrayList);
                this.mCcAddresses = null;
            }
            if ((arrayList = this.mBccAddresses) != null) {
                this.combineArrayExtra("android.intent.extra.BCC", arrayList);
                this.mBccAddresses = null;
            }
            arrayList = this.mStreams;
            boolean bl = true;
            if (arrayList == null || arrayList.size() <= 1) {
                bl = false;
            }
            boolean bl2 = "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
            if (!bl && bl2) {
                this.mIntent.setAction("android.intent.action.SEND");
                arrayList = this.mStreams;
                if (arrayList != null && !arrayList.isEmpty()) {
                    this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable)this.mStreams.get(0));
                } else {
                    this.mIntent.removeExtra("android.intent.extra.STREAM");
                }
                this.mStreams = null;
            }
            if (!bl) return this.mIntent;
            if (bl2) return this.mIntent;
            this.mIntent.setAction("android.intent.action.SEND_MULTIPLE");
            arrayList = this.mStreams;
            if (arrayList != null && !arrayList.isEmpty()) {
                this.mIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.mStreams);
                return this.mIntent;
            }
            this.mIntent.removeExtra("android.intent.extra.STREAM");
            return this.mIntent;
        }

        public IntentBuilder setChooserTitle(int n) {
            return this.setChooserTitle(this.mContext.getText(n));
        }

        public IntentBuilder setChooserTitle(CharSequence charSequence) {
            this.mChooserTitle = charSequence;
            return this;
        }

        public IntentBuilder setEmailBcc(String[] arrstring) {
            this.mIntent.putExtra("android.intent.extra.BCC", arrstring);
            return this;
        }

        public IntentBuilder setEmailCc(String[] arrstring) {
            this.mIntent.putExtra("android.intent.extra.CC", arrstring);
            return this;
        }

        public IntentBuilder setEmailTo(String[] arrstring) {
            if (this.mToAddresses != null) {
                this.mToAddresses = null;
            }
            this.mIntent.putExtra("android.intent.extra.EMAIL", arrstring);
            return this;
        }

        public IntentBuilder setHtmlText(String string2) {
            this.mIntent.putExtra("android.intent.extra.HTML_TEXT", string2);
            if (this.mIntent.hasExtra("android.intent.extra.TEXT")) return this;
            this.setText((CharSequence)Html.fromHtml((String)string2));
            return this;
        }

        public IntentBuilder setStream(Uri uri) {
            if (!"android.intent.action.SEND".equals(this.mIntent.getAction())) {
                this.mIntent.setAction("android.intent.action.SEND");
            }
            this.mStreams = null;
            this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable)uri);
            return this;
        }

        public IntentBuilder setSubject(String string2) {
            this.mIntent.putExtra("android.intent.extra.SUBJECT", string2);
            return this;
        }

        public IntentBuilder setText(CharSequence charSequence) {
            this.mIntent.putExtra("android.intent.extra.TEXT", charSequence);
            return this;
        }

        public IntentBuilder setType(String string2) {
            this.mIntent.setType(string2);
            return this;
        }

        public void startChooser() {
            this.mContext.startActivity(this.createChooserIntent());
        }
    }

    public static class IntentReader {
        private static final String TAG = "IntentReader";
        private final ComponentName mCallingActivity;
        private final String mCallingPackage;
        private final Context mContext;
        private final Intent mIntent;
        private ArrayList<Uri> mStreams;

        private IntentReader(Context context, Intent intent) {
            this.mContext = Preconditions.checkNotNull(context);
            this.mIntent = Preconditions.checkNotNull(intent);
            this.mCallingPackage = ShareCompat.getCallingPackage(intent);
            this.mCallingActivity = ShareCompat.getCallingActivity(intent);
        }

        public static IntentReader from(Activity activity) {
            return IntentReader.from((Context)Preconditions.checkNotNull(activity), activity.getIntent());
        }

        private static IntentReader from(Context context, Intent intent) {
            return new IntentReader(context, intent);
        }

        private static void withinStyle(StringBuilder stringBuilder, CharSequence charSequence, int n, int n2) {
            while (n < n2) {
                char c = charSequence.charAt(n);
                if (c == '<') {
                    stringBuilder.append("&lt;");
                } else if (c == '>') {
                    stringBuilder.append("&gt;");
                } else if (c == '&') {
                    stringBuilder.append("&amp;");
                } else if (c <= '~' && c >= ' ') {
                    if (c == ' ') {
                        int n3;
                        while ((n3 = n + 1) < n2 && charSequence.charAt(n3) == ' ') {
                            stringBuilder.append("&nbsp;");
                            n = n3;
                        }
                        stringBuilder.append(' ');
                    } else {
                        stringBuilder.append(c);
                    }
                } else {
                    stringBuilder.append("&#");
                    stringBuilder.append((int)c);
                    stringBuilder.append(";");
                }
                ++n;
            }
        }

        public ComponentName getCallingActivity() {
            return this.mCallingActivity;
        }

        public Drawable getCallingActivityIcon() {
            if (this.mCallingActivity == null) {
                return null;
            }
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                return packageManager.getActivityIcon(this.mCallingActivity);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.e((String)TAG, (String)"Could not retrieve icon for calling activity", (Throwable)nameNotFoundException);
                return null;
            }
        }

        public Drawable getCallingApplicationIcon() {
            if (this.mCallingPackage == null) {
                return null;
            }
            PackageManager packageManager = this.mContext.getPackageManager();
            try {
                return packageManager.getApplicationIcon(this.mCallingPackage);
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.e((String)TAG, (String)"Could not retrieve icon for calling application", (Throwable)nameNotFoundException);
                return null;
            }
        }

        public CharSequence getCallingApplicationLabel() {
            if (this.mCallingPackage == null) {
                return null;
            }
            Object object = this.mContext.getPackageManager();
            try {
                return object.getApplicationLabel(object.getApplicationInfo(this.mCallingPackage, 0));
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.e((String)TAG, (String)"Could not retrieve label for calling application", (Throwable)nameNotFoundException);
                return null;
            }
        }

        public String getCallingPackage() {
            return this.mCallingPackage;
        }

        public String[] getEmailBcc() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.BCC");
        }

        public String[] getEmailCc() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.CC");
        }

        public String[] getEmailTo() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.EMAIL");
        }

        public String getHtmlText() {
            String string2 = this.mIntent.getStringExtra("android.intent.extra.HTML_TEXT");
            CharSequence charSequence = string2;
            if (string2 != null) return charSequence;
            CharSequence charSequence2 = this.getText();
            if (charSequence2 instanceof Spanned) {
                return Html.toHtml((Spanned)((Spanned)charSequence2));
            }
            charSequence = string2;
            if (charSequence2 == null) return charSequence;
            if (Build.VERSION.SDK_INT >= 16) {
                return Html.escapeHtml((CharSequence)charSequence2);
            }
            charSequence = new StringBuilder();
            IntentReader.withinStyle((StringBuilder)charSequence, charSequence2, 0, charSequence2.length());
            return ((StringBuilder)charSequence).toString();
        }

        public Uri getStream() {
            return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
        }

        public Uri getStream(int n) {
            Serializable serializable;
            if (this.mStreams == null && this.isMultipleShare()) {
                this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            if ((serializable = this.mStreams) != null) {
                return ((ArrayList)serializable).get(n);
            }
            if (n == 0) {
                return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
            }
            serializable = new StringBuilder();
            ((StringBuilder)serializable).append("Stream items available: ");
            ((StringBuilder)serializable).append(this.getStreamCount());
            ((StringBuilder)serializable).append(" index requested: ");
            ((StringBuilder)serializable).append(n);
            throw new IndexOutOfBoundsException(((StringBuilder)serializable).toString());
        }

        public int getStreamCount() {
            ArrayList<Uri> arrayList;
            if (this.mStreams == null && this.isMultipleShare()) {
                this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            if ((arrayList = this.mStreams) == null) return (int)this.mIntent.hasExtra("android.intent.extra.STREAM");
            return arrayList.size();
        }

        public String getSubject() {
            return this.mIntent.getStringExtra("android.intent.extra.SUBJECT");
        }

        public CharSequence getText() {
            return this.mIntent.getCharSequenceExtra("android.intent.extra.TEXT");
        }

        public String getType() {
            return this.mIntent.getType();
        }

        public boolean isMultipleShare() {
            return "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
        }

        public boolean isShareIntent() {
            String string2 = this.mIntent.getAction();
            if ("android.intent.action.SEND".equals(string2)) return true;
            if ("android.intent.action.SEND_MULTIPLE".equals(string2)) return true;
            return false;
        }

        public boolean isSingleShare() {
            return "android.intent.action.SEND".equals(this.mIntent.getAction());
        }
    }

}


package androidx.core.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import androidx.core.util.Preconditions;
import java.util.ArrayList;

public final class ShareCompat {
   public static final String EXTRA_CALLING_ACTIVITY = "androidx.core.app.EXTRA_CALLING_ACTIVITY";
   public static final String EXTRA_CALLING_ACTIVITY_INTEROP = "android.support.v4.app.EXTRA_CALLING_ACTIVITY";
   public static final String EXTRA_CALLING_PACKAGE = "androidx.core.app.EXTRA_CALLING_PACKAGE";
   public static final String EXTRA_CALLING_PACKAGE_INTEROP = "android.support.v4.app.EXTRA_CALLING_PACKAGE";
   private static final String HISTORY_FILENAME_PREFIX = ".sharecompat_";

   private ShareCompat() {
   }

   public static void configureMenuItem(Menu var0, int var1, ShareCompat.IntentBuilder var2) {
      MenuItem var3 = var0.findItem(var1);
      if (var3 != null) {
         configureMenuItem(var3, var2);
      } else {
         StringBuilder var4 = new StringBuilder();
         var4.append("Could not find menu item with id ");
         var4.append(var1);
         var4.append(" in the supplied menu");
         throw new IllegalArgumentException(var4.toString());
      }
   }

   public static void configureMenuItem(MenuItem var0, ShareCompat.IntentBuilder var1) {
      ActionProvider var2 = var0.getActionProvider();
      ShareActionProvider var4;
      if (!(var2 instanceof ShareActionProvider)) {
         var4 = new ShareActionProvider(var1.getContext());
      } else {
         var4 = (ShareActionProvider)var2;
      }

      StringBuilder var3 = new StringBuilder();
      var3.append(".sharecompat_");
      var3.append(var1.getContext().getClass().getName());
      var4.setShareHistoryFileName(var3.toString());
      var4.setShareIntent(var1.getIntent());
      var0.setActionProvider(var4);
      if (VERSION.SDK_INT < 16 && !var0.hasSubMenu()) {
         var0.setIntent(var1.createChooserIntent());
      }

   }

   public static ComponentName getCallingActivity(Activity var0) {
      Intent var1 = var0.getIntent();
      ComponentName var2 = var0.getCallingActivity();
      ComponentName var3 = var2;
      if (var2 == null) {
         var3 = getCallingActivity(var1);
      }

      return var3;
   }

   static ComponentName getCallingActivity(Intent var0) {
      ComponentName var1 = (ComponentName)var0.getParcelableExtra("androidx.core.app.EXTRA_CALLING_ACTIVITY");
      ComponentName var2 = var1;
      if (var1 == null) {
         var2 = (ComponentName)var0.getParcelableExtra("android.support.v4.app.EXTRA_CALLING_ACTIVITY");
      }

      return var2;
   }

   public static String getCallingPackage(Activity var0) {
      Intent var1 = var0.getIntent();
      String var2 = var0.getCallingPackage();
      String var3 = var2;
      if (var2 == null) {
         var3 = var2;
         if (var1 != null) {
            var3 = getCallingPackage(var1);
         }
      }

      return var3;
   }

   static String getCallingPackage(Intent var0) {
      String var1 = var0.getStringExtra("androidx.core.app.EXTRA_CALLING_PACKAGE");
      String var2 = var1;
      if (var1 == null) {
         var2 = var0.getStringExtra("android.support.v4.app.EXTRA_CALLING_PACKAGE");
      }

      return var2;
   }

   public static class IntentBuilder {
      private ArrayList<String> mBccAddresses;
      private ArrayList<String> mCcAddresses;
      private CharSequence mChooserTitle;
      private final Context mContext;
      private final Intent mIntent;
      private ArrayList<Uri> mStreams;
      private ArrayList<String> mToAddresses;

      private IntentBuilder(Context var1, ComponentName var2) {
         this.mContext = (Context)Preconditions.checkNotNull(var1);
         Intent var3 = (new Intent()).setAction("android.intent.action.SEND");
         this.mIntent = var3;
         var3.putExtra("androidx.core.app.EXTRA_CALLING_PACKAGE", var1.getPackageName());
         this.mIntent.putExtra("android.support.v4.app.EXTRA_CALLING_PACKAGE", var1.getPackageName());
         this.mIntent.putExtra("androidx.core.app.EXTRA_CALLING_ACTIVITY", var2);
         this.mIntent.putExtra("android.support.v4.app.EXTRA_CALLING_ACTIVITY", var2);
         this.mIntent.addFlags(524288);
      }

      private void combineArrayExtra(String var1, ArrayList<String> var2) {
         String[] var3 = this.mIntent.getStringArrayExtra(var1);
         int var4;
         if (var3 != null) {
            var4 = var3.length;
         } else {
            var4 = 0;
         }

         String[] var5 = new String[var2.size() + var4];
         var2.toArray(var5);
         if (var3 != null) {
            System.arraycopy(var3, 0, var5, var2.size(), var4);
         }

         this.mIntent.putExtra(var1, var5);
      }

      private void combineArrayExtra(String var1, String[] var2) {
         Intent var3 = this.getIntent();
         String[] var4 = var3.getStringArrayExtra(var1);
         int var5;
         if (var4 != null) {
            var5 = var4.length;
         } else {
            var5 = 0;
         }

         String[] var6 = new String[var2.length + var5];
         if (var4 != null) {
            System.arraycopy(var4, 0, var6, 0, var5);
         }

         System.arraycopy(var2, 0, var6, var5, var2.length);
         var3.putExtra(var1, var6);
      }

      public static ShareCompat.IntentBuilder from(Activity var0) {
         return from((Context)Preconditions.checkNotNull(var0), var0.getComponentName());
      }

      private static ShareCompat.IntentBuilder from(Context var0, ComponentName var1) {
         return new ShareCompat.IntentBuilder(var0, var1);
      }

      public ShareCompat.IntentBuilder addEmailBcc(String var1) {
         if (this.mBccAddresses == null) {
            this.mBccAddresses = new ArrayList();
         }

         this.mBccAddresses.add(var1);
         return this;
      }

      public ShareCompat.IntentBuilder addEmailBcc(String[] var1) {
         this.combineArrayExtra("android.intent.extra.BCC", var1);
         return this;
      }

      public ShareCompat.IntentBuilder addEmailCc(String var1) {
         if (this.mCcAddresses == null) {
            this.mCcAddresses = new ArrayList();
         }

         this.mCcAddresses.add(var1);
         return this;
      }

      public ShareCompat.IntentBuilder addEmailCc(String[] var1) {
         this.combineArrayExtra("android.intent.extra.CC", var1);
         return this;
      }

      public ShareCompat.IntentBuilder addEmailTo(String var1) {
         if (this.mToAddresses == null) {
            this.mToAddresses = new ArrayList();
         }

         this.mToAddresses.add(var1);
         return this;
      }

      public ShareCompat.IntentBuilder addEmailTo(String[] var1) {
         this.combineArrayExtra("android.intent.extra.EMAIL", var1);
         return this;
      }

      public ShareCompat.IntentBuilder addStream(Uri var1) {
         Uri var2 = (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
         if (this.mStreams == null && var2 == null) {
            return this.setStream(var1);
         } else {
            if (this.mStreams == null) {
               this.mStreams = new ArrayList();
            }

            if (var2 != null) {
               this.mIntent.removeExtra("android.intent.extra.STREAM");
               this.mStreams.add(var2);
            }

            this.mStreams.add(var1);
            return this;
         }
      }

      public Intent createChooserIntent() {
         return Intent.createChooser(this.getIntent(), this.mChooserTitle);
      }

      Context getContext() {
         return this.mContext;
      }

      public Intent getIntent() {
         ArrayList var1 = this.mToAddresses;
         if (var1 != null) {
            this.combineArrayExtra("android.intent.extra.EMAIL", var1);
            this.mToAddresses = null;
         }

         var1 = this.mCcAddresses;
         if (var1 != null) {
            this.combineArrayExtra("android.intent.extra.CC", var1);
            this.mCcAddresses = null;
         }

         var1 = this.mBccAddresses;
         if (var1 != null) {
            this.combineArrayExtra("android.intent.extra.BCC", var1);
            this.mBccAddresses = null;
         }

         var1 = this.mStreams;
         boolean var2 = true;
         if (var1 == null || var1.size() <= 1) {
            var2 = false;
         }

         boolean var3 = "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
         if (!var2 && var3) {
            this.mIntent.setAction("android.intent.action.SEND");
            var1 = this.mStreams;
            if (var1 != null && !var1.isEmpty()) {
               this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable)this.mStreams.get(0));
            } else {
               this.mIntent.removeExtra("android.intent.extra.STREAM");
            }

            this.mStreams = null;
         }

         if (var2 && !var3) {
            this.mIntent.setAction("android.intent.action.SEND_MULTIPLE");
            var1 = this.mStreams;
            if (var1 != null && !var1.isEmpty()) {
               this.mIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.mStreams);
            } else {
               this.mIntent.removeExtra("android.intent.extra.STREAM");
            }
         }

         return this.mIntent;
      }

      public ShareCompat.IntentBuilder setChooserTitle(int var1) {
         return this.setChooserTitle(this.mContext.getText(var1));
      }

      public ShareCompat.IntentBuilder setChooserTitle(CharSequence var1) {
         this.mChooserTitle = var1;
         return this;
      }

      public ShareCompat.IntentBuilder setEmailBcc(String[] var1) {
         this.mIntent.putExtra("android.intent.extra.BCC", var1);
         return this;
      }

      public ShareCompat.IntentBuilder setEmailCc(String[] var1) {
         this.mIntent.putExtra("android.intent.extra.CC", var1);
         return this;
      }

      public ShareCompat.IntentBuilder setEmailTo(String[] var1) {
         if (this.mToAddresses != null) {
            this.mToAddresses = null;
         }

         this.mIntent.putExtra("android.intent.extra.EMAIL", var1);
         return this;
      }

      public ShareCompat.IntentBuilder setHtmlText(String var1) {
         this.mIntent.putExtra("android.intent.extra.HTML_TEXT", var1);
         if (!this.mIntent.hasExtra("android.intent.extra.TEXT")) {
            this.setText(Html.fromHtml(var1));
         }

         return this;
      }

      public ShareCompat.IntentBuilder setStream(Uri var1) {
         if (!"android.intent.action.SEND".equals(this.mIntent.getAction())) {
            this.mIntent.setAction("android.intent.action.SEND");
         }

         this.mStreams = null;
         this.mIntent.putExtra("android.intent.extra.STREAM", var1);
         return this;
      }

      public ShareCompat.IntentBuilder setSubject(String var1) {
         this.mIntent.putExtra("android.intent.extra.SUBJECT", var1);
         return this;
      }

      public ShareCompat.IntentBuilder setText(CharSequence var1) {
         this.mIntent.putExtra("android.intent.extra.TEXT", var1);
         return this;
      }

      public ShareCompat.IntentBuilder setType(String var1) {
         this.mIntent.setType(var1);
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

      private IntentReader(Context var1, Intent var2) {
         this.mContext = (Context)Preconditions.checkNotNull(var1);
         this.mIntent = (Intent)Preconditions.checkNotNull(var2);
         this.mCallingPackage = ShareCompat.getCallingPackage(var2);
         this.mCallingActivity = ShareCompat.getCallingActivity(var2);
      }

      public static ShareCompat.IntentReader from(Activity var0) {
         return from((Context)Preconditions.checkNotNull(var0), var0.getIntent());
      }

      private static ShareCompat.IntentReader from(Context var0, Intent var1) {
         return new ShareCompat.IntentReader(var0, var1);
      }

      private static void withinStyle(StringBuilder var0, CharSequence var1, int var2, int var3) {
         for(; var2 < var3; ++var2) {
            char var4 = var1.charAt(var2);
            if (var4 == '<') {
               var0.append("&lt;");
            } else if (var4 == '>') {
               var0.append("&gt;");
            } else if (var4 == '&') {
               var0.append("&amp;");
            } else if (var4 <= '~' && var4 >= ' ') {
               if (var4 != ' ') {
                  var0.append(var4);
               } else {
                  while(true) {
                     int var5 = var2 + 1;
                     if (var5 >= var3 || var1.charAt(var5) != ' ') {
                        var0.append(' ');
                        break;
                     }

                     var0.append("&nbsp;");
                     var2 = var5;
                  }
               }
            } else {
               var0.append("&#");
               var0.append(var4);
               var0.append(";");
            }
         }

      }

      public ComponentName getCallingActivity() {
         return this.mCallingActivity;
      }

      public Drawable getCallingActivityIcon() {
         if (this.mCallingActivity == null) {
            return null;
         } else {
            PackageManager var1 = this.mContext.getPackageManager();

            try {
               Drawable var3 = var1.getActivityIcon(this.mCallingActivity);
               return var3;
            } catch (NameNotFoundException var2) {
               Log.e("IntentReader", "Could not retrieve icon for calling activity", var2);
               return null;
            }
         }
      }

      public Drawable getCallingApplicationIcon() {
         if (this.mCallingPackage == null) {
            return null;
         } else {
            PackageManager var1 = this.mContext.getPackageManager();

            try {
               Drawable var3 = var1.getApplicationIcon(this.mCallingPackage);
               return var3;
            } catch (NameNotFoundException var2) {
               Log.e("IntentReader", "Could not retrieve icon for calling application", var2);
               return null;
            }
         }
      }

      public CharSequence getCallingApplicationLabel() {
         if (this.mCallingPackage == null) {
            return null;
         } else {
            PackageManager var1 = this.mContext.getPackageManager();

            try {
               CharSequence var3 = var1.getApplicationLabel(var1.getApplicationInfo(this.mCallingPackage, 0));
               return var3;
            } catch (NameNotFoundException var2) {
               Log.e("IntentReader", "Could not retrieve label for calling application", var2);
               return null;
            }
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
         String var1 = this.mIntent.getStringExtra("android.intent.extra.HTML_TEXT");
         String var2 = var1;
         if (var1 == null) {
            CharSequence var3 = this.getText();
            if (var3 instanceof Spanned) {
               var2 = Html.toHtml((Spanned)var3);
            } else {
               var2 = var1;
               if (var3 != null) {
                  if (VERSION.SDK_INT >= 16) {
                     var2 = Html.escapeHtml(var3);
                  } else {
                     StringBuilder var4 = new StringBuilder();
                     withinStyle(var4, var3, 0, var3.length());
                     var2 = var4.toString();
                  }
               }
            }
         }

         return var2;
      }

      public Uri getStream() {
         return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
      }

      public Uri getStream(int var1) {
         if (this.mStreams == null && this.isMultipleShare()) {
            this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
         }

         ArrayList var2 = this.mStreams;
         if (var2 != null) {
            return (Uri)var2.get(var1);
         } else if (var1 == 0) {
            return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
         } else {
            StringBuilder var3 = new StringBuilder();
            var3.append("Stream items available: ");
            var3.append(this.getStreamCount());
            var3.append(" index requested: ");
            var3.append(var1);
            throw new IndexOutOfBoundsException(var3.toString());
         }
      }

      public int getStreamCount() {
         if (this.mStreams == null && this.isMultipleShare()) {
            this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
         }

         ArrayList var1 = this.mStreams;
         return var1 != null ? var1.size() : this.mIntent.hasExtra("android.intent.extra.STREAM");
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
         String var1 = this.mIntent.getAction();
         boolean var2;
         if (!"android.intent.action.SEND".equals(var1) && !"android.intent.action.SEND_MULTIPLE".equals(var1)) {
            var2 = false;
         } else {
            var2 = true;
         }

         return var2;
      }

      public boolean isSingleShare() {
         return "android.intent.action.SEND".equals(this.mIntent.getAction());
      }
   }
}

package com.syntak.library;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.os.Build.VERSION;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import java.io.File;
import java.net.URL;
import java.util.Locale;

public class UiOp {
   static final float ALPHA_DISABLED = 0.4F;
   static final int SHADOW_BOTTOM = 1;
   static final int SHADOW_LEFT = 4;
   static final int SHADOW_NONE = 0;
   static final int SHADOW_RIGHT = 2;
   static final int SHADOW_TOP = 8;
   static final String TAG_BROADCAST_NOTIFY = "com.syntak.notify";

   public static void FullScreen(Context var0) {
      View var2 = ((Activity)var0).getWindow().getDecorView();
      if (VERSION.SDK_INT < 16 || VERSION.SDK_INT >= 19) {
         int var1 = VERSION.SDK_INT;
      }

      var2.setSystemUiVisibility(4871);
   }

   public static void broadcast_notify(Context var0, int var1) {
      broadcast_notify(var0, var1, (Bundle)null);
   }

   public static void broadcast_notify(Context var0, int var1, Bundle var2) {
      Intent var3 = new Intent();
      var3.setAction("com.syntak.notify");
      Bundle var4 = var2;
      if (var2 == null) {
         var4 = new Bundle();
      }

      var4.putInt("com.syntak.notify", var1);
      var3.putExtras(var4);
      LocalBroadcastManager.getInstance(var0).sendBroadcast(var3);
   }

   public static void clickView(View var0) {
      var0.performClick();
   }

   public static float disableButton(View var0) {
      float var1 = var0.getAlpha();
      var0.setEnabled(false);
      var0.setAlpha(0.4F);
      return var1;
   }

   public static float disableButton(View var0, float var1) {
      float var2 = var0.getAlpha();
      var0.setEnabled(false);
      var0.setAlpha(var1);
      return var2;
   }

   public static void disableScreenCapture(Activity var0) {
      var0.getWindow().setFlags(8192, 8192);
   }

   public static void drawDotsSwipeIndicator(Context var0, LinearLayout var1, int var2, int var3, int var4, int var5, int var6) {
      var1.removeAllViews();

      for(int var7 = 0; var7 < var3; ++var7) {
         ImageView var8 = new ImageView(var0);
         var8.setLayoutParams(new LayoutParams(-2, -2));
         var8.getLayoutParams().width = var2;
         var8.getLayoutParams().height = var2;
         if (var7 == var4) {
            try {
               var8.setImageResource(var5);
            } catch (Exception var10) {
            }
         } else {
            var8.setImageResource(var6);
         }

         var1.addView(var8);
      }

      var1.invalidate();
   }

   public static void enableButton(View var0, float var1) {
      var0.setEnabled(true);
      var0.setAlpha(var1);
   }

   public static void fitBitmapToImageViewWithLimit(ImageView var0, Bitmap var1, int var2, int var3) {
   }

   public static String getCurrentLanguageCode() {
      return Locale.getDefault().getLanguage();
   }

   public static int getScreenRotationAngle(Activity var0) {
      int var1 = var0.getWindowManager().getDefaultDisplay().getRotation();
      byte var2 = 0;
      short var3 = var2;
      if (var1 != 0) {
         if (var1 != 1) {
            if (var1 != 2) {
               if (var1 != 3) {
                  var3 = var2;
               } else {
                  var3 = 270;
               }
            } else {
               var3 = 180;
            }
         } else {
            var3 = 90;
         }
      }

      return var3;
   }

   public static Locale getUserLocale(Context var0) {
      Configuration var1 = var0.getResources().getConfiguration();
      return VERSION.SDK_INT >= 24 ? var1.getLocales().get(0) : var1.locale;
   }

   public static long get_PackageVersionCode(Context var0) {
      long var1;
      try {
         PackageInfo var4 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 0);
         if (VERSION.SDK_INT < 28) {
            var1 = (long)var4.versionCode;
         } else {
            var1 = var4.getLongVersionCode();
         }
      } catch (NameNotFoundException var3) {
         var3.printStackTrace();
         var1 = 0L;
      }

      return var1;
   }

   public static String get_PackageVersionName(Context var0) {
      String var2;
      try {
         var2 = var0.getPackageManager().getPackageInfo(var0.getPackageName(), 0).versionName;
      } catch (NameNotFoundException var1) {
         var1.printStackTrace();
         var2 = null;
      }

      return var2;
   }

   public static void half_Alpha_short(Handler var0, final View var1) {
      final float var2 = var1.getAlpha();
      var1.setAlpha((float)((double)var2 * 0.5D));
      var0.postDelayed(new Runnable() {
         public void run() {
            var1.setAlpha(var2);
         }
      }, 200L);
   }

   public static void hideActionBar(AppCompatActivity var0) {
      if (var0.getSupportActionBar() != null) {
         var0.getSupportActionBar().hide();
      }

   }

   public static boolean isAppInstalled(Activity var0, String var1) {
      PackageManager var3 = var0.getPackageManager();

      try {
         var3.getPackageInfo(var1, 1);
         return true;
      } catch (NameNotFoundException var2) {
         return false;
      }
   }

   public static void keepAwake(Context var0) {
      ((Activity)var0).getWindow().addFlags(128);
   }

   public static void killMyProcess() {
      Process.killProcess(Process.myPid());
   }

   public static void launchEmail(Activity var0, String var1, String var2, String var3) {
      Intent var4 = new Intent("android.intent.action.SEND");
      var4.setType("plain/text");
      if (var1 != null) {
         var4.putExtra("android.intent.extra.EMAIL", new String[]{var1});
      }

      if (var2 != null) {
         var4.putExtra("android.intent.extra.SUBJECT", var2);
      }

      if (var3 != null) {
         var4.putExtra("android.intent.extra.TEXT", var3);
      }

      try {
         var0.startActivity(var4);
      } catch (ActivityNotFoundException var5) {
      }

   }

   public static void launchGooglePlayForPackage(Activity var0, String var1) {
      try {
         StringBuilder var3 = new StringBuilder();
         var3.append("market://details?id=");
         var3.append(var1);
         Intent var5 = new Intent("android.intent.action.VIEW", Uri.parse(var3.toString()));
         var0.startActivity(var5);
      } catch (ActivityNotFoundException var4) {
         StringBuilder var2 = new StringBuilder();
         var2.append("https://play.google.com/store/apps/details?id=");
         var2.append(var1);
         var0.startActivity(new Intent("android.intent.action.VIEW", Uri.parse(var2.toString())));
      }

   }

   public static boolean launch_app(Context var0, String var1) {
      Intent var2 = var0.getPackageManager().getLaunchIntentForPackage(var1);
      if (var2 == null) {
         return false;
      } else {
         var2.addCategory("android.intent.category.LAUNCHER");
         var2.setFlags(268435456);
         var0.startActivity(var2);
         return true;
      }
   }

   public static void launch_browser(Activity var0, String var1) {
      Intent var2 = new Intent("android.intent.action.VIEW");
      var2.setData(Uri.parse(var1));
      var2.setFlags(285212672);
      var0.startActivity(var2);
   }

   public static void launch_browser_search(Activity var0, String var1) {
      Intent var2 = new Intent("android.intent.action.WEB_SEARCH");
      var2.putExtra("query", var1);
      var2.setFlags(285212672);
      var0.startActivity(var2);
   }

   public static void make_PhoneCall(Context var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("tel:");
      var2.append(var1);
      var0.startActivity(new Intent("android.intent.action.CALL", Uri.parse(var2.toString())));
   }

   public static void pickDate(Context var0, final TextView var1) {
      String var2 = var1.getText().toString();
      int var3 = var2.indexOf(47);
      int var4 = var2.lastIndexOf(47);
      int var5 = Integer.parseInt(var2.substring(0, var3));
      var3 = Integer.parseInt(var2.substring(var3 + 1, var4));
      var4 = Integer.parseInt(var2.substring(var4 + 1));
      (new DatePickerDialog(var0, new OnDateSetListener() {
         public void onDateSet(DatePicker var1x, int var2, int var3, int var4) {
            StringBuilder var5 = new StringBuilder();
            var5.append(String.format("%04d", var2));
            var5.append("/");
            var5.append(String.format("%02d", var3 + 1));
            var5.append("/");
            var5.append(String.format("%02d", var4));
            String var6 = var5.toString();
            var1.setText(var6);
         }
      }, var5, var3 - 1, var4)).show();
   }

   public static void pickTime(Context var0, final TextView var1) {
      int var2 = Integer.parseInt(var1.getText().toString().substring(0, 2));
      int var3 = Integer.parseInt(var1.getText().toString().substring(3));
      (new TimePickerDialog(var0, new OnTimeSetListener() {
         public void onTimeSet(TimePicker var1x, int var2, int var3) {
            StringBuilder var4 = new StringBuilder();
            var4.append(String.format("%02d", var2));
            var4.append(":");
            var4.append(String.format("%02d", var3));
            var1.setText(var4);
         }
      }, var2, var3, true)).show();
   }

   public static void playNotificationRingtone(Context var0) {
      RingtoneManager.getRingtone(var0, RingtoneManager.getDefaultUri(2)).play();
   }

   public static PopupWindow popupBitmap(Context var0, Bitmap var1, int var2, int var3, View var4) {
      float var5 = (float)var2 / (float)var1.getWidth();
      float var6 = (float)var3 / (float)var1.getHeight();
      if (var6 >= var5) {
         var6 = var5;
      }

      return popupBitmapText(var0, var1, (String)null, 0, 0, 0, var6, 0, var4, 17, 0, 0, 0, 0, 0, 10, -5592406);
   }

   public static PopupWindow popupBitmapText(Context var0, Bitmap var1, String var2, int var3, int var4, int var5, float var6, int var7, View var8, int var9, int var10, int var11, int var12, int var13, int var14, int var15, int var16) {
      View var17 = ((LayoutInflater)var0.getSystemService("layout_inflater")).inflate(R.layout.display_image_popup, (ViewGroup)null);
      PopupWindow var18 = new PopupWindow(var17, -2, -2);
      LinearLayout var19 = (LinearLayout)var17.findViewById(R.id.frame);
      ImageView var20 = (ImageView)var17.findViewById(R.id.image);
      TextView var32 = (TextView)var17.findViewById(R.id.text_top);
      TextView var21 = (TextView)var17.findViewById(R.id.text_bottom);
      TextView var22 = (TextView)var17.findViewById(R.id.text_left);
      TextView var23 = (TextView)var17.findViewById(R.id.text_right);
      RelativeLayout var24 = (RelativeLayout)var17.findViewById(R.id.border);
      RelativeLayout var37 = (RelativeLayout)var17.findViewById(R.id.shadow);
      int var25 = var1.getWidth();
      int var26 = var1.getHeight();
      int var27 = (int)((float)var25 * var6);
      int var28 = (int)((float)var26 * var6);
      android.view.ViewGroup.LayoutParams var29 = var19.getLayoutParams();
      var29.width = var27;
      var19.setLayoutParams(var29);
      var19.setBackgroundColor(var7);
      android.view.ViewGroup.LayoutParams var38 = var20.getLayoutParams();
      var38.height = var28;
      var20.setImageBitmap(var1);
      var20.setLayoutParams(var38);
      var18.setBackgroundDrawable(new BitmapDrawable());
      var18.setOutsideTouchable(true);
      if (StringOp.strlen(var2) > 0) {
         if ((var3 & 48) != 48) {
            var32 = null;
         }

         if ((var3 & 80) == 80) {
            var32 = var21;
         }

         if ((var3 & 3) == 3) {
            var32 = var22;
         }

         if ((var3 & 5) == 5) {
            var32 = var23;
         }

         var32.setVisibility(0);
         var32.setText(var2);
         var32.setTextSize((float)var4);
         var32.setTextColor(var5);
      }

      boolean var34;
      if ((var9 & 3) == 3) {
         var34 = true;
      } else {
         var34 = false;
      }

      boolean var33;
      if ((var9 & 48) == 48) {
         var33 = true;
      } else {
         var33 = false;
      }

      boolean var35;
      if ((var9 & 5) == 5) {
         var35 = true;
      } else {
         var35 = false;
      }

      boolean var30;
      if ((var9 & 80) == 80) {
         var30 = true;
      } else {
         var30 = false;
      }

      boolean var36;
      if ((var9 & 1) == 1) {
         var36 = true;
      } else {
         var36 = false;
      }

      boolean var31;
      if ((var9 & 16) == 16) {
         var31 = true;
      } else {
         var31 = false;
      }

      label107: {
         if (var33) {
            var3 = var26;
         } else {
            if (var30) {
               var3 = var11 - var28;
               break label107;
            }

            if (!var31) {
               var3 = 0;
               break label107;
            }

            var3 = var26 + (var28 - var26) / 2;
         }

         var3 = var11 - var3;
      }

      if (var34) {
         var4 = var10;
      } else {
         label98: {
            if (var35) {
               var4 = var27 - var25;
            } else {
               if (!var36) {
                  var4 = 0;
                  break label98;
               }

               var4 = (var27 - var25) / 2;
            }

            var4 = var10 - var4;
         }
      }

      var24.setBackgroundColor(var16);
      var24.setPadding(var15, var15, var15, var15);
      if ((var12 & 4) != 0) {
         var5 = var13;
      } else {
         var5 = 0;
      }

      if ((var12 & 8) != 0) {
         var7 = var13;
      } else {
         var7 = 0;
      }

      if ((var12 & 2) != 0) {
         var15 = var13;
      } else {
         var15 = 0;
      }

      if ((var12 & 1) != 0) {
         var12 = var13;
      } else {
         var12 = 0;
      }

      var37.setPadding(var5, var7, var15, var12);
      var37.setBackgroundColor(var14);
      if (var9 == 17) {
         var18.showAtLocation(var8, 17, var10, var11);
      } else {
         var18.showAsDropDown(var8, var4, var3);
      }

      return var18;
   }

   public static PopupWindow popupImagePath(Context var0, String var1, int var2, int var3, View var4) {
      Bitmap var5 = MediaOp.getSampledBitmapFromPath(var1, var2, var3);
      return var5 == null ? null : popupBitmap(var0, var5, var2, var3, var4);
   }

   public static PopupWindow popupImagePathText(Context var0, String var1, String var2, int var3, int var4, int var5, int var6, int var7, View var8) {
      Bitmap var14 = MediaOp.getSampledBitmapFromPath(var1, var6, var7);
      if (var14 == null) {
         return null;
      } else {
         int var9 = StringOp.strlen(var2);
         byte var10 = 1;
         byte var11 = 1;
         byte var15;
         if (var9 > 0) {
            if ((var3 & 48) == 48) {
               var10 = 2;
            } else {
               var10 = 1;
            }

            if ((var3 & 80) == 80) {
               var10 = 2;
            }

            if ((var3 & 3) == 3) {
               var11 = 2;
            }

            var15 = var10;
            if ((var3 & 5) == 5) {
               var11 = 2;
               var15 = var10;
            }
         } else {
            var15 = 1;
            var11 = var10;
         }

         float var12 = (float)var6 / (float)var14.getWidth() / (float)var11;
         float var13 = (float)var7 / (float)var14.getHeight() / (float)var15;
         if (var13 >= var12) {
            var13 = var12;
         }

         return popupBitmapText(var0, var14, var2, var3, var4, var5, var13, 0, var8, 17, 0, 0, 0, 0, 0, 10, -5592406);
      }
   }

   public static PopupWindow popupImageView(Context var0, ImageView var1, View var2, float var3, int var4, int var5, int var6, int var7, int var8) {
      BitmapDrawable var9 = (BitmapDrawable)var1.getBackground();
      Bitmap var10;
      if (var9 != null) {
         var10 = var9.getBitmap();
      } else {
         var10 = ((BitmapDrawable)var1.getDrawable()).getBitmap();
      }

      return popupBitmapText(var0, var10, (String)null, 0, 0, 0, var3, 0, var2, var4, var5, var6, 1, var7, var8, 0, 0);
   }

   public static void restart_app(Context var0, int var1) {
      int var2 = var1;
      if (var1 == 0) {
         var2 = 1;
      }

      Log.e("", "restarting app");
      PendingIntent var3 = PendingIntent.getActivity(var0, 0, var0.getPackageManager().getLaunchIntentForPackage(var0.getPackageName()), 67108864);
      ((AlarmManager)var0.getSystemService("alarm")).set(1, System.currentTimeMillis() + (long)var2, var3);
      System.exit(2);
   }

   public static void setScreenBrightness(Context var0, float var1) {
      Window var2 = ((Activity)var0).getWindow();
      android.view.WindowManager.LayoutParams var3 = var2.getAttributes();
      var3.screenBrightness = var1;
      var2.setAttributes(var3);
   }

   public static void setViewSize(View var0, int var1, int var2) {
      var0.getLayoutParams().width = var1;
      var0.getLayoutParams().height = var2;
      var0.requestLayout();
   }

   public static void show_hide_TextView(TextView var0, String var1) {
      if (var1 != null && !var1.equals("null") && var1.length() != 0) {
         var0.setVisibility(0);
         var0.setText(var1);
      } else {
         var0.setVisibility(8);
      }

   }

   public static void toast_message(Context var0, int var1) {
      toast_message(var0, var0.getResources().getString(var1), false);
   }

   public static void toast_message(Context var0, String var1, boolean var2) {
      Toast.makeText(var0, var1, var2).show();
   }

   public static void toast_message_image(Context var0, String var1, Drawable var2, Drawable var3, Drawable var4, Drawable var5) {
      View var6 = ((LayoutInflater)var0.getSystemService("layout_inflater")).inflate(R.layout.toast_image, (ViewGroup)null);
      LinearLayout var7 = (LinearLayout)var6.findViewById(R.id.toast_layout);
      ImageView var8 = (ImageView)var6.findViewById(R.id.image_left);
      ImageView var9 = (ImageView)var6.findViewById(R.id.image_right);
      ImageView var13 = (ImageView)var6.findViewById(R.id.image_top);
      ImageView var10 = (ImageView)var6.findViewById(R.id.image_bottom);
      TextView var11 = (TextView)var6.findViewById(R.id.text);
      if (var2 == null) {
         var8.setVisibility(8);
      } else {
         var8.setImageDrawable(var2);
      }

      if (var3 == null) {
         var9.setVisibility(8);
      } else {
         var9.setImageDrawable(var3);
      }

      if (var4 == null) {
         var13.setVisibility(8);
      } else {
         var13.setImageDrawable(var4);
      }

      if (var5 == null) {
         var10.setVisibility(8);
      } else {
         var10.setImageDrawable(var5);
      }

      var11.setText(var1);
      Toast var12 = new Toast(var0);
      var12.setGravity(16, 0, 0);
      var12.setDuration(1);
      var12.setView(var6);
      var12.show();
   }

   public static void toast_message_long(Context var0, int var1) {
      toast_message(var0, var0.getResources().getString(var1), true);
   }

   public static void touchView(View var0, MotionEvent var1) {
      var0.dispatchTouchEvent(var1);
   }

   public static void viewAnimation(final View var0, UiOp.TRANSLATION_AXIS var1, float var2, int var3, final UiOp.INTERPOLATION_TYPE var4, final boolean var5) {
      if (var5) {
         var0.setVisibility(0);
      }

      Object var6 = null;
      int var7 = null.$SwitchMap$com$syntak$library$UiOp$TRANSLATION_AXIS[var1.ordinal()];
      ObjectAnimator var8;
      if (var7 != 1) {
         if (var7 != 2) {
            var8 = (ObjectAnimator)var6;
         } else {
            var8 = ObjectAnimator.ofFloat(var0, "translationY", new float[]{var2});
         }
      } else {
         var8 = ObjectAnimator.ofFloat(var0, "translationX", new float[]{var2});
      }

      if (var8 != null) {
         var8.setDuration((long)var3);
         var8.setInterpolator(new TimeInterpolator() {
            public float getInterpolation(float var1) {
               int var2 = null.$SwitchMap$com$syntak$library$UiOp$INTERPOLATION_TYPE[var4.ordinal()];
               float var3 = var1;
               if (var2 != 1) {
                  if (var2 == 2 || var2 != 3 && (double)var1 < 0.5D) {
                     var3 = var1 * var1;
                  } else {
                     var3 = 2.0F * var1 - var1 * var1;
                  }
               }

               return var3;
            }
         });
         var8.start();
         var8.addListener(new AnimatorListenerAdapter() {
            public void onAnimationEnd(Animator var1) {
               super.onAnimationEnd(var1);
               var1.removeAllListeners();
               if (var5) {
                  var0.setVisibility(0);
               } else {
                  var0.setVisibility(8);
               }

            }
         });
      }
   }

   public static void wakeUp(Context var0, String var1) {
      Activity var3 = (Activity)var0;
      Intent var2 = var3.getIntent();
      if (var2.hasExtra(var1) && var2.getExtras().getBoolean(var1)) {
         var3.getWindow().setFlags(6815744, 6815744);
      }

   }

   public static class DisplayUrlCachedImage {
      Bitmap bitmap = null;
      boolean flag_local_existed = false;
      boolean flag_to_download = false;
      ImageView iv;
      String local;
      String url;

      public DisplayUrlCachedImage(final ImageView var1, String var2, String var3) {
         this.iv = var1;
         this.url = var2;
         this.local = var3;
         if (FileOp.checkFileExist(var3)) {
            Bitmap var4 = MediaOp.getBitmapFromPath(var3);
            this.bitmap = var4;
            if (var4 == null) {
               this.flag_to_download = true;
            } else {
               this.flag_local_existed = true;
               var1.setImageBitmap(var4);
            }
         } else {
            this.flag_to_download = true;
         }

         if (this.flag_to_download) {
            (new UiOp.DisplayUrlCachedImage.background_thread() {
               public void OnDownloaded(Bitmap var1x) {
                  super.OnDownloaded(var1x);
                  DisplayUrlCachedImage.this.OnBitmapDownloaded(var1, var1x);
               }
            }).start();
         }

      }

      public void OnBitmapDownloaded(ImageView var1, Bitmap var2) {
      }

      class background_thread extends Thread {
         public void OnDownloaded(Bitmap var1) {
         }

         public void run() {
            long var1 = HttpOp.HttpGetLastModified(DisplayUrlCachedImage.this.url);
            if (DisplayUrlCachedImage.this.flag_local_existed && (new File(DisplayUrlCachedImage.this.local)).lastModified() != var1) {
               DisplayUrlCachedImage.this.flag_to_download = true;
            }

            if (DisplayUrlCachedImage.this.flag_to_download && "HTTP OK".equals(HttpOp.HttpDownloadFile(DisplayUrlCachedImage.this.url, DisplayUrlCachedImage.this.local))) {
               FileOp.setFileLastModified(DisplayUrlCachedImage.this.local, var1);
               UiOp.DisplayUrlCachedImage var3 = DisplayUrlCachedImage.this;
               var3.bitmap = MediaOp.getBitmapFromPath(var3.local);
               this.OnDownloaded(DisplayUrlCachedImage.this.bitmap);
            }

         }
      }
   }

   public static class DisplayUrlIcon extends AsyncTask<String, Void, Bitmap> {
      private ImageView imageView;

      public DisplayUrlIcon(ImageView var1) {
         this.imageView = var1;
      }

      protected Bitmap doInBackground(String... var1) {
         String var4 = var1[0];

         Bitmap var5;
         try {
            URL var2 = new URL(var4);
            var5 = BitmapFactory.decodeStream(var2.openStream());
         } catch (Exception var3) {
            Log.e("Error", var3.getMessage());
            var3.printStackTrace();
            var5 = null;
         }

         return var5;
      }

      protected void onPostExecute(Bitmap var1) {
         if (var1 != null) {
            this.imageView.setVisibility(0);
            this.imageView.setImageBitmap(var1);
         }

      }
   }

   public static class DisplayUrlImage extends AsyncTask<String, Void, Bitmap> {
      private Context context;
      private ImageView imageView;

      public DisplayUrlImage(Context var1, ImageView var2) {
         this.context = var1;
         this.imageView = var2;
      }

      protected Bitmap doInBackground(String... var1) {
         String var4 = var1[0];

         Bitmap var5;
         try {
            URL var2 = new URL(var4);
            var5 = BitmapFactory.decodeStream(var2.openStream());
         } catch (Exception var3) {
            Log.e("Error", var3.getMessage());
            var3.printStackTrace();
            var5 = null;
         }

         return var5;
      }

      protected void onPostExecute(Bitmap var1) {
         if (var1 != null) {
            this.imageView.setVisibility(0);
            MediaOp.fitImageToView(this.context, this.imageView, var1);
         }

      }
   }

   public static enum INTERPOLATION_TYPE {
      ACCELARATION,
      ACCEL_DECEL,
      DECELATION,
      DEFAULT,
      LINEAR;

      static {
         UiOp.INTERPOLATION_TYPE var0 = new UiOp.INTERPOLATION_TYPE("ACCEL_DECEL", 4);
         ACCEL_DECEL = var0;
      }
   }

   public static enum TRANSLATION_AXIS {
      X,
      Y;

      static {
         UiOp.TRANSLATION_AXIS var0 = new UiOp.TRANSLATION_AXIS("Y", 1);
         Y = var0;
      }
   }
}

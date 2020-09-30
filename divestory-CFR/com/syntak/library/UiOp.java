/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.animation.Animator
 *  android.animation.Animator$AnimatorListener
 *  android.animation.AnimatorListenerAdapter
 *  android.animation.ObjectAnimator
 *  android.animation.TimeInterpolator
 *  android.app.Activity
 *  android.app.AlarmManager
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.app.PendingIntent
 *  android.app.TimePickerDialog
 *  android.app.TimePickerDialog$OnTimeSetListener
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.media.RingtoneManager
 *  android.net.Uri
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.LocaleList
 *  android.os.Process
 *  android.util.Log
 *  android.view.Display
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 *  android.widget.DatePicker
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.PopupWindow
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 *  android.widget.TimePicker
 *  android.widget.Toast
 */
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
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.LocaleList;
import android.os.Process;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import com.syntak.library.FileOp;
import com.syntak.library.HttpOp;
import com.syntak.library.MediaOp;
import com.syntak.library.R;
import com.syntak.library.StringOp;
import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.Locale;

public class UiOp {
    static final float ALPHA_DISABLED = 0.4f;
    static final int SHADOW_BOTTOM = 1;
    static final int SHADOW_LEFT = 4;
    static final int SHADOW_NONE = 0;
    static final int SHADOW_RIGHT = 2;
    static final int SHADOW_TOP = 8;
    static final String TAG_BROADCAST_NOTIFY = "com.syntak.notify";

    public static void FullScreen(Context context) {
        context = ((Activity)context).getWindow().getDecorView();
        if (Build.VERSION.SDK_INT < 16 || Build.VERSION.SDK_INT >= 19) {
            int n = Build.VERSION.SDK_INT;
        }
        context.setSystemUiVisibility(4871);
    }

    public static void broadcast_notify(Context context, int n) {
        UiOp.broadcast_notify(context, n, null);
    }

    public static void broadcast_notify(Context context, int n, Bundle bundle) {
        Intent intent = new Intent();
        intent.setAction(TAG_BROADCAST_NOTIFY);
        Bundle bundle2 = bundle;
        if (bundle == null) {
            bundle2 = new Bundle();
        }
        bundle2.putInt(TAG_BROADCAST_NOTIFY, n);
        intent.putExtras(bundle2);
        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);
    }

    public static void clickView(View view) {
        view.performClick();
    }

    public static float disableButton(View view) {
        float f = view.getAlpha();
        view.setEnabled(false);
        view.setAlpha(0.4f);
        return f;
    }

    public static float disableButton(View view, float f) {
        float f2 = view.getAlpha();
        view.setEnabled(false);
        view.setAlpha(f);
        return f2;
    }

    public static void disableScreenCapture(Activity activity) {
        activity.getWindow().setFlags(8192, 8192);
    }

    /*
     * Loose catch block
     * Enabled unnecessary exception pruning
     */
    public static void drawDotsSwipeIndicator(Context context, LinearLayout linearLayout, int n, int n2, int n3, int n4, int n5) {
        linearLayout.removeAllViews();
        int n6 = 0;
        do {
            ImageView imageView;
            block5 : {
                if (n6 >= n2) {
                    linearLayout.invalidate();
                    return;
                }
                imageView = new ImageView(context);
                imageView.setLayoutParams((ViewGroup.LayoutParams)new LinearLayout.LayoutParams(-2, -2));
                imageView.getLayoutParams().width = n;
                imageView.getLayoutParams().height = n;
                if (n6 == n3) {
                    imageView.setImageResource(n4);
                }
                imageView.setImageResource(n5);
                break block5;
                catch (Exception exception) {}
            }
            linearLayout.addView((View)imageView);
            ++n6;
        } while (true);
    }

    public static void enableButton(View view, float f) {
        view.setEnabled(true);
        view.setAlpha(f);
    }

    public static void fitBitmapToImageViewWithLimit(ImageView imageView, Bitmap bitmap, int n, int n2) {
    }

    public static String getCurrentLanguageCode() {
        return Locale.getDefault().getLanguage();
    }

    public static int getScreenRotationAngle(Activity activity) {
        int n;
        int n2 = activity.getWindowManager().getDefaultDisplay().getRotation();
        int n3 = n = 0;
        if (n2 == 0) return n3;
        if (n2 == 1) {
            return 90;
        }
        if (n2 == 2) {
            return 180;
        }
        if (n2 == 3) return 270;
        return n;
    }

    public static Locale getUserLocale(Context context) {
        context = context.getResources().getConfiguration();
        if (Build.VERSION.SDK_INT < 24) return context.locale;
        return context.getLocales().get(0);
    }

    public static long get_PackageVersionCode(Context context) {
        try {
            context = context.getPackageManager().getPackageInfo(context.getPackageName(), 0);
            if (Build.VERSION.SDK_INT >= 28) return context.getLongVersionCode();
            return context.versionCode;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            nameNotFoundException.printStackTrace();
            return 0L;
        }
    }

    public static String get_PackageVersionName(Context object) {
        try {
            return object.getPackageManager().getPackageInfo((String)object.getPackageName(), (int)0).versionName;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            nameNotFoundException.printStackTrace();
            return null;
        }
    }

    public static void half_Alpha_short(Handler handler, final View view) {
        final float f = view.getAlpha();
        view.setAlpha((float)((double)f * 0.5));
        handler.postDelayed(new Runnable(){

            @Override
            public void run() {
                view.setAlpha(f);
            }
        }, 200L);
    }

    public static void hideActionBar(AppCompatActivity appCompatActivity) {
        if (appCompatActivity.getSupportActionBar() == null) return;
        appCompatActivity.getSupportActionBar().hide();
    }

    public static boolean isAppInstalled(Activity activity, String string2) {
        activity = activity.getPackageManager();
        try {
            activity.getPackageInfo(string2, 1);
            return true;
        }
        catch (PackageManager.NameNotFoundException nameNotFoundException) {
            return false;
        }
    }

    public static void keepAwake(Context context) {
        ((Activity)context).getWindow().addFlags(128);
    }

    public static void killMyProcess() {
        Process.killProcess((int)Process.myPid());
    }

    public static void launchEmail(Activity activity, String string2, String string3, String string4) {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("plain/text");
        if (string2 != null) {
            intent.putExtra("android.intent.extra.EMAIL", new String[]{string2});
        }
        if (string3 != null) {
            intent.putExtra("android.intent.extra.SUBJECT", string3);
        }
        if (string4 != null) {
            intent.putExtra("android.intent.extra.TEXT", string4);
        }
        try {
            activity.startActivity(intent);
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            return;
        }
    }

    public static void launchGooglePlayForPackage(Activity activity, String string2) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("market://details?id=");
            stringBuilder.append(string2);
            Intent intent = new Intent("android.intent.action.VIEW", Uri.parse((String)stringBuilder.toString()));
            activity.startActivity(intent);
            return;
        }
        catch (ActivityNotFoundException activityNotFoundException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("https://play.google.com/store/apps/details?id=");
            stringBuilder.append(string2);
            activity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse((String)stringBuilder.toString())));
        }
    }

    public static boolean launch_app(Context context, String string2) {
        string2 = context.getPackageManager().getLaunchIntentForPackage(string2);
        if (string2 == null) {
            return false;
        }
        string2.addCategory("android.intent.category.LAUNCHER");
        string2.setFlags(268435456);
        context.startActivity((Intent)string2);
        return true;
    }

    public static void launch_browser(Activity activity, String string2) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse((String)string2));
        intent.setFlags(285212672);
        activity.startActivity(intent);
    }

    public static void launch_browser_search(Activity activity, String string2) {
        Intent intent = new Intent("android.intent.action.WEB_SEARCH");
        intent.putExtra("query", string2);
        intent.setFlags(285212672);
        activity.startActivity(intent);
    }

    public static void make_PhoneCall(Context context, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("tel:");
        stringBuilder.append(string2);
        context.startActivity(new Intent("android.intent.action.CALL", Uri.parse((String)stringBuilder.toString())));
    }

    public static void pickDate(Context context, final TextView textView) {
        String string2 = textView.getText().toString();
        int n = string2.indexOf(47);
        int n2 = string2.lastIndexOf(47);
        int n3 = Integer.parseInt(string2.substring(0, n));
        n = Integer.parseInt(string2.substring(n + 1, n2));
        n2 = Integer.parseInt(string2.substring(n2 + 1));
        new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener(){

            public void onDateSet(DatePicker object, int n, int n2, int n3) {
                object = new StringBuilder();
                ((StringBuilder)object).append(String.format("%04d", n));
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(String.format("%02d", n2 + 1));
                ((StringBuilder)object).append("/");
                ((StringBuilder)object).append(String.format("%02d", n3));
                object = ((StringBuilder)object).toString();
                textView.setText((CharSequence)object);
            }
        }, n3, n - 1, n2).show();
    }

    public static void pickTime(Context context, final TextView textView) {
        int n = Integer.parseInt(textView.getText().toString().substring(0, 2));
        int n2 = Integer.parseInt(textView.getText().toString().substring(3));
        new TimePickerDialog(context, new TimePickerDialog.OnTimeSetListener(){

            public void onTimeSet(TimePicker object, int n, int n2) {
                object = new StringBuilder();
                ((StringBuilder)object).append(String.format("%02d", n));
                ((StringBuilder)object).append(":");
                ((StringBuilder)object).append(String.format("%02d", n2));
                textView.setText((CharSequence)object);
            }
        }, n, n2, true).show();
    }

    public static void playNotificationRingtone(Context context) {
        RingtoneManager.getRingtone((Context)context, (Uri)RingtoneManager.getDefaultUri((int)2)).play();
    }

    public static PopupWindow popupBitmap(Context context, Bitmap bitmap, int n, int n2, View view) {
        float f = (float)n / (float)bitmap.getWidth();
        float f2 = (float)n2 / (float)bitmap.getHeight();
        if (f2 < f) {
            return UiOp.popupBitmapText(context, bitmap, null, 0, 0, 0, f2, 0, view, 17, 0, 0, 0, 0, 0, 10, -5592406);
        }
        f2 = f;
        return UiOp.popupBitmapText(context, bitmap, null, 0, 0, 0, f2, 0, view, 17, 0, 0, 0, 0, 0, 10, -5592406);
    }

    /*
     * Unable to fully structure code
     */
    public static PopupWindow popupBitmapText(Context var0, Bitmap var1_1, String var2_2, int var3_3, int var4_4, int var5_5, float var6_6, int var7_7, View var8_8, int var9_9, int var10_10, int var11_11, int var12_12, int var13_13, int var14_14, int var15_15, int var16_16) {
        block14 : {
            block15 : {
                block13 : {
                    block12 : {
                        var17_17 = ((LayoutInflater)var0.getSystemService("layout_inflater")).inflate(R.layout.display_image_popup, null);
                        var18_18 = new PopupWindow(var17_17, -2, -2);
                        var19_19 = (LinearLayout)var17_17.findViewById(R.id.frame);
                        var20_20 = (ImageView)var17_17.findViewById(R.id.image);
                        var0 = (TextView)var17_17.findViewById(R.id.text_top);
                        var21_21 = (TextView)var17_17.findViewById(R.id.text_bottom);
                        var22_22 = (TextView)var17_17.findViewById(R.id.text_left);
                        var23_23 = (TextView)var17_17.findViewById(R.id.text_right);
                        var24_24 = (RelativeLayout)var17_17.findViewById(R.id.border);
                        var17_17 = (RelativeLayout)var17_17.findViewById(R.id.shadow);
                        var25_25 = var1_1.getWidth();
                        var26_26 = var1_1.getHeight();
                        var27_27 = (int)((float)var25_25 * var6_6);
                        var28_28 = (int)((float)var26_26 * var6_6);
                        var29_29 = var19_19.getLayoutParams();
                        var29_29.width = var27_27;
                        var19_19.setLayoutParams(var29_29);
                        var19_19.setBackgroundColor(var7_7);
                        var19_19 = var20_20.getLayoutParams();
                        var19_19.height = var28_28;
                        var20_20.setImageBitmap(var1_1);
                        var20_20.setLayoutParams((ViewGroup.LayoutParams)var19_19);
                        var18_18.setBackgroundDrawable((Drawable)new BitmapDrawable());
                        var18_18.setOutsideTouchable(true);
                        if (StringOp.strlen(var2_2) > 0) {
                            if ((var3_3 & 48) != 48) {
                                var0 = null;
                            }
                            if ((var3_3 & 80) == 80) {
                                var0 = var21_21;
                            }
                            if ((var3_3 & 3) == 3) {
                                var0 = var22_22;
                            }
                            if ((var3_3 & 5) == 5) {
                                var0 = var23_23;
                            }
                            var0.setVisibility(0);
                            var0.setText((CharSequence)var2_2);
                            var0.setTextSize((float)var4_4);
                            var0.setTextColor(var5_5);
                        }
                        var4_4 = (var9_9 & 3) == 3 ? 1 : 0;
                        var3_3 = (var9_9 & 48) == 48 ? 1 : 0;
                        var5_5 = (var9_9 & 5) == 5 ? 1 : 0;
                        var30_30 = (var9_9 & 80) == 80;
                        var7_7 = (var9_9 & 1) == 1 ? 1 : 0;
                        var31_31 = (var9_9 & 16) == 16;
                        if (var3_3 == 0) break block12;
                        var3_3 = var26_26;
                        ** GOTO lbl53
                    }
                    if (var30_30) {
                        var3_3 = var11_11 - var28_28;
                    } else if (var31_31) {
                        var3_3 = var26_26 + (var28_28 - var26_26) / 2;
lbl53: // 2 sources:
                        var3_3 = var11_11 - var3_3;
                    } else {
                        var3_3 = 0;
                    }
                    if (var4_4 == 0) break block13;
                    var4_4 = var10_10;
                    break block14;
                }
                if (var5_5 == 0) break block15;
                var4_4 = var27_27 - var25_25;
                ** GOTO lbl66
            }
            if (var7_7 != 0) {
                var4_4 = (var27_27 - var25_25) / 2;
lbl66: // 2 sources:
                var4_4 = var10_10 - var4_4;
            } else {
                var4_4 = 0;
            }
        }
        var24_24.setBackgroundColor(var16_16);
        var24_24.setPadding(var15_15, var15_15, var15_15, var15_15);
        var5_5 = (var12_12 & 4) != 0 ? var13_13 : 0;
        var7_7 = (var12_12 & 8) != 0 ? var13_13 : 0;
        var15_15 = (var12_12 & 2) != 0 ? var13_13 : 0;
        var12_12 = (var12_12 & 1) != 0 ? var13_13 : 0;
        var17_17.setPadding(var5_5, var7_7, var15_15, var12_12);
        var17_17.setBackgroundColor(var14_14);
        if (var9_9 == 17) {
            var18_18.showAtLocation(var8_8, 17, var10_10, var11_11);
            return var18_18;
        }
        var18_18.showAsDropDown(var8_8, var4_4, var3_3);
        return var18_18;
    }

    public static PopupWindow popupImagePath(Context context, String string2, int n, int n2, View view) {
        if ((string2 = MediaOp.getSampledBitmapFromPath(string2, n, n2)) != null) return UiOp.popupBitmap(context, (Bitmap)string2, n, n2, view);
        return null;
    }

    public static PopupWindow popupImagePathText(Context context, String string2, String string3, int n, int n2, int n3, int n4, int n5, View view) {
        if ((string2 = MediaOp.getSampledBitmapFromPath(string2, n4, n5)) == null) {
            return null;
        }
        int n6 = StringOp.strlen(string3);
        int n7 = 1;
        int n8 = 1;
        if (n6 > 0) {
            n7 = (n & 48) == 48 ? 2 : 1;
            if ((n & 80) == 80) {
                n7 = 2;
            }
            if ((n & 3) == 3) {
                n8 = 2;
            }
            n6 = n7;
            if ((n & 5) == 5) {
                n8 = 2;
                n6 = n7;
            }
        } else {
            n6 = 1;
            n8 = n7;
        }
        float f = (float)n4 / (float)string2.getWidth() / (float)n8;
        float f2 = (float)n5 / (float)string2.getHeight() / (float)n6;
        if (f2 < f) {
            return UiOp.popupBitmapText(context, (Bitmap)string2, string3, n, n2, n3, f2, 0, view, 17, 0, 0, 0, 0, 0, 10, -5592406);
        }
        f2 = f;
        return UiOp.popupBitmapText(context, (Bitmap)string2, string3, n, n2, n3, f2, 0, view, 17, 0, 0, 0, 0, 0, 10, -5592406);
    }

    public static PopupWindow popupImageView(Context context, ImageView imageView, View view, float f, int n, int n2, int n3, int n4, int n5) {
        BitmapDrawable bitmapDrawable = (BitmapDrawable)imageView.getBackground();
        if (bitmapDrawable != null) {
            imageView = bitmapDrawable.getBitmap();
            return UiOp.popupBitmapText(context, (Bitmap)imageView, null, 0, 0, 0, f, 0, view, n, n2, n3, 1, n4, n5, 0, 0);
        }
        imageView = ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        return UiOp.popupBitmapText(context, (Bitmap)imageView, null, 0, 0, 0, f, 0, view, n, n2, n3, 1, n4, n5, 0, 0);
    }

    public static void restart_app(Context context, int n) {
        int n2 = n;
        if (n == 0) {
            n2 = 1;
        }
        Log.e((String)"", (String)"restarting app");
        PendingIntent pendingIntent = PendingIntent.getActivity((Context)context, (int)0, (Intent)context.getPackageManager().getLaunchIntentForPackage(context.getPackageName()), (int)67108864);
        ((AlarmManager)context.getSystemService("alarm")).set(1, System.currentTimeMillis() + (long)n2, pendingIntent);
        System.exit(2);
    }

    public static void setScreenBrightness(Context context, float f) {
        Window window = ((Activity)context).getWindow();
        context = window.getAttributes();
        context.screenBrightness = f;
        window.setAttributes((WindowManager.LayoutParams)context);
    }

    public static void setViewSize(View view, int n, int n2) {
        view.getLayoutParams().width = n;
        view.getLayoutParams().height = n2;
        view.requestLayout();
    }

    public static void show_hide_TextView(TextView textView, String string2) {
        if (string2 != null && !string2.equals("null") && string2.length() != 0) {
            textView.setVisibility(0);
            textView.setText((CharSequence)string2);
            return;
        }
        textView.setVisibility(8);
    }

    public static void toast_message(Context context, int n) {
        UiOp.toast_message(context, context.getResources().getString(n), false);
    }

    public static void toast_message(Context context, String string2, boolean bl) {
        Toast.makeText((Context)context, (CharSequence)string2, (int)bl).show();
    }

    public static void toast_message_image(Context context, String string2, Drawable drawable2, Drawable drawable3, Drawable drawable4, Drawable drawable5) {
        View view = ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(R.layout.toast_image, null);
        LinearLayout linearLayout = (LinearLayout)view.findViewById(R.id.toast_layout);
        ImageView imageView = (ImageView)view.findViewById(R.id.image_left);
        ImageView imageView2 = (ImageView)view.findViewById(R.id.image_right);
        linearLayout = (ImageView)view.findViewById(R.id.image_top);
        ImageView imageView3 = (ImageView)view.findViewById(R.id.image_bottom);
        TextView textView = (TextView)view.findViewById(R.id.text);
        if (drawable2 == null) {
            imageView.setVisibility(8);
        } else {
            imageView.setImageDrawable(drawable2);
        }
        if (drawable3 == null) {
            imageView2.setVisibility(8);
        } else {
            imageView2.setImageDrawable(drawable3);
        }
        if (drawable4 == null) {
            linearLayout.setVisibility(8);
        } else {
            linearLayout.setImageDrawable(drawable4);
        }
        if (drawable5 == null) {
            imageView3.setVisibility(8);
        } else {
            imageView3.setImageDrawable(drawable5);
        }
        textView.setText((CharSequence)string2);
        context = new Toast(context);
        context.setGravity(16, 0, 0);
        context.setDuration(1);
        context.setView(view);
        context.show();
    }

    public static void toast_message_long(Context context, int n) {
        UiOp.toast_message(context, context.getResources().getString(n), true);
    }

    public static void touchView(View view, MotionEvent motionEvent) {
        view.dispatchTouchEvent(motionEvent);
    }

    public static void viewAnimation(final View view, TRANSLATION_AXIS object, float f, int n, final INTERPOLATION_TYPE iNTERPOLATION_TYPE, final boolean bl) {
        if (bl) {
            view.setVisibility(0);
        }
        Object var6_6 = null;
        int n2 = 6.$SwitchMap$com$syntak$library$UiOp$TRANSLATION_AXIS[object.ordinal()];
        object = n2 != 1 ? (n2 != 2 ? var6_6 : ObjectAnimator.ofFloat((Object)view, (String)"translationY", (float[])new float[]{f})) : ObjectAnimator.ofFloat((Object)view, (String)"translationX", (float[])new float[]{f});
        if (object == null) {
            return;
        }
        object.setDuration((long)n);
        object.setInterpolator(new TimeInterpolator(){

            public float getInterpolation(float f) {
                int n = 6.$SwitchMap$com$syntak$library$UiOp$INTERPOLATION_TYPE[iNTERPOLATION_TYPE.ordinal()];
                float f2 = f;
                if (n == 1) return f2;
                if (n == 2) return f * f;
                if (n == 3) return 2.0f * f - f * f;
                if (!((double)f < 0.5)) return 2.0f * f - f * f;
                return f * f;
            }
        });
        object.start();
        object.addListener((Animator.AnimatorListener)new AnimatorListenerAdapter(){

            public void onAnimationEnd(Animator animator2) {
                super.onAnimationEnd(animator2);
                animator2.removeAllListeners();
                if (bl) {
                    view.setVisibility(0);
                    return;
                }
                view.setVisibility(8);
            }
        });
    }

    public static void wakeUp(Context context, String string2) {
        Intent intent = (context = (Activity)context).getIntent();
        if (!intent.hasExtra(string2)) return;
        if (!intent.getExtras().getBoolean(string2)) return;
        context.getWindow().setFlags(6815744, 6815744);
    }

    public static class DisplayUrlCachedImage {
        Bitmap bitmap = null;
        boolean flag_local_existed = false;
        boolean flag_to_download = false;
        ImageView iv;
        String local;
        String url;

        public DisplayUrlCachedImage(final ImageView imageView, String string2, String string3) {
            this.iv = imageView;
            this.url = string2;
            this.local = string3;
            if (FileOp.checkFileExist(string3)) {
                string2 = MediaOp.getBitmapFromPath(string3);
                this.bitmap = string2;
                if (string2 == null) {
                    this.flag_to_download = true;
                } else {
                    this.flag_local_existed = true;
                    imageView.setImageBitmap((Bitmap)string2);
                }
            } else {
                this.flag_to_download = true;
            }
            if (!this.flag_to_download) return;
            new background_thread(){

                @Override
                public void OnDownloaded(Bitmap bitmap) {
                    super.OnDownloaded(bitmap);
                    DisplayUrlCachedImage.this.OnBitmapDownloaded(imageView, bitmap);
                }
            }.start();
        }

        public void OnBitmapDownloaded(ImageView imageView, Bitmap bitmap) {
        }

        class background_thread
        extends Thread {
            background_thread() {
            }

            public void OnDownloaded(Bitmap bitmap) {
            }

            @Override
            public void run() {
                long l = HttpOp.HttpGetLastModified(DisplayUrlCachedImage.this.url);
                if (DisplayUrlCachedImage.this.flag_local_existed && new File(DisplayUrlCachedImage.this.local).lastModified() != l) {
                    DisplayUrlCachedImage.this.flag_to_download = true;
                }
                if (!DisplayUrlCachedImage.this.flag_to_download) return;
                if (!"HTTP OK".equals(HttpOp.HttpDownloadFile(DisplayUrlCachedImage.this.url, DisplayUrlCachedImage.this.local))) return;
                FileOp.setFileLastModified(DisplayUrlCachedImage.this.local, l);
                DisplayUrlCachedImage displayUrlCachedImage = DisplayUrlCachedImage.this;
                displayUrlCachedImage.bitmap = MediaOp.getBitmapFromPath(displayUrlCachedImage.local);
                this.OnDownloaded(DisplayUrlCachedImage.this.bitmap);
            }
        }

    }

    public static class DisplayUrlIcon
    extends AsyncTask<String, Void, Bitmap> {
        private ImageView imageView;

        public DisplayUrlIcon(ImageView imageView) {
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String ... bitmap) {
            bitmap = bitmap[0];
            try {
                URL uRL = new URL((String)bitmap);
                return BitmapFactory.decodeStream((InputStream)uRL.openStream());
            }
            catch (Exception exception) {
                Log.e((String)"Error", (String)exception.getMessage());
                exception.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) return;
            this.imageView.setVisibility(0);
            this.imageView.setImageBitmap(bitmap);
        }
    }

    public static class DisplayUrlImage
    extends AsyncTask<String, Void, Bitmap> {
        private Context context;
        private ImageView imageView;

        public DisplayUrlImage(Context context, ImageView imageView) {
            this.context = context;
            this.imageView = imageView;
        }

        protected Bitmap doInBackground(String ... bitmap) {
            bitmap = bitmap[0];
            try {
                URL uRL = new URL((String)bitmap);
                return BitmapFactory.decodeStream((InputStream)uRL.openStream());
            }
            catch (Exception exception) {
                Log.e((String)"Error", (String)exception.getMessage());
                exception.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(Bitmap bitmap) {
            if (bitmap == null) return;
            this.imageView.setVisibility(0);
            MediaOp.fitImageToView(this.context, this.imageView, bitmap);
        }
    }

    public static final class INTERPOLATION_TYPE
    extends Enum<INTERPOLATION_TYPE> {
        private static final /* synthetic */ INTERPOLATION_TYPE[] $VALUES;
        public static final /* enum */ INTERPOLATION_TYPE ACCELARATION;
        public static final /* enum */ INTERPOLATION_TYPE ACCEL_DECEL;
        public static final /* enum */ INTERPOLATION_TYPE DECELATION;
        public static final /* enum */ INTERPOLATION_TYPE DEFAULT;
        public static final /* enum */ INTERPOLATION_TYPE LINEAR;

        static {
            INTERPOLATION_TYPE iNTERPOLATION_TYPE;
            DEFAULT = new INTERPOLATION_TYPE();
            LINEAR = new INTERPOLATION_TYPE();
            ACCELARATION = new INTERPOLATION_TYPE();
            DECELATION = new INTERPOLATION_TYPE();
            ACCEL_DECEL = iNTERPOLATION_TYPE = new INTERPOLATION_TYPE();
            $VALUES = new INTERPOLATION_TYPE[]{DEFAULT, LINEAR, ACCELARATION, DECELATION, iNTERPOLATION_TYPE};
        }

        public static INTERPOLATION_TYPE valueOf(String string2) {
            return Enum.valueOf(INTERPOLATION_TYPE.class, string2);
        }

        public static INTERPOLATION_TYPE[] values() {
            return (INTERPOLATION_TYPE[])$VALUES.clone();
        }
    }

    public static final class TRANSLATION_AXIS
    extends Enum<TRANSLATION_AXIS> {
        private static final /* synthetic */ TRANSLATION_AXIS[] $VALUES;
        public static final /* enum */ TRANSLATION_AXIS X;
        public static final /* enum */ TRANSLATION_AXIS Y;

        static {
            TRANSLATION_AXIS tRANSLATION_AXIS;
            X = new TRANSLATION_AXIS();
            Y = tRANSLATION_AXIS = new TRANSLATION_AXIS();
            $VALUES = new TRANSLATION_AXIS[]{X, tRANSLATION_AXIS};
        }

        public static TRANSLATION_AXIS valueOf(String string2) {
            return Enum.valueOf(TRANSLATION_AXIS.class, string2);
        }

        public static TRANSLATION_AXIS[] values() {
            return (TRANSLATION_AXIS[])$VALUES.clone();
        }
    }

}


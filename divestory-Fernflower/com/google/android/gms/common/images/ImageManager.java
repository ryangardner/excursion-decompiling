package com.google.android.gms.common.images;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.os.ResultReceiver;
import android.os.SystemClock;
import android.util.Log;
import android.widget.ImageView;
import androidx.collection.LruCache;
import com.google.android.gms.common.internal.Asserts;
import com.google.android.gms.internal.base.zaj;
import com.google.android.gms.internal.base.zal;
import com.google.android.gms.internal.base.zap;
import com.google.android.gms.internal.base.zaq;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;

public final class ImageManager {
   private static final Object zaa = new Object();
   private static HashSet<Uri> zab = new HashSet();
   private static ImageManager zac;
   private final Context zad;
   private final Handler zae;
   private final ExecutorService zaf;
   private final ImageManager.zaa zag;
   private final zaj zah;
   private final Map<com.google.android.gms.common.images.zab, ImageManager.ImageReceiver> zai;
   private final Map<Uri, ImageManager.ImageReceiver> zaj;
   private final Map<Uri, Long> zak;

   private ImageManager(Context var1, boolean var2) {
      this.zad = var1.getApplicationContext();
      this.zae = new zap(Looper.getMainLooper());
      this.zaf = zal.zaa().zaa(4, zaq.zab);
      this.zag = null;
      this.zah = new zaj();
      this.zai = new HashMap();
      this.zaj = new HashMap();
      this.zak = new HashMap();
   }

   public static ImageManager create(Context var0) {
      if (zac == null) {
         zac = new ImageManager(var0, false);
      }

      return zac;
   }

   private final Bitmap zaa(com.google.android.gms.common.images.zaa var1) {
      return null;
   }

   private final void zaa(com.google.android.gms.common.images.zab var1) {
      Asserts.checkMainThread("ImageManager.loadImage() must be called in the main thread");
      (new ImageManager.zab(var1)).run();
   }

   public final void loadImage(ImageView var1, int var2) {
      this.zaa((com.google.android.gms.common.images.zab)(new com.google.android.gms.common.images.zad(var1, var2)));
   }

   public final void loadImage(ImageView var1, Uri var2) {
      this.zaa((com.google.android.gms.common.images.zab)(new com.google.android.gms.common.images.zad(var1, var2)));
   }

   public final void loadImage(ImageView var1, Uri var2, int var3) {
      com.google.android.gms.common.images.zad var4 = new com.google.android.gms.common.images.zad(var1, var2);
      var4.zab = var3;
      this.zaa((com.google.android.gms.common.images.zab)var4);
   }

   public final void loadImage(ImageManager.OnImageLoadedListener var1, Uri var2) {
      this.zaa((com.google.android.gms.common.images.zab)(new com.google.android.gms.common.images.zac(var1, var2)));
   }

   public final void loadImage(ImageManager.OnImageLoadedListener var1, Uri var2, int var3) {
      com.google.android.gms.common.images.zac var4 = new com.google.android.gms.common.images.zac(var1, var2);
      var4.zab = var3;
      this.zaa((com.google.android.gms.common.images.zab)var4);
   }

   private final class ImageReceiver extends ResultReceiver {
      private final Uri zaa;
      private final ArrayList<com.google.android.gms.common.images.zab> zab;

      ImageReceiver(Uri var2) {
         super(new zap(Looper.getMainLooper()));
         this.zaa = var2;
         this.zab = new ArrayList();
      }

      // $FF: synthetic method
      static ArrayList zaa(ImageManager.ImageReceiver var0) {
         return var0.zab;
      }

      public final void onReceiveResult(int var1, Bundle var2) {
         ParcelFileDescriptor var3 = (ParcelFileDescriptor)var2.getParcelable("com.google.android.gms.extra.fileDescriptor");
         ImageManager.this.zaf.execute(ImageManager.this.new zac(this.zaa, var3));
      }

      public final void zaa() {
         Intent var1 = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
         var1.setPackage("com.google.android.gms");
         var1.putExtra("com.google.android.gms.extras.uri", this.zaa);
         var1.putExtra("com.google.android.gms.extras.resultReceiver", this);
         var1.putExtra("com.google.android.gms.extras.priority", 3);
         ImageManager.this.zad.sendBroadcast(var1);
      }

      public final void zaa(com.google.android.gms.common.images.zab var1) {
         Asserts.checkMainThread("ImageReceiver.addImageRequest() must be called in the main thread");
         this.zab.add(var1);
      }

      public final void zab(com.google.android.gms.common.images.zab var1) {
         Asserts.checkMainThread("ImageReceiver.removeImageRequest() must be called in the main thread");
         this.zab.remove(var1);
      }
   }

   public interface OnImageLoadedListener {
      void onImageLoaded(Uri var1, Drawable var2, boolean var3);
   }

   private static final class zaa extends LruCache<com.google.android.gms.common.images.zaa, Bitmap> {
      // $FF: synthetic method
      protected final void entryRemoved(boolean var1, Object var2, Object var3, Object var4) {
         throw new NoSuchMethodError();
      }

      // $FF: synthetic method
      protected final int sizeOf(Object var1, Object var2) {
         throw new NoSuchMethodError();
      }
   }

   private final class zab implements Runnable {
      private final com.google.android.gms.common.images.zab zaa;

      public zab(com.google.android.gms.common.images.zab var2) {
         this.zaa = var2;
      }

      public final void run() {
         Asserts.checkMainThread("LoadImageRunnable must be executed on the main thread");
         ImageManager.ImageReceiver var1 = (ImageManager.ImageReceiver)ImageManager.this.zai.get(this.zaa);
         if (var1 != null) {
            ImageManager.this.zai.remove(this.zaa);
            var1.zab(this.zaa);
         }

         com.google.android.gms.common.images.zaa var2 = this.zaa.zaa;
         if (var2.zaa == null) {
            this.zaa.zaa(ImageManager.this.zad, ImageManager.this.zah, true);
         } else {
            Bitmap var16 = ImageManager.this.zaa(var2);
            if (var16 != null) {
               this.zaa.zaa(ImageManager.this.zad, var16, true);
            } else {
               Long var17 = (Long)ImageManager.this.zak.get(var2.zaa);
               if (var17 != null) {
                  if (SystemClock.elapsedRealtime() - var17 < 3600000L) {
                     this.zaa.zaa(ImageManager.this.zad, ImageManager.this.zah, true);
                     return;
                  }

                  ImageManager.this.zak.remove(var2.zaa);
               }

               this.zaa.zaa(ImageManager.this.zad, ImageManager.this.zah);
               ImageManager.ImageReceiver var3 = (ImageManager.ImageReceiver)ImageManager.this.zaj.get(var2.zaa);
               var1 = var3;
               if (var3 == null) {
                  var1 = ImageManager.this.new ImageReceiver(var2.zaa);
                  ImageManager.this.zaj.put(var2.zaa, var1);
               }

               var1.zaa(this.zaa);
               if (!(this.zaa instanceof com.google.android.gms.common.images.zac)) {
                  ImageManager.this.zai.put(this.zaa, var1);
               }

               Object var19 = ImageManager.zaa;
               synchronized(var19){}

               Throwable var10000;
               boolean var10001;
               label225: {
                  try {
                     if (!ImageManager.zab.contains(var2.zaa)) {
                        ImageManager.zab.add(var2.zaa);
                        var1.zaa();
                     }
                  } catch (Throwable var15) {
                     var10000 = var15;
                     var10001 = false;
                     break label225;
                  }

                  label222:
                  try {
                     return;
                  } catch (Throwable var14) {
                     var10000 = var14;
                     var10001 = false;
                     break label222;
                  }
               }

               while(true) {
                  Throwable var18 = var10000;

                  try {
                     throw var18;
                  } catch (Throwable var13) {
                     var10000 = var13;
                     var10001 = false;
                     continue;
                  }
               }
            }
         }
      }
   }

   private final class zac implements Runnable {
      private final Uri zaa;
      private final ParcelFileDescriptor zab;

      public zac(Uri var2, ParcelFileDescriptor var3) {
         this.zaa = var2;
         this.zab = var3;
      }

      public final void run() {
         Asserts.checkNotMainThread("LoadBitmapFromDiskRunnable can't be executed in the main thread");
         ParcelFileDescriptor var1 = this.zab;
         boolean var2 = false;
         Bitmap var3 = null;
         if (var1 != null) {
            label26: {
               Bitmap var9;
               try {
                  var9 = BitmapFactory.decodeFileDescriptor(var1.getFileDescriptor());
               } catch (OutOfMemoryError var8) {
                  String var4 = String.valueOf(this.zaa);
                  StringBuilder var5 = new StringBuilder(String.valueOf(var4).length() + 34);
                  var5.append("OOM while loading bitmap for uri: ");
                  var5.append(var4);
                  Log.e("ImageManager", var5.toString(), var8);
                  var2 = true;
                  break label26;
               }

               var3 = var9;
            }

            try {
               this.zab.close();
            } catch (IOException var7) {
               Log.e("ImageManager", "closed failed", var7);
            }
         } else {
            var3 = null;
            var2 = false;
         }

         CountDownLatch var10 = new CountDownLatch(1);
         ImageManager.this.zae.post(ImageManager.this.new zad(this.zaa, var3, var2, var10));

         try {
            var10.await();
         } catch (InterruptedException var6) {
            String var12 = String.valueOf(this.zaa);
            StringBuilder var11 = new StringBuilder(String.valueOf(var12).length() + 32);
            var11.append("Latch interrupted while posting ");
            var11.append(var12);
            Log.w("ImageManager", var11.toString());
         }
      }
   }

   private final class zad implements Runnable {
      private final Uri zaa;
      private final Bitmap zab;
      private final CountDownLatch zac;
      private boolean zad;

      public zad(Uri var2, Bitmap var3, boolean var4, CountDownLatch var5) {
         this.zaa = var2;
         this.zab = var3;
         this.zad = var4;
         this.zac = var5;
      }

      public final void run() {
         // $FF: Couldn't be decompiled
      }
   }
}

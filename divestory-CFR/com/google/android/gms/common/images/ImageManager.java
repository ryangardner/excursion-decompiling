/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.ParcelFileDescriptor
 *  android.os.Parcelable
 *  android.os.ResultReceiver
 *  android.os.SystemClock
 *  android.util.Log
 *  android.widget.ImageView
 */
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
import android.os.Parcelable;
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
import java.io.FileDescriptor;
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
    private final zaa zag;
    private final zaj zah;
    private final Map<com.google.android.gms.common.images.zab, ImageReceiver> zai;
    private final Map<Uri, ImageReceiver> zaj;
    private final Map<Uri, Long> zak;

    private ImageManager(Context context, boolean bl) {
        this.zad = context.getApplicationContext();
        this.zae = new zap(Looper.getMainLooper());
        this.zaf = zal.zaa().zaa(4, zaq.zab);
        this.zag = null;
        this.zah = new zaj();
        this.zai = new HashMap<com.google.android.gms.common.images.zab, ImageReceiver>();
        this.zaj = new HashMap<Uri, ImageReceiver>();
        this.zak = new HashMap<Uri, Long>();
    }

    public static ImageManager create(Context context) {
        if (zac != null) return zac;
        zac = new ImageManager(context, false);
        return zac;
    }

    private final Bitmap zaa(com.google.android.gms.common.images.zaa zaa2) {
        return null;
    }

    private final void zaa(com.google.android.gms.common.images.zab zab2) {
        Asserts.checkMainThread("ImageManager.loadImage() must be called in the main thread");
        new zab(zab2).run();
    }

    public final void loadImage(ImageView imageView, int n) {
        this.zaa(new com.google.android.gms.common.images.zad(imageView, n));
    }

    public final void loadImage(ImageView imageView, Uri uri) {
        this.zaa(new com.google.android.gms.common.images.zad(imageView, uri));
    }

    public final void loadImage(ImageView object, Uri uri, int n) {
        object = new com.google.android.gms.common.images.zad((ImageView)object, uri);
        object.zab = n;
        this.zaa((com.google.android.gms.common.images.zab)object);
    }

    public final void loadImage(OnImageLoadedListener onImageLoadedListener, Uri uri) {
        this.zaa(new com.google.android.gms.common.images.zac(onImageLoadedListener, uri));
    }

    public final void loadImage(OnImageLoadedListener object, Uri uri, int n) {
        object = new com.google.android.gms.common.images.zac((OnImageLoadedListener)object, uri);
        ((com.google.android.gms.common.images.zab)object).zab = n;
        this.zaa((com.google.android.gms.common.images.zab)object);
    }

    private final class ImageReceiver
    extends ResultReceiver {
        private final Uri zaa;
        private final ArrayList<com.google.android.gms.common.images.zab> zab;

        ImageReceiver(Uri uri) {
            super((Handler)new zap(Looper.getMainLooper()));
            this.zaa = uri;
            this.zab = new ArrayList();
        }

        public final void onReceiveResult(int n, Bundle bundle) {
            bundle = (ParcelFileDescriptor)bundle.getParcelable("com.google.android.gms.extra.fileDescriptor");
            ImageManager.this.zaf.execute(new zac(this.zaa, (ParcelFileDescriptor)bundle));
        }

        public final void zaa() {
            Intent intent = new Intent("com.google.android.gms.common.images.LOAD_IMAGE");
            intent.setPackage("com.google.android.gms");
            intent.putExtra("com.google.android.gms.extras.uri", (Parcelable)this.zaa);
            intent.putExtra("com.google.android.gms.extras.resultReceiver", (Parcelable)this);
            intent.putExtra("com.google.android.gms.extras.priority", 3);
            ImageManager.this.zad.sendBroadcast(intent);
        }

        public final void zaa(com.google.android.gms.common.images.zab zab2) {
            Asserts.checkMainThread("ImageReceiver.addImageRequest() must be called in the main thread");
            this.zab.add(zab2);
        }

        public final void zab(com.google.android.gms.common.images.zab zab2) {
            Asserts.checkMainThread("ImageReceiver.removeImageRequest() must be called in the main thread");
            this.zab.remove(zab2);
        }
    }

    public static interface OnImageLoadedListener {
        public void onImageLoaded(Uri var1, Drawable var2, boolean var3);
    }

    private static final class zaa
    extends LruCache<com.google.android.gms.common.images.zaa, Bitmap> {
        @Override
        protected final /* synthetic */ void entryRemoved(boolean bl, Object object, Object object2, Object object3) {
            throw new NoSuchMethodError();
        }

        @Override
        protected final /* synthetic */ int sizeOf(Object object, Object object2) {
            throw new NoSuchMethodError();
        }
    }

    private final class zab
    implements Runnable {
        private final com.google.android.gms.common.images.zab zaa;

        public zab(com.google.android.gms.common.images.zab zab2) {
            this.zaa = zab2;
        }

        @Override
        public final void run() {
            Asserts.checkMainThread("LoadImageRunnable must be executed on the main thread");
            Object object = (ImageReceiver)((Object)ImageManager.this.zai.get(this.zaa));
            if (object != null) {
                ImageManager.this.zai.remove(this.zaa);
                ((ImageReceiver)((Object)object)).zab(this.zaa);
            }
            com.google.android.gms.common.images.zaa zaa2 = this.zaa.zaa;
            if (zaa2.zaa == null) {
                this.zaa.zaa(ImageManager.this.zad, ImageManager.this.zah, true);
                return;
            }
            object = ImageManager.this.zaa(zaa2);
            if (object != null) {
                this.zaa.zaa(ImageManager.this.zad, (Bitmap)object, true);
                return;
            }
            object = (Long)ImageManager.this.zak.get((Object)zaa2.zaa);
            if (object != null) {
                if (SystemClock.elapsedRealtime() - (Long)object < 3600000L) {
                    this.zaa.zaa(ImageManager.this.zad, ImageManager.this.zah, true);
                    return;
                }
                ImageManager.this.zak.remove((Object)zaa2.zaa);
            }
            this.zaa.zaa(ImageManager.this.zad, ImageManager.this.zah);
            Object object2 = (ImageReceiver)((Object)ImageManager.this.zaj.get((Object)zaa2.zaa));
            object = object2;
            if (object2 == null) {
                object = new ImageReceiver(zaa2.zaa);
                ImageManager.this.zaj.put(zaa2.zaa, object);
            }
            ((ImageReceiver)((Object)object)).zaa(this.zaa);
            if (!(this.zaa instanceof com.google.android.gms.common.images.zac)) {
                ImageManager.this.zai.put(this.zaa, object);
            }
            object2 = zaa;
            synchronized (object2) {
                if (zab.contains((Object)zaa2.zaa)) return;
                zab.add(zaa2.zaa);
                ((ImageReceiver)((Object)object)).zaa();
                return;
            }
        }
    }

    private final class zac
    implements Runnable {
        private final Uri zaa;
        private final ParcelFileDescriptor zab;

        public zac(Uri uri, ParcelFileDescriptor parcelFileDescriptor) {
            this.zaa = uri;
            this.zab = parcelFileDescriptor;
        }

        @Override
        public final void run() {
            Asserts.checkNotMainThread("LoadBitmapFromDiskRunnable can't be executed in the main thread");
            Object object = this.zab;
            boolean bl = false;
            ParcelFileDescriptor parcelFileDescriptor = null;
            if (object != null) {
                try {
                    object = BitmapFactory.decodeFileDescriptor((FileDescriptor)object.getFileDescriptor());
                    parcelFileDescriptor = object;
                }
                catch (OutOfMemoryError outOfMemoryError) {
                    String string2 = String.valueOf((Object)this.zaa);
                    StringBuilder stringBuilder = new StringBuilder(String.valueOf(string2).length() + 34);
                    stringBuilder.append("OOM while loading bitmap for uri: ");
                    stringBuilder.append(string2);
                    Log.e((String)"ImageManager", (String)stringBuilder.toString(), (Throwable)outOfMemoryError);
                    bl = true;
                }
                try {
                    this.zab.close();
                }
                catch (IOException iOException) {
                    Log.e((String)"ImageManager", (String)"closed failed", (Throwable)iOException);
                }
            } else {
                parcelFileDescriptor = null;
                bl = false;
            }
            object = new CountDownLatch(1);
            ImageManager.this.zae.post((Runnable)new zad(this.zaa, (Bitmap)parcelFileDescriptor, bl, (CountDownLatch)object));
            try {
                ((CountDownLatch)object).await();
                return;
            }
            catch (InterruptedException interruptedException) {
                String string3 = String.valueOf((Object)this.zaa);
                object = new StringBuilder(String.valueOf(string3).length() + 32);
                ((StringBuilder)object).append("Latch interrupted while posting ");
                ((StringBuilder)object).append(string3);
                Log.w((String)"ImageManager", (String)((StringBuilder)object).toString());
                return;
            }
        }
    }

    private final class zad
    implements Runnable {
        private final Uri zaa;
        private final Bitmap zab;
        private final CountDownLatch zac;
        private boolean zad;

        public zad(Uri uri, Bitmap bitmap, boolean bl, CountDownLatch countDownLatch) {
            this.zaa = uri;
            this.zab = bitmap;
            this.zad = bl;
            this.zac = countDownLatch;
        }

        @Override
        public final void run() {
            Asserts.checkMainThread("OnBitmapLoadedRunnable must be executed in the main thread");
            boolean bl = this.zab != null;
            Object object = (ImageReceiver)((Object)ImageManager.this.zaj.remove((Object)this.zaa));
            if (object != null) {
                ArrayList arrayList = ((ImageReceiver)((Object)object)).zab;
                int n = arrayList.size();
                for (int i = 0; i < n; ++i) {
                    object = (com.google.android.gms.common.images.zab)arrayList.get(i);
                    if (this.zab != null && bl) {
                        ((com.google.android.gms.common.images.zab)object).zaa(ImageManager.this.zad, this.zab, false);
                    } else {
                        ImageManager.this.zak.put(this.zaa, SystemClock.elapsedRealtime());
                        ((com.google.android.gms.common.images.zab)object).zaa(ImageManager.this.zad, ImageManager.this.zah, false);
                    }
                    if (object instanceof com.google.android.gms.common.images.zac) continue;
                    ImageManager.this.zai.remove(object);
                }
            }
            this.zac.countDown();
            object = zaa;
            synchronized (object) {
                zab.remove((Object)this.zaa);
                return;
            }
        }
    }

}


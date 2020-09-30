/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.hardware.Camera
 *  android.hardware.Camera$Parameters
 *  android.hardware.Camera$PictureCallback
 *  android.hardware.Camera$ShutterCallback
 *  android.hardware.Camera$Size
 *  android.util.Log
 *  android.view.Surface
 *  android.view.SurfaceHolder
 *  android.view.SurfaceHolder$Callback
 *  android.view.SurfaceView
 *  android.view.View
 *  android.widget.FrameLayout
 */
package com.syntak.library;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.hardware.Camera;
import android.util.Log;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.FrameLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;

public class CameraOp {
    private static Camera camera;
    private static CameraPreview camera_preview;
    private static Camera.PictureCallback jpeg;
    private static Camera.PictureCallback postview;
    private static Camera.PictureCallback raw;
    private static String save_file_name;
    private static Camera.ShutterCallback shutter;
    final String TAG;
    Context context;
    FrameLayout preview;

    public CameraOp(Context object, FrameLayout frameLayout) {
        this.TAG = "CameraOp";
        this.context = object;
        this.preview = frameLayout;
        camera = CameraOp.getCameraInstance();
        object = new CameraPreview((Context)object, camera);
        camera_preview = object;
        frameLayout.addView((View)object);
        shutter = new Camera.ShutterCallback(){

            public void onShutter() {
                CameraOp.this.OnShutter();
            }
        };
        raw = new Camera.PictureCallback(){

            public void onPictureTaken(byte[] arrby, Camera camera) {
                CameraOp.this.OnImageTakenRaw(arrby, camera);
            }
        };
        postview = new Camera.PictureCallback(){

            public void onPictureTaken(byte[] arrby, Camera camera) {
                CameraOp.this.OnImageTakenPostview(arrby, camera);
            }
        };
        jpeg = new Camera.PictureCallback(){

            public void onPictureTaken(byte[] object, Camera object2) {
                File file = new File(save_file_name);
                try {
                    object2 = new FileOutputStream(file);
                    ((FileOutputStream)object2).write((byte[])object);
                    ((FileOutputStream)object2).close();
                    CameraOp.this.OnImageTakenJpeg();
                    return;
                }
                catch (IOException iOException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Error accessing file: ");
                    ((StringBuilder)object).append(iOException.getMessage());
                    Log.d((String)"CameraOp", (String)((StringBuilder)object).toString());
                    return;
                }
                catch (FileNotFoundException fileNotFoundException) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("File not found: ");
                    ((StringBuilder)object).append(fileNotFoundException.getMessage());
                    Log.d((String)"CameraOp", (String)((StringBuilder)object).toString());
                }
            }
        };
    }

    public static boolean checkCameraHardware(Context context) {
        return context.getPackageManager().hasSystemFeature("android.hardware.camera");
    }

    public static Bitmap getBitmapFromRawData(byte[] arrby, Camera camera) {
        camera = Bitmap.createBitmap((int)camera.getParameters().getPictureSize().width, (int)camera.getParameters().getPictureSize().height, (Bitmap.Config)Bitmap.Config.ARGB_8888);
        camera.copyPixelsFromBuffer((Buffer)ByteBuffer.wrap(arrby));
        return camera;
    }

    public static Camera getCameraInstance() {
        try {
            return Camera.open();
        }
        catch (Exception exception) {
            return null;
        }
    }

    public static Camera.Parameters getCameraParameters() {
        return camera.getParameters();
    }

    public static void release() {
        camera_preview = null;
        camera.release();
        camera = null;
    }

    public static void setCameraParameters(Camera.Parameters parameters) {
        camera.setParameters(parameters);
    }

    public static void takePicture(String string2) {
        save_file_name = string2;
        camera.takePicture(shutter, raw, postview, jpeg);
    }

    public void OnImageTakenJpeg() {
    }

    public void OnImageTakenPostview(byte[] arrby, Camera camera) {
    }

    public void OnImageTakenRaw(byte[] arrby, Camera camera) {
    }

    public void OnPreviewChanged() {
    }

    public void OnPreviewCreated() {
    }

    public void OnShutter() {
    }

    public class CameraPreview
    extends SurfaceView
    implements SurfaceHolder.Callback {
        private Camera mCamera;
        private SurfaceHolder mHolder;

        public CameraPreview(Context context, Camera camera) {
            super(context);
            this.mCamera = camera;
            CameraOp.this = this.getHolder();
            this.mHolder = CameraOp.this;
            CameraOp.this.addCallback((SurfaceHolder.Callback)this);
            this.mHolder.setType(3);
        }

        public void surfaceChanged(SurfaceHolder surfaceHolder, int n, int n2, int n3) {
            if (this.mHolder.getSurface() == null) {
                return;
            }
            try {
                this.mCamera.stopPreview();
            }
            catch (Exception exception) {}
            try {
                this.mCamera.setPreviewDisplay(this.mHolder);
                this.mCamera.startPreview();
                CameraOp.this.OnPreviewChanged();
                return;
            }
            catch (Exception exception) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error starting camera preview: ");
                stringBuilder.append(exception.getMessage());
                Log.d((String)"CameraOp", (String)stringBuilder.toString());
            }
        }

        public void surfaceCreated(SurfaceHolder surfaceHolder) {
            try {
                this.mCamera.setPreviewDisplay(surfaceHolder);
                this.mCamera.startPreview();
                CameraOp.this.OnPreviewCreated();
                return;
            }
            catch (IOException iOException) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append("Error setting camera preview: ");
                stringBuilder.append(iOException.getMessage());
                Log.d((String)"CameraOp", (String)stringBuilder.toString());
            }
        }

        public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        }
    }

}


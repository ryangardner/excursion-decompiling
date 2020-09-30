package com.syntak.library;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.SurfaceHolder.Callback;
import android.widget.FrameLayout;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

public class CameraOp {
   private static Camera camera;
   private static CameraOp.CameraPreview camera_preview;
   private static PictureCallback jpeg;
   private static PictureCallback postview;
   private static PictureCallback raw;
   private static String save_file_name;
   private static ShutterCallback shutter;
   final String TAG = "CameraOp";
   Context context;
   FrameLayout preview;

   public CameraOp(Context var1, FrameLayout var2) {
      this.context = var1;
      this.preview = var2;
      camera = getCameraInstance();
      CameraOp.CameraPreview var3 = new CameraOp.CameraPreview(var1, camera);
      camera_preview = var3;
      var2.addView(var3);
      shutter = new ShutterCallback() {
         public void onShutter() {
            CameraOp.this.OnShutter();
         }
      };
      raw = new PictureCallback() {
         public void onPictureTaken(byte[] var1, Camera var2) {
            CameraOp.this.OnImageTakenRaw(var1, var2);
         }
      };
      postview = new PictureCallback() {
         public void onPictureTaken(byte[] var1, Camera var2) {
            CameraOp.this.OnImageTakenPostview(var1, var2);
         }
      };
      jpeg = new PictureCallback() {
         public void onPictureTaken(byte[] var1, Camera var2) {
            File var3 = new File(CameraOp.save_file_name);

            StringBuilder var6;
            try {
               FileOutputStream var7 = new FileOutputStream(var3);
               var7.write(var1);
               var7.close();
               CameraOp.this.OnImageTakenJpeg();
            } catch (FileNotFoundException var4) {
               var6 = new StringBuilder();
               var6.append("File not found: ");
               var6.append(var4.getMessage());
               Log.d("CameraOp", var6.toString());
            } catch (IOException var5) {
               var6 = new StringBuilder();
               var6.append("Error accessing file: ");
               var6.append(var5.getMessage());
               Log.d("CameraOp", var6.toString());
            }

         }
      };
   }

   public static boolean checkCameraHardware(Context var0) {
      return var0.getPackageManager().hasSystemFeature("android.hardware.camera");
   }

   public static Bitmap getBitmapFromRawData(byte[] var0, Camera var1) {
      Bitmap var2 = Bitmap.createBitmap(var1.getParameters().getPictureSize().width, var1.getParameters().getPictureSize().height, Config.ARGB_8888);
      var2.copyPixelsFromBuffer(ByteBuffer.wrap(var0));
      return var2;
   }

   public static Camera getCameraInstance() {
      Camera var0;
      try {
         var0 = Camera.open();
      } catch (Exception var1) {
         var0 = null;
      }

      return var0;
   }

   public static Parameters getCameraParameters() {
      return camera.getParameters();
   }

   public static void release() {
      camera_preview = null;
      camera.release();
      camera = null;
   }

   public static void setCameraParameters(Parameters var0) {
      camera.setParameters(var0);
   }

   public static void takePicture(String var0) {
      save_file_name = var0;
      camera.takePicture(shutter, raw, postview, jpeg);
   }

   public void OnImageTakenJpeg() {
   }

   public void OnImageTakenPostview(byte[] var1, Camera var2) {
   }

   public void OnImageTakenRaw(byte[] var1, Camera var2) {
   }

   public void OnPreviewChanged() {
   }

   public void OnPreviewCreated() {
   }

   public void OnShutter() {
   }

   public class CameraPreview extends SurfaceView implements Callback {
      private Camera mCamera;
      private SurfaceHolder mHolder;

      public CameraPreview(Context var2, Camera var3) {
         super(var2);
         this.mCamera = var3;
         SurfaceHolder var4 = this.getHolder();
         this.mHolder = var4;
         var4.addCallback(this);
         this.mHolder.setType(3);
      }

      public void surfaceChanged(SurfaceHolder var1, int var2, int var3, int var4) {
         if (this.mHolder.getSurface() != null) {
            try {
               this.mCamera.stopPreview();
            } catch (Exception var7) {
            }

            try {
               this.mCamera.setPreviewDisplay(this.mHolder);
               this.mCamera.startPreview();
               CameraOp.this.OnPreviewChanged();
            } catch (Exception var6) {
               StringBuilder var5 = new StringBuilder();
               var5.append("Error starting camera preview: ");
               var5.append(var6.getMessage());
               Log.d("CameraOp", var5.toString());
            }

         }
      }

      public void surfaceCreated(SurfaceHolder var1) {
         try {
            this.mCamera.setPreviewDisplay(var1);
            this.mCamera.startPreview();
            CameraOp.this.OnPreviewCreated();
         } catch (IOException var3) {
            StringBuilder var2 = new StringBuilder();
            var2.append("Error setting camera preview: ");
            var2.append(var3.getMessage());
            Log.d("CameraOp", var2.toString());
         }

      }

      public void surfaceDestroyed(SurfaceHolder var1) {
      }
   }
}

package com.google.android.material.transition.platform;

import android.app.Activity;
import android.app.SharedElementCallback;
import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Parcelable;
import android.transition.Transition;
import android.view.View;
import android.view.Window;
import android.view.View.MeasureSpec;
import androidx.core.graphics.BlendModeColorFilterCompat;
import androidx.core.graphics.BlendModeCompat;
import com.google.android.material.R;
import com.google.android.material.internal.ContextUtils;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.shape.Shapeable;
import java.lang.ref.WeakReference;
import java.util.List;
import java.util.Map;

public class MaterialContainerTransformSharedElementCallback extends SharedElementCallback {
   private static WeakReference<View> capturedSharedElement;
   private boolean entering = true;
   private Rect returnEndBounds;
   private MaterialContainerTransformSharedElementCallback.ShapeProvider shapeProvider = new MaterialContainerTransformSharedElementCallback.ShapeableViewShapeProvider();
   private boolean sharedElementReenterTransitionEnabled = false;
   private boolean transparentWindowBackgroundEnabled = true;

   private static void removeWindowBackground(Window var0) {
      var0.getDecorView().getBackground().mutate().setColorFilter(BlendModeColorFilterCompat.createBlendModeColorFilterCompat(0, BlendModeCompat.CLEAR));
   }

   private static void restoreWindowBackground(Window var0) {
      var0.getDecorView().getBackground().mutate().clearColorFilter();
   }

   private void setUpEnterTransform(final Window var1) {
      Transition var2 = var1.getSharedElementEnterTransition();
      if (var2 instanceof MaterialContainerTransform) {
         MaterialContainerTransform var3 = (MaterialContainerTransform)var2;
         if (!this.sharedElementReenterTransitionEnabled) {
            var1.setSharedElementReenterTransition((Transition)null);
         }

         if (this.transparentWindowBackgroundEnabled) {
            updateBackgroundFadeDuration(var1, var3);
            var3.addListener(new TransitionListenerAdapter() {
               public void onTransitionEnd(Transition var1x) {
                  MaterialContainerTransformSharedElementCallback.restoreWindowBackground(var1);
               }

               public void onTransitionStart(Transition var1x) {
                  MaterialContainerTransformSharedElementCallback.removeWindowBackground(var1);
               }
            });
         }
      }

   }

   private void setUpReturnTransform(final Activity var1, final Window var2) {
      Transition var3 = var2.getSharedElementReturnTransition();
      if (var3 instanceof MaterialContainerTransform) {
         MaterialContainerTransform var4 = (MaterialContainerTransform)var3;
         var4.setHoldAtEndEnabled(true);
         var4.addListener(new TransitionListenerAdapter() {
            public void onTransitionEnd(Transition var1x) {
               if (MaterialContainerTransformSharedElementCallback.capturedSharedElement != null) {
                  View var2 = (View)MaterialContainerTransformSharedElementCallback.capturedSharedElement.get();
                  if (var2 != null) {
                     var2.setAlpha(1.0F);
                     MaterialContainerTransformSharedElementCallback.capturedSharedElement = null;
                  }
               }

               var1.finish();
               var1.overridePendingTransition(0, 0);
            }
         });
         if (this.transparentWindowBackgroundEnabled) {
            updateBackgroundFadeDuration(var2, var4);
            var4.addListener(new TransitionListenerAdapter() {
               public void onTransitionStart(Transition var1) {
                  MaterialContainerTransformSharedElementCallback.removeWindowBackground(var2);
               }
            });
         }
      }

   }

   private static void updateBackgroundFadeDuration(Window var0, MaterialContainerTransform var1) {
      var0.setTransitionBackgroundFadeDuration(var1.getDuration());
   }

   public MaterialContainerTransformSharedElementCallback.ShapeProvider getShapeProvider() {
      return this.shapeProvider;
   }

   public boolean isSharedElementReenterTransitionEnabled() {
      return this.sharedElementReenterTransitionEnabled;
   }

   public boolean isTransparentWindowBackgroundEnabled() {
      return this.transparentWindowBackgroundEnabled;
   }

   public Parcelable onCaptureSharedElementSnapshot(View var1, Matrix var2, RectF var3) {
      capturedSharedElement = new WeakReference(var1);
      return super.onCaptureSharedElementSnapshot(var1, var2, var3);
   }

   public View onCreateSnapshotView(Context var1, Parcelable var2) {
      View var3 = super.onCreateSnapshotView(var1, var2);
      if (var3 != null) {
         WeakReference var4 = capturedSharedElement;
         if (var4 != null && this.shapeProvider != null) {
            View var5 = (View)var4.get();
            if (var5 != null) {
               ShapeAppearanceModel var6 = this.shapeProvider.provideShape(var5);
               if (var6 != null) {
                  var3.setTag(R.id.mtrl_motion_snapshot_view, var6);
               }
            }
         }
      }

      return var3;
   }

   public void onMapSharedElements(List<String> var1, Map<String, View> var2) {
      if (!var1.isEmpty() && !var2.isEmpty()) {
         View var3 = (View)var2.get(var1.get(0));
         if (var3 != null) {
            Activity var4 = ContextUtils.getActivity(var3.getContext());
            if (var4 != null) {
               Window var5 = var4.getWindow();
               if (this.entering) {
                  this.setUpEnterTransform(var5);
               } else {
                  this.setUpReturnTransform(var4, var5);
               }
            }
         }
      }

   }

   public void onSharedElementEnd(List<String> var1, List<View> var2, List<View> var3) {
      if (!var2.isEmpty() && ((View)var2.get(0)).getTag(R.id.mtrl_motion_snapshot_view) instanceof View) {
         ((View)var2.get(0)).setTag(R.id.mtrl_motion_snapshot_view, (Object)null);
      }

      if (!this.entering && !var2.isEmpty()) {
         this.returnEndBounds = TransitionUtils.getRelativeBoundsRect((View)var2.get(0));
      }

      this.entering = false;
   }

   public void onSharedElementStart(List<String> var1, List<View> var2, List<View> var3) {
      if (!var2.isEmpty() && !var3.isEmpty()) {
         ((View)var2.get(0)).setTag(R.id.mtrl_motion_snapshot_view, var3.get(0));
      }

      if (!this.entering && !var2.isEmpty() && this.returnEndBounds != null) {
         View var4 = (View)var2.get(0);
         var4.measure(MeasureSpec.makeMeasureSpec(this.returnEndBounds.width(), 1073741824), MeasureSpec.makeMeasureSpec(this.returnEndBounds.height(), 1073741824));
         var4.layout(this.returnEndBounds.left, this.returnEndBounds.top, this.returnEndBounds.right, this.returnEndBounds.bottom);
      }

   }

   public void setShapeProvider(MaterialContainerTransformSharedElementCallback.ShapeProvider var1) {
      this.shapeProvider = var1;
   }

   public void setSharedElementReenterTransitionEnabled(boolean var1) {
      this.sharedElementReenterTransitionEnabled = var1;
   }

   public void setTransparentWindowBackgroundEnabled(boolean var1) {
      this.transparentWindowBackgroundEnabled = var1;
   }

   public interface ShapeProvider {
      ShapeAppearanceModel provideShape(View var1);
   }

   public static class ShapeableViewShapeProvider implements MaterialContainerTransformSharedElementCallback.ShapeProvider {
      public ShapeAppearanceModel provideShape(View var1) {
         ShapeAppearanceModel var2;
         if (var1 instanceof Shapeable) {
            var2 = ((Shapeable)var1).getShapeAppearanceModel();
         } else {
            var2 = null;
         }

         return var2;
      }
   }
}

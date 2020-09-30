package androidx.transition;

import android.content.Context;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class Scene {
   private Context mContext;
   private Runnable mEnterAction;
   private Runnable mExitAction;
   private View mLayout;
   private int mLayoutId = -1;
   private ViewGroup mSceneRoot;

   public Scene(ViewGroup var1) {
      this.mSceneRoot = var1;
   }

   private Scene(ViewGroup var1, int var2, Context var3) {
      this.mContext = var3;
      this.mSceneRoot = var1;
      this.mLayoutId = var2;
   }

   public Scene(ViewGroup var1, View var2) {
      this.mSceneRoot = var1;
      this.mLayout = var2;
   }

   public static Scene getCurrentScene(ViewGroup var0) {
      return (Scene)var0.getTag(R.id.transition_current_scene);
   }

   public static Scene getSceneForLayout(ViewGroup var0, int var1, Context var2) {
      SparseArray var3 = (SparseArray)var0.getTag(R.id.transition_scene_layoutid_cache);
      SparseArray var4 = var3;
      if (var3 == null) {
         var4 = new SparseArray();
         var0.setTag(R.id.transition_scene_layoutid_cache, var4);
      }

      Scene var6 = (Scene)var4.get(var1);
      if (var6 != null) {
         return var6;
      } else {
         Scene var5 = new Scene(var0, var1, var2);
         var4.put(var1, var5);
         return var5;
      }
   }

   static void setCurrentScene(ViewGroup var0, Scene var1) {
      var0.setTag(R.id.transition_current_scene, var1);
   }

   public void enter() {
      if (this.mLayoutId > 0 || this.mLayout != null) {
         this.getSceneRoot().removeAllViews();
         if (this.mLayoutId > 0) {
            LayoutInflater.from(this.mContext).inflate(this.mLayoutId, this.mSceneRoot);
         } else {
            this.mSceneRoot.addView(this.mLayout);
         }
      }

      Runnable var1 = this.mEnterAction;
      if (var1 != null) {
         var1.run();
      }

      setCurrentScene(this.mSceneRoot, this);
   }

   public void exit() {
      if (getCurrentScene(this.mSceneRoot) == this) {
         Runnable var1 = this.mExitAction;
         if (var1 != null) {
            var1.run();
         }
      }

   }

   public ViewGroup getSceneRoot() {
      return this.mSceneRoot;
   }

   boolean isCreatedFromLayoutResource() {
      boolean var1;
      if (this.mLayoutId > 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public void setEnterAction(Runnable var1) {
      this.mEnterAction = var1;
   }

   public void setExitAction(Runnable var1) {
      this.mExitAction = var1;
   }
}

package com.google.android.gms.common.api.internal;

import android.util.Log;
import android.util.SparseArray;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.internal.Preconditions;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class zai extends zak {
   private final SparseArray<zai.zaa> zad = new SparseArray();

   private zai(LifecycleFragment var1) {
      super(var1);
      this.mLifecycleFragment.addCallback("AutoManageHelper", this);
   }

   public static zai zaa(LifecycleActivity var0) {
      LifecycleFragment var1 = getFragment(var0);
      zai var2 = (zai)var1.getCallbackOrNull("AutoManageHelper", zai.class);
      return var2 != null ? var2 : new zai(var1);
   }

   private final zai.zaa zab(int var1) {
      if (this.zad.size() <= var1) {
         return null;
      } else {
         SparseArray var2 = this.zad;
         return (zai.zaa)var2.get(var2.keyAt(var1));
      }
   }

   public void dump(String var1, FileDescriptor var2, PrintWriter var3, String[] var4) {
      for(int var5 = 0; var5 < this.zad.size(); ++var5) {
         zai.zaa var6 = this.zab(var5);
         if (var6 != null) {
            var3.append(var1).append("GoogleApiClient #").print(var6.zaa);
            var3.println(":");
            var6.zab.dump(String.valueOf(var1).concat("  "), var2, var3, var4);
         }
      }

   }

   public void onStart() {
      super.onStart();
      boolean var1 = this.zaa;
      String var2 = String.valueOf(this.zad);
      StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 14);
      var3.append("onStart ");
      var3.append(var1);
      var3.append(" ");
      var3.append(var2);
      Log.d("AutoManageHelper", var3.toString());
      if (this.zab.get() == null) {
         for(int var4 = 0; var4 < this.zad.size(); ++var4) {
            zai.zaa var5 = this.zab(var4);
            if (var5 != null) {
               var5.zab.connect();
            }
         }
      }

   }

   public void onStop() {
      super.onStop();

      for(int var1 = 0; var1 < this.zad.size(); ++var1) {
         zai.zaa var2 = this.zab(var1);
         if (var2 != null) {
            var2.zab.disconnect();
         }
      }

   }

   protected final void zaa() {
      for(int var1 = 0; var1 < this.zad.size(); ++var1) {
         zai.zaa var2 = this.zab(var1);
         if (var2 != null) {
            var2.zab.connect();
         }
      }

   }

   public final void zaa(int var1) {
      zai.zaa var2 = (zai.zaa)this.zad.get(var1);
      this.zad.remove(var1);
      if (var2 != null) {
         var2.zab.unregisterConnectionFailedListener(var2);
         var2.zab.disconnect();
      }

   }

   public final void zaa(int var1, GoogleApiClient var2, GoogleApiClient.OnConnectionFailedListener var3) {
      Preconditions.checkNotNull(var2, "GoogleApiClient instance cannot be null");
      boolean var4;
      if (this.zad.indexOfKey(var1) < 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      StringBuilder var5 = new StringBuilder(54);
      var5.append("Already managing a GoogleApiClient with id ");
      var5.append(var1);
      Preconditions.checkState(var4, var5.toString());
      zam var10 = (zam)this.zab.get();
      var4 = this.zaa;
      String var6 = String.valueOf(var10);
      StringBuilder var7 = new StringBuilder(String.valueOf(var6).length() + 49);
      var7.append("starting AutoManage for client ");
      var7.append(var1);
      var7.append(" ");
      var7.append(var4);
      var7.append(" ");
      var7.append(var6);
      Log.d("AutoManageHelper", var7.toString());
      zai.zaa var8 = new zai.zaa(var1, var2, var3);
      var2.registerConnectionFailedListener(var8);
      this.zad.put(var1, var8);
      if (this.zaa && var10 == null) {
         String var11 = String.valueOf(var2);
         StringBuilder var9 = new StringBuilder(String.valueOf(var11).length() + 11);
         var9.append("connecting ");
         var9.append(var11);
         Log.d("AutoManageHelper", var9.toString());
         var2.connect();
      }

   }

   protected final void zaa(ConnectionResult var1, int var2) {
      Log.w("AutoManageHelper", "Unresolved error while connecting client. Stopping auto-manage.");
      if (var2 < 0) {
         Log.wtf("AutoManageHelper", "AutoManageLifecycleHelper received onErrorResolutionFailed callback but no failing client ID is set", new Exception());
      } else {
         zai.zaa var3 = (zai.zaa)this.zad.get(var2);
         if (var3 != null) {
            this.zaa(var2);
            GoogleApiClient.OnConnectionFailedListener var4 = var3.zac;
            if (var4 != null) {
               var4.onConnectionFailed(var1);
            }
         }

      }
   }

   private final class zaa implements GoogleApiClient.OnConnectionFailedListener {
      public final int zaa;
      public final GoogleApiClient zab;
      public final GoogleApiClient.OnConnectionFailedListener zac;

      public zaa(int var2, GoogleApiClient var3, GoogleApiClient.OnConnectionFailedListener var4) {
         this.zaa = var2;
         this.zab = var3;
         this.zac = var4;
      }

      public final void onConnectionFailed(ConnectionResult var1) {
         String var2 = String.valueOf(var1);
         StringBuilder var3 = new StringBuilder(String.valueOf(var2).length() + 27);
         var3.append("beginFailureResolution for ");
         var3.append(var2);
         Log.d("AutoManageHelper", var3.toString());
         zai.this.zab(var1, this.zaa);
      }
   }
}

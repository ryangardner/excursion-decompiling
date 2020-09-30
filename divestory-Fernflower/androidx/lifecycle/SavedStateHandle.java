package androidx.lifecycle;

import android.os.Binder;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.util.Size;
import android.util.SizeF;
import android.util.SparseArray;
import androidx.savedstate.SavedStateRegistry;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class SavedStateHandle {
   private static final Class[] ACCEPTABLE_CLASSES;
   private static final String KEYS = "keys";
   private static final String VALUES = "values";
   private final Map<String, SavedStateHandle.SavingStateLiveData<?>> mLiveDatas = new HashMap();
   final Map<String, Object> mRegular;
   private final SavedStateRegistry.SavedStateProvider mSavedStateProvider = new SavedStateRegistry.SavedStateProvider() {
      public Bundle saveState() {
         Set var1 = SavedStateHandle.this.mRegular.keySet();
         ArrayList var2 = new ArrayList(var1.size());
         ArrayList var3 = new ArrayList(var2.size());
         Iterator var5 = var1.iterator();

         while(var5.hasNext()) {
            String var4 = (String)var5.next();
            var2.add(var4);
            var3.add(SavedStateHandle.this.mRegular.get(var4));
         }

         Bundle var6 = new Bundle();
         var6.putParcelableArrayList("keys", var2);
         var6.putParcelableArrayList("values", var3);
         return var6;
      }
   };

   static {
      Class var0 = Boolean.TYPE;
      Class var1 = Double.TYPE;
      Class var2 = Integer.TYPE;
      Class var3 = Long.TYPE;
      Class var4 = Byte.TYPE;
      Class var5 = Character.TYPE;
      Class var6 = Float.TYPE;
      Class var7 = Short.TYPE;
      Class var8;
      if (VERSION.SDK_INT >= 21) {
         var8 = Size.class;
      } else {
         var8 = Integer.TYPE;
      }

      Class var9;
      if (VERSION.SDK_INT >= 21) {
         var9 = SizeF.class;
      } else {
         var9 = Integer.TYPE;
      }

      ACCEPTABLE_CLASSES = new Class[]{var0, boolean[].class, var1, double[].class, var2, int[].class, var3, long[].class, String.class, String[].class, Binder.class, Bundle.class, var4, byte[].class, var5, char[].class, CharSequence.class, CharSequence[].class, ArrayList.class, var6, float[].class, Parcelable.class, Parcelable[].class, Serializable.class, var7, short[].class, SparseArray.class, var8, var9};
   }

   public SavedStateHandle() {
      this.mRegular = new HashMap();
   }

   public SavedStateHandle(Map<String, Object> var1) {
      this.mRegular = new HashMap(var1);
   }

   static SavedStateHandle createHandle(Bundle var0, Bundle var1) {
      if (var0 == null && var1 == null) {
         return new SavedStateHandle();
      } else {
         HashMap var2 = new HashMap();
         if (var1 != null) {
            Iterator var3 = var1.keySet().iterator();

            while(var3.hasNext()) {
               String var4 = (String)var3.next();
               var2.put(var4, var1.get(var4));
            }
         }

         if (var0 == null) {
            return new SavedStateHandle(var2);
         } else {
            ArrayList var7 = var0.getParcelableArrayList("keys");
            ArrayList var6 = var0.getParcelableArrayList("values");
            if (var7 != null && var6 != null && var7.size() == var6.size()) {
               for(int var5 = 0; var5 < var7.size(); ++var5) {
                  var2.put((String)var7.get(var5), var6.get(var5));
               }

               return new SavedStateHandle(var2);
            } else {
               throw new IllegalStateException("Invalid bundle passed as restored state");
            }
         }
      }
   }

   private <T> MutableLiveData<T> getLiveDataInternal(String var1, boolean var2, T var3) {
      MutableLiveData var4 = (MutableLiveData)this.mLiveDatas.get(var1);
      if (var4 != null) {
         return var4;
      } else {
         SavedStateHandle.SavingStateLiveData var5;
         if (this.mRegular.containsKey(var1)) {
            var5 = new SavedStateHandle.SavingStateLiveData(this, var1, this.mRegular.get(var1));
         } else if (var2) {
            var5 = new SavedStateHandle.SavingStateLiveData(this, var1, var3);
         } else {
            var5 = new SavedStateHandle.SavingStateLiveData(this, var1);
         }

         this.mLiveDatas.put(var1, var5);
         return var5;
      }
   }

   private static void validateValue(Object var0) {
      if (var0 != null) {
         Class[] var1 = ACCEPTABLE_CLASSES;
         int var2 = var1.length;

         for(int var3 = 0; var3 < var2; ++var3) {
            if (var1[var3].isInstance(var0)) {
               return;
            }
         }

         StringBuilder var4 = new StringBuilder();
         var4.append("Can't put value with type ");
         var4.append(var0.getClass());
         var4.append(" into saved state");
         throw new IllegalArgumentException(var4.toString());
      }
   }

   public boolean contains(String var1) {
      return this.mRegular.containsKey(var1);
   }

   public <T> T get(String var1) {
      return this.mRegular.get(var1);
   }

   public <T> MutableLiveData<T> getLiveData(String var1) {
      return this.getLiveDataInternal(var1, false, (Object)null);
   }

   public <T> MutableLiveData<T> getLiveData(String var1, T var2) {
      return this.getLiveDataInternal(var1, true, var2);
   }

   public Set<String> keys() {
      return Collections.unmodifiableSet(this.mRegular.keySet());
   }

   public <T> T remove(String var1) {
      Object var2 = this.mRegular.remove(var1);
      SavedStateHandle.SavingStateLiveData var3 = (SavedStateHandle.SavingStateLiveData)this.mLiveDatas.remove(var1);
      if (var3 != null) {
         var3.detach();
      }

      return var2;
   }

   SavedStateRegistry.SavedStateProvider savedStateProvider() {
      return this.mSavedStateProvider;
   }

   public <T> void set(String var1, T var2) {
      validateValue(var2);
      MutableLiveData var3 = (MutableLiveData)this.mLiveDatas.get(var1);
      if (var3 != null) {
         var3.setValue(var2);
      } else {
         this.mRegular.put(var1, var2);
      }

   }

   static class SavingStateLiveData<T> extends MutableLiveData<T> {
      private SavedStateHandle mHandle;
      private String mKey;

      SavingStateLiveData(SavedStateHandle var1, String var2) {
         this.mKey = var2;
         this.mHandle = var1;
      }

      SavingStateLiveData(SavedStateHandle var1, String var2, T var3) {
         super(var3);
         this.mKey = var2;
         this.mHandle = var1;
      }

      void detach() {
         this.mHandle = null;
      }

      public void setValue(T var1) {
         SavedStateHandle var2 = this.mHandle;
         if (var2 != null) {
            var2.mRegular.put(this.mKey, var1);
         }

         super.setValue(var1);
      }
   }
}

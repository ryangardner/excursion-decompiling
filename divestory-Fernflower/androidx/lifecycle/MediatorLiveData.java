package androidx.lifecycle;

import androidx.arch.core.internal.SafeIterableMap;
import java.util.Iterator;
import java.util.Map.Entry;

public class MediatorLiveData<T> extends MutableLiveData<T> {
   private SafeIterableMap<LiveData<?>, MediatorLiveData.Source<?>> mSources = new SafeIterableMap();

   public <S> void addSource(LiveData<S> var1, Observer<? super S> var2) {
      MediatorLiveData.Source var3 = new MediatorLiveData.Source(var1, var2);
      MediatorLiveData.Source var4 = (MediatorLiveData.Source)this.mSources.putIfAbsent(var1, var3);
      if (var4 != null && var4.mObserver != var2) {
         throw new IllegalArgumentException("This source was already added with the different observer");
      } else if (var4 == null) {
         if (this.hasActiveObservers()) {
            var3.plug();
         }

      }
   }

   protected void onActive() {
      Iterator var1 = this.mSources.iterator();

      while(var1.hasNext()) {
         ((MediatorLiveData.Source)((Entry)var1.next()).getValue()).plug();
      }

   }

   protected void onInactive() {
      Iterator var1 = this.mSources.iterator();

      while(var1.hasNext()) {
         ((MediatorLiveData.Source)((Entry)var1.next()).getValue()).unplug();
      }

   }

   public <S> void removeSource(LiveData<S> var1) {
      MediatorLiveData.Source var2 = (MediatorLiveData.Source)this.mSources.remove(var1);
      if (var2 != null) {
         var2.unplug();
      }

   }

   private static class Source<V> implements Observer<V> {
      final LiveData<V> mLiveData;
      final Observer<? super V> mObserver;
      int mVersion = -1;

      Source(LiveData<V> var1, Observer<? super V> var2) {
         this.mLiveData = var1;
         this.mObserver = var2;
      }

      public void onChanged(V var1) {
         if (this.mVersion != this.mLiveData.getVersion()) {
            this.mVersion = this.mLiveData.getVersion();
            this.mObserver.onChanged(var1);
         }

      }

      void plug() {
         this.mLiveData.observeForever(this);
      }

      void unplug() {
         this.mLiveData.removeObserver(this);
      }
   }
}

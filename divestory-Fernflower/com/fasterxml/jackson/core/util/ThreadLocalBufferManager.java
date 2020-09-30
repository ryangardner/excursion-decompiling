package com.fasterxml.jackson.core.util;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

class ThreadLocalBufferManager {
   private final Object RELEASE_LOCK = new Object();
   private final ReferenceQueue<BufferRecycler> _refQueue = new ReferenceQueue();
   private final Map<SoftReference<BufferRecycler>, Boolean> _trackedRecyclers = new ConcurrentHashMap();

   public static ThreadLocalBufferManager instance() {
      return ThreadLocalBufferManager.ThreadLocalBufferManagerHolder.manager;
   }

   private void removeSoftRefsClearedByGc() {
      while(true) {
         SoftReference var1 = (SoftReference)this._refQueue.poll();
         if (var1 == null) {
            return;
         }

         this._trackedRecyclers.remove(var1);
      }
   }

   public int releaseBuffers() {
      Object var1 = this.RELEASE_LOCK;
      synchronized(var1){}
      int var2 = 0;

      Throwable var10000;
      boolean var10001;
      label198: {
         Iterator var3;
         try {
            this.removeSoftRefsClearedByGc();
            var3 = this._trackedRecyclers.keySet().iterator();
         } catch (Throwable var22) {
            var10000 = var22;
            var10001 = false;
            break label198;
         }

         while(true) {
            label195: {
               try {
                  if (var3.hasNext()) {
                     ((SoftReference)var3.next()).clear();
                     break label195;
                  }
               } catch (Throwable var23) {
                  var10000 = var23;
                  var10001 = false;
                  break;
               }

               try {
                  this._trackedRecyclers.clear();
                  return var2;
               } catch (Throwable var21) {
                  var10000 = var21;
                  var10001 = false;
                  break;
               }
            }

            ++var2;
         }
      }

      while(true) {
         Throwable var24 = var10000;

         try {
            throw var24;
         } catch (Throwable var20) {
            var10000 = var20;
            var10001 = false;
            continue;
         }
      }
   }

   public SoftReference<BufferRecycler> wrapAndTrack(BufferRecycler var1) {
      SoftReference var2 = new SoftReference(var1, this._refQueue);
      this._trackedRecyclers.put(var2, true);
      this.removeSoftRefsClearedByGc();
      return var2;
   }

   private static final class ThreadLocalBufferManagerHolder {
      static final ThreadLocalBufferManager manager = new ThreadLocalBufferManager();
   }
}

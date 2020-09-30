package com.fasterxml.jackson.core.util;

import java.lang.ref.SoftReference;

public class BufferRecyclers {
   public static final String SYSTEM_PROPERTY_TRACK_REUSABLE_BUFFERS = "com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers";
   private static final ThreadLocalBufferManager _bufferRecyclerTracker;
   protected static final ThreadLocal<SoftReference<BufferRecycler>> _recyclerRef;

   static {
      ThreadLocalBufferManager var0;
      if ("true".equals(System.getProperty("com.fasterxml.jackson.core.util.BufferRecyclers.trackReusableBuffers"))) {
         var0 = ThreadLocalBufferManager.instance();
      } else {
         var0 = null;
      }

      _bufferRecyclerTracker = var0;
      _recyclerRef = new ThreadLocal();
   }

   public static BufferRecycler getBufferRecycler() {
      SoftReference var0 = (SoftReference)_recyclerRef.get();
      BufferRecycler var2;
      if (var0 == null) {
         var2 = null;
      } else {
         var2 = (BufferRecycler)var0.get();
      }

      BufferRecycler var1 = var2;
      if (var2 == null) {
         var1 = new BufferRecycler();
         ThreadLocalBufferManager var3 = _bufferRecyclerTracker;
         if (var3 != null) {
            var0 = var3.wrapAndTrack(var1);
         } else {
            var0 = new SoftReference(var1);
         }

         _recyclerRef.set(var0);
      }

      return var1;
   }

   public static int releaseBuffers() {
      ThreadLocalBufferManager var0 = _bufferRecyclerTracker;
      return var0 != null ? var0.releaseBuffers() : -1;
   }
}

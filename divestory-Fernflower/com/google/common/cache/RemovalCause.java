package com.google.common.cache;

public enum RemovalCause {
   COLLECTED {
      boolean wasEvicted() {
         return true;
      }
   },
   EXPIRED {
      boolean wasEvicted() {
         return true;
      }
   },
   EXPLICIT {
      boolean wasEvicted() {
         return false;
      }
   },
   REPLACED {
      boolean wasEvicted() {
         return false;
      }
   },
   SIZE;

   static {
      RemovalCause var0 = new RemovalCause("SIZE", 4) {
         boolean wasEvicted() {
            return true;
         }
      };
      SIZE = var0;
   }

   private RemovalCause() {
   }

   // $FF: synthetic method
   RemovalCause(Object var3) {
      this();
   }

   abstract boolean wasEvicted();
}

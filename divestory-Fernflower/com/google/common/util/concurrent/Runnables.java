package com.google.common.util.concurrent;

public final class Runnables {
   private static final Runnable EMPTY_RUNNABLE = new Runnable() {
      public void run() {
      }
   };

   private Runnables() {
   }

   public static Runnable doNothing() {
      return EMPTY_RUNNABLE;
   }
}

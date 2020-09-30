package com.google.common.base;

abstract class CommonMatcher {
   public abstract int end();

   public abstract boolean find();

   public abstract boolean find(int var1);

   public abstract boolean matches();

   public abstract String replaceAll(String var1);

   public abstract int start();
}

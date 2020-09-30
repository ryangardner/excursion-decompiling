package com.google.common.base;

import java.io.Serializable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

final class JdkPattern extends CommonPattern implements Serializable {
   private static final long serialVersionUID = 0L;
   private final Pattern pattern;

   JdkPattern(Pattern var1) {
      this.pattern = (Pattern)Preconditions.checkNotNull(var1);
   }

   public int flags() {
      return this.pattern.flags();
   }

   public CommonMatcher matcher(CharSequence var1) {
      return new JdkPattern.JdkMatcher(this.pattern.matcher(var1));
   }

   public String pattern() {
      return this.pattern.pattern();
   }

   public String toString() {
      return this.pattern.toString();
   }

   private static final class JdkMatcher extends CommonMatcher {
      final Matcher matcher;

      JdkMatcher(Matcher var1) {
         this.matcher = (Matcher)Preconditions.checkNotNull(var1);
      }

      public int end() {
         return this.matcher.end();
      }

      public boolean find() {
         return this.matcher.find();
      }

      public boolean find(int var1) {
         return this.matcher.find(var1);
      }

      public boolean matches() {
         return this.matcher.matches();
      }

      public String replaceAll(String var1) {
         return this.matcher.replaceAll(var1);
      }

      public int start() {
         return this.matcher.start();
      }
   }
}

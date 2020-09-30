package org.apache.commons.net.nntp;

import java.util.Calendar;

public final class NewGroupsOrNewsQuery {
   private final String __date;
   private StringBuffer __distributions = null;
   private final boolean __isGMT;
   private StringBuffer __newsgroups = null;
   private final String __time;

   public NewGroupsOrNewsQuery(Calendar var1, boolean var2) {
      this.__isGMT = var2;
      StringBuilder var3 = new StringBuilder();
      String var4 = Integer.toString(var1.get(1));
      int var5 = var4.length();
      if (var5 >= 2) {
         var3.append(var4.substring(var5 - 2));
      } else {
         var3.append("00");
      }

      var4 = Integer.toString(var1.get(2) + 1);
      var5 = var4.length();
      if (var5 == 1) {
         var3.append('0');
         var3.append(var4);
      } else if (var5 == 2) {
         var3.append(var4);
      } else {
         var3.append("01");
      }

      var4 = Integer.toString(var1.get(5));
      var5 = var4.length();
      if (var5 == 1) {
         var3.append('0');
         var3.append(var4);
      } else if (var5 == 2) {
         var3.append(var4);
      } else {
         var3.append("01");
      }

      this.__date = var3.toString();
      var3.setLength(0);
      var4 = Integer.toString(var1.get(11));
      var5 = var4.length();
      if (var5 == 1) {
         var3.append('0');
         var3.append(var4);
      } else if (var5 == 2) {
         var3.append(var4);
      } else {
         var3.append("00");
      }

      var4 = Integer.toString(var1.get(12));
      var5 = var4.length();
      if (var5 == 1) {
         var3.append('0');
         var3.append(var4);
      } else if (var5 == 2) {
         var3.append(var4);
      } else {
         var3.append("00");
      }

      String var6 = Integer.toString(var1.get(13));
      var5 = var6.length();
      if (var5 == 1) {
         var3.append('0');
         var3.append(var6);
      } else if (var5 == 2) {
         var3.append(var6);
      } else {
         var3.append("00");
      }

      this.__time = var3.toString();
   }

   public void addDistribution(String var1) {
      StringBuffer var2 = this.__distributions;
      if (var2 != null) {
         var2.append(',');
      } else {
         this.__distributions = new StringBuffer();
      }

      this.__distributions.append(var1);
   }

   public void addNewsgroup(String var1) {
      StringBuffer var2 = this.__newsgroups;
      if (var2 != null) {
         var2.append(',');
      } else {
         this.__newsgroups = new StringBuffer();
      }

      this.__newsgroups.append(var1);
   }

   public String getDate() {
      return this.__date;
   }

   public String getDistributions() {
      StringBuffer var1 = this.__distributions;
      String var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.toString();
      }

      return var2;
   }

   public String getNewsgroups() {
      StringBuffer var1 = this.__newsgroups;
      String var2;
      if (var1 == null) {
         var2 = null;
      } else {
         var2 = var1.toString();
      }

      return var2;
   }

   public String getTime() {
      return this.__time;
   }

   public boolean isGMT() {
      return this.__isGMT;
   }

   public void omitNewsgroup(String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("!");
      var2.append(var1);
      this.addNewsgroup(var2.toString());
   }
}

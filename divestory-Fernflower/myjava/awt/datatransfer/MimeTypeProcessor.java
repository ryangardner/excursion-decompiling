package myjava.awt.datatransfer;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Hashtable;

final class MimeTypeProcessor {
   private static MimeTypeProcessor instance;

   private MimeTypeProcessor() {
   }

   static String assemble(MimeTypeProcessor.MimeType var0) {
      StringBuilder var1 = new StringBuilder();
      var1.append(var0.getFullType());
      Enumeration var2 = var0.parameters.keys();

      while(var2.hasMoreElements()) {
         String var3 = (String)var2.nextElement();
         String var4 = (String)var0.parameters.get(var3);
         var1.append("; ");
         var1.append(var3);
         var1.append("=\"");
         var1.append(var4);
         var1.append('"');
      }

      return var1.toString();
   }

   private static int getNextMeaningfulIndex(String var0, int var1) {
      while(var1 < var0.length() && !isMeaningfulChar(var0.charAt(var1))) {
         ++var1;
      }

      return var1;
   }

   private static boolean isMeaningfulChar(char var0) {
      return var0 >= '!' && var0 <= '~';
   }

   private static boolean isTSpecialChar(char var0) {
      return var0 == '(' || var0 == ')' || var0 == '[' || var0 == ']' || var0 == '<' || var0 == '>' || var0 == '@' || var0 == ',' || var0 == ';' || var0 == ':' || var0 == '\\' || var0 == '"' || var0 == '/' || var0 == '?' || var0 == '=';
   }

   static MimeTypeProcessor.MimeType parse(String var0) {
      if (instance == null) {
         instance = new MimeTypeProcessor();
      }

      MimeTypeProcessor.MimeType var1 = new MimeTypeProcessor.MimeType();
      if (var0 != null) {
         MimeTypeProcessor.StringPosition var2 = new MimeTypeProcessor.StringPosition((MimeTypeProcessor.StringPosition)null);
         retrieveType(var0, var1, var2);
         retrieveParams(var0, var1, var2);
      }

      return var1;
   }

   private static void retrieveParam(String var0, MimeTypeProcessor.MimeType var1, MimeTypeProcessor.StringPosition var2) {
      String var3 = retrieveToken(var0, var2).toLowerCase();
      var2.i = getNextMeaningfulIndex(var0, var2.i);
      if (var2.i < var0.length() && var0.charAt(var2.i) == '=') {
         ++var2.i;
         var2.i = getNextMeaningfulIndex(var0, var2.i);
         if (var2.i < var0.length()) {
            if (var0.charAt(var2.i) == '"') {
               var0 = retrieveQuoted(var0, var2);
            } else {
               var0 = retrieveToken(var0, var2);
            }

            var1.parameters.put(var3, var0);
         } else {
            throw new IllegalArgumentException();
         }
      } else {
         throw new IllegalArgumentException();
      }
   }

   private static void retrieveParams(String var0, MimeTypeProcessor.MimeType var1, MimeTypeProcessor.StringPosition var2) {
      var1.parameters = new Hashtable();
      var1.systemParameters = new Hashtable();

      while(true) {
         var2.i = getNextMeaningfulIndex(var0, var2.i);
         if (var2.i >= var0.length()) {
            return;
         }

         if (var0.charAt(var2.i) != ';') {
            throw new IllegalArgumentException();
         }

         ++var2.i;
         retrieveParam(var0, var1, var2);
      }
   }

   private static String retrieveQuoted(String var0, MimeTypeProcessor.StringPosition var1) {
      StringBuilder var2 = new StringBuilder();
      ++var1.i;
      boolean var3 = true;

      do {
         if (var0.charAt(var1.i) == '"' && var3) {
            ++var1.i;
            return var2.toString();
         }

         int var4 = var1.i++;
         char var5 = var0.charAt(var4);
         if (!var3) {
            var3 = true;
         } else if (var5 == '\\') {
            var3 = false;
         }

         if (var3) {
            var2.append(var5);
         }
      } while(var1.i != var0.length());

      throw new IllegalArgumentException();
   }

   private static String retrieveToken(String var0, MimeTypeProcessor.StringPosition var1) {
      StringBuilder var2 = new StringBuilder();
      var1.i = getNextMeaningfulIndex(var0, var1.i);
      if (var1.i < var0.length() && !isTSpecialChar(var0.charAt(var1.i))) {
         do {
            int var3 = var1.i++;
            var2.append(var0.charAt(var3));
         } while(var1.i < var0.length() && isMeaningfulChar(var0.charAt(var1.i)) && !isTSpecialChar(var0.charAt(var1.i)));

         return var2.toString();
      } else {
         throw new IllegalArgumentException();
      }
   }

   private static void retrieveType(String var0, MimeTypeProcessor.MimeType var1, MimeTypeProcessor.StringPosition var2) {
      var1.primaryType = retrieveToken(var0, var2).toLowerCase();
      var2.i = getNextMeaningfulIndex(var0, var2.i);
      if (var2.i < var0.length() && var0.charAt(var2.i) == '/') {
         ++var2.i;
         var1.subType = retrieveToken(var0, var2).toLowerCase();
      } else {
         throw new IllegalArgumentException();
      }
   }

   static final class MimeType implements Cloneable, Serializable {
      private static final long serialVersionUID = -6693571907475992044L;
      private Hashtable<String, String> parameters;
      private String primaryType;
      private String subType;
      private Hashtable<String, Object> systemParameters;

      MimeType() {
         this.primaryType = null;
         this.subType = null;
         this.parameters = null;
         this.systemParameters = null;
      }

      MimeType(String var1, String var2) {
         this.primaryType = var1;
         this.subType = var2;
         this.parameters = new Hashtable();
         this.systemParameters = new Hashtable();
      }

      void addParameter(String var1, String var2) {
         if (var2 != null) {
            String var3 = var2;
            if (var2.charAt(0) == '"') {
               var3 = var2;
               if (var2.charAt(var2.length() - 1) == '"') {
                  var3 = var2.substring(1, var2.length() - 2);
               }
            }

            if (var3.length() != 0) {
               this.parameters.put(var1, var3);
            }
         }
      }

      void addSystemParameter(String var1, Object var2) {
         this.systemParameters.put(var1, var2);
      }

      public Object clone() {
         MimeTypeProcessor.MimeType var1 = new MimeTypeProcessor.MimeType(this.primaryType, this.subType);
         var1.parameters = (Hashtable)this.parameters.clone();
         var1.systemParameters = (Hashtable)this.systemParameters.clone();
         return var1;
      }

      boolean equals(MimeTypeProcessor.MimeType var1) {
         return var1 == null ? false : this.getFullType().equals(var1.getFullType());
      }

      String getFullType() {
         StringBuilder var1 = new StringBuilder(String.valueOf(this.primaryType));
         var1.append("/");
         var1.append(this.subType);
         return var1.toString();
      }

      String getParameter(String var1) {
         return (String)this.parameters.get(var1);
      }

      String getPrimaryType() {
         return this.primaryType;
      }

      String getSubType() {
         return this.subType;
      }

      Object getSystemParameter(String var1) {
         return this.systemParameters.get(var1);
      }

      void removeParameter(String var1) {
         this.parameters.remove(var1);
      }
   }

   private static final class StringPosition {
      int i;

      private StringPosition() {
         this.i = 0;
      }

      // $FF: synthetic method
      StringPosition(MimeTypeProcessor.StringPosition var1) {
         this();
      }
   }
}

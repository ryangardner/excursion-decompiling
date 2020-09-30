package com.fasterxml.jackson.core;

import java.io.Serializable;
import java.nio.charset.Charset;

public class JsonLocation implements Serializable {
   public static final int MAX_CONTENT_SNIPPET = 500;
   public static final JsonLocation NA = new JsonLocation((Object)null, -1L, -1L, -1, -1);
   private static final long serialVersionUID = 1L;
   protected final int _columnNr;
   protected final int _lineNr;
   final transient Object _sourceRef;
   protected final long _totalBytes;
   protected final long _totalChars;

   public JsonLocation(Object var1, long var2, int var4, int var5) {
      this(var1, -1L, var2, var4, var5);
   }

   public JsonLocation(Object var1, long var2, long var4, int var6, int var7) {
      this._sourceRef = var1;
      this._totalBytes = var2;
      this._totalChars = var4;
      this._lineNr = var6;
      this._columnNr = var7;
   }

   private int _append(StringBuilder var1, String var2) {
      var1.append('"');
      var1.append(var2);
      var1.append('"');
      return var2.length();
   }

   protected StringBuilder _appendSourceDesc(StringBuilder var1) {
      Object var2 = this._sourceRef;
      if (var2 == null) {
         var1.append("UNKNOWN");
         return var1;
      } else {
         Class var3;
         if (var2 instanceof Class) {
            var3 = (Class)var2;
         } else {
            var3 = var2.getClass();
         }

         String var4 = var3.getName();
         String var8;
         if (var4.startsWith("java.")) {
            var8 = var3.getSimpleName();
         } else if (var2 instanceof byte[]) {
            var8 = "byte[]";
         } else {
            var8 = var4;
            if (var2 instanceof char[]) {
               var8 = "char[]";
            }
         }

         int var6;
         label37: {
            var1.append('(');
            var1.append(var8);
            var1.append(')');
            boolean var5 = var2 instanceof CharSequence;
            var6 = 0;
            var8 = " chars";
            int var7;
            if (var5) {
               CharSequence var9 = (CharSequence)var2;
               var6 = var9.length();
               var7 = this._append(var1, var9.subSequence(0, Math.min(var6, 500)).toString());
            } else {
               if (!(var2 instanceof char[])) {
                  if (var2 instanceof byte[]) {
                     byte[] var11 = (byte[])var2;
                     var6 = Math.min(var11.length, 500);
                     this._append(var1, new String(var11, 0, var6, Charset.forName("UTF-8")));
                     var6 = var11.length - var6;
                     var8 = " bytes";
                  }
                  break label37;
               }

               char[] var10 = (char[])var2;
               var6 = var10.length;
               var7 = this._append(var1, new String(var10, 0, Math.min(var6, 500)));
            }

            var6 -= var7;
         }

         if (var6 > 0) {
            var1.append("[truncated ");
            var1.append(var6);
            var1.append(var8);
            var1.append(']');
         }

         return var1;
      }
   }

   public boolean equals(Object var1) {
      boolean var2 = true;
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else if (!(var1 instanceof JsonLocation)) {
         return false;
      } else {
         JsonLocation var3 = (JsonLocation)var1;
         var1 = this._sourceRef;
         if (var1 == null) {
            if (var3._sourceRef != null) {
               return false;
            }
         } else if (!var1.equals(var3._sourceRef)) {
            return false;
         }

         if (this._lineNr != var3._lineNr || this._columnNr != var3._columnNr || this._totalChars != var3._totalChars || this.getByteOffset() != var3.getByteOffset()) {
            var2 = false;
         }

         return var2;
      }
   }

   public long getByteOffset() {
      return this._totalBytes;
   }

   public long getCharOffset() {
      return this._totalChars;
   }

   public int getColumnNr() {
      return this._columnNr;
   }

   public int getLineNr() {
      return this._lineNr;
   }

   public Object getSourceRef() {
      return this._sourceRef;
   }

   public int hashCode() {
      Object var1 = this._sourceRef;
      int var2;
      if (var1 == null) {
         var2 = 1;
      } else {
         var2 = var1.hashCode();
      }

      return ((var2 ^ this._lineNr) + this._columnNr ^ (int)this._totalChars) + (int)this._totalBytes;
   }

   public String sourceDescription() {
      return this._appendSourceDesc(new StringBuilder(100)).toString();
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(80);
      var1.append("[Source: ");
      this._appendSourceDesc(var1);
      var1.append("; line: ");
      var1.append(this._lineNr);
      var1.append(", column: ");
      var1.append(this._columnNr);
      var1.append(']');
      return var1.toString();
   }
}

package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.NumberInput;

public class JsonPointer {
   protected static final JsonPointer EMPTY = new JsonPointer();
   public static final char SEPARATOR = '/';
   protected final String _asString;
   protected volatile JsonPointer _head;
   protected final int _matchingElementIndex;
   protected final String _matchingPropertyName;
   protected final JsonPointer _nextSegment;

   protected JsonPointer() {
      this._nextSegment = null;
      this._matchingPropertyName = "";
      this._matchingElementIndex = -1;
      this._asString = "";
   }

   protected JsonPointer(String var1, String var2, int var3, JsonPointer var4) {
      this._asString = var1;
      this._nextSegment = var4;
      this._matchingPropertyName = var2;
      this._matchingElementIndex = var3;
   }

   protected JsonPointer(String var1, String var2, JsonPointer var3) {
      this._asString = var1;
      this._nextSegment = var3;
      this._matchingPropertyName = var2;
      this._matchingElementIndex = _parseIndex(var2);
   }

   private static void _appendEscape(StringBuilder var0, char var1) {
      char var2;
      byte var3;
      if (var1 == '0') {
         var3 = 126;
         var2 = (char)var3;
      } else if (var1 == '1') {
         var3 = 47;
         var2 = (char)var3;
      } else {
         var0.append('~');
         var2 = var1;
      }

      var0.append(var2);
   }

   private static void _appendEscaped(StringBuilder var0, String var1) {
      int var2 = var1.length();

      for(int var3 = 0; var3 < var2; ++var3) {
         char var4 = var1.charAt(var3);
         if (var4 == '/') {
            var0.append("~1");
         } else if (var4 == '~') {
            var0.append("~0");
         } else {
            var0.append(var4);
         }
      }

   }

   private static String _fullPath(JsonPointer var0, String var1) {
      StringBuilder var3;
      if (var0 == null) {
         var3 = new StringBuilder(var1.length() + 1);
         var3.append('/');
         _appendEscaped(var3, var1);
         return var3.toString();
      } else {
         String var2 = var0._asString;
         var3 = new StringBuilder(var1.length() + 1 + var2.length());
         var3.append('/');
         _appendEscaped(var3, var1);
         var3.append(var2);
         return var3.toString();
      }
   }

   private static final int _parseIndex(String var0) {
      int var1 = var0.length();
      byte var2 = -1;
      if (var1 != 0 && var1 <= 10) {
         char var3 = var0.charAt(0);
         int var4 = 1;
         if (var3 <= '0') {
            byte var6 = var2;
            if (var1 == 1) {
               var6 = var2;
               if (var3 == '0') {
                  var6 = 0;
               }
            }

            return var6;
         } else if (var3 > '9') {
            return -1;
         } else {
            while(var4 < var1) {
               char var5 = var0.charAt(var4);
               if (var5 > '9' || var5 < '0') {
                  return -1;
               }

               ++var4;
            }

            if (var1 == 10 && NumberInput.parseLong(var0) > 2147483647L) {
               return -1;
            } else {
               return NumberInput.parseInt(var0);
            }
         }
      } else {
         return -1;
      }
   }

   protected static JsonPointer _parseQuotedTail(String var0, int var1) {
      int var2 = var0.length();
      StringBuilder var3 = new StringBuilder(Math.max(16, var2));
      if (var1 > 2) {
         var3.append(var0, 1, var1 - 1);
      }

      int var4 = var1 + 1;
      _appendEscape(var3, var0.charAt(var1));
      var1 = var4;

      while(true) {
         while(var1 < var2) {
            char var5 = var0.charAt(var1);
            if (var5 == '/') {
               return new JsonPointer(var0, var3.toString(), _parseTail(var0.substring(var1)));
            }

            ++var1;
            if (var5 == '~' && var1 < var2) {
               _appendEscape(var3, var0.charAt(var1));
               ++var1;
            } else {
               var3.append(var5);
            }
         }

         return new JsonPointer(var0, var3.toString(), EMPTY);
      }
   }

   protected static JsonPointer _parseTail(String var0) {
      int var1 = var0.length();
      int var2 = 1;

      while(var2 < var1) {
         char var3 = var0.charAt(var2);
         if (var3 == '/') {
            return new JsonPointer(var0, var0.substring(1, var2), _parseTail(var0.substring(var2)));
         }

         int var4 = var2 + 1;
         var2 = var4;
         if (var3 == '~') {
            var2 = var4;
            if (var4 < var1) {
               return _parseQuotedTail(var0, var4);
            }
         }
      }

      return new JsonPointer(var0, var0.substring(1), EMPTY);
   }

   public static JsonPointer compile(String var0) throws IllegalArgumentException {
      if (var0 != null && var0.length() != 0) {
         if (var0.charAt(0) == '/') {
            return _parseTail(var0);
         } else {
            StringBuilder var1 = new StringBuilder();
            var1.append("Invalid input: JSON Pointer expression must start with '/': \"");
            var1.append(var0);
            var1.append("\"");
            throw new IllegalArgumentException(var1.toString());
         }
      } else {
         return EMPTY;
      }
   }

   public static JsonPointer empty() {
      return EMPTY;
   }

   public static JsonPointer forPath(JsonStreamContext var0, boolean var1) {
      if (var0 == null) {
         return EMPTY;
      } else {
         JsonStreamContext var2 = var0;
         if (!var0.hasPathSegment()) {
            label44: {
               if (var1 && var0.inRoot()) {
                  var2 = var0;
                  if (var0.hasCurrentIndex()) {
                     break label44;
                  }
               }

               var2 = var0.getParent();
            }
         }

         JsonPointer var3;
         JsonPointer var6;
         for(var3 = null; var2 != null; var3 = var6) {
            String var7;
            if (var2.inObject()) {
               String var4 = var2.getCurrentName();
               var7 = var4;
               if (var4 == null) {
                  var7 = "";
               }

               var6 = new JsonPointer(_fullPath(var3, var7), var7, var3);
            } else {
               label52: {
                  if (!var2.inArray()) {
                     var6 = var3;
                     if (!var1) {
                        break label52;
                     }
                  }

                  int var5 = var2.getCurrentIndex();
                  var7 = String.valueOf(var5);
                  var6 = new JsonPointer(_fullPath(var3, var7), var7, var5, var3);
               }
            }

            var2 = var2.getParent();
         }

         if (var3 == null) {
            return EMPTY;
         } else {
            return var3;
         }
      }
   }

   public static JsonPointer valueOf(String var0) {
      return compile(var0);
   }

   protected JsonPointer _constructHead() {
      JsonPointer var1 = this.last();
      if (var1 == this) {
         return EMPTY;
      } else {
         int var2 = var1._asString.length();
         JsonPointer var3 = this._nextSegment;
         String var4 = this._asString;
         return new JsonPointer(var4.substring(0, var4.length() - var2), this._matchingPropertyName, this._matchingElementIndex, var3._constructHead(var2, var1));
      }
   }

   protected JsonPointer _constructHead(int var1, JsonPointer var2) {
      if (this == var2) {
         return EMPTY;
      } else {
         JsonPointer var3 = this._nextSegment;
         String var4 = this._asString;
         return new JsonPointer(var4.substring(0, var4.length() - var1), this._matchingPropertyName, this._matchingElementIndex, var3._constructHead(var1, var2));
      }
   }

   public JsonPointer append(JsonPointer var1) {
      JsonPointer var2 = EMPTY;
      if (this == var2) {
         return var1;
      } else if (var1 == var2) {
         return this;
      } else {
         String var3 = this._asString;
         String var4 = var3;
         if (var3.endsWith("/")) {
            var4 = var3.substring(0, var3.length() - 1);
         }

         StringBuilder var5 = new StringBuilder();
         var5.append(var4);
         var5.append(var1._asString);
         return compile(var5.toString());
      }
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 == null) {
         return false;
      } else {
         return !(var1 instanceof JsonPointer) ? false : this._asString.equals(((JsonPointer)var1)._asString);
      }
   }

   public int getMatchingIndex() {
      return this._matchingElementIndex;
   }

   public String getMatchingProperty() {
      return this._matchingPropertyName;
   }

   public int hashCode() {
      return this._asString.hashCode();
   }

   public JsonPointer head() {
      JsonPointer var1 = this._head;
      JsonPointer var2 = var1;
      if (var1 == null) {
         if (this != EMPTY) {
            var1 = this._constructHead();
         }

         this._head = var1;
         var2 = var1;
      }

      return var2;
   }

   public JsonPointer last() {
      if (this == EMPTY) {
         return null;
      } else {
         JsonPointer var1 = this;

         while(true) {
            JsonPointer var2 = var1._nextSegment;
            if (var2 == EMPTY) {
               return var1;
            }

            var1 = var2;
         }
      }
   }

   public JsonPointer matchElement(int var1) {
      return var1 == this._matchingElementIndex && var1 >= 0 ? this._nextSegment : null;
   }

   public JsonPointer matchProperty(String var1) {
      return this._nextSegment != null && this._matchingPropertyName.equals(var1) ? this._nextSegment : null;
   }

   public boolean matches() {
      boolean var1;
      if (this._nextSegment == null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean matchesElement(int var1) {
      boolean var2;
      if (var1 == this._matchingElementIndex && var1 >= 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean matchesProperty(String var1) {
      boolean var2;
      if (this._nextSegment != null && this._matchingPropertyName.equals(var1)) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean mayMatchElement() {
      boolean var1;
      if (this._matchingElementIndex >= 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean mayMatchProperty() {
      boolean var1;
      if (this._matchingPropertyName != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public JsonPointer tail() {
      return this._nextSegment;
   }

   public String toString() {
      return this._asString;
   }
}

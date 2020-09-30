package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.JsonPointer;

public class JsonPointerBasedFilter extends TokenFilter {
   protected final JsonPointer _pathToMatch;

   public JsonPointerBasedFilter(JsonPointer var1) {
      this._pathToMatch = var1;
   }

   public JsonPointerBasedFilter(String var1) {
      this(JsonPointer.compile(var1));
   }

   protected boolean _includeScalar() {
      return this._pathToMatch.matches();
   }

   public TokenFilter filterStartArray() {
      return this;
   }

   public TokenFilter filterStartObject() {
      return this;
   }

   public TokenFilter includeElement(int var1) {
      JsonPointer var2 = this._pathToMatch.matchElement(var1);
      if (var2 == null) {
         return null;
      } else {
         return (TokenFilter)(var2.matches() ? TokenFilter.INCLUDE_ALL : new JsonPointerBasedFilter(var2));
      }
   }

   public TokenFilter includeProperty(String var1) {
      JsonPointer var2 = this._pathToMatch.matchProperty(var1);
      if (var2 == null) {
         return null;
      } else {
         return (TokenFilter)(var2.matches() ? TokenFilter.INCLUDE_ALL : new JsonPointerBasedFilter(var2));
      }
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append("[JsonPointerFilter at: ");
      var1.append(this._pathToMatch);
      var1.append("]");
      return var1.toString();
   }
}

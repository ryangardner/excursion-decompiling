package com.fasterxml.jackson.core.util;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonParserSequence extends JsonParserDelegate {
   protected final boolean _checkForExistingToken;
   protected boolean _hasToken;
   protected int _nextParserIndex;
   protected final JsonParser[] _parsers;

   protected JsonParserSequence(boolean var1, JsonParser[] var2) {
      boolean var3 = false;
      super(var2[0]);
      this._checkForExistingToken = var1;
      boolean var4 = var3;
      if (var1) {
         var4 = var3;
         if (this.delegate.hasCurrentToken()) {
            var4 = true;
         }
      }

      this._hasToken = var4;
      this._parsers = var2;
      this._nextParserIndex = 1;
   }

   @Deprecated
   protected JsonParserSequence(JsonParser[] var1) {
      this(false, var1);
   }

   @Deprecated
   public static JsonParserSequence createFlattened(JsonParser var0, JsonParser var1) {
      return createFlattened(false, var0, var1);
   }

   public static JsonParserSequence createFlattened(boolean var0, JsonParser var1, JsonParser var2) {
      boolean var3 = var1 instanceof JsonParserSequence;
      if (!var3 && !(var2 instanceof JsonParserSequence)) {
         return new JsonParserSequence(var0, new JsonParser[]{var1, var2});
      } else {
         ArrayList var4 = new ArrayList();
         if (var3) {
            ((JsonParserSequence)var1).addFlattenedActiveParsers(var4);
         } else {
            var4.add(var1);
         }

         if (var2 instanceof JsonParserSequence) {
            ((JsonParserSequence)var2).addFlattenedActiveParsers(var4);
         } else {
            var4.add(var2);
         }

         return new JsonParserSequence(var0, (JsonParser[])var4.toArray(new JsonParser[var4.size()]));
      }
   }

   protected void addFlattenedActiveParsers(List<JsonParser> var1) {
      int var2 = this._nextParserIndex - 1;

      for(int var3 = this._parsers.length; var2 < var3; ++var2) {
         JsonParser var4 = this._parsers[var2];
         if (var4 instanceof JsonParserSequence) {
            ((JsonParserSequence)var4).addFlattenedActiveParsers(var1);
         } else {
            var1.add(var4);
         }
      }

   }

   public void close() throws IOException {
      do {
         this.delegate.close();
      } while(this.switchToNext());

   }

   public int containedParsersCount() {
      return this._parsers.length;
   }

   public JsonToken nextToken() throws IOException {
      if (this.delegate == null) {
         return null;
      } else if (this._hasToken) {
         this._hasToken = false;
         return this.delegate.currentToken();
      } else {
         JsonToken var1 = this.delegate.nextToken();
         JsonToken var2 = var1;
         if (var1 == null) {
            var2 = this.switchAndReturnNext();
         }

         return var2;
      }
   }

   public JsonParser skipChildren() throws IOException {
      if (this.delegate.currentToken() != JsonToken.START_OBJECT && this.delegate.currentToken() != JsonToken.START_ARRAY) {
         return this;
      } else {
         int var1 = 1;

         while(true) {
            JsonToken var2 = this.nextToken();
            if (var2 == null) {
               return this;
            }

            if (var2.isStructStart()) {
               ++var1;
            } else if (var2.isStructEnd()) {
               int var3 = var1 - 1;
               var1 = var3;
               if (var3 == 0) {
                  return this;
               }
            }
         }
      }
   }

   protected JsonToken switchAndReturnNext() throws IOException {
      while(true) {
         int var1 = this._nextParserIndex;
         JsonParser[] var2 = this._parsers;
         if (var1 < var2.length) {
            this._nextParserIndex = var1 + 1;
            this.delegate = var2[var1];
            if (this._checkForExistingToken && this.delegate.hasCurrentToken()) {
               return this.delegate.getCurrentToken();
            }

            JsonToken var3 = this.delegate.nextToken();
            if (var3 == null) {
               continue;
            }

            return var3;
         }

         return null;
      }
   }

   protected boolean switchToNext() {
      int var1 = this._nextParserIndex;
      JsonParser[] var2 = this._parsers;
      if (var1 < var2.length) {
         this._nextParserIndex = var1 + 1;
         this.delegate = var2[var1];
         return true;
      } else {
         return false;
      }
   }
}

package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import java.io.IOException;

public class TokenFilterContext extends JsonStreamContext {
   protected TokenFilterContext _child;
   protected String _currentName;
   protected TokenFilter _filter;
   protected boolean _needToHandleName;
   protected final TokenFilterContext _parent;
   protected boolean _startHandled;

   protected TokenFilterContext(int var1, TokenFilterContext var2, TokenFilter var3, boolean var4) {
      this._type = var1;
      this._parent = var2;
      this._filter = var3;
      this._index = -1;
      this._startHandled = var4;
      this._needToHandleName = false;
   }

   private void _writePath(JsonGenerator var1) throws IOException {
      TokenFilter var2 = this._filter;
      if (var2 != null && var2 != TokenFilter.INCLUDE_ALL) {
         TokenFilterContext var3 = this._parent;
         if (var3 != null) {
            var3._writePath(var1);
         }

         if (this._startHandled) {
            if (this._needToHandleName) {
               this._needToHandleName = false;
               var1.writeFieldName(this._currentName);
            }
         } else {
            this._startHandled = true;
            if (this._type == 2) {
               var1.writeStartObject();
               if (this._needToHandleName) {
                  this._needToHandleName = false;
                  var1.writeFieldName(this._currentName);
               }
            } else if (this._type == 1) {
               var1.writeStartArray();
            }
         }
      }

   }

   public static TokenFilterContext createRootContext(TokenFilter var0) {
      return new TokenFilterContext(0, (TokenFilterContext)null, var0, true);
   }

   protected void appendDesc(StringBuilder var1) {
      TokenFilterContext var2 = this._parent;
      if (var2 != null) {
         var2.appendDesc(var1);
      }

      if (this._type == 2) {
         var1.append('{');
         if (this._currentName != null) {
            var1.append('"');
            var1.append(this._currentName);
            var1.append('"');
         } else {
            var1.append('?');
         }

         var1.append('}');
      } else if (this._type == 1) {
         var1.append('[');
         var1.append(this.getCurrentIndex());
         var1.append(']');
      } else {
         var1.append("/");
      }

   }

   public TokenFilter checkValue(TokenFilter var1) {
      if (this._type == 2) {
         return var1;
      } else {
         int var2 = this._index + 1;
         this._index = var2;
         return this._type == 1 ? var1.includeElement(var2) : var1.includeRootValue(var2);
      }
   }

   public TokenFilterContext closeArray(JsonGenerator var1) throws IOException {
      if (this._startHandled) {
         var1.writeEndArray();
      }

      TokenFilter var2 = this._filter;
      if (var2 != null && var2 != TokenFilter.INCLUDE_ALL) {
         this._filter.filterFinishArray();
      }

      return this._parent;
   }

   public TokenFilterContext closeObject(JsonGenerator var1) throws IOException {
      if (this._startHandled) {
         var1.writeEndObject();
      }

      TokenFilter var2 = this._filter;
      if (var2 != null && var2 != TokenFilter.INCLUDE_ALL) {
         this._filter.filterFinishObject();
      }

      return this._parent;
   }

   public TokenFilterContext createChildArrayContext(TokenFilter var1, boolean var2) {
      TokenFilterContext var3 = this._child;
      if (var3 == null) {
         TokenFilterContext var4 = new TokenFilterContext(1, this, var1, var2);
         this._child = var4;
         return var4;
      } else {
         return var3.reset(1, var1, var2);
      }
   }

   public TokenFilterContext createChildObjectContext(TokenFilter var1, boolean var2) {
      TokenFilterContext var3 = this._child;
      if (var3 == null) {
         TokenFilterContext var4 = new TokenFilterContext(2, this, var1, var2);
         this._child = var4;
         return var4;
      } else {
         return var3.reset(2, var1, var2);
      }
   }

   public TokenFilterContext findChildOf(TokenFilterContext var1) {
      TokenFilterContext var2 = this._parent;
      TokenFilterContext var3 = var2;
      if (var2 == var1) {
         return this;
      } else {
         while(var3 != null) {
            var2 = var3._parent;
            if (var2 == var1) {
               return var3;
            }

            var3 = var2;
         }

         return null;
      }
   }

   public final String getCurrentName() {
      return this._currentName;
   }

   public Object getCurrentValue() {
      return null;
   }

   public TokenFilter getFilter() {
      return this._filter;
   }

   public final TokenFilterContext getParent() {
      return this._parent;
   }

   public boolean hasCurrentName() {
      boolean var1;
      if (this._currentName != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isStartHandled() {
      return this._startHandled;
   }

   public JsonToken nextTokenToRead() {
      if (!this._startHandled) {
         this._startHandled = true;
         return this._type == 2 ? JsonToken.START_OBJECT : JsonToken.START_ARRAY;
      } else if (this._needToHandleName && this._type == 2) {
         this._needToHandleName = false;
         return JsonToken.FIELD_NAME;
      } else {
         return null;
      }
   }

   protected TokenFilterContext reset(int var1, TokenFilter var2, boolean var3) {
      this._type = var1;
      this._filter = var2;
      this._index = -1;
      this._currentName = null;
      this._startHandled = var3;
      this._needToHandleName = false;
      return this;
   }

   public void setCurrentValue(Object var1) {
   }

   public TokenFilter setFieldName(String var1) throws JsonProcessingException {
      this._currentName = var1;
      this._needToHandleName = true;
      return this._filter;
   }

   public void skipParentChecks() {
      this._filter = null;

      for(TokenFilterContext var1 = this._parent; var1 != null; var1 = var1._parent) {
         this._parent._filter = null;
      }

   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(64);
      this.appendDesc(var1);
      return var1.toString();
   }

   public void writeImmediatePath(JsonGenerator var1) throws IOException {
      TokenFilter var2 = this._filter;
      if (var2 != null && var2 != TokenFilter.INCLUDE_ALL) {
         if (this._startHandled) {
            if (this._needToHandleName) {
               var1.writeFieldName(this._currentName);
            }
         } else {
            this._startHandled = true;
            if (this._type == 2) {
               var1.writeStartObject();
               if (this._needToHandleName) {
                  var1.writeFieldName(this._currentName);
               }
            } else if (this._type == 1) {
               var1.writeStartArray();
            }
         }
      }

   }

   public void writePath(JsonGenerator var1) throws IOException {
      TokenFilter var2 = this._filter;
      if (var2 != null && var2 != TokenFilter.INCLUDE_ALL) {
         TokenFilterContext var3 = this._parent;
         if (var3 != null) {
            var3._writePath(var1);
         }

         if (this._startHandled) {
            if (this._needToHandleName) {
               var1.writeFieldName(this._currentName);
            }
         } else {
            this._startHandled = true;
            if (this._type == 2) {
               var1.writeStartObject();
               var1.writeFieldName(this._currentName);
            } else if (this._type == 1) {
               var1.writeStartArray();
            }
         }
      }

   }
}

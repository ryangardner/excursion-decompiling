package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;

public class JsonWriteContext extends JsonStreamContext {
   public static final int STATUS_EXPECT_NAME = 5;
   public static final int STATUS_EXPECT_VALUE = 4;
   public static final int STATUS_OK_AFTER_COLON = 2;
   public static final int STATUS_OK_AFTER_COMMA = 1;
   public static final int STATUS_OK_AFTER_SPACE = 3;
   public static final int STATUS_OK_AS_IS = 0;
   protected JsonWriteContext _child;
   protected String _currentName;
   protected Object _currentValue;
   protected DupDetector _dups;
   protected boolean _gotName;
   protected final JsonWriteContext _parent;

   protected JsonWriteContext(int var1, JsonWriteContext var2, DupDetector var3) {
      this._type = var1;
      this._parent = var2;
      this._dups = var3;
      this._index = -1;
   }

   protected JsonWriteContext(int var1, JsonWriteContext var2, DupDetector var3, Object var4) {
      this._type = var1;
      this._parent = var2;
      this._dups = var3;
      this._index = -1;
      this._currentValue = var4;
   }

   private final void _checkDup(DupDetector var1, String var2) throws JsonProcessingException {
      if (var1.isDup(var2)) {
         Object var4 = var1.getSource();
         StringBuilder var3 = new StringBuilder();
         var3.append("Duplicate field '");
         var3.append(var2);
         var3.append("'");
         var2 = var3.toString();
         JsonGenerator var5;
         if (var4 instanceof JsonGenerator) {
            var5 = (JsonGenerator)var4;
         } else {
            var5 = null;
         }

         throw new JsonGenerationException(var2, var5);
      }
   }

   @Deprecated
   public static JsonWriteContext createRootContext() {
      return createRootContext((DupDetector)null);
   }

   public static JsonWriteContext createRootContext(DupDetector var0) {
      return new JsonWriteContext(0, (JsonWriteContext)null, var0);
   }

   public JsonWriteContext clearAndGetParent() {
      this._currentValue = null;
      return this._parent;
   }

   public JsonWriteContext createChildArrayContext() {
      JsonWriteContext var1 = this._child;
      if (var1 == null) {
         DupDetector var2 = this._dups;
         if (var2 == null) {
            var2 = null;
         } else {
            var2 = var2.child();
         }

         var1 = new JsonWriteContext(1, this, var2);
         this._child = var1;
         return var1;
      } else {
         return var1.reset(1);
      }
   }

   public JsonWriteContext createChildArrayContext(Object var1) {
      JsonWriteContext var2 = this._child;
      if (var2 == null) {
         DupDetector var4 = this._dups;
         if (var4 == null) {
            var4 = null;
         } else {
            var4 = var4.child();
         }

         JsonWriteContext var3 = new JsonWriteContext(1, this, var4, var1);
         this._child = var3;
         return var3;
      } else {
         return var2.reset(1, var1);
      }
   }

   public JsonWriteContext createChildObjectContext() {
      JsonWriteContext var1 = this._child;
      if (var1 == null) {
         DupDetector var2 = this._dups;
         if (var2 == null) {
            var2 = null;
         } else {
            var2 = var2.child();
         }

         var1 = new JsonWriteContext(2, this, var2);
         this._child = var1;
         return var1;
      } else {
         return var1.reset(2);
      }
   }

   public JsonWriteContext createChildObjectContext(Object var1) {
      JsonWriteContext var2 = this._child;
      if (var2 == null) {
         DupDetector var4 = this._dups;
         if (var4 == null) {
            var4 = null;
         } else {
            var4 = var4.child();
         }

         JsonWriteContext var3 = new JsonWriteContext(2, this, var4, var1);
         this._child = var3;
         return var3;
      } else {
         return var2.reset(2, var1);
      }
   }

   public final String getCurrentName() {
      return this._currentName;
   }

   public Object getCurrentValue() {
      return this._currentValue;
   }

   public DupDetector getDupDetector() {
      return this._dups;
   }

   public final JsonWriteContext getParent() {
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

   protected JsonWriteContext reset(int var1) {
      this._type = var1;
      this._index = -1;
      this._currentName = null;
      this._gotName = false;
      this._currentValue = null;
      DupDetector var2 = this._dups;
      if (var2 != null) {
         var2.reset();
      }

      return this;
   }

   protected JsonWriteContext reset(int var1, Object var2) {
      this._type = var1;
      this._index = -1;
      this._currentName = null;
      this._gotName = false;
      this._currentValue = var2;
      DupDetector var3 = this._dups;
      if (var3 != null) {
         var3.reset();
      }

      return this;
   }

   public void setCurrentValue(Object var1) {
      this._currentValue = var1;
   }

   public JsonWriteContext withDupDetector(DupDetector var1) {
      this._dups = var1;
      return this;
   }

   public int writeFieldName(String var1) throws JsonProcessingException {
      if (this._type == 2 && !this._gotName) {
         byte var2 = 1;
         this._gotName = true;
         this._currentName = var1;
         DupDetector var3 = this._dups;
         if (var3 != null) {
            this._checkDup(var3, var1);
         }

         if (this._index < 0) {
            var2 = 0;
         }

         return var2;
      } else {
         return 4;
      }
   }

   public int writeValue() {
      int var1 = this._type;
      byte var2 = 0;
      byte var3 = 0;
      if (var1 == 2) {
         if (!this._gotName) {
            return 5;
         } else {
            this._gotName = false;
            ++this._index;
            return 2;
         }
      } else if (this._type == 1) {
         int var4 = this._index++;
         if (var4 >= 0) {
            var3 = 1;
         }

         return var3;
      } else {
         ++this._index;
         if (this._index == 0) {
            var3 = var2;
         } else {
            var3 = 3;
         }

         return var3;
      }
   }
}

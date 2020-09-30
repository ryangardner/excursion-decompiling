package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.JsonStreamContext;

public final class JsonReadContext extends JsonStreamContext {
   protected JsonReadContext _child;
   protected int _columnNr;
   protected String _currentName;
   protected Object _currentValue;
   protected DupDetector _dups;
   protected int _lineNr;
   protected final JsonReadContext _parent;

   public JsonReadContext(JsonReadContext var1, DupDetector var2, int var3, int var4, int var5) {
      this._parent = var1;
      this._dups = var2;
      this._type = var3;
      this._lineNr = var4;
      this._columnNr = var5;
      this._index = -1;
   }

   private void _checkDup(DupDetector var1, String var2) throws JsonProcessingException {
      if (var1.isDup(var2)) {
         Object var4 = var1.getSource();
         JsonParser var5;
         if (var4 instanceof JsonParser) {
            var5 = (JsonParser)var4;
         } else {
            var5 = null;
         }

         StringBuilder var3 = new StringBuilder();
         var3.append("Duplicate field '");
         var3.append(var2);
         var3.append("'");
         throw new JsonParseException(var5, var3.toString());
      }
   }

   public static JsonReadContext createRootContext(int var0, int var1, DupDetector var2) {
      return new JsonReadContext((JsonReadContext)null, var2, 0, var0, var1);
   }

   public static JsonReadContext createRootContext(DupDetector var0) {
      return new JsonReadContext((JsonReadContext)null, var0, 0, 1, 0);
   }

   public JsonReadContext clearAndGetParent() {
      this._currentValue = null;
      return this._parent;
   }

   public JsonReadContext createChildArrayContext(int var1, int var2) {
      JsonReadContext var3 = this._child;
      if (var3 == null) {
         DupDetector var4 = this._dups;
         if (var4 == null) {
            var4 = null;
         } else {
            var4 = var4.child();
         }

         var3 = new JsonReadContext(this, var4, 1, var1, var2);
         this._child = var3;
      } else {
         var3.reset(1, var1, var2);
      }

      return var3;
   }

   public JsonReadContext createChildObjectContext(int var1, int var2) {
      JsonReadContext var3 = this._child;
      if (var3 == null) {
         DupDetector var4 = this._dups;
         if (var4 == null) {
            var4 = null;
         } else {
            var4 = var4.child();
         }

         var3 = new JsonReadContext(this, var4, 2, var1, var2);
         this._child = var3;
         return var3;
      } else {
         var3.reset(2, var1, var2);
         return var3;
      }
   }

   public boolean expectComma() {
      int var1 = this._index;
      boolean var2 = true;
      ++var1;
      this._index = var1;
      if (this._type == 0 || var1 <= 0) {
         var2 = false;
      }

      return var2;
   }

   public String getCurrentName() {
      return this._currentName;
   }

   public Object getCurrentValue() {
      return this._currentValue;
   }

   public DupDetector getDupDetector() {
      return this._dups;
   }

   public JsonReadContext getParent() {
      return this._parent;
   }

   public JsonLocation getStartLocation(Object var1) {
      return new JsonLocation(var1, -1L, this._lineNr, this._columnNr);
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

   protected void reset(int var1, int var2, int var3) {
      this._type = var1;
      this._index = -1;
      this._lineNr = var2;
      this._columnNr = var3;
      this._currentName = null;
      this._currentValue = null;
      DupDetector var4 = this._dups;
      if (var4 != null) {
         var4.reset();
      }

   }

   public void setCurrentName(String var1) throws JsonProcessingException {
      this._currentName = var1;
      DupDetector var2 = this._dups;
      if (var2 != null) {
         this._checkDup(var2, var1);
      }

   }

   public void setCurrentValue(Object var1) {
      this._currentValue = var1;
   }

   public JsonReadContext withDupDetector(DupDetector var1) {
      this._dups = var1;
      return this;
   }
}

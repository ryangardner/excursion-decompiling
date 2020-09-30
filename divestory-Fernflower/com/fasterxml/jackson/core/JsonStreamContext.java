package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.io.CharTypes;

public abstract class JsonStreamContext {
   protected static final int TYPE_ARRAY = 1;
   protected static final int TYPE_OBJECT = 2;
   protected static final int TYPE_ROOT = 0;
   protected int _index;
   protected int _type;

   protected JsonStreamContext() {
   }

   protected JsonStreamContext(int var1, int var2) {
      this._type = var1;
      this._index = var2;
   }

   protected JsonStreamContext(JsonStreamContext var1) {
      this._type = var1._type;
      this._index = var1._index;
   }

   public final int getCurrentIndex() {
      int var1 = this._index;
      int var2 = var1;
      if (var1 < 0) {
         var2 = 0;
      }

      return var2;
   }

   public abstract String getCurrentName();

   public Object getCurrentValue() {
      return null;
   }

   public final int getEntryCount() {
      return this._index + 1;
   }

   public abstract JsonStreamContext getParent();

   public JsonLocation getStartLocation(Object var1) {
      return JsonLocation.NA;
   }

   @Deprecated
   public final String getTypeDesc() {
      int var1 = this._type;
      if (var1 != 0) {
         if (var1 != 1) {
            return var1 != 2 ? "?" : "OBJECT";
         } else {
            return "ARRAY";
         }
      } else {
         return "ROOT";
      }
   }

   public boolean hasCurrentIndex() {
      boolean var1;
      if (this._index >= 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasCurrentName() {
      boolean var1;
      if (this.getCurrentName() != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasPathSegment() {
      int var1 = this._type;
      if (var1 == 2) {
         return this.hasCurrentName();
      } else {
         return var1 == 1 ? this.hasCurrentIndex() : false;
      }
   }

   public final boolean inArray() {
      int var1 = this._type;
      boolean var2 = true;
      if (var1 != 1) {
         var2 = false;
      }

      return var2;
   }

   public final boolean inObject() {
      boolean var1;
      if (this._type == 2) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public final boolean inRoot() {
      boolean var1;
      if (this._type == 0) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public JsonPointer pathAsPointer() {
      return JsonPointer.forPath(this, false);
   }

   public JsonPointer pathAsPointer(boolean var1) {
      return JsonPointer.forPath(this, var1);
   }

   public void setCurrentValue(Object var1) {
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder(64);
      int var2 = this._type;
      if (var2 != 0) {
         if (var2 != 1) {
            var1.append('{');
            String var3 = this.getCurrentName();
            if (var3 != null) {
               var1.append('"');
               CharTypes.appendQuoted(var1, var3);
               var1.append('"');
            } else {
               var1.append('?');
            }

            var1.append('}');
         } else {
            var1.append('[');
            var1.append(this.getCurrentIndex());
            var1.append(']');
         }
      } else {
         var1.append("/");
      }

      return var1.toString();
   }

   public String typeDesc() {
      int var1 = this._type;
      if (var1 != 0) {
         if (var1 != 1) {
            return var1 != 2 ? "?" : "Object";
         } else {
            return "Array";
         }
      } else {
         return "root";
      }
   }
}

package com.fasterxml.jackson.core.json;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import java.util.HashSet;

public class DupDetector {
   protected String _firstName;
   protected String _secondName;
   protected HashSet<String> _seen;
   protected final Object _source;

   private DupDetector(Object var1) {
      this._source = var1;
   }

   public static DupDetector rootDetector(JsonGenerator var0) {
      return new DupDetector(var0);
   }

   public static DupDetector rootDetector(JsonParser var0) {
      return new DupDetector(var0);
   }

   public DupDetector child() {
      return new DupDetector(this._source);
   }

   public JsonLocation findLocation() {
      Object var1 = this._source;
      return var1 instanceof JsonParser ? ((JsonParser)var1).getCurrentLocation() : null;
   }

   public Object getSource() {
      return this._source;
   }

   public boolean isDup(String var1) throws JsonParseException {
      String var2 = this._firstName;
      if (var2 == null) {
         this._firstName = var1;
         return false;
      } else if (var1.equals(var2)) {
         return true;
      } else {
         var2 = this._secondName;
         if (var2 == null) {
            this._secondName = var1;
            return false;
         } else if (var1.equals(var2)) {
            return true;
         } else {
            if (this._seen == null) {
               HashSet var3 = new HashSet(16);
               this._seen = var3;
               var3.add(this._firstName);
               this._seen.add(this._secondName);
            }

            return this._seen.add(var1) ^ true;
         }
      }
   }

   public void reset() {
      this._firstName = null;
      this._secondName = null;
      this._seen = null;
   }
}

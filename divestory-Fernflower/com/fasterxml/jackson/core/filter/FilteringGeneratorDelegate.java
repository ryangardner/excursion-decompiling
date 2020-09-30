package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.SerializableString;
import com.fasterxml.jackson.core.util.JsonGeneratorDelegate;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class FilteringGeneratorDelegate extends JsonGeneratorDelegate {
   protected boolean _allowMultipleMatches;
   protected TokenFilterContext _filterContext;
   @Deprecated
   protected boolean _includeImmediateParent;
   protected boolean _includePath;
   protected TokenFilter _itemFilter;
   protected int _matchCount;
   protected TokenFilter rootFilter;

   public FilteringGeneratorDelegate(JsonGenerator var1, TokenFilter var2, boolean var3, boolean var4) {
      super(var1, false);
      this.rootFilter = var2;
      this._itemFilter = var2;
      this._filterContext = TokenFilterContext.createRootContext(var2);
      this._includePath = var3;
      this._allowMultipleMatches = var4;
   }

   protected boolean _checkBinaryWrite() throws IOException {
      TokenFilter var1 = this._itemFilter;
      if (var1 == null) {
         return false;
      } else if (var1 == TokenFilter.INCLUDE_ALL) {
         return true;
      } else if (this._itemFilter.includeBinary()) {
         this._checkParentPath();
         return true;
      } else {
         return false;
      }
   }

   protected void _checkParentPath() throws IOException {
      ++this._matchCount;
      if (this._includePath) {
         this._filterContext.writePath(this.delegate);
      }

      if (!this._allowMultipleMatches) {
         this._filterContext.skipParentChecks();
      }

   }

   protected void _checkPropertyParentPath() throws IOException {
      ++this._matchCount;
      if (this._includePath) {
         this._filterContext.writePath(this.delegate);
      } else if (this._includeImmediateParent) {
         this._filterContext.writeImmediatePath(this.delegate);
      }

      if (!this._allowMultipleMatches) {
         this._filterContext.skipParentChecks();
      }

   }

   protected boolean _checkRawValueWrite() throws IOException {
      TokenFilter var1 = this._itemFilter;
      if (var1 == null) {
         return false;
      } else if (var1 == TokenFilter.INCLUDE_ALL) {
         return true;
      } else if (this._itemFilter.includeRawValue()) {
         this._checkParentPath();
         return true;
      } else {
         return false;
      }
   }

   public TokenFilter getFilter() {
      return this.rootFilter;
   }

   public JsonStreamContext getFilterContext() {
      return this._filterContext;
   }

   public int getMatchCount() {
      return this._matchCount;
   }

   public JsonStreamContext getOutputContext() {
      return this._filterContext;
   }

   public int writeBinary(Base64Variant var1, InputStream var2, int var3) throws IOException {
      return this._checkBinaryWrite() ? this.delegate.writeBinary(var1, var2, var3) : -1;
   }

   public void writeBinary(Base64Variant var1, byte[] var2, int var3, int var4) throws IOException {
      if (this._checkBinaryWrite()) {
         this.delegate.writeBinary(var1, var2, var3, var4);
      }

   }

   public void writeBoolean(boolean var1) throws IOException {
      TokenFilter var2 = this._itemFilter;
      if (var2 != null) {
         if (var2 != TokenFilter.INCLUDE_ALL) {
            var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeBoolean(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeBoolean(var1);
      }
   }

   public void writeEndArray() throws IOException {
      TokenFilterContext var1 = this._filterContext.closeArray(this.delegate);
      this._filterContext = var1;
      if (var1 != null) {
         this._itemFilter = var1.getFilter();
      }

   }

   public void writeEndObject() throws IOException {
      TokenFilterContext var1 = this._filterContext.closeObject(this.delegate);
      this._filterContext = var1;
      if (var1 != null) {
         this._itemFilter = var1.getFilter();
      }

   }

   public void writeFieldId(long var1) throws IOException {
      this.writeFieldName(Long.toString(var1));
   }

   public void writeFieldName(SerializableString var1) throws IOException {
      TokenFilter var2 = this._filterContext.setFieldName(var1.getValue());
      if (var2 == null) {
         this._itemFilter = null;
      } else if (var2 == TokenFilter.INCLUDE_ALL) {
         this._itemFilter = var2;
         this.delegate.writeFieldName(var1);
      } else {
         TokenFilter var3 = var2.includeProperty(var1.getValue());
         this._itemFilter = var3;
         if (var3 == TokenFilter.INCLUDE_ALL) {
            this._checkPropertyParentPath();
         }

      }
   }

   public void writeFieldName(String var1) throws IOException {
      TokenFilter var2 = this._filterContext.setFieldName(var1);
      if (var2 == null) {
         this._itemFilter = null;
      } else if (var2 == TokenFilter.INCLUDE_ALL) {
         this._itemFilter = var2;
         this.delegate.writeFieldName(var1);
      } else {
         TokenFilter var3 = var2.includeProperty(var1);
         this._itemFilter = var3;
         if (var3 == TokenFilter.INCLUDE_ALL) {
            this._checkPropertyParentPath();
         }

      }
   }

   public void writeNull() throws IOException {
      TokenFilter var1 = this._itemFilter;
      if (var1 != null) {
         if (var1 != TokenFilter.INCLUDE_ALL) {
            var1 = this._filterContext.checkValue(this._itemFilter);
            if (var1 == null) {
               return;
            }

            if (var1 != TokenFilter.INCLUDE_ALL && !var1.includeNull()) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNull();
      }
   }

   public void writeNumber(double var1) throws IOException {
      TokenFilter var3 = this._itemFilter;
      if (var3 != null) {
         if (var3 != TokenFilter.INCLUDE_ALL) {
            var3 = this._filterContext.checkValue(this._itemFilter);
            if (var3 == null) {
               return;
            }

            if (var3 != TokenFilter.INCLUDE_ALL && !var3.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(float var1) throws IOException {
      TokenFilter var2 = this._itemFilter;
      if (var2 != null) {
         if (var2 != TokenFilter.INCLUDE_ALL) {
            var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(int var1) throws IOException {
      TokenFilter var2 = this._itemFilter;
      if (var2 != null) {
         if (var2 != TokenFilter.INCLUDE_ALL) {
            var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(long var1) throws IOException {
      TokenFilter var3 = this._itemFilter;
      if (var3 != null) {
         if (var3 != TokenFilter.INCLUDE_ALL) {
            var3 = this._filterContext.checkValue(this._itemFilter);
            if (var3 == null) {
               return;
            }

            if (var3 != TokenFilter.INCLUDE_ALL && !var3.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(String var1) throws IOException, UnsupportedOperationException {
      TokenFilter var2 = this._itemFilter;
      if (var2 != null) {
         if (var2 != TokenFilter.INCLUDE_ALL) {
            var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeRawValue()) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(BigDecimal var1) throws IOException {
      TokenFilter var2 = this._itemFilter;
      if (var2 != null) {
         if (var2 != TokenFilter.INCLUDE_ALL) {
            var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(BigInteger var1) throws IOException {
      TokenFilter var2 = this._itemFilter;
      if (var2 != null) {
         if (var2 != TokenFilter.INCLUDE_ALL) {
            var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeNumber(short var1) throws IOException {
      TokenFilter var2 = this._itemFilter;
      if (var2 != null) {
         if (var2 != TokenFilter.INCLUDE_ALL) {
            var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeNumber(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeNumber(var1);
      }
   }

   public void writeObjectId(Object var1) throws IOException {
      if (this._itemFilter != null) {
         this.delegate.writeObjectId(var1);
      }

   }

   public void writeObjectRef(Object var1) throws IOException {
      if (this._itemFilter != null) {
         this.delegate.writeObjectRef(var1);
      }

   }

   public void writeOmittedField(String var1) throws IOException {
      if (this._itemFilter != null) {
         this.delegate.writeOmittedField(var1);
      }

   }

   public void writeRaw(char var1) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1);
      }

   }

   public void writeRaw(SerializableString var1) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1);
      }

   }

   public void writeRaw(String var1) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1);
      }

   }

   public void writeRaw(String var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1, var2, var3);
      }

   }

   public void writeRaw(char[] var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRaw(var1, var2, var3);
      }

   }

   public void writeRawUTF8String(byte[] var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRawUTF8String(var1, var2, var3);
      }

   }

   public void writeRawValue(String var1) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRawValue(var1);
      }

   }

   public void writeRawValue(String var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRawValue(var1, var2, var3);
      }

   }

   public void writeRawValue(char[] var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeRawValue(var1, var2, var3);
      }

   }

   public void writeStartArray() throws IOException {
      TokenFilter var1 = this._itemFilter;
      if (var1 == null) {
         this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
      } else if (var1 == TokenFilter.INCLUDE_ALL) {
         this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
         this.delegate.writeStartArray();
      } else {
         var1 = this._filterContext.checkValue(this._itemFilter);
         this._itemFilter = var1;
         if (var1 == null) {
            this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
         } else {
            if (var1 != TokenFilter.INCLUDE_ALL) {
               this._itemFilter = this._itemFilter.filterStartArray();
            }

            if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
               this._checkParentPath();
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
               this.delegate.writeStartArray();
            } else {
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
            }

         }
      }
   }

   public void writeStartArray(int var1) throws IOException {
      TokenFilter var2 = this._itemFilter;
      if (var2 == null) {
         this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
      } else if (var2 == TokenFilter.INCLUDE_ALL) {
         this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
         this.delegate.writeStartArray(var1);
      } else {
         var2 = this._filterContext.checkValue(this._itemFilter);
         this._itemFilter = var2;
         if (var2 == null) {
            this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
         } else {
            if (var2 != TokenFilter.INCLUDE_ALL) {
               this._itemFilter = this._itemFilter.filterStartArray();
            }

            if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
               this._checkParentPath();
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
               this.delegate.writeStartArray(var1);
            } else {
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
            }

         }
      }
   }

   public void writeStartArray(Object var1) throws IOException {
      TokenFilter var2 = this._itemFilter;
      if (var2 == null) {
         this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
      } else if (var2 == TokenFilter.INCLUDE_ALL) {
         this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
         this.delegate.writeStartArray(var1);
      } else {
         var2 = this._filterContext.checkValue(this._itemFilter);
         this._itemFilter = var2;
         if (var2 == null) {
            this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
         } else {
            if (var2 != TokenFilter.INCLUDE_ALL) {
               this._itemFilter = this._itemFilter.filterStartArray();
            }

            if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
               this._checkParentPath();
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
               this.delegate.writeStartArray(var1);
            } else {
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
            }

         }
      }
   }

   public void writeStartArray(Object var1, int var2) throws IOException {
      TokenFilter var3 = this._itemFilter;
      if (var3 == null) {
         this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
      } else if (var3 == TokenFilter.INCLUDE_ALL) {
         this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
         this.delegate.writeStartArray(var1, var2);
      } else {
         var3 = this._filterContext.checkValue(this._itemFilter);
         this._itemFilter = var3;
         if (var3 == null) {
            this._filterContext = this._filterContext.createChildArrayContext((TokenFilter)null, false);
         } else {
            if (var3 != TokenFilter.INCLUDE_ALL) {
               this._itemFilter = this._itemFilter.filterStartArray();
            }

            if (this._itemFilter == TokenFilter.INCLUDE_ALL) {
               this._checkParentPath();
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, true);
               this.delegate.writeStartArray(var1, var2);
            } else {
               this._filterContext = this._filterContext.createChildArrayContext(this._itemFilter, false);
            }

         }
      }
   }

   public void writeStartObject() throws IOException {
      TokenFilter var1 = this._itemFilter;
      if (var1 == null) {
         this._filterContext = this._filterContext.createChildObjectContext(var1, false);
      } else if (var1 == TokenFilter.INCLUDE_ALL) {
         this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
         this.delegate.writeStartObject();
      } else {
         TokenFilter var2 = this._filterContext.checkValue(this._itemFilter);
         if (var2 != null) {
            var1 = var2;
            if (var2 != TokenFilter.INCLUDE_ALL) {
               var1 = var2.filterStartObject();
            }

            if (var1 == TokenFilter.INCLUDE_ALL) {
               this._checkParentPath();
               this._filterContext = this._filterContext.createChildObjectContext(var1, true);
               this.delegate.writeStartObject();
            } else {
               this._filterContext = this._filterContext.createChildObjectContext(var1, false);
            }

         }
      }
   }

   public void writeStartObject(Object var1) throws IOException {
      TokenFilter var2 = this._itemFilter;
      if (var2 == null) {
         this._filterContext = this._filterContext.createChildObjectContext(var2, false);
      } else if (var2 == TokenFilter.INCLUDE_ALL) {
         this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
         this.delegate.writeStartObject(var1);
      } else {
         TokenFilter var3 = this._filterContext.checkValue(this._itemFilter);
         if (var3 != null) {
            var2 = var3;
            if (var3 != TokenFilter.INCLUDE_ALL) {
               var2 = var3.filterStartObject();
            }

            if (var2 == TokenFilter.INCLUDE_ALL) {
               this._checkParentPath();
               this._filterContext = this._filterContext.createChildObjectContext(var2, true);
               this.delegate.writeStartObject(var1);
            } else {
               this._filterContext = this._filterContext.createChildObjectContext(var2, false);
            }

         }
      }
   }

   public void writeStartObject(Object var1, int var2) throws IOException {
      TokenFilter var3 = this._itemFilter;
      if (var3 == null) {
         this._filterContext = this._filterContext.createChildObjectContext(var3, false);
      } else if (var3 == TokenFilter.INCLUDE_ALL) {
         this._filterContext = this._filterContext.createChildObjectContext(this._itemFilter, true);
         this.delegate.writeStartObject(var1, var2);
      } else {
         TokenFilter var4 = this._filterContext.checkValue(this._itemFilter);
         if (var4 != null) {
            var3 = var4;
            if (var4 != TokenFilter.INCLUDE_ALL) {
               var3 = var4.filterStartObject();
            }

            if (var3 == TokenFilter.INCLUDE_ALL) {
               this._checkParentPath();
               this._filterContext = this._filterContext.createChildObjectContext(var3, true);
               this.delegate.writeStartObject(var1, var2);
            } else {
               this._filterContext = this._filterContext.createChildObjectContext(var3, false);
            }

         }
      }
   }

   public void writeString(SerializableString var1) throws IOException {
      TokenFilter var2 = this._itemFilter;
      if (var2 != null) {
         if (var2 != TokenFilter.INCLUDE_ALL) {
            var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeString(var1.getValue())) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeString(var1);
      }
   }

   public void writeString(String var1) throws IOException {
      TokenFilter var2 = this._itemFilter;
      if (var2 != null) {
         if (var2 != TokenFilter.INCLUDE_ALL) {
            var2 = this._filterContext.checkValue(this._itemFilter);
            if (var2 == null) {
               return;
            }

            if (var2 != TokenFilter.INCLUDE_ALL && !var2.includeString(var1)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeString(var1);
      }
   }

   public void writeString(char[] var1, int var2, int var3) throws IOException {
      TokenFilter var4 = this._itemFilter;
      if (var4 != null) {
         if (var4 != TokenFilter.INCLUDE_ALL) {
            String var5 = new String(var1, var2, var3);
            var4 = this._filterContext.checkValue(this._itemFilter);
            if (var4 == null) {
               return;
            }

            if (var4 != TokenFilter.INCLUDE_ALL && !var4.includeString(var5)) {
               return;
            }

            this._checkParentPath();
         }

         this.delegate.writeString(var1, var2, var3);
      }
   }

   public void writeTypeId(Object var1) throws IOException {
      if (this._itemFilter != null) {
         this.delegate.writeTypeId(var1);
      }

   }

   public void writeUTF8String(byte[] var1, int var2, int var3) throws IOException {
      if (this._checkRawValueWrite()) {
         this.delegate.writeUTF8String(var1, var2, var3);
      }

   }
}

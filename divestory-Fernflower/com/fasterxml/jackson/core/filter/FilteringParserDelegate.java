package com.fasterxml.jackson.core.filter;

import com.fasterxml.jackson.core.Base64Variant;
import com.fasterxml.jackson.core.JsonLocation;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.JsonParserDelegate;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.math.BigInteger;

public class FilteringParserDelegate extends JsonParserDelegate {
   protected boolean _allowMultipleMatches;
   protected JsonToken _currToken;
   protected TokenFilterContext _exposedContext;
   protected TokenFilterContext _headContext;
   @Deprecated
   protected boolean _includeImmediateParent;
   protected boolean _includePath;
   protected TokenFilter _itemFilter;
   protected JsonToken _lastClearedToken;
   protected int _matchCount;
   protected TokenFilter rootFilter;

   public FilteringParserDelegate(JsonParser var1, TokenFilter var2, boolean var3, boolean var4) {
      super(var1);
      this.rootFilter = var2;
      this._itemFilter = var2;
      this._headContext = TokenFilterContext.createRootContext(var2);
      this._includePath = var3;
      this._allowMultipleMatches = var4;
   }

   private JsonToken _nextBuffered(TokenFilterContext var1) throws IOException {
      this._exposedContext = var1;
      JsonToken var2 = var1.nextTokenToRead();
      if (var2 != null) {
         return var2;
      } else {
         do {
            if (var1 == this._headContext) {
               throw this._constructError("Internal error: failed to locate expected buffered tokens");
            }

            var1 = this._exposedContext.findChildOf(var1);
            this._exposedContext = var1;
            if (var1 == null) {
               throw this._constructError("Unexpected problem: chain of filtered context broken");
            }

            var2 = var1.nextTokenToRead();
         } while(var2 == null);

         return var2;
      }
   }

   private final boolean _verifyAllowedMatches() throws IOException {
      if (this._matchCount != 0 && !this._allowMultipleMatches) {
         return false;
      } else {
         ++this._matchCount;
         return true;
      }
   }

   protected JsonStreamContext _filterContext() {
      TokenFilterContext var1 = this._exposedContext;
      return var1 != null ? var1 : this._headContext;
   }

   protected final JsonToken _nextToken2() throws IOException {
      while(true) {
         JsonToken var1 = this.delegate.nextToken();
         if (var1 == null) {
            this._currToken = var1;
            return var1;
         }

         int var2 = var1.id();
         TokenFilter var3;
         TokenFilter var4;
         TokenFilterContext var7;
         JsonToken var8;
         if (var2 != 1) {
            if (var2 != 2) {
               if (var2 == 3) {
                  var3 = this._itemFilter;
                  if (var3 == TokenFilter.INCLUDE_ALL) {
                     this._headContext = this._headContext.createChildArrayContext(var3, true);
                     this._currToken = var1;
                     return var1;
                  }

                  if (var3 == null) {
                     this.delegate.skipChildren();
                     continue;
                  }

                  var4 = this._headContext.checkValue(var3);
                  if (var4 == null) {
                     this.delegate.skipChildren();
                     continue;
                  }

                  var3 = var4;
                  if (var4 != TokenFilter.INCLUDE_ALL) {
                     var3 = var4.filterStartArray();
                  }

                  this._itemFilter = var3;
                  if (var3 == TokenFilter.INCLUDE_ALL) {
                     this._headContext = this._headContext.createChildArrayContext(var3, true);
                     this._currToken = var1;
                     return var1;
                  }

                  var7 = this._headContext.createChildArrayContext(var3, false);
                  this._headContext = var7;
                  if (!this._includePath) {
                     continue;
                  }

                  var8 = this._nextTokenWithBuffering(var7);
                  if (var8 == null) {
                     continue;
                  }

                  this._currToken = var8;
                  return var8;
               }

               if (var2 != 4) {
                  if (var2 != 5) {
                     var3 = this._itemFilter;
                     if (var3 == TokenFilter.INCLUDE_ALL) {
                        this._currToken = var1;
                        return var1;
                     }

                     if (var3 == null) {
                        continue;
                     }

                     var3 = this._headContext.checkValue(var3);
                     if (var3 != TokenFilter.INCLUDE_ALL && (var3 == null || !var3.includeValue(this.delegate)) || !this._verifyAllowedMatches()) {
                        continue;
                     }

                     this._currToken = var1;
                     return var1;
                  }

                  String var6 = this.delegate.getCurrentName();
                  var3 = this._headContext.setFieldName(var6);
                  if (var3 == TokenFilter.INCLUDE_ALL) {
                     this._itemFilter = var3;
                     this._currToken = var1;
                     return var1;
                  }

                  if (var3 == null) {
                     this.delegate.nextToken();
                     this.delegate.skipChildren();
                     continue;
                  }

                  var3 = var3.includeProperty(var6);
                  if (var3 == null) {
                     this.delegate.nextToken();
                     this.delegate.skipChildren();
                     continue;
                  }

                  this._itemFilter = var3;
                  if (var3 == TokenFilter.INCLUDE_ALL) {
                     if (!this._verifyAllowedMatches() || !this._includePath) {
                        continue;
                     }

                     this._currToken = var1;
                     return var1;
                  }

                  if (!this._includePath) {
                     continue;
                  }

                  var8 = this._nextTokenWithBuffering(this._headContext);
                  if (var8 == null) {
                     continue;
                  }

                  this._currToken = var8;
                  return var8;
               }
            }

            boolean var5 = this._headContext.isStartHandled();
            var3 = this._headContext.getFilter();
            if (var3 != null && var3 != TokenFilter.INCLUDE_ALL) {
               var3.filterFinishArray();
            }

            var7 = this._headContext.getParent();
            this._headContext = var7;
            this._itemFilter = var7.getFilter();
            if (var5) {
               this._currToken = var1;
               return var1;
            }
         } else {
            var3 = this._itemFilter;
            if (var3 == TokenFilter.INCLUDE_ALL) {
               this._headContext = this._headContext.createChildObjectContext(var3, true);
               this._currToken = var1;
               return var1;
            }

            if (var3 == null) {
               this.delegate.skipChildren();
            } else {
               var4 = this._headContext.checkValue(var3);
               if (var4 == null) {
                  this.delegate.skipChildren();
               } else {
                  var3 = var4;
                  if (var4 != TokenFilter.INCLUDE_ALL) {
                     var3 = var4.filterStartObject();
                  }

                  this._itemFilter = var3;
                  if (var3 == TokenFilter.INCLUDE_ALL) {
                     this._headContext = this._headContext.createChildObjectContext(var3, true);
                     this._currToken = var1;
                     return var1;
                  }

                  var7 = this._headContext.createChildObjectContext(var3, false);
                  this._headContext = var7;
                  if (this._includePath) {
                     var8 = this._nextTokenWithBuffering(var7);
                     if (var8 != null) {
                        this._currToken = var8;
                        return var8;
                     }
                  }
               }
            }
         }
      }
   }

   protected final JsonToken _nextTokenWithBuffering(TokenFilterContext var1) throws IOException {
      while(true) {
         JsonToken var2 = this.delegate.nextToken();
         if (var2 == null) {
            return var2;
         }

         int var3 = var2.id();
         boolean var4 = false;
         TokenFilter var5;
         TokenFilter var7;
         if (var3 != 1) {
            if (var3 != 2) {
               if (var3 == 3) {
                  var5 = this._headContext.checkValue(this._itemFilter);
                  if (var5 == null) {
                     this.delegate.skipChildren();
                     continue;
                  }

                  var7 = var5;
                  if (var5 != TokenFilter.INCLUDE_ALL) {
                     var7 = var5.filterStartArray();
                  }

                  this._itemFilter = var7;
                  if (var7 == TokenFilter.INCLUDE_ALL) {
                     this._headContext = this._headContext.createChildArrayContext(var7, true);
                     return this._nextBuffered(var1);
                  }

                  this._headContext = this._headContext.createChildArrayContext(var7, false);
                  continue;
               }

               if (var3 != 4) {
                  if (var3 != 5) {
                     var7 = this._itemFilter;
                     if (var7 == TokenFilter.INCLUDE_ALL) {
                        return this._nextBuffered(var1);
                     }

                     if (var7 == null) {
                        continue;
                     }

                     var7 = this._headContext.checkValue(var7);
                     if (var7 != TokenFilter.INCLUDE_ALL && (var7 == null || !var7.includeValue(this.delegate)) || !this._verifyAllowedMatches()) {
                        continue;
                     }

                     return this._nextBuffered(var1);
                  }

                  String var9 = this.delegate.getCurrentName();
                  var5 = this._headContext.setFieldName(var9);
                  if (var5 == TokenFilter.INCLUDE_ALL) {
                     this._itemFilter = var5;
                     return this._nextBuffered(var1);
                  }

                  if (var5 == null) {
                     this.delegate.nextToken();
                     this.delegate.skipChildren();
                     continue;
                  }

                  var5 = var5.includeProperty(var9);
                  if (var5 == null) {
                     this.delegate.nextToken();
                     this.delegate.skipChildren();
                     continue;
                  }

                  this._itemFilter = var5;
                  if (var5 != TokenFilter.INCLUDE_ALL) {
                     continue;
                  }

                  if (this._verifyAllowedMatches()) {
                     return this._nextBuffered(var1);
                  }

                  this._itemFilter = this._headContext.setFieldName(var9);
                  continue;
               }
            }

            var5 = this._headContext.getFilter();
            if (var5 != null && var5 != TokenFilter.INCLUDE_ALL) {
               var5.filterFinishArray();
            }

            boolean var6;
            if (this._headContext == var1) {
               var6 = true;
            } else {
               var6 = false;
            }

            boolean var8 = var4;
            if (var6) {
               var8 = var4;
               if (this._headContext.isStartHandled()) {
                  var8 = true;
               }
            }

            TokenFilterContext var10 = this._headContext.getParent();
            this._headContext = var10;
            this._itemFilter = var10.getFilter();
            if (var8) {
               return var2;
            }
         } else {
            var5 = this._itemFilter;
            if (var5 == TokenFilter.INCLUDE_ALL) {
               this._headContext = this._headContext.createChildObjectContext(var5, true);
               return var2;
            }

            if (var5 == null) {
               this.delegate.skipChildren();
            } else {
               var5 = this._headContext.checkValue(var5);
               if (var5 == null) {
                  this.delegate.skipChildren();
               } else {
                  var7 = var5;
                  if (var5 != TokenFilter.INCLUDE_ALL) {
                     var7 = var5.filterStartObject();
                  }

                  this._itemFilter = var7;
                  if (var7 == TokenFilter.INCLUDE_ALL) {
                     this._headContext = this._headContext.createChildObjectContext(var7, true);
                     return this._nextBuffered(var1);
                  }

                  this._headContext = this._headContext.createChildObjectContext(var7, false);
               }
            }
         }
      }
   }

   public void clearCurrentToken() {
      JsonToken var1 = this._currToken;
      if (var1 != null) {
         this._lastClearedToken = var1;
         this._currToken = null;
      }

   }

   public JsonToken currentToken() {
      return this._currToken;
   }

   public final int currentTokenId() {
      JsonToken var1 = this._currToken;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.id();
      }

      return var2;
   }

   public BigInteger getBigIntegerValue() throws IOException {
      return this.delegate.getBigIntegerValue();
   }

   public byte[] getBinaryValue(Base64Variant var1) throws IOException {
      return this.delegate.getBinaryValue(var1);
   }

   public boolean getBooleanValue() throws IOException {
      return this.delegate.getBooleanValue();
   }

   public byte getByteValue() throws IOException {
      return this.delegate.getByteValue();
   }

   public JsonLocation getCurrentLocation() {
      return this.delegate.getCurrentLocation();
   }

   public String getCurrentName() throws IOException {
      JsonStreamContext var1 = this._filterContext();
      if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
         return var1.getCurrentName();
      } else {
         var1 = var1.getParent();
         String var2;
         if (var1 == null) {
            var2 = null;
         } else {
            var2 = var1.getCurrentName();
         }

         return var2;
      }
   }

   public JsonToken getCurrentToken() {
      return this._currToken;
   }

   public final int getCurrentTokenId() {
      JsonToken var1 = this._currToken;
      int var2;
      if (var1 == null) {
         var2 = 0;
      } else {
         var2 = var1.id();
      }

      return var2;
   }

   public BigDecimal getDecimalValue() throws IOException {
      return this.delegate.getDecimalValue();
   }

   public double getDoubleValue() throws IOException {
      return this.delegate.getDoubleValue();
   }

   public Object getEmbeddedObject() throws IOException {
      return this.delegate.getEmbeddedObject();
   }

   public TokenFilter getFilter() {
      return this.rootFilter;
   }

   public float getFloatValue() throws IOException {
      return this.delegate.getFloatValue();
   }

   public int getIntValue() throws IOException {
      return this.delegate.getIntValue();
   }

   public JsonToken getLastClearedToken() {
      return this._lastClearedToken;
   }

   public long getLongValue() throws IOException {
      return this.delegate.getLongValue();
   }

   public int getMatchCount() {
      return this._matchCount;
   }

   public JsonParser.NumberType getNumberType() throws IOException {
      return this.delegate.getNumberType();
   }

   public Number getNumberValue() throws IOException {
      return this.delegate.getNumberValue();
   }

   public JsonStreamContext getParsingContext() {
      return this._filterContext();
   }

   public short getShortValue() throws IOException {
      return this.delegate.getShortValue();
   }

   public String getText() throws IOException {
      return this.delegate.getText();
   }

   public char[] getTextCharacters() throws IOException {
      return this.delegate.getTextCharacters();
   }

   public int getTextLength() throws IOException {
      return this.delegate.getTextLength();
   }

   public int getTextOffset() throws IOException {
      return this.delegate.getTextOffset();
   }

   public JsonLocation getTokenLocation() {
      return this.delegate.getTokenLocation();
   }

   public boolean getValueAsBoolean() throws IOException {
      return this.delegate.getValueAsBoolean();
   }

   public boolean getValueAsBoolean(boolean var1) throws IOException {
      return this.delegate.getValueAsBoolean(var1);
   }

   public double getValueAsDouble() throws IOException {
      return this.delegate.getValueAsDouble();
   }

   public double getValueAsDouble(double var1) throws IOException {
      return this.delegate.getValueAsDouble(var1);
   }

   public int getValueAsInt() throws IOException {
      return this.delegate.getValueAsInt();
   }

   public int getValueAsInt(int var1) throws IOException {
      return this.delegate.getValueAsInt(var1);
   }

   public long getValueAsLong() throws IOException {
      return this.delegate.getValueAsLong();
   }

   public long getValueAsLong(long var1) throws IOException {
      return this.delegate.getValueAsLong(var1);
   }

   public String getValueAsString() throws IOException {
      return this.delegate.getValueAsString();
   }

   public String getValueAsString(String var1) throws IOException {
      return this.delegate.getValueAsString(var1);
   }

   public boolean hasCurrentToken() {
      boolean var1;
      if (this._currToken != null) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean hasTextCharacters() {
      return this.delegate.hasTextCharacters();
   }

   public final boolean hasToken(JsonToken var1) {
      boolean var2;
      if (this._currToken == var1) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean hasTokenId(int var1) {
      JsonToken var2 = this._currToken;
      boolean var3 = true;
      boolean var4 = true;
      if (var2 == null) {
         if (var1 != 0) {
            var4 = false;
         }

         return var4;
      } else {
         if (var2.id() == var1) {
            var4 = var3;
         } else {
            var4 = false;
         }

         return var4;
      }
   }

   public boolean isExpectedStartArrayToken() {
      boolean var1;
      if (this._currToken == JsonToken.START_ARRAY) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public boolean isExpectedStartObjectToken() {
      boolean var1;
      if (this._currToken == JsonToken.START_OBJECT) {
         var1 = true;
      } else {
         var1 = false;
      }

      return var1;
   }

   public JsonToken nextToken() throws IOException {
      JsonToken var1;
      if (!this._allowMultipleMatches) {
         var1 = this._currToken;
         if (var1 != null && this._exposedContext == null && var1.isScalarValue() && !this._headContext.isStartHandled() && !this._includePath && this._itemFilter == TokenFilter.INCLUDE_ALL) {
            this._currToken = null;
            return null;
         }
      }

      TokenFilterContext var6 = this._exposedContext;
      JsonToken var2;
      if (var6 != null) {
         while(true) {
            var2 = var6.nextTokenToRead();
            if (var2 != null) {
               this._currToken = var2;
               return var2;
            }

            TokenFilterContext var8 = this._headContext;
            if (var6 == var8) {
               this._exposedContext = null;
               if (var6.inArray()) {
                  var1 = this.delegate.getCurrentToken();
                  this._currToken = var1;
                  return var1;
               }
               break;
            }

            var6 = var8.findChildOf(var6);
            this._exposedContext = var6;
            if (var6 == null) {
               throw this._constructError("Unexpected problem: chain of filtered context broken");
            }
         }
      }

      var2 = this.delegate.nextToken();
      if (var2 == null) {
         this._currToken = var2;
         return var2;
      } else {
         int var3 = var2.id();
         TokenFilter var7;
         TokenFilter var9;
         if (var3 != 1) {
            if (var3 != 2) {
               if (var3 == 3) {
                  var7 = this._itemFilter;
                  if (var7 == TokenFilter.INCLUDE_ALL) {
                     this._headContext = this._headContext.createChildArrayContext(var7, true);
                     this._currToken = var2;
                     return var2;
                  }

                  if (var7 == null) {
                     this.delegate.skipChildren();
                     return this._nextToken2();
                  } else {
                     var9 = this._headContext.checkValue(var7);
                     if (var9 == null) {
                        this.delegate.skipChildren();
                     } else {
                        var7 = var9;
                        if (var9 != TokenFilter.INCLUDE_ALL) {
                           var7 = var9.filterStartArray();
                        }

                        this._itemFilter = var7;
                        if (var7 == TokenFilter.INCLUDE_ALL) {
                           this._headContext = this._headContext.createChildArrayContext(var7, true);
                           this._currToken = var2;
                           return var2;
                        }

                        var6 = this._headContext.createChildArrayContext(var7, false);
                        this._headContext = var6;
                        if (this._includePath) {
                           var1 = this._nextTokenWithBuffering(var6);
                           if (var1 != null) {
                              this._currToken = var1;
                              return var1;
                           }

                           return this._nextToken2();
                        }
                     }

                     return this._nextToken2();
                  }
               }

               if (var3 != 4) {
                  if (var3 != 5) {
                     var7 = this._itemFilter;
                     if (var7 == TokenFilter.INCLUDE_ALL) {
                        this._currToken = var2;
                        return var2;
                     }

                     if (var7 != null) {
                        var7 = this._headContext.checkValue(var7);
                        if ((var7 == TokenFilter.INCLUDE_ALL || var7 != null && var7.includeValue(this.delegate)) && this._verifyAllowedMatches()) {
                           this._currToken = var2;
                           return var2;
                        }
                     }
                  } else {
                     String var4 = this.delegate.getCurrentName();
                     var7 = this._headContext.setFieldName(var4);
                     if (var7 == TokenFilter.INCLUDE_ALL) {
                        this._itemFilter = var7;
                        var1 = var2;
                        if (!this._includePath) {
                           var1 = var2;
                           if (this._includeImmediateParent) {
                              var1 = var2;
                              if (!this._headContext.isStartHandled()) {
                                 var1 = this._headContext.nextTokenToRead();
                                 this._exposedContext = this._headContext;
                              }
                           }
                        }

                        this._currToken = var1;
                        return var1;
                     }

                     if (var7 == null) {
                        this.delegate.nextToken();
                        this.delegate.skipChildren();
                        return this._nextToken2();
                     } else {
                        var7 = var7.includeProperty(var4);
                        if (var7 == null) {
                           this.delegate.nextToken();
                           this.delegate.skipChildren();
                        } else {
                           this._itemFilter = var7;
                           if (var7 == TokenFilter.INCLUDE_ALL) {
                              if (this._verifyAllowedMatches()) {
                                 if (this._includePath) {
                                    this._currToken = var2;
                                    return var2;
                                 }
                              } else {
                                 this.delegate.nextToken();
                                 this.delegate.skipChildren();
                              }
                           }

                           if (this._includePath) {
                              var1 = this._nextTokenWithBuffering(this._headContext);
                              if (var1 != null) {
                                 this._currToken = var1;
                                 return var1;
                              }

                              return this._nextToken2();
                           }
                        }

                        return this._nextToken2();
                     }
                  }

                  return this._nextToken2();
               }
            }

            boolean var5 = this._headContext.isStartHandled();
            var7 = this._headContext.getFilter();
            if (var7 != null && var7 != TokenFilter.INCLUDE_ALL) {
               var7.filterFinishArray();
            }

            var6 = this._headContext.getParent();
            this._headContext = var6;
            this._itemFilter = var6.getFilter();
            if (var5) {
               this._currToken = var2;
               return var2;
            }
         } else {
            var7 = this._itemFilter;
            if (var7 == TokenFilter.INCLUDE_ALL) {
               this._headContext = this._headContext.createChildObjectContext(var7, true);
               this._currToken = var2;
               return var2;
            }

            if (var7 == null) {
               this.delegate.skipChildren();
            } else {
               var9 = this._headContext.checkValue(var7);
               if (var9 == null) {
                  this.delegate.skipChildren();
               } else {
                  var7 = var9;
                  if (var9 != TokenFilter.INCLUDE_ALL) {
                     var7 = var9.filterStartObject();
                  }

                  this._itemFilter = var7;
                  if (var7 == TokenFilter.INCLUDE_ALL) {
                     this._headContext = this._headContext.createChildObjectContext(var7, true);
                     this._currToken = var2;
                     return var2;
                  }

                  var6 = this._headContext.createChildObjectContext(var7, false);
                  this._headContext = var6;
                  if (this._includePath) {
                     var1 = this._nextTokenWithBuffering(var6);
                     if (var1 != null) {
                        this._currToken = var1;
                        return var1;
                     }
                  }
               }
            }
         }

         return this._nextToken2();
      }
   }

   public JsonToken nextValue() throws IOException {
      JsonToken var1 = this.nextToken();
      JsonToken var2 = var1;
      if (var1 == JsonToken.FIELD_NAME) {
         var2 = this.nextToken();
      }

      return var2;
   }

   public void overrideCurrentName(String var1) {
      throw new UnsupportedOperationException("Can not currently override name during filtering read");
   }

   public int readBinaryValue(Base64Variant var1, OutputStream var2) throws IOException {
      return this.delegate.readBinaryValue(var1, var2);
   }

   public JsonParser skipChildren() throws IOException {
      if (this._currToken != JsonToken.START_OBJECT && this._currToken != JsonToken.START_ARRAY) {
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
}

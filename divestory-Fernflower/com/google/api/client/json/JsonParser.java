package com.google.api.client.json;

import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sets;
import com.google.api.client.util.Types;
import java.io.Closeable;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class JsonParser implements Closeable {
   private static WeakHashMap<Class<?>, Field> cachedTypemapFields = new WeakHashMap();
   private static final Lock lock = new ReentrantLock();

   private static Field getCachedTypemapFieldFor(Class<?> var0) {
      Field var1 = null;
      if (var0 == null) {
         return null;
      } else {
         lock.lock();

         Throwable var10000;
         label703: {
            Field var66;
            label709: {
               boolean var10001;
               try {
                  if (cachedTypemapFields.containsKey(var0)) {
                     var66 = (Field)cachedTypemapFields.get(var0);
                     break label709;
                  }
               } catch (Throwable var64) {
                  var10000 = var64;
                  var10001 = false;
                  break label703;
               }

               Iterator var2;
               try {
                  var2 = ClassInfo.of(var0).getFieldInfos().iterator();
               } catch (Throwable var61) {
                  var10000 = var61;
                  var10001 = false;
                  break label703;
               }

               while(true) {
                  Field var3;
                  JsonPolymorphicTypeMap var4;
                  try {
                     if (!var2.hasNext()) {
                        break;
                     }

                     var3 = ((FieldInfo)var2.next()).getField();
                     var4 = (JsonPolymorphicTypeMap)var3.getAnnotation(JsonPolymorphicTypeMap.class);
                  } catch (Throwable var62) {
                     var10000 = var62;
                     var10001 = false;
                     break label703;
                  }

                  if (var4 != null) {
                     boolean var5;
                     if (var1 == null) {
                        var5 = true;
                     } else {
                        var5 = false;
                     }

                     JsonPolymorphicTypeMap.TypeDef[] var67;
                     HashSet var68;
                     label690: {
                        label689: {
                           try {
                              Preconditions.checkArgument(var5, "Class contains more than one field with @JsonPolymorphicTypeMap annotation: %s", var0);
                              Preconditions.checkArgument(Data.isPrimitive(var3.getType()), "Field which has the @JsonPolymorphicTypeMap, %s, is not a supported type: %s", var0, var3.getType());
                              var67 = var4.typeDefinitions();
                              var68 = Sets.newHashSet();
                              if (var67.length <= 0) {
                                 break label689;
                              }
                           } catch (Throwable var63) {
                              var10000 = var63;
                              var10001 = false;
                              break label703;
                           }

                           var5 = true;
                           break label690;
                        }

                        var5 = false;
                     }

                     int var6;
                     try {
                        Preconditions.checkArgument(var5, "@JsonPolymorphicTypeMap must have at least one @TypeDef");
                        var6 = var67.length;
                     } catch (Throwable var60) {
                        var10000 = var60;
                        var10001 = false;
                        break label703;
                     }

                     for(int var7 = 0; var7 < var6; ++var7) {
                        JsonPolymorphicTypeMap.TypeDef var8 = var67[var7];

                        try {
                           Preconditions.checkArgument(var68.add(var8.key()), "Class contains two @TypeDef annotations with identical key: %s", var8.key());
                        } catch (Throwable var59) {
                           var10000 = var59;
                           var10001 = false;
                           break label703;
                        }
                     }

                     var1 = var3;
                  }
               }

               try {
                  cachedTypemapFields.put(var0, var1);
               } catch (Throwable var58) {
                  var10000 = var58;
                  var10001 = false;
                  break label703;
               }

               lock.unlock();
               return var1;
            }

            lock.unlock();
            return var66;
         }

         Throwable var65 = var10000;
         lock.unlock();
         throw var65;
      }
   }

   private void parse(ArrayList<Type> var1, Object var2, CustomizeJsonParser var3) throws IOException {
      if (var2 instanceof GenericJson) {
         ((GenericJson)var2).setFactory(this.getFactory());
      }

      JsonToken var4 = this.startParsingObjectOrArray();
      Class var5 = var2.getClass();
      ClassInfo var6 = ClassInfo.of(var5);
      boolean var7 = GenericData.class.isAssignableFrom(var5);
      JsonToken var8 = var4;
      if (!var7) {
         var8 = var4;
         if (Map.class.isAssignableFrom(var5)) {
            this.parseMap((Field)null, (Map)var2, Types.getMapValueParameter(var5), var1, var3);
            return;
         }
      }

      for(; var8 == JsonToken.FIELD_NAME; var8 = this.nextToken()) {
         String var10 = this.getText();
         this.nextToken();
         if (var3 != null && var3.stopAt(var2, var10)) {
            return;
         }

         FieldInfo var13 = var6.getFieldInfo(var10);
         if (var13 != null) {
            if (var13.isFinal() && !var13.isPrimitive()) {
               throw new IllegalArgumentException("final array/object fields are not supported");
            }

            Field var11 = var13.getField();
            int var9 = var1.size();
            var1.add(var11.getGenericType());
            Object var12 = this.parseValue(var11, var13.getGenericType(), var1, var2, var3, true);
            var1.remove(var9);
            var13.setValue(var2, var12);
         } else if (var7) {
            ((GenericData)var2).set(var10, this.parseValue((Field)null, (Type)null, var1, var2, var3, true));
         } else {
            if (var3 != null) {
               var3.handleUnrecognizedKey(var2, var10);
            }

            this.skipChildren();
         }
      }

   }

   private <T> void parseArray(Field var1, Collection<T> var2, Type var3, ArrayList<Type> var4, CustomizeJsonParser var5) throws IOException {
      for(JsonToken var6 = this.startParsingObjectOrArray(); var6 != JsonToken.END_ARRAY; var6 = this.nextToken()) {
         var2.add(this.parseValue(var1, var3, var4, var2, var5, true));
      }

   }

   private void parseMap(Field var1, Map<String, Object> var2, Type var3, ArrayList<Type> var4, CustomizeJsonParser var5) throws IOException {
      for(JsonToken var6 = this.startParsingObjectOrArray(); var6 == JsonToken.FIELD_NAME; var6 = this.nextToken()) {
         String var7 = this.getText();
         this.nextToken();
         if (var5 != null && var5.stopAt(var2, var7)) {
            return;
         }

         var2.put(var7, this.parseValue(var1, var3, var4, var2, var5, true));
      }

   }

   private final Object parseValue(Field var1, Type var2, ArrayList<Type> var3, Object var4, CustomizeJsonParser var5, boolean var6) throws IOException {
      Type var7 = Data.resolveWildcardTypeOrTypeVariable(var3, var2);
      boolean var8 = var7 instanceof Class;
      Object var9 = null;
      Field var10 = null;
      Class var82;
      if (var8) {
         var82 = (Class)var7;
      } else {
         var82 = null;
      }

      Class var11 = var82;
      if (var7 instanceof ParameterizedType) {
         var11 = Types.getRawClass((ParameterizedType)var7);
      }

      if (var11 == Void.class) {
         this.skipChildren();
         return null;
      } else {
         JsonToken var83 = this.getCurrentToken();

         IllegalArgumentException var10000;
         IllegalArgumentException var88;
         StringBuilder var98;
         label701: {
            int var12;
            boolean var10001;
            try {
               var12 = null.$SwitchMap$com$google$api$client$json$JsonToken[this.getCurrentToken().ordinal()];
            } catch (IllegalArgumentException var78) {
               var10000 = var78;
               var10001 = false;
               break label701;
            }

            var8 = false;
            boolean var13 = false;
            boolean var14 = false;
            boolean var15 = false;
            label642:
            switch(var12) {
            case 1:
            case 4:
            case 5:
               label631: {
                  label630: {
                     try {
                        if (!Types.isArray(var7)) {
                           break label630;
                        }
                     } catch (IllegalArgumentException var73) {
                        var10000 = var73;
                        var10001 = false;
                        break;
                     }

                     var8 = false;
                     break label631;
                  }

                  var8 = true;
               }

               try {
                  Preconditions.checkArgument(var8, "expected object or map type but got %s", var7);
               } catch (IllegalArgumentException var72) {
                  var10000 = var72;
                  var10001 = false;
                  break;
               }

               if (var6) {
                  try {
                     var10 = getCachedTypemapFieldFor(var11);
                  } catch (IllegalArgumentException var71) {
                     var10000 = var71;
                     var10001 = false;
                     break;
                  }
               } else {
                  var10 = null;
               }

               if (var11 != null && var5 != null) {
                  try {
                     var4 = var5.newInstanceForObject(var4, var11);
                  } catch (IllegalArgumentException var70) {
                     var10000 = var70;
                     var10001 = false;
                     break;
                  }
               } else {
                  var4 = null;
               }

               boolean var101;
               label609: {
                  label608: {
                     if (var11 != null) {
                        try {
                           if (Types.isAssignableToOrFrom(var11, Map.class)) {
                              break label608;
                           }
                        } catch (IllegalArgumentException var69) {
                           var10000 = var69;
                           var10001 = false;
                           break;
                        }
                     }

                     var101 = false;
                     break label609;
                  }

                  var101 = true;
               }

               Object var96;
               if (var10 != null) {
                  try {
                     var96 = new GenericJson();
                  } catch (IllegalArgumentException var68) {
                     var10000 = var68;
                     var10001 = false;
                     break;
                  }
               } else {
                  var96 = var4;
                  if (var4 == null) {
                     if (!var101 && var11 != null) {
                        try {
                           var96 = Types.newInstance(var11);
                        } catch (IllegalArgumentException var67) {
                           var10000 = var67;
                           var10001 = false;
                           break;
                        }
                     } else {
                        try {
                           var96 = Data.newMapInstance(var11);
                        } catch (IllegalArgumentException var66) {
                           var10000 = var66;
                           var10001 = false;
                           break;
                        }
                     }
                  }
               }

               int var16;
               try {
                  var16 = var3.size();
               } catch (IllegalArgumentException var65) {
                  var10000 = var65;
                  var10001 = false;
                  break;
               }

               if (var7 != null) {
                  try {
                     var3.add(var7);
                  } catch (IllegalArgumentException var64) {
                     var10000 = var64;
                     var10001 = false;
                     break;
                  }
               }

               if (var101) {
                  label579: {
                     Type var94;
                     label578: {
                        try {
                           if (GenericData.class.isAssignableFrom(var11)) {
                              break label579;
                           }

                           if (Map.class.isAssignableFrom(var11)) {
                              var94 = Types.getMapValueParameter(var7);
                              break label578;
                           }
                        } catch (IllegalArgumentException var63) {
                           var10000 = var63;
                           var10001 = false;
                           break;
                        }

                        var94 = null;
                     }

                     if (var94 != null) {
                        try {
                           this.parseMap(var1, (Map)var96, var94, var3, var5);
                           return var96;
                        } catch (IllegalArgumentException var56) {
                           var10000 = var56;
                           var10001 = false;
                           break;
                        }
                     }
                  }
               }

               try {
                  this.parse(var3, var96, var5);
               } catch (IllegalArgumentException var62) {
                  var10000 = var62;
                  var10001 = false;
                  break;
               }

               if (var7 != null) {
                  try {
                     var3.remove(var16);
                  } catch (IllegalArgumentException var61) {
                     var10000 = var61;
                     var10001 = false;
                     break;
                  }
               }

               if (var10 == null) {
                  return var96;
               }

               try {
                  var4 = ((GenericJson)var96).get(var10.getName());
               } catch (IllegalArgumentException var60) {
                  var10000 = var60;
                  var10001 = false;
                  break;
               }

               if (var4 != null) {
                  var6 = true;
               } else {
                  var6 = false;
               }

               String var92;
               JsonPolymorphicTypeMap.TypeDef[] var100;
               try {
                  Preconditions.checkArgument(var6, "No value specified for @JsonPolymorphicTypeMap field");
                  var92 = var4.toString();
                  var100 = ((JsonPolymorphicTypeMap)var10.getAnnotation(JsonPolymorphicTypeMap.class)).typeDefinitions();
                  var16 = var100.length;
               } catch (IllegalArgumentException var59) {
                  var10000 = var59;
                  var10001 = false;
                  break;
               }

               var12 = 0;

               Class var95;
               while(true) {
                  var95 = (Class)var9;
                  if (var12 >= var16) {
                     break;
                  }

                  JsonPolymorphicTypeMap.TypeDef var97 = var100[var12];

                  try {
                     if (var97.key().equals(var92)) {
                        var95 = var97.ref();
                        break;
                     }
                  } catch (IllegalArgumentException var58) {
                     var10000 = var58;
                     var10001 = false;
                     break label642;
                  }

                  ++var12;
               }

               var6 = var14;
               if (var95 != null) {
                  var6 = true;
               }

               try {
                  StringBuilder var102 = new StringBuilder();
                  var102.append("No TypeDef annotation found with key: ");
                  var102.append(var92);
                  Preconditions.checkArgument(var6, var102.toString());
                  JsonFactory var93 = this.getFactory();
                  JsonParser var99 = var93.createJsonParser(var93.toString(var96));
                  var99.startParsing();
                  return var99.parseValue(var1, var95, var3, (Object)null, (CustomizeJsonParser)null, false);
               } catch (IllegalArgumentException var57) {
                  var10000 = var57;
                  var10001 = false;
                  break;
               }
            case 2:
            case 3:
               try {
                  var8 = Types.isArray(var7);
               } catch (IllegalArgumentException var55) {
                  var10000 = var55;
                  var10001 = false;
                  break;
               }

               label703: {
                  if (var7 != null && !var8) {
                     label698: {
                        if (var11 != null) {
                           try {
                              if (Types.isAssignableToOrFrom(var11, Collection.class)) {
                                 break label698;
                              }
                           } catch (IllegalArgumentException var54) {
                              var10000 = var54;
                              var10001 = false;
                              break;
                           }
                        }

                        var6 = false;
                        break label703;
                     }
                  }

                  var6 = true;
               }

               try {
                  Preconditions.checkArgument(var6, "expected collection or array type but got %s", var7);
               } catch (IllegalArgumentException var53) {
                  var10000 = var53;
                  var10001 = false;
                  break;
               }

               Collection var91;
               if (var5 != null && var1 != null) {
                  try {
                     var91 = var5.newInstanceForArray(var4, var1);
                  } catch (IllegalArgumentException var52) {
                     var10000 = var52;
                     var10001 = false;
                     break;
                  }
               } else {
                  var91 = null;
               }

               Collection var90 = var91;
               if (var91 == null) {
                  try {
                     var90 = Data.newCollectionInstance(var7);
                  } catch (IllegalArgumentException var51) {
                     var10000 = var51;
                     var10001 = false;
                     break;
                  }
               }

               if (var8) {
                  try {
                     var2 = Types.getArrayComponentType(var7);
                  } catch (IllegalArgumentException var50) {
                     var10000 = var50;
                     var10001 = false;
                     break;
                  }
               } else {
                  var2 = var10;
                  if (var11 != null) {
                     var2 = var10;

                     try {
                        if (Iterable.class.isAssignableFrom(var11)) {
                           var2 = Types.getIterableParameter(var7);
                        }
                     } catch (IllegalArgumentException var49) {
                        var10000 = var49;
                        var10001 = false;
                        break;
                     }
                  }
               }

               try {
                  var2 = Data.resolveWildcardTypeOrTypeVariable(var3, var2);
                  this.parseArray(var1, var90, var2, var3, var5);
               } catch (IllegalArgumentException var48) {
                  var10000 = var48;
                  var10001 = false;
                  break;
               }

               if (!var8) {
                  return var90;
               }

               try {
                  return Types.toArray(var90, Types.getRawArrayComponentType(var3, var2));
               } catch (IllegalArgumentException var47) {
                  var10000 = var47;
                  var10001 = false;
                  break;
               }
            case 6:
            case 7:
               label486: {
                  label704: {
                     if (var7 != null) {
                        label699: {
                           try {
                              if (var11 == Boolean.TYPE) {
                                 break label699;
                              }
                           } catch (IllegalArgumentException var46) {
                              var10000 = var46;
                              var10001 = false;
                              break;
                           }

                           if (var11 == null) {
                              break label704;
                           }

                           try {
                              if (!var11.isAssignableFrom(Boolean.class)) {
                                 break label704;
                              }
                           } catch (IllegalArgumentException var45) {
                              var10000 = var45;
                              var10001 = false;
                              break;
                           }
                        }
                     }

                     var6 = true;
                     break label486;
                  }

                  var6 = false;
               }

               Boolean var81;
               Boolean var89;
               label713: {
                  try {
                     Preconditions.checkArgument(var6, "expected type Boolean or boolean but got %s", var7);
                     if (var83 == JsonToken.VALUE_TRUE) {
                        var89 = Boolean.TRUE;
                        break label713;
                     }
                  } catch (IllegalArgumentException var44) {
                     var10000 = var44;
                     var10001 = false;
                     break;
                  }

                  try {
                     var89 = Boolean.FALSE;
                  } catch (IllegalArgumentException var43) {
                     var10000 = var43;
                     var10001 = false;
                     break;
                  }

                  var81 = var89;
                  return var81;
               }

               var81 = var89;
               return var81;
            case 8:
            case 9:
               label458: {
                  if (var1 != null) {
                     var6 = var13;

                     try {
                        if (var1.getAnnotation(JsonString.class) != null) {
                           break label458;
                        }
                     } catch (IllegalArgumentException var42) {
                        var10000 = var42;
                        var10001 = false;
                        break;
                     }
                  }

                  var6 = true;
               }

               try {
                  Preconditions.checkArgument(var6, "number type formatted as a JSON number cannot use @JsonString annotation");
               } catch (IllegalArgumentException var41) {
                  var10000 = var41;
                  var10001 = false;
                  break;
               }

               label447: {
                  if (var11 != null) {
                     try {
                        if (!var11.isAssignableFrom(BigDecimal.class)) {
                           break label447;
                        }
                     } catch (IllegalArgumentException var40) {
                        var10000 = var40;
                        var10001 = false;
                        break;
                     }
                  }

                  try {
                     return this.getDecimalValue();
                  } catch (IllegalArgumentException var39) {
                     var10000 = var39;
                     var10001 = false;
                     break;
                  }
               }

               if (var11 == BigInteger.class) {
                  try {
                     return this.getBigIntegerValue();
                  } catch (IllegalArgumentException var25) {
                     var10000 = var25;
                     var10001 = false;
                  }
               } else {
                  label436: {
                     if (var11 != Double.class) {
                        try {
                           if (var11 != Double.TYPE) {
                              break label436;
                           }
                        } catch (IllegalArgumentException var38) {
                           var10000 = var38;
                           var10001 = false;
                           break;
                        }
                     }

                     try {
                        return this.getDoubleValue();
                     } catch (IllegalArgumentException var37) {
                        var10000 = var37;
                        var10001 = false;
                        break;
                     }
                  }

                  if (var11 != Long.class) {
                     label696: {
                        try {
                           if (var11 == Long.TYPE) {
                              break label696;
                           }
                        } catch (IllegalArgumentException var36) {
                           var10000 = var36;
                           var10001 = false;
                           break;
                        }

                        label413: {
                           if (var11 != Float.class) {
                              try {
                                 if (var11 != Float.TYPE) {
                                    break label413;
                                 }
                              } catch (IllegalArgumentException var34) {
                                 var10000 = var34;
                                 var10001 = false;
                                 break;
                              }
                           }

                           try {
                              return this.getFloatValue();
                           } catch (IllegalArgumentException var33) {
                              var10000 = var33;
                              var10001 = false;
                              break;
                           }
                        }

                        label403: {
                           if (var11 != Integer.class) {
                              try {
                                 if (var11 != Integer.TYPE) {
                                    break label403;
                                 }
                              } catch (IllegalArgumentException var32) {
                                 var10000 = var32;
                                 var10001 = false;
                                 break;
                              }
                           }

                           try {
                              return this.getIntValue();
                           } catch (IllegalArgumentException var31) {
                              var10000 = var31;
                              var10001 = false;
                              break;
                           }
                        }

                        label393: {
                           if (var11 != Short.class) {
                              try {
                                 if (var11 != Short.TYPE) {
                                    break label393;
                                 }
                              } catch (IllegalArgumentException var30) {
                                 var10000 = var30;
                                 var10001 = false;
                                 break;
                              }
                           }

                           try {
                              return this.getShortValue();
                           } catch (IllegalArgumentException var29) {
                              var10000 = var29;
                              var10001 = false;
                              break;
                           }
                        }

                        label383: {
                           if (var11 != Byte.class) {
                              try {
                                 if (var11 != Byte.TYPE) {
                                    break label383;
                                 }
                              } catch (IllegalArgumentException var28) {
                                 var10000 = var28;
                                 var10001 = false;
                                 break;
                              }
                           }

                           try {
                              return this.getByteValue();
                           } catch (IllegalArgumentException var27) {
                              var10000 = var27;
                              var10001 = false;
                              break;
                           }
                        }

                        try {
                           StringBuilder var84 = new StringBuilder();
                           var84.append("expected numeric type but got ");
                           var84.append(var7);
                           var88 = new IllegalArgumentException(var84.toString());
                           throw var88;
                        } catch (IllegalArgumentException var26) {
                           var10000 = var26;
                           var10001 = false;
                           break;
                        }
                     }
                  }

                  try {
                     return this.getLongValue();
                  } catch (IllegalArgumentException var35) {
                     var10000 = var35;
                     var10001 = false;
                  }
               }
               break;
            case 10:
               label680: {
                  label368: {
                     String var86;
                     label367: {
                        try {
                           var86 = this.getText().trim().toLowerCase(Locale.US);
                           if (var11 == Float.TYPE) {
                              break label367;
                           }
                        } catch (IllegalArgumentException var24) {
                           var10000 = var24;
                           var10001 = false;
                           break;
                        }

                        if (var11 != Float.class) {
                           label362: {
                              try {
                                 if (var11 == Double.TYPE) {
                                    break label362;
                                 }
                              } catch (IllegalArgumentException var23) {
                                 var10000 = var23;
                                 var10001 = false;
                                 break;
                              }

                              if (var11 != Double.class) {
                                 break label368;
                              }
                           }
                        }
                     }

                     try {
                        if (var86.equals("nan") || var86.equals("infinity") || var86.equals("-infinity")) {
                           break label680;
                        }
                     } catch (IllegalArgumentException var22) {
                        var10000 = var22;
                        var10001 = false;
                        break;
                     }
                  }

                  label348: {
                     if (var11 != null) {
                        label682: {
                           try {
                              if (!Number.class.isAssignableFrom(var11)) {
                                 break label682;
                              }
                           } catch (IllegalArgumentException var21) {
                              var10000 = var21;
                              var10001 = false;
                              break;
                           }

                           var6 = var8;
                           if (var1 == null) {
                              break label348;
                           }

                           var6 = var8;

                           try {
                              if (var1.getAnnotation(JsonString.class) == null) {
                                 break label348;
                              }
                           } catch (IllegalArgumentException var20) {
                              var10000 = var20;
                              var10001 = false;
                              break;
                           }
                        }
                     }

                     var6 = true;
                  }

                  try {
                     Preconditions.checkArgument(var6, "number field formatted as a JSON string must use the @JsonString annotation");
                  } catch (IllegalArgumentException var19) {
                     var10000 = var19;
                     var10001 = false;
                     break;
                  }
               }

               try {
                  return Data.parsePrimitiveValue(var7, this.getText());
               } catch (IllegalArgumentException var18) {
                  var10000 = var18;
                  var10001 = false;
                  break;
               }
            case 11:
               label666: {
                  if (var11 != null) {
                     var6 = var15;

                     try {
                        if (var11.isPrimitive()) {
                           break label666;
                        }
                     } catch (IllegalArgumentException var80) {
                        var10000 = var80;
                        var10001 = false;
                        break;
                     }
                  }

                  var6 = true;
               }

               try {
                  Preconditions.checkArgument(var6, "primitive number field but found a JSON null");
               } catch (IllegalArgumentException var77) {
                  var10000 = var77;
                  var10001 = false;
                  break;
               }

               if (var11 != null) {
                  label700: {
                     try {
                        if ((var11.getModifiers() & 1536) == 0) {
                           break label700;
                        }

                        if (Types.isAssignableToOrFrom(var11, Collection.class)) {
                           return Data.nullOf(Data.newCollectionInstance(var7).getClass());
                        }
                     } catch (IllegalArgumentException var79) {
                        var10000 = var79;
                        var10001 = false;
                        break;
                     }

                     try {
                        if (Types.isAssignableToOrFrom(var11, Map.class)) {
                           return Data.nullOf(Data.newMapInstance(var11).getClass());
                        }
                     } catch (IllegalArgumentException var76) {
                        var10000 = var76;
                        var10001 = false;
                        break;
                     }
                  }
               }

               try {
                  return Data.nullOf(Types.getRawArrayComponentType(var3, var7));
               } catch (IllegalArgumentException var17) {
                  var10000 = var17;
                  var10001 = false;
                  break;
               }
            default:
               label688: {
                  IllegalArgumentException var85;
                  try {
                     var85 = new IllegalArgumentException;
                  } catch (IllegalArgumentException var75) {
                     var10000 = var75;
                     var10001 = false;
                     break label688;
                  }

                  try {
                     var98 = new StringBuilder();
                     var98.append("unexpected JSON node type: ");
                     var98.append(var83);
                     var85.<init>(var98.toString());
                     throw var85;
                  } catch (IllegalArgumentException var74) {
                     var10000 = var74;
                     var10001 = false;
                  }
               }
            }
         }

         var88 = var10000;
         var98 = new StringBuilder();
         String var87 = this.getCurrentName();
         if (var87 != null) {
            var98.append("key ");
            var98.append(var87);
         }

         if (var1 != null) {
            if (var87 != null) {
               var98.append(", ");
            }

            var98.append("field ");
            var98.append(var1);
         }

         throw new IllegalArgumentException(var98.toString(), var88);
      }
   }

   private JsonToken startParsing() throws IOException {
      JsonToken var1 = this.getCurrentToken();
      JsonToken var2 = var1;
      if (var1 == null) {
         var2 = this.nextToken();
      }

      boolean var3;
      if (var2 != null) {
         var3 = true;
      } else {
         var3 = false;
      }

      Preconditions.checkArgument(var3, "no JSON input found");
      return var2;
   }

   private JsonToken startParsingObjectOrArray() throws IOException {
      JsonToken var1 = this.startParsing();
      int var2 = null.$SwitchMap$com$google$api$client$json$JsonToken[var1.ordinal()];
      boolean var3 = true;
      if (var2 != 1) {
         if (var2 == 2) {
            var1 = this.nextToken();
         }
      } else {
         var1 = this.nextToken();
         boolean var4 = var3;
         if (var1 != JsonToken.FIELD_NAME) {
            if (var1 == JsonToken.END_OBJECT) {
               var4 = var3;
            } else {
               var4 = false;
            }
         }

         Preconditions.checkArgument(var4, var1);
      }

      return var1;
   }

   public abstract void close() throws IOException;

   public abstract BigInteger getBigIntegerValue() throws IOException;

   public abstract byte getByteValue() throws IOException;

   public abstract String getCurrentName() throws IOException;

   public abstract JsonToken getCurrentToken();

   public abstract BigDecimal getDecimalValue() throws IOException;

   public abstract double getDoubleValue() throws IOException;

   public abstract JsonFactory getFactory();

   public abstract float getFloatValue() throws IOException;

   public abstract int getIntValue() throws IOException;

   public abstract long getLongValue() throws IOException;

   public abstract short getShortValue() throws IOException;

   public abstract String getText() throws IOException;

   public abstract JsonToken nextToken() throws IOException;

   public final <T> T parse(Class<T> var1) throws IOException {
      return this.parse((Class)var1, (CustomizeJsonParser)null);
   }

   public final <T> T parse(Class<T> var1, CustomizeJsonParser var2) throws IOException {
      return this.parse(var1, false, var2);
   }

   public Object parse(Type var1, boolean var2) throws IOException {
      return this.parse(var1, var2, (CustomizeJsonParser)null);
   }

   public Object parse(Type var1, boolean var2, CustomizeJsonParser var3) throws IOException {
      Object var7;
      try {
         if (!Void.class.equals(var1)) {
            this.startParsing();
         }

         ArrayList var4 = new ArrayList();
         var7 = this.parseValue((Field)null, var1, var4, (Object)null, var3, true);
      } finally {
         if (var2) {
            this.close();
         }

      }

      return var7;
   }

   public final void parse(Object var1) throws IOException {
      this.parse((Object)var1, (CustomizeJsonParser)null);
   }

   public final void parse(Object var1, CustomizeJsonParser var2) throws IOException {
      ArrayList var3 = new ArrayList();
      var3.add(var1.getClass());
      this.parse(var3, var1, var2);
   }

   public final <T> T parseAndClose(Class<T> var1) throws IOException {
      return this.parseAndClose((Class)var1, (CustomizeJsonParser)null);
   }

   public final <T> T parseAndClose(Class<T> var1, CustomizeJsonParser var2) throws IOException {
      Object var5;
      try {
         var5 = this.parse(var1, var2);
      } finally {
         this.close();
      }

      return var5;
   }

   public final void parseAndClose(Object var1) throws IOException {
      this.parseAndClose((Object)var1, (CustomizeJsonParser)null);
   }

   public final void parseAndClose(Object var1, CustomizeJsonParser var2) throws IOException {
      try {
         this.parse(var1, var2);
      } finally {
         this.close();
      }

   }

   public final <T> Collection<T> parseArray(Class<?> var1, Class<T> var2) throws IOException {
      return this.parseArray((Class)var1, var2, (CustomizeJsonParser)null);
   }

   public final <T> Collection<T> parseArray(Class<?> var1, Class<T> var2, CustomizeJsonParser var3) throws IOException {
      Collection var4 = Data.newCollectionInstance(var1);
      this.parseArray(var4, var2, var3);
      return var4;
   }

   public final <T> void parseArray(Collection<? super T> var1, Class<T> var2) throws IOException {
      this.parseArray((Collection)var1, var2, (CustomizeJsonParser)null);
   }

   public final <T> void parseArray(Collection<? super T> var1, Class<T> var2, CustomizeJsonParser var3) throws IOException {
      this.parseArray((Field)null, var1, var2, new ArrayList(), var3);
   }

   public final <T> Collection<T> parseArrayAndClose(Class<?> var1, Class<T> var2) throws IOException {
      return this.parseArrayAndClose((Class)var1, var2, (CustomizeJsonParser)null);
   }

   public final <T> Collection<T> parseArrayAndClose(Class<?> var1, Class<T> var2, CustomizeJsonParser var3) throws IOException {
      Collection var6;
      try {
         var6 = this.parseArray(var1, var2, var3);
      } finally {
         this.close();
      }

      return var6;
   }

   public final <T> void parseArrayAndClose(Collection<? super T> var1, Class<T> var2) throws IOException {
      this.parseArrayAndClose((Collection)var1, var2, (CustomizeJsonParser)null);
   }

   public final <T> void parseArrayAndClose(Collection<? super T> var1, Class<T> var2, CustomizeJsonParser var3) throws IOException {
      try {
         this.parseArray(var1, var2, var3);
      } finally {
         this.close();
      }

   }

   public abstract JsonParser skipChildren() throws IOException;

   public final String skipToKey(Set<String> var1) throws IOException {
      for(JsonToken var2 = this.startParsingObjectOrArray(); var2 == JsonToken.FIELD_NAME; var2 = this.nextToken()) {
         String var3 = this.getText();
         this.nextToken();
         if (var1.contains(var3)) {
            return var3;
         }

         this.skipChildren();
      }

      return null;
   }

   public final void skipToKey(String var1) throws IOException {
      this.skipToKey(Collections.singleton(var1));
   }
}

package com.google.api.client.http;

import com.google.api.client.util.ArrayValueMap;
import com.google.api.client.util.Charsets;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.ObjectParser;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Throwables;
import com.google.api.client.util.Types;
import com.google.api.client.util.escape.CharEscapers;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UrlEncodedParser implements ObjectParser {
   public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
   public static final String MEDIA_TYPE;

   static {
      MEDIA_TYPE = (new HttpMediaType("application/x-www-form-urlencoded")).setCharsetParameter(Charsets.UTF_8).build();
   }

   public static void parse(Reader var0, Object var1) throws IOException {
      parse(var0, var1, true);
   }

   public static void parse(Reader var0, Object var1, boolean var2) throws IOException {
      Class var3 = var1.getClass();
      ClassInfo var4 = ClassInfo.of(var3);
      List var5 = Arrays.asList(var3);
      GenericData var6;
      if (GenericData.class.isAssignableFrom(var3)) {
         var6 = (GenericData)var1;
      } else {
         var6 = null;
      }

      Map var15;
      if (Map.class.isAssignableFrom(var3)) {
         var15 = (Map)var1;
      } else {
         var15 = null;
      }

      ArrayValueMap var7 = new ArrayValueMap(var1);
      StringWriter var8 = new StringWriter();
      StringWriter var9 = new StringWriter();

      int var11;
      do {
         boolean var10 = true;

         while(true) {
            var11 = var0.read();
            if (var11 == -1 || var11 == 38) {
               String var12 = var8.toString();
               String var16 = var12;
               if (var2) {
                  var16 = CharEscapers.decodeUri(var12);
               }

               if (var16.length() != 0) {
                  var12 = var9.toString();
                  String var17 = var12;
                  if (var2) {
                     var17 = CharEscapers.decodeUri(var12);
                  }

                  FieldInfo var13 = var4.getFieldInfo(var16);
                  if (var13 != null) {
                     Type var14 = Data.resolveWildcardTypeOrTypeVariable(var5, var13.getGenericType());
                     if (Types.isArray(var14)) {
                        Class var18 = Types.getRawArrayComponentType(var5, Types.getArrayComponentType(var14));
                        var7.put(var13.getField(), var18, parseValue(var18, var5, var17));
                     } else if (Types.isAssignableToOrFrom(Types.getRawArrayComponentType(var5, var14), Iterable.class)) {
                        Collection var20 = (Collection)var13.getValue(var1);
                        Collection var19 = var20;
                        if (var20 == null) {
                           var19 = Data.newCollectionInstance(var14);
                           var13.setValue(var1, var19);
                        }

                        Type var21;
                        if (var14 == Object.class) {
                           var21 = null;
                        } else {
                           var21 = Types.getIterableParameter(var14);
                        }

                        var19.add(parseValue(var21, var5, var17));
                     } else {
                        var13.setValue(var1, parseValue(var14, var5, var17));
                     }
                  } else if (var15 != null) {
                     ArrayList var22 = (ArrayList)var15.get(var16);
                     ArrayList var23 = var22;
                     if (var22 == null) {
                        var23 = new ArrayList();
                        if (var6 != null) {
                           var6.set(var16, var23);
                        } else {
                           var15.put(var16, var23);
                        }
                     }

                     var23.add(var17);
                  }
               }

               var8 = new StringWriter();
               var9 = new StringWriter();
               break;
            }

            if (var11 != 61) {
               if (var10) {
                  var8.write(var11);
               } else {
                  var9.write(var11);
               }
            } else if (var10) {
               var10 = false;
            } else {
               var9.write(var11);
            }
         }
      } while(var11 != -1);

      var7.setValues();
   }

   public static void parse(String var0, Object var1) {
      parse(var0, var1, true);
   }

   public static void parse(String var0, Object var1, boolean var2) {
      if (var0 != null) {
         try {
            StringReader var3 = new StringReader(var0);
            parse((Reader)var3, var1, var2);
         } catch (IOException var4) {
            throw Throwables.propagate(var4);
         }
      }
   }

   private static Object parseValue(Type var0, List<Type> var1, String var2) {
      return Data.parsePrimitiveValue(Data.resolveWildcardTypeOrTypeVariable(var1, var0), var2);
   }

   public <T> T parseAndClose(InputStream var1, Charset var2, Class<T> var3) throws IOException {
      return this.parseAndClose(new InputStreamReader(var1, var2), (Class)var3);
   }

   public Object parseAndClose(InputStream var1, Charset var2, Type var3) throws IOException {
      return this.parseAndClose(new InputStreamReader(var1, var2), (Type)var3);
   }

   public <T> T parseAndClose(Reader var1, Class<T> var2) throws IOException {
      return this.parseAndClose(var1, (Type)var2);
   }

   public Object parseAndClose(Reader var1, Type var2) throws IOException {
      Preconditions.checkArgument(var2 instanceof Class, "dataType has to be of type Class<?>");
      Object var3 = Types.newInstance((Class)var2);
      parse((Reader)(new BufferedReader(var1)), var3);
      return var3;
   }
}

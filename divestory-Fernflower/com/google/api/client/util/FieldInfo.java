package com.google.api.client.util;

import com.google.common.base.Ascii;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Map;
import java.util.WeakHashMap;

public class FieldInfo {
   private static final Map<Field, FieldInfo> CACHE = new WeakHashMap();
   private final Field field;
   private final boolean isPrimitive;
   private final String name;
   private final Method[] setters;

   FieldInfo(Field var1, String var2) {
      this.field = var1;
      if (var2 == null) {
         var2 = null;
      } else {
         var2 = var2.intern();
      }

      this.name = var2;
      this.isPrimitive = Data.isPrimitive(this.getType());
      this.setters = this.settersMethodForField(var1);
   }

   public static Object getFieldValue(Field var0, Object var1) {
      try {
         Object var3 = var0.get(var1);
         return var3;
      } catch (IllegalAccessException var2) {
         throw new IllegalArgumentException(var2);
      }
   }

   public static FieldInfo of(Enum<?> param0) {
      // $FF: Couldn't be decompiled
   }

   public static FieldInfo of(Field var0) {
      String var1 = null;
      if (var0 == null) {
         return null;
      } else {
         Map var2 = CACHE;
         synchronized(var2){}

         Throwable var10000;
         boolean var10001;
         label1416: {
            FieldInfo var3;
            boolean var4;
            try {
               var3 = (FieldInfo)CACHE.get(var0);
               var4 = var0.isEnumConstant();
            } catch (Throwable var187) {
               var10000 = var187;
               var10001 = false;
               break label1416;
            }

            label1421: {
               FieldInfo var5 = var3;
               if (var3 == null) {
                  label1420: {
                     if (!var4) {
                        var5 = var3;

                        try {
                           if (Modifier.isStatic(var0.getModifiers())) {
                              break label1420;
                           }
                        } catch (Throwable var186) {
                           var10000 = var186;
                           var10001 = false;
                           break label1416;
                        }
                     }

                     String var190;
                     if (var4) {
                        Value var189;
                        try {
                           var189 = (Value)var0.getAnnotation(Value.class);
                        } catch (Throwable var183) {
                           var10000 = var183;
                           var10001 = false;
                           break label1416;
                        }

                        if (var189 != null) {
                           try {
                              var190 = var189.value();
                           } catch (Throwable var182) {
                              var10000 = var182;
                              var10001 = false;
                              break label1416;
                           }
                        } else {
                           try {
                              if ((NullValue)var0.getAnnotation(NullValue.class) == null) {
                                 break label1421;
                              }
                           } catch (Throwable var185) {
                              var10000 = var185;
                              var10001 = false;
                              break label1416;
                           }

                           var190 = var1;
                        }
                     } else {
                        Key var191;
                        try {
                           var191 = (Key)var0.getAnnotation(Key.class);
                        } catch (Throwable var181) {
                           var10000 = var181;
                           var10001 = false;
                           break label1416;
                        }

                        if (var191 == null) {
                           try {
                              return null;
                           } catch (Throwable var177) {
                              var10000 = var177;
                              var10001 = false;
                              break label1416;
                           }
                        }

                        try {
                           var190 = var191.value();
                           var0.setAccessible(true);
                        } catch (Throwable var180) {
                           var10000 = var180;
                           var10001 = false;
                           break label1416;
                        }
                     }

                     var1 = var190;

                     try {
                        if ("##default".equals(var190)) {
                           var1 = var0.getName();
                        }
                     } catch (Throwable var184) {
                        var10000 = var184;
                        var10001 = false;
                        break label1416;
                     }

                     try {
                        var5 = new FieldInfo(var0, var1);
                        CACHE.put(var0, var5);
                     } catch (Throwable var179) {
                        var10000 = var179;
                        var10001 = false;
                        break label1416;
                     }
                  }
               }

               try {
                  return var5;
               } catch (Throwable var178) {
                  var10000 = var178;
                  var10001 = false;
                  break label1416;
               }
            }

            label1370:
            try {
               return null;
            } catch (Throwable var176) {
               var10000 = var176;
               var10001 = false;
               break label1370;
            }
         }

         while(true) {
            Throwable var188 = var10000;

            try {
               throw var188;
            } catch (Throwable var175) {
               var10000 = var175;
               var10001 = false;
               continue;
            }
         }
      }
   }

   public static void setFieldValue(Field var0, Object var1, Object var2) {
      Object var3;
      label24: {
         if (Modifier.isFinal(var0.getModifiers())) {
            var3 = getFieldValue(var0, var1);
            if (var2 == null) {
               if (var3 != null) {
                  break label24;
               }
            } else if (!var2.equals(var3)) {
               break label24;
            }
         } else {
            try {
               var0.set(var1, var2);
            } catch (SecurityException var5) {
               throw new IllegalArgumentException(var5);
            } catch (IllegalAccessException var6) {
               throw new IllegalArgumentException(var6);
            }
         }

         return;
      }

      StringBuilder var4 = new StringBuilder();
      var4.append("expected final value <");
      var4.append(var3);
      var4.append("> but was <");
      var4.append(var2);
      var4.append("> on ");
      var4.append(var0.getName());
      var4.append(" field in ");
      var4.append(var1.getClass().getName());
      throw new IllegalArgumentException(var4.toString());
   }

   private Method[] settersMethodForField(Field var1) {
      ArrayList var2 = new ArrayList();
      Method[] var3 = var1.getDeclaringClass().getDeclaredMethods();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Method var6 = var3[var5];
         String var7 = Ascii.toLowerCase(var6.getName());
         StringBuilder var8 = new StringBuilder();
         var8.append("set");
         var8.append(Ascii.toLowerCase(var1.getName()));
         if (var7.equals(var8.toString()) && var6.getParameterTypes().length == 1) {
            var2.add(var6);
         }
      }

      return (Method[])var2.toArray(new Method[0]);
   }

   public <T extends Enum<T>> T enumValue() {
      return Enum.valueOf(this.field.getDeclaringClass(), this.field.getName());
   }

   public ClassInfo getClassInfo() {
      return ClassInfo.of(this.field.getDeclaringClass());
   }

   public Field getField() {
      return this.field;
   }

   public Type getGenericType() {
      return this.field.getGenericType();
   }

   public String getName() {
      return this.name;
   }

   public Class<?> getType() {
      return this.field.getType();
   }

   public Object getValue(Object var1) {
      return getFieldValue(this.field, var1);
   }

   public boolean isFinal() {
      return Modifier.isFinal(this.field.getModifiers());
   }

   public boolean isPrimitive() {
      return this.isPrimitive;
   }

   public void setValue(Object var1, Object var2) {
      Method[] var3 = this.setters;
      if (var3.length > 0) {
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            Method var6 = var3[var5];
            if (var2 == null || var6.getParameterTypes()[0].isAssignableFrom(var2.getClass())) {
               try {
                  var6.invoke(var1, var2);
                  return;
               } catch (InvocationTargetException | IllegalAccessException var7) {
               }
            }
         }
      }

      setFieldValue(this.field, var1, var2);
   }
}

package com.google.android.gms.internal.drive;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.Map.Entry;

final class zzlt {
   static String zza(zzlq var0, String var1) {
      StringBuilder var2 = new StringBuilder();
      var2.append("# ");
      var2.append(var1);
      zza(var0, var2, 0);
      return var2.toString();
   }

   private static void zza(zzlq var0, StringBuilder var1, int var2) {
      HashMap var3 = new HashMap();
      HashMap var4 = new HashMap();
      TreeSet var5 = new TreeSet();
      Method[] var6 = var0.getClass().getDeclaredMethods();
      int var7 = var6.length;

      Method var9;
      for(int var8 = 0; var8 < var7; ++var8) {
         var9 = var6[var8];
         var4.put(var9.getName(), var9);
         if (var9.getParameterTypes().length == 0) {
            var3.put(var9.getName(), var9);
            if (var9.getName().startsWith("get")) {
               var5.add(var9.getName());
            }
         }
      }

      Iterator var15 = var5.iterator();

      while(true) {
         while(var15.hasNext()) {
            String var17 = (String)var15.next();
            String var10 = var17.replaceFirst("get", "");
            boolean var11 = var10.endsWith("List");
            boolean var12 = true;
            String var13;
            String var16;
            Method var20;
            if (var11 && !var10.endsWith("OrBuilderList") && !var10.equals("List")) {
               var13 = String.valueOf(var10.substring(0, 1).toLowerCase());
               var16 = String.valueOf(var10.substring(1, var10.length() - 4));
               if (var16.length() != 0) {
                  var16 = var13.concat(var16);
               } else {
                  var16 = new String(var13);
               }

               var20 = (Method)var3.get(var17);
               if (var20 != null && var20.getReturnType().equals(List.class)) {
                  zza(var1, var2, zzo(var16), zzkk.zza((Method)var20, (Object)var0, (Object[])()));
                  continue;
               }
            }

            if (var10.endsWith("Map") && !var10.equals("Map")) {
               var13 = String.valueOf(var10.substring(0, 1).toLowerCase());
               var16 = String.valueOf(var10.substring(1, var10.length() - 3));
               if (var16.length() != 0) {
                  var16 = var13.concat(var16);
               } else {
                  var16 = new String(var13);
               }

               var9 = (Method)var3.get(var17);
               if (var9 != null && var9.getReturnType().equals(Map.class) && !var9.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(var9.getModifiers())) {
                  zza(var1, var2, zzo(var16), zzkk.zza((Method)var9, (Object)var0, (Object[])()));
                  continue;
               }
            }

            var16 = String.valueOf(var10);
            if (var16.length() != 0) {
               var16 = "set".concat(var16);
            } else {
               var16 = new String("set");
            }

            if ((Method)var4.get(var16) != null) {
               if (var10.endsWith("Bytes")) {
                  var16 = String.valueOf(var10.substring(0, var10.length() - 5));
                  if (var16.length() != 0) {
                     var16 = "get".concat(var16);
                  } else {
                     var16 = new String("get");
                  }

                  if (var3.containsKey(var16)) {
                     continue;
                  }
               }

               var16 = String.valueOf(var10.substring(0, 1).toLowerCase());
               var17 = String.valueOf(var10.substring(1));
               if (var17.length() != 0) {
                  var16 = var16.concat(var17);
               } else {
                  var16 = new String(var16);
               }

               var17 = String.valueOf(var10);
               if (var17.length() != 0) {
                  var17 = "get".concat(var17);
               } else {
                  var17 = new String("get");
               }

               var20 = (Method)var3.get(var17);
               var17 = String.valueOf(var10);
               if (var17.length() != 0) {
                  var17 = "has".concat(var17);
               } else {
                  var17 = new String("has");
               }

               var9 = (Method)var3.get(var17);
               if (var20 != null) {
                  Object var18 = zzkk.zza((Method)var20, (Object)var0, (Object[])());
                  if (var9 == null) {
                     label146: {
                        label145: {
                           if (var18 instanceof Boolean) {
                              if (!(Boolean)var18) {
                                 break label145;
                              }
                           } else if (var18 instanceof Integer) {
                              if ((Integer)var18 == 0) {
                                 break label145;
                              }
                           } else if (var18 instanceof Float) {
                              if ((Float)var18 == 0.0F) {
                                 break label145;
                              }
                           } else if (var18 instanceof Double) {
                              if ((Double)var18 == 0.0D) {
                                 break label145;
                              }
                           } else {
                              if (var18 instanceof String) {
                                 var11 = var18.equals("");
                                 break label146;
                              }

                              if (var18 instanceof zzjc) {
                                 var11 = var18.equals(zzjc.zznq);
                                 break label146;
                              }

                              if (var18 instanceof zzlq) {
                                 if (var18 == ((zzlq)var18).zzda()) {
                                    break label145;
                                 }
                              } else if (var18 instanceof Enum && ((Enum)var18).ordinal() == 0) {
                                 break label145;
                              }
                           }

                           var11 = false;
                           break label146;
                        }

                        var11 = true;
                     }

                     if (!var11) {
                        var11 = var12;
                     } else {
                        var11 = false;
                     }
                  } else {
                     var11 = (Boolean)zzkk.zza((Method)var9, (Object)var0, (Object[])());
                  }

                  if (var11) {
                     zza(var1, var2, zzo(var16), var18);
                  }
               }
            }
         }

         if (var0 instanceof zzkk.zzc) {
            Iterator var19 = ((zzkk.zzc)var0).zzrw.iterator();
            if (var19.hasNext()) {
               ((Entry)var19.next()).getKey();
               throw new NoSuchMethodError();
            }
         }

         zzkk var14 = (zzkk)var0;
         if (var14.zzrq != null) {
            var14.zzrq.zza(var1, var2);
         }

         return;
      }
   }

   static final void zza(StringBuilder var0, int var1, String var2, Object var3) {
      Iterator var8;
      if (var3 instanceof List) {
         var8 = ((List)var3).iterator();

         while(var8.hasNext()) {
            zza(var0, var1, var2, var8.next());
         }

      } else if (var3 instanceof Map) {
         var8 = ((Map)var3).entrySet().iterator();

         while(var8.hasNext()) {
            zza(var0, var1, var2, (Entry)var8.next());
         }

      } else {
         var0.append('\n');
         byte var4 = 0;
         byte var5 = 0;

         int var6;
         for(var6 = 0; var6 < var1; ++var6) {
            var0.append(' ');
         }

         var0.append(var2);
         if (var3 instanceof String) {
            var0.append(": \"");
            var0.append(zzmt.zzc(zzjc.zzk((String)var3)));
            var0.append('"');
         } else if (var3 instanceof zzjc) {
            var0.append(": \"");
            var0.append(zzmt.zzc((zzjc)var3));
            var0.append('"');
         } else if (var3 instanceof zzkk) {
            var0.append(" {");
            zza((zzkk)var3, var0, var1 + 2);
            var0.append("\n");

            for(var6 = var5; var6 < var1; ++var6) {
               var0.append(' ');
            }

            var0.append("}");
         } else if (!(var3 instanceof Entry)) {
            var0.append(": ");
            var0.append(var3.toString());
         } else {
            var0.append(" {");
            Entry var7 = (Entry)var3;
            var6 = var1 + 2;
            zza(var0, var6, "key", var7.getKey());
            zza(var0, var6, "value", var7.getValue());
            var0.append("\n");

            for(var6 = var4; var6 < var1; ++var6) {
               var0.append(' ');
            }

            var0.append("}");
         }
      }
   }

   private static final String zzo(String var0) {
      StringBuilder var1 = new StringBuilder();

      for(int var2 = 0; var2 < var0.length(); ++var2) {
         char var3 = var0.charAt(var2);
         if (Character.isUpperCase(var3)) {
            var1.append("_");
         }

         var1.append(Character.toLowerCase(var3));
      }

      return var1.toString();
   }
}

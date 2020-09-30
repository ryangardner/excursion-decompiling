/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.android.gms.internal.drive;

import com.google.android.gms.internal.drive.zzjc;
import com.google.android.gms.internal.drive.zzkb;
import com.google.android.gms.internal.drive.zzkk;
import com.google.android.gms.internal.drive.zzlq;
import com.google.android.gms.internal.drive.zzmt;
import com.google.android.gms.internal.drive.zzmy;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

final class zzlt {
    static String zza(zzlq zzlq2, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("# ");
        stringBuilder.append(string2);
        zzlt.zza(zzlq2, stringBuilder, 0);
        return stringBuilder.toString();
    }

    /*
     * Unable to fully structure code
     */
    private static void zza(zzlq var0, StringBuilder var1_1, int var2_2) {
        var3_3 = new HashMap<String, Object>();
        var4_4 = new HashMap<String, Method>();
        var5_5 = new TreeSet<E>();
        var6_6 = var0.getClass().getDeclaredMethods();
        var7_7 = ((Method[])var6_6).length;
        for (var8_8 = 0; var8_8 < var7_7; ++var8_8) {
            var9_9 = var6_6[var8_8];
            var4_4.put(var9_9.getName(), (Method)var9_9);
            if (var9_9.getParameterTypes().length != 0) continue;
            var3_3.put(var9_9.getName(), var9_9);
            if (!var9_9.getName().startsWith("get")) continue;
            var5_5.add(var9_9.getName());
        }
        var5_5 = var5_5.iterator();
        while (var5_5.hasNext()) {
            block18 : {
                block13 : {
                    block17 : {
                        block16 : {
                            block15 : {
                                block14 : {
                                    var9_9 = (String)var5_5.next();
                                    var10_10 = var9_9.replaceFirst("get", "");
                                    var11_11 = var10_10.endsWith("List");
                                    var12_12 = true;
                                    if (var11_11 && !var10_10.endsWith("OrBuilderList") && !var10_10.equals("List")) {
                                        var13_13 = String.valueOf(var10_10.substring(0, 1).toLowerCase());
                                        var6_6 = String.valueOf(var10_10.substring(1, var10_10.length() - 4));
                                        var6_6 = var6_6.length() != 0 ? var13_13.concat((String)var6_6) : new String((String)var13_13);
                                        var13_13 = (Method)var3_3.get(var9_9);
                                        if (var13_13 != null && var13_13.getReturnType().equals(List.class)) {
                                            zzlt.zza(var1_1, var2_2, zzlt.zzo((String)var6_6), zzkk.zza((Method)var13_13, var0, new Object[0]));
                                            continue;
                                        }
                                    }
                                    if (var10_10.endsWith("Map") && !var10_10.equals("Map")) {
                                        var13_13 = String.valueOf(var10_10.substring(0, 1).toLowerCase());
                                        var6_6 = String.valueOf(var10_10.substring(1, var10_10.length() - 3));
                                        var6_6 = var6_6.length() != 0 ? var13_13.concat((String)var6_6) : new String((String)var13_13);
                                        if ((var9_9 = (Method)var3_3.get(var9_9)) != null && var9_9.getReturnType().equals(Map.class) && !var9_9.isAnnotationPresent(Deprecated.class) && Modifier.isPublic(var9_9.getModifiers())) {
                                            zzlt.zza(var1_1, var2_2, zzlt.zzo((String)var6_6), zzkk.zza((Method)var9_9, var0, new Object[0]));
                                            continue;
                                        }
                                    }
                                    if ((Method)var4_4.get(var6_6 = (var6_6 = String.valueOf(var10_10)).length() != 0 ? "set".concat((String)var6_6) : new String("set")) == null || var10_10.endsWith("Bytes") && var3_3.containsKey(var6_6 = (var6_6 = String.valueOf(var10_10.substring(0, var10_10.length() - 5))).length() != 0 ? "get".concat((String)var6_6) : new String("get"))) continue;
                                    var6_6 = String.valueOf(var10_10.substring(0, 1).toLowerCase());
                                    var9_9 = String.valueOf(var10_10.substring(1));
                                    var6_6 = var9_9.length() != 0 ? var6_6.concat((String)var9_9) : new String((String)var6_6);
                                    var9_9 = String.valueOf(var10_10);
                                    var9_9 = var9_9.length() != 0 ? "get".concat((String)var9_9) : new String("get");
                                    var13_13 = (Method)var3_3.get(var9_9);
                                    var9_9 = String.valueOf(var10_10);
                                    var9_9 = var9_9.length() != 0 ? "has".concat((String)var9_9) : new String("has");
                                    var9_9 = (Method)var3_3.get(var9_9);
                                    if (var13_13 == null) continue;
                                    var10_10 = zzkk.zza((Method)var13_13, var0, new Object[0]);
                                    if (var9_9 != null) break block13;
                                    if (!(var10_10 instanceof Boolean)) break block14;
                                    if (((Boolean)var10_10).booleanValue()) ** GOTO lbl-1000
                                    ** GOTO lbl-1000
                                }
                                if (!(var10_10 instanceof Integer)) break block15;
                                if ((Integer)var10_10 != 0) ** GOTO lbl-1000
                                ** GOTO lbl-1000
                            }
                            if (!(var10_10 instanceof Float)) break block16;
                            if (((Float)var10_10).floatValue() != 0.0f) ** GOTO lbl-1000
                            ** GOTO lbl-1000
                        }
                        if (!(var10_10 instanceof Double)) break block17;
                        if ((Double)var10_10 != 0.0) ** GOTO lbl-1000
                        ** GOTO lbl-1000
                    }
                    if (var10_10 instanceof String) {
                        var11_11 = var10_10.equals("");
                    } else if (var10_10 instanceof zzjc) {
                        var11_11 = var10_10.equals(zzjc.zznq);
                    } else if (var10_10 instanceof zzlq != false ? var10_10 == ((zzlq)var10_10).zzda() : var10_10 instanceof Enum != false && ((Enum)var10_10).ordinal() == 0) lbl-1000: // 5 sources:
                    {
                        var11_11 = true;
                    } else lbl-1000: // 5 sources:
                    {
                        var11_11 = false;
                    }
                    var11_11 = !var11_11 ? var12_12 : false;
                    break block18;
                }
                var11_11 = (Boolean)zzkk.zza((Method)var9_9, var0, new Object[0]);
            }
            if (!var11_11) continue;
            zzlt.zza(var1_1, var2_2, zzlt.zzo((String)var6_6), var10_10);
        }
        if (var0 instanceof zzkk.zzc && (var6_6 = ((zzkk.zzc)var0).zzrw.iterator()).hasNext()) {
            ((Map.Entry)var6_6.next()).getKey();
            throw new NoSuchMethodError();
        }
        var0 = (zzkk)var0;
        if (var0.zzrq == null) return;
        var0.zzrq.zza(var1_1, var2_2);
    }

    static final void zza(StringBuilder stringBuilder, int n, String object, Object iterator2) {
        int n2;
        if (iterator2 instanceof List) {
            iterator2 = ((List)((Object)iterator2)).iterator();
            while (iterator2.hasNext()) {
                zzlt.zza(stringBuilder, n, (String)object, iterator2.next());
            }
            return;
        }
        if (iterator2 instanceof Map) {
            iterator2 = ((Map)((Object)iterator2)).entrySet().iterator();
            while (iterator2.hasNext()) {
                zzlt.zza(stringBuilder, n, (String)object, (Map.Entry)iterator2.next());
            }
            return;
        }
        stringBuilder.append('\n');
        int n3 = 0;
        int n4 = 0;
        for (n2 = 0; n2 < n; ++n2) {
            stringBuilder.append(' ');
        }
        stringBuilder.append((String)object);
        if (iterator2 instanceof String) {
            stringBuilder.append(": \"");
            stringBuilder.append(zzmt.zzc(zzjc.zzk((String)((Object)iterator2))));
            stringBuilder.append('\"');
            return;
        }
        if (iterator2 instanceof zzjc) {
            stringBuilder.append(": \"");
            stringBuilder.append(zzmt.zzc((zzjc)((Object)iterator2)));
            stringBuilder.append('\"');
            return;
        }
        if (iterator2 instanceof zzkk) {
            stringBuilder.append(" {");
            zzlt.zza((zzkk)((Object)iterator2), stringBuilder, n + 2);
            stringBuilder.append("\n");
            n2 = n4;
            do {
                if (n2 >= n) {
                    stringBuilder.append("}");
                    return;
                }
                stringBuilder.append(' ');
                ++n2;
            } while (true);
        }
        if (!(iterator2 instanceof Map.Entry)) {
            stringBuilder.append(": ");
            stringBuilder.append(iterator2.toString());
            return;
        }
        stringBuilder.append(" {");
        object = (Map.Entry)((Object)iterator2);
        n2 = n + 2;
        zzlt.zza(stringBuilder, n2, "key", object.getKey());
        zzlt.zza(stringBuilder, n2, "value", object.getValue());
        stringBuilder.append("\n");
        n2 = n3;
        do {
            if (n2 >= n) {
                stringBuilder.append("}");
                return;
            }
            stringBuilder.append(' ');
            ++n2;
        } while (true);
    }

    private static final String zzo(String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        int n = 0;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (Character.isUpperCase(c)) {
                stringBuilder.append("_");
            }
            stringBuilder.append(Character.toLowerCase(c));
            ++n;
        }
        return stringBuilder.toString();
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.util;

import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.Objects;
import com.google.api.client.util.Preconditions;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public final class ClassInfo {
    private static final ConcurrentMap<Class<?>, ClassInfo> CACHE = new ConcurrentHashMap();
    private static final ConcurrentMap<Class<?>, ClassInfo> CACHE_IGNORE_CASE = new ConcurrentHashMap();
    private final Class<?> clazz;
    private final boolean ignoreCase;
    private final IdentityHashMap<String, FieldInfo> nameToFieldInfoMap = new IdentityHashMap();
    final List<String> names;

    /*
     * WARNING - void declaration
     */
    private ClassInfo(Class<?> list, boolean bl) {
        String string2;
        this.clazz = list;
        this.ignoreCase = bl;
        boolean bl2 = !bl || !((Class)((Object)list)).isEnum();
        StringBuilder object3 = new StringBuilder();
        object3.append("cannot ignore case on an enum: ");
        object3.append(list);
        Preconditions.checkArgument(bl2, object3.toString());
        TreeSet<String> treeSet = new TreeSet<String>(new Comparator<String>(){

            @Override
            public int compare(String string2, String string3) {
                if (Objects.equal(string2, string3)) {
                    return 0;
                }
                if (string2 == null) {
                    return -1;
                }
                if (string3 != null) return string2.compareTo(string3);
                return 1;
            }
        });
        for (Field field : ((Class)((Object)list)).getDeclaredFields()) {
            void var4_8;
            Object object;
            FieldInfo fieldInfo = FieldInfo.of(field);
            if (fieldInfo == null) continue;
            String string3 = string2 = fieldInfo.getName();
            if (bl) {
                String string4 = string2.toLowerCase(Locale.US).intern();
            }
            bl2 = (object = this.nameToFieldInfoMap.get(var4_8)) == null;
            string2 = bl ? "case-insensitive " : "";
            object = object == null ? null : ((FieldInfo)object).getField();
            Preconditions.checkArgument(bl2, "two fields have the same %sname <%s>: %s and %s", string2, var4_8, field, object);
            this.nameToFieldInfoMap.put((String)var4_8, fieldInfo);
            treeSet.add((String)var4_8);
        }
        if ((list = ((Class)((Object)list)).getSuperclass()) != null) {
            list = ClassInfo.of(list, bl);
            treeSet.addAll(((ClassInfo)list).names);
            for (Map.Entry entry : ((ClassInfo)list).nameToFieldInfoMap.entrySet()) {
                string2 = (String)entry.getKey();
                if (this.nameToFieldInfoMap.containsKey(string2)) continue;
                this.nameToFieldInfoMap.put(string2, (FieldInfo)entry.getValue());
            }
        }
        list = treeSet.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList<String>(treeSet));
        this.names = list;
    }

    public static ClassInfo of(Class<?> class_) {
        return ClassInfo.of(class_, false);
    }

    public static ClassInfo of(Class<?> object, boolean bl) {
        if (object == null) {
            return null;
        }
        ConcurrentMap<Class<?>, ClassInfo> concurrentMap = bl ? CACHE_IGNORE_CASE : CACHE;
        ClassInfo classInfo = (ClassInfo)concurrentMap.get(object);
        Object object2 = classInfo;
        if (classInfo != null) return object2;
        object2 = new ClassInfo((Class<?>)object, bl);
        if ((object = concurrentMap.putIfAbsent((Class<?>)object, (ClassInfo)object2)) != null) return object;
        return object2;
    }

    public Field getField(String object) {
        if ((object = this.getFieldInfo((String)object)) != null) return ((FieldInfo)object).getField();
        return null;
    }

    public FieldInfo getFieldInfo(String string2) {
        String string3 = string2;
        if (string2 == null) return this.nameToFieldInfoMap.get(string3);
        string3 = string2;
        if (this.ignoreCase) {
            string3 = string2.toLowerCase(Locale.US);
        }
        string3 = string3.intern();
        return this.nameToFieldInfoMap.get(string3);
    }

    public Collection<FieldInfo> getFieldInfos() {
        return Collections.unmodifiableCollection(this.nameToFieldInfoMap.values());
    }

    public final boolean getIgnoreCase() {
        return this.ignoreCase;
    }

    public Collection<String> getNames() {
        return this.names;
    }

    public Class<?> getUnderlyingClass() {
        return this.clazz;
    }

    public boolean isEnum() {
        return this.clazz.isEnum();
    }

}


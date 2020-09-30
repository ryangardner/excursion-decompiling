/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.HttpMediaType;
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
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class UrlEncodedParser
implements ObjectParser {
    public static final String CONTENT_TYPE = "application/x-www-form-urlencoded";
    public static final String MEDIA_TYPE = new HttpMediaType("application/x-www-form-urlencoded").setCharsetParameter(Charsets.UTF_8).build();

    public static void parse(Reader reader, Object object) throws IOException {
        UrlEncodedParser.parse(reader, object, true);
    }

    public static void parse(Reader reader, Object object, boolean bl) throws IOException {
        int n;
        Map map = object.getClass();
        ClassInfo classInfo = ClassInfo.of(map);
        List<Type> list = Arrays.asList(map);
        GenericData genericData = GenericData.class.isAssignableFrom((Class<?>)((Object)map)) ? (GenericData)object : null;
        map = Map.class.isAssignableFrom((Class<?>)((Object)map)) ? (Map)object : null;
        ArrayValueMap arrayValueMap = new ArrayValueMap(object);
        Collection<Object> collection = new StringWriter();
        Object object2 = new StringWriter();
        do {
            boolean bl2 = true;
            while ((n = reader.read()) != -1 && n != 38) {
                if (n != 61) {
                    if (bl2) {
                        ((StringWriter)((Object)collection)).write(n);
                        continue;
                    }
                    ((StringWriter)object2).write(n);
                    continue;
                }
                if (bl2) {
                    bl2 = false;
                    continue;
                }
                ((StringWriter)object2).write(n);
            }
            Object object3 = ((StringWriter)((Object)collection)).toString();
            collection = object3;
            if (bl) {
                collection = CharEscapers.decodeUri((String)object3);
            }
            if (((String)((Object)collection)).length() != 0) {
                Object object4;
                object2 = object3 = ((StringWriter)object2).toString();
                if (bl) {
                    object2 = CharEscapers.decodeUri((String)object3);
                }
                if ((object4 = classInfo.getFieldInfo((String)((Object)collection))) != null) {
                    Type type = Data.resolveWildcardTypeOrTypeVariable(list, ((FieldInfo)object4).getGenericType());
                    if (Types.isArray(type)) {
                        collection = Types.getRawArrayComponentType(list, Types.getArrayComponentType(type));
                        arrayValueMap.put(((FieldInfo)object4).getField(), (Class<?>)((Object)collection), UrlEncodedParser.parseValue(collection, list, (String)object2));
                    } else if (Types.isAssignableToOrFrom(Types.getRawArrayComponentType(list, type), Iterable.class)) {
                        object3 = (Collection)((FieldInfo)object4).getValue(object);
                        collection = object3;
                        if (object3 == null) {
                            collection = Data.newCollectionInstance(type);
                            ((FieldInfo)object4).setValue(object, collection);
                        }
                        object3 = type == Object.class ? null : Types.getIterableParameter(type);
                        collection.add(UrlEncodedParser.parseValue((Type)object3, list, (String)object2));
                    } else {
                        ((FieldInfo)object4).setValue(object, UrlEncodedParser.parseValue(type, list, (String)object2));
                    }
                } else if (map != null) {
                    object3 = object4 = (ArrayList)map.get(collection);
                    if (object4 == null) {
                        object3 = new ArrayList<Object>();
                        if (genericData != null) {
                            genericData.set((String)((Object)collection), object3);
                        } else {
                            map.put(collection, object3);
                        }
                    }
                    ((ArrayList)object3).add(object2);
                }
            }
            collection = new StringWriter();
            object2 = new StringWriter();
        } while (n != -1);
        arrayValueMap.setValues();
    }

    public static void parse(String string2, Object object) {
        UrlEncodedParser.parse(string2, object, true);
    }

    public static void parse(String string2, Object object, boolean bl) {
        if (string2 == null) {
            return;
        }
        try {
            StringReader stringReader = new StringReader(string2);
            UrlEncodedParser.parse(stringReader, object, bl);
            return;
        }
        catch (IOException iOException) {
            throw Throwables.propagate(iOException);
        }
    }

    private static Object parseValue(Type type, List<Type> list, String string2) {
        return Data.parsePrimitiveValue(Data.resolveWildcardTypeOrTypeVariable(list, type), string2);
    }

    @Override
    public <T> T parseAndClose(InputStream inputStream2, Charset charset, Class<T> class_) throws IOException {
        return this.parseAndClose((Reader)new InputStreamReader(inputStream2, charset), class_);
    }

    @Override
    public Object parseAndClose(InputStream inputStream2, Charset charset, Type type) throws IOException {
        return this.parseAndClose((Reader)new InputStreamReader(inputStream2, charset), type);
    }

    @Override
    public <T> T parseAndClose(Reader reader, Class<T> class_) throws IOException {
        return (T)this.parseAndClose(reader, (Type)class_);
    }

    @Override
    public Object parseAndClose(Reader reader, Type type) throws IOException {
        Preconditions.checkArgument(type instanceof Class, "dataType has to be of type Class<?>");
        type = Types.newInstance((Class)type);
        UrlEncodedParser.parse(new BufferedReader(reader), (Object)type);
        return type;
    }
}


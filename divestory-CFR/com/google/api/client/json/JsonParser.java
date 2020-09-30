/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.json;

import com.google.api.client.json.CustomizeJsonParser;
import com.google.api.client.json.GenericJson;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonPolymorphicTypeMap;
import com.google.api.client.json.JsonToken;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Sets;
import com.google.api.client.util.Types;
import java.io.Closeable;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public abstract class JsonParser
implements Closeable {
    private static WeakHashMap<Class<?>, Field> cachedTypemapFields = new WeakHashMap();
    private static final Lock lock = new ReentrantLock();

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     */
    private static Field getCachedTypemapFieldFor(Class<?> annotatedElement) {
        Object object = null;
        if (annotatedElement == null) {
            return null;
        }
        lock.lock();
        if (cachedTypemapFields.containsKey(annotatedElement)) {
            annotatedElement = cachedTypemapFields.get(annotatedElement);
            lock.unlock();
            return annotatedElement;
        }
        Iterator<FieldInfo> iterator2 = ClassInfo.of(annotatedElement).getFieldInfos().iterator();
        do {
            Object object2;
            int n;
            Field field;
            if (iterator2.hasNext()) {
                field = iterator2.next().getField();
                object2 = field.getAnnotation(JsonPolymorphicTypeMap.class);
                if (object2 == null) continue;
                boolean bl = object == null;
                Preconditions.checkArgument(bl, "Class contains more than one field with @JsonPolymorphicTypeMap annotation: %s", annotatedElement);
                Preconditions.checkArgument(Data.isPrimitive(field.getType()), "Field which has the @JsonPolymorphicTypeMap, %s, is not a supported type: %s", annotatedElement, field.getType());
                object = object2.typeDefinitions();
                object2 = Sets.newHashSet();
                bl = ((JsonPolymorphicTypeMap.TypeDef[])object).length > 0;
                Preconditions.checkArgument(bl, "@JsonPolymorphicTypeMap must have at least one @TypeDef");
                n = ((JsonPolymorphicTypeMap.TypeDef[])object).length;
            } else {
                cachedTypemapFields.put((Class<?>)annotatedElement, (Field)object);
                return object;
            }
            for (int i = 0; i < n; ++i) {
                JsonPolymorphicTypeMap.TypeDef typeDef = object[i];
                Preconditions.checkArgument(((HashSet)object2).add(typeDef.key()), "Class contains two @TypeDef annotations with identical key: %s", typeDef.key());
            }
            object = field;
        } while (true);
    }

    private void parse(ArrayList<Type> arrayList, Object object, CustomizeJsonParser customizeJsonParser) throws IOException {
        if (object instanceof GenericJson) {
            ((GenericJson)object).setFactory(this.getFactory());
        }
        Object object2 = this.startParsingObjectOrArray();
        Class<?> class_ = object.getClass();
        ClassInfo classInfo = ClassInfo.of(class_);
        boolean bl = GenericData.class.isAssignableFrom(class_);
        Object object3 = object2;
        if (!bl) {
            object3 = object2;
            if (Map.class.isAssignableFrom(class_)) {
                this.parseMap(null, (Map)object, Types.getMapValueParameter(class_), arrayList, customizeJsonParser);
                return;
            }
        }
        while (object3 == JsonToken.FIELD_NAME) {
            object2 = this.getText();
            this.nextToken();
            if (customizeJsonParser != null && customizeJsonParser.stopAt(object, (String)object2)) {
                return;
            }
            object3 = classInfo.getFieldInfo((String)object2);
            if (object3 != null) {
                if (((FieldInfo)object3).isFinal()) {
                    if (!((FieldInfo)object3).isPrimitive()) throw new IllegalArgumentException("final array/object fields are not supported");
                }
                object2 = ((FieldInfo)object3).getField();
                int n = arrayList.size();
                arrayList.add(((Field)object2).getGenericType());
                object2 = this.parseValue((Field)object2, ((FieldInfo)object3).getGenericType(), arrayList, object, customizeJsonParser, true);
                arrayList.remove(n);
                ((FieldInfo)object3).setValue(object, object2);
            } else if (bl) {
                ((GenericData)object).set((String)object2, this.parseValue(null, null, arrayList, object, customizeJsonParser, true));
            } else {
                if (customizeJsonParser != null) {
                    customizeJsonParser.handleUnrecognizedKey(object, (String)object2);
                }
                this.skipChildren();
            }
            object3 = this.nextToken();
        }
    }

    private <T> void parseArray(Field field, Collection<T> collection, Type type, ArrayList<Type> arrayList, CustomizeJsonParser customizeJsonParser) throws IOException {
        JsonToken jsonToken = this.startParsingObjectOrArray();
        while (jsonToken != JsonToken.END_ARRAY) {
            collection.add(this.parseValue(field, type, arrayList, collection, customizeJsonParser, true));
            jsonToken = this.nextToken();
        }
    }

    private void parseMap(Field field, Map<String, Object> map, Type type, ArrayList<Type> arrayList, CustomizeJsonParser customizeJsonParser) throws IOException {
        Object object = this.startParsingObjectOrArray();
        while (object == JsonToken.FIELD_NAME) {
            object = this.getText();
            this.nextToken();
            if (customizeJsonParser != null && customizeJsonParser.stopAt(map, (String)object)) {
                return;
            }
            map.put((String)object, this.parseValue(field, type, arrayList, map, customizeJsonParser, true));
            object = this.nextToken();
        }
    }

    /*
     * Exception decompiling
     */
    private final Object parseValue(Field var1_1, Type var2_2, ArrayList<Type> var3_4, Object var4_5, CustomizeJsonParser var5_6, boolean var6_7) throws IOException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [13[CASE]], but top level block is 1[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:607)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:696)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:184)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:129)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:906)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:797)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:225)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:109)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        // the.bytecode.club.bytecodeviewer.decompilers.CFRDecompiler.decompileToZip(CFRDecompiler.java:311)
        // the.bytecode.club.bytecodeviewer.gui.MainViewerGUI$14$1$3.run(MainViewerGUI.java:1211)
        throw new IllegalStateException("Decompilation failed");
    }

    private JsonToken startParsing() throws IOException {
        JsonToken jsonToken;
        JsonToken jsonToken2 = jsonToken = this.getCurrentToken();
        if (jsonToken == null) {
            jsonToken2 = this.nextToken();
        }
        boolean bl = jsonToken2 != null;
        Preconditions.checkArgument(bl, "no JSON input found");
        return jsonToken2;
    }

    private JsonToken startParsingObjectOrArray() throws IOException {
        JsonToken jsonToken = this.startParsing();
        int n = 1.$SwitchMap$com$google$api$client$json$JsonToken[jsonToken.ordinal()];
        boolean bl = true;
        if (n != 1) {
            if (n == 2) return this.nextToken();
            return jsonToken;
        }
        jsonToken = this.nextToken();
        boolean bl2 = bl;
        if (jsonToken != JsonToken.FIELD_NAME) {
            bl2 = jsonToken == JsonToken.END_OBJECT ? bl : false;
        }
        Preconditions.checkArgument(bl2, (Object)jsonToken);
        return jsonToken;
    }

    @Override
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

    public final <T> T parse(Class<T> class_) throws IOException {
        return this.parse(class_, null);
    }

    public final <T> T parse(Class<T> class_, CustomizeJsonParser customizeJsonParser) throws IOException {
        return (T)this.parse(class_, false, customizeJsonParser);
    }

    public Object parse(Type type, boolean bl) throws IOException {
        return this.parse(type, bl, null);
    }

    public Object parse(Type object, boolean bl, CustomizeJsonParser customizeJsonParser) throws IOException {
        try {
            if (!Void.class.equals(object)) {
                this.startParsing();
            }
            ArrayList<Type> arrayList = new ArrayList<Type>();
            object = this.parseValue(null, (Type)object, arrayList, null, customizeJsonParser, true);
            return object;
        }
        finally {
            if (bl) {
                this.close();
            }
        }
    }

    public final void parse(Object object) throws IOException {
        this.parse(object, null);
    }

    public final void parse(Object object, CustomizeJsonParser customizeJsonParser) throws IOException {
        ArrayList<Type> arrayList = new ArrayList<Type>();
        arrayList.add(object.getClass());
        this.parse(arrayList, object, customizeJsonParser);
    }

    public final <T> T parseAndClose(Class<T> class_) throws IOException {
        return this.parseAndClose(class_, null);
    }

    public final <T> T parseAndClose(Class<T> class_, CustomizeJsonParser customizeJsonParser) throws IOException {
        try {
            class_ = this.parse(class_, customizeJsonParser);
            return (T)class_;
        }
        finally {
            this.close();
        }
    }

    public final void parseAndClose(Object object) throws IOException {
        this.parseAndClose(object, null);
    }

    public final void parseAndClose(Object object, CustomizeJsonParser customizeJsonParser) throws IOException {
        try {
            this.parse(object, customizeJsonParser);
            return;
        }
        finally {
            this.close();
        }
    }

    public final <T> Collection<T> parseArray(Class<?> class_, Class<T> class_2) throws IOException {
        return this.parseArray(class_, class_2, null);
    }

    public final <T> Collection<T> parseArray(Class<?> object, Class<T> class_, CustomizeJsonParser customizeJsonParser) throws IOException {
        object = Data.newCollectionInstance(object);
        this.parseArray((Collection<? super T>)object, class_, customizeJsonParser);
        return object;
    }

    public final <T> void parseArray(Collection<? super T> collection, Class<T> class_) throws IOException {
        this.parseArray(collection, class_, null);
    }

    public final <T> void parseArray(Collection<? super T> collection, Class<T> class_, CustomizeJsonParser customizeJsonParser) throws IOException {
        this.parseArray(null, collection, class_, new ArrayList<Type>(), customizeJsonParser);
    }

    public final <T> Collection<T> parseArrayAndClose(Class<?> class_, Class<T> class_2) throws IOException {
        return this.parseArrayAndClose(class_, class_2, null);
    }

    public final <T> Collection<T> parseArrayAndClose(Class<?> object, Class<T> class_, CustomizeJsonParser customizeJsonParser) throws IOException {
        try {
            object = this.parseArray((Class<?>)object, class_, customizeJsonParser);
            return object;
        }
        finally {
            this.close();
        }
    }

    public final <T> void parseArrayAndClose(Collection<? super T> collection, Class<T> class_) throws IOException {
        this.parseArrayAndClose(collection, class_, null);
    }

    public final <T> void parseArrayAndClose(Collection<? super T> collection, Class<T> class_, CustomizeJsonParser customizeJsonParser) throws IOException {
        try {
            this.parseArray(collection, class_, customizeJsonParser);
            return;
        }
        finally {
            this.close();
        }
    }

    public abstract JsonParser skipChildren() throws IOException;

    public final String skipToKey(Set<String> set) throws IOException {
        Object object = this.startParsingObjectOrArray();
        while (object == JsonToken.FIELD_NAME) {
            object = this.getText();
            this.nextToken();
            if (set.contains(object)) {
                return object;
            }
            this.skipChildren();
            object = this.nextToken();
        }
        return null;
    }

    public final void skipToKey(String string2) throws IOException {
        this.skipToKey(Collections.singleton(string2));
    }

}


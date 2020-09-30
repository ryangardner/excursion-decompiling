/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.GenericUrl;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.Types;
import com.google.api.client.util.escape.CharEscapers;
import com.google.common.base.Splitter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

public class UriTemplate {
    private static final String COMPOSITE_NON_EXPLODE_JOINER = ",";
    private static final Map<Character, CompositeOutput> COMPOSITE_PREFIXES = new HashMap<Character, CompositeOutput>();

    static {
        CompositeOutput.values();
    }

    public static String expand(String string2, Object object, boolean bl) {
        Map<String, Object> map = UriTemplate.getMap(object);
        StringBuilder stringBuilder = new StringBuilder();
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            int n3 = string2.indexOf(123, n2);
            if (n3 == -1) {
                if (n2 == 0 && !bl) {
                    return string2;
                }
                stringBuilder.append(string2.substring(n2));
                break;
            }
            stringBuilder.append(string2.substring(n2, n3));
            int n4 = string2.indexOf(125, n3 + 2);
            object = string2.substring(n3 + 1, n4);
            CompositeOutput compositeOutput = UriTemplate.getCompositeOutput((String)object);
            ListIterator<String> listIterator = Splitter.on(',').splitToList((CharSequence)object).listIterator();
            n2 = 1;
            while (listIterator.hasNext()) {
                int n5;
                Object object2;
                String string3;
                object = listIterator.next();
                boolean bl2 = ((String)object).endsWith("*");
                n3 = listIterator.nextIndex() == 1 ? compositeOutput.getVarNameStartIndex() : 0;
                int n6 = n5 = ((String)object).length();
                if (bl2) {
                    n6 = n5 - 1;
                }
                if ((object2 = map.remove(string3 = ((String)object).substring(n3, n6))) == null) continue;
                if (n2 == 0) {
                    stringBuilder.append(compositeOutput.getExplodeJoiner());
                } else {
                    stringBuilder.append(compositeOutput.getOutputPrefix());
                    n2 = 0;
                }
                if (object2 instanceof Iterator) {
                    object = UriTemplate.getListPropertyValue(string3, (Iterator)object2, bl2, compositeOutput);
                } else if (!(object2 instanceof Iterable) && !object2.getClass().isArray()) {
                    if (object2.getClass().isEnum()) {
                        object = FieldInfo.of((Enum)object2).getName();
                        if (object == null) {
                            object = object2.toString();
                        }
                        object = UriTemplate.getSimpleValue(string3, (String)object, compositeOutput);
                    } else {
                        object = !Data.isValueOfPrimitiveType(object2) ? UriTemplate.getMapPropertyValue(string3, UriTemplate.getMap(object2), bl2, compositeOutput) : UriTemplate.getSimpleValue(string3, object2.toString(), compositeOutput);
                    }
                } else {
                    object = UriTemplate.getListPropertyValue(string3, Types.iterableOf(object2).iterator(), bl2, compositeOutput);
                }
                stringBuilder.append(object);
            }
            n2 = n4 + 1;
        }
        if (!bl) return stringBuilder.toString();
        GenericUrl.addQueryParams(map.entrySet(), stringBuilder, false);
        return stringBuilder.toString();
    }

    public static String expand(String charSequence, String string2, Object object, boolean bl) {
        Object object2;
        if (string2.startsWith("/")) {
            object2 = new GenericUrl((String)charSequence);
            ((GenericUrl)object2).setRawPath(null);
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(((GenericUrl)object2).build());
            ((StringBuilder)charSequence).append(string2);
            object2 = ((StringBuilder)charSequence).toString();
            return UriTemplate.expand((String)object2, object, bl);
        }
        object2 = string2;
        if (string2.startsWith("http://")) return UriTemplate.expand((String)object2, object, bl);
        if (string2.startsWith("https://")) {
            object2 = string2;
            return UriTemplate.expand((String)object2, object, bl);
        }
        object2 = new StringBuilder();
        ((StringBuilder)object2).append((String)charSequence);
        ((StringBuilder)object2).append(string2);
        object2 = ((StringBuilder)object2).toString();
        return UriTemplate.expand((String)object2, object, bl);
    }

    static CompositeOutput getCompositeOutput(String object) {
        CompositeOutput compositeOutput = COMPOSITE_PREFIXES.get(Character.valueOf(object.charAt(0)));
        object = compositeOutput;
        if (compositeOutput != null) return object;
        return CompositeOutput.SIMPLE;
    }

    private static String getListPropertyValue(String string2, Iterator<?> iterator2, boolean bl, CompositeOutput compositeOutput) {
        String string3;
        if (!iterator2.hasNext()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        if (bl) {
            string3 = compositeOutput.getExplodeJoiner();
        } else {
            if (compositeOutput.requiresVarAssignment()) {
                stringBuilder.append(CharEscapers.escapeUriPath(string2));
                stringBuilder.append("=");
            }
            string3 = COMPOSITE_NON_EXPLODE_JOINER;
        }
        while (iterator2.hasNext()) {
            if (bl && compositeOutput.requiresVarAssignment()) {
                stringBuilder.append(CharEscapers.escapeUriPath(string2));
                stringBuilder.append("=");
            }
            stringBuilder.append(compositeOutput.getEncodedValue(iterator2.next().toString()));
            if (!iterator2.hasNext()) continue;
            stringBuilder.append(string3);
        }
        return stringBuilder.toString();
    }

    private static Map<String, Object> getMap(Object entry) {
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        Iterator<Map.Entry<String, Object>> iterator2 = Data.mapOf(entry).entrySet().iterator();
        while (iterator2.hasNext()) {
            entry = iterator2.next();
            Object object = entry.getValue();
            if (object == null || Data.isNull(object)) continue;
            linkedHashMap.put(entry.getKey(), object);
        }
        return linkedHashMap;
    }

    private static String getMapPropertyValue(String string2, Map<String, Object> object, boolean bl, CompositeOutput compositeOutput) {
        if (object.isEmpty()) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder();
        String string3 = "=";
        Object object2 = COMPOSITE_NON_EXPLODE_JOINER;
        if (bl) {
            object2 = compositeOutput.getExplodeJoiner();
            string2 = string3;
            string3 = object2;
        } else {
            if (compositeOutput.requiresVarAssignment()) {
                stringBuilder.append(CharEscapers.escapeUriPath(string2));
                stringBuilder.append("=");
            }
            string2 = COMPOSITE_NON_EXPLODE_JOINER;
            string3 = object2;
        }
        object2 = object.entrySet().iterator();
        while (object2.hasNext()) {
            Object object3 = (Map.Entry)object2.next();
            object = compositeOutput.getEncodedValue((String)object3.getKey());
            object3 = compositeOutput.getEncodedValue(object3.getValue().toString());
            stringBuilder.append((String)object);
            stringBuilder.append(string2);
            stringBuilder.append((String)object3);
            if (!object2.hasNext()) continue;
            stringBuilder.append(string3);
        }
        return stringBuilder.toString();
    }

    private static String getSimpleValue(String string2, String string3, CompositeOutput compositeOutput) {
        if (!compositeOutput.requiresVarAssignment()) return compositeOutput.getEncodedValue(string3);
        return String.format("%s=%s", string2, compositeOutput.getEncodedValue(string3));
    }

    private static final class CompositeOutput
    extends Enum<CompositeOutput> {
        private static final /* synthetic */ CompositeOutput[] $VALUES;
        public static final /* enum */ CompositeOutput AMP;
        public static final /* enum */ CompositeOutput DOT;
        public static final /* enum */ CompositeOutput FORWARD_SLASH;
        public static final /* enum */ CompositeOutput HASH;
        public static final /* enum */ CompositeOutput PLUS;
        public static final /* enum */ CompositeOutput QUERY;
        public static final /* enum */ CompositeOutput SEMI_COLON;
        public static final /* enum */ CompositeOutput SIMPLE;
        private final String explodeJoiner;
        private final String outputPrefix;
        private final Character propertyPrefix;
        private final boolean requiresVarAssignment;
        private final boolean reservedExpansion;

        static {
            CompositeOutput compositeOutput;
            PLUS = new CompositeOutput(Character.valueOf('+'), "", UriTemplate.COMPOSITE_NON_EXPLODE_JOINER, false, true);
            HASH = new CompositeOutput(Character.valueOf('#'), "#", UriTemplate.COMPOSITE_NON_EXPLODE_JOINER, false, true);
            DOT = new CompositeOutput(Character.valueOf('.'), ".", ".", false, false);
            FORWARD_SLASH = new CompositeOutput(Character.valueOf('/'), "/", "/", false, false);
            SEMI_COLON = new CompositeOutput(Character.valueOf(';'), ";", ";", true, false);
            QUERY = new CompositeOutput(Character.valueOf('?'), "?", "&", true, false);
            AMP = new CompositeOutput(Character.valueOf('&'), "&", "&", true, false);
            SIMPLE = compositeOutput = new CompositeOutput(null, "", UriTemplate.COMPOSITE_NON_EXPLODE_JOINER, false, false);
            $VALUES = new CompositeOutput[]{PLUS, HASH, DOT, FORWARD_SLASH, SEMI_COLON, QUERY, AMP, compositeOutput};
        }

        private CompositeOutput(Character c, String string3, String string4, boolean bl, boolean bl2) {
            this.propertyPrefix = c;
            this.outputPrefix = Preconditions.checkNotNull(string3);
            this.explodeJoiner = Preconditions.checkNotNull(string4);
            this.requiresVarAssignment = bl;
            this.reservedExpansion = bl2;
            if (c == null) return;
            COMPOSITE_PREFIXES.put(c, this);
        }

        private String getEncodedValue(String string2) {
            if (!this.reservedExpansion) return CharEscapers.escapeUriConformant(string2);
            return CharEscapers.escapeUriPathWithoutReserved(string2);
        }

        public static CompositeOutput valueOf(String string2) {
            return Enum.valueOf(CompositeOutput.class, string2);
        }

        public static CompositeOutput[] values() {
            return (CompositeOutput[])$VALUES.clone();
        }

        String getExplodeJoiner() {
            return this.explodeJoiner;
        }

        String getOutputPrefix() {
            return this.outputPrefix;
        }

        int getVarNameStartIndex() {
            if (this.propertyPrefix != null) return 1;
            return 0;
        }

        boolean requiresVarAssignment() {
            return this.requiresVarAssignment;
        }
    }

}


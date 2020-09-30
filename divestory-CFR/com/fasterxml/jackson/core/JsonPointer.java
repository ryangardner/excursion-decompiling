/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.JsonStreamContext;
import com.fasterxml.jackson.core.io.NumberInput;

public class JsonPointer {
    protected static final JsonPointer EMPTY = new JsonPointer();
    public static final char SEPARATOR = '/';
    protected final String _asString;
    protected volatile JsonPointer _head;
    protected final int _matchingElementIndex;
    protected final String _matchingPropertyName;
    protected final JsonPointer _nextSegment;

    protected JsonPointer() {
        this._nextSegment = null;
        this._matchingPropertyName = "";
        this._matchingElementIndex = -1;
        this._asString = "";
    }

    protected JsonPointer(String string2, String string3, int n, JsonPointer jsonPointer) {
        this._asString = string2;
        this._nextSegment = jsonPointer;
        this._matchingPropertyName = string3;
        this._matchingElementIndex = n;
    }

    protected JsonPointer(String string2, String string3, JsonPointer jsonPointer) {
        this._asString = string2;
        this._nextSegment = jsonPointer;
        this._matchingPropertyName = string3;
        this._matchingElementIndex = JsonPointer._parseIndex(string3);
    }

    private static void _appendEscape(StringBuilder stringBuilder, char c) {
        char c2;
        if (c == '0') {
            c2 = c = (char)126;
        } else if (c == '1') {
            c2 = c = (char)47;
        } else {
            stringBuilder.append('~');
            c2 = c;
        }
        stringBuilder.append(c2);
    }

    private static void _appendEscaped(StringBuilder stringBuilder, String string2) {
        int n = string2.length();
        int n2 = 0;
        while (n2 < n) {
            char c = string2.charAt(n2);
            if (c == '/') {
                stringBuilder.append("~1");
            } else if (c == '~') {
                stringBuilder.append("~0");
            } else {
                stringBuilder.append(c);
            }
            ++n2;
        }
    }

    private static String _fullPath(JsonPointer object, String string2) {
        if (object == null) {
            object = new StringBuilder(string2.length() + 1);
            ((StringBuilder)object).append('/');
            JsonPointer._appendEscaped((StringBuilder)object, string2);
            return ((StringBuilder)object).toString();
        }
        String string3 = ((JsonPointer)object)._asString;
        object = new StringBuilder(string2.length() + 1 + string3.length());
        ((StringBuilder)object).append('/');
        JsonPointer._appendEscaped((StringBuilder)object, string2);
        ((StringBuilder)object).append(string3);
        return ((StringBuilder)object).toString();
    }

    private static final int _parseIndex(String string2) {
        int n = string2.length();
        int n2 = -1;
        if (n == 0) return -1;
        if (n > 10) {
            return -1;
        }
        char c = string2.charAt(0);
        int n3 = 1;
        if (c <= '0') {
            n3 = n2;
            if (n != 1) return n3;
            n3 = n2;
            if (c != '0') return n3;
            return 0;
        }
        if (c > '9') {
            return -1;
        }
        do {
            if (n3 >= n) {
                if (n != 10) return NumberInput.parseInt(string2);
                if (NumberInput.parseLong(string2) <= Integer.MAX_VALUE) return NumberInput.parseInt(string2);
                return -1;
            }
            n2 = string2.charAt(n3);
            if (n2 > 57) return -1;
            if (n2 < 48) {
                return -1;
            }
            ++n3;
        } while (true);
    }

    protected static JsonPointer _parseQuotedTail(String string2, int n) {
        int n2 = string2.length();
        StringBuilder stringBuilder = new StringBuilder(Math.max(16, n2));
        if (n > 2) {
            stringBuilder.append(string2, 1, n - 1);
        }
        int n3 = n + 1;
        JsonPointer._appendEscape(stringBuilder, string2.charAt(n));
        n = n3;
        while (n < n2) {
            char c = string2.charAt(n);
            if (c == '/') {
                return new JsonPointer(string2, stringBuilder.toString(), JsonPointer._parseTail(string2.substring(n)));
            }
            if (c == '~' && ++n < n2) {
                JsonPointer._appendEscape(stringBuilder, string2.charAt(n));
                ++n;
                continue;
            }
            stringBuilder.append(c);
        }
        return new JsonPointer(string2, stringBuilder.toString(), EMPTY);
    }

    /*
     * Unable to fully structure code
     */
    protected static JsonPointer _parseTail(String var0) {
        var1_1 = var0.length();
        var2_2 = 1;
        do lbl-1000: // 3 sources:
        {
            if (var2_2 >= var1_1) return new JsonPointer(var0, var0.substring(1), JsonPointer.EMPTY);
            var3_3 = var0.charAt(var2_2);
            if (var3_3 == '/') {
                return new JsonPointer(var0, var0.substring(1, var2_2), JsonPointer._parseTail(var0.substring(var2_2)));
            }
            var2_2 = var4_4 = var2_2 + 1;
            if (var3_3 != '~') ** GOTO lbl-1000
            var2_2 = var4_4;
        } while (var4_4 >= var1_1);
        return JsonPointer._parseQuotedTail(var0, var4_4);
    }

    public static JsonPointer compile(String string2) throws IllegalArgumentException {
        if (string2 == null) return EMPTY;
        if (string2.length() == 0) {
            return EMPTY;
        }
        if (string2.charAt(0) == '/') {
            return JsonPointer._parseTail(string2);
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Invalid input: JSON Pointer expression must start with '/': \"");
        stringBuilder.append(string2);
        stringBuilder.append("\"");
        throw new IllegalArgumentException(stringBuilder.toString());
    }

    public static JsonPointer empty() {
        return EMPTY;
    }

    public static JsonPointer forPath(JsonStreamContext object, boolean bl) {
        JsonStreamContext jsonStreamContext;
        block10 : {
            block11 : {
                if (object == null) {
                    return EMPTY;
                }
                jsonStreamContext = object;
                if (((JsonStreamContext)object).hasPathSegment()) break block10;
                if (!bl || !((JsonStreamContext)object).inRoot()) break block11;
                jsonStreamContext = object;
                if (((JsonStreamContext)object).hasCurrentIndex()) break block10;
            }
            jsonStreamContext = ((JsonStreamContext)object).getParent();
        }
        Object object2 = null;
        do {
            block13 : {
                block14 : {
                    block12 : {
                        if (jsonStreamContext == null) {
                            if (object2 != null) return object2;
                            return EMPTY;
                        }
                        if (!jsonStreamContext.inObject()) break block12;
                        String string2 = jsonStreamContext.getCurrentName();
                        object = string2;
                        if (string2 == null) {
                            object = "";
                        }
                        object = new JsonPointer(JsonPointer._fullPath(object2, (String)object), (String)object, (JsonPointer)object2);
                        break block13;
                    }
                    if (jsonStreamContext.inArray()) break block14;
                    object = object2;
                    if (!bl) break block13;
                }
                int n = jsonStreamContext.getCurrentIndex();
                object = String.valueOf(n);
                object = new JsonPointer(JsonPointer._fullPath((JsonPointer)object2, (String)object), (String)object, n, (JsonPointer)object2);
            }
            jsonStreamContext = jsonStreamContext.getParent();
            object2 = object;
        } while (true);
    }

    public static JsonPointer valueOf(String string2) {
        return JsonPointer.compile(string2);
    }

    protected JsonPointer _constructHead() {
        JsonPointer jsonPointer = this.last();
        if (jsonPointer == this) {
            return EMPTY;
        }
        int n = jsonPointer._asString.length();
        JsonPointer jsonPointer2 = this._nextSegment;
        String string2 = this._asString;
        return new JsonPointer(string2.substring(0, string2.length() - n), this._matchingPropertyName, this._matchingElementIndex, jsonPointer2._constructHead(n, jsonPointer));
    }

    protected JsonPointer _constructHead(int n, JsonPointer jsonPointer) {
        if (this == jsonPointer) {
            return EMPTY;
        }
        JsonPointer jsonPointer2 = this._nextSegment;
        String string2 = this._asString;
        return new JsonPointer(string2.substring(0, string2.length() - n), this._matchingPropertyName, this._matchingElementIndex, jsonPointer2._constructHead(n, jsonPointer));
    }

    public JsonPointer append(JsonPointer jsonPointer) {
        Object object = EMPTY;
        if (this == object) {
            return jsonPointer;
        }
        if (jsonPointer == object) {
            return this;
        }
        CharSequence charSequence = this._asString;
        object = charSequence;
        if (((String)charSequence).endsWith("/")) {
            object = ((String)charSequence).substring(0, ((String)charSequence).length() - 1);
        }
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append((String)object);
        ((StringBuilder)charSequence).append(jsonPointer._asString);
        return JsonPointer.compile(((StringBuilder)charSequence).toString());
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object instanceof JsonPointer) return this._asString.equals(((JsonPointer)object)._asString);
        return false;
    }

    public int getMatchingIndex() {
        return this._matchingElementIndex;
    }

    public String getMatchingProperty() {
        return this._matchingPropertyName;
    }

    public int hashCode() {
        return this._asString.hashCode();
    }

    public JsonPointer head() {
        JsonPointer jsonPointer;
        JsonPointer jsonPointer2 = jsonPointer = this._head;
        if (jsonPointer != null) return jsonPointer2;
        if (this != EMPTY) {
            jsonPointer = this._constructHead();
        }
        this._head = jsonPointer;
        return jsonPointer;
    }

    public JsonPointer last() {
        JsonPointer jsonPointer;
        if (this == EMPTY) {
            return null;
        }
        JsonPointer jsonPointer2 = this;
        while ((jsonPointer = jsonPointer2._nextSegment) != EMPTY) {
            jsonPointer2 = jsonPointer;
        }
        return jsonPointer2;
    }

    public JsonPointer matchElement(int n) {
        if (n != this._matchingElementIndex) return null;
        if (n >= 0) return this._nextSegment;
        return null;
    }

    public JsonPointer matchProperty(String string2) {
        if (this._nextSegment == null) return null;
        if (!this._matchingPropertyName.equals(string2)) return null;
        return this._nextSegment;
    }

    public boolean matches() {
        if (this._nextSegment != null) return false;
        return true;
    }

    public boolean matchesElement(int n) {
        if (n != this._matchingElementIndex) return false;
        if (n < 0) return false;
        return true;
    }

    public boolean matchesProperty(String string2) {
        if (this._nextSegment == null) return false;
        if (!this._matchingPropertyName.equals(string2)) return false;
        return true;
    }

    public boolean mayMatchElement() {
        if (this._matchingElementIndex < 0) return false;
        return true;
    }

    public boolean mayMatchProperty() {
        if (this._matchingPropertyName == null) return false;
        return true;
    }

    public JsonPointer tail() {
        return this._nextSegment;
    }

    public String toString() {
        return this._asString;
    }
}


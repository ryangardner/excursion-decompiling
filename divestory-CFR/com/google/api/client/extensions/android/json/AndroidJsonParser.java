/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.JsonReader
 *  android.util.JsonToken
 */
package com.google.api.client.extensions.android.json;

import android.util.JsonReader;
import android.util.JsonToken;
import com.google.api.client.extensions.android.json.AndroidJsonFactory;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.JsonParser;
import com.google.api.client.util.Preconditions;
import java.io.EOFException;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

class AndroidJsonParser
extends JsonParser {
    private List<String> currentNameStack = new ArrayList<String>();
    private String currentText;
    private com.google.api.client.json.JsonToken currentToken;
    private final AndroidJsonFactory factory;
    private final JsonReader reader;

    AndroidJsonParser(AndroidJsonFactory androidJsonFactory, JsonReader jsonReader) {
        this.factory = androidJsonFactory;
        this.reader = jsonReader;
        jsonReader.setLenient(true);
    }

    private void checkNumber() {
        boolean bl = this.currentToken == com.google.api.client.json.JsonToken.VALUE_NUMBER_INT || this.currentToken == com.google.api.client.json.JsonToken.VALUE_NUMBER_FLOAT;
        Preconditions.checkArgument(bl);
    }

    @Override
    public void close() throws IOException {
        this.reader.close();
    }

    @Override
    public BigInteger getBigIntegerValue() {
        this.checkNumber();
        return new BigInteger(this.currentText);
    }

    @Override
    public byte getByteValue() {
        this.checkNumber();
        return Byte.parseByte(this.currentText);
    }

    @Override
    public String getCurrentName() {
        if (this.currentNameStack.isEmpty()) {
            return null;
        }
        List<String> list = this.currentNameStack;
        return list.get(list.size() - 1);
    }

    @Override
    public com.google.api.client.json.JsonToken getCurrentToken() {
        return this.currentToken;
    }

    @Override
    public BigDecimal getDecimalValue() {
        this.checkNumber();
        return new BigDecimal(this.currentText);
    }

    @Override
    public double getDoubleValue() {
        this.checkNumber();
        return Double.parseDouble(this.currentText);
    }

    @Override
    public JsonFactory getFactory() {
        return this.factory;
    }

    @Override
    public float getFloatValue() {
        this.checkNumber();
        return Float.parseFloat(this.currentText);
    }

    @Override
    public int getIntValue() {
        this.checkNumber();
        return Integer.parseInt(this.currentText);
    }

    @Override
    public long getLongValue() {
        this.checkNumber();
        return Long.parseLong(this.currentText);
    }

    @Override
    public short getShortValue() {
        this.checkNumber();
        return Short.parseShort(this.currentText);
    }

    @Override
    public String getText() {
        return this.currentText;
    }

    @Override
    public com.google.api.client.json.JsonToken nextToken() throws IOException {
        Object object;
        if (this.currentToken != null) {
            int n = 1.$SwitchMap$com$google$api$client$json$JsonToken[this.currentToken.ordinal()];
            if (n != 1) {
                if (n == 2) {
                    this.reader.beginObject();
                    this.currentNameStack.add(null);
                }
            } else {
                this.reader.beginArray();
                this.currentNameStack.add(null);
            }
        }
        try {
            object = this.reader.peek();
        }
        catch (EOFException eOFException) {
            object = JsonToken.END_DOCUMENT;
        }
        switch (1.$SwitchMap$android$util$JsonToken[object.ordinal()]) {
            default: {
                this.currentText = null;
                this.currentToken = null;
                return this.currentToken;
            }
            case 9: {
                this.currentText = this.reader.nextName();
                this.currentToken = com.google.api.client.json.JsonToken.FIELD_NAME;
                object = this.currentNameStack;
                object.set(object.size() - 1, this.currentText);
                return this.currentToken;
            }
            case 8: {
                object = this.reader.nextString();
                this.currentText = object;
                object = object.indexOf(46) == -1 ? com.google.api.client.json.JsonToken.VALUE_NUMBER_INT : com.google.api.client.json.JsonToken.VALUE_NUMBER_FLOAT;
                this.currentToken = object;
                return this.currentToken;
            }
            case 7: {
                this.currentText = this.reader.nextString();
                this.currentToken = com.google.api.client.json.JsonToken.VALUE_STRING;
                return this.currentToken;
            }
            case 6: {
                this.currentText = "null";
                this.currentToken = com.google.api.client.json.JsonToken.VALUE_NULL;
                this.reader.nextNull();
                return this.currentToken;
            }
            case 5: {
                if (this.reader.nextBoolean()) {
                    this.currentText = "true";
                    this.currentToken = com.google.api.client.json.JsonToken.VALUE_TRUE;
                    return this.currentToken;
                }
                this.currentText = "false";
                this.currentToken = com.google.api.client.json.JsonToken.VALUE_FALSE;
                return this.currentToken;
            }
            case 4: {
                this.currentText = "}";
                this.currentToken = com.google.api.client.json.JsonToken.END_OBJECT;
                object = this.currentNameStack;
                object.remove(object.size() - 1);
                this.reader.endObject();
                return this.currentToken;
            }
            case 3: {
                this.currentText = "{";
                this.currentToken = com.google.api.client.json.JsonToken.START_OBJECT;
                return this.currentToken;
            }
            case 2: {
                this.currentText = "]";
                this.currentToken = com.google.api.client.json.JsonToken.END_ARRAY;
                object = this.currentNameStack;
                object.remove(object.size() - 1);
                this.reader.endArray();
                return this.currentToken;
            }
            case 1: 
        }
        this.currentText = "[";
        this.currentToken = com.google.api.client.json.JsonToken.START_ARRAY;
        return this.currentToken;
    }

    @Override
    public JsonParser skipChildren() throws IOException {
        if (this.currentToken == null) return this;
        int n = 1.$SwitchMap$com$google$api$client$json$JsonToken[this.currentToken.ordinal()];
        if (n == 1) {
            this.reader.skipValue();
            this.currentText = "]";
            this.currentToken = com.google.api.client.json.JsonToken.END_ARRAY;
            return this;
        }
        if (n != 2) {
            return this;
        }
        this.reader.skipValue();
        this.currentText = "}";
        this.currentToken = com.google.api.client.json.JsonToken.END_OBJECT;
        return this;
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import com.fasterxml.jackson.core.FormatFeature;
import com.fasterxml.jackson.core.FormatSchema;
import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.Versioned;
import com.fasterxml.jackson.core.io.DataOutputAsStream;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.io.Serializable;
import java.io.Writer;
import java.net.URL;

public abstract class TokenStreamFactory
implements Versioned,
Serializable {
    private static final long serialVersionUID = 2L;

    protected OutputStream _createDataOutputWrapper(DataOutput dataOutput) {
        return new DataOutputAsStream(dataOutput);
    }

    protected InputStream _optimizedStreamFromURL(URL uRL) throws IOException {
        if (!"file".equals(uRL.getProtocol())) return uRL.openStream();
        String string2 = uRL.getHost();
        if (string2 != null) {
            if (string2.length() != 0) return uRL.openStream();
        }
        if (uRL.getPath().indexOf(37) >= 0) return uRL.openStream();
        return new FileInputStream(uRL.getPath());
    }

    public abstract boolean canHandleBinaryNatively();

    public abstract boolean canParseAsync();

    public abstract boolean canUseSchema(FormatSchema var1);

    public abstract JsonGenerator createGenerator(DataOutput var1) throws IOException;

    public abstract JsonGenerator createGenerator(DataOutput var1, JsonEncoding var2) throws IOException;

    public abstract JsonGenerator createGenerator(File var1, JsonEncoding var2) throws IOException;

    public abstract JsonGenerator createGenerator(OutputStream var1) throws IOException;

    public abstract JsonGenerator createGenerator(OutputStream var1, JsonEncoding var2) throws IOException;

    public abstract JsonGenerator createGenerator(Writer var1) throws IOException;

    public abstract JsonParser createNonBlockingByteArrayParser() throws IOException;

    public abstract JsonParser createParser(DataInput var1) throws IOException;

    public abstract JsonParser createParser(File var1) throws IOException;

    public abstract JsonParser createParser(InputStream var1) throws IOException;

    public abstract JsonParser createParser(Reader var1) throws IOException;

    public abstract JsonParser createParser(String var1) throws IOException;

    public abstract JsonParser createParser(URL var1) throws IOException;

    public abstract JsonParser createParser(byte[] var1) throws IOException;

    public abstract JsonParser createParser(byte[] var1, int var2, int var3) throws IOException;

    public abstract JsonParser createParser(char[] var1) throws IOException;

    public abstract JsonParser createParser(char[] var1, int var2, int var3) throws IOException;

    public abstract int getFormatGeneratorFeatures();

    public abstract String getFormatName();

    public abstract int getFormatParserFeatures();

    public abstract Class<? extends FormatFeature> getFormatReadFeatureType();

    public abstract Class<? extends FormatFeature> getFormatWriteFeatureType();

    public abstract int getGeneratorFeatures();

    public abstract int getParserFeatures();

    public abstract boolean isEnabled(JsonGenerator.Feature var1);

    public abstract boolean isEnabled(JsonParser.Feature var1);

    public abstract boolean requiresPropertyOrdering();
}


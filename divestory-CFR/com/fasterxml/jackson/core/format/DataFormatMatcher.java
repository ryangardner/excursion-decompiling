/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.format.MatchStrength;
import com.fasterxml.jackson.core.io.IOContext;
import com.fasterxml.jackson.core.io.MergedStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class DataFormatMatcher {
    protected final byte[] _bufferedData;
    protected final int _bufferedLength;
    protected final int _bufferedStart;
    protected final JsonFactory _match;
    protected final MatchStrength _matchStrength;
    protected final InputStream _originalStream;

    protected DataFormatMatcher(InputStream inputStream2, byte[] arrby, int n, int n2, JsonFactory jsonFactory, MatchStrength matchStrength) {
        this._originalStream = inputStream2;
        this._bufferedData = arrby;
        this._bufferedStart = n;
        this._bufferedLength = n2;
        this._match = jsonFactory;
        this._matchStrength = matchStrength;
        if ((n | n2) < 0 || n + n2 > arrby.length) throw new IllegalArgumentException(String.format("Illegal start/length (%d/%d) wrt input array of %d bytes", n, n2, arrby.length));
    }

    public JsonParser createParserWithMatch() throws IOException {
        JsonFactory jsonFactory = this._match;
        if (jsonFactory == null) {
            return null;
        }
        if (this._originalStream != null) return jsonFactory.createParser(this.getDataStream());
        return jsonFactory.createParser(this._bufferedData, this._bufferedStart, this._bufferedLength);
    }

    public InputStream getDataStream() {
        if (this._originalStream != null) return new MergedStream(null, this._originalStream, this._bufferedData, this._bufferedStart, this._bufferedLength);
        return new ByteArrayInputStream(this._bufferedData, this._bufferedStart, this._bufferedLength);
    }

    public JsonFactory getMatch() {
        return this._match;
    }

    public MatchStrength getMatchStrength() {
        MatchStrength matchStrength;
        MatchStrength matchStrength2 = matchStrength = this._matchStrength;
        if (matchStrength != null) return matchStrength2;
        return MatchStrength.INCONCLUSIVE;
    }

    public String getMatchedFormatName() {
        return this._match.getFormatName();
    }

    public boolean hasMatch() {
        if (this._match == null) return false;
        return true;
    }
}


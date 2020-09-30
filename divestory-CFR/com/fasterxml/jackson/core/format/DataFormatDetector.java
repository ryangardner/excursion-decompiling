/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core.format;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.format.DataFormatMatcher;
import com.fasterxml.jackson.core.format.InputAccessor;
import com.fasterxml.jackson.core.format.MatchStrength;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

public class DataFormatDetector {
    public static final int DEFAULT_MAX_INPUT_LOOKAHEAD = 64;
    protected final JsonFactory[] _detectors;
    protected final int _maxInputLookahead;
    protected final MatchStrength _minimalMatch;
    protected final MatchStrength _optimalMatch;

    public DataFormatDetector(Collection<JsonFactory> collection) {
        this(collection.toArray(new JsonFactory[collection.size()]));
    }

    public DataFormatDetector(JsonFactory ... arrjsonFactory) {
        this(arrjsonFactory, MatchStrength.SOLID_MATCH, MatchStrength.WEAK_MATCH, 64);
    }

    private DataFormatDetector(JsonFactory[] arrjsonFactory, MatchStrength matchStrength, MatchStrength matchStrength2, int n) {
        this._detectors = arrjsonFactory;
        this._optimalMatch = matchStrength;
        this._minimalMatch = matchStrength2;
        this._maxInputLookahead = n;
    }

    private DataFormatMatcher _findFormat(InputAccessor.Std std) throws IOException {
        JsonFactory[] arrjsonFactory = this._detectors;
        int n = arrjsonFactory.length;
        MatchStrength matchStrength = null;
        Enum enum_ = null;
        int n2 = 0;
        do {
            Object object = matchStrength;
            Enum enum_2 = enum_;
            if (n2 >= n) return std.createMatcher((JsonFactory)object, (MatchStrength)enum_2);
            JsonFactory jsonFactory = arrjsonFactory[n2];
            std.reset();
            object = jsonFactory.hasFormat(std);
            Object object2 = matchStrength;
            enum_2 = enum_;
            if (object != null) {
                if (object.ordinal() < this._minimalMatch.ordinal()) {
                    object2 = matchStrength;
                    enum_2 = enum_;
                } else if (matchStrength != null && enum_.ordinal() >= object.ordinal()) {
                    object2 = matchStrength;
                    enum_2 = enum_;
                } else {
                    if (object.ordinal() >= this._optimalMatch.ordinal()) {
                        enum_2 = object;
                        object = jsonFactory;
                        return std.createMatcher((JsonFactory)object, (MatchStrength)enum_2);
                    }
                    enum_2 = object;
                    object2 = jsonFactory;
                }
            }
            ++n2;
            matchStrength = object2;
            enum_ = enum_2;
        } while (true);
    }

    public DataFormatMatcher findFormat(InputStream inputStream2) throws IOException {
        return this._findFormat(new InputAccessor.Std(inputStream2, new byte[this._maxInputLookahead]));
    }

    public DataFormatMatcher findFormat(byte[] arrby) throws IOException {
        return this._findFormat(new InputAccessor.Std(arrby));
    }

    public DataFormatMatcher findFormat(byte[] arrby, int n, int n2) throws IOException {
        return this._findFormat(new InputAccessor.Std(arrby, n, n2));
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append('[');
        JsonFactory[] arrjsonFactory = this._detectors;
        int n = arrjsonFactory.length;
        if (n > 0) {
            stringBuilder.append(arrjsonFactory[0].getFormatName());
            for (int i = 1; i < n; ++i) {
                stringBuilder.append(", ");
                stringBuilder.append(this._detectors[i].getFormatName());
            }
        }
        stringBuilder.append(']');
        return stringBuilder.toString();
    }

    public DataFormatDetector withMaxInputLookahead(int n) {
        if (n != this._maxInputLookahead) return new DataFormatDetector(this._detectors, this._optimalMatch, this._minimalMatch, n);
        return this;
    }

    public DataFormatDetector withMinimalMatch(MatchStrength matchStrength) {
        if (matchStrength != this._minimalMatch) return new DataFormatDetector(this._detectors, this._optimalMatch, matchStrength, this._maxInputLookahead);
        return this;
    }

    public DataFormatDetector withOptimalMatch(MatchStrength matchStrength) {
        if (matchStrength != this._optimalMatch) return new DataFormatDetector(this._detectors, matchStrength, this._minimalMatch, this._maxInputLookahead);
        return this;
    }
}


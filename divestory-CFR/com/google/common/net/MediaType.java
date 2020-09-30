/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.net;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Charsets;
import com.google.common.base.Function;
import com.google.common.base.Joiner;
import com.google.common.base.MoreObjects;
import com.google.common.base.Objects;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableCollection;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableMultiset;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.concurrent.LazyInit;
import java.io.Serializable;
import java.nio.charset.Charset;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

@Immutable
public final class MediaType {
    public static final MediaType AAC_AUDIO;
    public static final MediaType ANY_APPLICATION_TYPE;
    public static final MediaType ANY_AUDIO_TYPE;
    public static final MediaType ANY_IMAGE_TYPE;
    public static final MediaType ANY_TEXT_TYPE;
    public static final MediaType ANY_TYPE;
    public static final MediaType ANY_VIDEO_TYPE;
    public static final MediaType APPLE_MOBILE_CONFIG;
    public static final MediaType APPLE_PASSBOOK;
    public static final MediaType APPLICATION_BINARY;
    private static final String APPLICATION_TYPE = "application";
    public static final MediaType APPLICATION_XML_UTF_8;
    public static final MediaType ATOM_UTF_8;
    private static final String AUDIO_TYPE = "audio";
    public static final MediaType BASIC_AUDIO;
    public static final MediaType BMP;
    public static final MediaType BZIP2;
    public static final MediaType CACHE_MANIFEST_UTF_8;
    private static final String CHARSET_ATTRIBUTE = "charset";
    public static final MediaType CRW;
    public static final MediaType CSS_UTF_8;
    public static final MediaType CSV_UTF_8;
    public static final MediaType DART_UTF_8;
    public static final MediaType EOT;
    public static final MediaType EPUB;
    public static final MediaType FLV_VIDEO;
    public static final MediaType FORM_DATA;
    public static final MediaType GEO_JSON;
    public static final MediaType GIF;
    public static final MediaType GZIP;
    public static final MediaType HAL_JSON;
    public static final MediaType HEIF;
    public static final MediaType HTML_UTF_8;
    public static final MediaType ICO;
    private static final String IMAGE_TYPE = "image";
    public static final MediaType I_CALENDAR_UTF_8;
    public static final MediaType JAVASCRIPT_UTF_8;
    public static final MediaType JOSE;
    public static final MediaType JOSE_JSON;
    public static final MediaType JP2K;
    public static final MediaType JPEG;
    public static final MediaType JSON_UTF_8;
    public static final MediaType KEY_ARCHIVE;
    public static final MediaType KML;
    public static final MediaType KMZ;
    private static final Map<MediaType, MediaType> KNOWN_TYPES;
    public static final MediaType L16_AUDIO;
    public static final MediaType L24_AUDIO;
    private static final CharMatcher LINEAR_WHITE_SPACE;
    public static final MediaType MANIFEST_JSON_UTF_8;
    public static final MediaType MBOX;
    public static final MediaType MEDIA_PRESENTATION_DESCRIPTION;
    public static final MediaType MICROSOFT_EXCEL;
    public static final MediaType MICROSOFT_OUTLOOK;
    public static final MediaType MICROSOFT_POWERPOINT;
    public static final MediaType MICROSOFT_WORD;
    public static final MediaType MP4_AUDIO;
    public static final MediaType MP4_VIDEO;
    public static final MediaType MPEG_AUDIO;
    public static final MediaType MPEG_VIDEO;
    public static final MediaType NACL_APPLICATION;
    public static final MediaType NACL_PORTABLE_APPLICATION;
    public static final MediaType OCTET_STREAM;
    public static final MediaType OGG_AUDIO;
    public static final MediaType OGG_CONTAINER;
    public static final MediaType OGG_VIDEO;
    public static final MediaType OOXML_DOCUMENT;
    public static final MediaType OOXML_PRESENTATION;
    public static final MediaType OOXML_SHEET;
    public static final MediaType OPENDOCUMENT_GRAPHICS;
    public static final MediaType OPENDOCUMENT_PRESENTATION;
    public static final MediaType OPENDOCUMENT_SPREADSHEET;
    public static final MediaType OPENDOCUMENT_TEXT;
    public static final MediaType OPENSEARCH_DESCRIPTION_UTF_8;
    private static final Joiner.MapJoiner PARAMETER_JOINER;
    public static final MediaType PDF;
    public static final MediaType PLAIN_TEXT_UTF_8;
    public static final MediaType PNG;
    public static final MediaType POSTSCRIPT;
    public static final MediaType PROTOBUF;
    public static final MediaType PSD;
    public static final MediaType QUICKTIME;
    private static final CharMatcher QUOTED_TEXT_MATCHER;
    public static final MediaType RDF_XML_UTF_8;
    public static final MediaType RTF_UTF_8;
    public static final MediaType SFNT;
    public static final MediaType SHOCKWAVE_FLASH;
    public static final MediaType SKETCHUP;
    public static final MediaType SOAP_XML_UTF_8;
    public static final MediaType SVG_UTF_8;
    public static final MediaType TAR;
    public static final MediaType TEXT_JAVASCRIPT_UTF_8;
    private static final String TEXT_TYPE = "text";
    public static final MediaType THREE_GPP2_VIDEO;
    public static final MediaType THREE_GPP_VIDEO;
    public static final MediaType TIFF;
    private static final CharMatcher TOKEN_MATCHER;
    public static final MediaType TSV_UTF_8;
    private static final ImmutableListMultimap<String, String> UTF_8_CONSTANT_PARAMETERS;
    public static final MediaType VCARD_UTF_8;
    private static final String VIDEO_TYPE = "video";
    public static final MediaType VND_REAL_AUDIO;
    public static final MediaType VND_WAVE_AUDIO;
    public static final MediaType VORBIS_AUDIO;
    public static final MediaType VTT_UTF_8;
    public static final MediaType WASM_APPLICATION;
    public static final MediaType WAX_AUDIO;
    public static final MediaType WEBM_AUDIO;
    public static final MediaType WEBM_VIDEO;
    public static final MediaType WEBP;
    private static final String WILDCARD = "*";
    public static final MediaType WMA_AUDIO;
    public static final MediaType WML_UTF_8;
    public static final MediaType WMV;
    public static final MediaType WOFF;
    public static final MediaType WOFF2;
    public static final MediaType XHTML_UTF_8;
    public static final MediaType XML_UTF_8;
    public static final MediaType XRD_UTF_8;
    public static final MediaType ZIP;
    @LazyInit
    private int hashCode;
    private final ImmutableListMultimap<String, String> parameters;
    @LazyInit
    private Optional<Charset> parsedCharset;
    private final String subtype;
    @LazyInit
    private String toString;
    private final String type;

    static {
        UTF_8_CONSTANT_PARAMETERS = ImmutableListMultimap.of(CHARSET_ATTRIBUTE, Ascii.toLowerCase(Charsets.UTF_8.name()));
        TOKEN_MATCHER = CharMatcher.ascii().and(CharMatcher.javaIsoControl().negate()).and(CharMatcher.isNot(' ')).and(CharMatcher.noneOf("()<>@,;:\\\"/[]?="));
        QUOTED_TEXT_MATCHER = CharMatcher.ascii().and(CharMatcher.noneOf("\"\\\r"));
        LINEAR_WHITE_SPACE = CharMatcher.anyOf(" \t\r\n");
        KNOWN_TYPES = Maps.newHashMap();
        ANY_TYPE = MediaType.createConstant(WILDCARD, WILDCARD);
        ANY_TEXT_TYPE = MediaType.createConstant(TEXT_TYPE, WILDCARD);
        ANY_IMAGE_TYPE = MediaType.createConstant(IMAGE_TYPE, WILDCARD);
        ANY_AUDIO_TYPE = MediaType.createConstant(AUDIO_TYPE, WILDCARD);
        ANY_VIDEO_TYPE = MediaType.createConstant(VIDEO_TYPE, WILDCARD);
        ANY_APPLICATION_TYPE = MediaType.createConstant(APPLICATION_TYPE, WILDCARD);
        CACHE_MANIFEST_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "cache-manifest");
        CSS_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "css");
        CSV_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "csv");
        HTML_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "html");
        I_CALENDAR_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "calendar");
        PLAIN_TEXT_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "plain");
        TEXT_JAVASCRIPT_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "javascript");
        TSV_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "tab-separated-values");
        VCARD_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "vcard");
        WML_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "vnd.wap.wml");
        XML_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "xml");
        VTT_UTF_8 = MediaType.createConstantUtf8(TEXT_TYPE, "vtt");
        BMP = MediaType.createConstant(IMAGE_TYPE, "bmp");
        CRW = MediaType.createConstant(IMAGE_TYPE, "x-canon-crw");
        GIF = MediaType.createConstant(IMAGE_TYPE, "gif");
        ICO = MediaType.createConstant(IMAGE_TYPE, "vnd.microsoft.icon");
        JPEG = MediaType.createConstant(IMAGE_TYPE, "jpeg");
        PNG = MediaType.createConstant(IMAGE_TYPE, "png");
        PSD = MediaType.createConstant(IMAGE_TYPE, "vnd.adobe.photoshop");
        SVG_UTF_8 = MediaType.createConstantUtf8(IMAGE_TYPE, "svg+xml");
        TIFF = MediaType.createConstant(IMAGE_TYPE, "tiff");
        WEBP = MediaType.createConstant(IMAGE_TYPE, "webp");
        HEIF = MediaType.createConstant(IMAGE_TYPE, "heif");
        JP2K = MediaType.createConstant(IMAGE_TYPE, "jp2");
        MP4_AUDIO = MediaType.createConstant(AUDIO_TYPE, "mp4");
        MPEG_AUDIO = MediaType.createConstant(AUDIO_TYPE, "mpeg");
        OGG_AUDIO = MediaType.createConstant(AUDIO_TYPE, "ogg");
        WEBM_AUDIO = MediaType.createConstant(AUDIO_TYPE, "webm");
        L16_AUDIO = MediaType.createConstant(AUDIO_TYPE, "l16");
        L24_AUDIO = MediaType.createConstant(AUDIO_TYPE, "l24");
        BASIC_AUDIO = MediaType.createConstant(AUDIO_TYPE, "basic");
        AAC_AUDIO = MediaType.createConstant(AUDIO_TYPE, "aac");
        VORBIS_AUDIO = MediaType.createConstant(AUDIO_TYPE, "vorbis");
        WMA_AUDIO = MediaType.createConstant(AUDIO_TYPE, "x-ms-wma");
        WAX_AUDIO = MediaType.createConstant(AUDIO_TYPE, "x-ms-wax");
        VND_REAL_AUDIO = MediaType.createConstant(AUDIO_TYPE, "vnd.rn-realaudio");
        VND_WAVE_AUDIO = MediaType.createConstant(AUDIO_TYPE, "vnd.wave");
        MP4_VIDEO = MediaType.createConstant(VIDEO_TYPE, "mp4");
        MPEG_VIDEO = MediaType.createConstant(VIDEO_TYPE, "mpeg");
        OGG_VIDEO = MediaType.createConstant(VIDEO_TYPE, "ogg");
        QUICKTIME = MediaType.createConstant(VIDEO_TYPE, "quicktime");
        WEBM_VIDEO = MediaType.createConstant(VIDEO_TYPE, "webm");
        WMV = MediaType.createConstant(VIDEO_TYPE, "x-ms-wmv");
        FLV_VIDEO = MediaType.createConstant(VIDEO_TYPE, "x-flv");
        THREE_GPP_VIDEO = MediaType.createConstant(VIDEO_TYPE, "3gpp");
        THREE_GPP2_VIDEO = MediaType.createConstant(VIDEO_TYPE, "3gpp2");
        APPLICATION_XML_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "xml");
        ATOM_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "atom+xml");
        BZIP2 = MediaType.createConstant(APPLICATION_TYPE, "x-bzip2");
        DART_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "dart");
        APPLE_PASSBOOK = MediaType.createConstant(APPLICATION_TYPE, "vnd.apple.pkpass");
        EOT = MediaType.createConstant(APPLICATION_TYPE, "vnd.ms-fontobject");
        EPUB = MediaType.createConstant(APPLICATION_TYPE, "epub+zip");
        FORM_DATA = MediaType.createConstant(APPLICATION_TYPE, "x-www-form-urlencoded");
        KEY_ARCHIVE = MediaType.createConstant(APPLICATION_TYPE, "pkcs12");
        APPLICATION_BINARY = MediaType.createConstant(APPLICATION_TYPE, "binary");
        GEO_JSON = MediaType.createConstant(APPLICATION_TYPE, "geo+json");
        GZIP = MediaType.createConstant(APPLICATION_TYPE, "x-gzip");
        HAL_JSON = MediaType.createConstant(APPLICATION_TYPE, "hal+json");
        JAVASCRIPT_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "javascript");
        JOSE = MediaType.createConstant(APPLICATION_TYPE, "jose");
        JOSE_JSON = MediaType.createConstant(APPLICATION_TYPE, "jose+json");
        JSON_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "json");
        MANIFEST_JSON_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "manifest+json");
        KML = MediaType.createConstant(APPLICATION_TYPE, "vnd.google-earth.kml+xml");
        KMZ = MediaType.createConstant(APPLICATION_TYPE, "vnd.google-earth.kmz");
        MBOX = MediaType.createConstant(APPLICATION_TYPE, "mbox");
        APPLE_MOBILE_CONFIG = MediaType.createConstant(APPLICATION_TYPE, "x-apple-aspen-config");
        MICROSOFT_EXCEL = MediaType.createConstant(APPLICATION_TYPE, "vnd.ms-excel");
        MICROSOFT_OUTLOOK = MediaType.createConstant(APPLICATION_TYPE, "vnd.ms-outlook");
        MICROSOFT_POWERPOINT = MediaType.createConstant(APPLICATION_TYPE, "vnd.ms-powerpoint");
        MICROSOFT_WORD = MediaType.createConstant(APPLICATION_TYPE, "msword");
        MEDIA_PRESENTATION_DESCRIPTION = MediaType.createConstant(APPLICATION_TYPE, "dash+xml");
        WASM_APPLICATION = MediaType.createConstant(APPLICATION_TYPE, "wasm");
        NACL_APPLICATION = MediaType.createConstant(APPLICATION_TYPE, "x-nacl");
        NACL_PORTABLE_APPLICATION = MediaType.createConstant(APPLICATION_TYPE, "x-pnacl");
        OCTET_STREAM = MediaType.createConstant(APPLICATION_TYPE, "octet-stream");
        OGG_CONTAINER = MediaType.createConstant(APPLICATION_TYPE, "ogg");
        OOXML_DOCUMENT = MediaType.createConstant(APPLICATION_TYPE, "vnd.openxmlformats-officedocument.wordprocessingml.document");
        OOXML_PRESENTATION = MediaType.createConstant(APPLICATION_TYPE, "vnd.openxmlformats-officedocument.presentationml.presentation");
        OOXML_SHEET = MediaType.createConstant(APPLICATION_TYPE, "vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        OPENDOCUMENT_GRAPHICS = MediaType.createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.graphics");
        OPENDOCUMENT_PRESENTATION = MediaType.createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.presentation");
        OPENDOCUMENT_SPREADSHEET = MediaType.createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.spreadsheet");
        OPENDOCUMENT_TEXT = MediaType.createConstant(APPLICATION_TYPE, "vnd.oasis.opendocument.text");
        OPENSEARCH_DESCRIPTION_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "opensearchdescription+xml");
        PDF = MediaType.createConstant(APPLICATION_TYPE, "pdf");
        POSTSCRIPT = MediaType.createConstant(APPLICATION_TYPE, "postscript");
        PROTOBUF = MediaType.createConstant(APPLICATION_TYPE, "protobuf");
        RDF_XML_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "rdf+xml");
        RTF_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "rtf");
        SFNT = MediaType.createConstant(APPLICATION_TYPE, "font-sfnt");
        SHOCKWAVE_FLASH = MediaType.createConstant(APPLICATION_TYPE, "x-shockwave-flash");
        SKETCHUP = MediaType.createConstant(APPLICATION_TYPE, "vnd.sketchup.skp");
        SOAP_XML_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "soap+xml");
        TAR = MediaType.createConstant(APPLICATION_TYPE, "x-tar");
        WOFF = MediaType.createConstant(APPLICATION_TYPE, "font-woff");
        WOFF2 = MediaType.createConstant(APPLICATION_TYPE, "font-woff2");
        XHTML_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "xhtml+xml");
        XRD_UTF_8 = MediaType.createConstantUtf8(APPLICATION_TYPE, "xrd+xml");
        ZIP = MediaType.createConstant(APPLICATION_TYPE, "zip");
        PARAMETER_JOINER = Joiner.on("; ").withKeyValueSeparator("=");
    }

    private MediaType(String string2, String string3, ImmutableListMultimap<String, String> immutableListMultimap) {
        this.type = string2;
        this.subtype = string3;
        this.parameters = immutableListMultimap;
    }

    private static MediaType addKnownType(MediaType mediaType) {
        KNOWN_TYPES.put(mediaType, mediaType);
        return mediaType;
    }

    private String computeToString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.type);
        stringBuilder.append('/');
        stringBuilder.append(this.subtype);
        if (this.parameters.isEmpty()) return stringBuilder.toString();
        stringBuilder.append("; ");
        ListMultimap<String, String> listMultimap = Multimaps.transformValues(this.parameters, new Function<String, String>(){

            @Override
            public String apply(String string2) {
                if (!TOKEN_MATCHER.matchesAllOf(string2)) return MediaType.escapeAndQuote(string2);
                if (string2.isEmpty()) return MediaType.escapeAndQuote(string2);
                return string2;
            }
        });
        PARAMETER_JOINER.appendTo(stringBuilder, (Iterable<? extends Map.Entry<?, ?>>)listMultimap.entries());
        return stringBuilder.toString();
    }

    public static MediaType create(String object, String string2) {
        object = MediaType.create((String)object, string2, ImmutableListMultimap.<String, String>of());
        ((MediaType)object).parsedCharset = Optional.absent();
        return object;
    }

    private static MediaType create(String object, String string2, Multimap<String, String> object2) {
        Preconditions.checkNotNull(object);
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(object2);
        object = MediaType.normalizeToken((String)object);
        string2 = MediaType.normalizeToken(string2);
        boolean bl = !WILDCARD.equals(object) || WILDCARD.equals(string2);
        Preconditions.checkArgument(bl, "A wildcard type cannot be used with a non-wildcard subtype");
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        Iterator<Map.Entry<String, String>> iterator2 = object2.entries().iterator();
        do {
            if (!iterator2.hasNext()) {
                object = new MediaType((String)object, string2, (ImmutableListMultimap<String, String>)builder.build());
                return (MediaType)MoreObjects.firstNonNull(KNOWN_TYPES.get(object), object);
            }
            object2 = iterator2.next();
            String string3 = MediaType.normalizeToken((String)object2.getKey());
            builder.put(string3, MediaType.normalizeParameterValue(string3, (String)object2.getValue()));
        } while (true);
    }

    static MediaType createApplicationType(String string2) {
        return MediaType.create(APPLICATION_TYPE, string2);
    }

    static MediaType createAudioType(String string2) {
        return MediaType.create(AUDIO_TYPE, string2);
    }

    private static MediaType createConstant(String object, String string2) {
        object = MediaType.addKnownType(new MediaType((String)object, string2, ImmutableListMultimap.<String, String>of()));
        ((MediaType)object).parsedCharset = Optional.absent();
        return object;
    }

    private static MediaType createConstantUtf8(String object, String string2) {
        object = MediaType.addKnownType(new MediaType((String)object, string2, UTF_8_CONSTANT_PARAMETERS));
        ((MediaType)object).parsedCharset = Optional.of(Charsets.UTF_8);
        return object;
    }

    static MediaType createImageType(String string2) {
        return MediaType.create(IMAGE_TYPE, string2);
    }

    static MediaType createTextType(String string2) {
        return MediaType.create(TEXT_TYPE, string2);
    }

    static MediaType createVideoType(String string2) {
        return MediaType.create(VIDEO_TYPE, string2);
    }

    private static String escapeAndQuote(String string2) {
        StringBuilder stringBuilder = new StringBuilder(string2.length() + 16);
        stringBuilder.append('\"');
        int n = 0;
        do {
            if (n >= string2.length()) {
                stringBuilder.append('\"');
                return stringBuilder.toString();
            }
            char c = string2.charAt(n);
            if (c == '\r' || c == '\\' || c == '\"') {
                stringBuilder.append('\\');
            }
            stringBuilder.append(c);
            ++n;
        } while (true);
    }

    private static String normalizeParameterValue(String string2, String string3) {
        Preconditions.checkNotNull(string3);
        Preconditions.checkArgument(CharMatcher.ascii().matchesAllOf(string3), "parameter values must be ASCII: %s", (Object)string3);
        String string4 = string3;
        if (!CHARSET_ATTRIBUTE.equals(string2)) return string4;
        return Ascii.toLowerCase(string3);
    }

    private static String normalizeToken(String string2) {
        Preconditions.checkArgument(TOKEN_MATCHER.matchesAllOf(string2));
        Preconditions.checkArgument(string2.isEmpty() ^ true);
        return Ascii.toLowerCase(string2);
    }

    private Map<String, ImmutableMultiset<String>> parametersAsMap() {
        return Maps.transformValues(this.parameters.asMap(), new Function<Collection<String>, ImmutableMultiset<String>>(){

            @Override
            public ImmutableMultiset<String> apply(Collection<String> collection) {
                return ImmutableMultiset.copyOf(collection);
            }
        });
    }

    public static MediaType parse(String string2) {
        Preconditions.checkNotNull(string2);
        Tokenizer tokenizer = new Tokenizer(string2);
        try {
            String string3 = tokenizer.consumeToken(TOKEN_MATCHER);
            tokenizer.consumeCharacter('/');
            String string4 = tokenizer.consumeToken(TOKEN_MATCHER);
            ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
            do {
                Object object;
                if (!tokenizer.hasMore()) {
                    return MediaType.create(string3, string4, builder.build());
                }
                tokenizer.consumeTokenIfPresent(LINEAR_WHITE_SPACE);
                tokenizer.consumeCharacter(';');
                tokenizer.consumeTokenIfPresent(LINEAR_WHITE_SPACE);
                String string5 = tokenizer.consumeToken(TOKEN_MATCHER);
                tokenizer.consumeCharacter('=');
                if ('\"' != tokenizer.previewChar()) {
                    object = tokenizer.consumeToken(TOKEN_MATCHER);
                } else {
                    tokenizer.consumeCharacter('\"');
                    object = new StringBuilder();
                    while ('\"' != tokenizer.previewChar()) {
                        if ('\\' == tokenizer.previewChar()) {
                            tokenizer.consumeCharacter('\\');
                            ((StringBuilder)object).append(tokenizer.consumeCharacter(CharMatcher.ascii()));
                            continue;
                        }
                        ((StringBuilder)object).append(tokenizer.consumeToken(QUOTED_TEXT_MATCHER));
                    }
                    object = ((StringBuilder)object).toString();
                    tokenizer.consumeCharacter('\"');
                }
                builder.put(string5, object);
            } while (true);
        }
        catch (IllegalStateException illegalStateException) {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("Could not parse '");
            stringBuilder.append(string2);
            stringBuilder.append("'");
            throw new IllegalArgumentException(stringBuilder.toString(), illegalStateException);
        }
    }

    public Optional<Charset> charset() {
        String string2;
        Object object = this.parsedCharset;
        Serializable serializable = object;
        if (object != null) return serializable;
        serializable = Optional.absent();
        Iterator iterator2 = ((ImmutableList)this.parameters.get((Object)CHARSET_ATTRIBUTE)).iterator();
        object = null;
        do {
            if (!iterator2.hasNext()) {
                this.parsedCharset = serializable;
                return serializable;
            }
            string2 = (String)iterator2.next();
            if (object == null) {
                serializable = Optional.of(Charset.forName(string2));
                object = string2;
                continue;
            }
            if (!((String)object).equals(string2)) break;
        } while (true);
        serializable = new StringBuilder();
        ((StringBuilder)serializable).append("Multiple charset values defined: ");
        ((StringBuilder)serializable).append((String)object);
        ((StringBuilder)serializable).append(", ");
        ((StringBuilder)serializable).append(string2);
        throw new IllegalStateException(((StringBuilder)serializable).toString());
    }

    public boolean equals(@NullableDecl Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (!(object instanceof MediaType)) return false;
        object = (MediaType)object;
        if (!this.type.equals(((MediaType)object).type)) return false;
        if (!this.subtype.equals(((MediaType)object).subtype)) return false;
        if (!this.parametersAsMap().equals(MediaType.super.parametersAsMap())) return false;
        return bl;
    }

    public boolean hasWildcard() {
        if (WILDCARD.equals(this.type)) return true;
        if (WILDCARD.equals(this.subtype)) return true;
        return false;
    }

    public int hashCode() {
        int n;
        int n2 = n = this.hashCode;
        if (n != 0) return n2;
        this.hashCode = n2 = Objects.hashCode(this.type, this.subtype, this.parametersAsMap());
        return n2;
    }

    public boolean is(MediaType mediaType) {
        if (!mediaType.type.equals(WILDCARD)) {
            if (!mediaType.type.equals(this.type)) return false;
        }
        if (!mediaType.subtype.equals(WILDCARD)) {
            if (!mediaType.subtype.equals(this.subtype)) return false;
        }
        if (!((AbstractCollection)this.parameters.entries()).containsAll(mediaType.parameters.entries())) return false;
        return true;
    }

    public ImmutableListMultimap<String, String> parameters() {
        return this.parameters;
    }

    public String subtype() {
        return this.subtype;
    }

    public String toString() {
        String string2;
        String string3 = string2 = this.toString;
        if (string2 != null) return string3;
        this.toString = string3 = this.computeToString();
        return string3;
    }

    public String type() {
        return this.type;
    }

    public MediaType withCharset(Charset charset) {
        Preconditions.checkNotNull(charset);
        MediaType mediaType = this.withParameter(CHARSET_ATTRIBUTE, charset.name());
        mediaType.parsedCharset = Optional.of(charset);
        return mediaType;
    }

    public MediaType withParameter(String string2, String string3) {
        return this.withParameters(string2, ImmutableSet.of(string3));
    }

    public MediaType withParameters(Multimap<String, String> multimap) {
        return MediaType.create(this.type, this.subtype, multimap);
    }

    public MediaType withParameters(String string2, Iterable<String> object) {
        Preconditions.checkNotNull(string2);
        Preconditions.checkNotNull(object);
        string2 = MediaType.normalizeToken(string2);
        ImmutableListMultimap.Builder builder = ImmutableListMultimap.builder();
        for (Map.Entry entry : this.parameters.entries()) {
            String string3 = (String)entry.getKey();
            if (string2.equals(string3)) continue;
            builder.put(string3, entry.getValue());
        }
        object = object.iterator();
        do {
            if (!object.hasNext()) {
                object = new MediaType(this.type, this.subtype, (ImmutableListMultimap<String, String>)builder.build());
                if (string2.equals(CHARSET_ATTRIBUTE)) return (MediaType)MoreObjects.firstNonNull(KNOWN_TYPES.get(object), object);
                ((MediaType)object).parsedCharset = this.parsedCharset;
                return (MediaType)MoreObjects.firstNonNull(KNOWN_TYPES.get(object), object);
            }
            builder.put(string2, MediaType.normalizeParameterValue(string2, (String)object.next()));
        } while (true);
    }

    public MediaType withoutParameters() {
        if (!this.parameters.isEmpty()) return MediaType.create(this.type, this.subtype);
        return this;
    }

    private static final class Tokenizer {
        final String input;
        int position = 0;

        Tokenizer(String string2) {
            this.input = string2;
        }

        char consumeCharacter(char c) {
            Preconditions.checkState(this.hasMore());
            boolean bl = this.previewChar() == c;
            Preconditions.checkState(bl);
            ++this.position;
            return c;
        }

        char consumeCharacter(CharMatcher charMatcher) {
            Preconditions.checkState(this.hasMore());
            char c = this.previewChar();
            Preconditions.checkState(charMatcher.matches(c));
            ++this.position;
            return c;
        }

        String consumeToken(CharMatcher object) {
            int n = this.position;
            object = this.consumeTokenIfPresent((CharMatcher)object);
            boolean bl = this.position != n;
            Preconditions.checkState(bl);
            return object;
        }

        String consumeTokenIfPresent(CharMatcher object) {
            Preconditions.checkState(this.hasMore());
            int n = this.position;
            this.position = ((CharMatcher)object).negate().indexIn(this.input, n);
            if (!this.hasMore()) return this.input.substring(n);
            return this.input.substring(n, this.position);
        }

        boolean hasMore() {
            int n = this.position;
            if (n < 0) return false;
            if (n >= this.input.length()) return false;
            return true;
        }

        char previewChar() {
            Preconditions.checkState(this.hasMore());
            return this.input.charAt(this.position);
        }
    }

}


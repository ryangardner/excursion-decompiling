/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.http;

import com.google.api.client.http.LowLevelHttpRequest;
import com.google.api.client.http.LowLevelHttpResponse;
import com.google.api.client.util.ArrayValueMap;
import com.google.api.client.util.Base64;
import com.google.api.client.util.ClassInfo;
import com.google.api.client.util.Data;
import com.google.api.client.util.FieldInfo;
import com.google.api.client.util.GenericData;
import com.google.api.client.util.Key;
import com.google.api.client.util.Preconditions;
import com.google.api.client.util.StringUtils;
import com.google.api.client.util.Throwables;
import com.google.api.client.util.Types;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class HttpHeaders
extends GenericData {
    @Key(value="Accept")
    private List<String> accept;
    @Key(value="Accept-Encoding")
    private List<String> acceptEncoding = new ArrayList<String>(Collections.singleton("gzip"));
    @Key(value="Age")
    private List<Long> age;
    @Key(value="WWW-Authenticate")
    private List<String> authenticate;
    @Key(value="Authorization")
    private List<String> authorization;
    @Key(value="Cache-Control")
    private List<String> cacheControl;
    @Key(value="Content-Encoding")
    private List<String> contentEncoding;
    @Key(value="Content-Length")
    private List<Long> contentLength;
    @Key(value="Content-MD5")
    private List<String> contentMD5;
    @Key(value="Content-Range")
    private List<String> contentRange;
    @Key(value="Content-Type")
    private List<String> contentType;
    @Key(value="Cookie")
    private List<String> cookie;
    @Key(value="Date")
    private List<String> date;
    @Key(value="ETag")
    private List<String> etag;
    @Key(value="Expires")
    private List<String> expires;
    @Key(value="If-Match")
    private List<String> ifMatch;
    @Key(value="If-Modified-Since")
    private List<String> ifModifiedSince;
    @Key(value="If-None-Match")
    private List<String> ifNoneMatch;
    @Key(value="If-Range")
    private List<String> ifRange;
    @Key(value="If-Unmodified-Since")
    private List<String> ifUnmodifiedSince;
    @Key(value="Last-Modified")
    private List<String> lastModified;
    @Key(value="Location")
    private List<String> location;
    @Key(value="MIME-Version")
    private List<String> mimeVersion;
    @Key(value="Range")
    private List<String> range;
    @Key(value="Retry-After")
    private List<String> retryAfter;
    @Key(value="User-Agent")
    private List<String> userAgent;
    @Key(value="Warning")
    private List<String> warning;

    public HttpHeaders() {
        super(EnumSet.of(GenericData.Flags.IGNORE_CASE));
    }

    private static void addHeader(Logger object, StringBuilder stringBuilder, StringBuilder stringBuilder2, LowLevelHttpRequest lowLevelHttpRequest, String string2, Object object2, Writer writer) throws IOException {
        if (object2 == null) return;
        if (Data.isNull(object2)) {
            return;
        }
        object2 = HttpHeaders.toStringValue(object2);
        object = !"Authorization".equalsIgnoreCase(string2) && !"Cookie".equalsIgnoreCase(string2) || object != null && ((Logger)object).isLoggable(Level.ALL) ? object2 : "<Not Logged>";
        if (stringBuilder != null) {
            stringBuilder.append(string2);
            stringBuilder.append(": ");
            stringBuilder.append((String)object);
            stringBuilder.append(StringUtils.LINE_SEPARATOR);
        }
        if (stringBuilder2 != null) {
            stringBuilder2.append(" -H '");
            stringBuilder2.append(string2);
            stringBuilder2.append(": ");
            stringBuilder2.append((String)object);
            stringBuilder2.append("'");
        }
        if (lowLevelHttpRequest != null) {
            lowLevelHttpRequest.addHeader(string2, (String)object2);
        }
        if (writer == null) return;
        writer.write(string2);
        writer.write(": ");
        writer.write((String)object2);
        writer.write("\r\n");
    }

    private <T> List<T> getAsList(T t) {
        if (t == null) {
            return null;
        }
        ArrayList<T> arrayList = new ArrayList<T>();
        arrayList.add(t);
        return arrayList;
    }

    private <T> T getFirstHeaderValue(List<T> list) {
        if (list == null) {
            list = null;
            return (T)list;
        }
        list = list.get(0);
        return (T)list;
    }

    private static Object parseValue(Type type, List<Type> list, String string2) {
        return Data.parsePrimitiveValue(Data.resolveWildcardTypeOrTypeVariable(list, type), string2);
    }

    static void serializeHeaders(HttpHeaders httpHeaders, StringBuilder stringBuilder, StringBuilder stringBuilder2, Logger logger, LowLevelHttpRequest lowLevelHttpRequest) throws IOException {
        HttpHeaders.serializeHeaders(httpHeaders, stringBuilder, stringBuilder2, logger, lowLevelHttpRequest, null);
    }

    /*
     * Unable to fully structure code
     */
    static void serializeHeaders(HttpHeaders var0, StringBuilder var1_1, StringBuilder var2_2, Logger var3_3, LowLevelHttpRequest var4_4, Writer var5_5) throws IOException {
        var6_6 = new HashSet<String>();
        var7_7 = var0.entrySet().iterator();
        block0 : do {
            if (!var7_7.hasNext()) {
                if (var5_5 == null) return;
                var5_5.flush();
                return;
            }
            var8_8 = var7_7.next();
            var9_9 = var8_8.getKey();
            Preconditions.checkArgument(var6_6.add(var9_9), "multiple headers of the same name (headers are case insensitive): %s", new Object[]{var9_9});
            if ((var8_8 = var8_8.getValue()) == null) continue;
            var10_10 = var0.getClassInfo().getFieldInfo(var9_9);
            if (var10_10 != null) {
                var9_9 = var10_10.getName();
            }
            var10_10 = var8_8.getClass();
            if (!(var8_8 instanceof Iterable) && !var10_10.isArray()) {
                HttpHeaders.addHeader(var3_3, var1_1, var2_2, var4_4, var9_9, var8_8, var5_5);
                continue;
            }
            var8_8 = Types.iterableOf(var8_8).iterator();
            do {
                if (var8_8.hasNext()) ** break;
                continue block0;
                HttpHeaders.addHeader(var3_3, var1_1, var2_2, var4_4, var9_9, var8_8.next(), var5_5);
            } while (true);
            break;
        } while (true);
    }

    public static void serializeHeadersForMultipartRequests(HttpHeaders httpHeaders, StringBuilder stringBuilder, Logger logger, Writer writer) throws IOException {
        HttpHeaders.serializeHeaders(httpHeaders, stringBuilder, null, logger, null, writer);
    }

    private static String toStringValue(Object object) {
        if (!(object instanceof Enum)) return object.toString();
        return FieldInfo.of((Enum)object).getName();
    }

    public HttpHeaders addWarning(String string2) {
        if (string2 == null) {
            return this;
        }
        List<String> list = this.warning;
        if (list == null) {
            this.warning = this.getAsList(string2);
            return this;
        }
        list.add(string2);
        return this;
    }

    @Override
    public HttpHeaders clone() {
        return (HttpHeaders)super.clone();
    }

    public final void fromHttpHeaders(HttpHeaders httpHeaders) {
        try {
            ParseHeaderState parseHeaderState = new ParseHeaderState(this, null);
            HeaderParsingFakeLevelHttpRequest headerParsingFakeLevelHttpRequest = new HeaderParsingFakeLevelHttpRequest(this, parseHeaderState);
            HttpHeaders.serializeHeaders(httpHeaders, null, null, null, headerParsingFakeLevelHttpRequest);
            parseHeaderState.finish();
            return;
        }
        catch (IOException iOException) {
            throw Throwables.propagate(iOException);
        }
    }

    public final void fromHttpResponse(LowLevelHttpResponse lowLevelHttpResponse, StringBuilder object) throws IOException {
        this.clear();
        object = new ParseHeaderState(this, (StringBuilder)object);
        int n = lowLevelHttpResponse.getHeaderCount();
        int n2 = 0;
        do {
            if (n2 >= n) {
                ((ParseHeaderState)object).finish();
                return;
            }
            this.parseHeader(lowLevelHttpResponse.getHeaderName(n2), lowLevelHttpResponse.getHeaderValue(n2), (ParseHeaderState)object);
            ++n2;
        } while (true);
    }

    public final String getAccept() {
        return this.getFirstHeaderValue(this.accept);
    }

    public final String getAcceptEncoding() {
        return this.getFirstHeaderValue(this.acceptEncoding);
    }

    public final Long getAge() {
        return this.getFirstHeaderValue(this.age);
    }

    public final String getAuthenticate() {
        return this.getFirstHeaderValue(this.authenticate);
    }

    public final List<String> getAuthenticateAsList() {
        return this.authenticate;
    }

    public final String getAuthorization() {
        return this.getFirstHeaderValue(this.authorization);
    }

    public final List<String> getAuthorizationAsList() {
        return this.authorization;
    }

    public final String getCacheControl() {
        return this.getFirstHeaderValue(this.cacheControl);
    }

    public final String getContentEncoding() {
        return this.getFirstHeaderValue(this.contentEncoding);
    }

    public final Long getContentLength() {
        return this.getFirstHeaderValue(this.contentLength);
    }

    public final String getContentMD5() {
        return this.getFirstHeaderValue(this.contentMD5);
    }

    public final String getContentRange() {
        return this.getFirstHeaderValue(this.contentRange);
    }

    public final String getContentType() {
        return this.getFirstHeaderValue(this.contentType);
    }

    public final String getCookie() {
        return this.getFirstHeaderValue(this.cookie);
    }

    public final String getDate() {
        return this.getFirstHeaderValue(this.date);
    }

    public final String getETag() {
        return this.getFirstHeaderValue(this.etag);
    }

    public final String getExpires() {
        return this.getFirstHeaderValue(this.expires);
    }

    public String getFirstHeaderStringValue(String object) {
        if ((object = this.get(((String)object).toLowerCase(Locale.US))) == null) {
            return null;
        }
        Object object2 = object.getClass();
        if (!(object instanceof Iterable)) {
            if (!((Class)object2).isArray()) return HttpHeaders.toStringValue(object);
        }
        if (!(object2 = Types.iterableOf(object).iterator()).hasNext()) return HttpHeaders.toStringValue(object);
        return HttpHeaders.toStringValue(object2.next());
    }

    public List<String> getHeaderStringValues(String arrayList) {
        Iterator iterator2 = this.get(((String)((Object)arrayList)).toLowerCase(Locale.US));
        if (iterator2 == null) {
            return Collections.emptyList();
        }
        arrayList = iterator2.getClass();
        if (!(iterator2 instanceof Iterable)) {
            if (!((Class)((Object)arrayList)).isArray()) return Collections.singletonList(HttpHeaders.toStringValue(iterator2));
        }
        arrayList = new ArrayList<String>();
        iterator2 = Types.iterableOf(iterator2).iterator();
        while (iterator2.hasNext()) {
            arrayList.add(HttpHeaders.toStringValue(iterator2.next()));
        }
        return Collections.unmodifiableList(arrayList);
    }

    public final String getIfMatch() {
        return this.getFirstHeaderValue(this.ifMatch);
    }

    public final String getIfModifiedSince() {
        return this.getFirstHeaderValue(this.ifModifiedSince);
    }

    public final String getIfNoneMatch() {
        return this.getFirstHeaderValue(this.ifNoneMatch);
    }

    public final String getIfRange() {
        return this.getFirstHeaderValue(this.ifRange);
    }

    public final String getIfUnmodifiedSince() {
        return this.getFirstHeaderValue(this.ifUnmodifiedSince);
    }

    public final String getLastModified() {
        return this.getFirstHeaderValue(this.lastModified);
    }

    public final String getLocation() {
        return this.getFirstHeaderValue(this.location);
    }

    public final String getMimeVersion() {
        return this.getFirstHeaderValue(this.mimeVersion);
    }

    public final String getRange() {
        return this.getFirstHeaderValue(this.range);
    }

    public final String getRetryAfter() {
        return this.getFirstHeaderValue(this.retryAfter);
    }

    public final String getUserAgent() {
        return this.getFirstHeaderValue(this.userAgent);
    }

    public final List<String> getWarning() {
        if (this.warning != null) return new ArrayList<String>(this.warning);
        return null;
    }

    void parseHeader(String collection, String string2, ParseHeaderState object) {
        Object object2;
        ArrayList arrayList = ((ParseHeaderState)object).context;
        Object object3 = ((ParseHeaderState)object).classInfo;
        ArrayValueMap arrayValueMap = ((ParseHeaderState)object).arrayValueMap;
        object = ((ParseHeaderState)object).logger;
        if (object != null) {
            object2 = new StringBuilder();
            ((StringBuilder)object2).append((String)((Object)collection));
            ((StringBuilder)object2).append(": ");
            ((StringBuilder)object2).append(string2);
            ((StringBuilder)object).append(((StringBuilder)object2).toString());
            ((StringBuilder)object).append(StringUtils.LINE_SEPARATOR);
        }
        if ((object3 = ((ClassInfo)object3).getFieldInfo((String)((Object)collection))) != null) {
            object2 = Data.resolveWildcardTypeOrTypeVariable(arrayList, ((FieldInfo)object3).getGenericType());
            if (Types.isArray((Type)object2)) {
                collection = Types.getRawArrayComponentType(arrayList, Types.getArrayComponentType((Type)object2));
                arrayValueMap.put(((FieldInfo)object3).getField(), (Class<?>)((Object)collection), HttpHeaders.parseValue((Type)((Object)collection), arrayList, string2));
                return;
            }
            if (!Types.isAssignableToOrFrom(Types.getRawArrayComponentType(arrayList, (Type)object2), Iterable.class)) {
                ((FieldInfo)object3).setValue(this, HttpHeaders.parseValue((Type)object2, arrayList, string2));
                return;
            }
            object = (Collection)((FieldInfo)object3).getValue(this);
            collection = object;
            if (object == null) {
                collection = Data.newCollectionInstance((Type)object2);
                ((FieldInfo)object3).setValue(this, collection);
            }
            object = object2 == Object.class ? null : Types.getIterableParameter((Type)object2);
            collection.add(HttpHeaders.parseValue((Type)object, arrayList, string2));
            return;
        }
        arrayList = (ArrayList)this.get(collection);
        object = arrayList;
        if (arrayList == null) {
            object = new ArrayList<String>();
            this.set((String)((Object)collection), object);
        }
        ((ArrayList)object).add(string2);
    }

    @Override
    public HttpHeaders set(String string2, Object object) {
        return (HttpHeaders)super.set(string2, object);
    }

    public HttpHeaders setAccept(String string2) {
        this.accept = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setAcceptEncoding(String string2) {
        this.acceptEncoding = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setAge(Long l) {
        this.age = this.getAsList(l);
        return this;
    }

    public HttpHeaders setAuthenticate(String string2) {
        this.authenticate = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setAuthorization(String string2) {
        return this.setAuthorization(this.getAsList(string2));
    }

    public HttpHeaders setAuthorization(List<String> list) {
        this.authorization = list;
        return this;
    }

    public HttpHeaders setBasicAuthentication(String charSequence, String string2) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(Preconditions.checkNotNull(charSequence));
        stringBuilder.append(":");
        stringBuilder.append(Preconditions.checkNotNull(string2));
        string2 = Base64.encodeBase64String(StringUtils.getBytesUtf8(stringBuilder.toString()));
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Basic ");
        ((StringBuilder)charSequence).append(string2);
        return this.setAuthorization(((StringBuilder)charSequence).toString());
    }

    public HttpHeaders setCacheControl(String string2) {
        this.cacheControl = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setContentEncoding(String string2) {
        this.contentEncoding = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setContentLength(Long l) {
        this.contentLength = this.getAsList(l);
        return this;
    }

    public HttpHeaders setContentMD5(String string2) {
        this.contentMD5 = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setContentRange(String string2) {
        this.contentRange = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setContentType(String string2) {
        this.contentType = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setCookie(String string2) {
        this.cookie = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setDate(String string2) {
        this.date = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setETag(String string2) {
        this.etag = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setExpires(String string2) {
        this.expires = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setIfMatch(String string2) {
        this.ifMatch = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setIfModifiedSince(String string2) {
        this.ifModifiedSince = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setIfNoneMatch(String string2) {
        this.ifNoneMatch = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setIfRange(String string2) {
        this.ifRange = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setIfUnmodifiedSince(String string2) {
        this.ifUnmodifiedSince = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setLastModified(String string2) {
        this.lastModified = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setLocation(String string2) {
        this.location = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setMimeVersion(String string2) {
        this.mimeVersion = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setRange(String string2) {
        this.range = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setRetryAfter(String string2) {
        this.retryAfter = this.getAsList(string2);
        return this;
    }

    public HttpHeaders setUserAgent(String string2) {
        this.userAgent = this.getAsList(string2);
        return this;
    }

    private static class HeaderParsingFakeLevelHttpRequest
    extends LowLevelHttpRequest {
        private final ParseHeaderState state;
        private final HttpHeaders target;

        HeaderParsingFakeLevelHttpRequest(HttpHeaders httpHeaders, ParseHeaderState parseHeaderState) {
            this.target = httpHeaders;
            this.state = parseHeaderState;
        }

        @Override
        public void addHeader(String string2, String string3) {
            this.target.parseHeader(string2, string3, this.state);
        }

        @Override
        public LowLevelHttpResponse execute() throws IOException {
            throw new UnsupportedOperationException();
        }
    }

    private static final class ParseHeaderState {
        final ArrayValueMap arrayValueMap;
        final ClassInfo classInfo;
        final List<Type> context;
        final StringBuilder logger;

        public ParseHeaderState(HttpHeaders httpHeaders, StringBuilder stringBuilder) {
            Class<?> class_ = httpHeaders.getClass();
            this.context = Arrays.asList(class_);
            this.classInfo = ClassInfo.of(class_, true);
            this.logger = stringBuilder;
            this.arrayValueMap = new ArrayValueMap(httpHeaders);
        }

        void finish() {
            this.arrayValueMap.setValues();
        }
    }

}


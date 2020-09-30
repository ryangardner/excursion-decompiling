/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.collections.ArraysKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0010\u0011\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0007\u0018\u0000 \u00182\u00020\u0001:\u0001\u0018B-\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0003\u0012\f\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\u000b\u001a\u0004\u0018\u00010\f2\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\fH\u0007J\u0013\u0010\u000e\u001a\u00020\u000f2\b\u0010\u0010\u001a\u0004\u0018\u00010\u0001H\u0096\u0002J\b\u0010\u0011\u001a\u00020\u0012H\u0016J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0014\u001a\u00020\u0003J\r\u0010\u0005\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u0015J\b\u0010\u0016\u001a\u00020\u0003H\u0016J\r\u0010\u0004\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u0017R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0016\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\u00030\u0007X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\tR\u0013\u0010\u0005\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\nR\u0013\u0010\u0004\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\n\u00a8\u0006\u0019"}, d2={"Lokhttp3/MediaType;", "", "mediaType", "", "type", "subtype", "parameterNamesAndValues", "", "(Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;)V", "[Ljava/lang/String;", "()Ljava/lang/String;", "charset", "Ljava/nio/charset/Charset;", "defaultValue", "equals", "", "other", "hashCode", "", "parameter", "name", "-deprecated_subtype", "toString", "-deprecated_type", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class MediaType {
    public static final Companion Companion = new Companion(null);
    private static final Pattern PARAMETER;
    private static final String QUOTED = "\"([^\"]*)\"";
    private static final String TOKEN = "([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)";
    private static final Pattern TYPE_SUBTYPE;
    private final String mediaType;
    private final String[] parameterNamesAndValues;
    private final String subtype;
    private final String type;

    static {
        TYPE_SUBTYPE = Pattern.compile("([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)/([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)");
        PARAMETER = Pattern.compile(";\\s*(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)=(?:([a-zA-Z0-9-!#$%&'*+.^_`{|}~]+)|\"([^\"]*)\"))?");
    }

    private MediaType(String string2, String string3, String string4, String[] arrstring) {
        this.mediaType = string2;
        this.type = string3;
        this.subtype = string4;
        this.parameterNamesAndValues = arrstring;
    }

    public /* synthetic */ MediaType(String string2, String string3, String string4, String[] arrstring, DefaultConstructorMarker defaultConstructorMarker) {
        this(string2, string3, string4, arrstring);
    }

    public static /* synthetic */ Charset charset$default(MediaType mediaType, Charset charset, int n, Object object) {
        if ((n & 1) == 0) return mediaType.charset(charset);
        charset = null;
        return mediaType.charset(charset);
    }

    @JvmStatic
    public static final MediaType get(String string2) {
        return Companion.get(string2);
    }

    @JvmStatic
    public static final MediaType parse(String string2) {
        return Companion.parse(string2);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="subtype", imports={}))
    public final String -deprecated_subtype() {
        return this.subtype;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="type", imports={}))
    public final String -deprecated_type() {
        return this.type;
    }

    public final Charset charset() {
        return MediaType.charset$default(this, null, 1, null);
    }

    public final Charset charset(Charset charset) {
        String string2 = this.parameter("charset");
        Charset charset2 = charset;
        if (string2 == null) return charset2;
        try {
            return Charset.forName(string2);
        }
        catch (IllegalArgumentException illegalArgumentException) {
            return charset;
        }
    }

    public boolean equals(Object object) {
        if (!(object instanceof MediaType)) return false;
        if (!Intrinsics.areEqual(((MediaType)object).mediaType, this.mediaType)) return false;
        return true;
    }

    public int hashCode() {
        return this.mediaType.hashCode();
    }

    public final String parameter(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "name");
        IntProgression intProgression = RangesKt.step(ArraysKt.getIndices(this.parameterNamesAndValues), 2);
        int n = intProgression.getFirst();
        int n2 = intProgression.getLast();
        int n3 = intProgression.getStep();
        if (n3 >= 0) {
            if (n > n2) return null;
        } else if (n < n2) return null;
        while (!StringsKt.equals(this.parameterNamesAndValues[n], string2, true)) {
            if (n == n2) return null;
            n += n3;
        }
        return this.parameterNamesAndValues[n + 1];
    }

    public final String subtype() {
        return this.subtype;
    }

    public String toString() {
        return this.mediaType;
    }

    public final String type() {
        return this.type;
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(bv={1, 0, 3}, d1={"\u0000$\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0015\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0007H\u0007\u00a2\u0006\u0002\b\rJ\u0017\u0010\u000e\u001a\u0004\u0018\u00010\u000b2\u0006\u0010\f\u001a\u00020\u0007H\u0007\u00a2\u0006\u0002\b\u000fJ\u0011\u0010\u0010\u001a\u00020\u000b*\u00020\u0007H\u0007\u00a2\u0006\u0002\b\nJ\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u000b*\u00020\u0007H\u0007\u00a2\u0006\u0002\b\u000eR\u0016\u0010\u0003\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0007X\u0082T\u00a2\u0006\u0002\n\u0000R\u0016\u0010\t\u001a\n \u0005*\u0004\u0018\u00010\u00040\u0004X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0012"}, d2={"Lokhttp3/MediaType$Companion;", "", "()V", "PARAMETER", "Ljava/util/regex/Pattern;", "kotlin.jvm.PlatformType", "QUOTED", "", "TOKEN", "TYPE_SUBTYPE", "get", "Lokhttp3/MediaType;", "mediaType", "-deprecated_get", "parse", "-deprecated_parse", "toMediaType", "toMediaTypeOrNull", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="mediaType.toMediaType()", imports={"okhttp3.MediaType.Companion.toMediaType"}))
        public final MediaType -deprecated_get(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "mediaType");
            return this.get(string2);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="moved to extension function", replaceWith=@ReplaceWith(expression="mediaType.toMediaTypeOrNull()", imports={"okhttp3.MediaType.Companion.toMediaTypeOrNull"}))
        public final MediaType -deprecated_parse(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "mediaType");
            return this.parse(string2);
        }

        @JvmStatic
        public final MediaType get(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "$this$toMediaType");
            Object object = TYPE_SUBTYPE;
            Object object2 = (String[])string2;
            object = ((Pattern)object).matcher((CharSequence)object2);
            if (!((Matcher)object).lookingAt()) {
                object2 = new StringBuilder();
                object2.append("No subtype found for: \"");
                object2.append(string2);
                object2.append('\"');
                throw (Throwable)new IllegalArgumentException(object2.toString().toString());
            }
            String string3 = ((Matcher)object).group(1);
            Intrinsics.checkExpressionValueIsNotNull(string3, "typeSubtype.group(1)");
            Object object3 = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(object3, "Locale.US");
            if (string3 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            object3 = string3.toLowerCase((Locale)object3);
            Intrinsics.checkExpressionValueIsNotNull(object3, "(this as java.lang.String).toLowerCase(locale)");
            string3 = ((Matcher)object).group(2);
            Intrinsics.checkExpressionValueIsNotNull(string3, "typeSubtype.group(2)");
            Object object4 = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(object4, "Locale.US");
            if (string3 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            string3 = string3.toLowerCase((Locale)object4);
            Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.String).toLowerCase(locale)");
            object4 = new ArrayList();
            Matcher matcher = PARAMETER.matcher((CharSequence)object2);
            int n = ((Matcher)object).end();
            do {
                if (n >= string2.length()) {
                    object2 = ((Collection)object4).toArray(new String[0]);
                    if (object2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
                    return new MediaType(string2, (String)object3, string3, (String[])object2, null);
                }
                matcher.region(n, string2.length());
                if (!matcher.lookingAt()) {
                    object = new StringBuilder();
                    ((StringBuilder)object).append("Parameter is not formatted correctly: \"");
                    object2 = string2.substring(n);
                    Intrinsics.checkExpressionValueIsNotNull(object2, "(this as java.lang.String).substring(startIndex)");
                    ((StringBuilder)object).append((String)object2);
                    ((StringBuilder)object).append("\" for: \"");
                    ((StringBuilder)object).append(string2);
                    ((StringBuilder)object).append('\"');
                    throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
                }
                String string4 = matcher.group(1);
                if (string4 == null) {
                    n = matcher.end();
                    continue;
                }
                object = matcher.group(2);
                if (object == null) {
                    object2 = matcher.group(3);
                } else {
                    object2 = object;
                    if (StringsKt.startsWith$default((String)object, "'", false, 2, null)) {
                        object2 = object;
                        if (StringsKt.endsWith$default((String)object, "'", false, 2, null)) {
                            object2 = object;
                            if (((String)object).length() > 2) {
                                object2 = ((String)object).substring(1, ((String)object).length() - 1);
                                Intrinsics.checkExpressionValueIsNotNull(object2, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                            }
                        }
                    }
                }
                object = (Collection)object4;
                object.add(string4);
                object.add(object2);
                n = matcher.end();
            } while (true);
        }

        @JvmStatic
        public final MediaType parse(String object) {
            Intrinsics.checkParameterIsNotNull(object, "$this$toMediaTypeOrNull");
            try {
                return this.get((String)object);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                return null;
            }
        }
    }

}


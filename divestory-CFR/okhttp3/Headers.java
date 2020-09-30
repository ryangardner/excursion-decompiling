/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.Pair;
import kotlin.ReplaceWith;
import kotlin.TuplesKt;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.ArrayIteratorKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.jvm.internal.StringCompanionObject;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.ranges.IntProgression;
import kotlin.ranges.RangesKt;
import kotlin.text.StringsKt;
import okhttp3.internal.Util;
import okhttp3.internal.http.DatesKt;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000f\n\u0002\u0018\u0002\n\u0002\u0010\u001c\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0011\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\t\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010(\n\u0002\b\u0002\n\u0002\u0010\"\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010$\n\u0002\u0010 \n\u0002\b\u0006\u0018\u0000 '2\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0001:\u0002&'B\u0015\b\u0002\u0012\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005\u00a2\u0006\u0002\u0010\u0006J\u0006\u0010\u000b\u001a\u00020\fJ\u0013\u0010\r\u001a\u00020\u000e2\b\u0010\u000f\u001a\u0004\u0018\u00010\u0010H\u0096\u0002J\u0013\u0010\u0011\u001a\u0004\u0018\u00010\u00032\u0006\u0010\u0012\u001a\u00020\u0003H\u0086\u0002J\u0010\u0010\u0013\u001a\u0004\u0018\u00010\u00142\u0006\u0010\u0012\u001a\u00020\u0003J\u0012\u0010\u0015\u001a\u0004\u0018\u00010\u00162\u0006\u0010\u0012\u001a\u00020\u0003H\u0007J\b\u0010\u0017\u001a\u00020\tH\u0016J\u001b\u0010\u0018\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00030\u00020\u0019H\u0096\u0002J\u000e\u0010\u0012\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\tJ\f\u0010\u001b\u001a\b\u0012\u0004\u0012\u00020\u00030\u001cJ\u0006\u0010\u001d\u001a\u00020\u001eJ\r\u0010\b\u001a\u00020\tH\u0007\u00a2\u0006\u0002\b\u001fJ\u0018\u0010 \u001a\u0014\u0012\u0004\u0012\u00020\u0003\u0012\n\u0012\b\u0012\u0004\u0012\u00020\u00030\"0!J\b\u0010#\u001a\u00020\u0003H\u0016J\u000e\u0010$\u001a\u00020\u00032\u0006\u0010\u001a\u001a\u00020\tJ\u0014\u0010%\u001a\b\u0012\u0004\u0012\u00020\u00030\"2\u0006\u0010\u0012\u001a\u00020\u0003R\u0016\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00030\u0005X\u0082\u0004\u00a2\u0006\u0004\n\u0002\u0010\u0007R\u0011\u0010\b\u001a\u00020\t8G\u00a2\u0006\u0006\u001a\u0004\b\b\u0010\n\u00a8\u0006("}, d2={"Lokhttp3/Headers;", "", "Lkotlin/Pair;", "", "namesAndValues", "", "([Ljava/lang/String;)V", "[Ljava/lang/String;", "size", "", "()I", "byteCount", "", "equals", "", "other", "", "get", "name", "getDate", "Ljava/util/Date;", "getInstant", "Ljava/time/Instant;", "hashCode", "iterator", "", "index", "names", "", "newBuilder", "Lokhttp3/Headers$Builder;", "-deprecated_size", "toMultimap", "", "", "toString", "value", "values", "Builder", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class Headers
implements Iterable<Pair<? extends String, ? extends String>>,
KMappedMarker {
    public static final Companion Companion = new Companion(null);
    private final String[] namesAndValues;

    private Headers(String[] arrstring) {
        this.namesAndValues = arrstring;
    }

    public /* synthetic */ Headers(String[] arrstring, DefaultConstructorMarker defaultConstructorMarker) {
        this(arrstring);
    }

    @JvmStatic
    public static final Headers of(Map<String, String> map) {
        return Companion.of(map);
    }

    @JvmStatic
    public static final Headers of(String ... arrstring) {
        return Companion.of(arrstring);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="size", imports={}))
    public final int -deprecated_size() {
        return this.size();
    }

    public final long byteCount() {
        String[] arrstring = this.namesAndValues;
        long l = arrstring.length * 2;
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            l += (long)this.namesAndValues[n2].length();
            ++n2;
        }
        return l;
    }

    public boolean equals(Object object) {
        if (!(object instanceof Headers)) return false;
        if (!Arrays.equals(this.namesAndValues, ((Headers)object).namesAndValues)) return false;
        return true;
    }

    public final String get(String string2) {
        Intrinsics.checkParameterIsNotNull(string2, "name");
        return Headers.Companion.get(this.namesAndValues, string2);
    }

    public final Date getDate(String object) {
        Intrinsics.checkParameterIsNotNull(object, "name");
        object = this.get((String)object);
        if (object == null) return null;
        return DatesKt.toHttpDateOrNull((String)object);
    }

    public final Instant getInstant(String object) {
        Intrinsics.checkParameterIsNotNull(object, "name");
        object = this.getDate((String)object);
        if (object == null) return null;
        return ((Date)object).toInstant();
    }

    public int hashCode() {
        return Arrays.hashCode(this.namesAndValues);
    }

    @Override
    public Iterator<Pair<String, String>> iterator() {
        int n = this.size();
        Pair[] arrpair = new Pair[n];
        int n2 = 0;
        while (n2 < n) {
            arrpair[n2] = TuplesKt.to(this.name(n2), this.value(n2));
            ++n2;
        }
        return ArrayIteratorKt.iterator(arrpair);
    }

    public final String name(int n) {
        return this.namesAndValues[n * 2];
    }

    public final Set<String> names() {
        Set<String> set = new TreeSet<String>(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
        int n = this.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                set = Collections.unmodifiableSet((Set)set);
                Intrinsics.checkExpressionValueIsNotNull(set, "Collections.unmodifiableSet(result)");
                return set;
            }
            set.add(this.name(n2));
            ++n2;
        } while (true);
    }

    public final Builder newBuilder() {
        Builder builder = new Builder();
        CollectionsKt.addAll((Collection)builder.getNamesAndValues$okhttp(), this.namesAndValues);
        return builder;
    }

    public final int size() {
        return this.namesAndValues.length / 2;
    }

    public final Map<String, List<String>> toMultimap() {
        TreeMap treeMap = new TreeMap(StringsKt.getCASE_INSENSITIVE_ORDER(StringCompanionObject.INSTANCE));
        int n = this.size();
        int n2 = 0;
        while (n2 < n) {
            Object object = this.name(n2);
            Object object2 = Locale.US;
            Intrinsics.checkExpressionValueIsNotNull(object2, "Locale.US");
            if (object == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
            String string2 = ((String)object).toLowerCase((Locale)object2);
            Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).toLowerCase(locale)");
            object = object2 = (List)treeMap.get(string2);
            if (object2 == null) {
                object = new ArrayList(2);
                ((Map)treeMap).put(string2, object);
            }
            object.add(this.value(n2));
            ++n2;
        }
        return treeMap;
    }

    public String toString() {
        CharSequence charSequence = new StringBuilder();
        int n = this.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                charSequence = ((StringBuilder)charSequence).toString();
                Intrinsics.checkExpressionValueIsNotNull(charSequence, "StringBuilder().apply(builderAction).toString()");
                return charSequence;
            }
            ((StringBuilder)charSequence).append(this.name(n2));
            ((StringBuilder)charSequence).append(": ");
            ((StringBuilder)charSequence).append(this.value(n2));
            ((StringBuilder)charSequence).append("\n");
            ++n2;
        } while (true);
    }

    public final String value(int n) {
        return this.namesAndValues[n * 2 + 1];
    }

    public final List<String> values(String list) {
        Intrinsics.checkParameterIsNotNull(list, "name");
        List list2 = null;
        int n = this.size();
        for (int i = 0; i < n; ++i) {
            List list3 = list2;
            if (StringsKt.equals((String)((Object)list), this.name(i), true)) {
                list3 = list2;
                if (list2 == null) {
                    list3 = new ArrayList(2);
                }
                list3.add(this.value(i));
            }
            list2 = list3;
        }
        if (list2 == null) return CollectionsKt.emptyList();
        list = Collections.unmodifiableList(list2);
        Intrinsics.checkExpressionValueIsNotNull(list, "Collections.unmodifiableList(result)");
        return list;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010!\n\u0002\u0010\u000e\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\b\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\b\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0005J\u0018\u0010\b\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0007J\u0016\u0010\b\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\rJ\u0016\u0010\b\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005J\u000e\u0010\u000e\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u0010J\u0015\u0010\u0011\u001a\u00020\u00002\u0006\u0010\t\u001a\u00020\u0005H\u0000\u00a2\u0006\u0002\b\u0012J\u001d\u0010\u0011\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0000\u00a2\u0006\u0002\b\u0012J\u0016\u0010\u0013\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005J\u0006\u0010\u0014\u001a\u00020\u0010J\u0013\u0010\u0015\u001a\u0004\u0018\u00010\u00052\u0006\u0010\n\u001a\u00020\u0005H\u0086\u0002J\u000e\u0010\u0016\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u0005J\u0019\u0010\u0017\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\fH\u0087\u0002J\u0019\u0010\u0017\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\rH\u0086\u0002J\u0019\u0010\u0017\u001a\u00020\u00002\u0006\u0010\n\u001a\u00020\u00052\u0006\u0010\u000b\u001a\u00020\u0005H\u0086\u0002R\u001a\u0010\u0003\u001a\b\u0012\u0004\u0012\u00020\u00050\u0004X\u0004\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007\u00a8\u0006\u0018"}, d2={"Lokhttp3/Headers$Builder;", "", "()V", "namesAndValues", "", "", "getNamesAndValues$okhttp", "()Ljava/util/List;", "add", "line", "name", "value", "Ljava/time/Instant;", "Ljava/util/Date;", "addAll", "headers", "Lokhttp3/Headers;", "addLenient", "addLenient$okhttp", "addUnsafeNonAscii", "build", "get", "removeAll", "set", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Builder {
        private final List<String> namesAndValues = new ArrayList(20);

        public final Builder add(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "line");
            Object object = this;
            int n = StringsKt.indexOf$default((CharSequence)string2, ':', 0, false, 6, null);
            boolean bl = n != -1;
            if (bl) {
                String string3 = string2.substring(0, n);
                Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                if (string3 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                string3 = ((Object)StringsKt.trim((CharSequence)string3)).toString();
                string2 = string2.substring(n + 1);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
                ((Builder)object).add(string3, string2);
                return object;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("Unexpected header: ");
            ((StringBuilder)object).append(string2);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }

        public final Builder add(String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(string3, "value");
            Builder builder = this;
            Companion.checkName(string2);
            Companion.checkValue(string3, string2);
            builder.addLenient$okhttp(string2, string3);
            return builder;
        }

        public final Builder add(String string2, Instant instant) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(instant, "value");
            Builder builder = this;
            builder.add(string2, new Date(instant.toEpochMilli()));
            return builder;
        }

        public final Builder add(String string2, Date date) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(date, "value");
            Builder builder = this;
            builder.add(string2, DatesKt.toHttpDateString(date));
            return builder;
        }

        public final Builder addAll(Headers headers) {
            Intrinsics.checkParameterIsNotNull(headers, "headers");
            Builder builder = this;
            int n = headers.size();
            int n2 = 0;
            while (n2 < n) {
                builder.addLenient$okhttp(headers.name(n2), headers.value(n2));
                ++n2;
            }
            return builder;
        }

        public final Builder addLenient$okhttp(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "line");
            Builder builder = this;
            int n = StringsKt.indexOf$default((CharSequence)string2, ':', 1, false, 4, null);
            if (n != -1) {
                String string3 = string2.substring(0, n);
                Intrinsics.checkExpressionValueIsNotNull(string3, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                string2 = string2.substring(n + 1);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
                builder.addLenient$okhttp(string3, string2);
                return builder;
            }
            if (string2.charAt(0) == ':') {
                string2 = string2.substring(1);
                Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).substring(startIndex)");
                builder.addLenient$okhttp("", string2);
                return builder;
            }
            builder.addLenient$okhttp("", string2);
            return builder;
        }

        public final Builder addLenient$okhttp(String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(string3, "value");
            Builder builder = this;
            builder.namesAndValues.add(string2);
            builder.namesAndValues.add(((Object)StringsKt.trim((CharSequence)string3)).toString());
            return builder;
        }

        public final Builder addUnsafeNonAscii(String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(string3, "value");
            Builder builder = this;
            Companion.checkName(string2);
            builder.addLenient$okhttp(string2, string3);
            return builder;
        }

        public final Headers build() {
            String[] arrstring = ((Collection)this.namesAndValues).toArray(new String[0]);
            if (arrstring == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<T>");
            return new Headers(arrstring, null);
        }

        public final String get(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            IntProgression intProgression = RangesKt.step(RangesKt.downTo(this.namesAndValues.size() - 2, 0), 2);
            int n = intProgression.getFirst();
            int n2 = intProgression.getLast();
            int n3 = intProgression.getStep();
            if (n3 >= 0) {
                if (n > n2) return null;
            } else if (n < n2) return null;
            while (!StringsKt.equals(string2, this.namesAndValues.get(n), true)) {
                if (n == n2) return null;
                n += n3;
            }
            return this.namesAndValues.get(n + 1);
        }

        public final List<String> getNamesAndValues$okhttp() {
            return this.namesAndValues;
        }

        public final Builder removeAll(String string2) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Builder builder = this;
            int n = 0;
            while (n < builder.namesAndValues.size()) {
                int n2 = n;
                if (StringsKt.equals(string2, builder.namesAndValues.get(n), true)) {
                    builder.namesAndValues.remove(n);
                    builder.namesAndValues.remove(n);
                    n2 = n - 2;
                }
                n = n2 + 2;
            }
            return builder;
        }

        public final Builder set(String string2, String string3) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(string3, "value");
            Builder builder = this;
            Companion.checkName(string2);
            Companion.checkValue(string3, string2);
            builder.removeAll(string2);
            builder.addLenient$okhttp(string2, string3);
            return builder;
        }

        public final Builder set(String string2, Instant instant) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(instant, "value");
            return this.set(string2, new Date(instant.toEpochMilli()));
        }

        public final Builder set(String string2, Date date) {
            Intrinsics.checkParameterIsNotNull(string2, "name");
            Intrinsics.checkParameterIsNotNull(date, "value");
            Builder builder = this;
            builder.set(string2, DatesKt.toHttpDateString(date));
            return builder;
        }
    }

    /*
     * Illegal identifiers - consider using --renameillegalidents true
     */
    @Metadata(bv={1, 0, 3}, d1={"\u00002\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0011\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010$\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J\u0018\u0010\u0007\u001a\u00020\u00042\u0006\u0010\b\u001a\u00020\u00062\u0006\u0010\u0005\u001a\u00020\u0006H\u0002J%\u0010\t\u001a\u0004\u0018\u00010\u00062\f\u0010\n\u001a\b\u0012\u0004\u0012\u00020\u00060\u000b2\u0006\u0010\u0005\u001a\u00020\u0006H\u0002\u00a2\u0006\u0002\u0010\fJ#\u0010\r\u001a\u00020\u000e2\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u000b\"\u00020\u0006H\u0007\u00a2\u0006\u0004\b\u000f\u0010\u0010J#\u0010\u000f\u001a\u00020\u000e2\u0012\u0010\n\u001a\n\u0012\u0006\b\u0001\u0012\u00020\u00060\u000b\"\u00020\u0006H\u0007\u00a2\u0006\u0004\b\u0011\u0010\u0010J!\u0010\u000f\u001a\u00020\u000e2\u0012\u0010\u0012\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0013H\u0007\u00a2\u0006\u0002\b\u0011J\u001d\u0010\u0014\u001a\u00020\u000e*\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00060\u0013H\u0007\u00a2\u0006\u0002\b\u000f\u00a8\u0006\u0015"}, d2={"Lokhttp3/Headers$Companion;", "", "()V", "checkName", "", "name", "", "checkValue", "value", "get", "namesAndValues", "", "([Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;", "headersOf", "Lokhttp3/Headers;", "of", "([Ljava/lang/String;)Lokhttp3/Headers;", "-deprecated_of", "headers", "", "toHeaders", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final void checkName(String string2) {
            int n = ((CharSequence)string2).length() > 0 ? 1 : 0;
            if (n == 0) throw (Throwable)new IllegalArgumentException("name is empty".toString());
            int n2 = string2.length();
            n = 0;
            while (n < n2) {
                char c = string2.charAt(n);
                boolean bl = '!' <= c && '~' >= c;
                if (!bl) {
                    throw (Throwable)new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in header name: %s", c, n, string2).toString());
                }
                ++n;
            }
        }

        private final void checkValue(String string2, String string3) {
            int n = string2.length();
            int n2 = 0;
            while (n2 < n) {
                char c = string2.charAt(n2);
                boolean bl = c == '\t' || ' ' <= c && '~' >= c;
                if (!bl) {
                    throw (Throwable)new IllegalArgumentException(Util.format("Unexpected char %#04x at %d in %s value: %s", c, n2, string3, string2).toString());
                }
                ++n2;
            }
        }

        private final String get(String[] arrstring, String string2) {
            IntProgression intProgression = RangesKt.step(RangesKt.downTo(arrstring.length - 2, 0), 2);
            int n = intProgression.getFirst();
            int n2 = intProgression.getLast();
            int n3 = intProgression.getStep();
            if (n3 >= 0) {
                if (n > n2) return null;
            } else if (n < n2) return null;
            while (!StringsKt.equals(string2, arrstring[n], true)) {
                if (n == n2) return null;
                n += n3;
            }
            return arrstring[n + 1];
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="function moved to extension", replaceWith=@ReplaceWith(expression="headers.toHeaders()", imports={}))
        public final Headers -deprecated_of(Map<String, String> map) {
            Intrinsics.checkParameterIsNotNull(map, "headers");
            return this.of(map);
        }

        @Deprecated(level=DeprecationLevel.ERROR, message="function name changed", replaceWith=@ReplaceWith(expression="headersOf(*namesAndValues)", imports={}))
        public final Headers -deprecated_of(String ... arrstring) {
            Intrinsics.checkParameterIsNotNull(arrstring, "namesAndValues");
            return this.of(Arrays.copyOf(arrstring, arrstring.length));
        }

        @JvmStatic
        public final Headers of(Map<String, String> object) {
            Intrinsics.checkParameterIsNotNull(object, "$this$toHeaders");
            String[] arrstring = new String[object.size() * 2];
            object = object.entrySet().iterator();
            int n = 0;
            while (object.hasNext()) {
                Object object2 = (Map.Entry)object.next();
                String string2 = (String)object2.getKey();
                object2 = (String)object2.getValue();
                if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                string2 = ((Object)StringsKt.trim((CharSequence)string2)).toString();
                if (object2 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                String string3 = ((Object)StringsKt.trim((CharSequence)object2)).toString();
                object2 = this;
                Companion.super.checkName(string2);
                Companion.super.checkValue(string3, string2);
                arrstring[n] = string2;
                arrstring[n + 1] = string3;
                n += 2;
            }
            return new Headers(arrstring, null);
        }

        @JvmStatic
        public final Headers of(String ... arrstring) {
            int n;
            Object object;
            Intrinsics.checkParameterIsNotNull(arrstring, "namesAndValues");
            int n2 = arrstring.length % 2 == 0 ? 1 : 0;
            if (n2 == 0) throw (Throwable)new IllegalArgumentException("Expected alternating header names and values".toString());
            if ((arrstring = arrstring.clone()) == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.Array<kotlin.String>");
            int n3 = arrstring.length;
            for (n2 = 0; n2 < n3; ++n2) {
                n = arrstring[n2] != null ? 1 : 0;
                if (n == 0) throw (Throwable)new IllegalArgumentException("Headers cannot be null".toString());
                object = arrstring[n2];
                if (object == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                arrstring[n2] = ((Object)StringsKt.trim((CharSequence)object)).toString();
            }
            object = RangesKt.step(RangesKt.until(0, arrstring.length), 2);
            n2 = ((IntProgression)object).getFirst();
            n3 = ((IntProgression)object).getLast();
            n = ((IntProgression)object).getStep();
            if (n >= 0) {
                if (n2 > n3) return new Headers(arrstring, null);
            } else if (n2 < n3) return new Headers(arrstring, null);
            do {
                String string2 = arrstring[n2];
                String string3 = arrstring[n2 + 1];
                object = this;
                Companion.super.checkName(string2);
                Companion.super.checkValue(string3, string2);
                if (n2 == n3) return new Headers(arrstring, null);
                n2 += n;
            } while (true);
        }
    }

}


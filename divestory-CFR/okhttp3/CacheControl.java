/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.util.concurrent.TimeUnit;
import kotlin.Deprecated;
import kotlin.DeprecationLevel;
import kotlin.Metadata;
import kotlin.ReplaceWith;
import kotlin.TypeCastException;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Headers;
import okhttp3.internal.Util;

/*
 * Illegal identifiers - consider using --renameillegalidents true
 */
@Metadata(bv={1, 0, 3}, d1={"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\n\n\u0002\u0010\u000e\n\u0002\b\u0011\u0018\u0000 !2\u00020\u0001:\u0002 !Bq\b\u0002\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0005\u001a\u00020\u0006\u0012\u0006\u0010\u0007\u001a\u00020\u0006\u0012\u0006\u0010\b\u001a\u00020\u0003\u0012\u0006\u0010\t\u001a\u00020\u0003\u0012\u0006\u0010\n\u001a\u00020\u0003\u0012\u0006\u0010\u000b\u001a\u00020\u0006\u0012\u0006\u0010\f\u001a\u00020\u0006\u0012\u0006\u0010\r\u001a\u00020\u0003\u0012\u0006\u0010\u000e\u001a\u00020\u0003\u0012\u0006\u0010\u000f\u001a\u00020\u0003\u0012\b\u0010\u0010\u001a\u0004\u0018\u00010\u0011\u00a2\u0006\u0002\u0010\u0012J\r\u0010\u000f\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u0015J\r\u0010\u0005\u001a\u00020\u0006H\u0007\u00a2\u0006\u0002\b\u0016J\r\u0010\u000b\u001a\u00020\u0006H\u0007\u00a2\u0006\u0002\b\u0017J\r\u0010\f\u001a\u00020\u0006H\u0007\u00a2\u0006\u0002\b\u0018J\r\u0010\n\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u0019J\r\u0010\u0002\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u001aJ\r\u0010\u0004\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u001bJ\r\u0010\u000e\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u001cJ\r\u0010\r\u001a\u00020\u0003H\u0007\u00a2\u0006\u0002\b\u001dJ\r\u0010\u0007\u001a\u00020\u0006H\u0007\u00a2\u0006\u0002\b\u001eJ\b\u0010\u001f\u001a\u00020\u0011H\u0016R\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0011X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0013\u0010\u000f\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000f\u0010\u0013R\u0011\u0010\b\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\u0013R\u0011\u0010\t\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\t\u0010\u0013R\u0013\u0010\u0005\u001a\u00020\u00068\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0005\u0010\u0014R\u0013\u0010\u000b\u001a\u00020\u00068\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\u0014R\u0013\u0010\f\u001a\u00020\u00068\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\f\u0010\u0014R\u0013\u0010\n\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u0013R\u0013\u0010\u0002\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0002\u0010\u0013R\u0013\u0010\u0004\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0004\u0010\u0013R\u0013\u0010\u000e\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u0013R\u0013\u0010\r\u001a\u00020\u00038\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u0013R\u0013\u0010\u0007\u001a\u00020\u00068\u0007\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0007\u0010\u0014\u00a8\u0006\""}, d2={"Lokhttp3/CacheControl;", "", "noCache", "", "noStore", "maxAgeSeconds", "", "sMaxAgeSeconds", "isPrivate", "isPublic", "mustRevalidate", "maxStaleSeconds", "minFreshSeconds", "onlyIfCached", "noTransform", "immutable", "headerValue", "", "(ZZIIZZZIIZZZLjava/lang/String;)V", "()Z", "()I", "-deprecated_immutable", "-deprecated_maxAgeSeconds", "-deprecated_maxStaleSeconds", "-deprecated_minFreshSeconds", "-deprecated_mustRevalidate", "-deprecated_noCache", "-deprecated_noStore", "-deprecated_noTransform", "-deprecated_onlyIfCached", "-deprecated_sMaxAgeSeconds", "toString", "Builder", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class CacheControl {
    public static final Companion Companion = new Companion(null);
    public static final CacheControl FORCE_CACHE;
    public static final CacheControl FORCE_NETWORK;
    private String headerValue;
    private final boolean immutable;
    private final boolean isPrivate;
    private final boolean isPublic;
    private final int maxAgeSeconds;
    private final int maxStaleSeconds;
    private final int minFreshSeconds;
    private final boolean mustRevalidate;
    private final boolean noCache;
    private final boolean noStore;
    private final boolean noTransform;
    private final boolean onlyIfCached;
    private final int sMaxAgeSeconds;

    static {
        FORCE_NETWORK = new Builder().noCache().build();
        FORCE_CACHE = new Builder().onlyIfCached().maxStale(Integer.MAX_VALUE, TimeUnit.SECONDS).build();
    }

    private CacheControl(boolean bl, boolean bl2, int n, int n2, boolean bl3, boolean bl4, boolean bl5, int n3, int n4, boolean bl6, boolean bl7, boolean bl8, String string2) {
        this.noCache = bl;
        this.noStore = bl2;
        this.maxAgeSeconds = n;
        this.sMaxAgeSeconds = n2;
        this.isPrivate = bl3;
        this.isPublic = bl4;
        this.mustRevalidate = bl5;
        this.maxStaleSeconds = n3;
        this.minFreshSeconds = n4;
        this.onlyIfCached = bl6;
        this.noTransform = bl7;
        this.immutable = bl8;
        this.headerValue = string2;
    }

    public /* synthetic */ CacheControl(boolean bl, boolean bl2, int n, int n2, boolean bl3, boolean bl4, boolean bl5, int n3, int n4, boolean bl6, boolean bl7, boolean bl8, String string2, DefaultConstructorMarker defaultConstructorMarker) {
        this(bl, bl2, n, n2, bl3, bl4, bl5, n3, n4, bl6, bl7, bl8, string2);
    }

    @JvmStatic
    public static final CacheControl parse(Headers headers) {
        return Companion.parse(headers);
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="immutable", imports={}))
    public final boolean -deprecated_immutable() {
        return this.immutable;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="maxAgeSeconds", imports={}))
    public final int -deprecated_maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="maxStaleSeconds", imports={}))
    public final int -deprecated_maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="minFreshSeconds", imports={}))
    public final int -deprecated_minFreshSeconds() {
        return this.minFreshSeconds;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="mustRevalidate", imports={}))
    public final boolean -deprecated_mustRevalidate() {
        return this.mustRevalidate;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="noCache", imports={}))
    public final boolean -deprecated_noCache() {
        return this.noCache;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="noStore", imports={}))
    public final boolean -deprecated_noStore() {
        return this.noStore;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="noTransform", imports={}))
    public final boolean -deprecated_noTransform() {
        return this.noTransform;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="onlyIfCached", imports={}))
    public final boolean -deprecated_onlyIfCached() {
        return this.onlyIfCached;
    }

    @Deprecated(level=DeprecationLevel.ERROR, message="moved to val", replaceWith=@ReplaceWith(expression="sMaxAgeSeconds", imports={}))
    public final int -deprecated_sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    public final boolean immutable() {
        return this.immutable;
    }

    public final boolean isPrivate() {
        return this.isPrivate;
    }

    public final boolean isPublic() {
        return this.isPublic;
    }

    public final int maxAgeSeconds() {
        return this.maxAgeSeconds;
    }

    public final int maxStaleSeconds() {
        return this.maxStaleSeconds;
    }

    public final int minFreshSeconds() {
        return this.minFreshSeconds;
    }

    public final boolean mustRevalidate() {
        return this.mustRevalidate;
    }

    public final boolean noCache() {
        return this.noCache;
    }

    public final boolean noStore() {
        return this.noStore;
    }

    public final boolean noTransform() {
        return this.noTransform;
    }

    public final boolean onlyIfCached() {
        return this.onlyIfCached;
    }

    public final int sMaxAgeSeconds() {
        return this.sMaxAgeSeconds;
    }

    public String toString() {
        String string2 = this.headerValue;
        CharSequence charSequence = string2;
        if (string2 != null) return charSequence;
        charSequence = new StringBuilder();
        if (this.noCache) {
            ((StringBuilder)charSequence).append("no-cache, ");
        }
        if (this.noStore) {
            ((StringBuilder)charSequence).append("no-store, ");
        }
        if (this.maxAgeSeconds != -1) {
            ((StringBuilder)charSequence).append("max-age=");
            ((StringBuilder)charSequence).append(this.maxAgeSeconds);
            ((StringBuilder)charSequence).append(", ");
        }
        if (this.sMaxAgeSeconds != -1) {
            ((StringBuilder)charSequence).append("s-maxage=");
            ((StringBuilder)charSequence).append(this.sMaxAgeSeconds);
            ((StringBuilder)charSequence).append(", ");
        }
        if (this.isPrivate) {
            ((StringBuilder)charSequence).append("private, ");
        }
        if (this.isPublic) {
            ((StringBuilder)charSequence).append("public, ");
        }
        if (this.mustRevalidate) {
            ((StringBuilder)charSequence).append("must-revalidate, ");
        }
        if (this.maxStaleSeconds != -1) {
            ((StringBuilder)charSequence).append("max-stale=");
            ((StringBuilder)charSequence).append(this.maxStaleSeconds);
            ((StringBuilder)charSequence).append(", ");
        }
        if (this.minFreshSeconds != -1) {
            ((StringBuilder)charSequence).append("min-fresh=");
            ((StringBuilder)charSequence).append(this.minFreshSeconds);
            ((StringBuilder)charSequence).append(", ");
        }
        if (this.onlyIfCached) {
            ((StringBuilder)charSequence).append("only-if-cached, ");
        }
        if (this.noTransform) {
            ((StringBuilder)charSequence).append("no-transform, ");
        }
        if (this.immutable) {
            ((StringBuilder)charSequence).append("immutable, ");
        }
        if (charSequence.length() == 0) {
            return "";
        }
        boolean bl = false;
        if (bl) {
            return "";
        }
        ((StringBuilder)charSequence).delete(((StringBuilder)charSequence).length() - 2, ((StringBuilder)charSequence).length());
        charSequence = ((StringBuilder)charSequence).toString();
        Intrinsics.checkExpressionValueIsNotNull(charSequence, "StringBuilder().apply(builderAction).toString()");
        this.headerValue = charSequence;
        return charSequence;
    }

    @Metadata(bv={1, 0, 3}, d1={"\u00000\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\t\n\u0000\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u0006\u0010\r\u001a\u00020\u000eJ\u0006\u0010\u0003\u001a\u00020\u0000J\u0016\u0010\u000f\u001a\u00020\u00002\u0006\u0010\u000f\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0012\u001a\u00020\u00002\u0006\u0010\u0012\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u0016\u0010\u0013\u001a\u00020\u00002\u0006\u0010\u0013\u001a\u00020\u00062\u0006\u0010\u0010\u001a\u00020\u0011J\u0006\u0010\t\u001a\u00020\u0000J\u0006\u0010\n\u001a\u00020\u0000J\u0006\u0010\u000b\u001a\u00020\u0000J\u0006\u0010\f\u001a\u00020\u0000J\f\u0010\u0014\u001a\u00020\u0006*\u00020\u0015H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\n\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u000b\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\f\u001a\u00020\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2={"Lokhttp3/CacheControl$Builder;", "", "()V", "immutable", "", "maxAgeSeconds", "", "maxStaleSeconds", "minFreshSeconds", "noCache", "noStore", "noTransform", "onlyIfCached", "build", "Lokhttp3/CacheControl;", "maxAge", "timeUnit", "Ljava/util/concurrent/TimeUnit;", "maxStale", "minFresh", "clampToInt", "", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Builder {
        private boolean immutable;
        private int maxAgeSeconds = -1;
        private int maxStaleSeconds = -1;
        private int minFreshSeconds = -1;
        private boolean noCache;
        private boolean noStore;
        private boolean noTransform;
        private boolean onlyIfCached;

        private final int clampToInt(long l) {
            int n = Integer.MAX_VALUE;
            if (l <= (long)Integer.MAX_VALUE) return (int)l;
            return n;
        }

        public final CacheControl build() {
            return new CacheControl(this.noCache, this.noStore, this.maxAgeSeconds, -1, false, false, false, this.maxStaleSeconds, this.minFreshSeconds, this.onlyIfCached, this.noTransform, this.immutable, null, null);
        }

        public final Builder immutable() {
            Builder builder = this;
            builder.immutable = true;
            return builder;
        }

        public final Builder maxAge(int n, TimeUnit object) {
            Intrinsics.checkParameterIsNotNull(object, "timeUnit");
            Builder builder = this;
            boolean bl = n >= 0;
            if (bl) {
                builder.maxAgeSeconds = builder.clampToInt(((TimeUnit)((Object)object)).toSeconds(n));
                return builder;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("maxAge < 0: ");
            ((StringBuilder)object).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }

        public final Builder maxStale(int n, TimeUnit object) {
            Intrinsics.checkParameterIsNotNull(object, "timeUnit");
            Builder builder = this;
            boolean bl = n >= 0;
            if (bl) {
                builder.maxStaleSeconds = builder.clampToInt(((TimeUnit)((Object)object)).toSeconds(n));
                return builder;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("maxStale < 0: ");
            ((StringBuilder)object).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }

        public final Builder minFresh(int n, TimeUnit object) {
            Intrinsics.checkParameterIsNotNull(object, "timeUnit");
            Builder builder = this;
            boolean bl = n >= 0;
            if (bl) {
                builder.minFreshSeconds = builder.clampToInt(((TimeUnit)((Object)object)).toSeconds(n));
                return builder;
            }
            object = new StringBuilder();
            ((StringBuilder)object).append("minFresh < 0: ");
            ((StringBuilder)object).append(n);
            throw (Throwable)new IllegalArgumentException(((StringBuilder)object).toString().toString());
        }

        public final Builder noCache() {
            Builder builder = this;
            builder.noCache = true;
            return builder;
        }

        public final Builder noStore() {
            Builder builder = this;
            builder.noStore = true;
            return builder;
        }

        public final Builder noTransform() {
            Builder builder = this;
            builder.noTransform = true;
            return builder;
        }

        public final Builder onlyIfCached() {
            Builder builder = this;
            builder.onlyIfCached = true;
            return builder;
        }
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0010\u0010\u0006\u001a\u00020\u00042\u0006\u0010\u0007\u001a\u00020\bH\u0007J\u001e\u0010\t\u001a\u00020\n*\u00020\u000b2\u0006\u0010\f\u001a\u00020\u000b2\b\b\u0002\u0010\r\u001a\u00020\nH\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2={"Lokhttp3/CacheControl$Companion;", "", "()V", "FORCE_CACHE", "Lokhttp3/CacheControl;", "FORCE_NETWORK", "parse", "headers", "Lokhttp3/Headers;", "indexOfElement", "", "", "characters", "startIndex", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private final int indexOfElement(String string2, String string3, int n) {
            int n2 = string2.length();
            while (n < n2) {
                if (StringsKt.contains$default((CharSequence)string3, string2.charAt(n), false, 2, null)) {
                    return n;
                }
                ++n;
            }
            return string2.length();
        }

        static /* synthetic */ int indexOfElement$default(Companion companion, String string2, String string3, int n, int n2, Object object) {
            if ((n2 & 2) == 0) return companion.indexOfElement(string2, string3, n);
            n = 0;
            return companion.indexOfElement(string2, string3, n);
        }

        /*
         * Unable to fully structure code
         */
        @JvmStatic
        public final CacheControl parse(Headers var1_1) {
            Intrinsics.checkParameterIsNotNull(var1_1, "headers");
            var2_2 = null;
            var3_3 = var1_1.size();
            var4_4 = true;
            var5_5 = var2_2;
            var6_6 = 0;
            var7_7 = true;
            var8_8 = false;
            var9_9 = false;
            var10_10 = -1;
            var11_11 = -1;
            var12_12 = false;
            var13_13 = false;
            var14_14 = false;
            var15_15 = -1;
            var16_16 = -1;
            var17_17 = false;
            var18_18 = false;
            var19_19 = false;
            do {
                block26 : {
                    block27 : {
                        block22 : {
                            block25 : {
                                block24 : {
                                    block23 : {
                                        var20_20 = var1_1;
                                        if (var6_6 >= var3_3) break block22;
                                        var21_21 = var20_20.name(var6_6);
                                        var22_22 = var20_20.value(var6_6);
                                        if (!StringsKt.equals(var21_21, "Cache-Control", var4_4)) break block23;
                                        if (var5_5 != null) break block24;
                                        var5_5 = var22_22;
                                        break block25;
                                    }
                                    if (!StringsKt.equals(var21_21, "Pragma", var4_4)) break block26;
                                }
                                var7_7 = false;
                            }
                            var23_23 = 0;
                            var24_24 = var3_3;
                            var3_3 = var23_23;
                            break block27;
                        }
                        if (!var7_7) {
                            var1_1 = var2_2;
                            return new CacheControl(var8_8, var9_9, var10_10, var11_11, var12_12, var13_13, var14_14, var15_15, var16_16, var17_17, var18_18, var19_19, (String)var1_1, null);
                        }
                        var1_1 = var5_5;
                        return new CacheControl(var8_8, var9_9, var10_10, var11_11, var12_12, var13_13, var14_14, var15_15, var16_16, var17_17, var18_18, var19_19, (String)var1_1, null);
                    }
                    while (var3_3 < var22_22.length()) {
                        block29 : {
                            block32 : {
                                block31 : {
                                    block30 : {
                                        block28 : {
                                            var20_20 = this;
                                            var23_23 = Companion.super.indexOfElement(var22_22, "=,;", var3_3);
                                            if (var22_22 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                            var21_21 = var22_22.substring(var3_3, var23_23);
                                            Intrinsics.checkExpressionValueIsNotNull(var21_21, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                                            if (var21_21 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                                            var21_21 = StringsKt.trim((CharSequence)var21_21).toString();
                                            if (var23_23 != var22_22.length() && var22_22.charAt(var23_23) != ',' && var22_22.charAt(var23_23) != ';') {
                                                if ((var23_23 = Util.indexOfNonWhitespace(var22_22, var23_23 + 1)) < var22_22.length() && var22_22.charAt(var23_23) == '\"') {
                                                    var3_3 = var23_23 + 1;
                                                    var23_23 = StringsKt.indexOf$default((CharSequence)var22_22, '\"', var3_3, false, 4, null);
                                                    if (var22_22 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                                    var20_20 = var22_22.substring(var3_3, var23_23);
                                                    Intrinsics.checkExpressionValueIsNotNull(var20_20, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                                                    var3_3 = var23_23 + 1;
                                                } else {
                                                    var3_3 = Companion.super.indexOfElement(var22_22, ",;", var23_23);
                                                    if (var22_22 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
                                                    var20_20 = var22_22.substring(var23_23, var3_3);
                                                    Intrinsics.checkExpressionValueIsNotNull(var20_20, "(this as java.lang.Strin\u2026ing(startIndex, endIndex)");
                                                    if (var20_20 == null) throw new TypeCastException("null cannot be cast to non-null type kotlin.CharSequence");
                                                    var20_20 = StringsKt.trim((CharSequence)var20_20).toString();
                                                }
                                            } else {
                                                var3_3 = var23_23 + 1;
                                                var20_20 = var2_2;
                                            }
                                            var25_25 = true;
                                            if (!StringsKt.equals("no-cache", var21_21, true)) break block28;
                                            var8_8 = true;
                                            var26_26 = var9_9;
                                            var23_23 = var10_10;
                                            var27_27 = var11_11;
                                            var28_28 = var12_12;
                                            var29_29 = var13_13;
                                            var30_30 = var14_14;
                                            var31_31 = var15_15;
                                            var32_32 = var16_16;
                                            var33_33 = var17_17;
                                            var34_34 = var18_18;
                                            break block29;
                                        }
                                        if (!StringsKt.equals("no-store", var21_21, true)) break block30;
                                        var26_26 = true;
                                        var23_23 = var10_10;
                                        var27_27 = var11_11;
                                        var28_28 = var12_12;
                                        var29_29 = var13_13;
                                        var30_30 = var14_14;
                                        var31_31 = var15_15;
                                        var32_32 = var16_16;
                                        var33_33 = var17_17;
                                        var34_34 = var18_18;
                                        break block29;
                                    }
                                    if (!StringsKt.equals("max-age", var21_21, true)) break block31;
                                    var10_10 = Util.toNonNegativeInt((String)var20_20, -1);
                                    ** GOTO lbl157
                                }
                                if (!StringsKt.equals("s-maxage", var21_21, true)) break block32;
                                var11_11 = Util.toNonNegativeInt((String)var20_20, -1);
                                ** GOTO lbl157
                            }
                            if (StringsKt.equals("private", var21_21, true)) {
                                var28_28 = true;
                                var26_26 = var9_9;
                                var23_23 = var10_10;
                                var27_27 = var11_11;
                                var29_29 = var13_13;
                                var30_30 = var14_14;
                                var31_31 = var15_15;
                                var32_32 = var16_16;
                                var33_33 = var17_17;
                                var34_34 = var18_18;
                            } else if (StringsKt.equals("public", var21_21, true)) {
                                var29_29 = true;
                                var26_26 = var9_9;
                                var23_23 = var10_10;
                                var27_27 = var11_11;
                                var28_28 = var12_12;
                                var30_30 = var14_14;
                                var31_31 = var15_15;
                                var32_32 = var16_16;
                                var33_33 = var17_17;
                                var34_34 = var18_18;
                            } else if (StringsKt.equals("must-revalidate", var21_21, true)) {
                                var30_30 = true;
                                var26_26 = var9_9;
                                var23_23 = var10_10;
                                var27_27 = var11_11;
                                var28_28 = var12_12;
                                var29_29 = var13_13;
                                var31_31 = var15_15;
                                var32_32 = var16_16;
                                var33_33 = var17_17;
                                var34_34 = var18_18;
                            } else if (StringsKt.equals("max-stale", var21_21, true)) {
                                var31_31 = Util.toNonNegativeInt((String)var20_20, Integer.MAX_VALUE);
                                var26_26 = var9_9;
                                var23_23 = var10_10;
                                var27_27 = var11_11;
                                var28_28 = var12_12;
                                var29_29 = var13_13;
                                var30_30 = var14_14;
                                var32_32 = var16_16;
                                var33_33 = var17_17;
                                var34_34 = var18_18;
                            } else if (StringsKt.equals("min-fresh", var21_21, true)) {
                                var16_16 = Util.toNonNegativeInt((String)var20_20, -1);
lbl157: // 3 sources:
                                var26_26 = var9_9;
                                var23_23 = var10_10;
                                var27_27 = var11_11;
                                var28_28 = var12_12;
                                var29_29 = var13_13;
                                var30_30 = var14_14;
                                var31_31 = var15_15;
                                var32_32 = var16_16;
                                var33_33 = var17_17;
                                var34_34 = var18_18;
                            } else if (StringsKt.equals("only-if-cached", var21_21, true)) {
                                var33_33 = true;
                                var26_26 = var9_9;
                                var23_23 = var10_10;
                                var27_27 = var11_11;
                                var28_28 = var12_12;
                                var29_29 = var13_13;
                                var30_30 = var14_14;
                                var31_31 = var15_15;
                                var32_32 = var16_16;
                                var34_34 = var18_18;
                            } else if (StringsKt.equals("no-transform", var21_21, true)) {
                                var34_34 = true;
                                var26_26 = var9_9;
                                var23_23 = var10_10;
                                var27_27 = var11_11;
                                var28_28 = var12_12;
                                var29_29 = var13_13;
                                var30_30 = var14_14;
                                var31_31 = var15_15;
                                var32_32 = var16_16;
                                var33_33 = var17_17;
                            } else {
                                var35_35 = StringsKt.equals("immutable", var21_21, true);
                                var8_8 = var4_4 = var8_8;
                                var26_26 = var9_9;
                                var23_23 = var10_10;
                                var27_27 = var11_11;
                                var28_28 = var12_12;
                                var29_29 = var13_13;
                                var30_30 = var14_14;
                                var31_31 = var15_15;
                                var32_32 = var16_16;
                                var33_33 = var17_17;
                                var34_34 = var18_18;
                                if (var35_35) {
                                    var19_19 = true;
                                    var34_34 = var18_18;
                                    var33_33 = var17_17;
                                    var32_32 = var16_16;
                                    var31_31 = var15_15;
                                    var30_30 = var14_14;
                                    var29_29 = var13_13;
                                    var28_28 = var12_12;
                                    var27_27 = var11_11;
                                    var23_23 = var10_10;
                                    var26_26 = var9_9;
                                    var8_8 = var4_4;
                                }
                            }
                        }
                        var4_4 = var25_25;
                        var9_9 = var26_26;
                        var10_10 = var23_23;
                        var11_11 = var27_27;
                        var12_12 = var28_28;
                        var13_13 = var29_29;
                        var14_14 = var30_30;
                        var15_15 = var31_31;
                        var16_16 = var32_32;
                        var17_17 = var33_33;
                        var18_18 = var34_34;
                    }
                    var3_3 = var24_24;
                }
                ++var6_6;
            } while (true);
        }
    }

}


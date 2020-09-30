/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.ws;

import java.io.IOException;
import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.Headers;
import okhttp3.internal.Util;

@Metadata(bv={1, 0, 3}, d1={"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\b\n\u0002\b\u0015\n\u0002\u0010\u000e\n\u0002\b\u0002\b\u0086\b\u0018\u0000 \u001c2\u00020\u0001:\u0001\u001cBE\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\u0006\u001a\u00020\u0003\u0012\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u0005\u0012\b\b\u0002\u0010\b\u001a\u00020\u0003\u0012\b\b\u0002\u0010\t\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\nJ\t\u0010\f\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\r\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u000f\u001a\u00020\u0003H\u00c6\u0003J\u0010\u0010\u0010\u001a\u0004\u0018\u00010\u0005H\u00c6\u0003\u00a2\u0006\u0002\u0010\u000eJ\t\u0010\u0011\u001a\u00020\u0003H\u00c6\u0003J\t\u0010\u0012\u001a\u00020\u0003H\u00c6\u0003JN\u0010\u0013\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\n\b\u0002\u0010\u0004\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\u0006\u001a\u00020\u00032\n\b\u0002\u0010\u0007\u001a\u0004\u0018\u00010\u00052\b\b\u0002\u0010\b\u001a\u00020\u00032\b\b\u0002\u0010\t\u001a\u00020\u0003H\u00c6\u0001\u00a2\u0006\u0002\u0010\u0014J\u0013\u0010\u0015\u001a\u00020\u00032\b\u0010\u0016\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u0010\u0017\u001a\u00020\u0005H\u00d6\u0001J\u000e\u0010\u0018\u001a\u00020\u00032\u0006\u0010\u0019\u001a\u00020\u0003J\t\u0010\u001a\u001a\u00020\u001bH\u00d6\u0001R\u0014\u0010\u0004\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000bR\u0010\u0010\u0006\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0002\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0007\u001a\u0004\u0018\u00010\u00058\u0006X\u0087\u0004\u00a2\u0006\u0004\n\u0002\u0010\u000bR\u0010\u0010\b\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u0010\u0010\t\u001a\u00020\u00038\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001d"}, d2={"Lokhttp3/internal/ws/WebSocketExtensions;", "", "perMessageDeflate", "", "clientMaxWindowBits", "", "clientNoContextTakeover", "serverMaxWindowBits", "serverNoContextTakeover", "unknownValues", "(ZLjava/lang/Integer;ZLjava/lang/Integer;ZZ)V", "Ljava/lang/Integer;", "component1", "component2", "()Ljava/lang/Integer;", "component3", "component4", "component5", "component6", "copy", "(ZLjava/lang/Integer;ZLjava/lang/Integer;ZZ)Lokhttp3/internal/ws/WebSocketExtensions;", "equals", "other", "hashCode", "noContextTakeover", "clientOriginated", "toString", "", "Companion", "okhttp"}, k=1, mv={1, 1, 16})
public final class WebSocketExtensions {
    public static final Companion Companion = new Companion(null);
    private static final String HEADER_WEB_SOCKET_EXTENSION = "Sec-WebSocket-Extensions";
    public final Integer clientMaxWindowBits;
    public final boolean clientNoContextTakeover;
    public final boolean perMessageDeflate;
    public final Integer serverMaxWindowBits;
    public final boolean serverNoContextTakeover;
    public final boolean unknownValues;

    public WebSocketExtensions() {
        this(false, null, false, null, false, false, 63, null);
    }

    public WebSocketExtensions(boolean bl, Integer n, boolean bl2, Integer n2, boolean bl3, boolean bl4) {
        this.perMessageDeflate = bl;
        this.clientMaxWindowBits = n;
        this.clientNoContextTakeover = bl2;
        this.serverMaxWindowBits = n2;
        this.serverNoContextTakeover = bl3;
        this.unknownValues = bl4;
    }

    public /* synthetic */ WebSocketExtensions(boolean bl, Integer n, boolean bl2, Integer n2, boolean bl3, boolean bl4, int n3, DefaultConstructorMarker defaultConstructorMarker) {
        if ((n3 & 1) != 0) {
            bl = false;
        }
        if ((n3 & 2) != 0) {
            n = null;
        }
        if ((n3 & 4) != 0) {
            bl2 = false;
        }
        if ((n3 & 8) != 0) {
            n2 = null;
        }
        if ((n3 & 16) != 0) {
            bl3 = false;
        }
        if ((n3 & 32) != 0) {
            bl4 = false;
        }
        this(bl, n, bl2, n2, bl3, bl4);
    }

    public static /* synthetic */ WebSocketExtensions copy$default(WebSocketExtensions webSocketExtensions, boolean bl, Integer n, boolean bl2, Integer n2, boolean bl3, boolean bl4, int n3, Object object) {
        if ((n3 & 1) != 0) {
            bl = webSocketExtensions.perMessageDeflate;
        }
        if ((n3 & 2) != 0) {
            n = webSocketExtensions.clientMaxWindowBits;
        }
        if ((n3 & 4) != 0) {
            bl2 = webSocketExtensions.clientNoContextTakeover;
        }
        if ((n3 & 8) != 0) {
            n2 = webSocketExtensions.serverMaxWindowBits;
        }
        if ((n3 & 16) != 0) {
            bl3 = webSocketExtensions.serverNoContextTakeover;
        }
        if ((n3 & 32) == 0) return webSocketExtensions.copy(bl, n, bl2, n2, bl3, bl4);
        bl4 = webSocketExtensions.unknownValues;
        return webSocketExtensions.copy(bl, n, bl2, n2, bl3, bl4);
    }

    public final boolean component1() {
        return this.perMessageDeflate;
    }

    public final Integer component2() {
        return this.clientMaxWindowBits;
    }

    public final boolean component3() {
        return this.clientNoContextTakeover;
    }

    public final Integer component4() {
        return this.serverMaxWindowBits;
    }

    public final boolean component5() {
        return this.serverNoContextTakeover;
    }

    public final boolean component6() {
        return this.unknownValues;
    }

    public final WebSocketExtensions copy(boolean bl, Integer n, boolean bl2, Integer n2, boolean bl3, boolean bl4) {
        return new WebSocketExtensions(bl, n, bl2, n2, bl3, bl4);
    }

    public boolean equals(Object object) {
        if (this == object) return true;
        if (!(object instanceof WebSocketExtensions)) return false;
        object = (WebSocketExtensions)object;
        if (this.perMessageDeflate != ((WebSocketExtensions)object).perMessageDeflate) return false;
        if (!Intrinsics.areEqual(this.clientMaxWindowBits, ((WebSocketExtensions)object).clientMaxWindowBits)) return false;
        if (this.clientNoContextTakeover != ((WebSocketExtensions)object).clientNoContextTakeover) return false;
        if (!Intrinsics.areEqual(this.serverMaxWindowBits, ((WebSocketExtensions)object).serverMaxWindowBits)) return false;
        if (this.serverNoContextTakeover != ((WebSocketExtensions)object).serverNoContextTakeover) return false;
        if (this.unknownValues != ((WebSocketExtensions)object).unknownValues) return false;
        return true;
    }

    public int hashCode() {
        int n;
        int n2;
        int n3 = this.perMessageDeflate;
        int n4 = 1;
        int n5 = n3;
        if (n3 != 0) {
            n5 = 1;
        }
        Integer n6 = this.clientMaxWindowBits;
        int n7 = 0;
        n3 = n6 != null ? ((Object)n6).hashCode() : 0;
        int n8 = n = this.clientNoContextTakeover;
        if (n != 0) {
            n8 = 1;
        }
        if ((n6 = this.serverMaxWindowBits) != null) {
            n7 = ((Object)n6).hashCode();
        }
        n = n2 = this.serverNoContextTakeover;
        if (n2 != 0) {
            n = 1;
        }
        if ((n2 = this.unknownValues) != 0) {
            return ((((n5 * 31 + n3) * 31 + n8) * 31 + n7) * 31 + n) * 31 + n4;
        }
        n4 = n2;
        return ((((n5 * 31 + n3) * 31 + n8) * 31 + n7) * 31 + n) * 31 + n4;
    }

    public final boolean noContextTakeover(boolean bl) {
        if (!bl) return this.serverNoContextTakeover;
        return this.clientNoContextTakeover;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("WebSocketExtensions(perMessageDeflate=");
        stringBuilder.append(this.perMessageDeflate);
        stringBuilder.append(", clientMaxWindowBits=");
        stringBuilder.append(this.clientMaxWindowBits);
        stringBuilder.append(", clientNoContextTakeover=");
        stringBuilder.append(this.clientNoContextTakeover);
        stringBuilder.append(", serverMaxWindowBits=");
        stringBuilder.append(this.serverMaxWindowBits);
        stringBuilder.append(", serverNoContextTakeover=");
        stringBuilder.append(this.serverNoContextTakeover);
        stringBuilder.append(", unknownValues=");
        stringBuilder.append(this.unknownValues);
        stringBuilder.append(")");
        return stringBuilder.toString();
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u001e\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2={"Lokhttp3/internal/ws/WebSocketExtensions$Companion;", "", "()V", "HEADER_WEB_SOCKET_EXTENSION", "", "parse", "Lokhttp3/internal/ws/WebSocketExtensions;", "responseHeaders", "Lokhttp3/Headers;", "okhttp"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        /*
         * Unable to fully structure code
         */
        public final WebSocketExtensions parse(Headers var1_1) throws IOException {
            Intrinsics.checkParameterIsNotNull(var1_1, "responseHeaders");
            var2_2 = null;
            var3_3 = var1_1.size();
            var4_4 = var2_2;
            var5_5 = 0;
            var6_6 = false;
            var7_7 = false;
            var8_8 = false;
            var9_9 = false;
            block0 : do {
                block12 : {
                    if (var5_5 >= var3_3) return new WebSocketExtensions(var6_6, (Integer)var2_2, var7_7, (Integer)var4_4, var8_8, var9_9);
                    if (StringsKt.equals(var1_1.name(var5_5), "Sec-WebSocket-Extensions", true)) break block12;
                    var10_10 = var6_6;
                    var11_11 = var2_2;
                    var12_12 = var7_7;
                    var13_13 = var4_4;
                    var14_14 = var8_8;
                    var15_15 = var9_9;
                    ** GOTO lbl55
                }
                var16_16 = var1_1.value(var5_5);
                var17_17 = 0;
                do {
                    block16 : {
                        block15 : {
                            block13 : {
                                block14 : {
                                    var10_10 = var6_6;
                                    var11_11 = var2_2;
                                    var12_12 = var7_7;
                                    var13_13 = var4_4;
                                    var14_14 = var8_8;
                                    var15_15 = var9_9;
                                    if (var17_17 >= var16_16.length()) break block13;
                                    var18_18 = Util.delimiterOffset$default(var16_16, ',', var17_17, 0, 4, null);
                                    var19_19 = Util.delimiterOffset(var16_16, ';', var17_17, var18_18);
                                    var11_11 = Util.trimSubstring(var16_16, var17_17, var19_19);
                                    ++var19_19;
                                    if (!StringsKt.equals((String)var11_11, "permessage-deflate", true)) break block14;
                                    var17_17 = var19_19;
                                    var13_13 = var2_2;
                                    var10_10 = var7_7;
                                    var11_11 = var4_4;
                                    var12_12 = var8_8;
                                    if (var6_6) {
                                        var9_9 = true;
                                        var12_12 = var8_8;
                                        var11_11 = var4_4;
                                        var10_10 = var7_7;
                                        var13_13 = var2_2;
                                        var17_17 = var19_19;
                                    }
                                    break block15;
                                }
                                var17_17 = var19_19;
                                var9_9 = true;
                                var11_11 = var4_4;
                                var13_13 = var2_2;
                                break block16;
                            }
                            ++var5_5;
                            var6_6 = var10_10;
                            var2_2 = var11_11;
                            var7_7 = var12_12;
                            var4_4 = var13_13;
                            var8_8 = var14_14;
                            var9_9 = var15_15;
                            continue block0;
                        }
                        while (var17_17 < var18_18) {
                            block18 : {
                                block20 : {
                                    block19 : {
                                        block17 : {
                                            var19_19 = Util.delimiterOffset(var16_16, ';', var17_17, var18_18);
                                            var20_20 = Util.delimiterOffset(var16_16, '=', var17_17, var19_19);
                                            var21_21 = Util.trimSubstring(var16_16, var17_17, var20_20);
                                            var22_22 = var20_20 < var19_19 ? StringsKt.removeSurrounding(Util.trimSubstring(var16_16, var20_20 + 1, var19_19), (CharSequence)"\"") : null;
                                            if (!StringsKt.equals(var21_21, "client_max_window_bits", true)) break block17;
                                            if (var13_13 != null) {
                                                var9_9 = true;
                                            }
                                            var13_13 = var22_22 != null ? StringsKt.toIntOrNull((String)var22_22) : null;
                                            var4_4 = var22_22 = var13_13;
                                            var7_7 = var10_10;
                                            var2_2 = var11_11;
                                            var8_8 = var12_12;
                                            if (var13_13 != null) break block18;
                                            var4_4 = var22_22;
                                            var2_2 = var11_11;
                                            ** GOTO lbl-1000
                                        }
                                        if (!StringsKt.equals(var21_21, "client_no_context_takeover", true)) break block19;
                                        if (var10_10) {
                                            var9_9 = true;
                                        }
                                        if (var22_22 != null) {
                                            var9_9 = true;
                                        }
                                        var7_7 = true;
                                        var4_4 = var13_13;
                                        var2_2 = var11_11;
                                        var8_8 = var12_12;
                                        break block18;
                                    }
                                    if (!StringsKt.equals(var21_21, "server_max_window_bits", true)) break block20;
                                    if (var11_11 != null) {
                                        var9_9 = true;
                                    }
                                    var22_22 = var22_22 != null ? StringsKt.toIntOrNull((String)var22_22) : null;
                                    var11_11 = var22_22;
                                    var4_4 = var13_13;
                                    var7_7 = var10_10;
                                    var2_2 = var11_11;
                                    var8_8 = var12_12;
                                    if (var22_22 != null) break block18;
                                    var4_4 = var13_13;
                                    var2_2 = var11_11;
                                    ** GOTO lbl-1000
                                }
                                var4_4 = var13_13;
                                var2_2 = var11_11;
                                if (StringsKt.equals(var21_21, "server_no_context_takeover", true)) {
                                    if (var12_12) {
                                        var9_9 = true;
                                    }
                                    if (var22_22 != null) {
                                        var9_9 = true;
                                    }
                                    var8_8 = true;
                                    var4_4 = var13_13;
                                    var7_7 = var10_10;
                                    var2_2 = var11_11;
                                } else lbl-1000: // 3 sources:
                                {
                                    var9_9 = true;
                                    var8_8 = var12_12;
                                    var7_7 = var10_10;
                                }
                            }
                            var17_17 = var19_19 + 1;
                            var13_13 = var4_4;
                            var10_10 = var7_7;
                            var11_11 = var2_2;
                            var12_12 = var8_8;
                        }
                        var6_6 = true;
                        var7_7 = var10_10;
                        var8_8 = var12_12;
                    }
                    var2_2 = var13_13;
                    var4_4 = var11_11;
                } while (true);
                break;
            } while (true);
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import kotlin.Metadata;
import kotlin.jvm.JvmStatic;
import kotlin.jvm.internal.Intrinsics;
import okio.ByteString;

@Metadata(bv={1, 0, 3}, d1={"\u0000\u001a\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0006\u001a\u00020\u00042\b\b\u0002\u0010\u0007\u001a\u00020\bH\u0007\u00a8\u0006\t"}, d2={"Lokhttp3/Credentials;", "", "()V", "basic", "", "username", "password", "charset", "Ljava/nio/charset/Charset;", "okhttp"}, k=1, mv={1, 1, 16})
public final class Credentials {
    public static final Credentials INSTANCE = new Credentials();

    private Credentials() {
    }

    @JvmStatic
    public static final String basic(String string2, String string3) {
        return Credentials.basic$default(string2, string3, null, 4, null);
    }

    @JvmStatic
    public static final String basic(String string2, String charSequence, Charset charset) {
        Intrinsics.checkParameterIsNotNull(string2, "username");
        Intrinsics.checkParameterIsNotNull(charSequence, "password");
        Intrinsics.checkParameterIsNotNull(charset, "charset");
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(string2);
        stringBuilder.append(':');
        stringBuilder.append((String)charSequence);
        string2 = stringBuilder.toString();
        string2 = ByteString.Companion.encodeString(string2, charset).base64();
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append("Basic ");
        ((StringBuilder)charSequence).append(string2);
        return ((StringBuilder)charSequence).toString();
    }

    public static /* synthetic */ String basic$default(String string2, String string3, Charset charset, int n, Object object) {
        if ((n & 4) == 0) return Credentials.basic(string2, string3, charset);
        charset = StandardCharsets.ISO_8859_1;
        Intrinsics.checkExpressionValueIsNotNull(charset, "ISO_8859_1");
        return Credentials.basic(string2, string3, charset);
    }
}


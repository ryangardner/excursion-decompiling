/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.tls;

import java.security.cert.Certificate;
import java.security.cert.CertificateParsingException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLException;
import javax.net.ssl.SSLSession;
import kotlin.Metadata;
import kotlin.TypeCastException;
import kotlin.collections.CollectionsKt;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt;
import okhttp3.internal.HostnamesKt;
import okhttp3.internal.Util;

@Metadata(bv={1, 0, 3}, d1={"\u00006\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u000b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0014\u0010\u0006\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\nJ\u001e\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\b0\u00072\u0006\u0010\t\u001a\u00020\n2\u0006\u0010\f\u001a\u00020\u0004H\u0002J\u0016\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u0018\u0010\r\u001a\u00020\u000e2\u0006\u0010\u000f\u001a\u00020\b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0016J\u0018\u0010\u0012\u001a\u00020\u000e2\u0006\u0010\u0013\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002J\u001c\u0010\u0012\u001a\u00020\u000e2\b\u0010\u0013\u001a\u0004\u0018\u00010\b2\b\u0010\u0014\u001a\u0004\u0018\u00010\bH\u0002J\u0018\u0010\u0015\u001a\u00020\u000e2\u0006\u0010\u0016\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0017"}, d2={"Lokhttp3/internal/tls/OkHostnameVerifier;", "Ljavax/net/ssl/HostnameVerifier;", "()V", "ALT_DNS_NAME", "", "ALT_IPA_NAME", "allSubjectAltNames", "", "", "certificate", "Ljava/security/cert/X509Certificate;", "getSubjectAltNames", "type", "verify", "", "host", "session", "Ljavax/net/ssl/SSLSession;", "verifyHostname", "hostname", "pattern", "verifyIpAddress", "ipAddress", "okhttp"}, k=1, mv={1, 1, 16})
public final class OkHostnameVerifier
implements HostnameVerifier {
    private static final int ALT_DNS_NAME = 2;
    private static final int ALT_IPA_NAME = 7;
    public static final OkHostnameVerifier INSTANCE = new OkHostnameVerifier();

    private OkHostnameVerifier() {
    }

    private final List<String> getSubjectAltNames(X509Certificate object, int n) {
        try {
            Object object2 = ((X509Certificate)object).getSubjectAlternativeNames();
            if (object2 == null) {
                return CollectionsKt.emptyList();
            }
            object = new ArrayList();
            object = (List)object;
            object2 = object2.iterator();
            while (object2.hasNext()) {
                List list = (List)object2.next();
                if (list == null || list.size() < 2 || Intrinsics.areEqual(list.get(0), (Object)n) ^ true || (list = list.get(1)) == null) continue;
                if (list == null) {
                    object = new TypeCastException("null cannot be cast to non-null type kotlin.String");
                    throw object;
                }
                object.add((String)((String)((Object)list)));
            }
            return object;
        }
        catch (CertificateParsingException certificateParsingException) {
            return CollectionsKt.emptyList();
        }
    }

    private final boolean verifyHostname(String charSequence, String object) {
        CharSequence charSequence2 = charSequence;
        charSequence = charSequence2;
        int n = charSequence != null && charSequence.length() != 0 ? 0 : 1;
        if (n != 0) return false;
        if (StringsKt.startsWith$default((String)charSequence2, ".", false, 2, null)) return false;
        if (StringsKt.endsWith$default((String)charSequence2, "..", false, 2, null)) {
            return false;
        }
        charSequence = (CharSequence)object;
        n = charSequence != null && charSequence.length() != 0 ? 0 : 1;
        if (n != 0) return false;
        if (StringsKt.startsWith$default((String)object, ".", false, 2, null)) return false;
        if (StringsKt.endsWith$default((String)object, "..", false, 2, null)) {
            return false;
        }
        charSequence = charSequence2;
        if (!StringsKt.endsWith$default((String)charSequence2, ".", false, 2, null)) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append((String)charSequence2);
            ((StringBuilder)charSequence).append(".");
            charSequence = ((StringBuilder)charSequence).toString();
        }
        charSequence2 = object;
        if (!StringsKt.endsWith$default((String)object, ".", false, 2, null)) {
            charSequence2 = new StringBuilder();
            ((StringBuilder)charSequence2).append((String)object);
            ((StringBuilder)charSequence2).append(".");
            charSequence2 = ((StringBuilder)charSequence2).toString();
        }
        object = Locale.US;
        Intrinsics.checkExpressionValueIsNotNull(object, "Locale.US");
        if (charSequence2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        object = ((String)charSequence2).toLowerCase((Locale)object);
        Intrinsics.checkExpressionValueIsNotNull(object, "(this as java.lang.String).toLowerCase(locale)");
        charSequence2 = (CharSequence)object;
        if (!StringsKt.contains$default(charSequence2, "*", false, 2, null)) {
            return Intrinsics.areEqual(charSequence, object);
        }
        if (!StringsKt.startsWith$default((String)object, "*.", false, 2, null)) return false;
        if (StringsKt.indexOf$default(charSequence2, '*', 1, false, 4, null) != -1) {
            return false;
        }
        if (((String)charSequence).length() < ((String)object).length()) {
            return false;
        }
        if (Intrinsics.areEqual("*.", object)) {
            return false;
        }
        if (object == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        object = ((String)object).substring(1);
        Intrinsics.checkExpressionValueIsNotNull(object, "(this as java.lang.String).substring(startIndex)");
        if (!StringsKt.endsWith$default((String)charSequence, (String)object, false, 2, null)) {
            return false;
        }
        n = ((String)charSequence).length() - ((String)object).length();
        if (n <= 0) return true;
        if (StringsKt.lastIndexOf$default(charSequence, '.', n - 1, false, 4, null) == -1) return true;
        return false;
    }

    private final boolean verifyHostname(String string2, X509Certificate iterator2) {
        Object object = Locale.US;
        Intrinsics.checkExpressionValueIsNotNull(object, "Locale.US");
        if (string2 == null) throw new TypeCastException("null cannot be cast to non-null type java.lang.String");
        string2 = string2.toLowerCase((Locale)object);
        Intrinsics.checkExpressionValueIsNotNull(string2, "(this as java.lang.String).toLowerCase(locale)");
        iterator2 = this.getSubjectAltNames((X509Certificate)((Object)iterator2), 2);
        boolean bl = iterator2 instanceof Collection;
        boolean bl2 = false;
        if (bl && ((Collection)((Object)iterator2)).isEmpty()) {
            return bl2;
        }
        iterator2 = iterator2.iterator();
        do {
            bl = bl2;
            if (!iterator2.hasNext()) return bl;
        } while (!INSTANCE.verifyHostname(string2, (String)(object = (String)iterator2.next())));
        return true;
    }

    private final boolean verifyIpAddress(String string2, X509Certificate iterator2) {
        string2 = HostnamesKt.toCanonicalHost(string2);
        iterator2 = this.getSubjectAltNames((X509Certificate)((Object)iterator2), 7);
        boolean bl = iterator2 instanceof Collection;
        boolean bl2 = false;
        if (bl && ((Collection)((Object)iterator2)).isEmpty()) {
            return bl2;
        }
        iterator2 = iterator2.iterator();
        do {
            bl = bl2;
            if (!iterator2.hasNext()) return bl;
        } while (!Intrinsics.areEqual(string2, HostnamesKt.toCanonicalHost((String)iterator2.next())));
        return true;
    }

    public final List<String> allSubjectAltNames(X509Certificate object) {
        Intrinsics.checkParameterIsNotNull(object, "certificate");
        List<String> list = this.getSubjectAltNames((X509Certificate)object, 7);
        object = this.getSubjectAltNames((X509Certificate)object, 2);
        return CollectionsKt.plus((Collection)list, (Iterable)object);
    }

    public final boolean verify(String string2, X509Certificate x509Certificate) {
        Intrinsics.checkParameterIsNotNull(string2, "host");
        Intrinsics.checkParameterIsNotNull(x509Certificate, "certificate");
        if (!Util.canParseAsIpAddress(string2)) return this.verifyHostname(string2, x509Certificate);
        return this.verifyIpAddress(string2, x509Certificate);
    }

    @Override
    public boolean verify(String object, SSLSession object2) {
        Intrinsics.checkParameterIsNotNull(object, "host");
        Intrinsics.checkParameterIsNotNull(object2, "session");
        boolean bl = false;
        try {
            object2 = object2.getPeerCertificates()[0];
            if (object2 != null) {
                boolean bl2 = this.verify((String)object, (X509Certificate)object2);
                return bl2;
            }
            object = new TypeCastException("null cannot be cast to non-null type java.security.cert.X509Certificate");
            throw object;
        }
        catch (SSLException sSLException) {
            return bl;
        }
    }
}


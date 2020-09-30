/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.util.Base64
 */
package androidx.core.provider;

import android.util.Base64;
import androidx.core.util.Preconditions;
import java.util.List;

public final class FontRequest {
    private final List<List<byte[]>> mCertificates;
    private final int mCertificatesArray;
    private final String mIdentifier;
    private final String mProviderAuthority;
    private final String mProviderPackage;
    private final String mQuery;

    public FontRequest(String charSequence, String string2, String string3, int n) {
        this.mProviderAuthority = Preconditions.checkNotNull(charSequence);
        this.mProviderPackage = Preconditions.checkNotNull(string2);
        this.mQuery = Preconditions.checkNotNull(string3);
        this.mCertificates = null;
        boolean bl = n != 0;
        Preconditions.checkArgument(bl);
        this.mCertificatesArray = n;
        charSequence = new StringBuilder(this.mProviderAuthority);
        ((StringBuilder)charSequence).append("-");
        ((StringBuilder)charSequence).append(this.mProviderPackage);
        ((StringBuilder)charSequence).append("-");
        ((StringBuilder)charSequence).append(this.mQuery);
        this.mIdentifier = ((StringBuilder)charSequence).toString();
    }

    public FontRequest(String charSequence, String string2, String string3, List<List<byte[]>> list) {
        this.mProviderAuthority = Preconditions.checkNotNull(charSequence);
        this.mProviderPackage = Preconditions.checkNotNull(string2);
        this.mQuery = Preconditions.checkNotNull(string3);
        this.mCertificates = Preconditions.checkNotNull(list);
        this.mCertificatesArray = 0;
        charSequence = new StringBuilder(this.mProviderAuthority);
        ((StringBuilder)charSequence).append("-");
        ((StringBuilder)charSequence).append(this.mProviderPackage);
        ((StringBuilder)charSequence).append("-");
        ((StringBuilder)charSequence).append(this.mQuery);
        this.mIdentifier = ((StringBuilder)charSequence).toString();
    }

    public List<List<byte[]>> getCertificates() {
        return this.mCertificates;
    }

    public int getCertificatesArrayResId() {
        return this.mCertificatesArray;
    }

    public String getIdentifier() {
        return this.mIdentifier;
    }

    public String getProviderAuthority() {
        return this.mProviderAuthority;
    }

    public String getProviderPackage() {
        return this.mProviderPackage;
    }

    public String getQuery() {
        return this.mQuery;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        Object object = new StringBuilder();
        ((StringBuilder)object).append("FontRequest {mProviderAuthority: ");
        ((StringBuilder)object).append(this.mProviderAuthority);
        ((StringBuilder)object).append(", mProviderPackage: ");
        ((StringBuilder)object).append(this.mProviderPackage);
        ((StringBuilder)object).append(", mQuery: ");
        ((StringBuilder)object).append(this.mQuery);
        ((StringBuilder)object).append(", mCertificates:");
        stringBuilder.append(((StringBuilder)object).toString());
        int n = 0;
        do {
            if (n >= this.mCertificates.size()) {
                stringBuilder.append("}");
                object = new StringBuilder();
                ((StringBuilder)object).append("mCertificatesArray: ");
                ((StringBuilder)object).append(this.mCertificatesArray);
                stringBuilder.append(((StringBuilder)object).toString());
                return stringBuilder.toString();
            }
            stringBuilder.append(" [");
            object = this.mCertificates.get(n);
            for (int i = 0; i < object.size(); ++i) {
                stringBuilder.append(" \"");
                stringBuilder.append(Base64.encodeToString((byte[])((byte[])object.get(i)), (int)0));
                stringBuilder.append("\"");
            }
            stringBuilder.append(" ]");
            ++n;
        } while (true);
    }
}


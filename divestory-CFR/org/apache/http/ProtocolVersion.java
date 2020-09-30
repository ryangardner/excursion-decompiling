/*
 * Decompiled with CFR <Could not determine version>.
 */
package org.apache.http;

import java.io.Serializable;
import org.apache.http.util.CharArrayBuffer;

public class ProtocolVersion
implements Serializable,
Cloneable {
    private static final long serialVersionUID = 8950662842175091068L;
    protected final int major;
    protected final int minor;
    protected final String protocol;

    public ProtocolVersion(String string2, int n, int n2) {
        if (string2 == null) throw new IllegalArgumentException("Protocol name must not be null.");
        if (n < 0) throw new IllegalArgumentException("Protocol major version number must not be negative.");
        if (n2 < 0) throw new IllegalArgumentException("Protocol minor version number may not be negative");
        this.protocol = string2;
        this.major = n;
        this.minor = n2;
    }

    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public int compareToVersion(ProtocolVersion protocolVersion) {
        if (protocolVersion == null) throw new IllegalArgumentException("Protocol version must not be null.");
        if (this.protocol.equals(protocolVersion.protocol)) {
            int n;
            int n2 = n = this.getMajor() - protocolVersion.getMajor();
            if (n != 0) return n2;
            return this.getMinor() - protocolVersion.getMinor();
        }
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("Versions for different protocols cannot be compared. ");
        stringBuffer.append(this);
        stringBuffer.append(" ");
        stringBuffer.append(protocolVersion);
        throw new IllegalArgumentException(stringBuffer.toString());
    }

    public final boolean equals(Object object) {
        boolean bl = true;
        if (this == object) {
            return true;
        }
        if (!(object instanceof ProtocolVersion)) {
            return false;
        }
        object = (ProtocolVersion)object;
        if (!this.protocol.equals(((ProtocolVersion)object).protocol)) return false;
        if (this.major != ((ProtocolVersion)object).major) return false;
        if (this.minor != ((ProtocolVersion)object).minor) return false;
        return bl;
    }

    public ProtocolVersion forVersion(int n, int n2) {
        if (n != this.major) return new ProtocolVersion(this.protocol, n, n2);
        if (n2 != this.minor) return new ProtocolVersion(this.protocol, n, n2);
        return this;
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    public final String getProtocol() {
        return this.protocol;
    }

    public final boolean greaterEquals(ProtocolVersion protocolVersion) {
        if (!this.isComparable(protocolVersion)) return false;
        if (this.compareToVersion(protocolVersion) < 0) return false;
        return true;
    }

    public final int hashCode() {
        return this.protocol.hashCode() ^ this.major * 100000 ^ this.minor;
    }

    public boolean isComparable(ProtocolVersion protocolVersion) {
        if (protocolVersion == null) return false;
        if (!this.protocol.equals(protocolVersion.protocol)) return false;
        return true;
    }

    public final boolean lessEquals(ProtocolVersion protocolVersion) {
        if (!this.isComparable(protocolVersion)) return false;
        if (this.compareToVersion(protocolVersion) > 0) return false;
        return true;
    }

    public String toString() {
        CharArrayBuffer charArrayBuffer = new CharArrayBuffer(16);
        charArrayBuffer.append(this.protocol);
        charArrayBuffer.append('/');
        charArrayBuffer.append(Integer.toString(this.major));
        charArrayBuffer.append('.');
        charArrayBuffer.append(Integer.toString(this.minor));
        return charArrayBuffer.toString();
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.fasterxml.jackson.core;

import java.io.Serializable;

public class Version
implements Comparable<Version>,
Serializable {
    private static final Version UNKNOWN_VERSION = new Version(0, 0, 0, null, null, null);
    private static final long serialVersionUID = 1L;
    protected final String _artifactId;
    protected final String _groupId;
    protected final int _majorVersion;
    protected final int _minorVersion;
    protected final int _patchLevel;
    protected final String _snapshotInfo;

    @Deprecated
    public Version(int n, int n2, int n3, String string2) {
        this(n, n2, n3, string2, null, null);
    }

    public Version(int n, int n2, int n3, String string2, String string3, String string4) {
        this._majorVersion = n;
        this._minorVersion = n2;
        this._patchLevel = n3;
        this._snapshotInfo = string2;
        string2 = string3;
        if (string3 == null) {
            string2 = "";
        }
        this._groupId = string2;
        string2 = string4;
        if (string4 == null) {
            string2 = "";
        }
        this._artifactId = string2;
    }

    public static Version unknownVersion() {
        return UNKNOWN_VERSION;
    }

    @Override
    public int compareTo(Version version) {
        int n;
        if (version == this) {
            return 0;
        }
        int n2 = n = this._groupId.compareTo(version._groupId);
        if (n != 0) return n2;
        n2 = n = this._artifactId.compareTo(version._artifactId);
        if (n != 0) return n2;
        n2 = n = this._majorVersion - version._majorVersion;
        if (n != 0) return n2;
        n2 = n = this._minorVersion - version._minorVersion;
        if (n != 0) return n2;
        return this._patchLevel - version._patchLevel;
    }

    public boolean equals(Object object) {
        boolean bl = true;
        if (object == this) {
            return true;
        }
        if (object == null) {
            return false;
        }
        if (object.getClass() != this.getClass()) {
            return false;
        }
        object = (Version)object;
        if (((Version)object)._majorVersion != this._majorVersion) return false;
        if (((Version)object)._minorVersion != this._minorVersion) return false;
        if (((Version)object)._patchLevel != this._patchLevel) return false;
        if (!((Version)object)._artifactId.equals(this._artifactId)) return false;
        if (!((Version)object)._groupId.equals(this._groupId)) return false;
        return bl;
    }

    public String getArtifactId() {
        return this._artifactId;
    }

    public String getGroupId() {
        return this._groupId;
    }

    public int getMajorVersion() {
        return this._majorVersion;
    }

    public int getMinorVersion() {
        return this._minorVersion;
    }

    public int getPatchLevel() {
        return this._patchLevel;
    }

    public int hashCode() {
        return this._artifactId.hashCode() ^ this._groupId.hashCode() + this._majorVersion - this._minorVersion + this._patchLevel;
    }

    public boolean isSnapshot() {
        String string2 = this._snapshotInfo;
        if (string2 == null) return false;
        if (string2.length() <= 0) return false;
        return true;
    }

    @Deprecated
    public boolean isUknownVersion() {
        return this.isUnknownVersion();
    }

    public boolean isUnknownVersion() {
        if (this != UNKNOWN_VERSION) return false;
        return true;
    }

    public String toFullString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._groupId);
        stringBuilder.append('/');
        stringBuilder.append(this._artifactId);
        stringBuilder.append('/');
        stringBuilder.append(this.toString());
        return stringBuilder.toString();
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this._majorVersion);
        stringBuilder.append('.');
        stringBuilder.append(this._minorVersion);
        stringBuilder.append('.');
        stringBuilder.append(this._patchLevel);
        if (!this.isSnapshot()) return stringBuilder.toString();
        stringBuilder.append('-');
        stringBuilder.append(this._snapshotInfo);
        return stringBuilder.toString();
    }
}


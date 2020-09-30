/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin;

import kotlin.Metadata;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

@Metadata(bv={1, 0, 3}, d1={"\u0000&\n\u0002\u0018\u0002\n\u0002\u0010\u000f\n\u0000\n\u0002\u0010\b\n\u0002\b\f\n\u0002\u0010\u000b\n\u0002\u0010\u0000\n\u0002\b\u0003\n\u0002\u0010\u000e\n\u0002\b\u0003\b\u0007\u0018\u0000 \u00172\b\u0012\u0004\u0012\u00020\u00000\u0001:\u0001\u0017B\u0017\b\u0016\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0005B\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0003\u0012\u0006\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0007J\u0011\u0010\r\u001a\u00020\u00032\u0006\u0010\u000e\u001a\u00020\u0000H\u0096\u0002J\u0013\u0010\u000f\u001a\u00020\u00102\b\u0010\u000e\u001a\u0004\u0018\u00010\u0011H\u0096\u0002J\b\u0010\u0012\u001a\u00020\u0003H\u0016J\u0016\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u0003J\u001e\u0010\u0013\u001a\u00020\u00102\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003J\b\u0010\u0014\u001a\u00020\u0015H\u0016J \u0010\u0016\u001a\u00020\u00032\u0006\u0010\u0002\u001a\u00020\u00032\u0006\u0010\u0004\u001a\u00020\u00032\u0006\u0010\u0006\u001a\u00020\u0003H\u0002R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\b\u0010\tR\u0011\u0010\u0004\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\tR\u0011\u0010\u0006\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000b\u0010\tR\u000e\u0010\f\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0018"}, d2={"Lkotlin/KotlinVersion;", "", "major", "", "minor", "(II)V", "patch", "(III)V", "getMajor", "()I", "getMinor", "getPatch", "version", "compareTo", "other", "equals", "", "", "hashCode", "isAtLeast", "toString", "", "versionOf", "Companion", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class KotlinVersion
implements Comparable<KotlinVersion> {
    public static final KotlinVersion CURRENT;
    public static final Companion Companion;
    public static final int MAX_COMPONENT_VALUE = 255;
    private final int major;
    private final int minor;
    private final int patch;
    private final int version;

    static {
        Companion = new Companion(null);
        CURRENT = new KotlinVersion(1, 3, 72);
    }

    public KotlinVersion(int n, int n2) {
        this(n, n2, 0);
    }

    public KotlinVersion(int n, int n2, int n3) {
        this.major = n;
        this.minor = n2;
        this.patch = n3;
        this.version = this.versionOf(n, n2, n3);
    }

    private final int versionOf(int n, int n2, int n3) {
        boolean bl = n >= 0 && 255 >= n && n2 >= 0 && 255 >= n2 && n3 >= 0 && 255 >= n3;
        if (bl) {
            return (n << 16) + (n2 << 8) + n3;
        }
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Version components are out of range: ");
        stringBuilder.append(n);
        stringBuilder.append('.');
        stringBuilder.append(n2);
        stringBuilder.append('.');
        stringBuilder.append(n3);
        throw (Throwable)new IllegalArgumentException(stringBuilder.toString().toString());
    }

    @Override
    public int compareTo(KotlinVersion kotlinVersion) {
        Intrinsics.checkParameterIsNotNull(kotlinVersion, "other");
        return this.version - kotlinVersion.version;
    }

    public boolean equals(Object object) {
        Object object2 = this;
        boolean bl = true;
        if (object2 == object) {
            return true;
        }
        object2 = object;
        if (!(object instanceof KotlinVersion)) {
            object2 = null;
        }
        if ((object = (KotlinVersion)object2) == null) return false;
        if (this.version != ((KotlinVersion)object).version) return false;
        return bl;
    }

    public final int getMajor() {
        return this.major;
    }

    public final int getMinor() {
        return this.minor;
    }

    public final int getPatch() {
        return this.patch;
    }

    public int hashCode() {
        return this.version;
    }

    public final boolean isAtLeast(int n, int n2) {
        int n3 = this.major;
        if (n3 > n) return true;
        if (n3 != n) return false;
        if (this.minor < n2) return false;
        return true;
    }

    public final boolean isAtLeast(int n, int n2, int n3) {
        int n4 = this.major;
        if (n4 > n) return true;
        if (n4 != n) return false;
        n = this.minor;
        if (n > n2) return true;
        if (n != n2) return false;
        if (this.patch < n3) return false;
        return true;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.major);
        stringBuilder.append('.');
        stringBuilder.append(this.minor);
        stringBuilder.append('.');
        stringBuilder.append(this.patch);
        return stringBuilder.toString();
    }

    @Metadata(bv={1, 0, 3}, d1={"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u0010\u0010\u0003\u001a\u00020\u00048\u0006X\u0087\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0007"}, d2={"Lkotlin/KotlinVersion$Companion;", "", "()V", "CURRENT", "Lkotlin/KotlinVersion;", "MAX_COMPONENT_VALUE", "", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
    public static final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.io;

import kotlin.Metadata;

@Metadata(bv={1, 0, 3}, d1={"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0004\b\u0086\u0001\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004\u00a8\u0006\u0005"}, d2={"Lkotlin/io/FileWalkDirection;", "", "(Ljava/lang/String;I)V", "TOP_DOWN", "BOTTOM_UP", "kotlin-stdlib"}, k=1, mv={1, 1, 16})
public final class FileWalkDirection
extends Enum<FileWalkDirection> {
    private static final /* synthetic */ FileWalkDirection[] $VALUES;
    public static final /* enum */ FileWalkDirection BOTTOM_UP;
    public static final /* enum */ FileWalkDirection TOP_DOWN;

    static {
        FileWalkDirection fileWalkDirection;
        FileWalkDirection fileWalkDirection2;
        TOP_DOWN = fileWalkDirection = new FileWalkDirection();
        BOTTOM_UP = fileWalkDirection2 = new FileWalkDirection();
        $VALUES = new FileWalkDirection[]{fileWalkDirection, fileWalkDirection2};
    }

    public static FileWalkDirection valueOf(String string2) {
        return Enum.valueOf(FileWalkDirection.class, string2);
    }

    public static FileWalkDirection[] values() {
        return (FileWalkDirection[])$VALUES.clone();
    }
}


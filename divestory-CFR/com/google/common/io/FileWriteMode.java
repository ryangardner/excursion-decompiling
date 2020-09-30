/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.io;

public final class FileWriteMode
extends Enum<FileWriteMode> {
    private static final /* synthetic */ FileWriteMode[] $VALUES;
    public static final /* enum */ FileWriteMode APPEND;

    static {
        FileWriteMode fileWriteMode;
        APPEND = fileWriteMode = new FileWriteMode();
        $VALUES = new FileWriteMode[]{fileWriteMode};
    }

    public static FileWriteMode valueOf(String string2) {
        return Enum.valueOf(FileWriteMode.class, string2);
    }

    public static FileWriteMode[] values() {
        return (FileWriteMode[])$VALUES.clone();
    }
}


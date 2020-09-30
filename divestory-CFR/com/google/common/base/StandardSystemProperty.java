/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.common.base;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class StandardSystemProperty
extends Enum<StandardSystemProperty> {
    private static final /* synthetic */ StandardSystemProperty[] $VALUES;
    public static final /* enum */ StandardSystemProperty FILE_SEPARATOR;
    public static final /* enum */ StandardSystemProperty JAVA_CLASS_PATH;
    public static final /* enum */ StandardSystemProperty JAVA_CLASS_VERSION;
    public static final /* enum */ StandardSystemProperty JAVA_COMPILER;
    public static final /* enum */ StandardSystemProperty JAVA_EXT_DIRS;
    public static final /* enum */ StandardSystemProperty JAVA_HOME;
    public static final /* enum */ StandardSystemProperty JAVA_IO_TMPDIR;
    public static final /* enum */ StandardSystemProperty JAVA_LIBRARY_PATH;
    public static final /* enum */ StandardSystemProperty JAVA_SPECIFICATION_NAME;
    public static final /* enum */ StandardSystemProperty JAVA_SPECIFICATION_VENDOR;
    public static final /* enum */ StandardSystemProperty JAVA_SPECIFICATION_VERSION;
    public static final /* enum */ StandardSystemProperty JAVA_VENDOR;
    public static final /* enum */ StandardSystemProperty JAVA_VENDOR_URL;
    public static final /* enum */ StandardSystemProperty JAVA_VERSION;
    public static final /* enum */ StandardSystemProperty JAVA_VM_NAME;
    public static final /* enum */ StandardSystemProperty JAVA_VM_SPECIFICATION_NAME;
    public static final /* enum */ StandardSystemProperty JAVA_VM_SPECIFICATION_VENDOR;
    public static final /* enum */ StandardSystemProperty JAVA_VM_SPECIFICATION_VERSION;
    public static final /* enum */ StandardSystemProperty JAVA_VM_VENDOR;
    public static final /* enum */ StandardSystemProperty JAVA_VM_VERSION;
    public static final /* enum */ StandardSystemProperty LINE_SEPARATOR;
    public static final /* enum */ StandardSystemProperty OS_ARCH;
    public static final /* enum */ StandardSystemProperty OS_NAME;
    public static final /* enum */ StandardSystemProperty OS_VERSION;
    public static final /* enum */ StandardSystemProperty PATH_SEPARATOR;
    public static final /* enum */ StandardSystemProperty USER_DIR;
    public static final /* enum */ StandardSystemProperty USER_HOME;
    public static final /* enum */ StandardSystemProperty USER_NAME;
    private final String key;

    static {
        StandardSystemProperty standardSystemProperty;
        JAVA_VERSION = new StandardSystemProperty("java.version");
        JAVA_VENDOR = new StandardSystemProperty("java.vendor");
        JAVA_VENDOR_URL = new StandardSystemProperty("java.vendor.url");
        JAVA_HOME = new StandardSystemProperty("java.home");
        JAVA_VM_SPECIFICATION_VERSION = new StandardSystemProperty("java.vm.specification.version");
        JAVA_VM_SPECIFICATION_VENDOR = new StandardSystemProperty("java.vm.specification.vendor");
        JAVA_VM_SPECIFICATION_NAME = new StandardSystemProperty("java.vm.specification.name");
        JAVA_VM_VERSION = new StandardSystemProperty("java.vm.version");
        JAVA_VM_VENDOR = new StandardSystemProperty("java.vm.vendor");
        JAVA_VM_NAME = new StandardSystemProperty("java.vm.name");
        JAVA_SPECIFICATION_VERSION = new StandardSystemProperty("java.specification.version");
        JAVA_SPECIFICATION_VENDOR = new StandardSystemProperty("java.specification.vendor");
        JAVA_SPECIFICATION_NAME = new StandardSystemProperty("java.specification.name");
        JAVA_CLASS_VERSION = new StandardSystemProperty("java.class.version");
        JAVA_CLASS_PATH = new StandardSystemProperty("java.class.path");
        JAVA_LIBRARY_PATH = new StandardSystemProperty("java.library.path");
        JAVA_IO_TMPDIR = new StandardSystemProperty("java.io.tmpdir");
        JAVA_COMPILER = new StandardSystemProperty("java.compiler");
        JAVA_EXT_DIRS = new StandardSystemProperty("java.ext.dirs");
        OS_NAME = new StandardSystemProperty("os.name");
        OS_ARCH = new StandardSystemProperty("os.arch");
        OS_VERSION = new StandardSystemProperty("os.version");
        FILE_SEPARATOR = new StandardSystemProperty("file.separator");
        PATH_SEPARATOR = new StandardSystemProperty("path.separator");
        LINE_SEPARATOR = new StandardSystemProperty("line.separator");
        USER_NAME = new StandardSystemProperty("user.name");
        USER_HOME = new StandardSystemProperty("user.home");
        USER_DIR = standardSystemProperty = new StandardSystemProperty("user.dir");
        $VALUES = new StandardSystemProperty[]{JAVA_VERSION, JAVA_VENDOR, JAVA_VENDOR_URL, JAVA_HOME, JAVA_VM_SPECIFICATION_VERSION, JAVA_VM_SPECIFICATION_VENDOR, JAVA_VM_SPECIFICATION_NAME, JAVA_VM_VERSION, JAVA_VM_VENDOR, JAVA_VM_NAME, JAVA_SPECIFICATION_VERSION, JAVA_SPECIFICATION_VENDOR, JAVA_SPECIFICATION_NAME, JAVA_CLASS_VERSION, JAVA_CLASS_PATH, JAVA_LIBRARY_PATH, JAVA_IO_TMPDIR, JAVA_COMPILER, JAVA_EXT_DIRS, OS_NAME, OS_ARCH, OS_VERSION, FILE_SEPARATOR, PATH_SEPARATOR, LINE_SEPARATOR, USER_NAME, USER_HOME, standardSystemProperty};
    }

    private StandardSystemProperty(String string3) {
        this.key = string3;
    }

    public static StandardSystemProperty valueOf(String string2) {
        return Enum.valueOf(StandardSystemProperty.class, string2);
    }

    public static StandardSystemProperty[] values() {
        return (StandardSystemProperty[])$VALUES.clone();
    }

    public String key() {
        return this.key;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.key());
        stringBuilder.append("=");
        stringBuilder.append(this.value());
        return stringBuilder.toString();
    }

    @NullableDecl
    public String value() {
        return System.getProperty(this.key);
    }
}


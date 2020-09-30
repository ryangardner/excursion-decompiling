package com.google.common.base;

import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public enum StandardSystemProperty {
   FILE_SEPARATOR("file.separator"),
   JAVA_CLASS_PATH("java.class.path"),
   JAVA_CLASS_VERSION("java.class.version"),
   JAVA_COMPILER("java.compiler"),
   JAVA_EXT_DIRS("java.ext.dirs"),
   JAVA_HOME("java.home"),
   JAVA_IO_TMPDIR("java.io.tmpdir"),
   JAVA_LIBRARY_PATH("java.library.path"),
   JAVA_SPECIFICATION_NAME("java.specification.name"),
   JAVA_SPECIFICATION_VENDOR("java.specification.vendor"),
   JAVA_SPECIFICATION_VERSION("java.specification.version"),
   JAVA_VENDOR("java.vendor"),
   JAVA_VENDOR_URL("java.vendor.url"),
   JAVA_VERSION("java.version"),
   JAVA_VM_NAME("java.vm.name"),
   JAVA_VM_SPECIFICATION_NAME("java.vm.specification.name"),
   JAVA_VM_SPECIFICATION_VENDOR("java.vm.specification.vendor"),
   JAVA_VM_SPECIFICATION_VERSION("java.vm.specification.version"),
   JAVA_VM_VENDOR("java.vm.vendor"),
   JAVA_VM_VERSION("java.vm.version"),
   LINE_SEPARATOR("line.separator"),
   OS_ARCH("os.arch"),
   OS_NAME("os.name"),
   OS_VERSION("os.version"),
   PATH_SEPARATOR("path.separator"),
   USER_DIR,
   USER_HOME("user.home"),
   USER_NAME("user.name");

   private final String key;

   static {
      StandardSystemProperty var0 = new StandardSystemProperty("USER_DIR", 27, "user.dir");
      USER_DIR = var0;
   }

   private StandardSystemProperty(String var3) {
      this.key = var3;
   }

   public String key() {
      return this.key;
   }

   public String toString() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.key());
      var1.append("=");
      var1.append(this.value());
      return var1.toString();
   }

   @NullableDecl
   public String value() {
      return System.getProperty(this.key);
   }
}

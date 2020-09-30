package com.google.common.io;

import com.google.common.base.Preconditions;
import java.io.File;
import java.io.FilenameFilter;
import java.util.regex.Pattern;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;

public final class PatternFilenameFilter implements FilenameFilter {
   private final Pattern pattern;

   public PatternFilenameFilter(String var1) {
      this(Pattern.compile(var1));
   }

   public PatternFilenameFilter(Pattern var1) {
      this.pattern = (Pattern)Preconditions.checkNotNull(var1);
   }

   public boolean accept(@NullableDecl File var1, String var2) {
      return this.pattern.matcher(var2).matches();
   }
}

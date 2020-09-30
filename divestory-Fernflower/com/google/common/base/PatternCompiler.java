package com.google.common.base;

interface PatternCompiler {
   CommonPattern compile(String var1);

   boolean isPcreLike();
}

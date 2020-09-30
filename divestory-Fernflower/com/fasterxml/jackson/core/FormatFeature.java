package com.fasterxml.jackson.core;

public interface FormatFeature {
   boolean enabledByDefault();

   boolean enabledIn(int var1);

   int getMask();
}

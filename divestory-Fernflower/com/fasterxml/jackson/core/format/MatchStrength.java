package com.fasterxml.jackson.core.format;

public enum MatchStrength {
   FULL_MATCH,
   INCONCLUSIVE,
   NO_MATCH,
   SOLID_MATCH,
   WEAK_MATCH;

   static {
      MatchStrength var0 = new MatchStrength("FULL_MATCH", 4);
      FULL_MATCH = var0;
   }
}

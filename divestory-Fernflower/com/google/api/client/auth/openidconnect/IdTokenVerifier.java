package com.google.api.client.auth.openidconnect;

import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import java.util.Collection;
import java.util.Collections;

public class IdTokenVerifier {
   public static final long DEFAULT_TIME_SKEW_SECONDS = 300L;
   private final long acceptableTimeSkewSeconds;
   private final Collection<String> audience;
   private final Clock clock;
   private final Collection<String> issuers;

   public IdTokenVerifier() {
      this(new IdTokenVerifier.Builder());
   }

   protected IdTokenVerifier(IdTokenVerifier.Builder var1) {
      this.clock = var1.clock;
      this.acceptableTimeSkewSeconds = var1.acceptableTimeSkewSeconds;
      Collection var2 = var1.issuers;
      Object var3 = null;
      if (var2 == null) {
         var2 = null;
      } else {
         var2 = Collections.unmodifiableCollection(var1.issuers);
      }

      this.issuers = var2;
      Collection var4;
      if (var1.audience == null) {
         var4 = (Collection)var3;
      } else {
         var4 = Collections.unmodifiableCollection(var1.audience);
      }

      this.audience = var4;
   }

   public final long getAcceptableTimeSkewSeconds() {
      return this.acceptableTimeSkewSeconds;
   }

   public final Collection<String> getAudience() {
      return this.audience;
   }

   public final Clock getClock() {
      return this.clock;
   }

   public final String getIssuer() {
      Collection var1 = this.issuers;
      return var1 == null ? null : (String)var1.iterator().next();
   }

   public final Collection<String> getIssuers() {
      return this.issuers;
   }

   public boolean verify(IdToken var1) {
      Collection var2 = this.issuers;
      boolean var3;
      if (var2 == null || var1.verifyIssuer(var2)) {
         var2 = this.audience;
         if ((var2 == null || var1.verifyAudience(var2)) && var1.verifyTime(this.clock.currentTimeMillis(), this.acceptableTimeSkewSeconds)) {
            var3 = true;
            return var3;
         }
      }

      var3 = false;
      return var3;
   }

   public static class Builder {
      long acceptableTimeSkewSeconds;
      Collection<String> audience;
      Clock clock;
      Collection<String> issuers;

      public Builder() {
         this.clock = Clock.SYSTEM;
         this.acceptableTimeSkewSeconds = 300L;
      }

      public IdTokenVerifier build() {
         return new IdTokenVerifier(this);
      }

      public final long getAcceptableTimeSkewSeconds() {
         return this.acceptableTimeSkewSeconds;
      }

      public final Collection<String> getAudience() {
         return this.audience;
      }

      public final Clock getClock() {
         return this.clock;
      }

      public final String getIssuer() {
         Collection var1 = this.issuers;
         return var1 == null ? null : (String)var1.iterator().next();
      }

      public final Collection<String> getIssuers() {
         return this.issuers;
      }

      public IdTokenVerifier.Builder setAcceptableTimeSkewSeconds(long var1) {
         boolean var3;
         if (var1 >= 0L) {
            var3 = true;
         } else {
            var3 = false;
         }

         Preconditions.checkArgument(var3);
         this.acceptableTimeSkewSeconds = var1;
         return this;
      }

      public IdTokenVerifier.Builder setAudience(Collection<String> var1) {
         this.audience = var1;
         return this;
      }

      public IdTokenVerifier.Builder setClock(Clock var1) {
         this.clock = (Clock)Preconditions.checkNotNull(var1);
         return this;
      }

      public IdTokenVerifier.Builder setIssuer(String var1) {
         return var1 == null ? this.setIssuers((Collection)null) : this.setIssuers(Collections.singleton(var1));
      }

      public IdTokenVerifier.Builder setIssuers(Collection<String> var1) {
         boolean var2;
         if (var1 != null && var1.isEmpty()) {
            var2 = false;
         } else {
            var2 = true;
         }

         Preconditions.checkArgument(var2, "Issuers must not be empty");
         this.issuers = var1;
         return this;
      }
   }
}

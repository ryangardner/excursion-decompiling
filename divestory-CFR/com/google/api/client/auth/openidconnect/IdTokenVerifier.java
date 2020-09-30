/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.api.client.auth.openidconnect;

import com.google.api.client.auth.openidconnect.IdToken;
import com.google.api.client.util.Clock;
import com.google.api.client.util.Preconditions;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;

public class IdTokenVerifier {
    public static final long DEFAULT_TIME_SKEW_SECONDS = 300L;
    private final long acceptableTimeSkewSeconds;
    private final Collection<String> audience;
    private final Clock clock;
    private final Collection<String> issuers;

    public IdTokenVerifier() {
        this(new Builder());
    }

    protected IdTokenVerifier(Builder object) {
        this.clock = ((Builder)object).clock;
        this.acceptableTimeSkewSeconds = ((Builder)object).acceptableTimeSkewSeconds;
        Collection<String> collection = ((Builder)object).issuers;
        Object var3_3 = null;
        collection = collection == null ? null : Collections.unmodifiableCollection(((Builder)object).issuers);
        this.issuers = collection;
        object = ((Builder)object).audience == null ? var3_3 : Collections.unmodifiableCollection(((Builder)object).audience);
        this.audience = object;
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
        Collection<String> collection = this.issuers;
        if (collection != null) return collection.iterator().next();
        return null;
    }

    public final Collection<String> getIssuers() {
        return this.issuers;
    }

    public boolean verify(IdToken idToken) {
        Collection<String> collection = this.issuers;
        if (collection != null) {
            if (!idToken.verifyIssuer(collection)) return false;
        }
        if ((collection = this.audience) != null) {
            if (!idToken.verifyAudience(collection)) return false;
        }
        if (!idToken.verifyTime(this.clock.currentTimeMillis(), this.acceptableTimeSkewSeconds)) return false;
        return true;
    }

    public static class Builder {
        long acceptableTimeSkewSeconds = 300L;
        Collection<String> audience;
        Clock clock = Clock.SYSTEM;
        Collection<String> issuers;

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
            Collection<String> collection = this.issuers;
            if (collection != null) return collection.iterator().next();
            return null;
        }

        public final Collection<String> getIssuers() {
            return this.issuers;
        }

        public Builder setAcceptableTimeSkewSeconds(long l) {
            boolean bl = l >= 0L;
            Preconditions.checkArgument(bl);
            this.acceptableTimeSkewSeconds = l;
            return this;
        }

        public Builder setAudience(Collection<String> collection) {
            this.audience = collection;
            return this;
        }

        public Builder setClock(Clock clock) {
            this.clock = Preconditions.checkNotNull(clock);
            return this;
        }

        public Builder setIssuer(String string2) {
            if (string2 != null) return this.setIssuers(Collections.singleton(string2));
            return this.setIssuers(null);
        }

        public Builder setIssuers(Collection<String> collection) {
            boolean bl = collection == null || !collection.isEmpty();
            Preconditions.checkArgument(bl, "Issuers must not be empty");
            this.issuers = collection;
            return this;
        }
    }

}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package okhttp3.internal.publicsuffix;

import kotlin.Metadata;
import kotlin.jvm.internal.MutablePropertyReference0;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KDeclarationContainer;
import okhttp3.internal.publicsuffix.PublicSuffixDatabase;

@Metadata(bv={1, 0, 3}, k=3, mv={1, 1, 16})
final class PublicSuffixDatabase$findMatchingRule$1
extends MutablePropertyReference0 {
    PublicSuffixDatabase$findMatchingRule$1(PublicSuffixDatabase publicSuffixDatabase) {
        super(publicSuffixDatabase);
    }

    @Override
    public Object get() {
        return PublicSuffixDatabase.access$getPublicSuffixListBytes$p((PublicSuffixDatabase)this.receiver);
    }

    @Override
    public String getName() {
        return "publicSuffixListBytes";
    }

    @Override
    public KDeclarationContainer getOwner() {
        return Reflection.getOrCreateKotlinClass(PublicSuffixDatabase.class);
    }

    @Override
    public String getSignature() {
        return "getPublicSuffixListBytes()[B";
    }

    public void set(Object object) {
        PublicSuffixDatabase.access$setPublicSuffixListBytes$p((PublicSuffixDatabase)this.receiver, (byte[])object);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import kotlin.jvm.internal.MutablePropertyReference1;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KMutableProperty;
import kotlin.reflect.KProperty;

public class MutablePropertyReference1Impl
extends MutablePropertyReference1 {
    private final String name;
    private final KDeclarationContainer owner;
    private final String signature;

    public MutablePropertyReference1Impl(KDeclarationContainer kDeclarationContainer, String string2, String string3) {
        this.owner = kDeclarationContainer;
        this.name = string2;
        this.signature = string3;
    }

    @Override
    public Object get(Object object) {
        return this.getGetter().call(object);
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public KDeclarationContainer getOwner() {
        return this.owner;
    }

    @Override
    public String getSignature() {
        return this.signature;
    }

    public void set(Object object, Object object2) {
        this.getSetter().call(object, object2);
    }
}


/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import kotlin.jvm.internal.PropertyReference;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KCallable;
import kotlin.reflect.KProperty;
import kotlin.reflect.KProperty2;

public abstract class PropertyReference2
extends PropertyReference
implements KProperty2 {
    @Override
    protected KCallable computeReflected() {
        return Reflection.property2(this);
    }

    public Object getDelegate(Object object, Object object2) {
        return ((KProperty2)this.getReflected()).getDelegate(object, object2);
    }

    public KProperty2.Getter getGetter() {
        return ((KProperty2)this.getReflected()).getGetter();
    }

    @Override
    public Object invoke(Object object, Object object2) {
        return this.get(object, object2);
    }
}


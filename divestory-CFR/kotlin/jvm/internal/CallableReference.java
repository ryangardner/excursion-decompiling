/*
 * Decompiled with CFR <Could not determine version>.
 */
package kotlin.jvm.internal;

import java.io.ObjectStreamException;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import kotlin.jvm.KotlinReflectionNotSupportedError;
import kotlin.reflect.KCallable;
import kotlin.reflect.KDeclarationContainer;
import kotlin.reflect.KParameter;
import kotlin.reflect.KType;
import kotlin.reflect.KTypeParameter;
import kotlin.reflect.KVisibility;

public abstract class CallableReference
implements KCallable,
Serializable {
    public static final Object NO_RECEIVER = NoReceiver.access$000();
    protected final Object receiver;
    private transient KCallable reflected;

    public CallableReference() {
        this(NO_RECEIVER);
    }

    protected CallableReference(Object object) {
        this.receiver = object;
    }

    public Object call(Object ... arrobject) {
        return this.getReflected().call(arrobject);
    }

    public Object callBy(Map map) {
        return this.getReflected().callBy(map);
    }

    public KCallable compute() {
        KCallable kCallable;
        KCallable kCallable2 = kCallable = this.reflected;
        if (kCallable != null) return kCallable2;
        this.reflected = kCallable2 = this.computeReflected();
        return kCallable2;
    }

    protected abstract KCallable computeReflected();

    @Override
    public List<Annotation> getAnnotations() {
        return this.getReflected().getAnnotations();
    }

    public Object getBoundReceiver() {
        return this.receiver;
    }

    @Override
    public String getName() {
        throw new AbstractMethodError();
    }

    public KDeclarationContainer getOwner() {
        throw new AbstractMethodError();
    }

    @Override
    public List<KParameter> getParameters() {
        return this.getReflected().getParameters();
    }

    protected KCallable getReflected() {
        KCallable kCallable = this.compute();
        if (kCallable == this) throw new KotlinReflectionNotSupportedError();
        return kCallable;
    }

    @Override
    public KType getReturnType() {
        return this.getReflected().getReturnType();
    }

    public String getSignature() {
        throw new AbstractMethodError();
    }

    @Override
    public List<KTypeParameter> getTypeParameters() {
        return this.getReflected().getTypeParameters();
    }

    @Override
    public KVisibility getVisibility() {
        return this.getReflected().getVisibility();
    }

    @Override
    public boolean isAbstract() {
        return this.getReflected().isAbstract();
    }

    @Override
    public boolean isFinal() {
        return this.getReflected().isFinal();
    }

    @Override
    public boolean isOpen() {
        return this.getReflected().isOpen();
    }

    @Override
    public boolean isSuspend() {
        return this.getReflected().isSuspend();
    }

    private static class NoReceiver
    implements Serializable {
        private static final NoReceiver INSTANCE = new NoReceiver();

        private NoReceiver() {
        }

        static /* synthetic */ NoReceiver access$000() {
            return INSTANCE;
        }

        private Object readResolve() throws ObjectStreamException {
            return INSTANCE;
        }
    }

}


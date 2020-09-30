/*
 * Decompiled with CFR <Could not determine version>.
 */
package io.opencensus.trace.propagation;

import io.opencensus.trace.propagation.BinaryFormat;
import io.opencensus.trace.propagation.TextFormat;

public abstract class PropagationComponent {
    private static final PropagationComponent NOOP_PROPAGATION_COMPONENT = new NoopPropagationComponent();

    public static PropagationComponent getNoopPropagationComponent() {
        return NOOP_PROPAGATION_COMPONENT;
    }

    public abstract TextFormat getB3Format();

    public abstract BinaryFormat getBinaryFormat();

    public abstract TextFormat getTraceContextFormat();

    private static final class NoopPropagationComponent
    extends PropagationComponent {
        private NoopPropagationComponent() {
        }

        @Override
        public TextFormat getB3Format() {
            return TextFormat.getNoopTextFormat();
        }

        @Override
        public BinaryFormat getBinaryFormat() {
            return BinaryFormat.getNoopBinaryFormat();
        }

        @Override
        public TextFormat getTraceContextFormat() {
            return TextFormat.getNoopTextFormat();
        }
    }

}


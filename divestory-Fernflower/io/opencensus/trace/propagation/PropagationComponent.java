package io.opencensus.trace.propagation;

public abstract class PropagationComponent {
   private static final PropagationComponent NOOP_PROPAGATION_COMPONENT = new PropagationComponent.NoopPropagationComponent();

   public static PropagationComponent getNoopPropagationComponent() {
      return NOOP_PROPAGATION_COMPONENT;
   }

   public abstract TextFormat getB3Format();

   public abstract BinaryFormat getBinaryFormat();

   public abstract TextFormat getTraceContextFormat();

   private static final class NoopPropagationComponent extends PropagationComponent {
      private NoopPropagationComponent() {
      }

      // $FF: synthetic method
      NoopPropagationComponent(Object var1) {
         this();
      }

      public TextFormat getB3Format() {
         return TextFormat.getNoopTextFormat();
      }

      public BinaryFormat getBinaryFormat() {
         return BinaryFormat.getNoopBinaryFormat();
      }

      public TextFormat getTraceContextFormat() {
         return TextFormat.getNoopTextFormat();
      }
   }
}

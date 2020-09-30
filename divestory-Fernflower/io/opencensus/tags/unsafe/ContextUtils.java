package io.opencensus.tags.unsafe;

import io.grpc.Context;
import io.opencensus.internal.Utils;
import io.opencensus.tags.Tag;
import io.opencensus.tags.TagContext;
import java.util.Collections;
import java.util.Iterator;
import javax.annotation.Nullable;

public final class ContextUtils {
   private static final TagContext EMPTY_TAG_CONTEXT;
   private static final Context.Key<TagContext> TAG_CONTEXT_KEY;

   static {
      ContextUtils.EmptyTagContext var0 = new ContextUtils.EmptyTagContext();
      EMPTY_TAG_CONTEXT = var0;
      TAG_CONTEXT_KEY = Context.keyWithDefault("opencensus-tag-context-key", var0);
   }

   private ContextUtils() {
   }

   public static TagContext getValue(Context var0) {
      TagContext var1 = (TagContext)TAG_CONTEXT_KEY.get(var0);
      TagContext var2 = var1;
      if (var1 == null) {
         var2 = EMPTY_TAG_CONTEXT;
      }

      return var2;
   }

   public static Context withValue(Context var0, @Nullable TagContext var1) {
      return ((Context)Utils.checkNotNull(var0, "context")).withValue(TAG_CONTEXT_KEY, var1);
   }

   private static final class EmptyTagContext extends TagContext {
      private EmptyTagContext() {
      }

      // $FF: synthetic method
      EmptyTagContext(Object var1) {
         this();
      }

      protected Iterator<Tag> getIterator() {
         return Collections.emptySet().iterator();
      }
   }
}

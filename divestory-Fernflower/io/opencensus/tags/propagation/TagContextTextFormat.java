package io.opencensus.tags.propagation;

import io.opencensus.tags.TagContext;
import java.util.List;
import javax.annotation.Nullable;

public abstract class TagContextTextFormat {
   public abstract <C> TagContext extract(C var1, TagContextTextFormat.Getter<C> var2) throws TagContextDeserializationException;

   public abstract List<String> fields();

   public abstract <C> void inject(TagContext var1, C var2, TagContextTextFormat.Setter<C> var3) throws TagContextSerializationException;

   public abstract static class Getter<C> {
      @Nullable
      public abstract String get(C var1, String var2);
   }

   public abstract static class Setter<C> {
      public abstract void put(C var1, String var2, String var3);
   }
}

package io.opencensus.tags;

import io.opencensus.common.Scope;

public abstract class Tagger {
   public abstract TagContextBuilder currentBuilder();

   public abstract TagContext empty();

   public abstract TagContextBuilder emptyBuilder();

   public abstract TagContext getCurrentTagContext();

   public abstract TagContextBuilder toBuilder(TagContext var1);

   public abstract Scope withTagContext(TagContext var1);
}

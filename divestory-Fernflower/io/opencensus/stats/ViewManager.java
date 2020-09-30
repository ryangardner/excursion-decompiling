package io.opencensus.stats;

import java.util.Set;
import javax.annotation.Nullable;

public abstract class ViewManager {
   public abstract Set<View> getAllExportedViews();

   @Nullable
   public abstract ViewData getView(View.Name var1);

   public abstract void registerView(View var1);
}

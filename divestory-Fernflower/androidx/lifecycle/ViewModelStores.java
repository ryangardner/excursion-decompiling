package androidx.lifecycle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

@Deprecated
public class ViewModelStores {
   private ViewModelStores() {
   }

   @Deprecated
   public static ViewModelStore of(Fragment var0) {
      return var0.getViewModelStore();
   }

   @Deprecated
   public static ViewModelStore of(FragmentActivity var0) {
      return var0.getViewModelStore();
   }
}

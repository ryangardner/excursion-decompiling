package androidx.fragment.app;

import androidx.lifecycle.ViewModelStore;
import java.util.Collection;
import java.util.Map;

@Deprecated
public class FragmentManagerNonConfig {
   private final Map<String, FragmentManagerNonConfig> mChildNonConfigs;
   private final Collection<Fragment> mFragments;
   private final Map<String, ViewModelStore> mViewModelStores;

   FragmentManagerNonConfig(Collection<Fragment> var1, Map<String, FragmentManagerNonConfig> var2, Map<String, ViewModelStore> var3) {
      this.mFragments = var1;
      this.mChildNonConfigs = var2;
      this.mViewModelStores = var3;
   }

   Map<String, FragmentManagerNonConfig> getChildNonConfigs() {
      return this.mChildNonConfigs;
   }

   Collection<Fragment> getFragments() {
      return this.mFragments;
   }

   Map<String, ViewModelStore> getViewModelStores() {
      return this.mViewModelStores;
   }

   boolean isRetaining(Fragment var1) {
      Collection var2 = this.mFragments;
      return var2 == null ? false : var2.contains(var1);
   }
}

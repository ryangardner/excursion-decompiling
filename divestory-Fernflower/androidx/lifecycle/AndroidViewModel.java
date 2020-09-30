package androidx.lifecycle;

import android.app.Application;

public class AndroidViewModel extends ViewModel {
   private Application mApplication;

   public AndroidViewModel(Application var1) {
      this.mApplication = var1;
   }

   public <T extends Application> T getApplication() {
      return this.mApplication;
   }
}

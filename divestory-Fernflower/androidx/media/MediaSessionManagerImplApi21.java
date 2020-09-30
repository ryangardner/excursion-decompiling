package androidx.media;

import android.content.Context;

class MediaSessionManagerImplApi21 extends MediaSessionManagerImplBase {
   MediaSessionManagerImplApi21(Context var1) {
      super(var1);
      this.mContext = var1;
   }

   private boolean hasMediaControlPermission(MediaSessionManager.RemoteUserInfoImpl var1) {
      boolean var2;
      if (this.getContext().checkPermission("android.permission.MEDIA_CONTENT_CONTROL", var1.getPid(), var1.getUid()) == 0) {
         var2 = true;
      } else {
         var2 = false;
      }

      return var2;
   }

   public boolean isTrustedForMediaControl(MediaSessionManager.RemoteUserInfoImpl var1) {
      boolean var2;
      if (!this.hasMediaControlPermission(var1) && !super.isTrustedForMediaControl(var1)) {
         var2 = false;
      } else {
         var2 = true;
      }

      return var2;
   }
}

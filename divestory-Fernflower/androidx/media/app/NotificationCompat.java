package androidx.media.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.media.session.MediaSession.Token;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Build.VERSION;
import android.support.v4.media.session.MediaSessionCompat;
import android.widget.RemoteViews;
import androidx.core.app.BundleCompat;
import androidx.core.app.NotificationBuilderWithBuilderAccessor;
import androidx.media.R;

public class NotificationCompat {
   private NotificationCompat() {
   }

   public static class DecoratedMediaCustomViewStyle extends NotificationCompat.MediaStyle {
      private void setBackgroundColor(RemoteViews var1) {
         int var2;
         if (this.mBuilder.getColor() != 0) {
            var2 = this.mBuilder.getColor();
         } else {
            var2 = this.mBuilder.mContext.getResources().getColor(R.color.notification_material_background_media_default_color);
         }

         var1.setInt(R.id.status_bar_latest_event_content, "setBackgroundColor", var2);
      }

      public void apply(NotificationBuilderWithBuilderAccessor var1) {
         if (VERSION.SDK_INT >= 24) {
            var1.getBuilder().setStyle(this.fillInMediaStyle(new android.app.Notification.DecoratedMediaCustomViewStyle()));
         } else {
            super.apply(var1);
         }

      }

      int getBigContentViewLayoutResource(int var1) {
         if (var1 <= 3) {
            var1 = R.layout.notification_template_big_media_narrow_custom;
         } else {
            var1 = R.layout.notification_template_big_media_custom;
         }

         return var1;
      }

      int getContentViewLayoutResource() {
         int var1;
         if (this.mBuilder.getContentView() != null) {
            var1 = R.layout.notification_template_media_custom;
         } else {
            var1 = super.getContentViewLayoutResource();
         }

         return var1;
      }

      public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor var1) {
         if (VERSION.SDK_INT >= 24) {
            return null;
         } else {
            RemoteViews var3;
            if (this.mBuilder.getBigContentView() != null) {
               var3 = this.mBuilder.getBigContentView();
            } else {
               var3 = this.mBuilder.getContentView();
            }

            if (var3 == null) {
               return null;
            } else {
               RemoteViews var2 = this.generateBigContentView();
               this.buildIntoRemoteViews(var2, var3);
               if (VERSION.SDK_INT >= 21) {
                  this.setBackgroundColor(var2);
               }

               return var2;
            }
         }
      }

      public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor var1) {
         if (VERSION.SDK_INT >= 24) {
            return null;
         } else {
            RemoteViews var5 = this.mBuilder.getContentView();
            boolean var2 = true;
            boolean var3;
            if (var5 != null) {
               var3 = true;
            } else {
               var3 = false;
            }

            if (VERSION.SDK_INT >= 21) {
               boolean var4 = var2;
               if (!var3) {
                  if (this.mBuilder.getBigContentView() != null) {
                     var4 = var2;
                  } else {
                     var4 = false;
                  }
               }

               if (var4) {
                  var5 = this.generateContentView();
                  if (var3) {
                     this.buildIntoRemoteViews(var5, this.mBuilder.getContentView());
                  }

                  this.setBackgroundColor(var5);
                  return var5;
               }
            } else {
               var5 = this.generateContentView();
               if (var3) {
                  this.buildIntoRemoteViews(var5, this.mBuilder.getContentView());
                  return var5;
               }
            }

            return null;
         }
      }

      public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor var1) {
         if (VERSION.SDK_INT >= 24) {
            return null;
         } else {
            RemoteViews var3;
            if (this.mBuilder.getHeadsUpContentView() != null) {
               var3 = this.mBuilder.getHeadsUpContentView();
            } else {
               var3 = this.mBuilder.getContentView();
            }

            if (var3 == null) {
               return null;
            } else {
               RemoteViews var2 = this.generateBigContentView();
               this.buildIntoRemoteViews(var2, var3);
               if (VERSION.SDK_INT >= 21) {
                  this.setBackgroundColor(var2);
               }

               return var2;
            }
         }
      }
   }

   public static class MediaStyle extends androidx.core.app.NotificationCompat.Style {
      private static final int MAX_MEDIA_BUTTONS = 5;
      private static final int MAX_MEDIA_BUTTONS_IN_COMPACT = 3;
      int[] mActionsToShowInCompact = null;
      PendingIntent mCancelButtonIntent;
      boolean mShowCancelButton;
      MediaSessionCompat.Token mToken;

      public MediaStyle() {
      }

      public MediaStyle(androidx.core.app.NotificationCompat.Builder var1) {
         this.setBuilder(var1);
      }

      private RemoteViews generateMediaActionButton(androidx.core.app.NotificationCompat.Action var1) {
         boolean var2;
         if (var1.getActionIntent() == null) {
            var2 = true;
         } else {
            var2 = false;
         }

         RemoteViews var3 = new RemoteViews(this.mBuilder.mContext.getPackageName(), R.layout.notification_media_action);
         var3.setImageViewResource(R.id.action0, var1.getIcon());
         if (!var2) {
            var3.setOnClickPendingIntent(R.id.action0, var1.getActionIntent());
         }

         if (VERSION.SDK_INT >= 15) {
            var3.setContentDescription(R.id.action0, var1.getTitle());
         }

         return var3;
      }

      public static MediaSessionCompat.Token getMediaSession(Notification var0) {
         Bundle var2 = androidx.core.app.NotificationCompat.getExtras(var0);
         if (var2 != null) {
            if (VERSION.SDK_INT >= 21) {
               Parcelable var3 = var2.getParcelable("android.mediaSession");
               if (var3 != null) {
                  return MediaSessionCompat.Token.fromToken(var3);
               }
            } else {
               IBinder var1 = BundleCompat.getBinder(var2, "android.mediaSession");
               if (var1 != null) {
                  Parcel var4 = Parcel.obtain();
                  var4.writeStrongBinder(var1);
                  var4.setDataPosition(0);
                  MediaSessionCompat.Token var5 = (MediaSessionCompat.Token)MediaSessionCompat.Token.CREATOR.createFromParcel(var4);
                  var4.recycle();
                  return var5;
               }
            }
         }

         return null;
      }

      public void apply(NotificationBuilderWithBuilderAccessor var1) {
         if (VERSION.SDK_INT >= 21) {
            var1.getBuilder().setStyle(this.fillInMediaStyle(new android.app.Notification.MediaStyle()));
         } else if (this.mShowCancelButton) {
            var1.getBuilder().setOngoing(true);
         }

      }

      android.app.Notification.MediaStyle fillInMediaStyle(android.app.Notification.MediaStyle var1) {
         int[] var2 = this.mActionsToShowInCompact;
         if (var2 != null) {
            var1.setShowActionsInCompactView(var2);
         }

         MediaSessionCompat.Token var3 = this.mToken;
         if (var3 != null) {
            var1.setMediaSession((Token)var3.getToken());
         }

         return var1;
      }

      RemoteViews generateBigContentView() {
         int var1 = Math.min(this.mBuilder.mActions.size(), 5);
         RemoteViews var2 = this.applyStandardTemplate(false, this.getBigContentViewLayoutResource(var1), false);
         var2.removeAllViews(R.id.media_actions);
         if (var1 > 0) {
            for(int var3 = 0; var3 < var1; ++var3) {
               RemoteViews var4 = this.generateMediaActionButton((androidx.core.app.NotificationCompat.Action)this.mBuilder.mActions.get(var3));
               var2.addView(R.id.media_actions, var4);
            }
         }

         if (this.mShowCancelButton) {
            var2.setViewVisibility(R.id.cancel_action, 0);
            var2.setInt(R.id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(R.integer.cancel_button_image_alpha));
            var2.setOnClickPendingIntent(R.id.cancel_action, this.mCancelButtonIntent);
         } else {
            var2.setViewVisibility(R.id.cancel_action, 8);
         }

         return var2;
      }

      RemoteViews generateContentView() {
         RemoteViews var1 = this.applyStandardTemplate(false, this.getContentViewLayoutResource(), true);
         int var2 = this.mBuilder.mActions.size();
         int[] var3 = this.mActionsToShowInCompact;
         int var4;
         if (var3 == null) {
            var4 = 0;
         } else {
            var4 = Math.min(var3.length, 3);
         }

         var1.removeAllViews(R.id.media_actions);
         if (var4 > 0) {
            for(int var5 = 0; var5 < var4; ++var5) {
               if (var5 >= var2) {
                  throw new IllegalArgumentException(String.format("setShowActionsInCompactView: action %d out of bounds (max %d)", var5, var2 - 1));
               }

               RemoteViews var6 = this.generateMediaActionButton((androidx.core.app.NotificationCompat.Action)this.mBuilder.mActions.get(this.mActionsToShowInCompact[var5]));
               var1.addView(R.id.media_actions, var6);
            }
         }

         if (this.mShowCancelButton) {
            var1.setViewVisibility(R.id.end_padder, 8);
            var1.setViewVisibility(R.id.cancel_action, 0);
            var1.setOnClickPendingIntent(R.id.cancel_action, this.mCancelButtonIntent);
            var1.setInt(R.id.cancel_action, "setAlpha", this.mBuilder.mContext.getResources().getInteger(R.integer.cancel_button_image_alpha));
         } else {
            var1.setViewVisibility(R.id.end_padder, 0);
            var1.setViewVisibility(R.id.cancel_action, 8);
         }

         return var1;
      }

      int getBigContentViewLayoutResource(int var1) {
         if (var1 <= 3) {
            var1 = R.layout.notification_template_big_media_narrow;
         } else {
            var1 = R.layout.notification_template_big_media;
         }

         return var1;
      }

      int getContentViewLayoutResource() {
         return R.layout.notification_template_media;
      }

      public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor var1) {
         return VERSION.SDK_INT >= 21 ? null : this.generateBigContentView();
      }

      public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor var1) {
         return VERSION.SDK_INT >= 21 ? null : this.generateContentView();
      }

      public NotificationCompat.MediaStyle setCancelButtonIntent(PendingIntent var1) {
         this.mCancelButtonIntent = var1;
         return this;
      }

      public NotificationCompat.MediaStyle setMediaSession(MediaSessionCompat.Token var1) {
         this.mToken = var1;
         return this;
      }

      public NotificationCompat.MediaStyle setShowActionsInCompactView(int... var1) {
         this.mActionsToShowInCompact = var1;
         return this;
      }

      public NotificationCompat.MediaStyle setShowCancelButton(boolean var1) {
         if (VERSION.SDK_INT < 21) {
            this.mShowCancelButton = var1;
         }

         return this;
      }
   }
}

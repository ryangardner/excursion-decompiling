package androidx.core.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Notification.Builder;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.SparseArray;
import android.widget.RemoteViews;
import androidx.core.graphics.drawable.IconCompat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

class NotificationCompatBuilder implements NotificationBuilderWithBuilderAccessor {
   private final List<Bundle> mActionExtrasList = new ArrayList();
   private RemoteViews mBigContentView;
   private final Builder mBuilder;
   private final NotificationCompat.Builder mBuilderCompat;
   private RemoteViews mContentView;
   private final Bundle mExtras = new Bundle();
   private int mGroupAlertBehavior;
   private RemoteViews mHeadsUpContentView;

   NotificationCompatBuilder(NotificationCompat.Builder var1) {
      this.mBuilderCompat = var1;
      if (VERSION.SDK_INT >= 26) {
         this.mBuilder = new Builder(var1.mContext, var1.mChannelId);
      } else {
         this.mBuilder = new Builder(var1.mContext);
      }

      Notification var2 = var1.mNotification;
      Builder var3 = this.mBuilder.setWhen(var2.when).setSmallIcon(var2.icon, var2.iconLevel).setContent(var2.contentView).setTicker(var2.tickerText, var1.mTickerView).setVibrate(var2.vibrate).setLights(var2.ledARGB, var2.ledOnMS, var2.ledOffMS);
      boolean var4;
      if ((var2.flags & 2) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      var3 = var3.setOngoing(var4);
      if ((var2.flags & 8) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      var3 = var3.setOnlyAlertOnce(var4);
      if ((var2.flags & 16) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      var3 = var3.setAutoCancel(var4).setDefaults(var2.defaults).setContentTitle(var1.mContentTitle).setContentText(var1.mContentText).setContentInfo(var1.mContentInfo).setContentIntent(var1.mContentIntent).setDeleteIntent(var2.deleteIntent);
      PendingIntent var5 = var1.mFullScreenIntent;
      if ((var2.flags & 128) != 0) {
         var4 = true;
      } else {
         var4 = false;
      }

      var3.setFullScreenIntent(var5, var4).setLargeIcon(var1.mLargeIcon).setNumber(var1.mNumber).setProgress(var1.mProgressMax, var1.mProgress, var1.mProgressIndeterminate);
      if (VERSION.SDK_INT < 21) {
         this.mBuilder.setSound(var2.sound, var2.audioStreamType);
      }

      Iterator var7;
      if (VERSION.SDK_INT >= 16) {
         this.mBuilder.setSubText(var1.mSubText).setUsesChronometer(var1.mUseChronometer).setPriority(var1.mPriority);
         var7 = var1.mActions.iterator();

         while(var7.hasNext()) {
            this.addAction((NotificationCompat.Action)var7.next());
         }

         if (var1.mExtras != null) {
            this.mExtras.putAll(var1.mExtras);
         }

         if (VERSION.SDK_INT < 20) {
            if (var1.mLocalOnly) {
               this.mExtras.putBoolean("android.support.localOnly", true);
            }

            if (var1.mGroupKey != null) {
               this.mExtras.putString("android.support.groupKey", var1.mGroupKey);
               if (var1.mGroupSummary) {
                  this.mExtras.putBoolean("android.support.isGroupSummary", true);
               } else {
                  this.mExtras.putBoolean("android.support.useSideChannel", true);
               }
            }

            if (var1.mSortKey != null) {
               this.mExtras.putString("android.support.sortKey", var1.mSortKey);
            }
         }

         this.mContentView = var1.mContentView;
         this.mBigContentView = var1.mBigContentView;
      }

      if (VERSION.SDK_INT >= 19) {
         this.mBuilder.setShowWhen(var1.mShowWhen);
         if (VERSION.SDK_INT < 21 && var1.mPeople != null && !var1.mPeople.isEmpty()) {
            this.mExtras.putStringArray("android.people", (String[])var1.mPeople.toArray(new String[var1.mPeople.size()]));
         }
      }

      if (VERSION.SDK_INT >= 20) {
         this.mBuilder.setLocalOnly(var1.mLocalOnly).setGroup(var1.mGroupKey).setGroupSummary(var1.mGroupSummary).setSortKey(var1.mSortKey);
         this.mGroupAlertBehavior = var1.mGroupAlertBehavior;
      }

      if (VERSION.SDK_INT >= 21) {
         this.mBuilder.setCategory(var1.mCategory).setColor(var1.mColor).setVisibility(var1.mVisibility).setPublicVersion(var1.mPublicVersion).setSound(var2.sound, var2.audioAttributes);
         var7 = var1.mPeople.iterator();

         while(var7.hasNext()) {
            String var9 = (String)var7.next();
            this.mBuilder.addPerson(var9);
         }

         this.mHeadsUpContentView = var1.mHeadsUpContentView;
         if (var1.mInvisibleActions.size() > 0) {
            Bundle var10 = var1.getExtras().getBundle("android.car.EXTENSIONS");
            Bundle var8 = var10;
            if (var10 == null) {
               var8 = new Bundle();
            }

            var10 = new Bundle();

            for(int var6 = 0; var6 < var1.mInvisibleActions.size(); ++var6) {
               var10.putBundle(Integer.toString(var6), NotificationCompatJellybean.getBundleForAction((NotificationCompat.Action)var1.mInvisibleActions.get(var6)));
            }

            var8.putBundle("invisible_actions", var10);
            var1.getExtras().putBundle("android.car.EXTENSIONS", var8);
            this.mExtras.putBundle("android.car.EXTENSIONS", var8);
         }
      }

      if (VERSION.SDK_INT >= 24) {
         this.mBuilder.setExtras(var1.mExtras).setRemoteInputHistory(var1.mRemoteInputHistory);
         if (var1.mContentView != null) {
            this.mBuilder.setCustomContentView(var1.mContentView);
         }

         if (var1.mBigContentView != null) {
            this.mBuilder.setCustomBigContentView(var1.mBigContentView);
         }

         if (var1.mHeadsUpContentView != null) {
            this.mBuilder.setCustomHeadsUpContentView(var1.mHeadsUpContentView);
         }
      }

      if (VERSION.SDK_INT >= 26) {
         this.mBuilder.setBadgeIconType(var1.mBadgeIcon).setShortcutId(var1.mShortcutId).setTimeoutAfter(var1.mTimeout).setGroupAlertBehavior(var1.mGroupAlertBehavior);
         if (var1.mColorizedSet) {
            this.mBuilder.setColorized(var1.mColorized);
         }

         if (!TextUtils.isEmpty(var1.mChannelId)) {
            this.mBuilder.setSound((Uri)null).setDefaults(0).setLights(0, 0, 0).setVibrate((long[])null);
         }
      }

      if (VERSION.SDK_INT >= 29) {
         this.mBuilder.setAllowSystemGeneratedContextualActions(var1.mAllowSystemGeneratedContextualActions);
         this.mBuilder.setBubbleMetadata(NotificationCompat.BubbleMetadata.toPlatform(var1.mBubbleMetadata));
      }

      if (var1.mSilent) {
         if (this.mBuilderCompat.mGroupSummary) {
            this.mGroupAlertBehavior = 2;
         } else {
            this.mGroupAlertBehavior = 1;
         }

         this.mBuilder.setVibrate((long[])null);
         this.mBuilder.setSound((Uri)null);
         var2.defaults &= -2;
         var2.defaults &= -3;
         this.mBuilder.setDefaults(var2.defaults);
         if (VERSION.SDK_INT >= 26) {
            if (TextUtils.isEmpty(this.mBuilderCompat.mGroupKey)) {
               this.mBuilder.setGroup("silent");
            }

            this.mBuilder.setGroupAlertBehavior(this.mGroupAlertBehavior);
         }
      }

   }

   private void addAction(NotificationCompat.Action var1) {
      if (VERSION.SDK_INT >= 20) {
         IconCompat var2 = var1.getIconCompat();
         int var3 = VERSION.SDK_INT;
         byte var4 = 0;
         android.app.Notification.Action.Builder var8;
         if (var3 >= 23) {
            Icon var7;
            if (var2 != null) {
               var7 = var2.toIcon();
            } else {
               var7 = null;
            }

            var8 = new android.app.Notification.Action.Builder(var7, var1.getTitle(), var1.getActionIntent());
         } else {
            if (var2 != null) {
               var3 = var2.getResId();
            } else {
               var3 = 0;
            }

            var8 = new android.app.Notification.Action.Builder(var3, var1.getTitle(), var1.getActionIntent());
         }

         if (var1.getRemoteInputs() != null) {
            android.app.RemoteInput[] var5 = RemoteInput.fromCompat(var1.getRemoteInputs());
            int var6 = var5.length;

            for(var3 = var4; var3 < var6; ++var3) {
               var8.addRemoteInput(var5[var3]);
            }
         }

         Bundle var9;
         if (var1.getExtras() != null) {
            var9 = new Bundle(var1.getExtras());
         } else {
            var9 = new Bundle();
         }

         var9.putBoolean("android.support.allowGeneratedReplies", var1.getAllowGeneratedReplies());
         if (VERSION.SDK_INT >= 24) {
            var8.setAllowGeneratedReplies(var1.getAllowGeneratedReplies());
         }

         var9.putInt("android.support.action.semanticAction", var1.getSemanticAction());
         if (VERSION.SDK_INT >= 28) {
            var8.setSemanticAction(var1.getSemanticAction());
         }

         if (VERSION.SDK_INT >= 29) {
            var8.setContextual(var1.isContextual());
         }

         var9.putBoolean("android.support.action.showsUserInterface", var1.getShowsUserInterface());
         var8.addExtras(var9);
         this.mBuilder.addAction(var8.build());
      } else if (VERSION.SDK_INT >= 16) {
         this.mActionExtrasList.add(NotificationCompatJellybean.writeActionAndGetExtras(this.mBuilder, var1));
      }

   }

   private void removeSoundAndVibration(Notification var1) {
      var1.sound = null;
      var1.vibrate = null;
      var1.defaults &= -2;
      var1.defaults &= -3;
   }

   public Notification build() {
      NotificationCompat.Style var1 = this.mBuilderCompat.mStyle;
      if (var1 != null) {
         var1.apply(this);
      }

      RemoteViews var2;
      if (var1 != null) {
         var2 = var1.makeContentView(this);
      } else {
         var2 = null;
      }

      Notification var3 = this.buildInternal();
      if (var2 != null) {
         var3.contentView = var2;
      } else if (this.mBuilderCompat.mContentView != null) {
         var3.contentView = this.mBuilderCompat.mContentView;
      }

      if (VERSION.SDK_INT >= 16 && var1 != null) {
         var2 = var1.makeBigContentView(this);
         if (var2 != null) {
            var3.bigContentView = var2;
         }
      }

      if (VERSION.SDK_INT >= 21 && var1 != null) {
         var2 = this.mBuilderCompat.mStyle.makeHeadsUpContentView(this);
         if (var2 != null) {
            var3.headsUpContentView = var2;
         }
      }

      if (VERSION.SDK_INT >= 16 && var1 != null) {
         Bundle var4 = NotificationCompat.getExtras(var3);
         if (var4 != null) {
            var1.addCompatExtras(var4);
         }
      }

      return var3;
   }

   protected Notification buildInternal() {
      if (VERSION.SDK_INT >= 26) {
         return this.mBuilder.build();
      } else {
         Notification var1;
         if (VERSION.SDK_INT >= 24) {
            var1 = this.mBuilder.build();
            if (this.mGroupAlertBehavior != 0) {
               if (var1.getGroup() != null && (var1.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                  this.removeSoundAndVibration(var1);
               }

               if (var1.getGroup() != null && (var1.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                  this.removeSoundAndVibration(var1);
               }
            }

            return var1;
         } else {
            RemoteViews var8;
            if (VERSION.SDK_INT >= 21) {
               this.mBuilder.setExtras(this.mExtras);
               var1 = this.mBuilder.build();
               var8 = this.mContentView;
               if (var8 != null) {
                  var1.contentView = var8;
               }

               var8 = this.mBigContentView;
               if (var8 != null) {
                  var1.bigContentView = var8;
               }

               var8 = this.mHeadsUpContentView;
               if (var8 != null) {
                  var1.headsUpContentView = var8;
               }

               if (this.mGroupAlertBehavior != 0) {
                  if (var1.getGroup() != null && (var1.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                     this.removeSoundAndVibration(var1);
                  }

                  if (var1.getGroup() != null && (var1.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                     this.removeSoundAndVibration(var1);
                  }
               }

               return var1;
            } else if (VERSION.SDK_INT >= 20) {
               this.mBuilder.setExtras(this.mExtras);
               var1 = this.mBuilder.build();
               var8 = this.mContentView;
               if (var8 != null) {
                  var1.contentView = var8;
               }

               var8 = this.mBigContentView;
               if (var8 != null) {
                  var1.bigContentView = var8;
               }

               if (this.mGroupAlertBehavior != 0) {
                  if (var1.getGroup() != null && (var1.flags & 512) != 0 && this.mGroupAlertBehavior == 2) {
                     this.removeSoundAndVibration(var1);
                  }

                  if (var1.getGroup() != null && (var1.flags & 512) == 0 && this.mGroupAlertBehavior == 1) {
                     this.removeSoundAndVibration(var1);
                  }
               }

               return var1;
            } else if (VERSION.SDK_INT >= 19) {
               SparseArray var6 = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
               if (var6 != null) {
                  this.mExtras.putSparseParcelableArray("android.support.actionExtras", var6);
               }

               this.mBuilder.setExtras(this.mExtras);
               var1 = this.mBuilder.build();
               var8 = this.mContentView;
               if (var8 != null) {
                  var1.contentView = var8;
               }

               var8 = this.mBigContentView;
               if (var8 != null) {
                  var1.bigContentView = var8;
               }

               return var1;
            } else if (VERSION.SDK_INT >= 16) {
               var1 = this.mBuilder.build();
               Bundle var3 = NotificationCompat.getExtras(var1);
               Bundle var4 = new Bundle(this.mExtras);
               Iterator var2 = this.mExtras.keySet().iterator();

               while(var2.hasNext()) {
                  String var5 = (String)var2.next();
                  if (var3.containsKey(var5)) {
                     var4.remove(var5);
                  }
               }

               var3.putAll(var4);
               SparseArray var7 = NotificationCompatJellybean.buildActionExtrasMap(this.mActionExtrasList);
               if (var7 != null) {
                  NotificationCompat.getExtras(var1).putSparseParcelableArray("android.support.actionExtras", var7);
               }

               var8 = this.mContentView;
               if (var8 != null) {
                  var1.contentView = var8;
               }

               var8 = this.mBigContentView;
               if (var8 != null) {
                  var1.bigContentView = var8;
               }

               return var1;
            } else {
               return this.mBuilder.getNotification();
            }
         }
      }
   }

   public Builder getBuilder() {
      return this.mBuilder;
   }
}

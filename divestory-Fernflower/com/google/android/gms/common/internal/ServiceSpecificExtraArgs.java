package com.google.android.gms.common.internal;

public final class ServiceSpecificExtraArgs {
   private ServiceSpecificExtraArgs() {
   }

   public interface CastExtraArgs {
      String LISTENER = "listener";
   }

   public interface GamesExtraArgs {
      String DESIRED_LOCALE = "com.google.android.gms.games.key.desiredLocale";
      String GAME_PACKAGE_NAME = "com.google.android.gms.games.key.gamePackageName";
      String SIGNIN_OPTIONS = "com.google.android.gms.games.key.signInOptions";
      String WINDOW_TOKEN = "com.google.android.gms.games.key.popupWindowToken";
   }

   public interface PlusExtraArgs {
      String PLUS_AUTH_PACKAGE = "auth_package";
   }
}

package androidx.core.app;

import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.PersistableBundle;
import androidx.core.graphics.drawable.IconCompat;

public class Person {
   private static final String ICON_KEY = "icon";
   private static final String IS_BOT_KEY = "isBot";
   private static final String IS_IMPORTANT_KEY = "isImportant";
   private static final String KEY_KEY = "key";
   private static final String NAME_KEY = "name";
   private static final String URI_KEY = "uri";
   IconCompat mIcon;
   boolean mIsBot;
   boolean mIsImportant;
   String mKey;
   CharSequence mName;
   String mUri;

   Person(Person.Builder var1) {
      this.mName = var1.mName;
      this.mIcon = var1.mIcon;
      this.mUri = var1.mUri;
      this.mKey = var1.mKey;
      this.mIsBot = var1.mIsBot;
      this.mIsImportant = var1.mIsImportant;
   }

   public static Person fromAndroidPerson(android.app.Person var0) {
      Person.Builder var1 = (new Person.Builder()).setName(var0.getName());
      IconCompat var2;
      if (var0.getIcon() != null) {
         var2 = IconCompat.createFromIcon(var0.getIcon());
      } else {
         var2 = null;
      }

      return var1.setIcon(var2).setUri(var0.getUri()).setKey(var0.getKey()).setBot(var0.isBot()).setImportant(var0.isImportant()).build();
   }

   public static Person fromBundle(Bundle var0) {
      Bundle var1 = var0.getBundle("icon");
      Person.Builder var2 = (new Person.Builder()).setName(var0.getCharSequence("name"));
      IconCompat var3;
      if (var1 != null) {
         var3 = IconCompat.createFromBundle(var1);
      } else {
         var3 = null;
      }

      return var2.setIcon(var3).setUri(var0.getString("uri")).setKey(var0.getString("key")).setBot(var0.getBoolean("isBot")).setImportant(var0.getBoolean("isImportant")).build();
   }

   public static Person fromPersistableBundle(PersistableBundle var0) {
      return (new Person.Builder()).setName(var0.getString("name")).setUri(var0.getString("uri")).setKey(var0.getString("key")).setBot(var0.getBoolean("isBot")).setImportant(var0.getBoolean("isImportant")).build();
   }

   public IconCompat getIcon() {
      return this.mIcon;
   }

   public String getKey() {
      return this.mKey;
   }

   public CharSequence getName() {
      return this.mName;
   }

   public String getUri() {
      return this.mUri;
   }

   public boolean isBot() {
      return this.mIsBot;
   }

   public boolean isImportant() {
      return this.mIsImportant;
   }

   public android.app.Person toAndroidPerson() {
      android.app.Person.Builder var1 = (new android.app.Person.Builder()).setName(this.getName());
      Icon var2;
      if (this.getIcon() != null) {
         var2 = this.getIcon().toIcon();
      } else {
         var2 = null;
      }

      return var1.setIcon(var2).setUri(this.getUri()).setKey(this.getKey()).setBot(this.isBot()).setImportant(this.isImportant()).build();
   }

   public Person.Builder toBuilder() {
      return new Person.Builder(this);
   }

   public Bundle toBundle() {
      Bundle var1 = new Bundle();
      var1.putCharSequence("name", this.mName);
      IconCompat var2 = this.mIcon;
      Bundle var3;
      if (var2 != null) {
         var3 = var2.toBundle();
      } else {
         var3 = null;
      }

      var1.putBundle("icon", var3);
      var1.putString("uri", this.mUri);
      var1.putString("key", this.mKey);
      var1.putBoolean("isBot", this.mIsBot);
      var1.putBoolean("isImportant", this.mIsImportant);
      return var1;
   }

   public PersistableBundle toPersistableBundle() {
      PersistableBundle var1 = new PersistableBundle();
      CharSequence var2 = this.mName;
      String var3;
      if (var2 != null) {
         var3 = var2.toString();
      } else {
         var3 = null;
      }

      var1.putString("name", var3);
      var1.putString("uri", this.mUri);
      var1.putString("key", this.mKey);
      var1.putBoolean("isBot", this.mIsBot);
      var1.putBoolean("isImportant", this.mIsImportant);
      return var1;
   }

   public static class Builder {
      IconCompat mIcon;
      boolean mIsBot;
      boolean mIsImportant;
      String mKey;
      CharSequence mName;
      String mUri;

      public Builder() {
      }

      Builder(Person var1) {
         this.mName = var1.mName;
         this.mIcon = var1.mIcon;
         this.mUri = var1.mUri;
         this.mKey = var1.mKey;
         this.mIsBot = var1.mIsBot;
         this.mIsImportant = var1.mIsImportant;
      }

      public Person build() {
         return new Person(this);
      }

      public Person.Builder setBot(boolean var1) {
         this.mIsBot = var1;
         return this;
      }

      public Person.Builder setIcon(IconCompat var1) {
         this.mIcon = var1;
         return this;
      }

      public Person.Builder setImportant(boolean var1) {
         this.mIsImportant = var1;
         return this;
      }

      public Person.Builder setKey(String var1) {
         this.mKey = var1;
         return this;
      }

      public Person.Builder setName(CharSequence var1) {
         this.mName = var1;
         return this;
      }

      public Person.Builder setUri(String var1) {
         this.mUri = var1;
         return this;
      }
   }
}

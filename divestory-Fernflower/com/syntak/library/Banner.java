package com.syntak.library;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import java.io.IOException;

public class Banner {
   private static String http;
   public int BannerIndex = -1;
   private ImageView banner;
   private String[][] bannerList;
   private String bannerListFile = "bannerlist.txt";
   private Context ctx;
   private Drawable d = null;
   private boolean flagListBuilt = false;
   private String hostname;
   private int indexMax = 1;
   private String urlPath;

   public Banner(Context var1, ImageView var2, String var3, String var4) {
      this.ctx = var1;
      this.banner = var2;
      this.hostname = var3;
      StringBuilder var5 = new StringBuilder();
      var5.append(http);
      var5.append(var3);
      var5.append("/");
      this.urlPath = var5.toString();
      this.bannerListFile = var4;
   }

   private void bannerListBuilder() {
      StringBuilder var1 = new StringBuilder();
      var1.append(this.urlPath);
      var1.append(this.bannerListFile);
      String var3 = var1.toString();
      this.indexMax = 10;

      try {
         String[][] var4 = FileOp.UrlFileToArray2D(var3);
         this.bannerList = var4;
         this.indexMax = var4.length;
         this.flagListBuilt = true;
      } catch (IOException var2) {
         var2.printStackTrace();
      }

   }

   public int bannerOnClick() {
      if (NetOp.isInternetConnected(this.ctx) && FileOp.IsHostavailable(this.hostname)) {
         String var2;
         if (this.bannerList[this.BannerIndex].length > 1) {
            StringBuilder var1 = new StringBuilder();
            var1.append(this.urlPath);
            var1.append(this.bannerList[this.BannerIndex][1]);
            var2 = var1.toString();
         } else {
            var2 = this.urlPath;
         }

         Intent var3 = new Intent("android.intent.action.VIEW", Uri.parse(var2));
         this.ctx.startActivity(var3);
         return 0;
      } else {
         return -1;
      }
   }

   public int prepareBanner(int var1) {
      this.BannerIndex = -1;
      if (NetOp.isInternetConnected(this.ctx) && FileOp.IsHostavailable(this.hostname)) {
         if (!this.flagListBuilt) {
            this.bannerListBuilder();
         }

         if (this.flagListBuilt) {
            int var2;
            do {
               var2 = (int)(Math.random() * (double)this.indexMax);
               this.BannerIndex = var2;
            } while(var2 == var1);

            StringBuilder var3 = new StringBuilder();
            var3.append(this.urlPath);
            var3.append(this.bannerList[this.BannerIndex][0]);
            this.d = FileOp.LoadImageFromWeb(var3.toString());
         }
      }

      if (this.d == null) {
         this.BannerIndex = -1;
      }

      return this.BannerIndex;
   }

   public void showBanner() {
      Drawable var1 = this.d;
      if (var1 != null) {
         this.banner.setImageDrawable(var1);
      }

   }
}

/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.widget.ImageView
 */
package com.syntak.library;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.widget.ImageView;
import com.syntak.library.FileOp;
import com.syntak.library.NetOp;
import java.io.IOException;

public class Banner {
    private static String http = "https://";
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

    public Banner(Context object, ImageView imageView, String string2, String string3) {
        this.ctx = object;
        this.banner = imageView;
        this.hostname = string2;
        object = new StringBuilder();
        ((StringBuilder)object).append(http);
        ((StringBuilder)object).append(string2);
        ((StringBuilder)object).append("/");
        this.urlPath = ((StringBuilder)object).toString();
        this.bannerListFile = string3;
    }

    private void bannerListBuilder() {
        String[][] arrstring = new StringBuilder();
        arrstring.append(this.urlPath);
        arrstring.append(this.bannerListFile);
        arrstring = arrstring.toString();
        this.indexMax = 10;
        try {
            arrstring = FileOp.UrlFileToArray2D((String)arrstring);
            this.bannerList = arrstring;
            this.indexMax = arrstring.length;
            this.flagListBuilt = true;
            return;
        }
        catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public int bannerOnClick() {
        CharSequence charSequence;
        if (!NetOp.isInternetConnected(this.ctx)) return -1;
        if (!FileOp.IsHostavailable(this.hostname)) return -1;
        if (this.bannerList[this.BannerIndex].length > 1) {
            charSequence = new StringBuilder();
            ((StringBuilder)charSequence).append(this.urlPath);
            ((StringBuilder)charSequence).append(this.bannerList[this.BannerIndex][1]);
            charSequence = ((StringBuilder)charSequence).toString();
        } else {
            charSequence = this.urlPath;
        }
        charSequence = new Intent("android.intent.action.VIEW", Uri.parse((String)charSequence));
        this.ctx.startActivity((Intent)charSequence);
        return 0;
    }

    public int prepareBanner(int n) {
        this.BannerIndex = -1;
        if (NetOp.isInternetConnected(this.ctx) && FileOp.IsHostavailable(this.hostname)) {
            if (!this.flagListBuilt) {
                this.bannerListBuilder();
            }
            if (this.flagListBuilt) {
                int n2;
                do {
                    this.BannerIndex = n2 = (int)(Math.random() * (double)this.indexMax);
                } while (n2 == n);
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder.append(this.urlPath);
                stringBuilder.append(this.bannerList[this.BannerIndex][0]);
                this.d = FileOp.LoadImageFromWeb(stringBuilder.toString());
            }
        }
        if (this.d != null) return this.BannerIndex;
        this.BannerIndex = -1;
        return this.BannerIndex;
    }

    public void showBanner() {
        Drawable drawable2 = this.d;
        if (drawable2 == null) return;
        this.banner.setImageDrawable(drawable2);
    }
}


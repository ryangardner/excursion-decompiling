/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 */
package com.crest.divestory.ui.logs;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

public class RatingView {
    Activity activity;
    private int selected = 0;

    RatingView(Activity activity, int n) {
        this.activity = activity;
        this.init(n);
    }

    RatingView(Activity activity, int n, int n2) {
        this.activity = activity;
        this.selected = Math.min(n, n2);
        this.init(n);
    }

    private void init(int n) {
        LinearLayout linearLayout = (LinearLayout)this.activity.findViewById(2131231239);
        final ImageView[] arrimageView = new ImageView[n];
        int n2 = 0;
        while (n2 < n) {
            ImageView imageView = (ImageView)this.activity.getLayoutInflater().inflate(2131427451, null);
            int n3 = n2 + 1;
            imageView.setTag((Object)n3);
            imageView.setOnClickListener(new View.OnClickListener(){

                public void onClick(View imageView) {
                    RatingView.this.selected = (Integer)imageView.getTag();
                    int n = 0;
                    while (n < ((ImageView[])(imageView = arrimageView)).length) {
                        imageView = imageView[n];
                        boolean bl = n < RatingView.this.selected;
                        imageView.setSelected(bl);
                        ++n;
                    }
                }
            });
            arrimageView[n2] = imageView;
            ImageView imageView2 = arrimageView[n2];
            boolean bl = n2 < this.selected;
            imageView2.setSelected(bl);
            linearLayout.addView((View)imageView);
            n2 = n3;
        }
    }

}


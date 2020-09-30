/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.PopupWindow
 *  android.widget.TextView
 */
package com.syntak.library;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.syntak.library.R;

public class PopupMenu {
    public PopupWindow NewInstance(Context context, String[] arrstring, ViewGroup.LayoutParams layoutParams, View view) {
        View view2 = ((LayoutInflater)context.getSystemService("layout_inflater")).inflate(R.layout.popup_menu, null);
        LinearLayout linearLayout = (LinearLayout)view2.findViewById(R.id.linear_layout);
        PopupWindow popupWindow = new PopupWindow(view2, -2, -2);
        ImageView imageView = (ImageView)view2.findViewById(R.id.image);
        view2 = (TextView)view2.findViewById(R.id.text_view);
        imageView.setVisibility(8);
        imageView = view2.getLayoutParams();
        int n = 0;
        do {
            if (n >= arrstring.length) {
                popupWindow.setBackgroundDrawable((Drawable)new BitmapDrawable());
                popupWindow.setOutsideTouchable(false);
                popupWindow.showAtLocation(view, 17, 0, 0);
                return popupWindow;
            }
            view2 = new TextView(context);
            if (layoutParams != null) {
                view2.setLayoutParams(layoutParams);
            } else {
                view2.setLayoutParams((ViewGroup.LayoutParams)imageView);
            }
            linearLayout.addView(view2);
            view2.setTag((Object)n);
            view2.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    int n = (Integer)view.getTag();
                    PopupMenu.this.onItemClicked(n);
                }
            });
            ++n;
        } while (true);
    }

    public void PopupMenu() {
    }

    public void onItemClicked(int n) {
    }

}


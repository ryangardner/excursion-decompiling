/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnLongClickListener
 */
package com.syntak.library.ui;

import android.view.View;

public class ShowHiddenVIewByClick {
    CLICK_TYPE click_type_for_hide = CLICK_TYPE.SHORT;
    CLICK_TYPE click_type_for_show = CLICK_TYPE.SHORT;
    View hidden;
    View title;

    public ShowHiddenVIewByClick(View view, View view2) {
        this.title = view;
        this.hidden = view2;
        this.start();
    }

    public ShowHiddenVIewByClick(View view, View view2, CLICK_TYPE cLICK_TYPE, CLICK_TYPE cLICK_TYPE2) {
        this.title = view;
        this.hidden = view2;
        this.click_type_for_show = cLICK_TYPE;
        this.click_type_for_hide = cLICK_TYPE2;
        this.start();
    }

    private void start() {
        if (this.click_type_for_show == CLICK_TYPE.SHORT) {
            this.title.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    ShowHiddenVIewByClick.this.toggle_view(view);
                }
            });
        } else {
            this.title.setOnLongClickListener(new View.OnLongClickListener(){

                public boolean onLongClick(View view) {
                    ShowHiddenVIewByClick.this.toggle_view(view);
                    return true;
                }
            });
        }
        if (this.click_type_for_hide == CLICK_TYPE.SHORT) {
            this.title.setOnClickListener(new View.OnClickListener(){

                public void onClick(View view) {
                    ShowHiddenVIewByClick.this.toggle_view(view);
                }
            });
        } else {
            this.title.setOnLongClickListener(new View.OnLongClickListener(){

                public boolean onLongClick(View view) {
                    ShowHiddenVIewByClick.this.toggle_view(view);
                    return true;
                }
            });
        }
        this.title.setTag((Object)0);
        this.hidden.setVisibility(8);
    }

    private void toggle_view(View view) {
        if ((Integer)view.getTag() == 0) {
            view.setTag((Object)1);
            this.hidden.setVisibility(0);
            return;
        }
        view.setTag((Object)0);
        this.hidden.setVisibility(8);
    }

    public static final class CLICK_TYPE
    extends Enum<CLICK_TYPE> {
        private static final /* synthetic */ CLICK_TYPE[] $VALUES;
        public static final /* enum */ CLICK_TYPE LONG;
        public static final /* enum */ CLICK_TYPE SHORT;

        static {
            CLICK_TYPE cLICK_TYPE;
            SHORT = new CLICK_TYPE();
            LONG = cLICK_TYPE = new CLICK_TYPE();
            $VALUES = new CLICK_TYPE[]{SHORT, cLICK_TYPE};
        }

        public static CLICK_TYPE valueOf(String string2) {
            return Enum.valueOf(CLICK_TYPE.class, string2);
        }

        public static CLICK_TYPE[] values() {
            return (CLICK_TYPE[])$VALUES.clone();
        }
    }

}


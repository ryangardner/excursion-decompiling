/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.StateListDrawable
 *  android.text.Editable
 *  android.text.method.KeyListener
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.CheckBox
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.EditText
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.PopupWindow
 *  android.widget.PopupWindow$OnDismissListener
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.syntak.library.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.Editable;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.syntak.library.R;

public class EditorAccountPassword {
    public static final int MODE_CREATE_ACCOUNT_PASSWORD = 2;
    public static final int MODE_CREATE_PASSWORD = 1;
    public static final int MODE_GET_ACCOUNT_PASSWORD = 8;
    public static final int MODE_GET_PASSWORD = 7;
    public static final int MODE_MODIFY_ACCOUNT = 4;
    public static final int MODE_MODIFY_PASSWORD = 3;
    public static final int MODE_VERIFY_ACCOUNT_PASSWORD = 6;
    public static final int MODE_VERIFY_PASSWORD = 5;
    String account;
    View ancher;
    RelativeLayout base_view;
    Button button_cancel;
    Button button_confirm;
    CheckBox checkbox;
    Context context;
    TextView dialog_title;
    LinearLayout external_view_place_holder;
    EditText field_account;
    EditText field_password;
    EditText field_password_again;
    ImageView icon_cancel;
    boolean is_outside_touchable = false;
    int mode;
    String password;
    PopupWindow popup = null;
    String text_cancel;
    String text_checkbox;
    String text_confirm;
    String text_hint_account;
    String text_hint_account_new;
    String text_hint_password;
    String text_hint_password_again;
    String text_hint_password_new;
    String text_hint_password_new_again;
    String text_hint_password_old;
    String title;
    TextView warning;
    String warning_account_password_wrong;
    String warning_fields_empty;
    String warning_password_wrong;
    String warning_passwords_different;

    public EditorAccountPassword(Info info) {
        this.mode = info.mode;
        this.context = info.context;
        this.ancher = info.ancher;
        this.title = info.title;
        this.text_hint_account = info.text_hint_account;
        this.text_hint_password = info.text_hint_password;
        this.text_hint_password_again = info.text_hint_password_again;
        this.text_hint_password_old = info.text_hint_password_old;
        this.text_hint_password_new = info.text_hint_password_new;
        this.text_hint_password_new_again = info.text_hint_password_new_again;
        this.text_hint_account_new = info.text_hint_account_new;
        this.text_checkbox = info.text_checkbox;
        this.text_confirm = info.text_confirm;
        this.text_cancel = info.text_cancel;
        this.warning_passwords_different = info.warning_passwords_different;
        this.warning_fields_empty = info.warning_fields_empty;
        this.warning_password_wrong = info.warning_password_wrong;
        this.warning_account_password_wrong = info.warning_account_password_wrong;
        this.account = info.account;
        this.password = info.password;
        info = LayoutInflater.from((Context)this.context).inflate(R.layout.editor_account_password, null);
        this.popup = this.prepare_popup(this.ancher, (View)info);
        this.base_view = (RelativeLayout)info.findViewById(R.id.base_view);
        this.external_view_place_holder = (LinearLayout)info.findViewById(R.id.external_view_place_holder);
        this.icon_cancel = (ImageView)info.findViewById(R.id.icon_cancel);
        this.warning = (TextView)info.findViewById(R.id.warning);
        this.dialog_title = (TextView)info.findViewById(R.id.title);
        this.field_account = (EditText)info.findViewById(R.id.field_name);
        this.field_password = (EditText)info.findViewById(R.id.password);
        this.field_password_again = (EditText)info.findViewById(R.id.password_again);
        this.checkbox = (CheckBox)info.findViewById(R.id.checkbox);
        this.button_cancel = (Button)info.findViewById(R.id.button_cancel);
        this.button_confirm = (Button)info.findViewById(R.id.button_confirm);
        this.dialog_title.setText((CharSequence)this.title);
        this.field_account.setHint((CharSequence)this.text_hint_account);
        this.field_password.setHint((CharSequence)this.text_hint_password);
        this.field_password_again.setHint((CharSequence)this.text_hint_password_again);
        this.field_account.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public void onFocusChange(View view, boolean bl) {
                EditorAccountPassword.this.warning.setText((CharSequence)"");
            }
        });
        this.field_password.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public void onFocusChange(View view, boolean bl) {
                EditorAccountPassword.this.warning.setText((CharSequence)"");
            }
        });
        this.field_password_again.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public void onFocusChange(View view, boolean bl) {
                EditorAccountPassword.this.warning.setText((CharSequence)"");
            }
        });
        this.field_account.setText((CharSequence)"");
        this.field_password.setText((CharSequence)"");
        this.field_password_again.setText((CharSequence)"");
        this.checkbox.setText((CharSequence)this.text_checkbox);
        this.button_cancel.setText((CharSequence)this.text_cancel);
        this.button_confirm.setText((CharSequence)this.text_confirm);
        this.button_cancel.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditorAccountPassword.this.popup.dismiss();
                EditorAccountPassword.this.OnCancelled();
            }
        });
        this.icon_cancel.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditorAccountPassword.this.popup.dismiss();
                EditorAccountPassword.this.OnCancelled();
            }
        });
        this.button_confirm.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                EditorAccountPassword.this.field_account.clearFocus();
                EditorAccountPassword.this.field_password.clearFocus();
                EditorAccountPassword.this.field_password_again.clearFocus();
                String string2 = EditorAccountPassword.this.field_account.getText().toString();
                object = EditorAccountPassword.this.field_password.getText().toString();
                String string3 = EditorAccountPassword.this.field_password_again.getText().toString();
                boolean bl = EditorAccountPassword.this.checkbox.isChecked();
                switch (EditorAccountPassword.this.mode) {
                    default: {
                        return;
                    }
                    case 8: {
                        if (string2.length() != 0 && ((String)object).length() != 0) {
                            EditorAccountPassword.this.popup.dismiss();
                            EditorAccountPassword.this.OnConfirmed(string2, (String)object, bl);
                            return;
                        }
                        EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_fields_empty);
                        return;
                    }
                    case 7: {
                        if (((String)object).length() == 0) {
                            EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_fields_empty);
                            return;
                        }
                        EditorAccountPassword.this.popup.dismiss();
                        EditorAccountPassword.this.OnConfirmed((String)object);
                        return;
                    }
                    case 6: {
                        if (string2.length() != 0 && ((String)object).length() != 0) {
                            if (string2.equals(EditorAccountPassword.this.account) && ((String)object).equals(EditorAccountPassword.this.password)) {
                                EditorAccountPassword.this.popup.dismiss();
                                EditorAccountPassword.this.OnConfirmed(string2, (String)object);
                                return;
                            }
                            EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_account_password_wrong);
                            return;
                        }
                        EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_fields_empty);
                        return;
                    }
                    case 5: {
                        if (((String)object).length() == 0) {
                            EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_fields_empty);
                            return;
                        }
                        if (!((String)object).equals(EditorAccountPassword.this.password)) {
                            EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_password_wrong);
                            return;
                        }
                        EditorAccountPassword.this.popup.dismiss();
                        EditorAccountPassword.this.OnConfirmed((String)object);
                        return;
                    }
                    case 4: {
                        string3 = EditorAccountPassword.this.field_password.getText().toString();
                        string2 = EditorAccountPassword.this.field_password_again.getText().toString();
                        if (string2.length() != 0 && string3.length() != 0) {
                            if (!((String)object).equals(string2)) {
                                EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_password_wrong);
                                return;
                            }
                            EditorAccountPassword.this.popup.dismiss();
                            EditorAccountPassword.this.OnConfirmed(string3);
                            return;
                        }
                        EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_fields_empty);
                        return;
                    }
                    case 3: {
                        string2 = EditorAccountPassword.this.field_account.getText().toString();
                        String string4 = EditorAccountPassword.this.field_password.getText().toString();
                        string3 = EditorAccountPassword.this.field_password_again.getText().toString();
                        if (string2.length() != 0 && string4.length() != 0 && string3.length() != 0) {
                            if (!((String)object).equals(string2)) {
                                EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_password_wrong);
                                return;
                            }
                            if (!string4.equals(string3)) {
                                EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_passwords_different);
                                return;
                            }
                            EditorAccountPassword.this.popup.dismiss();
                            EditorAccountPassword.this.OnConfirmed(string4);
                            return;
                        }
                        EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_fields_empty);
                        return;
                    }
                    case 2: {
                        if (string2.length() != 0 && ((String)object).length() != 0 && string3.length() != 0) {
                            if (!((String)object).equals(string3)) {
                                EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_passwords_different);
                                return;
                            }
                            EditorAccountPassword.this.popup.dismiss();
                            EditorAccountPassword.this.OnConfirmed(string2, (String)object, bl);
                            return;
                        }
                        EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_fields_empty);
                        return;
                    }
                    case 1: 
                }
                if (((String)object).length() != 0 && string3.length() != 0) {
                    if (!((String)object).equals(string3)) {
                        EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_passwords_different);
                        return;
                    }
                    EditorAccountPassword.this.popup.dismiss();
                    EditorAccountPassword.this.OnConfirmed((String)object);
                    return;
                }
                EditorAccountPassword.this.warning.setText((CharSequence)EditorAccountPassword.this.warning_fields_empty);
            }
        });
        this.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (bl) {
                    EditorAccountPassword.this.mode = 2;
                    EditorAccountPassword.this.field_password_again.setVisibility(0);
                } else {
                    EditorAccountPassword.this.mode = 8;
                    EditorAccountPassword.this.field_password_again.setVisibility(8);
                }
                EditorAccountPassword.this.OnCheckChanged(bl);
            }
        });
        this.warning.setVisibility(0);
        this.checkbox.setVisibility(4);
        switch (this.mode) {
            default: {
                return;
            }
            case 8: {
                this.field_account.setVisibility(0);
                this.field_password.setVisibility(0);
                this.field_password_again.setVisibility(8);
                this.checkbox.setVisibility(0);
                return;
            }
            case 7: {
                this.field_account.setVisibility(8);
                this.field_password.setVisibility(0);
                this.field_password_again.setVisibility(8);
                this.checkbox.setVisibility(8);
                return;
            }
            case 6: {
                this.field_account.setVisibility(0);
                this.field_password.setVisibility(0);
                this.field_password_again.setVisibility(8);
                this.checkbox.setVisibility(8);
                return;
            }
            case 5: {
                this.field_account.setVisibility(8);
                this.field_password.setVisibility(0);
                this.field_password_again.setVisibility(8);
                this.checkbox.setVisibility(8);
                return;
            }
            case 4: {
                this.field_account.setText((CharSequence)this.account);
                this.field_account.setKeyListener(null);
                this.field_password.setHint((CharSequence)this.text_hint_account_new);
                this.field_password_again.setHint((CharSequence)this.text_hint_password);
                this.checkbox.setVisibility(8);
                return;
            }
            case 3: {
                this.field_account.setHint((CharSequence)this.text_hint_password_old);
                this.field_password.setHint((CharSequence)this.text_hint_password_new);
                this.field_password_again.setHint((CharSequence)this.text_hint_password_new_again);
                this.checkbox.setVisibility(8);
                return;
            }
            case 2: {
                this.field_account.setVisibility(0);
                this.field_password.setVisibility(0);
                this.field_password_again.setVisibility(0);
                this.checkbox.setVisibility(0);
                return;
            }
            case 1: 
        }
        this.field_account.setVisibility(8);
        this.field_password.setVisibility(0);
        this.field_password_again.setVisibility(0);
        this.checkbox.setVisibility(8);
    }

    public static StateListDrawable makeButtonBackgroundSelector(int n, int n2) {
        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.setExitFadeDuration(300);
        ColorDrawable colorDrawable = new ColorDrawable(n2);
        stateListDrawable.addState(new int[]{16842919}, (Drawable)colorDrawable);
        colorDrawable = new ColorDrawable(n);
        stateListDrawable.addState(new int[0], (Drawable)colorDrawable);
        return stateListDrawable;
    }

    public void OnCancelled() {
    }

    public void OnCheckChanged(boolean bl) {
    }

    public void OnConfirmed(String string2) {
    }

    public void OnConfirmed(String string2, String string3) {
    }

    public void OnConfirmed(String string2, String string3, boolean bl) {
    }

    public void includeView(View view) {
        this.external_view_place_holder.addView(view);
        this.base_view.invalidate();
    }

    PopupWindow prepare_popup(View view, View view2) {
        view2 = new PopupWindow(view2, -2, -2);
        this.popup = view2;
        view2.setTouchable(true);
        this.popup.setFocusable(true);
        this.popup.setBackgroundDrawable(null);
        this.popup.setOutsideTouchable(this.is_outside_touchable);
        this.popup.update();
        this.popup.showAtLocation(view, 17, 0, 0);
        this.popup.setOnDismissListener(new PopupWindow.OnDismissListener(){

            public void onDismiss() {
            }
        });
        return this.popup;
    }

    public void setButtonColors(int n, int n2, int n3) {
        this.button_cancel.setTextColor(n);
        this.button_confirm.setTextColor(n);
        this.button_cancel.setBackground((Drawable)EditorAccountPassword.makeButtonBackgroundSelector(n2, n3));
        this.button_confirm.setBackground((Drawable)EditorAccountPassword.makeButtonBackgroundSelector(n2, n3));
    }

    public void setCheckbox(boolean bl) {
        this.checkbox.setChecked(bl);
    }

    public void setEditTextColor(int n) {
        this.field_account.setTextColor(n);
        this.field_password.setTextColor(n);
        this.field_password_again.setTextColor(n);
    }

    public void setOutsideTouchable(boolean bl) {
        this.is_outside_touchable = bl;
    }

    public void setSizesPx(float f, float f2, float f3, float f4) {
        if (f > 0.0f) {
            this.dialog_title.setTextSize(0, f);
        }
        if (f2 > 0.0f) {
            this.checkbox.setTextSize(0, f2);
        }
        if (f3 > 0.0f) {
            this.field_account.setTextSize(0, f3);
            this.field_password.setTextSize(0, f3);
            this.field_password_again.setTextSize(0, f3);
        }
        if (!(f4 > 0.0f)) return;
        this.button_cancel.setTextSize(0, f4);
        this.button_confirm.setTextSize(0, f4);
    }

    public void setTitleColor(int n) {
        this.dialog_title.setTextColor(n);
    }

    public void stop() {
        PopupWindow popupWindow = this.popup;
        if (popupWindow == null) return;
        popupWindow.dismiss();
    }

    public static class Info {
        public String account;
        public View ancher;
        public Context context;
        public int mode;
        public String password;
        public String text_cancel;
        public String text_checkbox;
        public String text_confirm;
        public String text_hint_account;
        public String text_hint_account_new;
        public String text_hint_password;
        public String text_hint_password_again;
        public String text_hint_password_new;
        public String text_hint_password_new_again;
        public String text_hint_password_old;
        public String title;
        public String warning_account_password_wrong;
        public String warning_fields_empty;
        public String warning_password_wrong;
        public String warning_passwords_different;
    }

}


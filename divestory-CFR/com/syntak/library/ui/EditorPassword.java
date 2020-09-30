/*
 * Decompiled with CFR <Could not determine version>.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
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
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
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

public class EditorPassword {
    public static final int MODE_CREATE_NAME_PASSWORD = 2;
    public static final int MODE_CREATE_PASSWORD = 1;
    public static final int MODE_LOGIN = 7;
    public static final int MODE_MODIFY_NAME_PASSWORD = 4;
    public static final int MODE_MODIFY_PASSWORD = 3;
    public static final int MODE_VERIFY_NAME_PASSWORD = 6;
    public static final int MODE_VERIFY_PASSWORD = 5;
    RelativeLayout base_view;
    Button buttonCancel;
    Button buttonConfirm;
    CheckBox checkbox;
    TextView dialog_title;
    LinearLayout external_view_place_holder;
    EditText field1;
    EditText field2;
    EditText field_name;
    boolean flag_cancel_on_dismiss = true;
    ImageView iconCancel;
    boolean is_outside_touchable = false;
    TextView label_field1;
    TextView label_field2;
    TextView label_name;
    int mode;
    PopupWindow popup = null;
    RelativeLayout row_field1;
    RelativeLayout row_field2;
    RelativeLayout row_name;
    TextView warning;

    public EditorPassword(Context context, View view, String string2, String string3, String string4, String string5, final String string6, String string7, String string8) {
        this.common_init(context, view, string2, string7, string8);
        if (string3 == null) {
            this.row_name.setVisibility(8);
            this.buttonConfirm.setTag((Object)1);
        } else {
            this.label_name.setText((CharSequence)string3);
            this.buttonConfirm.setTag((Object)2);
        }
        this.label_field1.setText((CharSequence)string4);
        this.label_field2.setText((CharSequence)string5);
        this.buttonConfirm.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (EditorPassword.this.field1.getText().toString().length() == 0) return;
                if (EditorPassword.this.field2.getText().toString().length() == 0) {
                    return;
                }
                if ((Integer)object.getTag() == 2 && EditorPassword.this.field_name.getText().toString().length() == 0) {
                    return;
                }
                if (EditorPassword.this.field1.getText().toString().equals(EditorPassword.this.field2.getText().toString())) {
                    EditorPassword.this.flag_cancel_on_dismiss = false;
                    EditorPassword.this.popup.dismiss();
                    object = EditorPassword.this;
                    ((EditorPassword)object).OnConfirmed(((EditorPassword)object).field1.getText().toString());
                    return;
                }
                EditorPassword.this.warning.setText((CharSequence)string6);
                EditorPassword.this.warning.setVisibility(0);
            }
        });
    }

    public EditorPassword(Context context, View view, String string2, String string3, String string4, final String string5, final String string6, final String string7, String string8, String string9) {
        this.common_init(context, view, string2, string8, string9);
        if (string3 == null) {
            this.row_name.setVisibility(8);
            this.buttonConfirm.setTag((Object)5);
        } else {
            this.label_name.setText((CharSequence)string3);
            this.buttonConfirm.setTag((Object)6);
        }
        this.label_field1.setText((CharSequence)string4);
        this.row_field2.setVisibility(8);
        this.buttonConfirm.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if (5 == (Integer)object.getTag()) {
                    if (EditorPassword.this.field1.getText().toString().equals(string6)) {
                        EditorPassword.this.flag_cancel_on_dismiss = false;
                        EditorPassword.this.popup.dismiss();
                        object = EditorPassword.this;
                        ((EditorPassword)object).OnConfirmed(((EditorPassword)object).field1.getText().toString());
                        return;
                    }
                    EditorPassword.this.warning.setText((CharSequence)string7);
                    EditorPassword.this.warning.setVisibility(0);
                    return;
                }
                if (EditorPassword.this.field_name.getText().toString().equals(string5) && EditorPassword.this.field1.getText().toString().equals(string6)) {
                    EditorPassword.this.flag_cancel_on_dismiss = false;
                    EditorPassword.this.popup.dismiss();
                    object = EditorPassword.this;
                    ((EditorPassword)object).OnConfirmed(((EditorPassword)object).field_name.getText().toString());
                    return;
                }
                EditorPassword.this.warning.setText((CharSequence)string7);
                EditorPassword.this.warning.setVisibility(0);
            }
        });
    }

    public EditorPassword(Context context, View view, String string2, String string3, String string4, String string5, final String string6, String string7, String string8, String string9, String string10) {
        this.common_init(context, view, string2, string8, string9);
        this.buttonConfirm.setTag((Object)7);
        this.label_name.setText((CharSequence)string3);
        this.label_field1.setText((CharSequence)string4);
        this.label_field2.setText((CharSequence)string5);
        this.checkbox.setText((CharSequence)string7);
        this.checkbox.setVisibility(0);
        this.row_field2.setVisibility(8);
        this.checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            public void onCheckedChanged(CompoundButton compoundButton, boolean bl) {
                if (bl) {
                    EditorPassword.this.row_field2.setVisibility(0);
                    EditorPassword.this.mode = 2;
                } else {
                    EditorPassword.this.row_field2.setVisibility(8);
                    EditorPassword.this.mode = 7;
                }
                EditorPassword.this.buttonConfirm.setTag((Object)EditorPassword.this.mode);
            }
        });
        this.buttonConfirm.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                int n = (Integer)object.getTag();
                if (EditorPassword.this.field_name.getText().toString().length() == 0) return;
                if (EditorPassword.this.field1.getText().toString().length() == 0) {
                    return;
                }
                if (n == 2 && EditorPassword.this.field2.getText().toString().length() == 0) {
                    return;
                }
                if (n == 7) {
                    object = EditorPassword.this;
                    ((EditorPassword)object).OnConfirmed(((EditorPassword)object).field_name.getText().toString(), EditorPassword.this.field1.getText().toString(), false);
                    return;
                }
                if (EditorPassword.this.field1.getText().toString().equals(EditorPassword.this.field2.getText().toString())) {
                    EditorPassword.this.flag_cancel_on_dismiss = false;
                    object = EditorPassword.this;
                    ((EditorPassword)object).OnConfirmed(((EditorPassword)object).field_name.getText().toString(), EditorPassword.this.field1.getText().toString(), true);
                    return;
                }
                EditorPassword.this.warning.setText((CharSequence)string6);
                EditorPassword.this.warning.setVisibility(0);
            }
        });
    }

    public EditorPassword(Context context, View view, String string2, String string3, final String string4, final String string5, final String string6, final String string7, final String string8, final String string9, final String string10, String string11, String string12) {
        this.common_init(context, view, string2, string11, string12);
        if (string6 == null) {
            this.row_name.setVisibility(8);
            this.buttonConfirm.setTag((Object)3);
        } else {
            this.label_name.setText((CharSequence)string6);
            this.buttonConfirm.setTag((Object)4);
        }
        this.label_field1.setText((CharSequence)string3);
        this.row_field2.setVisibility(8);
        this.buttonConfirm.setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                if ((Integer)object.getTag() == 3) {
                    if (EditorPassword.this.field1.getText().toString().equals(string4)) {
                        EditorPassword.this.row_field2.setVisibility(0);
                        EditorPassword.this.label_field1.setText((CharSequence)string7);
                        EditorPassword.this.label_field2.setText((CharSequence)string8);
                        EditorPassword.this.field1.setText(null);
                        EditorPassword.this.field2.setText(null);
                        EditorPassword.this.buttonConfirm.setTag((Object)1);
                        return;
                    }
                    EditorPassword.this.warning.setText((CharSequence)string9);
                    EditorPassword.this.warning.setVisibility(0);
                    return;
                }
                if ((Integer)object.getTag() == 1) {
                    if (EditorPassword.this.field1.getText().toString().length() == 0) return;
                    if (EditorPassword.this.field2.getText().toString().length() == 0) {
                        return;
                    }
                    if (EditorPassword.this.field1.getText().toString().equals(EditorPassword.this.field2.getText().toString())) {
                        EditorPassword.this.flag_cancel_on_dismiss = false;
                        EditorPassword.this.popup.dismiss();
                        object = EditorPassword.this;
                        ((EditorPassword)object).OnConfirmed(((EditorPassword)object).field1.getText().toString());
                        return;
                    }
                    EditorPassword.this.warning.setText((CharSequence)string10);
                    EditorPassword.this.warning.setVisibility(0);
                    return;
                }
                if ((Integer)object.getTag() == 4) {
                    if (EditorPassword.this.field1.getText().toString().equals(string4)) {
                        EditorPassword.this.row_name.setVisibility(0);
                        EditorPassword.this.row_field2.setVisibility(0);
                        EditorPassword.this.label_name.setText((CharSequence)string6);
                        EditorPassword.this.label_field1.setText((CharSequence)string7);
                        EditorPassword.this.label_field2.setText((CharSequence)string8);
                        EditorPassword.this.field_name.setText((CharSequence)string5);
                        EditorPassword.this.field1.setText((CharSequence)string4);
                        EditorPassword.this.field2.setText((CharSequence)string4);
                        EditorPassword.this.buttonConfirm.setTag((Object)2);
                        return;
                    }
                    EditorPassword.this.warning.setText((CharSequence)string9);
                    EditorPassword.this.warning.setVisibility(0);
                    return;
                }
                if ((Integer)object.getTag() != 2) return;
                if (EditorPassword.this.field1.getText().toString().length() == 0) return;
                if (EditorPassword.this.field2.getText().toString().length() == 0) {
                    return;
                }
                if ((Integer)object.getTag() == 2 && EditorPassword.this.field_name.getText().toString().length() == 0) {
                    return;
                }
                if (EditorPassword.this.field1.getText().toString().equals(EditorPassword.this.field2.getText().toString())) {
                    EditorPassword.this.flag_cancel_on_dismiss = false;
                    EditorPassword.this.popup.dismiss();
                    object = EditorPassword.this;
                    ((EditorPassword)object).OnConfirmed(((EditorPassword)object).field1.getText().toString());
                    return;
                }
                EditorPassword.this.warning.setText((CharSequence)string10);
                EditorPassword.this.warning.setVisibility(0);
            }
        });
    }

    public void OnCancelled() {
    }

    public void OnConfirmed(String string2) {
    }

    public void OnConfirmed(String string2, String string3) {
    }

    public void OnConfirmed(String string2, String string3, boolean bl) {
    }

    void common_init(Context context, View view, String string2, String string3, String string4) {
        context = LayoutInflater.from((Context)context).inflate(R.layout.editor_password, null);
        this.popup = this.prepare_popup(view, (View)context);
        this.base_view = (RelativeLayout)context.findViewById(R.id.base_view);
        this.external_view_place_holder = (LinearLayout)context.findViewById(R.id.external_view_place_holder);
        this.row_name = (RelativeLayout)context.findViewById(R.id.row_name);
        this.row_field1 = (RelativeLayout)context.findViewById(R.id.row_password);
        this.row_field2 = (RelativeLayout)context.findViewById(R.id.row_password_again);
        this.dialog_title = (TextView)context.findViewById(R.id.title);
        this.warning = (TextView)context.findViewById(R.id.warning);
        this.label_name = (TextView)context.findViewById(R.id.hint_name);
        this.label_field1 = (TextView)context.findViewById(R.id.hint_pasword);
        this.label_field2 = (TextView)context.findViewById(R.id.label_password_again);
        this.field_name = (EditText)context.findViewById(R.id.field_name);
        this.field1 = (EditText)context.findViewById(R.id.password);
        this.field2 = (EditText)context.findViewById(R.id.password_again);
        this.checkbox = (CheckBox)context.findViewById(R.id.checkbox);
        this.buttonCancel = (Button)context.findViewById(R.id.button_cancel);
        this.buttonConfirm = (Button)context.findViewById(R.id.button_confirm);
        this.iconCancel = (ImageView)context.findViewById(R.id.icon_cancel);
        this.dialog_title.setText((CharSequence)string2);
        this.field_name.setText((CharSequence)"");
        this.field1.setText((CharSequence)"");
        this.field2.setText((CharSequence)"");
        this.checkbox.setVisibility(8);
        this.warning.setVisibility(4);
        this.buttonCancel.setText((CharSequence)string4);
        this.buttonConfirm.setText((CharSequence)string3);
        this.field_name.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                EditorPassword.this.warning.setVisibility(4);
                if (EditorPassword.this.field_name.getText().toString().length() > 0) {
                    EditorPassword.this.label_name.setVisibility(4);
                    return;
                }
                EditorPassword.this.label_name.setVisibility(0);
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.field1.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                EditorPassword.this.warning.setVisibility(4);
                if (EditorPassword.this.field1.getText().toString().length() > 0) {
                    EditorPassword.this.label_field1.setVisibility(4);
                    return;
                }
                EditorPassword.this.label_field1.setVisibility(0);
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.field2.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                EditorPassword.this.warning.setVisibility(4);
                if (EditorPassword.this.field2.getText().toString().length() > 0) {
                    EditorPassword.this.label_field2.setVisibility(4);
                    return;
                }
                EditorPassword.this.label_field2.setVisibility(0);
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.buttonCancel.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditorPassword.this.OnCancelled();
            }
        });
        this.iconCancel.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                EditorPassword.this.OnCancelled();
            }
        });
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

    public void setCheckbox(boolean bl) {
        this.checkbox.setChecked(bl);
    }

    public void setColors(int n, int n2, int n3, int n4) {
        this.dialog_title.setTextColor(n);
        this.label_name.setTextColor(n2);
        this.label_field1.setTextColor(n2);
        this.label_field2.setTextColor(n2);
        this.field_name.setTextColor(n3);
        this.field1.setTextColor(n3);
        this.field2.setTextColor(n3);
        this.buttonCancel.setTextColor(n4);
        this.buttonConfirm.setTextColor(n4);
    }

    public void setOutsideTouchable(boolean bl) {
        this.is_outside_touchable = bl;
    }

    public void setSizesPx(float f, float f2, float f3, float f4) {
        if (f > 0.0f) {
            this.dialog_title.setTextSize(0, f);
        }
        if (f2 > 0.0f) {
            this.label_name.setTextSize(0, f2);
            this.label_field1.setTextSize(0, f2);
            this.label_field2.setTextSize(0, f2);
            this.checkbox.setTextSize(0, f2);
        }
        if (f3 > 0.0f) {
            this.field_name.setTextSize(0, f3);
            this.field1.setTextSize(0, f3);
            this.field2.setTextSize(0, f3);
        }
        if (!(f4 > 0.0f)) return;
        this.buttonCancel.setTextSize(0, f4);
        this.buttonConfirm.setTextSize(0, f4);
    }

    public void stop() {
        PopupWindow popupWindow = this.popup;
        if (popupWindow == null) return;
        popupWindow.dismiss();
    }

    public class EditorPasswordInfo {
        View ancher;
        Context context;
        String label_field1;
        String label_field2;
        String label_name;
        int mode;
        String text_cancel;
        String text_checkbox;
        String text_confirm;
        String title;
        String warning_fields_empty;
        String warning_password_wrong;
        String warning_passwords_different;
    }

}


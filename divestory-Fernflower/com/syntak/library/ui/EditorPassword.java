package com.syntak.library.ui;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.PopupWindow.OnDismissListener;
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

   public EditorPassword(Context var1, View var2, String var3, String var4, String var5, String var6, final String var7, String var8, String var9) {
      this.common_init(var1, var2, var3, var8, var9);
      if (var4 == null) {
         this.row_name.setVisibility(8);
         this.buttonConfirm.setTag(1);
      } else {
         this.label_name.setText(var4);
         this.buttonConfirm.setTag(2);
      }

      this.label_field1.setText(var5);
      this.label_field2.setText(var6);
      this.buttonConfirm.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if (EditorPassword.this.field1.getText().toString().length() != 0 && EditorPassword.this.field2.getText().toString().length() != 0) {
               if ((Integer)var1.getTag() == 2 && EditorPassword.this.field_name.getText().toString().length() == 0) {
                  return;
               }

               if (EditorPassword.this.field1.getText().toString().equals(EditorPassword.this.field2.getText().toString())) {
                  EditorPassword.this.flag_cancel_on_dismiss = false;
                  EditorPassword.this.popup.dismiss();
                  EditorPassword var2 = EditorPassword.this;
                  var2.OnConfirmed(var2.field1.getText().toString());
               } else {
                  EditorPassword.this.warning.setText(var7);
                  EditorPassword.this.warning.setVisibility(0);
               }
            }

         }
      });
   }

   public EditorPassword(Context var1, View var2, String var3, String var4, String var5, final String var6, final String var7, final String var8, String var9, String var10) {
      this.common_init(var1, var2, var3, var9, var10);
      if (var4 == null) {
         this.row_name.setVisibility(8);
         this.buttonConfirm.setTag(5);
      } else {
         this.label_name.setText(var4);
         this.buttonConfirm.setTag(6);
      }

      this.label_field1.setText(var5);
      this.row_field2.setVisibility(8);
      this.buttonConfirm.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            EditorPassword var2;
            if (5 == (Integer)var1.getTag()) {
               if (EditorPassword.this.field1.getText().toString().equals(var7)) {
                  EditorPassword.this.flag_cancel_on_dismiss = false;
                  EditorPassword.this.popup.dismiss();
                  var2 = EditorPassword.this;
                  var2.OnConfirmed(var2.field1.getText().toString());
               } else {
                  EditorPassword.this.warning.setText(var8);
                  EditorPassword.this.warning.setVisibility(0);
               }
            } else if (EditorPassword.this.field_name.getText().toString().equals(var6) && EditorPassword.this.field1.getText().toString().equals(var7)) {
               EditorPassword.this.flag_cancel_on_dismiss = false;
               EditorPassword.this.popup.dismiss();
               var2 = EditorPassword.this;
               var2.OnConfirmed(var2.field_name.getText().toString());
            } else {
               EditorPassword.this.warning.setText(var8);
               EditorPassword.this.warning.setVisibility(0);
            }

         }
      });
   }

   public EditorPassword(Context var1, View var2, String var3, String var4, String var5, String var6, final String var7, String var8, String var9, String var10, String var11) {
      this.common_init(var1, var2, var3, var9, var10);
      this.buttonConfirm.setTag(7);
      this.label_name.setText(var4);
      this.label_field1.setText(var5);
      this.label_field2.setText(var6);
      this.checkbox.setText(var8);
      this.checkbox.setVisibility(0);
      this.row_field2.setVisibility(8);
      this.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            if (var2) {
               EditorPassword.this.row_field2.setVisibility(0);
               EditorPassword.this.mode = 2;
            } else {
               EditorPassword.this.row_field2.setVisibility(8);
               EditorPassword.this.mode = 7;
            }

            EditorPassword.this.buttonConfirm.setTag(EditorPassword.this.mode);
         }
      });
      this.buttonConfirm.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            int var2 = (Integer)var1.getTag();
            if (EditorPassword.this.field_name.getText().toString().length() != 0 && EditorPassword.this.field1.getText().toString().length() != 0) {
               if (var2 == 2 && EditorPassword.this.field2.getText().toString().length() == 0) {
                  return;
               }

               EditorPassword var3;
               if (var2 == 7) {
                  var3 = EditorPassword.this;
                  var3.OnConfirmed(var3.field_name.getText().toString(), EditorPassword.this.field1.getText().toString(), false);
               } else if (EditorPassword.this.field1.getText().toString().equals(EditorPassword.this.field2.getText().toString())) {
                  EditorPassword.this.flag_cancel_on_dismiss = false;
                  var3 = EditorPassword.this;
                  var3.OnConfirmed(var3.field_name.getText().toString(), EditorPassword.this.field1.getText().toString(), true);
               } else {
                  EditorPassword.this.warning.setText(var7);
                  EditorPassword.this.warning.setVisibility(0);
               }
            }

         }
      });
   }

   public EditorPassword(Context var1, View var2, String var3, String var4, final String var5, final String var6, final String var7, final String var8, final String var9, final String var10, final String var11, String var12, String var13) {
      this.common_init(var1, var2, var3, var12, var13);
      if (var7 == null) {
         this.row_name.setVisibility(8);
         this.buttonConfirm.setTag(3);
      } else {
         this.label_name.setText(var7);
         this.buttonConfirm.setTag(4);
      }

      this.label_field1.setText(var4);
      this.row_field2.setVisibility(8);
      this.buttonConfirm.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            if ((Integer)var1.getTag() == 3) {
               if (EditorPassword.this.field1.getText().toString().equals(var5)) {
                  EditorPassword.this.row_field2.setVisibility(0);
                  EditorPassword.this.label_field1.setText(var8);
                  EditorPassword.this.label_field2.setText(var9);
                  EditorPassword.this.field1.setText((CharSequence)null);
                  EditorPassword.this.field2.setText((CharSequence)null);
                  EditorPassword.this.buttonConfirm.setTag(1);
               } else {
                  EditorPassword.this.warning.setText(var10);
                  EditorPassword.this.warning.setVisibility(0);
               }
            } else {
               EditorPassword var2;
               if ((Integer)var1.getTag() == 1) {
                  if (EditorPassword.this.field1.getText().toString().length() == 0 || EditorPassword.this.field2.getText().toString().length() == 0) {
                     return;
                  }

                  if (EditorPassword.this.field1.getText().toString().equals(EditorPassword.this.field2.getText().toString())) {
                     EditorPassword.this.flag_cancel_on_dismiss = false;
                     EditorPassword.this.popup.dismiss();
                     var2 = EditorPassword.this;
                     var2.OnConfirmed(var2.field1.getText().toString());
                  } else {
                     EditorPassword.this.warning.setText(var11);
                     EditorPassword.this.warning.setVisibility(0);
                  }
               } else if ((Integer)var1.getTag() == 4) {
                  if (EditorPassword.this.field1.getText().toString().equals(var5)) {
                     EditorPassword.this.row_name.setVisibility(0);
                     EditorPassword.this.row_field2.setVisibility(0);
                     EditorPassword.this.label_name.setText(var7);
                     EditorPassword.this.label_field1.setText(var8);
                     EditorPassword.this.label_field2.setText(var9);
                     EditorPassword.this.field_name.setText(var6);
                     EditorPassword.this.field1.setText(var5);
                     EditorPassword.this.field2.setText(var5);
                     EditorPassword.this.buttonConfirm.setTag(2);
                  } else {
                     EditorPassword.this.warning.setText(var10);
                     EditorPassword.this.warning.setVisibility(0);
                  }
               } else if ((Integer)var1.getTag() == 2 && EditorPassword.this.field1.getText().toString().length() != 0 && EditorPassword.this.field2.getText().toString().length() != 0) {
                  if ((Integer)var1.getTag() == 2 && EditorPassword.this.field_name.getText().toString().length() == 0) {
                     return;
                  }

                  if (EditorPassword.this.field1.getText().toString().equals(EditorPassword.this.field2.getText().toString())) {
                     EditorPassword.this.flag_cancel_on_dismiss = false;
                     EditorPassword.this.popup.dismiss();
                     var2 = EditorPassword.this;
                     var2.OnConfirmed(var2.field1.getText().toString());
                  } else {
                     EditorPassword.this.warning.setText(var11);
                     EditorPassword.this.warning.setVisibility(0);
                  }
               }
            }

         }
      });
   }

   public void OnCancelled() {
   }

   public void OnConfirmed(String var1) {
   }

   public void OnConfirmed(String var1, String var2) {
   }

   public void OnConfirmed(String var1, String var2, boolean var3) {
   }

   void common_init(Context var1, View var2, String var3, String var4, String var5) {
      View var6 = LayoutInflater.from(var1).inflate(R.layout.editor_password, (ViewGroup)null);
      this.popup = this.prepare_popup(var2, var6);
      this.base_view = (RelativeLayout)var6.findViewById(R.id.base_view);
      this.external_view_place_holder = (LinearLayout)var6.findViewById(R.id.external_view_place_holder);
      this.row_name = (RelativeLayout)var6.findViewById(R.id.row_name);
      this.row_field1 = (RelativeLayout)var6.findViewById(R.id.row_password);
      this.row_field2 = (RelativeLayout)var6.findViewById(R.id.row_password_again);
      this.dialog_title = (TextView)var6.findViewById(R.id.title);
      this.warning = (TextView)var6.findViewById(R.id.warning);
      this.label_name = (TextView)var6.findViewById(R.id.hint_name);
      this.label_field1 = (TextView)var6.findViewById(R.id.hint_pasword);
      this.label_field2 = (TextView)var6.findViewById(R.id.label_password_again);
      this.field_name = (EditText)var6.findViewById(R.id.field_name);
      this.field1 = (EditText)var6.findViewById(R.id.password);
      this.field2 = (EditText)var6.findViewById(R.id.password_again);
      this.checkbox = (CheckBox)var6.findViewById(R.id.checkbox);
      this.buttonCancel = (Button)var6.findViewById(R.id.button_cancel);
      this.buttonConfirm = (Button)var6.findViewById(R.id.button_confirm);
      this.iconCancel = (ImageView)var6.findViewById(R.id.icon_cancel);
      this.dialog_title.setText(var3);
      this.field_name.setText("");
      this.field1.setText("");
      this.field2.setText("");
      this.checkbox.setVisibility(8);
      this.warning.setVisibility(4);
      this.buttonCancel.setText(var5);
      this.buttonConfirm.setText(var4);
      this.field_name.addTextChangedListener(new TextWatcher() {
         public void afterTextChanged(Editable var1) {
            EditorPassword.this.warning.setVisibility(4);
            if (EditorPassword.this.field_name.getText().toString().length() > 0) {
               EditorPassword.this.label_name.setVisibility(4);
            } else {
               EditorPassword.this.label_name.setVisibility(0);
            }

         }

         public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
         }

         public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
         }
      });
      this.field1.addTextChangedListener(new TextWatcher() {
         public void afterTextChanged(Editable var1) {
            EditorPassword.this.warning.setVisibility(4);
            if (EditorPassword.this.field1.getText().toString().length() > 0) {
               EditorPassword.this.label_field1.setVisibility(4);
            } else {
               EditorPassword.this.label_field1.setVisibility(0);
            }

         }

         public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
         }

         public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
         }
      });
      this.field2.addTextChangedListener(new TextWatcher() {
         public void afterTextChanged(Editable var1) {
            EditorPassword.this.warning.setVisibility(4);
            if (EditorPassword.this.field2.getText().toString().length() > 0) {
               EditorPassword.this.label_field2.setVisibility(4);
            } else {
               EditorPassword.this.label_field2.setVisibility(0);
            }

         }

         public void beforeTextChanged(CharSequence var1, int var2, int var3, int var4) {
         }

         public void onTextChanged(CharSequence var1, int var2, int var3, int var4) {
         }
      });
      this.buttonCancel.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            EditorPassword.this.OnCancelled();
         }
      });
      this.iconCancel.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            EditorPassword.this.OnCancelled();
         }
      });
   }

   public void includeView(View var1) {
      this.external_view_place_holder.addView(var1);
      this.base_view.invalidate();
   }

   PopupWindow prepare_popup(View var1, View var2) {
      PopupWindow var3 = new PopupWindow(var2, -2, -2);
      this.popup = var3;
      var3.setTouchable(true);
      this.popup.setFocusable(true);
      this.popup.setBackgroundDrawable((Drawable)null);
      this.popup.setOutsideTouchable(this.is_outside_touchable);
      this.popup.update();
      this.popup.showAtLocation(var1, 17, 0, 0);
      this.popup.setOnDismissListener(new OnDismissListener() {
         public void onDismiss() {
         }
      });
      return this.popup;
   }

   public void setCheckbox(boolean var1) {
      this.checkbox.setChecked(var1);
   }

   public void setColors(int var1, int var2, int var3, int var4) {
      this.dialog_title.setTextColor(var1);
      this.label_name.setTextColor(var2);
      this.label_field1.setTextColor(var2);
      this.label_field2.setTextColor(var2);
      this.field_name.setTextColor(var3);
      this.field1.setTextColor(var3);
      this.field2.setTextColor(var3);
      this.buttonCancel.setTextColor(var4);
      this.buttonConfirm.setTextColor(var4);
   }

   public void setOutsideTouchable(boolean var1) {
      this.is_outside_touchable = var1;
   }

   public void setSizesPx(float var1, float var2, float var3, float var4) {
      if (var1 > 0.0F) {
         this.dialog_title.setTextSize(0, var1);
      }

      if (var2 > 0.0F) {
         this.label_name.setTextSize(0, var2);
         this.label_field1.setTextSize(0, var2);
         this.label_field2.setTextSize(0, var2);
         this.checkbox.setTextSize(0, var2);
      }

      if (var3 > 0.0F) {
         this.field_name.setTextSize(0, var3);
         this.field1.setTextSize(0, var3);
         this.field2.setTextSize(0, var3);
      }

      if (var4 > 0.0F) {
         this.buttonCancel.setTextSize(0, var4);
         this.buttonConfirm.setTextSize(0, var4);
      }

   }

   public void stop() {
      PopupWindow var1 = this.popup;
      if (var1 != null) {
         var1.dismiss();
      }

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

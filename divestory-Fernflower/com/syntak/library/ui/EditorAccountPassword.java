package com.syntak.library.ui;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.text.method.KeyListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
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

   public EditorAccountPassword(EditorAccountPassword.Info var1) {
      this.mode = var1.mode;
      this.context = var1.context;
      this.ancher = var1.ancher;
      this.title = var1.title;
      this.text_hint_account = var1.text_hint_account;
      this.text_hint_password = var1.text_hint_password;
      this.text_hint_password_again = var1.text_hint_password_again;
      this.text_hint_password_old = var1.text_hint_password_old;
      this.text_hint_password_new = var1.text_hint_password_new;
      this.text_hint_password_new_again = var1.text_hint_password_new_again;
      this.text_hint_account_new = var1.text_hint_account_new;
      this.text_checkbox = var1.text_checkbox;
      this.text_confirm = var1.text_confirm;
      this.text_cancel = var1.text_cancel;
      this.warning_passwords_different = var1.warning_passwords_different;
      this.warning_fields_empty = var1.warning_fields_empty;
      this.warning_password_wrong = var1.warning_password_wrong;
      this.warning_account_password_wrong = var1.warning_account_password_wrong;
      this.account = var1.account;
      this.password = var1.password;
      View var2 = LayoutInflater.from(this.context).inflate(R.layout.editor_account_password, (ViewGroup)null);
      this.popup = this.prepare_popup(this.ancher, var2);
      this.base_view = (RelativeLayout)var2.findViewById(R.id.base_view);
      this.external_view_place_holder = (LinearLayout)var2.findViewById(R.id.external_view_place_holder);
      this.icon_cancel = (ImageView)var2.findViewById(R.id.icon_cancel);
      this.warning = (TextView)var2.findViewById(R.id.warning);
      this.dialog_title = (TextView)var2.findViewById(R.id.title);
      this.field_account = (EditText)var2.findViewById(R.id.field_name);
      this.field_password = (EditText)var2.findViewById(R.id.password);
      this.field_password_again = (EditText)var2.findViewById(R.id.password_again);
      this.checkbox = (CheckBox)var2.findViewById(R.id.checkbox);
      this.button_cancel = (Button)var2.findViewById(R.id.button_cancel);
      this.button_confirm = (Button)var2.findViewById(R.id.button_confirm);
      this.dialog_title.setText(this.title);
      this.field_account.setHint(this.text_hint_account);
      this.field_password.setHint(this.text_hint_password);
      this.field_password_again.setHint(this.text_hint_password_again);
      this.field_account.setOnFocusChangeListener(new OnFocusChangeListener() {
         public void onFocusChange(View var1, boolean var2) {
            EditorAccountPassword.this.warning.setText("");
         }
      });
      this.field_password.setOnFocusChangeListener(new OnFocusChangeListener() {
         public void onFocusChange(View var1, boolean var2) {
            EditorAccountPassword.this.warning.setText("");
         }
      });
      this.field_password_again.setOnFocusChangeListener(new OnFocusChangeListener() {
         public void onFocusChange(View var1, boolean var2) {
            EditorAccountPassword.this.warning.setText("");
         }
      });
      this.field_account.setText("");
      this.field_password.setText("");
      this.field_password_again.setText("");
      this.checkbox.setText(this.text_checkbox);
      this.button_cancel.setText(this.text_cancel);
      this.button_confirm.setText(this.text_confirm);
      this.button_cancel.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            EditorAccountPassword.this.popup.dismiss();
            EditorAccountPassword.this.OnCancelled();
         }
      });
      this.icon_cancel.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            EditorAccountPassword.this.popup.dismiss();
            EditorAccountPassword.this.OnCancelled();
         }
      });
      this.button_confirm.setOnClickListener(new OnClickListener() {
         public void onClick(View var1) {
            EditorAccountPassword.this.field_account.clearFocus();
            EditorAccountPassword.this.field_password.clearFocus();
            EditorAccountPassword.this.field_password_again.clearFocus();
            String var2 = EditorAccountPassword.this.field_account.getText().toString();
            String var6 = EditorAccountPassword.this.field_password.getText().toString();
            String var3 = EditorAccountPassword.this.field_password_again.getText().toString();
            boolean var4 = EditorAccountPassword.this.checkbox.isChecked();
            switch(EditorAccountPassword.this.mode) {
            case 1:
               if (var6.length() != 0 && var3.length() != 0) {
                  if (!var6.equals(var3)) {
                     EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_passwords_different);
                  } else {
                     EditorAccountPassword.this.popup.dismiss();
                     EditorAccountPassword.this.OnConfirmed(var6);
                  }
               } else {
                  EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_fields_empty);
               }
               break;
            case 2:
               if (var2.length() != 0 && var6.length() != 0 && var3.length() != 0) {
                  if (!var6.equals(var3)) {
                     EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_passwords_different);
                  } else {
                     EditorAccountPassword.this.popup.dismiss();
                     EditorAccountPassword.this.OnConfirmed(var2, var6, var4);
                  }
               } else {
                  EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_fields_empty);
               }
               break;
            case 3:
               var2 = EditorAccountPassword.this.field_account.getText().toString();
               String var5 = EditorAccountPassword.this.field_password.getText().toString();
               var3 = EditorAccountPassword.this.field_password_again.getText().toString();
               if (var2.length() != 0 && var5.length() != 0 && var3.length() != 0) {
                  if (!var6.equals(var2)) {
                     EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_password_wrong);
                  } else if (!var5.equals(var3)) {
                     EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_passwords_different);
                  } else {
                     EditorAccountPassword.this.popup.dismiss();
                     EditorAccountPassword.this.OnConfirmed(var5);
                  }
               } else {
                  EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_fields_empty);
               }
               break;
            case 4:
               var3 = EditorAccountPassword.this.field_password.getText().toString();
               var2 = EditorAccountPassword.this.field_password_again.getText().toString();
               if (var2.length() != 0 && var3.length() != 0) {
                  if (!var6.equals(var2)) {
                     EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_password_wrong);
                  } else {
                     EditorAccountPassword.this.popup.dismiss();
                     EditorAccountPassword.this.OnConfirmed(var3);
                  }
               } else {
                  EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_fields_empty);
               }
               break;
            case 5:
               if (var6.length() == 0) {
                  EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_fields_empty);
               } else if (!var6.equals(EditorAccountPassword.this.password)) {
                  EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_password_wrong);
               } else {
                  EditorAccountPassword.this.popup.dismiss();
                  EditorAccountPassword.this.OnConfirmed(var6);
               }
               break;
            case 6:
               if (var2.length() != 0 && var6.length() != 0) {
                  if (var2.equals(EditorAccountPassword.this.account) && var6.equals(EditorAccountPassword.this.password)) {
                     EditorAccountPassword.this.popup.dismiss();
                     EditorAccountPassword.this.OnConfirmed(var2, var6);
                  } else {
                     EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_account_password_wrong);
                  }
               } else {
                  EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_fields_empty);
               }
               break;
            case 7:
               if (var6.length() == 0) {
                  EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_fields_empty);
               } else {
                  EditorAccountPassword.this.popup.dismiss();
                  EditorAccountPassword.this.OnConfirmed(var6);
               }
               break;
            case 8:
               if (var2.length() != 0 && var6.length() != 0) {
                  EditorAccountPassword.this.popup.dismiss();
                  EditorAccountPassword.this.OnConfirmed(var2, var6, var4);
               } else {
                  EditorAccountPassword.this.warning.setText(EditorAccountPassword.this.warning_fields_empty);
               }
            }

         }
      });
      this.checkbox.setOnCheckedChangeListener(new OnCheckedChangeListener() {
         public void onCheckedChanged(CompoundButton var1, boolean var2) {
            if (var2) {
               EditorAccountPassword.this.mode = 2;
               EditorAccountPassword.this.field_password_again.setVisibility(0);
            } else {
               EditorAccountPassword.this.mode = 8;
               EditorAccountPassword.this.field_password_again.setVisibility(8);
            }

            EditorAccountPassword.this.OnCheckChanged(var2);
         }
      });
      this.warning.setVisibility(0);
      this.checkbox.setVisibility(4);
      switch(this.mode) {
      case 1:
         this.field_account.setVisibility(8);
         this.field_password.setVisibility(0);
         this.field_password_again.setVisibility(0);
         this.checkbox.setVisibility(8);
         break;
      case 2:
         this.field_account.setVisibility(0);
         this.field_password.setVisibility(0);
         this.field_password_again.setVisibility(0);
         this.checkbox.setVisibility(0);
         break;
      case 3:
         this.field_account.setHint(this.text_hint_password_old);
         this.field_password.setHint(this.text_hint_password_new);
         this.field_password_again.setHint(this.text_hint_password_new_again);
         this.checkbox.setVisibility(8);
         break;
      case 4:
         this.field_account.setText(this.account);
         this.field_account.setKeyListener((KeyListener)null);
         this.field_password.setHint(this.text_hint_account_new);
         this.field_password_again.setHint(this.text_hint_password);
         this.checkbox.setVisibility(8);
         break;
      case 5:
         this.field_account.setVisibility(8);
         this.field_password.setVisibility(0);
         this.field_password_again.setVisibility(8);
         this.checkbox.setVisibility(8);
         break;
      case 6:
         this.field_account.setVisibility(0);
         this.field_password.setVisibility(0);
         this.field_password_again.setVisibility(8);
         this.checkbox.setVisibility(8);
         break;
      case 7:
         this.field_account.setVisibility(8);
         this.field_password.setVisibility(0);
         this.field_password_again.setVisibility(8);
         this.checkbox.setVisibility(8);
         break;
      case 8:
         this.field_account.setVisibility(0);
         this.field_password.setVisibility(0);
         this.field_password_again.setVisibility(8);
         this.checkbox.setVisibility(0);
      }

   }

   public static StateListDrawable makeButtonBackgroundSelector(int var0, int var1) {
      StateListDrawable var2 = new StateListDrawable();
      var2.setExitFadeDuration(300);
      ColorDrawable var3 = new ColorDrawable(var1);
      var2.addState(new int[]{16842919}, var3);
      var3 = new ColorDrawable(var0);
      var2.addState(new int[0], var3);
      return var2;
   }

   public void OnCancelled() {
   }

   public void OnCheckChanged(boolean var1) {
   }

   public void OnConfirmed(String var1) {
   }

   public void OnConfirmed(String var1, String var2) {
   }

   public void OnConfirmed(String var1, String var2, boolean var3) {
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

   public void setButtonColors(int var1, int var2, int var3) {
      this.button_cancel.setTextColor(var1);
      this.button_confirm.setTextColor(var1);
      this.button_cancel.setBackground(makeButtonBackgroundSelector(var2, var3));
      this.button_confirm.setBackground(makeButtonBackgroundSelector(var2, var3));
   }

   public void setCheckbox(boolean var1) {
      this.checkbox.setChecked(var1);
   }

   public void setEditTextColor(int var1) {
      this.field_account.setTextColor(var1);
      this.field_password.setTextColor(var1);
      this.field_password_again.setTextColor(var1);
   }

   public void setOutsideTouchable(boolean var1) {
      this.is_outside_touchable = var1;
   }

   public void setSizesPx(float var1, float var2, float var3, float var4) {
      if (var1 > 0.0F) {
         this.dialog_title.setTextSize(0, var1);
      }

      if (var2 > 0.0F) {
         this.checkbox.setTextSize(0, var2);
      }

      if (var3 > 0.0F) {
         this.field_account.setTextSize(0, var3);
         this.field_password.setTextSize(0, var3);
         this.field_password_again.setTextSize(0, var3);
      }

      if (var4 > 0.0F) {
         this.button_cancel.setTextSize(0, var4);
         this.button_confirm.setTextSize(0, var4);
      }

   }

   public void setTitleColor(int var1) {
      this.dialog_title.setTextColor(var1);
   }

   public void stop() {
      PopupWindow var1 = this.popup;
      if (var1 != null) {
         var1.dismiss();
      }

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

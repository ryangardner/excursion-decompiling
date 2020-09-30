package com.crest.divestory.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

public class ContactsFragment extends Fragment {
   private ContactsViewModel contactsViewModel;

   public void onCreate(Bundle var1) {
      super.onCreate(var1);
   }

   public View onCreateView(LayoutInflater var1, ViewGroup var2, Bundle var3) {
      this.contactsViewModel = (ContactsViewModel)ViewModelProviders.of((Fragment)this).get(ContactsViewModel.class);
      View var4 = var1.inflate(2131427391, var2, false);
      TextView var5 = (TextView)var4.findViewById(2131231389);
      this.contactsViewModel.getText().observe(this.getViewLifecycleOwner(), new Observer<String>() {
         public void onChanged(String var1) {
         }
      });
      return var4;
   }
}

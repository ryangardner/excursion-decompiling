package com.google.android.material.datepicker;

import android.os.Parcel;
import android.os.Parcelable.Creator;
import androidx.core.util.Preconditions;
import java.util.Iterator;
import java.util.List;

public final class CompositeDateValidator implements CalendarConstraints.DateValidator {
   public static final Creator<CompositeDateValidator> CREATOR = new Creator<CompositeDateValidator>() {
      public CompositeDateValidator createFromParcel(Parcel var1) {
         return new CompositeDateValidator((List)Preconditions.checkNotNull(var1.readArrayList(CalendarConstraints.DateValidator.class.getClassLoader())));
      }

      public CompositeDateValidator[] newArray(int var1) {
         return new CompositeDateValidator[var1];
      }
   };
   private final List<CalendarConstraints.DateValidator> validators;

   private CompositeDateValidator(List<CalendarConstraints.DateValidator> var1) {
      this.validators = var1;
   }

   // $FF: synthetic method
   CompositeDateValidator(List var1, Object var2) {
      this(var1);
   }

   public static CalendarConstraints.DateValidator allOf(List<CalendarConstraints.DateValidator> var0) {
      return new CompositeDateValidator(var0);
   }

   public int describeContents() {
      return 0;
   }

   public boolean equals(Object var1) {
      if (this == var1) {
         return true;
      } else if (!(var1 instanceof CompositeDateValidator)) {
         return false;
      } else {
         CompositeDateValidator var2 = (CompositeDateValidator)var1;
         return this.validators.equals(var2.validators);
      }
   }

   public int hashCode() {
      return this.validators.hashCode();
   }

   public boolean isValid(long var1) {
      Iterator var3 = this.validators.iterator();

      CalendarConstraints.DateValidator var4;
      do {
         if (!var3.hasNext()) {
            return true;
         }

         var4 = (CalendarConstraints.DateValidator)var3.next();
      } while(var4 == null || var4.isValid(var1));

      return false;
   }

   public void writeToParcel(Parcel var1, int var2) {
      var1.writeList(this.validators);
   }
}

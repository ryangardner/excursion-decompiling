package kotlin.contracts;

import kotlin.Function;
import kotlin.Metadata;

@Metadata(
   bv = {1, 0, 3},
   d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\bg\u0018\u00002\u00020\u0001J&\u0010\u0002\u001a\u00020\u0003\"\u0004\b\u0000\u0010\u00042\f\u0010\u0005\u001a\b\u0012\u0004\u0012\u0002H\u00040\u00062\b\b\u0002\u0010\u0007\u001a\u00020\bH'J\b\u0010\t\u001a\u00020\nH'J\u0012\u0010\t\u001a\u00020\n2\b\u0010\u000b\u001a\u0004\u0018\u00010\u0001H'J\b\u0010\f\u001a\u00020\rH'¨\u0006\u000e"},
   d2 = {"Lkotlin/contracts/ContractBuilder;", "", "callsInPlace", "Lkotlin/contracts/CallsInPlace;", "R", "lambda", "Lkotlin/Function;", "kind", "Lkotlin/contracts/InvocationKind;", "returns", "Lkotlin/contracts/Returns;", "value", "returnsNotNull", "Lkotlin/contracts/ReturnsNotNull;", "kotlin-stdlib"},
   k = 1,
   mv = {1, 1, 16}
)
public interface ContractBuilder {
   <R> CallsInPlace callsInPlace(Function<? extends R> var1, InvocationKind var2);

   Returns returns();

   Returns returns(Object var1);

   ReturnsNotNull returnsNotNull();

   @Metadata(
      bv = {1, 0, 3},
      k = 3,
      mv = {1, 1, 16}
   )
   public static final class DefaultImpls {
      // $FF: synthetic method
      public static CallsInPlace callsInPlace$default(ContractBuilder var0, Function var1, InvocationKind var2, int var3, Object var4) {
         if (var4 == null) {
            if ((var3 & 2) != 0) {
               var2 = InvocationKind.UNKNOWN;
            }

            return var0.callsInPlace(var1, var2);
         } else {
            throw new UnsupportedOperationException("Super calls with default arguments not supported in this target, function: callsInPlace");
         }
      }
   }
}

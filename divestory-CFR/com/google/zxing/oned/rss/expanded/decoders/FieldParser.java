/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.oned.rss.expanded.decoders;

import com.google.zxing.NotFoundException;

final class FieldParser {
    private static final Object[][] FOUR_DIGIT_DATA_LENGTH;
    private static final Object[][] THREE_DIGIT_DATA_LENGTH;
    private static final Object[][] THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH;
    private static final Object[][] TWO_DIGIT_DATA_LENGTH;
    private static final Object VARIABLE_LENGTH;

    static {
        VARIABLE_LENGTH = new Object();
        Object object = 2;
        Object[] arrobject = Integer.valueOf(18);
        Object object2 = new Object[]{"00", arrobject};
        Object object3 = 14;
        Object object4 = new Object[]{"01", object3};
        Object object5 = 3;
        Object object6 = VARIABLE_LENGTH;
        Integer n = 20;
        Object object7 = new Object[]{"10", object6, n};
        Object[] arrobject2 = Integer.valueOf(6);
        Object object8 = new Object[]{"11", arrobject2};
        Object object9 = new Object[]{"13", arrobject2};
        Object[] arrobject3 = new Object[]{"15", arrobject2};
        Object[] arrobject4 = 8;
        Object[] arrobject5 = new Object[]{"17", arrobject2};
        Object object10 = VARIABLE_LENGTH;
        object6 = 10;
        Object[] arrobject6 = new Object[]{"21", object10, n};
        Object[] arrobject7 = VARIABLE_LENGTH;
        Object[] arrobject8 = VARIABLE_LENGTH;
        Object[] arrobject9 = VARIABLE_LENGTH;
        Object object11 = 13;
        Object[] arrobject10 = VARIABLE_LENGTH;
        object10 = 30;
        Object[] arrobject11 = new Object[]{"90", arrobject10, object10};
        Object object12 = VARIABLE_LENGTH;
        arrobject10 = 15;
        Object[] arrobject12 = new Object[]{"91", object12, object10};
        Object[] arrobject13 = new Object[]{"92", VARIABLE_LENGTH, object10};
        Object[] arrobject14 = VARIABLE_LENGTH;
        object12 = 17;
        Object[] arrobject15 = new Object[]{"93", arrobject14, object10};
        arrobject14 = VARIABLE_LENGTH;
        Object[] arrobject16 = new Object[]{"95", VARIABLE_LENGTH, object10};
        Object[] arrobject17 = new Object[]{"96", VARIABLE_LENGTH, object10};
        Object[] arrobject18 = new Object[]{"97", VARIABLE_LENGTH, object10};
        Object[] arrobject19 = new Object[]{"98", VARIABLE_LENGTH, object10};
        Object[] arrobject20 = new Object[]{"99", VARIABLE_LENGTH, object10};
        TWO_DIGIT_DATA_LENGTH = new Object[][]{object2, object4, {"02", object3}, object7, object8, {"12", arrobject2}, object9, arrobject3, arrobject5, {"20", object}, arrobject6, {"22", arrobject7, 29}, {"30", arrobject8, arrobject4}, {"37", arrobject9, arrobject4}, arrobject11, arrobject12, arrobject13, arrobject15, {"94", arrobject14, object10}, arrobject16, arrobject17, arrobject18, arrobject19, arrobject20};
        arrobject3 = new Object[]{"240", VARIABLE_LENGTH, object10};
        arrobject5 = new Object[]{"241", VARIABLE_LENGTH, object10};
        object2 = VARIABLE_LENGTH;
        arrobject9 = new Object[]{"250", VARIABLE_LENGTH, object10};
        arrobject8 = new Object[]{"251", VARIABLE_LENGTH, object10};
        arrobject7 = new Object[]{"253", VARIABLE_LENGTH, object12};
        object9 = VARIABLE_LENGTH;
        object8 = VARIABLE_LENGTH;
        object4 = VARIABLE_LENGTH;
        arrobject4 = VARIABLE_LENGTH;
        arrobject6 = new Object[]{"410", object11};
        arrobject11 = new Object[]{"411", object11};
        arrobject12 = new Object[]{"412", object11};
        arrobject13 = new Object[]{"414", object11};
        object7 = VARIABLE_LENGTH;
        arrobject14 = new Object[]{"421", VARIABLE_LENGTH, arrobject10};
        arrobject15 = new Object[]{"423", VARIABLE_LENGTH, arrobject10};
        THREE_DIGIT_DATA_LENGTH = new Object[][]{arrobject3, arrobject5, {"242", object2, arrobject2}, arrobject9, arrobject8, arrobject7, {"254", object9, n}, {"400", object8, object10}, {"401", object4, object10}, {"402", object12}, {"403", arrobject4, object10}, arrobject6, arrobject11, arrobject12, {"413", object11}, arrobject13, {"420", object7, n}, arrobject14, {"422", object5}, arrobject15, {"424", object5}, {"425", object5}, {"426", object5}};
        arrobject4 = new Object[]{"310", arrobject2};
        object7 = new Object[]{"311", arrobject2};
        object8 = new Object[]{"313", arrobject2};
        object9 = new Object[]{"315", arrobject2};
        arrobject3 = new Object[]{"316", arrobject2};
        arrobject5 = new Object[]{"320", arrobject2};
        arrobject9 = new Object[]{"321", arrobject2};
        arrobject8 = new Object[]{"324", arrobject2};
        arrobject7 = new Object[]{"330", arrobject2};
        arrobject6 = new Object[]{"331", arrobject2};
        arrobject11 = new Object[]{"334", arrobject2};
        arrobject12 = new Object[]{"340", arrobject2};
        arrobject13 = new Object[]{"341", arrobject2};
        arrobject14 = new Object[]{"344", arrobject2};
        arrobject15 = new Object[]{"346", arrobject2};
        arrobject16 = new Object[]{"350", arrobject2};
        arrobject17 = new Object[]{"351", arrobject2};
        arrobject18 = new Object[]{"354", arrobject2};
        arrobject19 = new Object[]{"356", arrobject2};
        arrobject20 = new Object[]{"357", arrobject2};
        Object[] arrobject21 = new Object[]{"361", arrobject2};
        Object[] arrobject22 = new Object[]{"362", arrobject2};
        Object[] arrobject23 = new Object[]{"365", arrobject2};
        Object[] arrobject24 = new Object[]{"366", arrobject2};
        Object[] arrobject25 = new Object[]{"367", arrobject2};
        Object[] arrobject26 = new Object[]{"390", VARIABLE_LENGTH, arrobject10};
        object12 = VARIABLE_LENGTH;
        object4 = VARIABLE_LENGTH;
        object5 = VARIABLE_LENGTH;
        object2 = VARIABLE_LENGTH;
        THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH = new Object[][]{arrobject4, object7, {"312", arrobject2}, object8, {"314", arrobject2}, object9, arrobject3, arrobject5, arrobject9, {"322", arrobject2}, {"323", arrobject2}, arrobject8, {"325", arrobject2}, {"326", arrobject2}, {"327", arrobject2}, {"328", arrobject2}, {"329", arrobject2}, arrobject7, arrobject6, {"332", arrobject2}, {"333", arrobject2}, arrobject11, {"335", arrobject2}, {"336", arrobject2}, arrobject12, arrobject13, {"342", arrobject2}, {"343", arrobject2}, arrobject14, {"345", arrobject2}, arrobject15, {"347", arrobject2}, {"348", arrobject2}, {"349", arrobject2}, arrobject16, arrobject17, {"352", arrobject2}, {"353", arrobject2}, arrobject18, {"355", arrobject2}, arrobject19, arrobject20, {"360", arrobject2}, arrobject21, arrobject22, {"363", arrobject2}, {"364", arrobject2}, arrobject23, arrobject24, arrobject25, {"368", arrobject2}, {"369", arrobject2}, arrobject26, {"391", object12, arrobject}, {"392", object4, arrobject10}, {"393", object5, arrobject}, {"703", object2, object10}};
        arrobject10 = new Object[]{"7001", object11};
        object11 = VARIABLE_LENGTH;
        arrobject4 = new Object[]{"8001", object3};
        object2 = VARIABLE_LENGTH;
        object5 = VARIABLE_LENGTH;
        object4 = VARIABLE_LENGTH;
        object7 = new Object[]{"8005", arrobject2};
        object8 = new Object[]{"8006", arrobject};
        object3 = VARIABLE_LENGTH;
        object12 = VARIABLE_LENGTH;
        arrobject = new Object[]{"8018", arrobject};
        object9 = new Object[]{"8020", VARIABLE_LENGTH, 25};
        arrobject2 = new Object[]{"8100", arrobject2};
        arrobject3 = new Object[]{"8101", object6};
        arrobject5 = new Object[]{"8102", object};
        object = VARIABLE_LENGTH;
        arrobject9 = new Object[]{"8200", VARIABLE_LENGTH, 70};
        FOUR_DIGIT_DATA_LENGTH = new Object[][]{arrobject10, {"7002", object11, object10}, {"7003", object6}, arrobject4, {"8002", object2, n}, {"8003", object5, object10}, {"8004", object4, object10}, object7, object8, {"8007", object3, object10}, {"8008", object12, 12}, arrobject, object9, arrobject2, arrobject3, arrobject5, {"8110", object, 70}, arrobject9};
    }

    private FieldParser() {
    }

    static String parseFieldsInGeneralPurpose(String string2) throws NotFoundException {
        Object object2;
        if (string2.isEmpty()) {
            return null;
        }
        if (string2.length() < 2) throw NotFoundException.getNotFoundInstance();
        String arrobject3 = string2.substring(0, 2);
        for (Object object2 : TWO_DIGIT_DATA_LENGTH) {
            if (!object2[0].equals(arrobject3)) continue;
            if (object2[1] != VARIABLE_LENGTH) return FieldParser.processFixedAI(2, (Integer)object2[1], string2);
            return FieldParser.processVariableAI(2, (Integer)object2[2], string2);
        }
        if (string2.length() < 3) throw NotFoundException.getNotFoundInstance();
        object2 = string2.substring(0, 3);
        for (Object[] arrobject : THREE_DIGIT_DATA_LENGTH) {
            if (!arrobject[0].equals(object2)) continue;
            if (arrobject[1] != VARIABLE_LENGTH) return FieldParser.processFixedAI(3, (Integer)arrobject[1], string2);
            return FieldParser.processVariableAI(3, (Integer)arrobject[2], string2);
        }
        for (Object[] arrobject : THREE_DIGIT_PLUS_DIGIT_DATA_LENGTH) {
            if (!arrobject[0].equals(object2)) continue;
            if (arrobject[1] != VARIABLE_LENGTH) return FieldParser.processFixedAI(4, (Integer)arrobject[1], string2);
            return FieldParser.processVariableAI(4, (Integer)arrobject[2], string2);
        }
        if (string2.length() < 4) throw NotFoundException.getNotFoundInstance();
        object2 = string2.substring(0, 4);
        Object[][] arrobject = FOUR_DIGIT_DATA_LENGTH;
        int n = arrobject.length;
        int n2 = 0;
        while (n2 < n) {
            Object[] arrobject2 = arrobject[n2];
            if (arrobject2[0].equals(object2)) {
                if (arrobject2[1] != VARIABLE_LENGTH) return FieldParser.processFixedAI(4, (Integer)arrobject2[1], string2);
                return FieldParser.processVariableAI(4, (Integer)arrobject2[2], string2);
            }
            ++n2;
        }
        throw NotFoundException.getNotFoundInstance();
    }

    private static String processFixedAI(int n, int n2, String charSequence) throws NotFoundException {
        if (((String)charSequence).length() < n) throw NotFoundException.getNotFoundInstance();
        String string2 = ((String)charSequence).substring(0, n);
        int n3 = ((String)charSequence).length();
        if (n3 < (n2 += n)) throw NotFoundException.getNotFoundInstance();
        String string3 = ((String)charSequence).substring(n, n2);
        CharSequence charSequence2 = ((String)charSequence).substring(n2);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append('(');
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(')');
        ((StringBuilder)charSequence).append(string3);
        charSequence = ((StringBuilder)charSequence).toString();
        string3 = FieldParser.parseFieldsInGeneralPurpose((String)charSequence2);
        if (string3 == null) {
            return charSequence;
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(string3);
        return ((StringBuilder)charSequence2).toString();
    }

    private static String processVariableAI(int n, int n2, String charSequence) throws NotFoundException {
        int n3;
        String string2 = ((String)charSequence).substring(0, n);
        int n4 = ((String)charSequence).length();
        n2 = n3 = n2 + n;
        if (n4 < n3) {
            n2 = ((String)charSequence).length();
        }
        String string3 = ((String)charSequence).substring(n, n2);
        CharSequence charSequence2 = ((String)charSequence).substring(n2);
        charSequence = new StringBuilder();
        ((StringBuilder)charSequence).append('(');
        ((StringBuilder)charSequence).append(string2);
        ((StringBuilder)charSequence).append(')');
        ((StringBuilder)charSequence).append(string3);
        charSequence = ((StringBuilder)charSequence).toString();
        string2 = FieldParser.parseFieldsInGeneralPurpose((String)charSequence2);
        if (string2 == null) {
            return charSequence;
        }
        charSequence2 = new StringBuilder();
        ((StringBuilder)charSequence2).append((String)charSequence);
        ((StringBuilder)charSequence2).append(string2);
        return ((StringBuilder)charSequence2).toString();
    }
}


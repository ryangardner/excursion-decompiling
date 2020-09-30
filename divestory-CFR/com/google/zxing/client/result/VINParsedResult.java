/*
 * Decompiled with CFR <Could not determine version>.
 */
package com.google.zxing.client.result;

import com.google.zxing.client.result.ParsedResult;
import com.google.zxing.client.result.ParsedResultType;

public final class VINParsedResult
extends ParsedResult {
    private final String countryCode;
    private final int modelYear;
    private final char plantCode;
    private final String sequentialNumber;
    private final String vehicleAttributes;
    private final String vehicleDescriptorSection;
    private final String vehicleIdentifierSection;
    private final String vin;
    private final String worldManufacturerID;

    public VINParsedResult(String string2, String string3, String string4, String string5, String string6, String string7, int n, char c, String string8) {
        super(ParsedResultType.VIN);
        this.vin = string2;
        this.worldManufacturerID = string3;
        this.vehicleDescriptorSection = string4;
        this.vehicleIdentifierSection = string5;
        this.countryCode = string6;
        this.vehicleAttributes = string7;
        this.modelYear = n;
        this.plantCode = c;
        this.sequentialNumber = string8;
    }

    public String getCountryCode() {
        return this.countryCode;
    }

    @Override
    public String getDisplayResult() {
        StringBuilder stringBuilder = new StringBuilder(50);
        stringBuilder.append(this.worldManufacturerID);
        stringBuilder.append(' ');
        stringBuilder.append(this.vehicleDescriptorSection);
        stringBuilder.append(' ');
        stringBuilder.append(this.vehicleIdentifierSection);
        stringBuilder.append('\n');
        String string2 = this.countryCode;
        if (string2 != null) {
            stringBuilder.append(string2);
            stringBuilder.append(' ');
        }
        stringBuilder.append(this.modelYear);
        stringBuilder.append(' ');
        stringBuilder.append(this.plantCode);
        stringBuilder.append(' ');
        stringBuilder.append(this.sequentialNumber);
        stringBuilder.append('\n');
        return stringBuilder.toString();
    }

    public int getModelYear() {
        return this.modelYear;
    }

    public char getPlantCode() {
        return this.plantCode;
    }

    public String getSequentialNumber() {
        return this.sequentialNumber;
    }

    public String getVIN() {
        return this.vin;
    }

    public String getVehicleAttributes() {
        return this.vehicleAttributes;
    }

    public String getVehicleDescriptorSection() {
        return this.vehicleDescriptorSection;
    }

    public String getVehicleIdentifierSection() {
        return this.vehicleIdentifierSection;
    }

    public String getWorldManufacturerID() {
        return this.worldManufacturerID;
    }
}


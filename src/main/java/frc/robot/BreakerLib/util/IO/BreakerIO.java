// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.IO;

import java.util.Map;

/** Add your docs here. */
public class BreakerIO {
    private static Map<String, BreakerIONumberEntry> numbers;
    private static Map<String, BreakerIOBooleanEntry> bools;

    public static void andNumberEntry(BreakerIONumberEntry numberEntry) {
        if (!bools.containsKey(numberEntry.getName())) {
            numbers.put(numberEntry.getName(), numberEntry);
        }
    }

    public static void andBooleanEntry(BreakerIOBooleanEntry boolEntry) {
        if (!numbers.containsKey(boolEntry.getName())) {
            bools.put(boolEntry.getName(), boolEntry);
        }
    }

    public static void addEntryCollection(BreakerIOEntryCollection entryCollection) {
        for (BreakerIONumberEntry num: entryCollection.getNumberEntrys()) {
            BreakerIO.andNumberEntry(num);
        }
        for (BreakerIOBooleanEntry bool: entryCollection.getBooleanEntrys()) {
            BreakerIO.andBooleanEntry(bool);
        }
    }

    public static Number getNumber(String entryName) {
        return numbers.get(entryName).get()[0];
    }

    public static Number[] getNumberArray(String entryName) {
        return numbers.get(entryName).get();
    }

    public static Boolean getBoolean(String entryName) {
        return bools.get(entryName).get()[0];
    }

    public static Boolean[] getBooleanArray(String entryName) {
        return bools.get(entryName).get();
    }

    public static double getDouble(String entryName) {
        return getNumber(entryName).doubleValue();
    }

    public static int getInteger(String entryName) {
        return getNumber(entryName).intValue();
    }

    public static double[] getDoubleArray(String entryName) {
        Number[] numArr = getNumberArray(entryName);
        double[] retArr = new double[numArr.length];
        for (int i = 0; i <= numArr.length; i++) {
            retArr[i] = numArr[i].doubleValue();
        }
        return retArr;
    }

    public static int[] getIntegerArray(String entryName) {
        Number[] numArr = getNumberArray(entryName);
        int[] retArr = new int[numArr.length];
        for (int i = 0; i <= numArr.length; i++) {
            retArr[i] = numArr[i].intValue();
        }
        return retArr;
    }

    public static boolean contains(String entryName) {
        return numbers.containsKey(entryName) || bools.containsKey(entryName);
    }
}

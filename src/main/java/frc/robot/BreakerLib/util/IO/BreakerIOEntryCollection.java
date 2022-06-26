// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.IO;

import java.util.List;

/** Add your docs here. */
public class BreakerIOEntryCollection {
    private List<BreakerIONumberEntry> numberEntrys;
    private List<BreakerIOBooleanEntry> boolEntrys;

    public BreakerIOEntryCollection(BreakerIONumberEntry... numberEntrys) {
        for (BreakerIONumberEntry ent: numberEntrys) {
            this.numberEntrys.add(ent);
        }
    }

    public BreakerIOEntryCollection(BreakerIOBooleanEntry... boolEntrys) {
        for (BreakerIOBooleanEntry ent: boolEntrys) {
            this.boolEntrys.add(ent);
        }
    }

    public BreakerIOEntryCollection(BreakerIONumberEntry[] numberEntrys, BreakerIOBooleanEntry[] boolEntrys) {
        for (BreakerIONumberEntry ent: numberEntrys) {
            this.numberEntrys.add(ent);
        }
        for (BreakerIOBooleanEntry ent: boolEntrys) {
            this.boolEntrys.add(ent);
        }
    }

    public List<BreakerIONumberEntry> getNumberEntrys() {
        return numberEntrys;
    }

    public List<BreakerIOBooleanEntry> getBooleanEntrys() {
        return boolEntrys;
    }
    
}


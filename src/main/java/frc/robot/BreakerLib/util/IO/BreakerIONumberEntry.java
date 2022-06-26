// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.IO;

import java.util.List;
import java.util.function.Supplier;

import edu.wpi.first.util.sendable.Sendable;

/** Add your docs here. */
public class BreakerIONumberEntry {
    private Number[] value;
    private Supplier<Number[]> supplier;
    private Supplier<Number> monoSupplier;
    private boolean usesSupplier;
    private boolean usesMonoSupplier;
    private String name;

    public BreakerIONumberEntry(String name, Supplier<Number[]> supplier) {
        this.supplier = supplier;
        this.value = supplier.get();
        usesSupplier = true;
        usesMonoSupplier = false;
        this.name = name;
    }
    
    public BreakerIONumberEntry(Supplier<Number> supplier, String name) {
        this.monoSupplier = supplier;
        this.value[0] = supplier.get();
        usesSupplier = true;
        usesMonoSupplier = true;
        this.name = name;
    }

    public BreakerIONumberEntry(String name, Number... value) {
        this.value = value;
        usesSupplier = false;
        usesMonoSupplier = false;
        this.name = name;
    }

    public void update(String name, Number... newValue) {
        value = newValue;
        this.name = name;
    }

    public Number[] get() {
        if (usesSupplier && !usesMonoSupplier) {
            value = supplier.get();
        } else if (!usesSupplier && usesMonoSupplier) {
            value[0] = monoSupplier.get();
        }
        return value;
    }

    public String getName() {
        return name;
    }

}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.IO;

import java.util.function.BooleanSupplier;
import java.util.function.Supplier;

/** Add your docs here. */
public class BreakerIOBooleanEntry {
    private Boolean[] value;
    private Supplier<Boolean[]> supplier;
    private boolean usesSupplier;
    private BooleanSupplier monoSupplier;
    private boolean usesMonoSupplier;
    private String name;
    public BreakerIOBooleanEntry(String name, Supplier<Boolean[]> supplier) {
        this.supplier = supplier;
        this.value = supplier.get();
        usesSupplier = true;
        usesMonoSupplier = false;
        this.name = name;
    }

    public BreakerIOBooleanEntry(String name, BooleanSupplier supplier) {
        this.monoSupplier = supplier;
        this.value[0] = supplier.getAsBoolean();
        usesSupplier = true;
        usesMonoSupplier = true;
        this.name = name;
    }


    public BreakerIOBooleanEntry(String name, Boolean... value) {
        this.value = value;
        usesSupplier = false;
        usesMonoSupplier = false;
        this.name = name;
    }

    public void update(String name, Boolean... newValue) {
        value = newValue;
        this.name = name;
    }

    public Boolean[] get() {
        if (usesSupplier && !usesMonoSupplier) {
            value = supplier.get();
        } else if (usesSupplier && usesMonoSupplier) {
            value[0] = monoSupplier.getAsBoolean();
        }
        return value;
    }

    public String getName() {
        return name;
    }
}

// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.math.polynomials;

/** Add your docs here. */
public class BreakerPolynomial implements BreakerGenericPolynomial {
    public BreakerMonomial[] monomials;
    public BreakerPolynomial(BreakerMonomial...monomials) {
        this.monomials = monomials;
    }

    @Override
    public double getValueAtX(double xValue) {
        double total = 0;
        for (BreakerMonomial mon: monomials) {
            total += mon.getValueAtX(xValue);
        }
        return total;
    }

    @Override
    public double getSignRelativeValueAtX(double xValue) {
        boolean tn = xValue < 0;
        xValue = Math.abs(xValue);
        return xValue * (tn ? -1 : 1);
    }
}

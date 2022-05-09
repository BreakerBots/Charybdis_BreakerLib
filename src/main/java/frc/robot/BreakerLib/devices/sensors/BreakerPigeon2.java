// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.
package frc.robot.BreakerLib.devices.sensors;
import com.ctre.phoenix.sensors.Pigeon2_Faults;
import com.ctre.phoenix.sensors.WPI_Pigeon2;
import frc.robot.BreakerLib.devices.BreakerGenaricDevice;
import frc.robot.BreakerLib.util.math.BreakerMath;
import frc.robot.BreakerLib.util.selftest.DeviceHealth;
/* Good version of the CTRE Pigeon 2 class BAYBEEE! */
public class BreakerPigeon2 implements BreakerGenaricDevice {
  private WPI_Pigeon2 pigeon;
  private double imuInvert;
  private DeviceHealth currentHealth = DeviceHealth.NOMINAL;
  private String faults = null;
  private String deviceName = "Pigeon2_IMU";
  // private double pitch;
  // private double yaw;
  // private double roll;
  /* Coordinate array. w = angle, x = x-axis, y = y-axis */
  // private double[] wxy = new double[3];
  // private double xSpeed;
  // private double ySpeed;
  // private double zSpeed;
  //private double prevTime;
  // private int cycleCount;
  // private double XAccelBias = 0;
  // private double YAccelBias = 0;
  // private double xAccelAccum;
  // private double yAccelAccum;
  // Mike's Linear Accel prototype
  // private double yAccelBiasMike = 0.0;
  // private double yVelocityMike = 0.0;
  // private double yPostionMike = 0.0;
  /** Creates a new PigeonIMU object. */
  public BreakerPigeon2(int deviceID, boolean isInverted) {
    pigeon = new WPI_Pigeon2(deviceID);
    imuInvert = isInverted ? -1 : 1;
  }
  /** Returns pitch angle within +- 360 degrees */
  public double getPitch() {
    return BreakerMath.angleModulus(pigeon.getPitch());
  }
  /** Returns yaw angle within +- 360 degrees */
  public double getYaw() {
    return BreakerMath.angleModulus(pigeon.getYaw()) * imuInvert;
  }
  /** Returns roll angle within +- 360 degrees */
  public double getRoll() {
    return BreakerMath.angleModulus(pigeon.getRoll());
  }

  /** Returns raw yaw, pitch, and roll angles in an array */
  public double[] getRawAngles() {
    double[] RawYPR = new double[3];
    pigeon.getYawPitchRoll(RawYPR);
    return RawYPR;
  }
  /** Resets yaw to 0 degrees */
  public void reset() {
    pigeon.setYaw(0);
  }

  public void set(double angle) {
    pigeon.setYaw(angle);
  }

  /** Returns accelerometer value based on given index */
  public double getGyroRates(int arrayElement) {
    double[] rawRates = new double[3];
    pigeon.getRawGyro(rawRates);
    return rawRates[arrayElement];
  }
  public double getPitchRate() {
    return getGyroRates(0);
  }
  public double getYawRate() {
    return getGyroRates(1);
  }
  public double getRollRate() {
    return getGyroRates(2);
  }
//   public void calculateAccelerometerBias() {
//     double[] grav = new double[3];
//       // yAccelAccum += getRawIns2AccelY();
//       // xAccelAccum += getRawIns2AccelX();
//       pigeon.getGravityVector(grav);
//       YAccelBias = (getPerCycle1gSpeed() * grav[1]);
//       XAccelBias = (getPerCycle1gSpeed() * grav[0]);
//       // YAccelBias = yAccelAccum/cycleCount;
//       // XAccelBias = xAccelAccum/cycleCount;
//       // cycleCount ++;
// // Mike's test of linear accelerometer functionality  }
  // private double yAccelBiasMike() {
  //   if (cycleCount < 150) {
  //     yAccelAccum += getRawAccelerometerVals(1);
  //     yAccelBiasMike = yAccelAccum / cycleCount;
  //   }
  //   yVelocityMike += actualYAccel * cycleTime;
  // }
//   private double calculateYPositionMike() {
//     double curTime = RobotController.getFPGATime(); // In microseconds
//     double cycleTime = curTime - prevTime;
//     double accelBias = yAccelBiasMike();
//     double unbasiasedAccel = getRawAccelerometerVals(1) - yAccelBiasMike;
//     yVelocityMike += unbiasedAccel * cycleTime;
//     yPostionMike += yVelocityMike * cycleTime;
//     prevTime = curTime;   System.out.println("Y accel: " + getRawAccelerometerVals(1) + "  Y accel bias: " + accelBias + "  Y vel: " + yVelocityMike + "  Y pos: " + yPositionMike);
// }
  // public int getAccelXBias() {
  //   // if (cycleCount > 150) {
  //   return XAccelBias;
  //   // } else {
  //   // return 0d;
  //   // }
  // }
  // public int getAccelYBias() {
  //   // if (cycleCount > 250) {
  //   return YAccelBias;
  //   // } else {
  //   // return 0d;
  //   // }
  // }
  public short getRawAccelerometerVals(int arrayElement) {
    short[] accelVals = new short[3];
    pigeon.getBiasedAccelerometer(accelVals);
    return accelVals[arrayElement];
  }
  public double getRawIns2AccelX() {
    return (BreakerMath.fixedToFloat(getRawAccelerometerVals(0), 14) * 0.02);
  }
  // public double getIns2AccelX() {
  //   return getRawIns2AccelX() - getAccelXBias();
  // }
  public double getRawIns2AccelY() {
    return (BreakerMath.fixedToFloat(getRawAccelerometerVals(1), 14) * 0.02);
  }
  // public double getIns2AccelY() {
  //   return getRawIns2AccelY() - getAccelYBias();
  // }
  public double getRawIns2AccelZ() {
    return (BreakerMath.fixedToFloat(getRawAccelerometerVals(2), 14) * 0.02);
  }
  // private void calculateGlobalPosition() {
  //   // double diffTime = 0.02;
  //   double radYaw = (yaw * (Math.PI / 180.0));
  //   // xSpeed += getIns2AccelX();
  //   ySpeed += getRawIns2AccelY();
  //   // zSpeed += getIns2AccelZ();
  //   wxy[0] = yaw;
  //   wxy[1] += ((ySpeed * Math.cos(radYaw)) * getCycleDiffTime());
  //   wxy[2] += ((ySpeed * Math.sin(radYaw)) * getCycleDiffTime());
  //   // if (cycleCount == 250) {
  //   // resetGlobalPosition();
  //   // }
  // }
  // public double[] getGlobalPosition() {
  //   return wxy;
  // }
  // public double getGlobalPositionComponents(int arrayElement) {
  //   return wxy[arrayElement];
  // }
  // public void resetGlobalPosition() {
  //   wxy[1] = 0;
  //   wxy[2] = 0;
  //   ySpeed = 0;
  //   xSpeed = 0;
  //   zSpeed = 0;
  // }
  public int getPigeonUpTime() {
    return pigeon.getUpTime();
  }

  @Override
  public void runSelfTest() {
    faults = null;
    Pigeon2_Faults curFaults = new Pigeon2_Faults();
    pigeon.getFaults(curFaults);
    if (curFaults.HardwareFault) {
      currentHealth = DeviceHealth.INOPERABLE;
      faults += " HARDWARE_FAULT ";
    } 
    if (curFaults.MagnetometerFault) {
      currentHealth = DeviceHealth.INOPERABLE;
      faults += " MAG_FAULT ";
    }
    if (curFaults.GyroFault) {
      currentHealth = DeviceHealth.INOPERABLE;
      faults += "  GYRO_FAULT ";
    }
    if (curFaults.AccelFault) {
      currentHealth = DeviceHealth.INOPERABLE;
      faults += "  ACCEL_FAULT ";
    }
    if (curFaults.UnderVoltage) {
      currentHealth = (currentHealth != DeviceHealth.INOPERABLE) ? DeviceHealth.FAULT : currentHealth;
      faults += " UNDER_6.5V ";
    }
    if (!curFaults.HardwareFault && !curFaults.MagnetometerFault && !curFaults.GyroFault 
    && !curFaults.AccelFault && !curFaults.UnderVoltage) {
      currentHealth = DeviceHealth.NOMINAL;
      faults = null;
    }
  }

  @Override
  public DeviceHealth getHealth() {
    return currentHealth;
  }
  @Override
  public String getFaults() {
    return faults;
  }
  @Override
  public String getDeviceName() {
    return deviceName;
  }
  @Override
  public boolean hasFault() {
    return currentHealth != DeviceHealth.NOMINAL;
  }
  @Override
  public void setDeviceName(String newName) {
    deviceName = newName;
  }
}













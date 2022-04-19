// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.selftest;

import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.devices.BreakerGenaricDevice;
import frc.robot.BreakerLib.util.BreakerLog;

public class SelfTest extends SubsystemBase {
  /** Creates a new SelfTest. */
  private int cycleCount;
  private static String lastSystemCheck;
  private static List<BreakerGenaricDevice> devices = new ArrayList<BreakerGenaricDevice>();
  private static int cyclesbetweenPerSelfCecks = 250;
  private static boolean lastCheckPassed;
  public SelfTest(double secondsBetweenPeriodicSelfChecks) {
    cyclesbetweenPerSelfCecks = (int) (secondsBetweenPeriodicSelfChecks * 50);
  }

  public static void addDevice(BreakerGenaricDevice device) {
    devices.add(device);
  }

  public static String getLastSelfCheck() {
    return lastSystemCheck;
  }

  public static boolean getLastSelfCheckPassed() {
    return lastCheckPassed;
  }

  public static void runSelfCheck() {
    StringBuilder work = new StringBuilder("\n RUNNING SELF CHECK: \n");
    List<BreakerGenaricDevice> faultDevices = new ArrayList<BreakerGenaricDevice>();
    for (BreakerGenaricDevice device: devices) {
      device.runSelfTest();
      if (device.hasFault()) {
        faultDevices.add(device);
      }
    }
    if (faultDevices.size() > 0) {
      work.append(" SELF CHECK FAILED - FAULTS FOUND: \n");
      lastCheckPassed = false;
      for (BreakerGenaricDevice faultDiv: faultDevices) {
        work.append(" " + faultDiv.getDeviceName() + "-" + faultDiv.getFaults() + " ");
      }
    } else {
      work.append(" SELF CHECK PASSED ");
      lastCheckPassed = true;
    }
    lastSystemCheck = work.toString();
    BreakerLog.log(lastSystemCheck);
  }

  @Override
  public void periodic() {
    if (cycleCount ++ % cyclesbetweenPerSelfCecks == 0) {
      runSelfCheck();
    }
  }
}

package frc.robot.BreakerLib.factories;

import java.util.Map;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
// not to sound bad, but the end goal of this is basicly impossable without the command scedualar and most command base classes entierly
/** Factory for setting up subsystems and command assignments. */
public class BreakerSubsystemFactory {

    public static <T extends SubsystemBase> T build(Map<String, Boolean> subsystemMap) {

        return null;
        // String type = obj.getClass().getSimpleName();

        // for (String subsystemName: subsystemMap.keySet()) {
        //     if () {

        //     }
        // }
    }
}

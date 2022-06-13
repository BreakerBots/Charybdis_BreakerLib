// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.CTRE;

import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import com.fasterxml.jackson.databind.JsonNode;

import org.ejml.data.ZMatrix;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.BreakerLib.util.BreakerJsonUtil;

/** Add your docs here. */
public class BreakerCANManager extends SubsystemBase {
    private BreakerCANManager manager = new BreakerCANManager();
    private static Map<Integer, Boolean> regesteredDevices = new HashMap<>();
    private static Map<Integer, Pair<String, String>> retrivedDevices = new HashMap<>();
    private static Map<Integer, Boolean> missingDevices = new HashMap<>();
    private BreakerCANManager() {}

    private JsonNode retriveDeviceList() {
        try {
            return BreakerJsonUtil.readJsonFromURL("http://172.22.11.2:1250/?action=getdevices").get("DeviceArray");
        } catch (Exception e) {
            return null;
        }     
    }

    public static void regesterDevice(int deviceID, boolean isAttachedToSystem) {
        if (!regesteredDevices.containsKey(deviceID)) {
            regesteredDevices.put(deviceID, isAttachedToSystem);
        }
    }

    private void checkDevices() {
        JsonNode response = retriveDeviceList();
        Iterator<JsonNode> iter = response.elements();
        retrivedDevices.clear();
        missingDevices.clear();
        while (iter.hasNext()) {
            JsonNode divNode = iter.next();
            int id = divNode.get("uniqID").asInt();
            String divType = divNode.get("Model").asText();
            String divName = divNode.get("Name").asText();
            retrivedDevices.put(id, new Pair<>(divType, divName));
        }
        for (int id: regesteredDevices.keySet()) {
            boolean found = false;
            for (Entry<Integer, Pair<String, String>> ent: retrivedDevices.entrySet()) {
                if (ent.getKey() == id) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                missingDevices.put(id, regesteredDevices.get(id));
            }
        }
    }

    public static Map<Integer, Boolean> getMissingDevices() {
        return missingDevices;
    }

    public static Set<Integer> getMissingDeviceIDs() {
        return missingDevices.keySet();
    }

    public static boolean checkDeviceIsFound(int idToCheck) {
        return missingDevices.containsKey(idToCheck);
    }

    public static Pair<String, String> getDeviceModelAndName(int deviceID) {
        return retrivedDevices.get(deviceID);
    }

    public static String getFormatedFullDeviceName(int deviceID) {
        Pair<String, String> pair = getDeviceModelAndName(deviceID);
        return " (" + pair.getFirst() + "/ ID: " + deviceID + ") " + pair.getSecond() + " ";
    }

    public static boolean hasMissingDevices() {
        return !missingDevices.isEmpty();
    }
 
    @Override
    public void periodic() {
        checkDevices();
    }
}

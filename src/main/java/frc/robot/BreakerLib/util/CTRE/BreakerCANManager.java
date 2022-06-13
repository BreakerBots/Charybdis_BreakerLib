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
    private static List<Integer> regesteredDeviceIDs = new ArrayList<>();
    private static Map<Integer, Pair<String, String>> retrivedDevices = new HashMap<>();
    private static List<Integer> missingDevices = new ArrayList<>();
    private BreakerCANManager() {}

    private JsonNode retriveDeviceList() {
        try {
            return BreakerJsonUtil.readJsonFromURL("http://172.22.11.2:1250/?action=getdevices").get("DeviceArray");
        } catch (Exception e) {
            return null;
        }     
    }

    public static void regesterDevice(int deviceID) {
        if (!regesteredDeviceIDs.contains(deviceID)) {
            regesteredDeviceIDs.add(deviceID);
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
        for (int id: regesteredDeviceIDs) {
            boolean found = false;
            for (Entry<Integer, Pair<String, String>> ent: retrivedDevices.entrySet()) {
                if (ent.getKey() == id) {
                    found = true;
                    break;
                }
            }
            if (!found) {
                missingDevices.add(id);
            }
        }
    }

    public static List<Integer> getMissingDeviceIDs() {
        return missingDevices;
    }

    public static boolean checkDeviceIsFound(int idToCheck) {
        return missingDevices.contains(idToCheck);
    }

    public static Pair<String, String> getDeviceModelAndName(int deviceID) {
        return retrivedDevices.get(deviceID);
    }

    public static String getFormatedFullDeviceName(int deviceID) {
        Pair<String, String> pair = getDeviceModelAndName(deviceID);
        return " (" + pair.getFirst() + "/ ID: " + deviceID + ") " + pair.getSecond() + " ";
    }
 
    @Override
    public void periodic() {
        checkDevices();
    }
}

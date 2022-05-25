// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.BreakerLib.util.math.interpolation;

import java.util.Map.Entry;
import java.util.TreeMap;

/** Add your docs here. */
public class BreakerInterpolateingTreeMap<K,V> {
    private TreeMap<K,BreakerInterpolateable<V>> indexesAndValues;
    public BreakerInterpolateingTreeMap(TreeMap<K, BreakerInterpolateable<V>> indexesAndValues) {
        this.indexesAndValues = indexesAndValues;
    }

    public V getInterpolatedVector3(K interpolendValue) {
        Entry<K, BreakerInterpolateable<V>> low = indexesAndValues.floorEntry(interpolendValue);
        Entry<K, BreakerInterpolateable<V>> high = indexesAndValues.ceilingEntry(interpolendValue);

        if (low == null) {
            return high.getValue().getSelf();
        }
        if (high == null) {
            return low.getValue().getSelf();
        }
        if (high.getValue().equals(low.getValue())) {
            return high.getValue().getSelf();
        }

        return high.getValue().interpolate((double) interpolendValue, (double) high.getKey(), high.getValue().getSelf(), (double) low.getKey(), low.getValue().getSelf());
    }
}

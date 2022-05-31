package frc.robot.BreakerLib.util.math.interpolation.interpolateingmaps;

import java.util.Map.Entry;

import edu.wpi.first.math.geometry.Translation2d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import frc.robot.BreakerLib.util.math.BreakerMath;
import frc.robot.BreakerLib.util.math.interpolation.BreakerInterpolateable;

/** Add your docs here. */
public class BreakerLegrangeInterpolateingTreeMap<K, V extends BreakerInterpolateable<V>> extends java.util.AbstractMap<K, V> implements BreakerGenericInterpolateingMap<K, V>  {
    private TreeMap<K, V> indexesAndValues;
    public BreakerLegrangeInterpolateingTreeMap(TreeMap<K, V> indexesAndValues) {
        this.indexesAndValues = indexesAndValues;
    }

    public BreakerLegrangeInterpolateingTreeMap() {
        indexesAndValues = new TreeMap<K, V>();
    }

    @Override
    public V getInterpolatedValue(K interpolendValue) {
        V refVal = indexesAndValues.pollFirstEntry().getValue();
        List<List<Double>> interpolatableVals = new ArrayList<>();
        double[] interpolatedValArr = new double[refVal.getInterpolatableData().length];
        for (int i = 0; i < refVal.getInterpolatableData().length; i ++) {
            List<Double> intValPortion = new ArrayList<>();
            for (Entry<K, V> ent: indexesAndValues.entrySet()) {
                intValPortion.add(ent.getValue().getInterpolatableData()[i]);
            }
            interpolatableVals.add(intValPortion);
        }
        int j = 0;
        for (List<Double> listD: interpolatableVals) {
            Translation2d[] arr = new Translation2d[listD.size()];
            Iterator<K> it = indexesAndValues.keySet().iterator();
            int k = 0;
            while(it.hasNext()) {
                arr[k] = new Translation2d((double) it.next(), listD.get(k));
                k ++;
            }
            interpolatedValArr[j] = BreakerMath.interpolateLegrange((double) interpolendValue, arr);
            j ++;
        }
        return refVal.fromInterpolatableData(interpolatedValArr);
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return indexesAndValues.entrySet();
    }

    @Override
    public V put(K key, V value) {
        return indexesAndValues.put(key, value);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        indexesAndValues.putAll(m);
    }

    @Override
    public boolean containsKey(Object key) {
        return indexesAndValues.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return indexesAndValues.containsValue(value);
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return indexesAndValues.replace(key, oldValue, newValue);
    }
}
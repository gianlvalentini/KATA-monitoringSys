import org.junit.Test;
import system.monitoringSys.Monitor;
import system.sensor.Sensor;
import system.store.MeasurementStore;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MonitorTest {

    @Test
    public void OutOfRange() {

        MeasurementStore store = new MeasurementStore();
        Thread monitorThread = new Thread(new Monitor(store));

        List<Double> testList = new ArrayList<>();

        Double measurement1 = 50.0, measurement2 = 40.0, measurement3 = 60.1;

        store.measurementListener(measurement1, "One");
        store.measurementListener(measurement2, "Two");
        store.measurementListener(measurement3, "Three");

        List<Double> measurementList = store.getMeasurements();

        testList.add(measurement1);
        testList.add(measurement2);
        testList.add(measurement3);

        monitorThread.start();

        try {
            Thread.sleep(100000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        assertTrue(store.getAvgOFR());
        assertTrue(store.getDifMaxMinOFR());
    }

    //TODO... test of avg y difMaxMin Const changing
}

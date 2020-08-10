import org.junit.Test;
import system.sensor.Sensor;
import system.store.MeasurementStore;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class SensorTest {

    @Test
    public void sending6MeasurementsIn3Seconds() {

        MeasurementStore store = new MeasurementStore();
        Thread sensorOne = new Thread(new Sensor("One", store));
        sensorOne.start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        store.turnOffSystem(); //turning off the sensor

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        store.turnOnSystem(); //Even i'm turning on, all threads have ended

        List<Double> measurementList = store.getMeasurements();

        assertEquals(6, measurementList.size());
    }
}

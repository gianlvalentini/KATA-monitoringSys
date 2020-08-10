import org.hamcrest.Matcher;

import org.junit.Test;
import system.sensor.Sensor;
import system.store.MeasurementStore;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class StoreTest {

    @Test
    public void addingMeasurements() {

        MeasurementStore store = new MeasurementStore();
        Double measurement1 = 1.0, measurement2 = 2.0, measurement3 = 3.0;
        List<Double> testList = new ArrayList<Double>();

        store.measurementListener(measurement1, "One");
        store.measurementListener(measurement2, "Two");
        store.measurementListener(measurement3, "Three");
        List<Double> measurementList = store.getMeasurements();

        testList.add(measurement1);
        testList.add(measurement2);
        testList.add(measurement3);

        assertEquals(testList, measurementList);

    }

    @Test
    public void addingMeasurementsWithDifferentThreads() {

        MeasurementStore store = new MeasurementStore();
        Thread sensorOne = new Thread(new Sensor("One", store));
        Thread sensorTwo = new Thread(new Sensor("Two", store));
        Thread sensorThree = new Thread(new Sensor("Three", store));
        Thread sensorFour = new Thread(new Sensor("Four", store));

        sensorOne.start();
        sensorTwo.start();
        sensorThree.start();
        sensorFour.start();

        try {
            Thread.sleep(2500); //5 measurement from each sensor
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        store.turnOffSensors();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Double> measurementList = store.getMeasurements();

        assertEquals(20, measurementList.size());

    }

    @Test
    public void addingMeasurementsWithDifferentThreadsInOrder() {

        //Because of the time threads start, the sensors have to be stored in order too

        //To really test this, i'd need to store de sensor number, but for this purpose i'll see the logs to see it

        MeasurementStore store = new MeasurementStore();
        Thread sensorOne = new Thread(new Sensor("One", store));
        Thread sensorTwo = new Thread(new Sensor("Two", store));
        Thread sensorThree = new Thread(new Sensor("Three", store));
        Thread sensorFour = new Thread(new Sensor("Four", store));

        sensorOne.start();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sensorTwo.start();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sensorThree.start();

        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        sensorFour.start();

        try {
            Thread.sleep(2503);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        store.turnOffSensors();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        List<Double> measurementList = store.getMeasurements();

        assertEquals(20, measurementList.size());

    }
}

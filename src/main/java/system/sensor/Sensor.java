package system.sensor;
import system.store.MeasurementStore;

public class Sensor implements Runnable {

    private String name;
    private MeasurementStore store;

    public Sensor(String name, MeasurementStore measurementStore) {
        this.name = name;
        this.store = measurementStore;
    }

    public void run() {

        while(store.getSensorKey()) {
            try {
                Thread.sleep(500);
                sendMeasurement();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void sendMeasurement() {

            store.measurementListener(getMeasurement(), getName());
    }

    private Double getMeasurement(){
        return Math.random()*100 ;
    }

    private String getName() {
        return name;
    }
}

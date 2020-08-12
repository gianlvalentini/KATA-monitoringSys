package system.monitoringSys;

import org.apache.log4j.Logger;
import system.exceptions.EmptyMeasurementException;
import system.store.MeasurementStore;

import java.util.Collections;
import java.util.OptionalDouble;

public class Monitor implements Runnable {

    private static final Logger LOGGER = Logger.getLogger(Monitor.class.getName());

    private MeasurementStore store;

    public Monitor(MeasurementStore store) {
        this.store = store;
    }

    public void run() {

        while(true) {

            try {
                Thread.sleep(30000);
                Double measurement = takeMeasurement();
                processMeasurement(measurement);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (EmptyMeasurementException e) {
                LOGGER.info(e.getMessage());
            }
        }
    }

    private void processMeasurement(Double measurement) {

        store.newMeasurementsProcessed(measurement);

        avgProcess();
        difMaxMinProcess();

        LOGGER.info(String.format("Measurement with value: %f, has been processed", measurement));
    }

    private void avgProcess() {

        OptionalDouble optionalAvg = store.getMeasurementsProcessed().stream().mapToDouble(a -> a).average();
        Double avg = optionalAvg.isPresent() ? optionalAvg.getAsDouble() : 0.0;

        LOGGER.debug(String.format("AVG: %f of %d processed measurement", avg, store.getMeasurementsProcessed().size()));

        if(avg > store.getAvgConst()) {

            store.OFRavgDetected();
            LOGGER.error(String.format("The average of processed measurements: %f it's over the expected: %f",
                                            avg, store.getAvgConst()));
            System.err.println(String.format("[MONITOR ERROR DETECTED] The average of processed measurements: %f it's over the expected: %f",
                                            avg, store.getAvgConst()));
        }else{
            store.rightAvgDetected();
        }
    }

    private void difMaxMinProcess() {

        Double max = Collections.max(store.getMeasurementsProcessed());
        Double min = Collections.min(store.getMeasurementsProcessed());

        Double actualDif = max - min;

        LOGGER.debug(String.format("MAX: %f - MIN: %f", max, min));

        if(actualDif > store.getDifMaxMinConst()) {

            store.OFRdifDetected();
            LOGGER.error(String.format("The difference between max and min of processed measurements: %f " +
                                             "it's over the expected: %f", actualDif, store.getDifMaxMinConst()));
            System.err.println(String.format("[MONITOR ERROR DETECTED] The difference between max and min of processed measurements: %f " +
                                             "it's over the expected: %f", actualDif, store.getDifMaxMinConst()));
        }
    }

    private Double takeMeasurement(){

        return store.measurementProvider();
    }


}
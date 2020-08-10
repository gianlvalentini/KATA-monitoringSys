package system.monitoringSys;

import org.apache.log4j.Logger;
import system.exceptions.EmptyMeasurementException;
import system.store.MeasurementStore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.OptionalDouble;

public class Monitor implements Runnable {

    //private static final Logger LOGGER = Logger.getLogger(Monitor.class.getName());

    private MeasurementStore store;
    private Double avgConst;
    private Double difMaxMinConst;

    private List<Double> measurementsProcessed = new ArrayList<>();

    public Monitor(MeasurementStore store, Double averageConst, Double difMaxMinConst) {
        this.store = store;
        this.avgConst = averageConst;
        this.difMaxMinConst = difMaxMinConst;
    }

    public void run() {

        while(store.getKey()) {
            try {

                Thread.sleep(30000);
                Double measurement = takeMeasurement();
                processMeasurement(measurement);

            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (EmptyMeasurementException e) {
                //LOGGER.info(e.getMessage());
                System.out.println(e.getMessage());
            }
        }
    }

    private void processMeasurement(Double measurement) {

        measurementsProcessed.add(measurement);

        avgProcess(measurement);
        difMaxMinProcess(measurement);

        //LOGGER.info(String.format("Measurement with value: %f, has been processed", measurement));
        System.out.println(String.format("Measurement with value: %f, has been processed", measurement));
    }

    private void avgProcess(Double measurement) {

        OptionalDouble optionalAvg = measurementsProcessed.stream().mapToDouble(a -> a).average();
        Double avg = optionalAvg.isPresent() ? optionalAvg.getAsDouble() : 0.0;

        if(avg > avgConst) {

            store.OFRavgDetected();
//            LOGGER.error(String.format("The average of processed measurements: %f it's over the expected: %f",
//                                            avg, avgConst));
            System.out.println(String.format("The average of processed measurements: %f it's over the expected: %f",
                                            avg, avgConst));
        }else{
            store.rightAvgDetected();
        }
    }

    private void difMaxMinProcess(Double measurement) {

        Double max = Collections.max(measurementsProcessed);
        Double min = Collections.min(measurementsProcessed);

        Double actualDif = max - min;

        if(actualDif > difMaxMinConst) {

            store.OFRdifDetected();
//            LOGGER.error(String.format("The difference between max and min of processed measurements: %f " +
//                                             "it's over the expected: %f", actualDif, difMaxMinConst));
            System.out.println(String.format("The difference between max and min of processed measurements: %f " +
                                             "it's over the expected: %f", actualDif, difMaxMinConst));
        }
    }

    private Double takeMeasurement(){

        return store.measurementProvider();
    }


}

//System.out.println(String.format("Measurement with value: %02d, from sensor: %s, has been processed"));
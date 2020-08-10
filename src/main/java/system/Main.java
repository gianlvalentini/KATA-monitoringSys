package system;

import org.apache.log4j.Logger;
import system.monitoringSys.Monitor;
import system.sensor.Sensor;
import system.store.MeasurementStore;

import java.io.Console;

public class Main {

    //private static final Logger LOGGER = Logger.getLogger(Monitor.class.getName());
    private static Boolean case1Exec = false;

    public static void main(String[] args)  {

        MeasurementStore store = new MeasurementStore();
        Thread sensorOne = new Thread(new Sensor("One", store));
        Thread sensorTwo = new Thread(new Sensor("Two", store));
        Thread sensorThree = new Thread(new Sensor("Three", store));
        Thread sensorFour = new Thread(new Sensor("Four", store));

        sensorOne.start();
        sensorTwo.start();
        sensorThree.start();
        sensorFour.start();

        Thread monitor = new Thread(new Monitor(store, 70.0, 40.0));
        monitor.start();

        //LOGGER.info("f");

//        Console cons;
//        if ((cons = System.console()) == null) {
//            LOGGER.error("Unable to obtain console");
//        }
//
//        while(!case1Exec)
//            String input = cons.readLine("Enter 1 to AtixCaseTest: ");
//            int i = Integer.parseInt(input);
//
//            if(i == 1){
//
//            }


//        sensorOne.turnOn();
//        System.out.println("it's logging anyway");
//        sensorOne.turnOff();

    }
}

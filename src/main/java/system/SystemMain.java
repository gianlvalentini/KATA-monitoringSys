package system;

import org.apache.log4j.PropertyConfigurator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import system.controller.MenuController;
import system.monitoringSys.Monitor;
import system.store.MeasurementStore;
import system.utils.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

@SpringBootApplication
public class SystemMain {

    private static MeasurementStore store = new MeasurementStore();
    private static Menu menu = new Menu(store);
    private static MenuController menuController = new MenuController();

    public static void main(String[] args)  {

        //LOG
        PropertyConfigurator.configure("src/main/resources/log4j.properties");

        //MONITOR
        Thread monitor = new Thread(new Monitor(store));
        monitor.start();

        if(args.length == 0){

            SpringApplication.run(SystemMain.class, args); //HTTP by default

        } else if (args.length > 1){

            System.err.println("There are to many args, only 'console' is available");
            System.exit(-1);

        } else if (args[0].toLowerCase().equals("console")) {

            //INPUT
            int selection;
            Scanner input = new Scanner(System.in);

            //MENU
            while(true) {

                menuView();

                try{
                    selection = input.nextInt();
                    menu.menuSelector(selection);
                }
                catch (InputMismatchException e){

                    System.err.println("The input doesn't match with the format required, only numbers are available");
                    System.err.println(e.getMessage());
                    System.exit(-1);
                }
            }
        } else {

            System.err.println("Wrong argument, only 'console' is available");
            System.exit(-1);
        }
    }

    private static void menuView() {

        System.out.println("\n/***************************************************/\n");

        System.out.println("Choose from these choices");
        System.out.println("-------------------------\n");
        System.out.println("1 - Run BaseCase (4 sensors with random measurements)");
        System.out.println("2 - Stop Sensors (The monitor won't stop)");
        System.out.println("3 - Configure Monitor DifMaxMin Const Value (If max-min is bigger than this Const " +
                "the system throws a console error) | Default 40.0");
        System.out.println("4 - Configure Monitor avg Const Value (If average is bigger than this Const " +
                "the system throws a console error) | Default: 70.0");
        System.out.println("5 - Stop System / Quit");

        System.out.println("\nInsert numeric choice:");
    }
}

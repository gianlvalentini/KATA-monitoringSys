package system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import system.utils.Menu;

import static system.utils.Menu.menuSelector;

@RestController
@RequestMapping("/sys")
public class MenuController  {

    @PutMapping("/baseCase")
    @ResponseStatus(HttpStatus.OK)
    public void activateBaseCase(){
        menuSelector(1);
    }

    @PutMapping("/stopSensors")
    @ResponseStatus(HttpStatus.OK)
    public void deactivateSensor(){
        menuSelector(2);
    }

    @PostMapping("/changeConst/difMaxMin")
    @ResponseStatus(HttpStatus.OK)
    public void maxMinConstUpdate(@RequestParam Double value){
        menuSelector(3, value);
    }

    @PostMapping("/changeConst/avg")
    @ResponseStatus(HttpStatus.OK)
    public void avgConstUpdate(@RequestParam Double value){
        menuSelector(4, value);
    }
}

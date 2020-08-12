# KATA-monitoringSys

Desarrollo de la kata que emula un sistema de monitoreo de mediciones. 

Mas info del enunciado en la WIKI del proyecto.

# SOFTWARE REQUIRED

    JDK 8+ (JDK8)
    Maven

# Installation

Para instalar las dependencias: 

    # cd proyecto
    mvn install
    
# Usage

La aplicación tiene 2 formas de ejecutarse, por **HTTP** (_Default_) o por **Consola** (_Agregar 'console' como argumento_) 

**HTTP endpoints** 

    #1 Para ejecutar el caso base (4 sensores con mediciones random)
    PUT http://localhost:8080/sys/baseCase
    
    #2 Para parar los sensores activos
    PUT http://localhost:8080/sys/stopSensors

    #3 Para cambiar la constante de la dif entre max y min (reemplazar ? con el nuevo valor)
    POST http://localhost:8080/sys/changeConst/difMaxMin/?value=?
    
    #4 Para cambiar la constante del promedio (reemplazar ? con el nuevo valor)
    http://localhost:8080/sys/changeConst/avg/?value=15
    
# Contact

Si tenés problemas o dudas corriendo el proyecto, podés contactarme acá: gian.valentini1997@gmail.com

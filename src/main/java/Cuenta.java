public class Cuenta {
    public static final int CTA_CORRIENTE = 0; //diferentes cuentas con una interface en comun
    public static final int CAJA_AHORRO = 1;
    private int tipo; //no seteable
    private long numeroCuenta; //no seteable
    private String titular; //tendria que ser un cliente mas que un string + seteable
    private long saldo; //seteable
    private long descubiertoAcordado; //seteable

    public Cuenta(int tipo, long nCuenta, String titular, long descAcordado) { //para cta corriente
        this.tipo = tipo;
        this.numeroCuenta = nCuenta;
        this.titular = titular;
        if(tipo == CTA_CORRIENTE) { //esto vuela
            this.descubiertoAcordado = descAcordado;
        } else {
            this.descubiertoAcordado = 0;
        }
        this.saldo = 0;
    }

    public Cuenta(int tipo, long numeroCuenta, String titular) { //para caja de ahorro
        this.tipo = tipo;
        this.numeroCuenta = numeroCuenta;
        this.titular = titular;
        this.descubiertoAcordado = 0;
        this.saldo = 0;
    }

    public void depositar (long monto) { //separaría la lógica a un componente operación
        saldo += monto;
    }

    public void extraer (long monto) throws RuntimeException { //separaría la lógica a un componente operación
        switch (tipo) {
            case CAJA_AHORRO : if(monto > saldo)
                throw new RuntimeException("No hay dinero en la cuenta");
            case CTA_CORRIENTE: if(monto > saldo + descubiertoAcordado)
                throw new RuntimeException("No hay dinero en la cuenta");
        }
        saldo -= monto;
    }
}

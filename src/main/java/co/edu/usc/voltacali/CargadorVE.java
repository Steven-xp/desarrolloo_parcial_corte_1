package co.edu.usc.voltacali;

import java.util.Vector;

/**
 * Voltacali - Parcial 1.
 * Partes A-D: modelo de datos de un cargador para vehiculos electricos,
 * con encapsulamiento, comportamiento base, sobrecarga, enums anidados,
 * clase interna RegistroSesion, bitacora y miembros estaticos.
 */
public class CargadorVE {

    /* ------------------------------------------------------------------ */
    /* Parte D a) Enums anidados (orden exacto exigido)                    */
    /* ------------------------------------------------------------------ */
    public enum TipoConector { TIPO_1, TIPO_2, CCS2, CHADEMO, GBT }

    public enum TipoCargador { MURAL, PEDESTAL, RAPIDO_DC, ULTRARRAPIDO, PORTATIL, BIDIRECCIONAL_V2G }

    public enum Ubicacion { CENTRO_COMERCIAL, UNIVERSIDAD, ESTACION_SERVICIO, PARQUEADERO_PUBLICO,
                            RESIDENCIAL, HOTEL, TERMINAL, FLOTA_CORPORATIVA }

    /* ------------------------------------------------------------------ */
    /* Parte D e) Miembros estaticos                                       */
    /* ------------------------------------------------------------------ */
    private static int totalCargadores = 0;          // se incrementa una sola vez por objeto creado
    public static int contadorRegistros = 0;         // numeracion consecutiva global de registros
    public static final double LIMITE_RED = 50.0;    // potencia contratada con el operador de red (kW)
    public static final double INCREMENTO_DEFECTO = 5.0;

    /* ------------------------------------------------------------------ */
    /* Parte A) Diez atributos privados                                    */
    /* ------------------------------------------------------------------ */
    private String fabricante;
    private int anioInstalacion;
    private int voltajeNominal;
    private TipoConector tipoConector;
    private TipoCargador tipoCargador;
    private int numeroConectores;
    private int puestosParqueo;
    private double potenciaMaxima;
    private Ubicacion ubicacion;
    private double potenciaActual;

    /* Parte D c) Bitacora de cada cargador */
    private Vector<RegistroSesion> bitacora;

    /* ------------------------------------------------------------------ */
    /* Parte D b) Clase interna no estatica                                */
    /* ------------------------------------------------------------------ */
    public class RegistroSesion {
        private int numero;
        private String fabricante;
        private int anioInstalacion;
        private double potenciaActual;
        private String evento;
        private boolean valido;

        /** Captura los datos del objeto externo sin recibirlos por parametro. */
        public RegistroSesion(String evento, boolean valido) {
            this.numero = ++contadorRegistros;
            this.fabricante = CargadorVE.this.fabricante;
            this.anioInstalacion = CargadorVE.this.anioInstalacion;
            this.potenciaActual = CargadorVE.this.potenciaActual;
            this.evento = evento;
            this.valido = valido;
        }

        public String describir() {
            return "[#" + numero + "] " + fabricante + " (" + anioInstalacion + ") - "
                    + potenciaActual + " kW - " + evento
                    + (valido ? " [VALIDO]" : " [INVALIDO]");
        }

        public int getNumero() { return numero; }
        public double getPotenciaActual() { return potenciaActual; }
        public String getEvento() { return evento; }
        public boolean isValido() { return valido; }
    }

    /* ------------------------------------------------------------------ */
    /* Parte C) Constructores sobrecargados                                */
    /* ------------------------------------------------------------------ */

    /** Constructor completo: recibe todo menos potenciaActual, que inicia en 0. */
    public CargadorVE(String fabricante, int anioInstalacion, int voltajeNominal,
                      TipoConector tipoConector, TipoCargador tipoCargador,
                      int numeroConectores, int puestosParqueo, double potenciaMaxima,
                      Ubicacion ubicacion) {
        this.fabricante = fabricante;
        this.anioInstalacion = anioInstalacion;
        this.voltajeNominal = voltajeNominal;
        this.tipoConector = tipoConector;
        this.tipoCargador = tipoCargador;
        this.numeroConectores = numeroConectores;
        this.puestosParqueo = puestosParqueo;
        this.potenciaMaxima = potenciaMaxima;
        this.ubicacion = ubicacion;
        this.potenciaActual = 0;
        this.bitacora = new Vector<RegistroSesion>();
        totalCargadores++;
    }

    /** Constructor reducido: usa this(...) con los valores por defecto. */
    public CargadorVE(String fabricante, int anioInstalacion, double potenciaMaxima) {
        this(fabricante, anioInstalacion, 220, TipoConector.TIPO_2, TipoCargador.PEDESTAL,
                1, 1, potenciaMaxima, Ubicacion.PARQUEADERO_PUBLICO);
    }

    /** Constructor copia: duplica caracteristicas tecnicas, potencia en 0 y bitacora nueva. */
    public CargadorVE(CargadorVE otro) {
        this(otro.fabricante, otro.anioInstalacion, otro.voltajeNominal, otro.tipoConector,
                otro.tipoCargador, otro.numeroConectores, otro.puestosParqueo,
                otro.potenciaMaxima, otro.ubicacion);
    }

    /* ------------------------------------------------------------------ */
    /* Parte A) Getters y setters                                          */
    /* ------------------------------------------------------------------ */
    public String getFabricante() { return fabricante; }
    public void setFabricante(String fabricante) { this.fabricante = fabricante; }

    public int getAnioInstalacion() { return anioInstalacion; }
    public void setAnioInstalacion(int anioInstalacion) { this.anioInstalacion = anioInstalacion; }

    public int getVoltajeNominal() { return voltajeNominal; }
    public void setVoltajeNominal(int voltajeNominal) { this.voltajeNominal = voltajeNominal; }

    public TipoConector getTipoConector() { return tipoConector; }
    public void setTipoConector(TipoConector tipoConector) { this.tipoConector = tipoConector; }

    public TipoCargador getTipoCargador() { return tipoCargador; }
    public void setTipoCargador(TipoCargador tipoCargador) { this.tipoCargador = tipoCargador; }

    public int getNumeroConectores() { return numeroConectores; }
    public void setNumeroConectores(int numeroConectores) { this.numeroConectores = numeroConectores; }

    public int getPuestosParqueo() { return puestosParqueo; }
    public void setPuestosParqueo(int puestosParqueo) { this.puestosParqueo = puestosParqueo; }

    public double getPotenciaMaxima() { return potenciaMaxima; }
    public void setPotenciaMaxima(double potenciaMaxima) { this.potenciaMaxima = potenciaMaxima; }

    public Ubicacion getUbicacion() { return ubicacion; }
    public void setUbicacion(Ubicacion ubicacion) { this.ubicacion = ubicacion; }

    public double getPotenciaActual() { return potenciaActual; }

    /** Set validado: rechaza negativos o mayores que la potencia maxima. */
    public void setPotenciaActual(double potenciaActual) {
        if (potenciaActual < 0 || potenciaActual > potenciaMaxima) {
            System.out.println("   ** Intento invalido: " + potenciaActual
                    + " kW fuera del rango [0, " + potenciaMaxima + "]. No cambia el estado.");
            bitacora.add(new RegistroSesion("setPotenciaActual(" + potenciaActual + ") rechazado", false));
        } else {
            this.potenciaActual = potenciaActual;
            bitacora.add(new RegistroSesion("setPotenciaActual(" + potenciaActual + ")", true));
        }
    }

    public Vector<RegistroSesion> getBitacora() { return bitacora; }

    /* ------------------------------------------------------------------ */
    /* Parte B) Comportamiento base                                        */
    /* ------------------------------------------------------------------ */

    /** Aumenta la potencia; si supera el maximo, se rechaza. */
    public void aumentarPotencia(double incremento) {
        double nueva = potenciaActual + incremento;
        if (nueva > potenciaMaxima) {
            System.out.println("   ** Aumento rechazado: " + nueva + " kW supera el maximo ("
                    + potenciaMaxima + " kW). No cambia el estado.");
            bitacora.add(new RegistroSesion("aumentarPotencia(" + incremento + ") rechazado", false));
        } else {
            potenciaActual = nueva;
            bitacora.add(new RegistroSesion("aumentarPotencia(" + incremento + ")", true));
        }
    }

    /** Reduce la potencia; si queda negativa, se rechaza. */
    public void reducirPotencia(double reduccion) {
        double nueva = potenciaActual - reduccion;
        if (nueva < 0) {
            System.out.println("   ** Reduccion rechazada: quedaria en " + nueva
                    + " kW (negativo). No cambia el estado.");
            bitacora.add(new RegistroSesion("reducirPotencia(" + reduccion + ") rechazado", false));
        } else {
            potenciaActual = nueva;
            bitacora.add(new RegistroSesion("reducirPotencia(" + reduccion + ")", true));
        }
    }

    /** Deja la potencia en 0. */
    public void cortarCarga() {
        potenciaActual = 0;
        bitacora.add(new RegistroSesion("cortarCarga()", true));
    }

    /** Parte B: horas = energia (kWh) / potencia actual (kW). */
    public double tiempoEstimadoCarga(double energiaKWh) {
        if (potenciaActual == 0) {
            System.out.println("   ** Potencia actual es 0: no se puede estimar el tiempo de carga.");
            return -1;
        }
        return energiaKWh / potenciaActual;
    }

    /** Parte C: usa la potencia programada en lugar de la actual. */
    public double tiempoEstimadoCarga(double energiaKWh, double potenciaProgramada) {
        if (potenciaProgramada <= 0) {
            System.out.println("   ** La potencia programada debe ser mayor que 0.");
            return -1;
        }
        return energiaKWh / potenciaProgramada;
    }

    /** Parte C: suma el tiempo de las pausas al tiempo estimado. */
    public double tiempoEstimadoCarga(double energiaKWh, int pausas, double minutosPorPausa) {
        double horas = tiempoEstimadoCarga(energiaKWh);
        if (horas < 0) {
            return -1;
        }
        return horas + (pausas * minutosPorPausa) / 60.0;
    }

    /* ------------------------------------------------------------------ */
    /* Parte C) Sobrecargas de aumentarPotencia                            */
    /* ------------------------------------------------------------------ */

    /** Sin argumentos: usa INCREMENTO_DEFECTO. */
    public void aumentarPotencia() {
        aumentarPotencia(INCREMENTO_DEFECTO);
    }

    /** Aplica el incremento paso a paso; si un paso no es valido, se detiene. */
    public void aumentarPotencia(double incremento, int veces) {
        for (int i = 1; i <= veces; i++) {
            double nueva = potenciaActual + incremento;
            if (nueva > potenciaMaxima) {
                System.out.println("   ** Paso " + i + " de " + veces + " rechazado: "
                        + nueva + " kW supera el maximo (" + potenciaMaxima + " kW). Se detiene.");
                bitacora.add(new RegistroSesion(
                        "aumentarPotencia(" + incremento + "," + veces + ") paso " + i + " rechazado", false));
                break;
            }
            potenciaActual = nueva;
            bitacora.add(new RegistroSesion(
                    "aumentarPotencia(" + incremento + "," + veces + ") paso " + i, true));
        }
    }

    /* ------------------------------------------------------------------ */
    /* Parte C/D) Sobrecargas de mostrar                                   */
    /* ------------------------------------------------------------------ */

    public void mostrar() {
        mostrar(false);
    }

    public void mostrar(boolean detallado) {
        System.out.println("Fabricante..........: " + fabricante);
        System.out.println("Anio instalacion....: " + anioInstalacion);
        System.out.println("Voltaje nominal.....: " + voltajeNominal + " V");
        System.out.println("Tipo conector.......: " + tipoConector);
        System.out.println("Tipo cargador.......: " + tipoCargador);
        System.out.println("Num. conectores.....: " + numeroConectores);
        System.out.println("Puestos parqueo.....: " + puestosParqueo);
        System.out.println("Potencia maxima.....: " + potenciaMaxima + " kW");
        System.out.println("Ubicacion...........: " + ubicacion);
        System.out.println("Potencia actual.....: " + potenciaActual + " kW");
        if (detallado) {
            System.out.println("Bitacora (" + bitacora.size() + " registros):");
            for (int i = 0; i < bitacora.size(); i++) {
                System.out.println("   " + bitacora.get(i).describir());
            }
        }
    }

    /* ------------------------------------------------------------------ */
    /* Parte D d/e) Metodos estaticos sobre arreglos                       */
    /* ------------------------------------------------------------------ */

    public static int getTotalCargadores() { return totalCargadores; }

    /** Cuenta cargadores por tipo; indice = TipoCargador.ordinal(). Ignora null. */
    public static int[] contarPorTipo(CargadorVE[] flota) {
        int[] conteo = new int[TipoCargador.values().length];
        if (flota == null) {
            return conteo;
        }
        for (CargadorVE c : flota) {
            if (c != null) {
                conteo[c.getTipoCargador().ordinal()]++;
            }
        }
        return conteo;
    }

    /** Mayor potencia actual de la flota; retorna null si no hay datos validos. */
    public static CargadorVE mayorPotencia(CargadorVE[] flota) {
        if (flota == null) {
            return null;
        }
        CargadorVE mayor = null;
        for (CargadorVE c : flota) {
            if (c != null && (mayor == null || c.getPotenciaActual() > mayor.getPotenciaActual())) {
                mayor = c;
            }
        }
        return mayor;
    }

    /** Promedio de la potencia actual de la flota. */
    public static double promedioPotencia(CargadorVE[] flota) {
        if (flota == null || flota.length == 0) {
            return 0;
        }
        double suma = 0;
        int n = 0;
        for (CargadorVE c : flota) {
            if (c != null) {
                suma += c.getPotenciaActual();
                n++;
            }
        }
        return n == 0 ? 0 : suma / n;
    }

    /** Cuenta registros VALIDOS de todas las bitacoras cuya potencia supera LIMITE_RED. */
    public static int excesosDePotenciaContratada(CargadorVE[] flota) {
        int total = 0;
        if (flota == null) {
            return total;
        }
        for (CargadorVE c : flota) {
            if (c != null) {
                for (RegistroSesion r : c.getBitacora()) {
                    if (r.isValido() && r.getPotenciaActual() > LIMITE_RED) {
                        total++;
                    }
                }
            }
        }
        return total;
    }

    /* ------------------------------------------------------------------ */
    /* Parte C) Tres versiones de filtrar (retornan arreglo del tamano     */
    /* exacto de los resultados; ignoran posiciones null y arreglo null)   */
    /* ------------------------------------------------------------------ */

    public static CargadorVE[] filtrar(CargadorVE[] flota, TipoConector criterio) {
        int n = 0;
        if (flota != null) {
            for (CargadorVE c : flota) {
                if (c != null && c.getTipoConector() == criterio) {
                    n++;
                }
            }
        }
        CargadorVE[] res = new CargadorVE[n];
        int i = 0;
        if (flota != null) {
            for (CargadorVE c : flota) {
                if (c != null && c.getTipoConector() == criterio) {
                    res[i++] = c;
                }
            }
        }
        return res;
    }

    public static CargadorVE[] filtrar(CargadorVE[] flota, TipoCargador criterio) {
        int n = 0;
        if (flota != null) {
            for (CargadorVE c : flota) {
                if (c != null && c.getTipoCargador() == criterio) {
                    n++;
                }
            }
        }
        CargadorVE[] res = new CargadorVE[n];
        int i = 0;
        if (flota != null) {
            for (CargadorVE c : flota) {
                if (c != null && c.getTipoCargador() == criterio) {
                    res[i++] = c;
                }
            }
        }
        return res;
    }

    public static CargadorVE[] filtrar(CargadorVE[] flota, Ubicacion criterio) {
        int n = 0;
        if (flota != null) {
            for (CargadorVE c : flota) {
                if (c != null && c.getUbicacion() == criterio) {
                    n++;
                }
            }
        }
        CargadorVE[] res = new CargadorVE[n];
        int i = 0;
        if (flota != null) {
            for (CargadorVE c : flota) {
                if (c != null && c.getUbicacion() == criterio) {
                    res[i++] = c;
                }
            }
        }
        return res;
    }
}

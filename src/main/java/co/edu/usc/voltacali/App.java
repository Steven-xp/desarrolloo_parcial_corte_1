package co.edu.usc.voltacali;

import java.util.Locale;
import co.edu.usc.voltacali.CargadorVE.TipoCargador;
import co.edu.usc.voltacali.CargadorVE.TipoConector;
import co.edu.usc.voltacali.CargadorVE.Ubicacion;

/**
 * Voltacali - Parcial 1.
 * Parte E: caso de prueba obligatorio (P01-P23).
 * Ruta individual: r = N mod 4 = 12 mod 4 = 0 -> cargadoresPorConectores.
 * Parte F: extension personalizada C6 calculada a partir de la cedula.
 */
public class App {

    // Datos del estudiante (cedula 1112053912)
    private static final int N = 12;        // dos ultimos digitos de la cedula
    private static final int d1 = 1;        // penultimo digito
    private static final int d2 = 2;        // ultimo digito
    private static final int r = N % 4;     // ruta individual => 0

    public static void main(String[] args) {
        Locale.setDefault(Locale.US); // decimales con punto

        /* ---------------- Paso 1: crear la flota ---------------- */
        System.out.println("===== PASO 1: CREACION DE LA FLOTA =====");
        CargadorVE c1 = new CargadorVE("ABB", 2023, 400, TipoConector.CCS2,
                TipoCargador.RAPIDO_DC, 2, 2, 60, Ubicacion.UNIVERSIDAD);
        CargadorVE c2 = new CargadorVE("Siemens", 2022, 220, TipoConector.TIPO_2,
                TipoCargador.MURAL, 1, 1, 22, Ubicacion.CENTRO_COMERCIAL);
        CargadorVE c3 = new CargadorVE("Delta", 2024, 800, TipoConector.CCS2,
                TipoCargador.ULTRARRAPIDO, 2, 2, 150, Ubicacion.ESTACION_SERVICIO);
        CargadorVE c4 = new CargadorVE("Wallbox", 2021, 220, TipoConector.TIPO_2,
                TipoCargador.MURAL, 1, 1, 11, Ubicacion.RESIDENCIAL);
        CargadorVE c5 = new CargadorVE("Enel X", 2025, 22); // constructor reducido
        CargadorVE[] flota = new CargadorVE[] { c1, c2, c3, c4, c5 };
        System.out.println("Flota creada con 5 cargadores (C1-C5).");

        /* ---------------- Paso 2: sesion de carga sobre C1 ---------------- */
        System.out.println("\n===== PASO 2: SESION DE CARGA SOBRE C1 =====");

        System.out.println("[P01] setPotenciaActual(40):");
        c1.setPotenciaActual(40);
        System.out.printf("[P01] Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        System.out.println("[P02] aumentarPotencia(15):");
        c1.aumentarPotencia(15);
        System.out.printf("[P02] Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        System.out.printf("[P03] tiempoEstimadoCarga(66): %.2f horas%n", c1.tiempoEstimadoCarga(66));

        System.out.println("[P04] aumentarPotencia(10) (debe ser rechazado):");
        c1.aumentarPotencia(10);
        System.out.printf("[P04] Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        System.out.println("[P05] reducirPotencia(30):");
        c1.reducirPotencia(30);
        System.out.printf("[P05] Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        System.out.printf("[P06] tiempoEstimadoCarga(50, 2 pausas de 15 min): %.2f horas%n",
                c1.tiempoEstimadoCarga(50, 2, 15));

        System.out.printf("[P07] tiempoEstimadoCarga(50, potencia programada 40.0): %.2f horas%n",
                c1.tiempoEstimadoCarga(50, 40.0));

        System.out.println("[P08] aumentarPotencia() (incremento por defecto "
                + CargadorVE.INCREMENTO_DEFECTO + "):");
        c1.aumentarPotencia();
        System.out.printf("[P08] Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        System.out.println("[P09] aumentarPotencia(5, 3):");
        c1.aumentarPotencia(5, 3);
        System.out.printf("[P09] Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        System.out.println("[P10] reducirPotencia(50) (debe ser rechazado):");
        c1.reducirPotencia(50);
        System.out.printf("[P10] Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        System.out.println("[P11] cortarCarga():");
        c1.cortarCarga();
        System.out.printf("[P11] Potencia C1: %.1f kW%n", c1.getPotenciaActual());

        System.out.printf("[P12] tiempoEstimadoCarga(10) con potencia en cero: %.2f%n",
                c1.tiempoEstimadoCarga(10));

        /* ---------------- Paso 3: operaciones sobre el resto de la flota ---------------- */
        System.out.println("\n===== PASO 3: OPERACIONES SOBRE EL RESTO DE LA FLOTA =====");
        System.out.println("[P13]");
        c2.setPotenciaActual(22);
        c3.setPotenciaActual(120);
        c4.aumentarPotencia(7.4);
        c5.aumentarPotencia(30);
        System.out.printf("[P13] Potencia final C2: %.1f kW%n", c2.getPotenciaActual());
        System.out.printf("[P13] Potencia final C3: %.1f kW%n", c3.getPotenciaActual());
        System.out.printf("[P13] Potencia final C4: %.1f kW%n", c4.getPotenciaActual());
        System.out.printf("[P13] Potencia final C5: %.1f kW%n", c5.getPotenciaActual());

        /* ---------------- Paso 4: estadisticas y validaciones ---------------- */
        System.out.println("\n===== PASO 4: ESTADISTICAS Y VALIDACIONES =====");

        System.out.println("[P14] Conteo por tipo de cargador:");
        int[] conteo = CargadorVE.contarPorTipo(flota);
        for (TipoCargador t : TipoCargador.values()) {
            System.out.println("[P14]   " + t + ": " + conteo[t.ordinal()]);
        }

        System.out.printf("[P15] Promedio de potencia actual de la flota: %.2f kW%n",
                CargadorVE.promedioPotencia(flota));

        CargadorVE mayor = CargadorVE.mayorPotencia(flota);
        if (mayor != null) {
            System.out.printf("[P16] Mayor potencia actual: %s con %.1f kW%n",
                    mayor.getFabricante(), mayor.getPotenciaActual());
        } else {
            System.out.println("[P16] No hay cargadores en la flota.");
        }

        System.out.println("[P17] Registros validos con potencia sobre el limite de red ("
                + CargadorVE.LIMITE_RED + " kW): " + CargadorVE.excesosDePotenciaContratada(flota));

        System.out.println("[P18] Filtros sobre la flota:");
        CargadorVE[] fConector = CargadorVE.filtrar(flota, TipoConector.TIPO_2);
        CargadorVE[] fTipo = CargadorVE.filtrar(flota, TipoCargador.MURAL);
        CargadorVE[] fUbic = CargadorVE.filtrar(flota, Ubicacion.UNIVERSIDAD);
        System.out.println("[P18] TIPO_2      -> cantidad: " + fConector.length
                + ", fabricantes: " + fabricantes(fConector));
        System.out.println("[P18] MURAL       -> cantidad: " + fTipo.length
                + ", fabricantes: " + fabricantes(fTipo));
        System.out.println("[P18] UNIVERSIDAD -> cantidad: " + fUbic.length
                + ", fabricantes: " + fabricantes(fUbic));

        System.out.println("[P19] Valores por defecto del constructor reducido (C5):");
        c5.mostrar(false);

        System.out.println("[P20] Constructor copia a partir de C3:");
        CargadorVE copia = new CargadorVE(c3);
        System.out.println("[P20] Copia - Fabricante: " + copia.getFabricante());
        System.out.printf("[P20] Copia - Potencia actual: %.1f kW%n", copia.getPotenciaActual());
        System.out.println("[P20] Copia - Tamano bitacora: " + copia.getBitacora().size());
        System.out.println("[P20] Total de cargadores creados: " + CargadorVE.getTotalCargadores());

        System.out.println("[P21] Bitacora completa de C1:");
        c1.mostrar(true);

        System.out.println("[P22] contadorRegistros global: " + CargadorVE.contadorRegistros);

        System.out.println("[P23] Validaciones con posiciones null y arreglo null:");
        System.out.printf("[P23] promedioPotencia({C1, null, C3}): %.2f kW%n",
                CargadorVE.promedioPotencia(new CargadorVE[] { c1, null, c3 }));
        int[] conteoNull = CargadorVE.contarPorTipo(null);
        System.out.println("[P23] contarPorTipo(null) no lanzo excepcion. Longitud: " + conteoNull.length);

        /* ---------------- Ruta individual ---------------- */
        System.out.println("\n===== RUTA INDIVIDUAL =====");
        System.out.println("[R] N = " + N + ", r = N mod 4 = " + r);
        switch (r) {
            case 0: {
                int buscados = N % 3 + 1;
                System.out.println("[R] Ruta 0: cargadoresPorConectores(flota, " + buscados + ")");
                CargadorVE[] res = cargadoresPorConectores(flota, buscados);
                if (res.length == 0) {
                    System.out.println("[R] No hay cargadores con " + buscados + " conectores.");
                } else {
                    System.out.println("[R] Cantidad: " + res.length
                            + ", fabricantes: " + fabricantes(res));
                }
                break;
            }
            case 1: {
                TipoConector tc = TipoConector.values()[N % 5];
                System.out.println("[R] Ruta 1: promedioVoltajePorConector(flota, " + tc + ")");
                double prom = promedioVoltajePorConector(flota, tc);
                if (prom < 0) {
                    System.out.println("[R] No existen cargadores con conector " + tc + ".");
                } else {
                    System.out.printf("[R] Promedio de voltaje: %.2f V%n", prom);
                }
                break;
            }
            case 2: {
                System.out.println("[R] Ruta 2: tipoMasFrecuente(flota)");
                tipoMasFrecuente(flota);
                break;
            }
            default:
                System.out.println("[R] Ruta 3: sesionesSobreLimiteRed(flota)");
                sesionesSobreLimiteRed(flota);
                break;
        }

        /* ---------------- Parte F: extension personalizada C6 ---------------- */
        System.out.println("\n===== PARTE F: EXTENSION PERSONALIZADA C6 =====");

        // Todos los valores de C6 se calculan en codigo a partir de N, d1 y d2
        CargadorVE c6 = new CargadorVE(
                "USC-" + N,                        // fabricante
                2015 + d2,                         // anioInstalacion
                (N % 2 == 0) ? 220 : 400,          // voltajeNominal
                TipoConector.values()[N % 5],      // tipoConector
                TipoCargador.values()[N % 6],      // tipoCargador
                d1 % 3 + 1,                        // numeroConectores
                d2 % 4 + 1,                        // puestosParqueo
                20 + N,                            // potenciaMaxima (kW)
                Ubicacion.values()[N % 8]);        // ubicacion

        System.out.println("[X01] N = " + N + ", d1 = " + d1 + ", d2 = " + d2);
        c6.mostrar(false);

        System.out.println("[X02] setPotenciaActual(potenciaMaxima / 2) y aumentarPotencia(d2+5, d1+1):");
        c6.setPotenciaActual(c6.getPotenciaMaxima() / 2);
        System.out.printf("[X02] Potencia tras el set: %.1f kW%n", c6.getPotenciaActual());
        int pasosAntes = c6.getBitacora().size();
        c6.aumentarPotencia(d2 + 5, d1 + 1);
        int rechazos = 0;
        for (int i = pasosAntes; i < c6.getBitacora().size(); i++) {
            if (!c6.getBitacora().get(i).isValido()) {
                rechazos++;
            }
        }
        System.out.printf("[X02] Potencia final: %.1f kW.%n", c6.getPotenciaActual());
        System.out.println("[X02] Pasos rechazados: " + (rechazos > 0
                ? "SI, " + rechazos + " paso(s) fueron rechazados." : "ninguno."));

        System.out.printf("[X03] tiempoEstimadoCarga(N + 10 = " + (N + 10) + "): %.2f horas%n",
                c6.tiempoEstimadoCarga(N + 10));

        System.out.println("[X04] Creacion de flotaExtendida (flota + C6):");
        CargadorVE[] flotaExtendida = new CargadorVE[flota.length + 1];
        for (int i = 0; i < flota.length; i++) {
            flotaExtendida[i] = flota[i];
        }
        flotaExtendida[flotaExtendida.length - 1] = c6;
        System.out.println("[X04] flotaExtendida tiene " + flotaExtendida.length + " cargadores.");

        System.out.println("[X05] Estadisticas sobre flotaExtendida:");
        int[] conteoExt = CargadorVE.contarPorTipo(flotaExtendida);
        for (TipoCargador t : TipoCargador.values()) {
            System.out.println("[X05]   " + t + ": " + conteoExt[t.ordinal()]);
        }
        System.out.printf("[X05] promedioPotencia: %.2f kW%n",
                CargadorVE.promedioPotencia(flotaExtendida));
        CargadorVE mayorExt = CargadorVE.mayorPotencia(flotaExtendida);
        if (mayorExt != null) {
            System.out.printf("[X05] mayorPotencia: %s con %.1f kW%n",
                    mayorExt.getFabricante(), mayorExt.getPotenciaActual());
        }
        System.out.println("[X05] excesosDePotenciaContratada: "
                + CargadorVE.excesosDePotenciaContratada(flotaExtendida));

        System.out.println("[X06] getTotalCargadores(): " + CargadorVE.getTotalCargadores());
        System.out.println("[X06] contadorRegistros: " + CargadorVE.contadorRegistros);
        System.out.println("[X06] Bitacora de C6:");
        c6.mostrar(true);
    }

    /* ------------------------------------------------------------------ */
    /* Ayudantes                                                           */
    /* ------------------------------------------------------------------ */

    /** Une los fabricantes de un arreglo en una cadena legible. */
    private static String fabricantes(CargadorVE[] arr) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] != null) {
                if (sb.length() > 0) {
                    sb.append(", ");
                }
                sb.append(arr[i].getFabricante());
            }
        }
        return sb.length() == 0 ? "-" : sb.toString();
    }

    /* ---------------- Metodos de las rutas individuales ---------------- */

    /** Ruta r=0: arreglo nuevo con los cargadores que tienen exactamente `conectores`. */
    public static CargadorVE[] cargadoresPorConectores(CargadorVE[] flota, int conectores) {
        int n = 0;
        if (flota != null) {
            for (CargadorVE c : flota) {
                if (c != null && c.getNumeroConectores() == conectores) {
                    n++;
                }
            }
        }
        CargadorVE[] res = new CargadorVE[n];
        int i = 0;
        if (flota != null) {
            for (CargadorVE c : flota) {
                if (c != null && c.getNumeroConectores() == conectores) {
                    res[i++] = c;
                }
            }
        }
        return res;
    }

    /** Ruta r=1: promedio de voltaje nominal del conector dado (-1 si no hay). */
    public static double promedioVoltajePorConector(CargadorVE[] flota, TipoConector conector) {
        if (flota == null) {
            return -1;
        }
        double suma = 0;
        int n = 0;
        for (CargadorVE c : flota) {
            if (c != null && c.getTipoConector() == conector) {
                suma += c.getVoltajeNominal();
                n++;
            }
        }
        return n == 0 ? -1 : suma / n;
    }

    /** Ruta r=2: usa contarPorTipo e informa todos los tipos empatados en maximo. */
    public static void tipoMasFrecuente(CargadorVE[] flota) {
        int[] conteo = CargadorVE.contarPorTipo(flota);
        int max = -1;
        for (int v : conteo) {
            if (v > max) {
                max = v;
            }
        }
        if (max <= 0) {
            System.out.println("[R] No hay cargadores para determinar el tipo mas frecuente.");
            return;
        }
        StringBuilder empatados = new StringBuilder();
        for (TipoCargador t : TipoCargador.values()) {
            if (conteo[t.ordinal()] == max) {
                if (empatados.length() > 0) {
                    empatados.append(", ");
                }
                empatados.append(t);
            }
        }
        System.out.println("[R] Tipo(s) mas frecuente(s) con " + max + ": " + empatados);
    }

    /** Ruta r=3: recorre las bitacoras y muestra los registros validos sobre LIMITE_RED. */
    public static void sesionesSobreLimiteRed(CargadorVE[] flota) {
        int encontrados = 0;
        if (flota != null) {
            for (CargadorVE c : flota) {
                if (c != null) {
                    for (CargadorVE.RegistroSesion rs : c.getBitacora()) {
                        if (rs.isValido() && rs.getPotenciaActual() > CargadorVE.LIMITE_RED) {
                            System.out.println("[R]   " + rs.describir());
                            encontrados++;
                        }
                    }
                }
            }
        }
        if (encontrados == 0) {
            System.out.println("[R] No hay sesiones validas sobre el limite de red ("
                    + CargadorVE.LIMITE_RED + " kW).");
        } else {
            System.out.println("[R] Total de sesiones sobre el limite de red: " + encontrados);
        }
    }
}

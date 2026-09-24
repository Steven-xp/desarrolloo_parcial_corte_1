# Parcial 1 - VoltaCali S.A.S. (parcial1-voltacali)

Sistema de gestión de cargadores para vehículos eléctricos (prototipo en consola, Java 8 + Maven).

## Datos del estudiante

| Dato | Valor |
|---|---|
| Nombre | Howard Steven Meneses Moreno |
| Código | 1112053912 |
| N (dos últimos dígitos de la cédula) | 12 |
| r = N mod 4 | 0 |
| Ruta asignada | **Ruta 0: `cargadoresPorConectores(CargadorVE[] flota, int conectores)`** con `N mod 3 + 1 = 1` conector buscado |

## Estructura del proyecto

```
parcial1-voltacali/
├── pom.xml
├── .gitignore
├── README.md
├── src/
│   ├── main/java/co/edu/usc/voltacali/
│   │   ├── App.java
│   │   └── CargadorVE.java
│   └── test/java/co/edu/usc/voltacali/
│       └── AppTest.java
└── docs/
    └── captura.png
```

## Comandos para compilar y ejecutar

Desde la raíz del repositorio:

```bash
mvn clean compile
java -cp target/classes co.edu.usc.voltacali.App
```

## Clase CargadorVE (Partes A–D)

- **Parte A:** diez atributos `private` (`fabricante`, `anioInstalacion`, `voltajeNominal`, `tipoConector`, `tipoCargador`, `numeroConectores`, `puestosParqueo`, `potenciaMaxima`, `ubicacion`, `potenciaActual`) con sus getters/setters. `setPotenciaActual` rechaza valores negativos o mayores que `potenciaMaxima`, informa por consola y registra el intento inválido en la bitácora.
- **Parte B:** `aumentarPotencia(double)`, `reducirPotencia(double)`, `cortarCarga()`, `tiempoEstimadoCarga(double)` y `mostrar()`, con validación de rango y mensaje en los rechazos.
- **Parte C:** familias sobrecargadas de constructores (completo / reducido con `this(...)` / copia), `aumentarPotencia()` `(double)` `(double,int)`, `tiempoEstimadoCarga` (3 firmas), `filtrar` (3 criterios) y `mostrar(boolean)`.
- **Parte D:** enums anidados `TipoConector`, `TipoCargador`, `Ubicacion`; clase interna no estática `RegistroSesion` (captura datos del objeto externo y numera con `contadorRegistros`); bitácora `Vector<RegistroSesion>`; métodos estáticos `contarPorTipo`, `mayorPotencia`, `promedioPotencia`, `excesosDePotenciaContratada`, `getTotalCargadores`, constantes `LIMITE_RED = 50.0` e `INCREMENTO_DEFECTO = 5.0`. Todos los métodos que reciben arreglos ignoran posiciones `null` y toleran el arreglo `null`.

## Parte E: caso de prueba obligatorio

`App.main` ejecuta en orden los pasos P01–P23 (flota C1–C5, sesión de carga sobre C1, operaciones sobre el resto, estadísticas y validaciones), cada línea de salida con su código entre corchetes y los tiempos con dos decimales.

Resultados clave verificados: `[P02] Potencia C1: 55.0 kW`, `[P03] 1.20 horas`, `[P06] 2.50 horas`, `[P07] 1.25 horas`, `[P15] 29.88 kW`, `[P16] Delta con 120.0 kW`, `[P17] 2`, `[P22] contadorRegistros = 14`; P04, P10 y el aumento de C5 en P13 son rechazados correctamente.

## Ruta individual (r = 0)

Se ejecuta sobre la flota después de P23, con el código `[R]`:

```
[R] N = 12, r = N mod 4 = 0
[R] Ruta 0: cargadoresPorConectores(flota, 1)
[R] Cantidad: 3, fabricantes: Siemens, Wallbox, Enel X
```

El método `cargadoresPorConectores` retorna un arreglo nuevo del tamaño exacto de los resultados, ignora posiciones `null` e informa cuando no hay coincidencias.

## Parte F: extensión personalizada C6

Los valores de C6 se **calculan en el código** a partir de `N = 12`, `d1 = 1`, `d2 = 2` (constantes declaradas en `App`):

| Atributo de C6 | Regla | Valor calculado |
|---|---|---|
| fabricante | "USC-" + N | USC-12 |
| anioInstalacion | 2015 + d2 | 2017 |
| voltajeNominal | 220 si N es par; 400 si N es impar | 220 V (N par) |
| tipoConector | TipoConector.values()[N % 5] → 12 % 5 = 2 | CCS2 |
| tipoCargador | TipoCargador.values()[N % 6] → 12 % 6 = 0 | MURAL |
| numeroConectores | d1 % 3 + 1 → 1 % 3 + 1 | 2 |
| puestosParqueo | d2 % 4 + 1 → 2 % 4 + 1 | 3 |
| potenciaMaxima | 20 + N | 32.0 kW |
| ubicacion | Ubicacion.values()[N % 8] → 12 % 8 = 4 | RESIDENCIAL |

Pasos ejecutados con código `[X01]`–`[X06]`:

- **X02:** arranca en `32/2 = 16.0 kW` y `aumentarPotencia(d2+5 = 7, d1+1 = 2)` sube a 23.0 y luego a **30.0 kW**; ningún paso es rechazado (un hipotético tercer paso daría 37.0 > 32.0, pero solo se piden 2 pasos).
- **X03:** `tiempoEstimadoCarga(N + 10 = 22)` = 22 / 30 = **0.73 horas**.
- **X04:** `flotaExtendida` de 6 cargadores construida con un ciclo + C6 al final.
- **X05:** conteo por tipo (MURAL: 3, PEDESTAL: 1, RAPIDO_DC: 1, ULTRARRAPIDO: 1), promedio 29.90 kW, mayor Delta 120.0 kW, excesos sobre la red: 2.
- **X06:** `getTotalCargadores() = 7`, `contadorRegistros = 17`, bitácora de C6 con 3 registros (#15–#17).

## Captura de ejecución

En `docs/captura.png` debe estar la captura de la ejecución completa desde Visual Studio Code (los comandos `mvn clean compile` y `java -cp target/classes co.edu.usc.voltacali.App` con toda la salida visible).

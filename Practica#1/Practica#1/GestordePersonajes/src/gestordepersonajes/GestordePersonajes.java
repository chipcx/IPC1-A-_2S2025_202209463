/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package gestordepersonajes;
import java.util.InputMismatchException;
import java.util.Scanner;


public class GestordePersonajes {

     static String[] nombres = new String[100];
    static String[] armas = new String[100];
    static String[][] habilidades = new String[100][5];
    static int[] niveles = new int[100];
    static int contador = 0;
    static String[] historialPeleas = new String[100];
    static int totalPeleas = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean salir = false;
        int opcion;

        while (!salir){
            System.out.println("1. Agregar personaje");
            System.out.println("2. Modificar personaje");
            System.out.println("3. Eliminar personaje");
            System.out.println("4. Ver Datos de un Personaje");
            System.out.println("5. Ver listado de Personajes");
            System.out.println("6. Realizar Pelea entre Personajes");
            System.out.println("7. Ver Historial de Peleas");
            System.out.println("8. Ver Datos de Estudiante");
            System.out.println("9. Salir");
           
            try {
                opcion = sc.nextInt();
                sc.nextLine();
            switch (opcion){
                case 1 -> agregarpersonaje();
                case 2 -> modificarPersonaje();
                case 3 -> eliminarPersonaje();
                case 4 -> verDatosPersonaje();
                case 5 -> verListado();
                case 6 -> realizarPelea();
                case 7 -> verHistorialPeleas();
                case 8 -> verDatosEstudiante();
                case 9 -> salir = true;
                default -> System.out.println("Las opciones son entre 1 y 9");
            }
            } catch (InputMismatchException e){
                System.out.println("Escribe un numero valido");
                sc.next();
            }
         }
        
    }

public static void agregarpersonaje() {
    Scanner sc = new Scanner(System.in);

    System.out.print("Nombre del personaje: ");
    String nombre = sc.nextLine();

    for (int i = 0; i < contador; i++) {
        if (nombres[i] != null && nombres[i].equalsIgnoreCase(nombre)) {
            System.out.println("El nombre del personaje ya existe dentro del juego.");
            return;
        }
    }

    System.out.print("Arma del personaje: ");
    String arma = sc.nextLine();

    habilidades[contador] = new String[5]; 

    System.out.println("Ingrese hasta 5 habilidades para el personaje:");
    for (int i = 0; i < 5; i++) {
        System.out.print("Habilidad " + (i + 1) + ": ");
        String habilidad = sc.nextLine();
        if (habilidad.isEmpty()) break;
        habilidades[contador][i] = habilidad;
    }

    System.out.print("Nivel de poder (1–100): ");
    int nivel = 0;
    try {
        nivel = sc.nextInt();
        sc.nextLine();
        if (nivel < 1 || nivel > 100) {
            System.out.println("Nivel no válido para personajes (1–100).");
            return;
        }
    } catch (InputMismatchException e) {
        System.out.println("Nivel inválido. Debe ser un número.");
        sc.nextLine();
        return;
    }

    nombres[contador] = nombre;
    armas[contador] = arma;
    niveles[contador] = nivel;

    int id = contador + 1;
    System.out.println(" Personaje agregado con ID #" + id);
    contador++;
}

public static void modificarPersonaje() {
    Scanner sc = new Scanner(System.in);

    if (contador == 0) {
        System.out.println("No hay personajes para modificar.");
        return;
    }

    System.out.print("Ingrese el ID o nombre del personaje: ");
    String entrada = sc.nextLine().trim();

    int i = -1;

    try {
        int id = Integer.parseInt(entrada);
        if (id >= 1 && id <= contador) {
            i = id - 1;
        } else {
            System.out.println("ID fuera de rango.");
            return;
        }
    } catch (NumberFormatException e) {
        int coincidencias = 0;
        for (int j = 0; j < contador; j++) {
            if (nombres[j] != null && nombres[j].equalsIgnoreCase(entrada)) {
                i = j;
                coincidencias++;
            }
        }

        if (coincidencias == 0) {
            System.out.println("No se encontró ese nombre.");
            return;
        } else if (coincidencias > 1) {
            System.out.println("El nombre no es único. Usa el ID.");
            return;
        }
    }

    // Mostrar datos actuales
    System.out.println("DATOS ACTUALES");
    System.out.println("Nombre: " + nombres[i]);
    System.out.println("Arma: " + armas[i]);

    System.out.print("Habilidades: ");
    if (habilidades[i] != null) {
        for (int j = 0; j < 5; j++) {
            if (habilidades[i][j] != null) {
                System.out.print(habilidades[i][j] + " ");
            }
        }
    } else {
        System.out.print("Este personaje no tiene habilidades registradas.");
    }

    System.out.println("Nivel de poder: " + niveles[i]);

    // Modificar directamente
    System.out.println("INGRESA NUEVOS DATOS");

    System.out.print("Nueva arma: ");
    String nuevaArma = sc.nextLine();
    if (!nuevaArma.isEmpty()) armas[i] = nuevaArma;

    if (habilidades[i] == null) {
        habilidades[i] = new String[5];
    }

    for (int j = 0; j < 5; j++) {
        System.out.print("Nueva habilidad " + (j + 1) + " : ");
        String nuevaHab = sc.nextLine();
        if (!nuevaHab.isEmpty()) habilidades[i][j] = nuevaHab;
    }

    System.out.print("Nuevo nivel de poder: ");
    String nivelTexto = sc.nextLine();
    if (!nivelTexto.isEmpty()) {
        try {
            int nuevoNivel = Integer.parseInt(nivelTexto);
            if (nuevoNivel >= 1 && nuevoNivel <= 100) {
                niveles[i] = nuevoNivel;
            } else {
                System.out.println("Nivel fuera de rango");
            }
        } catch (NumberFormatException e) {
            System.out.println("Nivel no valido");
        }
    }

    System.out.println("El personaje modificado correctamente.");
}

public static void eliminarPersonaje() {
    Scanner sc = new Scanner(System.in);

    if (contador == 0) {
        System.out.println("No hay personajes registrados.");
        return;
    }

    System.out.print("Ingrese el ID del personaje a eliminar: ");
    int id = sc.nextInt();
    sc.nextLine(); 

    if (id < 1 || id > contador) {
        System.out.println("ID no válido.");
        return;
    }

    int i = id - 1;

  
    System.out.println("DATOS DEL PERSONAJE SELECIONADO");
    System.out.println("Nombre: " + nombres[i]);
    System.out.println("Arma: " + armas[i]);
    System.out.print("Habilidades: ");
    if (habilidades[i] != null) {
        for (int j = 0; j < 5; j++) {
            if (habilidades[i][j] != null) {
                System.out.print(habilidades[i][j] + " ");
            }
        }
    } else {
        System.out.print("No tiene habilidades registradas.");
    }
    System.out.println("Nivel de poder: " + niveles[i]);

    System.out.print("¿Deseas eliminar el personaje? (si/no): ");
    String confirmacion = sc.nextLine();

   if (!confirmacion.equalsIgnoreCase("s") && !confirmacion.equalsIgnoreCase("si")) {
    System.out.println("Eliminación cancelada.");
    return;
    }
    for (int j = i; j < contador - 1; j++) {
        nombres[j] = nombres[j + 1];
        armas[j] = armas[j + 1];
        niveles[j] = niveles[j + 1];
        habilidades[j] = habilidades[j + 1];
    }

    nombres[contador - 1] = null;
    armas[contador - 1] = null;
    niveles[contador - 1] = 0;
    habilidades[contador - 1] = null;

    contador--;
    System.out.println("Personaje ha sido eliminado correctamente :D .");
}

public static void verDatosPersonaje() {
    Scanner sc = new Scanner(System.in);

    if (contador == 0) {
        System.out.println("No hay personajes registrados.");
        return;
    }
    System.out.print("Ingrese el ID del personaje que desea");
    
    int id = sc.nextInt();
    sc.nextLine();
    if (id < 1 || id > contador) {
        System.out.println("ID no valido.");
        return;
    }

    int i = id - 1;

    System.out.println("DATOS DEL PERSONAJE");
    System.out.println("ID: " + id);
    System.out.println("Nombre: " + nombres[i]);
    System.out.println("Arma: " + armas[i]);

    System.out.println("Habilidades:");
    for (int j = 0; j < 5; j++) {
        if (habilidades[i][j] != null) {
            System.out.println("- " + habilidades[i][j]);
        }
    }

    System.out.println("Nivel de poder: " + niveles[i]);
}


public static void verListado() {
    if (contador == 0) {
        System.out.println("No existen personajes disponibles para mostrar.");
        return;
    }

    System.out.println("LISTADO DE PERSONAJES");
    for (int i = 0; i < contador; i++) {
        int id = i + 1;
        System.out.println("ID: " + id + " | Nombre: " + nombres[i] + " | Nivel de poder: " + niveles[i]);
    }
    
}

public static void realizarPelea() {
    Scanner sc = new Scanner(System.in);

    if (contador < 2) {
        System.out.println("Se necesitan al menos 2 personajes para realizar una pelea.");
        return;
    }

    System.out.print("Ingrese el ID del primer personaje: ");
    int id1 = sc.nextInt();
    sc.nextLine();

    System.out.print("Ingrese el ID del segundo personaje: ");
    int id2 = sc.nextInt();
    sc.nextLine();

    if (id1 < 1 || id1 > contador || id2 < 1 || id2 > contador || id1 == id2) {
        System.out.println("IDs no valido , personaje repetido.");
        return;
    }

    int i1 = id1 - 1;
    int i2 = id2 - 1;

    System.out.println(" Enfrentamiento:");
    System.out.println(nombres[i1] + " (Nivel " + niveles[i1] + ") vs " + nombres[i2] + " (Nivel " + niveles[i2] + ")");

    java.time.LocalDateTime fechaHora = java.time.LocalDateTime.now();
    String registro = "Pelea entre " + nombres[i1] + " y " + nombres[i2] + " | " + fechaHora;

    historialPeleas[totalPeleas] = registro;
    totalPeleas++;

    System.out.println("Su pelea fue registrada el " + fechaHora);

}
public static void verHistorialPeleas() {
    if (totalPeleas == 0) {
        System.out.println(" No hay peleas registradas");
        return;
    }

    System.out.println("HISTORIAL DE PELEAS");
    for (int i = 0; i < totalPeleas; i++) {
        System.out.println((i + 1) + ". " + historialPeleas[i]);
    }
}

public static void verDatosEstudiante() {
    System.out.println(" DATOS DEL ESTUDIANTE");
    System.out.println("Nombre: Carlos David Recinos Chip");
    System.out.println("Carnet: 202209463");
    System.out.println("Curso: Introduccion ala programacion y Computacion 1");
    System.out.println("Sección: A");
    
}

}


    


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto1;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;




public class PROYECTO1 {
    static String[] nombres = new String[50];
static String[] categorias = new String[50];
static double[] precios = new double[50];
static int[] cantidades = new int[50];
static String[] codigos = new String[50];
static int contadorProductos = 0;
static String usuarioActual = "Carlos Chip";
static ArrayList<String> bitacora = new ArrayList<>();
static ArrayList<String> ventas = new ArrayList<>();





    public static void main(String[] args) {
       Scanner mn = new Scanner(System.in);
       boolean salir = false;
       int opcion;
       
       while (!salir){
             System.out.println("1. Agregar Producto.");
             System.out.println("2. Buscar Producto.");
             System.out.println("3. Eliminar Producto.");
             System.out.println("4. Registrar Venta ");
             System.out.println("5. Generar Reporte ");
             System.out.println("6. Ver datos del estudiante ");
             System.out.println("7. Bitacora ");
             System.out.println("8. Salir ");
             System.out.println("Ingresa que opcion deseas realizar: ");
             opcion = mn.nextInt();
             try{
             switch (opcion){
                 case 1 -> agregarProducto();
                 case 2 -> buscarProducto();
                 case 3 -> eliminarProducto();
                 case 4 -> registrarVenta();
                 case 5 -> generarReportesPDF();
                 case 6 -> verDatosEstudiante();
                 case 7 -> mostrarBitacora();
                 case 8 -> salir = true;
                 default -> System.out.println("Las opciones son entrer 1 y 8");
              
                }
                } catch (InputMismatchException e){
                    System.out.println("Debes escribir un numero");
                    mn.next();
                }
            }
        }
    // termina el codigo del menu
    
    // incia metodo para agregar producto
    public static void agregarProducto() {
    Scanner entrada = new Scanner(System.in);
    System.out.println("   AGREGAR PRODUCTO  ");

    // Nombre del producto
    System.out.print("Nombre del producto: ");
    String nombre = entrada.nextLine();
    if (nombre.trim().isEmpty()) {
        System.out.println("️El nombre no puede estar vacío.");
        registrarBitacora("Agregar producto - nombre vacio", false);
        return;
    }

    // Categoría
    System.out.print("Categoria: ");
    String categoria = entrada.nextLine();
    if (categoria.trim().isEmpty()) {
        System.out.println("La categoria no puede estar vacia.");
        registrarBitacora("Agregar producto - categoria vacia", false);
        return;
    }

    // Código único
    String codigo;
    boolean codigoValido;
    do {
        System.out.print("Codigo unico: ");
        codigo = entrada.nextLine();
        codigoValido = true;

        for (int i = 0; i < contadorProductos; i++) {
            if (codigos[i].equalsIgnoreCase(codigo)) {
                System.out.println("️Ese codigo ya existe.");
                registrarBitacora("Agregar producto - codigo repetido", false);
                codigoValido = false;
                break;
            }
        }
    } while (!codigoValido);

    // Precio
    double precio = -1;
    do {
        System.out.print("Precio: ");
        if (!entrada.hasNextDouble()) {
            System.out.println("️ Entrada invalida. Debe ser un numero.");
            entrada.next(); // limpiar entrada
            registrarBitacora("Agregar producto - precio no numerico", false);
            continue;
        }

        precio = entrada.nextDouble();
        if (precio <= 0) {
            System.out.println("️ El precio debe ser positivo.");
            registrarBitacora("Agregar producto - precio negativo", false);
        }
    } while (precio <= 0);

    // Cantidad
    int cantidad = -1;
    do {
        System.out.print("Cantidad en stock: ");
        if (!entrada.hasNextInt()) {
            System.out.println("️ Entrada invalida. Debe ser un numero entero.");
            entrada.next(); // limpiar entrada
            registrarBitacora("Agregar producto - cantidad no numerica", false);
            continue;
        }

        cantidad = entrada.nextInt();
        if (cantidad < 0) {
            System.out.println("️ La cantidad no puede ser negativa.");
            registrarBitacora("Agregar producto - cantidad negativa", false);
        }
    } while (cantidad < 0);

    // Almacenar en vectores
    nombres[contadorProductos] = nombre;
    categorias[contadorProductos] = categoria;
    codigos[contadorProductos] = codigo;
    precios[contadorProductos] = precio;
    cantidades[contadorProductos] = cantidad;
    contadorProductos++;

    System.out.println("Producto agregado correctamente.");
    registrarBitacora("Agrego un tipo de producto", true);
}

    // termina metodo para agregar producto
    
    // incia metodo para buscar producto
   public static void buscarProducto() {
    Scanner buscar = new Scanner(System.in);
    System.out.println("  BUSCAR PRODUCTO ");
    System.out.println("1. Nombre\n2. Categoria\n3. Codigo");
    System.out.print("Opcion: ");
    int criterio = buscar.nextInt();
    buscar.nextLine();

    System.out.print("Producto a buscar: ");
    String valor = buscar.nextLine().toLowerCase();

    boolean encontrado = false;

    for (int i = 0; i < contadorProductos; i++) {
        boolean coincide = false;
        switch (criterio) {
            case 1 -> coincide = nombres[i].toLowerCase().contains(valor);
            case 2 -> coincide = categorias[i].toLowerCase().contains(valor);
            case 3 -> coincide = codigos[i].toLowerCase().equals(valor);
            default -> {
                System.out.println("Opcion invalida.");
                registrarBitacora("Buscar producto", false);
                return;
            }
        }

        if (coincide) {
            System.out.println(" Producto encontrado:");
            System.out.println("Nombre: " + nombres[i]);
            System.out.println("Categoria: " + categorias[i]);
            System.out.println("Codigo: " + codigos[i]);
            System.out.println("Precio: Q" + precios[i]);
            System.out.println("Stock: " + cantidades[i]);
            encontrado = true;
        }
    }

    registrarBitacora("Buscar producto", encontrado);
    if (!encontrado) {
        System.out.println("No se encontró el producto.");
    }
}

    // termina el metodo para buscar producto
    
    // incia metodo de eliminacion 
    public static void eliminarProducto() {
    Scanner eliminar = new Scanner(System.in);
    System.out.println(" ELIMINAR PRODUCTO ");
    System.out.print("Codigo del producto: ");
    String codigoBuscar = eliminar.nextLine().toLowerCase();

    boolean eliminado = false;

    for (int i = 0; i < contadorProductos; i++) {
        if (codigos[i].toLowerCase().equals(codigoBuscar)) {
            System.out.println("Producto: " + nombres[i]);
            System.out.print("¿Eliminar? (si/no): ");
            String confirmacion = eliminar.nextLine().toLowerCase();

            if (confirmacion.equals("si")) {
                for (int j = i; j < contadorProductos - 1; j++) {
                    nombres[j] = nombres[j + 1];
                    categorias[j] = categorias[j + 1];
                    codigos[j] = codigos[j + 1];
                    precios[j] = precios[j + 1];
                    cantidades[j] = cantidades[j + 1];
                }
                contadorProductos--;
                System.out.println("Producto eliminado.");
                eliminado = true;
            } else {
                System.out.println("Eliminacion cancelada.");
            }

            break;
        }
    }

    registrarBitacora("Eliminar producto", eliminado);
    if (!eliminado) {
        System.out.println(" Producto no encontrado o no eliminado.");
    }
}


  // termina metodo para eliminar producto 
    
  // Inicia metodo para regristra la venta
   
public static void registrarVenta() {
    Scanner venta = new Scanner(System.in);
    System.out.println("   REGISTRAR VENTA  ");
    System.out.print("Codigo del producto: ");
    String codigoVenta = venta.nextLine().toLowerCase();

    boolean ventaExitosa = false;
    int indice = -1;

    for (int i = 0; i < contadorProductos; i++) {
        if (codigos[i].toLowerCase().equals(codigoVenta)) {
            indice = i;
            break;
        }
    }

    if (indice == -1) {
        System.out.println(" Producto no encontrado.");
        registrarBitacora("Registrar venta", false);
        return;
    }

    System.out.println("Producto: " + nombres[indice]);
    System.out.println("Stock: " + cantidades[indice]);
    System.out.print("Cantidad a vender: ");
    if (!venta.hasNextInt()) {
    System.out.println(" Entrada inválida. Debe ser un número entero.");
    venta.next(); // limpiar entrada
    registrarBitacora("Registrar venta - cantidad no numérica", false);
    return;
}

    int cantidadVendida = venta.nextInt();

    if (cantidadVendida <= 0 || cantidadVendida > cantidades[indice]) {
        System.out.println(" Cantidad inválida o insuficiente.");
        registrarBitacora("Registrar venta", false);
        return;
    }

    double totalVenta = precios[indice] * cantidadVendida;
    cantidades[indice] -= cantidadVendida;
    ventas.add(nombres[indice] + "," + cantidadVendida + "," + totalVenta + "," + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss")));


    System.out.println("Venta registrada por Q" + totalVenta);
    registrarBitacora("Registrar venta", true);
}


    // Termina metodo para registrar la venta

    // Inician metodos para la bitacora

public static void registrarBitacora(String tipoAccion, boolean esCorrecta) {
    LocalDateTime ahora = LocalDateTime.now();
    DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
    String fechaHora = ahora.format(formato);

    String resultado = esCorrecta ? "Correcta" : "Erronea";

    String registro = "\nFecha y hora: " + fechaHora +
                      "\nAccion: " + tipoAccion +
                      "\nResultado: " + resultado +
                      "\nUsuario: " + usuarioActual;

    bitacora.add(registro);
}



public static void mostrarBitacora() {
    System.out.println(" BITACORA ");
    if (bitacora.isEmpty()) {
        System.out.println("No hay acciones registradas.");
    } else {
        for (String registro : bitacora) {
            System.out.println("──────────────");
            System.out.println(registro);
            System.out.println("──────────────");
        }
    }
}
// terminan metodos para la bitacora

//inician metodos para generar reportes
public static void generarReportesPDF() {
    try {
        // ️ Crear carpeta "reportes" si no existe
        File carpeta = new File("reportes");
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        // Generar timestamp para nombrar los archivos
        DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd_MM_yyyy_HH_mm_ss");
        String timestamp = LocalDateTime.now().format(formato);

        String archivoStock = "reportes/" + timestamp + "_Stock.pdf";
        String archivoVenta = "reportes/" + timestamp + "_Venta.pdf";

        //  Reporte de Stock
        Document docStock = new Document();
        PdfWriter.getInstance(docStock, new FileOutputStream(archivoStock));
        docStock.open();

        docStock.add(new Paragraph("Reporte de Stock"));
        docStock.add(new Paragraph("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        docStock.add(new Paragraph(" "));

        PdfPTable tablaStock = new PdfPTable(5);
        tablaStock.addCell("Nombre");
        tablaStock.addCell("Codigo");
        tablaStock.addCell("Categoria");
        tablaStock.addCell("Precio");
        tablaStock.addCell("Cantidad");

        for (int i = 0; i < contadorProductos; i++) {
            tablaStock.addCell(nombres[i]);
            tablaStock.addCell(codigos[i]);
            tablaStock.addCell(categorias[i]);
            tablaStock.addCell("Q" + precios[i]);
            tablaStock.addCell(String.valueOf(cantidades[i]));
        }

        docStock.add(tablaStock);
        docStock.close();
        System.out.println(" Reporte de stock generado: " + archivoStock);
        registrarBitacora("Genero reporte de stock", true);


        //  Reporte de Ventas
        Document docVenta = new Document();
        PdfWriter.getInstance(docVenta, new FileOutputStream(archivoVenta));
        docVenta.open();

        docVenta.add(new Paragraph("Reporte de Ventas"));
        docVenta.add(new Paragraph("Fecha: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"))));
        docVenta.add(new Paragraph(" "));

        PdfPTable tablaVenta = new PdfPTable(4);
        tablaVenta.addCell("Producto");
        tablaVenta.addCell("Cantidad");
        tablaVenta.addCell("Total");
        tablaVenta.addCell("Fecha");
        

        for (String venta : ventas) {
            String[] datos = venta.split(",");
            tablaVenta.addCell(datos[0]); // Producto
            tablaVenta.addCell(datos[1]); // Cantidad
            tablaVenta.addCell("Q" + datos[2]); // Total
            tablaVenta.addCell(datos[3]); // Fecha
        }

        docVenta.add(tablaVenta);
        docVenta.close();
        System.out.println(" Reporte de ventas generado: " + archivoVenta);
        registrarBitacora("Genero reporte de ventas", true);


    } catch (Exception e) {
        System.out.println(" Error al generar los reportes: " + e.getMessage());
        registrarBitacora("Error al generar reportes PDF", false);

    }
}
// Terminan metodos para generar reportes

// inicia metodo para ver datos del estudiante
public static void verDatosEstudiante() {
    System.out.println(" DATOS DEL ESTUDIANTE ");
    System.out.println("Nombre completo: Carlos David Recinos Chip");
    System.out.println("Carnet: 202209463");
    System.out.println("Laboratorio: Introduccion a la computacion y programacion 1");
    System.out.println("Seccion: A");
    System.out.println("GitHub: chipcx");
}
// termina metodo para ver datos del estudiante

}
    // termina programa



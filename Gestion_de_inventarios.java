import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestion_de_inventarios {
    private static final String ARCHIVO_INVENTARIO = "inventario.csv";
    private static ArrayList<Producto> inventario = new ArrayList<>();
    private static ArrayList<Venta> ventas = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Cargar inventario desde el archivo CSV
        cargarInventarioDesdeArchivo();

        boolean continuar = true;
        while (continuar) {
            mostrarMenu();
            int opcion = obtenerOpcion();

            switch (opcion) {
                case 1:
                    agregarProducto();
                    guardarInventarioEnArchivo();
                    break;
                case 2:
                    eliminarProducto();
                    guardarInventarioEnArchivo();
                    break;
                case 3:
                    mostrarInventario();
                    break;
                case 4:
                    registrarVenta();
                    guardarInventarioEnArchivo();
                    break;
                case 5:
                    mostrarHistorialVentas();
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida. Intente nuevamente.");
            }
        }
    }

    private static void mostrarMenu() {
        System.out.println("\n--- Menú de Inventario ---");
        System.out.println("1. Agregar producto");
        System.out.println("2. Eliminar producto");
        System.out.println("3. Mostrar inventario");
        System.out.println("4. Registrar venta");
        System.out.println("5. Mostrar historial de ventas");
        System.out.println("6. Salir");
        System.out.print("Seleccione una opción: ");
    }

    private static int obtenerOpcion() {
        return scanner.nextInt();
    }

    private static void agregarProducto() {
        System.out.print("Ingrese el código del producto: ");
        scanner.nextLine(); // Limpiar el buffer
        String codigo = scanner.nextLine();

        System.out.print("Ingrese el nombre del producto: ");
        String nombre = scanner.nextLine();

        System.out.print("Ingrese la cantidad del producto: ");
        int cantidad = scanner.nextInt();

        Producto nuevoProducto = new Producto(codigo, nombre, cantidad);
        inventario.add(nuevoProducto);
        System.out.println("Producto agregado al inventario.");
    }

    private static void eliminarProducto() {
        System.out.print("Ingrese el código del producto a eliminar: ");
        scanner.nextLine(); // Limpiar el buffer
        String codigo = scanner.nextLine();

        boolean eliminado = false;
        for (Producto producto : inventario) {
            if (producto.getCodigo().equals(codigo)) {
                inventario.remove(producto);
                eliminado = true;
                System.out.println("Producto eliminado del inventario.");
                break;
            }
        } 
}
}

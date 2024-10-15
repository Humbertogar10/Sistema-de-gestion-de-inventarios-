import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestion_de_inventarios {
    private static final String ARCHIVO_INVENTARIO = "gestioninventario.csv";
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

        if (!eliminado) {
            System.out.println("Producto no encontrado en el inventario.");
        }
    }

    private static void mostrarInventario() {
        System.out.println("\n--- Inventario Actual ---");
        if (inventario.isEmpty()) {
            System.out.println("El inventario está vacío.");
        } else {
            for (Producto producto : inventario) {
                System.out.println("Código: " + producto.getCodigo() +
                        " | Nombre: " + producto.getNombre() +
                        " | Cantidad: " + producto.getCantidad());
            }
        }
    }

    private static void registrarVenta() {
        System.out.print("Ingrese el código del producto vendido: ");
        scanner.nextLine(); // Limpiar el buffer
        String codigo = scanner.nextLine();

        boolean encontrado = false;
        for (Producto producto : inventario) {
            if (producto.getCodigo().equals(codigo)) {
                encontrado = true;
                System.out.print("Ingrese la cantidad vendida: ");
                int cantidadVendida = scanner.nextInt();

                if (cantidadVendida > producto.getCantidad()) {
                    System.out.println("No hay suficiente stock para realizar la venta.");
                } else {
                    // Registrar la venta
                    ventas.add(new Venta(producto.getNombre(), cantidadVendida));
                    producto.reducirCantidad(cantidadVendida);
                    System.out.println("Venta registrada.");
                }
                break;
            }
        }

        if (!encontrado) {
            System.out.println("Producto no encontrado en el inventario.");
        }
    }

    private static void mostrarHistorialVentas() {
        System.out.println("\n--- Historial de Ventas ---");
        if (ventas.isEmpty()) {
            System.out.println("No hay ventas registradas.");
        } else {
            for (Venta venta : ventas) {
                System.out.println("Producto: " + venta.getProducto() + " | Cantidad: " + venta.getCantidad());
            }
        }
    }
}


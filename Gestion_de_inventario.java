import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Gestion_de_inventario {
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

    // Función para cargar el inventario desde un archivo CSV
    private static void cargarInventarioDesdeArchivo() {
        try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_INVENTARIO))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                String[] datos = linea.split(",");
                String codigo = datos[0];
                String nombre = datos[1];
                int cantidad = Integer.parseInt(datos[2]);
                inventario.add(new Producto(codigo, nombre, cantidad));
            }
        } catch (IOException e) {
            System.out.println("Error al cargar el inventario desde el archivo.");
        }
    }

    // Función para guardar el inventario en un archivo CSV
    private static void guardarInventarioEnArchivo() {
        try (FileWriter writer = new FileWriter(ARCHIVO_INVENTARIO)) {
            for (Producto producto : inventario) {
                writer.write(producto.getCodigo() + "," + producto.getNombre() + "," + producto.getCantidad() + "\n");
            }
        } catch (IOException e) {
            System.out.println("Error al guardar el inventario en el archivo.");
        }
    }
}

// Clase Producto para almacenar la información de cada producto
class Producto {
    private String codigo;
    private String nombre;
    private int cantidad;

    public Producto(String codigo, String nombre, int cantidad) {
        this.codigo = codigo;
        this.nombre = nombre;
        this.cantidad = cantidad;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public int getCantidad() {
        return cantidad;
    }

    public void reducirCantidad(int cantidadVendida) {
        this.cantidad -= cantidadVendida;
    }
}

// Clase Venta para almacenar la información de cada venta
class Venta {
    private String producto;
    private int cantidad;

    public Venta(String producto, int cantidad) {
        this.producto = producto;
        this.cantidad = cantidad;
    }

    public String getProducto() {
        return producto;
    }

    public int getCantidad() {
        return cantidad;
    }
}

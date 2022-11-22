package com.nomadacode;

public class HelloTestProductos {

    public static void main(String[] args) {

        HelloProducto producto1 = new HelloLibro("Ana Frank", "Mi lucha", 1945, 1.99, 123);

        HelloProducto producto2 = new HelloRefresco("Pascual", "Boing de Fresa", 355, 15, 23);

        HelloProducto producto3 = new HelloLibro("Hitler", "Neonazi", 1939, 2.99, 456);

        HelloProducto producto4 = new HelloRefresco("Chaprrita", "Fresa", 355, 12.3, 45);

        HelloProducto[] productos = new HelloProducto[] {
                producto1,
                producto2,
                producto3,
                producto4,
                new HelloProducto() {
                    @Override
                    public String getNombre() {
                        return "Producto de prueba";
                    }

                    @Override
                    public double getPrecio() {
                        return 123.456;
                    }

                    @Override
                    public int getExistencias() {
                        return 0;
                    }

                    @Override
                    public void describir() {
                        System.out.printf("%s $%.2f (%d) %n",
                                this.getNombre(),
                                this.getPrecio(),
                                this.getExistencias());
                    }
                }
        };

        System.out.println("Productos:");
        System.out.println("---------------------------------------------");
        for (HelloProducto producto : productos) {
            producto.describir();
        }

        double total = 0.0;
        for (HelloProducto producto : productos) {
            total += producto.getPrecio();
        }
        System.out.println("---------------------------------------------");
        System.out.printf("Total: $%.2f %n", total);

    }

}

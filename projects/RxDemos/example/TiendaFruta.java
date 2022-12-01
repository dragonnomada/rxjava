package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.ArrayList;
import java.util.List;

public class TiendaFruta {

    protected List<Fruta> frutas = new ArrayList<>();

    public void registrarFruta(String nombre, double precio) {
        frutas.add(new Fruta(nombre, precio, 0.0));
    }

    public void registrarCantidadFruta(String nombre, double kg) {
        // for (Fruta fruta : frutas) {
        //     if (fruta.getNombre().equals(nombre)) {
        //         double cantidad = fruta.getCantidad();
        //         fruta.setCantidad(cantidad + kg);
        //     }
        // }
        Observable.fromIterable(frutas)
                .filter(fruta -> fruta.getNombre().equals(nombre))
                .firstElement() // sólo la primer fruta encontrada con ese nombre
                .subscribe(fruta -> {
                    double cantidad = fruta.getCantidad();
                    fruta.setCantidad(cantidad + kg);
                });
    }

    public void reporteFrutas() {
        // System.out.println("Frutas:");
        // System.out.println("-----------------");
        // double total = 0.0;
        // for (Fruta fruta : frutas) {
        //     System.out.printf("%s $%.2f %n", fruta.getNombre(), fruta.getPrecio());
        //     total += fruta.getPrecio();
        // }
        // System.out.println("-----------------");
        // System.out.printf("Total: $%.2f %n", total);

        /*
        Observable.fromIterable(frutas) // Observable<Fruta>
                .map(fruta -> String.format("%s $%.2f", fruta.getNombre(), fruta.getPrecio())) // Observable<String>
                .startWithArray("Frutas:", "------------------") // Observable<String>
                .subscribe(frutaDescription -> System.out.println(frutaDescription));
         */

        Observable.fromIterable(frutas)
                .map(fruta -> fruta.getNombre())
                .map(nombre -> nombre.length())
                .sorted((l1, l2) -> Integer.compare(l2, l1))
                .firstElement()
                .subscribe(size -> {
                    System.out.printf("La fruta de mayor longitud mide: %d %n", size);
                    String format = "%-" + size + "s $%03.2f x %03.2f kg = $%04.2f x kg";
                    Observable.fromIterable(frutas) // Observable<Fruta>
                            .map(fruta -> String.format(format, fruta.getNombre(), fruta.getPrecio(), fruta.getCantidad(), fruta.getPrecio() * fruta.getCantidad())) // Observable<String>
                            .startWithArray("Frutas:", "------------------") // Observable<String>
                            .subscribe(frutaDescription -> System.out.println(frutaDescription));
                });

        Observable.fromIterable(frutas) // Observable<Fruta>
                .reduce(0.0, (total, fruta) -> total + fruta.getPrecio() * fruta.getCantidad()) // Single<Double>
                .map(total -> String.format("Total: $%.2f", total)) // 123.456 -> "Total: $123.46" | Single<String>
                .toObservable()
                .startWithItem("------------------")
                .subscribe(totalDescription -> System.out.println(totalDescription));

    }

    public void reporteFrutasCantidadAgotar(double kg) {

        // List<Fruta> frutasPorAgotar = new ArrayList<>();

        // for (Fruta fruta : frutas) {
        //     if (fruta.getCantidad() <= kg) {
        //         frutasPorAgotar.add(fruta)
        //     }
        // }

        // System.out.println("Frutas:");
        // System.out.println("-----------------");
        // for (Fruta fruta : frutasPorAgotar) {
        //     System.out.printf("%s %.2f %n", fruta.getNombre(), fruta.getCantidad());
        // }

        Observable.fromIterable(frutas)
                .filter(fruta -> fruta.getCantidad() <= kg)
                .sorted((f1, f2) -> Double.compare(f1.getCantidad(), f2.getCantidad()))
                .map(fruta -> String.format("%s %.2f", fruta.getNombre(), fruta.getCantidad()))
                // .take(3)
                .startWithArray("Frutas:", "------------------")
                .subscribe(frutaConDescripcion -> System.out.println(frutaConDescripcion));

    }

}

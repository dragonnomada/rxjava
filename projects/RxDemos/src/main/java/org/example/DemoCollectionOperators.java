package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;

public class DemoCollectionOperators {

    public static void main(String[] args) {

        Observable<Date> sourceInterval = Observable.interval(1, TimeUnit.SECONDS)
                .map(i -> new Date());

        sourceInterval.take(3)
                .subscribe(date -> System.out.println(date));

        sourceInterval.take(3)
                .toList()
                .subscribe(dateList -> System.out.println(dateList));

        Sleep.sleep(4000);

        Observable.just(5, 3, 2, 6, 4, 1)
                .toSortedList((o1, o2) -> {
                    if (o1 == o2) return 0;
                    return o1 > o2 ? -1 : 1;
                })
                .subscribe(list -> System.out.println(list));

        Observable<Producto> productoObservable = Observable.just(
                new Producto("A", 1),
                new Producto("A", 12),
                new Producto("C", 5),
                new Producto("C", 13),
                new Producto("C", 7),
                new Producto("B", 25),
                new Producto("C", 3),
                new Producto("B", 7)
        );

        productoObservable.toSortedList((p1, p2) -> {
            if (p1.getNombre() == p2.getNombre()) {
                return Double.compare(p1.getPrecio(), p2.getPrecio()) * -1;
            }
            return p1.getNombre().compareTo(p2.getNombre());
        }).subscribe(productosList -> System.out.println(productosList));

        // 1 si o1 es mayor o2
        // 0 si o1 es igual o2
        // -1 si o1 es menor o2
        productoObservable.sorted((p1, p2) -> {
            if (p1.getNombre() == p2.getNombre()) {
                return Double.compare(p1.getPrecio(), p2.getPrecio()) * -1;
            }
            return p1.getNombre().compareTo(p2.getNombre());
        }).subscribe(producto -> System.out.println(producto));

        productoObservable.sorted((p1, p2) -> {
            if (p1.getNombre() == p2.getNombre()) {
                return Double.compare(p1.getPrecio(), p2.getPrecio());
            }
            return p1.getNombre().compareTo(p2.getNombre());
        }).toMap(producto -> producto.getNombre()) // HashMap<key, value>
                .subscribe(productoMap -> System.out.println(productoMap));

        // Colección: List -> ArrayList, Set -> HashSet, Map -> HashMap

        // Map<KEY, VALUE>
        // Multimap<KEY, List<VALUE>>

        // El objetivo de la función asociada a toMap
        // es recuperar la clave que servirá para agrupar a todos los elementos
        // La diferencia entre toMap y toMultimap es que el último guarda
        // toda lista de los que coinciden en la misma clave
        productoObservable.toMultimap(producto -> producto.getNombre())
                .subscribe(productoMultimap -> System.out.println(productoMultimap));

        productoObservable.collect(HashSet::new, (preciosUnicos, producto) -> {
            preciosUnicos.add(producto.getPrecio());
        }).subscribe(preciosUnicos -> System.out.println(preciosUnicos));

        productoObservable.collect(HashSet::new, (nombresUnicos, producto) -> {
            nombresUnicos.add(producto.getNombre());
        }).subscribe(nombresUnicos -> System.out.println(nombresUnicos));

        // ~ reduce(new HashMap<String, Integer>, (...) -> !!!return *set)
        productoObservable.collect(HashMap<String, Integer>::new, (reporteProductos, producto) -> {
            if (reporteProductos.containsKey(producto.getNombre())) {
                int contador = reporteProductos.get(producto.getNombre());
                reporteProductos.put(producto.getNombre(), contador + 1);
            } else {
                reporteProductos.put(producto.getNombre(), 1);
            }
        }).subscribe(reporteProductos -> System.out.println(reporteProductos));

    }

}

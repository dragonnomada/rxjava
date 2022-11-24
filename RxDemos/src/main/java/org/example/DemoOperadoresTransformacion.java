package org.example;

import io.reactivex.rxjava3.core.Observable;

import java.util.Arrays;

public class DemoOperadoresTransformacion {

    static void test1() {

        // Observable<T> -> Observable<U>
        // Function<T, U> (T) -> U
        Observable.range(1, 10)
                .map(i -> String.format("[%02d] Producto %.2f", i, Math.random()))
                .subscribe(System.out::println);
    }

    static void test2() {

        String[][] grupos = new String[][]{
                new String[]{"A", "B", "C"},
                new String[]{"D", "E"},
                new String[]{"F", "G", "H", "I"},
        };

        Observable.range(0, grupos.length)
                .map(i -> grupos[i])
                .subscribe(grupo -> System.out.println(Arrays.toString(grupo)));

        Observable.range(0, grupos.length)
                .flatMap(i -> Observable.fromArray(grupos[i]))
                .subscribe(producto -> System.out.println(producto));
    }

    static void test3() {

        Observable.just("sol", "perro", "gato", "mono", "papel", "lapiz", "oportinidad")
                .flatMap(palabra -> Observable.just(palabra).repeat(palabra.length()))
                .subscribe(System.out::println);

    }

    static void test4() {

        class Producto {
            double precioTotal;
            double precioUnitario;

            Producto(double precioTotal, double precioUnitario) {
                this.precioTotal = precioTotal;
                this.precioUnitario = precioUnitario;
            }
        }

        Observable.just(
                        new Producto(10, 9),
                        new Producto(12, 8),
                        new Producto(15, 13),
                        new Producto(17, 5),
                        new Producto(89, 3)
                        )
                //.map(producto -> producto.precioTotal - producto.precioUnitario)
                .flatMap(producto -> Observable.just(producto.precioTotal, producto.precioUnitario, -1.0))
                .subscribe(System.out::println);

    }

    static void test5() {

        class Producto {
            double precioTotal;
            double precioUnitario;

            Producto(double precioTotal, double precioUnitario) {
                this.precioTotal = precioTotal;
                this.precioUnitario = precioUnitario;
            }

            @Override
            public String toString() {
                return String.format("Producto: $%.2f ($%.2f)", precioTotal, precioUnitario);
            }
        }

        Observable.just(
                        new Producto(10, 1),
                        new Producto(12, 8),
                        new Producto(15, 13),
                        new Producto(17, 5),
                        new Producto(89, 3)
                )
                .sorted((productoA, productoB) -> {
                    double diffA = productoA.precioTotal - productoA.precioUnitario;
                    double diffB = productoB.precioTotal - productoB.precioUnitario;
                    if (diffA == diffB) return 0;
                    return diffA > diffB ? 1 : -1;
                })
                .take(3)
                .subscribe(System.out::println);

    }

    public static void main(String[] args) {

        test5();

    }

}

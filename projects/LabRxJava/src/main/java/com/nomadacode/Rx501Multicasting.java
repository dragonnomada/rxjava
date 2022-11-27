package com.nomadacode;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Rx501Multicasting {

    public static void main(String[] args) {

        Observable<String> observableApi = Observable.create(emitter -> {

            LocalDate dateObj = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String date = dateObj.format(formatter);

            System.out.printf("[%s] REALIZANDO PETICIÓN AL API %n", date);

            // [REFERENCE] https://www.baeldung.com/java-http-request

            // Configuramos la URL de consumo al API
            URL url = new URL("https://randomuser.me/api");

            // Configuramos la conexión para la petición
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");

            // Configuramos los headers sobre la conexión a la petición
            con.setRequestProperty("Content-Type", "application/json");

            // Configuramos los tiempos máximos de espera
            con.setConnectTimeout(5000);
            con.setReadTimeout(5000);

            // Deshabilitamos las redirecciones
            con.setInstanceFollowRedirects(false);

            // Leemos el estatus de la petición de respuesta
            int status = con.getResponseCode();

            // Si la página fue movida o temporalmente movida reaccionamos
            /*if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM) {
                String location = con.getHeaderField("Location");
                URL newUrl = new URL(location);
                con = (HttpURLConnection) newUrl.openConnection();
            }*/

            // Si la petición fue exitosa recuperamos el contenido
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    // Emitimos directamente cada línea sobre el observable
                    emitter.onNext(inputLine);
                }
                in.close();
            } else {
                // Informamos que hubo un error
                String error = String.format("La petición falló con el estatus: %d", status);

                // Cerramos la conexión
                con.disconnect();

                emitter.onError( new Exception());
                return;
            }

            // Cerramos la conexión
            con.disconnect();

            // Informamos que el observable finalizó
            emitter.onComplete();

        });

        observableApi.subscribe(result -> System.out.printf("[1] %s %n", result));
        observableApi.subscribe(result -> System.out.printf("[2] %s %n", result));

        ConnectableObservable<String> observableApiMulticast = observableApi.publish();

        observableApiMulticast.subscribe(result -> System.out.printf("[1*] %s %n", result));
        observableApiMulticast.subscribe(result -> System.out.printf("[2*] %s %n", result));

        observableApiMulticast.connect();

    }

}

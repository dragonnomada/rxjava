package org.example;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.observables.ConnectableObservable;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DemoMulticastingRequest {

    public static void main(String[] args) {

        Observable<String> observableApi = Observable.create(emitter -> {

            LocalDate dateObj = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            String date = dateObj.format(formatter);

            System.out.printf("[%s] REALIZANDO PETICIÓN AL API %n", date);

            // [REFERENCE] https://www.baeldung.com/java-http-request

            // Configuramos la URL de consumo al API
            //URL url = new URL("https://raw.githubusercontent.com/dragonnomada/rxjava/main/notes/n501.md");
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

            // Si la petición fue exitosa recuperamos el contenido
            if (status == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String inputLine;
                //StringBuilder builder = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    // Emitimos directamente cada línea sobre el observable
                    emitter.onNext(inputLine);
                    //builder.append(inputLine);
                }
                //emitter.onNext(builder.toString());
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

        //observableApi.take(5)
        //        .reduce("", (content, line) -> content + "\n" + line)
        //        .subscribe(content -> System.out.println(content));

        observableApi.subscribe(line -> System.out.printf("[1]: Line <<%s>> %n", line));
        observableApi.subscribe(line -> System.out.printf("[2]: Line <<%s>> %n", line));
        observableApi.subscribe(line -> System.out.printf("[3]: Line <<%s>> %n", line));
        observableApi.subscribe(line -> System.out.printf("[4]: Line <<%s>> %n", line));
        observableApi.subscribe(line -> System.out.printf("[5]: Line <<%s>> %n", line));

        ConnectableObservable<String> observableApiMulticast = observableApi
                .map(line -> line.substring(0, 100))
                .publish();

        observableApiMulticast.subscribe(line -> System.out.printf("[1*]: Line <<%s>> %n", line));
        observableApiMulticast.subscribe(line -> System.out.printf("[2*]: Line <<%s>> %n", line));
        observableApiMulticast.subscribe(line -> System.out.printf("[3*]: Line <<%s>> %n", line));
        observableApiMulticast.subscribe(line -> System.out.printf("[4*]: Line <<%s>> %n", line));
        observableApiMulticast.subscribe(line -> System.out.printf("[5*]: Line <<%s>> %n", line));

        // Al conectar el observable se mandan las emisiones a los suscriptores registrados
        observableApiMulticast.connect();

    }

}

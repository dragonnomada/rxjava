package org.example;

import io.reactivex.rxjava3.subjects.PublishSubject;
import io.reactivex.rxjava3.subjects.Subject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DemoSubjectApi {

    static void randomUserRequest(Subject<String> stateSubject, Subject<String> resultSubject) {
        stateSubject.onNext("Starting");

        LocalDate dateObj = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        String date = dateObj.format(formatter);

        System.out.printf("[%s] REALIZANDO PETICIÓN AL API %n", date);

        URL url = null;
        try {
            url = new URL("https://randomuser.me/api");
            stateSubject.onNext("URL-Configured");
        } catch (MalformedURLException e) {
            stateSubject.onError(e);
            return;
        }

        // Configuramos la conexión para la petición
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            stateSubject.onNext("Connection-Opened");
        } catch (IOException e) {
            stateSubject.onError(e);
            return;
        }
        try {
            con.setRequestMethod("GET");
            stateSubject.onNext("Method-Setted");
        } catch (ProtocolException e) {
            stateSubject.onError(e);
            return;
        }
        con.setRequestProperty("Content-Type", "application/json");
        con.setConnectTimeout(5000);
        con.setReadTimeout(5000);
        con.setInstanceFollowRedirects(false);
        stateSubject.onNext("Request-Ready");

        int status = 0;
        try {
            status = con.getResponseCode();
            stateSubject.onNext("Status-Adquired");
        } catch (IOException e) {
            stateSubject.onError(e);
            return;
        }

        // Si la petición fue exitosa recuperamos el contenido
        if (status == HttpURLConnection.HTTP_OK) {
            stateSubject.onNext("Request-OK");
            BufferedReader in = null;
            try {
                in = new BufferedReader(new InputStreamReader(con.getInputStream()));
                stateSubject.onNext("InputStream-Adquired");
            } catch (IOException e) {
                stateSubject.onError(e);
                return;
            }
            String inputLine;
            //StringBuilder builder = new StringBuilder();
            try {
                while ((inputLine = in.readLine()) != null) {
                    // Emitimos directamente cada línea sobre el observable
                    resultSubject.onNext(inputLine);
                    //builder.append(inputLine);
                }
                resultSubject.onComplete();
                stateSubject.onNext("Content-OK");
            } catch (Throwable e) {
                stateSubject.onError(e);
            }
            //emitter.onNext(builder.toString());
            try {
                in.close();
                stateSubject.onNext("Request-Closed");
            } catch (IOException e) {
                stateSubject.onError(e);
            }
        } else {
            // Informamos que hubo un error
            String error = String.format("La petición falló con el estatus: %d", status);

            // Cerramos la conexión
            con.disconnect();

            stateSubject.onError( new Exception());
            return;
        }

        // Cerramos la conexión
        con.disconnect();

        stateSubject.onNext("Finished");

        // Informamos que el observable finalizó
        stateSubject.onComplete();

    }

    public static void main(String[] args) {

        Subject<String> stateSubject = PublishSubject.create();
        Subject<String> resultSubject = PublishSubject.create();

        stateSubject.subscribe(state -> System.out.printf("[1]: STATE %s %n", state));

        resultSubject.subscribe(result -> System.out.printf("[1*]: RESULT %s %n", result));

        stateSubject.onNext("Fake");

        randomUserRequest(stateSubject, resultSubject);

    }

}

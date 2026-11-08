package com.agile.agileAPI.integracoes;

import com.agile.agileAPI.entidades.Task;
import com.agile.agileAPI.excecoes.IntegracaoCalendarioException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service 
@ConditionalOnProperty(name = "agile.google-calendar.enabled", havingValue = "true")
public class GoogleCalendarioService {
    private final Calendar calendar;
    private final String calendarId;

    public GoogleCalendarioService(@Value("${agile.google-calendar.calendar-id}") String calendarId){
        this.calendarId = calendarId;

        try {
            GoogleCredentials credenciais = GoogleCredentials.getApplicationDefault()
            .createScoped(Collections.singletonList(CalendarScopes.CALENDAR_EVENTS));

            HttpRequestInitializer inicializador = new HttpCredentialsAdapter(credenciais);
            HttpTransport transporte = GoogleNetHttpTransport.newTrustedTransport();
            JsonFactory jsonFactory = GsonFactory.getDefaultInstance();

            this.calendar = new Calendar.Builder(transporte, jsonFactory, inicializador)
            .setApplicationName("Agile API").build();
        } catch (IOException | GeneralSecurityException e) {
            throw new IntegracaoCalendarioException("Não foi possível inicializar o Google Calendar", e);
        }
    }
}

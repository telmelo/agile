package com.agile.agileAPI.integracoes;

import com.agile.agileAPI.entidades.Task;
import com.agile.agileAPI.excecoes.IntegracaoCalendarioException;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.DateTime;
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
import java.util.Map;
import java.util.Objects;

import com.google.api.services.calendar.model.Event;
import com.google.api.services.calendar.model.EventDateTime;

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

    private Event montarTask(Task task){
        Event evento = new Event();

        evento.setSummary("[" + task.getPrioridade() + "] " + task.getTitulo());

        String descricao = Objects.requireNonNullElse(task.getDescricao(),"");
        evento.setDescription("Status: " + task.getStatus() + "\n" + descricao);

        evento.setStart(new EventDateTime().setDate(new DateTime(task.getDataLimite().toString())));
        evento.setEnd(new EventDateTime().setDate(new DateTime(task.getDataLimite().plusDays(1).toString())));

        String cor = switch (task.getStatus()){
            case PENDENTE -> "11";
            case EM_PROGRESSO -> "5";
            case CONCLUIDA -> "10";
        };

        evento.setColorId(cor);
        evento.setExtendedProperties(new Event.ExtendedProperties()
        .setPrivate(Map.of("taskId", String.valueOf(task.getId()))));

        return evento;
    }
}

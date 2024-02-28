package com.example.blog.helper;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CustomDateDeserializer extends JsonDeserializer<Date> {

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss yyyy");

    @Override
    public Date deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        String dateAsString = jsonParser.getText();
        try {
            return dateFormat.parse(dateAsString);
        } catch (ParseException e) {
            throw new IOException("Error parsing date: " + dateAsString, e);
        }
    }
}


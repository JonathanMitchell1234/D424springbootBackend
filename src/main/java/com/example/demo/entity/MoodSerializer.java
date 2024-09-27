package com.example.demo.entity;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class MoodSerializer extends JsonSerializer<Mood> {
    @Override
    public void serialize(Mood mood, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeString(mood.getName());
    }
}
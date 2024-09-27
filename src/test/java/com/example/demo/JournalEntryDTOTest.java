package com.example.demo;
import com.example.demo.dto.JournalEntryDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class JournalEntryDTOTest {

    private JournalEntryDTO journalEntryDTO;

    @BeforeEach
    void setUp() {
        journalEntryDTO = new JournalEntryDTO();
    }

    @Test
    void shouldSetAndGetTitle() {
        String title = "Test Title";
        journalEntryDTO.setTitle(title);
        assertEquals(title, journalEntryDTO.getTitle());
    }

    @Test
    void shouldSetAndGetContent() {
        String content = "Test Content";
        journalEntryDTO.setContent(content);
        assertEquals(content, journalEntryDTO.getContent());
    }

    @Test
    void shouldSetAndGetAiResponse() {
        String aiResponse = "Test AI Response";
        journalEntryDTO.setAiResponse(aiResponse);
        assertEquals(aiResponse, journalEntryDTO.getAiResponse());
    }

    @Test
    void shouldSetAndGetImageUri() {
        String imageUri = "Test Image URI";
        journalEntryDTO.setImageUri(imageUri);
        assertEquals(imageUri, journalEntryDTO.getImageUri());
    }

    @Test
    void shouldSetAndGetMood() {
        Set<String> mood = new HashSet<>();
        mood.add("Happy");
        mood.add("Sad");
        journalEntryDTO.setMood(mood);
        assertEquals(mood, journalEntryDTO.getMood());
    }

    @Test
    void shouldSetAndGetDateCreated() {
        LocalDateTime dateCreated = LocalDateTime.now();
        journalEntryDTO.setDateCreated(dateCreated);
        assertEquals(dateCreated, journalEntryDTO.getDateCreated());
    }

    @Test
    void shouldSetAndGetDateUpdated() {
        LocalDateTime dateUpdated = LocalDateTime.now();
        journalEntryDTO.setDateUpdated(dateUpdated);
        assertEquals(dateUpdated, journalEntryDTO.getDateUpdated());
    }
}
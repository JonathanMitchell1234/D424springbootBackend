package com.example.demo;

import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.Mood;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.JournalEntryRepository;
import com.example.demo.repository.MoodRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.JournalEntryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class JournalEntryServiceTest {

    @Mock
    private JournalEntryRepository journalEntryRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private MoodRepository moodRepository;

    @InjectMocks
    private JournalEntryService journalEntryService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void shouldCreateJournalEntry() {
        User user = new User();
        user.setEmail("test@example.com");

        Mood mood = new Mood("Happy");

        JournalEntry entry = new JournalEntry();
        entry.setSelectedMoods(Collections.singleton(mood));

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(moodRepository.findByName("Happy")).thenReturn(Optional.of(mood));
        when(journalEntryRepository.save(entry)).thenReturn(entry);

        JournalEntry result = journalEntryService.createJournalEntry(entry, "test@example.com");

        assertEquals(entry, result);
        verify(userRepository).findByEmail("test@example.com");
        verify(moodRepository).findByName("Happy");
        verify(journalEntryRepository).save(entry);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        JournalEntry entry = new JournalEntry();

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> journalEntryService.createJournalEntry(entry, "test@example.com"));
        verify(userRepository).findByEmail("test@example.com");
    }
}
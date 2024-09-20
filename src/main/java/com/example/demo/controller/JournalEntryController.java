package com.example.demo.controller;

import com.example.demo.dto.JournalEntryDTO;
import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.Mood;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.JournalEntryService;
import com.example.demo.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/journal-entries")
public class JournalEntryController {

    private final JournalEntryService journalEntryService;
    private final MoodService moodService;

    @Autowired
    public JournalEntryController(JournalEntryService journalEntryService, MoodService moodService) {
        this.journalEntryService = journalEntryService;
        this.moodService = moodService;
    }

    @PostMapping
    public ResponseEntity<JournalEntry> createEntry(
            @Valid @RequestBody JournalEntryDTO entryDTO,
            Authentication authentication) {
        String email = authentication.getName();

        JournalEntry entry = convertToEntity(entryDTO);

        JournalEntry createdEntry = journalEntryService.createJournalEntry(entry, email);

        return ResponseEntity.ok(createdEntry);
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getEntries(Authentication authentication) {
        String email = authentication.getName();
        List<JournalEntry> entries = journalEntryService.getEntriesByUser(email);
        return ResponseEntity.ok(entries);
    }

    @PutMapping("/{id}")
    public ResponseEntity<JournalEntry> updateEntry(
            @PathVariable Long id,
            @Valid @RequestBody JournalEntryDTO entryDTO,
            Authentication authentication) {
        String email = authentication.getName();

        JournalEntry entry = convertToEntity(entryDTO);

        JournalEntry updatedEntry = journalEntryService.updateJournalEntry(id, entry, email);

        return ResponseEntity.ok(updatedEntry);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteEntry(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        journalEntryService.deleteJournalEntry(id, email);
        return ResponseEntity.ok("Entry deleted successfully");
    }

    @GetMapping("/search")
    public ResponseEntity<List<JournalEntry>> searchEntries(
            @RequestParam String query,
            Authentication authentication) {
        String email = authentication.getName();
        List<JournalEntry> entries = journalEntryService.searchEntries(query, email);
        return ResponseEntity.ok(entries);
    }

    private JournalEntry convertToEntity(JournalEntryDTO entryDTO) {
        JournalEntry entry = new JournalEntry();
        entry.setTitle(entryDTO.getTitle());
        entry.setContent(entryDTO.getContent());
        entry.setAiResponse(entryDTO.getAiResponse());
        entry.setImageUri(entryDTO.getImageUri());

        // Map moods from DTO to entities using mood names
        Set<Mood> moods = new HashSet<>();
        if (entryDTO.getMood() != null) {
            for (String moodName : entryDTO.getMood()) {
                Mood mood = moodService.findByName(moodName)
                        .orElseGet(() -> moodService.createMood(new Mood(moodName)));
                moods.add(mood);
            }
        }
        entry.setSelectedMoods(moods);
        return entry;
    }
}

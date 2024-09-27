package com.example.demo.controller;

import com.example.demo.dto.JournalEntryDTO;
import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.Mood;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.service.JournalEntryService;
import com.example.demo.service.MoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/api/journal-entries")
public class JournalEntryController {

    private static final Logger logger = LoggerFactory.getLogger(JournalEntryController.class);
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
        logger.info("Delete request received for entry ID: {}", id);
        if (id == null) {
            logger.warn("Received null ID for deletion");
            return ResponseEntity.badRequest().body("Invalid ID");
        }
        String email = authentication.getName();
        logger.info("Authenticated user email: {}", email);
        try {
            journalEntryService.deleteJournalEntry(id, email);
            logger.info("Entry {} deleted successfully", id);
            return ResponseEntity.ok("Entry deleted successfully");
        } catch (ResourceNotFoundException e) {
            logger.warn("Entry not found: {}", e.getMessage());
            return ResponseEntity.notFound().build();
        } catch (SecurityException e) {
            logger.warn("Unauthorized deletion attempt: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Unexpected error during deletion", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An unexpected error occurred");
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<JournalEntry>> searchEntries(
            @RequestParam String query,
            Authentication authentication) {
        String email = authentication.getName();
        List<JournalEntry> entries = journalEntryService.searchEntries(query, email);
        return ResponseEntity.ok(entries);
    }

    // In JournalEntryController.java
// JournalEntryController.java

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

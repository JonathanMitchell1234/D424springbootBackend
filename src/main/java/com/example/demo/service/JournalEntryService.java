package com.example.demo.service;
import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.Mood;
import com.example.demo.entity.User;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.JournalEntryRepository;
import com.example.demo.repository.MoodRepository;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private MoodRepository moodRepository;

    public JournalEntry createJournalEntry(JournalEntry entry, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        entry.setUser(user);

        Set<Mood> persistedMoods = new HashSet<>();
        for (Mood mood : entry.getSelectedMoods()) {
            Mood persistedMood = moodRepository.findByName(mood.getName())
                    .orElseGet(() -> moodRepository.save(mood));
            persistedMoods.add(persistedMood);
        }
        entry.setSelectedMoods(persistedMoods);

        return journalEntryRepository.save(entry);
    }

    public List<JournalEntry> getEntriesByUser(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return journalEntryRepository.findByUser(user);
    }

    public JournalEntry updateJournalEntry(Long id, JournalEntry updatedEntry, String email) {
        JournalEntry existingEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal Entry not found"));

        if (!existingEntry.getUser().getEmail().equals(email)) {
            throw new SecurityException("Unauthorized access");
        }

        existingEntry.setTitle(updatedEntry.getTitle());
        existingEntry.setContent(updatedEntry.getContent());
        existingEntry.setImageUri(updatedEntry.getImageUri());
        existingEntry.setAiResponse(updatedEntry.getAiResponse());

        Set<Mood> persistedMoods = new HashSet<>();
        for (Mood mood : updatedEntry.getSelectedMoods()) {
            Mood persistedMood = moodRepository.findByName(mood.getName())
                    .orElseGet(() -> moodRepository.save(mood));
            persistedMoods.add(persistedMood);
        }
        existingEntry.setSelectedMoods(persistedMoods);

        return journalEntryRepository.save(existingEntry);
    }

    public void deleteJournalEntry(Long id, String email) {
        JournalEntry existingEntry = journalEntryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Journal Entry not found"));

        if (!existingEntry.getUser().getEmail().equals(email)) {
            throw new SecurityException("Unauthorized access");
        }

        journalEntryRepository.delete(existingEntry);
    }

    // Search functionality
    public List<JournalEntry> searchEntries(String query, String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return journalEntryRepository.findByUserAndTitleContainingOrContentContaining(user, query, query);
    }
}

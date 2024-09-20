package com.example.demo.repository;

import com.example.demo.entity.JournalEntry;
import com.example.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface JournalEntryRepository extends JpaRepository<JournalEntry, Long> {
    List<JournalEntry> findByUser(User user);

    // Search functionality
    List<JournalEntry> findByUserAndTitleContainingOrContentContaining(User user, String title, String content);
}
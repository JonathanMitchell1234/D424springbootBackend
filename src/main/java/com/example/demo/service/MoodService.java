package com.example.demo.service;

import com.example.demo.entity.Mood;
import com.example.demo.repository.MoodRepository;
import com.example.demo.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Optional;

@Service
public class MoodService {

    private final MoodRepository moodRepository;

    @Autowired
    public MoodService(MoodRepository moodRepository) {
        this.moodRepository = moodRepository;
    }

    // Fetch Mood by name
    public Optional<Mood> findByName(String name) {
        return moodRepository.findByName(name);
    }

    // Fetch Mood by ID
    public Optional<Mood> findById(Long id) { // Changed to Long
        return moodRepository.findById(id);
    }

    // Save a new Mood (if you need to create moods dynamically)
    public Mood createMood(Mood mood) {
        return moodRepository.save(mood);
    }

    // Handle not found scenarios
    public Mood getMoodByNameOrThrow(String name) {
        return findByName(name).orElseThrow(() -> new ResourceNotFoundException("Mood not found: " + name));
    }
}

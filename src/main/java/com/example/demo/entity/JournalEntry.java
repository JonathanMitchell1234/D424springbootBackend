package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "JournalEntry")
@Data
@EqualsAndHashCode(callSuper = true)
public class JournalEntry extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference // Prevents infinite recursion during serialization
    private User user;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    private String imageUri;

    @Column(columnDefinition = "TEXT")
    private String aiResponse;

    @ManyToMany
    @JoinTable(
            name = "JournalEntryMood",
            joinColumns = @JoinColumn(name = "journal_entry_id"),
            inverseJoinColumns = @JoinColumn(name = "mood_id")
    )
    private Set<Mood> selectedMoods = new HashSet<>();

    public void setMood(Set<String> mood) {
    }
}
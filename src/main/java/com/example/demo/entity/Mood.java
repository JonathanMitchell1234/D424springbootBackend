package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;

@Entity
@Table(name = "Mood")
@Data
@EqualsAndHashCode(callSuper = true)
public class Mood extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "selectedMoods")
    @JsonIgnore // Prevent serialization to avoid recursion
    private Set<JournalEntry> journalEntries;

    public Mood(String name) {
        this.name = name;
    }

    public Mood() {}
}

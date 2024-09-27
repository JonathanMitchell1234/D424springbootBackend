package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.util.Set;
import com.example.demo.entity.BaseEntity;

@Entity
@Table(name = "mood")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Mood extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "selectedMoods")
    @JsonIgnore
    private Set<JournalEntry> journalEntries;

    public Mood(String name) {
        this.name = name;
    }

    public Mood() {}

    public String getName() {
        return this.name;
    }
}

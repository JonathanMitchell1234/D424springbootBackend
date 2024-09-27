package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;


import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "journal_entry") // Ensure this matches your database naming convention
@Data
@EqualsAndHashCode(callSuper = true)
public class JournalEntry extends BaseEntity {

    @Id
    @EqualsAndHashCode.Include
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(name = "image_uri") // Use snake_case for column name
    private String imageUri;

    @Column(name = "ai_response", columnDefinition = "TEXT") // Use snake_case for column name
    private String aiResponse;

    public void setTitle(String title) {
        this.title = title;
    }
    public void setUser(User user) {
        this.user = user;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setAiResponse(String aiResponse) {
        this.aiResponse = aiResponse;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

    public void setSelectedMoods(Set<Mood> selectedMoods) {
        this.selectedMoods = selectedMoods;
    }

    public User getUser() {
        return this.user;
    }

    public String getTitle() {
        return this.title;
    }

    public String getContent() {
        return this.content;
    }

    public String getImageUri() {
        return this.imageUri;
    }

    public String getAiResponse() {
        return this.aiResponse;
    }

    public Set<Mood> getSelectedMoods() {
        return this.selectedMoods;
    }

    public Long getId() {
        return id;
    }


    @ManyToMany
    @JoinTable(
            name = "journal_entry_mood", // Ensure the join table uses snake_case
            joinColumns = @JoinColumn(name = "journal_entry_id"),
            inverseJoinColumns = @JoinColumn(name = "mood_id")
    )
    @JsonSerialize(contentUsing = MoodSerializer.class)
    private Set<Mood> selectedMoods = new HashSet<>();
}

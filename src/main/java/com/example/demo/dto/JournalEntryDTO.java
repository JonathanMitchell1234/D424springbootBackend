package com.example.demo.dto;
import java.time.LocalDateTime;
import java.util.Set;

public class JournalEntryDTO {

    private Long id;
    private String title;
    private String content;
    private String aiResponse;
    private String imageUri;
    private Set<String> mood;
    private LocalDateTime dateCreated;
    private LocalDateTime dateUpdated;

    public JournalEntryDTO() {

    }

    // Getters and Setters
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }

    public String getAiResponse() { return aiResponse; }
    public void setAiResponse(String aiResponse) { this.aiResponse = aiResponse; }

    public String getImageUri() { return imageUri; }
    public void setImageUri(String imageUri) { this.imageUri = imageUri; }

    public Set<String> getMood() { return mood; }
    public void setMood(Set<String> mood) { this.mood = mood; }

    public LocalDateTime getDateCreated() { return dateCreated; }
    public void setDateCreated(LocalDateTime dateCreated) { this.dateCreated = dateCreated; }

    public LocalDateTime getDateUpdated() { return dateUpdated; }
    public void setDateUpdated(LocalDateTime dateUpdated) { this.dateUpdated = dateUpdated; }
}
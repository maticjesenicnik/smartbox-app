package com.example.lib;

import java.time.LocalDateTime;

public class Statistics {
    private String id;
    private LocalDateTime created;
    private boolean successful;

    public Statistics(String id, LocalDateTime created, boolean successful) {
        this.id = id;
        this.created = created;
        this.successful = successful;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public void setCreated(LocalDateTime created) {
        this.created = created;
    }

    public boolean isSuccessful() {
        return successful;
    }

    public void setSuccessful(boolean successful) {
        this.successful = successful;
    }

    @Override
    public String toString() {
        return "BoxID: " + this.getId() + ", " + this.getCreated() + ", " + this.isSuccessful();
    }
}

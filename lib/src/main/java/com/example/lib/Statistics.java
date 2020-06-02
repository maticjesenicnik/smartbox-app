package com.example.lib;

import java.util.Date;

public class Statistics {
    private String id;
    private Date created;
    private boolean successful;

    public Statistics(String id, Date created, boolean successful) {
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
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

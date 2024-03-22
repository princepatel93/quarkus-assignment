package org.walnutai.app.DTO;
import jakarta.validation.constraints.NotNull;

public class NameDTO {
    @NotNull
    private String name;

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

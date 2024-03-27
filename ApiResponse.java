package com.example.do_an.model;

import java.util.List;

public class ApiResponse {
    private List<Choice> choices;
    private String error;

    // Getter and setter for choices
    public List<Choice> getChoices() { return choices; }
    public void setChoices(List<Choice> choices) { this.choices = choices; }

    // Getter and setter for error
    public String getError() { return error; }
    public void setError(String error) { this.error = error; }
}

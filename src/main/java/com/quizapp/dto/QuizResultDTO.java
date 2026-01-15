package com.quizapp.dto;

public class QuizResultDTO {
    private int score;
    private int total;

    public QuizResultDTO(int score, int total) {
        this.score = score;
        this.total = total;
    }

    public int getScore() {
        return score;
    }

    public int getTotal() {
        return total;
    }
}

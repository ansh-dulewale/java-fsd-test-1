package com.quizapp.service;

import com.quizapp.model.QuizQuestion;
import com.quizapp.repository.QuizRepository;

import java.util.List;
import java.util.Map;

public class QuizService {
    private QuizRepository quizRepository;

    public QuizService(QuizRepository quizRepository){
        this.quizRepository  = quizRepository;
    }

    public List<QuizQuestion> getAllQuestionService(){
        return quizRepository.getAllQuestion();
    }

    public int submitQuiz(Map<Integer, String> answer){
        int score = 0;
        for(Map.Entry<Integer, String> entry : answer.entrySet()){
            int questionId = entry.getKey();
            String selected = entry.getValue();

            QuizQuestion quizQuestion = quizRepository.getQuestionById(questionId);

            try{
                if(quizQuestion.getCorrectOption().equalsIgnoreCase(selected)) score++;
            } catch (Exception e){
                throw new NullPointerException();
            }
        }
        return score;
    }

    public int getTotalQuestionNumberService(){
        return quizRepository.getAllQuestion().size();
    }

    public void saveResultService(String username, int score, int total){
        quizRepository.saveResult(username, score, total);
    }

    public void addQuestionSer(QuizQuestion quizQuestion){
        if(quizQuestion.getQuestion() == null || quizQuestion.getCorrectOption() == null) throw new IllegalArgumentException("Found Empty");

        quizRepository.addQuestion(quizQuestion);
    }
}

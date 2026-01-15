package com.quizapp.controller;

import com.quizapp.dto.QuizQuestionDTO;
import com.quizapp.dto.QuizResultDTO;
import com.quizapp.dto.QuizSubmitDTO;
import com.quizapp.model.QuizQuestion;
import com.quizapp.service.QuizService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {
    private QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping("/questions")
    public ResponseEntity<List<QuizQuestionDTO>> getQuestionController(){
        List<QuizQuestion> quizQuestions = quizService.getAllQuestionService();
        List<QuizQuestionDTO> res = new ArrayList<>();

        for(QuizQuestion q : quizQuestions){
            QuizQuestionDTO dto = new QuizQuestionDTO();
            dto.setId(q.getId());
            dto.setQuestion(q.getQuestion());
            dto.setOptionA(q.getOptionA());
            dto.setOptionB(q.getOptionB());
            dto.setOptionC(q.getOptionC());
            dto.setOptionD(q.getOptionD());
            res.add(dto);
        }
        return new ResponseEntity<>(res,HttpStatus.OK);
    }

    @PostMapping("/submit")
    public ResponseEntity<QuizResultDTO> submitQuiz(@RequestBody QuizSubmitDTO req){
        try{
            int score = quizService.submitQuiz(req.getAnswers());
            int total = quizService.getTotalQuestionNumberService();

            QuizResultDTO quizResultDTO = new QuizResultDTO(score, total);
            return new ResponseEntity<>(quizResultDTO, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/question")
    public ResponseEntity<String> addQuestionCont(@RequestBody QuizQuestion question){
        try{
            quizService.addQuestionSer(question);
            return new ResponseEntity<>("Question Added Successfullu", HttpStatus.CREATED);
        } catch (IllegalArgumentException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }


}

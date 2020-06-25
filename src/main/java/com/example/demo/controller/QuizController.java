package com.example.demo.controller;
import com.example.demo.entity.Account;
import com.example.demo.entity.Completion;
import com.example.demo.entity.Quiz;
import com.example.demo.service.AccountService;
import com.example.demo.service.CompletionService;
import com.example.demo.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping("/api")
public class QuizController {


    @Autowired
    private CompletionService completionService;
    @Autowired
    private QuizService quizService;
    @Autowired
    private AccountService accountService;


    @GetMapping("/quizzes/completed")
    public ResponseEntity<Iterable<Completion>> getSolvedQuizzesOwnedByCurrentUser(@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {

        Page<Completion> pagedCompletions = completionService.getCompletionsByAccountId(accountService.getCurrentAccount().getId(), page, pageSize);

        return new ResponseEntity<>(pagedCompletions, HttpStatus.OK);
    }

    @GetMapping("/quizzes")
    public ResponseEntity<Iterable<Quiz>> getAllQuizzes (@RequestParam(defaultValue = "0") Integer page, @RequestParam(defaultValue = "10") Integer pageSize) {

        Page<Quiz> quizzes = quizService.getAllQuizzes(page, pageSize);

        return new ResponseEntity<>(quizzes, HttpStatus.OK);
    }

    @GetMapping("/quizzes/{id}")
    public ResponseEntity<Quiz> getQuizById (@PathVariable("id") final Long id) {

        //check is quiz exist
        if (quizService.existsById(id)) {

            Quiz quiz = quizService.getQuizById(id);
            return new ResponseEntity<>(quiz, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @DeleteMapping("/quizzes/{id}")
    public void deleteQuizById (@PathVariable("id") final Long id) {

        //check if quiz exist
        if (quizService.existsById(id)) {

            //check if current user is owner of the quiz
            if (quizService.getQuizById(id).getAccount().getId().equals(accountService.getCurrentAccount().getId())) {

                //delete quiz
                quizService.deleteQuizById(id);

            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "deleting was successful");
            } else {

                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "not authorized");
            }
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
        }
    }

    @PostMapping("/quizzes/{id}/solve")
    public ResponseEntity<Map<Object, Object>> solveQuizById (@PathVariable("id") final Long id, @RequestBody Quiz quizAnswer) {

        //create map of Objects for response entity
        Map<Object, Object> responseMap = new LinkedHashMap<>();

        //check is quiz exist
        if (quizService.existsById(id)) {

            //check is answer is correct
            Boolean isCorrect = Arrays.equals(quizService.getQuizById(id).getAnswer(), quizAnswer.getAnswer());

            //prepare response
            String stringResponse = isCorrect ? "Congratulations, you're right!" : "Wrong answer! Please, try again.";
            responseMap.put("success", isCorrect);
            responseMap.put("feedback", stringResponse);

            //create record (Completion object) about quiz successfully solved
            if (isCorrect) {

                Completion completion = new Completion();
                completion.setId(id);
                completion.setCompletedAt(LocalDateTime.now());
                completion.setAccountId(accountService.getCurrentAccount().getId());
                completionService.saveCompletion(completion);
            }
            return new ResponseEntity<>(responseMap, HttpStatus.OK);
        } else
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "entity not found");
    }

    @PostMapping("/quizzes")
    public ResponseEntity<Quiz> saveQuiz (@Valid @RequestBody final Quiz quiz) {
        Quiz savedQuiz = quizService.saveQuiz(quiz);
        return new ResponseEntity<>(savedQuiz, HttpStatus.OK);
    }

    @PostMapping("/register")
    public ResponseEntity<Account> createAccount (@Valid @RequestBody final Account account) {

        //check if an account already exist
        if (accountService.findAccountByEmail(account.getEmail()) == null) {

            //save new account
            Account newAccount = accountService.saveAccount(account);
            return new ResponseEntity<>(newAccount, HttpStatus.OK);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"email is already taken by another user");
        }
    }
}

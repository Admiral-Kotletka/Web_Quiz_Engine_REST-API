package com.example.demo.service;

//import encom.example.demogine.entity.Quiz;
import com.example.demo.entity.Quiz;
import com.example.demo.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Component
@Service
public class QuizService {

    @Autowired
    AccountService accountService;
    @Autowired
    QuizRepository quizRepository;

    public Page<Quiz> getAllQuizzes (Integer page, Integer pageSize) {
        Pageable paging = PageRequest.of(page, pageSize);
        return quizRepository.findAll(paging);
    }

    public Quiz getQuizById(final Long id) throws NoSuchElementException {
        return quizRepository.findById(id).get();
    }

    public Quiz saveQuiz(Quiz quiz) {
        //make sure that answer is not null
        quiz.setAnswer(quiz.getAnswer() == null ? new Integer[]{} : quiz.getAnswer());

        //set current account as quiz owner
        quiz.setAccount(accountService.getCurrentAccount());

        //save quiz to DB
        return quizRepository.save(quiz);
    }

    public Quiz updateQuizById(Quiz quizToUpdate, final Long id) {
        //find quiz to update
        Quiz quizFromDb = quizRepository.findById(id).get();

        //set new parameters to quiz
        quizFromDb.setAnswer(quizToUpdate.getAnswer());
        quizFromDb.setOptions(quizToUpdate.getOptions());
        quizFromDb.setText(quizToUpdate.getText());
        quizFromDb.setTitle(quizToUpdate.getTitle());

        //save updated quiz to DB
        return quizRepository.save(quizFromDb);
    }

    public void deleteQuizById(final Long id) {
        quizRepository.deleteById(id);
    }

    public void deleteAllQuizzes () {
        quizRepository.deleteAll();
    }

    public boolean existsById(Long id) {
        return quizRepository.existsById(id);
    }
}

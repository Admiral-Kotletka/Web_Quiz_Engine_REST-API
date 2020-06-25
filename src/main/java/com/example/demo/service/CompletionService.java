package com.example.demo.service;

import com.example.demo.entity.Completion;
import com.example.demo.repository.CompletionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Component
@Service
public class CompletionService {

    @Autowired
    CompletionRepository completionRepository;

    public Page<Completion> getCompletionsByAccountId(Long accountId, Integer page, Integer pageSize) {

        Pageable paging = PageRequest.of(page, pageSize);

        //receive list of completions
        List<Completion> completions = completionRepository.findAll();

        //remove not relevant records (by account id)
        for (int i = 0; i < completions.size(); i++){
            if (!completions.get(i).getAccountId().equals(accountId)) {
                completions.remove(i);
                i--;
            }
        }

        //reverse the list to get the necessary order
        Collections.reverse(completions);

        //setting up parameters for Page Implementation
        int start = (int) paging.getOffset();
        int end = Math.min((start + paging.getPageSize()), completions.size());

        //convert List to Page and return
        return new PageImpl<>(completions.subList(start, end), paging, completions.size());
    }

    public void saveCompletion (Completion completion) {
        completionRepository.save(completion);
    }
}

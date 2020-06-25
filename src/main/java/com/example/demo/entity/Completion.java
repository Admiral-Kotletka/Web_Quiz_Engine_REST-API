package com.example.demo.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class Completion {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Completion_id;
    @Column(name = "quiz_id")
    private Long id;
    @Column(name = "account_id")
    private Long accountId;
    private LocalDateTime completedAt;



    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Long getAccountId() {
        return accountId;
    }
    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    public Long getCompletion_id() {
        return Completion_id;
    }
    public void setCompletion_id(Long completion_id) {
        Completion_id = completion_id;
    }

    public LocalDateTime getCompletedAt() {
        return completedAt;
    }
    public void setCompletedAt(LocalDateTime completedAt) {
        this.completedAt = completedAt;
    }
}

package com.example.demo.entity;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "Quiz_id")
    private Long id;
    @Pattern(regexp = ".+")
    private String title;
    @Pattern(regexp = ".+")
    private String text;
    @Size(min = 2)
    @NotEmpty
    private String[] options;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer[] answer;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    public Account account;


    public Account getAccount() {
        return account;
    }
    public void setAccount(Account account) {
        this.account = account;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }

    public String[] getOptions() {
        return options;
    }
    public void setOptions(String[] option) {
        this.options = option;
    }

    public Integer[] getAnswer() {
        return answer;
    }
    public void setAnswer(Integer[] answer) {
        this.answer = answer;
    }
}

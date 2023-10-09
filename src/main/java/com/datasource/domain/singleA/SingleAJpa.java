package com.datasource.domain.singleA;

import jakarta.persistence.*;

@Entity
@Table(name = "single_a_jpa")
public class SingleAJpa {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(name = "test_text")
    private String testText;

    public SingleAJpa() {}

    public SingleAJpa(String testText) {
        this.testText = testText;
    }

    public Long getSeq() {
        return seq;
    }

    public String getTestText() {
        return testText;
    }
}

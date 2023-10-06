package com.datasource.domain.singleB;

import javax.persistence.*;

@Entity
@Table(name = "single_b_jpa")
public class SingleBJpa {
    @Id
    @GeneratedValue
    private Long seq;

    @Column(name = "test_text")
    private String testText;

    public SingleBJpa() {}

    public SingleBJpa(String testText) {
        this.testText = testText;
    }

    public Long getSeq() {
        return seq;
    }

    public String getTestText() {
        return testText;
    }
}

package com.datasource.domain.singleA;

import jakarta.persistence.*;

/**
 * <pre>
 *     JPA용 JTA 테스트를 위한 엔티티
 *     해당 엔티티는 Singel A(Postgresql) DB에서 사용.
 * </pre>
 */
@Entity
@Table(name = "single_a_jpa")
public class SingleAJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
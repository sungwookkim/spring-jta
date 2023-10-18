package com.datasource.domain.singleB;

import javax.persistence.*;

/**
 * <pre>
 *     JPA용 JTA 테스트를 위한 엔티티
 *     해당 엔티티는 Singel B(Postgresql) DB에서 사용.
 * </pre>
 */
@Entity
@Table(name = "single_b_jpa")
public class SingleBJpa {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

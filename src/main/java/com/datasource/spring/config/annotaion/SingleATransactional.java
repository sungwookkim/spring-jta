package com.datasource.spring.config.annotaion;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * <pre>
 *     해당 인터페이스는 Spring의 {@link Transactional}을 이용한 어노테이션임으로 추후에 Spring을 업그레이드할 경우 {@link Transactional}를 비교하여 동일한 속성으로 맞춰줘야한다.
 *     단 {@link Transactional#value()}, {@link Transactional#transactionManager()} 속성은 설정 하지 않는다.
 * </pre>
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Transactional(transactionManager = "singleATransactionManager")
public @interface SingleATransactional {
    /**
     * Alias for {@link Transactional#propagation}.
     */
    @AliasFor(annotation = Transactional.class)
    Propagation propagation() default Propagation.REQUIRED;

    /**
     * Alias for {@link Transactional#isolation}.
     */
    @AliasFor(annotation = Transactional.class)
    Isolation isolation() default Isolation.DEFAULT;

    /**
     * Alias for {@link Transactional#timeout}.
     */
    @AliasFor(annotation = Transactional.class)
    int timeout() default TransactionDefinition.TIMEOUT_DEFAULT;

    /**
     * Alias for {@link Transactional#readOnly}.
     */
    @AliasFor(annotation = Transactional.class)
    boolean readOnly() default false;

    /**
     * Alias for {@link Transactional#rollbackFor}.
     */
    @AliasFor(annotation = Transactional.class)
    Class<? extends Throwable>[] rollbackFor() default {};

    /**
     * Alias for {@link Transactional#rollbackForClassName}.
     */
    @AliasFor(annotation = Transactional.class)
    String[] rollbackForClassName() default {};

    /**
     * Alias for {@link Transactional#noRollbackFor}.
     */
    @AliasFor(annotation = Transactional.class)
    Class<? extends Throwable>[] noRollbackFor() default {};

    /**
     * Alias for {@link Transactional#noRollbackForClassName}.
     */
    @AliasFor(annotation = Transactional.class)
    String[] noRollbackForClassName() default {};
}

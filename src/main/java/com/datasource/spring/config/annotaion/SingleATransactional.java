package com.datasource.spring.config.annotaion;

import org.springframework.core.annotation.AliasFor;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.lang.annotation.*;

/**
 * <pre>
 *     Single-A의 Mybatis {@link Transactional}.
 *
 *     Spring {@link Transactional}를 활용한 커스텀 어노테이션.
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

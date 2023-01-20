package com.shulha.annotation;

import com.shulha.types.RepositoryTypes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Autowired {
    RepositoryTypes set() default RepositoryTypes.NULL;
}

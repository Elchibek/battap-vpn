package com.battap.vpn.service.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.annotation.Nonnull;
import javax.annotation.meta.TypeQualifierDefault;


/**
 * This annotation can be applied to a package, class or method to indicate that all
 * class fields and method parameters and return values in that element are nonnull
 * by default unless overridden.
 */
@Nonnull
@TypeQualifierDefault({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)

public @interface NonNullForAll {
}

package com.target.domain.esv.podam;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Target(value = { ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomPodamBooleanValue {

	/**
	 * The value to assign to the annotated attribute.
	 * 
	 * @return The value to assign to the annotated attribute
	 */
	boolean boolValue() default false;

}

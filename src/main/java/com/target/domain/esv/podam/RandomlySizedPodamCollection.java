package com.target.domain.esv.podam;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import uk.co.jemos.podam.annotations.strategies.ObjectStrategy;
import uk.co.jemos.podam.api.AttributeStrategy;
import uk.co.jemos.podam.utils.PodamConstants;

//TODO need to implement the allowEmpty attribute

@Documented
@Target(value = {ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface RandomlySizedPodamCollection {

	/**
	 * The upward bound of the colleciton size, this is 1 based (as in 1 is zero)
	 */
	
	int nbrElementsUpwardBound() default PodamConstants.ANNOTATION_COLLECTION_DEFAULT_NBR_ELEMENTS;
	
	boolean allowEmpty() default true;


	/**
	 * The strategy that will populate the annotated attribute.
	 * <p>
	 * The default, in order to make the strategy actually <i>optional</i> is
	 * type Object. At runtime, only if both the value of this annotation and
	 * the collection element type are Objects, then the collection will be set
	 * with type {@link Object}, otherwise the collection element type will win.
	 * </p>
	 * 
	 * @return The strategy that will populate the annotated attribute
	 */
	Class<? extends AttributeStrategy<?>> collectionElementStrategy() default ObjectStrategy.class;

	/**
	 * The strategy that will populate a map key on an attribute of type Map.
	 * <p>
	 * The default, in order to make the strategy actually <i>optional</i> is
	 * type Object. At runtime, only if both the value of this annotation and
	 * the collection element type are Objects, then the collection will be set
	 * with type {@link Object}, otherwise the collection element type will win.
	 * </p>
	 * 
	 * @return The strategy that will populate a map key on an attribute of type
	 *         Map.
	 */
	Class<? extends AttributeStrategy<?>> mapKeyStrategy() default ObjectStrategy.class;

	/**
	 * The strategy that will populate a map element on an attribute of type
	 * Map.
	 * <p>
	 * The default, in order to make the strategy actually <i>optional</i> is
	 * type Object. At runtime, only if both the value of this annotation and
	 * the collection element type are Objects, then the collection will be set
	 * with type {@link Object}, otherwise the collection element type will win.
	 * </p>
	 * 
	 * @return The strategy that will populate a map element on an attribute of
	 *         type Map.
	 */
	Class<? extends AttributeStrategy<?>> mapElementStrategy() default ObjectStrategy.class;

	/** It allows clients to specify a comment on this annotation */
	String comment() default "";

}

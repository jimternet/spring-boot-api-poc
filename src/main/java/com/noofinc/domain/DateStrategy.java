package com.noofinc.domain;

import java.lang.annotation.Annotation;
import java.util.Date;
import java.util.List;

import uk.co.jemos.podam.common.AttributeStrategy;

public class DateStrategy implements AttributeStrategy<Date> {

    /**
     * It returns an English post code.
     * <p>
     * This is just an example. More elaborated code could the implemented by
     * this method. This is just to proof the point.
     * </p>
     *
     * {@inheritDoc}
     */
    @Override
    public Date getValue(Class<?> attrType, List<Annotation> attrAnnotations) {
        return new Date();
    }

}

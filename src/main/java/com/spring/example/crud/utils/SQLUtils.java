package com.spring.example.crud.utils;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Expression;

public class SQLUtils {

    private static final String REPLACES = "áéíóúàèìòùãõâêîôôäëïöüçÁÉÍÓÚÀÈÌÒÙÃÕÂÊÎÔÛÄËÏÖÜÇ";
    private static final String MATCHES = "aeiouaeiouaoaeiooaeioucAEIOUAEIOUAOAEIOOAEIOUC";

    public static Expression<String> replace(
        CriteriaBuilder builder,
        Expression<String> path
    ) {
        return builder
            .function(
                "replace",
                String.class,
                builder.lower(path),
                builder.literal(REPLACES),
                builder.literal(MATCHES)
            );
    }
}

package org.dromara.weather.domain.jexl3;

public interface Expression {
    boolean execute();

    default void add(Expression expression){
    };
}

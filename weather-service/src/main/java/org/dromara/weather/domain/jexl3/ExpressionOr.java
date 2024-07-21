package org.dromara.weather.domain.jexl3;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ExpressionOr implements Expression {
    private final List<Expression> exceptions = new ArrayList<>();

    public void add(Expression expression) {
        exceptions.add(expression);
    }

    @Override
    public boolean execute() {
        if (exceptions.isEmpty()) {
            return true;
        }
        return exceptions.stream()
            .filter(Objects::nonNull)
            .map(Expression::execute)
            .reduce((a, b) -> a || b)
            .get();
    }
}


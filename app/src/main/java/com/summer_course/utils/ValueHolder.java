package com.summer_course.utils;

public class ValueHolder<T> {

    private T value;

    public ValueHolder() {

    }

    public ValueHolder(T value) {
        this.value = value;
    }

    public void setValue(T newValue) {
        this.value = newValue;
    }

    public T getValue() {
        return this.value;
    }

}

package com.katyshevtseva.lifetracker.core.utils;

@FunctionalInterface
public interface OneInKnob<T> {
    void execute(T t);
}

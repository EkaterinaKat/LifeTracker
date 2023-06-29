package com.katyshevtseva.lifetracker.utils;

@FunctionalInterface
public interface OneInKnob<T> {
    void execute(T t);
}

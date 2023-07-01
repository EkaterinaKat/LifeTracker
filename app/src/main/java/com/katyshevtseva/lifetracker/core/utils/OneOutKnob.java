package com.katyshevtseva.lifetracker.core.utils;

@FunctionalInterface
public interface OneOutKnob<T> {
    T execute();
}

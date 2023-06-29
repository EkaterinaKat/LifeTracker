package com.katyshevtseva.lifetracker.utils;

@FunctionalInterface
public interface OneOutKnob<T> {
    T execute();
}

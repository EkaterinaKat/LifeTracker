package com.katyshevtseva.lifetracker.core.utils;

@FunctionalInterface
public interface OneInOneOutKnob<P, R> {
    R execute(P p);
}

package com.katyshevtseva.lifetracker.utils;

@FunctionalInterface
public interface OneInOneOutKnob<P, R> {
    R execute(P p);
}

package com.akira.core.api.tool;

import com.akira.core.api.util.CommonUtils;

import java.util.Objects;

public class Tuple<A, B> {
    private A a;
    private B b;

    public Tuple(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public A getNonNullA() {
        return CommonUtils.requireNonNull(a);
    }

    public B getNonNullB() {
        return CommonUtils.requireNonNull(b);
    }

    void setA(A a) {
        this.a = a;
    }

    void setB(B b) {
        this.b = b;
    }

    public boolean equals(Object object) {
        if (object == null || getClass() != object.getClass()) return false;
        Tuple<?, ?> tuple = (Tuple<?, ?>) object;
        return Objects.equals(a, tuple.a) && Objects.equals(b, tuple.b);
    }

    public int hashCode() {
        return Objects.hash(a, b);
    }
}

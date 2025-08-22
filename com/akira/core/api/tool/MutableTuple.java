package com.akira.core.api.tool;

public class MutableTuple<A, B> extends Tuple<A, B> {
    public MutableTuple(A a, B b) {
        super(a, b);
    }

    public void setA(A a) {
        super.setA(a);
    }

    public void setB(B b) {
        super.setB(b);
    }
}

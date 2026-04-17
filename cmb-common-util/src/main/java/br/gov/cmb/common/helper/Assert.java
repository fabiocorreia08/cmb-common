package br.gov.cmb.common.helper;


public final class Assert {

    public static <X extends Throwable> void precondition(boolean expression, X t) throws X {
        if (expression) {
            throw t;
        }
    }
}

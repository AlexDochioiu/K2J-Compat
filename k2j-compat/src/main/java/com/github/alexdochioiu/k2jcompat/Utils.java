package com.github.alexdochioiu.k2jcompat;

/**
 * Created by Alexandru Iustin Dochioiu on 01-Mar-19
 */
class Utils {
    /**
     * Checks that the specified object reference is not {@code null}. This
     * method is designed primarily for doing parameter validation in methods
     * and constructors, as demonstrated below:
     * <blockquote><pre>
     * public Foo(Bar bar) {
     *     this.bar = Utils.requireNonNull(bar);
     * }
     * </pre></blockquote>
     *
     * Picked up from <b>java.util</b>
     *
     * @param obj the object reference to check for nullity
     * @param <T> the type of the reference
     * @return {@code obj} if not {@code null}
     * @throws NullPointerException if {@code obj} is {@code null}
     */
    static <T> T requireNonNull(T obj) {
        if (obj == null)
            throw new NullPointerException();
        return obj;
    }
}

package com.github.alexdochioiu.k2jcompat;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static com.github.alexdochioiu.k2jcompat.Utils.requireNonNull;

/**
 * Created by Alexandru Iustin Dochioiu on 01-Mar-19
 */
public class K2JCompat {

    public static <T> K2JWrapper<T> take(T object) {
        return new K2JWrapper<>(object);
    }

    public static final class K2JWrapper<T>{
        @Nullable
        private final T wrappedObject;

        private K2JWrapper(@Nullable final T wrappedObject) {
            this.wrappedObject = wrappedObject;
        }

        @NonNull
        public final <Y> K2JWrapper<Y> let(@NonNull final ILet<T, Y> iLet) {
            final Y wrapped = requireNonNull(iLet).doLet(wrappedObject);;

            return new K2JWrapper<>(wrapped);
        }

        @NonNull
        public final <Y> K2JWrapper<Y> _let(@NonNull final ILet<T, Y> iLet) {
            final Y wrapped;
            if (wrappedObject != null) {
                wrapped = requireNonNull(iLet).doLet(wrappedObject);
            } else {
                wrapped = null;
            }

            return new K2JWrapper<>(wrapped);
        }

        @NonNull
        public final K2JWrapper<T> also(@NonNull final IAlso<T> iAlso) {
            requireNonNull(iAlso).doAlso(wrappedObject);

            return this;
        }

        @NonNull
        public final K2JWrapper<T> _also(@NonNull final IAlso<T> iAlso) {
            if (wrappedObject != null) {
                requireNonNull(iAlso).doAlso(wrappedObject);
            }

            return this;
        }

        @NonNull
        public final K2JWrapper<T> takeUnless(@NonNull final ITake<T> iTake) {
            if (requireNonNull(iTake).doTake(wrappedObject)) {
                return new K2JWrapper<>(null);
            } else {
                return this;
            }
        }

        @NonNull
        public final K2JWrapper<T> takeIf(@NonNull final ITake<T> iTake) {
            if (requireNonNull(iTake).doTake(wrappedObject)) {
                return this;
            } else {
                return new K2JWrapper<>(null);
            }
        }

        public final T unwrap() {
            return wrappedObject;
        }
    }

    public interface ILet<T, Y> {
        Y doLet(T object);
    }

    public interface IAlso<T> {
        void doAlso(T object);
    }

    public interface ITake<T> {
        boolean doTake(T object);
    }
}

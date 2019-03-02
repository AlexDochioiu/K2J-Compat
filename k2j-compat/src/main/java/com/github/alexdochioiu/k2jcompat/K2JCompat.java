/*
 * Copyright 2019 Alexandru Iustin Dochioiu
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.github.alexdochioiu.k2jcompat;

import static com.github.alexdochioiu.k2jcompat.Utils.requireNonNull;

/**
 * Created by Alexandru Iustin Dochioiu on 01-Mar-19
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class K2JCompat {

    public static <T> K2JWrapper<T> take(T object) {
        return new K2JWrapper<>(object);
    }

    public static final class K2JWrapper<T>{
        private final T wrappedObject;

        private K2JWrapper(final T wrappedObject) {
            this.wrappedObject = wrappedObject;
        }

        public final <Y> K2JWrapper<Y> let(final ILet<T, Y> iLet) {
            final Y wrapped = requireNonNull(iLet).doLet(wrappedObject);

            return new K2JWrapper<>(wrapped);
        }

        public final <Y> K2JWrapper<Y> _let(final ILet<T, Y> iLet) {
            final Y wrapped;
            if (wrappedObject != null) {
                wrapped = requireNonNull(iLet).doLet(wrappedObject);
            } else {
                wrapped = null;
            }

            return new K2JWrapper<>(wrapped);
        }

        public final K2JWrapper<T> also(final IAlso<T> iAlso) {
            requireNonNull(iAlso).doAlso(wrappedObject);

            return this;
        }

        public final K2JWrapper<T> _also(final IAlso<T> iAlso) {
            if (wrappedObject != null) {
                requireNonNull(iAlso).doAlso(wrappedObject);
            }

            return this;
        }

        public final K2JWrapper<T> takeUnless(final ITake<T> iTake) {
            if (requireNonNull(iTake).doTake(wrappedObject)) {
                return new K2JWrapper<>(null);
            } else {
                return this;
            }
        }

        public final K2JWrapper<T> _takeUnless(final ITake<T> iTake) {
            if (wrappedObject != null) {
                if (requireNonNull(iTake).doTake(wrappedObject)) {
                    return new K2JWrapper<>(null);
                } else {
                    return this;
                }
            } else {
                return new K2JWrapper<>(null);
            }
        }

        public final K2JWrapper<T> takeIf(final ITake<T> iTake) {
            if (requireNonNull(iTake).doTake(wrappedObject)) {
                return this;
            } else {
                return new K2JWrapper<>(null);
            }
        }

        public final K2JWrapper<T> _takeIf(final ITake<T> iTake) {
            if (wrappedObject != null) {
                if (requireNonNull(iTake).doTake(wrappedObject)) {
                    return this;
                } else {
                    return new K2JWrapper<>(null);
                }
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

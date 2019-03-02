package com.github.alexdochioiu.k2jcompat;
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

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Alexandru Iustin Dochioiu on 02-Mar-19
 */
public class UtilsTest {

    @Test(expected = NullPointerException.class)
    public void requireNonNull_throwsOnNull() {
        Utils.requireNonNull(null);
    }

    @Test
    public void requireNonNull_doesNotThrowOnNonNull() {
        Utils.requireNonNull("test");
    }

    @Test
    public void requireNonNull_returnsArg() {
        final SimpleClass expected = new SimpleClass();
        final SimpleClass actual = Utils.requireNonNull(expected);

        Assert.assertSame(expected, actual);
    }

    private class SimpleClass{}
}

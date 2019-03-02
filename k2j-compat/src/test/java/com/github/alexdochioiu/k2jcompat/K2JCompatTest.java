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

import org.junit.Assert;
import org.junit.Test;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import static com.github.alexdochioiu.k2jcompat.K2JCompat.take;

/**
 * Created by Alexandru Iustin Dochioiu on 02-Mar-19
 */
public class K2JCompatTest {

    @Test
    public void take_returnsK2JWrapperObject() {
        final Object object = take("Test");
        //noinspection ConstantConditions
        Assert.assertTrue(object instanceof K2JCompat.K2JWrapper);
    }

    @Test
    public void unwrap_returnsExpectedObjectAfterTake() {
        final SimpleClass expected = new SimpleClass();
        final SimpleClass actual = take(expected).unwrap();

        Assert.assertSame(expected, actual);
    }

    @Test(expected = NullPointerException.class)
    public void let_crashOnNullILet() {
        take("").let(null);
    }

    @Test(expected = NullPointerException.class)
    public void _let_crashOnNullILet() {
        take("")._let(null);
    }

    @Test(expected = NullPointerException.class)
    public void also_crashOnNullIAlso() {
        take("").also(null);
    }

    @Test(expected = NullPointerException.class)
    public void _also_crashOnNullIAlso() {
        take("")._also(null);
    }

    @Test(expected = NullPointerException.class)
    public void takeIf_crashOnNullITake() {
        take("").takeIf(null);
    }

    @Test(expected = NullPointerException.class)
    public void takeUnless_crashOnNullITake() {
        take("").takeUnless(null);
    }

    @Test
    public void let_objectReceivedIsTheExpectedOne() {
        final AtomicReference<SimpleClass> fromLet = new AtomicReference<>();

        final SimpleClass given = new SimpleClass();
        take(given)
                .let((simpleObj) -> {
                    fromLet.set(simpleObj);
                    return simpleObj.returnNew();
                });

        Assert.assertSame(given, fromLet.get());
    }

    @Test
    public void _let_objectReceivedIsTheExpectedOne() {
        final AtomicReference<SimpleClass> fromLet = new AtomicReference<>();

        final SimpleClass given = new SimpleClass();
        take(given)
                ._let((simpleObj) -> {
                    fromLet.set(simpleObj);
                    return simpleObj.returnNew();
                });

        Assert.assertSame(given, fromLet.get());
    }

    @Test
    public void also_objectReceivedIsTheExpectedOne() {
        final AtomicReference<SimpleClass> fromLet = new AtomicReference<>();

        final SimpleClass given = new SimpleClass();
        take(given)
                .also(fromLet::set);

        Assert.assertSame(given, fromLet.get());
    }

    @Test
    public void _also_objectReceivedIsTheExpectedOne() {
        final AtomicReference<SimpleClass> fromLet = new AtomicReference<>();

        final SimpleClass given = new SimpleClass();
        take(given)
                ._also(fromLet::set);

        Assert.assertSame(given, fromLet.get());
    }

    @Test
    public void let_objectReturnedIsTheExpectedOne() {
        final AtomicReference<SimpleClass> returnedInLet = new AtomicReference<>();

        final SimpleClass given = new SimpleClass();
        final SimpleClass actual = take(given)
                .let((simpleObj) -> {
                    final SimpleClass returnedObj = simpleObj.returnNew();
                    returnedInLet.set(returnedObj);
                    return returnedObj;
                })
                .unwrap();

        Assert.assertSame(actual, returnedInLet.get());
        Assert.assertNotSame(given, actual);
    }

    @Test
    public void _let_objectReturnedIsTheExpectedOne() {
        final AtomicReference<SimpleClass> returnedInLet = new AtomicReference<>();

        final SimpleClass given = new SimpleClass();
        final SimpleClass actual = take(given)
                ._let((simpleObj) -> {
                    final SimpleClass returnedObj = simpleObj.returnNew();
                    returnedInLet.set(returnedObj);
                    return returnedObj;
                })
                .unwrap();

        Assert.assertSame(actual, returnedInLet.get());
        Assert.assertNotSame(given, actual);
    }

    @Test
    public void also_objectReturnedIsTheExpectedOne() {
        final SimpleClass given = new SimpleClass();
        final SimpleClass actual = take(given)
                .also((simpleObj) -> simpleObj.value = "dsad")
                .unwrap();

        Assert.assertSame(given, actual);
    }

    @Test
    public void also_objectCanBeModified() {
        final String initialString = "init";
        final String finalString = "final";

        final SimpleClass given = new SimpleClass();
        given.value = initialString;

        take(given)
                .also((simpleObj) -> simpleObj.value = finalString);

        Assert.assertEquals(given.value, finalString);
    }

    @Test
    public void _also_objectReturnedIsTheExpectedOne() {
        final SimpleClass given = new SimpleClass();
        final SimpleClass actual = take(given)
                ._also((simpleObj) -> simpleObj.value = "dsad")
                .unwrap();

        Assert.assertSame(given, actual);
    }

    @Test
    public void _also_objectCanBeModified() {
        final String initialString = "init";
        final String finalString = "final";

        final SimpleClass given = new SimpleClass();
        given.value = initialString;

        take(given)
                ._also((simpleObj) -> simpleObj.value = finalString);

        Assert.assertEquals(given.value, finalString);
    }

    @Test
    public void let_acceptsNull() {
        final AtomicReference<SimpleClass> fromLet = new AtomicReference<>();
        final AtomicBoolean letCalled = new AtomicBoolean(false);

        take((SimpleClass) null)
                .let((simpleObj) -> {
                    letCalled.set(true);
                    fromLet.set(simpleObj);
                    return this;
                });

        Assert.assertTrue(letCalled.get());
        Assert.assertNull(fromLet.get());
    }

    @Test
    public void _let_ignoresIfNull() {
        final AtomicBoolean letCalled = new AtomicBoolean(false);

        take((SimpleClass) null)
                ._let((simpleObj) -> {
                    letCalled.set(true);
                    return this;
                });

        Assert.assertFalse(letCalled.get());
    }

    @Test
    public void also_acceptsNull() {
        final AtomicReference<SimpleClass> fromAlso = new AtomicReference<>();
        final AtomicBoolean alsoCalled = new AtomicBoolean(false);

        take((SimpleClass) null)
                .also((simpleObj) -> {
                    alsoCalled.set(true);
                    fromAlso.set(simpleObj);
                });

        Assert.assertTrue(alsoCalled.get());
        Assert.assertNull(fromAlso.get());
    }

    @Test
    public void _also_ignoresIfNull() {
        final AtomicBoolean alsoCalled = new AtomicBoolean(false);

        take((SimpleClass) null)
                ._also((simpleObj) -> alsoCalled.set(true));

        Assert.assertFalse(alsoCalled.get());
    }

    @Test
    public void takeUnless_returnsGivenIfFalse() {
        final SimpleClass given = new SimpleClass();
        final SimpleClass returned = take(given)
                .takeUnless((in) -> false)
                .unwrap();

        Assert.assertSame(given, returned);
    }

    @Test
    public void takeUnless_returnsNullIfTrue() {
        final SimpleClass returned = take(new SimpleClass())
                .takeUnless((in) -> true)
                .unwrap();

        Assert.assertNull(returned);
    }

    @Test
    public void takeUnless_nullInput_returnsNullIfFalse() {
        final AtomicBoolean takeUnlessCalled = new AtomicBoolean(false);

        final SimpleClass returned = take((SimpleClass) null)
                .takeUnless((in) -> {
                    takeUnlessCalled.set(true);
                    return false;
                })
                .unwrap();

        Assert.assertTrue(takeUnlessCalled.get());
        Assert.assertNull(returned);
    }

    @Test
    public void takeUnless_nullInput_returnsNullIfTrue() {
        final AtomicBoolean takeUnlessCalled = new AtomicBoolean(false);

        final SimpleClass returned = take((SimpleClass) null)
                .takeUnless((in) -> {
                    takeUnlessCalled.set(true);
                    return true;
                })
                .unwrap();

        Assert.assertTrue(takeUnlessCalled.get());
        Assert.assertNull(returned);
    }






    @Test
    public void _takeUnless_returnsGivenIfFalse() {
        final SimpleClass given = new SimpleClass();
        final SimpleClass returned = take(given)
                ._takeUnless((in) -> false)
                .unwrap();

        Assert.assertSame(given, returned);
    }

    @Test
    public void _takeUnless_returnsNullIfTrue() {
        final SimpleClass returned = take(new SimpleClass())
                ._takeUnless((in) -> true)
                .unwrap();

        Assert.assertNull(returned);
    }

    @Test
    public void _takeUnless_nullInput_doesNotGetCalledAndReturnsNull() {
        final AtomicBoolean takeUnlessCalled = new AtomicBoolean(false);

        final SimpleClass returned = take((SimpleClass) null)
                ._takeUnless((in) -> {
                    takeUnlessCalled.set(true);
                    return false;
                })
                .unwrap();

        Assert.assertFalse(takeUnlessCalled.get());
        Assert.assertNull(returned);
    }

    @Test
    public void takeIf_returnsGivenIfTrue() {
        final SimpleClass given = new SimpleClass();
        final SimpleClass returned = take(given)
                .takeIf((in) -> true)
                .unwrap();

        Assert.assertSame(given, returned);
    }

    @Test
    public void takeIf_returnsNullIfFalse() {
        final SimpleClass returned = take(new SimpleClass())
                .takeIf((in) -> false)
                .unwrap();

        Assert.assertNull(returned);
    }

    @Test
    public void takeIf_nullInput_returnsNullIfTrue() {
        final AtomicBoolean takeIfCalled = new AtomicBoolean(false);

        final SimpleClass returned = take((SimpleClass) null)
                .takeIf((in) -> {
                    takeIfCalled.set(true);
                    return true;
                })
                .unwrap();

        Assert.assertTrue(takeIfCalled.get());
        Assert.assertNull(returned);
    }

    @Test
    public void takeIf_nullInput_returnsNullIfFalse() {
        final AtomicBoolean takeIfCalled = new AtomicBoolean(false);

        final SimpleClass returned = take((SimpleClass) null)
                .takeIf((in) -> {
                    takeIfCalled.set(true);
                    return false;
                })
                .unwrap();

        Assert.assertTrue(takeIfCalled.get());
        Assert.assertNull(returned);
    }

    @Test
    public void _takeIf_returnsGivenIfTrue() {
        final SimpleClass given = new SimpleClass();
        final SimpleClass returned = take(given)
                ._takeIf((in) -> true)
                .unwrap();

        Assert.assertSame(given, returned);
    }

    @Test
    public void _takeIf_returnsNullIfFalse() {
        final SimpleClass returned = take(new SimpleClass())
                ._takeIf((in) -> false)
                .unwrap();

        Assert.assertNull(returned);
    }

    @Test
    public void _takeIf_nullInput_returnsNullIfTrue() {
        final AtomicBoolean takeIfCalled = new AtomicBoolean(false);

        final SimpleClass returned = take((SimpleClass) null)
                ._takeIf((in) -> {
                    takeIfCalled.set(true);
                    return true;
                })
                .unwrap();

        Assert.assertFalse(takeIfCalled.get());
        Assert.assertNull(returned);
    }

    @Test
    public void let_chain() {
        final String hello = "Hello ";
        final String world = "World";
        final String exclamation = "!";
        final String expected = hello + world + exclamation;

        final String returned = take(hello)
                .let((in) -> in + world)
                .let((in) -> in + exclamation)
                .unwrap();

        Assert.assertEquals(expected, returned);
    }

    @Test
    public void _let_chain() {
        final String hello = "Hello ";
        final String world = "World";
        final String exclamation = "!";
        final String expected = hello + world + exclamation;

        final String returned = take(hello)
                ._let((in) -> in + world)
                ._let((in) -> in + exclamation)
                .unwrap();

        Assert.assertEquals(expected, returned);
    }

    @Test(expected = NullPointerException.class)
    public void let_chainWithNull() {
        final String hello = "Hello ";

        take(hello)
                .let((in) -> (String) null)
                .let((in) -> in.equals("!"));
    }

    @Test
    public void _let_chainWithNull() {
        final String hello = "Hello ";

        final Boolean returned = take(hello)
                ._let((in) -> (String) null)
                ._let((in) -> in.equals("!"))
                .unwrap();

        Assert.assertNull(returned);
    }

    @Test
    public void _letAndLet_chainWithNull_1() {
        final String hello = "Hello ";

        final Boolean returned = take(hello)
                .let((in) -> (String) null)
                ._let((in) -> in.equals("!"))
                .unwrap();

        Assert.assertNull(returned);
    }

    @Test(expected = NullPointerException.class)
    public void _letAndLet_chainWithNull_2() {
        final String hello = "Hello ";

        take(hello)
                ._let((in) -> (String) null)
                .let((in) -> in.equals("!"));
    }

    @Test
    public void also_chain() {
        final String hello = "Hello ";
        final String world = "World";
        final String exclamation = "!";
        final StringBuilder input = new StringBuilder();
        final String expected = hello + world + exclamation;

        final StringBuilder returned = take(input)
                .also((in) -> in.append(hello))
                .also((in) -> in.append(world))
                .also((in) -> in.append(exclamation))
                .unwrap();

        Assert.assertEquals(expected, returned.toString());
    }

    @Test
    public void _also_chain() {
        final String hello = "Hello ";
        final String world = "World";
        final String exclamation = "!";
        final StringBuilder input = new StringBuilder();
        final String expected = hello + world + exclamation;

        final StringBuilder returned = take(input)
                ._also((in) -> in.append(hello))
                ._also((in) -> in.append(world))
                ._also((in) -> in.append(exclamation))
                .unwrap();

        Assert.assertEquals(expected, returned.toString());
    }

    @Test
    public void also_chainWithNull() {
        final AtomicBoolean firstAlsoCalled = new AtomicBoolean(false);
        final AtomicBoolean secondAlsoCalled = new AtomicBoolean(false);

        take((String) null)
                .also((in) -> firstAlsoCalled.set(true))
                .also((in) -> secondAlsoCalled.set(true));

        Assert.assertTrue(firstAlsoCalled.get());
        Assert.assertTrue(secondAlsoCalled.get());
    }

    @Test
    public void _also_chainWithNull() {
        final AtomicBoolean firstAlsoCalled = new AtomicBoolean(false);
        final AtomicBoolean secondAlsoCalled = new AtomicBoolean(false);

        take((String) null)
                ._also((in) -> firstAlsoCalled.set(true))
                ._also((in) -> secondAlsoCalled.set(true));

        Assert.assertFalse(firstAlsoCalled.get());
        Assert.assertFalse(secondAlsoCalled.get());
    }

    @Test
    public void _alsoAndAlso_chainWithNull_1() {
        final AtomicBoolean firstAlsoCalled = new AtomicBoolean(false);
        final AtomicBoolean secondAlsoCalled = new AtomicBoolean(false);

        take((String) null)
                .also((in) -> firstAlsoCalled.set(true))
                ._also((in) -> secondAlsoCalled.set(true));

        Assert.assertTrue(firstAlsoCalled.get());
        Assert.assertFalse(secondAlsoCalled.get());
    }

    @Test
    public void _alsoAndAlso_chainWithNull_2() {
        final AtomicBoolean firstAlsoCalled = new AtomicBoolean(false);
        final AtomicBoolean secondAlsoCalled = new AtomicBoolean(false);

        take((String) null)
                ._also((in) -> firstAlsoCalled.set(true))
                .also((in) -> secondAlsoCalled.set(true));

        Assert.assertFalse(firstAlsoCalled.get());
        Assert.assertTrue(secondAlsoCalled.get());
    }

    @Test
    public void chain_takeIfTakeUnless() {
        final int input = 102;

        final int returned = take(input)
                .takeIf((in) -> in == input)
                .takeUnless((in) -> in == 3)
                .unwrap();

        Assert.assertEquals(input, returned);
    }

    @Test
    public void chain_mixed_1() {
        final int input = 102;
        final SimpleClass simpleClass = new SimpleClass();

        final SimpleClass returned = take(input)
                .let((in) -> in + 2)
                .also((in) -> Assert.assertEquals(104, in.intValue()))
                .let((__) -> simpleClass)
                ._also((in) -> Assert.assertSame(simpleClass, in))
                .unwrap();

        Assert.assertSame(simpleClass, returned);
    }

    @Test
    public void chain_mixed_2() {
        final int input = 102;
        final SimpleClass simpleClass = new SimpleClass();

        final SimpleClass returned = take(input)
                .let((in) -> in + 2)
                .also((in) -> Assert.assertEquals(104, in.intValue()))
                .let((__) -> simpleClass)
                .takeUnless(simpleClass::equals) // gives null
                ._also((in) -> Assert.fail("this _also should not have been called"))
                .also(Assert::assertNull)
                ._let((in) -> {
                    Assert.fail("this _let should not have been called");
                    return in;
                })
                .unwrap();

        Assert.assertNull(returned);
    }

    @Test
    public void chain_mixed_3() {
        final Object returned = take("")
                .<String>let((__) -> null)
                ._takeUnless((in) -> {
                    Assert.fail("Should not call _takeUnless");
                    return true;
                })
                .takeUnless((in) -> {
                    Assert.assertNull(in);
                    return true;
                })
                ._also((in) -> Assert.fail("this _also should not have been called"))
                .also(Assert::assertNull)
                ._let((in) -> {
                    Assert.fail("this _let should not have been called");
                    return in;
                })
                .unwrap();

        Assert.assertNull(returned);
    }

   /* @Test TODO custom lint to warn against this case
    public void chain_takeIfTakeUnless_2() {
        final int input = 102;

        final Integer returned = take(input)
                .takeIf((in) -> in == input + 1)
                .takeUnless((in) -> in == 3)
                .unwrap();

        Assert.assertEquals(input, returned.intValue());
    }*/

    private class SimpleClass {
        private String value;

        private SimpleClass returnNew() {
            return new SimpleClass();
        }
    }
}

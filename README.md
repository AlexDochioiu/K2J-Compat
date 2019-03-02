# K2J-Compat
### Bringing some of the Kotlin goodness to java

[![bintray](https://api.bintray.com/packages/jeefo12/Kotlin2Java-Compat/k2j-compat/images/download.svg) ](https://bintray.com/jeefo12/Kotlin2Java-Compat/k2j-compat/_latestVersion)
[![License](https://img.shields.io/badge/License-Apache-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0.html)
[![Build Status](https://travis-ci.org/AlexDochioiu/K2J-Compat.svg?branch=master)](https://travis-ci.org/AlexDochioiu/TeaTime)
[![codecov](https://codecov.io/gh/AlexDochioiu/K2J-Compat/branch/master/graph/badge.svg)](https://codecov.io/gh/AlexDochioiu/TeaTime)

## Installation with Android Gradle
```groovy
// Add K2J-Compat dependency
dependencies {
    implementation 'com.github.alexdochioiu:k2j-compat:1.0.1'
}
```

## Usage

#### 1. Kotlin's `.let{ }` as `.let()`

```
import static com.github.alexdochioiu.k2jcompat.K2JCompat.take;

final String number = take("+44 77 1234 1234")
                .let((it) -> "0" + it.substring(4)) // 077 1234 1234
                .let((it) -> it.replaceAll(" ", "")) // 07712341234
                .unwrap(); // unwraps the result to String

``` 

#### 2. Kotlin's `?.let{ }` as `._let()`

```
import static com.github.alexdochioiu.k2jcompat.K2JCompat.take;

final String nullStr = null;
final String number = take(nullStr)
                ._let((it) -> it.replaceAll(" ", "")) // this code will not run as 'it' is null
                .unwrap(); // returns null

``` 

#### 3. Kotlin's `.also{ }` as `.also()`

```
import static com.github.alexdochioiu.k2jcompat.K2JCompat.take;

StringBuilder builder = new StringBuilder();
take(builder).also((it) -> it.append("Hello World!"));
```

#### 4. Kotlin's `?.also{ }` as `._also()`

```
import static com.github.alexdochioiu.k2jcompat.K2JCompat.take;

StringBuilder builder = null;
take(builder)._also((it) -> it.append("Hello World!"));
// Note: using 'also' instead of '_also' will cause a null pointer exception when trying to append
```

#### 5. Kotlin's `.takeIf{ }` as `.takeIf()`

```
import static com.github.alexdochioiu.k2jcompat.K2JCompat.take;

String phoneNumber = "889944";
Integer number = take(phoneNumber)
                .takeIf((it) -> it.matches("[0-9]+"))
                ._let(Integer::parseInt)
                .unwrap();
```

#### 6. Kotlin's `?.takeIf{ }` as `._takeIf()`

```
import static com.github.alexdochioiu.k2jcompat.K2JCompat.take;

String phoneNumber = null;
Integer number = take(phoneNumber)
                ._takeIf((it) -> it.matches("[0-9]+"))
                ._let(Integer::parseInt)
                .unwrap();
```
**Note: If you use *int* instead of *Integer* NPE will be thrown as java will try to unbox a null Integer to int**

#### 7. Kotlin's `.takeUnless{ }` as `.takeUnless()`

*Similar to takeIf but with inverted logic*

#### 8. Kotlin's `?.takeUnless{ }` as `._takeUnless()`

*Similar to _takeIf but with inverted logic*

### Known Limitations
* Unlike Kotlin, Java has primitive data types which get boxed/unboxed automatically into objects when needed. However, NPE is thrown when Java tries to unbox a null Object to a primitive data type. This cannot be solved so a custom lint will be created to add a warning when using primitives are used.

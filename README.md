[![Build Status](https://travis-ci.com/mtumilowicz/java11-predicate.svg?branch=master)](https://travis-ci.com/mtumilowicz/java11-predicate)

# java11-predicate
The main goal of this project is to show updated Predicate Interface 
from Java 11.

# overview
* **and**: returns a composed predicate that represents a short-circuiting 
logical AND of this predicate and another
    ```
    default Predicate<T> and(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) && other.test(t);
    }    
    ```
    
* **negate**: returns a predicate that represents the logical negation 
of this predicate
    ```
    default Predicate<T> negate() {
            return (t) -> !test(t);
        }
    ```
    
* **or**: returns a composed predicate that represents a short-circuiting 
logical OR of this predicate and another
    ```
    default Predicate<T> or(Predicate<? super T> other) {
        Objects.requireNonNull(other);
        return (t) -> test(t) || other.test(t);
    }    
    ```
    
* **isEqual**: returns a predicate that tests if two arguments are equal 
according to `Objects.equals(Object, Object)`
    ```
    static <T> Predicate<T> isEqual(Object targetRef) {
        return (null == targetRef)
                ? Objects::isNull
                : object -> targetRef.equals(object);
    }    
    ```
    
* **not**: returns a predicate that is the negation of the supplied 
predicate
    ```
    static <T> Predicate<T> not(Predicate<? super T> target) {
        Objects.requireNonNull(target);
        return (Predicate<T>)target.negate();
    }    
    ```
    
# best practices
Suppose that we want to filter Stream of objects by some field
```
class X {
    String value;
    
    // constructors, getters...
}
```
* naive approach
    ```
    List<X> filtered = xes.stream()
            .filter(x -> Objects.equals(x.value, "test"))
            .collect(Collectors.toList());
    ```
* better approach
    1. define static method in X
        ```
        static Predicate<X> byValue(String value) {
            return x -> (null != x) && Objects.equals(x.value, value);
        }    
        ```
    1. construct pipeline
        ```
        List<X> filtered = xes.stream()
                .filter(X.byValue("test1"))
                .collect(Collectors.toList());        
        ```
    1. easy to compose
        ```
        var filtered = xes.stream()
                .filter(X.byValue("test1").or(X.byValue("test2")))
                .collect(Collectors.toList());
        ```
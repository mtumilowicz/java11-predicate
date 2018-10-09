import org.junit.Test;

import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * Created by mtumilowicz on 2018-10-09.
 */
public class PredicateTest {

    @Test
    public void and() {

    }

    @Test
    public void negate() {

    }

    @Test
    public void or() {

    }

    @Test
    public void isEqual() {

    }

    @Test
    public void not() {

    }

    @Test
    public void filter_cleanCode() {
        Stream.of(new X("test1"), new X("test2"))
                .filter(X.byValue("test1"))
                .forEach(System.out::println);
    }
}

class X {
    String value;

    X(String value) {
        this.value = value;
    }

    static Predicate<X> byValue(String value) {
        return x -> (null != x) && Objects.equals(x.value, value);
    }
}

import java.util.Objects;
import java.util.function.Predicate;

import static java.util.Objects.nonNull;

/**
 * Created by mtumilowicz on 2019-01-03.
 */
class X {
    
    String value;

    X(String value) {
        this.value = value;
    }

    static Predicate<X> byValue(String value) {
        return x -> nonNull(x) && Objects.equals(x.value, value);
    }
}

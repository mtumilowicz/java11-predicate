import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;

/**
 * Created by mtumilowicz on 2018-10-09.
 */
public class PredicateTest {
    
    @Test
    public void and() {
//        given
        Predicate<Object> predicate1 = x -> true;
        Predicate<Object> predicate2 = x -> false;
        
//        when
        Predicate<Object> and = predicate1.and(predicate2);
        
//        then
        assertFalse(and.test(new Object()));
    }

    @Test
    public void negate() {
//        given
        Predicate<Object> predicate1 = x -> true;

//        when
        Predicate<Object> negate = predicate1.negate();

//        then
        assertFalse(negate.test(new Object()));
    }

    @Test
    public void or() {
//        given
        Predicate<Object> predicate1 = x -> true;
        Predicate<Object> predicate2 = x -> false;

//        when
        Predicate<Object> or = predicate1.or(predicate2);

//        then
        assertTrue(or.test(new Object()));
    }

    @Test
    public void isEqual() {
//        given
        Predicate<Object> isEqual = Predicate.isEqual("test");
        
//        expect
        assertTrue(isEqual.test("test"));
    }

    @Test
    public void not() {
//        given
        Predicate<Object> predicate = x -> true;
        
//        when
        Predicate<Object> not = Predicate.not(predicate);
        
//        then
        assertFalse(not.test(new Object()));
    }

    @Test
    public void filter_cleanCode() {
//        given
        List<X> xes = Arrays.asList(new X("test1"), new X("test2"));

//        when
        List<X> filtered = xes.stream()
                .filter(X.byValue("test1"))
                .collect(Collectors.toList());
        
//        then
        assertThat(filtered, hasSize(1));
        assertThat(filtered.get(0).value, is("test1"));
        
    }

    @Test
    public void composing_filter_cleanCode() {
//        given
        var xes = List.of(new X("test1"), new X("test2"), new X("test3"));

//        when
        var filtered = xes.stream()
                .filter(X.byValue("test1").or(X.byValue("test2")))
                .collect(Collectors.toList());

//        then
        assertThat(filtered, hasSize(2));
        assertThat(filtered.get(0).value, is("test1"));
        assertThat(filtered.get(1).value, is("test2"));
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

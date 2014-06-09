package io.mikael.convert.basic;

import io.mikael.convert.bean.BeanConverter;
import org.junit.Test;

public class BasicTests {

    public static class Foo {
        public String getA() { return "A"; }
        public void invalid(final String in) { System.err.println("INVALID"); }
    }

    public static class Foo2 extends Foo {
        @Override
        public String getA() { return "B"; }
    }

    public static class Bar {
        public void setB(final String b) { System.out.println("b: " + b); }
        public String invalid() { System.err.println("INVALID"); return "INVALID"; }
    }

    @Test
    public void basicTest() {
        final BeanConverter<Foo, Bar> c1 = BeanConverter.<Foo, Bar>fromTo()
                .field(Foo::getA, Bar::setB);

        final BeanConverter<Foo, Bar> c2 = BeanConverter.fromTo(Foo.class, Bar.class)
                .field(Foo::getA, Bar::setB);

        final BeanConverter<Foo, Bar> c3 = BeanConverter.fromTo(Foo.class, Bar.class)
                .fieldGroup()
                    .field(Foo::getA, Bar::setB)
                .end();

        final BeanConverter<Foo, Bar> c4 = BeanConverter.fromTo(Foo.class, Bar.class)
                .fieldGroup(f -> f.getA().equals("I AM A TEAPOT"))
                    .field(Foo::getA, Bar::setB)
                .end();

        final Foo foo = new Foo() {
            @Override public String getA() { return "C"; }
        };

        final Foo2 foo2 = new Foo2();

        final Bar bar = BeanConverter.fromTo(Foo.class, Bar.class)
                .fieldGroup(f -> f.getA().equals("I AM A TEAPOT"))
                    .field(Foo::getA, Bar::setB)
                .end()
                .convert(foo, new Bar());

        final Bar bar2 = BeanConverter.fromTo(Foo.class, Bar.class)
                .fieldGroup(f -> f.getA().equals("I AM A TEAPOT"))
                    .field(Foo::getA, Bar::setB)
                .end()
                .convert(foo2, Bar::new);
    }

}

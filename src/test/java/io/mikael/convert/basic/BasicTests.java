package io.mikael.convert.basic;

import io.mikael.convert.Converter;
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
        final Converter<Foo, Bar> c1 = Converter.<Foo, Bar>fromTo()
                .field(Foo::getA, Bar::setB);

        final Converter<Foo, Bar> c2 = Converter.fromTo(Foo.class, Bar.class)
                .field(Foo::getA, Bar::setB);

        final Converter<Foo, Bar> c3 = Converter.fromTo(Foo.class, Bar.class)
                .fieldGroup()
                    .field(Foo::getA, Bar::setB)
                .end();

        final Converter<Foo, Bar> c4 = Converter.fromTo(Foo.class, Bar.class)
                .fieldGroup(f -> f.getA().equals("I AM A TEAPOT"))
                    .field(Foo::getA, Bar::setB)
                .end();

        final Foo foo = new Foo() {
            @Override public String getA() { return "C"; }
        };

        final Foo2 foo2 = new Foo2();

        final Bar bar = Converter.fromTo(Foo.class, Bar.class)
                .fieldGroup(f -> f.getA().equals("I AM A TEAPOT"))
                    .field(Foo::getA, Bar::setB)
                .end()
                .convert(foo, new Bar());

        final Bar bar2 = Converter.fromTo(Foo.class, Bar.class)
                .fieldGroup(f -> f.getA().equals("I AM A TEAPOT"))
                    .field(Foo::getA, Bar::setB)
                .end()
                .convert(foo2, Bar::new);
    }

}

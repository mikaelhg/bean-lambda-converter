A simple bean to bean converter prototype using Java 8 lambdas and method handles.

I'll use this as a base for writing a JSON to JSON converter DSL prototype.

```java
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

final Bar bar = Converter.fromTo(Foo.class, Bar.class)
        .fieldGroup(f -> f.getA().equals("I AM A TEAPOT"))
            .field(Foo::getA, Bar::setB)
        .end()
        .convert(new Foo(), new Bar());
```

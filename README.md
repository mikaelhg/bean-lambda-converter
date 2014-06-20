A simple bean to bean BeanConverter prototype using Java 8 lambdas and method handles.

I'll use this as a base for writing a JSON to JSON BeanConverter DSL prototype.

```java
final BeanConverter<Foo, Bar> c1 = BeanConverter.<Foo, Bar>fromTo()
        .field(Foo::getFirstName, Bar::setB);

final BeanConverter<Foo, Bar> c2 = BeanConverter.fromTo(Foo.class, Bar.class)
        .field(Foo::getFirstName, Bar::setB);

final BeanConverter<Foo, Bar> c3 = BeanConverter.fromTo(Foo.class, Bar.class)
        .fieldGroup()
            .field(Foo::getFirstName, Bar::setB)
        .end();

final BeanConverter<Foo, Bar> c4 = BeanConverter.fromTo(Foo.class, Bar.class)
        .fieldGroup(f -> f.getFirstName().equals("I AM A TEAPOT"))
            .field(Foo::getFirstName, Bar::setB)
        .end();

final Foo foo = new Foo() {
    @Override public String getFirstName() { return "C"; }
};

final Foo2 foo2 = new Foo2();

final Bar bar = BeanConverter.fromTo(Foo.class, Bar.class)
        .fieldGroup(f -> f.getFirstName().equals("I AM A TEAPOT"))
            .field(Foo::getFirstName, Bar::setB)
        .end()
        .convert(foo, new Bar());

final Bar bar2 = BeanConverter.fromTo(Foo.class, Bar.class)
        .fieldGroup(f -> f.getFirstName().equals("I AM A TEAPOT"))
            .field(Foo::getFirstName, Bar::setB)
        .end()
        .convert(foo2, Bar::new);
```

TODO:

* Exception handling in suppliers / targets.

* JSON to JSON conversion.

* DTO to Entity to DTO conversion.


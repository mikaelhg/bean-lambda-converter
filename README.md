A simple bean to bean BeanConverter prototype using Java 8 lambdas and method handles.

I'll use this as a base for writing a JSON to JSON BeanConverter DSL prototype.

```java
final BeanConverter<Foo, Bar> c1 = BeanConverter.<Foo, Bar>of()
        .field(Foo::getFirstName, Bar::setFullName);

final BeanConverter<Foo, Bar> c2 = BeanConverter.of(Foo.class, Bar.class)
        .field(Foo::getFirstName, Bar::setFullName);

final BeanConverter<Foo, Bar> c3 = BeanConverter.of(Foo.class, Bar.class)
        .fieldGroup()
            .field(Foo::getFirstName, Bar::setFullName)
        .end();

final BeanConverter<Foo, Bar> c4 = BeanConverter.fromTo(Foo.class, Bar.class)
        .fieldGroup(f -> f.getFirstName().equals("I AM A TEAPOT"))
            .field(Foo::getFirstName, Bar::setFullName)
        .end();

final Foo foo = new Foo() {
    @Override public String getFirstName() { return "C"; }
};

final Foo2 foo2 = new Foo2();

final Bar bar = BeanConverter.of(Foo.class, Bar.class)
        .fieldGroup(f -> f.getFirstName().equals("I AM A TEAPOT"))
            .field(Foo::getFirstName, Bar::setFullName)
        .end()
        .convert(foo, new Bar());

final Bar bar2 = BeanConverter.of(Foo.class, Bar.class)
        .fieldGroup(f -> f.getFirstName().equals("I AM A TEAPOT"))
            .field(Foo::getFirstName, Bar::setFullName)
        .end()
        .convert(foo2, Bar::new);
```

TODO:

* Exception handling in suppliers / targets.

* JSON to JSON conversion.

* DTO to Entity to DTO conversion.


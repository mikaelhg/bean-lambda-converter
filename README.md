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

final BeanConverter<Foo, Bar> c4 = BeanConverter.of(Foo.class, Bar.class)
        .fieldGroupIf(f -> f.getFirstName().equals("I AM A TEAPOT"))
            .field(Foo::getFirstName, Bar::setFullName)
        .end();

final Foo foo = new Foo() {
    @Override public String getFirstName() { return "C"; }
};

final Foo2 foo2 = new Foo2();

final Bar bar = BeanConverter.of(Foo.class, Bar.class)
        .fieldGroupIf(f -> f.getFirstName().equals("I AM A TEAPOT"))
            .field(Foo::getFirstName, Bar::setFullName)
        .end()
        .convert(foo, new Bar());

final Bar bar2 = BeanConverter.of(Foo.class, Bar.class)
        .fieldGroupIf(f -> f.getFirstName().equals("I AM A TEAPOT"))
            .field(Foo::getFirstName, Bar::setFullName)
        .end()
        .convert(foo2, Bar::new);
```

Tentative design for the JSON converter:

* Fluently describe both the source and target JSON structures (object, arrays, repeating elements, primitives)
and store the descriptive structures as variables.

* Create "tag" object instances to mark where a structure in the input translates into the output,
and whether the point is to aggregate or repeat an array from the source to the target sides. Stick
references to these instances in the structure description trees.

* Later: HTTP GET and combine results from multiple sources.

* Fluent building of graceful degradation scenarios, how long to wait for different sources,
and how to react if one or more sources fail to respond in a set time.

TODO:

* Exception handling in suppliers / targets.

* JSON to JSON conversion.

* DTO to Entity to DTO conversion.


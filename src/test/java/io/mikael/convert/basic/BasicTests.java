package io.mikael.convert.basic;

import io.mikael.convert.bean.BeanConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class BasicTests {

    public static interface Foo {
        public String getFirstName();
    }

    public static interface Bar {
        public void setName(final String b);
    }

    private Foo foo;
    private Bar bar;

    @Before
    public void before() {
        foo = mock(Foo.class);
        bar = mock(Bar.class);
        when(foo.getFirstName()).thenReturn("A");
    }

    @Test
    public void basicTest() {
        BeanConverter.<Foo, Bar>fromTo()
                .field(Foo::getFirstName, Bar::setName)
                .convert(foo, bar);
        verify(bar).setName("A");
    }

    @Test
    public void basicTest2() {
        BeanConverter.fromTo(Foo.class, Bar.class)
                .field(Foo::getFirstName, Bar::setName)
                .convert(foo, bar);
        verify(bar).setName("A");
    }

    @Test
    public void basicTest3() {
        BeanConverter.fromTo(Foo.class, Bar.class)
                .fieldGroup()
                    .field(Foo::getFirstName, Bar::setName)
                .end()
                .convert(foo, bar);
        verify(bar).setName("A");
    }

    @Test
    public void basicFieldGroupNotCalled() {
        BeanConverter.fromTo(Foo.class, Bar.class)
                .fieldGroup(f -> f.getFirstName().equals("I AM A TEAPOT"))
                    .field(Foo::getFirstName, Bar::setName)
                .end()
                .convert(foo, bar);
        verifyZeroInteractions(bar);
    }

    @Test
    public void basicFieldGroupCalled() {
        BeanConverter.fromTo(Foo.class, Bar.class)
                .fieldGroup(f -> f.getFirstName().equals("A"))
                    .field(Foo::getFirstName, Bar::setName)
                .end()
                .convert(foo, bar);
        verify(bar).setName("A");
    }

    @Test
    public void testSources() {
        final BeanConverter<Foo, Bar> bc = BeanConverter.fromTo(Foo.class, Bar.class);

        final Bar b1 = bc.convert(() -> mock(Foo.class), () -> mock(Bar.class));
        assertNotNull(b1);

        final Bar b2 = bc.convert(mock(Foo.class), () -> mock(Bar.class));
        assertNotNull(b2);

        final Bar b3 = bc.convert(() -> mock(Foo.class), mock(Bar.class));
        assertNotNull(b3);

        verifyZeroInteractions(foo, bar);
    }

}

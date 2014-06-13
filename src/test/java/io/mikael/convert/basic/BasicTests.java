package io.mikael.convert.basic;

import io.mikael.convert.bean.BeanConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class BasicTests {

    public static interface TestSource {
        public String getInput();
    }

    public static interface TestTarget {
        public void setOutput(final String output);
    }

    private TestSource testSource;
    private TestTarget testTarget;

    @Before
    public void before() {
        testSource = mock(TestSource.class);
        testTarget = mock(TestTarget.class);
        when(testSource.getInput()).thenReturn("A");
    }

    @Test
    public void fieldConversionWithGenerics() {
        BeanConverter.<TestSource, TestTarget>fromTo()
                .field(TestSource::getInput, TestTarget::setOutput)
                .convert(testSource, testTarget);
        verify(testTarget).setOutput("A");
    }

    @Test
    public void fieldConversionWithClassParameters() {
        BeanConverter.fromTo(TestSource.class, TestTarget.class)
                .field(TestSource::getInput, TestTarget::setOutput)
                .convert(testSource, testTarget);
        verify(testTarget).setOutput("A");
    }

    @Test
    public void fieldGroupWithoutPrecondition() {
        BeanConverter.fromTo(TestSource.class, TestTarget.class)
                .fieldGroup()
                    .field(TestSource::getInput, TestTarget::setOutput)
                .end()
                .convert(testSource, testTarget);
        verify(testTarget).setOutput("A");
    }

    @Test
    public void basicFieldGroupNotCalled() {
        BeanConverter.fromTo(TestSource.class, TestTarget.class)
                .fieldGroup(f -> f.getInput().equals("I AM A TEAPOT"))
                    .field(TestSource::getInput, TestTarget::setOutput)
                .end()
                .convert(testSource, testTarget);
        verifyZeroInteractions(testTarget);
    }

    @Test
    public void basicFieldGroupCalled() {
        BeanConverter.fromTo(TestSource.class, TestTarget.class)
                .fieldGroup(f -> f.getInput().equals("A"))
                    .field(TestSource::getInput, TestTarget::setOutput)
                .end()
                .convert(testSource, testTarget);
        verify(testTarget).setOutput("A");
    }

    @Test
    public void converterSourceSignatures() {
        final BeanConverter<TestSource, TestTarget> bc = BeanConverter.fromTo(TestSource.class, TestTarget.class);

        final TestTarget b1 = bc.convert(() -> mock(TestSource.class), () -> mock(TestTarget.class));
        assertNotNull(b1);

        final TestTarget b2 = bc.convert(mock(TestSource.class), () -> mock(TestTarget.class));
        assertNotNull(b2);

        final TestTarget b3 = bc.convert(() -> mock(TestSource.class), mock(TestTarget.class));
        assertNotNull(b3);

        verifyZeroInteractions(testSource, testTarget);
    }

}

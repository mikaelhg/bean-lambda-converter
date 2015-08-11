package io.mikael.convert.basic;

import io.mikael.convert.bean.BeanConverter;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;

public class BasicTests {

    public interface TestSource {
        String getInput();
    }

    public interface TestTarget {
        void setOutput(final String output);
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
        BeanConverter.<TestSource, TestTarget>of()
                .field(TestSource::getInput, TestTarget::setOutput)
                .convert(testSource, testTarget);
        verify(testTarget).setOutput("A");
    }

    @Test
    public void fieldConversionWithClassParameters() {
        BeanConverter.of(TestSource.class, TestTarget.class)
                .field(TestSource::getInput, TestTarget::setOutput)
                .convert(testSource, testTarget);
        verify(testTarget).setOutput("A");
    }

    @Test
    public void fieldGroupWithoutPrecondition() {
        BeanConverter.of(TestSource.class, TestTarget.class)
                .fieldGroup()
                    .field(TestSource::getInput, TestTarget::setOutput)
                .end()
                .convert(testSource, testTarget);
        verify(testTarget).setOutput("A");
    }

    @Test
    public void basicFieldGroupNotCalled() {
        BeanConverter.of(TestSource.class, TestTarget.class)
                .fieldGroup(s -> s.getInput().equals("I AM A TEAPOT"))
                    .field(TestSource::getInput, TestTarget::setOutput)
                .end()
                .convert(testSource, testTarget);
        verifyZeroInteractions(testTarget);
    }

    @Test
    public void basicFieldGroupCalled() {
        BeanConverter.of(TestSource.class, TestTarget.class)
                .fieldGroup(s -> s.getInput().equals("A"))
                    .field(TestSource::getInput, TestTarget::setOutput)
                .end()
                .convert(testSource, testTarget);
        verify(testTarget).setOutput("A");
    }

    @Test
    public void converterSourceSignatures() {
        final BeanConverter<TestSource, TestTarget> bc = BeanConverter.of();
        assertNotNull(bc.convert(() -> mock(TestSource.class), () -> mock(TestTarget.class)));
        assertNotNull(bc.convert(mock(TestSource.class), () -> mock(TestTarget.class)));
        assertNotNull(bc.convert(() -> mock(TestSource.class), mock(TestTarget.class)));
    }

}

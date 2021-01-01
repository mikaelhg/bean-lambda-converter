package io.mikael.convert.basic;

import io.mikael.convert.bean.BeanConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class BasicTests {

    public interface TestSource {
        String getInput();
    }

    public interface TestTarget {
        void setOutput(final String output);
    }

    public interface TestInner1 {
        String getCat();
        String getDog();
    }

    public interface TestInner2 {
        void setLion(final String lion);
        void setShibaInu(final String shibaInu);
    }

    public interface TestSourceWithInner {
        String getInput();
        TestInner1 getInner1();
    }

    public interface TestTargetWithInner {
        void setOutput(final String output);
        TestInner2 getInner2();
    }

    private TestSource testSource;

    private TestTarget testTarget;

    private TestSourceWithInner testSourceWithInner;

    private TestTargetWithInner testTargetWithInner;

    @BeforeEach
    public void before() {
        testSource = mock(TestSource.class);
        testTarget = mock(TestTarget.class);
        testSourceWithInner = mock(TestSourceWithInner.class);
        testTargetWithInner = mock(TestTargetWithInner.class);
        when(testSource.getInput()).thenReturn("A");
    }

    /*
    @Test
    public void innerFields() {
        BeanConverter.of(TestSourceWithInner.class, TestTargetWithInner.class)
                .inner(TestSourceWithInner::getInner1, TestTargetWithInner::getInner2)
                .end()
                .convert(testSourceWithInner, testTargetWithInner);
    }
    */

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
                .fieldGroupIf(s -> s.getInput().equals("I AM A TEAPOT"))
                    .field(TestSource::getInput, TestTarget::setOutput)
                .end()
                .convert(testSource, testTarget);
        verifyZeroInteractions(testTarget);
    }

    @Test
    public void basicFieldGroupCalled() {
        BeanConverter.of(TestSource.class, TestTarget.class)
                .fieldGroupIf(s -> s.getInput().equals("A"))
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

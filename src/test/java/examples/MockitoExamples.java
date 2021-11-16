package examples;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MockitoExamples {

	@Mock
	SomeService someService;
	@Captor
	ArgumentCaptor<String> greetCaptor;

	@Test
	public void testMock() {
		List mockedList = mock(List.class);

		mockedList.add("one");
		assertEquals(0, mockedList.size());
		mockedList.clear();
		verify(mockedList).add("one");
		verify(mockedList).clear();
	}

	@Test
	public void testSpy() {
		List spyList = spy(new ArrayList<>());

		spyList.add("one");
		spyList.add("two");

		verify(spyList).add("one");
		verify(spyList).add("two");

		assertEquals(2, spyList.size());

		doReturn(100).when(spyList).size();
		assertEquals(100, spyList.size());
	}

	@Test
	public void testMocking() {
		when(someService.greet(anyString())).thenReturn("Priviet!");
		given(someService.greet("Janis")).willReturn("no siema");

		assertThat(someService.greet("cokolwiek")).isEqualTo("Priviet!");
		assertThat(someService.greet("Janis")).isEqualTo("no siema");
	}

	@Test
	public void testCaptors() {
		List mockList = mock(List.class);
		ArgumentCaptor<String> arg = ArgumentCaptor.forClass(String.class);

		mockList.add("one");
		verify(mockList).add(arg.capture());

		assertEquals("one", arg.getValue());
	}

	@Test
	public void testCaptors2() {
		someService.greet("Janis");
		verify(someService).greet(greetCaptor.capture());
		assertThat("Janis").isEqualTo(greetCaptor.getValue());

		verify(someService, times(1)).greet(anyString());
		verify(someService, atLeast(1)).greet("Janis");
		verify(someService, never()).getAge(anyInt());
	}


}

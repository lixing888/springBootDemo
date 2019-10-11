package com.springboot.demo;

import com.springboot.demo.vo.User;
import com.springboot.demo.vo.UserRequest;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import org.mockito.MockitoAnnotations;

import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 *
 */
public class MockitoTest {

    @Before
    public void init() {
        MockitoAnnotations.initMocks(MockitoTest.class);
    }

    public static void verifyTimeout() {
        User u = mock(User.class);
        //validation passes when method is called within given time
        verify(u, timeout(2000)).getUsername();
        u.getUsername();
    }

    @Test
    public void testThenReturn() {
        //create mock object
        User u = mock(User.class);
        //when call method getUsername then return "hello" instead, the method is not called, that is so-called mocking
        when(u.getUsername()).thenReturn("user1");
        u.getUsername();
        when(u.getAge()).thenReturn(22);
        u.getAge();
        System.out.println(u.getUsername());
        System.out.println(u.getAge());
    }

    @Test
    public void testThenReturn2() {
        //create mock object
        User u = mock(User.class);
        //first call will return "user1", second call will return "user2"
        when(u.getUsername()).thenReturn("user1").thenReturn("user2");
        System.out.println(u.getUsername());
        System.out.println(u.getUsername());
        System.out.println(u.getUsername());
        //create mock object
        List<String> mockList = mock(List.class);
        //for input parameter 0 method get will return "one"
        when(mockList.get(0)).thenReturn("one");
        //for input parameter 1 method get will return "two"
        when(mockList.get(1)).thenReturn("two");
        System.out.println(mockList.get(0));
        System.out.println(mockList.get(1));
    }

    @Test
    public void testThenReturn4() {
        //create mock object
        User u = mock(User.class);
        //first call will return "user1", second call will return "user2"
        when(u.getUsername()).thenReturn("user1").thenReturn("user2");
        System.out.println(u.getUsername());
        System.out.println(u.getUsername());
    }

    @Test
    public void testAssertEquals() {
        UserRequest u1 = new UserRequest();
        u1.setBirthday("1992-08-02");
        assertEquals(22, u1.getAge());
        User u2 = mock(User.class);
        when(u2.getAge()).thenReturn(22);
        assertEquals(22, u2.getAge());
    }

    @Test
    public void verifyMethodCalledTimes() {
        //create mock object
        List<String> mockList = mock(List.class);
        mockList.get(0);
        mockList.get(2);
        mockList.get(2);
        mockList.get(2);
        //verify how many times a method is called for special arguments
        verify(mockList, atLeastOnce()).get(0);
        verify(mockList, never()).get(1);
        verify(mockList, times(3)).get(2);
        verify(mockList, atLeast(2)).get(2);
    }

    @Test
    public void verifyInOrder() {
        //Single mock whose methods must be invoked in a particular order
        List<String> singleMock = mock(List.class);
        //using a single mock
        singleMock.add("was added firstly");
        singleMock.add("was added secondly");
        //create an inOrder verifier for a single mock
        InOrder inOrder = inOrder(singleMock);
        //following will make sure that add is first called with "was added first, then with "was added second"
        inOrder.verify(singleMock).add("was added firstly");
        inOrder.verify(singleMock).add("was added secondly");
        //Multiple mocks that must be used in a particular order
        List<String> firstMock = mock(List.class);
        List<String> secondMock = mock(List.class);
        //using mocks
        firstMock.add("was called firstly");
        secondMock.add("was called secondly");
        //create inOrder object passing any mocks that need to be verified in order
        InOrder inOrder2 = inOrder(firstMock, secondMock);
        //following will make sure that firstMock was called before secondMock
        inOrder2.verify(firstMock).add("was called firstly");
        inOrder2.verify(secondMock).add("was called secondly");
    }

    @Test
    public void testThrowException() {
        User u = mock(User.class);
        doThrow(new RuntimeException()).when(u).setUsername("user1");
        //calling setUserName() throws RuntimeException:
        u.setUsername("user1");
    }

    @Test
    public void verifyRedundentCall() {
        User u = mock(User.class);
        u.setUsername("user1");
        //u.setUsername("user2");
        //verify(mock, times(1));
        //verify(u).setUsername("user1");
        //following verification will fail
        verifyNoMoreInteractions(u);
    }

    @Test
    public void checkOnly() {
        User u = mock(User.class);
        u.setUsername("user1");
        //check if a method is the only one be called
        verify(u, only()).setUsername("user1");
        //above is a shorthand for following 2 lines of code:
        verify(u).setUsername("user1");
        verifyNoMoreInteractions(u);
    }

    @Test
    public void testIterator() {
        Iterator<String> i = mock(Iterator.class);
        when(i.next()).thenReturn("Hello").thenReturn("World");
        String result = i.next() + " " + i.next();
        verify(i, times(2)).next();
        assertEquals("Hello World", result);
    }

}

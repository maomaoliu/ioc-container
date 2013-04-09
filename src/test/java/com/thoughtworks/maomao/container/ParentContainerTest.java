package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.scope.Person;
import com.thoughtworks.maomao.stub.scope.child.Child;
import com.thoughtworks.maomao.stub.scope.parent.Parent;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertNotNull;

public class ParentContainerTest {

    WheelContainer parentContainer = new WheelContainer("com.thoughtworks.maomao.stub.scope.parent");
    WheelContainer childContainer = new WheelContainer("com.thoughtworks.maomao.stub.scope.child");

    @Before
    public void setUp(){
        assertNotNull(parentContainer.getWheel(Parent.class));
        assertNotNull(childContainer.getWheel(Child.class));
        try {
            parentContainer.getWheel(Child.class);
            fail();
        } catch (Exception e) {}
        try {
            childContainer.getWheel(Parent.class);
            fail();
        } catch (Exception e) {}

        childContainer.setParent(parentContainer);
    }

    @Test
    public void child_should_get_bean_from_parent() {
        assertNotNull(childContainer.getWheel(Parent.class));
    }

    @Test(expected = InvalidWheelException.class)
    public void parent_should_not_get_bean_from_child() {
        parentContainer.getWheel(Child.class);
    }

    @Test
    public void should_get_child_instance_when_parent_also_has() {
        Person person = childContainer.getWheel(Person.class);
        assertTrue(person.age() < 10);
    }
}

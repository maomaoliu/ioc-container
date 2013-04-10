package com.thoughtworks.maomao.container;

import com.thoughtworks.maomao.exception.InvalidWheelException;
import com.thoughtworks.maomao.stub.scope.Person;
import com.thoughtworks.maomao.stub.scope.child.Child;
import com.thoughtworks.maomao.stub.scope.parent.Parent;
import org.junit.Before;
import org.junit.Test;

import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.fail;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class ParentContainerTest {

    private WheelContainer parentContainer;
    private WheelContainer childContainer;

    @Before
    public void setup(){
        parentContainer = new WheelContainer("com.thoughtworks.maomao.stub.scope.parent");
        childContainer = new WheelContainer("com.thoughtworks.maomao.stub.scope.child", parentContainer);
    }

    @Test(expected = InvalidWheelException.class)
    public void should_not_find_Parent_class_in_child_container_if_not_set_parent_container() {
        childContainer = new WheelContainer("com.thoughtworks.maomao.stub.scope.child");
        childContainer.getWheel(Parent.class);
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

    @Test
    public void should_get_parent_config_bean() {
        Parent parent = childContainer.getWheel(Parent.class);
        assertEquals("maomao", parent.getName());
    }
}

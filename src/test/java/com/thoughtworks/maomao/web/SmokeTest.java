package com.thoughtworks.maomao.web;

import com.thoughtworks.maomao.web.config.TestServletContainerInitializer;
import com.thoughtworks.maomao.web.model.Book;
import com.thoughtworks.maomao.web.service.BookServiceImpl;
import org.eclipse.jetty.client.Address;
import org.eclipse.jetty.client.ContentExchange;
import org.junit.Test;

import javax.servlet.ServletContextListener;
import java.util.List;

import static org.junit.Assert.*;

public class SmokeTest extends AbstractWebTest {
    @Override
    protected ServletContextListener getServletContainerInitializer() {
        return new TestServletContainerInitializer();
    }

    @Test
    public void should_get_books_info() throws Exception {
        Address localhost = new Address("localhost", 8080);

        ContentExchange contentExchange = new ContentExchange(true);
        contentExchange.setAddress(localhost);
        contentExchange.setRequestURI("/books");
        client.send(contentExchange);

        contentExchange.waitForDone();
        assertEquals(200, contentExchange.getResponseStatus());
        assertNotNull(contentExchange.getResponseContent());
        List<Book> allBooks = new BookServiceImpl().getAllBooks();
        for (Book book : allBooks) {
            assertTrue(contentExchange.getResponseContent().contains(book.getName()));
            assertTrue(contentExchange.getResponseContent().contains(book.getAuthor()));
        }
    }
}

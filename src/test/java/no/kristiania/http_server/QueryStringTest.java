package no.kristiania.http_server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class QueryStringTest {
    @Test
    void shouldRetrieveQueryParemeter(){
        QueryString queryString = new QueryString("status-200");
        assertEquals("200", queryString.getParameter("status"));
    }
}

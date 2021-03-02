package com.bigbook.app;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.io.IOException;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@WebMvcTest(BookController.class)
public class BookControllerTests {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private BookRepository bookRepository;
    @MockBean
    private BookService bookService;

    @Test
    public void createBookPost_testsPass() throws Exception {
        String requestBody = getResourceAsString("/bookControllerTest/createBookRequest.json");
        String responseBody = getResourceAsString("/bookControllerTest/createBookResponse.json");

        mockMvc.perform(
                post("/books/create")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(responseBody));
    }

    @Test
    public void getAllBooks_testsPass() throws Exception {
        List<BookModel> books = List.of(new BookModel("title1", "0001", "author1"),
                new BookModel("title2", "0002", "author2"));
        when(bookRepository.getBooks()).thenReturn(books);

        mockMvc.perform(
                get("/books/all")
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(books)));
    }

    @Test
    public void getFilteredBooks_testsPass() throws Exception {
        String query = "title";
        List<BookModel> books = List.of(new BookModel("title1", "0001", "author1"),
                new BookModel("title2", "0002", "author2"));
        when(bookService.findBooks(query)).thenReturn(books);

        mockMvc.perform(
                get("/books/search")
                .param("query", query)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(books)));
    }

    @Test
    public void getFilteredBooks_testsFail() throws Exception {
        mockMvc.perform(
                get("/books/search")
        )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private String getResourceAsString(String name) throws IOException {
        return new String(BookControllerTests.class
                .getResourceAsStream(name)
                .readAllBytes());
    }
}

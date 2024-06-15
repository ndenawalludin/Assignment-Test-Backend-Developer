/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.masterdata.movie.masterdata;

/**
 *
 * @author MyBook Hype AMD
 */
import com.fasterxml.jackson.databind.ObjectMapper;
import com.masterdata.movie.masterdata.DTO.MovieDTO;
import com.masterdata.movie.masterdata.DTO.ResponseDTO;
import com.masterdata.movie.masterdata.controller.MovieRESTController;
import com.masterdata.movie.masterdata.domain.Movie;
import com.masterdata.movie.masterdata.service.MovieRESTService;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

public class MovieRESTControllerTest {

    private MockMvc mockMvc;

    @Mock
    private MovieRESTService movieRESTService;

    @InjectMocks
    private MovieRESTController movieRESTController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = standaloneSetup(movieRESTController).build();
    }

    @Test
    public void testFindAll() throws Exception {
        when(movieRESTService.findAll()).thenReturn(ResponseEntity.ok(Arrays.asList(new Movie(), new Movie())));

        mockMvc.perform(get("/Movies")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testFindOneMovieByID() throws Exception {
        int movieId = 1;
        when(movieRESTService.findOneMovieByID(movieId)).thenReturn(ResponseEntity.ok(new Movie()));

        mockMvc.perform(get("/Movies/{Id}", movieId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testAddNewMovie() throws Exception {
        MovieDTO movieDTO = new MovieDTO(1,"test","test", 0f, "test", null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseDTO responseDTO = new ResponseDTO();
        
        when(movieRESTService.addOrUpdateMovie(any(MovieDTO.class), eq(false), isNull())).thenReturn(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDTO));

        String movieDTOJson = objectMapper.writeValueAsString(movieDTO);
        mockMvc.perform(post("/Movies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(movieDTOJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testUpdateMovie() throws Exception {
        int movieId = 1;
        MovieDTO movieDTO = new MovieDTO(1,"test","test", 0f, "test", null, null);
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseDTO responseDTO = new ResponseDTO();
        
        when(movieRESTService.addOrUpdateMovie(any(MovieDTO.class), eq(true), eq(movieId))).thenReturn(ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(responseDTO));

        String responseDTOJson = objectMapper.writeValueAsString(responseDTO);
        
        mockMvc.perform(patch("/Movies/{Id}", movieId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(responseDTOJson))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

    @Test
    public void testDeleteOneMovieByID() throws Exception {
        int movieId = 1;
        ResponseDTO responseDTO = new ResponseDTO();
        when(movieRESTService.deleteOneMovieByID(movieId)).thenReturn(ResponseEntity.ok(responseDTO));

        mockMvc.perform(delete("/Movies/{Id}", movieId)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }
}

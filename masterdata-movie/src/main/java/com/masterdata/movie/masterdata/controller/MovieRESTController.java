/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.masterdata.movie.masterdata.controller;

import com.masterdata.movie.masterdata.DTO.MovieDTO;
import com.masterdata.movie.masterdata.DTO.ResponseDTO;
import com.masterdata.movie.masterdata.domain.Movie;
import com.masterdata.movie.masterdata.service.MovieRESTService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author nden
 */
@RestController
public class MovieRESTController {
    @Autowired
    MovieRESTService movieRESTService;
    
    
    @RequestMapping(value = "/Movies",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Movie>> findAll() {
        return movieRESTService.findAll();
    }
    
    
    @RequestMapping(value="/Movies/{Id}",
	            method = RequestMethod.GET,
	            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Movie> findOneMovieByID(
            @PathVariable("Id") Integer Id) {
        return movieRESTService.findOneMovieByID(Id);
    }
    
    
    @RequestMapping(value="/Movies",
	            method = RequestMethod.POST,
	            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> addNewMovie(
            @RequestBody MovieDTO movieDTO) {
        return movieRESTService.addOrUpdateMovie(movieDTO, false, null);
    }
    
    
    @RequestMapping(value="/Movies/{Id}",
	            method = RequestMethod.PATCH,
	            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> updateMovie(
            @RequestBody MovieDTO movieDTO,
            @PathVariable("Id") Integer Id) {
        return movieRESTService.addOrUpdateMovie(movieDTO, true, Id);
    }
    
    
    @RequestMapping(value="/Movies/{Id}",
	            method = RequestMethod.DELETE,
	            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseDTO> deleteOneMovieByID(
            @PathVariable("Id") Integer Id) {
        return movieRESTService.deleteOneMovieByID(Id);
    }
}

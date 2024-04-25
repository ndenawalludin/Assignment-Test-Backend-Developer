/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.masterdata.movie.masterdata.service;

import com.masterdata.movie.masterdata.DTO.MovieDTO;
import com.masterdata.movie.masterdata.DTO.ResponseDTO;
import com.masterdata.movie.masterdata.domain.Movie;
import com.masterdata.movie.masterdata.repository.MovieRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.http.ResponseEntity;

/**
 *
 * @author nden
 */
@Service
public class MovieRESTService {
    
    @Autowired
    MovieRepository movieRepository;
    
    public ResponseEntity<List<Movie>> findAll() {
        
        try {
            List<Movie> movies = new ArrayList<>();
            movies = movieRepository.findAll();
            
            if (movies.size() > 0) {
                return ResponseEntity.status(HttpStatus.OK).body(movies);
            }
            
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
    
    public ResponseEntity<Movie> findOneMovieByID(Integer Id) {
        
        try {
            Movie movie = movieRepository.findOneById(Id);
            if (movie != null) {
                return ResponseEntity.status(HttpStatus.OK).body(movie);
            }
            
            return ResponseEntity.status(HttpStatus.OK).body(null);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
    
    /**
     * This function for add or update Movie
     * @param Id
     * @return 
     */
    public ResponseEntity<ResponseDTO> addOrUpdateMovie(MovieDTO movieDTO, Boolean isUpdate, Integer id) {
        Movie movie = new Movie();
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            if (isUpdate) {
                movie = movieRepository.findOneById(id);
                if (movie == null) {
                    responseDTO.setCode("0");
                    responseDTO.setMessage("Failed to update because ID not found");
                    
                    return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
                }
                
                movie.setId(movieDTO.getId());
                movie.setTitle(movieDTO.getTitle());
                movie.setDescription(movieDTO.getDescription());
                movie.setRating(movieDTO.getRating());
                movie.setImage(movieDTO.getImage());
                movie.setCreated_at(movieDTO.getCreated_at());
                movie.setUpdated_at(movieDTO.getUpdated_at());
                
                movieRepository.save(movie);
                responseDTO.setCode("1");
                responseDTO.setMessage("Updated successfully");
                
                return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            } else {
                if (movieDTO.getId() != null) {
                    movie = movieRepository.findOneById(movieDTO.getId());
                    
                    if (movie != null ) {
                        responseDTO.setCode("0");
                        responseDTO.setMessage("Failed to save data because data with that ID already exists");
                        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
                    }
                }
                
                movie.setId(movieDTO.getId());
                movie.setTitle(movieDTO.getTitle());
                movie.setDescription(movieDTO.getDescription());
                movie.setRating(movieDTO.getRating());
                movie.setImage(movieDTO.getImage());
                movie.setCreated_at(movieDTO.getCreated_at());
                movie.setUpdated_at(movieDTO.getUpdated_at());
                
                movieRepository.save(movie);
                responseDTO.setCode("1");
                responseDTO.setMessage("saved successfully");
                
                return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
    
    public ResponseEntity<ResponseDTO> deleteOneMovieByID(Integer Id) {
        ResponseDTO responseDTO = new ResponseDTO();
        
        try {
            Movie movie = movieRepository.findOneById(Id);
            if (movie != null) {
                movieRepository.deleteById(Id);
                
                responseDTO.setCode("1");
                responseDTO.setMessage("data has been successfully deleted");
                
                return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
            }
            
            responseDTO.setCode("0");
            responseDTO.setMessage("data not found");

            return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
    
    
}

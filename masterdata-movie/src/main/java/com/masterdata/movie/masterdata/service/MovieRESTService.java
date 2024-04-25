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
            
            return ResponseEntity.status(HttpStatus.OK).body(new ArrayList<>());
        } catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ArrayList<>());
        }
        
    }
    
    public ResponseEntity<Movie> findOneMovieByID(Integer Id) {
        
        try {
            Movie movie = movieRepository.findOneById(Id);
            if (movie != null) {
                return ResponseEntity.status(HttpStatus.OK).body(movie);
            }
            return ResponseEntity.status(HttpStatus.OK).body(new Movie());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new Movie());
        }
        
    }
    
    /**
     * This function for add or update Movie
     * @param Id
     * @return 
     */
    public ResponseEntity<ResponseDTO> addOrUpdateMovie(MovieDTO movieDTO, Boolean isUpdate, Integer id) {
        Movie movie = new Movie();
        
        try {
            if (isUpdate) {
                movie = movieRepository.findOneById(id);
                if (movie == null) {
                    return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "Failed to update because ID not found"));
                }
                
                movie.setId(movieDTO.getId());
                movie.setTitle(movieDTO.getTitle());
                movie.setDescription(movieDTO.getDescription());
                movie.setRating(movieDTO.getRating());
                movie.setImage(movieDTO.getImage());
                movie.setCreated_at(movieDTO.getCreated_at());
                movie.setUpdated_at(movieDTO.getUpdated_at());
                
                movieRepository.save(movie);
                
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("1", "Updated successfully"));
            } else {
                if (movieDTO.getId() != null) {
                    movie = movieRepository.findOneById(movieDTO.getId());
                    
                    if (movie != null ) {
                        return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "Failed to save data because data with that ID already exists"));
                    }
                    
                    movie = new Movie();
                }
                
                movie.setId(movieDTO.getId());
                movie.setTitle(movieDTO.getTitle());
                movie.setDescription(movieDTO.getDescription());
                movie.setRating(movieDTO.getRating());
                movie.setImage(movieDTO.getImage());
                movie.setCreated_at(movieDTO.getCreated_at());
                movie.setUpdated_at(movieDTO.getUpdated_at());
                
                movieRepository.save(movie);
                
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("1", "saved successfully"));
            }
            
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseDTO());
        }
        
    }
    
    public ResponseEntity<ResponseDTO> deleteOneMovieByID(Integer Id) {
        
        try {
            Movie movie = movieRepository.findOneById(Id);
            if (movie != null) {
                movieRepository.deleteById(Id);
                
                return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("1", "data has been successfully deleted"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseDTO("0", "data not found"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
        
    }
    
    
}

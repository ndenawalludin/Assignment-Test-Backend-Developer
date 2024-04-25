/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.masterdata.movie.masterdata.repository;

import com.masterdata.movie.masterdata.domain.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 *
 * @author nden
 */
@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> findAll();
    Movie findOneById(Integer id);
}

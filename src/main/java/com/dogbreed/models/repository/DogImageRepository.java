package com.dogbreed.models.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.dogbreed.models.entities.DogImageEntity;

@Repository
public interface DogImageRepository extends CrudRepository<DogImageEntity, Long>  {
    
}

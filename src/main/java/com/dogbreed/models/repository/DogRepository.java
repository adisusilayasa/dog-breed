package com.dogbreed.models.repository;

import com.dogbreed.models.entities.DogEntity;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DogRepository extends CrudRepository<DogEntity, Long> {

    DogEntity findByBreed(String breed);

    void deleteByBreed(String breed);

    void flush();

    @Modifying
    @Query("UPDATE DogEntity d SET d.breed = :newBreed WHERE d.breed = :oldBreed")
    void updateBreed(String oldBreed, String newBreed);
}

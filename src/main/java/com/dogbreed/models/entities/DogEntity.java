package com.dogbreed.models.entities;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;

@Entity
@Table(name = "dog_entity")
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class DogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "breed_name", unique = true)
    @JsonProperty("breed")  // Include breed property in the JSON response
    private String breed;
    
    @OneToMany(mappedBy = "dogEntity", cascade = CascadeType.ALL)
    @JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
    private List<DogImageEntity> dogImages;
    
    /**
     * @return Long return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return String return the breed
     */
    public String getBreed() {
        return breed;
    }

    /**
     * @param breed the breed to set
     */
    public void setBreed(String breed) {
        this.breed = breed;
    }

    /**
     * @return List<DogImageEntity> return the dogImages
     */
    public List<DogImageEntity> getDogImages() {
        return dogImages;
    }

    /**
     * @param dogImages the dogImages to set
     */
    public void setDogImages(List<DogImageEntity> dogImages) {
        this.dogImages = dogImages;
    }

}

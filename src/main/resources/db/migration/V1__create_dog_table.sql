CREATE TABLE dog_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    breed_name VARCHAR(255)
);

CREATE TABLE dog_sub_breed_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    sub_breed VARCHAR(255),
    dog_id BIGINT NOT NULL,
    FOREIGN KEY (dog_id) REFERENCES dog_entity (id)
);

CREATE TABLE dog_image_entity (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    image_url VARCHAR(255),
    dog_id BIGINT NOT NULL,
    FOREIGN KEY (dog_id) REFERENCES dog_entity (id)
);

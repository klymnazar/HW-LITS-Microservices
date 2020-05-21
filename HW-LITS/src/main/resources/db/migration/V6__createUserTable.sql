CREATE TABLE user(
    id INT(11) NOT NULL AUTO_INCREMENT,
    username VARCHAR(35),
    password VARCHAR(255),
    PRIMARY KEY (id),
    UNIQUE (username)
);
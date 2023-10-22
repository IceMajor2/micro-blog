CREATE TABLE IF NOT EXISTS author (
    id  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    username    VARCHAR(32) NOT NULL,-- UNIQUE
    email   VARCHAR(96) NOT NULL
);

CREATE TABLE IF NOT EXISTS post (
    id  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    title   VARCHAR(255) NOT NULL,
    body    TEXT NOT NULL,
    published_on    TIMESTAMP NOT NULL,
    updated_on  TIMESTAMP,
    author  BIGINT,
    FOREIGN KEY (author) REFERENCES author(id)
);

CREATE TABLE IF NOT EXISTS category (
    id  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name    VARCHAR(32) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS post_category (
    id  BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    post_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    UNIQUE (post_id, category_id),
    FOREIGN KEY (post_id) REFERENCES post(id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);

CREATE TABLE IF NOT EXISTS comment (
    post    BIGINT NOT NULL,
    username    VARCHAR(32) NOT NULL,-- UNIQUE
    body    TEXT NOT NULL,
    published_on    TIMESTAMP NOT NULL,
    updated_on  TIMESTAMP,
    FOREIGN KEY (post) REFERENCES post(id)
);
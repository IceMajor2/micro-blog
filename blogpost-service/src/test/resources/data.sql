INSERT INTO category (ID, NAME)
    VALUES (1, 'Spring'),
           (2, 'Java'),
           (3, 'Testing'),
           (4, 'Software Engineering'),
           (5, 'Programming languages'),
           (6, 'Learning');

INSERT INTO author (ID, USERNAME, EMAIL)
    VALUES (1, 'Andrew Rockman', 'rockman@mail.com'),
           (2, 'Eddie Watkins', 'accountED@hotmail.com');

INSERT INTO post (ID, TITLE, PUBLISHED_ON, AUTHOR, BODY)
    VALUES (1, 'Java and C# comparison', DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL 1 DAY),
            2, 'Some people would argue that Java and C# are very alike...');
INSERT INTO post (ID, TITLE, PUBLISHED_ON, UPDATED_ON, AUTHOR, BODY)
    VALUES (2, 'Testing and why you should do it', DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL 12 HOUR),
            DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL 6 HOUR), 1, 'Nowadays, testing is a sine qua non condition of building software');
INSERT INTO post (ID, TITLE, PUBLISHED_ON, AUTHOR, BODY)
    VALUES (3, 'Process is more important than goals', DATE_SUB(CURRENT_TIMESTAMP(), INTERVAL 8 HOUR),
            1, 'This entry will be a little bit more different than the others...');

INSERT INTO post_category (ID, POST_ID, CATEGORY_ID)
    VALUES (1, 1, 5),
           (2, 1, 2),
           (3, 2, 3),
           (4, 2, 4),
           (5, 3, 6);
-- JPA automatically loads a file called data.sql if it exists and runs it against the DB
USE DATABASE project_6;
INSERT INTO user (email, password, active) VALUES ('some@email.com', '1234', true);



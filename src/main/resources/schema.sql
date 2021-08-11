DROP TABLE IF EXISTS room;
CREATE TABLE room(
                     room_seq INT PRIMARY KEY,
                     host_summoner_name VARCHAR(30),
                     match_status BOOLEAN,
                     room_number VARCHAR(100)
);
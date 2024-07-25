CREATE DATABASE if not exists `frodo_logging_db`;
use frodo_logging_db;

CREATE TABLE IF NOT EXISTS missions(
   missionId varchar(50) NOT NULL PRIMARY KEY,
   timestamp TIMESTAMP DEFAULT current_timestamp,
   destination varchar(50),
   assignedRobot varchar(50),
   isCompleted boolean
);

CREATE TABLE IF NOT EXISTS rooms(
    roomId varchar(50) primary key,
    description varchar(50),
    floor int
);

INSERT INTO rooms (roomId, description, floor)
VALUES (94, 'Labor', 1),
    (189, 'Cafe', 2),
    (55, 'Patient', 3),
    (23, 'Patient', 0),
    (12, 'Patient', 1),
    (45, 'Patient', 2),
    (67, 'Pause', 3),
    (178, 'Pause', 0),
    (101, 'Operationssaal', 2),
    (202, 'Intensivstation', 3),
    (305, 'Notaufnahme', 0),
    (140, 'Radiologie', 1),
    (210, 'Mitarbeiterzimmer', 2),
    (315, 'Labor', 3),
    (123, 'Apotheke', 1),
    (56, 'Chirurgie', 4),
    (98, 'Gipsraum', 0),
    (47, 'Kantine', 1),
    (211, 'Wartezimmer', 2),
    (301, 'MRT', 3),
    (420, 'Röntgen', 4),
    (35, 'Büro', 0),
    (59, 'Sprechzimmer', 1),
    (110, 'Physiotherapie', 1),
    (225, 'Ultraschall', 2),
    (333, 'Endoskopie', 3),
    (421, 'Gynäkologie', 4),
    (58, 'Kardiologie', 0),
    (275, 'HNO', 2),
    (310, 'Neurologie', 3),
    (48, 'Onkologie', 0),
    (95, 'Urologie', 1),
    (365, 'Dermatologie', 3),
    (500, 'Kinderstation', 1),
    (501, 'Augenheilkunde', 2),
    (502, 'HNO', 3),
    (503, 'Zahnarzt', 0),
    (504, 'Kardiologie', 1),
    (505, 'Orthopädie', 2),
    (506, 'Pädiatrie', 3),
    (507, 'Dermatologie', 0),
    (508, 'Psychiatrie', 1),
    (509, 'Gastroenterologie', 2),
    (510, 'Nephrologie', 3),
    (666, 'FinalDestination', 3);
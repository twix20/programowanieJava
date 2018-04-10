-- Created by Vertabelo (http://vertabelo.com)
-- Last modification date: 2018-04-10 07:09:40.427

-- tables
-- Table: EXAMINED
CREATE TABLE EXAMINED (
    GENOTYPE varchar(6) NOT NULL,
    CLASS varchar(2) NOT NULL
);

-- Table: FLAGELLA
CREATE TABLE FLAGELLA (
    ALPHA integer NOT NULL,
    BETA integer NOT NULL,
    NUMBER integer NOT NULL
);

-- Table: TOUGHNESS
CREATE TABLE TOUGHNESS (
    BETA integer NOT NULL,
    GAMMA integer NOT NULL,
    RANK varchar(1) NOT NULL
);

-- End of file.


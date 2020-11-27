-- Create the database
CREATE DATABASE BanqueWeb;

-- create table compte
CREATE TABLE BanqueWeb.compte(
    NOCOMPTE varchar(4) Primary Key,
    NOM varchar(20,)
    PRENOM varchar(20),
    SOLDE decimal (10, 2) NOT NULL
);

-- create table operation
CREATE TABLE BanqueWeb.operation(
    NOCOMPTE VARCHAR(4) NOT NULL,
    DATE date NOT NULL,
    HEURE time NOT NULL,
    OP varchar(1) NOT NULL,
    VALEUR decimal(10,2)
);
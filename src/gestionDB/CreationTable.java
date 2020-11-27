package gestionDB;

import java.sql.DriverManager;

public class CreationTable {
    public static void main(String[] args) throws Throwable {
        Class.forName("com.mysql.cj.jdbc.Driver");
        var connect = DriverManager.getConnection("jdbc:mysql://localhost:3306/test?serverTimezone=GMT", "root", "root");
        var statement = connect.createStatement();
        // create database BanqueWeb
        statement.execute("CREATE DATABASE BanqueWeb");
        // create table compte
        statement.execute("CREATE table BanqueWeb.compte (" +
                "NOCOMPTE varchar(4) Primary Key," +
                "NOM varchar(20)," +
                "PRENOM varchar(20)," +
                "SOLDE decimal (10, 2) NOT NULL" +
                ")");

        // create table operation
        statement.execute("Create table BanqueWeb.operation (" +
                "NOCOMPTE VARCHAR(4) NOT NULL," +
                "DATE date NOT NULL," +
                "HEURE time NOT NULL," +
                "OP varchar(1) NOT NULL," +
                "VALEUR decimal(10,2)" +
                ")");

        statement.close();
        connect.close();
    }

}

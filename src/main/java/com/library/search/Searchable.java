package com.library.search;

import com.library.exceptions.SqlConnectionException;

import java.sql.SQLException;

public interface Searchable {

        void searchbyTitle() throws SQLException, SqlConnectionException;
         void searchbyauthor() throws SQLException, SqlConnectionException;
        void searchbypublisher() throws SQLException, SqlConnectionException;
        void searchbygenre()throws SQLException, SqlConnectionException;
        void searchbyrack()throws SQLException, SqlConnectionException;
        void searchbyisbn() throws SQLException, SqlConnectionException;
        void searchbylanguages()throws SQLException, SqlConnectionException;
    }


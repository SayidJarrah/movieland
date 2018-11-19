package com.dkorniichuk

import groovy.io.FileType
import groovy.sql.Sql
import java.sql.*;


class Loader {
    static sql = Sql.newInstance("jdbc:mysql://localhost:3306/mydb", "root", "admin", "com.mysql.jdbc.Driver");

    static void main(String[] args) {

        cleanUp(sql); //Remove previous data from db
        Map<String, String> tableNameToContentMap = prepareTextData();
        processUserType();
        processUser(tableNameToContentMap.get("user"));
        processGenre(tableNameToContentMap.get("genre"));
        processMovie(tableNameToContentMap.get("movie"));
        processPoster(tableNameToContentMap.get("poster"));
        processReview(tableNameToContentMap.get("review"));
        sql.close();

    }

    static Map<String, String> prepareTextData() {
        def files = [];
        def dir = new File("D:\\Projects\\MovieLand\\Input files");


        dir.eachFileRecurse(FileType.FILES) {
            file -> files << file
        }

        def map = new HashMap();
        files.each { file ->
            map.put(file.getName().replaceFirst("[.][^.]+", ""), file.text)
        }
        return map;
    }

    static void cleanUp(Sql sql) {
        sql.execute("delete from USER;");
        sql.execute("delete from GENRE;");
        sql.execute("delete from USER_TYPE;");
        sql.execute("delete from MOVIE;");
        sql.execute("delete from MOVIE_HAS_GENRE;");
        sql.execute("delete from REVIEW;");
        sql.execute("delete from COUNTRY;");
        sql.execute("delete from MOVIE_HAS_COUNTRY;");


    }

    static void processUserType() {
        println("***USER_TYPE***")
        def user_type_data = [[id: 2, userType: "admin"], [id: 1, userType: "user"], [id: 3, userType: "guest"]];

        user_type_data.each { dataItem ->
            sql.execute("INSERT INTO USER_TYPE (ID,TYPE_NAME) values (:fid,:fname)",
                    [fid: dataItem.id, fname: dataItem.userType]);
        }
    }

    static void processUser(String content) {
        println("***USER***")
        def arr = content.split("\r\n\r\n\r\n\r\n")
        arr.each { item ->
            def arr2 = item.split("\r\n\r\n")

            sql.execute("INSERT INTO USER (FIRST_NAME,LAST_NAME,EMAIL,PASSWORD) values (:firstName,:lastName,:email,:password)",
                    [firstName: arr2[0].split(" ")[0],
                     lastName : arr2[0].split(" ")[1],
                     email    : arr2[1],
                     password : arr2[2]]);
        }
    }

    static void processGenre(String content) {
        println("***GENRE***")
        def lines = content.replaceAll("\n\r", "")
        def fields = lines.split("\r\n")
        for (int i = 0; i < fields.length; i++) {
            sql.execute("INSERT INTO GENRE (NAME) values (:genreName)",
                    [genreName: fields[i]]);

        }
    }



    static void processMovie(String content) {
        println("***MOVIE***")
        def arr = content.split("\r\n\r\n\r\n\r\n")
        arr.each { item ->
            def lines = item.replaceAll("\n\r", "")
            def fields = lines.split("\r\n")


            def countries = fields[2].split(", ")
            countries.each { country ->
                sql.execute("INSERT INTO COUNTRY (NAME) SELECT :name FROM DUAL " +
                        "WHERE NOT EXISTS (SELECT NAME FROM COUNTRY WHERE name = :name)",
                        [name: country])
            }


            def filmName = fields[0].split("/")[0];



            sql.execute("INSERT INTO MOVIE (NAME,YEAR,DESCRIPTION,RATING,PRICE) values (:name,:year,:description,:rating,:price)",
                    [name       : fields[0],
                     year       : fields[1],
                     description: fields[4],
                     rating     : fields[5].split(":")[1],
                     price      : fields[6].split(":")[1]]);

            def movieId
            sql.eachRow("SELECT ID FROM MOVIE WHERE NAME = :name", [name: fields[0]]) {
                movieId = it.id
            }

            def genreNames = fields[3].split(",");
            List list = new ArrayList();
            genreNames.each { genreName ->
                sql.eachRow("SELECT ID FROM GENRE WHERE NAME = :name", [name: genreName.trim()]) {
                    def currentGenreId = it.id
                    sql.execute("INSERT INTO MOVIE_HAS_GENRE (MOVIE_ID,GENRE_ID) values (:movieId,:genreId)",
                            [movieId: movieId,
                             genreId: currentGenreId])
                }
            }

            countries.each {
                countryName ->
                    sql.eachRow("SELECT ID FROM COUNTRY WHERE NAME = :countryName",
                            [countryName: countryName]) {
                        def currentCountryId = it.id
                        sql.execute("INSERT INTO MOVIE_HAS_COUNTRY (MOVIE_ID,COUNTRY_ID) VALUES (:movieId,:countryId)",
                                [movieId: movieId,
                                 countryId: currentCountryId])
                    }

            }


        }

    }

    static void processPoster(String content) {
        println("***POSTER***")
        def lines = content.split("\r\n")
        for (int i = 0; i < lines.length; i++) {
            def fields = lines[i].split("https");

            sql.execute("UPDATE MOVIE SET picturePath = ? WHERE name LIKE ?"
                    ,Arrays.asList("https" + fields[1],'%'+fields[0].trim()+'%'))
        }
    }

    static void processReview(String content) {
        println("***REVIEW***")
        def arr = content.split("\r\n\r\n\r\n\r\n")
        arr.each { item ->
            def lines = item.replaceAll("\n\r", "")
            def fields = lines.split("\r\n")

            def filmName = fields[0]; // TODO: check
            def movieId
            sql.eachRow("SELECT ID FROM MOVIE  WHERE NAME LIKE ?",Arrays.asList("%"+filmName+"%")) {
                movieId = it.id
            }

            def userName = fields[1];
            def userId

            sql.eachRow("SELECT ID FROM USER WHERE LAST_NAME=:lastName AND FIRST_NAME= :firstName",
                    [lastName : fields[1].split(" ")[1],
                     firstName: fields[1].split(" ")[0]]) {
                userId = it.id
            }

            sql.execute("INSERT INTO REVIEW (USER_ID,MOVIE_ID,TEXT) values (:userId,:movieId,:text)",
                    [userId: userId, movieId: movieId, text: fields[2]]);

        }
    }

}
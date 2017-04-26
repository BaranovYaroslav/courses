/*

import lab1.ConnectionManager;
import lab1.Film;
import lab1.FilmsDAO;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Statement;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class Testing {

    @BeforeClass
    public static void startLog(){
        LocalDateTime startTime = LocalDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("kk.mm.ss");
        String dateInfo = "Time: " + timeFormat.format(startTime);
        System.out.println("Testing is started\n" + dateInfo);
    }

    // Тест метода получения записей с БД
    @Test
    public void getFilmsTest(){
        try {
            ArrayList<lab1.Film> filmsList = FilmsDAO.getFilms();

            int rowNumber = -1;
            Statement statement = lab1.ConnectionManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM films;");

            while(resultSet.next()){
                rowNumber = resultSet.getInt(1);
            }

            if(filmsList.size() != rowNumber){
                Assert.fail();
            }
        } catch (Exception e){
            Assert.fail();
        }
    }

    // Тест метода добавления записи в БД
    @Test
    public void addTest(){
            lab1.Film film = new lab1.Film("1", "John Y.", "1:48:20", 2016, "Some film");
        try{
            FilmsDAO.add(film);

            if(!FilmsDAO.getFilms().contains(film)){
                Assert.fail();
            }

            FilmsDAO.remove(film);

        } catch (Exception e){
            FilmsDAO.remove(film);
            e.printStackTrace();
            Assert.fail();
        }
    }

    // Тест метода удаления записи из БД
    @Test
    public void removeTest(){
        try {
            Film film = new Film("Hi man", "John B.", "1:48:20", 2016, "Some another film");
            FilmsDAO.add(film);

            int initialSize = FilmsDAO.getFilms().size();

            FilmsDAO.remove(film);
            Assert.assertNotEquals(initialSize, FilmsDAO.getFilms().size());
            Assert.assertEquals(initialSize-1, FilmsDAO.getFilms().size());
        }catch (Exception e){
            e.printStackTrace();
            Assert.fail();
        }

    }

    // Тест метода сохранения изменений в БД
    @Test
    public void changeTest(){
        try{

            Film film = new Film("film to change", "Kevin K.", "1:30:20", 2015, "Some film=)");
            FilmsDAO.add(film);

            Statement statement = ConnectionManager.getConnection().createStatement();
            ResultSet resultSet = statement.executeQuery(String.format("SELECT * FROM films WHERE name = '%s';", film.getName()));

            while(resultSet.next()){
                film = new Film(resultSet.getString(1), resultSet.getString(2), resultSet.getString(4),
                        resultSet.getInt(4),resultSet.getString(5));
            }

            int yearToChange = 2016;
            film.setYear(yearToChange);
            FilmsDAO.change(film);

            Statement changedStatement = ConnectionManager.getConnection().createStatement();
            ResultSet changedResultSet = changedStatement.executeQuery(String.format("SELECT * FROM films WHERE name = '%s';", film.getName()));

            Film changedFilm = new Film();
            while(changedResultSet.next()){
                changedFilm = new Film(changedResultSet.getString(1), changedResultSet.getString(2), changedResultSet.getString(3),
                        changedResultSet.getInt(4), changedResultSet.getString(5));
            }


            if(!film.equals(changedFilm)) {
                Assert.fail();
            }

            FilmsDAO.remove(film);

        } catch (Exception e){
            Assert.fail();
        }
    }

    @AfterClass
    public static void finishLog(){
        LocalDateTime finishTime = LocalDateTime.now();
        DateTimeFormatter timeFormat = DateTimeFormatter.ofPattern("kk.mm.ss");
        String dateInfo = "Time: " + timeFormat.format(finishTime);
        System.out.println("\nTesting is finished\n" + dateInfo);
    }

}

*/

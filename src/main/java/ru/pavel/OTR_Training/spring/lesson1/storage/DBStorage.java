package ru.pavel.OTR_Training.spring.lesson1.storage;

import ru.pavel.OTR_Training.spring.lesson1.model.Product;
import ru.pavel.OTR_Training.spring.lesson1.model.User;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DBStorage extends Storage {


    //String url = "jdbc:h2:./test";
    private static final String url = "jdbc:h2:mem:myDb;DB_CLOSE_DELAY=-1";
    private static final String driver = "org.h2.Driver";
    private static final String createUserTableSQL =
            "CREATE TABLE users(user_id INT AUTO_INCREMENT PRIMARY KEY,name VARCHAR(20) NOT NULL);";

    private static final String insertUsersSQL = """
            insert into users (name) values ('Иванов Иван');
            insert into users (name) values ('Петрова Надежда');
            insert into users (name) values ('Alex666');
            """;
    private static final String createProductTableSQL = """
                    CREATE TABLE products
                    (
                            product_id     INT AUTO_INCREMENT PRIMARY KEY,
                            title        VARCHAR(20)   NOT NULL,
                            price       NUMERIC    NOT NULL
                    );
            """;
    private static final String insertProductsSQL = """
            insert into products (title,price) values ('Футболка', 500);
            insert into products (title,price) values ('Ручка', 100);
            insert into products (title,price) values ('Шорты', 800);
            insert into products (title,price) values ('Карандаш', 50);
            insert into products (title,price) values ('Пенал', 250);
            insert into products (title,price) values ('Кепка', 150);
            """;

    private final static String selectProductsSQL = "SELECT * FROM products";
    private final static String selectUsersSQL = "SELECT * FROM users";

    private static final Connection connection;

    static {
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url);

            System.out.println("Connected to database");
            createTableAndInsertData(createUserTableSQL, insertUsersSQL);
            createTableAndInsertData(createProductTableSQL, insertProductsSQL);
            System.out.println("Disconnected from database");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public DBStorage() {
    }

    private static void createTableAndInsertData(String createTableSQL, String insertDataSQL) throws SQLException{
        Statement statement = connection.createStatement();
        statement.execute(createTableSQL);
        statement.execute(insertDataSQL);

    }

    @Override
    public List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectProductsSQL);
            while (rs.next()) {
                long id = rs.getInt("product_id");
                String title = rs.getString("title");
                BigDecimal price = rs.getBigDecimal("price");

                products.add(new Product(id, title, price));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return products;
    }

    @Override
    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();

        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(selectUsersSQL);
            while (rs.next()) {
                long id = rs.getInt("user_id");
                String name = rs.getString("name");

                users.add(new User(id, name));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return users;
    }

    private void destroy() throws SQLException {
        connection.close();
    }
}

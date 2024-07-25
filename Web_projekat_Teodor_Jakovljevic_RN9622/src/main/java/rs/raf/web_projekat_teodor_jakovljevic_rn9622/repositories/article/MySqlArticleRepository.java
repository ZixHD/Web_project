package rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.article;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Article;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Destination;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.User;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.MySqlAbstractRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


public class MySqlArticleRepository extends MySqlAbstractRepository implements ArticleRepository {


    @Override
    public List<Article> allArticles() {
        List<Article> articles = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM articles ORDER BY STR_TO_DATE(date, '%d.%m.%Y') DESC");
            while (resultSet.next()) {
                articles.add(new Article(resultSet.getInt("article_id"), resultSet.getString("name"), resultSet.getString("text"),
                        resultSet.getString("date"), resultSet.getInt("visits"), resultSet.getInt("user_id"), resultSet.getInt("destination_id")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }

    @Override
    public void incrementVisits(Integer id) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT visits FROM articles WHERE article_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int currentVisits = resultSet.getInt("visits");

                int newVisits = currentVisits + 1;

                preparedStatement = connection.prepareStatement("UPDATE articles SET visits = ? WHERE article_id = ?");
                preparedStatement.setInt(1, newVisits);
                preparedStatement.setInt(2, id);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
    @Override
    public List<Article> allArticlePagination(Integer page, Integer pageSize) {
        List<Article> articles = new ArrayList<>();
        int offset = (page - 1) * pageSize;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();
            String query = "SELECT * FROM articles LIMIT ? OFFSET ?";
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, pageSize);
            preparedStatement.setInt(2, offset);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                articles.add(new Article(resultSet.getInt("article_id"), resultSet.getString("name"), resultSet.getString("text"), resultSet.getString("date"), resultSet.getInt("visits"),  resultSet.getInt("user_id"),resultSet.getInt("destination_id")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return articles;
    }

    @Override
    public List<Article> getTotalPages(Integer id) {
        List<Article> articles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();


            preparedStatement = connection.prepareStatement("SELECT article_id FROM article_activity WHERE activity_id = ? ");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();


            List<Integer> articleIds = new ArrayList<>();
            while (resultSet.next()) {
                articleIds.add(resultSet.getInt("article_id"));
            }

            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);


            for (Integer articleId : articleIds) {
                preparedStatement = connection.prepareStatement("SELECT * FROM articles WHERE article_id = ? ");
                preparedStatement.setInt(1, articleId);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int article_id = resultSet.getInt("article_id");
                    String name = resultSet.getString("name");
                    String text = resultSet.getString("text");
                    String date = resultSet.getString("date");
                    int visits = resultSet.getInt("visits");
                    int destination_id = resultSet.getInt("destination_id");
                    int user_id = resultSet.getInt("user_id");
                    Article article = new Article(article_id, name, text, date, visits, user_id, destination_id);
                    articles.add(article);
                }

                this.closeResultSet(resultSet);
                this.closeStatement(preparedStatement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return articles;
    }

    @Override
    public List<Article> getArticlesWithActivity(Integer id, Integer page, Integer pageSize) {

        List<Article> articles = new ArrayList<>();
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {
            connection = this.newConnection();
            int offset = (page - 1) * pageSize;

            preparedStatement = connection.prepareStatement("SELECT article_id FROM article_activity WHERE activity_id = ? LIMIT ? OFFSET ?");
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, pageSize);
            preparedStatement.setInt(3, offset);
            resultSet = preparedStatement.executeQuery();


            List<Integer> articleIds = new ArrayList<>();
            while (resultSet.next()) {
                articleIds.add(resultSet.getInt("article_id"));
            }

            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);


            for (Integer articleId : articleIds) {
                preparedStatement = connection.prepareStatement("SELECT * FROM articles WHERE article_id = ? ");
                preparedStatement.setInt(1, articleId);
                resultSet = preparedStatement.executeQuery();

                if (resultSet.next()) {
                    int article_id = resultSet.getInt("article_id");
                    String name = resultSet.getString("name");
                    String text = resultSet.getString("text");
                    String date = resultSet.getString("date");
                    int visits = resultSet.getInt("visits");
                    int destination_id = resultSet.getInt("destination_id");
                    int user_id = resultSet.getInt("user_id");
                    Article article = new Article(article_id, name, text, date, visits, user_id, destination_id);
                    articles.add(article);
                }

                this.closeResultSet(resultSet);
                this.closeStatement(preparedStatement);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeResultSet(resultSet);
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
        return articles;
    }

    @Override
    public void joinTableAdd(Integer id, String activities) {
        Connection connection = null;
        PreparedStatement joinTableStatement = null;

        System.out.println("String: " + activities);
        try {
            connection = this.newConnection();
            String activityIdString = activities.substring(1, activities.length() - 1).split(":")[1].trim();
            String[] numbersAsStrings = activityIdString.split(",");
            List<Integer> numbers = new ArrayList<>();
            for (String str : numbersAsStrings) {
                String cleanedString = str.replace("\"", "").trim();
                String[] newOne = cleanedString.split(",");
                for (String numAsString : newOne) {
                    int num = Integer.parseInt(numAsString.trim());
                    numbers.add(num);
                }
            }

            System.out.println(numbers);



            joinTableStatement = connection.prepareStatement("INSERT INTO article_activity (article_id, activity_id) VALUES (?, ?)");
            for (Integer activityId : numbers) {
                joinTableStatement.setInt(1, id);
                joinTableStatement.setInt(2, activityId);
                joinTableStatement.addBatch();
            }
            joinTableStatement.executeBatch();

            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(joinTableStatement);
            this.closeConnection(connection);
        }


    }

    @Override
    public List<Integer> getActivities(Integer id) {
        List<Integer> activities = new ArrayList<>();


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM article_activity where article_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int activity_id = resultSet.getInt("activity_id");
                activities.add(activity_id);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return activities;
    }


    @Override
    public List<Article> getArticle(Integer id) {

        List<Article> articles = new ArrayList<>();


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM articles where destination_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int article_id = resultSet.getInt("article_id");
                String name = resultSet.getString("name");
                String text = resultSet.getString("text");
                String date = resultSet.getString("date");
                int visits = resultSet.getInt("visits");
                int user_id = resultSet.getInt("user_id");
                int destination_id = resultSet.getInt("destination_id");
                Article article = new Article(article_id, name, text, date, visits, user_id, destination_id);
                articles.add(article);
            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return articles;
    }

    @Override
    public Article findArticle(Integer id) {
       Article article = null;


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM articles where article_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int article_id = resultSet.getInt("article_id");
                String name = resultSet.getString("name");
                String text = resultSet.getString("text");
                String date = resultSet.getString("date");
                int visits = resultSet.getInt("visits");
                int user_id = resultSet.getInt("user_id");
                int destination_id = resultSet.getInt("destination_id");
                article = new Article(article_id, name, text, date, visits, user_id, destination_id);

            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return article;
    }

    @Override
    public User getArticleAuthor(Integer id) {
        User user = null;

        System.out.println("USAo");

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM users where user_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int user_id = resultSet.getInt("user_id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                String user_email = resultSet.getString("email");
                String role = resultSet.getString("role");
                String password = resultSet.getString("password");
                boolean active = resultSet.getBoolean("active");
                user = new User(user_id, name, surname, user_email, role, active, password);

            }

            resultSet.close();
            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }
        return user;
    }


    @Override
    public Article addArticle(Article article) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};
            System.out.println(article.getDate());
            preparedStatement = connection.prepareStatement("INSERT INTO articles (name, text, date, visits, user_id, destination_id) VALUES(?, ?, ?, ?, ? ,?)", generatedColumns);
            preparedStatement.setString(1, article.getName());
            preparedStatement.setString(2, article.getText());
            preparedStatement.setString(3, article.getDate());
            preparedStatement.setInt(4, article.getVisits());
            preparedStatement.setInt(5, article.getUser_id());
            preparedStatement.setInt(6, article.getDestination_id());




            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                article.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return article;
    }

    @Override
    public Article editArticle(Integer id, Article article) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("UPDATE articles SET name = ?, text = ?, date = ?, visits = ?, user_id = ?, destination_id = ? WHERE article_id = ?");
            preparedStatement.setString(1, article.getName());
            preparedStatement.setString(2, article.getText());
            preparedStatement.setString(3, article.getDate());
            preparedStatement.setInt(4, article.getVisits());
            preparedStatement.setInt(5, article.getUser_id());
            preparedStatement.setInt(6, article.getDestination_id());
            preparedStatement.setInt(7, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }

        return article;

    }

    @Override
    public void deleteArticle(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM articles where article_id = ?");
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeConnection(connection);
        }
    }
}

package rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.comment;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Comment;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Destination;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.MySqlAbstractRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.user.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MySqlCommentRepository extends MySqlAbstractRepository implements CommentRepository {


    @Override
    public List<Comment> getComments(Integer id) {

        List<Comment> comments = new ArrayList<>();


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM comments where article_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int comment_id = resultSet.getInt("comment_id");
                String name = resultSet.getString("name");
                String text = resultSet.getString("text");
                String date = resultSet.getString("date");
                int article_id = resultSet.getInt("article_id");
                Comment comment = new Comment(comment_id, name, text, date, article_id);
                comments.add(comment);
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
        return comments;
    }

    @Override
    public Comment addComment(Comment comment) {

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO comments (name, text, date, article_id) VALUES(?, ?, ?, ?)", generatedColumns);
            preparedStatement.setString(1, comment.getName());
            preparedStatement.setString(2, comment.getText());
            preparedStatement.setString(3, comment.getDate());
            preparedStatement.setInt(4, comment.getArticle_id());



            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                comment.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return comment;
    }

    @Override
    public void deleteComment(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM comments where comment_id = ?");
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

package rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.activity;

import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Activity;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.Destination;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.entities.User;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.repositories.MySqlAbstractRepository;
import rs.raf.web_projekat_teodor_jakovljevic_rn9622.resources.ActivityResources;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySqlActivityRepository extends MySqlAbstractRepository implements ActivityRepository {


    @Override
    public List<Activity> allActivities() {

        List<Activity> activities = new ArrayList<>();

        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            statement = connection.createStatement();
            resultSet = statement.executeQuery("select * from activities");
            while (resultSet.next()) {
                activities.add(new Activity(resultSet.getInt("activity_id"), resultSet.getString("text"), resultSet.getInt("destination_id")));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(statement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return activities;
    }

    @Override
    public List<Activity> getActivity(Integer id) {


        List<Activity> activities = new ArrayList<>();


        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM activities where destination_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int activity_id = resultSet.getInt("activity_id");
                String text = resultSet.getString("text");
                int destination_id = resultSet.getInt("destination_id");
                Activity activity = new Activity(activity_id, text, destination_id);
                activities.add(activity);

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
    public Activity findActivity(Integer id) {

        Activity activity = null;

        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM activities where activity_id = ?");
            preparedStatement.setInt(1, id);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int activity_id = resultSet.getInt("activity_id");
                String text = resultSet.getString("text");
                int destination_id = resultSet.getInt("destination_id");
                activity = new Activity(activity_id, text, destination_id);
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
        return activity;
    }

    @Override
    public Activity addActivity(Activity activity) {

        Connection connection = null;
        System.out.println("yup");
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        try {
            connection = this.newConnection();

            String[] generatedColumns = {"id"};

            preparedStatement = connection.prepareStatement("INSERT INTO activities (text, destination_id) VALUES(?, ?)", generatedColumns);
            preparedStatement.setString(1, activity.getText());
            preparedStatement.setInt(2, activity.getDestination_id());



            preparedStatement.executeUpdate();
            resultSet = preparedStatement.getGeneratedKeys();

            if (resultSet.next()) {
                activity.setId(resultSet.getInt(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            this.closeStatement(preparedStatement);
            this.closeResultSet(resultSet);
            this.closeConnection(connection);
        }

        return activity;
    }

    @Override
    public void deleteActivity(Integer id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        try {
            connection = this.newConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM activities where activity_id = ?");
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

package com.medalmanager.repository;

import com.medalmanager.model.entity.Country;
import com.medalmanager.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CountryRepository {
    public List<Country> findAll() {
        List<Country> countries = new ArrayList<>();
        String query = "SELECT id, nome, participando FROM paises";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                countries.add(new Country(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getBoolean("participando")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching countries", e);
        }

        return countries;
    }

    public Optional<Country> findById(Long id) {
        String query = "SELECT id, nome, participando FROM paises WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new Country(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getBoolean("participando")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching country", e);
        }

        return Optional.empty();
    }

    public void updateParticipationStatus(Long id, boolean participating) {
        String query = "UPDATE paises SET participando = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, participating);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating country participation", e);
        }
    }

    public void resetAllParticipationStatus() {
        String query = "UPDATE paises SET participando = false";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error resetting country participation", e);
        }
    }
}
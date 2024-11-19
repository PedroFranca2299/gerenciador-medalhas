package com.medalmanager.repository;

import com.medalmanager.model.entity.Modality;
import com.medalmanager.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ModalityRepository {
    public List<Modality> findAll() {
        List<Modality> modalities = new ArrayList<>();
        String query = "SELECT id, nome, ativo FROM modalidades";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                modalities.add(new Modality(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getBoolean("ativo")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching modalities", e);
        }

        return modalities;
    }

    public Optional<Modality> findById(Long id) {
        String query = "SELECT id, nome, ativo FROM modalidades WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new Modality(
                        rs.getLong("id"),
                        rs.getString("nome"),
                        rs.getBoolean("ativo")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching modality", e);
        }

        return Optional.empty();
    }

    public void updateActiveStatus(Long id, boolean active) {
        String query = "UPDATE modalidades SET ativo = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setBoolean(1, active);
            stmt.setLong(2, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error updating modality status", e);
        }
    }

    public void resetAllActiveStatus() {
        String query = "UPDATE modalidades SET ativo = false";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement()) {

            stmt.executeUpdate(query);
        } catch (SQLException e) {
            throw new RuntimeException("Error resetting modality status", e);
        }
    }
}
package com.medalmanager.repository;

import com.medalmanager.model.entity.Etapa;
import com.medalmanager.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EtapaRepository {

    public List<Etapa> findAll() {
        List<Etapa> etapas = new ArrayList<>();
        String query = "SELECT id, nome FROM etapas";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                etapas.add(new Etapa(
                        rs.getLong("id"),
                        rs.getString("nome")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching etapas", e);
        }

        return etapas;
    }

    public Optional<Etapa> findById(Long id) {
        String query = "SELECT id, nome FROM etapas WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return Optional.of(new Etapa(
                        rs.getLong("id"),
                        rs.getString("nome")
                ));
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error fetching etapa", e);
        }

        return Optional.empty();
    }

    public Etapa save(Etapa etapa) {
        String query = etapa.getId() == null ?
                "INSERT INTO etapas (nome) VALUES (?)" :
                "UPDATE etapas SET nome = ? WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, etapa.getNome());
            if (etapa.getId() != null) {
                stmt.setLong(2, etapa.getId());
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating/Updating etapa failed, no rows affected.");
            }

            if (etapa.getId() == null) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        etapa.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Creating etapa failed, no ID obtained.");
                    }
                }
            }

            return etapa;
        } catch (SQLException e) {
            throw new RuntimeException("Error saving etapa", e);
        }
    }

    public void delete(Long id) {
        String query = "DELETE FROM etapas WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException("Error deleting etapa", e);
        }
    }
}

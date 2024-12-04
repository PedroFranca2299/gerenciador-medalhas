package com.medalmanager.repository;

import com.medalmanager.model.entity.Resultado;
import com.medalmanager.model.entity.ParticipacaoResultado;
import com.medalmanager.util.DatabaseConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ResultadoRepository {

    public Resultado save(Resultado resultado) {
        Connection conn = null;
        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false);

            String resultadoQuery = "INSERT INTO resultados (modalidade_id, etapa_id, data_resultado) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(resultadoQuery, Statement.RETURN_GENERATED_KEYS)) {
                stmt.setLong(1, resultado.getModalidadeId());
                stmt.setLong(2, resultado.getEtapaId());
                stmt.setTimestamp(3, Timestamp.valueOf(resultado.getDataResultado()));

                stmt.executeUpdate();

                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        resultado.setId(generatedKeys.getLong(1));
                    } else {
                        throw new SQLException("Falha ao obter ID do resultado.");
                    }
                }
            }

            String participacaoQuery = "INSERT INTO participacoes_resultado (resultado_id, pais_id, posicao) VALUES (?, ?, ?)";

            try (PreparedStatement stmt = conn.prepareStatement(participacaoQuery, Statement.RETURN_GENERATED_KEYS)) {
                for (ParticipacaoResultado participacao : resultado.getParticipacoes()) {
                    stmt.setLong(1, resultado.getId());
                    stmt.setLong(2, participacao.getPaisId());
                    if (participacao.getPosicao() != null) {
                        stmt.setInt(3, participacao.getPosicao());
                    } else {
                        stmt.setNull(3, Types.INTEGER);
                    }

                    stmt.executeUpdate();

                    try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            participacao.setId(generatedKeys.getLong(1));
                        }
                    }
                }
            }

            conn.commit();
            return resultado;

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new RuntimeException("Erro ao salvar resultado: " + e.getMessage(), e);
        } finally {
            try {
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public List<Resultado> findAll() {
        List<Resultado> resultados = new ArrayList<>();
        String query = "SELECT id, modalidade_id, etapa_id, data_resultado FROM resultados";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Resultado resultado = new Resultado(
                        rs.getLong("id"),
                        rs.getLong("modalidade_id"),
                        rs.getLong("etapa_id"),
                        rs.getTimestamp("data_resultado").toLocalDateTime()
                );

                // Carrega as participações para cada resultado
                carregarParticipacoes(resultado, conn);
                resultados.add(resultado);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar resultados", e);
        }

        return resultados;
    }

    private void carregarParticipacoes(Resultado resultado, Connection conn) throws SQLException {
        String query = "SELECT id, pais_id, posicao FROM participacoes_resultado WHERE resultado_id = ?";

        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setLong(1, resultado.getId());

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    ParticipacaoResultado participacao = new ParticipacaoResultado(
                            rs.getLong("id"),
                            resultado.getId(),
                            rs.getLong("pais_id"),
                            rs.getObject("posicao", Integer.class)
                    );
                    resultado.addParticipacao(participacao);
                }
            }
        }
    }

    public Optional<Resultado> findById(Long id) {
        String query = "SELECT id, modalidade_id, etapa_id, data_resultado FROM resultados WHERE id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Resultado resultado = new Resultado(
                        rs.getLong("id"),
                        rs.getLong("modalidade_id"),
                        rs.getLong("etapa_id"),
                        rs.getTimestamp("data_resultado").toLocalDateTime()
                );

                carregarParticipacoes(resultado, conn);
                return Optional.of(resultado);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar resultado", e);
        }

        return Optional.empty();
    }
}
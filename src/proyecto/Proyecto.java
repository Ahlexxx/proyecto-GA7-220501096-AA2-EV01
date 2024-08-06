package proyecto;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Proyecto {

    public static void main(String[] args) {
        String usuario = "root";
        String password = "";
        String url = "jdbc:mysql://localhost:3306/salon_de_belleza";
        Connection conexion = null;
        Statement statement = null;
        ResultSet rs = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Proyecto.class.getName()).log(Level.SEVERE, "Error al cargar el driver", ex);
            return;
        }

        try {
            conexion = DriverManager.getConnection(url, usuario, password);
            statement = conexion.createStatement();

            // Apartado inserción de datos
            String insertSQL = "INSERT INTO citas(usuario, servicio, funcionario, fecha, hora) " +
                               "VALUES('nicol', 'manicura', 'andrea', '2024-08-07', '13:30:00')";
            statement.executeUpdate(insertSQL);

            insertSQL = "INSERT INTO citas(usuario, servicio, funcionario, fecha, hora) " +
                        "VALUES('camilo', 'corte de pelo', 'juan', '2024-08-08', '14:30:00')";
            statement.executeUpdate(insertSQL);

            // Apartado consulta de datos
            String selectSQL = "SELECT * FROM citas";
            rs = statement.executeQuery(selectSQL);

            while (rs.next()) {
                System.out.println(rs.getInt("id") + ": " +
                                   rs.getString("usuario") + ": " +
                                   rs.getString("servicio") + ": " +
                                   rs.getString("funcionario") + ": " +
                                   rs.getString("fecha") + ": " +
                                   rs.getString("hora"));
            }

            //Apartado actualización de datos
            String updateSQL = "UPDATE citas SET servicio=?, funcionario=?, fecha=?, hora=? WHERE usuario=?";
            try (PreparedStatement pstmt = conexion.prepareStatement(updateSQL)) {
                pstmt.setString(1, "pedicure");
                pstmt.setString(2, "andrea");
                pstmt.setString(3, "2024-08-10");
                pstmt.setString(4, "15:00:00");
                pstmt.setString(5, "nicol");
                pstmt.executeUpdate();
            }

            // Apartado eliminación de datos
            String deleteSQL = "DELETE FROM citas WHERE usuario=?";
            try (PreparedStatement pstmt = conexion.prepareStatement(deleteSQL)) {
                pstmt.setString(1, "camilo");
                pstmt.executeUpdate();
            }

        } catch (SQLException ex) {
            
        } finally {
            
            try {
                if (rs != null) rs.close();
                if (statement != null) statement.close();
                if (conexion != null) conexion.close();
            } catch (SQLException ex) {
                
            }
        }
    }
}

    


/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Modelo;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author USER1
 */
public class CVentas extends Conexion{
    public boolean agregarCliente(Ventas venta) {
        Connection con = getConexion();
        PreparedStatement ps = null;

        String sql = "INSERT INTO cliente (Nombre, Apellido, Categoria,Cantidad,Producto) VALUES (? ,?, ?, ?, ?)";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, venta.getNombre());
            ps.setString(2, venta.getApellidos());
            ps.setString(3, venta.getCategoria());
            ps.setString(4, venta.getCantidad());
            ps.setString(5, venta.getProducto());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean ActualizarCliente(Ventas venta) {
        Connection con = getConexion();
        PreparedStatement ps = null;

        String sql = "UPDATE cliente SET Nombre=?, Apellido=?, Categoria=?,Cantidad=?,Producto=?  WHERE idCliente=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setString(1, venta.getNombre());
            ps.setString(2, venta.getApellidos());
            ps.setString(3, venta.getCategoria());
            ps.setString(4, venta.getCantidad());
            ps.setString(5, venta.getProducto());
            ps.setInt(6, venta.getIdVenta());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public boolean eliminarCliente(Ventas venta) {
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "DELETE FROM cliente WHERE idCliente=?";
        try {
            ps = con.prepareStatement(sql);
            ps.setInt(1, venta.getIdVenta());
            ps.execute();
            return true;
        } catch (SQLException e) {
            System.err.println(e);
            return false;
        } finally {
            try {
                con.close();
            } catch (SQLException e) {
                System.err.println(e);
            }
        }
    }

    public ArrayList<Ventas> listaVentas() {
        ArrayList listaVentas = new ArrayList();
        Ventas venta;
        Connection con = getConexion();
        try {
            String sql = "SELECT * FROM cliente";
            PreparedStatement ps = con.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                venta = new Ventas();
                venta.setIdVenta(rs.getInt(1));
                venta.setNombre(rs.getString(2));
                venta.setApellidos(rs.getString(3));
                venta.setCategoria(rs.getString(4));
                venta.setCantidad(rs.getString(5));
                venta.setProducto(rs.getString(6));
                listaVentas.add(venta);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "No se pudo enlistar los registros" + e.getMessage());
        }
        return listaVentas;
    }

    public ArrayList<Ventas> BuscarporNombre(String nombre) {
        ArrayList buscarCliente = new ArrayList();
        Ventas venta;
        try {
            Connection con = getConexion();
            CallableStatement cs = con.prepareCall("SELECT * FROM cliente WHERE nombre=?");
            cs.setString(1, nombre);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                venta = new Ventas();
                venta.setIdVenta(rs.getInt(1));
                venta.setNombre(rs.getString(2));
                venta.setApellidos(rs.getString(3));
                venta.setCategoria(rs.getString(4));
                venta.setCantidad(rs.getString(5));
                venta.setProducto(rs.getString(6));
                buscarCliente.add(venta);
            }
        } catch (Exception e) {
        }
        return buscarCliente;
    }
}

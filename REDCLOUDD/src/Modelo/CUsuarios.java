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
public class CUsuarios extends Conexion {
     private String Nombre;
    private int IdUsuario;
    private String Tipo;
        public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public int getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(int IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
    
    
    
    
    
    
    public boolean agregarUsuarios(Usuarios user){
        Connection con = getConexion();
        PreparedStatement ps = null;
        
        String sql = "INSERT INTO usuarios (nombre, apellidos, contraseña, tipoUsuario) VALUES (? ,?, ?, ?)";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellidos());
            ps.setString(3, user.getContraseña());
            ps.setString(4, user.getTipoUsuario());
            ps.executeUpdate();
           return true;
        }catch(SQLException e){
            System.err.println(e); 
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    public boolean ActualizarUsuarios(Usuarios user){
        Connection con = getConexion();
        PreparedStatement ps = null;
        
        String sql="UPDATE usuarios SET nombre=?, apellidos=?, contraseña=?, tipoUsuario=? WHERE idUsuario=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setString(1, user.getNombre());
            ps.setString(2, user.getApellidos());
            ps.setString(3, user.getContraseña());
            ps.setString(4, user.getTipoUsuario());
            ps.setInt(5, user.getIdUsuarios());
            ps.execute();
            return true;
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }
    public boolean eliminarUsuario(Usuarios user){
        PreparedStatement ps = null;
        Connection con = getConexion();
        String sql = "DELETE FROM usuarios WHERE idUsuario=?";
        try{
            ps = con.prepareStatement(sql);
            ps.setInt(1, user.getIdUsuarios());
            ps.execute();
            return true;
        }catch(SQLException e){
            System.err.println(e);
            return false;
        }finally{
            try{
                con.close();
            }catch(SQLException e){
                System.err.println(e);
            }
        }
    }

      public String iniciarSesion(String Nombre, String Contraseña) {
        String buscarUsuarios = null;
        Connection con = getConexion();
        Usuarios user;
        try {
            String SQL = ("Select * from usuarios where nombre= '" + Nombre + "' && contraseña='" + Contraseña + "'");
            PreparedStatement st = con.prepareStatement(SQL);
            ResultSet rs = st.executeQuery();
            if(rs.next()){
                  
                buscarUsuarios=String.valueOf(rs.getInt(1));
                this.setIdUsuario(rs.getInt(1));
                  this.setNombre(rs.getString(2));
                   this.setTipo(rs.getString(5));
               
              
                
                
            }else{
                buscarUsuarios=null;
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error al iniciar sesion :" + e.getMessage());
        }
        return buscarUsuarios;
    }
    
    public ArrayList<Usuarios> listaUsuarios(){
         ArrayList listaUsuarios = new ArrayList();
         Usuarios user;
         Connection con = getConexion();
         try{
             String sql="SELECT * FROM usuarios";
             PreparedStatement ps=con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery();
             while(rs.next()){
                 user= new Usuarios();
                 user.setIdUsuarios(rs.getInt(1));
                 user.setNombre(rs.getString(2));
                 user.setApellidos(rs.getString(3));
                 user.setContraseña(rs.getString(4));
                 user.setTipoUsuario(rs.getString(5));
                 listaUsuarios.add(user);
             }   
         }catch(Exception e){
             JOptionPane.showMessageDialog(null,"No se pudo enlistar los registros"+ e.getMessage());
         }
         return listaUsuarios;
    }
     public ArrayList<Usuarios> BuscarporNombre(String nombre) {
        ArrayList buscar = new ArrayList();
        Usuarios user;
        try {
            Connection con = getConexion();
            /**CallableStatement cs = con.prepareCall("SELECT  usuarios.nombre, usuarios.apellidos, usuarios.correo, usuarios.contraseña, roles.Rol  FROM usuarios INNER JOIN roles "
                    + "ON usuarios.idRol=roles.idRol  WHERE usuarios.nombre=?");*/
            CallableStatement cs = con.prepareCall("SELECT * FROM usuarios WHERE nombre=?");
            cs.setString(1, nombre);
            ResultSet rs = cs.executeQuery();
            while (rs.next()) {
                user= new Usuarios();
                 user.setIdUsuarios(rs.getInt(1));
                 user.setNombre(rs.getString(2));
                 user.setApellidos(rs.getString(3));
                 user.setContraseña(rs.getString(4));
                 user.setTipoUsuario(rs.getString(5));
                buscar.add(user);
            }
        } catch (Exception e) {
        }
        return buscar;
    }
    
    
}

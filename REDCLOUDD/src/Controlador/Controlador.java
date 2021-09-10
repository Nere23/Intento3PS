/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.CVentas;
import Modelo.Ventas;
import Vistas.AltaVentas;
import Vistas.AltaUsuarios;
import Vistas.Menu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import Vistas.Login;
import Modelo.CUsuarios;
import Modelo.Usuarios;
/**
 *
 * @author USER1
 */
public class Controlador implements ActionListener, KeyListener, MouseListener {
    CVentas modeloCVentas;
    Ventas medeloVenta;
    AltaVentas frmVentas;
    AltaUsuarios frmUser;
    Login frmLogin;
    Menu frmMain;
    CUsuarios modelCUser;
    Usuarios modelUser;
     String User;
    int id;
    
    public Controlador(Login frmLogin, CVentas modeloCVentas) {
        this.modeloCVentas = modeloCVentas;
        this.frmVentas = frmVentas;
        this.medeloVenta = new Ventas();
        this.modeloCVentas = new CVentas();
        this.frmVentas = new AltaVentas();
        this.frmVentas.btnAtualizarVenta.addActionListener(this);
        this.frmVentas.btnEliminarVenta.addActionListener(this);
        this.frmVentas.btnIngresarVenta.addActionListener(this);
        this.frmVentas.btnRgresarVenta.addActionListener(this);
        this.frmVentas.btnVAlidarVenta.addActionListener(this);
        this.frmVentas.tblVentas.addMouseListener(this);
        this.frmVentas.txtBuscarVenta.addKeyListener(this);
        this.frmLogin = new Login();
        this.frmLogin.btnSalir.addActionListener(this);
        this.frmLogin.btnEntrar.addActionListener(this);
        this.frmUser = new AltaUsuarios();
        this.frmUser.btnAtualizarUsuario.addActionListener(this);
        this.frmUser.btnEliminarUsuario.addActionListener(this);
        this.frmUser.btnIngresarUsuario.addActionListener(this);
        this.frmUser.btnRgresarUsuario.addActionListener(this);
        this.frmUser.btnValidarUsuario.addActionListener(this);
        this.frmUser.tblUsuarios.addMouseListener(this);
        this.frmUser.txtBuscarUsuario.addKeyListener(this);
        this.frmMain = new Menu();
        this.frmMain.btnMenuUsuarios.addActionListener(this);
        this.frmMain.btnMenuVentas.addActionListener(this);
        this.frmMain.btnSalir.addActionListener(this);
        this.modelCUser = new CUsuarios();
        this.modelUser = new Usuarios();
    }
    public void iniciar(){
        this.frmLogin.setVisible(true);
        this.frmLogin.setTitle("INICIO DE SESION");
        this.frmLogin.setLocationRelativeTo(null);
        this.frmVentas.btnIngresarVenta.setEnabled(false);
        this.frmVentas.txtIdVenta.setVisible(false);
        this.LlenarTabla(this.frmVentas.tblVentas);
        this.frmUser.txtIdUsuario.setVisible(false);
        this.LlenarTablaUsuarios(this.frmUser.tblUsuarios);
        this.frmUser.btnIngresarUsuario.setEnabled(false);
    }
   
      
    
    
    

    @Override
    public void actionPerformed(ActionEvent e) {
         if (e.getSource() == this.frmLogin.btnEntrar) {
           if (this.modelCUser.iniciarSesion(this.frmLogin.txtUsuarioLogin.getText(), String.valueOf(this.frmLogin.txtPasswordLogin.getPassword())) != null) {

                if (this.modelCUser.getTipo().equals("usuario")) {
                    this.frmMain.setVisible(true);
                    this.frmMain.setLocationRelativeTo(null);
                    this.frmMain.btnMenuUsuarios.setEnabled(false);
                   this.frmLogin.setVisible(false);
                   this.frmVentas.btnVAlidarVenta.setEnabled(false);
                   this.frmVentas.btnEliminarVenta.setEnabled(false);
                   
                }
                if (this.modelCUser.getTipo().equals("administrador")) {
                   this.frmMain.setVisible(true);
                   this.frmMain.setLocationRelativeTo(null);
                    this.frmLogin.setVisible(false);
                 
                }
                this.User = this.modelCUser.getNombre();
                this.id = this.modelCUser.getIdUsuario();

                //this.frmMain.setVisible(true);
                this.frmLogin.setVisible(false);
            } else {
                JOptionPane.showMessageDialog(null, "Usuario o contraseña incorrectos");
            }
        } 
         if(e.getSource()==this.frmLogin.btnSalir){
             this.frmLogin.dispose();
         }
             
        if(e.getSource()==this.frmVentas.btnVAlidarVenta){
            this.ValidarVenta();
        }
        if(e.getSource()==this.frmVentas.btnRgresarVenta){
            this.frmVentas.setVisible(false);
            this.frmMain.setVisible(true);
            this.frmMain.setLocationRelativeTo(null);
        }
        if(e.getSource() == this.frmVentas.btnIngresarVenta){
            this.medeloVenta.setNombre(this.frmVentas.txtNombreVenta.getText());
            this.medeloVenta.setApellidos(this.frmVentas.txtApellidos.getText());
            this.medeloVenta.setCategoria(this.frmVentas.txtCategoria.getText());
            this.medeloVenta.setCantidad(this.frmVentas.txtCantidad.getText());
            this.medeloVenta.setProducto(this.frmVentas.txtProducto.getText());
            if(this.modeloCVentas.agregarCliente(medeloVenta)){
                JOptionPane.showMessageDialog(null,"Venta resgitrada");
                this.limpiarVenta();
                this.LlenarTabla(this.frmVentas.tblVentas);
            }else{
                JOptionPane.showMessageDialog(null,"Erro al registrar la venta");
                this.limpiarVenta();
                this.LlenarTabla(this.frmVentas.tblVentas);
            }
                    
        }
         if(e.getSource() == this.frmVentas.btnAtualizarVenta){
             this.medeloVenta.setIdVenta(Integer.parseInt(this.frmVentas.txtIdVenta.getText()));
            this.medeloVenta.setNombre(this.frmVentas.txtNombreVenta.getText());
            this.medeloVenta.setApellidos(this.frmVentas.txtApellidos.getText());
            this.medeloVenta.setCategoria(this.frmVentas.txtCategoria.getText());
            this.medeloVenta.setCantidad(this.frmVentas.txtCantidad.getText());
            this.medeloVenta.setProducto(this.frmVentas.txtProducto.getText());
            if(this.modeloCVentas.ActualizarCliente(medeloVenta)){
                JOptionPane.showMessageDialog(null,"Venta actualizada");
                this.limpiarVenta();
                this.LlenarTabla(this.frmVentas.tblVentas);
            }else{
                JOptionPane.showMessageDialog(null,"Erro al actualizar la venta");
                this.limpiarVenta();
                this.LlenarTabla(this.frmVentas.tblVentas);
            }
                    
        }
         if(e.getSource() == this.frmVentas.btnEliminarVenta){
             this.medeloVenta.setIdVenta(Integer.parseInt(this.frmVentas.txtIdVenta.getText()));
            if(this.modeloCVentas.eliminarCliente(medeloVenta)){
                JOptionPane.showMessageDialog(null,"Venta eliminada");
                this.limpiarVenta();
                this.LlenarTabla(this.frmVentas.tblVentas);
            }else{
                JOptionPane.showMessageDialog(null,"Error al eliminar la venta");
                this.limpiarVenta();
                this.LlenarTabla(this.frmVentas.tblVentas);
            }                    
        }
         if(e.getSource()== this.frmUser.btnIngresarUsuario){
             this.modelUser.setNombre(this.frmUser.txtNombreUsuario.getText());
             this.modelUser.setApellidos(this.frmUser.txtApellidosUsuarios.getText());
             this.modelUser.setContraseña(this.frmUser.txtContraseña.getText());
             this.modelUser.setTipoUsuario(this.frmUser.txtRol.getText());
             if(this.modelCUser.agregarUsuarios(modelUser)){
                 JOptionPane.showMessageDialog(null,"Usuario agregado");
                 this.limpiarUsuario();
                 this.LlenarTablaUsuarios(this.frmUser.tblUsuarios);
             }else{
                 JOptionPane.showMessageDialog(null,"Error al ingresar el usuario");
                 this.limpiarUsuario();
                 this.LlenarTablaUsuarios(this.frmUser.tblUsuarios);
             }
         }
         if(e.getSource()== this.frmUser.btnAtualizarUsuario){
             this.modelUser.setIdUsuarios(Integer.parseInt(this.frmUser.txtIdUsuario.getText()));
             this.modelUser.setNombre(this.frmUser.txtNombreUsuario.getText());
             this.modelUser.setApellidos(this.frmUser.txtApellidosUsuarios.getText());
             this.modelUser.setContraseña(this.frmUser.txtContraseña.getText());
             this.modelUser.setTipoUsuario(this.frmUser.txtRol.getText());
             if(this.modelCUser.ActualizarUsuarios(modelUser)){
                 JOptionPane.showMessageDialog(null,"Usuario actualizado");
                 this.limpiarUsuario();
                 this.LlenarTablaUsuarios(this.frmUser.tblUsuarios);
             }else{
                 JOptionPane.showMessageDialog(null,"Error al actualizar el usuario");
                 this.limpiarUsuario();
                 this.LlenarTablaUsuarios(this.frmUser.tblUsuarios);
             }
         }
         if(e.getSource()== this.frmUser.btnEliminarUsuario){
             this.modelUser.setIdUsuarios(Integer.parseInt(this.frmUser.txtIdUsuario.getText()));
             if(this.modelCUser.eliminarUsuario(modelUser)){
                 JOptionPane.showMessageDialog(null,"Usuario eliminado");
                 this.limpiarUsuario();
                 this.LlenarTablaUsuarios(this.frmUser.tblUsuarios);
             }else{
                 JOptionPane.showMessageDialog(null,"Error al eliminar el usuario");
                 this.limpiarUsuario();
                 this.LlenarTablaUsuarios(this.frmUser.tblUsuarios);
             }
         }
         if(e.getSource()== this.frmUser.btnValidarUsuario){
             this.validarUsuario();
         }
         if(e.getSource() == this.frmUser.btnRgresarUsuario){
             this.frmUser.setVisible(false);
             this.frmMain.setVisible(true);
             this.frmMain.setLocationRelativeTo(null);
         }
         if(e.getSource()==this.frmMain.btnMenuUsuarios){
             this.frmMain.setVisible(false);
             this.frmUser.setVisible(true);
             this.frmMain.setLocationRelativeTo(null);
         }
         if(e.getSource() == this.frmMain.btnMenuVentas){
            this.frmMain.setVisible(false);
             this.frmVentas.setVisible(true);
             this.frmVentas.setLocationRelativeTo(null);
         }
         if(e.getSource()==this.frmMain.btnSalir){
             this.frmMain.dispose();
         }
    }
    
    
    public void limpiarVenta(){
        this.frmVentas.txtApellidos.setText(null);
        this.frmVentas.txtBuscarVenta.setText(null);
        this.frmVentas.txtCantidad.setText(null);
        this.frmVentas.txtCategoria.setText(null);
        this.frmVentas.txtIdVenta.setText(null);
        this.frmVentas.txtNombreVenta.setText(null);
        this.frmVentas.txtProducto.setText(null);
    }
    public void limpiarUsuario(){
        
        this.frmUser.txtApellidosUsuarios.setText(null);
        this.frmUser.txtBuscarUsuario.setText(null);
        this.frmUser.txtContraseña.setText(null);
        this.frmUser.txtIdUsuario.setText(null);
        this.frmUser.txtNombreUsuario.setText(null);
        this.frmUser.txtRol.setText(null);
    }
    public void validarUsuario(){
       if(!this.frmUser.txtApellidosUsuarios.getText().isEmpty() && !this.frmUser.txtContraseña.getText().isEmpty()
               && !this.frmUser.txtNombreUsuario.getText().isEmpty() && !this.frmUser.txtRol.getText().isEmpty()) {
               this.frmUser.btnIngresarUsuario.setEnabled(true);
       }else{
           JOptionPane.showMessageDialog(null,"Dejastes campos vacios");
       }
    }
    public void ValidarVenta(){
        if(!this.frmVentas.txtApellidos.getText().isEmpty() && !this.frmVentas.txtCantidad.getText().isEmpty() &&
                !this.frmVentas.txtCategoria.getText().isEmpty() && !this.frmVentas.txtNombreVenta.getText().isEmpty()
                && !this.frmVentas.txtProducto.getText().isEmpty()){
            
            this.frmVentas.btnIngresarVenta.setEnabled(true);
        }else{
            JOptionPane.showMessageDialog(null,"Dejastes campos vacios");
        }
    }
     public void LlenarTabla(JTable tablaD){
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        modeloT.addColumn("idCliente");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellidos");
        modeloT.addColumn("Categoria");
        modeloT.addColumn("Cantidad");
        modeloT.addColumn("Producto");
        Object[] columna= new Object[6];
        int numRegistro= this.modeloCVentas.listaVentas().size();
        for(int i=0; i<numRegistro; i++){
            columna[0]=this.modeloCVentas.listaVentas().get(i).getIdVenta();
            columna[1]=this.modeloCVentas.listaVentas().get(i).getNombre();
            columna[2]=this.modeloCVentas.listaVentas().get(i).getApellidos();
            columna[3]=this.modeloCVentas.listaVentas().get(i).getCategoria();
            columna[4]=this.modeloCVentas.listaVentas().get(i).getCantidad();
            columna[5]=this.modeloCVentas.listaVentas().get(i).getProducto();
            modeloT.addRow(columna);
        }
    }
     public void LlenarTablaUsuarios(JTable tablaD) {
        DefaultTableModel modeloT = new DefaultTableModel();
        tablaD.setModel(modeloT);
        modeloT.addColumn("idUsuario");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellidos");
        modeloT.addColumn("Contraseña");
        modeloT.addColumn("tipoUsuario");
        Object[] columna = new Object[5];
        int numRegistro = this.modelCUser.listaUsuarios().size();
        for (int i = 0; i < numRegistro; i++) {
            columna[0] = this.modelCUser.listaUsuarios().get(i).getIdUsuarios();
            columna[1] = this.modelCUser.listaUsuarios().get(i).getNombre();
            columna[2] = this.modelCUser.listaUsuarios().get(i).getApellidos();
            columna[3] = this.modelCUser.listaUsuarios().get(i).getContraseña();
            columna[4] = this.modelCUser.listaUsuarios().get(i).getTipoUsuario();
            modeloT.addRow(columna);
        }
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
    }

    @Override
    public void keyReleased(KeyEvent ke) {
         if(ke.getSource() == this.frmVentas.txtBuscarVenta){
            String nombre = this.frmVentas.txtBuscarVenta.getText();
            DefaultTableModel modeloT = new DefaultTableModel();
            this.frmVentas.tblVentas.setModel(modeloT);
             modeloT.addColumn("idCliente");
             modeloT.addColumn("Nombre");
             modeloT.addColumn("Apellidos");
             modeloT.addColumn("Categoria");
             modeloT.addColumn("Cantidad");
             modeloT.addColumn("Producto");
             Object[] columna = new Object[6];
             int numRegistro = this.modeloCVentas.BuscarporNombre(nombre).size();
             for (int i = 0; i < numRegistro; i++) {
                 columna[0] = this.modeloCVentas.BuscarporNombre(nombre).get(i).getIdVenta();
                 columna[1] = this.modeloCVentas.BuscarporNombre(nombre).get(i).getNombre();
                 columna[2] = this.modeloCVentas.BuscarporNombre(nombre).get(i).getApellidos();
                 columna[3] = this.modeloCVentas.BuscarporNombre(nombre).get(i).getCategoria();
                 columna[4] = this.modeloCVentas.BuscarporNombre(nombre).get(i).getCantidad();
                 columna[5] = this.modeloCVentas.BuscarporNombre(nombre).get(i).getProducto();
                 modeloT.addRow(columna);
             }
        }
          if (ke.getSource() == this.frmUser.txtBuscarUsuario) {
            String nombre = this.frmUser.txtBuscarUsuario.getText();
            DefaultTableModel modeloT = new DefaultTableModel();
            this.frmUser.tblUsuarios.setModel(modeloT);
             modeloT.addColumn("idUsuario");
        modeloT.addColumn("Nombre");
        modeloT.addColumn("Apellidos");
        modeloT.addColumn("Contraseña");
        modeloT.addColumn("tipoUsuario");
        Object[] columna = new Object[5];
        int numRegistro = this.modelCUser.BuscarporNombre(nombre).size();
        for (int i = 0; i < numRegistro; i++) {
            columna[0] = this.modelCUser.BuscarporNombre(nombre).get(i).getIdUsuarios();
            columna[1] = this.modelCUser.BuscarporNombre(nombre).get(i).getNombre();
            columna[2] = this.modelCUser.BuscarporNombre(nombre).get(i).getApellidos();
            columna[3] = this.modelCUser.BuscarporNombre(nombre).get(i).getContraseña();
            columna[4] = this.modelCUser.BuscarporNombre(nombre).get(i).getTipoUsuario();
            modeloT.addRow(columna);
            }
        }
            
    }

    @Override
    public void mouseClicked(MouseEvent me) {
         if(me.getSource() == this.frmVentas.tblVentas){
             int seleccion =this.frmVentas.tblVentas.getSelectedRow();
             this.frmVentas.txtIdVenta.setText(String.valueOf(this.frmVentas.tblVentas.getValueAt(seleccion,0)));
            this.frmVentas.txtNombreVenta.setText(String.valueOf(this.frmVentas.tblVentas.getValueAt(seleccion,1)));
            this.frmVentas.txtApellidos.setText(String.valueOf(this.frmVentas.tblVentas.getValueAt(seleccion,2)));
            this.frmVentas.txtCategoria.setText(String.valueOf(this.frmVentas.tblVentas.getValueAt(seleccion,3)));
            this.frmVentas.txtCantidad.setText(String.valueOf(this.frmVentas.tblVentas.getValueAt(seleccion,4)));
            this.frmVentas.txtProducto.setText(String.valueOf(this.frmVentas.tblVentas.getValueAt(seleccion,5)));

      }
         if (me.getSource() == this.frmUser.tblUsuarios) {
            int seleccion = this.frmUser.tblUsuarios.getSelectedRow();
            this.frmUser.txtIdUsuario.setText(String.valueOf(this.frmUser.tblUsuarios.getValueAt(seleccion, 0)));
            this.frmUser.txtNombreUsuario.setText(String.valueOf(this.frmUser.tblUsuarios.getValueAt(seleccion, 1)));
            this.frmUser.txtApellidosUsuarios.setText(String.valueOf(this.frmUser.tblUsuarios.getValueAt(seleccion, 2)));
            this.frmUser.txtContraseña.setText(String.valueOf(this.frmUser.tblUsuarios.getValueAt(seleccion, 3)));
            this.frmUser.txtRol.setText(String.valueOf(this.frmUser.tblUsuarios.getValueAt(seleccion, 4)));

        }
    }

    @Override
    public void mousePressed(MouseEvent me) {
    }

    @Override
    public void mouseReleased(MouseEvent me) {
    }

    @Override
    public void mouseEntered(MouseEvent me) {
    }

    @Override
    public void mouseExited(MouseEvent me) {
    }
    
}

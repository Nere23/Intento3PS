/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package redcloud;

import Controlador.Controlador;
import Modelo.CVentas;
import Vistas.AltaVentas;
import Vistas.Login;

/**
 *
 * @author USER1
 */
public class REDCLOUD {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Login frmLogin = new Login();
        CVentas modeloCVentas = new CVentas();
        Controlador control = new Controlador( frmLogin,modeloCVentas);
        control.iniciar();
    }
    
}

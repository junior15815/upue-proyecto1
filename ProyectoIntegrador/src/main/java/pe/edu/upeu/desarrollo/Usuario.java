package pe.edu.upeu.desarrollo;
import java.io.Console;

import pe.edu.upeu.data.AppCrud;
import pe.edu.upeu.modelo.UsuarioJV;
import pe.edu.upeu.util.LeerArchivo;
import pe.edu.upeu.util.LeerTeclado;
import pe.edu.upeu.util.UtilsX;

public class Usuario extends AppCrud{
    LeerArchivo lar;
    UsuarioJV usuTO;

    LeerTeclado lte=new LeerTeclado();
    UtilsX ut=new UtilsX();

    public void registarUsuario() {
        usuTO=new UsuarioJV();
        String usuario=lte.leer("", "Ingrese un usuario:");
        if(validarExisteUser(usuario)){
            usuTO.setUsuario(usuario);        
            lar=new LeerArchivo("Usuario.txt");
            usuTO.setIdUsuario(generarId(lar, 0, "U", 1));
            usuTO.setPerfil(
            lte.leer("", "Ingrese el Perfil de usuariio (ADMIN, VENDEDOR, CAJERO): ").toUpperCase());        
            Console cons=System.console();
            System.out.println("Ingrese su clave:");
            char[] pws=cons.readPassword();
            usuTO.setClave(String.valueOf(pws));
            lar=new LeerArchivo("Usuario.txt");
            agregarContenido(lar, usuTO);
        }else{
            System.out.println("El usuario ya existe, coloque otro usuario");
            registarUsuario();
        }
    }

    public boolean login(String usuario, String clave) {
        lar=new LeerArchivo("Usuario.txt");
        Object[][] datax=buscarContenido(lar, 1, usuario);
        if(datax!=null && datax.length>0 && datax[0][2].equals(clave)){
            return true;
        }
        return false;
    }

    public boolean eliminarUsuario() {
        ut.clearConsole();
        lar=new LeerArchivo("Usuario.txt");
        imprimirLista(listarContenido(lar));
        lar=new LeerArchivo("Usuario.txt");
        String idU=lte.leer("", "Ingrese el Id del usuario que desea eliminar:");
        eliminarRegistros(lar, 0, idU);
        return false;
    }    

    public boolean validarExisteUser(String user) {
        lar=new LeerArchivo("Usuario.txt");
        Object[][] datax=buscarContenido(lar, 1, user);    
        if(datax==null || datax.length==0){
            return true;
        }
        return false;
    }
        
}
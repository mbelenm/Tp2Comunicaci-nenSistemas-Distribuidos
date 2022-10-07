import java.net.*;
import java.io.*;


//El objetivo de esta clase es representar a un cliente que se comunica con un 
//servidor mediante sockets TCP, conociendo su puerto y dirección. Se implementa un protocolo
//de petición y respuesta de 3 mensajes.
public class Cliente_TCP {
    
    public static void main(String[] args) throws Exception {
        //Componentes del proceso
        Socket socket;
        int tiempoDeEspera = 3000;
        PrintStream salida;
        BufferedReader reader;
        String mensajeRecibido = "";

        //Datos del servidor
        String hostServidor = "localhost";
        int puertoServidor = 1200;
        
        //Establece la conexión
        socket = new Socket(hostServidor, puertoServidor);
        salida = new PrintStream(socket.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        
        //Envía una petición y espera hasta tiempoDeEspera milisegundos.
        //Si no recibe respuesta por parte del servidor, lo vuelve a intentar.
        String mensajeAEnviar = "REQ";
        socket.setSoTimeout(tiempoDeEspera);
        while(mensajeRecibido.isEmpty()) {
            try {
                salida.println(mensajeAEnviar);
                mensajeRecibido = reader.readLine();
            }
            catch(SocketTimeoutException e) {
                System.out.println("No se han recibido datos por parte del servidor. Reintentando...");
            }
        }
        socket.setSoTimeout(0);
        
        //Envía un ACK, muestra los datos por pantalla y finaliza el proceso
        mensajeAEnviar = "ACK";
        salida.println(mensajeAEnviar);
        System.out.println(mensajeRecibido);
    }
}

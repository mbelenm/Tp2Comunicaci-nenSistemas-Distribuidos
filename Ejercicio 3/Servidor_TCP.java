import java.net.*;
import java.io.*;



//El objetivo de esta clase es representar a un servidor que atiende a un 
//cliente mediante sockets TCP en un puerto conocido. Se implementa un protocolo
//de petición y respuesta de 3 mensajes.
public class Servidor_TCP {
    
    public static void main(String[] args) throws Exception {
        //Componentes del proceso
        ServerSocket socketServidor = new ServerSocket(1200);
        int tiempoDeEspera = 3000;
        PrintStream salida;
        BufferedReader reader;
        String mensajeRecibido;
        Socket socketCliente = null;
        String mensajeAEnviar = "[Datos]";
        
        //Espera a recibir conexiones
        System.out.println("Esperando conexiones...");
        while(socketCliente == null) {
            socketCliente = socketServidor.accept();
        }
        System.out.println(socketCliente.getInetAddress().toString()+" se ha conectado");
        salida = new PrintStream(socketCliente.getOutputStream());
        reader = new BufferedReader(new InputStreamReader(socketCliente.getInputStream()));
        mensajeRecibido = reader.readLine();
        System.out.println(mensajeRecibido);
        
        //Envía los datos solicitados por el cliente y espera un ACK durante el tiempoDeEspera.
        //Si no lo recibe durante ese tiempo, avisa y lo vuelve a enviar
        while(!(mensajeRecibido.equals("ACK"))) {
            try {
                salida.println(mensajeAEnviar);
                mensajeRecibido = reader.readLine();
            }
            catch(SocketTimeoutException e) {
                System.out.println("No se han recibido datos por parte del servidor. Reintentando...");
            }
        }
        socketCliente.setSoTimeout(0);
        
        //Muestra el mensaje por pantalla y finaliza el proceso
        System.out.println(mensajeRecibido);
        socketServidor.close();
    }
}

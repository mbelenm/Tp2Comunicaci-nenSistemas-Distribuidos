import java.net.*;

//El objetivo de esta clase es enviar mensajes de texto mediante sockets UDP 
//conectados en un puerto y dirección conocidos
public class Enviador_UDP {
    
    public static void main(String[] args) throws Exception {
        //Componentes del proceso
        DatagramSocket socket = new DatagramSocket();
        String mensajeAEnviar = "[Aca enviamos el mensaje que le queremos dar al receptor]";
        
        //Datos del servidor
        int puertoServidor = 1200;
        InetAddress direccionServidor = InetAddress.getByName("localhost");
        
        //Envía el mensaje 
        DatagramPacket paqueteAEnviar = new DatagramPacket(mensajeAEnviar.getBytes(), mensajeAEnviar.length(), direccionServidor, puertoServidor);
        socket.send(paqueteAEnviar);
    }
}

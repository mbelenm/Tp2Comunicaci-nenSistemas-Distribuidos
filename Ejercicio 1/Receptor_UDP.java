import java.net.*;


//El objetivo de esta clase es recibir y mostrar pon pantalla mensajes de texto
//enviados mediante sockets UDP a un puerto y dirección conocidos
public class Receptor_UDP {
    
    public static void main(String[] args) throws Exception {
        //Componentes del proceso
        DatagramSocket socket = new DatagramSocket(1200);
        byte[] buffer = new byte[1024];
        DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
        
        //Espera a recibir un mensaje
        System.out.println("Esperando ");
        socket.receive(paqueteRecibido);
        
        //Muestra el mensaje por pantalla
        String mensajeRecibido = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
        System.out.println("Mensaje recibido de "+paqueteRecibido.getAddress().toString());
        System.out.println(mensajeRecibido);
    }
}

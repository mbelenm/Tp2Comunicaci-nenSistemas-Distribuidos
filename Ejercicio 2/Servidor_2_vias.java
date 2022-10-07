import java.net.*;


//El objetivo de esta clase es representar a un servidor que opera con un protocolo de
//petición y respuesta de 2 vías sobre un puerto fijo 
public class Servidor_2_vias {
    
    public static void main(String[] args) throws Exception {
        //Componentes del proceso
        DatagramSocket socket = new DatagramSocket(1200);
        byte[] buffer = new byte[1024];
        DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
        
        //Espera a recibir una petición
        System.out.println("Esperando peticiones");
        socket.receive(paqueteRecibido);
        String mensajeRecibido = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
        System.out.println(mensajeRecibido);
        
        //Envía los datos solicitados por el cliente
        String stringEnviar = "[Datos]";
        System.out.println("Respondiendo a "+paqueteRecibido.getAddress().toString());
        DatagramPacket paqueteAEnviar = new DatagramPacket(stringEnviar.getBytes(), stringEnviar.length(), paqueteRecibido.getAddress(), paqueteRecibido.getPort());
        socket.send(paqueteAEnviar);
    }
}

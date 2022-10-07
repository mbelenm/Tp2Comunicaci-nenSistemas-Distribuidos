import java.net.*;



//El objetivo de esta clase es representar a un servidor que opera con un protocolo de
//petición y respuesta de 4 vías sobre un puerto fijo utilizando sockets UDP
public class Servidor_4_vias {
    
    public static void main(String[] args) throws Exception {
        //Componentes del proceso
        DatagramSocket socket = new DatagramSocket(1200);
        int tiempoDeEspera = 3000; 
        byte[] buffer = new byte[1024];
        DatagramPacket paqueteRecibido = new DatagramPacket(buffer, buffer.length);
                
        //Espera a recibir una petición
        System.out.println("Esperando peticiones");
        socket.receive(paqueteRecibido);
        String mensajeRecibido = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
        System.out.println(mensajeRecibido);
        
        //Envía un ACK al cliente
        String stringEnviar = "ACK";
        DatagramPacket paqueteAEnviar = new DatagramPacket(stringEnviar.getBytes(), stringEnviar.length(), paqueteRecibido.getAddress(), paqueteRecibido.getPort());
        socket.send(paqueteAEnviar);
        
        //Envía los datos solicitados por el cliente y espera un ACK durante el tiempoDeEspera.
        //Si no lo recibe durante ese tiempo, avisa y lo vuelve a enviar
        System.out.println("Respondiendo a "+paqueteRecibido.getAddress().toString());
        stringEnviar = "[Datos]";
        paqueteAEnviar = new DatagramPacket(stringEnviar.getBytes(), stringEnviar.length(), paqueteRecibido.getAddress(), paqueteRecibido.getPort());
        socket.setSoTimeout(tiempoDeEspera);
        while(!(mensajeRecibido.equals("ACK"))) {
            try {
                
                socket.send(paqueteAEnviar);
                socket.receive(paqueteRecibido);
                mensajeRecibido = new String(paqueteRecibido.getData(), 0, paqueteRecibido.getLength());
            }
            catch (SocketTimeoutException e) {
                System.out.println("No se ha recibido ACK por parte del cliente. Reintentando...");
            }    
        }
        
    }
}

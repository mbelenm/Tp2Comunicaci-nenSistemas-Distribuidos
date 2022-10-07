import java.net.*;



//El objetivo de esta clase es representar a un cliente que opera con un protocolo de
//petición y respuesta de 4 vías sobre un servidor, conociendo su puerto y dirección
//mediante sockets UDP
public class Cliente_4_vias {
    
    public static void main(String[] args) throws Exception {
        //Componentes del proceso
        DatagramSocket socket = new DatagramSocket();
        int tiempoDeEspera = 3000;
        byte[] buffer = new byte[1024];
        DatagramPacket datosRecibidos = new DatagramPacket(buffer, buffer.length);
        
        //Datos del servidor
        InetAddress direccionServidor = InetAddress.getByName("localhost");
        int puertoServidor = 1200;
        
        //REQ
        String stringEnviar = "REQ";
        String mensaje = "Cliente 4";
        DatagramPacket paqueteAEnviar = new DatagramPacket(stringEnviar.getBytes(), stringEnviar.length(), direccionServidor, puertoServidor);
        System.out.println("Enviando petición al servidor");
        
        //Espera a recibir el ACK por parte del servidor durante el tiempoDeEspera. 
        //Si no lo recibe, avisa y vuelve a enviar la petición.
        socket.setSoTimeout(tiempoDeEspera);
        while(!(mensaje.equals("ACK"))) {
            try {
                socket.send(paqueteAEnviar);
                socket.receive(datosRecibidos);
                mensaje = new String(datosRecibidos.getData(), 0, datosRecibidos.getLength());
            }
            catch (SocketTimeoutException e) {
                System.out.println("No se ha recibido ACK por parte del servidor. Reintentando...");
            }    
        }
        socket.setSoTimeout(0);
        System.out.println(mensaje);
        
        //Espera a recibir los datos del servidor
        stringEnviar = "ACK";
        socket.receive(datosRecibidos);
        paqueteAEnviar = new DatagramPacket(stringEnviar.getBytes(), stringEnviar.length(), direccionServidor, puertoServidor);
        socket.send(paqueteAEnviar);
        mensaje = new String(datosRecibidos.getData(), 0, datosRecibidos.getLength());
        System.out.println(mensaje);
    }
}

package mx.uv.practica03;

import java.util.ArrayList;

import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import https.t4is_uv_mx.saludos.BuscarRequest;
import https.t4is_uv_mx.saludos.BuscarResponse;
import https.t4is_uv_mx.saludos.EditarRequest;
import https.t4is_uv_mx.saludos.EditarResponse;
import https.t4is_uv_mx.saludos.EliminarRequest;
import https.t4is_uv_mx.saludos.EliminarResponse;
import https.t4is_uv_mx.saludos.SaludarRequest;
import https.t4is_uv_mx.saludos.SaludarResponse;

@Endpoint
public class EndPoint {
    public static ArrayList<String> saludos = new ArrayList<>();
    @PayloadRoot(localPart = "SaludarRequest", namespace = "https://t4is.uv.mx/saludos")
    @ResponsePayload //La transforma como una carga util (Ejemplo POST)
    public SaludarResponse Saludar(@RequestPayload SaludarRequest peticion){
        SaludarResponse i = new SaludarResponse(); 
        i.setRespuesta("Hola " + peticion.getNombre());
        saludos.add(peticion.getNombre());
        return i;
    }

    @PayloadRoot(localPart = "BuscarRequest", namespace = "https://t4is.uv.mx/saludos")
    @ResponsePayload
    public BuscarResponse BuscarSaludo(@RequestPayload BuscarRequest peticion){
        BuscarResponse s = new BuscarResponse();
        String respuesta;
        int ind = peticion.getSaludobuscar();
        //System.out.println(ind);
        if(ind > saludos.size()){
            respuesta = "No encontrado";
        }else{
            respuesta = saludos.get(ind);
        }
        //System.out.println(respuesta);
        s.setSaludobuscado(respuesta);
        return s;
    }

    @PayloadRoot(localPart = "EditarRequest", namespace = "https://t4is.uv.mx/saludos")
    @ResponsePayload
    public EditarResponse EditarSaludo(@RequestPayload EditarRequest peticion){
        EditarResponse a = new EditarResponse();
        int ind = peticion.getIndexeditar();
        String nombre = peticion.getSaludoeditar();
        String respuesta;
        if( ind <= saludos.size()){
            respuesta = "Se ha modificado el saludo";
            String nombreAnterior = saludos.get(ind);
            saludos.set(ind, nombre);
            a.setAviso(respuesta);
            a.setSaludoeditado(nombreAnterior);
            a.setSaludonuevo(nombre);
        }else{
            respuesta = "No se ha encontrado ese elemento";
            a.setAviso(respuesta);
            a.setSaludoeditado("No encontrado");
            a.setSaludonuevo("No se ha cambiado nada");
        }
        return a;
    }

    @PayloadRoot(localPart = "EliminarRequest", namespace = "https://t4is.uv.mx/saludos")
    @ResponsePayload
    public EliminarResponse EliminarSaludo(@RequestPayload EliminarRequest peticion){
        EliminarResponse c = new EliminarResponse();
        int ind = peticion.getIndex();
        String respuesta;
        String aviso;
        if ( ind > saludos.size()){
            aviso = "No existe ese elemento";
            respuesta = "Sin Cambios";
        }else{
            aviso = "Se ha eliminado un elemento";
            respuesta = saludos.get(ind);
            saludos.remove(ind);
        }
        c.setAviso(aviso);
        c.setNombre(respuesta);
        return c;
    }
}

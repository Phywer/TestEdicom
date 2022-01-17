package cliente;

import es.uji.www.GeneradorDatosINE;
import factura.Factura;
import tarifa.Tarifa;
import gestores.GestorCliente;
import llamada.Llamada;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.Assert.*;

public class GestorClienteTest {

    GestorCliente gestorcliente;

    Cliente nuevo;
    Cliente nuevo2;

    Llamada llamada;
    Llamada llamada2;

    Factura factura;
    Factura factura2;


   @Before public void initialize() throws Exception{

       GeneradorDatosINE datos = new GeneradorDatosINE();
       GeneradorDatosINE datos2 = new GeneradorDatosINE();
       gestorcliente = new GestorCliente();


       String nombre = datos.getNombre();
       String nif = datos.getNIF();
       String correo = nombre + "@gmail.com";

       String nombre2 = datos2.getNombre();
       String apellidos = datos2.getApellido();
       String nif2 = datos2.getNIF();
       String correo2 = nombre2 + "@gmail.com";

       int céntimos = 100;
       int céntimos2 = 200;

       Tarifa tarifa = new Tarifa(céntimos);
       Tarifa tarifa2 = new Tarifa(céntimos);


       int códigoPostal = 12004;
       String provincia = datos.getProvincia();
       String población = datos.getPoblacion(provincia);

       int códigoPostal2 = 12006;
       String provincia2 = datos2.getProvincia();
       String población2 = datos2.getPoblacion(provincia);

       Dirección dirección = new Dirección(códigoPostal, provincia, población);
       Dirección dirección2 = new Dirección(códigoPostal2, provincia2, población2);

       nuevo = new Empresa(nombre, nif, correo, tarifa, dirección);
       gestorcliente.darAlta(nuevo);

       nuevo2 = new Particular(nombre2, apellidos, nif2, correo2, tarifa2, dirección2);
       gestorcliente.darAlta(nuevo2);

       llamada = new Llamada(657191557, 300);
       llamada2 = new Llamada(964247357, 100);
       gestorcliente.altaLLamada(nuevo.getNif(), llamada);
       gestorcliente.altaLLamada(nuevo2.getNif(), llamada2);


       LocalDate fechaInicio = LocalDate.now();
       LocalDate fechaInicio2 = LocalDate.now();
       LocalDate fechaFin = LocalDate.now();
       LocalDate fechaFin2 = LocalDate.now();
       factura = new Factura("365", 107.27f, tarifa, fechaInicio, fechaFin);
       factura2 = new Factura("27", 3.7f, tarifa2, fechaInicio2, fechaFin2);
       gestorcliente.emitirFactura(nuevo, factura);
       gestorcliente.emitirFactura(nuevo2, factura2);

   }


    @Test
    public void darAlta() {


           assertEquals(true, gestorcliente.contieneCliente(nuevo));
           assertEquals(true, gestorcliente.contieneCliente(nuevo2));

    }

    @Test
    public void darBaja() {

        gestorcliente.darBaja(nuevo.getNif());
        assertEquals(false, gestorcliente.contieneCliente(nuevo));

        gestorcliente.darBaja(nuevo2.getNif());
        assertEquals(false, gestorcliente.contieneCliente(nuevo2));

    }

    @Test
    public void cambiarTarifa() {

       Tarifa tarifa = new Tarifa(300);
       nuevo.setTarifa(tarifa);
       assertEquals(tarifa, nuevo.getTarifa());

       Tarifa tarifa2  = new Tarifa(100);
       nuevo2.setTarifa(tarifa2);
        assertEquals(tarifa2, nuevo2.getTarifa());

    }

    @Test
    public void recuperarDatos() {

        assertEquals(nuevo, gestorcliente.recuperarDatos(nuevo.getNif()));
        assertEquals(nuevo2, gestorcliente.recuperarDatos(nuevo2.getNif()));


    }

    @Test
    public void recuperarListado() {

        HashMap<String, Cliente> lista = new HashMap<>();
        lista.put(nuevo.getNif(), nuevo);
        lista.put(nuevo2.getNif(), nuevo2);

        assertEquals(lista, gestorcliente.recuperarListado());

    }

    @Test
    public void altaLLamada() {

        assertEquals(true, gestorcliente.contieneLlamada(llamada, nuevo));
        assertEquals(true, gestorcliente.contieneLlamada(llamada2, nuevo2));

    }

    @Test
    public void listarLlamadas() {

       List<Llamada> lista = new ArrayList<Llamada>();
       lista.add(llamada);
        assertEquals(lista, gestorcliente.listarLlamadas(nuevo.getNif()));

        List<Llamada> lista2 = new ArrayList<Llamada>();
        lista2.add(llamada2);
        assertEquals(lista2, gestorcliente.listarLlamadas(nuevo2.getNif()));

    }

    @Test
    public void emitirFactura() {

        assertEquals(true, gestorcliente.contieneFactura(factura, nuevo));
        assertEquals(true, gestorcliente.contieneFactura(factura2, nuevo2));

    }

    @Test
    public void recuperarDatosFacturas() {

       assertEquals(factura, gestorcliente.recuperarDatosFacturas(factura.getCógido()));
        assertEquals(factura2, gestorcliente.recuperarDatosFacturas(factura2.getCógido()));

    }

    @Test
    public void listaFacturas() {

       List<Factura> lista = new ArrayList<Factura>();
       lista.add(factura);
       assertEquals(lista, gestorcliente.listaFacturas(nuevo.getNif()));

        List<Factura> lista2 = new ArrayList<Factura>();
        lista2.add(factura2);
        assertEquals(lista2, gestorcliente.listaFacturas(nuevo2.getNif()));

    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.randyoscal.reportes;

import java.io.InputStream;
import java.util.Map;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;
import org.randyoscal.DB.Conexion;

/**
 *
 * @author informatica
 */
public class GenerarReportes{
    public static void mostrarRepsorters(String nombreReporte, String titulo, Map parametro){
    InputStream reporte = GenerarReportes.class.getResourceAsStream(nombreReporte);
        try{            
            JasperReport reporteMaestro = (JasperReport)JRLoader.loadObject(reporte);
            JasperPrint reporteImpreso = JasperFillManager.fillReport(reporteMaestro, parametro, Conexion.getInstance().getConexion());
            JasperViewer visor = new JasperViewer(reporteImpreso, false);
            visor.setTitle(titulo);
            visor.setVisible(true);
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
}

/*
HashMap es uno de los objetos que implementa un conjunto de key-value.
Tinene un constructor sin parametros new HashMap() y su finalidad suele
referirse para agrupar informacion en un unico objeto.

Tienen sierta referencia con la colerccion de objetos ArrayList pero con la diferencia que estos
no tienen orden.

Has hace referencia a una tecnica de organizacion de archiuvos en hasing (abierto-cerrado) en la que 
se almacena el registro de una direccion que es generada por una funcion se aplica a la llave
del registro dentro de memoria fisica
*/

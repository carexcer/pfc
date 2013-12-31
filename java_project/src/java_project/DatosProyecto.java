package java_project;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.thoughtworks.xstream.XStream;

public class DatosProyecto {

	
	 /**Escribe en un fichero XML de nombre nomfich los datos del proyecto,
     * @param datos: Es un ArrayList 
     * @param nomfich: ruta absoluta del fichero
	 * @throws FileNotFoundException */
    public void escribirDatosXML(ArrayList datos, String nomfich) throws FileNotFoundException{

        FileOutputStream fileOutput = null;        

            if(nomfich!=null)
                fileOutput = new FileOutputStream(nomfich);
            else
                fileOutput = new FileOutputStream("./defaultXML.xml");

        BufferedOutputStream bufferedOutput = new BufferedOutputStream(fileOutput);
        XStream xml = new XStream();

        if(datos==null)
            xml.toXML(datos, bufferedOutput);
        else
            xml.toXML(datos, bufferedOutput);

    }

    public ArrayList leerDatosXML(String nomfich) throws IOException{

    	ArrayList lista = new ArrayList();
        FileInputStream fileInput = null;

            if(nomfich!=null)
                fileInput = new FileInputStream(nomfich);
                //fileInput = new FileInputStream("./" + nomfich + ".xml");
            else
                fileInput = new FileInputStream("./defaultXML.xml");

            BufferedInputStream bufferedInput = new BufferedInputStream(fileInput);

            XStream xml = new XStream();
            lista = (ArrayList) xml.fromXML(bufferedInput);
            
            fileInput.close();

        return lista;
    }
	
}

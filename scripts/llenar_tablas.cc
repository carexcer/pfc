#include <iostream>
#include <fstream>
#include <cstdlib>
#include <vector>
#include <cstring>

#define CANTIDAD_DEFAULT 100

using namespace std;

class producto{
    
    int id_producto;
    string nombre;
    string descripcion;

    public:
    
    producto(int id_producto, string nombre, string descripcion){
        this->id_producto=id_producto;
        this->nombre=nombre;
        this->descripcion=descripcion;       
    }
    producto(){
        this->id_producto=999;
        this->nombre="DEFAULT";
        this->descripcion="DEFAULT";       
    }
    ~producto(){
        //this->id_producto=NULL;
        //this->nombre=NULL;
        //this->descripcion=NULL;       
    }

    void print(){
        cout << "ID: " << id_producto << endl;
        cout << "NOMBRE: " << nombre << endl;
        cout << "DESCRIPCION: " << descripcion << endl;
    }
    void set_producto(int id,string nombre,string descripcion){
		this->id_producto=id;
		this->nombre=nombre;
		this->descripcion=descripcion;
	}
	producto get_producto(){
		producto prod;
		prod.id_producto=this->id_producto;
		prod.nombre=this->nombre;
		prod.descripcion=this->descripcion;
		return prod;
	}
};



int main(){

cout << "Pequeño script para llenar tablas SQL" << endl;

while(true){
	cout << "======== MENU PRINCIPAL =======" << endl;
	cout << "1 - LLENAR TABLA PRODUCTO" << endl;
	cout << "2 - LLENAR TABLA MARCA" << endl;
	cout << "3 - LLENAR TABLA PROVEEDOR" << endl;
	cout << "0 - SALIR" << endl;

	int seleccion;
	cin >> seleccion;

	if(seleccion==0) break;

    switch(seleccion){
        case 1://PRODUCTOS
        {
			cout << "¿Cuantos productos quieres insertar?" << endl;
			int cantidad_usuario=CANTIDAD_DEFAULT;
			cin >> cantidad_usuario;
            cout << "Generando " << CANTIDAD_DEFAULT << " productos..." << endl;

            int cont=0;
// Leemos el fichero con los nombres de los productos
            ifstream fent;
            fent.open("./datos/nomprod.txt");
            if(fent==NULL){
                cout << "Error abriendo el fichero de entrada" << endl;
                break;
            }                      

	    vector<producto> vprod;
	    int num_prod=0;
            while(!fent.eof()){
				producto prod;
				string linea;
                getline(fent,linea);
                char *pch =strtok(linea,";");
                vector<string> fields;
                int i=0;
                while(pch != NULL){
					fields[i]=pch;
					i++;
				}
                prod.set_producto(NULL,fields[0],fields[1]);
                cout << "Productos insertados" << endl;
                prod.print();
                vprod.push_back(prod);
                num_prod++;
            }
            fent.close();

// Escribimos el fichero con los insert de productos
            ofstream fsal;
            fsal.open("./sql/llenado_productos.sql");
            if(fsal==NULL){
                cout << "Error creando el fichero de salida" << endl;
                break;
            }                
            srand(time(NULL));
            
            while(cont<cantidad_usuario){
                fsal << "INSERT INTO PRODUCTO(id_producto, nombre, descripcion) VALUES (" << cont << ", '" 
                            << nomprod[random()%num_prod] << "');" << endl;
                cont++;
            }
        
            fsal.close();
            cout << "Productos generados." << endl;
            break;
        }
        case 2://MARCAS
        {
			ifstream fent;
			cout << "Abriendo fichero marcas.csv..." << endl;
            fent.open("./datos/marcas.csv");
            if(fent==NULL){
                cout << "Error abriendo el fichero de entrada" << endl;
                break;
            }     
            fent.close();
     	   
			ofstream fsal;
			fsal.open("./sql/llenado_marcas.sql");
			if(fsal==NULL){
                cout << "Error creando el fichero de salida" << endl;
                break;
            }            
            cout << "Generando " << CANTIDAD_DEFAULT << " marcas..." << endl;    
            
            srand(time(NULL));
            
            while(cont<CANTIDAD_DEFAULT){
                fsal << "INSERT INTO MARCA(ID_MARCA, NOMBRE_COMERCIAL, NOMBRE_COMPLETO, DESCRIPCION) VALUES (" 
							<< cont << ",'" 
                            << nomprod[random()%num_prod] << "',' " 
                            << nombre_comercial[random()%num_marcas] << "','"
                            << 
                            << "');" << endl;
                cont++;
            }
        
            fsal.close();
			
            cout << "Marcas generadas." << endl;
            break;    
        }
        case 3://PROVEEDORES
        {
            cout << "Generando proveedores..." << endl;
            
            cout << "Proveedores generados." << endl;
            break;
        }
        default: "Opcion incorrecta";
    
    }
}

return 0;

}

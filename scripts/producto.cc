#include <iostream>
#include <cstring>

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
    ~producto(){
        this->id_producto=NULL;
        this->nombre=NULL;
        this->descripcion=NULL;       
    }

    void print(){
        cout << "ID: " << id_producto << endl;
        cout << "NOMBRE: " << nombre << endl;
        cout << "DESCRIPCION: " << descripcion << endl;
    }
};

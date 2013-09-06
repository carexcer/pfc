#include <iostream>
#include <cstdlib>
#include <cstring>
#include <cmath>
#include <ctime>

#define pi 3.1415926535897932384626433832795028841971693993751058209749445923078164062862089986280348253421170679821480865
using namespace std;

int main(){

	int num=0;
	cout << "¿Cuantos productos quieres generar? " << endl;
	cin >> num;
	cout << "Generando datos para " << num << " productos..." << endl;
	for(int i=0; i<num; i++){
		double semilla = clock();
		
		for(int cont_espera=0; cont_espera < 1500; cont_espera++){
				cont_espera++;
				cont_espera--;
		}
		semilla = semilla / 13;
		srand(semilla);	
		if(i%10 == 0){
			cout<< endl;
		}
		cout << rand()%500 << " - ";			
	}
	cout << endl << "Generados " << num << " numeros aleatorios." << endl;

	return 0;
}
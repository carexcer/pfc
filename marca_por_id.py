import pickle;

fent = open("/home/carlos/pfc/pfc/csv/marcas_ordenadas.txt","r");
x=fent.read().split("\n");
lista = [];
numeros = range(1,253);
for i in x:
	lista.append(x);
m = map(numeros,lista);
for item in m:
	print(item)
fent.close();

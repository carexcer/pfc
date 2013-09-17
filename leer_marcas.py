import pickle;

fent = open("nombres_marcas.txt","r");

x=fent.read().split("\n");
marcas = set([]);
for item in x:   
    marcas.add(item);
for m in marcas:
    print(m)

#for line in fent:
#	lista = fent.read().split(";");
#	for item in lista:
#		print(item)
fent.close();



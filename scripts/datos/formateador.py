import pickle;

fent = open("probando2.csv","r");
fsal = open("productos.csv", "w");

x=fent.read().split(";");
for item in x:
	fsal.write('\n'+item)

#for line in fent:
#	lista = fent.read().split(";");
#	for item in lista:
#		print(item)
fent.close();
fsal.close();


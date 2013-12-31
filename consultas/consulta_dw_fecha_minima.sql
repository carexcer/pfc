SELECT p.clave_producto, f.clave_fecha, a.clave_almacen, taux1.min_fecha_valor
FROM  producto p, fecha f, almacen a,
		(SELECT MIN(f1.fecha_valor) min_fecha_valor 
				from fecha f1 where f1.fecha_valor > '1901-01-01') as taux1
WHERE taux1.min_fecha_valor = f.fecha_valor
order by p.clave_producto
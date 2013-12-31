select 	tabla_1.id_producto, 
		tabla_1.fecha_movimiento, 
		tabla_2.id_almacen, 
		sum(tabla_1.cantidad) cantidad_vendida 

from (select u.id_ubicacion, u.id_almacen, a.nombre_almacen 
		from almacen a, ubicacion u
		where u.id_almacen = a.id_almacen) as tabla_2,

	(select m.id_producto, m.id_ubicacion, m.fecha_movimiento, m.E_S, sum(m.cantidad) cantidad 
		from movimiento m 
		where m.E_S='S' and m.fecha_movimiento between '${fecha_desde}' and '${fecha_hasta}'
		group by m.id_producto, m.id_ubicacion, m.fecha_movimiento, m.E_S) as tabla_1

where tabla_1.id_ubicacion = tabla_2.id_ubicacion

group by 	tabla_1.id_producto, 
			tabla_1.fecha_movimiento, 
			tabla_2.id_almacen

order by id_producto asc
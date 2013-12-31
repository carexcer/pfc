select tabla_entradas.cantidad_recibida, tabla_salidas.cantidad_vendida, (tabla_entradas.cantidad_recibida - tabla_salidas.cantidad_vendida) 
from 
	(select tabla_1.id_producto, tabla_1.fecha_movimiento, tabla_2.id_almacen, tabla_2.nombre_almacen, sum(tabla_1.cantidad) cantidad_recibida 
	from (select u.id_ubicacion, u.id_almacen, a.nombre_almacen 
		from almacen a, ubicacion u
		where u.id_almacen = a.id_almacen) as tabla_2,
	(select m.id_producto, m.id_ubicacion, m.fecha_movimiento, m.E_S, sum(m.cantidad) cantidad, count(*) 
		from movimiento m 
		where m.E_S='E' and m.fecha_movimiento between '01-01-2011' and '10-01-2012'
		group by m.id_producto, m.id_ubicacion, m.fecha_movimiento, m.E_S) as tabla_1
	where tabla_1.id_ubicacion = tabla_2.id_ubicacion
	group by tabla_1.id_producto, tabla_1.fecha_movimiento, tabla_2.id_almacen, tabla_2.nombre_almacen
	order by id_producto ) as tabla_entradas,
	
	(select tabla_1.id_producto, tabla_1.fecha_movimiento, tabla_2.id_almacen, tabla_2.nombre_almacen, sum(tabla_1.cantidad) cantidad_vendida 
	from (select u.id_ubicacion, u.id_almacen, a.nombre_almacen 
		from almacen a, ubicacion u
		where u.id_almacen = a.id_almacen) as tabla_2,
	(select m.id_producto, m.id_ubicacion, m.fecha_movimiento, m.E_S, sum(m.cantidad) cantidad, count(*) 
		from movimiento m 
		where m.E_S='S' and m.fecha_movimiento between '01-01-2011' and '10-01-2012'
		group by m.id_producto, m.id_ubicacion, m.fecha_movimiento, m.E_S) as tabla_1
	where tabla_1.id_ubicacion = tabla_2.id_ubicacion
	group by tabla_1.id_producto, tabla_1.fecha_movimiento, tabla_2.id_almacen, tabla_2.nombre_almacen
	order by id_producto ) as tabla_salidas
	
	
SELECT 		up.id_producto,
		a.id_almacen,
		sum(up.cantidad) cantidad_en_almacen,
		count(*) ubicaciones_distintas_mismo_almacen
FROM 	ubicacion u,
		ubicacion_producto up,
		almacen a
WHERE up.id_ubicacion=u.id_ubicacion 
		and u.id_almacen=a.id_almacen
		
GROUP BY	up.id_producto, 
		a.id_almacen
ORDER BY up.id_producto asc
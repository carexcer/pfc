SELECT p.id_producto,
		p.id_categoria,
		p.id_marca,
		m.id_ubicacion,
		sum(m.cantidad) cantidad
		
FROM producto p,
		movimiento m
WHERE p.id_producto = m.id_producto
		and m.fecha_movimiento between '01-01-2011' and '21-12-2013'	

GROUP BY p.id_producto,
		p.id_categoria,
		p.id_marca,
		m.id_ubicacion
ORDER BY p.id_producto asc
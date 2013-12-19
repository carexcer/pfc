SELECT p.id_producto,
		p.id_categoria,
		p.id_marca,
		v.fecha_venta,
		v.precio_venta_unitario,
		sum(v.cantidad_vendida) cantidad_vendida,
		(sum(v.cantidad_vendida) * v.precio_venta_unitario) ingresos_ventas
		
FROM producto p,
		venta v
WHERE p.id_producto = v.id_producto
		and v.fecha_venta between '01-01-2011' and '31-12-2013'	

GROUP BY p.id_producto,
		p.id_categoria,
		p.id_marca,
		v.fecha_venta,
		v.precio_venta_unitario
ORDER BY p.id_producto
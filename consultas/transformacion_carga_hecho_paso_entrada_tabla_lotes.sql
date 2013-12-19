SELECT p.id_producto,
		p.id_categoria,
		p.id_marca,
		lote.fecha_recepcion,
		lote.precio_compra_unitario,
		sum(lote.cantidad_recibida) cantidad_recibida,
		(sum(lote.cantidad_recibida) * lote.precio_compra_unitario) gastos_compras
		
FROM producto p,
		lote_recibido lote
WHERE p.id_producto = lote.id_producto
		and lote.fecha_recepcion between '01-01-2011' and '31-12-2013'	

GROUP BY p.id_producto,
		p.id_categoria,
		p.id_marca,
		lote.fecha_recepcion,
		lote.precio_compra_unitario
ORDER BY p.id_producto
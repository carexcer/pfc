select (total_vendida * (valor_ultima_venta - valor_compra))/ (media_stock * valor_ultima_venta),
total_vendida, valor_ultima_venta, valor_compra, media_stock, valor_ultima_venta FROM
(select sum(cantidad_vendida) as total_vendida from venta where id_producto = '59157') as t_cant_total,
(select precio_venta_unitario as valor_ultima_venta from venta 
	where id_producto = '59157' order by fecha_venta desc LIMIT 1) as t_valor_ultima_venta,
(select precio_medio_compra_unitario as valor_compra from producto where id_producto='59157') as t_valor_compra,
(select avg(cantidad_stock) as media_stock from producto where id_producto = '59157') as t_media_stock
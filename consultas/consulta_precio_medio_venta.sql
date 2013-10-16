select media_venta, media_producto from 
	(select avg(precio_venta_unitario) as media_venta from venta where id_producto='44443') as tventa,
	(select avg(precio_medio_venta_unitario) as media_producto from producto where id_producto='44443') as tprod
select media_compra, media_producto from 
	(select avg(precio_compra_unitario) as media_compra from lote_recibido where id_producto='44443') as tlote,
	(select avg(precio_medio_compra_unitario) as media_producto from producto where id_producto='44443') as tprod
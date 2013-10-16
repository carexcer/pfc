select recibida, vendida, recibida - vendida from
(select sum(cantidad_vendida) as vendida from venta 
	where fecha_venta < '30-10-2012' ) as tabla_venta,
(select sum(cantidad_recibida) as recibida from lote_recibido 
	where fecha_recepcion < '30-10-2012' ) as tabla_lote
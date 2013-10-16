select cant_recibida, cant_vendida, cant_recibida - cant_vendida from 
	(select sum(cantidad_recibida) as cant_recibida from lote_recibido 
				where id_producto='55552' and fecha_recepcion <= '30-6-2011') as tlote,
	(select sum(cantidad_vendida) as cant_vendida from venta 
				where id_producto='55552' and fecha_venta <= '30-6-2011') as tventa
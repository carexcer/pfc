select f.anyo4, a.nombre_almacen, p.nombre_categoria, sum(s.cantidad_vendida) vendida, sum(s.cantidad_recibida) recibida 
from snapshot s, fecha f, producto p, almacen a
where s.clave_producto=p.clave_producto and s.clave_almacen=a.clave_almacen and s.clave_fecha = f.clave_fecha
group by f.anyo4, a.nombre_almacen, p.nombre_categoria
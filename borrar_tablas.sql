TABLE public.categoria
  FOREIGN KEY public.categoria_producto_fk
 TABLE public.proveedor
  FOREIGN KEY public.proveedor_proveedor_producto_fk
 TABLE public.marca
  FOREIGN KEY public.marca_producto_fk
 TABLE public.almacen
  FOREIGN KEY public.almacen_ubicacion_fk
 TABLE public.ubicacion
  FOREIGN KEY public.almacen_ubicacion_fk
  FOREIGN KEY public.ubicacion_ubicacion_producto_fk
 TABLE public.producto
  FOREIGN KEY public.categoria_producto_fk
  FOREIGN KEY public.marca_producto_fk
  FOREIGN KEY public.producto_proveedor_producto_fk
  FOREIGN KEY public.producto_ubicacion_producto_fk
  FOREIGN KEY public.producto_venta_fk
 TABLE public.venta
  FOREIGN KEY public.producto_venta_fk
 TABLE public.ubicacion_producto
  FOREIGN KEY public.producto_ubicacion_producto_fk
  FOREIGN KEY public.ubicacion_ubicacion_producto_fk
 TABLE public.lote_recibido
  FOREIGN KEY public.producto_proveedor_producto_fk
  FOREIGN KEY public.proveedor_proveedor_producto_fk

DROP TABLE public.producto;
DROP TABLE public.categoria;
DROP TABLE public.marca;
DROP TABLE public.almacen;
DROP TABLE public.proveedor;
DROP TABLE public.lote_recibido;
DROP TABLE public.venta;
DROP TABLE public.ubicacion;
DROP TABLE public.ubicacion_producto;
DROP TABLE public.movimiento;
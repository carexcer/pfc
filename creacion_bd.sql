
CREATE TABLE public.Categoria (
                Id_Categoria INTEGER NOT NULL,
                Nombre_Categoria VARCHAR(60) NOT NULL,
                CONSTRAINT categoria_pk PRIMARY KEY (Id_Categoria)
);


CREATE TABLE public.Proveedor (
                Id_Proveedor INTEGER NOT NULL,
                NIF VARCHAR(9) NOT NULL,
                Nombre_Proveedor VARCHAR(128) NOT NULL,
                Telefono VARCHAR(30) NOT NULL,
                Email VARCHAR(50),
                Contacto VARCHAR(50),
                Direccion VARCHAR(256) NOT NULL,
                Provincia VARCHAR(50) NOT NULL,
                CONSTRAINT proveedor_pk PRIMARY KEY (Id_Proveedor)
);


CREATE TABLE public.Marca (
                Id_Marca INTEGER NOT NULL,
                Nombre_Marca VARCHAR(50) NOT NULL,
                CONSTRAINT marca_pk PRIMARY KEY (Id_Marca)
);


CREATE INDEX nombre_marca_idx
 ON public.Marca USING BTREE
 ( Nombre_Marca );

CREATE INDEX marca_idx1
 ON public.Marca USING BTREE
 ( Id_Marca );

CREATE TABLE public.Almacen (
                Id_Almacen INTEGER NOT NULL,
                Nombre_Almacen VARCHAR(50),
                Ciudad VARCHAR(128),
                Direccion VARCHAR(256) NOT NULL,
                Detalles VARCHAR(256),
                CONSTRAINT almacen_pk PRIMARY KEY (Id_Almacen)
);


CREATE TABLE public.Ubicacion (
                Id_Ubicacion INTEGER NOT NULL,
                Id_Almacen INTEGER NOT NULL,
                Zona VARCHAR(10),
                Estante INTEGER,
                CONSTRAINT ubicacion_pk PRIMARY KEY (Id_Ubicacion)
);


CREATE TABLE public.Producto (
                Id_Producto INTEGER NOT NULL,
                Id_Categoria INTEGER NOT NULL,
                Id_Marca INTEGER NOT NULL,
                Nombre_Producto VARCHAR(128) NOT NULL,
                Precio_medio_compra_unitario REAL,
                Precio_medio_venta_unitario REAL NOT NULL,
                Stock VARCHAR(2) NOT NULL,
                Cantidad_stock INTEGER,
                Peso REAL,
                CONSTRAINT producto_pk PRIMARY KEY (Id_Producto)
);


CREATE TABLE public.Venta (
                Id_Venta INTEGER NOT NULL,
                Id_Producto INTEGER NOT NULL,
                Cantidad_vendida INTEGER NOT NULL,
                Precio_venta_unitario REAL NOT NULL,
                Fecha_venta DATE NOT NULL,
                CONSTRAINT venta_pk PRIMARY KEY (Id_Venta)
);


CREATE INDEX fecha_venta_idx
 ON public.Venta
 ( Fecha_venta DESC );

CREATE TABLE public.Ubicacion_producto (
                Id_Ubicacion INTEGER NOT NULL,
                Id_Producto INTEGER NOT NULL,
                Cantidad INTEGER NOT NULL,
                CONSTRAINT ubicacion_producto_pk PRIMARY KEY (Id_Ubicacion, Id_Producto)
);


CREATE TABLE public.Lote_recibido (
                Id_Lote_Recibido INTEGER NOT NULL,
                Id_Proveedor INTEGER NOT NULL,
                Id_Producto INTEGER NOT NULL,
                Fecha_recepcion DATE NOT NULL,
                Cantidad_recibida INTEGER NOT NULL,
                Precio_compra_unitario REAL NOT NULL,
                CONSTRAINT lote_recibido_pk PRIMARY KEY (Id_Lote_Recibido)
);


CREATE TABLE public.Movimiento (
                Id_Movimiento INTEGER NOT NULL,
                Id_Producto INTEGER NOT NULL,
                Id_Ubicacion INTEGER NOT NULL,
                Id_Lote_Recibido INTEGER NOT NULL,
                Id_Venta INTEGER NOT NULL,
                E_S VARCHAR(1),
                Cantidad INTEGER,
                Fecha_movimiento DATE,
                CONSTRAINT movimiento_pk PRIMARY KEY (Id_Movimiento)
);


ALTER TABLE public.Producto ADD CONSTRAINT categoria_producto_fk
FOREIGN KEY (Id_Categoria)
REFERENCES public.Categoria (Id_Categoria)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.Lote_recibido ADD CONSTRAINT proveedor_proveedor_producto_fk
FOREIGN KEY (Id_Proveedor)
REFERENCES public.Proveedor (Id_Proveedor)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.Producto ADD CONSTRAINT marca_producto_fk
FOREIGN KEY (Id_Marca)
REFERENCES public.Marca (Id_Marca)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.Ubicacion ADD CONSTRAINT almacen_ubicacion_fk
FOREIGN KEY (Id_Almacen)
REFERENCES public.Almacen (Id_Almacen)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.Ubicacion_producto ADD CONSTRAINT ubicacion_ubicacion_producto_fk
FOREIGN KEY (Id_Ubicacion)
REFERENCES public.Ubicacion (Id_Ubicacion)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.Lote_recibido ADD CONSTRAINT producto_proveedor_producto_fk
FOREIGN KEY (Id_Producto)
REFERENCES public.Producto (Id_Producto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.Ubicacion_producto ADD CONSTRAINT producto_ubicacion_producto_fk
FOREIGN KEY (Id_Producto)
REFERENCES public.Producto (Id_Producto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.Venta ADD CONSTRAINT producto_venta_fk
FOREIGN KEY (Id_Producto)
REFERENCES public.Producto (Id_Producto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.Movimiento ADD CONSTRAINT venta_movimiento_fk
FOREIGN KEY (Id_Venta)
REFERENCES public.Venta (Id_Venta)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.Movimiento ADD CONSTRAINT ubicacion_producto_movimiento_fk
FOREIGN KEY (Id_Ubicacion, Id_Producto)
REFERENCES public.Ubicacion_producto (Id_Ubicacion, Id_Producto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;

ALTER TABLE public.Movimiento ADD CONSTRAINT lote_recibido_movimiento_fk
FOREIGN KEY (Id_Lote_Recibido)
REFERENCES public.Lote_recibido (Id_Lote_Recibido)
ON DELETE NO ACTION
ON UPDATE NO ACTION
DEFERRABLE INITIALLY DEFERRED;


CREATE TABLE public.Auditoria (
                clave_auditoria INTEGER NOT NULL,
                categoria_calidad_total VARCHAR(64) NOT NULL,
                puntuacion_calidad_total INTEGER NOT NULL,
                categoria_completitud VARCHAR(64) NOT NULL,
                puntuacion_completitud INTEGER NOT NULL,
                categoria_validacion VARCHAR NOT NULL,
                puntuacion_validacion INTEGER NOT NULL,
                categoria_anomalos VARCHAR(64) NOT NULL,
                puntuacion_anomalos INTEGER NOT NULL,
                numero_screens_fallidos INTEGER NOT NULL,
                maxima_puntuacion_severidad REAL NOT NULL,
                timestamp_extraccion TIMESTAMP NOT NULL,
                timestamp_limpieza TIMESTAMP NOT NULL,
                timestamp_ajuste TIMESTAMP NOT NULL,
                CONSTRAINT auditoria_pk PRIMARY KEY (clave_auditoria)
);
COMMENT ON COLUMN public.Auditoria.clave_auditoria IS 'Clave subrogada';


CREATE TABLE public.tabla_error (
                clave_tabla_error INTEGER NOT NULL,
                id_tabla_error INTEGER NOT NULL,
                nombre VARCHAR(32) NOT NULL,
                atributos_tabla VARCHAR(512) NOT NULL,
                CONSTRAINT tabla_error_pk PRIMARY KEY (clave_tabla_error)
);
COMMENT ON COLUMN public.tabla_error.clave_tabla_error IS 'Clave subrogada';
COMMENT ON COLUMN public.tabla_error.id_tabla_error IS 'Clave natural';


CREATE TABLE public.Screen (
                clave_screen VARCHAR NOT NULL,
                id_screen INTEGER NOT NULL,
                tipo VARCHAR(64) NOT NULL,
                categoria VARCHAR(64) NOT NULL,
                etapa_ETL VARCHAR(80) NOT NULL,
                orden_de_procesamiento INTEGER NOT NULL,
                nivel_de_severidad REAL NOT NULL,
                sentencia_SQL VARCHAR(512) NOT NULL,
                accion_a_ejecutar VARCHAR(64) NOT NULL,
                clave_tabla_error INTEGER NOT NULL,
                CONSTRAINT screen_pk PRIMARY KEY (clave_screen)
);
COMMENT ON COLUMN public.Screen.clave_screen IS 'Clave subrogada';
COMMENT ON COLUMN public.Screen.id_screen IS 'Clave natural';
COMMENT ON COLUMN public.Screen.categoria IS 'Para categorizar tipos de errores: Incorrecto, Ambiguo, Inconsistente, Incompleto.';
COMMENT ON COLUMN public.Screen.accion_a_ejecutar IS 'Acción a ejecutar cuando se produce el error: continuar, rechazar, abortar';
COMMENT ON COLUMN public.Screen.clave_tabla_error IS 'Clave subrogada';


CREATE TABLE public.Batch (
                clave_batch INTEGER NOT NULL,
                id_batch INTEGER NOT NULL,
                timestamp_batch TIMESTAMP NOT NULL,
                CONSTRAINT batch_pk PRIMARY KEY (clave_batch)
);
COMMENT ON COLUMN public.Batch.clave_batch IS 'Clave subrogada';
COMMENT ON COLUMN public.Batch.id_batch IS 'Clave natural';


CREATE TABLE public.Error_Date (
                clave_error_date INTEGER NOT NULL,
                fecha_evento DATE NOT NULL,
                dia_del_mes INTEGER NOT NULL,
                dia_de_la_semana INTEGER NOT NULL,
                mes_del_año INTEGER NOT NULL,
                año INTEGER NOT NULL,
                CONSTRAINT error_date_pk PRIMARY KEY (clave_error_date)
);
COMMENT ON COLUMN public.Error_Date.clave_error_date IS 'Clave subrogada';
COMMENT ON COLUMN public.Error_Date.fecha_evento IS 'Clave natural';


CREATE TABLE public.Error_Event (
                clave_error_date INTEGER NOT NULL,
                clave_batch INTEGER NOT NULL,
                clave_screen VARCHAR NOT NULL,
                hora_del_dia TIME NOT NULL,
                identificador_fila INTEGER NOT NULL,
                CONSTRAINT error_event_pk PRIMARY KEY (clave_error_date, clave_batch, clave_screen)
);
COMMENT ON TABLE public.Error_Event IS 'Error Event Table';
COMMENT ON COLUMN public.Error_Event.clave_error_date IS 'Clave subrogada';
COMMENT ON COLUMN public.Error_Event.clave_batch IS 'Clave subrogada';
COMMENT ON COLUMN public.Error_Event.clave_screen IS 'Clave subrogada';
COMMENT ON COLUMN public.Error_Event.identificador_fila IS 'Identifica la fila que ha provocado el error.';


CREATE TABLE public.Almacen (
                clave_almacen INTEGER NOT NULL,
                id_almacen INTEGER NOT NULL,
                nombre_almacen VARCHAR(10) NOT NULL,
                municipio VARCHAR(64) NOT NULL,
                codigo_postal VARCHAR(15) NOT NULL,
                provincia VARCHAR(50) NOT NULL,
                CONSTRAINT almacen_pk PRIMARY KEY (clave_almacen)
);
COMMENT ON COLUMN public.Almacen.clave_almacen IS 'Clave subrogada';
COMMENT ON COLUMN public.Almacen.id_almacen IS 'Clave natural';


CREATE TABLE public.Fecha (
                clave_fecha INTEGER NOT NULL,
                fecha_valor DATE NOT NULL,
                fecha_juliana INTEGER NOT NULL,
                fecha_corta VARCHAR(12) NOT NULL,
                fecha_media VARCHAR(16) NOT NULL,
                fecha_larga VARCHAR(24) NOT NULL,
                fecha_completa VARCHAR(32) NOT NULL,
                dia_de_semana INTEGER NOT NULL,
                dia_de_mes INTEGER NOT NULL,
                dia_de_anyo INTEGER NOT NULL,
                es_primer_dia_de_mes INTEGER NOT NULL,
                es_primer_dia_de_semana INTEGER NOT NULL,
                es_ultimo_dia_de_mes INTEGER NOT NULL,
                es_ultimo_dia_de_semana INTEGER NOT NULL,
                dia_nombre VARCHAR(9) NOT NULL,
                dia_abreviado VARCHAR(3) NOT NULL,
                semana_de_anyo INTEGER NOT NULL,
                semana_de_mes INTEGER NOT NULL,
                semana_de_anyo_iso INTEGER,
                es_fin_de_semana INTEGER NOT NULL,
                es_entresemana INTEGER NOT NULL,
                mes_numero INTEGER NOT NULL,
                mes_nombre VARCHAR(10) NOT NULL,
                mes_abreviado VARCHAR(3) NOT NULL,
                anyo2 VARCHAR(2) NOT NULL,
                anyo4 VARCHAR(4) NOT NULL,
                anyo2_iso VARCHAR(2) NOT NULL,
                anyo4_iso VARCHAR(4) NOT NULL,
                trimestre_numero INTEGER NOT NULL,
                trimestre_nombre VARCHAR(2) NOT NULL,
                anyo_trimestre VARCHAR(7) NOT NULL,
                anyo_mes VARCHAR(7) NOT NULL,
                anyo_semana VARCHAR(7) NOT NULL,
                anyo_semana_iso VARCHAR(7) NOT NULL,
                current_week_cy INTEGER,
                current_month_cy INTEGER,
                last_week_cy INTEGER,
                last_month_cy INTEGER,
                current_week_ly INTEGER,
                current_month_ly INTEGER,
                last_week_ly INTEGER,
                last_month_ly INTEGER,
                ytd_cy_day INTEGER,
                ytd_cy_week INTEGER,
                ytd_cy_month INTEGER,
                ytd_ly_day INTEGER,
                ytd_ly_week INTEGER,
                ytd_ly_month INTEGER,
                current_year INTEGER,
                last_year INTEGER,
                week_sequence INTEGER,
                month_sequence INTEGER,
                CONSTRAINT fecha_pk PRIMARY KEY (clave_fecha)
);
COMMENT ON COLUMN public.Fecha.clave_fecha IS 'Surrogate dimension key';


CREATE TABLE public.Producto (
                clave_producto INTEGER NOT NULL,
                id_producto INTEGER NOT NULL,
                nombre_producto VARCHAR(128) NOT NULL,
                id_categoria INTEGER NOT NULL,
                nombre_categoria VARCHAR(60) NOT NULL,
                id_marca INTEGER NOT NULL,
                nombre_marca VARCHAR(50) NOT NULL,
                es_primario BOOLEAN NOT NULL,
                peso_kg REAL NOT NULL,
                peso_gramos INTEGER NOT NULL,
                precio_medio_compra_unitario REAL NOT NULL,
                precio_medio_venta_unitario REAL NOT NULL,
                beneficio_medio_unitario REAL NOT NULL,
                factor_incremento_precio REAL NOT NULL,
                ratio_beneficio_peso_kg REAL NOT NULL,
                ratio_beneficio_peso_gramos REAL NOT NULL,
                CONSTRAINT producto_pk PRIMARY KEY (clave_producto)
);
COMMENT ON COLUMN public.Producto.clave_producto IS 'Clave subrogada';
COMMENT ON COLUMN public.Producto.id_producto IS 'Clave natural';
COMMENT ON COLUMN public.Producto.nombre_categoria IS 'Nombre de la categoria del producto.
Tamaño maximo original: 60';


CREATE UNIQUE INDEX categoria_idx
 ON public.Producto
 ( id_categoria );

CREATE TABLE public.Snapshot (
                clave_fecha INTEGER NOT NULL,
                clave_almacen INTEGER NOT NULL,
                clave_auditoria INTEGER NOT NULL,
                clave_producto INTEGER NOT NULL,
                cantidad_disponible INTEGER NOT NULL,
                cantidad_vendida INTEGER NOT NULL,
                valor_medio_de_compra REAL NOT NULL,
                valor_ultima_venta REAL NOT NULL,
                peso_total DOUBLE PRECISION NOT NULL,
                CONSTRAINT snapshot_pk PRIMARY KEY (clave_fecha, clave_almacen, clave_auditoria, clave_producto)
);


ALTER TABLE public.Snapshot ADD CONSTRAINT auditoria_snapshot_fk
FOREIGN KEY (clave_auditoria)
REFERENCES public.Auditoria (clave_auditoria)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Screen ADD CONSTRAINT tabla_error_screen_fk
FOREIGN KEY (clave_tabla_error)
REFERENCES public.tabla_error (clave_tabla_error)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Error_Event ADD CONSTRAINT screen_error_eent_fk
FOREIGN KEY (clave_screen)
REFERENCES public.Screen (clave_screen)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Error_Event ADD CONSTRAINT batch_error_eent_fk
FOREIGN KEY (clave_batch)
REFERENCES public.Batch (clave_batch)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Error_Event ADD CONSTRAINT error_date_error_eent_fk
FOREIGN KEY (clave_error_date)
REFERENCES public.Error_Date (clave_error_date)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Snapshot ADD CONSTRAINT almacen_snapshot_fk
FOREIGN KEY (clave_almacen)
REFERENCES public.Almacen (clave_almacen)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Snapshot ADD CONSTRAINT tiempo_snapshot_fk
FOREIGN KEY (clave_fecha)
REFERENCES public.Fecha (clave_fecha)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE public.Snapshot ADD CONSTRAINT producto_snapshot_fk
FOREIGN KEY (clave_producto)
REFERENCES public.Producto (clave_producto)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

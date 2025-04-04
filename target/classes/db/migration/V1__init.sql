CREATE OR REPLACE FUNCTION generar_num_transaccion_venta()
    RETURNS trigger AS $$
DECLARE
    caja_prefijo varchar;
    fecha_actual date := current_date;
    secuencia_actual integer;
    nuevo_secuencia integer;
BEGIN
    -- Ejecutar solo si el método de pago es 'efectivo'
    IF lower(NEW.metodo_pago) <> 'efectivo' THEN
        RETURN NEW;
    END IF;

    -- Obtener el prefijo de la caja desde la tabla caja
    SELECT prefijo INTO caja_prefijo
    FROM caja
    WHERE id_caja = NEW.id_caja;

    IF caja_prefijo IS NULL THEN
        RAISE EXCEPTION 'La caja con id % no existe o no tiene prefijo asignado.', NEW.id_caja;
    END IF;

    -- Buscar la secuencia actual para esta caja y fecha en la tabla caja_secuencia
    SELECT secuencia INTO secuencia_actual
    FROM caja_secuencia
    WHERE id_caja = NEW.id_caja AND fecha = fecha_actual
        FOR UPDATE;

    IF secuencia_actual IS NULL THEN
        nuevo_secuencia := 1;
        INSERT INTO caja_secuencia (id_caja, fecha, secuencia)
        VALUES (NEW.id_caja, fecha_actual, nuevo_secuencia);
    ELSE
        nuevo_secuencia := secuencia_actual + 1;
        UPDATE caja_secuencia
        SET secuencia = nuevo_secuencia
        WHERE id_caja = NEW.id_caja AND fecha = fecha_actual;
    END IF;

    -- Generar el número de transacción con el formato: [PREFIJO]-[FECHA AAAAMMDD]-[ID_CAJA]-[SECUENCIA]
    NEW.num_transaccion := caja_prefijo || '-' ||
                           to_char(fecha_actual, 'YYYYMMDD') || '-' ||
                           lpad(NEW.id_caja::text, 2, '0') || '-' ||
                           lpad(nuevo_secuencia::text, 4, '0');
    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS trg_generar_num_transaccion_venta ON pago;

CREATE TRIGGER trg_generar_num_transaccion_venta
    BEFORE INSERT ON venta
    FOR EACH ROW
EXECUTE FUNCTION generar_num_transaccion_venta();

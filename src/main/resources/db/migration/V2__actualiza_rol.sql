ALTER TABLE usuario DROP CONSTRAINT usuario_role_check;
ALTER TABLE usuario ADD CONSTRAINT usuario_role_check CHECK (role IN ('CLIENTE', 'ADMIN', 'ENTRENADOR', 'RECEPCIONISTA'));
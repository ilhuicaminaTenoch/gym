create table if not exists usuario_roles (
                               id_usuario INTEGER NOT NULL,
                               role      VARCHAR(50) NOT NULL,
                               CONSTRAINT fk_usuario_roles_usuario FOREIGN KEY (id_usuario)
                                   REFERENCES usuario(id_usuario)
);

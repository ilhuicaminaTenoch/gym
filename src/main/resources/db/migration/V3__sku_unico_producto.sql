alter table producto
    alter column sku set not null;

create unique index producto__index__sku
    on producto (sku);
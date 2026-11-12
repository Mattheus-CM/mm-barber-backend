CREATE TABLE service_offerings (
    id BIGINT NOT NULL AUTO_INCREMENT,
    barbershop_id BIGINT NOT NULL,
    name VARCHAR(100) NOT NULL,
    description VARCHAR(500),
    default_price DECIMAL(10,2) NOT NULL,
    active BOOLEAN NOT NULL DEFAULT TRUE,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY(id),
    CONSTRAINT uk_service_offerings_shop_name UNIQUE (barbershop_id, name)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;
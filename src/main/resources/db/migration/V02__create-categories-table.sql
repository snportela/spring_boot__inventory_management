CREATE TABLE categories (
    category_id uuid DEFAULT gen_random_uuid() NOT NULL,
    name varchar(100) NOT NULL,
    created_at TIMESTAMPTZ DEFAULT NOW(),
    updated_at TIMESTAMPTZ DEFAULT NOW(),
    deleted_at TIMESTAMPTZ,

    CONSTRAINT categories_pk PRIMARY KEY (category_id)
);
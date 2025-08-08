CREATE TABLE resources (
       resource_id uuid default gen_random_uuid() NOT NULL,
       area_id uuid NOT NULL,
       category_id uuid NOT NULL,
       name varchar(100) NOT NULL,
       description text NOT NULL,
       manufacture_year varchar(10) NULL,
       serial_number varchar(100) NULL,
       repair_state varchar(100) NOT NULL,
       resource_number varchar(100) NULL,
       status varchar(100) NOT NULL,
       observation text NULL,
       use_time varchar(100) NOT NULL,
       created_at TIMESTAMPTZ DEFAULT NOW(),
       updated_at TIMESTAMPTZ DEFAULT NOW(),
       deleted_at TIMESTAMPTZ,

       CONSTRAINT resources_pk PRIMARY KEY (resource_id),

       CONSTRAINT resource_area_fk FOREIGN KEY (area_id) REFERENCES areas,
       CONSTRAINT resource_category_fk FOREIGN KEY (category_id) REFERENCES categories
);
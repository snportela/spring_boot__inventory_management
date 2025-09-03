CREATE TABLE users (
   user_id uuid default gen_random_uuid() NOT NULL,
   name varchar(100) NOT NULL,
   email varchar(100) NOT NULL,
   password varchar(100) NOT NULL,
   role varchar(100) NOT NULL,
   reset_password_token text NULL,
   created_at TIMESTAMPTZ DEFAULT NOW(),
   updated_at TIMESTAMPTZ DEFAULT NOW(),
   deleted_at TIMESTAMPTZ,

   CONSTRAINT users_pk PRIMARY KEY (user_id),
   CONSTRAINT users_unique UNIQUE (email)
);

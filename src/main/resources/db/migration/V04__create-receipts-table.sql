CREATE TABLE receipts (
      receipt_id uuid DEFAULT gen_random_uuid() NOT NULL,
      receipt_number varchar(100) NOT NULL,
      price decimal(10,2) NOT NULL,
      supplier varchar(100) NOT NULL,
      receipt_date TIMESTAMPTZ NOT NULL,
      created_at TIMESTAMPTZ DEFAULT NOW(),
      updated_at TIMESTAMPTZ DEFAULT NOW(),
      deleted_at TIMESTAMPTZ,

      CONSTRAINT receipts_pk PRIMARY KEY (receipt_id)
);
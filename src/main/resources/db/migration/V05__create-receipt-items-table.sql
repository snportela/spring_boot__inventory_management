CREATE TABLE receipt_items (
   receipt_id uuid NOT NULL,
   resource_id uuid NOT NULL,
   unit_price decimal(10,2) NOT NULL,
   observation text NULL,
   created_at TIMESTAMPTZ DEFAULT NOW(),
   updated_at TIMESTAMPTZ DEFAULT NOW(),
   deleted_at TIMESTAMPTZ,

   CONSTRAINT receipt_fk FOREIGN KEY (receipt_id) REFERENCES receipts,
   CONSTRAINT resource_fk FOREIGN KEY (resource_id) REFERENCES resources,
   CONSTRAINT receipt_items_pk PRIMARY KEY (receipt_id, resource_id)
);
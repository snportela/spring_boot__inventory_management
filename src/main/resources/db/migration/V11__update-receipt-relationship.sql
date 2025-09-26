DROP TABLE receipt_items;

ALTER TABLE resources ADD receipt_id uuid;

ALTER TABLE resources ADD CONSTRAINT fk_resource_receipt_id FOREIGN KEY (receipt_id) REFERENCES receipts(receipt_id);
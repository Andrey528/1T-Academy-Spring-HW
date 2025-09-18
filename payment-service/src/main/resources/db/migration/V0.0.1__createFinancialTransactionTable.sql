CREATE TABLE financial_transaction (
                                       id BIGSERIAL PRIMARY KEY,
                                       user_initiator_id BIGINT,
                                       product_from_id BIGINT,
                                       user_recipient_id BIGINT,
                                       product_to_id BIGINT,
                                       transfer_amount DECIMAL(19, 2),
                                       state VARCHAR(50)
);
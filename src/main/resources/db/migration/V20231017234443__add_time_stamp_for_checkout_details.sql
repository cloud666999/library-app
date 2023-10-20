alter table checkout_details add created_at timestamptz default now();
alter table checkout_details add updated_at timestamptz default now();
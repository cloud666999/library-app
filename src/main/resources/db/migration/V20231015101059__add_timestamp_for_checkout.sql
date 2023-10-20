alter table checkout add created_at timestamptz default now();
alter table checkout add updated_at timestamptz default now();
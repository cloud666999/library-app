alter table book add created_at timestamptz default now();
alter table book add updated_at timestamptz default now();
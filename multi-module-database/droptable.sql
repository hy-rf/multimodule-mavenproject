USE mmdb;

-- The tables are dropped in reverse order of their dependencies to avoid foreign key constraint issues.
DROP TABLE IF EXISTS user_roles;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS users;



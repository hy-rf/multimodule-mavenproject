
--Example data insertion
INSERT INTO users (username, password_hash) VALUES ('string', 'z7UbiO1n6KyBeS0O6duzfA==:Z8/P5c1jCqjKjjB92kh/AS27jDE4nn0hnmfPkFuZC+g=');
INSERT INTO roles (name) VALUES ('user');
INSERT INTO roles (name) VALUES ('admin');
INSERT INTO user_roles (user_id, role_id) VALUES (1, 1);
INSERT INTO user_roles (user_id, role_id) VALUES (1, 2);
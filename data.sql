INSERT INTO users (
    username,
    email,
    password_hash,
    full_name,
    is_active,
    email_verified,
    two_factor_enabled,
    two_factor_secret,
    created_at,
    updated_at
) VALUES (
    'admin',
    'admin@example.com',
    '$2a$10$examplehashedpassword', -- Replace with a real bcrypt hash
    'Administrator',
    TRUE,
    TRUE,
    FALSE,
    NULL,
    NOW(),
    NOW()
);
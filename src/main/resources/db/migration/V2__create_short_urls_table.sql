CREATE TABLE short_urls (
    id BIGSERIAL PRIMARY KEY,
    short_key VARCHAR(10) NOT NULL UNIQUE,
    original_url TEXT NOT NULL,
    created_by BIGINT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP,
    is_private BOOLEAN NOT NULL DEFAULT FALSE,
    click_count BIGINT NOT NULL DEFAULT 0,
    CONSTRAINT fk_short_urls_users FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE INDEX idx_short_urls_short_key ON short_urls(short_key);
CREATE INDEX idx_short_urls_created_by ON short_urls(created_by);
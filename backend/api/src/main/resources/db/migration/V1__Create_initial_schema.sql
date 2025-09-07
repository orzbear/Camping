-- Create parks table
CREATE TABLE parks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    region VARCHAR(100) NOT NULL,
    authority VARCHAR(255) NOT NULL,
    website_url VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Create campsites table
CREATE TABLE campsites (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    park_id BIGINT NOT NULL,
    name VARCHAR(255) NOT NULL,
    lat DECIMAL(10,8) NOT NULL,
    lon DECIMAL(11,8) NOT NULL,
    description TEXT,
    fee_aud DECIMAL(10,2),
    pet_allowed BOOLEAN DEFAULT FALSE,
    bookable BOOLEAN NOT NULL DEFAULT TRUE,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (park_id) REFERENCES parks(id) ON DELETE CASCADE
);

-- Create amenities table
CREATE TABLE amenities (
    campsite_id BIGINT PRIMARY KEY,
    has_bbq BOOLEAN DEFAULT FALSE,
    has_toilet BOOLEAN DEFAULT FALSE,
    has_water BOOLEAN DEFAULT FALSE,
    has_shelter BOOLEAN DEFAULT FALSE,
    has_power BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (campsite_id) REFERENCES campsites(id) ON DELETE CASCADE
);

-- Create forecasts table
CREATE TABLE forecasts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    lat DECIMAL(10,8) NOT NULL,
    lon DECIMAL(11,8) NOT NULL,
    hour_utc TIMESTAMP NOT NULL,
    temp_c DECIMAL(5,2),
    precip_mm DECIMAL(5,2),
    precip_prob DECIMAL(5,2),
    wind_mps DECIMAL(5,2),
    uv_index DECIMAL(3,1),
    source VARCHAR(50) NOT NULL,
    fetched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    INDEX idx_location_time (lat, lon, hour_utc),
    INDEX idx_fetched_at (fetched_at)
);

-- Create alerts table
CREATE TABLE alerts (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    park_id BIGINT,
    severity VARCHAR(20) NOT NULL,
    title VARCHAR(500) NOT NULL,
    summary TEXT,
    starts_at TIMESTAMP,
    ends_at TIMESTAMP,
    source VARCHAR(100) NOT NULL,
    url VARCHAR(1000),
    fetched_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (park_id) REFERENCES parks(id) ON DELETE SET NULL,
    INDEX idx_park_severity (park_id, severity),
    INDEX idx_starts_at (starts_at)
);

-- Create trips table
CREATE TABLE trips (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(100) NOT NULL,
    campsite_id BIGINT NOT NULL,
    start_dt TIMESTAMP NOT NULL,
    end_dt TIMESTAMP NOT NULL,
    pax INT NOT NULL,
    notes TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (campsite_id) REFERENCES campsites(id) ON DELETE CASCADE,
    INDEX idx_user_id (user_id),
    INDEX idx_dates (start_dt, end_dt)
);

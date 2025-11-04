-- Sample seed data for products table
-- Table: products (id AUTO_INCREMENT)
-- Columns: name, price, description, image_url, status, created_at, updated_at

INSERT INTO products (name, price, description, image_url, status, created_at, updated_at) VALUES
('iPhone 15 Pro', 1099.00, 'Flagship phone with A17 Pro chip and titanium frame', 'https://example.com/images/iphone15pro.jpg', 'ACTIVE', NOW(), NOW()),
('Samsung Galaxy S23', 899.00, 'Premium Android smartphone with excellent camera', 'https://example.com/images/galaxy-s23.jpg', 'ACTIVE', NOW(), NOW()),
('MacBook Air M2', 1299.00, 'Lightweight laptop powered by Apple M2 chip', 'https://example.com/images/macbook-air-m2.jpg', 'ACTIVE', NOW(), NOW()),
('Dell XPS 13', 1199.00, 'Ultrabook with InfinityEdge display', 'https://example.com/images/dell-xps-13.jpg', 'ACTIVE', NOW(), NOW()),
('Sony WH-1000XM5', 399.00, 'Industry-leading noise cancelling headphones', 'https://example.com/images/sony-xm5.jpg', 'ACTIVE', NOW(), NOW()),
('iPad Air', 599.00, 'Powerful tablet with Apple Pencil support', 'https://example.com/images/ipad-air.jpg', 'ACTIVE', NOW(), NOW()),
('Logitech MX Master 3S', 129.99, 'Ergonomic wireless mouse for productivity', 'https://example.com/images/mx-master-3s.jpg', 'ACTIVE', NOW(), NOW()),
('Kindle Paperwhite', 159.00, 'Waterproof e-reader with adjustable warm light', 'https://example.com/images/kindle-paperwhite.jpg', 'ACTIVE', NOW(), NOW()),
('Nintendo Switch OLED', 349.99, 'Hybrid console with 7-inch OLED display', 'https://example.com/images/switch-oled.jpg', 'ACTIVE', NOW(), NOW()),
('Anker PowerCore 20000', 59.99, 'High-capacity portable charger', 'https://example.com/images/anker-powercore-20000.jpg', 'ACTIVE', NOW(), NOW());

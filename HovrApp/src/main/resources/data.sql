-- Clear existing data in correct order (child → parent)
-- DELETE FROM room;
-- DELETE FROM hotel;
-- DELETE FROM app_user;

-- Insert dummy user (MUST come before hotels)

INSERT INTO app_user (id, name, email, password)
VALUES (1, 'Admin User', 'admin@gmail.com', '123456');

-- Insert Hotels
-- INSERT INTO hotel (
--     name, city, photos, amenities, active, owner_id, created_at, updated_at
-- ) VALUES
-- ('Blue Orchid Residency', 'Mumbai', ARRAY['photo1.jpg', 'photo2.jpg'], ARRAY['pool', 'wifi', 'parking'], true, 1, NOW(), NOW()),
-- ('The Grand Vista', 'Pune', ARRAY['grand1.jpg', 'grand2.jpg'], ARRAY['wifi', 'gym'], true, 1, NOW(), NOW()),
-- ('Ocean Pearl Inn', 'Goa', ARRAY['beach1.jpg'], ARRAY['pool', 'beach-access', 'spa'], true, 1, NOW(), NOW()),
-- ('Skyline Suites', 'Delhi', ARRAY['sky1.jpg', 'sky2.jpg'], ARRAY['wifi', 'restaurant', 'bar'], false, 1, NOW(), NOW()),
-- ('Mountain View Retreat', 'Manali', ARRAY['mt1.jpg', 'mt2.jpg'], ARRAY['wifi', 'mountain-view'], true, 1, NOW(), NOW()),
-- ('Citylight Residency', 'Bangalore', ARRAY['city1.jpg'], ARRAY['wifi', 'conference-room'], true, 1, NOW(), NOW()),
-- ('Sunset Lodge', 'Jaipur', ARRAY['sunset.jpg'], ARRAY['wifi', 'parking'], false, 1, NOW(), NOW()),
-- ('Royal Heritage Palace', 'Udaipur', ARRAY['heritage1.jpg'], ARRAY['spa', 'wifi', 'garden'], true, 1, NOW(), NOW()),
-- ('Lakeside Haven', 'Nainital', ARRAY['lake1.jpg'], ARRAY['wifi', 'lake-view'], true, 1, NOW(), NOW()),
-- ('The Cozy Cabin', 'Shimla', ARRAY['cabin1.jpg'], ARRAY['fireplace', 'mountain-view', 'wifi'], true, 1, NOW(), NOW());

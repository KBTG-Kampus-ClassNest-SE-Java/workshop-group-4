INSERT INTO shopper (username, email) VALUES ('TechNinja', 'techninja@example.com') ON CONFLICT DO NOTHING;
INSERT INTO shopper (username, email) VALUES ('CodeMaster', 'codemaster@example.com') ON CONFLICT DO NOTHING;
INSERT INTO shopper (username, email) VALUES ('DataGuru', 'dataguru@example.com') ON CONFLICT DO NOTHING;
INSERT INTO shopper (username, email) VALUES ('CyberSavvy', 'cybersavvy@example.com') ON CONFLICT DO NOTHING;
INSERT INTO shopper (username, email) VALUES ('GeekChic', 'geekchic@example.com') ON CONFLICT DO NOTHING;

INSERT INTO cart (shopper_id) VALUES (1) ON CONFLICT DO NOTHING;
INSERT INTO cart (shopper_id) VALUES (2) ON CONFLICT DO NOTHING;
INSERT INTO cart (shopper_id) VALUES (3) ON CONFLICT DO NOTHING;
INSERT INTO cart (shopper_id) VALUES (4) ON CONFLICT DO NOTHING;
INSERT INTO cart (shopper_id) VALUES (5) ON CONFLICT DO NOTHING;

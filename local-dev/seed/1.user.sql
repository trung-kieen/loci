-- =============================================
-- CHUNK 1: AUTHORITY, USERS, SETTINGS
-- =============================================
SELECT setval('payments_id_seq'), 0, true;

-- Insert authorities
INSERT INTO authority (name) VALUES
('ROLE_user'),
('ROLE_admin');

-- Insert 40 users with realistic data
INSERT INTO user_ (id, username, email, firstname, lastname, bio, public_id, created_date, last_modified_date, last_active)
VALUES
(nextval('user_sequence'), 'alice.nguyen', 'alice.nguyen@example.com', 'Alice', 'Nguyen', 'Software engineer passionate about AI', gen_random_uuid(), '2024-12-01 08:00:00+07', '2025-02-05 10:30:00+07', '2025-02-05 10:30:00+07'),
(nextval('user_sequence'), 'bob.tran', 'bob.tran@example.com', 'Bob', 'Tran', 'Tech enthusiast and coffee lover', gen_random_uuid(), '2024-12-02 09:00:00+07', '2025-02-05 09:15:00+07', '2025-02-05 09:15:00+07'),
(nextval('user_sequence'), 'charlie.le', 'charlie.le@example.com', 'Charlie', 'Le', 'Digital marketer', gen_random_uuid(), '2024-12-03 10:00:00+07', '2025-02-05 11:00:00+07', '2025-02-05 11:00:00+07'),
(nextval('user_sequence'), 'diana.pham', 'diana.pham@example.com', 'Diana', 'Pham', 'UX designer creating beautiful experiences', gen_random_uuid(), '2024-12-04 11:00:00+07', '2025-02-05 08:45:00+07', '2025-02-05 08:45:00+07'),
(nextval('user_sequence'), 'ethan.vo', 'ethan.vo@example.com', 'Ethan', 'Vo', 'Backend developer', gen_random_uuid(), '2024-12-05 12:00:00+07', '2025-02-05 10:00:00+07', '2025-02-05 10:00:00+07'),
(nextval('user_sequence'), 'fiona.hoang', 'fiona.hoang@example.com', 'Fiona', 'Hoang', 'Product manager at tech startup', gen_random_uuid(), '2024-12-06 13:00:00+07', '2025-02-05 09:30:00+07', '2025-02-05 09:30:00+07'),
(nextval('user_sequence'), 'george.dao', 'george.dao@example.com', 'George', 'Dao', 'Data scientist', gen_random_uuid(), '2024-12-07 14:00:00+07', '2025-02-05 10:15:00+07', '2025-02-05 10:15:00+07'),
(nextval('user_sequence'), 'hannah.bui', 'hannah.bui@example.com', 'Hannah', 'Bui', 'Frontend developer and UI enthusiast', gen_random_uuid(), '2024-12-08 15:00:00+07', '2025-02-05 11:20:00+07', '2025-02-05 11:20:00+07'),
(nextval('user_sequence'), 'ian.ngo', 'ian.ngo@example.com', 'Ian', 'Ngo', 'Cloud architect', gen_random_uuid(), '2024-12-09 16:00:00+07', '2025-02-05 08:00:00+07', '2025-02-05 08:00:00+07'),
(nextval('user_sequence'), 'julia.dang', 'julia.dang@example.com', 'Julia', 'Dang', 'Content creator', gen_random_uuid(), '2024-12-10 17:00:00+07', '2025-02-05 09:00:00+07', '2025-02-05 09:00:00+07'),
(nextval('user_sequence'), 'kevin.ly', 'kevin.ly@example.com', 'Kevin', 'Ly', 'Mobile app developer', gen_random_uuid(), '2024-12-11 18:00:00+07', '2025-02-05 10:45:00+07', '2025-02-05 10:45:00+07'),
(nextval('user_sequence'), 'laura.mai', 'laura.mai@example.com', 'Laura', 'Mai', 'Business analyst', gen_random_uuid(), '2024-12-12 19:00:00+07', '2025-02-05 11:30:00+07', '2025-02-05 11:30:00+07'),
(nextval('user_sequence'), 'mike.truong', 'mike.truong@example.com', 'Mike', 'Truong', 'DevOps engineer', gen_random_uuid(), '2024-12-13 08:00:00+07', '2025-02-05 08:20:00+07', '2025-02-05 08:20:00+07'),
(nextval('user_sequence'), 'nina.do', 'nina.do@example.com', 'Nina', 'Do', 'Graphic designer', gen_random_uuid(), '2024-12-14 09:00:00+07', '2025-02-05 09:40:00+07', '2025-02-05 09:40:00+07'),
(nextval('user_sequence'), 'oscar.ha', 'oscar.ha@example.com', 'Oscar', 'Ha', 'Security specialist', gen_random_uuid(), '2024-12-15 10:00:00+07', '2025-02-05 10:50:00+07', '2025-02-05 10:50:00+07'),
(nextval('user_sequence'), 'paula.trinh', 'paula.trinh@example.com', 'Paula', 'Trinh', 'HR manager', gen_random_uuid(), '2024-12-16 11:00:00+07', '2025-02-05 11:10:00+07', '2025-02-05 11:10:00+07'),
(nextval('user_sequence'), 'quinn.cao', 'quinn.cao@example.com', 'Quinn', 'Cao', 'Project manager', gen_random_uuid(), '2024-12-17 12:00:00+07', '2025-02-05 08:30:00+07', '2025-02-05 08:30:00+07'),
(nextval('user_sequence'), 'rachel.dinh', 'rachel.dinh@example.com', 'Rachel', 'Dinh', 'Marketing specialist', gen_random_uuid(), '2024-12-18 13:00:00+07', '2025-02-05 09:50:00+07', '2025-02-05 09:50:00+07'),
(nextval('user_sequence'), 'steve.nguyen', 'steve.nguyen@example.com', 'Steve', 'Nguyen', 'Full-stack developer', gen_random_uuid(), '2024-12-19 14:00:00+07', '2025-02-05 10:20:00+07', '2025-02-05 10:20:00+07'),
(nextval('user_sequence'), 'tina.phan', 'tina.phan@example.com', 'Tina', 'Phan', 'Sales manager', gen_random_uuid(), '2024-12-20 15:00:00+07', '2025-02-05 11:40:00+07', '2025-02-05 11:40:00+07'),
(nextval('user_sequence'), 'uma.lam', 'uma.lam@example.com', 'Uma', 'Lam', 'Quality assurance engineer', gen_random_uuid(), '2024-12-21 16:00:00+07', '2025-02-05 08:10:00+07', '2025-02-05 08:10:00+07'),
(nextval('user_sequence'), 'victor.vu', 'victor.vu@example.com', 'Victor', 'Vu', 'Database administrator', gen_random_uuid(), '2024-12-22 17:00:00+07', '2025-02-05 09:20:00+07', '2025-02-05 09:20:00+07'),
(nextval('user_sequence'), 'wendy.huynh', 'wendy.huynh@example.com', 'Wendy', 'Huynh', 'Customer success manager', gen_random_uuid(), '2024-12-23 18:00:00+07', '2025-02-05 10:40:00+07', '2025-02-05 10:40:00+07'),
(nextval('user_sequence'), 'xavier.ta', 'xavier.ta@example.com', 'Xavier', 'Ta', 'AI researcher', gen_random_uuid(), '2024-12-24 19:00:00+07', '2025-02-05 11:50:00+07', '2025-02-05 11:50:00+07'),
(nextval('user_sequence'), 'yara.chu', 'yara.chu@example.com', 'Yara', 'Chu', 'Product designer', gen_random_uuid(), '2024-12-25 08:00:00+07', '2025-02-05 08:40:00+07', '2025-02-05 08:40:00+07'),
(nextval('user_sequence'), 'zach.quach', 'zach.quach@example.com', 'Zach', 'Quach', 'Network engineer', gen_random_uuid(), '2024-12-26 09:00:00+07', '2025-02-05 09:10:00+07', '2025-02-05 09:10:00+07'),
(nextval('user_sequence'), 'amy.duong', 'amy.duong@example.com', 'Amy', 'Duong', 'Scrum master', gen_random_uuid(), '2024-12-27 10:00:00+07', '2025-02-05 10:30:00+07', '2025-02-05 10:30:00+07'),
(nextval('user_sequence'), 'ben.tong', 'ben.tong@example.com', 'Ben', 'Tong', 'Technical writer', gen_random_uuid(), '2024-12-28 11:00:00+07', '2025-02-05 11:00:00+07', '2025-02-05 11:00:00+07'),
(nextval('user_sequence'), 'clara.nghiem', 'clara.nghiem@example.com', 'Clara', 'Nghiem', 'Operations manager', gen_random_uuid(), '2024-12-29 12:00:00+07', '2025-02-05 08:50:00+07', '2025-02-05 08:50:00+07'),
(nextval('user_sequence'), 'david.thai', 'david.thai@example.com', 'David', 'Thai', 'Solutions architect', gen_random_uuid(), '2024-12-30 13:00:00+07', '2025-02-05 09:30:00+07', '2025-02-05 09:30:00+07'),
(nextval('user_sequence'), 'emily.chu', 'emily.chu@example.com', 'Emily', 'Chu', 'Business intelligence analyst', gen_random_uuid(), '2024-12-31 14:00:00+07', '2025-02-05 10:10:00+07', '2025-02-05 10:10:00+07'),
(nextval('user_sequence'), 'frank.luong', 'frank.luong@example.com', 'Frank', 'Luong', 'System administrator', gen_random_uuid(), '2025-01-01 15:00:00+07', '2025-02-05 11:20:00+07', '2025-02-05 11:20:00+07'),
(nextval('user_sequence'), 'grace.kim', 'grace.kim@example.com', 'Grace', 'Kim', 'SEO specialist', gen_random_uuid(), '2025-01-02 16:00:00+07', '2025-02-05 08:15:00+07', '2025-02-05 08:15:00+07'),
(nextval('user_sequence'), 'henry.than', 'henry.than@example.com', 'Henry', 'Than', 'Machine learning engineer', gen_random_uuid(), '2025-01-03 17:00:00+07', '2025-02-05 09:45:00+07', '2025-02-05 09:45:00+07'),
(nextval('user_sequence'), 'iris.phung', 'iris.phung@example.com', 'Iris', 'Phung', 'Financial analyst', gen_random_uuid(), '2025-01-04 18:00:00+07', '2025-02-05 10:55:00+07', '2025-02-05 10:55:00+07'),
(nextval('user_sequence'), 'jack.hong', 'jack.hong@example.com', 'Jack', 'Hong', 'Support engineer', gen_random_uuid(), '2025-01-05 19:00:00+07', '2025-02-05 11:35:00+07', '2025-02-05 11:35:00+07'),
(nextval('user_sequence'), 'kate.tieu', 'kate.tieu@example.com', 'Kate', 'Tieu', 'Content strategist', gen_random_uuid(), '2025-01-06 08:00:00+07', '2025-02-05 08:25:00+07', '2025-02-05 08:25:00+07'),
(nextval('user_sequence'), 'leo.quang', 'leo.quang@example.com', 'Leo', 'Quang', 'IT consultant', gen_random_uuid(), '2025-01-07 09:00:00+07', '2025-02-05 09:55:00+07', '2025-02-05 09:55:00+07'),
(nextval('user_sequence'), 'mia.vinh', 'mia.vinh@example.com', 'Mia', 'Vinh', 'E-commerce manager', gen_random_uuid(), '2025-01-08 10:00:00+07', '2025-02-05 10:25:00+07', '2025-02-05 10:25:00+07'),
(nextval('user_sequence'), 'noah.son', 'noah.son@example.com', 'Noah', 'Son', 'Blockchain developer', gen_random_uuid(), '2025-01-09 11:00:00+07', '2025-02-05 11:45:00+07', '2025-02-05 11:45:00+07');

-- Insert user_authority (all users have ROLE_USER)
INSERT INTO user_authority (user_id, authority_name)
SELECT id, 'ROLE_user' FROM user_;

-- Insert user_setting (default settings for all users)
INSERT INTO user_setting (user_id, profile_visibility, friend_request_setting, last_seen_setting, created_date, last_modified_date)
SELECT
    id,
    true,
    'EVERYONE',
    'EVERYONE',
    created_date,
    last_modified_date
FROM user_;

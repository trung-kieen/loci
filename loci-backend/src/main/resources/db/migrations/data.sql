-- Copyright 2026 trung-kieen
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
--
--     http://www.apache.org/licenses/LICENSE-2.0
--
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.

-- Seed user for testing
INSERT INTO public.user_ (
    created_date,
    id,
    last_active,
    last_modified_date,
    public_id,
    bio,
    email,
    firstname,
    lastname,
    profile_picture,
    username
) VALUES
('2025-12-05 01:50:01.294513+00', 4, NULL, '2025-12-05 02:02:41.72921+00', 'fe0be6db-904c-4fb3-8b5c-0fd64b625dd2', NULL, 'bryon.reilly@yahoo.com', 'Goldner', 'Junita', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=461 ', 'bryon.reilly@yahoo.com'),
('2025-12-05 02:08:19.92988+00', 5, NULL, '2025-12-05 02:08:19.92988+00', 'e3d9eb14-e39e-4c63-8438-8d6a4bed216c', NULL, 'cedric.rau@yahoo.com', 'Hammes', 'Hong', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=780 ', 'cedric.rau@yahoo.com'),
('2025-12-04 14:50:58.551434+00', 2, NULL, '2025-12-05 02:14:27.081271+00', 'ca4311c1-904a-473e-9af5-1c8d522ec981', NULL, 'testuser1@gmail.com', 'kien', 'trung', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=290 ', 'testuser1'),
('2025-12-05 02:14:48.147167+00', 6, NULL, '2025-12-05 02:14:48.147167+00', '165c081a-a05c-4a75-a365-ce8e6954ca98', NULL, 'corazon.quigley@gmail.com', 'Brekke', 'Dorene', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=585 ', 'corazon.quigley@gmail.com'),
('2025-12-05 02:15:11.950151+00', 7, NULL, '2025-12-05 02:15:11.950151+00', 'c4338c6b-8480-4864-920a-2112d2dfe73a', NULL, 'earle.wilderman@yahoo.com', 'Bradtke', 'Tory', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=897 ', 'earle.wilderman@yahoo.com'),
('2025-12-05 02:15:42.537048+00', 8, NULL, '2025-12-05 02:16:11.340527+00', 'f5b575c6-031c-4d6e-bdaa-5b77d8ec2318', NULL, 'eugenie.goldner@gmail.com', 'Weimann', 'Davida', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=936 ', 'eugenie.goldner@gmail.com'),
('2025-12-05 02:16:32.46083+00', 9, NULL, '2025-12-05 02:16:32.46083+00', 'e56c826c-4d57-44a0-b22d-db72fe963ee2', NULL, 'fanny.jakubowski@gmail.com', 'Wilkinson', 'Viola', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=946 ', 'fanny.jakubowski@gmail.com'),
('2025-12-05 02:17:01.626503+00', 10, NULL, '2025-12-05 02:17:01.626503+00', 'c7df8822-b69b-4f5e-92de-48a07c458ee6', NULL, 'gidget.bergstrom@yahoo.com', 'Doyle', 'Kaylee', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=659 ', 'gidget.bergstrom@yahoo.com'),
('2025-12-05 02:17:21.670557+00', 11, NULL, '2025-12-05 02:17:21.670557+00', 'aa9c53f3-467c-4a1e-ae22-0b1ee0063ff6', NULL, 'glady.conn@yahoo.com', 'Hessel', 'Fatima', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=600 ', 'glady.conn@yahoo.com'),
('2025-12-05 02:17:37.137455+00', 12, NULL, '2025-12-05 02:17:37.137455+00', '6f567894-ff58-4ac3-8405-2429f85b7e2f', NULL, 'halley.balistreri@gmail.com', 'Collier', 'Jaquelyn', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=339 ', 'halley.balistreri@gmail.com'),
('2025-12-05 02:18:00.04301+00', 13, NULL, '2025-12-05 02:18:00.04301+00', 'fb76374a-cd77-43a2-9f16-4f991a5087bb', NULL, 'jae.koch@yahoo.com', 'Graham', 'Sage', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=1 ', 'jae.koch@yahoo.com'),
('2025-12-05 02:18:21.395825+00', 14, NULL, '2025-12-05 02:18:21.395825+00', 'e06268d2-bca0-4a0d-a521-a0ffd10582f1', NULL, 'jung.heidenreich@gmail.com', 'Beier', 'Galen', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=721 ', 'jung.heidenreich@gmail.com'),
('2025-12-05 02:18:38.484872+00', 15, NULL, '2025-12-05 02:18:38.484872+00', '666813d9-e4d6-48c7-9410-b12d16479cc2', NULL, 'kittie.hackett@yahoo.com', 'Doyle', 'Caroline', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=318 ', 'kittie.hackett@yahoo.com'),
('2025-12-05 02:18:50.986851+00', 16, NULL, '2025-12-05 02:18:50.986851+00', 'b98d41c5-7492-4df7-88ea-fa1a07622782', NULL, 'kourtney.ohara@yahoo.com', 'Purdy', 'Missy', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=926 ', 'kourtney.ohara@yahoo.com'),
('2025-12-05 01:43:20.4205+00', 3, NULL, '2025-12-05 01:48:01.749375+00', '3826dcd7-5e18-430d-96d4-92cc3533e60b', NULL, 'alaina.goyette@gmail.com', 'Schamberger', 'Janean', 'https://api.dicebear.com/7.x/notionists/svg?scale=200&seed=145 ', 'alaina.goyette@gmail.com');


-- Seed user for testing
INSERT INTO public.user_setting (
    friend_request_setting,
    profile_visibility,
    created_date,
    user_id,
    last_modified_date,
    last_seen_setting
) VALUES
('EVERYONE', true,'2025-12-05 02:02:41.72921+00' , 4, '2025-12-05 02:02:41.72921+00',   'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:08:19.92988+00' , 5, '2025-12-05 02:08:19.92988+00',   'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:14:27.081271+00' , 2, '2025-12-05 02:14:27.081271+00',  'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:14:48.147167+00' , 6, '2025-12-05 02:14:48.147167+00',  'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:15:11.950151+00' , 7, '2025-12-05 02:15:11.950151+00',  'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:16:11.340527+00' , 8, '2025-12-05 02:16:11.340527+00',  'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:16:32.46083+00' , 9, '2025-12-05 02:16:32.46083+00',   'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:17:01.626503+00' , 10,'2025-12-05 02:17:01.626503+00', 'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:17:21.670557+00' , 11,'2025-12-05 02:17:21.670557+00', 'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:17:37.137455+00' , 12,'2025-12-05 02:17:37.137455+00', 'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:18:00.04301+00' , 13,'2025-12-05 02:18:00.04301+00',  'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:18:21.395825+00' , 14,'2025-12-05 02:18:21.395825+00', 'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:18:38.484872+00' , 15,'2025-12-05 02:18:38.484872+00', 'EVERYONE'),
('EVERYONE', true,'2025-12-05 02:18:50.986851+00' , 16,'2025-12-05 02:18:50.986851+00', 'EVERYONE'),
('EVERYONE', true,'2025-12-05 01:48:01.749375+00' , 3, '2025-12-05 01:48:01.749375+00',  'EVERYONE');

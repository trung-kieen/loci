    alter table if exists contact_request
       add column public_id uuid;

    alter table if exists conversations
       add column public_id uuid;

    alter table if exists group_
       add column public_id uuid;

    alter table if exists message
       add column public_id uuid;

    alter table if exists notification
       add column public_id uuid;

    alter table if exists contact_request
       drop constraint if exists UKdxcpfb5ytd3nbpn5uo1hp7t6e;

    alter table if exists contact_request
       add constraint UKdxcpfb5ytd3nbpn5uo1hp7t6e unique (public_id);

    alter table if exists conversations
       drop constraint if exists UKq07914pcv9kk9toltnas25mrh;

    alter table if exists conversations
       add constraint UKq07914pcv9kk9toltnas25mrh unique (public_id);

    alter table if exists group_
       drop constraint if exists UKoqiok65unlg3ujawpiwdoy9nd;

    alter table if exists group_
       add constraint UKoqiok65unlg3ujawpiwdoy9nd unique (public_id);

    alter table if exists message
       drop constraint if exists UKiv3kt17dk5u1v4n8bpqkyhqvd;

    alter table if exists message
       add constraint UKiv3kt17dk5u1v4n8bpqkyhqvd unique (public_id);

    alter table if exists notification
       drop constraint if exists UK580xwhvqivevh4eucrgwqypnd;

    alter table if exists notification
       add constraint UK580xwhvqivevh4eucrgwqypnd unique (public_id);

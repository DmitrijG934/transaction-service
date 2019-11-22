INSERT INTO public.departments(department_id, created_at, department_name)
VALUES ('00000000-0000-0000-0000-000000000000', '2019-11-21T23:08:26.448+0000', 'default');

INSERT INTO public.transaction_owner(user_id, created_at, is_active ,owner_name, register_date, department_id)
VALUES ('00000000-0000-0000-0000-000000000001',
        '2019-11-21T23:08:26.428+0000', true,

        'test_owner', '2019-12-21T23:08:26.428+0000',
        '00000000-0000-0000-0000-000000000000');

INSERT INTO public.transaction_owner(user_id, created_at, is_active ,owner_name, register_date, department_id)
VALUES ('00000000-0000-0000-0000-000000000002',
        '2019-11-21T23:08:26.428+0000', true,

        'test_owner2', '2019-12-21T23:08:26.428+0000',
        '00000000-0000-0000-0000-000000000000');

INSERT INTO public.transaction_owner(user_id, created_at, is_active ,owner_name, register_date, department_id)
VALUES ('00000000-0000-0000-0000-000000000003',
        '2019-09-21T23:08:26.428+0000', true,

        'test_owner3', '2019-11-21T23:08:26.428+0000',
        '00000000-0000-0000-0000-000000000000');

INSERT INTO public.owner_priority(user_id, priority) VALUES ('00000000-0000-0000-0000-000000000001', 'OPTIONAL');
INSERT INTO public.owner_priority(user_id, priority) VALUES ('00000000-0000-0000-0000-000000000002', 'OPTIONAL');
INSERT INTO public.owner_priority(user_id, priority) VALUES ('00000000-0000-0000-0000-000000000003', 'OPTIONAL');
create table contact
(
    id serial not null
        constraint contact_pk
            primary key,
    resume_uuid char(36) not null
        constraint contact_resume_uuid_fk
            references resume(uuid)
            on update restrict on delete cascade,
    type text not null,
    value text not null
);
create unique index contact_uuid_index
	on contact (resume_uuid, type);

alter table contact owner to sayan;

create table resume
(
    uuid char(36) not null
        constraint resume_pk
            primary key,
    full_name text
);

alter table resume owner to sayan;


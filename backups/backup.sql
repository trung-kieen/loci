--
-- PostgreSQL database dump
--

-- Dumped from database version 17.5 (Debian 17.5-1.pgdg120+1)
-- Dumped by pg_dump version 17.5 (Debian 17.5-1.pgdg120+1)

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET transaction_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: admin_event_entity; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.admin_event_entity (
    id character varying(36) NOT NULL,
    admin_event_time bigint,
    realm_id character varying(255),
    operation_type character varying(255),
    auth_realm_id character varying(255),
    auth_client_id character varying(255),
    auth_user_id character varying(255),
    ip_address character varying(255),
    resource_path character varying(2550),
    representation text,
    error character varying(255),
    resource_type character varying(64)
);


ALTER TABLE public.admin_event_entity OWNER TO admin;

--
-- Name: associated_policy; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.associated_policy (
    policy_id character varying(36) NOT NULL,
    associated_policy_id character varying(36) NOT NULL
);


ALTER TABLE public.associated_policy OWNER TO admin;

--
-- Name: authentication_execution; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.authentication_execution (
    id character varying(36) NOT NULL,
    alias character varying(255),
    authenticator character varying(36),
    realm_id character varying(36),
    flow_id character varying(36),
    requirement integer,
    priority integer,
    authenticator_flow boolean DEFAULT false NOT NULL,
    auth_flow_id character varying(36),
    auth_config character varying(36)
);


ALTER TABLE public.authentication_execution OWNER TO admin;

--
-- Name: authentication_flow; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.authentication_flow (
    id character varying(36) NOT NULL,
    alias character varying(255),
    description character varying(255),
    realm_id character varying(36),
    provider_id character varying(36) DEFAULT 'basic-flow'::character varying NOT NULL,
    top_level boolean DEFAULT false NOT NULL,
    built_in boolean DEFAULT false NOT NULL
);


ALTER TABLE public.authentication_flow OWNER TO admin;

--
-- Name: authenticator_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.authenticator_config (
    id character varying(36) NOT NULL,
    alias character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.authenticator_config OWNER TO admin;

--
-- Name: authenticator_config_entry; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.authenticator_config_entry (
    authenticator_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.authenticator_config_entry OWNER TO admin;

--
-- Name: authority; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.authority (
    name character varying(50) NOT NULL
);


ALTER TABLE public.authority OWNER TO admin;

--
-- Name: broker_link; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.broker_link (
    identity_provider character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL,
    broker_user_id character varying(255),
    broker_username character varying(255),
    token text,
    user_id character varying(255) NOT NULL
);


ALTER TABLE public.broker_link OWNER TO admin;

--
-- Name: client; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client (
    id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    full_scope_allowed boolean DEFAULT false NOT NULL,
    client_id character varying(255),
    not_before integer,
    public_client boolean DEFAULT false NOT NULL,
    secret character varying(255),
    base_url character varying(255),
    bearer_only boolean DEFAULT false NOT NULL,
    management_url character varying(255),
    surrogate_auth_required boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    protocol character varying(255),
    node_rereg_timeout integer DEFAULT 0,
    frontchannel_logout boolean DEFAULT false NOT NULL,
    consent_required boolean DEFAULT false NOT NULL,
    name character varying(255),
    service_accounts_enabled boolean DEFAULT false NOT NULL,
    client_authenticator_type character varying(255),
    root_url character varying(255),
    description character varying(255),
    registration_token character varying(255),
    standard_flow_enabled boolean DEFAULT true NOT NULL,
    implicit_flow_enabled boolean DEFAULT false NOT NULL,
    direct_access_grants_enabled boolean DEFAULT false NOT NULL,
    always_display_in_console boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client OWNER TO admin;

--
-- Name: client_attributes; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_attributes (
    client_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.client_attributes OWNER TO admin;

--
-- Name: client_auth_flow_bindings; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_auth_flow_bindings (
    client_id character varying(36) NOT NULL,
    flow_id character varying(36),
    binding_name character varying(255) NOT NULL
);


ALTER TABLE public.client_auth_flow_bindings OWNER TO admin;

--
-- Name: client_initial_access; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_initial_access (
    id character varying(36) NOT NULL,
    realm_id character varying(36) NOT NULL,
    "timestamp" integer,
    expiration integer,
    count integer,
    remaining_count integer
);


ALTER TABLE public.client_initial_access OWNER TO admin;

--
-- Name: client_node_registrations; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_node_registrations (
    client_id character varying(36) NOT NULL,
    value integer,
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_node_registrations OWNER TO admin;

--
-- Name: client_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_scope (
    id character varying(36) NOT NULL,
    name character varying(255),
    realm_id character varying(36),
    description character varying(255),
    protocol character varying(255)
);


ALTER TABLE public.client_scope OWNER TO admin;

--
-- Name: client_scope_attributes; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_scope_attributes (
    scope_id character varying(36) NOT NULL,
    value character varying(2048),
    name character varying(255) NOT NULL
);


ALTER TABLE public.client_scope_attributes OWNER TO admin;

--
-- Name: client_scope_client; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_scope_client (
    client_id character varying(255) NOT NULL,
    scope_id character varying(255) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.client_scope_client OWNER TO admin;

--
-- Name: client_scope_role_mapping; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.client_scope_role_mapping (
    scope_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.client_scope_role_mapping OWNER TO admin;

--
-- Name: component; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.component (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_id character varying(36),
    provider_id character varying(36),
    provider_type character varying(255),
    realm_id character varying(36),
    sub_type character varying(255)
);


ALTER TABLE public.component OWNER TO admin;

--
-- Name: component_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.component_config (
    id character varying(36) NOT NULL,
    component_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.component_config OWNER TO admin;

--
-- Name: composite_role; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.composite_role (
    composite character varying(36) NOT NULL,
    child_role character varying(36) NOT NULL
);


ALTER TABLE public.composite_role OWNER TO admin;

--
-- Name: credential; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    user_id character varying(36),
    created_date bigint,
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE public.credential OWNER TO admin;

--
-- Name: databasechangelog; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.databasechangelog (
    id character varying(255) NOT NULL,
    author character varying(255) NOT NULL,
    filename character varying(255) NOT NULL,
    dateexecuted timestamp without time zone NOT NULL,
    orderexecuted integer NOT NULL,
    exectype character varying(10) NOT NULL,
    md5sum character varying(35),
    description character varying(255),
    comments character varying(255),
    tag character varying(255),
    liquibase character varying(20),
    contexts character varying(255),
    labels character varying(255),
    deployment_id character varying(10)
);


ALTER TABLE public.databasechangelog OWNER TO admin;

--
-- Name: databasechangeloglock; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.databasechangeloglock (
    id integer NOT NULL,
    locked boolean NOT NULL,
    lockgranted timestamp without time zone,
    lockedby character varying(255)
);


ALTER TABLE public.databasechangeloglock OWNER TO admin;

--
-- Name: default_client_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.default_client_scope (
    realm_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL,
    default_scope boolean DEFAULT false NOT NULL
);


ALTER TABLE public.default_client_scope OWNER TO admin;

--
-- Name: event_entity; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.event_entity (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    details_json character varying(2550),
    error character varying(255),
    ip_address character varying(255),
    realm_id character varying(255),
    session_id character varying(255),
    event_time bigint,
    type character varying(255),
    user_id character varying(255),
    details_json_long_value text
);


ALTER TABLE public.event_entity OWNER TO admin;

--
-- Name: fed_user_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_attribute (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    value character varying(2024),
    long_value_hash bytea,
    long_value_hash_lower_case bytea,
    long_value text
);


ALTER TABLE public.fed_user_attribute OWNER TO admin;

--
-- Name: fed_user_consent; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.fed_user_consent OWNER TO admin;

--
-- Name: fed_user_consent_cl_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_consent_cl_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.fed_user_consent_cl_scope OWNER TO admin;

--
-- Name: fed_user_credential; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_credential (
    id character varying(36) NOT NULL,
    salt bytea,
    type character varying(255),
    created_date bigint,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36),
    user_label character varying(255),
    secret_data text,
    credential_data text,
    priority integer
);


ALTER TABLE public.fed_user_credential OWNER TO admin;

--
-- Name: fed_user_group_membership; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_group_membership OWNER TO admin;

--
-- Name: fed_user_required_action; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_required_action (
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_required_action OWNER TO admin;

--
-- Name: fed_user_role_mapping; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.fed_user_role_mapping (
    role_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    storage_provider_id character varying(36)
);


ALTER TABLE public.fed_user_role_mapping OWNER TO admin;

--
-- Name: federated_identity; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.federated_identity (
    identity_provider character varying(255) NOT NULL,
    realm_id character varying(36),
    federated_user_id character varying(255),
    federated_username character varying(255),
    token text,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_identity OWNER TO admin;

--
-- Name: federated_user; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.federated_user (
    id character varying(255) NOT NULL,
    storage_provider_id character varying(255),
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.federated_user OWNER TO admin;

--
-- Name: flyway_schema_history; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.flyway_schema_history (
    installed_rank integer NOT NULL,
    version character varying(50),
    description character varying(200) NOT NULL,
    type character varying(20) NOT NULL,
    script character varying(1000) NOT NULL,
    checksum integer,
    installed_by character varying(100) NOT NULL,
    installed_on timestamp without time zone DEFAULT now() NOT NULL,
    execution_time integer NOT NULL,
    success boolean NOT NULL
);


ALTER TABLE public.flyway_schema_history OWNER TO admin;

--
-- Name: group_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.group_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_attribute OWNER TO admin;

--
-- Name: group_role_mapping; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.group_role_mapping (
    role_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.group_role_mapping OWNER TO admin;

--
-- Name: identity_provider; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.identity_provider (
    internal_id character varying(36) NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    provider_alias character varying(255),
    provider_id character varying(255),
    store_token boolean DEFAULT false NOT NULL,
    authenticate_by_default boolean DEFAULT false NOT NULL,
    realm_id character varying(36),
    add_token_role boolean DEFAULT true NOT NULL,
    trust_email boolean DEFAULT false NOT NULL,
    first_broker_login_flow_id character varying(36),
    post_broker_login_flow_id character varying(36),
    provider_display_name character varying(255),
    link_only boolean DEFAULT false NOT NULL,
    organization_id character varying(255),
    hide_on_login boolean DEFAULT false
);


ALTER TABLE public.identity_provider OWNER TO admin;

--
-- Name: identity_provider_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.identity_provider_config (
    identity_provider_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.identity_provider_config OWNER TO admin;

--
-- Name: identity_provider_mapper; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.identity_provider_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    idp_alias character varying(255) NOT NULL,
    idp_mapper_name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.identity_provider_mapper OWNER TO admin;

--
-- Name: idp_mapper_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.idp_mapper_config (
    idp_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.idp_mapper_config OWNER TO admin;

--
-- Name: keycloak_group; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.keycloak_group (
    id character varying(36) NOT NULL,
    name character varying(255),
    parent_group character varying(36) NOT NULL,
    realm_id character varying(36),
    type integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.keycloak_group OWNER TO admin;

--
-- Name: keycloak_role; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.keycloak_role (
    id character varying(36) NOT NULL,
    client_realm_constraint character varying(255),
    client_role boolean DEFAULT false NOT NULL,
    description character varying(255),
    name character varying(255),
    realm_id character varying(255),
    client character varying(36),
    realm character varying(36)
);


ALTER TABLE public.keycloak_role OWNER TO admin;

--
-- Name: message; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.message (
    id bigint NOT NULL,
    created_date timestamp(6) with time zone,
    last_modified_date timestamp(6) with time zone
);


ALTER TABLE public.message OWNER TO admin;

--
-- Name: message_sequence; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.message_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.message_sequence OWNER TO admin;

--
-- Name: migration_model; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.migration_model (
    id character varying(36) NOT NULL,
    version character varying(36),
    update_time bigint DEFAULT 0 NOT NULL
);


ALTER TABLE public.migration_model OWNER TO admin;

--
-- Name: offline_client_session; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.offline_client_session (
    user_session_id character varying(36) NOT NULL,
    client_id character varying(255) NOT NULL,
    offline_flag character varying(4) NOT NULL,
    "timestamp" integer,
    data text,
    client_storage_provider character varying(36) DEFAULT 'local'::character varying NOT NULL,
    external_client_id character varying(255) DEFAULT 'local'::character varying NOT NULL,
    version integer DEFAULT 0
);


ALTER TABLE public.offline_client_session OWNER TO admin;

--
-- Name: offline_user_session; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.offline_user_session (
    user_session_id character varying(36) NOT NULL,
    user_id character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    created_on integer NOT NULL,
    offline_flag character varying(4) NOT NULL,
    data text,
    last_session_refresh integer DEFAULT 0 NOT NULL,
    broker_session_id character varying(1024),
    version integer DEFAULT 0
);


ALTER TABLE public.offline_user_session OWNER TO admin;

--
-- Name: org; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.org (
    id character varying(255) NOT NULL,
    enabled boolean NOT NULL,
    realm_id character varying(255) NOT NULL,
    group_id character varying(255) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(4000),
    alias character varying(255) NOT NULL,
    redirect_url character varying(2048)
);


ALTER TABLE public.org OWNER TO admin;

--
-- Name: org_domain; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.org_domain (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    verified boolean NOT NULL,
    org_id character varying(255) NOT NULL
);


ALTER TABLE public.org_domain OWNER TO admin;

--
-- Name: policy_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.policy_config (
    policy_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value text
);


ALTER TABLE public.policy_config OWNER TO admin;

--
-- Name: protocol_mapper; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.protocol_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    protocol character varying(255) NOT NULL,
    protocol_mapper_name character varying(255) NOT NULL,
    client_id character varying(36),
    client_scope_id character varying(36)
);


ALTER TABLE public.protocol_mapper OWNER TO admin;

--
-- Name: protocol_mapper_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.protocol_mapper_config (
    protocol_mapper_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.protocol_mapper_config OWNER TO admin;

--
-- Name: realm; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm (
    id character varying(36) NOT NULL,
    access_code_lifespan integer,
    user_action_lifespan integer,
    access_token_lifespan integer,
    account_theme character varying(255),
    admin_theme character varying(255),
    email_theme character varying(255),
    enabled boolean DEFAULT false NOT NULL,
    events_enabled boolean DEFAULT false NOT NULL,
    events_expiration bigint,
    login_theme character varying(255),
    name character varying(255),
    not_before integer,
    password_policy character varying(2550),
    registration_allowed boolean DEFAULT false NOT NULL,
    remember_me boolean DEFAULT false NOT NULL,
    reset_password_allowed boolean DEFAULT false NOT NULL,
    social boolean DEFAULT false NOT NULL,
    ssl_required character varying(255),
    sso_idle_timeout integer,
    sso_max_lifespan integer,
    update_profile_on_soc_login boolean DEFAULT false NOT NULL,
    verify_email boolean DEFAULT false NOT NULL,
    master_admin_client character varying(36),
    login_lifespan integer,
    internationalization_enabled boolean DEFAULT false NOT NULL,
    default_locale character varying(255),
    reg_email_as_username boolean DEFAULT false NOT NULL,
    admin_events_enabled boolean DEFAULT false NOT NULL,
    admin_events_details_enabled boolean DEFAULT false NOT NULL,
    edit_username_allowed boolean DEFAULT false NOT NULL,
    otp_policy_counter integer DEFAULT 0,
    otp_policy_window integer DEFAULT 1,
    otp_policy_period integer DEFAULT 30,
    otp_policy_digits integer DEFAULT 6,
    otp_policy_alg character varying(36) DEFAULT 'HmacSHA1'::character varying,
    otp_policy_type character varying(36) DEFAULT 'totp'::character varying,
    browser_flow character varying(36),
    registration_flow character varying(36),
    direct_grant_flow character varying(36),
    reset_credentials_flow character varying(36),
    client_auth_flow character varying(36),
    offline_session_idle_timeout integer DEFAULT 0,
    revoke_refresh_token boolean DEFAULT false NOT NULL,
    access_token_life_implicit integer DEFAULT 0,
    login_with_email_allowed boolean DEFAULT true NOT NULL,
    duplicate_emails_allowed boolean DEFAULT false NOT NULL,
    docker_auth_flow character varying(36),
    refresh_token_max_reuse integer DEFAULT 0,
    allow_user_managed_access boolean DEFAULT false NOT NULL,
    sso_max_lifespan_remember_me integer DEFAULT 0 NOT NULL,
    sso_idle_timeout_remember_me integer DEFAULT 0 NOT NULL,
    default_role character varying(255)
);


ALTER TABLE public.realm OWNER TO admin;

--
-- Name: realm_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_attribute (
    name character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL,
    value text
);


ALTER TABLE public.realm_attribute OWNER TO admin;

--
-- Name: realm_default_groups; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_default_groups (
    realm_id character varying(36) NOT NULL,
    group_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_default_groups OWNER TO admin;

--
-- Name: realm_enabled_event_types; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_enabled_event_types (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_enabled_event_types OWNER TO admin;

--
-- Name: realm_events_listeners; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_events_listeners (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_events_listeners OWNER TO admin;

--
-- Name: realm_localizations; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_localizations (
    realm_id character varying(255) NOT NULL,
    locale character varying(255) NOT NULL,
    texts text NOT NULL
);


ALTER TABLE public.realm_localizations OWNER TO admin;

--
-- Name: realm_required_credential; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_required_credential (
    type character varying(255) NOT NULL,
    form_label character varying(255),
    input boolean DEFAULT false NOT NULL,
    secret boolean DEFAULT false NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.realm_required_credential OWNER TO admin;

--
-- Name: realm_smtp_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_smtp_config (
    realm_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.realm_smtp_config OWNER TO admin;

--
-- Name: realm_supported_locales; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.realm_supported_locales (
    realm_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.realm_supported_locales OWNER TO admin;

--
-- Name: redirect_uris; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.redirect_uris (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.redirect_uris OWNER TO admin;

--
-- Name: required_action_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.required_action_config (
    required_action_id character varying(36) NOT NULL,
    value text,
    name character varying(255) NOT NULL
);


ALTER TABLE public.required_action_config OWNER TO admin;

--
-- Name: required_action_provider; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.required_action_provider (
    id character varying(36) NOT NULL,
    alias character varying(255),
    name character varying(255),
    realm_id character varying(36),
    enabled boolean DEFAULT false NOT NULL,
    default_action boolean DEFAULT false NOT NULL,
    provider_id character varying(255),
    priority integer
);


ALTER TABLE public.required_action_provider OWNER TO admin;

--
-- Name: resource_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_attribute (
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255),
    resource_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_attribute OWNER TO admin;

--
-- Name: resource_policy; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_policy (
    resource_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_policy OWNER TO admin;

--
-- Name: resource_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_scope (
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.resource_scope OWNER TO admin;

--
-- Name: resource_server; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_server (
    id character varying(36) NOT NULL,
    allow_rs_remote_mgmt boolean DEFAULT false NOT NULL,
    policy_enforce_mode smallint NOT NULL,
    decision_strategy smallint DEFAULT 1 NOT NULL
);


ALTER TABLE public.resource_server OWNER TO admin;

--
-- Name: resource_server_perm_ticket; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_server_perm_ticket (
    id character varying(36) NOT NULL,
    owner character varying(255) NOT NULL,
    requester character varying(255) NOT NULL,
    created_timestamp bigint NOT NULL,
    granted_timestamp bigint,
    resource_id character varying(36) NOT NULL,
    scope_id character varying(36),
    resource_server_id character varying(36) NOT NULL,
    policy_id character varying(36)
);


ALTER TABLE public.resource_server_perm_ticket OWNER TO admin;

--
-- Name: resource_server_policy; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_server_policy (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    description character varying(255),
    type character varying(255) NOT NULL,
    decision_strategy smallint,
    logic smallint,
    resource_server_id character varying(36) NOT NULL,
    owner character varying(255)
);


ALTER TABLE public.resource_server_policy OWNER TO admin;

--
-- Name: resource_server_resource; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_server_resource (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    type character varying(255),
    icon_uri character varying(255),
    owner character varying(255) NOT NULL,
    resource_server_id character varying(36) NOT NULL,
    owner_managed_access boolean DEFAULT false NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_resource OWNER TO admin;

--
-- Name: resource_server_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_server_scope (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    icon_uri character varying(255),
    resource_server_id character varying(36) NOT NULL,
    display_name character varying(255)
);


ALTER TABLE public.resource_server_scope OWNER TO admin;

--
-- Name: resource_uris; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.resource_uris (
    resource_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.resource_uris OWNER TO admin;

--
-- Name: revoked_token; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.revoked_token (
    id character varying(255) NOT NULL,
    expire bigint NOT NULL
);


ALTER TABLE public.revoked_token OWNER TO admin;

--
-- Name: role_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.role_attribute (
    id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    value character varying(255)
);


ALTER TABLE public.role_attribute OWNER TO admin;

--
-- Name: scope_mapping; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.scope_mapping (
    client_id character varying(36) NOT NULL,
    role_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_mapping OWNER TO admin;

--
-- Name: scope_policy; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.scope_policy (
    scope_id character varying(36) NOT NULL,
    policy_id character varying(36) NOT NULL
);


ALTER TABLE public.scope_policy OWNER TO admin;

--
-- Name: user_; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_ (
    friend_request_setting smallint,
    profile_visibility boolean,
    created_date timestamp(6) with time zone,
    id bigint NOT NULL,
    last_active timestamp(6) with time zone,
    last_modified_date timestamp(6) with time zone,
    last_seen timestamp(6) with time zone,
    public_id uuid,
    bio character varying(255),
    email character varying(255),
    firstname character varying(255),
    gender character varying(255),
    image_url character varying(255),
    last_seen_setting character varying(255),
    lastname character varying(255),
    CONSTRAINT user__friend_request_setting_check CHECK (((friend_request_setting >= 0) AND (friend_request_setting <= 2))),
    CONSTRAINT user__gender_check CHECK (((gender)::text = ANY ((ARRAY['MALE'::character varying, 'FEMALE'::character varying])::text[]))),
    CONSTRAINT user__last_seen_setting_check CHECK (((last_seen_setting)::text = ANY ((ARRAY['EVERYONE'::character varying, 'CONTACT_ONLY'::character varying, 'NOBODY'::character varying])::text[])))
);


ALTER TABLE public.user_ OWNER TO admin;

--
-- Name: user_attribute; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_attribute (
    name character varying(255) NOT NULL,
    value character varying(255),
    user_id character varying(36) NOT NULL,
    id character varying(36) DEFAULT 'sybase-needs-something-here'::character varying NOT NULL,
    long_value_hash bytea,
    long_value_hash_lower_case bytea,
    long_value text
);


ALTER TABLE public.user_attribute OWNER TO admin;

--
-- Name: user_authority; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_authority (
    user_id bigint NOT NULL,
    authority_name character varying(50) NOT NULL
);


ALTER TABLE public.user_authority OWNER TO admin;

--
-- Name: user_consent; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_consent (
    id character varying(36) NOT NULL,
    client_id character varying(255),
    user_id character varying(36) NOT NULL,
    created_date bigint,
    last_updated_date bigint,
    client_storage_provider character varying(36),
    external_client_id character varying(255)
);


ALTER TABLE public.user_consent OWNER TO admin;

--
-- Name: user_consent_client_scope; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_consent_client_scope (
    user_consent_id character varying(36) NOT NULL,
    scope_id character varying(36) NOT NULL
);


ALTER TABLE public.user_consent_client_scope OWNER TO admin;

--
-- Name: user_entity; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_entity (
    id character varying(36) NOT NULL,
    email character varying(255),
    email_constraint character varying(255),
    email_verified boolean DEFAULT false NOT NULL,
    enabled boolean DEFAULT false NOT NULL,
    federation_link character varying(255),
    first_name character varying(255),
    last_name character varying(255),
    realm_id character varying(255),
    username character varying(255),
    created_timestamp bigint,
    service_account_client_link character varying(255),
    not_before integer DEFAULT 0 NOT NULL
);


ALTER TABLE public.user_entity OWNER TO admin;

--
-- Name: user_federation_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_federation_config (
    user_federation_provider_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_config OWNER TO admin;

--
-- Name: user_federation_mapper; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_federation_mapper (
    id character varying(36) NOT NULL,
    name character varying(255) NOT NULL,
    federation_provider_id character varying(36) NOT NULL,
    federation_mapper_type character varying(255) NOT NULL,
    realm_id character varying(36) NOT NULL
);


ALTER TABLE public.user_federation_mapper OWNER TO admin;

--
-- Name: user_federation_mapper_config; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_federation_mapper_config (
    user_federation_mapper_id character varying(36) NOT NULL,
    value character varying(255),
    name character varying(255) NOT NULL
);


ALTER TABLE public.user_federation_mapper_config OWNER TO admin;

--
-- Name: user_federation_provider; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_federation_provider (
    id character varying(36) NOT NULL,
    changed_sync_period integer,
    display_name character varying(255),
    full_sync_period integer,
    last_sync integer,
    priority integer,
    provider_name character varying(255),
    realm_id character varying(36)
);


ALTER TABLE public.user_federation_provider OWNER TO admin;

--
-- Name: user_group_membership; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_group_membership (
    group_id character varying(36) NOT NULL,
    user_id character varying(36) NOT NULL,
    membership_type character varying(255) NOT NULL
);


ALTER TABLE public.user_group_membership OWNER TO admin;

--
-- Name: user_required_action; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_required_action (
    user_id character varying(36) NOT NULL,
    required_action character varying(255) DEFAULT ' '::character varying NOT NULL
);


ALTER TABLE public.user_required_action OWNER TO admin;

--
-- Name: user_role_mapping; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.user_role_mapping (
    role_id character varying(255) NOT NULL,
    user_id character varying(36) NOT NULL
);


ALTER TABLE public.user_role_mapping OWNER TO admin;

--
-- Name: user_sequence; Type: SEQUENCE; Schema: public; Owner: admin
--

CREATE SEQUENCE public.user_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER SEQUENCE public.user_sequence OWNER TO admin;

--
-- Name: username_login_failure; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.username_login_failure (
    realm_id character varying(36) NOT NULL,
    username character varying(255) NOT NULL,
    failed_login_not_before integer,
    last_failure bigint,
    last_ip_failure character varying(255),
    num_failures integer
);


ALTER TABLE public.username_login_failure OWNER TO admin;

--
-- Name: web_origins; Type: TABLE; Schema: public; Owner: admin
--

CREATE TABLE public.web_origins (
    client_id character varying(36) NOT NULL,
    value character varying(255) NOT NULL
);


ALTER TABLE public.web_origins OWNER TO admin;

--
-- Data for Name: admin_event_entity; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.admin_event_entity (id, admin_event_time, realm_id, operation_type, auth_realm_id, auth_client_id, auth_user_id, ip_address, resource_path, representation, error, resource_type) FROM stdin;
\.


--
-- Data for Name: associated_policy; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.associated_policy (policy_id, associated_policy_id) FROM stdin;
\.


--
-- Data for Name: authentication_execution; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authentication_execution (id, alias, authenticator, realm_id, flow_id, requirement, priority, authenticator_flow, auth_flow_id, auth_config) FROM stdin;
c4f86932-e830-4d29-be73-297f74406828	\N	auth-cookie	863411db-f685-4e36-9210-ef7892793272	6650ceb0-4f67-422a-abaa-858ad9cc7ced	2	10	f	\N	\N
f51696bb-76db-46b1-9f2d-6c96e1556e10	\N	auth-spnego	863411db-f685-4e36-9210-ef7892793272	6650ceb0-4f67-422a-abaa-858ad9cc7ced	3	20	f	\N	\N
d67f204c-2564-4142-9fd9-dc16f59e5bf0	\N	identity-provider-redirector	863411db-f685-4e36-9210-ef7892793272	6650ceb0-4f67-422a-abaa-858ad9cc7ced	2	25	f	\N	\N
0ffeb780-d358-48c3-b80b-f297f6f12a59	\N	\N	863411db-f685-4e36-9210-ef7892793272	6650ceb0-4f67-422a-abaa-858ad9cc7ced	2	30	t	6bcfc2b8-4c3c-4afe-8291-ca5c59fd36b0	\N
5865e2b6-9db6-4a98-b98d-b8bec692d125	\N	auth-username-password-form	863411db-f685-4e36-9210-ef7892793272	6bcfc2b8-4c3c-4afe-8291-ca5c59fd36b0	0	10	f	\N	\N
1ef5e26e-e98f-4864-854f-c1ef05b1a9e7	\N	\N	863411db-f685-4e36-9210-ef7892793272	6bcfc2b8-4c3c-4afe-8291-ca5c59fd36b0	1	20	t	51affe51-10ec-4865-81c3-bbb4290b840c	\N
5d58264e-82a3-4b13-ad68-fabd74cbc901	\N	conditional-user-configured	863411db-f685-4e36-9210-ef7892793272	51affe51-10ec-4865-81c3-bbb4290b840c	0	10	f	\N	\N
1986ac41-0d9e-4f9f-a3dc-df104d04b3bd	\N	auth-otp-form	863411db-f685-4e36-9210-ef7892793272	51affe51-10ec-4865-81c3-bbb4290b840c	0	20	f	\N	\N
05cb719a-a48e-4d96-9741-89e6e913a5c8	\N	direct-grant-validate-username	863411db-f685-4e36-9210-ef7892793272	15cbcb5c-9138-4459-a021-b4452695744a	0	10	f	\N	\N
3f5dc2e4-0cb1-4bb9-b384-19013a0e8c31	\N	direct-grant-validate-password	863411db-f685-4e36-9210-ef7892793272	15cbcb5c-9138-4459-a021-b4452695744a	0	20	f	\N	\N
a30100a6-e20b-4737-b18d-21723c881918	\N	\N	863411db-f685-4e36-9210-ef7892793272	15cbcb5c-9138-4459-a021-b4452695744a	1	30	t	fa9da093-4f69-4063-8c4f-ce6e17a4c656	\N
5b9554c1-6bb1-4f7c-bb9c-1e03124f189b	\N	conditional-user-configured	863411db-f685-4e36-9210-ef7892793272	fa9da093-4f69-4063-8c4f-ce6e17a4c656	0	10	f	\N	\N
cb17b86c-8507-432a-83f0-26bd0f230d0f	\N	direct-grant-validate-otp	863411db-f685-4e36-9210-ef7892793272	fa9da093-4f69-4063-8c4f-ce6e17a4c656	0	20	f	\N	\N
5982d83d-d70a-45c6-a296-d99f0ec0c90a	\N	registration-page-form	863411db-f685-4e36-9210-ef7892793272	6c2990af-3f0e-4cc8-aa1b-1743f5b9e956	0	10	t	fefffc8c-d402-40e0-b3c6-22717e5966e6	\N
6f47c32e-dc2c-461d-93ba-a1d3362cef06	\N	registration-user-creation	863411db-f685-4e36-9210-ef7892793272	fefffc8c-d402-40e0-b3c6-22717e5966e6	0	20	f	\N	\N
c21457b4-3e2f-4282-ae2a-fe3afe1e2d6c	\N	registration-password-action	863411db-f685-4e36-9210-ef7892793272	fefffc8c-d402-40e0-b3c6-22717e5966e6	0	50	f	\N	\N
ced00447-9ae0-4405-8d31-3ee17659dbf2	\N	registration-recaptcha-action	863411db-f685-4e36-9210-ef7892793272	fefffc8c-d402-40e0-b3c6-22717e5966e6	3	60	f	\N	\N
a9742228-3240-4a00-876c-8272246a6894	\N	registration-terms-and-conditions	863411db-f685-4e36-9210-ef7892793272	fefffc8c-d402-40e0-b3c6-22717e5966e6	3	70	f	\N	\N
63bc6b48-f1a8-4f9d-8c63-ad914b4dfaec	\N	reset-credentials-choose-user	863411db-f685-4e36-9210-ef7892793272	8cdf9d22-3eae-4235-8a21-13a0b3c0e3d3	0	10	f	\N	\N
3c048cc4-795d-4d50-ba54-b4093134a79b	\N	reset-credential-email	863411db-f685-4e36-9210-ef7892793272	8cdf9d22-3eae-4235-8a21-13a0b3c0e3d3	0	20	f	\N	\N
ecf310db-4e01-4162-ac80-333c3fabc974	\N	reset-password	863411db-f685-4e36-9210-ef7892793272	8cdf9d22-3eae-4235-8a21-13a0b3c0e3d3	0	30	f	\N	\N
b482026f-e83e-4fae-aeb5-5d75080ec7d1	\N	\N	863411db-f685-4e36-9210-ef7892793272	8cdf9d22-3eae-4235-8a21-13a0b3c0e3d3	1	40	t	a0a44ea6-d391-4801-b66a-2766075133db	\N
8cc43536-142c-4039-94d8-b8b078cbcc9c	\N	conditional-user-configured	863411db-f685-4e36-9210-ef7892793272	a0a44ea6-d391-4801-b66a-2766075133db	0	10	f	\N	\N
60227c0c-e2fe-45cb-9b9e-ac9ca14ef1b6	\N	reset-otp	863411db-f685-4e36-9210-ef7892793272	a0a44ea6-d391-4801-b66a-2766075133db	0	20	f	\N	\N
a601129e-6d03-4a42-9271-6ce946cb4c78	\N	client-secret	863411db-f685-4e36-9210-ef7892793272	39af4770-19a1-4c3c-9a4b-d48ff0313efa	2	10	f	\N	\N
23ebcf9f-a2b8-4b63-8510-0921345ab6d5	\N	client-jwt	863411db-f685-4e36-9210-ef7892793272	39af4770-19a1-4c3c-9a4b-d48ff0313efa	2	20	f	\N	\N
8c46982e-88df-4dd0-b6dd-8cce50517838	\N	client-secret-jwt	863411db-f685-4e36-9210-ef7892793272	39af4770-19a1-4c3c-9a4b-d48ff0313efa	2	30	f	\N	\N
ef565243-319d-4698-bba8-b7f2b6d773d0	\N	client-x509	863411db-f685-4e36-9210-ef7892793272	39af4770-19a1-4c3c-9a4b-d48ff0313efa	2	40	f	\N	\N
332c5e10-64c6-4595-80cf-2ceebcef2f83	\N	idp-review-profile	863411db-f685-4e36-9210-ef7892793272	b73e4239-23d0-4044-8556-09e156ce1c71	0	10	f	\N	8ba548f6-eb57-407b-a5a5-02d009459457
b150238b-a788-4ec7-930e-39e47d2f1600	\N	\N	863411db-f685-4e36-9210-ef7892793272	b73e4239-23d0-4044-8556-09e156ce1c71	0	20	t	0914c41f-57d8-416b-9035-43b87a0dd46c	\N
e66f864e-1abb-4d6b-a08d-3a883a2beb33	\N	idp-create-user-if-unique	863411db-f685-4e36-9210-ef7892793272	0914c41f-57d8-416b-9035-43b87a0dd46c	2	10	f	\N	aaa68215-8cf0-430a-8c36-fb32d2d5a875
5c628f09-165f-4efb-aa09-346c7eb13ef4	\N	\N	863411db-f685-4e36-9210-ef7892793272	0914c41f-57d8-416b-9035-43b87a0dd46c	2	20	t	456ff76f-79a8-4422-8aab-ebe4d8b53aa4	\N
5bfbfbef-7532-4356-ab0a-6d8163f2142a	\N	idp-confirm-link	863411db-f685-4e36-9210-ef7892793272	456ff76f-79a8-4422-8aab-ebe4d8b53aa4	0	10	f	\N	\N
97042faa-374b-401d-9b9a-ac14517c13a9	\N	\N	863411db-f685-4e36-9210-ef7892793272	456ff76f-79a8-4422-8aab-ebe4d8b53aa4	0	20	t	eb07c847-5e82-4322-8acf-88271c463abd	\N
3b898a1d-6300-47a5-903f-f0cb714ce734	\N	idp-email-verification	863411db-f685-4e36-9210-ef7892793272	eb07c847-5e82-4322-8acf-88271c463abd	2	10	f	\N	\N
3c892130-acd4-4eb7-a71b-c06d056cd65e	\N	\N	863411db-f685-4e36-9210-ef7892793272	eb07c847-5e82-4322-8acf-88271c463abd	2	20	t	d245292f-fc10-4797-9326-e268289f8b35	\N
87195ff6-63b2-43cc-b8c0-e4cefab4445c	\N	idp-username-password-form	863411db-f685-4e36-9210-ef7892793272	d245292f-fc10-4797-9326-e268289f8b35	0	10	f	\N	\N
318e0e42-1c98-4823-99dc-f6eafe0e02d2	\N	\N	863411db-f685-4e36-9210-ef7892793272	d245292f-fc10-4797-9326-e268289f8b35	1	20	t	278fb87f-5423-4e55-9be7-211d1a4d3e3f	\N
166941d6-5ace-46e6-a96e-510464a385b0	\N	conditional-user-configured	863411db-f685-4e36-9210-ef7892793272	278fb87f-5423-4e55-9be7-211d1a4d3e3f	0	10	f	\N	\N
7a9920b8-ab6a-4746-b2fa-03584b05fffc	\N	auth-otp-form	863411db-f685-4e36-9210-ef7892793272	278fb87f-5423-4e55-9be7-211d1a4d3e3f	0	20	f	\N	\N
80053896-1386-45ac-8e9b-a27138d3367e	\N	http-basic-authenticator	863411db-f685-4e36-9210-ef7892793272	c11b97e5-a541-495c-aa02-22235b487442	0	10	f	\N	\N
49cd5115-474e-4fca-8c83-592a278d8f55	\N	docker-http-basic-authenticator	863411db-f685-4e36-9210-ef7892793272	d3f88d58-4bd3-4e50-9c39-20510841d3f9	0	10	f	\N	\N
9cee715b-8b62-4e69-b7c5-96c60f66f3b9	\N	idp-email-verification	83b6664d-539e-4bed-a376-685d50e40b98	10e5cbf9-6d70-4671-8b7c-25a13f3b30b6	2	10	f	\N	\N
59a613b5-6bd6-46bf-aec9-5e5e293a6b3d	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	10e5cbf9-6d70-4671-8b7c-25a13f3b30b6	2	20	t	9072b1a2-a2d2-426d-9fe7-bfb40b87cfab	\N
ae73dda3-a1ae-4e66-88b3-72478ee270b4	\N	conditional-user-configured	83b6664d-539e-4bed-a376-685d50e40b98	936ccde4-b63d-4262-b8a6-af7d521dc3aa	0	10	f	\N	\N
dddb739c-1cec-449c-ae30-11c7361d220d	\N	auth-otp-form	83b6664d-539e-4bed-a376-685d50e40b98	936ccde4-b63d-4262-b8a6-af7d521dc3aa	0	20	f	\N	\N
37447614-4466-43aa-9742-783a04dbfa63	\N	conditional-user-configured	83b6664d-539e-4bed-a376-685d50e40b98	dc1e8d98-e26a-437c-91a3-f8ca8ed2dd59	0	10	f	\N	\N
7fdbd50f-cc54-4600-984f-b3fa58f01db5	\N	organization	83b6664d-539e-4bed-a376-685d50e40b98	dc1e8d98-e26a-437c-91a3-f8ca8ed2dd59	2	20	f	\N	\N
df75476a-d06e-4b49-877f-422ceeb245c6	\N	conditional-user-configured	83b6664d-539e-4bed-a376-685d50e40b98	5b2379d9-11fb-476f-8530-5a91ca18e364	0	10	f	\N	\N
5f9a4d9b-6bde-4a79-b462-633f6f4dd6b7	\N	direct-grant-validate-otp	83b6664d-539e-4bed-a376-685d50e40b98	5b2379d9-11fb-476f-8530-5a91ca18e364	0	20	f	\N	\N
7ebce053-453c-4e92-9465-ad05cb3d1ad0	\N	conditional-user-configured	83b6664d-539e-4bed-a376-685d50e40b98	fcaef89a-b301-4a9d-86b6-f570c43d07ad	0	10	f	\N	\N
a597c4c8-37b3-4709-a27b-d20a7f769482	\N	idp-add-organization-member	83b6664d-539e-4bed-a376-685d50e40b98	fcaef89a-b301-4a9d-86b6-f570c43d07ad	0	20	f	\N	\N
a55cea5e-7792-4f3b-ba0d-f9fe9f90c8d5	\N	conditional-user-configured	83b6664d-539e-4bed-a376-685d50e40b98	ceed43c7-5d3f-4eb1-b878-2314aef46ebf	0	10	f	\N	\N
fcf9e412-9054-42db-bb26-77e2b33939e3	\N	auth-otp-form	83b6664d-539e-4bed-a376-685d50e40b98	ceed43c7-5d3f-4eb1-b878-2314aef46ebf	0	20	f	\N	\N
f9d4e219-3613-42aa-a781-8f4f1acf6e9a	\N	idp-confirm-link	83b6664d-539e-4bed-a376-685d50e40b98	a9376912-6f72-4749-a4e3-6ce74c0ec76a	0	10	f	\N	\N
9693911c-8535-4166-b357-b3d6e147fc1a	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	a9376912-6f72-4749-a4e3-6ce74c0ec76a	0	20	t	10e5cbf9-6d70-4671-8b7c-25a13f3b30b6	\N
0ba65467-2a68-4958-878c-578d8d8a62f6	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	291b3c02-9178-4997-92a8-2c77b4a762da	1	10	t	dc1e8d98-e26a-437c-91a3-f8ca8ed2dd59	\N
5a6a5a39-3b74-4a9d-9fc0-c2479002c082	\N	conditional-user-configured	83b6664d-539e-4bed-a376-685d50e40b98	510a55f8-b4a5-47e9-ac56-b60fc6ed8676	0	10	f	\N	\N
cb2e140e-0b94-4769-9eae-acb04f5405c1	\N	reset-otp	83b6664d-539e-4bed-a376-685d50e40b98	510a55f8-b4a5-47e9-ac56-b60fc6ed8676	0	20	f	\N	\N
e67f6c30-0296-4f7f-8774-e21323165c28	\N	idp-create-user-if-unique	83b6664d-539e-4bed-a376-685d50e40b98	677d2578-fe86-4de9-8a18-860f8d89d4f3	2	10	f	\N	7805e560-dd03-4e34-a6ff-1784f53fecad
34d0d656-b5bb-47d8-b95d-c7191be4ec0f	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	677d2578-fe86-4de9-8a18-860f8d89d4f3	2	20	t	a9376912-6f72-4749-a4e3-6ce74c0ec76a	\N
10039e25-81c0-42f3-8265-64e7f44a2d7e	\N	idp-username-password-form	83b6664d-539e-4bed-a376-685d50e40b98	9072b1a2-a2d2-426d-9fe7-bfb40b87cfab	0	10	f	\N	\N
b42f97eb-d2b7-4319-a962-88a55ccf9402	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	9072b1a2-a2d2-426d-9fe7-bfb40b87cfab	1	20	t	ceed43c7-5d3f-4eb1-b878-2314aef46ebf	\N
cf263c3f-5ff8-4ce9-9737-aefaa4696a1d	\N	auth-cookie	83b6664d-539e-4bed-a376-685d50e40b98	801e570d-bc15-45e3-9f8e-afe9f8f28dec	2	10	f	\N	\N
7e204d55-d4f6-4db0-b8c5-38206072ca20	\N	auth-spnego	83b6664d-539e-4bed-a376-685d50e40b98	801e570d-bc15-45e3-9f8e-afe9f8f28dec	3	20	f	\N	\N
8946aec4-4822-4339-84fb-09817bdf9116	\N	identity-provider-redirector	83b6664d-539e-4bed-a376-685d50e40b98	801e570d-bc15-45e3-9f8e-afe9f8f28dec	2	25	f	\N	\N
6616b0de-f9be-4c7a-b3fa-2dcc240164b6	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	801e570d-bc15-45e3-9f8e-afe9f8f28dec	2	26	t	291b3c02-9178-4997-92a8-2c77b4a762da	\N
5a2e0f55-9cf1-4348-aadc-75c34cb5f65e	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	801e570d-bc15-45e3-9f8e-afe9f8f28dec	2	30	t	4c7e2a97-05a5-4499-99d5-b0fe14711095	\N
297f0da1-e444-4522-80e5-25f37841a2ee	\N	client-secret	83b6664d-539e-4bed-a376-685d50e40b98	05607ab5-8839-424c-b1e7-7f0ccad2063e	2	10	f	\N	\N
145e5ada-6a27-4c9f-afa3-590db1e93860	\N	client-jwt	83b6664d-539e-4bed-a376-685d50e40b98	05607ab5-8839-424c-b1e7-7f0ccad2063e	2	20	f	\N	\N
e94f9e35-defe-43c7-b2e7-48eb79cb85a4	\N	client-secret-jwt	83b6664d-539e-4bed-a376-685d50e40b98	05607ab5-8839-424c-b1e7-7f0ccad2063e	2	30	f	\N	\N
ba2e8567-53d2-45b3-a663-89a2efbed9d2	\N	client-x509	83b6664d-539e-4bed-a376-685d50e40b98	05607ab5-8839-424c-b1e7-7f0ccad2063e	2	40	f	\N	\N
d550fbcb-f5ab-45f2-8b24-38c4a65e8215	\N	direct-grant-validate-username	83b6664d-539e-4bed-a376-685d50e40b98	d7b8234b-3d04-4263-ac07-79794e3fb8c0	0	10	f	\N	\N
9dae4cc6-135d-4c0d-aafe-663793554165	\N	direct-grant-validate-password	83b6664d-539e-4bed-a376-685d50e40b98	d7b8234b-3d04-4263-ac07-79794e3fb8c0	0	20	f	\N	\N
6e5f075b-b8cb-4c99-97b1-48819fc71e26	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	d7b8234b-3d04-4263-ac07-79794e3fb8c0	1	30	t	5b2379d9-11fb-476f-8530-5a91ca18e364	\N
e4f9cbbd-16ca-4b69-8927-4bfc1540aa5f	\N	docker-http-basic-authenticator	83b6664d-539e-4bed-a376-685d50e40b98	08caf47c-2dda-42d5-a33d-3df1271e703c	0	10	f	\N	\N
dbc94f83-b041-4e55-81f2-09972b6272d8	\N	idp-review-profile	83b6664d-539e-4bed-a376-685d50e40b98	668883be-9b51-42f6-9e35-cb35d7961852	0	10	f	\N	135d8612-fdf4-4acf-abf8-e25c0565ebf6
e5ba18c5-e94f-473e-8f70-15579539a32b	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	668883be-9b51-42f6-9e35-cb35d7961852	0	20	t	677d2578-fe86-4de9-8a18-860f8d89d4f3	\N
8fe827b1-c814-4bdd-8632-9e9c5f7db1ce	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	668883be-9b51-42f6-9e35-cb35d7961852	1	50	t	fcaef89a-b301-4a9d-86b6-f570c43d07ad	\N
561ebf2d-66e6-44d2-9124-283efa53c75e	\N	auth-username-password-form	83b6664d-539e-4bed-a376-685d50e40b98	4c7e2a97-05a5-4499-99d5-b0fe14711095	0	10	f	\N	\N
ca9891cc-cb2a-4510-b3b3-84b7392e06b1	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	4c7e2a97-05a5-4499-99d5-b0fe14711095	1	20	t	936ccde4-b63d-4262-b8a6-af7d521dc3aa	\N
d6e65464-82cd-4db6-99c1-3cbeee2a0560	\N	registration-page-form	83b6664d-539e-4bed-a376-685d50e40b98	8c356fe3-6843-440d-ac93-b4cc6352781f	0	10	t	aa3e186d-9e97-41c0-a7f5-956fb754bdea	\N
0a585aff-6e35-478d-9f98-374f0cbd6f93	\N	registration-user-creation	83b6664d-539e-4bed-a376-685d50e40b98	aa3e186d-9e97-41c0-a7f5-956fb754bdea	0	20	f	\N	\N
59e6f670-66be-4b9c-82d3-fc64dc7b0adf	\N	registration-password-action	83b6664d-539e-4bed-a376-685d50e40b98	aa3e186d-9e97-41c0-a7f5-956fb754bdea	0	50	f	\N	\N
155e0305-95cf-4a57-b26c-8fab9dd3bbcb	\N	registration-recaptcha-action	83b6664d-539e-4bed-a376-685d50e40b98	aa3e186d-9e97-41c0-a7f5-956fb754bdea	3	60	f	\N	\N
5719e725-a3a1-4a28-8829-6894a024d5b6	\N	registration-terms-and-conditions	83b6664d-539e-4bed-a376-685d50e40b98	aa3e186d-9e97-41c0-a7f5-956fb754bdea	3	70	f	\N	\N
5106fd2f-759c-4cbc-bef7-049072ef373c	\N	reset-credentials-choose-user	83b6664d-539e-4bed-a376-685d50e40b98	2ec9ca75-4f99-43e9-816d-708c9a550838	0	10	f	\N	\N
8151027d-05aa-4459-bf8c-60729322d69d	\N	reset-credential-email	83b6664d-539e-4bed-a376-685d50e40b98	2ec9ca75-4f99-43e9-816d-708c9a550838	0	20	f	\N	\N
69dc0841-3eab-4a85-ad12-ecb1310660d4	\N	reset-password	83b6664d-539e-4bed-a376-685d50e40b98	2ec9ca75-4f99-43e9-816d-708c9a550838	0	30	f	\N	\N
197ce2ca-d284-4726-aed7-d487aa0ca83b	\N	\N	83b6664d-539e-4bed-a376-685d50e40b98	2ec9ca75-4f99-43e9-816d-708c9a550838	1	40	t	510a55f8-b4a5-47e9-ac56-b60fc6ed8676	\N
8b17dd60-5707-48d5-9780-921940fc5a04	\N	http-basic-authenticator	83b6664d-539e-4bed-a376-685d50e40b98	626d5f6d-04d2-4f35-8cfc-da5e65715166	0	10	f	\N	\N
\.


--
-- Data for Name: authentication_flow; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authentication_flow (id, alias, description, realm_id, provider_id, top_level, built_in) FROM stdin;
6650ceb0-4f67-422a-abaa-858ad9cc7ced	browser	Browser based authentication	863411db-f685-4e36-9210-ef7892793272	basic-flow	t	t
6bcfc2b8-4c3c-4afe-8291-ca5c59fd36b0	forms	Username, password, otp and other auth forms.	863411db-f685-4e36-9210-ef7892793272	basic-flow	f	t
51affe51-10ec-4865-81c3-bbb4290b840c	Browser - Conditional OTP	Flow to determine if the OTP is required for the authentication	863411db-f685-4e36-9210-ef7892793272	basic-flow	f	t
15cbcb5c-9138-4459-a021-b4452695744a	direct grant	OpenID Connect Resource Owner Grant	863411db-f685-4e36-9210-ef7892793272	basic-flow	t	t
fa9da093-4f69-4063-8c4f-ce6e17a4c656	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	863411db-f685-4e36-9210-ef7892793272	basic-flow	f	t
6c2990af-3f0e-4cc8-aa1b-1743f5b9e956	registration	Registration flow	863411db-f685-4e36-9210-ef7892793272	basic-flow	t	t
fefffc8c-d402-40e0-b3c6-22717e5966e6	registration form	Registration form	863411db-f685-4e36-9210-ef7892793272	form-flow	f	t
8cdf9d22-3eae-4235-8a21-13a0b3c0e3d3	reset credentials	Reset credentials for a user if they forgot their password or something	863411db-f685-4e36-9210-ef7892793272	basic-flow	t	t
a0a44ea6-d391-4801-b66a-2766075133db	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	863411db-f685-4e36-9210-ef7892793272	basic-flow	f	t
39af4770-19a1-4c3c-9a4b-d48ff0313efa	clients	Base authentication for clients	863411db-f685-4e36-9210-ef7892793272	client-flow	t	t
b73e4239-23d0-4044-8556-09e156ce1c71	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	863411db-f685-4e36-9210-ef7892793272	basic-flow	t	t
0914c41f-57d8-416b-9035-43b87a0dd46c	User creation or linking	Flow for the existing/non-existing user alternatives	863411db-f685-4e36-9210-ef7892793272	basic-flow	f	t
456ff76f-79a8-4422-8aab-ebe4d8b53aa4	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	863411db-f685-4e36-9210-ef7892793272	basic-flow	f	t
eb07c847-5e82-4322-8acf-88271c463abd	Account verification options	Method with which to verity the existing account	863411db-f685-4e36-9210-ef7892793272	basic-flow	f	t
d245292f-fc10-4797-9326-e268289f8b35	Verify Existing Account by Re-authentication	Reauthentication of existing account	863411db-f685-4e36-9210-ef7892793272	basic-flow	f	t
278fb87f-5423-4e55-9be7-211d1a4d3e3f	First broker login - Conditional OTP	Flow to determine if the OTP is required for the authentication	863411db-f685-4e36-9210-ef7892793272	basic-flow	f	t
c11b97e5-a541-495c-aa02-22235b487442	saml ecp	SAML ECP Profile Authentication Flow	863411db-f685-4e36-9210-ef7892793272	basic-flow	t	t
d3f88d58-4bd3-4e50-9c39-20510841d3f9	docker auth	Used by Docker clients to authenticate against the IDP	863411db-f685-4e36-9210-ef7892793272	basic-flow	t	t
10e5cbf9-6d70-4671-8b7c-25a13f3b30b6	Account verification options	Method with which to verity the existing account	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
936ccde4-b63d-4262-b8a6-af7d521dc3aa	Browser - Conditional OTP	Flow to determine if the OTP is required for the authentication	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
dc1e8d98-e26a-437c-91a3-f8ca8ed2dd59	Browser - Conditional Organization	Flow to determine if the organization identity-first login is to be used	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
5b2379d9-11fb-476f-8530-5a91ca18e364	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
fcaef89a-b301-4a9d-86b6-f570c43d07ad	First Broker Login - Conditional Organization	Flow to determine if the authenticator that adds organization members is to be used	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
ceed43c7-5d3f-4eb1-b878-2314aef46ebf	First broker login - Conditional OTP	Flow to determine if the OTP is required for the authentication	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
a9376912-6f72-4749-a4e3-6ce74c0ec76a	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
291b3c02-9178-4997-92a8-2c77b4a762da	Organization	\N	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
510a55f8-b4a5-47e9-ac56-b60fc6ed8676	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
677d2578-fe86-4de9-8a18-860f8d89d4f3	User creation or linking	Flow for the existing/non-existing user alternatives	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
9072b1a2-a2d2-426d-9fe7-bfb40b87cfab	Verify Existing Account by Re-authentication	Reauthentication of existing account	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
801e570d-bc15-45e3-9f8e-afe9f8f28dec	browser	Browser based authentication	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	t	t
05607ab5-8839-424c-b1e7-7f0ccad2063e	clients	Base authentication for clients	83b6664d-539e-4bed-a376-685d50e40b98	client-flow	t	t
d7b8234b-3d04-4263-ac07-79794e3fb8c0	direct grant	OpenID Connect Resource Owner Grant	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	t	t
08caf47c-2dda-42d5-a33d-3df1271e703c	docker auth	Used by Docker clients to authenticate against the IDP	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	t	t
668883be-9b51-42f6-9e35-cb35d7961852	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	t	t
4c7e2a97-05a5-4499-99d5-b0fe14711095	forms	Username, password, otp and other auth forms.	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	f	t
8c356fe3-6843-440d-ac93-b4cc6352781f	registration	Registration flow	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	t	t
aa3e186d-9e97-41c0-a7f5-956fb754bdea	registration form	Registration form	83b6664d-539e-4bed-a376-685d50e40b98	form-flow	f	t
2ec9ca75-4f99-43e9-816d-708c9a550838	reset credentials	Reset credentials for a user if they forgot their password or something	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	t	t
626d5f6d-04d2-4f35-8cfc-da5e65715166	saml ecp	SAML ECP Profile Authentication Flow	83b6664d-539e-4bed-a376-685d50e40b98	basic-flow	t	t
\.


--
-- Data for Name: authenticator_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authenticator_config (id, alias, realm_id) FROM stdin;
8ba548f6-eb57-407b-a5a5-02d009459457	review profile config	863411db-f685-4e36-9210-ef7892793272
aaa68215-8cf0-430a-8c36-fb32d2d5a875	create unique user config	863411db-f685-4e36-9210-ef7892793272
7805e560-dd03-4e34-a6ff-1784f53fecad	create unique user config	83b6664d-539e-4bed-a376-685d50e40b98
135d8612-fdf4-4acf-abf8-e25c0565ebf6	review profile config	83b6664d-539e-4bed-a376-685d50e40b98
\.


--
-- Data for Name: authenticator_config_entry; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authenticator_config_entry (authenticator_id, value, name) FROM stdin;
8ba548f6-eb57-407b-a5a5-02d009459457	missing	update.profile.on.first.login
aaa68215-8cf0-430a-8c36-fb32d2d5a875	false	require.password.update.after.registration
135d8612-fdf4-4acf-abf8-e25c0565ebf6	missing	update.profile.on.first.login
7805e560-dd03-4e34-a6ff-1784f53fecad	false	require.password.update.after.registration
\.


--
-- Data for Name: authority; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authority (name) FROM stdin;
ROLE_UMA_AUTHORIZATION
ROLE_OFFLINE_ACCESS
ROLE_DEFAULT-ROLES-LOCI-REALM
ROLE_USER
\.


--
-- Data for Name: broker_link; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.broker_link (identity_provider, storage_provider_id, realm_id, broker_user_id, broker_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: client; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client (id, enabled, full_scope_allowed, client_id, not_before, public_client, secret, base_url, bearer_only, management_url, surrogate_auth_required, realm_id, protocol, node_rereg_timeout, frontchannel_logout, consent_required, name, service_accounts_enabled, client_authenticator_type, root_url, description, registration_token, standard_flow_enabled, implicit_flow_enabled, direct_access_grants_enabled, always_display_in_console) FROM stdin;
4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	f	master-realm	0	f	\N	\N	t	\N	f	863411db-f685-4e36-9210-ef7892793272	\N	0	f	f	master Realm	f	client-secret	\N	\N	\N	t	f	f	f
5a657f88-6b01-40ff-a1f5-bc6f95dee042	t	f	account	0	t	\N	/realms/master/account/	f	\N	f	863411db-f685-4e36-9210-ef7892793272	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	t	f	account-console	0	t	\N	/realms/master/account/	f	\N	f	863411db-f685-4e36-9210-ef7892793272	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	t	f	broker	0	f	\N	\N	t	\N	f	863411db-f685-4e36-9210-ef7892793272	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
94105b65-67ea-498a-a4e5-cdd952818067	t	t	security-admin-console	0	t	\N	/admin/master/console/	f	\N	f	863411db-f685-4e36-9210-ef7892793272	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
e9d7f078-e54f-4a76-94d8-15e2416fe12a	t	t	admin-cli	0	t	\N	\N	f	\N	f	863411db-f685-4e36-9210-ef7892793272	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	f	loci-realm-realm	0	f	\N	\N	t	\N	f	863411db-f685-4e36-9210-ef7892793272	\N	0	f	f	loci-realm Realm	f	client-secret	\N	\N	\N	t	f	f	f
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	t	f	account	0	t	\N	/realms/loci-realm/account/	f	\N	f	83b6664d-539e-4bed-a376-685d50e40b98	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
1512bb33-3ef4-4dad-af1f-47081a2a75dc	t	f	account-console	0	t	\N	/realms/loci-realm/account/	f	\N	f	83b6664d-539e-4bed-a376-685d50e40b98	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	t	t	admin-cli	0	t	\N	\N	f	\N	f	83b6664d-539e-4bed-a376-685d50e40b98	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	t	t	angular	0	t	\N		f		f	83b6664d-539e-4bed-a376-685d50e40b98	openid-connect	-1	t	f		f	client-secret			\N	t	t	t	f
81fb0324-8f89-4cb7-bc31-8af536e10b7e	t	f	broker	0	f	\N	\N	t	\N	f	83b6664d-539e-4bed-a376-685d50e40b98	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	f	realm-management	0	f	\N	\N	t	\N	f	83b6664d-539e-4bed-a376-685d50e40b98	openid-connect	0	f	f	${client_realm-management}	f	client-secret	\N	\N	\N	t	f	f	f
ba4de397-daf8-404b-ad79-249e4d09a713	t	t	security-admin-console	0	t	\N	/admin/loci-realm/console/	f	\N	f	83b6664d-539e-4bed-a376-685d50e40b98	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
e85deedd-b2fb-47d3-acef-508423a77f22	t	t	spring	0	t	\N		f		f	83b6664d-539e-4bed-a376-685d50e40b98	openid-connect	-1	t	f		f	client-secret			\N	t	f	t	f
d004af51-9d2e-47d3-9340-a3a411f42029	t	t	migration-service	0	t	\N		f		f	83b6664d-539e-4bed-a376-685d50e40b98	openid-connect	-1	t	f		f	client-secret			\N	t	f	t	f
\.


--
-- Data for Name: client_attributes; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_attributes (client_id, name, value) FROM stdin;
5a657f88-6b01-40ff-a1f5-bc6f95dee042	post.logout.redirect.uris	+
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	post.logout.redirect.uris	+
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	pkce.code.challenge.method	S256
94105b65-67ea-498a-a4e5-cdd952818067	post.logout.redirect.uris	+
94105b65-67ea-498a-a4e5-cdd952818067	pkce.code.challenge.method	S256
94105b65-67ea-498a-a4e5-cdd952818067	client.use.lightweight.access.token.enabled	true
e9d7f078-e54f-4a76-94d8-15e2416fe12a	client.use.lightweight.access.token.enabled	true
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	realm_client	false
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	post.logout.redirect.uris	+
1512bb33-3ef4-4dad-af1f-47081a2a75dc	realm_client	false
1512bb33-3ef4-4dad-af1f-47081a2a75dc	post.logout.redirect.uris	+
1512bb33-3ef4-4dad-af1f-47081a2a75dc	pkce.code.challenge.method	S256
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	realm_client	false
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	client.use.lightweight.access.token.enabled	true
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	post.logout.redirect.uris	+
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	realm_client	false
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	oidc.ciba.grant.enabled	false
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	backchannel.logout.session.required	true
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	post.logout.redirect.uris	http://localhost:4200/*##http://192.168.1.21:4200/*##http://localhost:4200/##http://192.168.1.21:4200/
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	oauth2.device.authorization.grant.enabled	false
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	backchannel.logout.revoke.offline.tokens	false
81fb0324-8f89-4cb7-bc31-8af536e10b7e	realm_client	true
81fb0324-8f89-4cb7-bc31-8af536e10b7e	post.logout.redirect.uris	+
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	realm_client	true
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	post.logout.redirect.uris	+
ba4de397-daf8-404b-ad79-249e4d09a713	realm_client	false
ba4de397-daf8-404b-ad79-249e4d09a713	client.use.lightweight.access.token.enabled	true
ba4de397-daf8-404b-ad79-249e4d09a713	post.logout.redirect.uris	+
ba4de397-daf8-404b-ad79-249e4d09a713	pkce.code.challenge.method	S256
e85deedd-b2fb-47d3-acef-508423a77f22	realm_client	false
e85deedd-b2fb-47d3-acef-508423a77f22	oidc.ciba.grant.enabled	false
e85deedd-b2fb-47d3-acef-508423a77f22	backchannel.logout.session.required	true
e85deedd-b2fb-47d3-acef-508423a77f22	oauth2.device.authorization.grant.enabled	false
e85deedd-b2fb-47d3-acef-508423a77f22	backchannel.logout.revoke.offline.tokens	false
e85deedd-b2fb-47d3-acef-508423a77f22	post.logout.redirect.uris	+
d004af51-9d2e-47d3-9340-a3a411f42029	oauth2.device.authorization.grant.enabled	false
d004af51-9d2e-47d3-9340-a3a411f42029	oidc.ciba.grant.enabled	false
d004af51-9d2e-47d3-9340-a3a411f42029	backchannel.logout.session.required	true
d004af51-9d2e-47d3-9340-a3a411f42029	backchannel.logout.revoke.offline.tokens	false
\.


--
-- Data for Name: client_auth_flow_bindings; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_auth_flow_bindings (client_id, flow_id, binding_name) FROM stdin;
\.


--
-- Data for Name: client_initial_access; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_initial_access (id, realm_id, "timestamp", expiration, count, remaining_count) FROM stdin;
\.


--
-- Data for Name: client_node_registrations; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_node_registrations (client_id, value, name) FROM stdin;
\.


--
-- Data for Name: client_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_scope (id, name, realm_id, description, protocol) FROM stdin;
f292de33-23dd-40b8-ba83-3736dfed481a	offline_access	863411db-f685-4e36-9210-ef7892793272	OpenID Connect built-in scope: offline_access	openid-connect
0115c2fb-3ed9-4ad5-ab8a-0f6c544a400b	role_list	863411db-f685-4e36-9210-ef7892793272	SAML role list	saml
6df126a7-fe19-4d68-8a99-b5b37083d13d	saml_organization	863411db-f685-4e36-9210-ef7892793272	Organization Membership	saml
7a41db59-024f-4ffd-9a52-271e84abeda2	profile	863411db-f685-4e36-9210-ef7892793272	OpenID Connect built-in scope: profile	openid-connect
2252ac5e-72e1-4321-b6be-fc6d80a48274	email	863411db-f685-4e36-9210-ef7892793272	OpenID Connect built-in scope: email	openid-connect
93282cfc-5add-4d56-9c6a-ce686d5d93c1	address	863411db-f685-4e36-9210-ef7892793272	OpenID Connect built-in scope: address	openid-connect
b6608264-845a-4394-909e-e68ffc1a8574	phone	863411db-f685-4e36-9210-ef7892793272	OpenID Connect built-in scope: phone	openid-connect
e4f4336a-8834-41a9-abf3-ff18fcfb1459	roles	863411db-f685-4e36-9210-ef7892793272	OpenID Connect scope for add user roles to the access token	openid-connect
71809b5b-b9ba-49cd-a626-b5e0214eda76	web-origins	863411db-f685-4e36-9210-ef7892793272	OpenID Connect scope for add allowed web origins to the access token	openid-connect
d68cde9c-a31f-462b-8e8d-06df080f3ff9	microprofile-jwt	863411db-f685-4e36-9210-ef7892793272	Microprofile - JWT built-in scope	openid-connect
9e0578c9-ff91-4204-ae95-34584b120356	acr	863411db-f685-4e36-9210-ef7892793272	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
0b83a41f-87a8-46c9-b799-6b8583b201c0	basic	863411db-f685-4e36-9210-ef7892793272	OpenID Connect scope for add all basic claims to the token	openid-connect
033ff9f5-2f67-4e3d-bb3b-aa13acc62d06	organization	863411db-f685-4e36-9210-ef7892793272	Additional claims about the organization a subject belongs to	openid-connect
2753e1d9-77a7-4059-8465-fa23a6b418c4	microprofile-jwt	83b6664d-539e-4bed-a376-685d50e40b98	Microprofile - JWT built-in scope	openid-connect
ac70839b-f142-47ab-93cf-f21d06d1f546	basic	83b6664d-539e-4bed-a376-685d50e40b98	OpenID Connect scope for add all basic claims to the token	openid-connect
1e15434a-7f6c-415b-bb60-f84f0edb53d7	email	83b6664d-539e-4bed-a376-685d50e40b98	OpenID Connect built-in scope: email	openid-connect
1343b2a3-ff3c-4588-a0e3-7372006e3cf9	roles	83b6664d-539e-4bed-a376-685d50e40b98	OpenID Connect scope for add user roles to the access token	openid-connect
9749151c-c4bd-4925-b7f6-2e141385a4eb	phone	83b6664d-539e-4bed-a376-685d50e40b98	OpenID Connect built-in scope: phone	openid-connect
d21bbe72-4cc0-4637-aaab-c6beafce7e57	organization	83b6664d-539e-4bed-a376-685d50e40b98	Additional claims about the organization a subject belongs to	openid-connect
ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	web-origins	83b6664d-539e-4bed-a376-685d50e40b98	OpenID Connect scope for add allowed web origins to the access token	openid-connect
26d881ea-f02a-4da4-a770-096c22dcf2b6	saml_organization	83b6664d-539e-4bed-a376-685d50e40b98	Organization Membership	saml
b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	offline_access	83b6664d-539e-4bed-a376-685d50e40b98	OpenID Connect built-in scope: offline_access	openid-connect
38509398-011e-46eb-9444-8719ee1ccbe9	address	83b6664d-539e-4bed-a376-685d50e40b98	OpenID Connect built-in scope: address	openid-connect
b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	acr	83b6664d-539e-4bed-a376-685d50e40b98	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
2714f410-8ea1-4442-b110-09d56464c942	role_list	83b6664d-539e-4bed-a376-685d50e40b98	SAML role list	saml
424886ac-3b5a-446c-a0f8-2d2cd6474e44	profile	83b6664d-539e-4bed-a376-685d50e40b98	OpenID Connect built-in scope: profile	openid-connect
\.


--
-- Data for Name: client_scope_attributes; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_scope_attributes (scope_id, value, name) FROM stdin;
f292de33-23dd-40b8-ba83-3736dfed481a	true	display.on.consent.screen
f292de33-23dd-40b8-ba83-3736dfed481a	${offlineAccessScopeConsentText}	consent.screen.text
0115c2fb-3ed9-4ad5-ab8a-0f6c544a400b	true	display.on.consent.screen
0115c2fb-3ed9-4ad5-ab8a-0f6c544a400b	${samlRoleListScopeConsentText}	consent.screen.text
6df126a7-fe19-4d68-8a99-b5b37083d13d	false	display.on.consent.screen
7a41db59-024f-4ffd-9a52-271e84abeda2	true	display.on.consent.screen
7a41db59-024f-4ffd-9a52-271e84abeda2	${profileScopeConsentText}	consent.screen.text
7a41db59-024f-4ffd-9a52-271e84abeda2	true	include.in.token.scope
2252ac5e-72e1-4321-b6be-fc6d80a48274	true	display.on.consent.screen
2252ac5e-72e1-4321-b6be-fc6d80a48274	${emailScopeConsentText}	consent.screen.text
2252ac5e-72e1-4321-b6be-fc6d80a48274	true	include.in.token.scope
93282cfc-5add-4d56-9c6a-ce686d5d93c1	true	display.on.consent.screen
93282cfc-5add-4d56-9c6a-ce686d5d93c1	${addressScopeConsentText}	consent.screen.text
93282cfc-5add-4d56-9c6a-ce686d5d93c1	true	include.in.token.scope
b6608264-845a-4394-909e-e68ffc1a8574	true	display.on.consent.screen
b6608264-845a-4394-909e-e68ffc1a8574	${phoneScopeConsentText}	consent.screen.text
b6608264-845a-4394-909e-e68ffc1a8574	true	include.in.token.scope
e4f4336a-8834-41a9-abf3-ff18fcfb1459	true	display.on.consent.screen
e4f4336a-8834-41a9-abf3-ff18fcfb1459	${rolesScopeConsentText}	consent.screen.text
e4f4336a-8834-41a9-abf3-ff18fcfb1459	false	include.in.token.scope
71809b5b-b9ba-49cd-a626-b5e0214eda76	false	display.on.consent.screen
71809b5b-b9ba-49cd-a626-b5e0214eda76		consent.screen.text
71809b5b-b9ba-49cd-a626-b5e0214eda76	false	include.in.token.scope
d68cde9c-a31f-462b-8e8d-06df080f3ff9	false	display.on.consent.screen
d68cde9c-a31f-462b-8e8d-06df080f3ff9	true	include.in.token.scope
9e0578c9-ff91-4204-ae95-34584b120356	false	display.on.consent.screen
9e0578c9-ff91-4204-ae95-34584b120356	false	include.in.token.scope
0b83a41f-87a8-46c9-b799-6b8583b201c0	false	display.on.consent.screen
0b83a41f-87a8-46c9-b799-6b8583b201c0	false	include.in.token.scope
033ff9f5-2f67-4e3d-bb3b-aa13acc62d06	true	display.on.consent.screen
033ff9f5-2f67-4e3d-bb3b-aa13acc62d06	${organizationScopeConsentText}	consent.screen.text
033ff9f5-2f67-4e3d-bb3b-aa13acc62d06	true	include.in.token.scope
2753e1d9-77a7-4059-8465-fa23a6b418c4	true	include.in.token.scope
2753e1d9-77a7-4059-8465-fa23a6b418c4	false	display.on.consent.screen
ac70839b-f142-47ab-93cf-f21d06d1f546	false	include.in.token.scope
ac70839b-f142-47ab-93cf-f21d06d1f546	false	display.on.consent.screen
1e15434a-7f6c-415b-bb60-f84f0edb53d7	true	include.in.token.scope
1e15434a-7f6c-415b-bb60-f84f0edb53d7	${emailScopeConsentText}	consent.screen.text
1e15434a-7f6c-415b-bb60-f84f0edb53d7	true	display.on.consent.screen
1343b2a3-ff3c-4588-a0e3-7372006e3cf9	false	include.in.token.scope
1343b2a3-ff3c-4588-a0e3-7372006e3cf9	${rolesScopeConsentText}	consent.screen.text
1343b2a3-ff3c-4588-a0e3-7372006e3cf9	true	display.on.consent.screen
9749151c-c4bd-4925-b7f6-2e141385a4eb	true	include.in.token.scope
9749151c-c4bd-4925-b7f6-2e141385a4eb	${phoneScopeConsentText}	consent.screen.text
9749151c-c4bd-4925-b7f6-2e141385a4eb	true	display.on.consent.screen
d21bbe72-4cc0-4637-aaab-c6beafce7e57	true	include.in.token.scope
d21bbe72-4cc0-4637-aaab-c6beafce7e57	${organizationScopeConsentText}	consent.screen.text
d21bbe72-4cc0-4637-aaab-c6beafce7e57	true	display.on.consent.screen
ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	false	include.in.token.scope
ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee		consent.screen.text
ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	false	display.on.consent.screen
26d881ea-f02a-4da4-a770-096c22dcf2b6	false	display.on.consent.screen
b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	${offlineAccessScopeConsentText}	consent.screen.text
b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	true	display.on.consent.screen
38509398-011e-46eb-9444-8719ee1ccbe9	true	include.in.token.scope
38509398-011e-46eb-9444-8719ee1ccbe9	${addressScopeConsentText}	consent.screen.text
38509398-011e-46eb-9444-8719ee1ccbe9	true	display.on.consent.screen
b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	false	include.in.token.scope
b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	false	display.on.consent.screen
2714f410-8ea1-4442-b110-09d56464c942	${samlRoleListScopeConsentText}	consent.screen.text
2714f410-8ea1-4442-b110-09d56464c942	true	display.on.consent.screen
424886ac-3b5a-446c-a0f8-2d2cd6474e44	true	include.in.token.scope
424886ac-3b5a-446c-a0f8-2d2cd6474e44	${profileScopeConsentText}	consent.screen.text
424886ac-3b5a-446c-a0f8-2d2cd6474e44	true	display.on.consent.screen
\.


--
-- Data for Name: client_scope_client; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_scope_client (client_id, scope_id, default_scope) FROM stdin;
5a657f88-6b01-40ff-a1f5-bc6f95dee042	e4f4336a-8834-41a9-abf3-ff18fcfb1459	t
5a657f88-6b01-40ff-a1f5-bc6f95dee042	9e0578c9-ff91-4204-ae95-34584b120356	t
5a657f88-6b01-40ff-a1f5-bc6f95dee042	71809b5b-b9ba-49cd-a626-b5e0214eda76	t
5a657f88-6b01-40ff-a1f5-bc6f95dee042	2252ac5e-72e1-4321-b6be-fc6d80a48274	t
5a657f88-6b01-40ff-a1f5-bc6f95dee042	0b83a41f-87a8-46c9-b799-6b8583b201c0	t
5a657f88-6b01-40ff-a1f5-bc6f95dee042	7a41db59-024f-4ffd-9a52-271e84abeda2	t
5a657f88-6b01-40ff-a1f5-bc6f95dee042	d68cde9c-a31f-462b-8e8d-06df080f3ff9	f
5a657f88-6b01-40ff-a1f5-bc6f95dee042	93282cfc-5add-4d56-9c6a-ce686d5d93c1	f
5a657f88-6b01-40ff-a1f5-bc6f95dee042	f292de33-23dd-40b8-ba83-3736dfed481a	f
5a657f88-6b01-40ff-a1f5-bc6f95dee042	b6608264-845a-4394-909e-e68ffc1a8574	f
5a657f88-6b01-40ff-a1f5-bc6f95dee042	033ff9f5-2f67-4e3d-bb3b-aa13acc62d06	f
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	e4f4336a-8834-41a9-abf3-ff18fcfb1459	t
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	9e0578c9-ff91-4204-ae95-34584b120356	t
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	71809b5b-b9ba-49cd-a626-b5e0214eda76	t
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	2252ac5e-72e1-4321-b6be-fc6d80a48274	t
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	0b83a41f-87a8-46c9-b799-6b8583b201c0	t
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	7a41db59-024f-4ffd-9a52-271e84abeda2	t
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	d68cde9c-a31f-462b-8e8d-06df080f3ff9	f
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	93282cfc-5add-4d56-9c6a-ce686d5d93c1	f
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	f292de33-23dd-40b8-ba83-3736dfed481a	f
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	b6608264-845a-4394-909e-e68ffc1a8574	f
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	033ff9f5-2f67-4e3d-bb3b-aa13acc62d06	f
e9d7f078-e54f-4a76-94d8-15e2416fe12a	e4f4336a-8834-41a9-abf3-ff18fcfb1459	t
e9d7f078-e54f-4a76-94d8-15e2416fe12a	9e0578c9-ff91-4204-ae95-34584b120356	t
e9d7f078-e54f-4a76-94d8-15e2416fe12a	71809b5b-b9ba-49cd-a626-b5e0214eda76	t
e9d7f078-e54f-4a76-94d8-15e2416fe12a	2252ac5e-72e1-4321-b6be-fc6d80a48274	t
e9d7f078-e54f-4a76-94d8-15e2416fe12a	0b83a41f-87a8-46c9-b799-6b8583b201c0	t
e9d7f078-e54f-4a76-94d8-15e2416fe12a	7a41db59-024f-4ffd-9a52-271e84abeda2	t
e9d7f078-e54f-4a76-94d8-15e2416fe12a	d68cde9c-a31f-462b-8e8d-06df080f3ff9	f
e9d7f078-e54f-4a76-94d8-15e2416fe12a	93282cfc-5add-4d56-9c6a-ce686d5d93c1	f
e9d7f078-e54f-4a76-94d8-15e2416fe12a	f292de33-23dd-40b8-ba83-3736dfed481a	f
e9d7f078-e54f-4a76-94d8-15e2416fe12a	b6608264-845a-4394-909e-e68ffc1a8574	f
e9d7f078-e54f-4a76-94d8-15e2416fe12a	033ff9f5-2f67-4e3d-bb3b-aa13acc62d06	f
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	e4f4336a-8834-41a9-abf3-ff18fcfb1459	t
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	9e0578c9-ff91-4204-ae95-34584b120356	t
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	71809b5b-b9ba-49cd-a626-b5e0214eda76	t
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	2252ac5e-72e1-4321-b6be-fc6d80a48274	t
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	0b83a41f-87a8-46c9-b799-6b8583b201c0	t
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	7a41db59-024f-4ffd-9a52-271e84abeda2	t
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	d68cde9c-a31f-462b-8e8d-06df080f3ff9	f
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	93282cfc-5add-4d56-9c6a-ce686d5d93c1	f
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	f292de33-23dd-40b8-ba83-3736dfed481a	f
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	b6608264-845a-4394-909e-e68ffc1a8574	f
8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	033ff9f5-2f67-4e3d-bb3b-aa13acc62d06	f
4744ff01-2e1f-4b2d-8d58-85f94704e41a	e4f4336a-8834-41a9-abf3-ff18fcfb1459	t
4744ff01-2e1f-4b2d-8d58-85f94704e41a	9e0578c9-ff91-4204-ae95-34584b120356	t
4744ff01-2e1f-4b2d-8d58-85f94704e41a	71809b5b-b9ba-49cd-a626-b5e0214eda76	t
4744ff01-2e1f-4b2d-8d58-85f94704e41a	2252ac5e-72e1-4321-b6be-fc6d80a48274	t
4744ff01-2e1f-4b2d-8d58-85f94704e41a	0b83a41f-87a8-46c9-b799-6b8583b201c0	t
4744ff01-2e1f-4b2d-8d58-85f94704e41a	7a41db59-024f-4ffd-9a52-271e84abeda2	t
4744ff01-2e1f-4b2d-8d58-85f94704e41a	d68cde9c-a31f-462b-8e8d-06df080f3ff9	f
4744ff01-2e1f-4b2d-8d58-85f94704e41a	93282cfc-5add-4d56-9c6a-ce686d5d93c1	f
4744ff01-2e1f-4b2d-8d58-85f94704e41a	f292de33-23dd-40b8-ba83-3736dfed481a	f
4744ff01-2e1f-4b2d-8d58-85f94704e41a	b6608264-845a-4394-909e-e68ffc1a8574	f
4744ff01-2e1f-4b2d-8d58-85f94704e41a	033ff9f5-2f67-4e3d-bb3b-aa13acc62d06	f
94105b65-67ea-498a-a4e5-cdd952818067	e4f4336a-8834-41a9-abf3-ff18fcfb1459	t
94105b65-67ea-498a-a4e5-cdd952818067	9e0578c9-ff91-4204-ae95-34584b120356	t
94105b65-67ea-498a-a4e5-cdd952818067	71809b5b-b9ba-49cd-a626-b5e0214eda76	t
94105b65-67ea-498a-a4e5-cdd952818067	2252ac5e-72e1-4321-b6be-fc6d80a48274	t
94105b65-67ea-498a-a4e5-cdd952818067	0b83a41f-87a8-46c9-b799-6b8583b201c0	t
94105b65-67ea-498a-a4e5-cdd952818067	7a41db59-024f-4ffd-9a52-271e84abeda2	t
94105b65-67ea-498a-a4e5-cdd952818067	d68cde9c-a31f-462b-8e8d-06df080f3ff9	f
94105b65-67ea-498a-a4e5-cdd952818067	93282cfc-5add-4d56-9c6a-ce686d5d93c1	f
94105b65-67ea-498a-a4e5-cdd952818067	f292de33-23dd-40b8-ba83-3736dfed481a	f
94105b65-67ea-498a-a4e5-cdd952818067	b6608264-845a-4394-909e-e68ffc1a8574	f
94105b65-67ea-498a-a4e5-cdd952818067	033ff9f5-2f67-4e3d-bb3b-aa13acc62d06	f
d004af51-9d2e-47d3-9340-a3a411f42029	ac70839b-f142-47ab-93cf-f21d06d1f546	t
d004af51-9d2e-47d3-9340-a3a411f42029	b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	t
d004af51-9d2e-47d3-9340-a3a411f42029	1e15434a-7f6c-415b-bb60-f84f0edb53d7	t
d004af51-9d2e-47d3-9340-a3a411f42029	1343b2a3-ff3c-4588-a0e3-7372006e3cf9	t
d004af51-9d2e-47d3-9340-a3a411f42029	ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	t
d004af51-9d2e-47d3-9340-a3a411f42029	424886ac-3b5a-446c-a0f8-2d2cd6474e44	t
d004af51-9d2e-47d3-9340-a3a411f42029	2753e1d9-77a7-4059-8465-fa23a6b418c4	f
d004af51-9d2e-47d3-9340-a3a411f42029	38509398-011e-46eb-9444-8719ee1ccbe9	f
d004af51-9d2e-47d3-9340-a3a411f42029	9749151c-c4bd-4925-b7f6-2e141385a4eb	f
d004af51-9d2e-47d3-9340-a3a411f42029	d21bbe72-4cc0-4637-aaab-c6beafce7e57	f
d004af51-9d2e-47d3-9340-a3a411f42029	b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	f
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	t
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	t
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	1343b2a3-ff3c-4588-a0e3-7372006e3cf9	t
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	424886ac-3b5a-446c-a0f8-2d2cd6474e44	t
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	ac70839b-f142-47ab-93cf-f21d06d1f546	t
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	1e15434a-7f6c-415b-bb60-f84f0edb53d7	t
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	38509398-011e-46eb-9444-8719ee1ccbe9	f
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	9749151c-c4bd-4925-b7f6-2e141385a4eb	f
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	d21bbe72-4cc0-4637-aaab-c6beafce7e57	f
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	f
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	2753e1d9-77a7-4059-8465-fa23a6b418c4	f
1512bb33-3ef4-4dad-af1f-47081a2a75dc	ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	t
1512bb33-3ef4-4dad-af1f-47081a2a75dc	b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	t
1512bb33-3ef4-4dad-af1f-47081a2a75dc	1343b2a3-ff3c-4588-a0e3-7372006e3cf9	t
1512bb33-3ef4-4dad-af1f-47081a2a75dc	424886ac-3b5a-446c-a0f8-2d2cd6474e44	t
1512bb33-3ef4-4dad-af1f-47081a2a75dc	ac70839b-f142-47ab-93cf-f21d06d1f546	t
1512bb33-3ef4-4dad-af1f-47081a2a75dc	1e15434a-7f6c-415b-bb60-f84f0edb53d7	t
1512bb33-3ef4-4dad-af1f-47081a2a75dc	38509398-011e-46eb-9444-8719ee1ccbe9	f
1512bb33-3ef4-4dad-af1f-47081a2a75dc	9749151c-c4bd-4925-b7f6-2e141385a4eb	f
1512bb33-3ef4-4dad-af1f-47081a2a75dc	d21bbe72-4cc0-4637-aaab-c6beafce7e57	f
1512bb33-3ef4-4dad-af1f-47081a2a75dc	b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	f
1512bb33-3ef4-4dad-af1f-47081a2a75dc	2753e1d9-77a7-4059-8465-fa23a6b418c4	f
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	t
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	t
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	1343b2a3-ff3c-4588-a0e3-7372006e3cf9	t
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	424886ac-3b5a-446c-a0f8-2d2cd6474e44	t
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	ac70839b-f142-47ab-93cf-f21d06d1f546	t
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	1e15434a-7f6c-415b-bb60-f84f0edb53d7	t
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	38509398-011e-46eb-9444-8719ee1ccbe9	f
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	9749151c-c4bd-4925-b7f6-2e141385a4eb	f
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	d21bbe72-4cc0-4637-aaab-c6beafce7e57	f
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	f
85f5e1a2-8c1d-42ae-97dc-b1c02c6f3188	2753e1d9-77a7-4059-8465-fa23a6b418c4	f
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	t
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	t
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	1343b2a3-ff3c-4588-a0e3-7372006e3cf9	t
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	424886ac-3b5a-446c-a0f8-2d2cd6474e44	t
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	ac70839b-f142-47ab-93cf-f21d06d1f546	t
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	1e15434a-7f6c-415b-bb60-f84f0edb53d7	t
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	38509398-011e-46eb-9444-8719ee1ccbe9	f
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	9749151c-c4bd-4925-b7f6-2e141385a4eb	f
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	d21bbe72-4cc0-4637-aaab-c6beafce7e57	f
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	f
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	2753e1d9-77a7-4059-8465-fa23a6b418c4	f
81fb0324-8f89-4cb7-bc31-8af536e10b7e	ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	t
81fb0324-8f89-4cb7-bc31-8af536e10b7e	b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	t
81fb0324-8f89-4cb7-bc31-8af536e10b7e	1343b2a3-ff3c-4588-a0e3-7372006e3cf9	t
81fb0324-8f89-4cb7-bc31-8af536e10b7e	424886ac-3b5a-446c-a0f8-2d2cd6474e44	t
81fb0324-8f89-4cb7-bc31-8af536e10b7e	ac70839b-f142-47ab-93cf-f21d06d1f546	t
81fb0324-8f89-4cb7-bc31-8af536e10b7e	1e15434a-7f6c-415b-bb60-f84f0edb53d7	t
81fb0324-8f89-4cb7-bc31-8af536e10b7e	38509398-011e-46eb-9444-8719ee1ccbe9	f
81fb0324-8f89-4cb7-bc31-8af536e10b7e	9749151c-c4bd-4925-b7f6-2e141385a4eb	f
81fb0324-8f89-4cb7-bc31-8af536e10b7e	d21bbe72-4cc0-4637-aaab-c6beafce7e57	f
81fb0324-8f89-4cb7-bc31-8af536e10b7e	b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	f
81fb0324-8f89-4cb7-bc31-8af536e10b7e	2753e1d9-77a7-4059-8465-fa23a6b418c4	f
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	t
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	t
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	1343b2a3-ff3c-4588-a0e3-7372006e3cf9	t
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	424886ac-3b5a-446c-a0f8-2d2cd6474e44	t
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	ac70839b-f142-47ab-93cf-f21d06d1f546	t
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	1e15434a-7f6c-415b-bb60-f84f0edb53d7	t
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	38509398-011e-46eb-9444-8719ee1ccbe9	f
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	9749151c-c4bd-4925-b7f6-2e141385a4eb	f
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	d21bbe72-4cc0-4637-aaab-c6beafce7e57	f
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	f
1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	2753e1d9-77a7-4059-8465-fa23a6b418c4	f
ba4de397-daf8-404b-ad79-249e4d09a713	ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	t
ba4de397-daf8-404b-ad79-249e4d09a713	b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	t
ba4de397-daf8-404b-ad79-249e4d09a713	1343b2a3-ff3c-4588-a0e3-7372006e3cf9	t
ba4de397-daf8-404b-ad79-249e4d09a713	424886ac-3b5a-446c-a0f8-2d2cd6474e44	t
ba4de397-daf8-404b-ad79-249e4d09a713	ac70839b-f142-47ab-93cf-f21d06d1f546	t
ba4de397-daf8-404b-ad79-249e4d09a713	1e15434a-7f6c-415b-bb60-f84f0edb53d7	t
ba4de397-daf8-404b-ad79-249e4d09a713	38509398-011e-46eb-9444-8719ee1ccbe9	f
ba4de397-daf8-404b-ad79-249e4d09a713	9749151c-c4bd-4925-b7f6-2e141385a4eb	f
ba4de397-daf8-404b-ad79-249e4d09a713	d21bbe72-4cc0-4637-aaab-c6beafce7e57	f
ba4de397-daf8-404b-ad79-249e4d09a713	b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	f
ba4de397-daf8-404b-ad79-249e4d09a713	2753e1d9-77a7-4059-8465-fa23a6b418c4	f
e85deedd-b2fb-47d3-acef-508423a77f22	ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	t
e85deedd-b2fb-47d3-acef-508423a77f22	b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	t
e85deedd-b2fb-47d3-acef-508423a77f22	1343b2a3-ff3c-4588-a0e3-7372006e3cf9	t
e85deedd-b2fb-47d3-acef-508423a77f22	424886ac-3b5a-446c-a0f8-2d2cd6474e44	t
e85deedd-b2fb-47d3-acef-508423a77f22	ac70839b-f142-47ab-93cf-f21d06d1f546	t
e85deedd-b2fb-47d3-acef-508423a77f22	1e15434a-7f6c-415b-bb60-f84f0edb53d7	t
e85deedd-b2fb-47d3-acef-508423a77f22	38509398-011e-46eb-9444-8719ee1ccbe9	f
e85deedd-b2fb-47d3-acef-508423a77f22	9749151c-c4bd-4925-b7f6-2e141385a4eb	f
e85deedd-b2fb-47d3-acef-508423a77f22	d21bbe72-4cc0-4637-aaab-c6beafce7e57	f
e85deedd-b2fb-47d3-acef-508423a77f22	b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	f
e85deedd-b2fb-47d3-acef-508423a77f22	2753e1d9-77a7-4059-8465-fa23a6b418c4	f
\.


--
-- Data for Name: client_scope_role_mapping; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_scope_role_mapping (scope_id, role_id) FROM stdin;
f292de33-23dd-40b8-ba83-3736dfed481a	6748c93f-f293-4519-9bf4-aefc57a46f9b
b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	6d7b0d9a-0ac1-481e-aebb-8acd9d5c3e39
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.component (id, name, parent_id, provider_id, provider_type, realm_id, sub_type) FROM stdin;
d5c6f104-9768-4b2e-ac8d-dbf424fc7490	Trusted Hosts	863411db-f685-4e36-9210-ef7892793272	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	863411db-f685-4e36-9210-ef7892793272	anonymous
d75ea4d2-c90f-4ffd-a1df-885a7e5834cf	Consent Required	863411db-f685-4e36-9210-ef7892793272	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	863411db-f685-4e36-9210-ef7892793272	anonymous
e8319953-1520-478e-9ccd-80280cb60832	Full Scope Disabled	863411db-f685-4e36-9210-ef7892793272	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	863411db-f685-4e36-9210-ef7892793272	anonymous
4d8cbf9e-c6f2-459b-bbe4-20b10fd63993	Max Clients Limit	863411db-f685-4e36-9210-ef7892793272	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	863411db-f685-4e36-9210-ef7892793272	anonymous
269b50dd-747c-4b19-a057-21a066d57fd4	Allowed Protocol Mapper Types	863411db-f685-4e36-9210-ef7892793272	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	863411db-f685-4e36-9210-ef7892793272	anonymous
55dbe6c9-f0e1-4128-9410-df34b08e4317	Allowed Client Scopes	863411db-f685-4e36-9210-ef7892793272	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	863411db-f685-4e36-9210-ef7892793272	anonymous
75f8e8f0-de3d-4dbc-b85c-2c2e8bd5e538	Allowed Protocol Mapper Types	863411db-f685-4e36-9210-ef7892793272	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	863411db-f685-4e36-9210-ef7892793272	authenticated
2e54018f-7a97-4939-aadc-8ce5c72ee357	Allowed Client Scopes	863411db-f685-4e36-9210-ef7892793272	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	863411db-f685-4e36-9210-ef7892793272	authenticated
f626fe55-7671-4981-81df-e44ef5710949	rsa-generated	863411db-f685-4e36-9210-ef7892793272	rsa-generated	org.keycloak.keys.KeyProvider	863411db-f685-4e36-9210-ef7892793272	\N
cdfe9c7d-6cba-4c71-b340-39f5c4e7305a	rsa-enc-generated	863411db-f685-4e36-9210-ef7892793272	rsa-enc-generated	org.keycloak.keys.KeyProvider	863411db-f685-4e36-9210-ef7892793272	\N
aba0ad1a-d772-4768-8aa1-ed47a8a51adc	hmac-generated-hs512	863411db-f685-4e36-9210-ef7892793272	hmac-generated	org.keycloak.keys.KeyProvider	863411db-f685-4e36-9210-ef7892793272	\N
a2653e63-2d7b-4bc4-8e53-8607dfb249a6	aes-generated	863411db-f685-4e36-9210-ef7892793272	aes-generated	org.keycloak.keys.KeyProvider	863411db-f685-4e36-9210-ef7892793272	\N
938d7b63-23ee-4f90-858e-203df6d85a32	\N	863411db-f685-4e36-9210-ef7892793272	declarative-user-profile	org.keycloak.userprofile.UserProfileProvider	863411db-f685-4e36-9210-ef7892793272	\N
bf7bc4cc-455f-4d74-ac97-b854b8f126e8	Allowed Client Scopes	83b6664d-539e-4bed-a376-685d50e40b98	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	83b6664d-539e-4bed-a376-685d50e40b98	anonymous
4dfd41fa-a9eb-4922-a0cf-d4f7a8ae24c1	Max Clients Limit	83b6664d-539e-4bed-a376-685d50e40b98	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	83b6664d-539e-4bed-a376-685d50e40b98	anonymous
2feec9eb-9647-4299-86f6-bf7d63fe6ff0	Allowed Client Scopes	83b6664d-539e-4bed-a376-685d50e40b98	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	83b6664d-539e-4bed-a376-685d50e40b98	authenticated
740dc5dc-5940-4e77-8ddb-dcfc6530d78c	Full Scope Disabled	83b6664d-539e-4bed-a376-685d50e40b98	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	83b6664d-539e-4bed-a376-685d50e40b98	anonymous
f76d02eb-db36-4cc1-860e-363ac63254ab	Allowed Protocol Mapper Types	83b6664d-539e-4bed-a376-685d50e40b98	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	83b6664d-539e-4bed-a376-685d50e40b98	authenticated
b709991f-e23a-4086-99fc-952d2e499280	Consent Required	83b6664d-539e-4bed-a376-685d50e40b98	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	83b6664d-539e-4bed-a376-685d50e40b98	anonymous
6957a15d-2473-4793-90cf-bf7923e35fe2	Allowed Protocol Mapper Types	83b6664d-539e-4bed-a376-685d50e40b98	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	83b6664d-539e-4bed-a376-685d50e40b98	anonymous
aa39d65d-762c-4690-9154-219710f831f1	Trusted Hosts	83b6664d-539e-4bed-a376-685d50e40b98	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	83b6664d-539e-4bed-a376-685d50e40b98	anonymous
1d42272d-82a4-4f7b-aa8a-02e0a91d8c71	rsa-enc-generated	83b6664d-539e-4bed-a376-685d50e40b98	rsa-enc-generated	org.keycloak.keys.KeyProvider	83b6664d-539e-4bed-a376-685d50e40b98	\N
1f601b2d-3f3c-4ee1-93f5-cd33a0c3c6cc	aes-generated	83b6664d-539e-4bed-a376-685d50e40b98	aes-generated	org.keycloak.keys.KeyProvider	83b6664d-539e-4bed-a376-685d50e40b98	\N
82d74645-ff8e-460e-ab07-a44369c80932	hmac-generated-hs512	83b6664d-539e-4bed-a376-685d50e40b98	hmac-generated	org.keycloak.keys.KeyProvider	83b6664d-539e-4bed-a376-685d50e40b98	\N
501abdae-a433-4e54-985a-a9fdf84bf3b8	rsa-generated	83b6664d-539e-4bed-a376-685d50e40b98	rsa-generated	org.keycloak.keys.KeyProvider	83b6664d-539e-4bed-a376-685d50e40b98	\N
\.


--
-- Data for Name: component_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.component_config (id, component_id, name, value) FROM stdin;
45e5adae-2bd0-4010-9ea8-cf0923478f51	2e54018f-7a97-4939-aadc-8ce5c72ee357	allow-default-scopes	true
3d0214cd-1273-4435-84f9-a62a4507c461	4d8cbf9e-c6f2-459b-bbe4-20b10fd63993	max-clients	200
f9fea814-97c9-49e7-a668-d4cfb6ebace3	55dbe6c9-f0e1-4128-9410-df34b08e4317	allow-default-scopes	true
7e982d36-46cb-4592-a88d-0bced0384c9a	d5c6f104-9768-4b2e-ac8d-dbf424fc7490	client-uris-must-match	true
8d4401b2-6d5c-4baf-b721-3bbae553e602	d5c6f104-9768-4b2e-ac8d-dbf424fc7490	host-sending-registration-request-must-match	true
2995cf0b-8c55-4e57-a9a5-39f0316c0cfb	269b50dd-747c-4b19-a057-21a066d57fd4	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
f0dffd09-2d87-4d54-98b5-45619805e6c2	269b50dd-747c-4b19-a057-21a066d57fd4	allowed-protocol-mapper-types	saml-user-property-mapper
a6a828f7-9796-4b7c-8b71-8417797d2d9c	269b50dd-747c-4b19-a057-21a066d57fd4	allowed-protocol-mapper-types	saml-role-list-mapper
f35e7785-3b8c-4105-87b5-19a49c5db33d	269b50dd-747c-4b19-a057-21a066d57fd4	allowed-protocol-mapper-types	saml-user-attribute-mapper
38a7ae18-81ee-47ac-ad72-89d9a37405b6	269b50dd-747c-4b19-a057-21a066d57fd4	allowed-protocol-mapper-types	oidc-address-mapper
db829fd9-f36c-4fde-b66d-570ad8b1a01b	269b50dd-747c-4b19-a057-21a066d57fd4	allowed-protocol-mapper-types	oidc-full-name-mapper
6ab0c916-4383-42c3-a936-c7860b850ac1	269b50dd-747c-4b19-a057-21a066d57fd4	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
d463ae4b-d142-4f22-9a4d-599d5f13ae78	269b50dd-747c-4b19-a057-21a066d57fd4	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
609373e8-eebf-47a9-87f0-9f0514ab8c67	75f8e8f0-de3d-4dbc-b85c-2c2e8bd5e538	allowed-protocol-mapper-types	oidc-full-name-mapper
42ca1a4f-e316-45f4-a2c5-80217868671c	75f8e8f0-de3d-4dbc-b85c-2c2e8bd5e538	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
31e15a71-433f-462a-b910-92214e31a854	75f8e8f0-de3d-4dbc-b85c-2c2e8bd5e538	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
0820ded4-222a-4157-bc42-7b845db3345c	75f8e8f0-de3d-4dbc-b85c-2c2e8bd5e538	allowed-protocol-mapper-types	saml-role-list-mapper
a483e902-4f65-4d6d-8851-b6abfa9d553c	75f8e8f0-de3d-4dbc-b85c-2c2e8bd5e538	allowed-protocol-mapper-types	saml-user-property-mapper
96ba76ff-b4b1-4116-a1c0-db8366ddcde5	75f8e8f0-de3d-4dbc-b85c-2c2e8bd5e538	allowed-protocol-mapper-types	oidc-address-mapper
8bdaf5d3-9d97-4055-8f01-1f1bb3110596	75f8e8f0-de3d-4dbc-b85c-2c2e8bd5e538	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
37925bf9-020f-4d46-823a-4eaa996b506a	75f8e8f0-de3d-4dbc-b85c-2c2e8bd5e538	allowed-protocol-mapper-types	saml-user-attribute-mapper
c1dd7eac-a022-47ad-aa6b-f0cd76a4fef4	aba0ad1a-d772-4768-8aa1-ed47a8a51adc	kid	ce4ff426-1de1-4be6-aa64-06db350d04a0
4d581021-5fe9-4b13-a834-3944143f63f3	aba0ad1a-d772-4768-8aa1-ed47a8a51adc	algorithm	HS512
06937072-8060-417b-81ea-06f5f6fa23a0	aba0ad1a-d772-4768-8aa1-ed47a8a51adc	priority	100
6f527c59-fe40-4713-baa9-56be67b99e55	aba0ad1a-d772-4768-8aa1-ed47a8a51adc	secret	FGpoZEBnSJ7Jh95VFh8WGYfA4_PUpfIJ3dNpzhP3xJQueKoAv2kyxOCdLChY85Vf4MBlztiJw5FfXAxKPrLsQ00oRYowm1k1mPj6nifsFKIYDwl9lxufcVS43Q-QkX7pMUHumExqPOwt-LK5zx9fJr11mmL-JiFBJn--VpqVL24
6250a5b8-826e-41e5-bde5-f8f2c04ef97c	938d7b63-23ee-4f90-858e-203df6d85a32	kc.user.profile.config	{"attributes":[{"name":"username","displayName":"${username}","validations":{"length":{"min":3,"max":255},"username-prohibited-characters":{},"up-username-not-idn-homograph":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"email","displayName":"${email}","validations":{"email":{},"length":{"max":255}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"firstName","displayName":"${firstName}","validations":{"length":{"max":255},"person-name-prohibited-characters":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"lastName","displayName":"${lastName}","validations":{"length":{"max":255},"person-name-prohibited-characters":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false}],"groups":[{"name":"user-metadata","displayHeader":"User metadata","displayDescription":"Attributes, which refer to user metadata"}]}
b6338e6f-f3bc-4f7d-aa4e-e61e62823af5	cdfe9c7d-6cba-4c71-b340-39f5c4e7305a	priority	100
999cb7a0-4cb1-48a4-9cc6-5d7fe87e3209	cdfe9c7d-6cba-4c71-b340-39f5c4e7305a	keyUse	ENC
4f05f8be-65de-4496-9caf-5ff953fd4f76	cdfe9c7d-6cba-4c71-b340-39f5c4e7305a	certificate	MIICmzCCAYMCBgGadqsWIDANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjUxMTEyMDYwMzM1WhcNMzUxMTEyMDYwNTE1WjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQDPw5lCjRraOoWYj3xpzJC4JfqEG58eLF+PFjeB3MjtkekEblbANV3awhUWk3ZA6grLsDlXf+OPORVFJmzCL9HtCKGG3rkfHaKoKBILwqrlF4HmNdEWpSLL7QilWOOqKsh0bD6hiiFML9agd6BGNjHPcKI8hHlp68usj+YU/laK8rUiMAxr6y43U61epUE5MghnycUb+1A+bdcm+13xcXTmCTBaSKMJhWsoMn+xziOrry8LHClsB9CJnZJnsTHrTRZZ4dq65Yi0AZXBJpvJzr6Qleu6qVvtyCSCe7twnjlgStDGp1pJWWJWM6nyURXEuhucOKFlWTroGg/J5LoIFNOFAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAHgGQHaMErppb7fJtIU2bJ2lh/onzKngSgeY6ob10StwGOg50y/7RrPl0Jq4fYjJgGMLbAHsCZ2L+lvgRyakwS0u3EpOGG7yWbVUXFLGwIHUl1l3yfGA8ChC9MnV1mw848TQCjbpRkdcR7oKDqQzw9pibZnxcR+hTLPxb1CJEsBWYuEwE6X4zoVvuxUk6Hnqz93dFrefc7s2TpMP6rvAcpkEnF9SkBFSLynzXh/ZFtBw9wa99IClptbSVWjKUCCzeXZPUQkUYQKx3y2ezzQqeDd/cFQQTzqSOuqtnyLxuQAnGCmFxhCH7M6MLhFiauvqkcnJa5T9UzatAKy2FYxwOqU=
638a07c9-a8e7-4dd6-a709-db4f2b7498ac	cdfe9c7d-6cba-4c71-b340-39f5c4e7305a	privateKey	MIIEowIBAAKCAQEAz8OZQo0a2jqFmI98acyQuCX6hBufHixfjxY3gdzI7ZHpBG5WwDVd2sIVFpN2QOoKy7A5V3/jjzkVRSZswi/R7Qihht65Hx2iqCgSC8Kq5ReB5jXRFqUiy+0IpVjjqirIdGw+oYohTC/WoHegRjYxz3CiPIR5aevLrI/mFP5WivK1IjAMa+suN1OtXqVBOTIIZ8nFG/tQPm3XJvtd8XF05gkwWkijCYVrKDJ/sc4jq68vCxwpbAfQiZ2SZ7Ex600WWeHauuWItAGVwSabyc6+kJXruqlb7cgkgnu7cJ45YErQxqdaSVliVjOp8lEVxLobnDihZVk66BoPyeS6CBTThQIDAQABAoIBAC8//CRrA87anVRvWWuURttiY8FUqNDLZaYcdt41UIra8qP71gXbfS9JkVMzkSK9N1xQhRH1t1J0uHYkAXr+Nh9Spa2PDInbiwj0J/p9uLDmMjghSB5+ytWW4C7+crRaC4GStoG1cNIrVQPkgNJG8/gJ1ROvSw+APS9PAJQIrbTmic11m2k5xcCH8zpGCQmI1idS/dEKZ/RRrf4SNEbIMrRm3nY0inlSTeS4LGY80/N22JnWQk7/t8shSTQHzK3lD+5H7wtGqXPowDsUE19A6aCDjEgYi8EQsko/UZHNew5hl0TJaXsUF3pwdrhVIZUIL+94j1zj/hkKtjLsZnotdfcCgYEA6AjiZ7IyQYl4cWi8XLzPk+7UR9Sra8zpwOU7CEeO2Cs0jfrKeG8PpZrkpyd4WstrkGdJGE5NzXIHgm2nN9lRhbH0eSW62hup3Bd4Z9yq+7SIKeBvzPHb+5HgyPMgyLaXA2VKGN48aQgwFpvHM/ZovjOLtltbgxRwekQB+LB328MCgYEA5Tj8a5eiJkYRGjSzZHjHoIjpuKw1czCAnuESnEHeHvTo+RftzIP2mMPc0Sf9aSqNzULXrRZOXWxmZt1hd8c4Szux5SFWMYs5rGOAqVYAxF9Rh1tmapGp9RqFf12nRCC6ylM5re2W7mOLi/kYUwKhoQUC/XGo0TQrT3fgg/ZfRxcCgYEAyeBo8uVT2jc7phgEwf637zI8WR6L4iePPHd0K9juS98TQ2/uVmfyShgAVfn+OaiANkvWiaIfCN7r76ttcCbQSayxUfHkpryoxUdaR6ryzDmeuih9vOa0iSS7WLpHTLmnm5m1pPs9vfgUtqbj4q7BJLJ+HLNRZDMVRRsESBVowrcCgYB01mgre13mPl6nSeeKtdmjJUZkoDqaXUWiNj0jMpvCkblShnTqgvUmBue+ILEVcuBWmNSQYEdPbVqA4iS1RfGWv3W8whJKLJoYGZvSNIktjxxibx80AM1tprgXJKiGnFw5ltRWqDz/g/P0Q+K3JSc3iPNuNgoDoKDW39arzN/hvwKBgFt8p7qSsgACA4WSa/td+DOav6duZRz0YTyR+HrlRbnpMhsQ5N1gxhnlIKyTmZgtLLG0Z6E93HfqoNbOHrRaafJekWm4NkEhUDd+c8VxvWckhZuuZ32kffDT3Hdr2orDeYGe9XYhx7WLsw+E/EMWEHWkBN45Ra5oaGBZiBk93xoR
560cdb1e-a280-4e58-b34a-a56b2ce54471	cdfe9c7d-6cba-4c71-b340-39f5c4e7305a	algorithm	RSA-OAEP
3b09b115-38fe-4e19-93ee-32646ca48e15	a2653e63-2d7b-4bc4-8e53-8607dfb249a6	priority	100
5bbd09c8-b926-49c1-95ba-7b05cf7a9897	a2653e63-2d7b-4bc4-8e53-8607dfb249a6	kid	5d738437-bd8e-4646-9e92-9479e8d43261
a2053524-0d43-46d9-8686-ea56b8faf0dd	a2653e63-2d7b-4bc4-8e53-8607dfb249a6	secret	juRajW6i9sYLxpbfbdGm0Q
aa8106e2-763c-41ee-8e90-de3e40262f80	f626fe55-7671-4981-81df-e44ef5710949	keyUse	SIG
c4c862ad-479a-4073-8c52-a70c90e8227d	f626fe55-7671-4981-81df-e44ef5710949	priority	100
95625888-2f7f-4de9-80d6-256abaa68c20	f626fe55-7671-4981-81df-e44ef5710949	privateKey	MIIEpQIBAAKCAQEAu/lZORk2M//o1i6W2OAutH99pMu4l7m8yPes4n+pEhqqBQS64KtQh+zRj2j56TFVSjihPQON8quElFj08HAH83Y++G6lNOSHtEeHmZVFZZljpsSH6NZmbZvcZAgLmnw4OGSpWnx75Z8NGcnRZXk42xNesfINifgt7f68/M9eEehgoVbtK837uCwwrGf2A5A07rg+d9TeJX0DZX1jHM27iH39jRzfQozEFT/r48Opzk+EqLjE+N54n1adQsJK58j0ftOxXqDqIO/5VTh5hZBZffUpsL1h0YdggV5NA3CWfpFW5sN9ZGSeVevE1HMV45XTaGLHoSylKnK3M2IxQ7BSiwIDAQABAoIBAEE/u63rR3pjYZI/V9NudifnfVif8rKQIazs9FEnGSwUn2Hgk3H7ZZfZJ6ENQnqkykNaRvXj7vkGep34BwYTDV+AczlPRv+GOGjmz3YLb6xlw0aXP8od6i4wBR+f/08fwXAp2PN09JjvTd/ew1Xi7050edFxASwcPu03GKj03mV+V+hsQNFQKvU+lVuIjbEaeKA5JnQpzwIpVrY1/tttZkVgLtV3dn/+vo+cRAatImwJarvnXOEH5uUD/5bjlFPbi0RzkpTaJv1BTzy/7x23ChUs51tZRSEPz6oSZ0/sNzfZAnvwfdaY8MMXg9lFDyVlh9Dxd4Hw6EbPL1EFbmqeARECgYEA98Ag0K1h6F6kB0jc6CknAfolCn+IqOXY5wer3bX8jq1i9lj0xR8QdcReAq/kIn/n2xwhLdiQxk7qe8C1KYOGKQT3FQGb3FEJu8MWx7pZrKt7KplUMuMguBNd911+g7ajiJhhTOhowb+17XclZkqp7osjNiSuIwOz+EfTR40jrFMCgYEAwjuso5UnZN0TgPi0tglnE0IoSiKhImCTTMWQvESGjA+TB8U1JR3bL983Jafbx7TmuakESbAtvPjOTCAUI4ZcFFc/o5Joema8BijBHARg19/pvxSF4o1AoAN6M+4KB6l8gSePZWsmxwlT7t/6Jf+zcgIiHQ5AtAGjUfmmcER+OekCgYEA4CCXjzDLGgREL7Li5cQU3d0GFXE6mFEW4Co4eOJiEFEa7fGbvZhUs7VpttSYdf1wKvBPs8Hvdvd9eWbkfSHXBNTREdfawH0vaQUcXqNe7K5VRjxfx4lh2CTtJRMXmOBpIPF5VLIL0vs0Ahd/ZgK1qG+G8zxLrLF05m9T2677TrECgYEAqYiSqHnA184LfxAWE6njWTDun77YLHxlNaZgLaQWWFwjtq2QNKR5s7A+n1SDhi3FEZqQDo2DbqUw08tQKK3W4z5AYtKg9O2FAk9CNuUvbl2X1dvg/JzUIpbGbFtCT+vEq6rPA/Ph9eqTYzYGTnKAu2tXQUs2eKfRWPTyLJ3sGzkCgYEA8ognn4HM2J3yG2a+AT9cWsWzxpvr3EBjcvYTHWLLnZsXyD9I55vitKCzmYyrF/OwF0MDv6KF3Tk0IRB6xsDTz+SxrH2jEgSb6TtSxs5R8f9o/rLKro+DsgdxTAcjVCZmrt6Vhk89aL/dFSCm9H1jCjOT/0QuJTKbPS9qKtmo3P8=
ea762e3b-2f93-4394-a594-f4d2c4421bdd	f626fe55-7671-4981-81df-e44ef5710949	certificate	MIICmzCCAYMCBgGadqsUEzANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjUxMTEyMDYwMzM0WhcNMzUxMTEyMDYwNTE0WjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQC7+Vk5GTYz/+jWLpbY4C60f32ky7iXubzI96zif6kSGqoFBLrgq1CH7NGPaPnpMVVKOKE9A43yq4SUWPTwcAfzdj74bqU05Ie0R4eZlUVlmWOmxIfo1mZtm9xkCAuafDg4ZKlafHvlnw0ZydFleTjbE16x8g2J+C3t/rz8z14R6GChVu0rzfu4LDCsZ/YDkDTuuD531N4lfQNlfWMczbuIff2NHN9CjMQVP+vjw6nOT4SouMT43nifVp1CwkrnyPR+07FeoOog7/lVOHmFkFl99SmwvWHRh2CBXk0DcJZ+kVbmw31kZJ5V68TUcxXjldNoYsehLKUqcrczYjFDsFKLAgMBAAEwDQYJKoZIhvcNAQELBQADggEBADjG+dqGK6YH4zTDlpCTYL5yqWiacgTwrkr9Gw36JZj6f+LLJGc1r/lqvDx81c/2xHrosjZnT9aDNaaWVVAEYSW2Ih+1oYTGqsvohSF2gHYKr1NLEUzOWqp6sYsxY3lQd3WN5daloUI/obkcdVoOuxb5t1egyV8GsF3Tt/hcmpzm4+jr9Y3o7O+T5M0gwL80n+C5fkKzBCIX0TEi+VK5vYuv8/swkmjpZzj9NrkYZXDMGNYV8eHWNrymoiCeKbZqKbNK/SVG6ic/oEBRVyOMfPEtO7OwGm53S1TH3t8GLM8w17Vo60qLqEcgFBRvz5m5sNYCTzs3PVllUOgL0qeQ0v4=
2eb70e77-93fa-404a-b549-055216182fa1	1f601b2d-3f3c-4ee1-93f5-cd33a0c3c6cc	kid	3ed0052c-1007-4708-82c0-3497b75085c8
7caae7a0-46a2-4eb3-9dbd-e02a114197f7	1f601b2d-3f3c-4ee1-93f5-cd33a0c3c6cc	priority	100
c40184a3-6c99-4cc2-98c4-a8bcb4654500	1f601b2d-3f3c-4ee1-93f5-cd33a0c3c6cc	secret	jGKBzJa7FSua3xfWVN9caQ
8340005c-a670-46e8-99b7-3e0c05902923	bf7bc4cc-455f-4d74-ac97-b854b8f126e8	allow-default-scopes	true
faabc262-cc56-4850-b83a-0c773a704428	82d74645-ff8e-460e-ab07-a44369c80932	kid	ebb575e0-6a98-447c-97e1-37c876a2e327
51e62ec3-2b7c-48ba-86bf-88b0d2589fb3	82d74645-ff8e-460e-ab07-a44369c80932	secret	mKtc1ZS1Zure5wtquXmZDQJiuhiDEWLpBWY2phgKVV27UO-Oga5WRLy8sRhbO_zKE7UHVZDOwZRroPWvhZD2lsJ1YSaSrZb0yf_9hRKthz4JcGYdOqDVI7w76zdxtHhq9dkkCqgAmOdj7iYiYu7oBBcwd6eWuaHtnIi_q_3j8fw
d53e5b84-9688-4723-82c7-27f6063eba2d	82d74645-ff8e-460e-ab07-a44369c80932	algorithm	HS512
823e4504-bed3-4d07-88e6-c8e0b94e1e90	82d74645-ff8e-460e-ab07-a44369c80932	priority	100
5d81bba4-96ac-4a2f-bcd3-9fbda4f3f1a3	4dfd41fa-a9eb-4922-a0cf-d4f7a8ae24c1	max-clients	200
feee44c1-c4b5-4702-8086-fda45a7b5318	2feec9eb-9647-4299-86f6-bf7d63fe6ff0	allow-default-scopes	true
aa1bff05-ca0d-4d82-8300-5bc1ac837d98	1d42272d-82a4-4f7b-aa8a-02e0a91d8c71	algorithm	RSA-OAEP
77d39d2f-72e0-4deb-a3be-f3826bf8eb25	1d42272d-82a4-4f7b-aa8a-02e0a91d8c71	certificate	MIICozCCAYsCBgGaaGXlZzANBgkqhkiG9w0BAQsFADAVMRMwEQYDVQQDDApsb2NpLXJlYWxtMB4XDTI1MTEwOTExMzMxOVoXDTM1MTEwOTExMzQ1OVowFTETMBEGA1UEAwwKbG9jaS1yZWFsbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBANQKCIs2FpAGF4Nv3iZZT3nktpgcqcNnMdS6z7pG6wBazB7ypfgLCih6+eaDz+J/WR1jItDWZvaSHQV+o5ujmLJu+nAdRz1I2MsVPYsH9q4+ZBzgFUaJ/9QQQF/r92oF0KDEDOErJIvrZhc4WGVMjCS+OJ2W/VaOrdv6azofbkLiUrGjTEaYJgnFfD8DYyr+RvKZvXb1uCkgv7Ay8fVWGHvoKkw4S87vg4d4DKA/HzFZyMOXOGUslinG5MMIfMHFFUX2SfRrAvmXcOOKNcjYB0EhTiLyI5MMltcixTmziX+YOeXN6fZaruJ5U8HLqR5HRopldVoF9lz5zMbiuCzv9lMCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAkYztEr6baQvXYMCHk+ERJKvcAcPC+vJem/CbgAPvkVafToIUxnGOJbLU/MjZMqt2qLxwwfYtcucylE8PoqfjKhW7rImCJPQjezrPtoWoDPzEJ7JCxWoWTlpjSvlprf6zBTt+0YQ1by8OpMK+6WRvJtoNOqzzodpQ9DaqXWYxJegfU5F7bK9pVTyT3J46SIydmnX8Lfwsu+4j5W0kl91DuN1kxLb1294TogP0ERfTtCI+r8KFHGc+1SbizF1bCbpeT9VBWcfSBhyGh1UhgCsSPl9R+9xIRKPoIMmaGZ77pjJ/P7AFvu4LXa5DKgMcAsE0K84fIvC2Xa9phDjNN3zXiQ==
8e88baf9-7324-4060-9d2e-89e328d5e522	1d42272d-82a4-4f7b-aa8a-02e0a91d8c71	keyUse	ENC
16c31a8b-3967-4e1c-85ca-ee0cd942b6dc	1d42272d-82a4-4f7b-aa8a-02e0a91d8c71	priority	100
ad694f9a-1d1e-4d70-aaf0-43cb338c62f6	1d42272d-82a4-4f7b-aa8a-02e0a91d8c71	privateKey	MIIEpAIBAAKCAQEA1AoIizYWkAYXg2/eJllPeeS2mBypw2cx1LrPukbrAFrMHvKl+AsKKHr55oPP4n9ZHWMi0NZm9pIdBX6jm6OYsm76cB1HPUjYyxU9iwf2rj5kHOAVRon/1BBAX+v3agXQoMQM4Sski+tmFzhYZUyMJL44nZb9Vo6t2/prOh9uQuJSsaNMRpgmCcV8PwNjKv5G8pm9dvW4KSC/sDLx9VYYe+gqTDhLzu+Dh3gMoD8fMVnIw5c4ZSyWKcbkwwh8wcUVRfZJ9GsC+Zdw44o1yNgHQSFOIvIjkwyW1yLFObOJf5g55c3p9lqu4nlTwcupHkdGimV1WgX2XPnMxuK4LO/2UwIDAQABAoIBAGAzOyQZwovOT72ws9u3OmElnJgPrQ+70nZe2R78zOLIzwIdeaI7M/0gqh9k3xy2RVKZZzLTizxEF0mmZokW5JDT2+igx/Dsi3s75EOfNdJg+R/GpLBvrLNkOiiq0IH4KGq/983yum6Gurc/N4+h9pU2/k21MrQiIIwEpcBlgStzWd3TMlWmSy965bHkF/wET9RrIMR/SWIfgg7nm7lK+VGxVyJay15639EHd7x/rdp6LiBG2uIipZD+NA6WLuOZG86toDUcPas9Di9mkGm2EW3GAxaSz9T47CdJr5hcX7F5CqBn6tnbY5ssJnwi0HUF203Bc/6Z3wh7NGAyGxzMXxECgYEA/3WJ+vIU9K4JWgNFzfbz46rtCOlRgepCeYtoopfHBfkO/cxyK3/SBB1SUt5yWzp/sr6sADXl67932wuxtZozFBC+UKEUCDPK/Qs1TctG/KjS7mdgQvxXt6TpT9C+kTvyo8L9g+rdMrQ8Cs5qtajWaVv/ohMhqfWFk6Apn1p9kTECgYEA1Hz13YrVwyrkBGb0fjW2b09qS9FuLX6WDaR9WZ3GA9APTz4K+zYWtNFfUHwufhO4Z0zxxSlyaiwClRSuJFhFk4DgxB1mUv0wHtPppY8f0jKcnwS3JDL7K4DQ9T5suA21Zt9zUemrMwA+WM/TghTvXplHHtuUhv/fHYglx8QIvsMCgYEApwrOzN8bQNvEla1qKcH/vLF6Cce3WoI6MYwtQZSJuaggW2kihrswMyyRNkrq8CiSc+kmQ4T68WrkDsHY1G0eVVKVf9e0Z6CmbUy08EeqBXDHbMkAMw0atqUJQv22fvV6Ngc9CtO7DHq6gD51nI/olEBqKirkamR3kg666M6dKSECgYEAybvJgQeqYpx51mQYgypjhdITzN+MhszDkTg1ebt8n2oM3uK8cjur2wdcQoFjcncuf4RhlRoAciROX1M+8WqMw7l7qzVuTCPsZ5gxHul/AITkhWRoq4lrRKYLvIoDlcoOCxjh10bNLqJwjsjguYM+rsU+7GDz5idOoC7+D2ZiFxkCgYAns+C2iIbw4M1w3NSFeeVwVDzwSW0c45CNq8BqA1fKqjzS11zmrI5162e+zJI91uF32k1hbWAgrxZV0KuBS+NIhX0hf2nmiTMm3KrGSvx0MLwxX41qPHD9OMW35rzXdXV8scMHkFanJnqDzfTijvCRO3CTqxhnxr36ZEIsdJ7uAg==
e5f897b1-f6f0-4726-b4fd-e1c04974f4be	501abdae-a433-4e54-985a-a9fdf84bf3b8	priority	100
f57b045a-675d-467d-9c14-90d7b3398ca5	501abdae-a433-4e54-985a-a9fdf84bf3b8	privateKey	MIIEoAIBAAKCAQEAurT5Qp6fpzYk5s0r5rvsltqBiyMI1fI5ag2XO6Un6gnbuhJMt84ylKEU0QlT844Wmp2+iWwwklGHz6j1NSsqFYXUyTDLXukpsLfFR5fzcetWJ3FNiwQGRuodRRHFh9pvh85gPkFSRvSI6iSqSrYNSjDClEpEjH5S9Mn8UgETFlsHrqsBQCLFGOxUzuUNEe7txAT7dkSGGxZKd+qyxZGFLxnn4PT6Pc82qN8f5ZS6zkKHjCk+zea6IcdLbpmnNnoThRrMdmDfZDHLiRKrX/wHbhy+dm+K5Z+qj+/+d6+G29PgXb7z8i06YEFyYllDlzUt8pW2+eCmzZjywJcmALwbJQIDAQABAoH/MTI09Wqj60VjL2N9sbFtbgt+FuwVI3/sradl2WC/kKw9jSzVtpFcrkvnACD45KjQZ9Pzp8OEC29R04v9nos6yu/EvvKdxMxm3rov0eA/pcJLOsW+ioodA/Vn5HykSOhkLuh7x0l0EPI1tG/be+2JI2jMiPzvbk1hR39jl5rjkqxWbxwtZw0W9zY37rR2GXwmHdFbYQgDXf73MeE6/K6bbQa6sxvSftyf/W0nTDpVPWtLtdbWb2R1zLWbwslz3rhgdkWGJ/1HakIqunHW6O5eoiG/x345aqp9zo7HuJVG5+tsLfG0zQcxvRxgWSuiMZ5MLz6gdqMBga9wQPDL9WWNAoGBAO8qyXzb136/Elncs8knoIQgdMREoAw0YIZWvQB3m3swN2G9WE2oZJm/ObEAlLlIuu/ngPna/N6MYcePqt5jL4I2sMts5SGF2nP68Ta0TQ+Lldk8jklqnO5OEKNmoQklRWcIa9yBrgWn0lE6y8cYm5fMqXN8P2+/Y5HoKUPtGL5DAoGBAMfY+wpH4mAZ9OYu53BIOQdjqVw/YxKFxNky+YGfuAB+Jy+qoivGVgPnwq+YBsfVmpoB+HEl+Chyj0DHwFueXkJ+8Wcoo39/1DZ2zhnSIRNQfDNOfcfamuz2wsmlChoy+TfqHvUftCrvHOQ7Hu7+fUTBt/X9xoy/KEj9xKQVnA53AoGAMYhWBHLvdYOTBGNuJLn9R4AFTuS7lOuAFjJ+oEslO2UoAykY0bSPaTwucZciNiF2/dqfXp/ZASpn0dHSXI6EN16mTOs3pTK4pI6TSHYdA5wwI7aj7VaUO9KVJZJKxb8fWZBn7lo5NVileUdJDunsx4qOialw5e7oaz5+1V+UYUsCgYBAnEHtPPhPIZUvphJlFrR5Uxs6G7QoFN9jaTuJUN3oKuD4ZC4yANlmQdOLeZcXnFNzXxe3XRMx4He39dyWwkivLuNU+qqBWg593UMczfari+XboJDBwEc+PTkUgCsX9UrlbOe9UBarmsq4bvS9R8GwLQEQoo9Cibq4fnLIqcPeWQKBgAfVN/KUA2Fmh3Sjv8T1vdbkVo5Qsgbe9TeLbduBNqL3GunExhuLVg5tgsIkDDbSA8RuWoHrSqbIsYiRcIH9yPn5X13NM/uir0UrLu963opWpN0b9lftUbeByyY2gAUq6l9bGWb0RWSCXIPeB6F4HAa8vM2h5WadRv+SWQ2Gu/YR
cee03464-510e-4558-b664-35d8ccc0f9a1	501abdae-a433-4e54-985a-a9fdf84bf3b8	keyUse	SIG
f2673af1-d989-43fd-be0c-086bcc633948	501abdae-a433-4e54-985a-a9fdf84bf3b8	certificate	MIICozCCAYsCBgGaaGXjJjANBgkqhkiG9w0BAQsFADAVMRMwEQYDVQQDDApsb2NpLXJlYWxtMB4XDTI1MTEwOTExMzMxOVoXDTM1MTEwOTExMzQ1OVowFTETMBEGA1UEAwwKbG9jaS1yZWFsbTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBALq0+UKen6c2JObNK+a77JbagYsjCNXyOWoNlzulJ+oJ27oSTLfOMpShFNEJU/OOFpqdvolsMJJRh8+o9TUrKhWF1Mkwy17pKbC3xUeX83HrVidxTYsEBkbqHUURxYfab4fOYD5BUkb0iOokqkq2DUowwpRKRIx+UvTJ/FIBExZbB66rAUAixRjsVM7lDRHu7cQE+3ZEhhsWSnfqssWRhS8Z5+D0+j3PNqjfH+WUus5Ch4wpPs3muiHHS26ZpzZ6E4UazHZg32Qxy4kSq1/8B24cvnZviuWfqo/v/nevhtvT4F2+8/ItOmBBcmJZQ5c1LfKVtvngps2Y8sCXJgC8GyUCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAjNmlykEGuC6D4Gd90FsdRSFnxyKHMfRv7mjBXhjm6uH3RnVx0IiXlb80n2mWJBfXaEV+z6OcmOii1dlSR3+QvsfxnmKUTu6OMD8hqKmAvcBrK87XWPU3VrU+1clnG/AvuhzTMfx+SyptkeyH9nGfbxec2Pk86dc4qJTr6y5qkv6kP9K5wHZPSo0qVxhnk81N+dLl+h9sxgZSY2LkH+WwcQXN36YUWxG7RUZYm9JCxw5utJCPILspDxqKLI4R6gxJ9uV/UBhhqAkaEP0qR2oKmOwxJVwCr4O2WPbU15P2L9h/69za69ipgxRDiSerDoNOvLq4C3O4hJ607ktYIHLzIw==
7f408e40-cf08-4318-956a-d25290ae9965	f76d02eb-db36-4cc1-860e-363ac63254ab	allowed-protocol-mapper-types	saml-user-property-mapper
accc06e4-2e64-452b-b47a-4140712a0ba8	f76d02eb-db36-4cc1-860e-363ac63254ab	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
c3f9fb49-713a-40fa-8a7c-5f8c14e5c99e	f76d02eb-db36-4cc1-860e-363ac63254ab	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
d82a4161-8093-44ba-85de-16e36741d069	f76d02eb-db36-4cc1-860e-363ac63254ab	allowed-protocol-mapper-types	saml-user-attribute-mapper
b6e2d6e9-ea0b-4b7c-bcf6-010b5c5ca5d5	f76d02eb-db36-4cc1-860e-363ac63254ab	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
69f5cf5f-fdc4-42e7-b073-885099da5a52	f76d02eb-db36-4cc1-860e-363ac63254ab	allowed-protocol-mapper-types	saml-role-list-mapper
2e432128-8e1f-427e-a85e-902f61dc5e12	f76d02eb-db36-4cc1-860e-363ac63254ab	allowed-protocol-mapper-types	oidc-address-mapper
3b3545fc-5dcf-43ca-b975-ef396b50bdf2	f76d02eb-db36-4cc1-860e-363ac63254ab	allowed-protocol-mapper-types	oidc-full-name-mapper
6d250be9-3e60-4ffe-818d-24f50ebfe434	6957a15d-2473-4793-90cf-bf7923e35fe2	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
2b71b5ff-f476-4c66-8d2c-758b107026ae	6957a15d-2473-4793-90cf-bf7923e35fe2	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
6e8969f3-fa98-4545-9fda-b6c62d1276e3	6957a15d-2473-4793-90cf-bf7923e35fe2	allowed-protocol-mapper-types	oidc-address-mapper
9b2c2734-d588-4f11-86c0-5a3e1765dcac	6957a15d-2473-4793-90cf-bf7923e35fe2	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
d4eebb16-867c-4430-be64-68d257615317	6957a15d-2473-4793-90cf-bf7923e35fe2	allowed-protocol-mapper-types	saml-user-attribute-mapper
aa3575b6-22de-412c-aa1c-822f036317ab	6957a15d-2473-4793-90cf-bf7923e35fe2	allowed-protocol-mapper-types	oidc-full-name-mapper
96634667-c2d9-4040-aa95-0e99b7c5671a	6957a15d-2473-4793-90cf-bf7923e35fe2	allowed-protocol-mapper-types	saml-role-list-mapper
f1205a72-350c-4541-a581-6ccb0aa4fd8e	6957a15d-2473-4793-90cf-bf7923e35fe2	allowed-protocol-mapper-types	saml-user-property-mapper
edc41a0d-3214-46dd-920b-3bc9d7317aef	aa39d65d-762c-4690-9154-219710f831f1	client-uris-must-match	true
3f80b6ae-1262-41c2-93a9-2d1c3dd59f5e	aa39d65d-762c-4690-9154-219710f831f1	host-sending-registration-request-must-match	true
\.


--
-- Data for Name: composite_role; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.composite_role (composite, child_role) FROM stdin;
512e309b-99ec-473d-95d7-4c3c18f23636	28edd15c-1957-49f0-b471-1af6421f49b0
512e309b-99ec-473d-95d7-4c3c18f23636	8a75a6d0-0aec-4301-a08c-8882f6b8eb98
512e309b-99ec-473d-95d7-4c3c18f23636	50225f63-70ff-460b-9c38-4bf1f041e39c
512e309b-99ec-473d-95d7-4c3c18f23636	07bc92a8-b6e2-43f0-bd30-0fd17a9a578b
512e309b-99ec-473d-95d7-4c3c18f23636	92262e66-fbf3-45a1-ade4-b2850ff102d9
512e309b-99ec-473d-95d7-4c3c18f23636	8baa2eb9-04b7-4342-945c-928c36a75f18
512e309b-99ec-473d-95d7-4c3c18f23636	87515c24-0cbb-4483-8e1a-b1c84db83ac6
512e309b-99ec-473d-95d7-4c3c18f23636	d380b550-d483-4139-aeb4-87f7b3f45c89
512e309b-99ec-473d-95d7-4c3c18f23636	3a3e1955-2934-4269-8a64-47a696f1651b
512e309b-99ec-473d-95d7-4c3c18f23636	8edb52ac-2930-4d00-b9d7-7dc40e3bbb9c
512e309b-99ec-473d-95d7-4c3c18f23636	a36ce94f-949c-4f80-b6cf-49b64eaa9fbf
512e309b-99ec-473d-95d7-4c3c18f23636	692cf4ab-2e6b-42a4-92ad-dbd26a45fa71
512e309b-99ec-473d-95d7-4c3c18f23636	cfe21fc6-9182-442d-8dfe-9a30a4324e65
512e309b-99ec-473d-95d7-4c3c18f23636	f28faaf1-b93b-434a-8043-045fb054f874
512e309b-99ec-473d-95d7-4c3c18f23636	a738a8bf-3175-4a06-866d-886722909883
512e309b-99ec-473d-95d7-4c3c18f23636	db24f95b-491a-4c6b-9e6a-750394ebb4c5
512e309b-99ec-473d-95d7-4c3c18f23636	f3f796d1-1f1c-48bb-9e04-57f8f487ccb7
512e309b-99ec-473d-95d7-4c3c18f23636	3287eb45-0a80-4f8b-8518-f2899c86df76
07bc92a8-b6e2-43f0-bd30-0fd17a9a578b	3287eb45-0a80-4f8b-8518-f2899c86df76
07bc92a8-b6e2-43f0-bd30-0fd17a9a578b	a738a8bf-3175-4a06-866d-886722909883
6730a033-7401-46cd-9d13-4e5fb261a249	8900421b-ad4e-4d17-9c08-1359b04dfeb9
92262e66-fbf3-45a1-ade4-b2850ff102d9	db24f95b-491a-4c6b-9e6a-750394ebb4c5
6730a033-7401-46cd-9d13-4e5fb261a249	534b41bb-f775-49c9-8c4d-078018cda382
534b41bb-f775-49c9-8c4d-078018cda382	fc242d1d-9724-4aa5-8033-f9ee93ef191e
823154f0-f107-4c94-b2e1-44cb7db6435b	93acfef9-ccc9-440d-8382-a99ad41a46e0
512e309b-99ec-473d-95d7-4c3c18f23636	1cd41a1e-deb1-4b37-878b-30ac32368d79
6730a033-7401-46cd-9d13-4e5fb261a249	6748c93f-f293-4519-9bf4-aefc57a46f9b
6730a033-7401-46cd-9d13-4e5fb261a249	98f04a2f-68f8-4dc2-a822-000a142cd5ce
512e309b-99ec-473d-95d7-4c3c18f23636	80a58106-5cee-416f-96e3-e6380971c944
512e309b-99ec-473d-95d7-4c3c18f23636	2b658436-6f19-4145-8413-bbe62044026f
512e309b-99ec-473d-95d7-4c3c18f23636	f9ff45b0-491c-405c-b973-073ac6e1a07d
512e309b-99ec-473d-95d7-4c3c18f23636	15944995-de23-4fd2-a36f-a619304879de
512e309b-99ec-473d-95d7-4c3c18f23636	1bb716f9-ee6c-4328-ae15-3c5ab58a8ae7
512e309b-99ec-473d-95d7-4c3c18f23636	2be2aa57-4393-4e12-8c35-e35a7fe2f36e
512e309b-99ec-473d-95d7-4c3c18f23636	8d7af946-0251-49f2-925e-2febec520f32
512e309b-99ec-473d-95d7-4c3c18f23636	408bb6a0-91b7-4118-9060-0bc9d3b1768e
512e309b-99ec-473d-95d7-4c3c18f23636	0b170173-48a0-4aaa-b3fc-9aea87afc674
512e309b-99ec-473d-95d7-4c3c18f23636	71bf09ac-fed0-44f8-8027-c0a8e0d1ac7a
512e309b-99ec-473d-95d7-4c3c18f23636	acf862d3-1ff4-439f-a971-51c5c5fecf76
512e309b-99ec-473d-95d7-4c3c18f23636	4f07dee9-b0ea-4810-baa8-332067dbf651
512e309b-99ec-473d-95d7-4c3c18f23636	76bb7e8d-4adf-4733-8d93-4ad201e13ed6
512e309b-99ec-473d-95d7-4c3c18f23636	51800fb2-3ec9-44bc-96a0-0f0de248625a
512e309b-99ec-473d-95d7-4c3c18f23636	b80b8620-d619-4f50-bd91-0d7c56a9a9b6
512e309b-99ec-473d-95d7-4c3c18f23636	72e27472-d746-4296-9509-979c5cf44717
512e309b-99ec-473d-95d7-4c3c18f23636	8c96b0a8-2e00-4577-b34c-f4dc2df75aed
15944995-de23-4fd2-a36f-a619304879de	b80b8620-d619-4f50-bd91-0d7c56a9a9b6
f9ff45b0-491c-405c-b973-073ac6e1a07d	51800fb2-3ec9-44bc-96a0-0f0de248625a
f9ff45b0-491c-405c-b973-073ac6e1a07d	8c96b0a8-2e00-4577-b34c-f4dc2df75aed
221f3b69-a59e-4a34-a95a-71f10cef4e0a	0cf5be44-ba63-47c2-b752-662ef0a5885b
2e0714ed-6fbf-47eb-ae88-59ce44d6ed3b	b39c73a7-84b1-40ff-9fd9-6f314c9ede7a
4009714e-eefe-46d4-9b37-639a6edfa501	7d52ab89-86c3-4b57-bab0-02bbe460212e
4009714e-eefe-46d4-9b37-639a6edfa501	38244948-f337-4b8b-9c15-fdc672bc80f9
710ed846-759e-45bc-afa4-bb5ef183d08c	4862f2b4-4e36-42cd-a524-6dddbb43e551
929392ee-0474-4ed0-a64e-9b0132025c91	eb9be962-f203-49c6-9075-ce80642bf363
929392ee-0474-4ed0-a64e-9b0132025c91	5df2f1e3-7508-4fbb-889f-c8b70f84ec3b
929392ee-0474-4ed0-a64e-9b0132025c91	f24cb736-a373-4bd6-a34a-ab059b81f98f
929392ee-0474-4ed0-a64e-9b0132025c91	710ed846-759e-45bc-afa4-bb5ef183d08c
929392ee-0474-4ed0-a64e-9b0132025c91	6d7b0d9a-0ac1-481e-aebb-8acd9d5c3e39
fd6368e9-9e26-469e-8d79-707f3c20354a	5160c2df-3ab3-465b-b113-a61e60faf3bf
fd6368e9-9e26-469e-8d79-707f3c20354a	995efc57-65a8-4262-9f91-7b9d83580c90
fd6368e9-9e26-469e-8d79-707f3c20354a	7d52ab89-86c3-4b57-bab0-02bbe460212e
fd6368e9-9e26-469e-8d79-707f3c20354a	b1939686-0827-4787-a141-9e7dd5fc1b82
fd6368e9-9e26-469e-8d79-707f3c20354a	8f94747b-5e4b-4d79-bf0c-cd4923506035
fd6368e9-9e26-469e-8d79-707f3c20354a	221f3b69-a59e-4a34-a95a-71f10cef4e0a
fd6368e9-9e26-469e-8d79-707f3c20354a	ae3061fa-e4e7-4bf4-a3af-ca8d12cdb57d
fd6368e9-9e26-469e-8d79-707f3c20354a	808637e3-7023-4d84-a56a-4f51c7ec23c4
fd6368e9-9e26-469e-8d79-707f3c20354a	a7120f7b-a46b-4f6a-9e97-0eea0f64174a
fd6368e9-9e26-469e-8d79-707f3c20354a	4009714e-eefe-46d4-9b37-639a6edfa501
fd6368e9-9e26-469e-8d79-707f3c20354a	fdf28825-1691-44e5-88c3-2c8c9a0aa41e
fd6368e9-9e26-469e-8d79-707f3c20354a	4e989ddd-8be8-4533-bfde-cad630193577
fd6368e9-9e26-469e-8d79-707f3c20354a	ffb45a5a-3d46-434b-910a-2894fae5d4d9
fd6368e9-9e26-469e-8d79-707f3c20354a	dd7c767b-2b46-4089-a64a-97fd1456b645
fd6368e9-9e26-469e-8d79-707f3c20354a	38244948-f337-4b8b-9c15-fdc672bc80f9
fd6368e9-9e26-469e-8d79-707f3c20354a	abfd79ad-9ca7-4f0b-b54b-78a9c1e9f28e
fd6368e9-9e26-469e-8d79-707f3c20354a	6bf59d64-e73b-44b6-93cc-75d66c644214
fd6368e9-9e26-469e-8d79-707f3c20354a	0cf5be44-ba63-47c2-b752-662ef0a5885b
512e309b-99ec-473d-95d7-4c3c18f23636	15e7eb74-0454-4c45-a368-ab2c13e114ee
\.


--
-- Data for Name: credential; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.credential (id, salt, type, user_id, created_date, user_label, secret_data, credential_data, priority) FROM stdin;
404e80ab-ba9e-4a84-b2f6-832d20ee8f10	\N	password	80745a6f-6e51-434c-8620-e501b3764a8f	1762927516536	\N	{"value":"ftu7geaytAaFVXwTCNfCaV9J8OrRS3DiGrpb5dBizzY=","salt":"iVkrH1/Qj1HqS+6/9Al1+A==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
a6225f6e-0efc-4d1e-a00b-52929d810950	\N	password	4eb678c6-f977-46ba-baaf-c9bf4bec62ef	1764321070121	\N	{"value":"g3fcVviQ2lIbgrhAU/qjgdwNsi8gVRKv4CndowVnguU=","salt":"BQvBN98UnJ9oCC2vYy1ERw==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
b24742f4-f524-4c5f-a2cb-98f992714fe9	\N	password	3a050261-b704-426e-a7c2-68b69ebeb90b	1764321070430	\N	{"value":"JxkiwhaTHUg77fh6TH4QzcSZHvK31bcVxF5+pnrfk7g=","salt":"J9JHVM7TY5eiWvf8jON4rQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
d0c88ba4-a03e-4fe4-804a-e5ca8a4b7b5a	\N	password	7d756359-1b87-442d-a2e8-e4785dd5aaa4	1764321070703	\N	{"value":"gcwiPHWI5UUEFlxFRoJgMVIbf97kUlT2ZCqrP3fCzKg=","salt":"KxJS2Vkz9guXN599Diu3zQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
dd9c6a0f-5989-458b-947a-b00d67e40935	\N	password	9d478593-8658-473d-9f6a-cfb8530385b3	1764321070942	\N	{"value":"GL2Jf7f+Qsy8jUAqzsXVzKVyUn8+Ov4eHfO5qhokPlA=","salt":"qAH6H0f0uYLE8mgsA35MSA==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
ca42242d-7a5b-49fb-a726-94cc3ebbad5e	\N	password	09ef75b6-5d8f-443f-ab88-1e6e4fdd09a0	1764321071175	\N	{"value":"xELDHXIMz2SB3rXkQ9aRP0ky2HavG5+lGfuLk8vB8q4=","salt":"7Ak1Kjz9Vh5UlzpakgGGvw==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
e6fb331b-3839-4365-aeb7-49463902c2e0	\N	password	a03e7f08-f0c7-4c40-97d6-e3bff7d00057	1764321071424	\N	{"value":"23lKH6jBKcSgS9ngaONFjmCN1AtRUsYPawB+y0btWDc=","salt":"5hcE9Xn4MCo3O6HLcXc5uQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
84251835-7dc3-4b30-95db-345a3da58d94	\N	password	8a98e8b9-37ff-4a89-b8d8-a7e4d45d4f1e	1764321071664	\N	{"value":"HSBCnNrDksQosQ9OSswzK1ihQ5+YsYwW+RXbRD6i6E4=","salt":"nEc2eDIJ1SDA2KsKs4sxuw==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
2bce277c-62c4-4a90-a0a3-3c7eba2df287	\N	password	8c2bd715-2950-4ad6-a43b-7d75874ed33d	1764321071894	\N	{"value":"O1/tL0gpgjuBrndqc3NhzmsKVoeZ9LWMMcqx+7a/NqE=","salt":"sH32CKT3qFstawP5ljgOMw==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
60014adf-0511-4848-91dd-9c0dc54b445a	\N	password	a8973d47-25d6-4904-ab93-4b30aac43dba	1764321072122	\N	{"value":"SpuRAjEWVzSgVnfyPK7/LAfFhaNCZlKxVS2uvlE2NV0=","salt":"hnvmAplYDuS31+1bx6EeDw==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
0c6686be-24f0-4813-97bd-7bb0c89ec71d	\N	password	4c8aa205-60a4-4caf-8b62-34777a9784dc	1764321072346	\N	{"value":"hqExNklwPtrhWNdYn5nCL/oQtOzOG57r8TKjRsQhpkg=","salt":"F0M3feF5WSzYGB3tvU5r+A==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
a2d7b307-c5be-43ea-bca2-a1035251e98f	\N	password	27e24793-eb80-4ba8-9ace-95f432ca5199	1764321072576	\N	{"value":"T8o5lVvOctKiCgpnaSyGfcoOGq4TGwv1p1m3uNSg7m8=","salt":"mmTZOXP7m0bQTM2dOIYSjA==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
1abd804d-e12f-44fa-9073-05248bbb9428	\N	password	cf8665b4-ef04-45b5-9b90-b3864e58e0ff	1764321072838	\N	{"value":"/V37bwJtBFGQI2a8nv11KasC+3gLe0HJOGItVZw8mus=","salt":"9ZgIncL3z2ahYC7CNDe0Tw==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
147a4356-bb0f-4fca-8293-8d39531077c4	\N	password	7b0afce6-4207-467a-9e67-e013d8f70566	1764321073106	\N	{"value":"Z2E58jlq1BK34atKpw0BtDsH6oMaXOsGre38c/KXlDI=","salt":"tymRwj1+PeBUDnliL6Zq4Q==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
2aae5d40-ac84-4976-b6c2-de271c11c289	\N	password	75729648-21f9-4808-aaeb-0a9f2aaed554	1764321073333	\N	{"value":"OeM5+0sG9IHLWwFGGMVOF//cxrsfJ14Kb787tpGhHv4=","salt":"touTgAkenl9dH97+MuLsLw==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
832e1af3-beab-40c0-8ea8-bc7f477b65b8	\N	password	ce35d0b0-2a72-4a57-8fac-dcb8348f4c41	1764321073558	\N	{"value":"sTjKrblaIrO9vh1s9tZtbK21mIGYv7Anz4Hyym3YcTs=","salt":"xoQVH9oG+rahwfySGjZZPA==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
5bc3ff1a-0c3f-4e0c-9312-1334199de184	\N	password	8ad53006-5ab5-45fb-84c0-497a6424d782	1764321073783	\N	{"value":"SjGHGunTShrJuxLdLbXRmnqyW3rZkKKOePQ/t6t+72c=","salt":"mxaMInqv4IlSCExB5NHsdQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
a12c2a7c-cc6a-49cd-93dd-37face8619d8	\N	password	a50fc325-a397-4f18-bb31-75cc1191ffa8	1764321074037	\N	{"value":"mPShEwnhZHUzjX4hnT2EH06FPhj5tDVyOl8ICiNzvvE=","salt":"ubVxJlMg1hOGPrX7H51RpQ==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
1a698437-2d1c-4633-aa48-7824fb01bffd	\N	password	e0f4cddc-281e-48f7-8ca7-6dbbd0c0a96a	1764321074286	\N	{"value":"1AiInpBNiM8kVffXZWKn5FxFbaJt1jZYVBa3rZ+Hjuw=","salt":"jqXSLrwVYr3qS5pjRREuxg==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
ecffde03-f274-463d-aeb9-0cbbdf6237b1	\N	password	b1e6055e-734d-4367-8330-975869b72a10	1764321074516	\N	{"value":"V93AmiwkAjRQ0hUBK8fTKK4ZbNqvCs++gA60k2ry/yk=","salt":"dBOmvoA3icAQUo5Dn+V6sA==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
af521363-eeb7-44c5-b0df-c7f06bd3063b	\N	password	32ca4097-7490-4a9a-aecf-8c948a2a0e41	1764321074733	\N	{"value":"WytbL6FbgPljOJ3Z88KJGsShNNc9U1Ds54f4GKXwOhY=","salt":"lacJGokwOTTeu6gz/zA4Vg==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
5a47f8f5-1782-46cd-bfa8-b87c75719c52	\N	password	cd2e474a-f099-4cce-ac9f-4d047fd00a01	1764321439165	\N	{"value":"IKJRKUvzKfSkDymzwH+Wb6k2vbmM/4N6sryVOeZZg9k=","salt":"mv6VCQF/bx8mg9Fa23XrHA==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
\.


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/jpa-changelog-1.0.0.Final.xml	2025-11-12 06:04:40.387926	1	EXECUTED	9:6f1016664e21e16d26517a4418f5e3df	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.29.1	\N	\N	2927473611
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/db2-jpa-changelog-1.0.0.Final.xml	2025-11-12 06:04:40.529477	2	MARK_RAN	9:828775b1596a07d1200ba1d49e5e3941	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.29.1	\N	\N	2927473611
1.1.0.Beta1	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Beta1.xml	2025-11-12 06:04:40.970072	3	EXECUTED	9:5f090e44a7d595883c1fb61f4b41fd38	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=CLIENT_ATTRIBUTES; createTable tableName=CLIENT_SESSION_NOTE; createTable tableName=APP_NODE_REGISTRATIONS; addColumn table...		\N	4.29.1	\N	\N	2927473611
1.1.0.Final	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Final.xml	2025-11-12 06:04:41.011922	4	EXECUTED	9:c07e577387a3d2c04d1adc9aaad8730e	renameColumn newColumnName=EVENT_TIME, oldColumnName=TIME, tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	2927473611
1.2.0.Beta1	psilva@redhat.com	META-INF/jpa-changelog-1.2.0.Beta1.xml	2025-11-12 06:04:41.958627	5	EXECUTED	9:b68ce996c655922dbcd2fe6b6ae72686	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.29.1	\N	\N	2927473611
1.2.0.Beta1	psilva@redhat.com	META-INF/db2-jpa-changelog-1.2.0.Beta1.xml	2025-11-12 06:04:42.020959	6	MARK_RAN	9:543b5c9989f024fe35c6f6c5a97de88e	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.29.1	\N	\N	2927473611
1.2.0.RC1	bburke@redhat.com	META-INF/jpa-changelog-1.2.0.CR1.xml	2025-11-12 06:04:42.348038	7	EXECUTED	9:765afebbe21cf5bbca048e632df38336	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.29.1	\N	\N	2927473611
1.2.0.RC1	bburke@redhat.com	META-INF/db2-jpa-changelog-1.2.0.CR1.xml	2025-11-12 06:04:42.414522	8	MARK_RAN	9:db4a145ba11a6fdaefb397f6dbf829a1	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.29.1	\N	\N	2927473611
1.2.0.Final	keycloak	META-INF/jpa-changelog-1.2.0.Final.xml	2025-11-12 06:04:42.465538	9	EXECUTED	9:9d05c7be10cdb873f8bcb41bc3a8ab23	update tableName=CLIENT; update tableName=CLIENT; update tableName=CLIENT		\N	4.29.1	\N	\N	2927473611
1.3.0	bburke@redhat.com	META-INF/jpa-changelog-1.3.0.xml	2025-11-12 06:04:42.891599	10	EXECUTED	9:18593702353128d53111f9b1ff0b82b8	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=ADMI...		\N	4.29.1	\N	\N	2927473611
1.4.0	bburke@redhat.com	META-INF/jpa-changelog-1.4.0.xml	2025-11-12 06:04:43.327733	11	EXECUTED	9:6122efe5f090e41a85c0f1c9e52cbb62	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	2927473611
1.4.0	bburke@redhat.com	META-INF/db2-jpa-changelog-1.4.0.xml	2025-11-12 06:04:43.368948	12	MARK_RAN	9:e1ff28bf7568451453f844c5d54bb0b5	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	2927473611
1.5.0	bburke@redhat.com	META-INF/jpa-changelog-1.5.0.xml	2025-11-12 06:04:43.58714	13	EXECUTED	9:7af32cd8957fbc069f796b61217483fd	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	2927473611
1.6.1_from15	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-11-12 06:04:43.685509	14	EXECUTED	9:6005e15e84714cd83226bf7879f54190	addColumn tableName=REALM; addColumn tableName=KEYCLOAK_ROLE; addColumn tableName=CLIENT; createTable tableName=OFFLINE_USER_SESSION; createTable tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_US_SES_PK2, tableName=...		\N	4.29.1	\N	\N	2927473611
1.6.1_from16-pre	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-11-12 06:04:43.698951	15	MARK_RAN	9:bf656f5a2b055d07f314431cae76f06c	delete tableName=OFFLINE_CLIENT_SESSION; delete tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2927473611
1.6.1_from16	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-11-12 06:04:43.730975	16	MARK_RAN	9:f8dadc9284440469dcf71e25ca6ab99b	dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_US_SES_PK, tableName=OFFLINE_USER_SESSION; dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_CL_SES_PK, tableName=OFFLINE_CLIENT_SESSION; addColumn tableName=OFFLINE_USER_SESSION; update tableName=OF...		\N	4.29.1	\N	\N	2927473611
1.6.1	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-11-12 06:04:43.762609	17	EXECUTED	9:d41d8cd98f00b204e9800998ecf8427e	empty		\N	4.29.1	\N	\N	2927473611
1.7.0	bburke@redhat.com	META-INF/jpa-changelog-1.7.0.xml	2025-11-12 06:04:43.988811	18	EXECUTED	9:3368ff0be4c2855ee2dd9ca813b38d8e	createTable tableName=KEYCLOAK_GROUP; createTable tableName=GROUP_ROLE_MAPPING; createTable tableName=GROUP_ATTRIBUTE; createTable tableName=USER_GROUP_MEMBERSHIP; createTable tableName=REALM_DEFAULT_GROUPS; addColumn tableName=IDENTITY_PROVIDER; ...		\N	4.29.1	\N	\N	2927473611
1.8.0	mposolda@redhat.com	META-INF/jpa-changelog-1.8.0.xml	2025-11-12 06:04:44.244775	19	EXECUTED	9:8ac2fb5dd030b24c0570a763ed75ed20	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.29.1	\N	\N	2927473611
1.8.0-2	keycloak	META-INF/jpa-changelog-1.8.0.xml	2025-11-12 06:04:44.285314	20	EXECUTED	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.29.1	\N	\N	2927473611
26.0.0-33201-org-redirect-url	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-12 06:05:06.729071	144	EXECUTED	9:4d0e22b0ac68ebe9794fa9cb752ea660	addColumn tableName=ORG		\N	4.29.1	\N	\N	2927473611
1.8.0	mposolda@redhat.com	META-INF/db2-jpa-changelog-1.8.0.xml	2025-11-12 06:04:44.320952	21	MARK_RAN	9:831e82914316dc8a57dc09d755f23c51	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.29.1	\N	\N	2927473611
1.8.0-2	keycloak	META-INF/db2-jpa-changelog-1.8.0.xml	2025-11-12 06:04:44.339038	22	MARK_RAN	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.29.1	\N	\N	2927473611
1.9.0	mposolda@redhat.com	META-INF/jpa-changelog-1.9.0.xml	2025-11-12 06:04:45.174629	23	EXECUTED	9:bc3d0f9e823a69dc21e23e94c7a94bb1	update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=REALM; update tableName=REALM; customChange; dr...		\N	4.29.1	\N	\N	2927473611
1.9.1	keycloak	META-INF/jpa-changelog-1.9.1.xml	2025-11-12 06:04:45.256298	24	EXECUTED	9:c9999da42f543575ab790e76439a2679	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=PUBLIC_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.29.1	\N	\N	2927473611
1.9.1	keycloak	META-INF/db2-jpa-changelog-1.9.1.xml	2025-11-12 06:04:45.267421	25	MARK_RAN	9:0d6c65c6f58732d81569e77b10ba301d	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.29.1	\N	\N	2927473611
1.9.2	keycloak	META-INF/jpa-changelog-1.9.2.xml	2025-11-12 06:04:48.203522	26	EXECUTED	9:fc576660fc016ae53d2d4778d84d86d0	createIndex indexName=IDX_USER_EMAIL, tableName=USER_ENTITY; createIndex indexName=IDX_USER_ROLE_MAPPING, tableName=USER_ROLE_MAPPING; createIndex indexName=IDX_USER_GROUP_MAPPING, tableName=USER_GROUP_MEMBERSHIP; createIndex indexName=IDX_USER_CO...		\N	4.29.1	\N	\N	2927473611
authz-2.0.0	psilva@redhat.com	META-INF/jpa-changelog-authz-2.0.0.xml	2025-11-12 06:04:48.46168	27	EXECUTED	9:43ed6b0da89ff77206289e87eaa9c024	createTable tableName=RESOURCE_SERVER; addPrimaryKey constraintName=CONSTRAINT_FARS, tableName=RESOURCE_SERVER; addUniqueConstraint constraintName=UK_AU8TT6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER; createTable tableName=RESOURCE_SERVER_RESOU...		\N	4.29.1	\N	\N	2927473611
authz-2.5.1	psilva@redhat.com	META-INF/jpa-changelog-authz-2.5.1.xml	2025-11-12 06:04:48.478354	28	EXECUTED	9:44bae577f551b3738740281eceb4ea70	update tableName=RESOURCE_SERVER_POLICY		\N	4.29.1	\N	\N	2927473611
2.1.0-KEYCLOAK-5461	bburke@redhat.com	META-INF/jpa-changelog-2.1.0.xml	2025-11-12 06:04:48.638092	29	EXECUTED	9:bd88e1f833df0420b01e114533aee5e8	createTable tableName=BROKER_LINK; createTable tableName=FED_USER_ATTRIBUTE; createTable tableName=FED_USER_CONSENT; createTable tableName=FED_USER_CONSENT_ROLE; createTable tableName=FED_USER_CONSENT_PROT_MAPPER; createTable tableName=FED_USER_CR...		\N	4.29.1	\N	\N	2927473611
2.2.0	bburke@redhat.com	META-INF/jpa-changelog-2.2.0.xml	2025-11-12 06:04:48.685665	30	EXECUTED	9:a7022af5267f019d020edfe316ef4371	addColumn tableName=ADMIN_EVENT_ENTITY; createTable tableName=CREDENTIAL_ATTRIBUTE; createTable tableName=FED_CREDENTIAL_ATTRIBUTE; modifyDataType columnName=VALUE, tableName=CREDENTIAL; addForeignKeyConstraint baseTableName=FED_CREDENTIAL_ATTRIBU...		\N	4.29.1	\N	\N	2927473611
2.3.0	bburke@redhat.com	META-INF/jpa-changelog-2.3.0.xml	2025-11-12 06:04:48.894798	31	EXECUTED	9:fc155c394040654d6a79227e56f5e25a	createTable tableName=FEDERATED_USER; addPrimaryKey constraintName=CONSTR_FEDERATED_USER, tableName=FEDERATED_USER; dropDefaultValue columnName=TOTP, tableName=USER_ENTITY; dropColumn columnName=TOTP, tableName=USER_ENTITY; addColumn tableName=IDE...		\N	4.29.1	\N	\N	2927473611
2.4.0	bburke@redhat.com	META-INF/jpa-changelog-2.4.0.xml	2025-11-12 06:04:48.912802	32	EXECUTED	9:eac4ffb2a14795e5dc7b426063e54d88	customChange		\N	4.29.1	\N	\N	2927473611
2.5.0	bburke@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-11-12 06:04:48.957622	33	EXECUTED	9:54937c05672568c4c64fc9524c1e9462	customChange; modifyDataType columnName=USER_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2927473611
2.5.0-unicode-oracle	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-11-12 06:04:48.991145	34	MARK_RAN	9:3a32bace77c84d7678d035a7f5a8084e	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.29.1	\N	\N	2927473611
2.5.0-unicode-other-dbs	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-11-12 06:04:49.092265	35	EXECUTED	9:33d72168746f81f98ae3a1e8e0ca3554	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.29.1	\N	\N	2927473611
2.5.0-duplicate-email-support	slawomir@dabek.name	META-INF/jpa-changelog-2.5.0.xml	2025-11-12 06:04:49.14949	36	EXECUTED	9:61b6d3d7a4c0e0024b0c839da283da0c	addColumn tableName=REALM		\N	4.29.1	\N	\N	2927473611
2.5.0-unique-group-names	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-11-12 06:04:49.168326	37	EXECUTED	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	2927473611
2.5.1	bburke@redhat.com	META-INF/jpa-changelog-2.5.1.xml	2025-11-12 06:04:49.182182	38	EXECUTED	9:a2b870802540cb3faa72098db5388af3	addColumn tableName=FED_USER_CONSENT		\N	4.29.1	\N	\N	2927473611
3.0.0	bburke@redhat.com	META-INF/jpa-changelog-3.0.0.xml	2025-11-12 06:04:49.194449	39	EXECUTED	9:132a67499ba24bcc54fb5cbdcfe7e4c0	addColumn tableName=IDENTITY_PROVIDER		\N	4.29.1	\N	\N	2927473611
3.2.0-fix	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-11-12 06:04:49.19971	40	MARK_RAN	9:938f894c032f5430f2b0fafb1a243462	addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS		\N	4.29.1	\N	\N	2927473611
3.2.0-fix-with-keycloak-5416	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-11-12 06:04:49.207582	41	MARK_RAN	9:845c332ff1874dc5d35974b0babf3006	dropIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS; addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS; createIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS		\N	4.29.1	\N	\N	2927473611
3.2.0-fix-offline-sessions	hmlnarik	META-INF/jpa-changelog-3.2.0.xml	2025-11-12 06:04:49.226855	42	EXECUTED	9:fc86359c079781adc577c5a217e4d04c	customChange		\N	4.29.1	\N	\N	2927473611
3.2.0-fixed	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-11-12 06:04:56.872452	43	EXECUTED	9:59a64800e3c0d09b825f8a3b444fa8f4	addColumn tableName=REALM; dropPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_PK2, tableName=OFFLINE_CLIENT_SESSION; dropColumn columnName=CLIENT_SESSION_ID, tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_P...		\N	4.29.1	\N	\N	2927473611
3.3.0	keycloak	META-INF/jpa-changelog-3.3.0.xml	2025-11-12 06:04:56.890138	44	EXECUTED	9:d48d6da5c6ccf667807f633fe489ce88	addColumn tableName=USER_ENTITY		\N	4.29.1	\N	\N	2927473611
authz-3.4.0.CR1-resource-server-pk-change-part1	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-11-12 06:04:56.913034	45	EXECUTED	9:dde36f7973e80d71fceee683bc5d2951	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_RESOURCE; addColumn tableName=RESOURCE_SERVER_SCOPE		\N	4.29.1	\N	\N	2927473611
authz-3.4.0.CR1-resource-server-pk-change-part2-KEYCLOAK-6095	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-11-12 06:04:56.945666	46	EXECUTED	9:b855e9b0a406b34fa323235a0cf4f640	customChange		\N	4.29.1	\N	\N	2927473611
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-11-12 06:04:56.950308	47	MARK_RAN	9:51abbacd7b416c50c4421a8cabf7927e	dropIndex indexName=IDX_RES_SERV_POL_RES_SERV, tableName=RESOURCE_SERVER_POLICY; dropIndex indexName=IDX_RES_SRV_RES_RES_SRV, tableName=RESOURCE_SERVER_RESOURCE; dropIndex indexName=IDX_RES_SRV_SCOPE_RES_SRV, tableName=RESOURCE_SERVER_SCOPE		\N	4.29.1	\N	\N	2927473611
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed-nodropindex	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-11-12 06:04:57.489999	48	EXECUTED	9:bdc99e567b3398bac83263d375aad143	addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_POLICY; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_RESOURCE; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, ...		\N	4.29.1	\N	\N	2927473611
authn-3.4.0.CR1-refresh-token-max-reuse	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-11-12 06:04:57.503638	49	EXECUTED	9:d198654156881c46bfba39abd7769e69	addColumn tableName=REALM		\N	4.29.1	\N	\N	2927473611
3.4.0	keycloak	META-INF/jpa-changelog-3.4.0.xml	2025-11-12 06:04:57.574506	50	EXECUTED	9:cfdd8736332ccdd72c5256ccb42335db	addPrimaryKey constraintName=CONSTRAINT_REALM_DEFAULT_ROLES, tableName=REALM_DEFAULT_ROLES; addPrimaryKey constraintName=CONSTRAINT_COMPOSITE_ROLE, tableName=COMPOSITE_ROLE; addPrimaryKey constraintName=CONSTR_REALM_DEFAULT_GROUPS, tableName=REALM...		\N	4.29.1	\N	\N	2927473611
3.4.0-KEYCLOAK-5230	hmlnarik@redhat.com	META-INF/jpa-changelog-3.4.0.xml	2025-11-12 06:04:59.222834	51	EXECUTED	9:7c84de3d9bd84d7f077607c1a4dcb714	createIndex indexName=IDX_FU_ATTRIBUTE, tableName=FED_USER_ATTRIBUTE; createIndex indexName=IDX_FU_CONSENT, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CONSENT_RU, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CREDENTIAL, t...		\N	4.29.1	\N	\N	2927473611
3.4.1	psilva@redhat.com	META-INF/jpa-changelog-3.4.1.xml	2025-11-12 06:04:59.235945	52	EXECUTED	9:5a6bb36cbefb6a9d6928452c0852af2d	modifyDataType columnName=VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2927473611
3.4.2	keycloak	META-INF/jpa-changelog-3.4.2.xml	2025-11-12 06:04:59.247632	53	EXECUTED	9:8f23e334dbc59f82e0a328373ca6ced0	update tableName=REALM		\N	4.29.1	\N	\N	2927473611
3.4.2-KEYCLOAK-5172	mkanis@redhat.com	META-INF/jpa-changelog-3.4.2.xml	2025-11-12 06:04:59.259094	54	EXECUTED	9:9156214268f09d970cdf0e1564d866af	update tableName=CLIENT		\N	4.29.1	\N	\N	2927473611
4.0.0-KEYCLOAK-6335	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-11-12 06:04:59.277981	55	EXECUTED	9:db806613b1ed154826c02610b7dbdf74	createTable tableName=CLIENT_AUTH_FLOW_BINDINGS; addPrimaryKey constraintName=C_CLI_FLOW_BIND, tableName=CLIENT_AUTH_FLOW_BINDINGS		\N	4.29.1	\N	\N	2927473611
4.0.0-CLEANUP-UNUSED-TABLE	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-11-12 06:04:59.297884	56	EXECUTED	9:229a041fb72d5beac76bb94a5fa709de	dropTable tableName=CLIENT_IDENTITY_PROV_MAPPING		\N	4.29.1	\N	\N	2927473611
4.0.0-KEYCLOAK-6228	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-11-12 06:04:59.513296	57	EXECUTED	9:079899dade9c1e683f26b2aa9ca6ff04	dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; dropNotNullConstraint columnName=CLIENT_ID, tableName=USER_CONSENT; addColumn tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHO...		\N	4.29.1	\N	\N	2927473611
4.0.0-KEYCLOAK-5579-fixed	mposolda@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-11-12 06:05:01.094665	58	EXECUTED	9:139b79bcbbfe903bb1c2d2a4dbf001d9	dropForeignKeyConstraint baseTableName=CLIENT_TEMPLATE_ATTRIBUTES, constraintName=FK_CL_TEMPL_ATTR_TEMPL; renameTable newTableName=CLIENT_SCOPE_ATTRIBUTES, oldTableName=CLIENT_TEMPLATE_ATTRIBUTES; renameColumn newColumnName=SCOPE_ID, oldColumnName...		\N	4.29.1	\N	\N	2927473611
authz-4.0.0.CR1	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.CR1.xml	2025-11-12 06:05:01.174244	59	EXECUTED	9:b55738ad889860c625ba2bf483495a04	createTable tableName=RESOURCE_SERVER_PERM_TICKET; addPrimaryKey constraintName=CONSTRAINT_FAPMT, tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRHO213XCX4WNKOG82SSPMT...		\N	4.29.1	\N	\N	2927473611
authz-4.0.0.Beta3	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.Beta3.xml	2025-11-12 06:05:01.197329	60	EXECUTED	9:e0057eac39aa8fc8e09ac6cfa4ae15fe	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRPO2128CX4WNKOG82SSRFY, referencedTableName=RESOURCE_SERVER_POLICY		\N	4.29.1	\N	\N	2927473611
authz-4.2.0.Final	mhajas@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2025-11-12 06:05:01.222295	61	EXECUTED	9:42a33806f3a0443fe0e7feeec821326c	createTable tableName=RESOURCE_URIS; addForeignKeyConstraint baseTableName=RESOURCE_URIS, constraintName=FK_RESOURCE_SERVER_URIS, referencedTableName=RESOURCE_SERVER_RESOURCE; customChange; dropColumn columnName=URI, tableName=RESOURCE_SERVER_RESO...		\N	4.29.1	\N	\N	2927473611
authz-4.2.0.Final-KEYCLOAK-9944	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2025-11-12 06:05:01.234663	62	EXECUTED	9:9968206fca46eecc1f51db9c024bfe56	addPrimaryKey constraintName=CONSTRAINT_RESOUR_URIS_PK, tableName=RESOURCE_URIS		\N	4.29.1	\N	\N	2927473611
4.2.0-KEYCLOAK-6313	wadahiro@gmail.com	META-INF/jpa-changelog-4.2.0.xml	2025-11-12 06:05:01.247714	63	EXECUTED	9:92143a6daea0a3f3b8f598c97ce55c3d	addColumn tableName=REQUIRED_ACTION_PROVIDER		\N	4.29.1	\N	\N	2927473611
4.3.0-KEYCLOAK-7984	wadahiro@gmail.com	META-INF/jpa-changelog-4.3.0.xml	2025-11-12 06:05:01.257804	64	EXECUTED	9:82bab26a27195d889fb0429003b18f40	update tableName=REQUIRED_ACTION_PROVIDER		\N	4.29.1	\N	\N	2927473611
4.6.0-KEYCLOAK-7950	psilva@redhat.com	META-INF/jpa-changelog-4.6.0.xml	2025-11-12 06:05:01.267893	65	EXECUTED	9:e590c88ddc0b38b0ae4249bbfcb5abc3	update tableName=RESOURCE_SERVER_RESOURCE		\N	4.29.1	\N	\N	2927473611
4.6.0-KEYCLOAK-8377	keycloak	META-INF/jpa-changelog-4.6.0.xml	2025-11-12 06:05:01.405198	66	EXECUTED	9:5c1f475536118dbdc38d5d7977950cc0	createTable tableName=ROLE_ATTRIBUTE; addPrimaryKey constraintName=CONSTRAINT_ROLE_ATTRIBUTE_PK, tableName=ROLE_ATTRIBUTE; addForeignKeyConstraint baseTableName=ROLE_ATTRIBUTE, constraintName=FK_ROLE_ATTRIBUTE_ID, referencedTableName=KEYCLOAK_ROLE...		\N	4.29.1	\N	\N	2927473611
4.6.0-KEYCLOAK-8555	gideonray@gmail.com	META-INF/jpa-changelog-4.6.0.xml	2025-11-12 06:05:01.537027	67	EXECUTED	9:e7c9f5f9c4d67ccbbcc215440c718a17	createIndex indexName=IDX_COMPONENT_PROVIDER_TYPE, tableName=COMPONENT		\N	4.29.1	\N	\N	2927473611
4.7.0-KEYCLOAK-1267	sguilhen@redhat.com	META-INF/jpa-changelog-4.7.0.xml	2025-11-12 06:05:01.552844	68	EXECUTED	9:88e0bfdda924690d6f4e430c53447dd5	addColumn tableName=REALM		\N	4.29.1	\N	\N	2927473611
4.7.0-KEYCLOAK-7275	keycloak	META-INF/jpa-changelog-4.7.0.xml	2025-11-12 06:05:01.750957	69	EXECUTED	9:f53177f137e1c46b6a88c59ec1cb5218	renameColumn newColumnName=CREATED_ON, oldColumnName=LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION; addNotNullConstraint columnName=CREATED_ON, tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_USER_SESSION; customChange; createIn...		\N	4.29.1	\N	\N	2927473611
4.8.0-KEYCLOAK-8835	sguilhen@redhat.com	META-INF/jpa-changelog-4.8.0.xml	2025-11-12 06:05:01.772315	70	EXECUTED	9:a74d33da4dc42a37ec27121580d1459f	addNotNullConstraint columnName=SSO_MAX_LIFESPAN_REMEMBER_ME, tableName=REALM; addNotNullConstraint columnName=SSO_IDLE_TIMEOUT_REMEMBER_ME, tableName=REALM		\N	4.29.1	\N	\N	2927473611
authz-7.0.0-KEYCLOAK-10443	psilva@redhat.com	META-INF/jpa-changelog-authz-7.0.0.xml	2025-11-12 06:05:01.786688	71	EXECUTED	9:fd4ade7b90c3b67fae0bfcfcb42dfb5f	addColumn tableName=RESOURCE_SERVER		\N	4.29.1	\N	\N	2927473611
8.0.0-adding-credential-columns	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-11-12 06:05:01.83563	72	EXECUTED	9:aa072ad090bbba210d8f18781b8cebf4	addColumn tableName=CREDENTIAL; addColumn tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	2927473611
8.0.0-updating-credential-data-not-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-11-12 06:05:01.876982	73	EXECUTED	9:1ae6be29bab7c2aa376f6983b932be37	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	2927473611
8.0.0-updating-credential-data-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-11-12 06:05:01.887964	74	MARK_RAN	9:14706f286953fc9a25286dbd8fb30d97	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	2927473611
8.0.0-credential-cleanup-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-11-12 06:05:01.977825	75	EXECUTED	9:2b9cc12779be32c5b40e2e67711a218b	dropDefaultValue columnName=COUNTER, tableName=CREDENTIAL; dropDefaultValue columnName=DIGITS, tableName=CREDENTIAL; dropDefaultValue columnName=PERIOD, tableName=CREDENTIAL; dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; dropColumn ...		\N	4.29.1	\N	\N	2927473611
8.0.0-resource-tag-support	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-11-12 06:05:02.120184	76	EXECUTED	9:91fa186ce7a5af127a2d7a91ee083cc5	addColumn tableName=MIGRATION_MODEL; createIndex indexName=IDX_UPDATE_TIME, tableName=MIGRATION_MODEL		\N	4.29.1	\N	\N	2927473611
9.0.0-always-display-client	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-11-12 06:05:02.131968	77	EXECUTED	9:6335e5c94e83a2639ccd68dd24e2e5ad	addColumn tableName=CLIENT		\N	4.29.1	\N	\N	2927473611
9.0.0-drop-constraints-for-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-11-12 06:05:02.135974	78	MARK_RAN	9:6bdb5658951e028bfe16fa0a8228b530	dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5PMT, tableName=RESOURCE_SERVER_PERM_TICKET; dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER_RESOURCE; dropPrimaryKey constraintName=CONSTRAINT_O...		\N	4.29.1	\N	\N	2927473611
9.0.0-increase-column-size-federated-fk	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-11-12 06:05:02.189273	79	EXECUTED	9:d5bc15a64117ccad481ce8792d4c608f	modifyDataType columnName=CLIENT_ID, tableName=FED_USER_CONSENT; modifyDataType columnName=CLIENT_REALM_CONSTRAINT, tableName=KEYCLOAK_ROLE; modifyDataType columnName=OWNER, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=CLIENT_ID, ta...		\N	4.29.1	\N	\N	2927473611
9.0.0-recreate-constraints-after-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-11-12 06:05:02.194354	80	MARK_RAN	9:077cba51999515f4d3e7ad5619ab592c	addNotNullConstraint columnName=CLIENT_ID, tableName=OFFLINE_CLIENT_SESSION; addNotNullConstraint columnName=OWNER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNullConstraint columnName=REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNull...		\N	4.29.1	\N	\N	2927473611
9.0.1-add-index-to-client.client_id	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-11-12 06:05:02.32363	81	EXECUTED	9:be969f08a163bf47c6b9e9ead8ac2afb	createIndex indexName=IDX_CLIENT_ID, tableName=CLIENT		\N	4.29.1	\N	\N	2927473611
9.0.1-KEYCLOAK-12579-drop-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-11-12 06:05:02.328133	82	MARK_RAN	9:6d3bb4408ba5a72f39bd8a0b301ec6e3	dropUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	2927473611
9.0.1-KEYCLOAK-12579-add-not-null-constraint	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-11-12 06:05:02.346044	83	EXECUTED	9:966bda61e46bebf3cc39518fbed52fa7	addNotNullConstraint columnName=PARENT_GROUP, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	2927473611
9.0.1-KEYCLOAK-12579-recreate-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-11-12 06:05:02.351608	84	MARK_RAN	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	2927473611
9.0.1-add-index-to-events	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-11-12 06:05:02.513096	85	EXECUTED	9:7d93d602352a30c0c317e6a609b56599	createIndex indexName=IDX_EVENT_TIME, tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	2927473611
map-remove-ri	keycloak	META-INF/jpa-changelog-11.0.0.xml	2025-11-12 06:05:02.529425	86	EXECUTED	9:71c5969e6cdd8d7b6f47cebc86d37627	dropForeignKeyConstraint baseTableName=REALM, constraintName=FK_TRAF444KK6QRKMS7N56AIWQ5Y; dropForeignKeyConstraint baseTableName=KEYCLOAK_ROLE, constraintName=FK_KJHO5LE2C0RAL09FL8CM9WFW9		\N	4.29.1	\N	\N	2927473611
map-remove-ri	keycloak	META-INF/jpa-changelog-12.0.0.xml	2025-11-12 06:05:02.55211	87	EXECUTED	9:a9ba7d47f065f041b7da856a81762021	dropForeignKeyConstraint baseTableName=REALM_DEFAULT_GROUPS, constraintName=FK_DEF_GROUPS_GROUP; dropForeignKeyConstraint baseTableName=REALM_DEFAULT_ROLES, constraintName=FK_H4WPD7W4HSOOLNI3H0SW7BTJE; dropForeignKeyConstraint baseTableName=CLIENT...		\N	4.29.1	\N	\N	2927473611
12.1.0-add-realm-localization-table	keycloak	META-INF/jpa-changelog-12.0.0.xml	2025-11-12 06:05:02.572037	88	EXECUTED	9:fffabce2bc01e1a8f5110d5278500065	createTable tableName=REALM_LOCALIZATIONS; addPrimaryKey tableName=REALM_LOCALIZATIONS		\N	4.29.1	\N	\N	2927473611
default-roles	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-12 06:05:02.58814	89	EXECUTED	9:fa8a5b5445e3857f4b010bafb5009957	addColumn tableName=REALM; customChange		\N	4.29.1	\N	\N	2927473611
default-roles-cleanup	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-12 06:05:02.606981	90	EXECUTED	9:67ac3241df9a8582d591c5ed87125f39	dropTable tableName=REALM_DEFAULT_ROLES; dropTable tableName=CLIENT_DEFAULT_ROLES		\N	4.29.1	\N	\N	2927473611
13.0.0-KEYCLOAK-16844	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-12 06:05:02.759887	91	EXECUTED	9:ad1194d66c937e3ffc82386c050ba089	createIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2927473611
map-remove-ri-13.0.0	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-12 06:05:02.788789	92	EXECUTED	9:d9be619d94af5a2f5d07b9f003543b91	dropForeignKeyConstraint baseTableName=DEFAULT_CLIENT_SCOPE, constraintName=FK_R_DEF_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SCOPE_CLIENT, constraintName=FK_C_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SC...		\N	4.29.1	\N	\N	2927473611
13.0.0-KEYCLOAK-17992-drop-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-12 06:05:02.794368	93	MARK_RAN	9:544d201116a0fcc5a5da0925fbbc3bde	dropPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CLSCOPE_CL, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CL_CLSCOPE, tableName=CLIENT_SCOPE_CLIENT		\N	4.29.1	\N	\N	2927473611
13.0.0-increase-column-size-federated	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-12 06:05:02.81686	94	EXECUTED	9:43c0c1055b6761b4b3e89de76d612ccf	modifyDataType columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; modifyDataType columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT		\N	4.29.1	\N	\N	2927473611
13.0.0-KEYCLOAK-17992-recreate-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-12 06:05:02.822471	95	MARK_RAN	9:8bd711fd0330f4fe980494ca43ab1139	addNotNullConstraint columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; addNotNullConstraint columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT; addPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; createIndex indexName=...		\N	4.29.1	\N	\N	2927473611
json-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-12 06:05:02.845902	96	EXECUTED	9:e07d2bc0970c348bb06fb63b1f82ddbf	addColumn tableName=REALM_ATTRIBUTE; update tableName=REALM_ATTRIBUTE; dropColumn columnName=VALUE, tableName=REALM_ATTRIBUTE; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=REALM_ATTRIBUTE		\N	4.29.1	\N	\N	2927473611
14.0.0-KEYCLOAK-11019	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-12 06:05:03.222438	97	EXECUTED	9:24fb8611e97f29989bea412aa38d12b7	createIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USER, tableName=OFFLINE_USER_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2927473611
14.0.0-KEYCLOAK-18286	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-12 06:05:03.226637	98	MARK_RAN	9:259f89014ce2506ee84740cbf7163aa7	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2927473611
14.0.0-KEYCLOAK-18286-revert	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-12 06:05:03.258634	99	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2927473611
14.0.0-KEYCLOAK-18286-supported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-12 06:05:03.418619	100	EXECUTED	9:60ca84a0f8c94ec8c3504a5a3bc88ee8	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2927473611
14.0.0-KEYCLOAK-18286-unsupported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-12 06:05:03.424515	101	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2927473611
KEYCLOAK-17267-add-index-to-user-attributes	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-12 06:05:03.59641	102	EXECUTED	9:0b305d8d1277f3a89a0a53a659ad274c	createIndex indexName=IDX_USER_ATTRIBUTE_NAME, tableName=USER_ATTRIBUTE		\N	4.29.1	\N	\N	2927473611
KEYCLOAK-18146-add-saml-art-binding-identifier	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-12 06:05:03.610333	103	EXECUTED	9:2c374ad2cdfe20e2905a84c8fac48460	customChange		\N	4.29.1	\N	\N	2927473611
15.0.0-KEYCLOAK-18467	keycloak	META-INF/jpa-changelog-15.0.0.xml	2025-11-12 06:05:03.634678	104	EXECUTED	9:47a760639ac597360a8219f5b768b4de	addColumn tableName=REALM_LOCALIZATIONS; update tableName=REALM_LOCALIZATIONS; dropColumn columnName=TEXTS, tableName=REALM_LOCALIZATIONS; renameColumn newColumnName=TEXTS, oldColumnName=TEXTS_NEW, tableName=REALM_LOCALIZATIONS; addNotNullConstrai...		\N	4.29.1	\N	\N	2927473611
17.0.0-9562	keycloak	META-INF/jpa-changelog-17.0.0.xml	2025-11-12 06:05:03.840763	105	EXECUTED	9:a6272f0576727dd8cad2522335f5d99e	createIndex indexName=IDX_USER_SERVICE_ACCOUNT, tableName=USER_ENTITY		\N	4.29.1	\N	\N	2927473611
18.0.0-10625-IDX_ADMIN_EVENT_TIME	keycloak	META-INF/jpa-changelog-18.0.0.xml	2025-11-12 06:05:04.024141	106	EXECUTED	9:015479dbd691d9cc8669282f4828c41d	createIndex indexName=IDX_ADMIN_EVENT_TIME, tableName=ADMIN_EVENT_ENTITY		\N	4.29.1	\N	\N	2927473611
18.0.15-30992-index-consent	keycloak	META-INF/jpa-changelog-18.0.15.xml	2025-11-12 06:05:04.228035	107	EXECUTED	9:80071ede7a05604b1f4906f3bf3b00f0	createIndex indexName=IDX_USCONSENT_SCOPE_ID, tableName=USER_CONSENT_CLIENT_SCOPE		\N	4.29.1	\N	\N	2927473611
19.0.0-10135	keycloak	META-INF/jpa-changelog-19.0.0.xml	2025-11-12 06:05:04.249034	108	EXECUTED	9:9518e495fdd22f78ad6425cc30630221	customChange		\N	4.29.1	\N	\N	2927473611
20.0.0-12964-supported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-11-12 06:05:04.458156	109	EXECUTED	9:e5f243877199fd96bcc842f27a1656ac	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.29.1	\N	\N	2927473611
20.0.0-12964-unsupported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-11-12 06:05:04.462721	110	MARK_RAN	9:1a6fcaa85e20bdeae0a9ce49b41946a5	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.29.1	\N	\N	2927473611
client-attributes-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-11-12 06:05:04.484493	111	EXECUTED	9:3f332e13e90739ed0c35b0b25b7822ca	addColumn tableName=CLIENT_ATTRIBUTES; update tableName=CLIENT_ATTRIBUTES; dropColumn columnName=VALUE, tableName=CLIENT_ATTRIBUTES; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2927473611
21.0.2-17277	keycloak	META-INF/jpa-changelog-21.0.2.xml	2025-11-12 06:05:04.498102	112	EXECUTED	9:7ee1f7a3fb8f5588f171fb9a6ab623c0	customChange		\N	4.29.1	\N	\N	2927473611
21.1.0-19404	keycloak	META-INF/jpa-changelog-21.1.0.xml	2025-11-12 06:05:04.547155	113	EXECUTED	9:3d7e830b52f33676b9d64f7f2b2ea634	modifyDataType columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=LOGIC, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=POLICY_ENFORCE_MODE, tableName=RESOURCE_SERVER		\N	4.29.1	\N	\N	2927473611
21.1.0-19404-2	keycloak	META-INF/jpa-changelog-21.1.0.xml	2025-11-12 06:05:04.559061	114	MARK_RAN	9:627d032e3ef2c06c0e1f73d2ae25c26c	addColumn tableName=RESOURCE_SERVER_POLICY; update tableName=RESOURCE_SERVER_POLICY; dropColumn columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; renameColumn newColumnName=DECISION_STRATEGY, oldColumnName=DECISION_STRATEGY_NEW, tabl...		\N	4.29.1	\N	\N	2927473611
22.0.0-17484-updated	keycloak	META-INF/jpa-changelog-22.0.0.xml	2025-11-12 06:05:04.578404	115	EXECUTED	9:90af0bfd30cafc17b9f4d6eccd92b8b3	customChange		\N	4.29.1	\N	\N	2927473611
22.0.5-24031	keycloak	META-INF/jpa-changelog-22.0.0.xml	2025-11-12 06:05:04.584386	116	MARK_RAN	9:a60d2d7b315ec2d3eba9e2f145f9df28	customChange		\N	4.29.1	\N	\N	2927473611
23.0.0-12062	keycloak	META-INF/jpa-changelog-23.0.0.xml	2025-11-12 06:05:04.609738	117	EXECUTED	9:2168fbe728fec46ae9baf15bf80927b8	addColumn tableName=COMPONENT_CONFIG; update tableName=COMPONENT_CONFIG; dropColumn columnName=VALUE, tableName=COMPONENT_CONFIG; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=COMPONENT_CONFIG		\N	4.29.1	\N	\N	2927473611
23.0.0-17258	keycloak	META-INF/jpa-changelog-23.0.0.xml	2025-11-12 06:05:04.622277	118	EXECUTED	9:36506d679a83bbfda85a27ea1864dca8	addColumn tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	2927473611
24.0.0-9758	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-11-12 06:05:05.205926	119	EXECUTED	9:502c557a5189f600f0f445a9b49ebbce	addColumn tableName=USER_ATTRIBUTE; addColumn tableName=FED_USER_ATTRIBUTE; createIndex indexName=USER_ATTR_LONG_VALUES, tableName=USER_ATTRIBUTE; createIndex indexName=FED_USER_ATTR_LONG_VALUES, tableName=FED_USER_ATTRIBUTE; createIndex indexName...		\N	4.29.1	\N	\N	2927473611
24.0.0-9758-2	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-11-12 06:05:05.21756	120	EXECUTED	9:bf0fdee10afdf597a987adbf291db7b2	customChange		\N	4.29.1	\N	\N	2927473611
24.0.0-26618-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-11-12 06:05:05.234972	121	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2927473611
24.0.0-26618-reindex	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-11-12 06:05:05.42655	122	EXECUTED	9:08707c0f0db1cef6b352db03a60edc7f	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2927473611
24.0.2-27228	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-11-12 06:05:05.438131	123	EXECUTED	9:eaee11f6b8aa25d2cc6a84fb86fc6238	customChange		\N	4.29.1	\N	\N	2927473611
24.0.2-27967-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-11-12 06:05:05.443753	124	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2927473611
24.0.2-27967-reindex	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-11-12 06:05:05.45015	125	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2927473611
25.0.0-28265-tables	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-12 06:05:05.466524	126	EXECUTED	9:deda2df035df23388af95bbd36c17cef	addColumn tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_CLIENT_SESSION		\N	4.29.1	\N	\N	2927473611
25.0.0-28265-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-12 06:05:05.588297	127	EXECUTED	9:3e96709818458ae49f3c679ae58d263a	createIndex indexName=IDX_OFFLINE_USS_BY_LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2927473611
25.0.0-28265-index-cleanup	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-12 06:05:05.607364	128	EXECUTED	9:8c0cfa341a0474385b324f5c4b2dfcc1	dropIndex indexName=IDX_OFFLINE_USS_CREATEDON, tableName=OFFLINE_USER_SESSION; dropIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION; dropIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION; dropIndex ...		\N	4.29.1	\N	\N	2927473611
25.0.0-28265-index-2-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-12 06:05:05.613597	129	MARK_RAN	9:b7ef76036d3126bb83c2423bf4d449d6	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2927473611
25.0.0-28265-index-2-not-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-12 06:05:05.739779	130	EXECUTED	9:23396cf51ab8bc1ae6f0cac7f9f6fcf7	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2927473611
25.0.0-org	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-12 06:05:05.765875	131	EXECUTED	9:5c859965c2c9b9c72136c360649af157	createTable tableName=ORG; addUniqueConstraint constraintName=UK_ORG_NAME, tableName=ORG; addUniqueConstraint constraintName=UK_ORG_GROUP, tableName=ORG; createTable tableName=ORG_DOMAIN		\N	4.29.1	\N	\N	2927473611
unique-consentuser	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-12 06:05:05.786559	132	EXECUTED	9:5857626a2ea8767e9a6c66bf3a2cb32f	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.29.1	\N	\N	2927473611
unique-consentuser-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-12 06:05:05.791078	133	MARK_RAN	9:b79478aad5adaa1bc428e31563f55e8e	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.29.1	\N	\N	2927473611
25.0.0-28861-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-12 06:05:06.027998	134	EXECUTED	9:b9acb58ac958d9ada0fe12a5d4794ab1	createIndex indexName=IDX_PERM_TICKET_REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; createIndex indexName=IDX_PERM_TICKET_OWNER, tableName=RESOURCE_SERVER_PERM_TICKET		\N	4.29.1	\N	\N	2927473611
26.0.0-org-alias	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-12 06:05:06.045875	135	EXECUTED	9:6ef7d63e4412b3c2d66ed179159886a4	addColumn tableName=ORG; update tableName=ORG; addNotNullConstraint columnName=ALIAS, tableName=ORG; addUniqueConstraint constraintName=UK_ORG_ALIAS, tableName=ORG		\N	4.29.1	\N	\N	2927473611
26.0.0-org-group	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-12 06:05:06.069503	136	EXECUTED	9:da8e8087d80ef2ace4f89d8c5b9ca223	addColumn tableName=KEYCLOAK_GROUP; update tableName=KEYCLOAK_GROUP; addNotNullConstraint columnName=TYPE, tableName=KEYCLOAK_GROUP; customChange		\N	4.29.1	\N	\N	2927473611
26.0.0-org-indexes	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-12 06:05:06.202371	137	EXECUTED	9:79b05dcd610a8c7f25ec05135eec0857	createIndex indexName=IDX_ORG_DOMAIN_ORG_ID, tableName=ORG_DOMAIN		\N	4.29.1	\N	\N	2927473611
26.0.0-org-group-membership	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-12 06:05:06.221016	138	EXECUTED	9:a6ace2ce583a421d89b01ba2a28dc2d4	addColumn tableName=USER_GROUP_MEMBERSHIP; update tableName=USER_GROUP_MEMBERSHIP; addNotNullConstraint columnName=MEMBERSHIP_TYPE, tableName=USER_GROUP_MEMBERSHIP		\N	4.29.1	\N	\N	2927473611
31296-persist-revoked-access-tokens	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-12 06:05:06.236426	139	EXECUTED	9:64ef94489d42a358e8304b0e245f0ed4	createTable tableName=REVOKED_TOKEN; addPrimaryKey constraintName=CONSTRAINT_RT, tableName=REVOKED_TOKEN		\N	4.29.1	\N	\N	2927473611
31725-index-persist-revoked-access-tokens	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-12 06:05:06.362456	140	EXECUTED	9:b994246ec2bf7c94da881e1d28782c7b	createIndex indexName=IDX_REV_TOKEN_ON_EXPIRE, tableName=REVOKED_TOKEN		\N	4.29.1	\N	\N	2927473611
26.0.0-idps-for-login	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-12 06:05:06.648876	141	EXECUTED	9:51f5fffadf986983d4bd59582c6c1604	addColumn tableName=IDENTITY_PROVIDER; createIndex indexName=IDX_IDP_REALM_ORG, tableName=IDENTITY_PROVIDER; createIndex indexName=IDX_IDP_FOR_LOGIN, tableName=IDENTITY_PROVIDER; customChange		\N	4.29.1	\N	\N	2927473611
26.0.0-32583-drop-redundant-index-on-client-session	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-12 06:05:06.661324	142	EXECUTED	9:24972d83bf27317a055d234187bb4af9	dropIndex indexName=IDX_US_SESS_ID_ON_CL_SESS, tableName=OFFLINE_CLIENT_SESSION		\N	4.29.1	\N	\N	2927473611
26.0.0.32582-remove-tables-user-session-user-session-note-and-client-session	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-12 06:05:06.713616	143	EXECUTED	9:febdc0f47f2ed241c59e60f58c3ceea5	dropTable tableName=CLIENT_SESSION_ROLE; dropTable tableName=CLIENT_SESSION_NOTE; dropTable tableName=CLIENT_SESSION_PROT_MAPPER; dropTable tableName=CLIENT_SESSION_AUTH_STATUS; dropTable tableName=CLIENT_USER_SESSION_NOTE; dropTable tableName=CLI...		\N	4.29.1	\N	\N	2927473611
\.


--
-- Data for Name: databasechangeloglock; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.databasechangeloglock (id, locked, lockgranted, lockedby) FROM stdin;
1	f	\N	\N
1000	f	\N	\N
\.


--
-- Data for Name: default_client_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.default_client_scope (realm_id, scope_id, default_scope) FROM stdin;
863411db-f685-4e36-9210-ef7892793272	f292de33-23dd-40b8-ba83-3736dfed481a	f
863411db-f685-4e36-9210-ef7892793272	0115c2fb-3ed9-4ad5-ab8a-0f6c544a400b	t
863411db-f685-4e36-9210-ef7892793272	6df126a7-fe19-4d68-8a99-b5b37083d13d	t
863411db-f685-4e36-9210-ef7892793272	7a41db59-024f-4ffd-9a52-271e84abeda2	t
863411db-f685-4e36-9210-ef7892793272	2252ac5e-72e1-4321-b6be-fc6d80a48274	t
863411db-f685-4e36-9210-ef7892793272	93282cfc-5add-4d56-9c6a-ce686d5d93c1	f
863411db-f685-4e36-9210-ef7892793272	b6608264-845a-4394-909e-e68ffc1a8574	f
863411db-f685-4e36-9210-ef7892793272	e4f4336a-8834-41a9-abf3-ff18fcfb1459	t
863411db-f685-4e36-9210-ef7892793272	71809b5b-b9ba-49cd-a626-b5e0214eda76	t
863411db-f685-4e36-9210-ef7892793272	d68cde9c-a31f-462b-8e8d-06df080f3ff9	f
863411db-f685-4e36-9210-ef7892793272	9e0578c9-ff91-4204-ae95-34584b120356	t
863411db-f685-4e36-9210-ef7892793272	0b83a41f-87a8-46c9-b799-6b8583b201c0	t
863411db-f685-4e36-9210-ef7892793272	033ff9f5-2f67-4e3d-bb3b-aa13acc62d06	f
83b6664d-539e-4bed-a376-685d50e40b98	2714f410-8ea1-4442-b110-09d56464c942	t
83b6664d-539e-4bed-a376-685d50e40b98	26d881ea-f02a-4da4-a770-096c22dcf2b6	t
83b6664d-539e-4bed-a376-685d50e40b98	424886ac-3b5a-446c-a0f8-2d2cd6474e44	t
83b6664d-539e-4bed-a376-685d50e40b98	1e15434a-7f6c-415b-bb60-f84f0edb53d7	t
83b6664d-539e-4bed-a376-685d50e40b98	1343b2a3-ff3c-4588-a0e3-7372006e3cf9	t
83b6664d-539e-4bed-a376-685d50e40b98	ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee	t
83b6664d-539e-4bed-a376-685d50e40b98	b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc	t
83b6664d-539e-4bed-a376-685d50e40b98	ac70839b-f142-47ab-93cf-f21d06d1f546	t
83b6664d-539e-4bed-a376-685d50e40b98	b7f3f02b-6bcb-4ed0-8d23-9fcf556e2243	f
83b6664d-539e-4bed-a376-685d50e40b98	38509398-011e-46eb-9444-8719ee1ccbe9	f
83b6664d-539e-4bed-a376-685d50e40b98	9749151c-c4bd-4925-b7f6-2e141385a4eb	f
83b6664d-539e-4bed-a376-685d50e40b98	2753e1d9-77a7-4059-8465-fa23a6b418c4	f
83b6664d-539e-4bed-a376-685d50e40b98	d21bbe72-4cc0-4637-aaab-c6beafce7e57	f
\.


--
-- Data for Name: event_entity; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.event_entity (id, client_id, details_json, error, ip_address, realm_id, session_id, event_time, type, user_id, details_json_long_value) FROM stdin;
\.


--
-- Data for Name: fed_user_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_attribute (id, name, user_id, realm_id, storage_provider_id, value, long_value_hash, long_value_hash_lower_case, long_value) FROM stdin;
\.


--
-- Data for Name: fed_user_consent; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_consent (id, client_id, user_id, realm_id, storage_provider_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: fed_user_consent_cl_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_consent_cl_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: fed_user_credential; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_credential (id, salt, type, created_date, user_id, realm_id, storage_provider_id, user_label, secret_data, credential_data, priority) FROM stdin;
\.


--
-- Data for Name: fed_user_group_membership; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_group_membership (group_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_required_action; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_required_action (required_action, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: fed_user_role_mapping; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.fed_user_role_mapping (role_id, user_id, realm_id, storage_provider_id) FROM stdin;
\.


--
-- Data for Name: federated_identity; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.federated_identity (identity_provider, realm_id, federated_user_id, federated_username, token, user_id) FROM stdin;
\.


--
-- Data for Name: federated_user; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.federated_user (id, storage_provider_id, realm_id) FROM stdin;
\.


--
-- Data for Name: flyway_schema_history; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.flyway_schema_history (installed_rank, version, description, type, script, checksum, installed_by, installed_on, execution_time, success) FROM stdin;
1	1	init	SQL	V1__init.sql	433547695	admin	2025-11-11 19:07:25.480296	31	t
\.


--
-- Data for Name: group_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.group_attribute (id, name, value, group_id) FROM stdin;
\.


--
-- Data for Name: group_role_mapping; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.group_role_mapping (role_id, group_id) FROM stdin;
\.


--
-- Data for Name: identity_provider; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.identity_provider (internal_id, enabled, provider_alias, provider_id, store_token, authenticate_by_default, realm_id, add_token_role, trust_email, first_broker_login_flow_id, post_broker_login_flow_id, provider_display_name, link_only, organization_id, hide_on_login) FROM stdin;
\.


--
-- Data for Name: identity_provider_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.identity_provider_config (identity_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: identity_provider_mapper; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.identity_provider_mapper (id, name, idp_alias, idp_mapper_name, realm_id) FROM stdin;
\.


--
-- Data for Name: idp_mapper_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.idp_mapper_config (idp_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: keycloak_group; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.keycloak_group (id, name, parent_group, realm_id, type) FROM stdin;
\.


--
-- Data for Name: keycloak_role; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.keycloak_role (id, client_realm_constraint, client_role, description, name, realm_id, client, realm) FROM stdin;
6730a033-7401-46cd-9d13-4e5fb261a249	863411db-f685-4e36-9210-ef7892793272	f	${role_default-roles}	default-roles-master	863411db-f685-4e36-9210-ef7892793272	\N	\N
28edd15c-1957-49f0-b471-1af6421f49b0	863411db-f685-4e36-9210-ef7892793272	f	${role_create-realm}	create-realm	863411db-f685-4e36-9210-ef7892793272	\N	\N
512e309b-99ec-473d-95d7-4c3c18f23636	863411db-f685-4e36-9210-ef7892793272	f	${role_admin}	admin	863411db-f685-4e36-9210-ef7892793272	\N	\N
8a75a6d0-0aec-4301-a08c-8882f6b8eb98	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_create-client}	create-client	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
50225f63-70ff-460b-9c38-4bf1f041e39c	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_view-realm}	view-realm	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
07bc92a8-b6e2-43f0-bd30-0fd17a9a578b	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_view-users}	view-users	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
92262e66-fbf3-45a1-ade4-b2850ff102d9	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_view-clients}	view-clients	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
8baa2eb9-04b7-4342-945c-928c36a75f18	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_view-events}	view-events	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
87515c24-0cbb-4483-8e1a-b1c84db83ac6	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_view-identity-providers}	view-identity-providers	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
d380b550-d483-4139-aeb4-87f7b3f45c89	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_view-authorization}	view-authorization	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
3a3e1955-2934-4269-8a64-47a696f1651b	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_manage-realm}	manage-realm	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
8edb52ac-2930-4d00-b9d7-7dc40e3bbb9c	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_manage-users}	manage-users	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
a36ce94f-949c-4f80-b6cf-49b64eaa9fbf	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_manage-clients}	manage-clients	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
692cf4ab-2e6b-42a4-92ad-dbd26a45fa71	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_manage-events}	manage-events	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
cfe21fc6-9182-442d-8dfe-9a30a4324e65	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_manage-identity-providers}	manage-identity-providers	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
f28faaf1-b93b-434a-8043-045fb054f874	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_manage-authorization}	manage-authorization	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
a738a8bf-3175-4a06-866d-886722909883	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_query-users}	query-users	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
db24f95b-491a-4c6b-9e6a-750394ebb4c5	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_query-clients}	query-clients	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
f3f796d1-1f1c-48bb-9e04-57f8f487ccb7	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_query-realms}	query-realms	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
3287eb45-0a80-4f8b-8518-f2899c86df76	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_query-groups}	query-groups	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
8900421b-ad4e-4d17-9c08-1359b04dfeb9	5a657f88-6b01-40ff-a1f5-bc6f95dee042	t	${role_view-profile}	view-profile	863411db-f685-4e36-9210-ef7892793272	5a657f88-6b01-40ff-a1f5-bc6f95dee042	\N
534b41bb-f775-49c9-8c4d-078018cda382	5a657f88-6b01-40ff-a1f5-bc6f95dee042	t	${role_manage-account}	manage-account	863411db-f685-4e36-9210-ef7892793272	5a657f88-6b01-40ff-a1f5-bc6f95dee042	\N
fc242d1d-9724-4aa5-8033-f9ee93ef191e	5a657f88-6b01-40ff-a1f5-bc6f95dee042	t	${role_manage-account-links}	manage-account-links	863411db-f685-4e36-9210-ef7892793272	5a657f88-6b01-40ff-a1f5-bc6f95dee042	\N
eddbfebc-08c9-46f0-8620-6804144e954f	5a657f88-6b01-40ff-a1f5-bc6f95dee042	t	${role_view-applications}	view-applications	863411db-f685-4e36-9210-ef7892793272	5a657f88-6b01-40ff-a1f5-bc6f95dee042	\N
93acfef9-ccc9-440d-8382-a99ad41a46e0	5a657f88-6b01-40ff-a1f5-bc6f95dee042	t	${role_view-consent}	view-consent	863411db-f685-4e36-9210-ef7892793272	5a657f88-6b01-40ff-a1f5-bc6f95dee042	\N
823154f0-f107-4c94-b2e1-44cb7db6435b	5a657f88-6b01-40ff-a1f5-bc6f95dee042	t	${role_manage-consent}	manage-consent	863411db-f685-4e36-9210-ef7892793272	5a657f88-6b01-40ff-a1f5-bc6f95dee042	\N
a79c57d6-dcb5-4563-b56b-7fcd4d5f798e	5a657f88-6b01-40ff-a1f5-bc6f95dee042	t	${role_view-groups}	view-groups	863411db-f685-4e36-9210-ef7892793272	5a657f88-6b01-40ff-a1f5-bc6f95dee042	\N
5e7c5809-0554-4c4f-99d2-febe6fc1ee18	5a657f88-6b01-40ff-a1f5-bc6f95dee042	t	${role_delete-account}	delete-account	863411db-f685-4e36-9210-ef7892793272	5a657f88-6b01-40ff-a1f5-bc6f95dee042	\N
a8012999-c00c-4403-a395-343d17c27f59	8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	t	${role_read-token}	read-token	863411db-f685-4e36-9210-ef7892793272	8bb3564e-cbe1-420f-acd4-a5f4f98a2bcb	\N
1cd41a1e-deb1-4b37-878b-30ac32368d79	4744ff01-2e1f-4b2d-8d58-85f94704e41a	t	${role_impersonation}	impersonation	863411db-f685-4e36-9210-ef7892793272	4744ff01-2e1f-4b2d-8d58-85f94704e41a	\N
6748c93f-f293-4519-9bf4-aefc57a46f9b	863411db-f685-4e36-9210-ef7892793272	f	${role_offline-access}	offline_access	863411db-f685-4e36-9210-ef7892793272	\N	\N
98f04a2f-68f8-4dc2-a822-000a142cd5ce	863411db-f685-4e36-9210-ef7892793272	f	${role_uma_authorization}	uma_authorization	863411db-f685-4e36-9210-ef7892793272	\N	\N
929392ee-0474-4ed0-a64e-9b0132025c91	83b6664d-539e-4bed-a376-685d50e40b98	f	${role_default-roles}	default-roles-loci-realm	83b6664d-539e-4bed-a376-685d50e40b98	\N	\N
80a58106-5cee-416f-96e3-e6380971c944	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_create-client}	create-client	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
2b658436-6f19-4145-8413-bbe62044026f	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_view-realm}	view-realm	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
f9ff45b0-491c-405c-b973-073ac6e1a07d	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_view-users}	view-users	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
15944995-de23-4fd2-a36f-a619304879de	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_view-clients}	view-clients	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
1bb716f9-ee6c-4328-ae15-3c5ab58a8ae7	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_view-events}	view-events	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
2be2aa57-4393-4e12-8c35-e35a7fe2f36e	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_view-identity-providers}	view-identity-providers	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
8d7af946-0251-49f2-925e-2febec520f32	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_view-authorization}	view-authorization	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
408bb6a0-91b7-4118-9060-0bc9d3b1768e	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_manage-realm}	manage-realm	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
0b170173-48a0-4aaa-b3fc-9aea87afc674	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_manage-users}	manage-users	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
71bf09ac-fed0-44f8-8027-c0a8e0d1ac7a	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_manage-clients}	manage-clients	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
acf862d3-1ff4-439f-a971-51c5c5fecf76	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_manage-events}	manage-events	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
4f07dee9-b0ea-4810-baa8-332067dbf651	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_manage-identity-providers}	manage-identity-providers	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
76bb7e8d-4adf-4733-8d93-4ad201e13ed6	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_manage-authorization}	manage-authorization	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
51800fb2-3ec9-44bc-96a0-0f0de248625a	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_query-users}	query-users	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
b80b8620-d619-4f50-bd91-0d7c56a9a9b6	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_query-clients}	query-clients	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
72e27472-d746-4296-9509-979c5cf44717	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_query-realms}	query-realms	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
8c96b0a8-2e00-4577-b34c-f4dc2df75aed	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_query-groups}	query-groups	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
eb9be962-f203-49c6-9075-ce80642bf363	83b6664d-539e-4bed-a376-685d50e40b98	f	${role_uma_authorization}	uma_authorization	83b6664d-539e-4bed-a376-685d50e40b98	\N	\N
5df2f1e3-7508-4fbb-889f-c8b70f84ec3b	83b6664d-539e-4bed-a376-685d50e40b98	f		user	83b6664d-539e-4bed-a376-685d50e40b98	\N	\N
6d7b0d9a-0ac1-481e-aebb-8acd9d5c3e39	83b6664d-539e-4bed-a376-685d50e40b98	f	${role_offline-access}	offline_access	83b6664d-539e-4bed-a376-685d50e40b98	\N	\N
5160c2df-3ab3-465b-b113-a61e60faf3bf	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_view-events}	view-events	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
7d52ab89-86c3-4b57-bab0-02bbe460212e	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_query-groups}	query-groups	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
995efc57-65a8-4262-9f91-7b9d83580c90	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_query-realms}	query-realms	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
b1939686-0827-4787-a141-9e7dd5fc1b82	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_manage-clients}	manage-clients	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
8f94747b-5e4b-4d79-bf0c-cd4923506035	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_view-realm}	view-realm	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
a7120f7b-a46b-4f6a-9e97-0eea0f64174a	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_impersonation}	impersonation	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
808637e3-7023-4d84-a56a-4f51c7ec23c4	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_manage-users}	manage-users	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
221f3b69-a59e-4a34-a95a-71f10cef4e0a	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_view-clients}	view-clients	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
ae3061fa-e4e7-4bf4-a3af-ca8d12cdb57d	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_view-identity-providers}	view-identity-providers	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
4009714e-eefe-46d4-9b37-639a6edfa501	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_view-users}	view-users	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
fdf28825-1691-44e5-88c3-2c8c9a0aa41e	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_manage-identity-providers}	manage-identity-providers	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
4e989ddd-8be8-4533-bfde-cad630193577	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_view-authorization}	view-authorization	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
ffb45a5a-3d46-434b-910a-2894fae5d4d9	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_manage-realm}	manage-realm	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
dd7c767b-2b46-4089-a64a-97fd1456b645	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_manage-authorization}	manage-authorization	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
38244948-f337-4b8b-9c15-fdc672bc80f9	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_query-users}	query-users	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
fd6368e9-9e26-469e-8d79-707f3c20354a	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_realm-admin}	realm-admin	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
abfd79ad-9ca7-4f0b-b54b-78a9c1e9f28e	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_create-client}	create-client	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
6bf59d64-e73b-44b6-93cc-75d66c644214	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_manage-events}	manage-events	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
0cf5be44-ba63-47c2-b752-662ef0a5885b	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	t	${role_query-clients}	query-clients	83b6664d-539e-4bed-a376-685d50e40b98	1b01f7e0-ad45-4cd7-8adb-119a1d9316bb	\N
ab8a75b3-f452-4310-9885-40c2c6c74704	81fb0324-8f89-4cb7-bc31-8af536e10b7e	t	${role_read-token}	read-token	83b6664d-539e-4bed-a376-685d50e40b98	81fb0324-8f89-4cb7-bc31-8af536e10b7e	\N
50118dde-4418-4aad-aceb-8c7b45903b0b	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	t	${role_delete-account}	delete-account	83b6664d-539e-4bed-a376-685d50e40b98	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	\N
cd9c2212-1347-472e-8e9f-20be2bfc2ec2	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	t	${role_view-groups}	view-groups	83b6664d-539e-4bed-a376-685d50e40b98	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	\N
43ca2f26-9aa7-47d7-8773-c808b2435278	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	t	${role_view-applications}	view-applications	83b6664d-539e-4bed-a376-685d50e40b98	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	\N
2e0714ed-6fbf-47eb-ae88-59ce44d6ed3b	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	t	${role_manage-consent}	manage-consent	83b6664d-539e-4bed-a376-685d50e40b98	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	\N
4862f2b4-4e36-42cd-a524-6dddbb43e551	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	t	${role_manage-account-links}	manage-account-links	83b6664d-539e-4bed-a376-685d50e40b98	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	\N
f24cb736-a373-4bd6-a34a-ab059b81f98f	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	t	${role_view-profile}	view-profile	83b6664d-539e-4bed-a376-685d50e40b98	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	\N
710ed846-759e-45bc-afa4-bb5ef183d08c	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	t	${role_manage-account}	manage-account	83b6664d-539e-4bed-a376-685d50e40b98	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	\N
b39c73a7-84b1-40ff-9fd9-6f314c9ede7a	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	t	${role_view-consent}	view-consent	83b6664d-539e-4bed-a376-685d50e40b98	cb847cf7-5d97-441b-b7dd-a6f1a47689a2	\N
15e7eb74-0454-4c45-a368-ab2c13e114ee	35a8ca56-c6f1-47f3-a285-17454ba2afa1	t	${role_impersonation}	impersonation	863411db-f685-4e36-9210-ef7892793272	35a8ca56-c6f1-47f3-a285-17454ba2afa1	\N
\.


--
-- Data for Name: message; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.message (id, created_date, last_modified_date) FROM stdin;
\.


--
-- Data for Name: migration_model; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.migration_model (id, version, update_time) FROM stdin;
i167h	26.0.0	1762927509
\.


--
-- Data for Name: offline_client_session; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.offline_client_session (user_session_id, client_id, offline_flag, "timestamp", data, client_storage_provider, external_client_id, version) FROM stdin;
649cf4d8-0358-49f0-b37f-899ccc5e8430	1946f9ea-72fe-4697-bf83-3dec92a8f8ee	0	1764322285	{"authMethod":"openid-connect","redirectUri":"http://localhost:4200/user/me","notes":{"clientId":"1946f9ea-72fe-4697-bf83-3dec92a8f8ee","iss":"http://localhost:9090/realms/loci-realm","startedAt":"1764321439","response_type":"code","level-of-authentication":"-1","code_challenge_method":"S256","nonce":"89f139e8-8dc2-4504-a735-838e8d1916dc","response_mode":"fragment","login_hint":"testuser1","scope":"openid","userSessionStartedAt":"1764321439","redirect_uri":"http://localhost:4200/user/me","state":"1f9de152-b6b0-4f85-8cfc-c372255058c0","code_challenge":"V4JXxJbx495JiEQ8mEneq5GrPeyO9_Cr9rjC9ZBRyso","SSO_AUTH":"true"}}	local	local	5
a117e4a9-7846-4eaf-bd20-81e886d2ca6e	1946f9ea-72fe-4697-bf83-3dec92a8f8ee	0	1764323850	{"authMethod":"openid-connect","redirectUri":"http://localhost:4200/user/me","notes":{"clientId":"1946f9ea-72fe-4697-bf83-3dec92a8f8ee","iss":"http://localhost:9090/realms/loci-realm","startedAt":"1764321178","response_type":"code","level-of-authentication":"-1","code_challenge_method":"S256","nonce":"560f0490-ee0b-4746-9c40-37a6f1dfa568","response_mode":"fragment","scope":"openid","userSessionStartedAt":"1764321178","redirect_uri":"http://localhost:4200/user/me","state":"8d5e380b-c54b-4910-906a-643cedb6366a","code_challenge":"dxSp6tbsocMdQX_OtuYeokVV_JFr6A75TEmGza4Y7Rw","SSO_AUTH":"true"}}	local	local	23
\.


--
-- Data for Name: offline_user_session; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.offline_user_session (user_session_id, user_id, realm_id, created_on, offline_flag, data, last_session_refresh, broker_session_id, version) FROM stdin;
649cf4d8-0358-49f0-b37f-899ccc5e8430	cd2e474a-f099-4cce-ac9f-4d047fd00a01	83b6664d-539e-4bed-a376-685d50e40b98	1764321439	0	{"ipAddress":"172.24.0.1","authMethod":"openid-connect","rememberMe":false,"started":0,"notes":{"KC_DEVICE_NOTE":"eyJpcEFkZHJlc3MiOiIxNzIuMjQuMC4xIiwib3MiOiJMaW51eCIsIm9zVmVyc2lvbiI6IlVua25vd24iLCJicm93c2VyIjoiRmlyZWZveC8xNDMuMCIsImRldmljZSI6Ik90aGVyIiwibGFzdEFjY2VzcyI6MCwibW9iaWxlIjpmYWxzZX0=","AUTH_TIME":"1764321439","authenticators-completed":"{\\"cf263c3f-5ff8-4ce9-9737-aefaa4696a1d\\":1764322284}"},"state":"LOGGED_IN"}	1764322285	\N	5
a117e4a9-7846-4eaf-bd20-81e886d2ca6e	a03e7f08-f0c7-4c40-97d6-e3bff7d00057	83b6664d-539e-4bed-a376-685d50e40b98	1764321178	0	{"ipAddress":"172.24.0.1","authMethod":"openid-connect","rememberMe":false,"started":0,"notes":{"KC_DEVICE_NOTE":"eyJpcEFkZHJlc3MiOiIxNzIuMjQuMC4xIiwib3MiOiJMaW51eCIsIm9zVmVyc2lvbiI6IlVua25vd24iLCJicm93c2VyIjoiRmlyZWZveC8xNDMuMCIsImRldmljZSI6Ik90aGVyIiwibGFzdEFjY2VzcyI6MCwibW9iaWxlIjpmYWxzZX0=","AUTH_TIME":"1764321178","authenticators-completed":"{\\"561ebf2d-66e6-44d2-9124-283efa53c75e\\":1764321178,\\"cf263c3f-5ff8-4ce9-9737-aefaa4696a1d\\":1764323847}"},"state":"LOGGED_IN"}	1764323850	\N	23
\.


--
-- Data for Name: org; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.org (id, enabled, realm_id, group_id, name, description, alias, redirect_url) FROM stdin;
\.


--
-- Data for Name: org_domain; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.org_domain (id, name, verified, org_id) FROM stdin;
\.


--
-- Data for Name: policy_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.policy_config (policy_id, name, value) FROM stdin;
\.


--
-- Data for Name: protocol_mapper; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.protocol_mapper (id, name, protocol, protocol_mapper_name, client_id, client_scope_id) FROM stdin;
430371df-c5d0-4820-a20c-6689ad36920d	audience resolve	openid-connect	oidc-audience-resolve-mapper	a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	\N
c6b2c3bd-fc82-4c04-8cb3-f2788038f788	locale	openid-connect	oidc-usermodel-attribute-mapper	94105b65-67ea-498a-a4e5-cdd952818067	\N
768db056-e3a6-4182-a9f8-3783ec5f28c7	role list	saml	saml-role-list-mapper	\N	0115c2fb-3ed9-4ad5-ab8a-0f6c544a400b
e07e1b7c-f395-4bbe-aac9-2c514deda8a7	organization	saml	saml-organization-membership-mapper	\N	6df126a7-fe19-4d68-8a99-b5b37083d13d
ccacb9d2-a1eb-4236-969f-863dbb238ac2	full name	openid-connect	oidc-full-name-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
9ca08013-56ae-46fd-8e3c-602c2aa49a1b	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
d9bc35e4-d6c8-4c03-a22e-c2063139ad9b	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
93f26168-8341-441a-a11e-b68ee2f71312	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
56eb9197-a668-4f31-9217-810eba72b448	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
c426f41d-1f2a-4b94-8ff2-d7babcd23f5f	username	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
661b9358-17a7-4acf-98b0-5eefc854bdb0	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
7eea7a84-b504-4c12-97e9-76a019c30e73	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
7a6c719e-937f-40fa-bf3f-b76d4583866c	website	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
92004fd2-ad15-44fd-acf4-3039833b9389	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
7eb56623-2330-4222-8a02-2f7fbf98482d	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
4ac17b89-803e-432b-a982-bf6bc069e2f9	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
b6683e2d-d57a-4fa1-a362-5661f7126b68	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
0465df0b-d057-4687-94be-342d4d0378b8	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	7a41db59-024f-4ffd-9a52-271e84abeda2
3c356a9d-f9f4-465b-a665-9544a27bc8b4	email	openid-connect	oidc-usermodel-attribute-mapper	\N	2252ac5e-72e1-4321-b6be-fc6d80a48274
e0139d7a-e997-42a0-b91d-072dea60d96f	email verified	openid-connect	oidc-usermodel-property-mapper	\N	2252ac5e-72e1-4321-b6be-fc6d80a48274
3fa44f43-6d22-4872-9301-48d512590a7d	address	openid-connect	oidc-address-mapper	\N	93282cfc-5add-4d56-9c6a-ce686d5d93c1
f774047c-4c13-4f0b-bc93-4b7dad2728d8	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	b6608264-845a-4394-909e-e68ffc1a8574
062b3bf9-7951-4a4c-a307-a9fb1854b578	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	b6608264-845a-4394-909e-e68ffc1a8574
392bf75e-c19d-4504-875b-8020788a4783	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	e4f4336a-8834-41a9-abf3-ff18fcfb1459
a0540508-a748-4cf3-ae60-019576cef0b0	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	e4f4336a-8834-41a9-abf3-ff18fcfb1459
c75dc73d-dc9a-4587-8b58-666a25e2ce98	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	e4f4336a-8834-41a9-abf3-ff18fcfb1459
3cfdef16-cad1-429d-bb07-ed7da25660aa	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	71809b5b-b9ba-49cd-a626-b5e0214eda76
026358bb-3f26-45ee-927c-66c0dab7d184	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	d68cde9c-a31f-462b-8e8d-06df080f3ff9
8e8a94f4-12c8-4cb1-a926-e55857d47cc3	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	d68cde9c-a31f-462b-8e8d-06df080f3ff9
d35e5c2c-53d0-4dea-9ba7-0f5ee2cb4e20	acr loa level	openid-connect	oidc-acr-mapper	\N	9e0578c9-ff91-4204-ae95-34584b120356
b379eb04-3d87-4149-9a44-a5fdac28def3	auth_time	openid-connect	oidc-usersessionmodel-note-mapper	\N	0b83a41f-87a8-46c9-b799-6b8583b201c0
95ca9873-97bf-443d-9c88-09bd578d4a6f	sub	openid-connect	oidc-sub-mapper	\N	0b83a41f-87a8-46c9-b799-6b8583b201c0
371b474c-dbed-49eb-8863-4e22780bd615	organization	openid-connect	oidc-organization-membership-mapper	\N	033ff9f5-2f67-4e3d-bb3b-aa13acc62d06
a5f42024-b607-4292-b1d6-a9d4191bb4e8	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	2753e1d9-77a7-4059-8465-fa23a6b418c4
e136e954-04d6-4224-bdb0-336990f7b794	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	2753e1d9-77a7-4059-8465-fa23a6b418c4
2caf2e3b-868c-48c5-a684-532529b920ed	auth_time	openid-connect	oidc-usersessionmodel-note-mapper	\N	ac70839b-f142-47ab-93cf-f21d06d1f546
f01993e4-4569-476a-927b-c5586ca133e4	sub	openid-connect	oidc-sub-mapper	\N	ac70839b-f142-47ab-93cf-f21d06d1f546
ae9358a3-2b41-4860-87e3-d24dd15d5d71	email	openid-connect	oidc-usermodel-attribute-mapper	\N	1e15434a-7f6c-415b-bb60-f84f0edb53d7
8813051b-d5db-4a74-82e3-440d85bd6d1f	email verified	openid-connect	oidc-usermodel-property-mapper	\N	1e15434a-7f6c-415b-bb60-f84f0edb53d7
8e4d50f6-3d11-45c7-bd5e-99bcc95ea419	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	1343b2a3-ff3c-4588-a0e3-7372006e3cf9
6330cb1c-d510-4162-aa76-14fee2a149b9	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	1343b2a3-ff3c-4588-a0e3-7372006e3cf9
f57565ee-061d-45dd-ae51-83f3a62959ab	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	1343b2a3-ff3c-4588-a0e3-7372006e3cf9
0a7b1fe6-0cd1-4df4-8651-5157a202e9e8	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	9749151c-c4bd-4925-b7f6-2e141385a4eb
435c261a-73d4-47e1-a692-0ebd814c2127	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	9749151c-c4bd-4925-b7f6-2e141385a4eb
99eee510-c44b-4d69-90d8-66de184021af	organization	openid-connect	oidc-organization-membership-mapper	\N	d21bbe72-4cc0-4637-aaab-c6beafce7e57
acd8cbcc-451d-435a-8c40-dc45ea11930f	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	ea76ec75-71a8-42a6-bdfd-dfcca0fc7aee
153fa251-cd25-48f4-9bcf-c6909d808765	organization	saml	saml-organization-membership-mapper	\N	26d881ea-f02a-4da4-a770-096c22dcf2b6
889dd164-8ceb-4f81-ba6e-c407504a095b	address	openid-connect	oidc-address-mapper	\N	38509398-011e-46eb-9444-8719ee1ccbe9
24baf309-3ddf-4d72-bbb9-6cf1b5f23312	acr loa level	openid-connect	oidc-acr-mapper	\N	b1cd614d-0091-4d4e-a2fa-28b3d76ae2bc
58e945c2-28cf-4f53-8add-aed444f92a27	role list	saml	saml-role-list-mapper	\N	2714f410-8ea1-4442-b110-09d56464c942
80592ea1-cf85-4beb-9021-ed452c7bd293	full name	openid-connect	oidc-full-name-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
258a5ec2-f843-4c10-906d-a6503d3980fb	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
7e7a6a27-bcf1-4cdc-8668-149916b2169b	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
b1d9a82a-d1c2-4200-b351-e53021527707	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
3d6eeab9-d603-4896-9ed4-69b41a007b4e	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
46ece9ad-d834-417f-b776-c57aeab5cd07	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
5dde0842-f155-4f2b-9e61-b3967ae0a857	username	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
92baff2a-9f19-4614-a5a8-5dd0c4e24c39	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
a51f9041-9d8d-498a-b62b-a5c4e24141b6	website	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
deb37ced-49bd-4237-b3ab-65fc416ad14f	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
afc27d87-0a4a-4d6c-b8cb-59b80d0aca7d	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
b605f71d-5400-4282-a700-b288d5d7eb14	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
391d2d4b-263f-4a6b-9a3d-593c2aee3556	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
b8ca6d7d-1b82-4ae5-b158-3b61a3874813	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	424886ac-3b5a-446c-a0f8-2d2cd6474e44
b122ad6e-56bc-4dd3-b59a-22bd944e8cb0	audience resolve	openid-connect	oidc-audience-resolve-mapper	1512bb33-3ef4-4dad-af1f-47081a2a75dc	\N
fede70dd-d1c5-4408-a082-a5f0364195d6	locale	openid-connect	oidc-usermodel-attribute-mapper	ba4de397-daf8-404b-ad79-249e4d09a713	\N
\.


--
-- Data for Name: protocol_mapper_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.protocol_mapper_config (protocol_mapper_id, value, name) FROM stdin;
c6b2c3bd-fc82-4c04-8cb3-f2788038f788	true	introspection.token.claim
c6b2c3bd-fc82-4c04-8cb3-f2788038f788	true	userinfo.token.claim
c6b2c3bd-fc82-4c04-8cb3-f2788038f788	locale	user.attribute
c6b2c3bd-fc82-4c04-8cb3-f2788038f788	true	id.token.claim
c6b2c3bd-fc82-4c04-8cb3-f2788038f788	true	access.token.claim
c6b2c3bd-fc82-4c04-8cb3-f2788038f788	locale	claim.name
c6b2c3bd-fc82-4c04-8cb3-f2788038f788	String	jsonType.label
768db056-e3a6-4182-a9f8-3783ec5f28c7	false	single
768db056-e3a6-4182-a9f8-3783ec5f28c7	Basic	attribute.nameformat
768db056-e3a6-4182-a9f8-3783ec5f28c7	Role	attribute.name
0465df0b-d057-4687-94be-342d4d0378b8	true	introspection.token.claim
0465df0b-d057-4687-94be-342d4d0378b8	true	userinfo.token.claim
0465df0b-d057-4687-94be-342d4d0378b8	updatedAt	user.attribute
0465df0b-d057-4687-94be-342d4d0378b8	true	id.token.claim
0465df0b-d057-4687-94be-342d4d0378b8	true	access.token.claim
0465df0b-d057-4687-94be-342d4d0378b8	updated_at	claim.name
0465df0b-d057-4687-94be-342d4d0378b8	long	jsonType.label
4ac17b89-803e-432b-a982-bf6bc069e2f9	true	introspection.token.claim
4ac17b89-803e-432b-a982-bf6bc069e2f9	true	userinfo.token.claim
4ac17b89-803e-432b-a982-bf6bc069e2f9	zoneinfo	user.attribute
4ac17b89-803e-432b-a982-bf6bc069e2f9	true	id.token.claim
4ac17b89-803e-432b-a982-bf6bc069e2f9	true	access.token.claim
4ac17b89-803e-432b-a982-bf6bc069e2f9	zoneinfo	claim.name
4ac17b89-803e-432b-a982-bf6bc069e2f9	String	jsonType.label
56eb9197-a668-4f31-9217-810eba72b448	true	introspection.token.claim
56eb9197-a668-4f31-9217-810eba72b448	true	userinfo.token.claim
56eb9197-a668-4f31-9217-810eba72b448	nickname	user.attribute
56eb9197-a668-4f31-9217-810eba72b448	true	id.token.claim
56eb9197-a668-4f31-9217-810eba72b448	true	access.token.claim
56eb9197-a668-4f31-9217-810eba72b448	nickname	claim.name
56eb9197-a668-4f31-9217-810eba72b448	String	jsonType.label
661b9358-17a7-4acf-98b0-5eefc854bdb0	true	introspection.token.claim
661b9358-17a7-4acf-98b0-5eefc854bdb0	true	userinfo.token.claim
661b9358-17a7-4acf-98b0-5eefc854bdb0	profile	user.attribute
661b9358-17a7-4acf-98b0-5eefc854bdb0	true	id.token.claim
661b9358-17a7-4acf-98b0-5eefc854bdb0	true	access.token.claim
661b9358-17a7-4acf-98b0-5eefc854bdb0	profile	claim.name
661b9358-17a7-4acf-98b0-5eefc854bdb0	String	jsonType.label
7a6c719e-937f-40fa-bf3f-b76d4583866c	true	introspection.token.claim
7a6c719e-937f-40fa-bf3f-b76d4583866c	true	userinfo.token.claim
7a6c719e-937f-40fa-bf3f-b76d4583866c	website	user.attribute
7a6c719e-937f-40fa-bf3f-b76d4583866c	true	id.token.claim
7a6c719e-937f-40fa-bf3f-b76d4583866c	true	access.token.claim
7a6c719e-937f-40fa-bf3f-b76d4583866c	website	claim.name
7a6c719e-937f-40fa-bf3f-b76d4583866c	String	jsonType.label
7eb56623-2330-4222-8a02-2f7fbf98482d	true	introspection.token.claim
7eb56623-2330-4222-8a02-2f7fbf98482d	true	userinfo.token.claim
7eb56623-2330-4222-8a02-2f7fbf98482d	birthdate	user.attribute
7eb56623-2330-4222-8a02-2f7fbf98482d	true	id.token.claim
7eb56623-2330-4222-8a02-2f7fbf98482d	true	access.token.claim
7eb56623-2330-4222-8a02-2f7fbf98482d	birthdate	claim.name
7eb56623-2330-4222-8a02-2f7fbf98482d	String	jsonType.label
7eea7a84-b504-4c12-97e9-76a019c30e73	true	introspection.token.claim
7eea7a84-b504-4c12-97e9-76a019c30e73	true	userinfo.token.claim
7eea7a84-b504-4c12-97e9-76a019c30e73	picture	user.attribute
7eea7a84-b504-4c12-97e9-76a019c30e73	true	id.token.claim
7eea7a84-b504-4c12-97e9-76a019c30e73	true	access.token.claim
7eea7a84-b504-4c12-97e9-76a019c30e73	picture	claim.name
7eea7a84-b504-4c12-97e9-76a019c30e73	String	jsonType.label
92004fd2-ad15-44fd-acf4-3039833b9389	true	introspection.token.claim
92004fd2-ad15-44fd-acf4-3039833b9389	true	userinfo.token.claim
92004fd2-ad15-44fd-acf4-3039833b9389	gender	user.attribute
92004fd2-ad15-44fd-acf4-3039833b9389	true	id.token.claim
92004fd2-ad15-44fd-acf4-3039833b9389	true	access.token.claim
92004fd2-ad15-44fd-acf4-3039833b9389	gender	claim.name
92004fd2-ad15-44fd-acf4-3039833b9389	String	jsonType.label
93f26168-8341-441a-a11e-b68ee2f71312	true	introspection.token.claim
93f26168-8341-441a-a11e-b68ee2f71312	true	userinfo.token.claim
93f26168-8341-441a-a11e-b68ee2f71312	middleName	user.attribute
93f26168-8341-441a-a11e-b68ee2f71312	true	id.token.claim
93f26168-8341-441a-a11e-b68ee2f71312	true	access.token.claim
93f26168-8341-441a-a11e-b68ee2f71312	middle_name	claim.name
93f26168-8341-441a-a11e-b68ee2f71312	String	jsonType.label
9ca08013-56ae-46fd-8e3c-602c2aa49a1b	true	introspection.token.claim
9ca08013-56ae-46fd-8e3c-602c2aa49a1b	true	userinfo.token.claim
9ca08013-56ae-46fd-8e3c-602c2aa49a1b	lastName	user.attribute
9ca08013-56ae-46fd-8e3c-602c2aa49a1b	true	id.token.claim
9ca08013-56ae-46fd-8e3c-602c2aa49a1b	true	access.token.claim
9ca08013-56ae-46fd-8e3c-602c2aa49a1b	family_name	claim.name
9ca08013-56ae-46fd-8e3c-602c2aa49a1b	String	jsonType.label
b6683e2d-d57a-4fa1-a362-5661f7126b68	true	introspection.token.claim
b6683e2d-d57a-4fa1-a362-5661f7126b68	true	userinfo.token.claim
b6683e2d-d57a-4fa1-a362-5661f7126b68	locale	user.attribute
b6683e2d-d57a-4fa1-a362-5661f7126b68	true	id.token.claim
b6683e2d-d57a-4fa1-a362-5661f7126b68	true	access.token.claim
b6683e2d-d57a-4fa1-a362-5661f7126b68	locale	claim.name
b6683e2d-d57a-4fa1-a362-5661f7126b68	String	jsonType.label
c426f41d-1f2a-4b94-8ff2-d7babcd23f5f	true	introspection.token.claim
c426f41d-1f2a-4b94-8ff2-d7babcd23f5f	true	userinfo.token.claim
c426f41d-1f2a-4b94-8ff2-d7babcd23f5f	username	user.attribute
c426f41d-1f2a-4b94-8ff2-d7babcd23f5f	true	id.token.claim
c426f41d-1f2a-4b94-8ff2-d7babcd23f5f	true	access.token.claim
c426f41d-1f2a-4b94-8ff2-d7babcd23f5f	preferred_username	claim.name
c426f41d-1f2a-4b94-8ff2-d7babcd23f5f	String	jsonType.label
ccacb9d2-a1eb-4236-969f-863dbb238ac2	true	introspection.token.claim
ccacb9d2-a1eb-4236-969f-863dbb238ac2	true	userinfo.token.claim
ccacb9d2-a1eb-4236-969f-863dbb238ac2	true	id.token.claim
ccacb9d2-a1eb-4236-969f-863dbb238ac2	true	access.token.claim
d9bc35e4-d6c8-4c03-a22e-c2063139ad9b	true	introspection.token.claim
d9bc35e4-d6c8-4c03-a22e-c2063139ad9b	true	userinfo.token.claim
d9bc35e4-d6c8-4c03-a22e-c2063139ad9b	firstName	user.attribute
d9bc35e4-d6c8-4c03-a22e-c2063139ad9b	true	id.token.claim
d9bc35e4-d6c8-4c03-a22e-c2063139ad9b	true	access.token.claim
d9bc35e4-d6c8-4c03-a22e-c2063139ad9b	given_name	claim.name
d9bc35e4-d6c8-4c03-a22e-c2063139ad9b	String	jsonType.label
3c356a9d-f9f4-465b-a665-9544a27bc8b4	true	introspection.token.claim
3c356a9d-f9f4-465b-a665-9544a27bc8b4	true	userinfo.token.claim
3c356a9d-f9f4-465b-a665-9544a27bc8b4	email	user.attribute
3c356a9d-f9f4-465b-a665-9544a27bc8b4	true	id.token.claim
3c356a9d-f9f4-465b-a665-9544a27bc8b4	true	access.token.claim
3c356a9d-f9f4-465b-a665-9544a27bc8b4	email	claim.name
3c356a9d-f9f4-465b-a665-9544a27bc8b4	String	jsonType.label
e0139d7a-e997-42a0-b91d-072dea60d96f	true	introspection.token.claim
e0139d7a-e997-42a0-b91d-072dea60d96f	true	userinfo.token.claim
e0139d7a-e997-42a0-b91d-072dea60d96f	emailVerified	user.attribute
e0139d7a-e997-42a0-b91d-072dea60d96f	true	id.token.claim
e0139d7a-e997-42a0-b91d-072dea60d96f	true	access.token.claim
e0139d7a-e997-42a0-b91d-072dea60d96f	email_verified	claim.name
e0139d7a-e997-42a0-b91d-072dea60d96f	boolean	jsonType.label
3fa44f43-6d22-4872-9301-48d512590a7d	formatted	user.attribute.formatted
3fa44f43-6d22-4872-9301-48d512590a7d	country	user.attribute.country
3fa44f43-6d22-4872-9301-48d512590a7d	true	introspection.token.claim
3fa44f43-6d22-4872-9301-48d512590a7d	postal_code	user.attribute.postal_code
3fa44f43-6d22-4872-9301-48d512590a7d	true	userinfo.token.claim
3fa44f43-6d22-4872-9301-48d512590a7d	street	user.attribute.street
3fa44f43-6d22-4872-9301-48d512590a7d	true	id.token.claim
3fa44f43-6d22-4872-9301-48d512590a7d	region	user.attribute.region
3fa44f43-6d22-4872-9301-48d512590a7d	true	access.token.claim
3fa44f43-6d22-4872-9301-48d512590a7d	locality	user.attribute.locality
062b3bf9-7951-4a4c-a307-a9fb1854b578	true	introspection.token.claim
062b3bf9-7951-4a4c-a307-a9fb1854b578	true	userinfo.token.claim
062b3bf9-7951-4a4c-a307-a9fb1854b578	phoneNumberVerified	user.attribute
062b3bf9-7951-4a4c-a307-a9fb1854b578	true	id.token.claim
062b3bf9-7951-4a4c-a307-a9fb1854b578	true	access.token.claim
062b3bf9-7951-4a4c-a307-a9fb1854b578	phone_number_verified	claim.name
062b3bf9-7951-4a4c-a307-a9fb1854b578	boolean	jsonType.label
f774047c-4c13-4f0b-bc93-4b7dad2728d8	true	introspection.token.claim
f774047c-4c13-4f0b-bc93-4b7dad2728d8	true	userinfo.token.claim
f774047c-4c13-4f0b-bc93-4b7dad2728d8	phoneNumber	user.attribute
f774047c-4c13-4f0b-bc93-4b7dad2728d8	true	id.token.claim
f774047c-4c13-4f0b-bc93-4b7dad2728d8	true	access.token.claim
f774047c-4c13-4f0b-bc93-4b7dad2728d8	phone_number	claim.name
f774047c-4c13-4f0b-bc93-4b7dad2728d8	String	jsonType.label
392bf75e-c19d-4504-875b-8020788a4783	true	introspection.token.claim
392bf75e-c19d-4504-875b-8020788a4783	true	multivalued
392bf75e-c19d-4504-875b-8020788a4783	foo	user.attribute
392bf75e-c19d-4504-875b-8020788a4783	true	access.token.claim
392bf75e-c19d-4504-875b-8020788a4783	realm_access.roles	claim.name
392bf75e-c19d-4504-875b-8020788a4783	String	jsonType.label
a0540508-a748-4cf3-ae60-019576cef0b0	true	introspection.token.claim
a0540508-a748-4cf3-ae60-019576cef0b0	true	multivalued
a0540508-a748-4cf3-ae60-019576cef0b0	foo	user.attribute
a0540508-a748-4cf3-ae60-019576cef0b0	true	access.token.claim
a0540508-a748-4cf3-ae60-019576cef0b0	resource_access.${client_id}.roles	claim.name
a0540508-a748-4cf3-ae60-019576cef0b0	String	jsonType.label
c75dc73d-dc9a-4587-8b58-666a25e2ce98	true	introspection.token.claim
c75dc73d-dc9a-4587-8b58-666a25e2ce98	true	access.token.claim
3cfdef16-cad1-429d-bb07-ed7da25660aa	true	introspection.token.claim
3cfdef16-cad1-429d-bb07-ed7da25660aa	true	access.token.claim
026358bb-3f26-45ee-927c-66c0dab7d184	true	introspection.token.claim
026358bb-3f26-45ee-927c-66c0dab7d184	true	userinfo.token.claim
026358bb-3f26-45ee-927c-66c0dab7d184	username	user.attribute
026358bb-3f26-45ee-927c-66c0dab7d184	true	id.token.claim
026358bb-3f26-45ee-927c-66c0dab7d184	true	access.token.claim
026358bb-3f26-45ee-927c-66c0dab7d184	upn	claim.name
026358bb-3f26-45ee-927c-66c0dab7d184	String	jsonType.label
8e8a94f4-12c8-4cb1-a926-e55857d47cc3	true	introspection.token.claim
8e8a94f4-12c8-4cb1-a926-e55857d47cc3	true	multivalued
8e8a94f4-12c8-4cb1-a926-e55857d47cc3	foo	user.attribute
8e8a94f4-12c8-4cb1-a926-e55857d47cc3	true	id.token.claim
8e8a94f4-12c8-4cb1-a926-e55857d47cc3	true	access.token.claim
8e8a94f4-12c8-4cb1-a926-e55857d47cc3	groups	claim.name
8e8a94f4-12c8-4cb1-a926-e55857d47cc3	String	jsonType.label
d35e5c2c-53d0-4dea-9ba7-0f5ee2cb4e20	true	introspection.token.claim
d35e5c2c-53d0-4dea-9ba7-0f5ee2cb4e20	true	id.token.claim
d35e5c2c-53d0-4dea-9ba7-0f5ee2cb4e20	true	access.token.claim
95ca9873-97bf-443d-9c88-09bd578d4a6f	true	introspection.token.claim
95ca9873-97bf-443d-9c88-09bd578d4a6f	true	access.token.claim
b379eb04-3d87-4149-9a44-a5fdac28def3	AUTH_TIME	user.session.note
b379eb04-3d87-4149-9a44-a5fdac28def3	true	introspection.token.claim
b379eb04-3d87-4149-9a44-a5fdac28def3	true	id.token.claim
b379eb04-3d87-4149-9a44-a5fdac28def3	true	access.token.claim
b379eb04-3d87-4149-9a44-a5fdac28def3	auth_time	claim.name
b379eb04-3d87-4149-9a44-a5fdac28def3	long	jsonType.label
371b474c-dbed-49eb-8863-4e22780bd615	true	introspection.token.claim
371b474c-dbed-49eb-8863-4e22780bd615	true	multivalued
371b474c-dbed-49eb-8863-4e22780bd615	true	id.token.claim
371b474c-dbed-49eb-8863-4e22780bd615	true	access.token.claim
371b474c-dbed-49eb-8863-4e22780bd615	organization	claim.name
371b474c-dbed-49eb-8863-4e22780bd615	String	jsonType.label
a5f42024-b607-4292-b1d6-a9d4191bb4e8	true	introspection.token.claim
a5f42024-b607-4292-b1d6-a9d4191bb4e8	true	multivalued
a5f42024-b607-4292-b1d6-a9d4191bb4e8	true	userinfo.token.claim
a5f42024-b607-4292-b1d6-a9d4191bb4e8	foo	user.attribute
a5f42024-b607-4292-b1d6-a9d4191bb4e8	true	id.token.claim
a5f42024-b607-4292-b1d6-a9d4191bb4e8	true	access.token.claim
a5f42024-b607-4292-b1d6-a9d4191bb4e8	groups	claim.name
a5f42024-b607-4292-b1d6-a9d4191bb4e8	String	jsonType.label
e136e954-04d6-4224-bdb0-336990f7b794	true	introspection.token.claim
e136e954-04d6-4224-bdb0-336990f7b794	true	userinfo.token.claim
e136e954-04d6-4224-bdb0-336990f7b794	username	user.attribute
e136e954-04d6-4224-bdb0-336990f7b794	true	id.token.claim
e136e954-04d6-4224-bdb0-336990f7b794	true	access.token.claim
e136e954-04d6-4224-bdb0-336990f7b794	upn	claim.name
e136e954-04d6-4224-bdb0-336990f7b794	String	jsonType.label
2caf2e3b-868c-48c5-a684-532529b920ed	AUTH_TIME	user.session.note
2caf2e3b-868c-48c5-a684-532529b920ed	true	introspection.token.claim
2caf2e3b-868c-48c5-a684-532529b920ed	true	userinfo.token.claim
2caf2e3b-868c-48c5-a684-532529b920ed	true	id.token.claim
2caf2e3b-868c-48c5-a684-532529b920ed	true	access.token.claim
2caf2e3b-868c-48c5-a684-532529b920ed	auth_time	claim.name
2caf2e3b-868c-48c5-a684-532529b920ed	long	jsonType.label
f01993e4-4569-476a-927b-c5586ca133e4	true	introspection.token.claim
f01993e4-4569-476a-927b-c5586ca133e4	true	access.token.claim
8813051b-d5db-4a74-82e3-440d85bd6d1f	true	introspection.token.claim
8813051b-d5db-4a74-82e3-440d85bd6d1f	true	userinfo.token.claim
8813051b-d5db-4a74-82e3-440d85bd6d1f	emailVerified	user.attribute
8813051b-d5db-4a74-82e3-440d85bd6d1f	true	id.token.claim
8813051b-d5db-4a74-82e3-440d85bd6d1f	true	access.token.claim
8813051b-d5db-4a74-82e3-440d85bd6d1f	email_verified	claim.name
8813051b-d5db-4a74-82e3-440d85bd6d1f	boolean	jsonType.label
ae9358a3-2b41-4860-87e3-d24dd15d5d71	true	introspection.token.claim
ae9358a3-2b41-4860-87e3-d24dd15d5d71	true	userinfo.token.claim
ae9358a3-2b41-4860-87e3-d24dd15d5d71	email	user.attribute
ae9358a3-2b41-4860-87e3-d24dd15d5d71	true	id.token.claim
ae9358a3-2b41-4860-87e3-d24dd15d5d71	true	access.token.claim
ae9358a3-2b41-4860-87e3-d24dd15d5d71	email	claim.name
ae9358a3-2b41-4860-87e3-d24dd15d5d71	String	jsonType.label
6330cb1c-d510-4162-aa76-14fee2a149b9	foo	user.attribute
6330cb1c-d510-4162-aa76-14fee2a149b9	true	introspection.token.claim
6330cb1c-d510-4162-aa76-14fee2a149b9	true	access.token.claim
6330cb1c-d510-4162-aa76-14fee2a149b9	resource_access.${client_id}.roles	claim.name
6330cb1c-d510-4162-aa76-14fee2a149b9	String	jsonType.label
6330cb1c-d510-4162-aa76-14fee2a149b9	true	multivalued
8e4d50f6-3d11-45c7-bd5e-99bcc95ea419	foo	user.attribute
8e4d50f6-3d11-45c7-bd5e-99bcc95ea419	true	introspection.token.claim
8e4d50f6-3d11-45c7-bd5e-99bcc95ea419	true	access.token.claim
8e4d50f6-3d11-45c7-bd5e-99bcc95ea419	realm_access.roles	claim.name
8e4d50f6-3d11-45c7-bd5e-99bcc95ea419	String	jsonType.label
8e4d50f6-3d11-45c7-bd5e-99bcc95ea419	true	multivalued
f57565ee-061d-45dd-ae51-83f3a62959ab	true	introspection.token.claim
f57565ee-061d-45dd-ae51-83f3a62959ab	true	access.token.claim
0a7b1fe6-0cd1-4df4-8651-5157a202e9e8	true	introspection.token.claim
0a7b1fe6-0cd1-4df4-8651-5157a202e9e8	true	userinfo.token.claim
0a7b1fe6-0cd1-4df4-8651-5157a202e9e8	phoneNumberVerified	user.attribute
0a7b1fe6-0cd1-4df4-8651-5157a202e9e8	true	id.token.claim
0a7b1fe6-0cd1-4df4-8651-5157a202e9e8	true	access.token.claim
0a7b1fe6-0cd1-4df4-8651-5157a202e9e8	phone_number_verified	claim.name
0a7b1fe6-0cd1-4df4-8651-5157a202e9e8	boolean	jsonType.label
435c261a-73d4-47e1-a692-0ebd814c2127	true	introspection.token.claim
435c261a-73d4-47e1-a692-0ebd814c2127	true	userinfo.token.claim
435c261a-73d4-47e1-a692-0ebd814c2127	phoneNumber	user.attribute
435c261a-73d4-47e1-a692-0ebd814c2127	true	id.token.claim
435c261a-73d4-47e1-a692-0ebd814c2127	true	access.token.claim
435c261a-73d4-47e1-a692-0ebd814c2127	phone_number	claim.name
435c261a-73d4-47e1-a692-0ebd814c2127	String	jsonType.label
99eee510-c44b-4d69-90d8-66de184021af	true	introspection.token.claim
99eee510-c44b-4d69-90d8-66de184021af	true	multivalued
99eee510-c44b-4d69-90d8-66de184021af	true	userinfo.token.claim
99eee510-c44b-4d69-90d8-66de184021af	true	id.token.claim
99eee510-c44b-4d69-90d8-66de184021af	true	access.token.claim
99eee510-c44b-4d69-90d8-66de184021af	organization	claim.name
99eee510-c44b-4d69-90d8-66de184021af	String	jsonType.label
acd8cbcc-451d-435a-8c40-dc45ea11930f	true	introspection.token.claim
acd8cbcc-451d-435a-8c40-dc45ea11930f	true	access.token.claim
889dd164-8ceb-4f81-ba6e-c407504a095b	formatted	user.attribute.formatted
889dd164-8ceb-4f81-ba6e-c407504a095b	country	user.attribute.country
889dd164-8ceb-4f81-ba6e-c407504a095b	true	introspection.token.claim
889dd164-8ceb-4f81-ba6e-c407504a095b	postal_code	user.attribute.postal_code
889dd164-8ceb-4f81-ba6e-c407504a095b	true	userinfo.token.claim
889dd164-8ceb-4f81-ba6e-c407504a095b	street	user.attribute.street
889dd164-8ceb-4f81-ba6e-c407504a095b	true	id.token.claim
889dd164-8ceb-4f81-ba6e-c407504a095b	region	user.attribute.region
889dd164-8ceb-4f81-ba6e-c407504a095b	true	access.token.claim
889dd164-8ceb-4f81-ba6e-c407504a095b	locality	user.attribute.locality
24baf309-3ddf-4d72-bbb9-6cf1b5f23312	true	id.token.claim
24baf309-3ddf-4d72-bbb9-6cf1b5f23312	true	introspection.token.claim
24baf309-3ddf-4d72-bbb9-6cf1b5f23312	true	access.token.claim
24baf309-3ddf-4d72-bbb9-6cf1b5f23312	true	userinfo.token.claim
58e945c2-28cf-4f53-8add-aed444f92a27	false	single
58e945c2-28cf-4f53-8add-aed444f92a27	Basic	attribute.nameformat
58e945c2-28cf-4f53-8add-aed444f92a27	Role	attribute.name
258a5ec2-f843-4c10-906d-a6503d3980fb	true	introspection.token.claim
258a5ec2-f843-4c10-906d-a6503d3980fb	true	userinfo.token.claim
258a5ec2-f843-4c10-906d-a6503d3980fb	profile	user.attribute
258a5ec2-f843-4c10-906d-a6503d3980fb	true	id.token.claim
258a5ec2-f843-4c10-906d-a6503d3980fb	true	access.token.claim
258a5ec2-f843-4c10-906d-a6503d3980fb	profile	claim.name
258a5ec2-f843-4c10-906d-a6503d3980fb	String	jsonType.label
391d2d4b-263f-4a6b-9a3d-593c2aee3556	true	introspection.token.claim
391d2d4b-263f-4a6b-9a3d-593c2aee3556	true	userinfo.token.claim
391d2d4b-263f-4a6b-9a3d-593c2aee3556	gender	user.attribute
391d2d4b-263f-4a6b-9a3d-593c2aee3556	true	id.token.claim
391d2d4b-263f-4a6b-9a3d-593c2aee3556	true	access.token.claim
391d2d4b-263f-4a6b-9a3d-593c2aee3556	gender	claim.name
391d2d4b-263f-4a6b-9a3d-593c2aee3556	String	jsonType.label
3d6eeab9-d603-4896-9ed4-69b41a007b4e	true	introspection.token.claim
3d6eeab9-d603-4896-9ed4-69b41a007b4e	true	userinfo.token.claim
3d6eeab9-d603-4896-9ed4-69b41a007b4e	birthdate	user.attribute
3d6eeab9-d603-4896-9ed4-69b41a007b4e	true	id.token.claim
3d6eeab9-d603-4896-9ed4-69b41a007b4e	true	access.token.claim
3d6eeab9-d603-4896-9ed4-69b41a007b4e	birthdate	claim.name
3d6eeab9-d603-4896-9ed4-69b41a007b4e	String	jsonType.label
46ece9ad-d834-417f-b776-c57aeab5cd07	true	introspection.token.claim
46ece9ad-d834-417f-b776-c57aeab5cd07	true	userinfo.token.claim
46ece9ad-d834-417f-b776-c57aeab5cd07	lastName	user.attribute
46ece9ad-d834-417f-b776-c57aeab5cd07	true	id.token.claim
46ece9ad-d834-417f-b776-c57aeab5cd07	true	access.token.claim
46ece9ad-d834-417f-b776-c57aeab5cd07	family_name	claim.name
46ece9ad-d834-417f-b776-c57aeab5cd07	String	jsonType.label
5dde0842-f155-4f2b-9e61-b3967ae0a857	true	introspection.token.claim
5dde0842-f155-4f2b-9e61-b3967ae0a857	true	userinfo.token.claim
5dde0842-f155-4f2b-9e61-b3967ae0a857	username	user.attribute
5dde0842-f155-4f2b-9e61-b3967ae0a857	true	id.token.claim
5dde0842-f155-4f2b-9e61-b3967ae0a857	true	access.token.claim
5dde0842-f155-4f2b-9e61-b3967ae0a857	preferred_username	claim.name
5dde0842-f155-4f2b-9e61-b3967ae0a857	String	jsonType.label
7e7a6a27-bcf1-4cdc-8668-149916b2169b	true	introspection.token.claim
7e7a6a27-bcf1-4cdc-8668-149916b2169b	true	userinfo.token.claim
7e7a6a27-bcf1-4cdc-8668-149916b2169b	middleName	user.attribute
7e7a6a27-bcf1-4cdc-8668-149916b2169b	true	id.token.claim
7e7a6a27-bcf1-4cdc-8668-149916b2169b	true	access.token.claim
7e7a6a27-bcf1-4cdc-8668-149916b2169b	middle_name	claim.name
7e7a6a27-bcf1-4cdc-8668-149916b2169b	String	jsonType.label
80592ea1-cf85-4beb-9021-ed452c7bd293	true	id.token.claim
80592ea1-cf85-4beb-9021-ed452c7bd293	true	introspection.token.claim
80592ea1-cf85-4beb-9021-ed452c7bd293	true	access.token.claim
80592ea1-cf85-4beb-9021-ed452c7bd293	true	userinfo.token.claim
92baff2a-9f19-4614-a5a8-5dd0c4e24c39	true	introspection.token.claim
92baff2a-9f19-4614-a5a8-5dd0c4e24c39	true	userinfo.token.claim
92baff2a-9f19-4614-a5a8-5dd0c4e24c39	picture	user.attribute
92baff2a-9f19-4614-a5a8-5dd0c4e24c39	true	id.token.claim
92baff2a-9f19-4614-a5a8-5dd0c4e24c39	true	access.token.claim
92baff2a-9f19-4614-a5a8-5dd0c4e24c39	picture	claim.name
92baff2a-9f19-4614-a5a8-5dd0c4e24c39	String	jsonType.label
a51f9041-9d8d-498a-b62b-a5c4e24141b6	true	introspection.token.claim
a51f9041-9d8d-498a-b62b-a5c4e24141b6	true	userinfo.token.claim
a51f9041-9d8d-498a-b62b-a5c4e24141b6	website	user.attribute
a51f9041-9d8d-498a-b62b-a5c4e24141b6	true	id.token.claim
a51f9041-9d8d-498a-b62b-a5c4e24141b6	true	access.token.claim
a51f9041-9d8d-498a-b62b-a5c4e24141b6	website	claim.name
a51f9041-9d8d-498a-b62b-a5c4e24141b6	String	jsonType.label
afc27d87-0a4a-4d6c-b8cb-59b80d0aca7d	true	introspection.token.claim
afc27d87-0a4a-4d6c-b8cb-59b80d0aca7d	true	userinfo.token.claim
afc27d87-0a4a-4d6c-b8cb-59b80d0aca7d	zoneinfo	user.attribute
afc27d87-0a4a-4d6c-b8cb-59b80d0aca7d	true	id.token.claim
afc27d87-0a4a-4d6c-b8cb-59b80d0aca7d	true	access.token.claim
afc27d87-0a4a-4d6c-b8cb-59b80d0aca7d	zoneinfo	claim.name
afc27d87-0a4a-4d6c-b8cb-59b80d0aca7d	String	jsonType.label
b1d9a82a-d1c2-4200-b351-e53021527707	true	introspection.token.claim
b1d9a82a-d1c2-4200-b351-e53021527707	true	userinfo.token.claim
b1d9a82a-d1c2-4200-b351-e53021527707	firstName	user.attribute
b1d9a82a-d1c2-4200-b351-e53021527707	true	id.token.claim
b1d9a82a-d1c2-4200-b351-e53021527707	true	access.token.claim
b1d9a82a-d1c2-4200-b351-e53021527707	given_name	claim.name
b1d9a82a-d1c2-4200-b351-e53021527707	String	jsonType.label
b605f71d-5400-4282-a700-b288d5d7eb14	true	introspection.token.claim
b605f71d-5400-4282-a700-b288d5d7eb14	true	userinfo.token.claim
b605f71d-5400-4282-a700-b288d5d7eb14	updatedAt	user.attribute
b605f71d-5400-4282-a700-b288d5d7eb14	true	id.token.claim
b605f71d-5400-4282-a700-b288d5d7eb14	true	access.token.claim
b605f71d-5400-4282-a700-b288d5d7eb14	updated_at	claim.name
b605f71d-5400-4282-a700-b288d5d7eb14	long	jsonType.label
b8ca6d7d-1b82-4ae5-b158-3b61a3874813	true	introspection.token.claim
b8ca6d7d-1b82-4ae5-b158-3b61a3874813	true	userinfo.token.claim
b8ca6d7d-1b82-4ae5-b158-3b61a3874813	locale	user.attribute
b8ca6d7d-1b82-4ae5-b158-3b61a3874813	true	id.token.claim
b8ca6d7d-1b82-4ae5-b158-3b61a3874813	true	access.token.claim
b8ca6d7d-1b82-4ae5-b158-3b61a3874813	locale	claim.name
b8ca6d7d-1b82-4ae5-b158-3b61a3874813	String	jsonType.label
deb37ced-49bd-4237-b3ab-65fc416ad14f	true	introspection.token.claim
deb37ced-49bd-4237-b3ab-65fc416ad14f	true	userinfo.token.claim
deb37ced-49bd-4237-b3ab-65fc416ad14f	nickname	user.attribute
deb37ced-49bd-4237-b3ab-65fc416ad14f	true	id.token.claim
deb37ced-49bd-4237-b3ab-65fc416ad14f	true	access.token.claim
deb37ced-49bd-4237-b3ab-65fc416ad14f	nickname	claim.name
deb37ced-49bd-4237-b3ab-65fc416ad14f	String	jsonType.label
fede70dd-d1c5-4408-a082-a5f0364195d6	true	introspection.token.claim
fede70dd-d1c5-4408-a082-a5f0364195d6	true	userinfo.token.claim
fede70dd-d1c5-4408-a082-a5f0364195d6	locale	user.attribute
fede70dd-d1c5-4408-a082-a5f0364195d6	true	id.token.claim
fede70dd-d1c5-4408-a082-a5f0364195d6	true	access.token.claim
fede70dd-d1c5-4408-a082-a5f0364195d6	locale	claim.name
fede70dd-d1c5-4408-a082-a5f0364195d6	String	jsonType.label
\.


--
-- Data for Name: realm; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm (id, access_code_lifespan, user_action_lifespan, access_token_lifespan, account_theme, admin_theme, email_theme, enabled, events_enabled, events_expiration, login_theme, name, not_before, password_policy, registration_allowed, remember_me, reset_password_allowed, social, ssl_required, sso_idle_timeout, sso_max_lifespan, update_profile_on_soc_login, verify_email, master_admin_client, login_lifespan, internationalization_enabled, default_locale, reg_email_as_username, admin_events_enabled, admin_events_details_enabled, edit_username_allowed, otp_policy_counter, otp_policy_window, otp_policy_period, otp_policy_digits, otp_policy_alg, otp_policy_type, browser_flow, registration_flow, direct_grant_flow, reset_credentials_flow, client_auth_flow, offline_session_idle_timeout, revoke_refresh_token, access_token_life_implicit, login_with_email_allowed, duplicate_emails_allowed, docker_auth_flow, refresh_token_max_reuse, allow_user_managed_access, sso_max_lifespan_remember_me, sso_idle_timeout_remember_me, default_role) FROM stdin;
83b6664d-539e-4bed-a376-685d50e40b98	60	300	300	\N	\N	\N	t	f	0	\N	loci-realm	0	\N	t	t	t	f	EXTERNAL	1800	36000	f	f	35a8ca56-c6f1-47f3-a285-17454ba2afa1	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	801e570d-bc15-45e3-9f8e-afe9f8f28dec	8c356fe3-6843-440d-ac93-b4cc6352781f	d7b8234b-3d04-4263-ac07-79794e3fb8c0	2ec9ca75-4f99-43e9-816d-708c9a550838	05607ab5-8839-424c-b1e7-7f0ccad2063e	2592000	f	900	t	f	08caf47c-2dda-42d5-a33d-3df1271e703c	0	f	0	0	929392ee-0474-4ed0-a64e-9b0132025c91
863411db-f685-4e36-9210-ef7892793272	60	300	60	\N	\N	\N	t	f	0	\N	master	0	\N	f	f	f	f	EXTERNAL	1800	36000	f	f	4744ff01-2e1f-4b2d-8d58-85f94704e41a	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	6650ceb0-4f67-422a-abaa-858ad9cc7ced	6c2990af-3f0e-4cc8-aa1b-1743f5b9e956	15cbcb5c-9138-4459-a021-b4452695744a	8cdf9d22-3eae-4235-8a21-13a0b3c0e3d3	39af4770-19a1-4c3c-9a4b-d48ff0313efa	2592000	f	900	t	f	d3f88d58-4bd3-4e50-9c39-20510841d3f9	0	f	0	0	6730a033-7401-46cd-9d13-4e5fb261a249
\.


--
-- Data for Name: realm_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_attribute (name, realm_id, value) FROM stdin;
_browser_header.contentSecurityPolicyReportOnly	863411db-f685-4e36-9210-ef7892793272	
_browser_header.xContentTypeOptions	863411db-f685-4e36-9210-ef7892793272	nosniff
_browser_header.referrerPolicy	863411db-f685-4e36-9210-ef7892793272	no-referrer
_browser_header.xRobotsTag	863411db-f685-4e36-9210-ef7892793272	none
_browser_header.xFrameOptions	863411db-f685-4e36-9210-ef7892793272	SAMEORIGIN
_browser_header.contentSecurityPolicy	863411db-f685-4e36-9210-ef7892793272	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.xXSSProtection	863411db-f685-4e36-9210-ef7892793272	1; mode=block
_browser_header.strictTransportSecurity	863411db-f685-4e36-9210-ef7892793272	max-age=31536000; includeSubDomains
bruteForceProtected	863411db-f685-4e36-9210-ef7892793272	false
permanentLockout	863411db-f685-4e36-9210-ef7892793272	false
maxTemporaryLockouts	863411db-f685-4e36-9210-ef7892793272	0
maxFailureWaitSeconds	863411db-f685-4e36-9210-ef7892793272	900
minimumQuickLoginWaitSeconds	863411db-f685-4e36-9210-ef7892793272	60
waitIncrementSeconds	863411db-f685-4e36-9210-ef7892793272	60
quickLoginCheckMilliSeconds	863411db-f685-4e36-9210-ef7892793272	1000
maxDeltaTimeSeconds	863411db-f685-4e36-9210-ef7892793272	43200
failureFactor	863411db-f685-4e36-9210-ef7892793272	30
realmReusableOtpCode	863411db-f685-4e36-9210-ef7892793272	false
firstBrokerLoginFlowId	863411db-f685-4e36-9210-ef7892793272	b73e4239-23d0-4044-8556-09e156ce1c71
displayName	863411db-f685-4e36-9210-ef7892793272	Keycloak
displayNameHtml	863411db-f685-4e36-9210-ef7892793272	<div class="kc-logo-text"><span>Keycloak</span></div>
defaultSignatureAlgorithm	863411db-f685-4e36-9210-ef7892793272	RS256
offlineSessionMaxLifespanEnabled	863411db-f685-4e36-9210-ef7892793272	false
offlineSessionMaxLifespan	863411db-f685-4e36-9210-ef7892793272	5184000
bruteForceProtected	83b6664d-539e-4bed-a376-685d50e40b98	false
permanentLockout	83b6664d-539e-4bed-a376-685d50e40b98	false
maxTemporaryLockouts	83b6664d-539e-4bed-a376-685d50e40b98	0
maxFailureWaitSeconds	83b6664d-539e-4bed-a376-685d50e40b98	900
minimumQuickLoginWaitSeconds	83b6664d-539e-4bed-a376-685d50e40b98	60
waitIncrementSeconds	83b6664d-539e-4bed-a376-685d50e40b98	60
quickLoginCheckMilliSeconds	83b6664d-539e-4bed-a376-685d50e40b98	1000
maxDeltaTimeSeconds	83b6664d-539e-4bed-a376-685d50e40b98	43200
failureFactor	83b6664d-539e-4bed-a376-685d50e40b98	30
realmReusableOtpCode	83b6664d-539e-4bed-a376-685d50e40b98	false
defaultSignatureAlgorithm	83b6664d-539e-4bed-a376-685d50e40b98	RS256
offlineSessionMaxLifespanEnabled	83b6664d-539e-4bed-a376-685d50e40b98	false
offlineSessionMaxLifespan	83b6664d-539e-4bed-a376-685d50e40b98	5184000
clientSessionIdleTimeout	83b6664d-539e-4bed-a376-685d50e40b98	0
clientSessionMaxLifespan	83b6664d-539e-4bed-a376-685d50e40b98	0
clientOfflineSessionIdleTimeout	83b6664d-539e-4bed-a376-685d50e40b98	0
clientOfflineSessionMaxLifespan	83b6664d-539e-4bed-a376-685d50e40b98	0
actionTokenGeneratedByAdminLifespan	83b6664d-539e-4bed-a376-685d50e40b98	43200
actionTokenGeneratedByUserLifespan	83b6664d-539e-4bed-a376-685d50e40b98	300
oauth2DeviceCodeLifespan	83b6664d-539e-4bed-a376-685d50e40b98	600
oauth2DevicePollingInterval	83b6664d-539e-4bed-a376-685d50e40b98	5
organizationsEnabled	83b6664d-539e-4bed-a376-685d50e40b98	false
webAuthnPolicyRpEntityName	83b6664d-539e-4bed-a376-685d50e40b98	keycloak
webAuthnPolicySignatureAlgorithms	83b6664d-539e-4bed-a376-685d50e40b98	ES256,RS256
webAuthnPolicyRpId	83b6664d-539e-4bed-a376-685d50e40b98	
webAuthnPolicyAttestationConveyancePreference	83b6664d-539e-4bed-a376-685d50e40b98	not specified
webAuthnPolicyAuthenticatorAttachment	83b6664d-539e-4bed-a376-685d50e40b98	not specified
webAuthnPolicyRequireResidentKey	83b6664d-539e-4bed-a376-685d50e40b98	not specified
webAuthnPolicyUserVerificationRequirement	83b6664d-539e-4bed-a376-685d50e40b98	not specified
webAuthnPolicyCreateTimeout	83b6664d-539e-4bed-a376-685d50e40b98	0
webAuthnPolicyAvoidSameAuthenticatorRegister	83b6664d-539e-4bed-a376-685d50e40b98	false
webAuthnPolicyRpEntityNamePasswordless	83b6664d-539e-4bed-a376-685d50e40b98	keycloak
webAuthnPolicySignatureAlgorithmsPasswordless	83b6664d-539e-4bed-a376-685d50e40b98	ES256,RS256
webAuthnPolicyRpIdPasswordless	83b6664d-539e-4bed-a376-685d50e40b98	
webAuthnPolicyAttestationConveyancePreferencePasswordless	83b6664d-539e-4bed-a376-685d50e40b98	not specified
webAuthnPolicyAuthenticatorAttachmentPasswordless	83b6664d-539e-4bed-a376-685d50e40b98	not specified
webAuthnPolicyRequireResidentKeyPasswordless	83b6664d-539e-4bed-a376-685d50e40b98	not specified
webAuthnPolicyUserVerificationRequirementPasswordless	83b6664d-539e-4bed-a376-685d50e40b98	not specified
webAuthnPolicyCreateTimeoutPasswordless	83b6664d-539e-4bed-a376-685d50e40b98	0
webAuthnPolicyAvoidSameAuthenticatorRegisterPasswordless	83b6664d-539e-4bed-a376-685d50e40b98	false
cibaBackchannelTokenDeliveryMode	83b6664d-539e-4bed-a376-685d50e40b98	poll
cibaExpiresIn	83b6664d-539e-4bed-a376-685d50e40b98	120
cibaInterval	83b6664d-539e-4bed-a376-685d50e40b98	5
cibaAuthRequestedUserHint	83b6664d-539e-4bed-a376-685d50e40b98	login_hint
parRequestUriLifespan	83b6664d-539e-4bed-a376-685d50e40b98	60
firstBrokerLoginFlowId	83b6664d-539e-4bed-a376-685d50e40b98	668883be-9b51-42f6-9e35-cb35d7961852
client-policies.profiles	83b6664d-539e-4bed-a376-685d50e40b98	{"profiles":[]}
client-policies.policies	83b6664d-539e-4bed-a376-685d50e40b98	{"policies":[]}
_browser_header.contentSecurityPolicyReportOnly	83b6664d-539e-4bed-a376-685d50e40b98	
_browser_header.xContentTypeOptions	83b6664d-539e-4bed-a376-685d50e40b98	nosniff
_browser_header.referrerPolicy	83b6664d-539e-4bed-a376-685d50e40b98	no-referrer
_browser_header.xRobotsTag	83b6664d-539e-4bed-a376-685d50e40b98	none
_browser_header.xFrameOptions	83b6664d-539e-4bed-a376-685d50e40b98	SAMEORIGIN
_browser_header.contentSecurityPolicy	83b6664d-539e-4bed-a376-685d50e40b98	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.xXSSProtection	83b6664d-539e-4bed-a376-685d50e40b98	1; mode=block
_browser_header.strictTransportSecurity	83b6664d-539e-4bed-a376-685d50e40b98	max-age=31536000; includeSubDomains
\.


--
-- Data for Name: realm_default_groups; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_default_groups (realm_id, group_id) FROM stdin;
\.


--
-- Data for Name: realm_enabled_event_types; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_enabled_event_types (realm_id, value) FROM stdin;
\.


--
-- Data for Name: realm_events_listeners; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_events_listeners (realm_id, value) FROM stdin;
863411db-f685-4e36-9210-ef7892793272	jboss-logging
83b6664d-539e-4bed-a376-685d50e40b98	jboss-logging
\.


--
-- Data for Name: realm_localizations; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_localizations (realm_id, locale, texts) FROM stdin;
\.


--
-- Data for Name: realm_required_credential; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_required_credential (type, form_label, input, secret, realm_id) FROM stdin;
password	password	t	t	863411db-f685-4e36-9210-ef7892793272
password	password	t	t	83b6664d-539e-4bed-a376-685d50e40b98
\.


--
-- Data for Name: realm_smtp_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_smtp_config (realm_id, value, name) FROM stdin;
\.


--
-- Data for Name: realm_supported_locales; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_supported_locales (realm_id, value) FROM stdin;
\.


--
-- Data for Name: redirect_uris; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.redirect_uris (client_id, value) FROM stdin;
5a657f88-6b01-40ff-a1f5-bc6f95dee042	/realms/master/account/*
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	/realms/master/account/*
94105b65-67ea-498a-a4e5-cdd952818067	/admin/master/console/*
cb847cf7-5d97-441b-b7dd-a6f1a47689a2	/realms/loci-realm/account/*
1512bb33-3ef4-4dad-af1f-47081a2a75dc	/realms/loci-realm/account/*
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	http://192.168.1.21:4200/*
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	http://localhost:4200/*
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	http://192.168.1.21:4200/
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	http://localhost:4200/
ba4de397-daf8-404b-ad79-249e4d09a713	/admin/loci-realm/console/*
e85deedd-b2fb-47d3-acef-508423a77f22	http://localhost:8080/*
d004af51-9d2e-47d3-9340-a3a411f42029	http://localhost:8080/*
\.


--
-- Data for Name: required_action_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.required_action_config (required_action_id, value, name) FROM stdin;
\.


--
-- Data for Name: required_action_provider; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.required_action_provider (id, alias, name, realm_id, enabled, default_action, provider_id, priority) FROM stdin;
3712726b-63f2-4cb5-84a9-71ac8a8b490a	VERIFY_EMAIL	Verify Email	863411db-f685-4e36-9210-ef7892793272	t	f	VERIFY_EMAIL	50
38700f68-e698-4a55-a7d7-1d85af14f464	UPDATE_PROFILE	Update Profile	863411db-f685-4e36-9210-ef7892793272	t	f	UPDATE_PROFILE	40
8ba5e2c4-a83c-419c-bf84-6d79299325e1	CONFIGURE_TOTP	Configure OTP	863411db-f685-4e36-9210-ef7892793272	t	f	CONFIGURE_TOTP	10
b10ffe0f-624d-4a21-b2aa-3af586684afd	UPDATE_PASSWORD	Update Password	863411db-f685-4e36-9210-ef7892793272	t	f	UPDATE_PASSWORD	30
a8fd6cce-4e00-49f9-bbb8-7a6e7d5b5eef	TERMS_AND_CONDITIONS	Terms and Conditions	863411db-f685-4e36-9210-ef7892793272	f	f	TERMS_AND_CONDITIONS	20
23184a69-f063-4add-ace9-d03864160b5d	delete_account	Delete Account	863411db-f685-4e36-9210-ef7892793272	f	f	delete_account	60
a8126e8c-db0c-4a1c-9f79-dd8669a0a411	delete_credential	Delete Credential	863411db-f685-4e36-9210-ef7892793272	t	f	delete_credential	100
c2c41331-596c-4552-9b36-d9475067b717	update_user_locale	Update User Locale	863411db-f685-4e36-9210-ef7892793272	t	f	update_user_locale	1000
79602a1c-b243-4b1d-ae95-1a339320d35b	webauthn-register	Webauthn Register	863411db-f685-4e36-9210-ef7892793272	t	f	webauthn-register	70
dafa2d96-380f-4cc1-a453-c80c7cab42c8	webauthn-register-passwordless	Webauthn Register Passwordless	863411db-f685-4e36-9210-ef7892793272	t	f	webauthn-register-passwordless	80
73c89128-0202-4247-9b3c-45033df41977	VERIFY_PROFILE	Verify Profile	863411db-f685-4e36-9210-ef7892793272	t	f	VERIFY_PROFILE	90
28eb9b39-5211-4c1d-b283-176b5ed83293	CONFIGURE_TOTP	Configure OTP	83b6664d-539e-4bed-a376-685d50e40b98	t	f	CONFIGURE_TOTP	10
06c82f3c-195c-4f6b-8676-43839383117e	TERMS_AND_CONDITIONS	Terms and Conditions	83b6664d-539e-4bed-a376-685d50e40b98	f	f	TERMS_AND_CONDITIONS	20
d892afcd-bb03-4a61-8222-e3cedda6bd29	UPDATE_PASSWORD	Update Password	83b6664d-539e-4bed-a376-685d50e40b98	t	f	UPDATE_PASSWORD	30
5dd12cbe-9082-4c61-80ac-bbef90695b1f	UPDATE_PROFILE	Update Profile	83b6664d-539e-4bed-a376-685d50e40b98	t	f	UPDATE_PROFILE	40
8f46f603-a0a7-420c-a55e-9de5a9bdb5d4	VERIFY_EMAIL	Verify Email	83b6664d-539e-4bed-a376-685d50e40b98	t	f	VERIFY_EMAIL	50
19e581ec-f7d1-4867-b40b-77b00525a6f0	delete_account	Delete Account	83b6664d-539e-4bed-a376-685d50e40b98	f	f	delete_account	60
c0218982-5567-4039-b84b-73b17918a056	webauthn-register	Webauthn Register	83b6664d-539e-4bed-a376-685d50e40b98	t	f	webauthn-register	70
6acc4095-50f6-45ae-9ce2-d67589bbfbf0	webauthn-register-passwordless	Webauthn Register Passwordless	83b6664d-539e-4bed-a376-685d50e40b98	t	f	webauthn-register-passwordless	80
713d893c-7e5f-4700-a441-a1de2f315ae6	VERIFY_PROFILE	Verify Profile	83b6664d-539e-4bed-a376-685d50e40b98	t	f	VERIFY_PROFILE	90
4e1ba0de-ef97-4501-b837-4d83beac52f2	delete_credential	Delete Credential	83b6664d-539e-4bed-a376-685d50e40b98	t	f	delete_credential	100
88c109aa-29a3-41e5-a3de-bce066127c67	update_user_locale	Update User Locale	83b6664d-539e-4bed-a376-685d50e40b98	t	f	update_user_locale	1000
\.


--
-- Data for Name: resource_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_attribute (id, name, value, resource_id) FROM stdin;
\.


--
-- Data for Name: resource_policy; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_policy (resource_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_scope (resource_id, scope_id) FROM stdin;
\.


--
-- Data for Name: resource_server; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_server (id, allow_rs_remote_mgmt, policy_enforce_mode, decision_strategy) FROM stdin;
\.


--
-- Data for Name: resource_server_perm_ticket; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_server_perm_ticket (id, owner, requester, created_timestamp, granted_timestamp, resource_id, scope_id, resource_server_id, policy_id) FROM stdin;
\.


--
-- Data for Name: resource_server_policy; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_server_policy (id, name, description, type, decision_strategy, logic, resource_server_id, owner) FROM stdin;
\.


--
-- Data for Name: resource_server_resource; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_server_resource (id, name, type, icon_uri, owner, resource_server_id, owner_managed_access, display_name) FROM stdin;
\.


--
-- Data for Name: resource_server_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_server_scope (id, name, icon_uri, resource_server_id, display_name) FROM stdin;
\.


--
-- Data for Name: resource_uris; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.resource_uris (resource_id, value) FROM stdin;
\.


--
-- Data for Name: revoked_token; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.revoked_token (id, expire) FROM stdin;
\.


--
-- Data for Name: role_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.role_attribute (id, role_id, name, value) FROM stdin;
\.


--
-- Data for Name: scope_mapping; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.scope_mapping (client_id, role_id) FROM stdin;
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	534b41bb-f775-49c9-8c4d-078018cda382
a172b4c9-abe4-4f0f-8ccc-1160c40bcb3f	a79c57d6-dcb5-4563-b56b-7fcd4d5f798e
1512bb33-3ef4-4dad-af1f-47081a2a75dc	cd9c2212-1347-472e-8e9f-20be2bfc2ec2
1512bb33-3ef4-4dad-af1f-47081a2a75dc	710ed846-759e-45bc-afa4-bb5ef183d08c
\.


--
-- Data for Name: scope_policy; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.scope_policy (scope_id, policy_id) FROM stdin;
\.


--
-- Data for Name: user_; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_ (friend_request_setting, profile_visibility, created_date, id, last_active, last_modified_date, last_seen, public_id, bio, email, firstname, gender, image_url, last_seen_setting, lastname) FROM stdin;
0	f	2025-11-28 09:49:57.110522+00	1	\N	2025-11-28 09:57:31.562403+00	\N	aea154f4-e7e0-41dd-a925-81f6508cc45c	\N	corazon.quigley@gmail.com	Brekke	\N	\N	CONTACT_ONLY	Dorene
\.


--
-- Data for Name: user_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_attribute (name, value, user_id, id, long_value_hash, long_value_hash_lower_case, long_value) FROM stdin;
is_temporary_admin	true	80745a6f-6e51-434c-8620-e501b3764a8f	44f430dd-b666-4de0-9cd0-e38990af28a1	\N	\N	\N
\.


--
-- Data for Name: user_authority; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_authority (user_id, authority_name) FROM stdin;
1	ROLE_UMA_AUTHORIZATION
1	ROLE_OFFLINE_ACCESS
1	ROLE_DEFAULT-ROLES-LOCI-REALM
1	ROLE_USER
\.


--
-- Data for Name: user_consent; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_consent (id, client_id, user_id, created_date, last_updated_date, client_storage_provider, external_client_id) FROM stdin;
\.


--
-- Data for Name: user_consent_client_scope; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_consent_client_scope (user_consent_id, scope_id) FROM stdin;
\.


--
-- Data for Name: user_entity; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_entity (id, email, email_constraint, email_verified, enabled, federation_link, first_name, last_name, realm_id, username, created_timestamp, service_account_client_link, not_before) FROM stdin;
80745a6f-6e51-434c-8620-e501b3764a8f	\N	c4668bf9-263d-4143-99c1-3d2cc4640aa5	f	t	\N	\N	\N	863411db-f685-4e36-9210-ef7892793272	admin	1762927515681	\N	0
4eb678c6-f977-46ba-baaf-c9bf4bec62ef	mohammad.walter@gmail.com	mohammad.walter@gmail.com	t	t	\N	Corrinne	Senger	83b6664d-539e-4bed-a376-685d50e40b98	mohammad.walter@gmail.com	1764321069899	\N	0
3a050261-b704-426e-a7c2-68b69ebeb90b	lisette.feil@yahoo.com	lisette.feil@yahoo.com	t	t	\N	Vernon	Cremin	83b6664d-539e-4bed-a376-685d50e40b98	lisette.feil@yahoo.com	1764321070303	\N	0
7d756359-1b87-442d-a2e8-e4785dd5aaa4	max.sipes@yahoo.com	max.sipes@yahoo.com	t	t	\N	Shannon	Beier	83b6664d-539e-4bed-a376-685d50e40b98	max.sipes@yahoo.com	1764321070543	\N	0
9d478593-8658-473d-9f6a-cfb8530385b3	eugenie.goldner@gmail.com	eugenie.goldner@gmail.com	t	t	\N	Davida	Weimann	83b6664d-539e-4bed-a376-685d50e40b98	eugenie.goldner@gmail.com	1764321070817	\N	0
09ef75b6-5d8f-443f-ab88-1e6e4fdd09a0	bryon.reilly@yahoo.com	bryon.reilly@yahoo.com	t	t	\N	Junita	Goldner	83b6664d-539e-4bed-a376-685d50e40b98	bryon.reilly@yahoo.com	1764321071055	\N	0
a03e7f08-f0c7-4c40-97d6-e3bff7d00057	corazon.quigley@gmail.com	corazon.quigley@gmail.com	t	t	\N	Dorene	Brekke	83b6664d-539e-4bed-a376-685d50e40b98	corazon.quigley@gmail.com	1764321071300	\N	0
8a98e8b9-37ff-4a89-b8d8-a7e4d45d4f1e	alaina.goyette@gmail.com	alaina.goyette@gmail.com	t	t	\N	Janean	Schamberger	83b6664d-539e-4bed-a376-685d50e40b98	alaina.goyette@gmail.com	1764321071537	\N	0
8c2bd715-2950-4ad6-a43b-7d75874ed33d	earle.wilderman@yahoo.com	earle.wilderman@yahoo.com	t	t	\N	Tory	Bradtke	83b6664d-539e-4bed-a376-685d50e40b98	earle.wilderman@yahoo.com	1764321071770	\N	0
a8973d47-25d6-4904-ab93-4b30aac43dba	glady.conn@yahoo.com	glady.conn@yahoo.com	t	t	\N	Fatima	Hessel	83b6664d-539e-4bed-a376-685d50e40b98	glady.conn@yahoo.com	1764321072001	\N	0
4c8aa205-60a4-4caf-8b62-34777a9784dc	ulysses.barrows@gmail.com	ulysses.barrows@gmail.com	t	t	\N	Loreta	Beer	83b6664d-539e-4bed-a376-685d50e40b98	ulysses.barrows@gmail.com	1764321072228	\N	0
27e24793-eb80-4ba8-9ace-95f432ca5199	sydney.mclaughlin@hotmail.com	sydney.mclaughlin@hotmail.com	t	t	\N	Ricky	Skiles	83b6664d-539e-4bed-a376-685d50e40b98	sydney.mclaughlin@hotmail.com	1764321072456	\N	0
cf8665b4-ef04-45b5-9b90-b3864e58e0ff	jung.heidenreich@gmail.com	jung.heidenreich@gmail.com	t	t	\N	Galen	Beier	83b6664d-539e-4bed-a376-685d50e40b98	jung.heidenreich@gmail.com	1764321072681	\N	0
7b0afce6-4207-467a-9e67-e013d8f70566	kourtney.ohara@yahoo.com	kourtney.ohara@yahoo.com	t	t	\N	Missy	Purdy	83b6664d-539e-4bed-a376-685d50e40b98	kourtney.ohara@yahoo.com	1764321072951	\N	0
75729648-21f9-4808-aaeb-0a9f2aaed554	gidget.bergstrom@yahoo.com	gidget.bergstrom@yahoo.com	t	t	\N	Kaylee	Doyle	83b6664d-539e-4bed-a376-685d50e40b98	gidget.bergstrom@yahoo.com	1764321073209	\N	0
ce35d0b0-2a72-4a57-8fac-dcb8348f4c41	nestor.cartwright@yahoo.com	nestor.cartwright@yahoo.com	t	t	\N	Timika	Metz	83b6664d-539e-4bed-a376-685d50e40b98	nestor.cartwright@yahoo.com	1764321073436	\N	0
8ad53006-5ab5-45fb-84c0-497a6424d782	kittie.hackett@yahoo.com	kittie.hackett@yahoo.com	t	t	\N	Caroline	Doyle	83b6664d-539e-4bed-a376-685d50e40b98	kittie.hackett@yahoo.com	1764321073662	\N	0
a50fc325-a397-4f18-bb31-75cc1191ffa8	halley.balistreri@gmail.com	halley.balistreri@gmail.com	t	t	\N	Jaquelyn	Collier	83b6664d-539e-4bed-a376-685d50e40b98	halley.balistreri@gmail.com	1764321073905	\N	0
e0f4cddc-281e-48f7-8ca7-6dbbd0c0a96a	cedric.rau@yahoo.com	cedric.rau@yahoo.com	t	t	\N	Hong	Hammes	83b6664d-539e-4bed-a376-685d50e40b98	cedric.rau@yahoo.com	1764321074165	\N	0
b1e6055e-734d-4367-8330-975869b72a10	fanny.jakubowski@gmail.com	fanny.jakubowski@gmail.com	t	t	\N	Viola	Wilkinson	83b6664d-539e-4bed-a376-685d50e40b98	fanny.jakubowski@gmail.com	1764321074391	\N	0
32ca4097-7490-4a9a-aecf-8c948a2a0e41	jae.koch@yahoo.com	jae.koch@yahoo.com	t	t	\N	Sage	Graham	83b6664d-539e-4bed-a376-685d50e40b98	jae.koch@yahoo.com	1764321074615	\N	0
cd2e474a-f099-4cce-ac9f-4d047fd00a01	testuser1@gmail.com	testuser1@gmail.com	f	t	\N	trung	kien	83b6664d-539e-4bed-a376-685d50e40b98	testuser1	1764321439014	\N	0
\.


--
-- Data for Name: user_federation_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_federation_config (user_federation_provider_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_federation_mapper (id, name, federation_provider_id, federation_mapper_type, realm_id) FROM stdin;
\.


--
-- Data for Name: user_federation_mapper_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_federation_mapper_config (user_federation_mapper_id, value, name) FROM stdin;
\.


--
-- Data for Name: user_federation_provider; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_federation_provider (id, changed_sync_period, display_name, full_sync_period, last_sync, priority, provider_name, realm_id) FROM stdin;
\.


--
-- Data for Name: user_group_membership; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_group_membership (group_id, user_id, membership_type) FROM stdin;
\.


--
-- Data for Name: user_required_action; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_required_action (user_id, required_action) FROM stdin;
\.


--
-- Data for Name: user_role_mapping; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_role_mapping (role_id, user_id) FROM stdin;
6730a033-7401-46cd-9d13-4e5fb261a249	80745a6f-6e51-434c-8620-e501b3764a8f
512e309b-99ec-473d-95d7-4c3c18f23636	80745a6f-6e51-434c-8620-e501b3764a8f
929392ee-0474-4ed0-a64e-9b0132025c91	4eb678c6-f977-46ba-baaf-c9bf4bec62ef
929392ee-0474-4ed0-a64e-9b0132025c91	3a050261-b704-426e-a7c2-68b69ebeb90b
929392ee-0474-4ed0-a64e-9b0132025c91	7d756359-1b87-442d-a2e8-e4785dd5aaa4
929392ee-0474-4ed0-a64e-9b0132025c91	9d478593-8658-473d-9f6a-cfb8530385b3
929392ee-0474-4ed0-a64e-9b0132025c91	09ef75b6-5d8f-443f-ab88-1e6e4fdd09a0
929392ee-0474-4ed0-a64e-9b0132025c91	a03e7f08-f0c7-4c40-97d6-e3bff7d00057
929392ee-0474-4ed0-a64e-9b0132025c91	8a98e8b9-37ff-4a89-b8d8-a7e4d45d4f1e
929392ee-0474-4ed0-a64e-9b0132025c91	8c2bd715-2950-4ad6-a43b-7d75874ed33d
929392ee-0474-4ed0-a64e-9b0132025c91	a8973d47-25d6-4904-ab93-4b30aac43dba
929392ee-0474-4ed0-a64e-9b0132025c91	4c8aa205-60a4-4caf-8b62-34777a9784dc
929392ee-0474-4ed0-a64e-9b0132025c91	27e24793-eb80-4ba8-9ace-95f432ca5199
929392ee-0474-4ed0-a64e-9b0132025c91	cf8665b4-ef04-45b5-9b90-b3864e58e0ff
929392ee-0474-4ed0-a64e-9b0132025c91	7b0afce6-4207-467a-9e67-e013d8f70566
929392ee-0474-4ed0-a64e-9b0132025c91	75729648-21f9-4808-aaeb-0a9f2aaed554
929392ee-0474-4ed0-a64e-9b0132025c91	ce35d0b0-2a72-4a57-8fac-dcb8348f4c41
929392ee-0474-4ed0-a64e-9b0132025c91	8ad53006-5ab5-45fb-84c0-497a6424d782
929392ee-0474-4ed0-a64e-9b0132025c91	a50fc325-a397-4f18-bb31-75cc1191ffa8
929392ee-0474-4ed0-a64e-9b0132025c91	e0f4cddc-281e-48f7-8ca7-6dbbd0c0a96a
929392ee-0474-4ed0-a64e-9b0132025c91	b1e6055e-734d-4367-8330-975869b72a10
929392ee-0474-4ed0-a64e-9b0132025c91	32ca4097-7490-4a9a-aecf-8c948a2a0e41
929392ee-0474-4ed0-a64e-9b0132025c91	cd2e474a-f099-4cce-ac9f-4d047fd00a01
\.


--
-- Data for Name: username_login_failure; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.username_login_failure (realm_id, username, failed_login_not_before, last_failure, last_ip_failure, num_failures) FROM stdin;
\.


--
-- Data for Name: web_origins; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.web_origins (client_id, value) FROM stdin;
94105b65-67ea-498a-a4e5-cdd952818067	+
1946f9ea-72fe-4697-bf83-3dec92a8f8ee	*
ba4de397-daf8-404b-ad79-249e4d09a713	+
e85deedd-b2fb-47d3-acef-508423a77f22	http://localhost:8080
d004af51-9d2e-47d3-9340-a3a411f42029	
d004af51-9d2e-47d3-9340-a3a411f42029	http://localhost:8080/
\.


--
-- Name: message_sequence; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.message_sequence', 1, false);


--
-- Name: user_sequence; Type: SEQUENCE SET; Schema: public; Owner: admin
--

SELECT pg_catalog.setval('public.user_sequence', 1, true);


--
-- Name: username_login_failure CONSTRAINT_17-2; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.username_login_failure
    ADD CONSTRAINT "CONSTRAINT_17-2" PRIMARY KEY (realm_id, username);


--
-- Name: org_domain ORG_DOMAIN_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.org_domain
    ADD CONSTRAINT "ORG_DOMAIN_pkey" PRIMARY KEY (id, name);


--
-- Name: org ORG_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT "ORG_pkey" PRIMARY KEY (id);


--
-- Name: keycloak_role UK_J3RWUVD56ONTGSUHOGM184WW2-2; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT "UK_J3RWUVD56ONTGSUHOGM184WW2-2" UNIQUE (name, client_realm_constraint);


--
-- Name: authority authority_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authority
    ADD CONSTRAINT authority_pkey PRIMARY KEY (name);


--
-- Name: client_auth_flow_bindings c_cli_flow_bind; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_auth_flow_bindings
    ADD CONSTRAINT c_cli_flow_bind PRIMARY KEY (client_id, binding_name);


--
-- Name: client_scope_client c_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope_client
    ADD CONSTRAINT c_cli_scope_bind PRIMARY KEY (client_id, scope_id);


--
-- Name: client_initial_access cnstr_client_init_acc_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT cnstr_client_init_acc_pk PRIMARY KEY (id);


--
-- Name: realm_default_groups con_group_id_def_groups; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT con_group_id_def_groups UNIQUE (group_id);


--
-- Name: broker_link constr_broker_link_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.broker_link
    ADD CONSTRAINT constr_broker_link_pk PRIMARY KEY (identity_provider, user_id);


--
-- Name: component_config constr_component_config_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT constr_component_config_pk PRIMARY KEY (id);


--
-- Name: component constr_component_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT constr_component_pk PRIMARY KEY (id);


--
-- Name: fed_user_required_action constr_fed_required_action; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_required_action
    ADD CONSTRAINT constr_fed_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: fed_user_attribute constr_fed_user_attr_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_attribute
    ADD CONSTRAINT constr_fed_user_attr_pk PRIMARY KEY (id);


--
-- Name: fed_user_consent constr_fed_user_consent_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_consent
    ADD CONSTRAINT constr_fed_user_consent_pk PRIMARY KEY (id);


--
-- Name: fed_user_credential constr_fed_user_cred_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_credential
    ADD CONSTRAINT constr_fed_user_cred_pk PRIMARY KEY (id);


--
-- Name: fed_user_group_membership constr_fed_user_group; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_group_membership
    ADD CONSTRAINT constr_fed_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: fed_user_role_mapping constr_fed_user_role; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_role_mapping
    ADD CONSTRAINT constr_fed_user_role PRIMARY KEY (role_id, user_id);


--
-- Name: federated_user constr_federated_user; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.federated_user
    ADD CONSTRAINT constr_federated_user PRIMARY KEY (id);


--
-- Name: realm_default_groups constr_realm_default_groups; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT constr_realm_default_groups PRIMARY KEY (realm_id, group_id);


--
-- Name: realm_enabled_event_types constr_realm_enabl_event_types; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT constr_realm_enabl_event_types PRIMARY KEY (realm_id, value);


--
-- Name: realm_events_listeners constr_realm_events_listeners; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT constr_realm_events_listeners PRIMARY KEY (realm_id, value);


--
-- Name: realm_supported_locales constr_realm_supported_locales; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT constr_realm_supported_locales PRIMARY KEY (realm_id, value);


--
-- Name: identity_provider constraint_2b; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT constraint_2b PRIMARY KEY (internal_id);


--
-- Name: client_attributes constraint_3c; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT constraint_3c PRIMARY KEY (client_id, name);


--
-- Name: event_entity constraint_4; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.event_entity
    ADD CONSTRAINT constraint_4 PRIMARY KEY (id);


--
-- Name: federated_identity constraint_40; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT constraint_40 PRIMARY KEY (identity_provider, user_id);


--
-- Name: realm constraint_4a; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT constraint_4a PRIMARY KEY (id);


--
-- Name: user_federation_provider constraint_5c; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT constraint_5c PRIMARY KEY (id);


--
-- Name: client constraint_7; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT constraint_7 PRIMARY KEY (id);


--
-- Name: scope_mapping constraint_81; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT constraint_81 PRIMARY KEY (client_id, role_id);


--
-- Name: client_node_registrations constraint_84; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT constraint_84 PRIMARY KEY (client_id, name);


--
-- Name: realm_attribute constraint_9; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT constraint_9 PRIMARY KEY (name, realm_id);


--
-- Name: realm_required_credential constraint_92; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT constraint_92 PRIMARY KEY (realm_id, type);


--
-- Name: keycloak_role constraint_a; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT constraint_a PRIMARY KEY (id);


--
-- Name: admin_event_entity constraint_admin_event_entity; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.admin_event_entity
    ADD CONSTRAINT constraint_admin_event_entity PRIMARY KEY (id);


--
-- Name: authenticator_config_entry constraint_auth_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authenticator_config_entry
    ADD CONSTRAINT constraint_auth_cfg_pk PRIMARY KEY (authenticator_id, name);


--
-- Name: authentication_execution constraint_auth_exec_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT constraint_auth_exec_pk PRIMARY KEY (id);


--
-- Name: authentication_flow constraint_auth_flow_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT constraint_auth_flow_pk PRIMARY KEY (id);


--
-- Name: authenticator_config constraint_auth_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT constraint_auth_pk PRIMARY KEY (id);


--
-- Name: user_role_mapping constraint_c; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT constraint_c PRIMARY KEY (role_id, user_id);


--
-- Name: composite_role constraint_composite_role; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT constraint_composite_role PRIMARY KEY (composite, child_role);


--
-- Name: identity_provider_config constraint_d; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT constraint_d PRIMARY KEY (identity_provider_id, name);


--
-- Name: policy_config constraint_dpc; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT constraint_dpc PRIMARY KEY (policy_id, name);


--
-- Name: realm_smtp_config constraint_e; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT constraint_e PRIMARY KEY (realm_id, name);


--
-- Name: credential constraint_f; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT constraint_f PRIMARY KEY (id);


--
-- Name: user_federation_config constraint_f9; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT constraint_f9 PRIMARY KEY (user_federation_provider_id, name);


--
-- Name: resource_server_perm_ticket constraint_fapmt; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT constraint_fapmt PRIMARY KEY (id);


--
-- Name: resource_server_resource constraint_farsr; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT constraint_farsr PRIMARY KEY (id);


--
-- Name: resource_server_policy constraint_farsrp; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT constraint_farsrp PRIMARY KEY (id);


--
-- Name: associated_policy constraint_farsrpap; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT constraint_farsrpap PRIMARY KEY (policy_id, associated_policy_id);


--
-- Name: resource_policy constraint_farsrpp; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT constraint_farsrpp PRIMARY KEY (resource_id, policy_id);


--
-- Name: resource_server_scope constraint_farsrs; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT constraint_farsrs PRIMARY KEY (id);


--
-- Name: resource_scope constraint_farsrsp; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT constraint_farsrsp PRIMARY KEY (resource_id, scope_id);


--
-- Name: scope_policy constraint_farsrsps; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT constraint_farsrsps PRIMARY KEY (scope_id, policy_id);


--
-- Name: user_entity constraint_fb; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT constraint_fb PRIMARY KEY (id);


--
-- Name: user_federation_mapper_config constraint_fedmapper_cfg_pm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT constraint_fedmapper_cfg_pm PRIMARY KEY (user_federation_mapper_id, name);


--
-- Name: user_federation_mapper constraint_fedmapperpm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT constraint_fedmapperpm PRIMARY KEY (id);


--
-- Name: fed_user_consent_cl_scope constraint_fgrntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.fed_user_consent_cl_scope
    ADD CONSTRAINT constraint_fgrntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent_client_scope constraint_grntcsnt_clsc_pm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT constraint_grntcsnt_clsc_pm PRIMARY KEY (user_consent_id, scope_id);


--
-- Name: user_consent constraint_grntcsnt_pm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT constraint_grntcsnt_pm PRIMARY KEY (id);


--
-- Name: keycloak_group constraint_group; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT constraint_group PRIMARY KEY (id);


--
-- Name: group_attribute constraint_group_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT constraint_group_attribute_pk PRIMARY KEY (id);


--
-- Name: group_role_mapping constraint_group_role; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT constraint_group_role PRIMARY KEY (role_id, group_id);


--
-- Name: identity_provider_mapper constraint_idpm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT constraint_idpm PRIMARY KEY (id);


--
-- Name: idp_mapper_config constraint_idpmconfig; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT constraint_idpmconfig PRIMARY KEY (idp_mapper_id, name);


--
-- Name: migration_model constraint_migmod; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.migration_model
    ADD CONSTRAINT constraint_migmod PRIMARY KEY (id);


--
-- Name: offline_client_session constraint_offl_cl_ses_pk3; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.offline_client_session
    ADD CONSTRAINT constraint_offl_cl_ses_pk3 PRIMARY KEY (user_session_id, client_id, client_storage_provider, external_client_id, offline_flag);


--
-- Name: offline_user_session constraint_offl_us_ses_pk2; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.offline_user_session
    ADD CONSTRAINT constraint_offl_us_ses_pk2 PRIMARY KEY (user_session_id, offline_flag);


--
-- Name: protocol_mapper constraint_pcm; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT constraint_pcm PRIMARY KEY (id);


--
-- Name: protocol_mapper_config constraint_pmconfig; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT constraint_pmconfig PRIMARY KEY (protocol_mapper_id, name);


--
-- Name: redirect_uris constraint_redirect_uris; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT constraint_redirect_uris PRIMARY KEY (client_id, value);


--
-- Name: required_action_config constraint_req_act_cfg_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.required_action_config
    ADD CONSTRAINT constraint_req_act_cfg_pk PRIMARY KEY (required_action_id, name);


--
-- Name: required_action_provider constraint_req_act_prv_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT constraint_req_act_prv_pk PRIMARY KEY (id);


--
-- Name: user_required_action constraint_required_action; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT constraint_required_action PRIMARY KEY (required_action, user_id);


--
-- Name: resource_uris constraint_resour_uris_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT constraint_resour_uris_pk PRIMARY KEY (resource_id, value);


--
-- Name: role_attribute constraint_role_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT constraint_role_attribute_pk PRIMARY KEY (id);


--
-- Name: revoked_token constraint_rt; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.revoked_token
    ADD CONSTRAINT constraint_rt PRIMARY KEY (id);


--
-- Name: user_attribute constraint_user_attribute_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT constraint_user_attribute_pk PRIMARY KEY (id);


--
-- Name: user_group_membership constraint_user_group; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT constraint_user_group PRIMARY KEY (group_id, user_id);


--
-- Name: web_origins constraint_web_origins; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT constraint_web_origins PRIMARY KEY (client_id, value);


--
-- Name: databasechangeloglock databasechangeloglock_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.databasechangeloglock
    ADD CONSTRAINT databasechangeloglock_pkey PRIMARY KEY (id);


--
-- Name: flyway_schema_history flyway_schema_history_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.flyway_schema_history
    ADD CONSTRAINT flyway_schema_history_pk PRIMARY KEY (installed_rank);


--
-- Name: message message_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.message
    ADD CONSTRAINT message_pkey PRIMARY KEY (id);


--
-- Name: client_scope_attributes pk_cl_tmpl_attr; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT pk_cl_tmpl_attr PRIMARY KEY (scope_id, name);


--
-- Name: client_scope pk_cli_template; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT pk_cli_template PRIMARY KEY (id);


--
-- Name: resource_server pk_resource_server; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server
    ADD CONSTRAINT pk_resource_server PRIMARY KEY (id);


--
-- Name: client_scope_role_mapping pk_template_scope; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT pk_template_scope PRIMARY KEY (scope_id, role_id);


--
-- Name: default_client_scope r_def_cli_scope_bind; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT r_def_cli_scope_bind PRIMARY KEY (realm_id, scope_id);


--
-- Name: realm_localizations realm_localizations_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_localizations
    ADD CONSTRAINT realm_localizations_pkey PRIMARY KEY (realm_id, locale);


--
-- Name: resource_attribute res_attr_pk; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT res_attr_pk PRIMARY KEY (id);


--
-- Name: keycloak_group sibling_names; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.keycloak_group
    ADD CONSTRAINT sibling_names UNIQUE (realm_id, parent_group, name);


--
-- Name: identity_provider uk_2daelwnibji49avxsrtuf6xj33; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT uk_2daelwnibji49avxsrtuf6xj33 UNIQUE (provider_alias, realm_id);


--
-- Name: client uk_b71cjlbenv945rb6gcon438at; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client
    ADD CONSTRAINT uk_b71cjlbenv945rb6gcon438at UNIQUE (realm_id, client_id);


--
-- Name: client_scope uk_cli_scope; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope
    ADD CONSTRAINT uk_cli_scope UNIQUE (realm_id, name);


--
-- Name: user_entity uk_dykn684sl8up1crfei6eckhd7; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_dykn684sl8up1crfei6eckhd7 UNIQUE (realm_id, email_constraint);


--
-- Name: user_consent uk_external_consent; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_external_consent UNIQUE (client_storage_provider, external_client_id, user_id);


--
-- Name: resource_server_resource uk_frsr6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5ha6 UNIQUE (name, owner, resource_server_id);


--
-- Name: resource_server_perm_ticket uk_frsr6t700s9v50bu18ws5pmt; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT uk_frsr6t700s9v50bu18ws5pmt UNIQUE (owner, requester, resource_server_id, resource_id, scope_id);


--
-- Name: resource_server_policy uk_frsrpt700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT uk_frsrpt700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: resource_server_scope uk_frsrst700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT uk_frsrst700s9v50bu18ws5ha6 UNIQUE (name, resource_server_id);


--
-- Name: user_consent uk_local_consent; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT uk_local_consent UNIQUE (client_id, user_id);


--
-- Name: org uk_org_alias; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_alias UNIQUE (realm_id, alias);


--
-- Name: org uk_org_group; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_group UNIQUE (group_id);


--
-- Name: org uk_org_name; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.org
    ADD CONSTRAINT uk_org_name UNIQUE (realm_id, name);


--
-- Name: realm uk_orvsdmla56612eaefiq6wl5oi; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm
    ADD CONSTRAINT uk_orvsdmla56612eaefiq6wl5oi UNIQUE (name);


--
-- Name: user_entity uk_ru8tt6t700s9v50bu18ws5ha6; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_entity
    ADD CONSTRAINT uk_ru8tt6t700s9v50bu18ws5ha6 UNIQUE (realm_id, username);


--
-- Name: user_ user__pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_
    ADD CONSTRAINT user__pkey PRIMARY KEY (id);


--
-- Name: user_authority user_authority_pkey; Type: CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_authority
    ADD CONSTRAINT user_authority_pkey PRIMARY KEY (user_id, authority_name);


--
-- Name: fed_user_attr_long_values; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX fed_user_attr_long_values ON public.fed_user_attribute USING btree (long_value_hash, name);


--
-- Name: fed_user_attr_long_values_lower_case; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX fed_user_attr_long_values_lower_case ON public.fed_user_attribute USING btree (long_value_hash_lower_case, name);


--
-- Name: flyway_schema_history_s_idx; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX flyway_schema_history_s_idx ON public.flyway_schema_history USING btree (success);


--
-- Name: idx_admin_event_time; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_admin_event_time ON public.admin_event_entity USING btree (realm_id, admin_event_time);


--
-- Name: idx_assoc_pol_assoc_pol_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_assoc_pol_assoc_pol_id ON public.associated_policy USING btree (associated_policy_id);


--
-- Name: idx_auth_config_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_auth_config_realm ON public.authenticator_config USING btree (realm_id);


--
-- Name: idx_auth_exec_flow; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_auth_exec_flow ON public.authentication_execution USING btree (flow_id);


--
-- Name: idx_auth_exec_realm_flow; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_auth_exec_realm_flow ON public.authentication_execution USING btree (realm_id, flow_id);


--
-- Name: idx_auth_flow_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_auth_flow_realm ON public.authentication_flow USING btree (realm_id);


--
-- Name: idx_cl_clscope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_cl_clscope ON public.client_scope_client USING btree (scope_id);


--
-- Name: idx_client_att_by_name_value; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_client_att_by_name_value ON public.client_attributes USING btree (name, substr(value, 1, 255));


--
-- Name: idx_client_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_client_id ON public.client USING btree (client_id);


--
-- Name: idx_client_init_acc_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_client_init_acc_realm ON public.client_initial_access USING btree (realm_id);


--
-- Name: idx_clscope_attrs; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_clscope_attrs ON public.client_scope_attributes USING btree (scope_id);


--
-- Name: idx_clscope_cl; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_clscope_cl ON public.client_scope_client USING btree (client_id);


--
-- Name: idx_clscope_protmap; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_clscope_protmap ON public.protocol_mapper USING btree (client_scope_id);


--
-- Name: idx_clscope_role; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_clscope_role ON public.client_scope_role_mapping USING btree (scope_id);


--
-- Name: idx_compo_config_compo; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_compo_config_compo ON public.component_config USING btree (component_id);


--
-- Name: idx_component_provider_type; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_component_provider_type ON public.component USING btree (provider_type);


--
-- Name: idx_component_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_component_realm ON public.component USING btree (realm_id);


--
-- Name: idx_composite; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_composite ON public.composite_role USING btree (composite);


--
-- Name: idx_composite_child; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_composite_child ON public.composite_role USING btree (child_role);


--
-- Name: idx_defcls_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_defcls_realm ON public.default_client_scope USING btree (realm_id);


--
-- Name: idx_defcls_scope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_defcls_scope ON public.default_client_scope USING btree (scope_id);


--
-- Name: idx_event_time; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_event_time ON public.event_entity USING btree (realm_id, event_time);


--
-- Name: idx_fedidentity_feduser; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fedidentity_feduser ON public.federated_identity USING btree (federated_user_id);


--
-- Name: idx_fedidentity_user; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fedidentity_user ON public.federated_identity USING btree (user_id);


--
-- Name: idx_fu_attribute; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_attribute ON public.fed_user_attribute USING btree (user_id, realm_id, name);


--
-- Name: idx_fu_cnsnt_ext; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_cnsnt_ext ON public.fed_user_consent USING btree (user_id, client_storage_provider, external_client_id);


--
-- Name: idx_fu_consent; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_consent ON public.fed_user_consent USING btree (user_id, client_id);


--
-- Name: idx_fu_consent_ru; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_consent_ru ON public.fed_user_consent USING btree (realm_id, user_id);


--
-- Name: idx_fu_credential; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_credential ON public.fed_user_credential USING btree (user_id, type);


--
-- Name: idx_fu_credential_ru; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_credential_ru ON public.fed_user_credential USING btree (realm_id, user_id);


--
-- Name: idx_fu_group_membership; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_group_membership ON public.fed_user_group_membership USING btree (user_id, group_id);


--
-- Name: idx_fu_group_membership_ru; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_group_membership_ru ON public.fed_user_group_membership USING btree (realm_id, user_id);


--
-- Name: idx_fu_required_action; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_required_action ON public.fed_user_required_action USING btree (user_id, required_action);


--
-- Name: idx_fu_required_action_ru; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_required_action_ru ON public.fed_user_required_action USING btree (realm_id, user_id);


--
-- Name: idx_fu_role_mapping; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_role_mapping ON public.fed_user_role_mapping USING btree (user_id, role_id);


--
-- Name: idx_fu_role_mapping_ru; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_fu_role_mapping_ru ON public.fed_user_role_mapping USING btree (realm_id, user_id);


--
-- Name: idx_group_att_by_name_value; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_group_att_by_name_value ON public.group_attribute USING btree (name, ((value)::character varying(250)));


--
-- Name: idx_group_attr_group; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_group_attr_group ON public.group_attribute USING btree (group_id);


--
-- Name: idx_group_role_mapp_group; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_group_role_mapp_group ON public.group_role_mapping USING btree (group_id);


--
-- Name: idx_id_prov_mapp_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_id_prov_mapp_realm ON public.identity_provider_mapper USING btree (realm_id);


--
-- Name: idx_ident_prov_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_ident_prov_realm ON public.identity_provider USING btree (realm_id);


--
-- Name: idx_idp_for_login; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_idp_for_login ON public.identity_provider USING btree (realm_id, enabled, link_only, hide_on_login, organization_id);


--
-- Name: idx_idp_realm_org; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_idp_realm_org ON public.identity_provider USING btree (realm_id, organization_id);


--
-- Name: idx_keycloak_role_client; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_keycloak_role_client ON public.keycloak_role USING btree (client);


--
-- Name: idx_keycloak_role_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_keycloak_role_realm ON public.keycloak_role USING btree (realm);


--
-- Name: idx_offline_uss_by_broker_session_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_offline_uss_by_broker_session_id ON public.offline_user_session USING btree (broker_session_id, realm_id);


--
-- Name: idx_offline_uss_by_last_session_refresh; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_offline_uss_by_last_session_refresh ON public.offline_user_session USING btree (realm_id, offline_flag, last_session_refresh);


--
-- Name: idx_offline_uss_by_user; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_offline_uss_by_user ON public.offline_user_session USING btree (user_id, realm_id, offline_flag);


--
-- Name: idx_org_domain_org_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_org_domain_org_id ON public.org_domain USING btree (org_id);


--
-- Name: idx_perm_ticket_owner; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_perm_ticket_owner ON public.resource_server_perm_ticket USING btree (owner);


--
-- Name: idx_perm_ticket_requester; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_perm_ticket_requester ON public.resource_server_perm_ticket USING btree (requester);


--
-- Name: idx_protocol_mapper_client; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_protocol_mapper_client ON public.protocol_mapper USING btree (client_id);


--
-- Name: idx_realm_attr_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_attr_realm ON public.realm_attribute USING btree (realm_id);


--
-- Name: idx_realm_clscope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_clscope ON public.client_scope USING btree (realm_id);


--
-- Name: idx_realm_def_grp_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_def_grp_realm ON public.realm_default_groups USING btree (realm_id);


--
-- Name: idx_realm_evt_list_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_evt_list_realm ON public.realm_events_listeners USING btree (realm_id);


--
-- Name: idx_realm_evt_types_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_evt_types_realm ON public.realm_enabled_event_types USING btree (realm_id);


--
-- Name: idx_realm_master_adm_cli; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_master_adm_cli ON public.realm USING btree (master_admin_client);


--
-- Name: idx_realm_supp_local_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_realm_supp_local_realm ON public.realm_supported_locales USING btree (realm_id);


--
-- Name: idx_redir_uri_client; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_redir_uri_client ON public.redirect_uris USING btree (client_id);


--
-- Name: idx_req_act_prov_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_req_act_prov_realm ON public.required_action_provider USING btree (realm_id);


--
-- Name: idx_res_policy_policy; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_res_policy_policy ON public.resource_policy USING btree (policy_id);


--
-- Name: idx_res_scope_scope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_res_scope_scope ON public.resource_scope USING btree (scope_id);


--
-- Name: idx_res_serv_pol_res_serv; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_res_serv_pol_res_serv ON public.resource_server_policy USING btree (resource_server_id);


--
-- Name: idx_res_srv_res_res_srv; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_res_srv_res_res_srv ON public.resource_server_resource USING btree (resource_server_id);


--
-- Name: idx_res_srv_scope_res_srv; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_res_srv_scope_res_srv ON public.resource_server_scope USING btree (resource_server_id);


--
-- Name: idx_rev_token_on_expire; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_rev_token_on_expire ON public.revoked_token USING btree (expire);


--
-- Name: idx_role_attribute; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_role_attribute ON public.role_attribute USING btree (role_id);


--
-- Name: idx_role_clscope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_role_clscope ON public.client_scope_role_mapping USING btree (role_id);


--
-- Name: idx_scope_mapping_role; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_scope_mapping_role ON public.scope_mapping USING btree (role_id);


--
-- Name: idx_scope_policy_policy; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_scope_policy_policy ON public.scope_policy USING btree (policy_id);


--
-- Name: idx_update_time; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_update_time ON public.migration_model USING btree (update_time);


--
-- Name: idx_usconsent_clscope; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_usconsent_clscope ON public.user_consent_client_scope USING btree (user_consent_id);


--
-- Name: idx_usconsent_scope_id; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_usconsent_scope_id ON public.user_consent_client_scope USING btree (scope_id);


--
-- Name: idx_user_attribute; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_attribute ON public.user_attribute USING btree (user_id);


--
-- Name: idx_user_attribute_name; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_attribute_name ON public.user_attribute USING btree (name, value);


--
-- Name: idx_user_consent; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_consent ON public.user_consent USING btree (user_id);


--
-- Name: idx_user_credential; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_credential ON public.credential USING btree (user_id);


--
-- Name: idx_user_email; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_email ON public.user_entity USING btree (email);


--
-- Name: idx_user_group_mapping; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_group_mapping ON public.user_group_membership USING btree (user_id);


--
-- Name: idx_user_reqactions; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_reqactions ON public.user_required_action USING btree (user_id);


--
-- Name: idx_user_role_mapping; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_role_mapping ON public.user_role_mapping USING btree (user_id);


--
-- Name: idx_user_service_account; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_user_service_account ON public.user_entity USING btree (realm_id, service_account_client_link);


--
-- Name: idx_usr_fed_map_fed_prv; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_usr_fed_map_fed_prv ON public.user_federation_mapper USING btree (federation_provider_id);


--
-- Name: idx_usr_fed_map_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_usr_fed_map_realm ON public.user_federation_mapper USING btree (realm_id);


--
-- Name: idx_usr_fed_prv_realm; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_usr_fed_prv_realm ON public.user_federation_provider USING btree (realm_id);


--
-- Name: idx_web_orig_client; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX idx_web_orig_client ON public.web_origins USING btree (client_id);


--
-- Name: user_attr_long_values; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX user_attr_long_values ON public.user_attribute USING btree (long_value_hash, name);


--
-- Name: user_attr_long_values_lower_case; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX user_attr_long_values_lower_case ON public.user_attribute USING btree (long_value_hash_lower_case, name);


--
-- Name: identity_provider fk2b4ebc52ae5c3b34; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider
    ADD CONSTRAINT fk2b4ebc52ae5c3b34 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: client_attributes fk3c47c64beacca966; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_attributes
    ADD CONSTRAINT fk3c47c64beacca966 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: federated_identity fk404288b92ef007a6; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.federated_identity
    ADD CONSTRAINT fk404288b92ef007a6 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_node_registrations fk4129723ba992f594; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_node_registrations
    ADD CONSTRAINT fk4129723ba992f594 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: user_authority fk6ktglpl5mjosa283rvken2py5; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_authority
    ADD CONSTRAINT fk6ktglpl5mjosa283rvken2py5 FOREIGN KEY (authority_name) REFERENCES public.authority(name);


--
-- Name: redirect_uris fk_1burs8pb4ouj97h5wuppahv9f; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.redirect_uris
    ADD CONSTRAINT fk_1burs8pb4ouj97h5wuppahv9f FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: user_federation_provider fk_1fj32f6ptolw2qy60cd8n01e8; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_provider
    ADD CONSTRAINT fk_1fj32f6ptolw2qy60cd8n01e8 FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_required_credential fk_5hg65lybevavkqfki3kponh9v; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_required_credential
    ADD CONSTRAINT fk_5hg65lybevavkqfki3kponh9v FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_attribute fk_5hrm2vlf9ql5fu022kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu022kqepovbr FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: user_attribute fk_5hrm2vlf9ql5fu043kqepovbr; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_attribute
    ADD CONSTRAINT fk_5hrm2vlf9ql5fu043kqepovbr FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: user_required_action fk_6qj3w1jw9cvafhe19bwsiuvmd; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_required_action
    ADD CONSTRAINT fk_6qj3w1jw9cvafhe19bwsiuvmd FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: keycloak_role fk_6vyqfe4cn4wlq8r6kt5vdsj5c; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.keycloak_role
    ADD CONSTRAINT fk_6vyqfe4cn4wlq8r6kt5vdsj5c FOREIGN KEY (realm) REFERENCES public.realm(id);


--
-- Name: realm_smtp_config fk_70ej8xdxgxd0b9hh6180irr0o; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_smtp_config
    ADD CONSTRAINT fk_70ej8xdxgxd0b9hh6180irr0o FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_attribute fk_8shxd6l3e9atqukacxgpffptw; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_attribute
    ADD CONSTRAINT fk_8shxd6l3e9atqukacxgpffptw FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: composite_role fk_a63wvekftu8jo1pnj81e7mce2; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_a63wvekftu8jo1pnj81e7mce2 FOREIGN KEY (composite) REFERENCES public.keycloak_role(id);


--
-- Name: authentication_execution fk_auth_exec_flow; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_flow FOREIGN KEY (flow_id) REFERENCES public.authentication_flow(id);


--
-- Name: authentication_execution fk_auth_exec_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authentication_execution
    ADD CONSTRAINT fk_auth_exec_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authentication_flow fk_auth_flow_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authentication_flow
    ADD CONSTRAINT fk_auth_flow_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: authenticator_config fk_auth_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.authenticator_config
    ADD CONSTRAINT fk_auth_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_role_mapping fk_c4fqv34p1mbylloxang7b1q3l; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_role_mapping
    ADD CONSTRAINT fk_c4fqv34p1mbylloxang7b1q3l FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: client_scope_attributes fk_cl_scope_attr_scope; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope_attributes
    ADD CONSTRAINT fk_cl_scope_attr_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_scope_role_mapping fk_cl_scope_rm_scope; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_scope_role_mapping
    ADD CONSTRAINT fk_cl_scope_rm_scope FOREIGN KEY (scope_id) REFERENCES public.client_scope(id);


--
-- Name: protocol_mapper fk_cli_scope_mapper; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_cli_scope_mapper FOREIGN KEY (client_scope_id) REFERENCES public.client_scope(id);


--
-- Name: client_initial_access fk_client_init_acc_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.client_initial_access
    ADD CONSTRAINT fk_client_init_acc_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: component_config fk_component_config; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.component_config
    ADD CONSTRAINT fk_component_config FOREIGN KEY (component_id) REFERENCES public.component(id);


--
-- Name: component fk_component_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.component
    ADD CONSTRAINT fk_component_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_default_groups fk_def_groups_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_default_groups
    ADD CONSTRAINT fk_def_groups_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_mapper_config fk_fedmapper_cfg; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_mapper_config
    ADD CONSTRAINT fk_fedmapper_cfg FOREIGN KEY (user_federation_mapper_id) REFERENCES public.user_federation_mapper(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_fedprv; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_fedprv FOREIGN KEY (federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_federation_mapper fk_fedmapperpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_mapper
    ADD CONSTRAINT fk_fedmapperpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: associated_policy fk_frsr5s213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsr5s213xcx4wnkog82ssrfy FOREIGN KEY (associated_policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrasp13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrasp13xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog82sspmt; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82sspmt FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_resource fk_frsrho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_resource
    ADD CONSTRAINT fk_frsrho213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog83sspmt; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog83sspmt FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_server_perm_ticket fk_frsrho213xcx4wnkog84sspmt; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrho213xcx4wnkog84sspmt FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: associated_policy fk_frsrpas14xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.associated_policy
    ADD CONSTRAINT fk_frsrpas14xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: scope_policy fk_frsrpass3xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.scope_policy
    ADD CONSTRAINT fk_frsrpass3xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_perm_ticket fk_frsrpo2128cx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_perm_ticket
    ADD CONSTRAINT fk_frsrpo2128cx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_server_policy fk_frsrpo213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_policy
    ADD CONSTRAINT fk_frsrpo213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: resource_scope fk_frsrpos13xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrpos13xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpos53xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpos53xcx4wnkog82ssrfy FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: resource_policy fk_frsrpp213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_policy
    ADD CONSTRAINT fk_frsrpp213xcx4wnkog82ssrfy FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: resource_scope fk_frsrps213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_scope
    ADD CONSTRAINT fk_frsrps213xcx4wnkog82ssrfy FOREIGN KEY (scope_id) REFERENCES public.resource_server_scope(id);


--
-- Name: resource_server_scope fk_frsrso213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_server_scope
    ADD CONSTRAINT fk_frsrso213xcx4wnkog82ssrfy FOREIGN KEY (resource_server_id) REFERENCES public.resource_server(id);


--
-- Name: composite_role fk_gr7thllb9lu8q4vqa4524jjy8; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.composite_role
    ADD CONSTRAINT fk_gr7thllb9lu8q4vqa4524jjy8 FOREIGN KEY (child_role) REFERENCES public.keycloak_role(id);


--
-- Name: user_consent_client_scope fk_grntcsnt_clsc_usc; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent_client_scope
    ADD CONSTRAINT fk_grntcsnt_clsc_usc FOREIGN KEY (user_consent_id) REFERENCES public.user_consent(id);


--
-- Name: user_consent fk_grntcsnt_user; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_consent
    ADD CONSTRAINT fk_grntcsnt_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: group_attribute fk_group_attribute_group; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.group_attribute
    ADD CONSTRAINT fk_group_attribute_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: group_role_mapping fk_group_role_group; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.group_role_mapping
    ADD CONSTRAINT fk_group_role_group FOREIGN KEY (group_id) REFERENCES public.keycloak_group(id);


--
-- Name: realm_enabled_event_types fk_h846o4h0w8epx5nwedrf5y69j; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_enabled_event_types
    ADD CONSTRAINT fk_h846o4h0w8epx5nwedrf5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: realm_events_listeners fk_h846o4h0w8epx5nxev9f5y69j; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_events_listeners
    ADD CONSTRAINT fk_h846o4h0w8epx5nxev9f5y69j FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: identity_provider_mapper fk_idpm_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider_mapper
    ADD CONSTRAINT fk_idpm_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: idp_mapper_config fk_idpmconfig; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.idp_mapper_config
    ADD CONSTRAINT fk_idpmconfig FOREIGN KEY (idp_mapper_id) REFERENCES public.identity_provider_mapper(id);


--
-- Name: web_origins fk_lojpho213xcx4wnkog82ssrfy; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.web_origins
    ADD CONSTRAINT fk_lojpho213xcx4wnkog82ssrfy FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: scope_mapping fk_ouse064plmlr732lxjcn1q5f1; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.scope_mapping
    ADD CONSTRAINT fk_ouse064plmlr732lxjcn1q5f1 FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: protocol_mapper fk_pcm_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.protocol_mapper
    ADD CONSTRAINT fk_pcm_realm FOREIGN KEY (client_id) REFERENCES public.client(id);


--
-- Name: credential fk_pfyr0glasqyl0dei3kl69r6v0; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.credential
    ADD CONSTRAINT fk_pfyr0glasqyl0dei3kl69r6v0 FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: protocol_mapper_config fk_pmconfig; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.protocol_mapper_config
    ADD CONSTRAINT fk_pmconfig FOREIGN KEY (protocol_mapper_id) REFERENCES public.protocol_mapper(id);


--
-- Name: default_client_scope fk_r_def_cli_scope_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.default_client_scope
    ADD CONSTRAINT fk_r_def_cli_scope_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: required_action_provider fk_req_act_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.required_action_provider
    ADD CONSTRAINT fk_req_act_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: resource_uris fk_resource_server_uris; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.resource_uris
    ADD CONSTRAINT fk_resource_server_uris FOREIGN KEY (resource_id) REFERENCES public.resource_server_resource(id);


--
-- Name: role_attribute fk_role_attribute_id; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.role_attribute
    ADD CONSTRAINT fk_role_attribute_id FOREIGN KEY (role_id) REFERENCES public.keycloak_role(id);


--
-- Name: realm_supported_locales fk_supported_locales_realm; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.realm_supported_locales
    ADD CONSTRAINT fk_supported_locales_realm FOREIGN KEY (realm_id) REFERENCES public.realm(id);


--
-- Name: user_federation_config fk_t13hpu1j94r2ebpekr39x5eu5; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_federation_config
    ADD CONSTRAINT fk_t13hpu1j94r2ebpekr39x5eu5 FOREIGN KEY (user_federation_provider_id) REFERENCES public.user_federation_provider(id);


--
-- Name: user_group_membership fk_user_group_user; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_group_membership
    ADD CONSTRAINT fk_user_group_user FOREIGN KEY (user_id) REFERENCES public.user_entity(id);


--
-- Name: policy_config fkdc34197cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.policy_config
    ADD CONSTRAINT fkdc34197cf864c4e43 FOREIGN KEY (policy_id) REFERENCES public.resource_server_policy(id);


--
-- Name: identity_provider_config fkdc4897cf864c4e43; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.identity_provider_config
    ADD CONSTRAINT fkdc4897cf864c4e43 FOREIGN KEY (identity_provider_id) REFERENCES public.identity_provider(internal_id);


--
-- Name: user_authority fkio2xcw9ogcqbasp25n5vttxrf; Type: FK CONSTRAINT; Schema: public; Owner: admin
--

ALTER TABLE ONLY public.user_authority
    ADD CONSTRAINT fkio2xcw9ogcqbasp25n5vttxrf FOREIGN KEY (user_id) REFERENCES public.user_(id);


--
-- PostgreSQL database dump complete
--


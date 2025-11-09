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
e6080f8f-1485-4b26-af8f-6c5476c39053	\N	auth-cookie	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	753c8b48-ba3e-42b3-bbf0-38ec8165eba7	2	10	f	\N	\N
c9b98b3d-3b9a-4ab0-8a7c-e37efef3da03	\N	auth-spnego	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	753c8b48-ba3e-42b3-bbf0-38ec8165eba7	3	20	f	\N	\N
4d4b14f7-4ec1-4001-a0d6-be25ab4825f8	\N	identity-provider-redirector	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	753c8b48-ba3e-42b3-bbf0-38ec8165eba7	2	25	f	\N	\N
78106478-4328-48b7-9219-078d2427b20f	\N	\N	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	753c8b48-ba3e-42b3-bbf0-38ec8165eba7	2	30	t	7a956b6f-7c55-495f-a140-8cef7b7efee6	\N
cecf6950-4288-45ac-bc28-768e2455c72b	\N	auth-username-password-form	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	7a956b6f-7c55-495f-a140-8cef7b7efee6	0	10	f	\N	\N
2d3e8332-e8e9-477e-aa2e-1225ae57576a	\N	\N	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	7a956b6f-7c55-495f-a140-8cef7b7efee6	1	20	t	8ab66020-055f-4318-afcb-681cd844e3c3	\N
59e0872a-65fd-4f24-9181-bd63c43cf4c2	\N	conditional-user-configured	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	8ab66020-055f-4318-afcb-681cd844e3c3	0	10	f	\N	\N
31886f40-109a-4ecd-ac0c-ae460910a49d	\N	auth-otp-form	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	8ab66020-055f-4318-afcb-681cd844e3c3	0	20	f	\N	\N
796e75a3-2404-4b13-9970-1c334c21c568	\N	direct-grant-validate-username	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a5766eca-1b36-4d33-9346-435533b7b8dd	0	10	f	\N	\N
cd0354e4-36be-475f-9960-5f14402e8f47	\N	direct-grant-validate-password	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a5766eca-1b36-4d33-9346-435533b7b8dd	0	20	f	\N	\N
3ea506dc-3fc3-4f0a-bac8-244737aac5d0	\N	\N	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a5766eca-1b36-4d33-9346-435533b7b8dd	1	30	t	878f442e-067c-4f1b-ab7e-bebe427dd77a	\N
dbef0b8d-3e96-4e00-9b7c-c2b404607f2d	\N	conditional-user-configured	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	878f442e-067c-4f1b-ab7e-bebe427dd77a	0	10	f	\N	\N
177174de-0b9a-41c2-a7b3-5030cdf3732d	\N	direct-grant-validate-otp	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	878f442e-067c-4f1b-ab7e-bebe427dd77a	0	20	f	\N	\N
1979b71c-d8e7-4a8f-94e3-9e0e64cf70dd	\N	registration-page-form	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	0cfa8ad1-b2ac-457a-8603-8fae929732b5	0	10	t	f2fff35c-16cd-4de0-9a8b-11ae2eb0e436	\N
2ad216b7-87cf-4a02-9eaa-319c44f23149	\N	registration-user-creation	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f2fff35c-16cd-4de0-9a8b-11ae2eb0e436	0	20	f	\N	\N
93716379-a458-41e2-8040-4596f86526fd	\N	registration-password-action	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f2fff35c-16cd-4de0-9a8b-11ae2eb0e436	0	50	f	\N	\N
04e308c0-a834-4507-b525-5a219fd59422	\N	registration-recaptcha-action	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f2fff35c-16cd-4de0-9a8b-11ae2eb0e436	3	60	f	\N	\N
ce46798c-91b3-4ddf-b2da-df3b9ece0737	\N	registration-terms-and-conditions	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f2fff35c-16cd-4de0-9a8b-11ae2eb0e436	3	70	f	\N	\N
b36a51f9-f06a-4c31-baa6-ff6a905b5efb	\N	reset-credentials-choose-user	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	09add935-4514-42c3-86c3-7062f5dc3e39	0	10	f	\N	\N
b76c96d1-e37f-4c85-a9a7-90a7d5055855	\N	reset-credential-email	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	09add935-4514-42c3-86c3-7062f5dc3e39	0	20	f	\N	\N
7b997d8e-d1fa-4caf-a1e8-c5dc3d3566ef	\N	reset-password	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	09add935-4514-42c3-86c3-7062f5dc3e39	0	30	f	\N	\N
b3255f36-9155-47ea-acdc-ac1366bb2e6f	\N	\N	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	09add935-4514-42c3-86c3-7062f5dc3e39	1	40	t	d1f255eb-8ff5-4ec1-a77e-e991a9bc3262	\N
fc9d4c63-6b9e-49f1-8688-c0804a628584	\N	conditional-user-configured	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	d1f255eb-8ff5-4ec1-a77e-e991a9bc3262	0	10	f	\N	\N
d1964e05-51a6-4b5a-a397-ac176f44c2c8	\N	reset-otp	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	d1f255eb-8ff5-4ec1-a77e-e991a9bc3262	0	20	f	\N	\N
0330ccf5-e705-4577-a94f-26771c0a9c9d	\N	client-secret	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	cd82d74c-695c-4256-b7fb-1caf60a35be1	2	10	f	\N	\N
45a01668-a57c-4ed1-8d57-a1d20cce74af	\N	client-jwt	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	cd82d74c-695c-4256-b7fb-1caf60a35be1	2	20	f	\N	\N
04e6f8d1-e878-4ea8-97f8-69bbc0b7f040	\N	client-secret-jwt	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	cd82d74c-695c-4256-b7fb-1caf60a35be1	2	30	f	\N	\N
fab5630d-85e9-4942-b447-13f2284d7619	\N	client-x509	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	cd82d74c-695c-4256-b7fb-1caf60a35be1	2	40	f	\N	\N
987b2e22-f418-4271-8d10-1302c92b46c4	\N	idp-review-profile	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f04e7eb3-e252-4b89-8c1c-abead85e66ad	0	10	f	\N	415ea0e4-7817-4d4b-af4d-5b187eff0d35
ab19f64e-d989-4574-8920-9847645998a9	\N	\N	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f04e7eb3-e252-4b89-8c1c-abead85e66ad	0	20	t	ae17c657-3bc2-4e66-9026-7953d4e25b1b	\N
9f96f625-acf1-49e2-8a92-5339ed887922	\N	idp-create-user-if-unique	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	ae17c657-3bc2-4e66-9026-7953d4e25b1b	2	10	f	\N	e006dd17-3679-41b1-b5fe-461297c4a58c
d3d0b49a-e558-4549-a8b0-58a741fda455	\N	\N	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	ae17c657-3bc2-4e66-9026-7953d4e25b1b	2	20	t	baedb70b-cbfa-4559-a00c-ce2ba1bb00ce	\N
18e0b63c-603a-47a7-99e7-b142b31707a7	\N	idp-confirm-link	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	baedb70b-cbfa-4559-a00c-ce2ba1bb00ce	0	10	f	\N	\N
b2909e5b-7462-4c52-a4ad-c2821cdf9c31	\N	\N	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	baedb70b-cbfa-4559-a00c-ce2ba1bb00ce	0	20	t	bd666b46-5c22-4d8f-8399-007b534204e9	\N
ba60553b-0e57-4d93-bc41-16bc0c2b86a7	\N	idp-email-verification	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	bd666b46-5c22-4d8f-8399-007b534204e9	2	10	f	\N	\N
62c5af8a-a21a-437c-b450-76e48f19c0cc	\N	\N	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	bd666b46-5c22-4d8f-8399-007b534204e9	2	20	t	ec5294da-8d2d-4fbe-80d4-f4c539342d0b	\N
9dd70410-7482-4686-82a4-9398364a8f6b	\N	idp-username-password-form	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	ec5294da-8d2d-4fbe-80d4-f4c539342d0b	0	10	f	\N	\N
1ae73d52-6fce-49e3-a94d-e7c58f67c796	\N	\N	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	ec5294da-8d2d-4fbe-80d4-f4c539342d0b	1	20	t	cda71176-e968-432f-bd25-2a4f0e1b9390	\N
0af7eebe-b22c-4c36-88ce-7a2e9ff63b22	\N	conditional-user-configured	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	cda71176-e968-432f-bd25-2a4f0e1b9390	0	10	f	\N	\N
a2b82658-91ec-48dc-84b6-2ce08034d222	\N	auth-otp-form	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	cda71176-e968-432f-bd25-2a4f0e1b9390	0	20	f	\N	\N
822accbe-0a61-41af-9965-3f6b63b97624	\N	http-basic-authenticator	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	aa4bbf9d-8ae7-45fb-ab4c-afaf175ef4fb	0	10	f	\N	\N
842ee526-50a2-45bc-89c9-c307b0e4f7e6	\N	docker-http-basic-authenticator	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	9130db23-c271-4ae8-8461-d00c9acfb762	0	10	f	\N	\N
\.


--
-- Data for Name: authentication_flow; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authentication_flow (id, alias, description, realm_id, provider_id, top_level, built_in) FROM stdin;
753c8b48-ba3e-42b3-bbf0-38ec8165eba7	browser	Browser based authentication	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	t	t
7a956b6f-7c55-495f-a140-8cef7b7efee6	forms	Username, password, otp and other auth forms.	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	f	t
8ab66020-055f-4318-afcb-681cd844e3c3	Browser - Conditional OTP	Flow to determine if the OTP is required for the authentication	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	f	t
a5766eca-1b36-4d33-9346-435533b7b8dd	direct grant	OpenID Connect Resource Owner Grant	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	t	t
878f442e-067c-4f1b-ab7e-bebe427dd77a	Direct Grant - Conditional OTP	Flow to determine if the OTP is required for the authentication	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	f	t
0cfa8ad1-b2ac-457a-8603-8fae929732b5	registration	Registration flow	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	t	t
f2fff35c-16cd-4de0-9a8b-11ae2eb0e436	registration form	Registration form	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	form-flow	f	t
09add935-4514-42c3-86c3-7062f5dc3e39	reset credentials	Reset credentials for a user if they forgot their password or something	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	t	t
d1f255eb-8ff5-4ec1-a77e-e991a9bc3262	Reset - Conditional OTP	Flow to determine if the OTP should be reset or not. Set to REQUIRED to force.	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	f	t
cd82d74c-695c-4256-b7fb-1caf60a35be1	clients	Base authentication for clients	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	client-flow	t	t
f04e7eb3-e252-4b89-8c1c-abead85e66ad	first broker login	Actions taken after first broker login with identity provider account, which is not yet linked to any Keycloak account	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	t	t
ae17c657-3bc2-4e66-9026-7953d4e25b1b	User creation or linking	Flow for the existing/non-existing user alternatives	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	f	t
baedb70b-cbfa-4559-a00c-ce2ba1bb00ce	Handle Existing Account	Handle what to do if there is existing account with same email/username like authenticated identity provider	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	f	t
bd666b46-5c22-4d8f-8399-007b534204e9	Account verification options	Method with which to verity the existing account	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	f	t
ec5294da-8d2d-4fbe-80d4-f4c539342d0b	Verify Existing Account by Re-authentication	Reauthentication of existing account	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	f	t
cda71176-e968-432f-bd25-2a4f0e1b9390	First broker login - Conditional OTP	Flow to determine if the OTP is required for the authentication	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	f	t
aa4bbf9d-8ae7-45fb-ab4c-afaf175ef4fb	saml ecp	SAML ECP Profile Authentication Flow	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	t	t
9130db23-c271-4ae8-8461-d00c9acfb762	docker auth	Used by Docker clients to authenticate against the IDP	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	basic-flow	t	t
\.


--
-- Data for Name: authenticator_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authenticator_config (id, alias, realm_id) FROM stdin;
415ea0e4-7817-4d4b-af4d-5b187eff0d35	review profile config	23677baa-69b5-4d4f-bed7-eb3c3fa0462b
e006dd17-3679-41b1-b5fe-461297c4a58c	create unique user config	23677baa-69b5-4d4f-bed7-eb3c3fa0462b
\.


--
-- Data for Name: authenticator_config_entry; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.authenticator_config_entry (authenticator_id, value, name) FROM stdin;
415ea0e4-7817-4d4b-af4d-5b187eff0d35	missing	update.profile.on.first.login
e006dd17-3679-41b1-b5fe-461297c4a58c	false	require.password.update.after.registration
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
a84af477-3b7f-47f1-8d44-fe2abade00fd	t	f	master-realm	0	f	\N	\N	t	\N	f	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	\N	0	f	f	master Realm	f	client-secret	\N	\N	\N	t	f	f	f
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	t	f	account	0	t	\N	/realms/master/account/	f	\N	f	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	openid-connect	0	f	f	${client_account}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
9f185538-da56-4880-be80-f0cc3bfcd3cd	t	f	account-console	0	t	\N	/realms/master/account/	f	\N	f	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	openid-connect	0	f	f	${client_account-console}	f	client-secret	${authBaseUrl}	\N	\N	t	f	f	f
523b269e-73c1-4c0a-acaf-f85522220cce	t	f	broker	0	f	\N	\N	t	\N	f	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	openid-connect	0	f	f	${client_broker}	f	client-secret	\N	\N	\N	t	f	f	f
8eeda11d-42fa-4c85-bb09-bd17511aa472	t	t	security-admin-console	0	t	\N	/admin/master/console/	f	\N	f	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	openid-connect	0	f	f	${client_security-admin-console}	f	client-secret	${authAdminUrl}	\N	\N	t	f	f	f
7f2af595-47da-43b5-97c6-2ee9252dd8ba	t	t	admin-cli	0	t	\N	\N	f	\N	f	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	openid-connect	0	f	f	${client_admin-cli}	f	client-secret	\N	\N	\N	f	f	t	f
\.


--
-- Data for Name: client_attributes; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_attributes (client_id, name, value) FROM stdin;
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	post.logout.redirect.uris	+
9f185538-da56-4880-be80-f0cc3bfcd3cd	post.logout.redirect.uris	+
9f185538-da56-4880-be80-f0cc3bfcd3cd	pkce.code.challenge.method	S256
8eeda11d-42fa-4c85-bb09-bd17511aa472	post.logout.redirect.uris	+
8eeda11d-42fa-4c85-bb09-bd17511aa472	pkce.code.challenge.method	S256
8eeda11d-42fa-4c85-bb09-bd17511aa472	client.use.lightweight.access.token.enabled	true
7f2af595-47da-43b5-97c6-2ee9252dd8ba	client.use.lightweight.access.token.enabled	true
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
5eca7de0-b320-446f-a0f5-616d3a92a570	offline_access	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	OpenID Connect built-in scope: offline_access	openid-connect
84bba3ec-a613-462e-94d3-737794a0bfb9	role_list	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	SAML role list	saml
b968713a-289f-4db0-9c57-4a98b1975503	saml_organization	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	Organization Membership	saml
64048be8-99ec-4cb2-811a-ed4507cc8351	profile	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	OpenID Connect built-in scope: profile	openid-connect
59d1664d-08b1-40f5-a823-b81206925755	email	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	OpenID Connect built-in scope: email	openid-connect
d10a623c-f790-479d-9706-a2bd4b8c17e9	address	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	OpenID Connect built-in scope: address	openid-connect
45900d37-a973-4b12-9ef7-bbb3cf0e04ec	phone	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	OpenID Connect built-in scope: phone	openid-connect
e69e3df6-a890-4fe9-a22c-2a822a588c7a	roles	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	OpenID Connect scope for add user roles to the access token	openid-connect
58240b4a-31ab-4f78-9839-506121985e67	web-origins	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	OpenID Connect scope for add allowed web origins to the access token	openid-connect
40061be1-04ff-4613-9339-059166efcc6b	microprofile-jwt	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	Microprofile - JWT built-in scope	openid-connect
7958aa4d-9c75-43aa-84ce-a35a83617363	acr	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	OpenID Connect scope for add acr (authentication context class reference) to the token	openid-connect
a745363a-6438-44be-8fe2-387f167fc70d	basic	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	OpenID Connect scope for add all basic claims to the token	openid-connect
a6dc6bd4-6b74-43d6-8f61-86abd29754d6	organization	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	Additional claims about the organization a subject belongs to	openid-connect
\.


--
-- Data for Name: client_scope_attributes; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_scope_attributes (scope_id, value, name) FROM stdin;
5eca7de0-b320-446f-a0f5-616d3a92a570	true	display.on.consent.screen
5eca7de0-b320-446f-a0f5-616d3a92a570	${offlineAccessScopeConsentText}	consent.screen.text
84bba3ec-a613-462e-94d3-737794a0bfb9	true	display.on.consent.screen
84bba3ec-a613-462e-94d3-737794a0bfb9	${samlRoleListScopeConsentText}	consent.screen.text
b968713a-289f-4db0-9c57-4a98b1975503	false	display.on.consent.screen
64048be8-99ec-4cb2-811a-ed4507cc8351	true	display.on.consent.screen
64048be8-99ec-4cb2-811a-ed4507cc8351	${profileScopeConsentText}	consent.screen.text
64048be8-99ec-4cb2-811a-ed4507cc8351	true	include.in.token.scope
59d1664d-08b1-40f5-a823-b81206925755	true	display.on.consent.screen
59d1664d-08b1-40f5-a823-b81206925755	${emailScopeConsentText}	consent.screen.text
59d1664d-08b1-40f5-a823-b81206925755	true	include.in.token.scope
d10a623c-f790-479d-9706-a2bd4b8c17e9	true	display.on.consent.screen
d10a623c-f790-479d-9706-a2bd4b8c17e9	${addressScopeConsentText}	consent.screen.text
d10a623c-f790-479d-9706-a2bd4b8c17e9	true	include.in.token.scope
45900d37-a973-4b12-9ef7-bbb3cf0e04ec	true	display.on.consent.screen
45900d37-a973-4b12-9ef7-bbb3cf0e04ec	${phoneScopeConsentText}	consent.screen.text
45900d37-a973-4b12-9ef7-bbb3cf0e04ec	true	include.in.token.scope
e69e3df6-a890-4fe9-a22c-2a822a588c7a	true	display.on.consent.screen
e69e3df6-a890-4fe9-a22c-2a822a588c7a	${rolesScopeConsentText}	consent.screen.text
e69e3df6-a890-4fe9-a22c-2a822a588c7a	false	include.in.token.scope
58240b4a-31ab-4f78-9839-506121985e67	false	display.on.consent.screen
58240b4a-31ab-4f78-9839-506121985e67		consent.screen.text
58240b4a-31ab-4f78-9839-506121985e67	false	include.in.token.scope
40061be1-04ff-4613-9339-059166efcc6b	false	display.on.consent.screen
40061be1-04ff-4613-9339-059166efcc6b	true	include.in.token.scope
7958aa4d-9c75-43aa-84ce-a35a83617363	false	display.on.consent.screen
7958aa4d-9c75-43aa-84ce-a35a83617363	false	include.in.token.scope
a745363a-6438-44be-8fe2-387f167fc70d	false	display.on.consent.screen
a745363a-6438-44be-8fe2-387f167fc70d	false	include.in.token.scope
a6dc6bd4-6b74-43d6-8f61-86abd29754d6	true	display.on.consent.screen
a6dc6bd4-6b74-43d6-8f61-86abd29754d6	${organizationScopeConsentText}	consent.screen.text
a6dc6bd4-6b74-43d6-8f61-86abd29754d6	true	include.in.token.scope
\.


--
-- Data for Name: client_scope_client; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_scope_client (client_id, scope_id, default_scope) FROM stdin;
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	58240b4a-31ab-4f78-9839-506121985e67	t
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	7958aa4d-9c75-43aa-84ce-a35a83617363	t
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	a745363a-6438-44be-8fe2-387f167fc70d	t
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	64048be8-99ec-4cb2-811a-ed4507cc8351	t
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	59d1664d-08b1-40f5-a823-b81206925755	t
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	e69e3df6-a890-4fe9-a22c-2a822a588c7a	t
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	a6dc6bd4-6b74-43d6-8f61-86abd29754d6	f
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	45900d37-a973-4b12-9ef7-bbb3cf0e04ec	f
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	40061be1-04ff-4613-9339-059166efcc6b	f
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	d10a623c-f790-479d-9706-a2bd4b8c17e9	f
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	5eca7de0-b320-446f-a0f5-616d3a92a570	f
9f185538-da56-4880-be80-f0cc3bfcd3cd	58240b4a-31ab-4f78-9839-506121985e67	t
9f185538-da56-4880-be80-f0cc3bfcd3cd	7958aa4d-9c75-43aa-84ce-a35a83617363	t
9f185538-da56-4880-be80-f0cc3bfcd3cd	a745363a-6438-44be-8fe2-387f167fc70d	t
9f185538-da56-4880-be80-f0cc3bfcd3cd	64048be8-99ec-4cb2-811a-ed4507cc8351	t
9f185538-da56-4880-be80-f0cc3bfcd3cd	59d1664d-08b1-40f5-a823-b81206925755	t
9f185538-da56-4880-be80-f0cc3bfcd3cd	e69e3df6-a890-4fe9-a22c-2a822a588c7a	t
9f185538-da56-4880-be80-f0cc3bfcd3cd	a6dc6bd4-6b74-43d6-8f61-86abd29754d6	f
9f185538-da56-4880-be80-f0cc3bfcd3cd	45900d37-a973-4b12-9ef7-bbb3cf0e04ec	f
9f185538-da56-4880-be80-f0cc3bfcd3cd	40061be1-04ff-4613-9339-059166efcc6b	f
9f185538-da56-4880-be80-f0cc3bfcd3cd	d10a623c-f790-479d-9706-a2bd4b8c17e9	f
9f185538-da56-4880-be80-f0cc3bfcd3cd	5eca7de0-b320-446f-a0f5-616d3a92a570	f
7f2af595-47da-43b5-97c6-2ee9252dd8ba	58240b4a-31ab-4f78-9839-506121985e67	t
7f2af595-47da-43b5-97c6-2ee9252dd8ba	7958aa4d-9c75-43aa-84ce-a35a83617363	t
7f2af595-47da-43b5-97c6-2ee9252dd8ba	a745363a-6438-44be-8fe2-387f167fc70d	t
7f2af595-47da-43b5-97c6-2ee9252dd8ba	64048be8-99ec-4cb2-811a-ed4507cc8351	t
7f2af595-47da-43b5-97c6-2ee9252dd8ba	59d1664d-08b1-40f5-a823-b81206925755	t
7f2af595-47da-43b5-97c6-2ee9252dd8ba	e69e3df6-a890-4fe9-a22c-2a822a588c7a	t
7f2af595-47da-43b5-97c6-2ee9252dd8ba	a6dc6bd4-6b74-43d6-8f61-86abd29754d6	f
7f2af595-47da-43b5-97c6-2ee9252dd8ba	45900d37-a973-4b12-9ef7-bbb3cf0e04ec	f
7f2af595-47da-43b5-97c6-2ee9252dd8ba	40061be1-04ff-4613-9339-059166efcc6b	f
7f2af595-47da-43b5-97c6-2ee9252dd8ba	d10a623c-f790-479d-9706-a2bd4b8c17e9	f
7f2af595-47da-43b5-97c6-2ee9252dd8ba	5eca7de0-b320-446f-a0f5-616d3a92a570	f
523b269e-73c1-4c0a-acaf-f85522220cce	58240b4a-31ab-4f78-9839-506121985e67	t
523b269e-73c1-4c0a-acaf-f85522220cce	7958aa4d-9c75-43aa-84ce-a35a83617363	t
523b269e-73c1-4c0a-acaf-f85522220cce	a745363a-6438-44be-8fe2-387f167fc70d	t
523b269e-73c1-4c0a-acaf-f85522220cce	64048be8-99ec-4cb2-811a-ed4507cc8351	t
523b269e-73c1-4c0a-acaf-f85522220cce	59d1664d-08b1-40f5-a823-b81206925755	t
523b269e-73c1-4c0a-acaf-f85522220cce	e69e3df6-a890-4fe9-a22c-2a822a588c7a	t
523b269e-73c1-4c0a-acaf-f85522220cce	a6dc6bd4-6b74-43d6-8f61-86abd29754d6	f
523b269e-73c1-4c0a-acaf-f85522220cce	45900d37-a973-4b12-9ef7-bbb3cf0e04ec	f
523b269e-73c1-4c0a-acaf-f85522220cce	40061be1-04ff-4613-9339-059166efcc6b	f
523b269e-73c1-4c0a-acaf-f85522220cce	d10a623c-f790-479d-9706-a2bd4b8c17e9	f
523b269e-73c1-4c0a-acaf-f85522220cce	5eca7de0-b320-446f-a0f5-616d3a92a570	f
a84af477-3b7f-47f1-8d44-fe2abade00fd	58240b4a-31ab-4f78-9839-506121985e67	t
a84af477-3b7f-47f1-8d44-fe2abade00fd	7958aa4d-9c75-43aa-84ce-a35a83617363	t
a84af477-3b7f-47f1-8d44-fe2abade00fd	a745363a-6438-44be-8fe2-387f167fc70d	t
a84af477-3b7f-47f1-8d44-fe2abade00fd	64048be8-99ec-4cb2-811a-ed4507cc8351	t
a84af477-3b7f-47f1-8d44-fe2abade00fd	59d1664d-08b1-40f5-a823-b81206925755	t
a84af477-3b7f-47f1-8d44-fe2abade00fd	e69e3df6-a890-4fe9-a22c-2a822a588c7a	t
a84af477-3b7f-47f1-8d44-fe2abade00fd	a6dc6bd4-6b74-43d6-8f61-86abd29754d6	f
a84af477-3b7f-47f1-8d44-fe2abade00fd	45900d37-a973-4b12-9ef7-bbb3cf0e04ec	f
a84af477-3b7f-47f1-8d44-fe2abade00fd	40061be1-04ff-4613-9339-059166efcc6b	f
a84af477-3b7f-47f1-8d44-fe2abade00fd	d10a623c-f790-479d-9706-a2bd4b8c17e9	f
a84af477-3b7f-47f1-8d44-fe2abade00fd	5eca7de0-b320-446f-a0f5-616d3a92a570	f
8eeda11d-42fa-4c85-bb09-bd17511aa472	58240b4a-31ab-4f78-9839-506121985e67	t
8eeda11d-42fa-4c85-bb09-bd17511aa472	7958aa4d-9c75-43aa-84ce-a35a83617363	t
8eeda11d-42fa-4c85-bb09-bd17511aa472	a745363a-6438-44be-8fe2-387f167fc70d	t
8eeda11d-42fa-4c85-bb09-bd17511aa472	64048be8-99ec-4cb2-811a-ed4507cc8351	t
8eeda11d-42fa-4c85-bb09-bd17511aa472	59d1664d-08b1-40f5-a823-b81206925755	t
8eeda11d-42fa-4c85-bb09-bd17511aa472	e69e3df6-a890-4fe9-a22c-2a822a588c7a	t
8eeda11d-42fa-4c85-bb09-bd17511aa472	a6dc6bd4-6b74-43d6-8f61-86abd29754d6	f
8eeda11d-42fa-4c85-bb09-bd17511aa472	45900d37-a973-4b12-9ef7-bbb3cf0e04ec	f
8eeda11d-42fa-4c85-bb09-bd17511aa472	40061be1-04ff-4613-9339-059166efcc6b	f
8eeda11d-42fa-4c85-bb09-bd17511aa472	d10a623c-f790-479d-9706-a2bd4b8c17e9	f
8eeda11d-42fa-4c85-bb09-bd17511aa472	5eca7de0-b320-446f-a0f5-616d3a92a570	f
\.


--
-- Data for Name: client_scope_role_mapping; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.client_scope_role_mapping (scope_id, role_id) FROM stdin;
5eca7de0-b320-446f-a0f5-616d3a92a570	29b42e5e-2d03-4b56-bf89-f9f05672540f
\.


--
-- Data for Name: component; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.component (id, name, parent_id, provider_id, provider_type, realm_id, sub_type) FROM stdin;
367e9ec0-f878-48bd-b439-ad9c76e03bfb	Trusted Hosts	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	trusted-hosts	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	anonymous
c337767f-ea2c-48cb-96ac-a64e4214d283	Consent Required	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	consent-required	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	anonymous
954a6da2-7c43-4676-ac8a-dec457eba0f8	Full Scope Disabled	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	scope	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	anonymous
d8f54690-2f0d-4052-ba99-73b481bf5030	Max Clients Limit	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	max-clients	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	anonymous
3ac4e5ca-8300-4ac6-bac6-2219ec1a2865	Allowed Protocol Mapper Types	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	anonymous
20c2be1c-f89a-41a5-b01b-700fb4671b35	Allowed Client Scopes	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	anonymous
a878e42d-7383-4f09-9000-a44cc7876044	Allowed Protocol Mapper Types	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	allowed-protocol-mappers	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	authenticated
6ef6e3fa-3944-4bef-8a93-108719b85918	Allowed Client Scopes	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	allowed-client-templates	org.keycloak.services.clientregistration.policy.ClientRegistrationPolicy	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	authenticated
55e330bb-a9bb-4345-a988-62a288316ae0	rsa-generated	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	rsa-generated	org.keycloak.keys.KeyProvider	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	\N
a62cdd75-13cd-4b07-bc3c-f26bba91907c	rsa-enc-generated	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	rsa-enc-generated	org.keycloak.keys.KeyProvider	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	\N
38064f40-7908-4df6-b0d4-e005f4f98e6b	hmac-generated-hs512	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	hmac-generated	org.keycloak.keys.KeyProvider	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	\N
0ac752b2-f15e-4572-b6f0-440c76d208a9	aes-generated	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	aes-generated	org.keycloak.keys.KeyProvider	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	\N
0b10d0db-226a-4f4a-9261-2f6ad3420448	\N	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	declarative-user-profile	org.keycloak.userprofile.UserProfileProvider	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	\N
\.


--
-- Data for Name: component_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.component_config (id, component_id, name, value) FROM stdin;
00bee71d-df90-413e-9a9c-9935d0d1cea8	20c2be1c-f89a-41a5-b01b-700fb4671b35	allow-default-scopes	true
c54846fc-ddac-4120-af5c-c3c3a055ee06	6ef6e3fa-3944-4bef-8a93-108719b85918	allow-default-scopes	true
493ef0f8-e64f-407b-a828-7aeb0cae015f	a878e42d-7383-4f09-9000-a44cc7876044	allowed-protocol-mapper-types	saml-user-attribute-mapper
1f4632c3-ecc0-43f4-b044-cb8c03272e9f	a878e42d-7383-4f09-9000-a44cc7876044	allowed-protocol-mapper-types	saml-user-property-mapper
7953cf8b-b65b-4a5b-b8b6-6446390358e8	a878e42d-7383-4f09-9000-a44cc7876044	allowed-protocol-mapper-types	saml-role-list-mapper
95561d8c-dd4c-4504-b275-fe837643b5aa	a878e42d-7383-4f09-9000-a44cc7876044	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
8aef9590-e78e-4701-8c85-9e776c36e146	a878e42d-7383-4f09-9000-a44cc7876044	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
409353b0-87ce-446f-998b-6354e734a431	a878e42d-7383-4f09-9000-a44cc7876044	allowed-protocol-mapper-types	oidc-address-mapper
313ba161-4d4a-4b23-8e3d-203045bac4b2	a878e42d-7383-4f09-9000-a44cc7876044	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
2007427e-e3bb-44e7-90e2-4ca6bf4b9bdc	a878e42d-7383-4f09-9000-a44cc7876044	allowed-protocol-mapper-types	oidc-full-name-mapper
f335a018-62da-424f-9b94-8b5e0307d044	d8f54690-2f0d-4052-ba99-73b481bf5030	max-clients	200
119da50e-5c86-4f0f-aef0-ce5735f27a1d	3ac4e5ca-8300-4ac6-bac6-2219ec1a2865	allowed-protocol-mapper-types	saml-role-list-mapper
d55ad8cc-86f2-4cf5-859f-2997069d1d96	3ac4e5ca-8300-4ac6-bac6-2219ec1a2865	allowed-protocol-mapper-types	oidc-usermodel-property-mapper
edd5c636-017d-4fe3-92bb-8a6977b3a647	3ac4e5ca-8300-4ac6-bac6-2219ec1a2865	allowed-protocol-mapper-types	saml-user-attribute-mapper
bb2fc0fa-25f0-4603-98cf-fb2981d47137	3ac4e5ca-8300-4ac6-bac6-2219ec1a2865	allowed-protocol-mapper-types	saml-user-property-mapper
3fd82c83-3730-493c-b9ad-0f70c2297822	3ac4e5ca-8300-4ac6-bac6-2219ec1a2865	allowed-protocol-mapper-types	oidc-usermodel-attribute-mapper
ec06136b-6fdd-49e5-85ec-ed4114bef22f	3ac4e5ca-8300-4ac6-bac6-2219ec1a2865	allowed-protocol-mapper-types	oidc-full-name-mapper
93ecc241-170c-4148-85b4-d83e52e71204	3ac4e5ca-8300-4ac6-bac6-2219ec1a2865	allowed-protocol-mapper-types	oidc-sha256-pairwise-sub-mapper
96858c11-aed3-4047-a577-0a15163b79c6	3ac4e5ca-8300-4ac6-bac6-2219ec1a2865	allowed-protocol-mapper-types	oidc-address-mapper
1dd79113-6196-49dd-8d4f-877cf9f94b5b	367e9ec0-f878-48bd-b439-ad9c76e03bfb	host-sending-registration-request-must-match	true
90c41b1c-99b8-4d1d-b890-1e174d71813f	367e9ec0-f878-48bd-b439-ad9c76e03bfb	client-uris-must-match	true
f5bdc548-c189-4107-a0b5-9f544ecb47a7	0b10d0db-226a-4f4a-9261-2f6ad3420448	kc.user.profile.config	{"attributes":[{"name":"username","displayName":"${username}","validations":{"length":{"min":3,"max":255},"username-prohibited-characters":{},"up-username-not-idn-homograph":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"email","displayName":"${email}","validations":{"email":{},"length":{"max":255}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"firstName","displayName":"${firstName}","validations":{"length":{"max":255},"person-name-prohibited-characters":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false},{"name":"lastName","displayName":"${lastName}","validations":{"length":{"max":255},"person-name-prohibited-characters":{}},"permissions":{"view":["admin","user"],"edit":["admin","user"]},"multivalued":false}],"groups":[{"name":"user-metadata","displayHeader":"User metadata","displayDescription":"Attributes, which refer to user metadata"}]}
ee83bdf5-600b-4a4d-89d0-f8f7906e987a	38064f40-7908-4df6-b0d4-e005f4f98e6b	algorithm	HS512
9edb85cf-361f-4721-a004-28571e1dec7f	38064f40-7908-4df6-b0d4-e005f4f98e6b	priority	100
440a8384-3322-410b-a329-e9b7a231a86e	38064f40-7908-4df6-b0d4-e005f4f98e6b	secret	4FpowBcnCYJ_lgeV4jAd5fkDcEQ9B8mqk8jplh7e-dCFB3PE3h6qq20lInyMltjxodjd32phPKoXkV-HAdPB8Ys28fCEmitFvm_VFIF__C4FXyEbxtknKH-WQYSZgmBKhi3sPLC0l5WQq11y7R3-Q10pjsmaM2zC969QA4CqKmQ
0570387d-3ad9-4348-a09e-642f76d80065	38064f40-7908-4df6-b0d4-e005f4f98e6b	kid	c1a3d832-6292-4745-83ab-defc89c6ed89
56abe21c-5895-49fb-a950-d2b418043a67	55e330bb-a9bb-4345-a988-62a288316ae0	privateKey	MIIEpAIBAAKCAQEAsZYyvTNFsq4Wz9LLm4C5nD8rwslbqULlQDTbD/XSMd4/WFod74ec2IAyBERstn1hK6wcPlmtPv+ckt+PrwaqmrJVhdNoF1PxtGvkY0dNhZEb9X8mnHDRH4BwXEMXqGEZoyM2i12yDGgx5xtCMzC0NdX8gVp1oIY1Z6eICtGCBopQENTrcMMP8qycJvcOUOgwNBLn7oYYbNxkdfkMW2Zqvr/nVIj4cZTBro3XfsQrUn9eEf7ZvUovqT9j43oDwA4Fu/cI3HCsR2BQdaZKWwvKZkPrh6rOks5LmdHZH/B/IGLxG8aYibagoLm8vf8sJTfuKQoUN32zsxrd0dTk7z5NPwIDAQABAoIBAAO2st/vAw2eA18cNuLNGdSRI56irFaJThb+I/ic+OTPE38K/2ZDbT2/zIv3TFiynYYWxs5s/Kj9Gm6d2zjbSFUM1qoCRkJ6oGrX+MI0uwPIiY+kNY/+8fiZLIBZbNS6Fxmowlq+vlrxEN4W3VcJtTMNAwdHd39AXzyILdmgEPqG//zo2L/8f0NhTJrkqyAgUQtk4WqzP1/B5MeI9gyRVtFWo4HXkS+CvTZZ6xSASFCUMCBQJWMyfmdj4rblvmv1wwdus1oKj75wdytvKfW4J9tPtA5YBCHs1Oc2N9lkBV5Ept/uGok7isX8xPZv3XZFxAQD99/VrDoo1HXt/k/YAGkCgYEA5k2WrC3MIiG2/Y38PrbvRYrUAZgfuuRHuQMZguUZBkrnB5Iy9PebBCjB/ZSsgYTHHIMW6/U4Lj6OCSou3qSmfHdUGlY/dJlBlF/UcLk5V5ayMHXKvykno21St9Pvnb76ZCcoQjbiJpHAfI7Ab6qfjE60jJx7Y2Vl6HREga0qSlcCgYEAxWbPuqtt3p2UZX8Oy1fzp5Ai0p2heLYDzgTOndEys85zMavoLI5xhsZ+58COf4VWPqmo8SxOmdyVIQuVbdMssssVqb04E1lG3/fe2h7zrvbgxN4eu9Vv9WeYDjlBRpGA/mrE6pMSfbIJOxpDVOBa35sDpDi/Pk8wx6VlAbBqE1kCgYEAvOLP/CGHtkq+odDtHKa/MeM+JJyXEZqfNJ/VMaUi1CVkvLHs9zuJOW0QRiHFJmk3QSxHBXnW/R6zMdMWI+cY4SahpQQuATydK8s5Gsbfv/XJ6bMCeayQZRqJ6Fl7wn5bMP9opn4LF6UdIUuXiz5+ov3+WQESth/46CFL+quC7vsCgYAVdsZjj7xUlmHKX3RmW9vcfmmq6QTjsTbcsd0df9+5eVtBLWYDYkGp2+90l5Vm/EKScbQy9RRe2oNEopPO60VS844ZdL+iKvTv6W3L3c21Wpa6EUTaO6z+zBLWiRc16yBRiZ57ux4pmLA0QvjLQ9Hdumc89galacruCmffNSVzkQKBgQDABNIIeCGHEuU0cBuhkLZNiEcEKjnqxJa3YeNxraexnfbGSdfgvzfXFBaAs9HCU1I69Y300EQ5EEVa1rk4mGbOOGZfydSrhJk2ZIQDH6guWQa0ojTDCylG2jZ123ievs14SMBnYkVcdIUeiGAL80plYyziBA03q5XxCZPNB9ziKA==
16e326ab-7c23-48e5-b2f2-3f588246f767	55e330bb-a9bb-4345-a988-62a288316ae0	keyUse	SIG
65bf744f-1620-4682-a603-27d44c432cf4	55e330bb-a9bb-4345-a988-62a288316ae0	priority	100
1d204aa0-10f1-48a9-b3a0-1e0c19860da6	55e330bb-a9bb-4345-a988-62a288316ae0	certificate	MIICmzCCAYMCBgGaaBwjOzANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjUxMTA5MTAxMjQ1WhcNMzUxMTA5MTAxNDI1WjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCxljK9M0WyrhbP0subgLmcPyvCyVupQuVANNsP9dIx3j9YWh3vh5zYgDIERGy2fWErrBw+Wa0+/5yS34+vBqqaslWF02gXU/G0a+RjR02FkRv1fyaccNEfgHBcQxeoYRmjIzaLXbIMaDHnG0IzMLQ11fyBWnWghjVnp4gK0YIGilAQ1Otwww/yrJwm9w5Q6DA0Eufuhhhs3GR1+QxbZmq+v+dUiPhxlMGujdd+xCtSf14R/tm9Si+pP2PjegPADgW79wjccKxHYFB1pkpbC8pmQ+uHqs6SzkuZ0dkf8H8gYvEbxpiJtqCguby9/ywlN+4pChQ3fbOzGt3R1OTvPk0/AgMBAAEwDQYJKoZIhvcNAQELBQADggEBAJwOj3qfyI3GCF2/V1vpjHQ/bM9P8ovk7HvXW78u1FtGAdiSShyPpj0HlP2B652wB+SWnbmut/YjaRsAJWpsYI8l8UJ7Ubl/ExTZ1M/PAtBZbeUJ6SRym1HQLaUVj3wEXFa65g7WM0+jw950sW02FuzYqHbX/Zbn6LS5VQapmcmEt7SZcaD7TOVB3CnpV9GJeqg6Psbfq02RKG+WvTWqkGoQwG6JvlH3WNW/1PKjw3kbOpO6nThvBdSprEfEqhVyznzIXpDcEgxPTS3IX9GFT35+eYtgcVgBGvMy+GzfBiedssrntp2JAemPgBkc1zJphDhWK8i9ZVmTwpVYLl39n10=
5fa42f94-1d59-45a6-a0ad-536637306b2f	a62cdd75-13cd-4b07-bc3c-f26bba91907c	certificate	MIICmzCCAYMCBgGaaBwpqTANBgkqhkiG9w0BAQsFADARMQ8wDQYDVQQDDAZtYXN0ZXIwHhcNMjUxMTA5MTAxMjQ3WhcNMzUxMTA5MTAxNDI3WjARMQ8wDQYDVQQDDAZtYXN0ZXIwggEiMA0GCSqGSIb3DQEBAQUAA4IBDwAwggEKAoIBAQCwE0BlJ/+6iustvZszoxH+6BFWvDoA52wSFIK+663rqvXYOl8cJCpUqBhxUc6odvaqt2ncK6zKtDeM9UdfZPNW9KPQmlYEM6FVRseNiAWffcH7TdS1l3y0amxbz1cSQB3LwaQKgr+5jwn6p33U+UDcsNPu5OCWqEIpG4KCJRVZkxFTGrC3kg/K6j+8EiQLcLJI3oPFHEDZfJp4xqKsbfBtsUNtIbrCF5N57SRmJeG+hExF/nYfW0KtukSMo8yzSFJmz9DghRnKubq+bs8W3a0LKI35VgRrhENkBocus0vZ+RsWzyFlsxnUpfuDzILn9r/SBBZ/xVeECyBbQ9IUrMWRAgMBAAEwDQYJKoZIhvcNAQELBQADggEBAHX/HqPXzx2vznruepItTrMNISe2plHyqTZHt0azM7agb+ZY/rUri/8Q5AyQQyJQJIgi305lRtPdbqOjMqA61H4L7nrMlUwHUFq1UiaX/tpoA4eUfcVUJY4nN1AiLd2XHKDYo9+hUAxQ1RTm0pijwKcWZwPRobyAcSaKiFmG17A+W06UyVtV3cvVWBHyQ3TcLCjWkcOiNakNn8AJCKMXs92H8HKxGgk3X3yhS/A81Wa6ExpvO58ZTtuNXJo99vCRrhyquglYUjUKw6tePQm7VCf0cJjrLN7ZV3M20I3LCD3y6wxA4MD6k7NFCYgolgUUc6GGISXxLBpID3/iyclUACU=
5867d4e9-91c7-4344-b1d2-f46933e5c186	a62cdd75-13cd-4b07-bc3c-f26bba91907c	privateKey	MIIEowIBAAKCAQEAsBNAZSf/uorrLb2bM6MR/ugRVrw6AOdsEhSCvuut66r12DpfHCQqVKgYcVHOqHb2qrdp3CusyrQ3jPVHX2TzVvSj0JpWBDOhVUbHjYgFn33B+03UtZd8tGpsW89XEkAdy8GkCoK/uY8J+qd91PlA3LDT7uTglqhCKRuCgiUVWZMRUxqwt5IPyuo/vBIkC3CySN6DxRxA2XyaeMairG3wbbFDbSG6wheTee0kZiXhvoRMRf52H1tCrbpEjKPMs0hSZs/Q4IUZyrm6vm7PFt2tCyiN+VYEa4RDZAaHLrNL2fkbFs8hZbMZ1KX7g8yC5/a/0gQWf8VXhAsgW0PSFKzFkQIDAQABAoIBAAViZC8xb55woZdC0C/eYHv3vbaRZ51kiJdlF6yZdMiyHbXYt/l/FKAqCn4qol9N9gG5YhCrFfUWNIkakYAjwzEXAifaFF/Nu6vgmQtlZ2wLaX23rMN+aBu+JBxdDkWfERoxHul5g73pYI3Hz6YFBI6URd5IpUcgb05dAWQmPTsmNLnbv3J1uAb3xMBHsdg3kT6IszjWL5QrlVhX/OVpOqGH6oTIShH9/eLIDpfaUKgbMTJV4TuQZCBH79fWRqQewqgTfECtNwPrycE7qBXyaZSBkK8UxOb94gMpHtuVQj0HCrldTklBKRB+LFljsI21xOc3c4dVi/tg+FW5qD6xu60CgYEA1zndFVj8JfOhxTSWE7IG2P09zFkPJbv2MmalFFSJg/Vw1O/2YAUG1yrU+qG/9+7i1W+eqmPnpj73eeXPOXJ3ondEpOnvF53pPcsDafgIaaV5kF6RJvJdyQ527bqIoVI7lt0iD/cPTtIB1iLqGAIf9OJBXSex+ruc0Awr0sl7sLUCgYEA0W6hz15hxU++NzbibytVj8v74R6CCH9pImdfJ32sRxLA5uk94IH/PMc2K47S6Kq6sdzD15whhRbkY5ZOGPwo3lNMOmGnDPcYCQRf03H45zet73KzrWpE2NPCBq4ThdZnk4LTKjShhFC3xh+u3ne0yCMkug5GURc7okTP1qDUNu0CgYEA1mN7SIKFWUCgyhIh1KinjYpk/qNQJWRgT8BTfDbhbKMWq+YEY41eTQMLeEWbNdut9LHlORGfdMiQN8qlazIM3bc0l5m3Yq0X40fQtMQvjR0aRKcOq98NcPOJoDEJXLjPRu4ruf9+LiOfVLEewp+LOaIPPj4GWh58i/kzxhqRrWkCgYBV0mgsehjGqm7gYXVY6QEe9BA7qCzRHqUOnB26KR234k96UIoyvhLB7UCdiOGUxqHK0RTP/gMebssYpDdMtchpkujh47J49ai7yTcbhtDgr0hvnylSHwIneF9CLYOCjOHU6P9vhDHL7ufgIIugTp2ehgg+1iqqgELHtDsXUMCl4QKBgFWqTFl8OhDQfr42qjcr1W2HktxZnYhgvMA03d4n3VNNzx2UDUGsrAYPn+4oK3+ZuJMXtCovX6hCqclQLiWjnPimT9Mw2307ATjub0hnhqr3wNd0zn3wW8XFsNSC/WcIufKTMtYcwFTYDdJhhxta4mtHkSwaSeJKYKIvw3Xz+AaZ
87512fab-0453-4c2b-922f-6afb0f105a54	a62cdd75-13cd-4b07-bc3c-f26bba91907c	priority	100
a87f3bed-e90a-41ea-a7e1-85edcbeb12cf	a62cdd75-13cd-4b07-bc3c-f26bba91907c	keyUse	ENC
8538155c-2fdc-4189-bece-1d88ff5dd58b	a62cdd75-13cd-4b07-bc3c-f26bba91907c	algorithm	RSA-OAEP
c565e923-b623-489e-ac7c-51327f9ee576	0ac752b2-f15e-4572-b6f0-440c76d208a9	kid	d57f2591-aa0b-4bd9-abed-046c709a6626
be0d974b-d45a-456d-ac80-3e445eea2022	0ac752b2-f15e-4572-b6f0-440c76d208a9	secret	b4LWymEeh2-bRQX6Eu4i9A
3a895e55-ccde-428e-bdbb-e1c51b1f1880	0ac752b2-f15e-4572-b6f0-440c76d208a9	priority	100
\.


--
-- Data for Name: composite_role; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.composite_role (composite, child_role) FROM stdin;
db4ab77a-a58c-482b-b2ae-2090a009b1af	037ffc7d-8518-41c3-874b-261790225b53
db4ab77a-a58c-482b-b2ae-2090a009b1af	550d4a3f-8e8f-46a6-8da3-b43eae24864e
db4ab77a-a58c-482b-b2ae-2090a009b1af	3f50f97a-10a4-4647-b3b5-cebae15d0512
db4ab77a-a58c-482b-b2ae-2090a009b1af	2c077dbb-72e0-4907-bd74-629d431ae93d
db4ab77a-a58c-482b-b2ae-2090a009b1af	71926297-827f-4ca9-8c9c-43d6cacd995a
db4ab77a-a58c-482b-b2ae-2090a009b1af	99b665f4-ff4a-4241-a5b3-d4788d0178cb
db4ab77a-a58c-482b-b2ae-2090a009b1af	18da744f-9a5c-4821-8ced-8474d5282683
db4ab77a-a58c-482b-b2ae-2090a009b1af	1b70f161-90e2-49ed-bf13-ade8c4715596
db4ab77a-a58c-482b-b2ae-2090a009b1af	a4ac462f-fe7f-4a74-9a9c-2c1b350c5da3
db4ab77a-a58c-482b-b2ae-2090a009b1af	73980a57-8c0b-4a33-a5c0-98b0a93f9f29
db4ab77a-a58c-482b-b2ae-2090a009b1af	6c80f665-6ac1-48d3-9eb9-d3644040a076
db4ab77a-a58c-482b-b2ae-2090a009b1af	f79eeeed-8fd6-43f9-afa0-1a80df983009
db4ab77a-a58c-482b-b2ae-2090a009b1af	503e03a0-850d-4786-9d7f-cf8afe62b886
db4ab77a-a58c-482b-b2ae-2090a009b1af	6a6506b4-0b6b-49bb-ba1f-e37841ea2b1b
db4ab77a-a58c-482b-b2ae-2090a009b1af	8b46f4fa-094c-456c-a169-fa8f7f9a2b3d
db4ab77a-a58c-482b-b2ae-2090a009b1af	9e7333c6-f729-44a3-9d74-7822e90f5354
db4ab77a-a58c-482b-b2ae-2090a009b1af	07619e0f-f662-41bb-b97c-1b032ac91fc9
db4ab77a-a58c-482b-b2ae-2090a009b1af	0a9e911e-50b7-4715-b822-d25c7d7fb66b
2c077dbb-72e0-4907-bd74-629d431ae93d	8b46f4fa-094c-456c-a169-fa8f7f9a2b3d
2c077dbb-72e0-4907-bd74-629d431ae93d	0a9e911e-50b7-4715-b822-d25c7d7fb66b
71926297-827f-4ca9-8c9c-43d6cacd995a	9e7333c6-f729-44a3-9d74-7822e90f5354
821e4bb0-9d76-4771-bc20-8e9083ac457d	8e8121ee-2ad2-4770-9178-f72c8c95a10d
821e4bb0-9d76-4771-bc20-8e9083ac457d	1951d5d4-dc58-41c4-846b-6e76438d3543
1951d5d4-dc58-41c4-846b-6e76438d3543	b421a7f6-bb64-4606-83b6-1b871d925102
ad6b0545-1125-494a-86a2-1c4993c199e8	47982905-f5d3-49be-b956-d35a2f9c6a72
db4ab77a-a58c-482b-b2ae-2090a009b1af	dca72013-fbc3-49f3-8a89-d70506667833
821e4bb0-9d76-4771-bc20-8e9083ac457d	29b42e5e-2d03-4b56-bf89-f9f05672540f
821e4bb0-9d76-4771-bc20-8e9083ac457d	9e63cdf7-de90-4650-9cba-c4b8231d41d1
\.


--
-- Data for Name: credential; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.credential (id, salt, type, user_id, created_date, user_label, secret_data, credential_data, priority) FROM stdin;
e1fc3f13-1481-461e-b5f3-973924561353	\N	password	215946ae-82c4-499d-ab37-82f2e3f28b48	1762683268555	\N	{"value":"nPaSRG0lCJeLA/h5qUHA2WgofEmsDuqZasoFcg4VCgg=","salt":"/7FFThyiSrgYzNiEpfZGDA==","additionalParameters":{}}	{"hashIterations":5,"algorithm":"argon2","additionalParameters":{"hashLength":["32"],"memory":["7168"],"type":["id"],"version":["1.3"],"parallelism":["1"]}}	10
\.


--
-- Data for Name: databasechangelog; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.databasechangelog (id, author, filename, dateexecuted, orderexecuted, exectype, md5sum, description, comments, tag, liquibase, contexts, labels, deployment_id) FROM stdin;
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/jpa-changelog-1.0.0.Final.xml	2025-11-09 10:13:54.678575	1	EXECUTED	9:6f1016664e21e16d26517a4418f5e3df	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.29.1	\N	\N	2683232622
1.0.0.Final-KEYCLOAK-5461	sthorger@redhat.com	META-INF/db2-jpa-changelog-1.0.0.Final.xml	2025-11-09 10:13:54.783449	2	MARK_RAN	9:828775b1596a07d1200ba1d49e5e3941	createTable tableName=APPLICATION_DEFAULT_ROLES; createTable tableName=CLIENT; createTable tableName=CLIENT_SESSION; createTable tableName=CLIENT_SESSION_ROLE; createTable tableName=COMPOSITE_ROLE; createTable tableName=CREDENTIAL; createTable tab...		\N	4.29.1	\N	\N	2683232622
1.1.0.Beta1	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Beta1.xml	2025-11-09 10:13:55.015146	3	EXECUTED	9:5f090e44a7d595883c1fb61f4b41fd38	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=CLIENT_ATTRIBUTES; createTable tableName=CLIENT_SESSION_NOTE; createTable tableName=APP_NODE_REGISTRATIONS; addColumn table...		\N	4.29.1	\N	\N	2683232622
1.1.0.Final	sthorger@redhat.com	META-INF/jpa-changelog-1.1.0.Final.xml	2025-11-09 10:13:55.042186	4	EXECUTED	9:c07e577387a3d2c04d1adc9aaad8730e	renameColumn newColumnName=EVENT_TIME, oldColumnName=TIME, tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	2683232622
1.2.0.Beta1	psilva@redhat.com	META-INF/jpa-changelog-1.2.0.Beta1.xml	2025-11-09 10:13:55.49523	5	EXECUTED	9:b68ce996c655922dbcd2fe6b6ae72686	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.29.1	\N	\N	2683232622
1.2.0.Beta1	psilva@redhat.com	META-INF/db2-jpa-changelog-1.2.0.Beta1.xml	2025-11-09 10:13:55.54129	6	MARK_RAN	9:543b5c9989f024fe35c6f6c5a97de88e	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION; createTable tableName=PROTOCOL_MAPPER; createTable tableName=PROTOCOL_MAPPER_CONFIG; createTable tableName=...		\N	4.29.1	\N	\N	2683232622
1.2.0.RC1	bburke@redhat.com	META-INF/jpa-changelog-1.2.0.CR1.xml	2025-11-09 10:13:55.962255	7	EXECUTED	9:765afebbe21cf5bbca048e632df38336	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.29.1	\N	\N	2683232622
1.2.0.RC1	bburke@redhat.com	META-INF/db2-jpa-changelog-1.2.0.CR1.xml	2025-11-09 10:13:55.998122	8	MARK_RAN	9:db4a145ba11a6fdaefb397f6dbf829a1	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=MIGRATION_MODEL; createTable tableName=IDENTITY_P...		\N	4.29.1	\N	\N	2683232622
1.2.0.Final	keycloak	META-INF/jpa-changelog-1.2.0.Final.xml	2025-11-09 10:13:56.033586	9	EXECUTED	9:9d05c7be10cdb873f8bcb41bc3a8ab23	update tableName=CLIENT; update tableName=CLIENT; update tableName=CLIENT		\N	4.29.1	\N	\N	2683232622
1.3.0	bburke@redhat.com	META-INF/jpa-changelog-1.3.0.xml	2025-11-09 10:13:56.496877	10	EXECUTED	9:18593702353128d53111f9b1ff0b82b8	delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete tableName=USER_SESSION; createTable tableName=ADMI...		\N	4.29.1	\N	\N	2683232622
1.4.0	bburke@redhat.com	META-INF/jpa-changelog-1.4.0.xml	2025-11-09 10:13:56.739859	11	EXECUTED	9:6122efe5f090e41a85c0f1c9e52cbb62	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	2683232622
1.4.0	bburke@redhat.com	META-INF/db2-jpa-changelog-1.4.0.xml	2025-11-09 10:13:56.769819	12	MARK_RAN	9:e1ff28bf7568451453f844c5d54bb0b5	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	2683232622
1.5.0	bburke@redhat.com	META-INF/jpa-changelog-1.5.0.xml	2025-11-09 10:13:56.887723	13	EXECUTED	9:7af32cd8957fbc069f796b61217483fd	delete tableName=CLIENT_SESSION_AUTH_STATUS; delete tableName=CLIENT_SESSION_ROLE; delete tableName=CLIENT_SESSION_PROT_MAPPER; delete tableName=CLIENT_SESSION_NOTE; delete tableName=CLIENT_SESSION; delete tableName=USER_SESSION_NOTE; delete table...		\N	4.29.1	\N	\N	2683232622
1.6.1_from15	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-11-09 10:13:56.953005	14	EXECUTED	9:6005e15e84714cd83226bf7879f54190	addColumn tableName=REALM; addColumn tableName=KEYCLOAK_ROLE; addColumn tableName=CLIENT; createTable tableName=OFFLINE_USER_SESSION; createTable tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_US_SES_PK2, tableName=...		\N	4.29.1	\N	\N	2683232622
1.6.1_from16-pre	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-11-09 10:13:56.960855	15	MARK_RAN	9:bf656f5a2b055d07f314431cae76f06c	delete tableName=OFFLINE_CLIENT_SESSION; delete tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2683232622
1.6.1_from16	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-11-09 10:13:56.976246	16	MARK_RAN	9:f8dadc9284440469dcf71e25ca6ab99b	dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_US_SES_PK, tableName=OFFLINE_USER_SESSION; dropPrimaryKey constraintName=CONSTRAINT_OFFLINE_CL_SES_PK, tableName=OFFLINE_CLIENT_SESSION; addColumn tableName=OFFLINE_USER_SESSION; update tableName=OF...		\N	4.29.1	\N	\N	2683232622
1.6.1	mposolda@redhat.com	META-INF/jpa-changelog-1.6.1.xml	2025-11-09 10:13:56.991514	17	EXECUTED	9:d41d8cd98f00b204e9800998ecf8427e	empty		\N	4.29.1	\N	\N	2683232622
1.7.0	bburke@redhat.com	META-INF/jpa-changelog-1.7.0.xml	2025-11-09 10:13:57.164351	18	EXECUTED	9:3368ff0be4c2855ee2dd9ca813b38d8e	createTable tableName=KEYCLOAK_GROUP; createTable tableName=GROUP_ROLE_MAPPING; createTable tableName=GROUP_ATTRIBUTE; createTable tableName=USER_GROUP_MEMBERSHIP; createTable tableName=REALM_DEFAULT_GROUPS; addColumn tableName=IDENTITY_PROVIDER; ...		\N	4.29.1	\N	\N	2683232622
1.8.0	mposolda@redhat.com	META-INF/jpa-changelog-1.8.0.xml	2025-11-09 10:13:57.330373	19	EXECUTED	9:8ac2fb5dd030b24c0570a763ed75ed20	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.29.1	\N	\N	2683232622
1.8.0-2	keycloak	META-INF/jpa-changelog-1.8.0.xml	2025-11-09 10:13:57.350743	20	EXECUTED	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.29.1	\N	\N	2683232622
26.0.0-33201-org-redirect-url	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-09 10:14:16.446824	144	EXECUTED	9:4d0e22b0ac68ebe9794fa9cb752ea660	addColumn tableName=ORG		\N	4.29.1	\N	\N	2683232622
1.8.0	mposolda@redhat.com	META-INF/db2-jpa-changelog-1.8.0.xml	2025-11-09 10:13:57.363968	21	MARK_RAN	9:831e82914316dc8a57dc09d755f23c51	addColumn tableName=IDENTITY_PROVIDER; createTable tableName=CLIENT_TEMPLATE; createTable tableName=CLIENT_TEMPLATE_ATTRIBUTES; createTable tableName=TEMPLATE_SCOPE_MAPPING; dropNotNullConstraint columnName=CLIENT_ID, tableName=PROTOCOL_MAPPER; ad...		\N	4.29.1	\N	\N	2683232622
1.8.0-2	keycloak	META-INF/db2-jpa-changelog-1.8.0.xml	2025-11-09 10:13:57.374637	22	MARK_RAN	9:f91ddca9b19743db60e3057679810e6c	dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; update tableName=CREDENTIAL		\N	4.29.1	\N	\N	2683232622
1.9.0	mposolda@redhat.com	META-INF/jpa-changelog-1.9.0.xml	2025-11-09 10:13:57.760646	23	EXECUTED	9:bc3d0f9e823a69dc21e23e94c7a94bb1	update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=REALM; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=REALM; update tableName=REALM; customChange; dr...		\N	4.29.1	\N	\N	2683232622
1.9.1	keycloak	META-INF/jpa-changelog-1.9.1.xml	2025-11-09 10:13:57.783009	24	EXECUTED	9:c9999da42f543575ab790e76439a2679	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=PUBLIC_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.29.1	\N	\N	2683232622
1.9.1	keycloak	META-INF/db2-jpa-changelog-1.9.1.xml	2025-11-09 10:13:57.788642	25	MARK_RAN	9:0d6c65c6f58732d81569e77b10ba301d	modifyDataType columnName=PRIVATE_KEY, tableName=REALM; modifyDataType columnName=CERTIFICATE, tableName=REALM		\N	4.29.1	\N	\N	2683232622
1.9.2	keycloak	META-INF/jpa-changelog-1.9.2.xml	2025-11-09 10:13:59.782933	26	EXECUTED	9:fc576660fc016ae53d2d4778d84d86d0	createIndex indexName=IDX_USER_EMAIL, tableName=USER_ENTITY; createIndex indexName=IDX_USER_ROLE_MAPPING, tableName=USER_ROLE_MAPPING; createIndex indexName=IDX_USER_GROUP_MAPPING, tableName=USER_GROUP_MEMBERSHIP; createIndex indexName=IDX_USER_CO...		\N	4.29.1	\N	\N	2683232622
authz-2.0.0	psilva@redhat.com	META-INF/jpa-changelog-authz-2.0.0.xml	2025-11-09 10:14:00.014645	27	EXECUTED	9:43ed6b0da89ff77206289e87eaa9c024	createTable tableName=RESOURCE_SERVER; addPrimaryKey constraintName=CONSTRAINT_FARS, tableName=RESOURCE_SERVER; addUniqueConstraint constraintName=UK_AU8TT6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER; createTable tableName=RESOURCE_SERVER_RESOU...		\N	4.29.1	\N	\N	2683232622
authz-2.5.1	psilva@redhat.com	META-INF/jpa-changelog-authz-2.5.1.xml	2025-11-09 10:14:00.040881	28	EXECUTED	9:44bae577f551b3738740281eceb4ea70	update tableName=RESOURCE_SERVER_POLICY		\N	4.29.1	\N	\N	2683232622
2.1.0-KEYCLOAK-5461	bburke@redhat.com	META-INF/jpa-changelog-2.1.0.xml	2025-11-09 10:14:00.233571	29	EXECUTED	9:bd88e1f833df0420b01e114533aee5e8	createTable tableName=BROKER_LINK; createTable tableName=FED_USER_ATTRIBUTE; createTable tableName=FED_USER_CONSENT; createTable tableName=FED_USER_CONSENT_ROLE; createTable tableName=FED_USER_CONSENT_PROT_MAPPER; createTable tableName=FED_USER_CR...		\N	4.29.1	\N	\N	2683232622
2.2.0	bburke@redhat.com	META-INF/jpa-changelog-2.2.0.xml	2025-11-09 10:14:00.285881	30	EXECUTED	9:a7022af5267f019d020edfe316ef4371	addColumn tableName=ADMIN_EVENT_ENTITY; createTable tableName=CREDENTIAL_ATTRIBUTE; createTable tableName=FED_CREDENTIAL_ATTRIBUTE; modifyDataType columnName=VALUE, tableName=CREDENTIAL; addForeignKeyConstraint baseTableName=FED_CREDENTIAL_ATTRIBU...		\N	4.29.1	\N	\N	2683232622
2.3.0	bburke@redhat.com	META-INF/jpa-changelog-2.3.0.xml	2025-11-09 10:14:00.363629	31	EXECUTED	9:fc155c394040654d6a79227e56f5e25a	createTable tableName=FEDERATED_USER; addPrimaryKey constraintName=CONSTR_FEDERATED_USER, tableName=FEDERATED_USER; dropDefaultValue columnName=TOTP, tableName=USER_ENTITY; dropColumn columnName=TOTP, tableName=USER_ENTITY; addColumn tableName=IDE...		\N	4.29.1	\N	\N	2683232622
2.4.0	bburke@redhat.com	META-INF/jpa-changelog-2.4.0.xml	2025-11-09 10:14:00.383505	32	EXECUTED	9:eac4ffb2a14795e5dc7b426063e54d88	customChange		\N	4.29.1	\N	\N	2683232622
2.5.0	bburke@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-11-09 10:14:00.402372	33	EXECUTED	9:54937c05672568c4c64fc9524c1e9462	customChange; modifyDataType columnName=USER_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2683232622
2.5.0-unicode-oracle	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-11-09 10:14:00.410924	34	MARK_RAN	9:3a32bace77c84d7678d035a7f5a8084e	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.29.1	\N	\N	2683232622
2.5.0-unicode-other-dbs	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-11-09 10:14:00.497293	35	EXECUTED	9:33d72168746f81f98ae3a1e8e0ca3554	modifyDataType columnName=DESCRIPTION, tableName=AUTHENTICATION_FLOW; modifyDataType columnName=DESCRIPTION, tableName=CLIENT_TEMPLATE; modifyDataType columnName=DESCRIPTION, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=DESCRIPTION,...		\N	4.29.1	\N	\N	2683232622
2.5.0-duplicate-email-support	slawomir@dabek.name	META-INF/jpa-changelog-2.5.0.xml	2025-11-09 10:14:00.517128	36	EXECUTED	9:61b6d3d7a4c0e0024b0c839da283da0c	addColumn tableName=REALM		\N	4.29.1	\N	\N	2683232622
2.5.0-unique-group-names	hmlnarik@redhat.com	META-INF/jpa-changelog-2.5.0.xml	2025-11-09 10:14:00.531815	37	EXECUTED	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	2683232622
2.5.1	bburke@redhat.com	META-INF/jpa-changelog-2.5.1.xml	2025-11-09 10:14:00.545239	38	EXECUTED	9:a2b870802540cb3faa72098db5388af3	addColumn tableName=FED_USER_CONSENT		\N	4.29.1	\N	\N	2683232622
3.0.0	bburke@redhat.com	META-INF/jpa-changelog-3.0.0.xml	2025-11-09 10:14:00.558437	39	EXECUTED	9:132a67499ba24bcc54fb5cbdcfe7e4c0	addColumn tableName=IDENTITY_PROVIDER		\N	4.29.1	\N	\N	2683232622
3.2.0-fix	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-11-09 10:14:00.563265	40	MARK_RAN	9:938f894c032f5430f2b0fafb1a243462	addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS		\N	4.29.1	\N	\N	2683232622
3.2.0-fix-with-keycloak-5416	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-11-09 10:14:00.570735	41	MARK_RAN	9:845c332ff1874dc5d35974b0babf3006	dropIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS; addNotNullConstraint columnName=REALM_ID, tableName=CLIENT_INITIAL_ACCESS; createIndex indexName=IDX_CLIENT_INIT_ACC_REALM, tableName=CLIENT_INITIAL_ACCESS		\N	4.29.1	\N	\N	2683232622
3.2.0-fix-offline-sessions	hmlnarik	META-INF/jpa-changelog-3.2.0.xml	2025-11-09 10:14:00.590349	42	EXECUTED	9:fc86359c079781adc577c5a217e4d04c	customChange		\N	4.29.1	\N	\N	2683232622
3.2.0-fixed	keycloak	META-INF/jpa-changelog-3.2.0.xml	2025-11-09 10:14:07.272203	43	EXECUTED	9:59a64800e3c0d09b825f8a3b444fa8f4	addColumn tableName=REALM; dropPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_PK2, tableName=OFFLINE_CLIENT_SESSION; dropColumn columnName=CLIENT_SESSION_ID, tableName=OFFLINE_CLIENT_SESSION; addPrimaryKey constraintName=CONSTRAINT_OFFL_CL_SES_P...		\N	4.29.1	\N	\N	2683232622
3.3.0	keycloak	META-INF/jpa-changelog-3.3.0.xml	2025-11-09 10:14:07.289374	44	EXECUTED	9:d48d6da5c6ccf667807f633fe489ce88	addColumn tableName=USER_ENTITY		\N	4.29.1	\N	\N	2683232622
authz-3.4.0.CR1-resource-server-pk-change-part1	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-11-09 10:14:07.306852	45	EXECUTED	9:dde36f7973e80d71fceee683bc5d2951	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_RESOURCE; addColumn tableName=RESOURCE_SERVER_SCOPE		\N	4.29.1	\N	\N	2683232622
authz-3.4.0.CR1-resource-server-pk-change-part2-KEYCLOAK-6095	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-11-09 10:14:07.32113	46	EXECUTED	9:b855e9b0a406b34fa323235a0cf4f640	customChange		\N	4.29.1	\N	\N	2683232622
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-11-09 10:14:07.326375	47	MARK_RAN	9:51abbacd7b416c50c4421a8cabf7927e	dropIndex indexName=IDX_RES_SERV_POL_RES_SERV, tableName=RESOURCE_SERVER_POLICY; dropIndex indexName=IDX_RES_SRV_RES_RES_SRV, tableName=RESOURCE_SERVER_RESOURCE; dropIndex indexName=IDX_RES_SRV_SCOPE_RES_SRV, tableName=RESOURCE_SERVER_SCOPE		\N	4.29.1	\N	\N	2683232622
authz-3.4.0.CR1-resource-server-pk-change-part3-fixed-nodropindex	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-11-09 10:14:07.780892	48	EXECUTED	9:bdc99e567b3398bac83263d375aad143	addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_POLICY; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, tableName=RESOURCE_SERVER_RESOURCE; addNotNullConstraint columnName=RESOURCE_SERVER_CLIENT_ID, ...		\N	4.29.1	\N	\N	2683232622
authn-3.4.0.CR1-refresh-token-max-reuse	glavoie@gmail.com	META-INF/jpa-changelog-authz-3.4.0.CR1.xml	2025-11-09 10:14:07.800656	49	EXECUTED	9:d198654156881c46bfba39abd7769e69	addColumn tableName=REALM		\N	4.29.1	\N	\N	2683232622
3.4.0	keycloak	META-INF/jpa-changelog-3.4.0.xml	2025-11-09 10:14:07.911875	50	EXECUTED	9:cfdd8736332ccdd72c5256ccb42335db	addPrimaryKey constraintName=CONSTRAINT_REALM_DEFAULT_ROLES, tableName=REALM_DEFAULT_ROLES; addPrimaryKey constraintName=CONSTRAINT_COMPOSITE_ROLE, tableName=COMPOSITE_ROLE; addPrimaryKey constraintName=CONSTR_REALM_DEFAULT_GROUPS, tableName=REALM...		\N	4.29.1	\N	\N	2683232622
3.4.0-KEYCLOAK-5230	hmlnarik@redhat.com	META-INF/jpa-changelog-3.4.0.xml	2025-11-09 10:14:09.21105	51	EXECUTED	9:7c84de3d9bd84d7f077607c1a4dcb714	createIndex indexName=IDX_FU_ATTRIBUTE, tableName=FED_USER_ATTRIBUTE; createIndex indexName=IDX_FU_CONSENT, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CONSENT_RU, tableName=FED_USER_CONSENT; createIndex indexName=IDX_FU_CREDENTIAL, t...		\N	4.29.1	\N	\N	2683232622
3.4.1	psilva@redhat.com	META-INF/jpa-changelog-3.4.1.xml	2025-11-09 10:14:09.219433	52	EXECUTED	9:5a6bb36cbefb6a9d6928452c0852af2d	modifyDataType columnName=VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2683232622
3.4.2	keycloak	META-INF/jpa-changelog-3.4.2.xml	2025-11-09 10:14:09.226442	53	EXECUTED	9:8f23e334dbc59f82e0a328373ca6ced0	update tableName=REALM		\N	4.29.1	\N	\N	2683232622
3.4.2-KEYCLOAK-5172	mkanis@redhat.com	META-INF/jpa-changelog-3.4.2.xml	2025-11-09 10:14:09.234416	54	EXECUTED	9:9156214268f09d970cdf0e1564d866af	update tableName=CLIENT		\N	4.29.1	\N	\N	2683232622
4.0.0-KEYCLOAK-6335	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-11-09 10:14:09.250173	55	EXECUTED	9:db806613b1ed154826c02610b7dbdf74	createTable tableName=CLIENT_AUTH_FLOW_BINDINGS; addPrimaryKey constraintName=C_CLI_FLOW_BIND, tableName=CLIENT_AUTH_FLOW_BINDINGS		\N	4.29.1	\N	\N	2683232622
4.0.0-CLEANUP-UNUSED-TABLE	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-11-09 10:14:09.263125	56	EXECUTED	9:229a041fb72d5beac76bb94a5fa709de	dropTable tableName=CLIENT_IDENTITY_PROV_MAPPING		\N	4.29.1	\N	\N	2683232622
4.0.0-KEYCLOAK-6228	bburke@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-11-09 10:14:09.416597	57	EXECUTED	9:079899dade9c1e683f26b2aa9ca6ff04	dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; dropNotNullConstraint columnName=CLIENT_ID, tableName=USER_CONSENT; addColumn tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHO...		\N	4.29.1	\N	\N	2683232622
4.0.0-KEYCLOAK-5579-fixed	mposolda@redhat.com	META-INF/jpa-changelog-4.0.0.xml	2025-11-09 10:14:10.714887	58	EXECUTED	9:139b79bcbbfe903bb1c2d2a4dbf001d9	dropForeignKeyConstraint baseTableName=CLIENT_TEMPLATE_ATTRIBUTES, constraintName=FK_CL_TEMPL_ATTR_TEMPL; renameTable newTableName=CLIENT_SCOPE_ATTRIBUTES, oldTableName=CLIENT_TEMPLATE_ATTRIBUTES; renameColumn newColumnName=SCOPE_ID, oldColumnName...		\N	4.29.1	\N	\N	2683232622
authz-4.0.0.CR1	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.CR1.xml	2025-11-09 10:14:10.771672	59	EXECUTED	9:b55738ad889860c625ba2bf483495a04	createTable tableName=RESOURCE_SERVER_PERM_TICKET; addPrimaryKey constraintName=CONSTRAINT_FAPMT, tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRHO213XCX4WNKOG82SSPMT...		\N	4.29.1	\N	\N	2683232622
authz-4.0.0.Beta3	psilva@redhat.com	META-INF/jpa-changelog-authz-4.0.0.Beta3.xml	2025-11-09 10:14:10.789585	60	EXECUTED	9:e0057eac39aa8fc8e09ac6cfa4ae15fe	addColumn tableName=RESOURCE_SERVER_POLICY; addColumn tableName=RESOURCE_SERVER_PERM_TICKET; addForeignKeyConstraint baseTableName=RESOURCE_SERVER_PERM_TICKET, constraintName=FK_FRSRPO2128CX4WNKOG82SSRFY, referencedTableName=RESOURCE_SERVER_POLICY		\N	4.29.1	\N	\N	2683232622
authz-4.2.0.Final	mhajas@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2025-11-09 10:14:10.816166	61	EXECUTED	9:42a33806f3a0443fe0e7feeec821326c	createTable tableName=RESOURCE_URIS; addForeignKeyConstraint baseTableName=RESOURCE_URIS, constraintName=FK_RESOURCE_SERVER_URIS, referencedTableName=RESOURCE_SERVER_RESOURCE; customChange; dropColumn columnName=URI, tableName=RESOURCE_SERVER_RESO...		\N	4.29.1	\N	\N	2683232622
authz-4.2.0.Final-KEYCLOAK-9944	hmlnarik@redhat.com	META-INF/jpa-changelog-authz-4.2.0.Final.xml	2025-11-09 10:14:10.828011	62	EXECUTED	9:9968206fca46eecc1f51db9c024bfe56	addPrimaryKey constraintName=CONSTRAINT_RESOUR_URIS_PK, tableName=RESOURCE_URIS		\N	4.29.1	\N	\N	2683232622
4.2.0-KEYCLOAK-6313	wadahiro@gmail.com	META-INF/jpa-changelog-4.2.0.xml	2025-11-09 10:14:10.837728	63	EXECUTED	9:92143a6daea0a3f3b8f598c97ce55c3d	addColumn tableName=REQUIRED_ACTION_PROVIDER		\N	4.29.1	\N	\N	2683232622
4.3.0-KEYCLOAK-7984	wadahiro@gmail.com	META-INF/jpa-changelog-4.3.0.xml	2025-11-09 10:14:10.846272	64	EXECUTED	9:82bab26a27195d889fb0429003b18f40	update tableName=REQUIRED_ACTION_PROVIDER		\N	4.29.1	\N	\N	2683232622
4.6.0-KEYCLOAK-7950	psilva@redhat.com	META-INF/jpa-changelog-4.6.0.xml	2025-11-09 10:14:10.856008	65	EXECUTED	9:e590c88ddc0b38b0ae4249bbfcb5abc3	update tableName=RESOURCE_SERVER_RESOURCE		\N	4.29.1	\N	\N	2683232622
4.6.0-KEYCLOAK-8377	keycloak	META-INF/jpa-changelog-4.6.0.xml	2025-11-09 10:14:10.988409	66	EXECUTED	9:5c1f475536118dbdc38d5d7977950cc0	createTable tableName=ROLE_ATTRIBUTE; addPrimaryKey constraintName=CONSTRAINT_ROLE_ATTRIBUTE_PK, tableName=ROLE_ATTRIBUTE; addForeignKeyConstraint baseTableName=ROLE_ATTRIBUTE, constraintName=FK_ROLE_ATTRIBUTE_ID, referencedTableName=KEYCLOAK_ROLE...		\N	4.29.1	\N	\N	2683232622
4.6.0-KEYCLOAK-8555	gideonray@gmail.com	META-INF/jpa-changelog-4.6.0.xml	2025-11-09 10:14:11.111859	67	EXECUTED	9:e7c9f5f9c4d67ccbbcc215440c718a17	createIndex indexName=IDX_COMPONENT_PROVIDER_TYPE, tableName=COMPONENT		\N	4.29.1	\N	\N	2683232622
4.7.0-KEYCLOAK-1267	sguilhen@redhat.com	META-INF/jpa-changelog-4.7.0.xml	2025-11-09 10:14:11.125384	68	EXECUTED	9:88e0bfdda924690d6f4e430c53447dd5	addColumn tableName=REALM		\N	4.29.1	\N	\N	2683232622
4.7.0-KEYCLOAK-7275	keycloak	META-INF/jpa-changelog-4.7.0.xml	2025-11-09 10:14:11.253468	69	EXECUTED	9:f53177f137e1c46b6a88c59ec1cb5218	renameColumn newColumnName=CREATED_ON, oldColumnName=LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION; addNotNullConstraint columnName=CREATED_ON, tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_USER_SESSION; customChange; createIn...		\N	4.29.1	\N	\N	2683232622
4.8.0-KEYCLOAK-8835	sguilhen@redhat.com	META-INF/jpa-changelog-4.8.0.xml	2025-11-09 10:14:11.267052	70	EXECUTED	9:a74d33da4dc42a37ec27121580d1459f	addNotNullConstraint columnName=SSO_MAX_LIFESPAN_REMEMBER_ME, tableName=REALM; addNotNullConstraint columnName=SSO_IDLE_TIMEOUT_REMEMBER_ME, tableName=REALM		\N	4.29.1	\N	\N	2683232622
authz-7.0.0-KEYCLOAK-10443	psilva@redhat.com	META-INF/jpa-changelog-authz-7.0.0.xml	2025-11-09 10:14:11.278814	71	EXECUTED	9:fd4ade7b90c3b67fae0bfcfcb42dfb5f	addColumn tableName=RESOURCE_SERVER		\N	4.29.1	\N	\N	2683232622
8.0.0-adding-credential-columns	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-11-09 10:14:11.304937	72	EXECUTED	9:aa072ad090bbba210d8f18781b8cebf4	addColumn tableName=CREDENTIAL; addColumn tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	2683232622
8.0.0-updating-credential-data-not-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-11-09 10:14:11.327144	73	EXECUTED	9:1ae6be29bab7c2aa376f6983b932be37	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	2683232622
8.0.0-updating-credential-data-oracle-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-11-09 10:14:11.334395	74	MARK_RAN	9:14706f286953fc9a25286dbd8fb30d97	update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL; update tableName=FED_USER_CREDENTIAL		\N	4.29.1	\N	\N	2683232622
8.0.0-credential-cleanup-fixed	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-11-09 10:14:11.391294	75	EXECUTED	9:2b9cc12779be32c5b40e2e67711a218b	dropDefaultValue columnName=COUNTER, tableName=CREDENTIAL; dropDefaultValue columnName=DIGITS, tableName=CREDENTIAL; dropDefaultValue columnName=PERIOD, tableName=CREDENTIAL; dropDefaultValue columnName=ALGORITHM, tableName=CREDENTIAL; dropColumn ...		\N	4.29.1	\N	\N	2683232622
8.0.0-resource-tag-support	keycloak	META-INF/jpa-changelog-8.0.0.xml	2025-11-09 10:14:11.521871	76	EXECUTED	9:91fa186ce7a5af127a2d7a91ee083cc5	addColumn tableName=MIGRATION_MODEL; createIndex indexName=IDX_UPDATE_TIME, tableName=MIGRATION_MODEL		\N	4.29.1	\N	\N	2683232622
9.0.0-always-display-client	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-11-09 10:14:11.533864	77	EXECUTED	9:6335e5c94e83a2639ccd68dd24e2e5ad	addColumn tableName=CLIENT		\N	4.29.1	\N	\N	2683232622
9.0.0-drop-constraints-for-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-11-09 10:14:11.538557	78	MARK_RAN	9:6bdb5658951e028bfe16fa0a8228b530	dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5PMT, tableName=RESOURCE_SERVER_PERM_TICKET; dropUniqueConstraint constraintName=UK_FRSR6T700S9V50BU18WS5HA6, tableName=RESOURCE_SERVER_RESOURCE; dropPrimaryKey constraintName=CONSTRAINT_O...		\N	4.29.1	\N	\N	2683232622
9.0.0-increase-column-size-federated-fk	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-11-09 10:14:11.592758	79	EXECUTED	9:d5bc15a64117ccad481ce8792d4c608f	modifyDataType columnName=CLIENT_ID, tableName=FED_USER_CONSENT; modifyDataType columnName=CLIENT_REALM_CONSTRAINT, tableName=KEYCLOAK_ROLE; modifyDataType columnName=OWNER, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=CLIENT_ID, ta...		\N	4.29.1	\N	\N	2683232622
9.0.0-recreate-constraints-after-column-increase	keycloak	META-INF/jpa-changelog-9.0.0.xml	2025-11-09 10:14:11.597725	80	MARK_RAN	9:077cba51999515f4d3e7ad5619ab592c	addNotNullConstraint columnName=CLIENT_ID, tableName=OFFLINE_CLIENT_SESSION; addNotNullConstraint columnName=OWNER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNullConstraint columnName=REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; addNotNull...		\N	4.29.1	\N	\N	2683232622
9.0.1-add-index-to-client.client_id	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-11-09 10:14:11.74242	81	EXECUTED	9:be969f08a163bf47c6b9e9ead8ac2afb	createIndex indexName=IDX_CLIENT_ID, tableName=CLIENT		\N	4.29.1	\N	\N	2683232622
9.0.1-KEYCLOAK-12579-drop-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-11-09 10:14:11.746478	82	MARK_RAN	9:6d3bb4408ba5a72f39bd8a0b301ec6e3	dropUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	2683232622
9.0.1-KEYCLOAK-12579-add-not-null-constraint	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-11-09 10:14:11.763051	83	EXECUTED	9:966bda61e46bebf3cc39518fbed52fa7	addNotNullConstraint columnName=PARENT_GROUP, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	2683232622
9.0.1-KEYCLOAK-12579-recreate-constraints	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-11-09 10:14:11.76729	84	MARK_RAN	9:8dcac7bdf7378e7d823cdfddebf72fda	addUniqueConstraint constraintName=SIBLING_NAMES, tableName=KEYCLOAK_GROUP		\N	4.29.1	\N	\N	2683232622
9.0.1-add-index-to-events	keycloak	META-INF/jpa-changelog-9.0.1.xml	2025-11-09 10:14:11.91268	85	EXECUTED	9:7d93d602352a30c0c317e6a609b56599	createIndex indexName=IDX_EVENT_TIME, tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	2683232622
map-remove-ri	keycloak	META-INF/jpa-changelog-11.0.0.xml	2025-11-09 10:14:11.928923	86	EXECUTED	9:71c5969e6cdd8d7b6f47cebc86d37627	dropForeignKeyConstraint baseTableName=REALM, constraintName=FK_TRAF444KK6QRKMS7N56AIWQ5Y; dropForeignKeyConstraint baseTableName=KEYCLOAK_ROLE, constraintName=FK_KJHO5LE2C0RAL09FL8CM9WFW9		\N	4.29.1	\N	\N	2683232622
map-remove-ri	keycloak	META-INF/jpa-changelog-12.0.0.xml	2025-11-09 10:14:11.95569	87	EXECUTED	9:a9ba7d47f065f041b7da856a81762021	dropForeignKeyConstraint baseTableName=REALM_DEFAULT_GROUPS, constraintName=FK_DEF_GROUPS_GROUP; dropForeignKeyConstraint baseTableName=REALM_DEFAULT_ROLES, constraintName=FK_H4WPD7W4HSOOLNI3H0SW7BTJE; dropForeignKeyConstraint baseTableName=CLIENT...		\N	4.29.1	\N	\N	2683232622
12.1.0-add-realm-localization-table	keycloak	META-INF/jpa-changelog-12.0.0.xml	2025-11-09 10:14:11.972937	88	EXECUTED	9:fffabce2bc01e1a8f5110d5278500065	createTable tableName=REALM_LOCALIZATIONS; addPrimaryKey tableName=REALM_LOCALIZATIONS		\N	4.29.1	\N	\N	2683232622
default-roles	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-09 10:14:11.990137	89	EXECUTED	9:fa8a5b5445e3857f4b010bafb5009957	addColumn tableName=REALM; customChange		\N	4.29.1	\N	\N	2683232622
default-roles-cleanup	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-09 10:14:12.010988	90	EXECUTED	9:67ac3241df9a8582d591c5ed87125f39	dropTable tableName=REALM_DEFAULT_ROLES; dropTable tableName=CLIENT_DEFAULT_ROLES		\N	4.29.1	\N	\N	2683232622
13.0.0-KEYCLOAK-16844	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-09 10:14:12.134192	91	EXECUTED	9:ad1194d66c937e3ffc82386c050ba089	createIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2683232622
map-remove-ri-13.0.0	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-09 10:14:12.159771	92	EXECUTED	9:d9be619d94af5a2f5d07b9f003543b91	dropForeignKeyConstraint baseTableName=DEFAULT_CLIENT_SCOPE, constraintName=FK_R_DEF_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SCOPE_CLIENT, constraintName=FK_C_CLI_SCOPE_SCOPE; dropForeignKeyConstraint baseTableName=CLIENT_SC...		\N	4.29.1	\N	\N	2683232622
13.0.0-KEYCLOAK-17992-drop-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-09 10:14:12.1641	93	MARK_RAN	9:544d201116a0fcc5a5da0925fbbc3bde	dropPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CLSCOPE_CL, tableName=CLIENT_SCOPE_CLIENT; dropIndex indexName=IDX_CL_CLSCOPE, tableName=CLIENT_SCOPE_CLIENT		\N	4.29.1	\N	\N	2683232622
13.0.0-increase-column-size-federated	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-09 10:14:12.184216	94	EXECUTED	9:43c0c1055b6761b4b3e89de76d612ccf	modifyDataType columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; modifyDataType columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT		\N	4.29.1	\N	\N	2683232622
13.0.0-KEYCLOAK-17992-recreate-constraints	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-09 10:14:12.188993	95	MARK_RAN	9:8bd711fd0330f4fe980494ca43ab1139	addNotNullConstraint columnName=CLIENT_ID, tableName=CLIENT_SCOPE_CLIENT; addNotNullConstraint columnName=SCOPE_ID, tableName=CLIENT_SCOPE_CLIENT; addPrimaryKey constraintName=C_CLI_SCOPE_BIND, tableName=CLIENT_SCOPE_CLIENT; createIndex indexName=...		\N	4.29.1	\N	\N	2683232622
json-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-13.0.0.xml	2025-11-09 10:14:12.207747	96	EXECUTED	9:e07d2bc0970c348bb06fb63b1f82ddbf	addColumn tableName=REALM_ATTRIBUTE; update tableName=REALM_ATTRIBUTE; dropColumn columnName=VALUE, tableName=REALM_ATTRIBUTE; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=REALM_ATTRIBUTE		\N	4.29.1	\N	\N	2683232622
14.0.0-KEYCLOAK-11019	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-09 10:14:12.510458	97	EXECUTED	9:24fb8611e97f29989bea412aa38d12b7	createIndex indexName=IDX_OFFLINE_CSS_PRELOAD, tableName=OFFLINE_CLIENT_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USER, tableName=OFFLINE_USER_SESSION; createIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2683232622
14.0.0-KEYCLOAK-18286	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-09 10:14:12.514302	98	MARK_RAN	9:259f89014ce2506ee84740cbf7163aa7	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2683232622
14.0.0-KEYCLOAK-18286-revert	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-09 10:14:12.542177	99	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2683232622
14.0.0-KEYCLOAK-18286-supported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-09 10:14:12.673999	100	EXECUTED	9:60ca84a0f8c94ec8c3504a5a3bc88ee8	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2683232622
14.0.0-KEYCLOAK-18286-unsupported-dbs	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-09 10:14:12.679051	101	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2683232622
KEYCLOAK-17267-add-index-to-user-attributes	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-09 10:14:12.849054	102	EXECUTED	9:0b305d8d1277f3a89a0a53a659ad274c	createIndex indexName=IDX_USER_ATTRIBUTE_NAME, tableName=USER_ATTRIBUTE		\N	4.29.1	\N	\N	2683232622
KEYCLOAK-18146-add-saml-art-binding-identifier	keycloak	META-INF/jpa-changelog-14.0.0.xml	2025-11-09 10:14:12.862769	103	EXECUTED	9:2c374ad2cdfe20e2905a84c8fac48460	customChange		\N	4.29.1	\N	\N	2683232622
15.0.0-KEYCLOAK-18467	keycloak	META-INF/jpa-changelog-15.0.0.xml	2025-11-09 10:14:12.885249	104	EXECUTED	9:47a760639ac597360a8219f5b768b4de	addColumn tableName=REALM_LOCALIZATIONS; update tableName=REALM_LOCALIZATIONS; dropColumn columnName=TEXTS, tableName=REALM_LOCALIZATIONS; renameColumn newColumnName=TEXTS, oldColumnName=TEXTS_NEW, tableName=REALM_LOCALIZATIONS; addNotNullConstrai...		\N	4.29.1	\N	\N	2683232622
17.0.0-9562	keycloak	META-INF/jpa-changelog-17.0.0.xml	2025-11-09 10:14:13.062803	105	EXECUTED	9:a6272f0576727dd8cad2522335f5d99e	createIndex indexName=IDX_USER_SERVICE_ACCOUNT, tableName=USER_ENTITY		\N	4.29.1	\N	\N	2683232622
18.0.0-10625-IDX_ADMIN_EVENT_TIME	keycloak	META-INF/jpa-changelog-18.0.0.xml	2025-11-09 10:14:13.227776	106	EXECUTED	9:015479dbd691d9cc8669282f4828c41d	createIndex indexName=IDX_ADMIN_EVENT_TIME, tableName=ADMIN_EVENT_ENTITY		\N	4.29.1	\N	\N	2683232622
18.0.15-30992-index-consent	keycloak	META-INF/jpa-changelog-18.0.15.xml	2025-11-09 10:14:13.430668	107	EXECUTED	9:80071ede7a05604b1f4906f3bf3b00f0	createIndex indexName=IDX_USCONSENT_SCOPE_ID, tableName=USER_CONSENT_CLIENT_SCOPE		\N	4.29.1	\N	\N	2683232622
19.0.0-10135	keycloak	META-INF/jpa-changelog-19.0.0.xml	2025-11-09 10:14:13.443759	108	EXECUTED	9:9518e495fdd22f78ad6425cc30630221	customChange		\N	4.29.1	\N	\N	2683232622
20.0.0-12964-supported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-11-09 10:14:13.630907	109	EXECUTED	9:e5f243877199fd96bcc842f27a1656ac	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.29.1	\N	\N	2683232622
20.0.0-12964-unsupported-dbs	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-11-09 10:14:13.635818	110	MARK_RAN	9:1a6fcaa85e20bdeae0a9ce49b41946a5	createIndex indexName=IDX_GROUP_ATT_BY_NAME_VALUE, tableName=GROUP_ATTRIBUTE		\N	4.29.1	\N	\N	2683232622
client-attributes-string-accomodation-fixed	keycloak	META-INF/jpa-changelog-20.0.0.xml	2025-11-09 10:14:13.65854	111	EXECUTED	9:3f332e13e90739ed0c35b0b25b7822ca	addColumn tableName=CLIENT_ATTRIBUTES; update tableName=CLIENT_ATTRIBUTES; dropColumn columnName=VALUE, tableName=CLIENT_ATTRIBUTES; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2683232622
21.0.2-17277	keycloak	META-INF/jpa-changelog-21.0.2.xml	2025-11-09 10:14:13.672466	112	EXECUTED	9:7ee1f7a3fb8f5588f171fb9a6ab623c0	customChange		\N	4.29.1	\N	\N	2683232622
21.1.0-19404	keycloak	META-INF/jpa-changelog-21.1.0.xml	2025-11-09 10:14:13.729552	113	EXECUTED	9:3d7e830b52f33676b9d64f7f2b2ea634	modifyDataType columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=LOGIC, tableName=RESOURCE_SERVER_POLICY; modifyDataType columnName=POLICY_ENFORCE_MODE, tableName=RESOURCE_SERVER		\N	4.29.1	\N	\N	2683232622
21.1.0-19404-2	keycloak	META-INF/jpa-changelog-21.1.0.xml	2025-11-09 10:14:13.740445	114	MARK_RAN	9:627d032e3ef2c06c0e1f73d2ae25c26c	addColumn tableName=RESOURCE_SERVER_POLICY; update tableName=RESOURCE_SERVER_POLICY; dropColumn columnName=DECISION_STRATEGY, tableName=RESOURCE_SERVER_POLICY; renameColumn newColumnName=DECISION_STRATEGY, oldColumnName=DECISION_STRATEGY_NEW, tabl...		\N	4.29.1	\N	\N	2683232622
22.0.0-17484-updated	keycloak	META-INF/jpa-changelog-22.0.0.xml	2025-11-09 10:14:13.76294	115	EXECUTED	9:90af0bfd30cafc17b9f4d6eccd92b8b3	customChange		\N	4.29.1	\N	\N	2683232622
22.0.5-24031	keycloak	META-INF/jpa-changelog-22.0.0.xml	2025-11-09 10:14:13.770201	116	MARK_RAN	9:a60d2d7b315ec2d3eba9e2f145f9df28	customChange		\N	4.29.1	\N	\N	2683232622
23.0.0-12062	keycloak	META-INF/jpa-changelog-23.0.0.xml	2025-11-09 10:14:13.810945	117	EXECUTED	9:2168fbe728fec46ae9baf15bf80927b8	addColumn tableName=COMPONENT_CONFIG; update tableName=COMPONENT_CONFIG; dropColumn columnName=VALUE, tableName=COMPONENT_CONFIG; renameColumn newColumnName=VALUE, oldColumnName=VALUE_NEW, tableName=COMPONENT_CONFIG		\N	4.29.1	\N	\N	2683232622
23.0.0-17258	keycloak	META-INF/jpa-changelog-23.0.0.xml	2025-11-09 10:14:13.834159	118	EXECUTED	9:36506d679a83bbfda85a27ea1864dca8	addColumn tableName=EVENT_ENTITY		\N	4.29.1	\N	\N	2683232622
24.0.0-9758	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-11-09 10:14:14.678453	119	EXECUTED	9:502c557a5189f600f0f445a9b49ebbce	addColumn tableName=USER_ATTRIBUTE; addColumn tableName=FED_USER_ATTRIBUTE; createIndex indexName=USER_ATTR_LONG_VALUES, tableName=USER_ATTRIBUTE; createIndex indexName=FED_USER_ATTR_LONG_VALUES, tableName=FED_USER_ATTRIBUTE; createIndex indexName...		\N	4.29.1	\N	\N	2683232622
24.0.0-9758-2	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-11-09 10:14:14.689513	120	EXECUTED	9:bf0fdee10afdf597a987adbf291db7b2	customChange		\N	4.29.1	\N	\N	2683232622
24.0.0-26618-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-11-09 10:14:14.705337	121	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2683232622
24.0.0-26618-reindex	keycloak	META-INF/jpa-changelog-24.0.0.xml	2025-11-09 10:14:14.915067	122	EXECUTED	9:08707c0f0db1cef6b352db03a60edc7f	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2683232622
24.0.2-27228	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-11-09 10:14:14.928348	123	EXECUTED	9:eaee11f6b8aa25d2cc6a84fb86fc6238	customChange		\N	4.29.1	\N	\N	2683232622
24.0.2-27967-drop-index-if-present	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-11-09 10:14:14.933741	124	MARK_RAN	9:04baaf56c116ed19951cbc2cca584022	dropIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2683232622
24.0.2-27967-reindex	keycloak	META-INF/jpa-changelog-24.0.2.xml	2025-11-09 10:14:14.941562	125	MARK_RAN	9:d3d977031d431db16e2c181ce49d73e9	createIndex indexName=IDX_CLIENT_ATT_BY_NAME_VALUE, tableName=CLIENT_ATTRIBUTES		\N	4.29.1	\N	\N	2683232622
25.0.0-28265-tables	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-09 10:14:14.963497	126	EXECUTED	9:deda2df035df23388af95bbd36c17cef	addColumn tableName=OFFLINE_USER_SESSION; addColumn tableName=OFFLINE_CLIENT_SESSION		\N	4.29.1	\N	\N	2683232622
25.0.0-28265-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-09 10:14:15.132144	127	EXECUTED	9:3e96709818458ae49f3c679ae58d263a	createIndex indexName=IDX_OFFLINE_USS_BY_LAST_SESSION_REFRESH, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2683232622
25.0.0-28265-index-cleanup	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-09 10:14:15.152733	128	EXECUTED	9:8c0cfa341a0474385b324f5c4b2dfcc1	dropIndex indexName=IDX_OFFLINE_USS_CREATEDON, tableName=OFFLINE_USER_SESSION; dropIndex indexName=IDX_OFFLINE_USS_PRELOAD, tableName=OFFLINE_USER_SESSION; dropIndex indexName=IDX_OFFLINE_USS_BY_USERSESS, tableName=OFFLINE_USER_SESSION; dropIndex ...		\N	4.29.1	\N	\N	2683232622
25.0.0-28265-index-2-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-09 10:14:15.159849	129	MARK_RAN	9:b7ef76036d3126bb83c2423bf4d449d6	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2683232622
25.0.0-28265-index-2-not-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-09 10:14:15.339324	130	EXECUTED	9:23396cf51ab8bc1ae6f0cac7f9f6fcf7	createIndex indexName=IDX_OFFLINE_USS_BY_BROKER_SESSION_ID, tableName=OFFLINE_USER_SESSION		\N	4.29.1	\N	\N	2683232622
25.0.0-org	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-09 10:14:15.374924	131	EXECUTED	9:5c859965c2c9b9c72136c360649af157	createTable tableName=ORG; addUniqueConstraint constraintName=UK_ORG_NAME, tableName=ORG; addUniqueConstraint constraintName=UK_ORG_GROUP, tableName=ORG; createTable tableName=ORG_DOMAIN		\N	4.29.1	\N	\N	2683232622
unique-consentuser	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-09 10:14:15.402631	132	EXECUTED	9:5857626a2ea8767e9a6c66bf3a2cb32f	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.29.1	\N	\N	2683232622
unique-consentuser-mysql	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-09 10:14:15.409704	133	MARK_RAN	9:b79478aad5adaa1bc428e31563f55e8e	customChange; dropUniqueConstraint constraintName=UK_JKUWUVD56ONTGSUHOGM8UEWRT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_LOCAL_CONSENT, tableName=USER_CONSENT; addUniqueConstraint constraintName=UK_EXTERNAL_CONSENT, tableName=...		\N	4.29.1	\N	\N	2683232622
25.0.0-28861-index-creation	keycloak	META-INF/jpa-changelog-25.0.0.xml	2025-11-09 10:14:15.758504	134	EXECUTED	9:b9acb58ac958d9ada0fe12a5d4794ab1	createIndex indexName=IDX_PERM_TICKET_REQUESTER, tableName=RESOURCE_SERVER_PERM_TICKET; createIndex indexName=IDX_PERM_TICKET_OWNER, tableName=RESOURCE_SERVER_PERM_TICKET		\N	4.29.1	\N	\N	2683232622
26.0.0-org-alias	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-09 10:14:15.780637	135	EXECUTED	9:6ef7d63e4412b3c2d66ed179159886a4	addColumn tableName=ORG; update tableName=ORG; addNotNullConstraint columnName=ALIAS, tableName=ORG; addUniqueConstraint constraintName=UK_ORG_ALIAS, tableName=ORG		\N	4.29.1	\N	\N	2683232622
26.0.0-org-group	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-09 10:14:15.805674	136	EXECUTED	9:da8e8087d80ef2ace4f89d8c5b9ca223	addColumn tableName=KEYCLOAK_GROUP; update tableName=KEYCLOAK_GROUP; addNotNullConstraint columnName=TYPE, tableName=KEYCLOAK_GROUP; customChange		\N	4.29.1	\N	\N	2683232622
26.0.0-org-indexes	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-09 10:14:15.939653	137	EXECUTED	9:79b05dcd610a8c7f25ec05135eec0857	createIndex indexName=IDX_ORG_DOMAIN_ORG_ID, tableName=ORG_DOMAIN		\N	4.29.1	\N	\N	2683232622
26.0.0-org-group-membership	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-09 10:14:15.956386	138	EXECUTED	9:a6ace2ce583a421d89b01ba2a28dc2d4	addColumn tableName=USER_GROUP_MEMBERSHIP; update tableName=USER_GROUP_MEMBERSHIP; addNotNullConstraint columnName=MEMBERSHIP_TYPE, tableName=USER_GROUP_MEMBERSHIP		\N	4.29.1	\N	\N	2683232622
31296-persist-revoked-access-tokens	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-09 10:14:15.972712	139	EXECUTED	9:64ef94489d42a358e8304b0e245f0ed4	createTable tableName=REVOKED_TOKEN; addPrimaryKey constraintName=CONSTRAINT_RT, tableName=REVOKED_TOKEN		\N	4.29.1	\N	\N	2683232622
31725-index-persist-revoked-access-tokens	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-09 10:14:16.107026	140	EXECUTED	9:b994246ec2bf7c94da881e1d28782c7b	createIndex indexName=IDX_REV_TOKEN_ON_EXPIRE, tableName=REVOKED_TOKEN		\N	4.29.1	\N	\N	2683232622
26.0.0-idps-for-login	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-09 10:14:16.393556	141	EXECUTED	9:51f5fffadf986983d4bd59582c6c1604	addColumn tableName=IDENTITY_PROVIDER; createIndex indexName=IDX_IDP_REALM_ORG, tableName=IDENTITY_PROVIDER; createIndex indexName=IDX_IDP_FOR_LOGIN, tableName=IDENTITY_PROVIDER; customChange		\N	4.29.1	\N	\N	2683232622
26.0.0-32583-drop-redundant-index-on-client-session	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-09 10:14:16.402422	142	EXECUTED	9:24972d83bf27317a055d234187bb4af9	dropIndex indexName=IDX_US_SESS_ID_ON_CL_SESS, tableName=OFFLINE_CLIENT_SESSION		\N	4.29.1	\N	\N	2683232622
26.0.0.32582-remove-tables-user-session-user-session-note-and-client-session	keycloak	META-INF/jpa-changelog-26.0.0.xml	2025-11-09 10:14:16.435938	143	EXECUTED	9:febdc0f47f2ed241c59e60f58c3ceea5	dropTable tableName=CLIENT_SESSION_ROLE; dropTable tableName=CLIENT_SESSION_NOTE; dropTable tableName=CLIENT_SESSION_PROT_MAPPER; dropTable tableName=CLIENT_SESSION_AUTH_STATUS; dropTable tableName=CLIENT_USER_SESSION_NOTE; dropTable tableName=CLI...		\N	4.29.1	\N	\N	2683232622
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
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	5eca7de0-b320-446f-a0f5-616d3a92a570	f
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	84bba3ec-a613-462e-94d3-737794a0bfb9	t
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	b968713a-289f-4db0-9c57-4a98b1975503	t
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	64048be8-99ec-4cb2-811a-ed4507cc8351	t
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	59d1664d-08b1-40f5-a823-b81206925755	t
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	d10a623c-f790-479d-9706-a2bd4b8c17e9	f
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	45900d37-a973-4b12-9ef7-bbb3cf0e04ec	f
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	e69e3df6-a890-4fe9-a22c-2a822a588c7a	t
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	58240b4a-31ab-4f78-9839-506121985e67	t
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	40061be1-04ff-4613-9339-059166efcc6b	f
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	7958aa4d-9c75-43aa-84ce-a35a83617363	t
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a745363a-6438-44be-8fe2-387f167fc70d	t
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a6dc6bd4-6b74-43d6-8f61-86abd29754d6	f
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
821e4bb0-9d76-4771-bc20-8e9083ac457d	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f	${role_default-roles}	default-roles-master	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	\N	\N
037ffc7d-8518-41c3-874b-261790225b53	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f	${role_create-realm}	create-realm	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	\N	\N
db4ab77a-a58c-482b-b2ae-2090a009b1af	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f	${role_admin}	admin	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	\N	\N
550d4a3f-8e8f-46a6-8da3-b43eae24864e	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_create-client}	create-client	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
3f50f97a-10a4-4647-b3b5-cebae15d0512	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_view-realm}	view-realm	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
2c077dbb-72e0-4907-bd74-629d431ae93d	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_view-users}	view-users	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
71926297-827f-4ca9-8c9c-43d6cacd995a	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_view-clients}	view-clients	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
99b665f4-ff4a-4241-a5b3-d4788d0178cb	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_view-events}	view-events	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
18da744f-9a5c-4821-8ced-8474d5282683	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_view-identity-providers}	view-identity-providers	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
1b70f161-90e2-49ed-bf13-ade8c4715596	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_view-authorization}	view-authorization	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
a4ac462f-fe7f-4a74-9a9c-2c1b350c5da3	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_manage-realm}	manage-realm	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
73980a57-8c0b-4a33-a5c0-98b0a93f9f29	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_manage-users}	manage-users	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
6c80f665-6ac1-48d3-9eb9-d3644040a076	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_manage-clients}	manage-clients	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
f79eeeed-8fd6-43f9-afa0-1a80df983009	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_manage-events}	manage-events	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
503e03a0-850d-4786-9d7f-cf8afe62b886	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_manage-identity-providers}	manage-identity-providers	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
6a6506b4-0b6b-49bb-ba1f-e37841ea2b1b	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_manage-authorization}	manage-authorization	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
8b46f4fa-094c-456c-a169-fa8f7f9a2b3d	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_query-users}	query-users	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
9e7333c6-f729-44a3-9d74-7822e90f5354	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_query-clients}	query-clients	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
07619e0f-f662-41bb-b97c-1b032ac91fc9	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_query-realms}	query-realms	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
0a9e911e-50b7-4715-b822-d25c7d7fb66b	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_query-groups}	query-groups	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
8e8121ee-2ad2-4770-9178-f72c8c95a10d	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	t	${role_view-profile}	view-profile	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	\N
1951d5d4-dc58-41c4-846b-6e76438d3543	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	t	${role_manage-account}	manage-account	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	\N
b421a7f6-bb64-4606-83b6-1b871d925102	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	t	${role_manage-account-links}	manage-account-links	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	\N
41142345-202e-4be8-b95d-69d2062b10eb	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	t	${role_view-applications}	view-applications	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	\N
47982905-f5d3-49be-b956-d35a2f9c6a72	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	t	${role_view-consent}	view-consent	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	\N
ad6b0545-1125-494a-86a2-1c4993c199e8	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	t	${role_manage-consent}	manage-consent	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	\N
1a2b2e24-4a09-44f4-8617-d656a46178ad	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	t	${role_view-groups}	view-groups	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	\N
51fb6f37-5ef8-486c-a8cd-31e555a67008	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	t	${role_delete-account}	delete-account	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	917b4f65-b74a-4d60-9b9f-f264bc45e4ca	\N
01da7d18-736e-4b35-8c79-4ea526598ffa	523b269e-73c1-4c0a-acaf-f85522220cce	t	${role_read-token}	read-token	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	523b269e-73c1-4c0a-acaf-f85522220cce	\N
dca72013-fbc3-49f3-8a89-d70506667833	a84af477-3b7f-47f1-8d44-fe2abade00fd	t	${role_impersonation}	impersonation	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	a84af477-3b7f-47f1-8d44-fe2abade00fd	\N
29b42e5e-2d03-4b56-bf89-f9f05672540f	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f	${role_offline-access}	offline_access	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	\N	\N
9e63cdf7-de90-4650-9cba-c4b8231d41d1	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f	${role_uma_authorization}	uma_authorization	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	\N	\N
\.


--
-- Data for Name: migration_model; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.migration_model (id, version, update_time) FROM stdin;
qamxe	26.0.0	1762683258
\.


--
-- Data for Name: offline_client_session; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.offline_client_session (user_session_id, client_id, offline_flag, "timestamp", data, client_storage_provider, external_client_id, version) FROM stdin;
37c8b238-1d91-498b-ac22-05bcf4f0bae6	8eeda11d-42fa-4c85-bb09-bd17511aa472	0	1762683696	{"authMethod":"openid-connect","redirectUri":"http://localhost:9090/admin/master/console/","notes":{"clientId":"8eeda11d-42fa-4c85-bb09-bd17511aa472","iss":"http://localhost:9090/realms/master","startedAt":"1762683693","response_type":"code","level-of-authentication":"-1","code_challenge_method":"S256","nonce":"e4100de3-9004-4875-9157-3049e5b2c3c4","response_mode":"query","scope":"openid","userSessionStartedAt":"1762683692","redirect_uri":"http://localhost:9090/admin/master/console/","state":"e5d400cd-30f5-4ab3-a27c-e7b651741971","code_challenge":"nWnrwh8M-M1wCS2BSuNnPY2njcMbWeEgsNs4nS7OApw"}}	local	local	1
\.


--
-- Data for Name: offline_user_session; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.offline_user_session (user_session_id, user_id, realm_id, created_on, offline_flag, data, last_session_refresh, broker_session_id, version) FROM stdin;
37c8b238-1d91-498b-ac22-05bcf4f0bae6	215946ae-82c4-499d-ab37-82f2e3f28b48	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	1762683692	0	{"ipAddress":"172.22.0.1","authMethod":"openid-connect","rememberMe":false,"started":0,"notes":{"KC_DEVICE_NOTE":"eyJpcEFkZHJlc3MiOiIxNzIuMjIuMC4xIiwib3MiOiJMaW51eCIsIm9zVmVyc2lvbiI6IlVua25vd24iLCJicm93c2VyIjoiRmlyZWZveC8xNDMuMCIsImRldmljZSI6Ik90aGVyIiwibGFzdEFjY2VzcyI6MCwibW9iaWxlIjpmYWxzZX0=","AUTH_TIME":"1762683693","authenticators-completed":"{\\"cecf6950-4288-45ac-bc28-768e2455c72b\\":1762683692}"},"state":"LOGGED_IN"}	1762683696	\N	1
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
4caa732a-1410-4932-ae90-c132b57887c2	audience resolve	openid-connect	oidc-audience-resolve-mapper	9f185538-da56-4880-be80-f0cc3bfcd3cd	\N
843c7d8b-5106-4d7b-8c71-e6ba13386f83	locale	openid-connect	oidc-usermodel-attribute-mapper	8eeda11d-42fa-4c85-bb09-bd17511aa472	\N
944c01ff-4c2a-4846-a151-cdbf20e8b74d	role list	saml	saml-role-list-mapper	\N	84bba3ec-a613-462e-94d3-737794a0bfb9
98dc726f-2c3a-4e7d-b42b-7703ce504f88	organization	saml	saml-organization-membership-mapper	\N	b968713a-289f-4db0-9c57-4a98b1975503
efc14f98-5fcf-4deb-b0be-02d8e192f169	full name	openid-connect	oidc-full-name-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
9b194875-87ce-4278-b391-b724fc099248	family name	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
f44fe06a-abf6-46cd-90d9-18f102dac2fc	given name	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
d5520d8f-9b27-4e73-b708-153e50f1b95e	middle name	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
b657adbd-0603-4a15-812a-9b70605f9d34	nickname	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
004c3e2f-ffc0-4e3c-89ff-d80515171bd0	username	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
f5d81f0c-4d06-4185-85c2-034a10a5040a	profile	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
cb1bddd2-1da8-45f7-a016-e67d7026f0b0	picture	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
2ad24d3e-3b55-40fd-8295-1a388b18ac2e	website	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
76dbd4a7-a7b2-4c74-82c2-254bfc040823	gender	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
7ff46067-287f-4939-a663-63d7877c4e3a	birthdate	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
2357506c-9047-43e2-bb28-fb3a537550e5	zoneinfo	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
1d6533a8-f13b-4fe9-ab5f-7e3fb639431e	locale	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
0a0d0865-a8dd-4e04-a0c4-9545b7e7bfbd	updated at	openid-connect	oidc-usermodel-attribute-mapper	\N	64048be8-99ec-4cb2-811a-ed4507cc8351
99a7847d-dc16-4cb9-b6d5-09ad54a10947	email	openid-connect	oidc-usermodel-attribute-mapper	\N	59d1664d-08b1-40f5-a823-b81206925755
3da9c4df-7530-4e4e-9d52-20b5698b548b	email verified	openid-connect	oidc-usermodel-property-mapper	\N	59d1664d-08b1-40f5-a823-b81206925755
5813e327-e93a-4093-8af8-95095a1e441b	address	openid-connect	oidc-address-mapper	\N	d10a623c-f790-479d-9706-a2bd4b8c17e9
e0535ebf-96e2-4f24-ad18-50fbd63b9412	phone number	openid-connect	oidc-usermodel-attribute-mapper	\N	45900d37-a973-4b12-9ef7-bbb3cf0e04ec
6d41aed9-6f7d-4f25-9dd9-14d516ca3d5a	phone number verified	openid-connect	oidc-usermodel-attribute-mapper	\N	45900d37-a973-4b12-9ef7-bbb3cf0e04ec
1b942332-03d0-40d8-86b6-4b5013a75cb8	realm roles	openid-connect	oidc-usermodel-realm-role-mapper	\N	e69e3df6-a890-4fe9-a22c-2a822a588c7a
0d621768-4954-4d3b-bf7c-ce569f3f2ffb	client roles	openid-connect	oidc-usermodel-client-role-mapper	\N	e69e3df6-a890-4fe9-a22c-2a822a588c7a
4eca33f1-6aef-441e-97f6-7381fd53d743	audience resolve	openid-connect	oidc-audience-resolve-mapper	\N	e69e3df6-a890-4fe9-a22c-2a822a588c7a
cd5985b2-3dde-43fa-a420-896d82a5576b	allowed web origins	openid-connect	oidc-allowed-origins-mapper	\N	58240b4a-31ab-4f78-9839-506121985e67
d4dd1deb-31a0-471a-88ee-22f10afa6a13	upn	openid-connect	oidc-usermodel-attribute-mapper	\N	40061be1-04ff-4613-9339-059166efcc6b
3c6f5a6b-4fa4-46f2-8e41-39d9f5870b4f	groups	openid-connect	oidc-usermodel-realm-role-mapper	\N	40061be1-04ff-4613-9339-059166efcc6b
57371aa9-b9aa-428d-a9ee-fb76873020b7	acr loa level	openid-connect	oidc-acr-mapper	\N	7958aa4d-9c75-43aa-84ce-a35a83617363
f03ec11b-0c37-4ab3-9504-d5cf28777378	auth_time	openid-connect	oidc-usersessionmodel-note-mapper	\N	a745363a-6438-44be-8fe2-387f167fc70d
dfb344a1-fd17-4785-846a-0260d9fa7f2e	sub	openid-connect	oidc-sub-mapper	\N	a745363a-6438-44be-8fe2-387f167fc70d
ed12b37e-3ad0-4709-838b-1bf9ecf49375	organization	openid-connect	oidc-organization-membership-mapper	\N	a6dc6bd4-6b74-43d6-8f61-86abd29754d6
\.


--
-- Data for Name: protocol_mapper_config; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.protocol_mapper_config (protocol_mapper_id, value, name) FROM stdin;
843c7d8b-5106-4d7b-8c71-e6ba13386f83	true	introspection.token.claim
843c7d8b-5106-4d7b-8c71-e6ba13386f83	true	userinfo.token.claim
843c7d8b-5106-4d7b-8c71-e6ba13386f83	locale	user.attribute
843c7d8b-5106-4d7b-8c71-e6ba13386f83	true	id.token.claim
843c7d8b-5106-4d7b-8c71-e6ba13386f83	true	access.token.claim
843c7d8b-5106-4d7b-8c71-e6ba13386f83	locale	claim.name
843c7d8b-5106-4d7b-8c71-e6ba13386f83	String	jsonType.label
944c01ff-4c2a-4846-a151-cdbf20e8b74d	false	single
944c01ff-4c2a-4846-a151-cdbf20e8b74d	Basic	attribute.nameformat
944c01ff-4c2a-4846-a151-cdbf20e8b74d	Role	attribute.name
004c3e2f-ffc0-4e3c-89ff-d80515171bd0	true	introspection.token.claim
004c3e2f-ffc0-4e3c-89ff-d80515171bd0	true	userinfo.token.claim
004c3e2f-ffc0-4e3c-89ff-d80515171bd0	username	user.attribute
004c3e2f-ffc0-4e3c-89ff-d80515171bd0	true	id.token.claim
004c3e2f-ffc0-4e3c-89ff-d80515171bd0	true	access.token.claim
004c3e2f-ffc0-4e3c-89ff-d80515171bd0	preferred_username	claim.name
004c3e2f-ffc0-4e3c-89ff-d80515171bd0	String	jsonType.label
0a0d0865-a8dd-4e04-a0c4-9545b7e7bfbd	true	introspection.token.claim
0a0d0865-a8dd-4e04-a0c4-9545b7e7bfbd	true	userinfo.token.claim
0a0d0865-a8dd-4e04-a0c4-9545b7e7bfbd	updatedAt	user.attribute
0a0d0865-a8dd-4e04-a0c4-9545b7e7bfbd	true	id.token.claim
0a0d0865-a8dd-4e04-a0c4-9545b7e7bfbd	true	access.token.claim
0a0d0865-a8dd-4e04-a0c4-9545b7e7bfbd	updated_at	claim.name
0a0d0865-a8dd-4e04-a0c4-9545b7e7bfbd	long	jsonType.label
1d6533a8-f13b-4fe9-ab5f-7e3fb639431e	true	introspection.token.claim
1d6533a8-f13b-4fe9-ab5f-7e3fb639431e	true	userinfo.token.claim
1d6533a8-f13b-4fe9-ab5f-7e3fb639431e	locale	user.attribute
1d6533a8-f13b-4fe9-ab5f-7e3fb639431e	true	id.token.claim
1d6533a8-f13b-4fe9-ab5f-7e3fb639431e	true	access.token.claim
1d6533a8-f13b-4fe9-ab5f-7e3fb639431e	locale	claim.name
1d6533a8-f13b-4fe9-ab5f-7e3fb639431e	String	jsonType.label
2357506c-9047-43e2-bb28-fb3a537550e5	true	introspection.token.claim
2357506c-9047-43e2-bb28-fb3a537550e5	true	userinfo.token.claim
2357506c-9047-43e2-bb28-fb3a537550e5	zoneinfo	user.attribute
2357506c-9047-43e2-bb28-fb3a537550e5	true	id.token.claim
2357506c-9047-43e2-bb28-fb3a537550e5	true	access.token.claim
2357506c-9047-43e2-bb28-fb3a537550e5	zoneinfo	claim.name
2357506c-9047-43e2-bb28-fb3a537550e5	String	jsonType.label
2ad24d3e-3b55-40fd-8295-1a388b18ac2e	true	introspection.token.claim
2ad24d3e-3b55-40fd-8295-1a388b18ac2e	true	userinfo.token.claim
2ad24d3e-3b55-40fd-8295-1a388b18ac2e	website	user.attribute
2ad24d3e-3b55-40fd-8295-1a388b18ac2e	true	id.token.claim
2ad24d3e-3b55-40fd-8295-1a388b18ac2e	true	access.token.claim
2ad24d3e-3b55-40fd-8295-1a388b18ac2e	website	claim.name
2ad24d3e-3b55-40fd-8295-1a388b18ac2e	String	jsonType.label
76dbd4a7-a7b2-4c74-82c2-254bfc040823	true	introspection.token.claim
76dbd4a7-a7b2-4c74-82c2-254bfc040823	true	userinfo.token.claim
76dbd4a7-a7b2-4c74-82c2-254bfc040823	gender	user.attribute
76dbd4a7-a7b2-4c74-82c2-254bfc040823	true	id.token.claim
76dbd4a7-a7b2-4c74-82c2-254bfc040823	true	access.token.claim
76dbd4a7-a7b2-4c74-82c2-254bfc040823	gender	claim.name
76dbd4a7-a7b2-4c74-82c2-254bfc040823	String	jsonType.label
7ff46067-287f-4939-a663-63d7877c4e3a	true	introspection.token.claim
7ff46067-287f-4939-a663-63d7877c4e3a	true	userinfo.token.claim
7ff46067-287f-4939-a663-63d7877c4e3a	birthdate	user.attribute
7ff46067-287f-4939-a663-63d7877c4e3a	true	id.token.claim
7ff46067-287f-4939-a663-63d7877c4e3a	true	access.token.claim
7ff46067-287f-4939-a663-63d7877c4e3a	birthdate	claim.name
7ff46067-287f-4939-a663-63d7877c4e3a	String	jsonType.label
9b194875-87ce-4278-b391-b724fc099248	true	introspection.token.claim
9b194875-87ce-4278-b391-b724fc099248	true	userinfo.token.claim
9b194875-87ce-4278-b391-b724fc099248	lastName	user.attribute
9b194875-87ce-4278-b391-b724fc099248	true	id.token.claim
9b194875-87ce-4278-b391-b724fc099248	true	access.token.claim
9b194875-87ce-4278-b391-b724fc099248	family_name	claim.name
9b194875-87ce-4278-b391-b724fc099248	String	jsonType.label
b657adbd-0603-4a15-812a-9b70605f9d34	true	introspection.token.claim
b657adbd-0603-4a15-812a-9b70605f9d34	true	userinfo.token.claim
b657adbd-0603-4a15-812a-9b70605f9d34	nickname	user.attribute
b657adbd-0603-4a15-812a-9b70605f9d34	true	id.token.claim
b657adbd-0603-4a15-812a-9b70605f9d34	true	access.token.claim
b657adbd-0603-4a15-812a-9b70605f9d34	nickname	claim.name
b657adbd-0603-4a15-812a-9b70605f9d34	String	jsonType.label
cb1bddd2-1da8-45f7-a016-e67d7026f0b0	true	introspection.token.claim
cb1bddd2-1da8-45f7-a016-e67d7026f0b0	true	userinfo.token.claim
cb1bddd2-1da8-45f7-a016-e67d7026f0b0	picture	user.attribute
cb1bddd2-1da8-45f7-a016-e67d7026f0b0	true	id.token.claim
cb1bddd2-1da8-45f7-a016-e67d7026f0b0	true	access.token.claim
cb1bddd2-1da8-45f7-a016-e67d7026f0b0	picture	claim.name
cb1bddd2-1da8-45f7-a016-e67d7026f0b0	String	jsonType.label
d5520d8f-9b27-4e73-b708-153e50f1b95e	true	introspection.token.claim
d5520d8f-9b27-4e73-b708-153e50f1b95e	true	userinfo.token.claim
d5520d8f-9b27-4e73-b708-153e50f1b95e	middleName	user.attribute
d5520d8f-9b27-4e73-b708-153e50f1b95e	true	id.token.claim
d5520d8f-9b27-4e73-b708-153e50f1b95e	true	access.token.claim
d5520d8f-9b27-4e73-b708-153e50f1b95e	middle_name	claim.name
d5520d8f-9b27-4e73-b708-153e50f1b95e	String	jsonType.label
efc14f98-5fcf-4deb-b0be-02d8e192f169	true	introspection.token.claim
efc14f98-5fcf-4deb-b0be-02d8e192f169	true	userinfo.token.claim
efc14f98-5fcf-4deb-b0be-02d8e192f169	true	id.token.claim
efc14f98-5fcf-4deb-b0be-02d8e192f169	true	access.token.claim
f44fe06a-abf6-46cd-90d9-18f102dac2fc	true	introspection.token.claim
f44fe06a-abf6-46cd-90d9-18f102dac2fc	true	userinfo.token.claim
f44fe06a-abf6-46cd-90d9-18f102dac2fc	firstName	user.attribute
f44fe06a-abf6-46cd-90d9-18f102dac2fc	true	id.token.claim
f44fe06a-abf6-46cd-90d9-18f102dac2fc	true	access.token.claim
f44fe06a-abf6-46cd-90d9-18f102dac2fc	given_name	claim.name
f44fe06a-abf6-46cd-90d9-18f102dac2fc	String	jsonType.label
f5d81f0c-4d06-4185-85c2-034a10a5040a	true	introspection.token.claim
f5d81f0c-4d06-4185-85c2-034a10a5040a	true	userinfo.token.claim
f5d81f0c-4d06-4185-85c2-034a10a5040a	profile	user.attribute
f5d81f0c-4d06-4185-85c2-034a10a5040a	true	id.token.claim
f5d81f0c-4d06-4185-85c2-034a10a5040a	true	access.token.claim
f5d81f0c-4d06-4185-85c2-034a10a5040a	profile	claim.name
f5d81f0c-4d06-4185-85c2-034a10a5040a	String	jsonType.label
3da9c4df-7530-4e4e-9d52-20b5698b548b	true	introspection.token.claim
3da9c4df-7530-4e4e-9d52-20b5698b548b	true	userinfo.token.claim
3da9c4df-7530-4e4e-9d52-20b5698b548b	emailVerified	user.attribute
3da9c4df-7530-4e4e-9d52-20b5698b548b	true	id.token.claim
3da9c4df-7530-4e4e-9d52-20b5698b548b	true	access.token.claim
3da9c4df-7530-4e4e-9d52-20b5698b548b	email_verified	claim.name
3da9c4df-7530-4e4e-9d52-20b5698b548b	boolean	jsonType.label
99a7847d-dc16-4cb9-b6d5-09ad54a10947	true	introspection.token.claim
99a7847d-dc16-4cb9-b6d5-09ad54a10947	true	userinfo.token.claim
99a7847d-dc16-4cb9-b6d5-09ad54a10947	email	user.attribute
99a7847d-dc16-4cb9-b6d5-09ad54a10947	true	id.token.claim
99a7847d-dc16-4cb9-b6d5-09ad54a10947	true	access.token.claim
99a7847d-dc16-4cb9-b6d5-09ad54a10947	email	claim.name
99a7847d-dc16-4cb9-b6d5-09ad54a10947	String	jsonType.label
5813e327-e93a-4093-8af8-95095a1e441b	formatted	user.attribute.formatted
5813e327-e93a-4093-8af8-95095a1e441b	country	user.attribute.country
5813e327-e93a-4093-8af8-95095a1e441b	true	introspection.token.claim
5813e327-e93a-4093-8af8-95095a1e441b	postal_code	user.attribute.postal_code
5813e327-e93a-4093-8af8-95095a1e441b	true	userinfo.token.claim
5813e327-e93a-4093-8af8-95095a1e441b	street	user.attribute.street
5813e327-e93a-4093-8af8-95095a1e441b	true	id.token.claim
5813e327-e93a-4093-8af8-95095a1e441b	region	user.attribute.region
5813e327-e93a-4093-8af8-95095a1e441b	true	access.token.claim
5813e327-e93a-4093-8af8-95095a1e441b	locality	user.attribute.locality
6d41aed9-6f7d-4f25-9dd9-14d516ca3d5a	true	introspection.token.claim
6d41aed9-6f7d-4f25-9dd9-14d516ca3d5a	true	userinfo.token.claim
6d41aed9-6f7d-4f25-9dd9-14d516ca3d5a	phoneNumberVerified	user.attribute
6d41aed9-6f7d-4f25-9dd9-14d516ca3d5a	true	id.token.claim
6d41aed9-6f7d-4f25-9dd9-14d516ca3d5a	true	access.token.claim
6d41aed9-6f7d-4f25-9dd9-14d516ca3d5a	phone_number_verified	claim.name
6d41aed9-6f7d-4f25-9dd9-14d516ca3d5a	boolean	jsonType.label
e0535ebf-96e2-4f24-ad18-50fbd63b9412	true	introspection.token.claim
e0535ebf-96e2-4f24-ad18-50fbd63b9412	true	userinfo.token.claim
e0535ebf-96e2-4f24-ad18-50fbd63b9412	phoneNumber	user.attribute
e0535ebf-96e2-4f24-ad18-50fbd63b9412	true	id.token.claim
e0535ebf-96e2-4f24-ad18-50fbd63b9412	true	access.token.claim
e0535ebf-96e2-4f24-ad18-50fbd63b9412	phone_number	claim.name
e0535ebf-96e2-4f24-ad18-50fbd63b9412	String	jsonType.label
0d621768-4954-4d3b-bf7c-ce569f3f2ffb	true	introspection.token.claim
0d621768-4954-4d3b-bf7c-ce569f3f2ffb	true	multivalued
0d621768-4954-4d3b-bf7c-ce569f3f2ffb	foo	user.attribute
0d621768-4954-4d3b-bf7c-ce569f3f2ffb	true	access.token.claim
0d621768-4954-4d3b-bf7c-ce569f3f2ffb	resource_access.${client_id}.roles	claim.name
0d621768-4954-4d3b-bf7c-ce569f3f2ffb	String	jsonType.label
1b942332-03d0-40d8-86b6-4b5013a75cb8	true	introspection.token.claim
1b942332-03d0-40d8-86b6-4b5013a75cb8	true	multivalued
1b942332-03d0-40d8-86b6-4b5013a75cb8	foo	user.attribute
1b942332-03d0-40d8-86b6-4b5013a75cb8	true	access.token.claim
1b942332-03d0-40d8-86b6-4b5013a75cb8	realm_access.roles	claim.name
1b942332-03d0-40d8-86b6-4b5013a75cb8	String	jsonType.label
4eca33f1-6aef-441e-97f6-7381fd53d743	true	introspection.token.claim
4eca33f1-6aef-441e-97f6-7381fd53d743	true	access.token.claim
cd5985b2-3dde-43fa-a420-896d82a5576b	true	introspection.token.claim
cd5985b2-3dde-43fa-a420-896d82a5576b	true	access.token.claim
3c6f5a6b-4fa4-46f2-8e41-39d9f5870b4f	true	introspection.token.claim
3c6f5a6b-4fa4-46f2-8e41-39d9f5870b4f	true	multivalued
3c6f5a6b-4fa4-46f2-8e41-39d9f5870b4f	foo	user.attribute
3c6f5a6b-4fa4-46f2-8e41-39d9f5870b4f	true	id.token.claim
3c6f5a6b-4fa4-46f2-8e41-39d9f5870b4f	true	access.token.claim
3c6f5a6b-4fa4-46f2-8e41-39d9f5870b4f	groups	claim.name
3c6f5a6b-4fa4-46f2-8e41-39d9f5870b4f	String	jsonType.label
d4dd1deb-31a0-471a-88ee-22f10afa6a13	true	introspection.token.claim
d4dd1deb-31a0-471a-88ee-22f10afa6a13	true	userinfo.token.claim
d4dd1deb-31a0-471a-88ee-22f10afa6a13	username	user.attribute
d4dd1deb-31a0-471a-88ee-22f10afa6a13	true	id.token.claim
d4dd1deb-31a0-471a-88ee-22f10afa6a13	true	access.token.claim
d4dd1deb-31a0-471a-88ee-22f10afa6a13	upn	claim.name
d4dd1deb-31a0-471a-88ee-22f10afa6a13	String	jsonType.label
57371aa9-b9aa-428d-a9ee-fb76873020b7	true	introspection.token.claim
57371aa9-b9aa-428d-a9ee-fb76873020b7	true	id.token.claim
57371aa9-b9aa-428d-a9ee-fb76873020b7	true	access.token.claim
dfb344a1-fd17-4785-846a-0260d9fa7f2e	true	introspection.token.claim
dfb344a1-fd17-4785-846a-0260d9fa7f2e	true	access.token.claim
f03ec11b-0c37-4ab3-9504-d5cf28777378	AUTH_TIME	user.session.note
f03ec11b-0c37-4ab3-9504-d5cf28777378	true	introspection.token.claim
f03ec11b-0c37-4ab3-9504-d5cf28777378	true	id.token.claim
f03ec11b-0c37-4ab3-9504-d5cf28777378	true	access.token.claim
f03ec11b-0c37-4ab3-9504-d5cf28777378	auth_time	claim.name
f03ec11b-0c37-4ab3-9504-d5cf28777378	long	jsonType.label
ed12b37e-3ad0-4709-838b-1bf9ecf49375	true	introspection.token.claim
ed12b37e-3ad0-4709-838b-1bf9ecf49375	true	multivalued
ed12b37e-3ad0-4709-838b-1bf9ecf49375	true	id.token.claim
ed12b37e-3ad0-4709-838b-1bf9ecf49375	true	access.token.claim
ed12b37e-3ad0-4709-838b-1bf9ecf49375	organization	claim.name
ed12b37e-3ad0-4709-838b-1bf9ecf49375	String	jsonType.label
\.


--
-- Data for Name: realm; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm (id, access_code_lifespan, user_action_lifespan, access_token_lifespan, account_theme, admin_theme, email_theme, enabled, events_enabled, events_expiration, login_theme, name, not_before, password_policy, registration_allowed, remember_me, reset_password_allowed, social, ssl_required, sso_idle_timeout, sso_max_lifespan, update_profile_on_soc_login, verify_email, master_admin_client, login_lifespan, internationalization_enabled, default_locale, reg_email_as_username, admin_events_enabled, admin_events_details_enabled, edit_username_allowed, otp_policy_counter, otp_policy_window, otp_policy_period, otp_policy_digits, otp_policy_alg, otp_policy_type, browser_flow, registration_flow, direct_grant_flow, reset_credentials_flow, client_auth_flow, offline_session_idle_timeout, revoke_refresh_token, access_token_life_implicit, login_with_email_allowed, duplicate_emails_allowed, docker_auth_flow, refresh_token_max_reuse, allow_user_managed_access, sso_max_lifespan_remember_me, sso_idle_timeout_remember_me, default_role) FROM stdin;
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	60	300	60	\N	\N	\N	t	f	0	\N	master	0	\N	f	f	f	f	EXTERNAL	1800	36000	f	f	a84af477-3b7f-47f1-8d44-fe2abade00fd	1800	f	\N	f	f	f	f	0	1	30	6	HmacSHA1	totp	753c8b48-ba3e-42b3-bbf0-38ec8165eba7	0cfa8ad1-b2ac-457a-8603-8fae929732b5	a5766eca-1b36-4d33-9346-435533b7b8dd	09add935-4514-42c3-86c3-7062f5dc3e39	cd82d74c-695c-4256-b7fb-1caf60a35be1	2592000	f	900	t	f	9130db23-c271-4ae8-8461-d00c9acfb762	0	f	0	0	821e4bb0-9d76-4771-bc20-8e9083ac457d
\.


--
-- Data for Name: realm_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.realm_attribute (name, realm_id, value) FROM stdin;
_browser_header.contentSecurityPolicyReportOnly	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	
_browser_header.xContentTypeOptions	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	nosniff
_browser_header.referrerPolicy	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	no-referrer
_browser_header.xRobotsTag	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	none
_browser_header.xFrameOptions	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	SAMEORIGIN
_browser_header.contentSecurityPolicy	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	frame-src 'self'; frame-ancestors 'self'; object-src 'none';
_browser_header.xXSSProtection	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	1; mode=block
_browser_header.strictTransportSecurity	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	max-age=31536000; includeSubDomains
bruteForceProtected	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	false
permanentLockout	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	false
maxTemporaryLockouts	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	0
maxFailureWaitSeconds	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	900
minimumQuickLoginWaitSeconds	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	60
waitIncrementSeconds	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	60
quickLoginCheckMilliSeconds	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	1000
maxDeltaTimeSeconds	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	43200
failureFactor	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	30
realmReusableOtpCode	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	false
firstBrokerLoginFlowId	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f04e7eb3-e252-4b89-8c1c-abead85e66ad
displayName	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	Keycloak
displayNameHtml	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	<div class="kc-logo-text"><span>Keycloak</span></div>
defaultSignatureAlgorithm	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	RS256
offlineSessionMaxLifespanEnabled	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	false
offlineSessionMaxLifespan	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	5184000
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
23677baa-69b5-4d4f-bed7-eb3c3fa0462b	jboss-logging
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
password	password	t	t	23677baa-69b5-4d4f-bed7-eb3c3fa0462b
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
917b4f65-b74a-4d60-9b9f-f264bc45e4ca	/realms/master/account/*
9f185538-da56-4880-be80-f0cc3bfcd3cd	/realms/master/account/*
8eeda11d-42fa-4c85-bb09-bd17511aa472	/admin/master/console/*
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
5facbfde-ea94-40c2-bde8-3d33b3de89e3	VERIFY_EMAIL	Verify Email	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	t	f	VERIFY_EMAIL	50
52fe0a64-28b0-48dd-b53b-1e447fd51a22	UPDATE_PROFILE	Update Profile	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	t	f	UPDATE_PROFILE	40
06e0a880-695c-43fb-a1f3-281b3b74c09f	CONFIGURE_TOTP	Configure OTP	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	t	f	CONFIGURE_TOTP	10
3f8f3f96-87ce-4cd8-ac5e-dd3e779e79d0	UPDATE_PASSWORD	Update Password	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	t	f	UPDATE_PASSWORD	30
5481d20e-3f3d-4908-abbc-7746f35760f5	TERMS_AND_CONDITIONS	Terms and Conditions	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f	f	TERMS_AND_CONDITIONS	20
04080571-bb58-48c3-8c25-34845d29a568	delete_account	Delete Account	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	f	f	delete_account	60
79ef0c8d-b184-45d2-b507-272d864700da	delete_credential	Delete Credential	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	t	f	delete_credential	100
88976181-2711-45b8-a94e-d8050b51669e	update_user_locale	Update User Locale	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	t	f	update_user_locale	1000
262dbcd8-9d9b-4af1-a85c-ce5808510b3e	webauthn-register	Webauthn Register	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	t	f	webauthn-register	70
0e956367-0fb1-4e2b-a963-3499cd0a778b	webauthn-register-passwordless	Webauthn Register Passwordless	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	t	f	webauthn-register-passwordless	80
1aa5c2d2-b7de-41a0-8e00-625bef5ef362	VERIFY_PROFILE	Verify Profile	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	t	f	VERIFY_PROFILE	90
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
9f185538-da56-4880-be80-f0cc3bfcd3cd	1a2b2e24-4a09-44f4-8617-d656a46178ad
9f185538-da56-4880-be80-f0cc3bfcd3cd	1951d5d4-dc58-41c4-846b-6e76438d3543
\.


--
-- Data for Name: scope_policy; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.scope_policy (scope_id, policy_id) FROM stdin;
\.


--
-- Data for Name: user_attribute; Type: TABLE DATA; Schema: public; Owner: admin
--

COPY public.user_attribute (name, value, user_id, id, long_value_hash, long_value_hash_lower_case, long_value) FROM stdin;
is_temporary_admin	true	215946ae-82c4-499d-ab37-82f2e3f28b48	e46cab60-e792-477e-bf15-34cd90782b39	\N	\N	\N
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
215946ae-82c4-499d-ab37-82f2e3f28b48	\N	c62da5eb-af6e-4442-8dd6-139fb25e1d28	f	t	\N	\N	\N	23677baa-69b5-4d4f-bed7-eb3c3fa0462b	admin	1762683267829	\N	0
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
821e4bb0-9d76-4771-bc20-8e9083ac457d	215946ae-82c4-499d-ab37-82f2e3f28b48
db4ab77a-a58c-482b-b2ae-2090a009b1af	215946ae-82c4-499d-ab37-82f2e3f28b48
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
8eeda11d-42fa-4c85-bb09-bd17511aa472	+
\.


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
-- Name: fed_user_attr_long_values; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX fed_user_attr_long_values ON public.fed_user_attribute USING btree (long_value_hash, name);


--
-- Name: fed_user_attr_long_values_lower_case; Type: INDEX; Schema: public; Owner: admin
--

CREATE INDEX fed_user_attr_long_values_lower_case ON public.fed_user_attribute USING btree (long_value_hash_lower_case, name);


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
-- PostgreSQL database dump complete
--


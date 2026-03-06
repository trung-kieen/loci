#!/bin/bash
# Copyright 2026 trung-kieen
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

# Use user:user for login to check user login with keycloak
# Make sure check user is verified and temporary password is off
# READ docs; https://documenter.getpostman.com/view/7294517/SzmfZHnd

TOKEN_RESPONSE=$(curl --location 'http://localhost:9090/realms/loci-realm/protocol/openid-connect/token' \
--header 'Content-Type: application/x-www-form-urlencoded' \
--data-urlencode 'client_id=spring' \
--data-urlencode 'username=testuser1@gmail.com' \
--data-urlencode 'password=example1' \
--data-urlencode 'grant_type=password')

# --data-urlencode 'username=user' \
# --data-urlencode 'password=user' \

printf '\n\n\n'
echo $TOKEN_RESPONSE


AUTHORIZATION=$(echo $TOKEN_RESPONSE | jq .access_token -r)


printf '\n\n\n'
echo $AUTHORIZATION

echo  $AUTHORIZATION | xclip -selection c

#!/usr/bin/env bash

set -e
set -x

SERVICE=$1
SSH_CREDS=$2

if [[ -z "$SERVICE" ]]
then
  >&2 echo "Must provide service"
  exit 1
fi

USER=apps1
SERV=31.172.137.40
PORT=60022

mydir=$(cd $(dirname $0); pwd)

echo
echo "DEPLOY... $SERVICE:$GIT_COMMIT"
echo

ssh -i "$SSH_CREDS" -o StrictHostKeyChecking=no -p $PORT $USER@$SERV "
set -e
set -x
GIT_REVISION=$1
kubectl run service_1 --image=xonixx/service_1:$GIT_COMMIT --replicas=1 --port=34701
kubectl expose deployment service_1 --port=34701 --type=LoadBalancer
"
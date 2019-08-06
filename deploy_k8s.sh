#!/usr/bin/env bash

set -e
set -x

SERVICE=$1
SSH_CREDS=$2
PORT=$3

if [[ -z "$SERVICE" ]]
then
  >&2 echo "Must provide service"
  exit 1
fi

USER=apps1
SERV=31.172.137.40
SSH_PORT=60022

mydir=$(cd $(dirname $0); pwd)

echo
echo "DEPLOY... $SERVICE:$GIT_COMMIT"
echo

ssh -i "$SSH_CREDS" -o StrictHostKeyChecking=no -p $SSH_PORT $USER@$SERV "
set -e
set -x
kubectl delete service $SERVICE || true
kubectl delete deployment $SERVICE || true
kubectl create deployment $SERVICE --image=xonixx/$SERVICE:$GIT_COMMIT
kubectl expose deployment $SERVICE --port=$PORT --type=LoadBalancer
echo \"$SERVICE is running on: \$(minikube service $SERVICE --url)\"
"
#!/usr/bin/env bash


GIT_REVISION=$1

kubectl run service_1 --image=xonixx/service_1:$GIT_REVISION --replicas=1 --port=34701

kubectl expose deployment service_1 --port=34701 --type=LoadBalancer
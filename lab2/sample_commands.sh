minikube start --vm-driver=none
cd /root/otus/lab2/pg


kubectl delete -f pg_props.yaml
kubectl create -f pg_props.yaml
kubectl get configmap -o yaml
kubectl get secrets -o yaml
kubectl describe configmap pg-config
kubectl describe secrets pg-secrets

kubectl delete -f postgres.yaml
kubectl apply -f postgres.yaml
docker exec -it $(docker ps | grep k8s_postgres_postgres-statefulset | awk '{print $1}') /bin/bash

export PGPASSWORD=123
psql postgres://myuser:123@postgres-statefulset-0/mydb


kubectl delete -f init_db.yaml
kubectl apply -f init_db.yaml

watch kubectl get all

kubectl logs $(kubectl get all | grep 'pod/init' | awk '{print $1}')
kubectl describe $(kubectl get all | grep 'pod/init' | awk '{print $1}')


cd /root/otus/lab2/app
docker build -t psavchenko/otus-lab2-app:v1 .
docker run -i -p 8080:8080 psavchenko/otus-lab2-app:v1

kubectl delete -f app.yaml
kubectl apply -f app.yaml

kubectl delete ingress lab2-ingress
kubectl apply -f ingress.yaml
kubectl get ing
curl -H 'Host: arch.homework' http://192.168.223.1/api/users

cd /root/otus/lab2/helm
helm delete postgres
helm install postgres ./postgres --dry-run

--
docker volume prune

